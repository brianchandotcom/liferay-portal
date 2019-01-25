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

import aQute.bnd.annotation.ProviderType;

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
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.LayoutPersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.NestedSetsTreeManager;
import com.liferay.portal.kernel.service.persistence.impl.PersistenceNestedSetsTreeManager;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.LayoutModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
 * @see com.liferay.portal.kernel.service.persistence.LayoutUtil
 * @generated
 */
@ProviderType
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
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationCountAncestors;
	private FinderPath _finderPathWithPaginationCountDescendants;
	private FinderPath _finderPathWithPaginationGetAncestors;
	private FinderPath _finderPathWithPaginationGetDescendants;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		OrderByComparator<Layout> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layouts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByUuid(String uuid, int start, int end,
		OrderByComparator<Layout> orderByComparator, boolean retrieveFromCache) {
		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUuid;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<Layout> list = null;

		if (retrieveFromCache) {
			list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Layout layout : list) {
					if (!uuid.equals(layout.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByUuid_First(String uuid,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByUuid_First(uuid, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

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
		OrderByComparator<Layout> orderByComparator) {
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
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByUuid_Last(String uuid,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByUuid_Last(uuid, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

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
		OrderByComparator<Layout> orderByComparator) {
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
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findByUuid_PrevAndNext(long plid, String uuid,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		uuid = Objects.toString(uuid, "");

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
		String uuid, OrderByComparator<Layout> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUT_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
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
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layout)) {
				qPos.add(orderByConditionValue);
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
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_2 = "layout.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(layout.uuid IS NULL OR layout.uuid = '')";
	private FinderPath _finderPathFetchByUUID_G_P;
	private FinderPath _finderPathCountByUUID_G_P;

	/**
	 * Returns the layout where uuid = &#63; and groupId = &#63; and privateLayout = &#63; or throws a {@link NoSuchLayoutException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the matching layout
	 * @throws NoSuchLayoutException if a matching layout could not be found
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

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
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
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByUUID_G_P(String uuid, long groupId,
		boolean privateLayout, boolean retrieveFromCache) {
		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = new Object[] { uuid, groupId, privateLayout };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(_finderPathFetchByUUID_G_P,
					finderArgs, this);
		}

		if (result instanceof Layout) {
			Layout layout = (Layout)result;

			if (!Objects.equals(uuid, layout.getUuid()) ||
					(groupId != layout.getGroupId()) ||
					(privateLayout != layout.isPrivateLayout())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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
					FinderCacheUtil.putResult(_finderPathFetchByUUID_G_P,
						finderArgs, list);
				}
				else {
					Layout layout = list.get(0);

					result = layout;

					cacheResult(layout);
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathFetchByUUID_G_P,
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
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G_P;

		Object[] finderArgs = new Object[] { uuid, groupId, privateLayout };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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

	private static final String _FINDER_COLUMN_UUID_G_P_UUID_2 = "layout.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_P_UUID_3 = "(layout.uuid IS NULL OR layout.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_P_GROUPID_2 = "layout.groupId = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_P_PRIVATELAYOUT_2 = "layout.privateLayout = ?";
	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		int end, OrderByComparator<Layout> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<Layout> orderByComparator,
		boolean retrieveFromCache) {
		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUuid_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<Layout> list = null;

		if (retrieveFromCache) {
			list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Layout layout : list) {
					if (!uuid.equals(layout.getUuid()) ||
							(companyId != layout.getCompanyId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

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
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
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

		msg.append("}");

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
		OrderByComparator<Layout> orderByComparator) {
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
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
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

		msg.append("}");

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
		OrderByComparator<Layout> orderByComparator) {
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
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findByUuid_C_PrevAndNext(long plid, String uuid,
		long companyId, OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		uuid = Objects.toString(uuid, "");

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
		String uuid, long companyId,
		OrderByComparator<Layout> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUT_WHERE);

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
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layout)) {
				qPos.add(orderByConditionValue);
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
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "layout.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(layout.uuid IS NULL OR layout.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "layout.companyId = ?";
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		OrderByComparator<Layout> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layouts where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByGroupId(long groupId, int start, int end,
		OrderByComparator<Layout> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByGroupId;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<Layout> list = null;

		if (retrieveFromCache) {
			list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Layout layout : list) {
					if ((groupId != layout.getGroupId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
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
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByGroupId_First(long groupId,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByGroupId_First(groupId, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

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
		OrderByComparator<Layout> orderByComparator) {
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
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByGroupId_Last(long groupId,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByGroupId_Last(groupId, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

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
		OrderByComparator<Layout> orderByComparator) {
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
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findByGroupId_PrevAndNext(long plid, long groupId,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
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
		long groupId, OrderByComparator<Layout> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
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
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layout)) {
				qPos.add(orderByConditionValue);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		OrderByComparator<Layout> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 2));
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
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] filterFindByGroupId_PrevAndNext(long plid, long groupId,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
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
		Layout layout, long groupId,
		OrderByComparator<Layout> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
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
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layout)) {
				qPos.add(orderByConditionValue);
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
		FinderPath finderPath = _finderPathCountByGroupId;

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "layout.groupId = ? AND layout.system = [$FALSE$]";
	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		OrderByComparator<Layout> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layouts where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByCompanyId(long companyId, int start, int end,
		OrderByComparator<Layout> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByCompanyId;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<Layout> list = null;

		if (retrieveFromCache) {
			list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Layout layout : list) {
					if ((companyId != layout.getCompanyId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
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
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByCompanyId_First(long companyId,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByCompanyId_First(companyId, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

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
		OrderByComparator<Layout> orderByComparator) {
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
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByCompanyId_Last(long companyId,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByCompanyId_Last(companyId, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

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
		OrderByComparator<Layout> orderByComparator) {
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
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findByCompanyId_PrevAndNext(long plid, long companyId,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
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
		long companyId, OrderByComparator<Layout> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
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
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layout)) {
				qPos.add(orderByConditionValue);
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
		FinderPath finderPath = _finderPathCountByCompanyId;

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "layout.companyId = ? AND layout.system = [$FALSE$]";
	private FinderPath _finderPathWithPaginationFindByParentPlid;
	private FinderPath _finderPathWithoutPaginationFindByParentPlid;
	private FinderPath _finderPathCountByParentPlid;

	/**
	 * Returns all the layouts where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @return the matching layouts
	 */
	@Override
	public List<Layout> findByParentPlid(long parentPlid) {
		return findByParentPlid(parentPlid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts where parentPlid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentPlid the parent plid
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts
	 */
	@Override
	public List<Layout> findByParentPlid(long parentPlid, int start, int end) {
		return findByParentPlid(parentPlid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layouts where parentPlid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentPlid the parent plid
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByParentPlid(long parentPlid, int start, int end,
		OrderByComparator<Layout> orderByComparator) {
		return findByParentPlid(parentPlid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layouts where parentPlid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentPlid the parent plid
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByParentPlid(long parentPlid, int start, int end,
		OrderByComparator<Layout> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByParentPlid;
			finderArgs = new Object[] { parentPlid };
		}
		else {
			finderPath = _finderPathWithPaginationFindByParentPlid;
			finderArgs = new Object[] { parentPlid, start, end, orderByComparator };
		}

		List<Layout> list = null;

		if (retrieveFromCache) {
			list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Layout layout : list) {
					if ((parentPlid != layout.getParentPlid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_PARENTPLID_PARENTPLID_2);

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

				qPos.add(parentPlid);

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
	 * Returns the first layout in the ordered set where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByParentPlid_First(long parentPlid,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByParentPlid_First(parentPlid, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentPlid=");
		msg.append(parentPlid);

		msg.append("}");

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the first layout in the ordered set where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByParentPlid_First(long parentPlid,
		OrderByComparator<Layout> orderByComparator) {
		List<Layout> list = findByParentPlid(parentPlid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout in the ordered set where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByParentPlid_Last(long parentPlid,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByParentPlid_Last(parentPlid, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentPlid=");
		msg.append(parentPlid);

		msg.append("}");

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the last layout in the ordered set where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByParentPlid_Last(long parentPlid,
		OrderByComparator<Layout> orderByComparator) {
		int count = countByParentPlid(parentPlid);

		if (count == 0) {
			return null;
		}

		List<Layout> list = findByParentPlid(parentPlid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layouts before and after the current layout in the ordered set where parentPlid = &#63;.
	 *
	 * @param plid the primary key of the current layout
	 * @param parentPlid the parent plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findByParentPlid_PrevAndNext(long plid, long parentPlid,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Layout[] array = new LayoutImpl[3];

			array[0] = getByParentPlid_PrevAndNext(session, layout, parentPlid,
					orderByComparator, true);

			array[1] = layout;

			array[2] = getByParentPlid_PrevAndNext(session, layout, parentPlid,
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

	protected Layout getByParentPlid_PrevAndNext(Session session,
		Layout layout, long parentPlid,
		OrderByComparator<Layout> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_PARENTPLID_PARENTPLID_2);

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

		qPos.add(parentPlid);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layout)) {
				qPos.add(orderByConditionValue);
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
	 * Removes all the layouts where parentPlid = &#63; from the database.
	 *
	 * @param parentPlid the parent plid
	 */
	@Override
	public void removeByParentPlid(long parentPlid) {
		for (Layout layout : findByParentPlid(parentPlid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(layout);
		}
	}

	/**
	 * Returns the number of layouts where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @return the number of matching layouts
	 */
	@Override
	public int countByParentPlid(long parentPlid) {
		FinderPath finderPath = _finderPathCountByParentPlid;

		Object[] finderArgs = new Object[] { parentPlid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_PARENTPLID_PARENTPLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentPlid);

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

	private static final String _FINDER_COLUMN_PARENTPLID_PARENTPLID_2 = "layout.parentPlid = ? AND layout.system = [$FALSE$]";
	private FinderPath _finderPathFetchByIconImageId;
	private FinderPath _finderPathCountByIconImageId;

	/**
	 * Returns the layout where iconImageId = &#63; or throws a {@link NoSuchLayoutException} if it could not be found.
	 *
	 * @param iconImageId the icon image ID
	 * @return the matching layout
	 * @throws NoSuchLayoutException if a matching layout could not be found
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

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
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
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByIconImageId(long iconImageId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { iconImageId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(_finderPathFetchByIconImageId,
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
					FinderCacheUtil.putResult(_finderPathFetchByIconImageId,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"LayoutPersistenceImpl.fetchByIconImageId(long, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					Layout layout = list.get(0);

					result = layout;

					cacheResult(layout);
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathFetchByIconImageId,
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
		FinderPath finderPath = _finderPathCountByIconImageId;

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
	private FinderPath _finderPathWithPaginationFindByLayoutPrototypeUuid;
	private FinderPath _finderPathWithoutPaginationFindByLayoutPrototypeUuid;
	private FinderPath _finderPathCountByLayoutPrototypeUuid;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		int start, int end, OrderByComparator<Layout> orderByComparator) {
		return findByLayoutPrototypeUuid(layoutPrototypeUuid, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layouts where layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByLayoutPrototypeUuid(String layoutPrototypeUuid,
		int start, int end, OrderByComparator<Layout> orderByComparator,
		boolean retrieveFromCache) {
		layoutPrototypeUuid = Objects.toString(layoutPrototypeUuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByLayoutPrototypeUuid;
			finderArgs = new Object[] { layoutPrototypeUuid };
		}
		else {
			finderPath = _finderPathWithPaginationFindByLayoutPrototypeUuid;
			finderArgs = new Object[] {
					layoutPrototypeUuid,
					
					start, end, orderByComparator
				};
		}

		List<Layout> list = null;

		if (retrieveFromCache) {
			list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Layout layout : list) {
					if (!layoutPrototypeUuid.equals(
								layout.getLayoutPrototypeUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			boolean bindLayoutPrototypeUuid = false;

			if (layoutPrototypeUuid.isEmpty()) {
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
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByLayoutPrototypeUuid_First(String layoutPrototypeUuid,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByLayoutPrototypeUuid_First(layoutPrototypeUuid,
				orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutPrototypeUuid=");
		msg.append(layoutPrototypeUuid);

		msg.append("}");

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
		OrderByComparator<Layout> orderByComparator) {
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
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByLayoutPrototypeUuid_Last(String layoutPrototypeUuid,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByLayoutPrototypeUuid_Last(layoutPrototypeUuid,
				orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutPrototypeUuid=");
		msg.append(layoutPrototypeUuid);

		msg.append("}");

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
		OrderByComparator<Layout> orderByComparator) {
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
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findByLayoutPrototypeUuid_PrevAndNext(long plid,
		String layoutPrototypeUuid, OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		layoutPrototypeUuid = Objects.toString(layoutPrototypeUuid, "");

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
		OrderByComparator<Layout> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUT_WHERE);

		boolean bindLayoutPrototypeUuid = false;

		if (layoutPrototypeUuid.isEmpty()) {
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
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layout)) {
				qPos.add(orderByConditionValue);
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
		layoutPrototypeUuid = Objects.toString(layoutPrototypeUuid, "");

		FinderPath finderPath = _finderPathCountByLayoutPrototypeUuid;

		Object[] finderArgs = new Object[] { layoutPrototypeUuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			boolean bindLayoutPrototypeUuid = false;

			if (layoutPrototypeUuid.isEmpty()) {
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

	private static final String _FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_2 =
		"layout.layoutPrototypeUuid = ? AND layout.system = [$FALSE$]";
	private static final String _FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_3 =
		"(layout.layoutPrototypeUuid IS NULL OR layout.layoutPrototypeUuid = '') AND layout.system = [$FALSE$]";
	private FinderPath _finderPathWithPaginationFindBySourcePrototypeLayoutUuid;
	private FinderPath _finderPathWithoutPaginationFindBySourcePrototypeLayoutUuid;
	private FinderPath _finderPathCountBySourcePrototypeLayoutUuid;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		OrderByComparator<Layout> orderByComparator) {
		return findBySourcePrototypeLayoutUuid(sourcePrototypeLayoutUuid,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layouts where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid, int start, int end,
		OrderByComparator<Layout> orderByComparator, boolean retrieveFromCache) {
		sourcePrototypeLayoutUuid = Objects.toString(sourcePrototypeLayoutUuid,
				"");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindBySourcePrototypeLayoutUuid;
			finderArgs = new Object[] { sourcePrototypeLayoutUuid };
		}
		else {
			finderPath = _finderPathWithPaginationFindBySourcePrototypeLayoutUuid;
			finderArgs = new Object[] {
					sourcePrototypeLayoutUuid,
					
					start, end, orderByComparator
				};
		}

		List<Layout> list = null;

		if (retrieveFromCache) {
			list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Layout layout : list) {
					if (!sourcePrototypeLayoutUuid.equals(
								layout.getSourcePrototypeLayoutUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			boolean bindSourcePrototypeLayoutUuid = false;

			if (sourcePrototypeLayoutUuid.isEmpty()) {
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
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findBySourcePrototypeLayoutUuid_First(
		String sourcePrototypeLayoutUuid,
		OrderByComparator<Layout> orderByComparator)
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

		msg.append("}");

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
		String sourcePrototypeLayoutUuid,
		OrderByComparator<Layout> orderByComparator) {
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
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findBySourcePrototypeLayoutUuid_Last(
		String sourcePrototypeLayoutUuid,
		OrderByComparator<Layout> orderByComparator)
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

		msg.append("}");

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
		String sourcePrototypeLayoutUuid,
		OrderByComparator<Layout> orderByComparator) {
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
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findBySourcePrototypeLayoutUuid_PrevAndNext(long plid,
		String sourcePrototypeLayoutUuid,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		sourcePrototypeLayoutUuid = Objects.toString(sourcePrototypeLayoutUuid,
				"");

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
		OrderByComparator<Layout> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUT_WHERE);

		boolean bindSourcePrototypeLayoutUuid = false;

		if (sourcePrototypeLayoutUuid.isEmpty()) {
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
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layout)) {
				qPos.add(orderByConditionValue);
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
		sourcePrototypeLayoutUuid = Objects.toString(sourcePrototypeLayoutUuid,
				"");

		FinderPath finderPath = _finderPathCountBySourcePrototypeLayoutUuid;

		Object[] finderArgs = new Object[] { sourcePrototypeLayoutUuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			boolean bindSourcePrototypeLayoutUuid = false;

			if (sourcePrototypeLayoutUuid.isEmpty()) {
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

	private static final String _FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_2 =
		"layout.sourcePrototypeLayoutUuid = ? AND layout.system = [$FALSE$]";
	private static final String _FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_3 =
		"(layout.sourcePrototypeLayoutUuid IS NULL OR layout.sourcePrototypeLayoutUuid = '') AND layout.system = [$FALSE$]";
	private FinderPath _finderPathWithPaginationFindByG_P;
	private FinderPath _finderPathWithoutPaginationFindByG_P;
	private FinderPath _finderPathCountByG_P;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		int start, int end, OrderByComparator<Layout> orderByComparator) {
		return findByG_P(groupId, privateLayout, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByG_P(long groupId, boolean privateLayout,
		int start, int end, OrderByComparator<Layout> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_P;
			finderArgs = new Object[] { groupId, privateLayout };
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_P;
			finderArgs = new Object[] {
					groupId, privateLayout,
					
					start, end, orderByComparator
				};
		}

		List<Layout> list = null;

		if (retrieveFromCache) {
			list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Layout layout : list) {
					if ((groupId != layout.getGroupId()) ||
							(privateLayout != layout.isPrivateLayout())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
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
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByG_P_First(long groupId, boolean privateLayout,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
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

		msg.append("}");

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
		OrderByComparator<Layout> orderByComparator) {
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
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByG_P_Last(long groupId, boolean privateLayout,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
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

		msg.append("}");

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
		OrderByComparator<Layout> orderByComparator) {
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
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findByG_P_PrevAndNext(long plid, long groupId,
		boolean privateLayout, OrderByComparator<Layout> orderByComparator)
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
		OrderByComparator<Layout> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
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
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layout)) {
				qPos.add(orderByConditionValue);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		int start, int end, OrderByComparator<Layout> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P(groupId, privateLayout, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 2));
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
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] filterFindByG_P_PrevAndNext(long plid, long groupId,
		boolean privateLayout, OrderByComparator<Layout> orderByComparator)
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
		OrderByComparator<Layout> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
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
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layout)) {
				qPos.add(orderByConditionValue);
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
		FinderPath finderPath = _finderPathCountByG_P;

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
	private static final String _FINDER_COLUMN_G_P_PRIVATELAYOUT_2 = "layout.privateLayout = ? AND layout.system = [$FALSE$]";
	private FinderPath _finderPathWithPaginationFindByG_T;
	private FinderPath _finderPathWithoutPaginationFindByG_T;
	private FinderPath _finderPathCountByG_T;

	/**
	 * Returns all the layouts where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching layouts
	 */
	@Override
	public List<Layout> findByG_T(long groupId, String type) {
		return findByG_T(groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layouts where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts
	 */
	@Override
	public List<Layout> findByG_T(long groupId, String type, int start, int end) {
		return findByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layouts where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByG_T(long groupId, String type, int start,
		int end, OrderByComparator<Layout> orderByComparator) {
		return findByG_T(groupId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layouts where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByG_T(long groupId, String type, int start,
		int end, OrderByComparator<Layout> orderByComparator,
		boolean retrieveFromCache) {
		type = Objects.toString(type, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_T;
			finderArgs = new Object[] { groupId, type };
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_T;
			finderArgs = new Object[] {
					groupId, type,
					
					start, end, orderByComparator
				};
		}

		List<Layout> list = null;

		if (retrieveFromCache) {
			list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Layout layout : list) {
					if ((groupId != layout.getGroupId()) ||
							!type.equals(layout.getType())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_G_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_T_TYPE_2);
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
	 * Returns the first layout in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByG_T_First(long groupId, String type,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByG_T_First(groupId, type, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the first layout in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByG_T_First(long groupId, String type,
		OrderByComparator<Layout> orderByComparator) {
		List<Layout> list = findByG_T(groupId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByG_T_Last(long groupId, String type,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByG_T_Last(groupId, type, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the last layout in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByG_T_Last(long groupId, String type,
		OrderByComparator<Layout> orderByComparator) {
		int count = countByG_T(groupId, type);

		if (count == 0) {
			return null;
		}

		List<Layout> list = findByG_T(groupId, type, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layouts before and after the current layout in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param plid the primary key of the current layout
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findByG_T_PrevAndNext(long plid, long groupId, String type,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		type = Objects.toString(type, "");

		Layout layout = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Layout[] array = new LayoutImpl[3];

			array[0] = getByG_T_PrevAndNext(session, layout, groupId, type,
					orderByComparator, true);

			array[1] = layout;

			array[2] = getByG_T_PrevAndNext(session, layout, groupId, type,
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

	protected Layout getByG_T_PrevAndNext(Session session, Layout layout,
		long groupId, String type, OrderByComparator<Layout> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			query.append(_FINDER_COLUMN_G_T_TYPE_3);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_T_TYPE_2);
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

		if (bindType) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layout)) {
				qPos.add(orderByConditionValue);
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
	 * Returns all the layouts that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByG_T(long groupId, String type) {
		return filterFindByG_T(groupId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByG_T(long groupId, String type, int start,
		int end) {
		return filterFindByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layouts that the user has permissions to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByG_T(long groupId, String type, int start,
		int end, OrderByComparator<Layout> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T(groupId, type, start, end, orderByComparator);
		}

		type = Objects.toString(type, "");

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 2));
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

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			query.append(_FINDER_COLUMN_G_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);
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
	 * Returns the layouts before and after the current layout in the ordered set of layouts that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param plid the primary key of the current layout
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] filterFindByG_T_PrevAndNext(long plid, long groupId,
		String type, OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_PrevAndNext(plid, groupId, type, orderByComparator);
		}

		type = Objects.toString(type, "");

		Layout layout = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Layout[] array = new LayoutImpl[3];

			array[0] = filterGetByG_T_PrevAndNext(session, layout, groupId,
					type, orderByComparator, true);

			array[1] = layout;

			array[2] = filterGetByG_T_PrevAndNext(session, layout, groupId,
					type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Layout filterGetByG_T_PrevAndNext(Session session, Layout layout,
		long groupId, String type, OrderByComparator<Layout> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
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

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			query.append(_FINDER_COLUMN_G_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);
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

		if (bindType) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layout)) {
				qPos.add(orderByConditionValue);
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
	 * Removes all the layouts where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	@Override
	public void removeByG_T(long groupId, String type) {
		for (Layout layout : findByG_T(groupId, type, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(layout);
		}
	}

	/**
	 * Returns the number of layouts where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching layouts
	 */
	@Override
	public int countByG_T(long groupId, String type) {
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByG_T;

		Object[] finderArgs = new Object[] { groupId, type };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_G_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_T_TYPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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
	 * Returns the number of layouts that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching layouts that the user has permission to view
	 */
	@Override
	public int filterCountByG_T(long groupId, String type) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_T(groupId, type);
		}

		type = Objects.toString(type, "");

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			query.append(_FINDER_COLUMN_G_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);
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

	private static final String _FINDER_COLUMN_G_T_GROUPID_2 = "layout.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_TYPE_2 = "layout.type = ? AND layout.system = [$FALSE$]";
	private static final String _FINDER_COLUMN_G_T_TYPE_3 = "(layout.type IS NULL OR layout.type = '') AND layout.system = [$FALSE$]";
	private static final String _FINDER_COLUMN_G_T_TYPE_2_SQL = "layout.type_ = ? AND layout.system = [$FALSE$]";
	private static final String _FINDER_COLUMN_G_T_TYPE_3_SQL = "(layout.type_ IS NULL OR layout.type_ = '') AND layout.system = [$FALSE$]";
	private FinderPath _finderPathWithPaginationFindByC_L;
	private FinderPath _finderPathWithoutPaginationFindByC_L;
	private FinderPath _finderPathCountByC_L;

	/**
	 * Returns all the layouts where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @return the matching layouts
	 */
	@Override
	public List<Layout> findByC_L(long companyId, String layoutPrototypeUuid) {
		return findByC_L(companyId, layoutPrototypeUuid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts
	 */
	@Override
	public List<Layout> findByC_L(long companyId, String layoutPrototypeUuid,
		int start, int end) {
		return findByC_L(companyId, layoutPrototypeUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layouts where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByC_L(long companyId, String layoutPrototypeUuid,
		int start, int end, OrderByComparator<Layout> orderByComparator) {
		return findByC_L(companyId, layoutPrototypeUuid, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layouts where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByC_L(long companyId, String layoutPrototypeUuid,
		int start, int end, OrderByComparator<Layout> orderByComparator,
		boolean retrieveFromCache) {
		layoutPrototypeUuid = Objects.toString(layoutPrototypeUuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByC_L;
			finderArgs = new Object[] { companyId, layoutPrototypeUuid };
		}
		else {
			finderPath = _finderPathWithPaginationFindByC_L;
			finderArgs = new Object[] {
					companyId, layoutPrototypeUuid,
					
					start, end, orderByComparator
				};
		}

		List<Layout> list = null;

		if (retrieveFromCache) {
			list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Layout layout : list) {
					if ((companyId != layout.getCompanyId()) ||
							!layoutPrototypeUuid.equals(
								layout.getLayoutPrototypeUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_C_L_COMPANYID_2);

			boolean bindLayoutPrototypeUuid = false;

			if (layoutPrototypeUuid.isEmpty()) {
				query.append(_FINDER_COLUMN_C_L_LAYOUTPROTOTYPEUUID_3);
			}
			else {
				bindLayoutPrototypeUuid = true;

				query.append(_FINDER_COLUMN_C_L_LAYOUTPROTOTYPEUUID_2);
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

				qPos.add(companyId);

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
	 * Returns the first layout in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByC_L_First(long companyId, String layoutPrototypeUuid,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByC_L_First(companyId, layoutPrototypeUuid,
				orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", layoutPrototypeUuid=");
		msg.append(layoutPrototypeUuid);

		msg.append("}");

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the first layout in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByC_L_First(long companyId, String layoutPrototypeUuid,
		OrderByComparator<Layout> orderByComparator) {
		List<Layout> list = findByC_L(companyId, layoutPrototypeUuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByC_L_Last(long companyId, String layoutPrototypeUuid,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByC_L_Last(companyId, layoutPrototypeUuid,
				orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", layoutPrototypeUuid=");
		msg.append(layoutPrototypeUuid);

		msg.append("}");

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the last layout in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByC_L_Last(long companyId, String layoutPrototypeUuid,
		OrderByComparator<Layout> orderByComparator) {
		int count = countByC_L(companyId, layoutPrototypeUuid);

		if (count == 0) {
			return null;
		}

		List<Layout> list = findByC_L(companyId, layoutPrototypeUuid,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layouts before and after the current layout in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param plid the primary key of the current layout
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findByC_L_PrevAndNext(long plid, long companyId,
		String layoutPrototypeUuid, OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		layoutPrototypeUuid = Objects.toString(layoutPrototypeUuid, "");

		Layout layout = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Layout[] array = new LayoutImpl[3];

			array[0] = getByC_L_PrevAndNext(session, layout, companyId,
					layoutPrototypeUuid, orderByComparator, true);

			array[1] = layout;

			array[2] = getByC_L_PrevAndNext(session, layout, companyId,
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

	protected Layout getByC_L_PrevAndNext(Session session, Layout layout,
		long companyId, String layoutPrototypeUuid,
		OrderByComparator<Layout> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_C_L_COMPANYID_2);

		boolean bindLayoutPrototypeUuid = false;

		if (layoutPrototypeUuid.isEmpty()) {
			query.append(_FINDER_COLUMN_C_L_LAYOUTPROTOTYPEUUID_3);
		}
		else {
			bindLayoutPrototypeUuid = true;

			query.append(_FINDER_COLUMN_C_L_LAYOUTPROTOTYPEUUID_2);
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

		qPos.add(companyId);

		if (bindLayoutPrototypeUuid) {
			qPos.add(layoutPrototypeUuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layout)) {
				qPos.add(orderByConditionValue);
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
	 * Removes all the layouts where companyId = &#63; and layoutPrototypeUuid = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 */
	@Override
	public void removeByC_L(long companyId, String layoutPrototypeUuid) {
		for (Layout layout : findByC_L(companyId, layoutPrototypeUuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layout);
		}
	}

	/**
	 * Returns the number of layouts where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @return the number of matching layouts
	 */
	@Override
	public int countByC_L(long companyId, String layoutPrototypeUuid) {
		layoutPrototypeUuid = Objects.toString(layoutPrototypeUuid, "");

		FinderPath finderPath = _finderPathCountByC_L;

		Object[] finderArgs = new Object[] { companyId, layoutPrototypeUuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_C_L_COMPANYID_2);

			boolean bindLayoutPrototypeUuid = false;

			if (layoutPrototypeUuid.isEmpty()) {
				query.append(_FINDER_COLUMN_C_L_LAYOUTPROTOTYPEUUID_3);
			}
			else {
				bindLayoutPrototypeUuid = true;

				query.append(_FINDER_COLUMN_C_L_LAYOUTPROTOTYPEUUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_C_L_COMPANYID_2 = "layout.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_L_LAYOUTPROTOTYPEUUID_2 = "layout.layoutPrototypeUuid = ? AND layout.system = [$FALSE$]";
	private static final String _FINDER_COLUMN_C_L_LAYOUTPROTOTYPEUUID_3 = "(layout.layoutPrototypeUuid IS NULL OR layout.layoutPrototypeUuid = '') AND layout.system = [$FALSE$]";
	private FinderPath _finderPathFetchByP_I;
	private FinderPath _finderPathCountByP_I;

	/**
	 * Returns the layout where privateLayout = &#63; and iconImageId = &#63; or throws a {@link NoSuchLayoutException} if it could not be found.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @return the matching layout
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByP_I(boolean privateLayout, long iconImageId)
		throws NoSuchLayoutException {
		Layout layout = fetchByP_I(privateLayout, iconImageId);

		if (layout == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("privateLayout=");
			msg.append(privateLayout);

			msg.append(", iconImageId=");
			msg.append(iconImageId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLayoutException(msg.toString());
		}

		return layout;
	}

	/**
	 * Returns the layout where privateLayout = &#63; and iconImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @return the matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByP_I(boolean privateLayout, long iconImageId) {
		return fetchByP_I(privateLayout, iconImageId, true);
	}

	/**
	 * Returns the layout where privateLayout = &#63; and iconImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByP_I(boolean privateLayout, long iconImageId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { privateLayout, iconImageId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(_finderPathFetchByP_I,
					finderArgs, this);
		}

		if (result instanceof Layout) {
			Layout layout = (Layout)result;

			if ((privateLayout != layout.isPrivateLayout()) ||
					(iconImageId != layout.getIconImageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_P_I_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_P_I_ICONIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(privateLayout);

				qPos.add(iconImageId);

				List<Layout> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(_finderPathFetchByP_I,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"LayoutPersistenceImpl.fetchByP_I(boolean, long, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					Layout layout = list.get(0);

					result = layout;

					cacheResult(layout);
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathFetchByP_I, finderArgs);

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
	 * Removes the layout where privateLayout = &#63; and iconImageId = &#63; from the database.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @return the layout that was removed
	 */
	@Override
	public Layout removeByP_I(boolean privateLayout, long iconImageId)
		throws NoSuchLayoutException {
		Layout layout = findByP_I(privateLayout, iconImageId);

		return remove(layout);
	}

	/**
	 * Returns the number of layouts where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @return the number of matching layouts
	 */
	@Override
	public int countByP_I(boolean privateLayout, long iconImageId) {
		FinderPath finderPath = _finderPathCountByP_I;

		Object[] finderArgs = new Object[] { privateLayout, iconImageId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_P_I_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_P_I_ICONIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(privateLayout);

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

	private static final String _FINDER_COLUMN_P_I_PRIVATELAYOUT_2 = "layout.privateLayout = ? AND ";
	private static final String _FINDER_COLUMN_P_I_ICONIMAGEID_2 = "layout.iconImageId = ?";
	private FinderPath _finderPathFetchByG_P_L;
	private FinderPath _finderPathCountByG_P_L;

	/**
	 * Returns the layout where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or throws a {@link NoSuchLayoutException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching layout
	 * @throws NoSuchLayoutException if a matching layout could not be found
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

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
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
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByG_P_L(long groupId, boolean privateLayout,
		long layoutId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, privateLayout, layoutId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(_finderPathFetchByG_P_L,
					finderArgs, this);
		}

		if (result instanceof Layout) {
			Layout layout = (Layout)result;

			if ((groupId != layout.getGroupId()) ||
					(privateLayout != layout.isPrivateLayout()) ||
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
					FinderCacheUtil.putResult(_finderPathFetchByG_P_L,
						finderArgs, list);
				}
				else {
					Layout layout = list.get(0);

					result = layout;

					cacheResult(layout);
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathFetchByG_P_L, finderArgs);

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
		FinderPath finderPath = _finderPathCountByG_P_L;

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
	private FinderPath _finderPathWithPaginationFindByG_P_P;
	private FinderPath _finderPathWithoutPaginationFindByG_P_P;
	private FinderPath _finderPathCountByG_P_P;
	private FinderPath _finderPathWithPaginationCountByG_P_P;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		OrderByComparator<Layout> orderByComparator) {
		return findByG_P_P(groupId, privateLayout, parentLayoutId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId, int start, int end,
		OrderByComparator<Layout> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_P_P;
			finderArgs = new Object[] { groupId, privateLayout, parentLayoutId };
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_P_P;
			finderArgs = new Object[] {
					groupId, privateLayout, parentLayoutId,
					
					start, end, orderByComparator
				};
		}

		List<Layout> list = null;

		if (retrieveFromCache) {
			list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Layout layout : list) {
					if ((groupId != layout.getGroupId()) ||
							(privateLayout != layout.isPrivateLayout()) ||
							(parentLayoutId != layout.getParentLayoutId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
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
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByG_P_P_First(long groupId, boolean privateLayout,
		long parentLayoutId, OrderByComparator<Layout> orderByComparator)
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

		msg.append("}");

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
		long parentLayoutId, OrderByComparator<Layout> orderByComparator) {
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
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByG_P_P_Last(long groupId, boolean privateLayout,
		long parentLayoutId, OrderByComparator<Layout> orderByComparator)
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

		msg.append("}");

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
		long parentLayoutId, OrderByComparator<Layout> orderByComparator) {
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
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findByG_P_P_PrevAndNext(long plid, long groupId,
		boolean privateLayout, long parentLayoutId,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
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
		OrderByComparator<Layout> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
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
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layout)) {
				qPos.add(orderByConditionValue);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		OrderByComparator<Layout> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_P(groupId, privateLayout, parentLayoutId, start,
				end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(6);
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
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] filterFindByG_P_P_PrevAndNext(long plid, long groupId,
		boolean privateLayout, long parentLayoutId,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
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
		long parentLayoutId, OrderByComparator<Layout> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(7 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
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
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layout)) {
				qPos.add(orderByConditionValue);
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
	 * Returns all the layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and parentLayoutId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutIds the parent layout IDs
	 * @return the matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByG_P_P(long groupId, boolean privateLayout,
		long[] parentLayoutIds) {
		return filterFindByG_P_P(groupId, privateLayout, parentLayoutIds,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and parentLayoutId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutIds the parent layout IDs
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByG_P_P(long groupId, boolean privateLayout,
		long[] parentLayoutIds, int start, int end) {
		return filterFindByG_P_P(groupId, privateLayout, parentLayoutIds,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and parentLayoutId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutIds the parent layout IDs
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByG_P_P(long groupId, boolean privateLayout,
		long[] parentLayoutIds, int start, int end,
		OrderByComparator<Layout> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_P(groupId, privateLayout, parentLayoutIds, start,
				end, orderByComparator);
		}

		if (parentLayoutIds == null) {
			parentLayoutIds = new long[0];
		}
		else if (parentLayoutIds.length > 1) {
			parentLayoutIds = ArrayUtil.unique(parentLayoutIds);

			Arrays.sort(parentLayoutIds);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_P_PRIVATELAYOUT_2);

		if (parentLayoutIds.length > 0) {
			query.append("(");

			query.append(_FINDER_COLUMN_G_P_P_PARENTLAYOUTID_7);

			query.append(StringUtil.merge(parentLayoutIds));

			query.append(")");

			query.append(")");
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		query.append(" AND layout.system = [$FALSE$]");

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
	 * Returns all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutIds the parent layout IDs
	 * @return the matching layouts
	 */
	@Override
	public List<Layout> findByG_P_P(long groupId, boolean privateLayout,
		long[] parentLayoutIds) {
		return findByG_P_P(groupId, privateLayout, parentLayoutIds,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutIds the parent layout IDs
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts
	 */
	@Override
	public List<Layout> findByG_P_P(long groupId, boolean privateLayout,
		long[] parentLayoutIds, int start, int end) {
		return findByG_P_P(groupId, privateLayout, parentLayoutIds, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutIds the parent layout IDs
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByG_P_P(long groupId, boolean privateLayout,
		long[] parentLayoutIds, int start, int end,
		OrderByComparator<Layout> orderByComparator) {
		return findByG_P_P(groupId, privateLayout, parentLayoutIds, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByG_P_P(long groupId, boolean privateLayout,
		long[] parentLayoutIds, int start, int end,
		OrderByComparator<Layout> orderByComparator, boolean retrieveFromCache) {
		if (parentLayoutIds == null) {
			parentLayoutIds = new long[0];
		}
		else if (parentLayoutIds.length > 1) {
			parentLayoutIds = ArrayUtil.unique(parentLayoutIds);

			Arrays.sort(parentLayoutIds);
		}

		if (parentLayoutIds.length == 1) {
			return findByG_P_P(groupId, privateLayout, parentLayoutIds[0],
				start, end, orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] {
					groupId, privateLayout, StringUtil.merge(parentLayoutIds)
				};
		}
		else {
			finderArgs = new Object[] {
					groupId, privateLayout, StringUtil.merge(parentLayoutIds),
					
					start, end, orderByComparator
				};
		}

		List<Layout> list = null;

		if (retrieveFromCache) {
			list = (List<Layout>)FinderCacheUtil.getResult(_finderPathWithPaginationFindByG_P_P,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Layout layout : list) {
					if ((groupId != layout.getGroupId()) ||
							(privateLayout != layout.isPrivateLayout()) ||
							!ArrayUtil.contains(parentLayoutIds,
								layout.getParentLayoutId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			try {
				if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
						(databaseInMaxParameters > 0) &&
						(parentLayoutIds.length > databaseInMaxParameters)) {
					list = new ArrayList<Layout>();

					long[][] parentLayoutIdsPages = (long[][])ArrayUtil.split(parentLayoutIds,
							databaseInMaxParameters);

					for (long[] parentLayoutIdsPage : parentLayoutIdsPages) {
						list.addAll(_findByG_P_P(groupId, privateLayout,
								parentLayoutIdsPage, start, end,
								orderByComparator, pagination));
					}

					Collections.sort(list, orderByComparator);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = _findByG_P_P(groupId, privateLayout,
							parentLayoutIds, start, end, orderByComparator,
							pagination);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(_finderPathWithPaginationFindByG_P_P,
					finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathWithPaginationFindByG_P_P,
					finderArgs);

				throw processException(e);
			}
		}

		return list;
	}

	private List<Layout> _findByG_P_P(long groupId, boolean privateLayout,
		long[] parentLayoutIds, int start, int end,
		OrderByComparator<Layout> orderByComparator, boolean pagination) {
		List<Layout> list = null;

		StringBundler query = new StringBundler();

		query.append(_SQL_SELECT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_G_P_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_P_PRIVATELAYOUT_2);

		if (parentLayoutIds.length > 0) {
			query.append("(");

			query.append(_FINDER_COLUMN_G_P_P_PARENTLAYOUTID_7);

			query.append(StringUtil.merge(parentLayoutIds));

			query.append(")");

			query.append(")");
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		query.append(" AND layout.system = [$FALSE$]");

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
				list = (List<Layout>)QueryUtil.list(q, getDialect(), start, end);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return list;
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
		FinderPath finderPath = _finderPathCountByG_P_P;

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
	 * Returns the number of layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutIds the parent layout IDs
	 * @return the number of matching layouts
	 */
	@Override
	public int countByG_P_P(long groupId, boolean privateLayout,
		long[] parentLayoutIds) {
		if (parentLayoutIds == null) {
			parentLayoutIds = new long[0];
		}
		else if (parentLayoutIds.length > 1) {
			parentLayoutIds = ArrayUtil.unique(parentLayoutIds);

			Arrays.sort(parentLayoutIds);
		}

		Object[] finderArgs = new Object[] {
				groupId, privateLayout, StringUtil.merge(parentLayoutIds)
			};

		Long count = (Long)FinderCacheUtil.getResult(_finderPathWithPaginationCountByG_P_P,
				finderArgs, this);

		if (count == null) {
			try {
				if ((databaseInMaxParameters > 0) &&
						(parentLayoutIds.length > databaseInMaxParameters)) {
					count = Long.valueOf(0);

					long[][] parentLayoutIdsPages = (long[][])ArrayUtil.split(parentLayoutIds,
							databaseInMaxParameters);

					for (long[] parentLayoutIdsPage : parentLayoutIdsPages) {
						count += Long.valueOf(_countByG_P_P(groupId,
								privateLayout, parentLayoutIdsPage));
					}
				}
				else {
					count = Long.valueOf(_countByG_P_P(groupId, privateLayout,
								parentLayoutIds));
				}

				FinderCacheUtil.putResult(_finderPathWithPaginationCountByG_P_P,
					finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathWithPaginationCountByG_P_P,
					finderArgs);

				throw processException(e);
			}
		}

		return count.intValue();
	}

	private int _countByG_P_P(long groupId, boolean privateLayout,
		long[] parentLayoutIds) {
		Long count = null;

		StringBundler query = new StringBundler();

		query.append(_SQL_COUNT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_G_P_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_P_PRIVATELAYOUT_2);

		if (parentLayoutIds.length > 0) {
			query.append("(");

			query.append(_FINDER_COLUMN_G_P_P_PARENTLAYOUTID_7);

			query.append(StringUtil.merge(parentLayoutIds));

			query.append(")");

			query.append(")");
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		query.append(" AND layout.system = [$FALSE$]");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

			count = (Long)q.uniqueResult();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
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

	/**
	 * Returns the number of layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and parentLayoutId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutIds the parent layout IDs
	 * @return the number of matching layouts that the user has permission to view
	 */
	@Override
	public int filterCountByG_P_P(long groupId, boolean privateLayout,
		long[] parentLayoutIds) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P_P(groupId, privateLayout, parentLayoutIds);
		}

		if (parentLayoutIds == null) {
			parentLayoutIds = new long[0];
		}
		else if (parentLayoutIds.length > 1) {
			parentLayoutIds = ArrayUtil.unique(parentLayoutIds);

			Arrays.sort(parentLayoutIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_G_P_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_P_PRIVATELAYOUT_2);

		if (parentLayoutIds.length > 0) {
			query.append("(");

			query.append(_FINDER_COLUMN_G_P_P_PARENTLAYOUTID_7);

			query.append(StringUtil.merge(parentLayoutIds));

			query.append(")");

			query.append(")");
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		query.append(" AND layout.system = [$FALSE$]");

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

	private static final String _FINDER_COLUMN_G_P_P_GROUPID_2 = "layout.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_P_PRIVATELAYOUT_2 = "layout.privateLayout = ? AND ";
	private static final String _FINDER_COLUMN_G_P_P_PARENTLAYOUTID_2 = "layout.parentLayoutId = ? AND layout.system = [$FALSE$]";
	private static final String _FINDER_COLUMN_G_P_P_PARENTLAYOUTID_7 = "layout.parentLayoutId IN (";
	private FinderPath _finderPathWithPaginationFindByG_P_T;
	private FinderPath _finderPathWithoutPaginationFindByG_P_T;
	private FinderPath _finderPathCountByG_P_T;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		String type, int start, int end,
		OrderByComparator<Layout> orderByComparator) {
		return findByG_P_T(groupId, privateLayout, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByG_P_T(long groupId, boolean privateLayout,
		String type, int start, int end,
		OrderByComparator<Layout> orderByComparator, boolean retrieveFromCache) {
		type = Objects.toString(type, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_P_T;
			finderArgs = new Object[] { groupId, privateLayout, type };
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_P_T;
			finderArgs = new Object[] {
					groupId, privateLayout, type,
					
					start, end, orderByComparator
				};
		}

		List<Layout> list = null;

		if (retrieveFromCache) {
			list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Layout layout : list) {
					if ((groupId != layout.getGroupId()) ||
							(privateLayout != layout.isPrivateLayout()) ||
							!type.equals(layout.getType())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_P_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_T_PRIVATELAYOUT_2);

			boolean bindType = false;

			if (type.isEmpty()) {
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
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByG_P_T_First(long groupId, boolean privateLayout,
		String type, OrderByComparator<Layout> orderByComparator)
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

		msg.append("}");

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
		String type, OrderByComparator<Layout> orderByComparator) {
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
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByG_P_T_Last(long groupId, boolean privateLayout,
		String type, OrderByComparator<Layout> orderByComparator)
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

		msg.append("}");

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
		String type, OrderByComparator<Layout> orderByComparator) {
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
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findByG_P_T_PrevAndNext(long plid, long groupId,
		boolean privateLayout, String type,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		type = Objects.toString(type, "");

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
		OrderByComparator<Layout> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_G_P_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_T_PRIVATELAYOUT_2);

		boolean bindType = false;

		if (type.isEmpty()) {
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
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layout)) {
				qPos.add(orderByConditionValue);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		String type, int start, int end,
		OrderByComparator<Layout> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_T(groupId, privateLayout, type, start, end,
				orderByComparator);
		}

		type = Objects.toString(type, "");

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(6);
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

		if (type.isEmpty()) {
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
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] filterFindByG_P_T_PrevAndNext(long plid, long groupId,
		boolean privateLayout, String type,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_T_PrevAndNext(plid, groupId, privateLayout, type,
				orderByComparator);
		}

		type = Objects.toString(type, "");

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
		OrderByComparator<Layout> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(7 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
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

		if (type.isEmpty()) {
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
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layout)) {
				qPos.add(orderByConditionValue);
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
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByG_P_T;

		Object[] finderArgs = new Object[] { groupId, privateLayout, type };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_P_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_T_PRIVATELAYOUT_2);

			boolean bindType = false;

			if (type.isEmpty()) {
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

		type = Objects.toString(type, "");

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_G_P_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_T_PRIVATELAYOUT_2);

		boolean bindType = false;

		if (type.isEmpty()) {
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
	private static final String _FINDER_COLUMN_G_P_T_TYPE_2 = "layout.type = ? AND layout.system = [$FALSE$]";
	private static final String _FINDER_COLUMN_G_P_T_TYPE_3 = "(layout.type IS NULL OR layout.type = '') AND layout.system = [$FALSE$]";
	private static final String _FINDER_COLUMN_G_P_T_TYPE_2_SQL = "layout.type_ = ? AND layout.system = [$FALSE$]";
	private static final String _FINDER_COLUMN_G_P_T_TYPE_3_SQL = "(layout.type_ IS NULL OR layout.type_ = '') AND layout.system = [$FALSE$]";
	private FinderPath _finderPathFetchByG_P_F;
	private FinderPath _finderPathCountByG_P_F;

	/**
	 * Returns the layout where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; or throws a {@link NoSuchLayoutException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @return the matching layout
	 * @throws NoSuchLayoutException if a matching layout could not be found
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

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
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
	 * @param friendlyURL the friendly url
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
	 * @param friendlyURL the friendly url
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByG_P_F(long groupId, boolean privateLayout,
		String friendlyURL, boolean retrieveFromCache) {
		friendlyURL = Objects.toString(friendlyURL, "");

		Object[] finderArgs = new Object[] { groupId, privateLayout, friendlyURL };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(_finderPathFetchByG_P_F,
					finderArgs, this);
		}

		if (result instanceof Layout) {
			Layout layout = (Layout)result;

			if ((groupId != layout.getGroupId()) ||
					(privateLayout != layout.isPrivateLayout()) ||
					!Objects.equals(friendlyURL, layout.getFriendlyURL())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_P_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_F_PRIVATELAYOUT_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL.isEmpty()) {
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
					FinderCacheUtil.putResult(_finderPathFetchByG_P_F,
						finderArgs, list);
				}
				else {
					Layout layout = list.get(0);

					result = layout;

					cacheResult(layout);
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathFetchByG_P_F, finderArgs);

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
	 * @param friendlyURL the friendly url
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
	 * @param friendlyURL the friendly url
	 * @return the number of matching layouts
	 */
	@Override
	public int countByG_P_F(long groupId, boolean privateLayout,
		String friendlyURL) {
		friendlyURL = Objects.toString(friendlyURL, "");

		FinderPath finderPath = _finderPathCountByG_P_F;

		Object[] finderArgs = new Object[] { groupId, privateLayout, friendlyURL };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_P_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_F_PRIVATELAYOUT_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL.isEmpty()) {
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
	private static final String _FINDER_COLUMN_G_P_F_FRIENDLYURL_2 = "layout.friendlyURL = ?";
	private static final String _FINDER_COLUMN_G_P_F_FRIENDLYURL_3 = "(layout.friendlyURL IS NULL OR layout.friendlyURL = '')";
	private FinderPath _finderPathFetchByG_P_SPLU;
	private FinderPath _finderPathCountByG_P_SPLU;

	/**
	 * Returns the layout where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; or throws a {@link NoSuchLayoutException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @return the matching layout
	 * @throws NoSuchLayoutException if a matching layout could not be found
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

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
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
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByG_P_SPLU(long groupId, boolean privateLayout,
		String sourcePrototypeLayoutUuid, boolean retrieveFromCache) {
		sourcePrototypeLayoutUuid = Objects.toString(sourcePrototypeLayoutUuid,
				"");

		Object[] finderArgs = new Object[] {
				groupId, privateLayout, sourcePrototypeLayoutUuid
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(_finderPathFetchByG_P_SPLU,
					finderArgs, this);
		}

		if (result instanceof Layout) {
			Layout layout = (Layout)result;

			if ((groupId != layout.getGroupId()) ||
					(privateLayout != layout.isPrivateLayout()) ||
					!Objects.equals(sourcePrototypeLayoutUuid,
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

			if (sourcePrototypeLayoutUuid.isEmpty()) {
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
					FinderCacheUtil.putResult(_finderPathFetchByG_P_SPLU,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"LayoutPersistenceImpl.fetchByG_P_SPLU(long, boolean, String, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					Layout layout = list.get(0);

					result = layout;

					cacheResult(layout);
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathFetchByG_P_SPLU,
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
		sourcePrototypeLayoutUuid = Objects.toString(sourcePrototypeLayoutUuid,
				"");

		FinderPath finderPath = _finderPathCountByG_P_SPLU;

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

			if (sourcePrototypeLayoutUuid.isEmpty()) {
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
	private static final String _FINDER_COLUMN_G_P_SPLU_SOURCEPROTOTYPELAYOUTUUID_2 =
		"layout.sourcePrototypeLayoutUuid = ?";
	private static final String _FINDER_COLUMN_G_P_SPLU_SOURCEPROTOTYPELAYOUTUUID_3 =
		"(layout.sourcePrototypeLayoutUuid IS NULL OR layout.sourcePrototypeLayoutUuid = '')";
	private FinderPath _finderPathWithPaginationFindByG_P_GtLP_LtRP;
	private FinderPath _finderPathWithPaginationCountByG_P_GtLP_LtRP;

	/**
	 * Returns all the layouts where groupId = &#63; and leftPlid &gt; &#63; and rightPlid &lt; &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param leftPlid the left plid
	 * @param rightPlid the right plid
	 * @param privateLayout the private layout
	 * @return the matching layouts
	 */
	@Override
	public List<Layout> findByG_P_GtLP_LtRP(long groupId, long leftPlid,
		long rightPlid, boolean privateLayout) {
		return findByG_P_GtLP_LtRP(groupId, leftPlid, rightPlid, privateLayout,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts where groupId = &#63; and leftPlid &gt; &#63; and rightPlid &lt; &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param leftPlid the left plid
	 * @param rightPlid the right plid
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts
	 */
	@Override
	public List<Layout> findByG_P_GtLP_LtRP(long groupId, long leftPlid,
		long rightPlid, boolean privateLayout, int start, int end) {
		return findByG_P_GtLP_LtRP(groupId, leftPlid, rightPlid, privateLayout,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the layouts where groupId = &#63; and leftPlid &gt; &#63; and rightPlid &lt; &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param leftPlid the left plid
	 * @param rightPlid the right plid
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByG_P_GtLP_LtRP(long groupId, long leftPlid,
		long rightPlid, boolean privateLayout, int start, int end,
		OrderByComparator<Layout> orderByComparator) {
		return findByG_P_GtLP_LtRP(groupId, leftPlid, rightPlid, privateLayout,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layouts where groupId = &#63; and leftPlid &gt; &#63; and rightPlid &lt; &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param leftPlid the left plid
	 * @param rightPlid the right plid
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByG_P_GtLP_LtRP(long groupId, long leftPlid,
		long rightPlid, boolean privateLayout, int start, int end,
		OrderByComparator<Layout> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_P_GtLP_LtRP;
		finderArgs = new Object[] {
				groupId, leftPlid, rightPlid, privateLayout,
				
				start, end, orderByComparator
			};

		List<Layout> list = null;

		if (retrieveFromCache) {
			list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Layout layout : list) {
					if ((groupId != layout.getGroupId()) ||
							(leftPlid >= layout.getLeftPlid()) ||
							(rightPlid <= layout.getRightPlid()) ||
							(privateLayout != layout.isPrivateLayout())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_LEFTPLID_2);

			query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_RIGHTPLID_2);

			query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_PRIVATELAYOUT_2);

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

				qPos.add(leftPlid);

				qPos.add(rightPlid);

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
	 * Returns the first layout in the ordered set where groupId = &#63; and leftPlid &gt; &#63; and rightPlid &lt; &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param leftPlid the left plid
	 * @param rightPlid the right plid
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByG_P_GtLP_LtRP_First(long groupId, long leftPlid,
		long rightPlid, boolean privateLayout,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByG_P_GtLP_LtRP_First(groupId, leftPlid,
				rightPlid, privateLayout, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", leftPlid=");
		msg.append(leftPlid);

		msg.append(", rightPlid=");
		msg.append(rightPlid);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append("}");

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the first layout in the ordered set where groupId = &#63; and leftPlid &gt; &#63; and rightPlid &lt; &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param leftPlid the left plid
	 * @param rightPlid the right plid
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByG_P_GtLP_LtRP_First(long groupId, long leftPlid,
		long rightPlid, boolean privateLayout,
		OrderByComparator<Layout> orderByComparator) {
		List<Layout> list = findByG_P_GtLP_LtRP(groupId, leftPlid, rightPlid,
				privateLayout, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout in the ordered set where groupId = &#63; and leftPlid &gt; &#63; and rightPlid &lt; &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param leftPlid the left plid
	 * @param rightPlid the right plid
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByG_P_GtLP_LtRP_Last(long groupId, long leftPlid,
		long rightPlid, boolean privateLayout,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByG_P_GtLP_LtRP_Last(groupId, leftPlid, rightPlid,
				privateLayout, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", leftPlid=");
		msg.append(leftPlid);

		msg.append(", rightPlid=");
		msg.append(rightPlid);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append("}");

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the last layout in the ordered set where groupId = &#63; and leftPlid &gt; &#63; and rightPlid &lt; &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param leftPlid the left plid
	 * @param rightPlid the right plid
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByG_P_GtLP_LtRP_Last(long groupId, long leftPlid,
		long rightPlid, boolean privateLayout,
		OrderByComparator<Layout> orderByComparator) {
		int count = countByG_P_GtLP_LtRP(groupId, leftPlid, rightPlid,
				privateLayout);

		if (count == 0) {
			return null;
		}

		List<Layout> list = findByG_P_GtLP_LtRP(groupId, leftPlid, rightPlid,
				privateLayout, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layouts before and after the current layout in the ordered set where groupId = &#63; and leftPlid &gt; &#63; and rightPlid &lt; &#63; and privateLayout = &#63;.
	 *
	 * @param plid the primary key of the current layout
	 * @param groupId the group ID
	 * @param leftPlid the left plid
	 * @param rightPlid the right plid
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findByG_P_GtLP_LtRP_PrevAndNext(long plid, long groupId,
		long leftPlid, long rightPlid, boolean privateLayout,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Layout[] array = new LayoutImpl[3];

			array[0] = getByG_P_GtLP_LtRP_PrevAndNext(session, layout, groupId,
					leftPlid, rightPlid, privateLayout, orderByComparator, true);

			array[1] = layout;

			array[2] = getByG_P_GtLP_LtRP_PrevAndNext(session, layout, groupId,
					leftPlid, rightPlid, privateLayout, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Layout getByG_P_GtLP_LtRP_PrevAndNext(Session session,
		Layout layout, long groupId, long leftPlid, long rightPlid,
		boolean privateLayout, OrderByComparator<Layout> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(7 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_LEFTPLID_2);

		query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_RIGHTPLID_2);

		query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_PRIVATELAYOUT_2);

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

		qPos.add(leftPlid);

		qPos.add(rightPlid);

		qPos.add(privateLayout);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layout)) {
				qPos.add(orderByConditionValue);
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
	 * Returns all the layouts that the user has permission to view where groupId = &#63; and leftPlid &gt; &#63; and rightPlid &lt; &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param leftPlid the left plid
	 * @param rightPlid the right plid
	 * @param privateLayout the private layout
	 * @return the matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByG_P_GtLP_LtRP(long groupId, long leftPlid,
		long rightPlid, boolean privateLayout) {
		return filterFindByG_P_GtLP_LtRP(groupId, leftPlid, rightPlid,
			privateLayout, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts that the user has permission to view where groupId = &#63; and leftPlid &gt; &#63; and rightPlid &lt; &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param leftPlid the left plid
	 * @param rightPlid the right plid
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByG_P_GtLP_LtRP(long groupId, long leftPlid,
		long rightPlid, boolean privateLayout, int start, int end) {
		return filterFindByG_P_GtLP_LtRP(groupId, leftPlid, rightPlid,
			privateLayout, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layouts that the user has permissions to view where groupId = &#63; and leftPlid &gt; &#63; and rightPlid &lt; &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param leftPlid the left plid
	 * @param rightPlid the right plid
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByG_P_GtLP_LtRP(long groupId, long leftPlid,
		long rightPlid, boolean privateLayout, int start, int end,
		OrderByComparator<Layout> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_GtLP_LtRP(groupId, leftPlid, rightPlid,
				privateLayout, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_LEFTPLID_2);

		query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_RIGHTPLID_2);

		query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_PRIVATELAYOUT_2);

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

			qPos.add(leftPlid);

			qPos.add(rightPlid);

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
	 * Returns the layouts before and after the current layout in the ordered set of layouts that the user has permission to view where groupId = &#63; and leftPlid &gt; &#63; and rightPlid &lt; &#63; and privateLayout = &#63;.
	 *
	 * @param plid the primary key of the current layout
	 * @param groupId the group ID
	 * @param leftPlid the left plid
	 * @param rightPlid the right plid
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] filterFindByG_P_GtLP_LtRP_PrevAndNext(long plid,
		long groupId, long leftPlid, long rightPlid, boolean privateLayout,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_GtLP_LtRP_PrevAndNext(plid, groupId, leftPlid,
				rightPlid, privateLayout, orderByComparator);
		}

		Layout layout = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Layout[] array = new LayoutImpl[3];

			array[0] = filterGetByG_P_GtLP_LtRP_PrevAndNext(session, layout,
					groupId, leftPlid, rightPlid, privateLayout,
					orderByComparator, true);

			array[1] = layout;

			array[2] = filterGetByG_P_GtLP_LtRP_PrevAndNext(session, layout,
					groupId, leftPlid, rightPlid, privateLayout,
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

	protected Layout filterGetByG_P_GtLP_LtRP_PrevAndNext(Session session,
		Layout layout, long groupId, long leftPlid, long rightPlid,
		boolean privateLayout, OrderByComparator<Layout> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(8 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_LEFTPLID_2);

		query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_RIGHTPLID_2);

		query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_PRIVATELAYOUT_2);

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

		qPos.add(leftPlid);

		qPos.add(rightPlid);

		qPos.add(privateLayout);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layout)) {
				qPos.add(orderByConditionValue);
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
	 * Removes all the layouts where groupId = &#63; and leftPlid &gt; &#63; and rightPlid &lt; &#63; and privateLayout = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param leftPlid the left plid
	 * @param rightPlid the right plid
	 * @param privateLayout the private layout
	 */
	@Override
	public void removeByG_P_GtLP_LtRP(long groupId, long leftPlid,
		long rightPlid, boolean privateLayout) {
		for (Layout layout : findByG_P_GtLP_LtRP(groupId, leftPlid, rightPlid,
				privateLayout, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layout);
		}
	}

	/**
	 * Returns the number of layouts where groupId = &#63; and leftPlid &gt; &#63; and rightPlid &lt; &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param leftPlid the left plid
	 * @param rightPlid the right plid
	 * @param privateLayout the private layout
	 * @return the number of matching layouts
	 */
	@Override
	public int countByG_P_GtLP_LtRP(long groupId, long leftPlid,
		long rightPlid, boolean privateLayout) {
		FinderPath finderPath = _finderPathWithPaginationCountByG_P_GtLP_LtRP;

		Object[] finderArgs = new Object[] {
				groupId, leftPlid, rightPlid, privateLayout
			};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_LEFTPLID_2);

			query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_RIGHTPLID_2);

			query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_PRIVATELAYOUT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(leftPlid);

				qPos.add(rightPlid);

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
	 * Returns the number of layouts that the user has permission to view where groupId = &#63; and leftPlid &gt; &#63; and rightPlid &lt; &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param leftPlid the left plid
	 * @param rightPlid the right plid
	 * @param privateLayout the private layout
	 * @return the number of matching layouts that the user has permission to view
	 */
	@Override
	public int filterCountByG_P_GtLP_LtRP(long groupId, long leftPlid,
		long rightPlid, boolean privateLayout) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P_GtLP_LtRP(groupId, leftPlid, rightPlid,
				privateLayout);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_LEFTPLID_2);

		query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_RIGHTPLID_2);

		query.append(_FINDER_COLUMN_G_P_GTLP_LTRP_PRIVATELAYOUT_2);

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

			qPos.add(leftPlid);

			qPos.add(rightPlid);

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

	private static final String _FINDER_COLUMN_G_P_GTLP_LTRP_GROUPID_2 = "layout.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_GTLP_LTRP_LEFTPLID_2 = "layout.leftPlid > ? AND ";
	private static final String _FINDER_COLUMN_G_P_GTLP_LTRP_RIGHTPLID_2 = "layout.rightPlid < ? AND ";
	private static final String _FINDER_COLUMN_G_P_GTLP_LTRP_PRIVATELAYOUT_2 = "layout.privateLayout = ? AND layout.system = [$FALSE$]";
	private FinderPath _finderPathWithPaginationFindByG_P_P_LtP;
	private FinderPath _finderPathWithPaginationCountByG_P_P_LtP;

	/**
	 * Returns all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority &le; &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @return the matching layouts
	 */
	@Override
	public List<Layout> findByG_P_P_LtP(long groupId, boolean privateLayout,
		long parentLayoutId, int priority) {
		return findByG_P_P_LtP(groupId, privateLayout, parentLayoutId,
			priority, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts
	 */
	@Override
	public List<Layout> findByG_P_P_LtP(long groupId, boolean privateLayout,
		long parentLayoutId, int priority, int start, int end) {
		return findByG_P_P_LtP(groupId, privateLayout, parentLayoutId,
			priority, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByG_P_P_LtP(long groupId, boolean privateLayout,
		long parentLayoutId, int priority, int start, int end,
		OrderByComparator<Layout> orderByComparator) {
		return findByG_P_P_LtP(groupId, privateLayout, parentLayoutId,
			priority, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layouts
	 */
	@Override
	public List<Layout> findByG_P_P_LtP(long groupId, boolean privateLayout,
		long parentLayoutId, int priority, int start, int end,
		OrderByComparator<Layout> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_P_P_LtP;
		finderArgs = new Object[] {
				groupId, privateLayout, parentLayoutId, priority,
				
				start, end, orderByComparator
			};

		List<Layout> list = null;

		if (retrieveFromCache) {
			list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Layout layout : list) {
					if ((groupId != layout.getGroupId()) ||
							(privateLayout != layout.isPrivateLayout()) ||
							(parentLayoutId != layout.getParentLayoutId()) ||
							(priority < layout.getPriority())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_P_P_LTP_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_P_LTP_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_P_LTP_PARENTLAYOUTID_2);

			query.append(_FINDER_COLUMN_G_P_P_LTP_PRIORITY_2);

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

				qPos.add(priority);

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
	 * Returns the first layout in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority &le; &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByG_P_P_LtP_First(long groupId, boolean privateLayout,
		long parentLayoutId, int priority,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByG_P_P_LtP_First(groupId, privateLayout,
				parentLayoutId, priority, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", parentLayoutId=");
		msg.append(parentLayoutId);

		msg.append(", priority=");
		msg.append(priority);

		msg.append("}");

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the first layout in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority &le; &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByG_P_P_LtP_First(long groupId, boolean privateLayout,
		long parentLayoutId, int priority,
		OrderByComparator<Layout> orderByComparator) {
		List<Layout> list = findByG_P_P_LtP(groupId, privateLayout,
				parentLayoutId, priority, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority &le; &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout
	 * @throws NoSuchLayoutException if a matching layout could not be found
	 */
	@Override
	public Layout findByG_P_P_LtP_Last(long groupId, boolean privateLayout,
		long parentLayoutId, int priority,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = fetchByG_P_P_LtP_Last(groupId, privateLayout,
				parentLayoutId, priority, orderByComparator);

		if (layout != null) {
			return layout;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", parentLayoutId=");
		msg.append(parentLayoutId);

		msg.append(", priority=");
		msg.append(priority);

		msg.append("}");

		throw new NoSuchLayoutException(msg.toString());
	}

	/**
	 * Returns the last layout in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority &le; &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchByG_P_P_LtP_Last(long groupId, boolean privateLayout,
		long parentLayoutId, int priority,
		OrderByComparator<Layout> orderByComparator) {
		int count = countByG_P_P_LtP(groupId, privateLayout, parentLayoutId,
				priority);

		if (count == 0) {
			return null;
		}

		List<Layout> list = findByG_P_P_LtP(groupId, privateLayout,
				parentLayoutId, priority, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layouts before and after the current layout in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority &le; &#63;.
	 *
	 * @param plid the primary key of the current layout
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] findByG_P_P_LtP_PrevAndNext(long plid, long groupId,
		boolean privateLayout, long parentLayoutId, int priority,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		Layout layout = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Layout[] array = new LayoutImpl[3];

			array[0] = getByG_P_P_LtP_PrevAndNext(session, layout, groupId,
					privateLayout, parentLayoutId, priority, orderByComparator,
					true);

			array[1] = layout;

			array[2] = getByG_P_P_LtP_PrevAndNext(session, layout, groupId,
					privateLayout, parentLayoutId, priority, orderByComparator,
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

	protected Layout getByG_P_P_LtP_PrevAndNext(Session session, Layout layout,
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		OrderByComparator<Layout> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(7 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_G_P_P_LTP_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_P_LTP_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_P_LTP_PARENTLAYOUTID_2);

		query.append(_FINDER_COLUMN_G_P_P_LTP_PRIORITY_2);

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

		qPos.add(priority);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layout)) {
				qPos.add(orderByConditionValue);
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
	 * Returns all the layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority &le; &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @return the matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByG_P_P_LtP(long groupId,
		boolean privateLayout, long parentLayoutId, int priority) {
		return filterFindByG_P_P_LtP(groupId, privateLayout, parentLayoutId,
			priority, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByG_P_P_LtP(long groupId,
		boolean privateLayout, long parentLayoutId, int priority, int start,
		int end) {
		return filterFindByG_P_P_LtP(groupId, privateLayout, parentLayoutId,
			priority, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layouts that the user has permissions to view where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layouts that the user has permission to view
	 */
	@Override
	public List<Layout> filterFindByG_P_P_LtP(long groupId,
		boolean privateLayout, long parentLayoutId, int priority, int start,
		int end, OrderByComparator<Layout> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_P_LtP(groupId, privateLayout, parentLayoutId,
				priority, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_P_LTP_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_P_LTP_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_P_LTP_PARENTLAYOUTID_2);

		query.append(_FINDER_COLUMN_G_P_P_LTP_PRIORITY_2);

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

			qPos.add(priority);

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
	 * Returns the layouts before and after the current layout in the ordered set of layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority &le; &#63;.
	 *
	 * @param plid the primary key of the current layout
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout[] filterFindByG_P_P_LtP_PrevAndNext(long plid, long groupId,
		boolean privateLayout, long parentLayoutId, int priority,
		OrderByComparator<Layout> orderByComparator)
		throws NoSuchLayoutException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_P_LtP_PrevAndNext(plid, groupId, privateLayout,
				parentLayoutId, priority, orderByComparator);
		}

		Layout layout = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Layout[] array = new LayoutImpl[3];

			array[0] = filterGetByG_P_P_LtP_PrevAndNext(session, layout,
					groupId, privateLayout, parentLayoutId, priority,
					orderByComparator, true);

			array[1] = layout;

			array[2] = filterGetByG_P_P_LtP_PrevAndNext(session, layout,
					groupId, privateLayout, parentLayoutId, priority,
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

	protected Layout filterGetByG_P_P_LtP_PrevAndNext(Session session,
		Layout layout, long groupId, boolean privateLayout,
		long parentLayoutId, int priority,
		OrderByComparator<Layout> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(8 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_P_LTP_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_P_LTP_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_P_LTP_PARENTLAYOUTID_2);

		query.append(_FINDER_COLUMN_G_P_P_LTP_PRIORITY_2);

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

		qPos.add(priority);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layout)) {
				qPos.add(orderByConditionValue);
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
	 * Removes all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority &le; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 */
	@Override
	public void removeByG_P_P_LtP(long groupId, boolean privateLayout,
		long parentLayoutId, int priority) {
		for (Layout layout : findByG_P_P_LtP(groupId, privateLayout,
				parentLayoutId, priority, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(layout);
		}
	}

	/**
	 * Returns the number of layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority &le; &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @return the number of matching layouts
	 */
	@Override
	public int countByG_P_P_LtP(long groupId, boolean privateLayout,
		long parentLayoutId, int priority) {
		FinderPath finderPath = _finderPathWithPaginationCountByG_P_P_LtP;

		Object[] finderArgs = new Object[] {
				groupId, privateLayout, parentLayoutId, priority
			};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_LAYOUT_WHERE);

			query.append(_FINDER_COLUMN_G_P_P_LTP_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_P_LTP_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_P_LTP_PARENTLAYOUTID_2);

			query.append(_FINDER_COLUMN_G_P_P_LTP_PRIORITY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(parentLayoutId);

				qPos.add(priority);

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
	 * Returns the number of layouts that the user has permission to view where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority &le; &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @return the number of matching layouts that the user has permission to view
	 */
	@Override
	public int filterCountByG_P_P_LtP(long groupId, boolean privateLayout,
		long parentLayoutId, int priority) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P_P_LtP(groupId, privateLayout, parentLayoutId,
				priority);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_LAYOUT_WHERE);

		query.append(_FINDER_COLUMN_G_P_P_LTP_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_P_LTP_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_P_LTP_PARENTLAYOUTID_2);

		query.append(_FINDER_COLUMN_G_P_P_LTP_PRIORITY_2);

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

			qPos.add(priority);

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

	private static final String _FINDER_COLUMN_G_P_P_LTP_GROUPID_2 = "layout.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_P_LTP_PRIVATELAYOUT_2 = "layout.privateLayout = ? AND ";
	private static final String _FINDER_COLUMN_G_P_P_LTP_PARENTLAYOUTID_2 = "layout.parentLayoutId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_P_LTP_PRIORITY_2 = "layout.priority <= ? AND layout.system = [$FALSE$]";

	public LayoutPersistenceImpl() {
		setModelClass(Layout.class);

		setModelImplClass(LayoutImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(LayoutModelImpl.ENTITY_CACHE_ENABLED);
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

		FinderCacheUtil.putResult(_finderPathFetchByUUID_G_P,
			new Object[] {
				layout.getUuid(), layout.getGroupId(), layout.isPrivateLayout()
			}, layout);

		FinderCacheUtil.putResult(_finderPathFetchByIconImageId,
			new Object[] { layout.getIconImageId() }, layout);

		FinderCacheUtil.putResult(_finderPathFetchByP_I,
			new Object[] { layout.isPrivateLayout(), layout.getIconImageId() },
			layout);

		FinderCacheUtil.putResult(_finderPathFetchByG_P_L,
			new Object[] {
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId()
			}, layout);

		FinderCacheUtil.putResult(_finderPathFetchByG_P_F,
			new Object[] {
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getFriendlyURL()
			}, layout);

		FinderCacheUtil.putResult(_finderPathFetchByG_P_SPLU,
			new Object[] {
				layout.getGroupId(), layout.isPrivateLayout(),
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
	 * The {@link EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(LayoutImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Layout layout) {
		EntityCacheUtil.removeResult(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutImpl.class, layout.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LayoutModelImpl)layout, true);
	}

	@Override
	public void clearCache(List<Layout> layouts) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Layout layout : layouts) {
			EntityCacheUtil.removeResult(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutImpl.class, layout.getPrimaryKey());

			clearUniqueFindersCache((LayoutModelImpl)layout, true);
		}
	}

	protected void cacheUniqueFindersCache(LayoutModelImpl layoutModelImpl) {
		Object[] args = new Object[] {
				layoutModelImpl.getUuid(), layoutModelImpl.getGroupId(),
				layoutModelImpl.isPrivateLayout()
			};

		FinderCacheUtil.putResult(_finderPathCountByUUID_G_P, args,
			Long.valueOf(1), false);
		FinderCacheUtil.putResult(_finderPathFetchByUUID_G_P, args,
			layoutModelImpl, false);

		args = new Object[] { layoutModelImpl.getIconImageId() };

		FinderCacheUtil.putResult(_finderPathCountByIconImageId, args,
			Long.valueOf(1), false);
		FinderCacheUtil.putResult(_finderPathFetchByIconImageId, args,
			layoutModelImpl, false);

		args = new Object[] {
				layoutModelImpl.isPrivateLayout(),
				layoutModelImpl.getIconImageId()
			};

		FinderCacheUtil.putResult(_finderPathCountByP_I, args, Long.valueOf(1),
			false);
		FinderCacheUtil.putResult(_finderPathFetchByP_I, args, layoutModelImpl,
			false);

		args = new Object[] {
				layoutModelImpl.getGroupId(), layoutModelImpl.isPrivateLayout(),
				layoutModelImpl.getLayoutId()
			};

		FinderCacheUtil.putResult(_finderPathCountByG_P_L, args,
			Long.valueOf(1), false);
		FinderCacheUtil.putResult(_finderPathFetchByG_P_L, args,
			layoutModelImpl, false);

		args = new Object[] {
				layoutModelImpl.getGroupId(), layoutModelImpl.isPrivateLayout(),
				layoutModelImpl.getFriendlyURL()
			};

		FinderCacheUtil.putResult(_finderPathCountByG_P_F, args,
			Long.valueOf(1), false);
		FinderCacheUtil.putResult(_finderPathFetchByG_P_F, args,
			layoutModelImpl, false);

		args = new Object[] {
				layoutModelImpl.getGroupId(), layoutModelImpl.isPrivateLayout(),
				layoutModelImpl.getSourcePrototypeLayoutUuid()
			};

		FinderCacheUtil.putResult(_finderPathCountByG_P_SPLU, args,
			Long.valueOf(1), false);
		FinderCacheUtil.putResult(_finderPathFetchByG_P_SPLU, args,
			layoutModelImpl, false);
	}

	protected void clearUniqueFindersCache(LayoutModelImpl layoutModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					layoutModelImpl.getUuid(), layoutModelImpl.getGroupId(),
					layoutModelImpl.isPrivateLayout()
				};

			FinderCacheUtil.removeResult(_finderPathCountByUUID_G_P, args);
			FinderCacheUtil.removeResult(_finderPathFetchByUUID_G_P, args);
		}

		if ((layoutModelImpl.getColumnBitmask() &
				_finderPathFetchByUUID_G_P.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					layoutModelImpl.getOriginalUuid(),
					layoutModelImpl.getOriginalGroupId(),
					layoutModelImpl.getOriginalPrivateLayout()
				};

			FinderCacheUtil.removeResult(_finderPathCountByUUID_G_P, args);
			FinderCacheUtil.removeResult(_finderPathFetchByUUID_G_P, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] { layoutModelImpl.getIconImageId() };

			FinderCacheUtil.removeResult(_finderPathCountByIconImageId, args);
			FinderCacheUtil.removeResult(_finderPathFetchByIconImageId, args);
		}

		if ((layoutModelImpl.getColumnBitmask() &
				_finderPathFetchByIconImageId.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					layoutModelImpl.getOriginalIconImageId()
				};

			FinderCacheUtil.removeResult(_finderPathCountByIconImageId, args);
			FinderCacheUtil.removeResult(_finderPathFetchByIconImageId, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					layoutModelImpl.isPrivateLayout(),
					layoutModelImpl.getIconImageId()
				};

			FinderCacheUtil.removeResult(_finderPathCountByP_I, args);
			FinderCacheUtil.removeResult(_finderPathFetchByP_I, args);
		}

		if ((layoutModelImpl.getColumnBitmask() &
				_finderPathFetchByP_I.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					layoutModelImpl.getOriginalPrivateLayout(),
					layoutModelImpl.getOriginalIconImageId()
				};

			FinderCacheUtil.removeResult(_finderPathCountByP_I, args);
			FinderCacheUtil.removeResult(_finderPathFetchByP_I, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					layoutModelImpl.getGroupId(),
					layoutModelImpl.isPrivateLayout(),
					layoutModelImpl.getLayoutId()
				};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_L, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_P_L, args);
		}

		if ((layoutModelImpl.getColumnBitmask() &
				_finderPathFetchByG_P_L.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					layoutModelImpl.getOriginalGroupId(),
					layoutModelImpl.getOriginalPrivateLayout(),
					layoutModelImpl.getOriginalLayoutId()
				};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_L, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_P_L, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					layoutModelImpl.getGroupId(),
					layoutModelImpl.isPrivateLayout(),
					layoutModelImpl.getFriendlyURL()
				};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_F, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_P_F, args);
		}

		if ((layoutModelImpl.getColumnBitmask() &
				_finderPathFetchByG_P_F.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					layoutModelImpl.getOriginalGroupId(),
					layoutModelImpl.getOriginalPrivateLayout(),
					layoutModelImpl.getOriginalFriendlyURL()
				};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_F, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_P_F, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					layoutModelImpl.getGroupId(),
					layoutModelImpl.isPrivateLayout(),
					layoutModelImpl.getSourcePrototypeLayoutUuid()
				};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_SPLU, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_P_SPLU, args);
		}

		if ((layoutModelImpl.getColumnBitmask() &
				_finderPathFetchByG_P_SPLU.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					layoutModelImpl.getOriginalGroupId(),
					layoutModelImpl.getOriginalPrivateLayout(),
					layoutModelImpl.getOriginalSourcePrototypeLayoutUuid()
				};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_SPLU, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_P_SPLU, args);
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

		layout.setCompanyId(companyProvider.getCompanyId());

		return layout;
	}

	/**
	 * Removes the layout with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param plid the primary key of the layout
	 * @return the layout that was removed
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
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
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout remove(Serializable primaryKey) throws NoSuchLayoutException {
		Session session = null;

		try {
			session = openSession();

			Layout layout = (Layout)session.get(LayoutImpl.class, primaryKey);

			if (layout == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
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
		Session session = null;

		try {
			session = openSession();

			if (rebuildTreeEnabled) {
				if (session.isDirty()) {
					session.flush();
				}

				nestedSetsTreeManager.delete(layout);

				clearCache();

				session.clear();
			}

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
	public Layout updateImpl(Layout layout) {
		boolean isNew = layout.isNew();

		if (!(layout instanceof LayoutModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(layout.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(layout);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in layout proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Layout implementation " +
				layout.getClass());
		}

		LayoutModelImpl layoutModelImpl = (LayoutModelImpl)layout;

		if (Validator.isNull(layout.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			layout.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (layout.getCreateDate() == null)) {
			if (serviceContext == null) {
				layout.setCreateDate(now);
			}
			else {
				layout.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!layoutModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layout.setModifiedDate(now);
			}
			else {
				layout.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (rebuildTreeEnabled) {
				if (session.isDirty()) {
					session.flush();
				}

				if (isNew) {
					nestedSetsTreeManager.insert(layout,
						fetchByPrimaryKey(layout.getParentPlid()));
				}
				else if (layout.getParentPlid() != layoutModelImpl.getOriginalParentPlid()) {
					nestedSetsTreeManager.move(layout,
						fetchByPrimaryKey(
							layoutModelImpl.getOriginalParentPlid()),
						fetchByPrimaryKey(layout.getParentPlid()));
				}

				clearCache();

				session.clear();
			}

			if (layout.isNew()) {
				session.save(layout);

				layout.setNew(false);
			}
			else {
				layout = (Layout)session.merge(layout);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LayoutModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { layoutModelImpl.getUuid() };

			FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByUuid,
				args);

			args = new Object[] {
					layoutModelImpl.getUuid(), layoutModelImpl.getCompanyId()
				};

			FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByUuid_C,
				args);

			args = new Object[] { layoutModelImpl.getGroupId() };

			FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByGroupId,
				args);

			args = new Object[] { layoutModelImpl.getCompanyId() };

			FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByCompanyId,
				args);

			args = new Object[] { layoutModelImpl.getParentPlid() };

			FinderCacheUtil.removeResult(_finderPathCountByParentPlid, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByParentPlid,
				args);

			args = new Object[] { layoutModelImpl.getLayoutPrototypeUuid() };

			FinderCacheUtil.removeResult(_finderPathCountByLayoutPrototypeUuid,
				args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByLayoutPrototypeUuid,
				args);

			args = new Object[] { layoutModelImpl.getSourcePrototypeLayoutUuid() };

			FinderCacheUtil.removeResult(_finderPathCountBySourcePrototypeLayoutUuid,
				args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindBySourcePrototypeLayoutUuid,
				args);

			args = new Object[] {
					layoutModelImpl.getGroupId(),
					layoutModelImpl.isPrivateLayout()
				};

			FinderCacheUtil.removeResult(_finderPathCountByG_P, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByG_P,
				args);

			args = new Object[] {
					layoutModelImpl.getGroupId(), layoutModelImpl.getType()
				};

			FinderCacheUtil.removeResult(_finderPathCountByG_T, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByG_T,
				args);

			args = new Object[] {
					layoutModelImpl.getCompanyId(),
					layoutModelImpl.getLayoutPrototypeUuid()
				};

			FinderCacheUtil.removeResult(_finderPathCountByC_L, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByC_L,
				args);

			args = new Object[] {
					layoutModelImpl.getGroupId(),
					layoutModelImpl.isPrivateLayout(),
					layoutModelImpl.getParentLayoutId()
				};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_P, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByG_P_P,
				args);

			args = new Object[] {
					layoutModelImpl.getGroupId(),
					layoutModelImpl.isPrivateLayout(), layoutModelImpl.getType()
				};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_T, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByG_P_T,
				args);

			FinderCacheUtil.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindAll,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((layoutModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByUuid.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { layoutModelImpl.getOriginalUuid() };

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByUuid,
					args);

				args = new Object[] { layoutModelImpl.getUuid() };

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByUuid,
					args);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutModelImpl.getOriginalUuid(),
						layoutModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByUuid_C,
					args);

				args = new Object[] {
						layoutModelImpl.getUuid(),
						layoutModelImpl.getCompanyId()
					};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByUuid_C,
					args);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByGroupId.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutModelImpl.getOriginalGroupId()
					};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByGroupId,
					args);

				args = new Object[] { layoutModelImpl.getGroupId() };

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByGroupId,
					args);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByCompanyId.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByCompanyId,
					args);

				args = new Object[] { layoutModelImpl.getCompanyId() };

				FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByCompanyId,
					args);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByParentPlid.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutModelImpl.getOriginalParentPlid()
					};

				FinderCacheUtil.removeResult(_finderPathCountByParentPlid, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByParentPlid,
					args);

				args = new Object[] { layoutModelImpl.getParentPlid() };

				FinderCacheUtil.removeResult(_finderPathCountByParentPlid, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByParentPlid,
					args);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByLayoutPrototypeUuid.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutModelImpl.getOriginalLayoutPrototypeUuid()
					};

				FinderCacheUtil.removeResult(_finderPathCountByLayoutPrototypeUuid,
					args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByLayoutPrototypeUuid,
					args);

				args = new Object[] { layoutModelImpl.getLayoutPrototypeUuid() };

				FinderCacheUtil.removeResult(_finderPathCountByLayoutPrototypeUuid,
					args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByLayoutPrototypeUuid,
					args);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindBySourcePrototypeLayoutUuid.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutModelImpl.getOriginalSourcePrototypeLayoutUuid()
					};

				FinderCacheUtil.removeResult(_finderPathCountBySourcePrototypeLayoutUuid,
					args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindBySourcePrototypeLayoutUuid,
					args);

				args = new Object[] {
						layoutModelImpl.getSourcePrototypeLayoutUuid()
					};

				FinderCacheUtil.removeResult(_finderPathCountBySourcePrototypeLayoutUuid,
					args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindBySourcePrototypeLayoutUuid,
					args);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByG_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutModelImpl.getOriginalGroupId(),
						layoutModelImpl.getOriginalPrivateLayout()
					};

				FinderCacheUtil.removeResult(_finderPathCountByG_P, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByG_P,
					args);

				args = new Object[] {
						layoutModelImpl.getGroupId(),
						layoutModelImpl.isPrivateLayout()
					};

				FinderCacheUtil.removeResult(_finderPathCountByG_P, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByG_P,
					args);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByG_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutModelImpl.getOriginalGroupId(),
						layoutModelImpl.getOriginalType()
					};

				FinderCacheUtil.removeResult(_finderPathCountByG_T, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByG_T,
					args);

				args = new Object[] {
						layoutModelImpl.getGroupId(), layoutModelImpl.getType()
					};

				FinderCacheUtil.removeResult(_finderPathCountByG_T, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByG_T,
					args);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByC_L.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutModelImpl.getOriginalCompanyId(),
						layoutModelImpl.getOriginalLayoutPrototypeUuid()
					};

				FinderCacheUtil.removeResult(_finderPathCountByC_L, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByC_L,
					args);

				args = new Object[] {
						layoutModelImpl.getCompanyId(),
						layoutModelImpl.getLayoutPrototypeUuid()
					};

				FinderCacheUtil.removeResult(_finderPathCountByC_L, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByC_L,
					args);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByG_P_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutModelImpl.getOriginalGroupId(),
						layoutModelImpl.getOriginalPrivateLayout(),
						layoutModelImpl.getOriginalParentLayoutId()
					};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_P, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByG_P_P,
					args);

				args = new Object[] {
						layoutModelImpl.getGroupId(),
						layoutModelImpl.isPrivateLayout(),
						layoutModelImpl.getParentLayoutId()
					};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_P, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByG_P_P,
					args);
			}

			if ((layoutModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByG_P_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutModelImpl.getOriginalGroupId(),
						layoutModelImpl.getOriginalPrivateLayout(),
						layoutModelImpl.getOriginalType()
					};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_T, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByG_P_T,
					args);

				args = new Object[] {
						layoutModelImpl.getGroupId(),
						layoutModelImpl.isPrivateLayout(),
						layoutModelImpl.getType()
					};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_T, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByG_P_T,
					args);
			}
		}

		EntityCacheUtil.putResult(LayoutModelImpl.ENTITY_CACHE_ENABLED,
			LayoutImpl.class, layout.getPrimaryKey(), layout, false);

		clearUniqueFindersCache(layoutModelImpl, false);
		cacheUniqueFindersCache(layoutModelImpl);

		layout.resetOriginalValues();

		return layout;
	}

	/**
	 * Returns the layout with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout
	 * @return the layout
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLayoutException {
		Layout layout = fetchByPrimaryKey(primaryKey);

		if (layout == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLayoutException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return layout;
	}

	/**
	 * Returns the layout with the primary key or throws a {@link NoSuchLayoutException} if it could not be found.
	 *
	 * @param plid the primary key of the layout
	 * @return the layout
	 * @throws NoSuchLayoutException if a layout with the primary key could not be found
	 */
	@Override
	public Layout findByPrimaryKey(long plid) throws NoSuchLayoutException {
		return findByPrimaryKey((Serializable)plid);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layouts
	 */
	@Override
	public List<Layout> findAll(int start, int end,
		OrderByComparator<Layout> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of layouts
	 */
	@Override
	public List<Layout> findAll(int start, int end,
		OrderByComparator<Layout> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Layout> list = null;

		if (retrieveFromCache) {
			list = (List<Layout>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

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
		Long count = (Long)FinderCacheUtil.getResult(_finderPathCountAll,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUT);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(_finderPathCountAll,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathCountAll,
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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "plid";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAYOUT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LayoutModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public long countAncestors(Layout layout) {
		Object[] finderArgs = new Object[] {
				layout.getGroupId(), layout.getLeftPlid(), layout.getRightPlid()
			};

		Long count = (Long)FinderCacheUtil.getResult(_finderPathWithPaginationCountAncestors,
				finderArgs, this);

		if (count == null) {
			try {
				count = nestedSetsTreeManager.countAncestors(layout);

				FinderCacheUtil.putResult(_finderPathWithPaginationCountAncestors,
					finderArgs, count);
			}
			catch (SystemException se) {
				FinderCacheUtil.removeResult(_finderPathWithPaginationCountAncestors,
					finderArgs);

				throw se;
			}
		}

		return count.intValue();
	}

	@Override
	public long countDescendants(Layout layout) {
		Object[] finderArgs = new Object[] {
				layout.getGroupId(), layout.getLeftPlid(), layout.getRightPlid()
			};

		Long count = (Long)FinderCacheUtil.getResult(_finderPathWithPaginationCountDescendants,
				finderArgs, this);

		if (count == null) {
			try {
				count = nestedSetsTreeManager.countDescendants(layout);

				FinderCacheUtil.putResult(_finderPathWithPaginationCountDescendants,
					finderArgs, count);
			}
			catch (SystemException se) {
				FinderCacheUtil.removeResult(_finderPathWithPaginationCountDescendants,
					finderArgs);

				throw se;
			}
		}

		return count.intValue();
	}

	@Override
	public List<Layout> getAncestors(Layout layout) {
		Object[] finderArgs = new Object[] {
				layout.getGroupId(), layout.getLeftPlid(), layout.getRightPlid()
			};

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(_finderPathWithPaginationGetAncestors,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Layout tempLayout : list) {
				if ((layout.getLeftPlid() < tempLayout.getLeftPlid()) ||
						(layout.getRightPlid() > tempLayout.getRightPlid())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			try {
				list = nestedSetsTreeManager.getAncestors(layout);

				cacheResult(list);

				FinderCacheUtil.putResult(_finderPathWithPaginationGetAncestors,
					finderArgs, list);
			}
			catch (SystemException se) {
				FinderCacheUtil.removeResult(_finderPathWithPaginationGetAncestors,
					finderArgs);

				throw se;
			}
		}

		return list;
	}

	@Override
	public List<Layout> getDescendants(Layout layout) {
		Object[] finderArgs = new Object[] {
				layout.getGroupId(), layout.getLeftPlid(), layout.getRightPlid()
			};

		List<Layout> list = (List<Layout>)FinderCacheUtil.getResult(_finderPathWithPaginationGetDescendants,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Layout tempLayout : list) {
				if ((layout.getLeftPlid() > tempLayout.getLeftPlid()) ||
						(layout.getRightPlid() < tempLayout.getRightPlid())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			try {
				list = nestedSetsTreeManager.getDescendants(layout);

				cacheResult(list);

				FinderCacheUtil.putResult(_finderPathWithPaginationGetDescendants,
					finderArgs, list);
			}
			catch (SystemException se) {
				FinderCacheUtil.removeResult(_finderPathWithPaginationGetDescendants,
					finderArgs);

				throw se;
			}
		}

		return list;
	}

	/**
	 * Rebuilds the layouts tree for the scope using the modified pre-order tree traversal algorithm.
	 *
	 * <p>
	 * Only call this method if the tree has become stale through operations other than normal CRUD. Under normal circumstances the tree is automatically rebuilt whenver necessary.
	 * </p>
	 *
	 * @param groupId the ID of the scope
	 * @param force whether to force the rebuild even if the tree is not stale
	 */
	@Override
	public void rebuildTree(long groupId, boolean force) {
		if (!rebuildTreeEnabled) {
			return;
		}

		if (force || (countOrphanTreeNodes(groupId) > 0)) {
			Session session = null;

			try {
				session = openSession();

				if (session.isDirty()) {
					session.flush();
				}

				SQLQuery selectQuery = session.createSQLQuery(
						"SELECT plid FROM Layout WHERE groupId = ? AND parentPlid = ? ORDER BY plid ASC");

				selectQuery.addScalar("plid",
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				SQLQuery updateQuery = session.createSQLQuery(
						"UPDATE Layout SET leftPlid = ?, rightPlid = ? WHERE plid = ?");

				rebuildTree(session, selectQuery, updateQuery, groupId, 0, 0);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}

			clearCache();
		}
	}

	@Override
	public void setRebuildTreeEnabled(boolean rebuildTreeEnabled) {
		this.rebuildTreeEnabled = rebuildTreeEnabled;
	}

	protected long countOrphanTreeNodes(long groupId) {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(
					"SELECT COUNT(*) AS COUNT_VALUE FROM Layout WHERE groupId = ? AND (leftPlid = 0 OR leftPlid IS NULL OR rightPlid = 0 OR rightPlid IS NULL)");

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (Long)q.uniqueResult();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected long rebuildTree(Session session, SQLQuery selectQuery,
		SQLQuery updateQuery, long groupId, long parentPlid, long leftPlid) {
		long rightPlid = leftPlid + 1;

		QueryPos qPos = QueryPos.getInstance(selectQuery);

		qPos.add(groupId);
		qPos.add(parentPlid);

		List<Long> plids = selectQuery.list();

		for (long plid : plids) {
			rightPlid = rebuildTree(session, selectQuery, updateQuery, groupId,
					plid, rightPlid);
		}

		if (parentPlid > 0) {
			qPos = QueryPos.getInstance(updateQuery);

			qPos.add(leftPlid);
			qPos.add(rightPlid);
			qPos.add(parentPlid);

			updateQuery.executeUpdate();
		}

		return rightPlid + 1;
	}

	/**
	 * Initializes the layout persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
				new String[0]);

		_finderPathCountAll = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
				new String[0]);

		_finderPathWithPaginationCountAncestors = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countAncestors",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName()
				});

		_finderPathWithPaginationCountDescendants = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countDescendants",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName()
				});

		_finderPathWithPaginationGetAncestors = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "getAncestors",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName()
				});

		_finderPathWithPaginationGetDescendants = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "getDescendants",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName()
				});

		_finderPathWithPaginationFindByUuid = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
				new String[] {
					String.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
				new String[] { String.class.getName() },
				LayoutModelImpl.UUID_COLUMN_BITMASK |
				LayoutModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
				LayoutModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
				new String[] { String.class.getName() });

		_finderPathFetchByUUID_G_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G_P",
				new String[] {
					String.class.getName(), Long.class.getName(),
					Boolean.class.getName()
				},
				LayoutModelImpl.UUID_COLUMN_BITMASK |
				LayoutModelImpl.GROUPID_COLUMN_BITMASK |
				LayoutModelImpl.PRIVATELAYOUT_COLUMN_BITMASK);

		_finderPathCountByUUID_G_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G_P",
				new String[] {
					String.class.getName(), Long.class.getName(),
					Boolean.class.getName()
				});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
				new String[] {
					String.class.getName(), Long.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
				new String[] { String.class.getName(), Long.class.getName() },
				LayoutModelImpl.UUID_COLUMN_BITMASK |
				LayoutModelImpl.COMPANYID_COLUMN_BITMASK |
				LayoutModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
				LayoutModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
				new String[] { String.class.getName(), Long.class.getName() });

		_finderPathWithPaginationFindByGroupId = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
				new String[] {
					Long.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
				new String[] { Long.class.getName() },
				LayoutModelImpl.GROUPID_COLUMN_BITMASK |
				LayoutModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
				LayoutModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
				new String[] { Long.class.getName() });

		_finderPathWithPaginationFindByCompanyId = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
				new String[] {
					Long.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
				new String[] { Long.class.getName() },
				LayoutModelImpl.COMPANYID_COLUMN_BITMASK |
				LayoutModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
				LayoutModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
				new String[] { Long.class.getName() });

		_finderPathWithPaginationFindByParentPlid = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByParentPlid",
				new String[] {
					Long.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByParentPlid = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByParentPlid",
				new String[] { Long.class.getName() },
				LayoutModelImpl.PARENTPLID_COLUMN_BITMASK |
				LayoutModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
				LayoutModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountByParentPlid = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByParentPlid",
				new String[] { Long.class.getName() });

		_finderPathFetchByIconImageId = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_ENTITY, "fetchByIconImageId",
				new String[] { Long.class.getName() },
				LayoutModelImpl.ICONIMAGEID_COLUMN_BITMASK);

		_finderPathCountByIconImageId = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"countByIconImageId", new String[] { Long.class.getName() });

		_finderPathWithPaginationFindByLayoutPrototypeUuid = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByLayoutPrototypeUuid",
				new String[] {
					String.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByLayoutPrototypeUuid = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByLayoutPrototypeUuid",
				new String[] { String.class.getName() },
				LayoutModelImpl.LAYOUTPROTOTYPEUUID_COLUMN_BITMASK |
				LayoutModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
				LayoutModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountByLayoutPrototypeUuid = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"countByLayoutPrototypeUuid",
				new String[] { String.class.getName() });

		_finderPathWithPaginationFindBySourcePrototypeLayoutUuid = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findBySourcePrototypeLayoutUuid",
				new String[] {
					String.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindBySourcePrototypeLayoutUuid = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findBySourcePrototypeLayoutUuid",
				new String[] { String.class.getName() },
				LayoutModelImpl.SOURCEPROTOTYPELAYOUTUUID_COLUMN_BITMASK |
				LayoutModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
				LayoutModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountBySourcePrototypeLayoutUuid = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"countBySourcePrototypeLayoutUuid",
				new String[] { String.class.getName() });

		_finderPathWithPaginationFindByG_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByG_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P",
				new String[] { Long.class.getName(), Boolean.class.getName() },
				LayoutModelImpl.GROUPID_COLUMN_BITMASK |
				LayoutModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
				LayoutModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
				LayoutModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountByG_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P",
				new String[] { Long.class.getName(), Boolean.class.getName() });

		_finderPathWithPaginationFindByG_T = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T",
				new String[] {
					Long.class.getName(), String.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByG_T = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T",
				new String[] { Long.class.getName(), String.class.getName() },
				LayoutModelImpl.GROUPID_COLUMN_BITMASK |
				LayoutModelImpl.TYPE_COLUMN_BITMASK |
				LayoutModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
				LayoutModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountByG_T = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T",
				new String[] { Long.class.getName(), String.class.getName() });

		_finderPathWithPaginationFindByC_L = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_L",
				new String[] {
					Long.class.getName(), String.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByC_L = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_L",
				new String[] { Long.class.getName(), String.class.getName() },
				LayoutModelImpl.COMPANYID_COLUMN_BITMASK |
				LayoutModelImpl.LAYOUTPROTOTYPEUUID_COLUMN_BITMASK |
				LayoutModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
				LayoutModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountByC_L = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_L",
				new String[] { Long.class.getName(), String.class.getName() });

		_finderPathFetchByP_I = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_ENTITY, "fetchByP_I",
				new String[] { Boolean.class.getName(), Long.class.getName() },
				LayoutModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
				LayoutModelImpl.ICONIMAGEID_COLUMN_BITMASK);

		_finderPathCountByP_I = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_I",
				new String[] { Boolean.class.getName(), Long.class.getName() });

		_finderPathFetchByG_P_L = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_ENTITY, "fetchByG_P_L",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					Long.class.getName()
				},
				LayoutModelImpl.GROUPID_COLUMN_BITMASK |
				LayoutModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
				LayoutModelImpl.LAYOUTID_COLUMN_BITMASK);

		_finderPathCountByG_P_L = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_L",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					Long.class.getName()
				});

		_finderPathWithPaginationFindByG_P_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_P",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					Long.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByG_P_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
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

		_finderPathCountByG_P_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_P",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					Long.class.getName()
				});

		_finderPathWithPaginationCountByG_P_P = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_P_P",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					Long.class.getName()
				});

		_finderPathWithPaginationFindByG_P_T = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_T",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					String.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByG_P_T = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
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

		_finderPathCountByG_P_T = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_T",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					String.class.getName()
				});

		_finderPathFetchByG_P_F = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_ENTITY, "fetchByG_P_F",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					String.class.getName()
				},
				LayoutModelImpl.GROUPID_COLUMN_BITMASK |
				LayoutModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
				LayoutModelImpl.FRIENDLYURL_COLUMN_BITMASK);

		_finderPathCountByG_P_F = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_F",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					String.class.getName()
				});

		_finderPathFetchByG_P_SPLU = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_ENTITY, "fetchByG_P_SPLU",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					String.class.getName()
				},
				LayoutModelImpl.GROUPID_COLUMN_BITMASK |
				LayoutModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
				LayoutModelImpl.SOURCEPROTOTYPELAYOUTUUID_COLUMN_BITMASK);

		_finderPathCountByG_P_SPLU = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_SPLU",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					String.class.getName()
				});

		_finderPathWithPaginationFindByG_P_GtLP_LtRP = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_GtLP_LtRP",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName(), Boolean.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithPaginationCountByG_P_GtLP_LtRP = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_P_GtLP_LtRP",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName(), Boolean.class.getName()
				});

		_finderPathWithPaginationFindByG_P_P_LtP = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, LayoutImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_P_LtP",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					Long.class.getName(), Integer.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithPaginationCountByG_P_P_LtP = new FinderPath(LayoutModelImpl.ENTITY_CACHE_ENABLED,
				LayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_P_P_LtP",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					Long.class.getName(), Integer.class.getName()
				});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(LayoutImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	protected NestedSetsTreeManager<Layout> nestedSetsTreeManager = new PersistenceNestedSetsTreeManager<Layout>(this,
			"Layout", "Layout", LayoutImpl.class, "plid", "groupId",
			"leftPlid", "rightPlid");
	protected boolean rebuildTreeEnabled = true;
	private static final String _SQL_SELECT_LAYOUT = "SELECT layout FROM Layout layout";
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
	private static final Log _log = LogFactoryUtil.getLog(LayoutPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "type", "hidden"
			});
}