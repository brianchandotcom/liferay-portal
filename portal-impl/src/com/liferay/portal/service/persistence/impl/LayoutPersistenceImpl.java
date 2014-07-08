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

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.LayoutModelImpl;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.LayoutPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the layout service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPersistence
 * @see LayoutUtil
 * @generated
 */
public class LayoutPersistenceImpl extends BasePersistenceImpl<Layout>
	implements LayoutPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LayoutUtil} to access the layout persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			LayoutModelImpl.UUID_COLUMN_BITMASK |
			LayoutModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
			LayoutModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the layouts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layouts
	 */
	@Override
	public List<Layout> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts
	 */
	@Override
	public List<Layout> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layouts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Layout layout : list) {
				if (!Validator.equals(uuid, layout.getUuid())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
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
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws NoSuchLayoutException {
		Layout layout = fetchByUuid_First(uuid, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the first layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) {
		List<Layout> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws NoSuchLayoutException {
		Layout layout = fetchByUuid_Last(uuid, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the last layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<Layout> list = findByUuid(uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layouts before and after the current layout in the ordered set where uuid = &#63;.
	 *
	 * @param plid the primary key of the current layout
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findByUuid_PrevAndNext(long plid, String uuid,
		OrderByComparator orderByComparator) throws NoSuchLayoutException {
		Layout layout = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Layout[] array = new LayoutImpl[3];

			array[0] = getByUuid_PrevAndNext(session, layout, uuid,
					orderByComparator, true);

			array[1] = layout;

			array[2] = getByUuid_PrevAndNext(session, layout, uuid,
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

	protected Layout getByUuid_PrevAndNext(Session session, Layout layout,
		String uuid, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUT_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(LayoutModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(layout);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Layout> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layouts where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (Layout layout : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(layout);
		}
	}

	/**
	 * Returns the number of layouts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layouts
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "layout.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "layout.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(layout.uuid IS NULL OR layout.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G_P",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			LayoutModelImpl.UUID_COLUMN_BITMASK |
			LayoutModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutModelImpl.PRIVATELAYOUT_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G_P",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

	/**
	 * Returns the layout where uuid = &#63; and groupId = &#63; and privateLayout = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByUUID_G_P(String uuid, long groupId,
		boolean privateLayout) throws NoSuchLayoutException {
		Layout layout = fetchByUUID_G_P(uuid, groupId, privateLayout);

		if (layout == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLayoutException(msg.toString());
		}

		return layout;
	}

	/**
	 * Returns the layout where uuid = &#63; and groupId = &#63; and privateLayout = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByUUID_G_P(String uuid, long groupId,
		boolean privateLayout) {
		return fetchByUUID_G_P(uuid, groupId, privateLayout, true);
	}

	/**
	 * Returns the layout where uuid = &#63; and groupId = &#63; and privateLayout = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByUUID_G_P(String uuid, long groupId,
		boolean privateLayout, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId, privateLayout };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G_P,
					finderArgs, this);
		}

		if (result instanceof Layout) {
			Layout layout = (Layout)result;

			if (!Validator.equals(uuid, layout.getUuid()) ||
					(groupId != layout.getGroupId()) ||
					(privateLayout != layout.getPrivateLayout())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_P_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_P_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_P_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_UUID_G_P_PRIVATELAYOUT_2);

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

				qPos.add(privateLayout);

				List<Layout> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G_P,
						finderArgs, list);
				}
				else {
					Layout layout = list.get(0);

					result = layout;

					cacheResult(layout);

					if ((layout.getUuid() == null) ||
							!layout.getUuid().equals(uuid) ||
							(layout.getGroupId() != groupId) ||
							(layout.getPrivateLayout() != privateLayout)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G_P,
							finderArgs, layout);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G_P,
					finderArgs);

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
			return (Layout)result;
		}
	}

	/**
	 * Removes the layout where uuid = &#63; and groupId = &#63; and privateLayout = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the layout that was removed
	 */
	@Override
	public Layout removeByUUID_G_P(String uuid, long groupId,
		boolean privateLayout) throws NoSuchLayoutException {
		Layout layout = findByUUID_G_P(uuid, groupId, privateLayout);

		return remove(layout);
	}

	/**
	 * Returns the number of layouts where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the number of matching layouts
	 */
	@Override
	public int countByUUID_G_P(String uuid, long groupId, boolean privateLayout) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G_P;

		Object[] finderArgs = new Object[] { uuid, groupId, privateLayout };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_P_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_P_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_P_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_UUID_G_P_PRIVATELAYOUT_2);

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

				qPos.add(privateLayout);

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

	private static final String _FINDER_COLUMN_UUID_G_P_UUID_1 = "layout.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_P_UUID_2 = "layout.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_P_UUID_3 = "(layout.uuid IS NULL OR layout.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_P_GROUPID_2 = "layout.groupId = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_P_PRIVATELAYOUT_2 = "layout.privateLayout = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			LayoutModelImpl.UUID_COLUMN_BITMASK |
			LayoutModelImpl.COMPANYID_COLUMN_BITMASK |
			LayoutModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
			LayoutModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layouts
	 */
	@Override
	public List<Layout> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts
	 */
	@Override
	public List<Layout> findByUuid_C(String uuid, long companyId, int start,
		int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Layout layout : list) {
				if (!Validator.equals(uuid, layout.getUuid()) ||
						(companyId != layout.getCompanyId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
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
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws NoSuchLayoutException {
		Layout layout = fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the first layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) {
		List<Layout> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws NoSuchLayoutException {
		Layout layout = fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the last layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<Layout> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layouts before and after the current layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param plid the primary key of the current layout
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findByUuid_C_PrevAndNext(long plid, String uuid,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Layout[] array = new LayoutImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, layout, uuid,
					companyId, orderByComparator, true);

			array[1] = layout;

			array[2] = getByUuid_C_PrevAndNext(session, layout, uuid,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Layout getByUuid_C_PrevAndNext(Session session, Layout layout,
		String uuid, long companyId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUT_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(LayoutModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(layout);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Layout> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layouts where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (Layout layout : findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(layout);
		}
	}

	/**
	 * Returns the number of layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layouts
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "layout.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "layout.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(layout.uuid IS NULL OR layout.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "layout.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			LayoutModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
			LayoutModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the layouts where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layouts
	 */
	@Override
	public List<Layout> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts
	 */
	@Override
	public List<Layout> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layouts where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Layout layout : list) {
				if ((groupId != layout.getGroupId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws NoSuchLayoutException {
		Layout layout = fetchByGroupId_First(groupId, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the first layout in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) {
		List<Layout> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws NoSuchLayoutException {
		Layout layout = fetchByGroupId_Last(groupId, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the last layout in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<Layout> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layouts before and after the current layout in the ordered set where groupId = &#63;.
	 *
	 * @param plid the primary key of the current layout
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findByGroupId_PrevAndNext(long plid, long groupId,
		OrderByComparator orderByComparator) throws NoSuchLayoutException {
		Layout layout = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Layout[] array = new LayoutImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, layout, groupId,
					orderByComparator, true);

			array[1] = layout;

			array[2] = getByGroupId_PrevAndNext(session, layout, groupId,
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

	protected Layout getByGroupId_PrevAndNext(Session session, Layout layout,
		long groupId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(LayoutModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layout);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Layout> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layouts that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByGroupId(long groupId, int start, int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layouts that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Layout.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, LayoutImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, LayoutImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<Layout>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the layouts before and after the current layout in the ordered set of layouts that the user has permission to view where groupId = &#63;.
	 *
	 * @param plid the primary key of the current layout
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] filterFindByGroupId_PrevAndNext(long plid, long groupId,
		OrderByComparator orderByComparator) throws NoSuchLayoutException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(plid, groupId, orderByComparator);
		}

		Layout layout = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Layout[] array = new LayoutImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, layout, groupId,
					orderByComparator, true);

			array[1] = layout;

			array[2] = filterGetByGroupId_PrevAndNext(session, layout, groupId,
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

	protected Layout filterGetByGroupId_PrevAndNext(Session session,
		Layout layout, long groupId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Layout.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layout);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Layout> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layouts where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (Layout layout : findByGroupId(groupId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(layout);
		}
	}

	/**
	 * Returns the number of layouts where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layouts
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

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
	 * Returns the number of layouts that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layouts that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Layout.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "layout.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			LayoutModelImpl.COMPANYID_COLUMN_BITMASK |
			LayoutModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
			LayoutModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the layouts where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching layouts
	 */
	@Override
	public List<Layout> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layouts where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts
	 */
	@Override
	public List<Layout> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layouts where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByCompanyId(long companyId, int start, int end,
		OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Layout layout : list) {
				if ((companyId != layout.getCompanyId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws NoSuchLayoutException {
		Layout layout = fetchByCompanyId_First(companyId, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the first layout in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) {
		List<Layout> list = findByCompanyId(companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws NoSuchLayoutException {
		Layout layout = fetchByCompanyId_Last(companyId, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the last layout in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<Layout> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layouts before and after the current layout in the ordered set where companyId = &#63;.
	 *
	 * @param plid the primary key of the current layout
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findByCompanyId_PrevAndNext(long plid, long companyId,
		OrderByComparator orderByComparator) throws NoSuchLayoutException {
		Layout layout = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Layout[] array = new LayoutImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, layout, companyId,
					orderByComparator, true);

			array[1] = layout;

			array[2] = getByCompanyId_PrevAndNext(session, layout, companyId,
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

	protected Layout getByCompanyId_PrevAndNext(Session session, Layout layout,
		long companyId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(LayoutModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layout);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Layout> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layouts where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (Layout layout : findByCompanyId(companyId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(layout);
		}
	}

	/**
	 * Returns the number of layouts where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching layouts
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "layout.companyId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_ICONIMAGEID = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByIconImageId",
			new String[] { Long.class.getName() },
			LayoutModelImpl.ICONIMAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ICONIMAGEID = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByIconImageId",
			new String[] { Long.class.getName() });

	/**
	 * Returns the layout where iconImageId = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutException} if it could not be found.
	 *
	 * @param iconImageId the icon image ID
	 * @return the matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByIconImageId(long iconImageId)
		throws NoSuchLayoutException {
		Layout layout = fetchByIconImageId(iconImageId);

		if (layout == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("iconImageId=");
			msg.append(iconImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLayoutException(msg.toString());
		}

		return layout;
	}

	/**
	 * Returns the layout where iconImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param iconImageId the icon image ID
	 * @return the matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByIconImageId(long iconImageId) {
		return fetchByIconImageId(iconImageId, true);
	}

	/**
	 * Returns the layout where iconImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param iconImageId the icon image ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByIconImageId(long iconImageId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { iconImageId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_ICONIMAGEID,
					finderArgs, this);
		}

		if (result instanceof Layout) {
			Layout layout = (Layout)result;

			if ((iconImageId != layout.getIconImageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_ICONIMAGEID_ICONIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(iconImageId);

				List<Layout> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ICONIMAGEID,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"LayoutPersistenceImpl.fetchByIconImageId(long, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					Layout layout = list.get(0);

					result = layout;

					cacheResult(layout);

					if ((layout.getIconImageId() != iconImageId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ICONIMAGEID,
							finderArgs, layout);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ICONIMAGEID,
					finderArgs);

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
			return (Layout)result;
		}
	}

	/**
	 * Removes the layout where iconImageId = &#63; from the database.
	 *
	 * @param iconImageId the icon image ID
	 * @return the layout that was removed
	 */
	@Override
	public Layout removeByIconImageId(long iconImageId)
		throws NoSuchLayoutException {
		Layout layout = findByIconImageId(iconImageId);

		return remove(layout);
	}

	/**
	 * Returns the number of layouts where iconImageId = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @return the number of matching layouts
	 */
	@Override
	public int countByIconImageId(long iconImageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ICONIMAGEID;

		Object[] finderArgs = new Object[] { iconImageId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_ICONIMAGEID_ICONIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(iconImageId);

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

	private static final String _FINDER_COLUMN_ICONIMAGEID_ICONIMAGEID_2 = "layout.iconImageId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTPROTOTYPEUUID =
		new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByLayoutPrototypeUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTPROTOTYPEUUID =
		new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByLayoutPrototypeUuid",
			new String[] { String.class.getName() },
			LayoutModelImpl.LAYOUTPROTOTYPEUUID_COLUMN_BITMASK |
			LayoutModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
			LayoutModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LAYOUTPROTOTYPEUUID = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLayoutPrototypeUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the layouts where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @return the matching layouts
	 */
	@Override
	public List<Layout> findByLayoutPrototypeUuid(String layoutPrototypeUuid) {
		return findByLayoutPrototypeUuid(layoutPrototypeUuid,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts where layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts
	 */
	@Override
	public List<Layout> findByLayoutPrototypeUuid(String layoutPrototypeUuid,
		int start, int end) {
		return findByLayoutPrototypeUuid(layoutPrototypeUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layouts where layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByLayoutPrototypeUuid(String layoutPrototypeUuid,
		int start, int end, OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTPROTOTYPEUUID;
			finderArgs = new Object[] { layoutPrototypeUuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTPROTOTYPEUUID;
			finderArgs = new Object[] {
					layoutPrototypeUuid,
					
					start, end, orderByComparator
				};
		}

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Layout layout : list) {
				if (!Validator.equals(layoutPrototypeUuid,
							layout.getLayoutPrototypeUuid())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			boolean bindLayoutPrototypeUuid = false;

			if (layoutPrototypeUuid == null) {
				query.append(_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_1);
			}
			else if (layoutPrototypeUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_3);
			}
			else {
				bindLayoutPrototypeUuid = true;

				query.append(_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindLayoutPrototypeUuid) {
					qPos.add(layoutPrototypeUuid);
				}

				if (!pagination) {
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout in the ordered set where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByLayoutPrototypeUuid_First(String layoutPrototypeUuid,
		OrderByComparator orderByComparator) throws NoSuchLayoutException {
		Layout layout = fetchByLayoutPrototypeUuid_First(layoutPrototypeUuid,
				orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutPrototypeUuid=");
		msg.append(layoutPrototypeUuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the first layout in the ordered set where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByLayoutPrototypeUuid_First(String layoutPrototypeUuid,
		OrderByComparator orderByComparator) {
		List<Layout> list = findByLayoutPrototypeUuid(layoutPrototypeUuid, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout in the ordered set where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByLayoutPrototypeUuid_Last(String layoutPrototypeUuid,
		OrderByComparator orderByComparator) throws NoSuchLayoutException {
		Layout layout = fetchByLayoutPrototypeUuid_Last(layoutPrototypeUuid,
				orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutPrototypeUuid=");
		msg.append(layoutPrototypeUuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the last layout in the ordered set where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByLayoutPrototypeUuid_Last(String layoutPrototypeUuid,
		OrderByComparator orderByComparator) {
		int count = countByLayoutPrototypeUuid(layoutPrototypeUuid);

		if (count == 0) {
			return null;
		}

		List<Layout> list = findByLayoutPrototypeUuid(layoutPrototypeUuid,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layouts before and after the current layout in the ordered set where layoutPrototypeUuid = &#63;.
	 *
	 * @param plid the primary key of the current layout
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findByLayoutPrototypeUuid_PrevAndNext(long plid,
		String layoutPrototypeUuid, OrderByComparator orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Layout[] array = new LayoutImpl[3];

			array[0] = getByLayoutPrototypeUuid_PrevAndNext(session, layout,
					layoutPrototypeUuid, orderByComparator, true);

			array[1] = layout;

			array[2] = getByLayoutPrototypeUuid_PrevAndNext(session, layout,
					layoutPrototypeUuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Layout getByLayoutPrototypeUuid_PrevAndNext(Session session,
		Layout layout, String layoutPrototypeUuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUT_WHERE);

		boolean bindLayoutPrototypeUuid = false;

		if (layoutPrototypeUuid == null) {
			query.append(_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_1);
		}
		else if (layoutPrototypeUuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_3);
		}
		else {
			bindLayoutPrototypeUuid = true;

			query.append(_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(LayoutModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindLayoutPrototypeUuid) {
			qPos.add(layoutPrototypeUuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layout);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Layout> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layouts where layoutPrototypeUuid = &#63; from the database.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 */
	@Override
	public void removeByLayoutPrototypeUuid(String layoutPrototypeUuid) {
		for (Layout layout : findByLayoutPrototypeUuid(layoutPrototypeUuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layout);
		}
	}

	/**
	 * Returns the number of layouts where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @return the number of matching layouts
	 */
	@Override
	public int countByLayoutPrototypeUuid(String layoutPrototypeUuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_LAYOUTPROTOTYPEUUID;

		Object[] finderArgs = new Object[] { layoutPrototypeUuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			boolean bindLayoutPrototypeUuid = false;

			if (layoutPrototypeUuid == null) {
				query.append(_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_1);
			}
			else if (layoutPrototypeUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_3);
			}
			else {
				bindLayoutPrototypeUuid = true;

				query.append(_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindLayoutPrototypeUuid) {
					qPos.add(layoutPrototypeUuid);
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

	private static final String _FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_1 =
		"layout.layoutPrototypeUuid IS NULL";
	private static final String _FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_2 =
		"layout.layoutPrototypeUuid = ?";
	private static final String _FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_3 =
		"(layout.layoutPrototypeUuid IS NULL OR layout.layoutPrototypeUuid = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SOURCEPROTOTYPELAYOUTUUID =
		new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findBySourcePrototypeLayoutUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SOURCEPROTOTYPELAYOUTUUID =
		new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findBySourcePrototypeLayoutUuid",
			new String[] { String.class.getName() },
			LayoutModelImpl.SOURCEPROTOTYPELAYOUTUUID_COLUMN_BITMASK |
			LayoutModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
			LayoutModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SOURCEPROTOTYPELAYOUTUUID =
		new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySourcePrototypeLayoutUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the layouts where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @return the matching layouts
	 */
	@Override
	public List<Layout> findBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid) {
		return findBySourcePrototypeLayoutUuid(sourcePrototypeLayoutUuid,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts
	 */
	@Override
	public List<Layout> findBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid, int start, int end) {
		return findBySourcePrototypeLayoutUuid(sourcePrototypeLayoutUuid,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the layouts where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid, int start, int end,
		OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SOURCEPROTOTYPELAYOUTUUID;
			finderArgs = new Object[] { sourcePrototypeLayoutUuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SOURCEPROTOTYPELAYOUTUUID;
			finderArgs = new Object[] {
					sourcePrototypeLayoutUuid,
					
					start, end, orderByComparator
				};
		}

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Layout layout : list) {
				if (!Validator.equals(sourcePrototypeLayoutUuid,
							layout.getSourcePrototypeLayoutUuid())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			boolean bindSourcePrototypeLayoutUuid = false;

			if (sourcePrototypeLayoutUuid == null) {
				query.append(_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_1);
			}
			else if (sourcePrototypeLayoutUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_3);
			}
			else {
				bindSourcePrototypeLayoutUuid = true;

				query.append(_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSourcePrototypeLayoutUuid) {
					qPos.add(sourcePrototypeLayoutUuid);
				}

				if (!pagination) {
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findBySourcePrototypeLayoutUuid_First(
		String sourcePrototypeLayoutUuid, OrderByComparator orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchBySourcePrototypeLayoutUuid_First(sourcePrototypeLayoutUuid,
				orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("sourcePrototypeLayoutUuid=");
		msg.append(sourcePrototypeLayoutUuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the first layout in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchBySourcePrototypeLayoutUuid_First(
		String sourcePrototypeLayoutUuid, OrderByComparator orderByComparator) {
		List<Layout> list = findBySourcePrototypeLayoutUuid(sourcePrototypeLayoutUuid,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findBySourcePrototypeLayoutUuid_Last(
		String sourcePrototypeLayoutUuid, OrderByComparator orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchBySourcePrototypeLayoutUuid_Last(sourcePrototypeLayoutUuid,
				orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("sourcePrototypeLayoutUuid=");
		msg.append(sourcePrototypeLayoutUuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the last layout in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchBySourcePrototypeLayoutUuid_Last(
		String sourcePrototypeLayoutUuid, OrderByComparator orderByComparator) {
		int count = countBySourcePrototypeLayoutUuid(sourcePrototypeLayoutUuid);

		if (count == 0) {
			return null;
		}

		List<Layout> list = findBySourcePrototypeLayoutUuid(sourcePrototypeLayoutUuid,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layouts before and after the current layout in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param plid the primary key of the current layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findBySourcePrototypeLayoutUuid_PrevAndNext(long plid,
		String sourcePrototypeLayoutUuid, OrderByComparator orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Layout[] array = new LayoutImpl[3];

			array[0] = getBySourcePrototypeLayoutUuid_PrevAndNext(session,
					layout, sourcePrototypeLayoutUuid, orderByComparator, true);

			array[1] = layout;

			array[2] = getBySourcePrototypeLayoutUuid_PrevAndNext(session,
					layout, sourcePrototypeLayoutUuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Layout getBySourcePrototypeLayoutUuid_PrevAndNext(
		Session session, Layout layout, String sourcePrototypeLayoutUuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUT_WHERE);

		boolean bindSourcePrototypeLayoutUuid = false;

		if (sourcePrototypeLayoutUuid == null) {
			query.append(_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_1);
		}
		else if (sourcePrototypeLayoutUuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_3);
		}
		else {
			bindSourcePrototypeLayoutUuid = true;

			query.append(_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(LayoutModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindSourcePrototypeLayoutUuid) {
			qPos.add(sourcePrototypeLayoutUuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layout);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Layout> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layouts where sourcePrototypeLayoutUuid = &#63; from the database.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 */
	@Override
	public void removeBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid) {
		for (Layout layout : findBySourcePrototypeLayoutUuid(
				sourcePrototypeLayoutUuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(layout);
		}
	}

	/**
	 * Returns the number of layouts where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @return the number of matching layouts
	 */
	@Override
	public int countBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SOURCEPROTOTYPELAYOUTUUID;

		Object[] finderArgs = new Object[] { sourcePrototypeLayoutUuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			boolean bindSourcePrototypeLayoutUuid = false;

			if (sourcePrototypeLayoutUuid == null) {
				query.append(_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_1);
			}
			else if (sourcePrototypeLayoutUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_3);
			}
			else {
				bindSourcePrototypeLayoutUuid = true;

				query.append(_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSourcePrototypeLayoutUuid) {
					qPos.add(sourcePrototypeLayoutUuid);
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

	private static final String _FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_1 =
		"layout.sourcePrototypeLayoutUuid IS NULL";
	private static final String _FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_2 =
		"layout.sourcePrototypeLayoutUuid = ?";
	private static final String _FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_3 =
		"(layout.sourcePrototypeLayoutUuid IS NULL OR layout.sourcePrototypeLayoutUuid = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			LayoutModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
			LayoutModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the layouts where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the matching layouts
	 */
	@Override
	public List<Layout> findByG_P(long groupId, boolean privateLayout) {
		return findByG_P(groupId, privateLayout, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts
	 */
	@Override
	public List<Layout> findByG_P(long groupId, boolean privateLayout,
		int start, int end) {
		return findByG_P(groupId, privateLayout, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByG_P(long groupId, boolean privateLayout,
		int start, int end, OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P;
			finderArgs = new Object[] { groupId, privateLayout };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P;
			finderArgs = new Object[] {
					groupId, privateLayout,
					
					start, end, orderByComparator
				};
		}

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Layout layout : list) {
				if ((groupId != layout.getGroupId()) ||
						(privateLayout != layout.getPrivateLayout())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (!pagination) {
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByG_P_First(long groupId, boolean privateLayout,
		OrderByComparator orderByComparator) throws NoSuchLayoutException {
		Layout layout = fetchByG_P_First(groupId, privateLayout,
				orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the first layout in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByG_P_First(long groupId, boolean privateLayout,
		OrderByComparator orderByComparator) {
		List<Layout> list = findByG_P(groupId, privateLayout, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByG_P_Last(long groupId, boolean privateLayout,
		OrderByComparator orderByComparator) throws NoSuchLayoutException {
		Layout layout = fetchByG_P_Last(groupId, privateLayout,
				orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the last layout in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByG_P_Last(long groupId, boolean privateLayout,
		OrderByComparator orderByComparator) {
		int count = countByG_P(groupId, privateLayout);

		if (count == 0) {
			return null;
		}

		List<Layout> list = findByG_P(groupId, privateLayout, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layouts before and after the current layout in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param plid the primary key of the current layout
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findByG_P_PrevAndNext(long plid, long groupId,
		boolean privateLayout, OrderByComparator orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Layout[] array = new LayoutImpl[3];

			array[0] = getByG_P_PrevAndNext(session, layout, groupId,
					privateLayout, orderByComparator, true);

			array[1] = layout;

			array[2] = getByG_P_PrevAndNext(session, layout, groupId,
					privateLayout, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Layout getByG_P_PrevAndNext(Session session, Layout layout,
		long groupId, boolean privateLayout,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(LayoutModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layout);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Layout> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByG_P(long groupId, boolean privateLayout) {
		return filterFindByG_P(groupId, privateLayout, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByG_P(long groupId, boolean privateLayout,
		int start, int end) {
		return filterFindByG_P(groupId, privateLayout, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layouts that the user has permissions to view where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByG_P(long groupId, boolean privateLayout,
		int start, int end, OrderByComparator orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P(groupId, privateLayout, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Layout.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, LayoutImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, LayoutImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

			return (List<Layout>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the layouts before and after the current layout in the ordered set of layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param plid the primary key of the current layout
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] filterFindByG_P_PrevAndNext(long plid, long groupId,
		boolean privateLayout, OrderByComparator orderByComparator)
		throws NoSuchLayoutException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_PrevAndNext(plid, groupId, privateLayout,
				orderByComparator);
		}

		Layout layout = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Layout[] array = new LayoutImpl[3];

			array[0] = filterGetByG_P_PrevAndNext(session, layout, groupId,
					privateLayout, orderByComparator, true);

			array[1] = layout;

			array[2] = filterGetByG_P_PrevAndNext(session, layout, groupId,
					privateLayout, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Layout filterGetByG_P_PrevAndNext(Session session, Layout layout,
		long groupId, boolean privateLayout,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Layout.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layout);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Layout> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layouts where groupId = &#63; and privateLayout = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 */
	@Override
	public void removeByG_P(long groupId, boolean privateLayout) {
		for (Layout layout : findByG_P(groupId, privateLayout,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layout);
		}
	}

	/**
	 * Returns the number of layouts where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the number of matching layouts
	 */
	@Override
	public int countByG_P(long groupId, boolean privateLayout) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_P;

		Object[] finderArgs = new Object[] { groupId, privateLayout };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

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
	 * Returns the number of layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the number of matching layouts that the user has permission to view
	 */
	@Override
	public int filterCountByG_P(long groupId, boolean privateLayout) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P(groupId, privateLayout);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Layout.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

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

	private static final String _FINDER_COLUMN_G_P_GROUPID_2 = "layout.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_PRIVATELAYOUT_2 = "layout.privateLayout = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_P_L = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			},
			LayoutModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutModelImpl.LAYOUTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_L = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			});

	/**
	 * Returns the layout where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws NoSuchLayoutException {
		Layout layout = fetchByG_P_L(groupId, privateLayout, layoutId);

		if (layout == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append(", layoutId=");
			msg.append(layoutId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLayoutException(msg.toString());
		}

		return layout;
	}

	/**
	 * Returns the layout where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByG_P_L(long groupId, boolean privateLayout,
		long layoutId) {
		return fetchByG_P_L(groupId, privateLayout, layoutId, true);
	}

	/**
	 * Returns the layout where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByG_P_L(long groupId, boolean privateLayout,
		long layoutId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, privateLayout, layoutId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_P_L,
					finderArgs, this);
		}

		if (result instanceof Layout) {
			Layout layout = (Layout)result;

			if ((groupId != layout.getGroupId()) ||
					(privateLayout != layout.getPrivateLayout()) ||
					(layoutId != layout.getLayoutId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_LAYOUT_WHERE);

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

				List<Layout> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_L,
						finderArgs, list);
				}
				else {
					Layout layout = list.get(0);

					result = layout;

					cacheResult(layout);

					if ((layout.getGroupId() != groupId) ||
							(layout.getPrivateLayout() != privateLayout) ||
							(layout.getLayoutId() != layoutId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_L,
							finderArgs, layout);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_L,
					finderArgs);

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
			return (Layout)result;
		}
	}

	/**
	 * Removes the layout where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the layout that was removed
	 */
	@Override
	public Layout removeByG_P_L(long groupId, boolean privateLayout,
		long layoutId) throws NoSuchLayoutException {
		Layout layout = findByG_P_L(groupId, privateLayout, layoutId);

		return remove(layout);
	}

	/**
	 * Returns the number of layouts where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the number of matching layouts
	 */
	@Override
	public int countByG_P_L(long groupId, boolean privateLayout, long layoutId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_P_L;

		Object[] finderArgs = new Object[] { groupId, privateLayout, layoutId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

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

	private static final String _FINDER_COLUMN_G_P_L_GROUPID_2 = "layout.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2 = "layout.privateLayout = ? AND ";
	private static final String _FINDER_COLUMN_G_P_L_LAYOUTID_2 = "layout.layoutId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			},
			LayoutModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
			LayoutModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			});

	/**
	 * Returns all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @return the matching layouts
	 */
	@Override
	public List<Layout> findByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) {
		return findByG_P_P(groupId, privateLayout, parentLayoutId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts
	 */
	@Override
	public List<Layout> findByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId, int start, int end) {
		return findByG_P_P(groupId, privateLayout, parentLayoutId, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId, int start, int end,
		OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_P;
			finderArgs = new Object[] { groupId, privateLayout, parentLayoutId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_P;
			finderArgs = new Object[] {
					groupId, privateLayout, parentLayoutId,
					
					start, end, orderByComparator
				};
		}

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Layout layout : list) {
				if ((groupId != layout.getGroupId()) ||
						(privateLayout != layout.getPrivateLayout()) ||
						(parentLayoutId != layout.getParentLayoutId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_P_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_P_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_P_PARENTLAYOUTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(parentLayoutId);

				if (!pagination) {
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByG_P_P_First(long groupId, boolean privateLayout,
		long parentLayoutId, OrderByComparator orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByG_P_P_First(groupId, privateLayout,
				parentLayoutId, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", parentLayoutId=");
		msg.append(parentLayoutId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the first layout in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByG_P_P_First(long groupId, boolean privateLayout,
		long parentLayoutId, OrderByComparator orderByComparator) {
		List<Layout> list = findByG_P_P(groupId, privateLayout, parentLayoutId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByG_P_P_Last(long groupId, boolean privateLayout,
		long parentLayoutId, OrderByComparator orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByG_P_P_Last(groupId, privateLayout,
				parentLayoutId, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", parentLayoutId=");
		msg.append(parentLayoutId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the last layout in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByG_P_P_Last(long groupId, boolean privateLayout,
		long parentLayoutId, OrderByComparator orderByComparator) {
		int count = countByG_P_P(groupId, privateLayout, parentLayoutId);

		if (count == 0) {
			return null;
		}

		List<Layout> list = findByG_P_P(groupId, privateLayout, parentLayoutId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layouts before and after the current layout in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param plid the primary key of the current layout
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findByG_P_P_PrevAndNext(long plid, long groupId,
		boolean privateLayout, long parentLayoutId,
		OrderByComparator orderByComparator) throws NoSuchLayoutException {
		Layout layout = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Layout[] array = new LayoutImpl[3];

			array[0] = getByG_P_P_PrevAndNext(session, layout, groupId,
					privateLayout, parentLayoutId, orderByComparator, true);

			array[1] = layout;

			array[2] = getByG_P_P_PrevAndNext(session, layout, groupId,
					privateLayout, parentLayoutId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Layout getByG_P_P_PrevAndNext(Session session, Layout layout,
		long groupId, boolean privateLayout, long parentLayoutId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_G_P_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_P_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_P_PARENTLAYOUTID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(LayoutModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		qPos.add(parentLayoutId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layout);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Layout> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @return the matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) {
		return filterFindByG_P_P(groupId, privateLayout, parentLayoutId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId, int start, int end) {
		return filterFindByG_P_P(groupId, privateLayout, parentLayoutId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the layouts that the user has permissions to view where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId, int start, int end,
		OrderByComparator orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_P(groupId, privateLayout, parentLayoutId, start,
				end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_P_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_P_PARENTLAYOUTID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Layout.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, LayoutImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, LayoutImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

			qPos.add(parentLayoutId);

			return (List<Layout>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the layouts before and after the current layout in the ordered set of layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param plid the primary key of the current layout
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] filterFindByG_P_P_PrevAndNext(long plid, long groupId,
		boolean privateLayout, long parentLayoutId,
		OrderByComparator orderByComparator) throws NoSuchLayoutException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_P_PrevAndNext(plid, groupId, privateLayout,
				parentLayoutId, orderByComparator);
		}

		Layout layout = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Layout[] array = new LayoutImpl[3];

			array[0] = filterGetByG_P_P_PrevAndNext(session, layout, groupId,
					privateLayout, parentLayoutId, orderByComparator, true);

			array[1] = layout;

			array[2] = filterGetByG_P_P_PrevAndNext(session, layout, groupId,
					privateLayout, parentLayoutId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Layout filterGetByG_P_P_PrevAndNext(Session session,
		Layout layout, long groupId, boolean privateLayout,
		long parentLayoutId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_P_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_P_PARENTLAYOUTID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Layout.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		qPos.add(parentLayoutId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layout);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Layout> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 */
	@Override
	public void removeByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) {
		for (Layout layout : findByG_P_P(groupId, privateLayout,
				parentLayoutId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layout);
		}
	}

	/**
	 * Returns the number of layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @return the number of matching layouts
	 */
	@Override
	public int countByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_P_P;

		Object[] finderArgs = new Object[] {
				groupId, privateLayout, parentLayoutId
			};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_P_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_P_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_P_PARENTLAYOUTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(parentLayoutId);

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
	 * Returns the number of layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @return the number of matching layouts that the user has permission to view
	 */
	@Override
	public int filterCountByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P_P(groupId, privateLayout, parentLayoutId);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_G_P_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_P_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_P_PARENTLAYOUTID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Layout.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

			qPos.add(parentLayoutId);

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

	private static final String _FINDER_COLUMN_G_P_P_GROUPID_2 = "layout.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_P_PRIVATELAYOUT_2 = "layout.privateLayout = ? AND ";
	private static final String _FINDER_COLUMN_G_P_P_PARENTLAYOUTID_2 = "layout.parentLayoutId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_P_F = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_P_F",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			LayoutModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutModelImpl.FRIENDLYURL_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_F = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_F",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the layout where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly u r l
	 * @return the matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByG_P_F(long groupId, boolean privateLayout,
		String friendlyURL) throws NoSuchLayoutException {
		Layout layout = fetchByG_P_F(groupId, privateLayout, friendlyURL);

		if (layout == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append(", friendlyURL=");
			msg.append(friendlyURL);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLayoutException(msg.toString());
		}

		return layout;
	}

	/**
	 * Returns the layout where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly u r l
	 * @return the matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByG_P_F(long groupId, boolean privateLayout,
		String friendlyURL) {
		return fetchByG_P_F(groupId, privateLayout, friendlyURL, true);
	}

	/**
	 * Returns the layout where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly u r l
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByG_P_F(long groupId, boolean privateLayout,
		String friendlyURL, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, privateLayout, friendlyURL };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_P_F,
					finderArgs, this);
		}

		if (result instanceof Layout) {
			Layout layout = (Layout)result;

			if ((groupId != layout.getGroupId()) ||
					(privateLayout != layout.getPrivateLayout()) ||
					!Validator.equals(friendlyURL, layout.getFriendlyURL())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_P_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_F_PRIVATELAYOUT_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL == null) {
				query.append(_FINDER_COLUMN_G_P_F_FRIENDLYURL_1);
			}
			else if (friendlyURL.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_P_F_FRIENDLYURL_3);
			}
			else {
				bindFriendlyURL = true;

				query.append(_FINDER_COLUMN_G_P_F_FRIENDLYURL_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (bindFriendlyURL) {
					qPos.add(friendlyURL);
				}

				List<Layout> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_F,
						finderArgs, list);
				}
				else {
					Layout layout = list.get(0);

					result = layout;

					cacheResult(layout);

					if ((layout.getGroupId() != groupId) ||
							(layout.getPrivateLayout() != privateLayout) ||
							(layout.getFriendlyURL() == null) ||
							!layout.getFriendlyURL().equals(friendlyURL)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_F,
							finderArgs, layout);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_F,
					finderArgs);

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
			return (Layout)result;
		}
	}

	/**
	 * Removes the layout where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly u r l
	 * @return the layout that was removed
	 */
	@Override
	public Layout removeByG_P_F(long groupId, boolean privateLayout,
		String friendlyURL) throws NoSuchLayoutException {
		Layout layout = findByG_P_F(groupId, privateLayout, friendlyURL);

		return remove(layout);
	}

	/**
	 * Returns the number of layouts where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly u r l
	 * @return the number of matching layouts
	 */
	@Override
	public int countByG_P_F(long groupId, boolean privateLayout,
		String friendlyURL) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_P_F;

		Object[] finderArgs = new Object[] { groupId, privateLayout, friendlyURL };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_P_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_F_PRIVATELAYOUT_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL == null) {
				query.append(_FINDER_COLUMN_G_P_F_FRIENDLYURL_1);
			}
			else if (friendlyURL.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_P_F_FRIENDLYURL_3);
			}
			else {
				bindFriendlyURL = true;

				query.append(_FINDER_COLUMN_G_P_F_FRIENDLYURL_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (bindFriendlyURL) {
					qPos.add(friendlyURL);
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

	private static final String _FINDER_COLUMN_G_P_F_GROUPID_2 = "layout.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_F_PRIVATELAYOUT_2 = "layout.privateLayout = ? AND ";
	private static final String _FINDER_COLUMN_G_P_F_FRIENDLYURL_1 = "layout.friendlyURL IS NULL";
	private static final String _FINDER_COLUMN_G_P_F_FRIENDLYURL_2 = "layout.friendlyURL = ?";
	private static final String _FINDER_COLUMN_G_P_F_FRIENDLYURL_3 = "(layout.friendlyURL IS NULL OR layout.friendlyURL = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_T = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_T = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			LayoutModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutModelImpl.TYPE_COLUMN_BITMASK |
			LayoutModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
			LayoutModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_T = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @return the matching layouts
	 */
	@Override
	public List<Layout> findByG_P_T(long groupId, boolean privateLayout,
		String type) {
		return findByG_P_T(groupId, privateLayout, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts
	 */
	@Override
	public List<Layout> findByG_P_T(long groupId, boolean privateLayout,
		String type, int start, int end) {
		return findByG_P_T(groupId, privateLayout, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByG_P_T(long groupId, boolean privateLayout,
		String type, int start, int end, OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_T;
			finderArgs = new Object[] { groupId, privateLayout, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_T;
			finderArgs = new Object[] {
					groupId, privateLayout, type,
					
					start, end, orderByComparator
				};
		}

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Layout layout : list) {
				if ((groupId != layout.getGroupId()) ||
						(privateLayout != layout.getPrivateLayout()) ||
						!Validator.equals(type, layout.getType())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_P_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_T_PRIVATELAYOUT_2);

			boolean bindType = false;

			if (type == null) {
				query.append(_FINDER_COLUMN_G_P_T_TYPE_1);
			}
			else if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_P_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_P_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (bindType) {
					qPos.add(type);
				}

				if (!pagination) {
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByG_P_T_First(long groupId, boolean privateLayout,
		String type, OrderByComparator orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByG_P_T_First(groupId, privateLayout, type,
				orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the first layout in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByG_P_T_First(long groupId, boolean privateLayout,
		String type, OrderByComparator orderByComparator) {
		List<Layout> list = findByG_P_T(groupId, privateLayout, type, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByG_P_T_Last(long groupId, boolean privateLayout,
		String type, OrderByComparator orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByG_P_T_Last(groupId, privateLayout, type,
				orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the last layout in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByG_P_T_Last(long groupId, boolean privateLayout,
		String type, OrderByComparator orderByComparator) {
		int count = countByG_P_T(groupId, privateLayout, type);

		if (count == 0) {
			return null;
		}

		List<Layout> list = findByG_P_T(groupId, privateLayout, type,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layouts before and after the current layout in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param plid the primary key of the current layout
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findByG_P_T_PrevAndNext(long plid, long groupId,
		boolean privateLayout, String type, OrderByComparator orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Layout[] array = new LayoutImpl[3];

			array[0] = getByG_P_T_PrevAndNext(session, layout, groupId,
					privateLayout, type, orderByComparator, true);

			array[1] = layout;

			array[2] = getByG_P_T_PrevAndNext(session, layout, groupId,
					privateLayout, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Layout getByG_P_T_PrevAndNext(Session session, Layout layout,
		long groupId, boolean privateLayout, String type,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_G_P_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_T_PRIVATELAYOUT_2);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_G_P_T_TYPE_1);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_P_T_TYPE_3);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_P_T_TYPE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(LayoutModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		if (bindType) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layout);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Layout> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @return the matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByG_P_T(long groupId, boolean privateLayout,
		String type) {
		return filterFindByG_P_T(groupId, privateLayout, type,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByG_P_T(long groupId, boolean privateLayout,
		String type, int start, int end) {
		return filterFindByG_P_T(groupId, privateLayout, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layouts that the user has permissions to view where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByG_P_T(long groupId, boolean privateLayout,
		String type, int start, int end, OrderByComparator orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_T(groupId, privateLayout, type, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_T_PRIVATELAYOUT_2);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_G_P_T_TYPE_1_SQL);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_P_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_P_T_TYPE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Layout.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, LayoutImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, LayoutImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

			if (bindType) {
				qPos.add(type);
			}

			return (List<Layout>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the layouts before and after the current layout in the ordered set of layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param plid the primary key of the current layout
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] filterFindByG_P_T_PrevAndNext(long plid, long groupId,
		boolean privateLayout, String type, OrderByComparator orderByComparator)
		throws NoSuchLayoutException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_T_PrevAndNext(plid, groupId, privateLayout, type,
				orderByComparator);
		}

		Layout layout = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Layout[] array = new LayoutImpl[3];

			array[0] = filterGetByG_P_T_PrevAndNext(session, layout, groupId,
					privateLayout, type, orderByComparator, true);

			array[1] = layout;

			array[2] = filterGetByG_P_T_PrevAndNext(session, layout, groupId,
					privateLayout, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Layout filterGetByG_P_T_PrevAndNext(Session session,
		Layout layout, long groupId, boolean privateLayout, String type,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_T_PRIVATELAYOUT_2);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_G_P_T_TYPE_1_SQL);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_P_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_P_T_TYPE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Layout.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		if (bindType) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layout);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Layout> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 */
	@Override
	public void removeByG_P_T(long groupId, boolean privateLayout, String type) {
		for (Layout layout : findByG_P_T(groupId, privateLayout, type,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layout);
		}
	}

	/**
	 * Returns the number of layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @return the number of matching layouts
	 */
	@Override
	public int countByG_P_T(long groupId, boolean privateLayout, String type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_P_T;

		Object[] finderArgs = new Object[] { groupId, privateLayout, type };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_P_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_T_PRIVATELAYOUT_2);

			boolean bindType = false;

			if (type == null) {
				query.append(_FINDER_COLUMN_G_P_T_TYPE_1);
			}
			else if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_P_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_P_T_TYPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (bindType) {
					qPos.add(type);
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

	/**
	 * Returns the number of layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @return the number of matching layouts that the user has permission to view
	 */
	@Override
	public int filterCountByG_P_T(long groupId, boolean privateLayout,
		String type) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P_T(groupId, privateLayout, type);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_G_P_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_T_PRIVATELAYOUT_2);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_G_P_T_TYPE_1_SQL);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_P_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_P_T_TYPE_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Layout.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

			if (bindType) {
				qPos.add(type);
			}

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

	private static final String _FINDER_COLUMN_G_P_T_GROUPID_2 = "layout.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_T_PRIVATELAYOUT_2 = "layout.privateLayout = ? AND ";
	private static final String _FINDER_COLUMN_G_P_T_TYPE_1 = "layout.type IS NULL";
	private static final String _FINDER_COLUMN_G_P_T_TYPE_2 = "layout.type = ?";
	private static final String _FINDER_COLUMN_G_P_T_TYPE_3 = "(layout.type IS NULL OR layout.type = '')";
	private static final String _FINDER_COLUMN_G_P_T_TYPE_1_SQL = "layout.type_ IS NULL";
	private static final String _FINDER_COLUMN_G_P_T_TYPE_2_SQL = "layout.type_ = ?";
	private static final String _FINDER_COLUMN_G_P_T_TYPE_3_SQL = "(layout.type_ IS NULL OR layout.type_ = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_P_SPLU = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_P_SPLU",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			LayoutModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutModelImpl.SOURCEPROTOTYPELAYOUTUUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_SPLU = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_SPLU",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the layout where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @return the matching layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByG_P_SPLU(long groupId, boolean privateLayout,
		String sourcePrototypeLayoutUuid) throws NoSuchLayoutException {
		Layout layout = fetchByG_P_SPLU(groupId, privateLayout,
				sourcePrototypeLayoutUuid);

		if (layout == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append(", sourcePrototypeLayoutUuid=");
			msg.append(sourcePrototypeLayoutUuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLayoutException(msg.toString());
		}

		return layout;
	}

	/**
	 * Returns the layout where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @return the matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByG_P_SPLU(long groupId, boolean privateLayout,
		String sourcePrototypeLayoutUuid) {
		return fetchByG_P_SPLU(groupId, privateLayout,
			sourcePrototypeLayoutUuid, true);
	}

	/**
	 * Returns the layout where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByG_P_SPLU(long groupId, boolean privateLayout,
		String sourcePrototypeLayoutUuid, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				groupId, privateLayout, sourcePrototypeLayoutUuid
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_P_SPLU,
					finderArgs, this);
		}

		if (result instanceof Layout) {
			Layout layout = (Layout)result;

			if ((groupId != layout.getGroupId()) ||
					(privateLayout != layout.getPrivateLayout()) ||
					!Validator.equals(sourcePrototypeLayoutUuid,
						layout.getSourcePrototypeLayoutUuid())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_P_SPLU_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_SPLU_PRIVATELAYOUT_2);

			boolean bindSourcePrototypeLayoutUuid = false;

			if (sourcePrototypeLayoutUuid == null) {
				query.append(_FINDER_COLUMN_G_P_SPLU_SOURCEPROTOTYPELAYOUTUUID_1);
			}
			else if (sourcePrototypeLayoutUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_P_SPLU_SOURCEPROTOTYPELAYOUTUUID_3);
			}
			else {
				bindSourcePrototypeLayoutUuid = true;

				query.append(_FINDER_COLUMN_G_P_SPLU_SOURCEPROTOTYPELAYOUTUUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (bindSourcePrototypeLayoutUuid) {
					qPos.add(sourcePrototypeLayoutUuid);
				}

				List<Layout> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_SPLU,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"LayoutPersistenceImpl.fetchByG_P_SPLU(long, boolean, String, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					Layout layout = list.get(0);

					result = layout;

					cacheResult(layout);

					if ((layout.getGroupId() != groupId) ||
							(layout.getPrivateLayout() != privateLayout) ||
							(layout.getSourcePrototypeLayoutUuid() == null) ||
							!layout.getSourcePrototypeLayoutUuid()
									   .equals(sourcePrototypeLayoutUuid)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_SPLU,
							finderArgs, layout);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_SPLU,
					finderArgs);

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
			return (Layout)result;
		}
	}

	/**
	 * Removes the layout where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @return the layout that was removed
	 */
	@Override
	public Layout removeByG_P_SPLU(long groupId, boolean privateLayout,
		String sourcePrototypeLayoutUuid) throws NoSuchLayoutException {
		Layout layout = findByG_P_SPLU(groupId, privateLayout,
				sourcePrototypeLayoutUuid);

		return remove(layout);
	}

	/**
	 * Returns the number of layouts where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @return the number of matching layouts
	 */
	@Override
	public int countByG_P_SPLU(long groupId, boolean privateLayout,
		String sourcePrototypeLayoutUuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_P_SPLU;

		Object[] finderArgs = new Object[] {
				groupId, privateLayout, sourcePrototypeLayoutUuid
			};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_P_SPLU_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_SPLU_PRIVATELAYOUT_2);

			boolean bindSourcePrototypeLayoutUuid = false;

			if (sourcePrototypeLayoutUuid == null) {
				query.append(_FINDER_COLUMN_G_P_SPLU_SOURCEPROTOTYPELAYOUTUUID_1);
			}
			else if (sourcePrototypeLayoutUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_P_SPLU_SOURCEPROTOTYPELAYOUTUUID_3);
			}
			else {
				bindSourcePrototypeLayoutUuid = true;

				query.append(_FINDER_COLUMN_G_P_SPLU_SOURCEPROTOTYPELAYOUTUUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (bindSourcePrototypeLayoutUuid) {
					qPos.add(sourcePrototypeLayoutUuid);
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

	private static final String _FINDER_COLUMN_G_P_SPLU_GROUPID_2 = "layout.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_SPLU_PRIVATELAYOUT_2 = "layout.privateLayout = ? AND ";
	private static final String _FINDER_COLUMN_G_P_SPLU_SOURCEPROTOTYPELAYOUTUUID_1 =
		"layout.sourcePrototypeLayoutUuid IS NULL";
	private static final String _FINDER_COLUMN_G_P_SPLU_SOURCEPROTOTYPELAYOUTUUID_2 =
		"layout.sourcePrototypeLayoutUuid = ?";
	private static final String _FINDER_COLUMN_G_P_SPLU_SOURCEPROTOTYPELAYOUTUUID_3 =
		"(layout.sourcePrototypeLayoutUuid IS NULL OR layout.sourcePrototypeLayoutUuid = '')";

	public LayoutPersistenceImpl() {
		setModelClass(Layout.class);
	}

	/**
	 * Caches the layout in the entity cache if it is enabled.
	 *
	 * @param layout the layout
	 */
	@Override
	public void cacheResult(Layout layout) {
		EntityCacheUtil.putResult(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutImpl.class, layout.getPrimaryKey(), layout);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G_P,
			new Object[] {
				layout.getUuid(), layout.getGroupId(), layout.getPrivateLayout()
			}, layout);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ICONIMAGEID,
			new Object[] { layout.getIconImageId() }, layout);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_L,
			new Object[] {
				layout.getGroupId(), layout.getPrivateLayout(),
				layout.getLayoutId()
			}, layout);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_F,
			new Object[] {
				layout.getGroupId(), layout.getPrivateLayout(),
				layout.getFriendlyURL()
			}, layout);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_SPLU,
			new Object[] {
				layout.getGroupId(), layout.getPrivateLayout(),
				layout.getSourcePrototypeLayoutUuid()
			}, layout);

		layout.resetOriginalValues();
	}

	/**
	 * Caches the layouts in the entity cache if it is enabled.
	 *
	 * @param layouts the layouts
	 */
	@Override
	public void cacheResult(List<Layout> layouts) {
		for (Layout layout : layouts) {
			if (EntityCacheUtil.getResult(
						LayoutModelImpl.ENTITY_CACHE_ENABLED, LayoutImpl.class,
						layout.getPrimaryKey()) == null) {
				cacheResult(layout);
			}
			else {
				layout.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layouts.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(LayoutImpl.class.getName());
		}

		EntityCacheUtil.clearCache(LayoutImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Layout layout) {
		EntityCacheUtil.removeResult(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutImpl.class, layout.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(layout);
	}

	@Override
	public void clearCache(List<Layout> layouts) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Layout layout : layouts) {
			EntityCacheUtil.removeResult(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutImpl.class, layout.getPrimaryKey());

			clearUniqueFindersCache(layout);
		}
	}

	protected void cacheUniqueFindersCache(Layout layout) {
		if (layout.isNew()) {
			Object[] args = new Object[] {
					layout.getUuid(), layout.getGroupId(),
					layout.getPrivateLayout()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G_P, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G_P, args,
				layout);

			args = new Object[] { layout.getIconImageId() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ICONIMAGEID, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ICONIMAGEID, args,
				layout);

			args = new Object[] {
					layout.getGroupId(), layout.getPrivateLayout(),
					layout.getLayoutId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_L, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_L, args, layout);

			args = new Object[] {
					layout.getGroupId(), layout.getPrivateLayout(),
					layout.getFriendlyURL()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_F, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_F, args, layout);

			args = new Object[] {
					layout.getGroupId(), layout.getPrivateLayout(),
					layout.getSourcePrototypeLayoutUuid()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_SPLU, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_SPLU, args,
				layout);
		}
		else {
			LayoutModelImpl layoutModelImpl = (LayoutModelImpl)layout;

			if ((layoutModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layout.getUuid(), layout.getGroupId(),
						layout.getPrivateLayout()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G_P, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G_P, args,
					layout);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_ICONIMAGEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { layout.getIconImageId() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ICONIMAGEID,
					args, Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ICONIMAGEID,
					args, layout);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_P_L.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layout.getGroupId(), layout.getPrivateLayout(),
						layout.getLayoutId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_L, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_L, args,
					layout);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_P_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layout.getGroupId(), layout.getPrivateLayout(),
						layout.getFriendlyURL()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_F, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_F, args,
					layout);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_P_SPLU.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layout.getGroupId(), layout.getPrivateLayout(),
						layout.getSourcePrototypeLayoutUuid()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_SPLU, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_SPLU, args,
					layout);
			}
		}
	}

	protected void clearUniqueFindersCache(Layout layout) {
		LayoutModelImpl layoutModelImpl = (LayoutModelImpl)layout;

		Object[] args = new Object[] {
				layout.getUuid(), layout.getGroupId(), layout.getPrivateLayout()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G_P, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G_P, args);

		if ((layoutModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G_P.getColumnBitmask()) != 0) {
			args = new Object[] {
					layoutModelImpl.getOriginalUuid(),
					layoutModelImpl.getOriginalGroupId(),
					layoutModelImpl.getOriginalPrivateLayout()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G_P, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G_P, args);
		}

		args = new Object[] { layout.getIconImageId() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ICONIMAGEID, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ICONIMAGEID, args);

		if ((layoutModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_ICONIMAGEID.getColumnBitmask()) != 0) {
			args = new Object[] { layoutModelImpl.getOriginalIconImageId() };

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ICONIMAGEID, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ICONIMAGEID, args);
		}

		args = new Object[] {
				layout.getGroupId(), layout.getPrivateLayout(),
				layout.getLayoutId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_L, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_L, args);

		if ((layoutModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_P_L.getColumnBitmask()) != 0) {
			args = new Object[] {
					layoutModelImpl.getOriginalGroupId(),
					layoutModelImpl.getOriginalPrivateLayout(),
					layoutModelImpl.getOriginalLayoutId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_L, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_L, args);
		}

		args = new Object[] {
				layout.getGroupId(), layout.getPrivateLayout(),
				layout.getFriendlyURL()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_F, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_F, args);

		if ((layoutModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_P_F.getColumnBitmask()) != 0) {
			args = new Object[] {
					layoutModelImpl.getOriginalGroupId(),
					layoutModelImpl.getOriginalPrivateLayout(),
					layoutModelImpl.getOriginalFriendlyURL()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_F, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_F, args);
		}

		args = new Object[] {
				layout.getGroupId(), layout.getPrivateLayout(),
				layout.getSourcePrototypeLayoutUuid()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_SPLU, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_SPLU, args);

		if ((layoutModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_P_SPLU.getColumnBitmask()) != 0) {
			args = new Object[] {
					layoutModelImpl.getOriginalGroupId(),
					layoutModelImpl.getOriginalPrivateLayout(),
					layoutModelImpl.getOriginalSourcePrototypeLayoutUuid()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_SPLU, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_SPLU, args);
		}
	}

	/**
	 * Creates a new layout with the primary key. Does not add the layout to the database.
	 *
	 * @param plid the primary key for the new layout
	 * @return the new layout
	 */
	@Override
	public Layout create(long plid) {
		Layout layout = new LayoutImpl();

		layout.setNew(true);
		layout.setPrimaryKey(plid);

		String uuid = PortalUUIDUtil.generate();

		layout.setUuid(uuid);

		return layout;
	}

	/**
	 * Removes the layout with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param plid the primary key of the layout
	 * @return the layout that was removed
	 * @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout remove(long plid) throws NoSuchLayoutException {
		return remove((Serializable)plid);
	}

	/**
	 * Removes the layout with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout
	 * @return the layout that was removed
	 * @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout remove(Serializable primaryKey) throws NoSuchLayoutException {
		Session session = null;

		try {
			session = openSession();

			Layout layout = (Layout)session.get(LayoutImpl.class, primaryKey);

			if (layout == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLayoutException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(layout);
		}
		catch (NoSuchLayoutException nsee) {
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
	protected Layout removeImpl(Layout layout) {
		layout = toUnwrappedModel(layout);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layout)) {
				layout = (Layout)session.get(LayoutImpl.class,
						layout.getPrimaryKeyObj());
			}

			if (layout != null) {
				session.delete(layout);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layout != null) {
			clearCache(layout);
		}

		return layout;
	}

	@Override
	public Layout updateImpl(com.liferay.portal.model.Layout layout) {
		layout = toUnwrappedModel(layout);

		boolean isNew = layout.isNew();

		LayoutModelImpl layoutModelImpl = (LayoutModelImpl)layout;

		if (Validator.isNull(layout.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			layout.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (layout.isNew()) {
				session.save(layout);

				layout.setNew(false);
			}
			else {
				session.merge(layout);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !LayoutModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((layoutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { layoutModelImpl.getOriginalUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { layoutModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutModelImpl.getOriginalUuid(),
						layoutModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						layoutModelImpl.getUuid(),
						layoutModelImpl.getCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutModelImpl.getOriginalGroupId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { layoutModelImpl.getGroupId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { layoutModelImpl.getCompanyId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTPROTOTYPEUUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutModelImpl.getOriginalLayoutPrototypeUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LAYOUTPROTOTYPEUUID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTPROTOTYPEUUID,
					args);

				args = new Object[] { layoutModelImpl.getLayoutPrototypeUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LAYOUTPROTOTYPEUUID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTPROTOTYPEUUID,
					args);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SOURCEPROTOTYPELAYOUTUUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutModelImpl.getOriginalSourcePrototypeLayoutUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_SOURCEPROTOTYPELAYOUTUUID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SOURCEPROTOTYPELAYOUTUUID,
					args);

				args = new Object[] {
						layoutModelImpl.getSourcePrototypeLayoutUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_SOURCEPROTOTYPELAYOUTUUID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SOURCEPROTOTYPELAYOUTUUID,
					args);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutModelImpl.getOriginalGroupId(),
						layoutModelImpl.getOriginalPrivateLayout()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P,
					args);

				args = new Object[] {
						layoutModelImpl.getGroupId(),
						layoutModelImpl.getPrivateLayout()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P,
					args);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutModelImpl.getOriginalGroupId(),
						layoutModelImpl.getOriginalPrivateLayout(),
						layoutModelImpl.getOriginalParentLayoutId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_P,
					args);

				args = new Object[] {
						layoutModelImpl.getGroupId(),
						layoutModelImpl.getPrivateLayout(),
						layoutModelImpl.getParentLayoutId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_P,
					args);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutModelImpl.getOriginalGroupId(),
						layoutModelImpl.getOriginalPrivateLayout(),
						layoutModelImpl.getOriginalType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_T,
					args);

				args = new Object[] {
						layoutModelImpl.getGroupId(),
						layoutModelImpl.getPrivateLayout(),
						layoutModelImpl.getType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_T,
					args);
			}
		}

		EntityCacheUtil.putResult(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutImpl.class, layout.getPrimaryKey(), layout, false);

		clearUniqueFindersCache(layout);
		cacheUniqueFindersCache(layout);

		layout.resetOriginalValues();

		return layout;
	}

	protected Layout toUnwrappedModel(Layout layout) {
		if (layout instanceof LayoutImpl) {
			return layout;
		}

		LayoutImpl layoutImpl = new LayoutImpl();

		layoutImpl.setNew(layout.isNew());
		layoutImpl.setPrimaryKey(layout.getPrimaryKey());

		layoutImpl.setMvccVersion(layout.getMvccVersion());
		layoutImpl.setUuid(layout.getUuid());
		layoutImpl.setPlid(layout.getPlid());
		layoutImpl.setGroupId(layout.getGroupId());
		layoutImpl.setCompanyId(layout.getCompanyId());
		layoutImpl.setUserId(layout.getUserId());
		layoutImpl.setUserName(layout.getUserName());
		layoutImpl.setCreateDate(layout.getCreateDate());
		layoutImpl.setModifiedDate(layout.getModifiedDate());
		layoutImpl.setPrivateLayout(layout.isPrivateLayout());
		layoutImpl.setLayoutId(layout.getLayoutId());
		layoutImpl.setParentLayoutId(layout.getParentLayoutId());
		layoutImpl.setName(layout.getName());
		layoutImpl.setTitle(layout.getTitle());
		layoutImpl.setDescription(layout.getDescription());
		layoutImpl.setKeywords(layout.getKeywords());
		layoutImpl.setRobots(layout.getRobots());
		layoutImpl.setType(layout.getType());
		layoutImpl.setTypeSettings(layout.getTypeSettings());
		layoutImpl.setHidden(layout.isHidden());
		layoutImpl.setFriendlyURL(layout.getFriendlyURL());
		layoutImpl.setIconImageId(layout.getIconImageId());
		layoutImpl.setThemeId(layout.getThemeId());
		layoutImpl.setColorSchemeId(layout.getColorSchemeId());
		layoutImpl.setWapThemeId(layout.getWapThemeId());
		layoutImpl.setWapColorSchemeId(layout.getWapColorSchemeId());
		layoutImpl.setCss(layout.getCss());
		layoutImpl.setPriority(layout.getPriority());
		layoutImpl.setLayoutPrototypeUuid(layout.getLayoutPrototypeUuid());
		layoutImpl.setLayoutPrototypeLinkEnabled(layout.isLayoutPrototypeLinkEnabled());
		layoutImpl.setSourcePrototypeLayoutUuid(layout.getSourcePrototypeLayoutUuid());

		return layoutImpl;
	}

	/**
	 * Returns the layout with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout
	 * @return the layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLayoutException {
		Layout layout = fetchByPrimaryKey(primaryKey);

		if (layout == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLayoutException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return layout;
	}

	/**
	 * Returns the layout with the primary key or throws a {@link com.liferay.portal.NoSuchLayoutException} if it could not be found.
	 *
	 * @param plid the primary key of the layout
	 * @return the layout
	 * @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout findByPrimaryKey(long plid) throws NoSuchLayoutException {
		return findByPrimaryKey((Serializable)plid);
	}

	/**
	 * Returns the layout with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout
	 * @return the layout, or <code>null</code> if a layout with the primary key could not be found
	 */
	@Override
	public Layout fetchByPrimaryKey(Serializable primaryKey) {
		Layout layout = (Layout)EntityCacheUtil.getResult(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutImpl.class, primaryKey);

		if (layout == _nullLayout) {
			return null;
		}

		if (layout == null) {
			Session session = null;

			try {
				session = openSession();

				layout = (Layout)session.get(LayoutImpl.class, primaryKey);

				if (layout != null) {
					cacheResult(layout);
				}
				else {
					EntityCacheUtil.putResult(LayoutModelImpl.ENTITY_CACHE_ENABLED,
						LayoutImpl.class, primaryKey, _nullLayout);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(LayoutModelImpl.ENTITY_CACHE_ENABLED,
					LayoutImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return layout;
	}

	/**
	 * Returns the layout with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param plid the primary key of the layout
	 * @return the layout, or <code>null</code> if a layout with the primary key could not be found
	 */
	@Override
	public Layout fetchByPrimaryKey(long plid) {
		return fetchByPrimaryKey((Serializable)plid);
	}

	@Override
	public Map<Serializable, Layout> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, Layout> map = new HashMap<Serializable, Layout>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			Layout layout = fetchByPrimaryKey(primaryKey);

			if (layout != null) {
				map.put(primaryKey, layout);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Layout layout = (Layout)EntityCacheUtil.getResult(LayoutModelImpl.ENTITY_CACHE_ENABLED,
					LayoutImpl.class, primaryKey);

			if (layout == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, layout);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_LAYOUT_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (Layout layout : (List<Layout>)q.list()) {
				map.put(layout.getPrimaryKeyObj(), layout);

				cacheResult(layout);

				uncachedPrimaryKeys.remove(layout.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(LayoutModelImpl.ENTITY_CACHE_ENABLED,
					LayoutImpl.class, primaryKey, _nullLayout);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the layouts.
	 *
	 * @return the layouts
	 */
	@Override
	public List<Layout> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of layouts
	 */
	@Override
	public List<Layout> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layouts
	 */
	@Override
	public List<Layout> findAll(int start, int end,
		OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_LAYOUT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUT;

				if (pagination) {
					sql = sql.concat(LayoutModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Layout>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the layouts from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Layout layout : findAll()) {
			remove(layout);
		}
	}

	/**
	 * Returns the number of layouts.
	 *
	 * @return the number of layouts
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUT);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	/**
	 * Initializes the layout persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Layout")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Layout>> listenersList = new ArrayList<ModelListener<Layout>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Layout>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(LayoutImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_LAYOUT = "SELECT layout FROM Layout layout";
	private static final String _SQL_SELECT_LAYOUT_WHERE_PKS_IN = "SELECT layout FROM Layout layout WHERE plid IN (";
	private static final String _SQL_SELECT_LAYOUT_WHERE = "SELECT layout FROM Layout layout WHERE ";
	private static final String _SQL_COUNT_LAYOUT = "SELECT COUNT(layout) FROM Layout layout";
	private static final String _SQL_COUNT_LAYOUT_WHERE = "SELECT COUNT(layout) FROM Layout layout WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "layout.plid";
	private static final String _FILTER_SQL_SELECT_LAYOUT_WHERE = "SELECT DISTINCT {layout.*} FROM Layout layout WHERE ";
	private static final String _FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {Layout.*} FROM (SELECT DISTINCT layout.plid FROM Layout layout WHERE ";
	private static final String _FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN Layout ON TEMP_TABLE.plid = Layout.plid";
	private static final String _FILTER_SQL_COUNT_LAYOUT_WHERE = "SELECT COUNT(DISTINCT layout.plid) AS COUNT_VALUE FROM Layout layout WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "layout";
	private static final String _FILTER_ENTITY_TABLE = "Layout";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layout.";
	private static final String _ORDER_BY_ENTITY_TABLE = "Layout.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Layout exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Layout exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(LayoutPersistenceImpl.class);
	private static Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "type", "hidden"
			});
	private static Layout _nullLayout = new LayoutImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Layout> toCacheModel() {
				return _nullLayoutCacheModel;
			}
		};

	private static CacheModel<Layout> _nullLayoutCacheModel = new NullCacheModel();

	private static class NullCacheModel implements CacheModel<Layout>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return 0;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public Layout toEntityModel() {
			return _nullLayout;
		}
	}
}