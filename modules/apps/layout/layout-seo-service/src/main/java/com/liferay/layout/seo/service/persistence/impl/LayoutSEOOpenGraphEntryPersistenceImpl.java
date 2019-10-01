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

package com.liferay.layout.seo.service.persistence.impl;

import com.liferay.layout.seo.exception.NoSuchOpenGraphEntryException;
import com.liferay.layout.seo.model.LayoutSEOOpenGraphEntry;
import com.liferay.layout.seo.model.impl.LayoutSEOOpenGraphEntryImpl;
import com.liferay.layout.seo.model.impl.LayoutSEOOpenGraphEntryModelImpl;
import com.liferay.layout.seo.service.persistence.LayoutSEOOpenGraphEntryPersistence;
import com.liferay.layout.seo.service.persistence.impl.constants.LayoutSEOPersistenceConstants;
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
 * The persistence implementation for the layout seo open graph entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = LayoutSEOOpenGraphEntryPersistence.class)
public class LayoutSEOOpenGraphEntryPersistenceImpl
	extends BasePersistenceImpl<LayoutSEOOpenGraphEntry>
	implements LayoutSEOOpenGraphEntryPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LayoutSEOOpenGraphEntryUtil</code> to access the layout seo open graph entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LayoutSEOOpenGraphEntryImpl.class.getName();

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
	 * Returns all the layout seo open graph entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout seo open graph entries
	 */
	@Override
	public List<LayoutSEOOpenGraphEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout seo open graph entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOOpenGraphEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo open graph entries
	 * @param end the upper bound of the range of layout seo open graph entries (not inclusive)
	 * @return the range of matching layout seo open graph entries
	 */
	@Override
	public List<LayoutSEOOpenGraphEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout seo open graph entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOOpenGraphEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo open graph entries
	 * @param end the upper bound of the range of layout seo open graph entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo open graph entries
	 */
	@Override
	public List<LayoutSEOOpenGraphEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutSEOOpenGraphEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout seo open graph entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOOpenGraphEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo open graph entries
	 * @param end the upper bound of the range of layout seo open graph entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo open graph entries
	 */
	@Override
	public List<LayoutSEOOpenGraphEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutSEOOpenGraphEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<LayoutSEOOpenGraphEntry> list = null;

		if (useFinderCache) {
			list = (List<LayoutSEOOpenGraphEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry : list) {
					if (!uuid.equals(layoutSEOOpenGraphEntry.getUuid())) {
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

			query.append(_SQL_SELECT_LAYOUTSEOOPENGRAPHENTRY_WHERE);

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
			else if (pagination) {
				query.append(LayoutSEOOpenGraphEntryModelImpl.ORDER_BY_JPQL);
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

				if (!pagination) {
					list = (List<LayoutSEOOpenGraphEntry>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSEOOpenGraphEntry>)QueryUtil.list(
						q, getDialect(), start, end);
				}

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
	 * Returns the first layout seo open graph entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo open graph entry
	 * @throws NoSuchOpenGraphEntryException if a matching layout seo open graph entry could not be found
	 */
	@Override
	public LayoutSEOOpenGraphEntry findByUuid_First(
			String uuid,
			OrderByComparator<LayoutSEOOpenGraphEntry> orderByComparator)
		throws NoSuchOpenGraphEntryException {

		LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (layoutSEOOpenGraphEntry != null) {
			return layoutSEOOpenGraphEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchOpenGraphEntryException(msg.toString());
	}

	/**
	 * Returns the first layout seo open graph entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo open graph entry, or <code>null</code> if a matching layout seo open graph entry could not be found
	 */
	@Override
	public LayoutSEOOpenGraphEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<LayoutSEOOpenGraphEntry> orderByComparator) {

		List<LayoutSEOOpenGraphEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout seo open graph entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo open graph entry
	 * @throws NoSuchOpenGraphEntryException if a matching layout seo open graph entry could not be found
	 */
	@Override
	public LayoutSEOOpenGraphEntry findByUuid_Last(
			String uuid,
			OrderByComparator<LayoutSEOOpenGraphEntry> orderByComparator)
		throws NoSuchOpenGraphEntryException {

		LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (layoutSEOOpenGraphEntry != null) {
			return layoutSEOOpenGraphEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchOpenGraphEntryException(msg.toString());
	}

	/**
	 * Returns the last layout seo open graph entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo open graph entry, or <code>null</code> if a matching layout seo open graph entry could not be found
	 */
	@Override
	public LayoutSEOOpenGraphEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<LayoutSEOOpenGraphEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<LayoutSEOOpenGraphEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout seo open graph entries before and after the current layout seo open graph entry in the ordered set where uuid = &#63;.
	 *
	 * @param layoutSEOEntryId the primary key of the current layout seo open graph entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo open graph entry
	 * @throws NoSuchOpenGraphEntryException if a layout seo open graph entry with the primary key could not be found
	 */
	@Override
	public LayoutSEOOpenGraphEntry[] findByUuid_PrevAndNext(
			long layoutSEOEntryId, String uuid,
			OrderByComparator<LayoutSEOOpenGraphEntry> orderByComparator)
		throws NoSuchOpenGraphEntryException {

		uuid = Objects.toString(uuid, "");

		LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry = findByPrimaryKey(
			layoutSEOEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutSEOOpenGraphEntry[] array =
				new LayoutSEOOpenGraphEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, layoutSEOOpenGraphEntry, uuid, orderByComparator,
				true);

			array[1] = layoutSEOOpenGraphEntry;

			array[2] = getByUuid_PrevAndNext(
				session, layoutSEOOpenGraphEntry, uuid, orderByComparator,
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

	protected LayoutSEOOpenGraphEntry getByUuid_PrevAndNext(
		Session session, LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry,
		String uuid,
		OrderByComparator<LayoutSEOOpenGraphEntry> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTSEOOPENGRAPHENTRY_WHERE);

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
			query.append(LayoutSEOOpenGraphEntryModelImpl.ORDER_BY_JPQL);
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
						layoutSEOOpenGraphEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSEOOpenGraphEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout seo open graph entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutSEOOpenGraphEntry);
		}
	}

	/**
	 * Returns the number of layout seo open graph entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout seo open graph entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTSEOOPENGRAPHENTRY_WHERE);

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
		"layoutSEOOpenGraphEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(layoutSEOOpenGraphEntry.uuid IS NULL OR layoutSEOOpenGraphEntry.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the layout seo open graph entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchOpenGraphEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout seo open graph entry
	 * @throws NoSuchOpenGraphEntryException if a matching layout seo open graph entry could not be found
	 */
	@Override
	public LayoutSEOOpenGraphEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchOpenGraphEntryException {

		LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry = fetchByUUID_G(
			uuid, groupId);

		if (layoutSEOOpenGraphEntry == null) {
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

			throw new NoSuchOpenGraphEntryException(msg.toString());
		}

		return layoutSEOOpenGraphEntry;
	}

	/**
	 * Returns the layout seo open graph entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout seo open graph entry, or <code>null</code> if a matching layout seo open graph entry could not be found
	 */
	@Override
	public LayoutSEOOpenGraphEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the layout seo open graph entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo open graph entry, or <code>null</code> if a matching layout seo open graph entry could not be found
	 */
	@Override
	public LayoutSEOOpenGraphEntry fetchByUUID_G(
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

		if (result instanceof LayoutSEOOpenGraphEntry) {
			LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry =
				(LayoutSEOOpenGraphEntry)result;

			if (!Objects.equals(uuid, layoutSEOOpenGraphEntry.getUuid()) ||
				(groupId != layoutSEOOpenGraphEntry.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LAYOUTSEOOPENGRAPHENTRY_WHERE);

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

				List<LayoutSEOOpenGraphEntry> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry = list.get(
						0);

					result = layoutSEOOpenGraphEntry;

					cacheResult(layoutSEOOpenGraphEntry);
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
			return (LayoutSEOOpenGraphEntry)result;
		}
	}

	/**
	 * Removes the layout seo open graph entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout seo open graph entry that was removed
	 */
	@Override
	public LayoutSEOOpenGraphEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchOpenGraphEntryException {

		LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry = findByUUID_G(
			uuid, groupId);

		return remove(layoutSEOOpenGraphEntry);
	}

	/**
	 * Returns the number of layout seo open graph entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout seo open graph entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTSEOOPENGRAPHENTRY_WHERE);

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
		"layoutSEOOpenGraphEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(layoutSEOOpenGraphEntry.uuid IS NULL OR layoutSEOOpenGraphEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"layoutSEOOpenGraphEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the layout seo open graph entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout seo open graph entries
	 */
	@Override
	public List<LayoutSEOOpenGraphEntry> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout seo open graph entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOOpenGraphEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo open graph entries
	 * @param end the upper bound of the range of layout seo open graph entries (not inclusive)
	 * @return the range of matching layout seo open graph entries
	 */
	@Override
	public List<LayoutSEOOpenGraphEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout seo open graph entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOOpenGraphEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo open graph entries
	 * @param end the upper bound of the range of layout seo open graph entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo open graph entries
	 */
	@Override
	public List<LayoutSEOOpenGraphEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutSEOOpenGraphEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout seo open graph entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOOpenGraphEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo open graph entries
	 * @param end the upper bound of the range of layout seo open graph entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo open graph entries
	 */
	@Override
	public List<LayoutSEOOpenGraphEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutSEOOpenGraphEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

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

		List<LayoutSEOOpenGraphEntry> list = null;

		if (useFinderCache) {
			list = (List<LayoutSEOOpenGraphEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry : list) {
					if (!uuid.equals(layoutSEOOpenGraphEntry.getUuid()) ||
						(companyId != layoutSEOOpenGraphEntry.getCompanyId())) {

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

			query.append(_SQL_SELECT_LAYOUTSEOOPENGRAPHENTRY_WHERE);

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
			else if (pagination) {
				query.append(LayoutSEOOpenGraphEntryModelImpl.ORDER_BY_JPQL);
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

				if (!pagination) {
					list = (List<LayoutSEOOpenGraphEntry>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSEOOpenGraphEntry>)QueryUtil.list(
						q, getDialect(), start, end);
				}

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
	 * Returns the first layout seo open graph entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo open graph entry
	 * @throws NoSuchOpenGraphEntryException if a matching layout seo open graph entry could not be found
	 */
	@Override
	public LayoutSEOOpenGraphEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LayoutSEOOpenGraphEntry> orderByComparator)
		throws NoSuchOpenGraphEntryException {

		LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (layoutSEOOpenGraphEntry != null) {
			return layoutSEOOpenGraphEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchOpenGraphEntryException(msg.toString());
	}

	/**
	 * Returns the first layout seo open graph entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo open graph entry, or <code>null</code> if a matching layout seo open graph entry could not be found
	 */
	@Override
	public LayoutSEOOpenGraphEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LayoutSEOOpenGraphEntry> orderByComparator) {

		List<LayoutSEOOpenGraphEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout seo open graph entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo open graph entry
	 * @throws NoSuchOpenGraphEntryException if a matching layout seo open graph entry could not be found
	 */
	@Override
	public LayoutSEOOpenGraphEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LayoutSEOOpenGraphEntry> orderByComparator)
		throws NoSuchOpenGraphEntryException {

		LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (layoutSEOOpenGraphEntry != null) {
			return layoutSEOOpenGraphEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchOpenGraphEntryException(msg.toString());
	}

	/**
	 * Returns the last layout seo open graph entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo open graph entry, or <code>null</code> if a matching layout seo open graph entry could not be found
	 */
	@Override
	public LayoutSEOOpenGraphEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LayoutSEOOpenGraphEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<LayoutSEOOpenGraphEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout seo open graph entries before and after the current layout seo open graph entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutSEOEntryId the primary key of the current layout seo open graph entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo open graph entry
	 * @throws NoSuchOpenGraphEntryException if a layout seo open graph entry with the primary key could not be found
	 */
	@Override
	public LayoutSEOOpenGraphEntry[] findByUuid_C_PrevAndNext(
			long layoutSEOEntryId, String uuid, long companyId,
			OrderByComparator<LayoutSEOOpenGraphEntry> orderByComparator)
		throws NoSuchOpenGraphEntryException {

		uuid = Objects.toString(uuid, "");

		LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry = findByPrimaryKey(
			layoutSEOEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutSEOOpenGraphEntry[] array =
				new LayoutSEOOpenGraphEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, layoutSEOOpenGraphEntry, uuid, companyId,
				orderByComparator, true);

			array[1] = layoutSEOOpenGraphEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, layoutSEOOpenGraphEntry, uuid, companyId,
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

	protected LayoutSEOOpenGraphEntry getByUuid_C_PrevAndNext(
		Session session, LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry,
		String uuid, long companyId,
		OrderByComparator<LayoutSEOOpenGraphEntry> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTSEOOPENGRAPHENTRY_WHERE);

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
			query.append(LayoutSEOOpenGraphEntryModelImpl.ORDER_BY_JPQL);
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
						layoutSEOOpenGraphEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSEOOpenGraphEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout seo open graph entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutSEOOpenGraphEntry);
		}
	}

	/**
	 * Returns the number of layout seo open graph entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout seo open graph entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTSEOOPENGRAPHENTRY_WHERE);

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
		"layoutSEOOpenGraphEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(layoutSEOOpenGraphEntry.uuid IS NULL OR layoutSEOOpenGraphEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"layoutSEOOpenGraphEntry.companyId = ?";

	private FinderPath _finderPathFetchByG_P_L;
	private FinderPath _finderPathCountByG_P_L;

	/**
	 * Returns the layout seo open graph entry where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or throws a <code>NoSuchOpenGraphEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching layout seo open graph entry
	 * @throws NoSuchOpenGraphEntryException if a matching layout seo open graph entry could not be found
	 */
	@Override
	public LayoutSEOOpenGraphEntry findByG_P_L(
			long groupId, boolean privateLayout, long layoutId)
		throws NoSuchOpenGraphEntryException {

		LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry = fetchByG_P_L(
			groupId, privateLayout, layoutId);

		if (layoutSEOOpenGraphEntry == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append(", layoutId=");
			msg.append(layoutId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchOpenGraphEntryException(msg.toString());
		}

		return layoutSEOOpenGraphEntry;
	}

	/**
	 * Returns the layout seo open graph entry where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching layout seo open graph entry, or <code>null</code> if a matching layout seo open graph entry could not be found
	 */
	@Override
	public LayoutSEOOpenGraphEntry fetchByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		return fetchByG_P_L(groupId, privateLayout, layoutId, true);
	}

	/**
	 * Returns the layout seo open graph entry where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo open graph entry, or <code>null</code> if a matching layout seo open graph entry could not be found
	 */
	@Override
	public LayoutSEOOpenGraphEntry fetchByG_P_L(
		long groupId, boolean privateLayout, long layoutId,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, privateLayout, layoutId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByG_P_L, finderArgs, this);
		}

		if (result instanceof LayoutSEOOpenGraphEntry) {
			LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry =
				(LayoutSEOOpenGraphEntry)result;

			if ((groupId != layoutSEOOpenGraphEntry.getGroupId()) ||
				(privateLayout != layoutSEOOpenGraphEntry.isPrivateLayout()) ||
				(layoutId != layoutSEOOpenGraphEntry.getLayoutId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_LAYOUTSEOOPENGRAPHENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_P_L_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_L_LAYOUTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				List<LayoutSEOOpenGraphEntry> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_P_L, finderArgs, list);
					}
				}
				else {
					LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry = list.get(
						0);

					result = layoutSEOOpenGraphEntry;

					cacheResult(layoutSEOOpenGraphEntry);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByG_P_L, finderArgs);
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
			return (LayoutSEOOpenGraphEntry)result;
		}
	}

	/**
	 * Removes the layout seo open graph entry where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the layout seo open graph entry that was removed
	 */
	@Override
	public LayoutSEOOpenGraphEntry removeByG_P_L(
			long groupId, boolean privateLayout, long layoutId)
		throws NoSuchOpenGraphEntryException {

		LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry = findByG_P_L(
			groupId, privateLayout, layoutId);

		return remove(layoutSEOOpenGraphEntry);
	}

	/**
	 * Returns the number of layout seo open graph entries where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the number of matching layout seo open graph entries
	 */
	@Override
	public int countByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		FinderPath finderPath = _finderPathCountByG_P_L;

		Object[] finderArgs = new Object[] {groupId, privateLayout, layoutId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTSEOOPENGRAPHENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_P_L_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_L_LAYOUTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

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

	private static final String _FINDER_COLUMN_G_P_L_GROUPID_2 =
		"layoutSEOOpenGraphEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2 =
		"layoutSEOOpenGraphEntry.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_LAYOUTID_2 =
		"layoutSEOOpenGraphEntry.layoutId = ?";

	public LayoutSEOOpenGraphEntryPersistenceImpl() {
		setModelClass(LayoutSEOOpenGraphEntry.class);

		setModelImplClass(LayoutSEOOpenGraphEntryImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the layout seo open graph entry in the entity cache if it is enabled.
	 *
	 * @param layoutSEOOpenGraphEntry the layout seo open graph entry
	 */
	@Override
	public void cacheResult(LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry) {
		entityCache.putResult(
			entityCacheEnabled, LayoutSEOOpenGraphEntryImpl.class,
			layoutSEOOpenGraphEntry.getPrimaryKey(), layoutSEOOpenGraphEntry);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				layoutSEOOpenGraphEntry.getUuid(),
				layoutSEOOpenGraphEntry.getGroupId()
			},
			layoutSEOOpenGraphEntry);

		finderCache.putResult(
			_finderPathFetchByG_P_L,
			new Object[] {
				layoutSEOOpenGraphEntry.getGroupId(),
				layoutSEOOpenGraphEntry.isPrivateLayout(),
				layoutSEOOpenGraphEntry.getLayoutId()
			},
			layoutSEOOpenGraphEntry);

		layoutSEOOpenGraphEntry.resetOriginalValues();
	}

	/**
	 * Caches the layout seo open graph entries in the entity cache if it is enabled.
	 *
	 * @param layoutSEOOpenGraphEntries the layout seo open graph entries
	 */
	@Override
	public void cacheResult(
		List<LayoutSEOOpenGraphEntry> layoutSEOOpenGraphEntries) {

		for (LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry :
				layoutSEOOpenGraphEntries) {

			if (entityCache.getResult(
					entityCacheEnabled, LayoutSEOOpenGraphEntryImpl.class,
					layoutSEOOpenGraphEntry.getPrimaryKey()) == null) {

				cacheResult(layoutSEOOpenGraphEntry);
			}
			else {
				layoutSEOOpenGraphEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout seo open graph entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LayoutSEOOpenGraphEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout seo open graph entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry) {
		entityCache.removeResult(
			entityCacheEnabled, LayoutSEOOpenGraphEntryImpl.class,
			layoutSEOOpenGraphEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(LayoutSEOOpenGraphEntryModelImpl)layoutSEOOpenGraphEntry, true);
	}

	@Override
	public void clearCache(
		List<LayoutSEOOpenGraphEntry> layoutSEOOpenGraphEntries) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry :
				layoutSEOOpenGraphEntries) {

			entityCache.removeResult(
				entityCacheEnabled, LayoutSEOOpenGraphEntryImpl.class,
				layoutSEOOpenGraphEntry.getPrimaryKey());

			clearUniqueFindersCache(
				(LayoutSEOOpenGraphEntryModelImpl)layoutSEOOpenGraphEntry,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		LayoutSEOOpenGraphEntryModelImpl layoutSEOOpenGraphEntryModelImpl) {

		Object[] args = new Object[] {
			layoutSEOOpenGraphEntryModelImpl.getUuid(),
			layoutSEOOpenGraphEntryModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, layoutSEOOpenGraphEntryModelImpl,
			false);

		args = new Object[] {
			layoutSEOOpenGraphEntryModelImpl.getGroupId(),
			layoutSEOOpenGraphEntryModelImpl.isPrivateLayout(),
			layoutSEOOpenGraphEntryModelImpl.getLayoutId()
		};

		finderCache.putResult(
			_finderPathCountByG_P_L, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_P_L, args, layoutSEOOpenGraphEntryModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		LayoutSEOOpenGraphEntryModelImpl layoutSEOOpenGraphEntryModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				layoutSEOOpenGraphEntryModelImpl.getUuid(),
				layoutSEOOpenGraphEntryModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((layoutSEOOpenGraphEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				layoutSEOOpenGraphEntryModelImpl.getOriginalUuid(),
				layoutSEOOpenGraphEntryModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				layoutSEOOpenGraphEntryModelImpl.getGroupId(),
				layoutSEOOpenGraphEntryModelImpl.isPrivateLayout(),
				layoutSEOOpenGraphEntryModelImpl.getLayoutId()
			};

			finderCache.removeResult(_finderPathCountByG_P_L, args);
			finderCache.removeResult(_finderPathFetchByG_P_L, args);
		}

		if ((layoutSEOOpenGraphEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_P_L.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				layoutSEOOpenGraphEntryModelImpl.getOriginalGroupId(),
				layoutSEOOpenGraphEntryModelImpl.getOriginalPrivateLayout(),
				layoutSEOOpenGraphEntryModelImpl.getOriginalLayoutId()
			};

			finderCache.removeResult(_finderPathCountByG_P_L, args);
			finderCache.removeResult(_finderPathFetchByG_P_L, args);
		}
	}

	/**
	 * Creates a new layout seo open graph entry with the primary key. Does not add the layout seo open graph entry to the database.
	 *
	 * @param layoutSEOEntryId the primary key for the new layout seo open graph entry
	 * @return the new layout seo open graph entry
	 */
	@Override
	public LayoutSEOOpenGraphEntry create(long layoutSEOEntryId) {
		LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry =
			new LayoutSEOOpenGraphEntryImpl();

		layoutSEOOpenGraphEntry.setNew(true);
		layoutSEOOpenGraphEntry.setPrimaryKey(layoutSEOEntryId);

		String uuid = PortalUUIDUtil.generate();

		layoutSEOOpenGraphEntry.setUuid(uuid);

		layoutSEOOpenGraphEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return layoutSEOOpenGraphEntry;
	}

	/**
	 * Removes the layout seo open graph entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOEntryId the primary key of the layout seo open graph entry
	 * @return the layout seo open graph entry that was removed
	 * @throws NoSuchOpenGraphEntryException if a layout seo open graph entry with the primary key could not be found
	 */
	@Override
	public LayoutSEOOpenGraphEntry remove(long layoutSEOEntryId)
		throws NoSuchOpenGraphEntryException {

		return remove((Serializable)layoutSEOEntryId);
	}

	/**
	 * Removes the layout seo open graph entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout seo open graph entry
	 * @return the layout seo open graph entry that was removed
	 * @throws NoSuchOpenGraphEntryException if a layout seo open graph entry with the primary key could not be found
	 */
	@Override
	public LayoutSEOOpenGraphEntry remove(Serializable primaryKey)
		throws NoSuchOpenGraphEntryException {

		Session session = null;

		try {
			session = openSession();

			LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry =
				(LayoutSEOOpenGraphEntry)session.get(
					LayoutSEOOpenGraphEntryImpl.class, primaryKey);

			if (layoutSEOOpenGraphEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOpenGraphEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(layoutSEOOpenGraphEntry);
		}
		catch (NoSuchOpenGraphEntryException nsee) {
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
	protected LayoutSEOOpenGraphEntry removeImpl(
		LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutSEOOpenGraphEntry)) {
				layoutSEOOpenGraphEntry = (LayoutSEOOpenGraphEntry)session.get(
					LayoutSEOOpenGraphEntryImpl.class,
					layoutSEOOpenGraphEntry.getPrimaryKeyObj());
			}

			if (layoutSEOOpenGraphEntry != null) {
				session.delete(layoutSEOOpenGraphEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutSEOOpenGraphEntry != null) {
			clearCache(layoutSEOOpenGraphEntry);
		}

		return layoutSEOOpenGraphEntry;
	}

	@Override
	public LayoutSEOOpenGraphEntry updateImpl(
		LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry) {

		boolean isNew = layoutSEOOpenGraphEntry.isNew();

		if (!(layoutSEOOpenGraphEntry instanceof
				LayoutSEOOpenGraphEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(layoutSEOOpenGraphEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					layoutSEOOpenGraphEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in layoutSEOOpenGraphEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LayoutSEOOpenGraphEntry implementation " +
					layoutSEOOpenGraphEntry.getClass());
		}

		LayoutSEOOpenGraphEntryModelImpl layoutSEOOpenGraphEntryModelImpl =
			(LayoutSEOOpenGraphEntryModelImpl)layoutSEOOpenGraphEntry;

		if (Validator.isNull(layoutSEOOpenGraphEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			layoutSEOOpenGraphEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (layoutSEOOpenGraphEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutSEOOpenGraphEntry.setCreateDate(now);
			}
			else {
				layoutSEOOpenGraphEntry.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!layoutSEOOpenGraphEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutSEOOpenGraphEntry.setModifiedDate(now);
			}
			else {
				layoutSEOOpenGraphEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (layoutSEOOpenGraphEntry.isNew()) {
				session.save(layoutSEOOpenGraphEntry);

				layoutSEOOpenGraphEntry.setNew(false);
			}
			else {
				layoutSEOOpenGraphEntry =
					(LayoutSEOOpenGraphEntry)session.merge(
						layoutSEOOpenGraphEntry);
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
			Object[] args = new Object[] {
				layoutSEOOpenGraphEntryModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				layoutSEOOpenGraphEntryModelImpl.getUuid(),
				layoutSEOOpenGraphEntryModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((layoutSEOOpenGraphEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutSEOOpenGraphEntryModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {
					layoutSEOOpenGraphEntryModelImpl.getUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((layoutSEOOpenGraphEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutSEOOpenGraphEntryModelImpl.getOriginalUuid(),
					layoutSEOOpenGraphEntryModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					layoutSEOOpenGraphEntryModelImpl.getUuid(),
					layoutSEOOpenGraphEntryModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, LayoutSEOOpenGraphEntryImpl.class,
			layoutSEOOpenGraphEntry.getPrimaryKey(), layoutSEOOpenGraphEntry,
			false);

		clearUniqueFindersCache(layoutSEOOpenGraphEntryModelImpl, false);
		cacheUniqueFindersCache(layoutSEOOpenGraphEntryModelImpl);

		layoutSEOOpenGraphEntry.resetOriginalValues();

		return layoutSEOOpenGraphEntry;
	}

	/**
	 * Returns the layout seo open graph entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout seo open graph entry
	 * @return the layout seo open graph entry
	 * @throws NoSuchOpenGraphEntryException if a layout seo open graph entry with the primary key could not be found
	 */
	@Override
	public LayoutSEOOpenGraphEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchOpenGraphEntryException {

		LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry = fetchByPrimaryKey(
			primaryKey);

		if (layoutSEOOpenGraphEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOpenGraphEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return layoutSEOOpenGraphEntry;
	}

	/**
	 * Returns the layout seo open graph entry with the primary key or throws a <code>NoSuchOpenGraphEntryException</code> if it could not be found.
	 *
	 * @param layoutSEOEntryId the primary key of the layout seo open graph entry
	 * @return the layout seo open graph entry
	 * @throws NoSuchOpenGraphEntryException if a layout seo open graph entry with the primary key could not be found
	 */
	@Override
	public LayoutSEOOpenGraphEntry findByPrimaryKey(long layoutSEOEntryId)
		throws NoSuchOpenGraphEntryException {

		return findByPrimaryKey((Serializable)layoutSEOEntryId);
	}

	/**
	 * Returns the layout seo open graph entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutSEOEntryId the primary key of the layout seo open graph entry
	 * @return the layout seo open graph entry, or <code>null</code> if a layout seo open graph entry with the primary key could not be found
	 */
	@Override
	public LayoutSEOOpenGraphEntry fetchByPrimaryKey(long layoutSEOEntryId) {
		return fetchByPrimaryKey((Serializable)layoutSEOEntryId);
	}

	/**
	 * Returns all the layout seo open graph entries.
	 *
	 * @return the layout seo open graph entries
	 */
	@Override
	public List<LayoutSEOOpenGraphEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout seo open graph entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOOpenGraphEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo open graph entries
	 * @param end the upper bound of the range of layout seo open graph entries (not inclusive)
	 * @return the range of layout seo open graph entries
	 */
	@Override
	public List<LayoutSEOOpenGraphEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout seo open graph entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOOpenGraphEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo open graph entries
	 * @param end the upper bound of the range of layout seo open graph entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout seo open graph entries
	 */
	@Override
	public List<LayoutSEOOpenGraphEntry> findAll(
		int start, int end,
		OrderByComparator<LayoutSEOOpenGraphEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout seo open graph entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOOpenGraphEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo open graph entries
	 * @param end the upper bound of the range of layout seo open graph entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout seo open graph entries
	 */
	@Override
	public List<LayoutSEOOpenGraphEntry> findAll(
		int start, int end,
		OrderByComparator<LayoutSEOOpenGraphEntry> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<LayoutSEOOpenGraphEntry> list = null;

		if (useFinderCache) {
			list = (List<LayoutSEOOpenGraphEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LAYOUTSEOOPENGRAPHENTRY);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTSEOOPENGRAPHENTRY;

				if (pagination) {
					sql = sql.concat(
						LayoutSEOOpenGraphEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LayoutSEOOpenGraphEntry>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSEOOpenGraphEntry>)QueryUtil.list(
						q, getDialect(), start, end);
				}

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
	 * Removes all the layout seo open graph entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry : findAll()) {
			remove(layoutSEOOpenGraphEntry);
		}
	}

	/**
	 * Returns the number of layout seo open graph entries.
	 *
	 * @return the number of layout seo open graph entries
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
					_SQL_COUNT_LAYOUTSEOOPENGRAPHENTRY);

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
		return "layoutSEOEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAYOUTSEOOPENGRAPHENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LayoutSEOOpenGraphEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout seo open graph entry persistence.
	 */
	@Activate
	public void activate() {
		LayoutSEOOpenGraphEntryModelImpl.setEntityCacheEnabled(
			entityCacheEnabled);
		LayoutSEOOpenGraphEntryModelImpl.setFinderCacheEnabled(
			finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutSEOOpenGraphEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutSEOOpenGraphEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutSEOOpenGraphEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutSEOOpenGraphEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			LayoutSEOOpenGraphEntryModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutSEOOpenGraphEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			LayoutSEOOpenGraphEntryModelImpl.UUID_COLUMN_BITMASK |
			LayoutSEOOpenGraphEntryModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutSEOOpenGraphEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutSEOOpenGraphEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			LayoutSEOOpenGraphEntryModelImpl.UUID_COLUMN_BITMASK |
			LayoutSEOOpenGraphEntryModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathFetchByG_P_L = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutSEOOpenGraphEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			},
			LayoutSEOOpenGraphEntryModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutSEOOpenGraphEntryModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutSEOOpenGraphEntryModelImpl.LAYOUTID_COLUMN_BITMASK);

		_finderPathCountByG_P_L = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(LayoutSEOOpenGraphEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = LayoutSEOPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.layout.seo.model.LayoutSEOOpenGraphEntry"),
			true);
	}

	@Override
	@Reference(
		target = LayoutSEOPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = LayoutSEOPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_LAYOUTSEOOPENGRAPHENTRY =
		"SELECT layoutSEOOpenGraphEntry FROM LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry";

	private static final String _SQL_SELECT_LAYOUTSEOOPENGRAPHENTRY_WHERE =
		"SELECT layoutSEOOpenGraphEntry FROM LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry WHERE ";

	private static final String _SQL_COUNT_LAYOUTSEOOPENGRAPHENTRY =
		"SELECT COUNT(layoutSEOOpenGraphEntry) FROM LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry";

	private static final String _SQL_COUNT_LAYOUTSEOOPENGRAPHENTRY_WHERE =
		"SELECT COUNT(layoutSEOOpenGraphEntry) FROM LayoutSEOOpenGraphEntry layoutSEOOpenGraphEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"layoutSEOOpenGraphEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LayoutSEOOpenGraphEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LayoutSEOOpenGraphEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSEOOpenGraphEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	static {
		try {
			Class.forName(LayoutSEOPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}