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

package com.liferay.data.engine.service.persistence.impl;

import com.liferay.data.engine.exception.NoSuchListViewException;
import com.liferay.data.engine.model.DEListView;
import com.liferay.data.engine.model.impl.DEListViewImpl;
import com.liferay.data.engine.model.impl.DEListViewModelImpl;
import com.liferay.data.engine.service.persistence.DEListViewPersistence;
import com.liferay.data.engine.service.persistence.impl.constants.DEPersistenceConstants;
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

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the de list view service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = DEListViewPersistence.class)
@ProviderType
public class DEListViewPersistenceImpl
	extends BasePersistenceImpl<DEListView> implements DEListViewPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DEListViewUtil</code> to access the de list view persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DEListViewImpl.class.getName();

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
	 * Returns all the de list views where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching de list views
	 */
	@Override
	public List<DEListView> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the de list views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @return the range of matching de list views
	 */
	@Override
	public List<DEListView> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the de list views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de list views
	 */
	@Override
	public List<DEListView> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DEListView> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the de list views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching de list views
	 */
	@Override
	public List<DEListView> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DEListView> orderByComparator,
		boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUuid;
			finderArgs = new Object[] {uuid};
		}
		else {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<DEListView> list = null;

		if (retrieveFromCache) {
			list = (List<DEListView>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DEListView deListView : list) {
					if (!uuid.equals(deListView.getUuid())) {
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

			query.append(_SQL_SELECT_DELISTVIEW_WHERE);

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
				query.append(DEListViewModelImpl.ORDER_BY_JPQL);
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
					list = (List<DEListView>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DEListView>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first de list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de list view
	 * @throws NoSuchListViewException if a matching de list view could not be found
	 */
	@Override
	public DEListView findByUuid_First(
			String uuid, OrderByComparator<DEListView> orderByComparator)
		throws NoSuchListViewException {

		DEListView deListView = fetchByUuid_First(uuid, orderByComparator);

		if (deListView != null) {
			return deListView;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchListViewException(msg.toString());
	}

	/**
	 * Returns the first de list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	@Override
	public DEListView fetchByUuid_First(
		String uuid, OrderByComparator<DEListView> orderByComparator) {

		List<DEListView> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last de list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de list view
	 * @throws NoSuchListViewException if a matching de list view could not be found
	 */
	@Override
	public DEListView findByUuid_Last(
			String uuid, OrderByComparator<DEListView> orderByComparator)
		throws NoSuchListViewException {

		DEListView deListView = fetchByUuid_Last(uuid, orderByComparator);

		if (deListView != null) {
			return deListView;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchListViewException(msg.toString());
	}

	/**
	 * Returns the last de list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	@Override
	public DEListView fetchByUuid_Last(
		String uuid, OrderByComparator<DEListView> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DEListView> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the de list views before and after the current de list view in the ordered set where uuid = &#63;.
	 *
	 * @param listViewId the primary key of the current de list view
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de list view
	 * @throws NoSuchListViewException if a de list view with the primary key could not be found
	 */
	@Override
	public DEListView[] findByUuid_PrevAndNext(
			long listViewId, String uuid,
			OrderByComparator<DEListView> orderByComparator)
		throws NoSuchListViewException {

		uuid = Objects.toString(uuid, "");

		DEListView deListView = findByPrimaryKey(listViewId);

		Session session = null;

		try {
			session = openSession();

			DEListView[] array = new DEListViewImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, deListView, uuid, orderByComparator, true);

			array[1] = deListView;

			array[2] = getByUuid_PrevAndNext(
				session, deListView, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DEListView getByUuid_PrevAndNext(
		Session session, DEListView deListView, String uuid,
		OrderByComparator<DEListView> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DELISTVIEW_WHERE);

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
			query.append(DEListViewModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(deListView)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DEListView> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the de list views where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DEListView deListView :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(deListView);
		}
	}

	/**
	 * Returns the number of de list views where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching de list views
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DELISTVIEW_WHERE);

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
		"deListView.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(deListView.uuid IS NULL OR deListView.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the de list view where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchListViewException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching de list view
	 * @throws NoSuchListViewException if a matching de list view could not be found
	 */
	@Override
	public DEListView findByUUID_G(String uuid, long groupId)
		throws NoSuchListViewException {

		DEListView deListView = fetchByUUID_G(uuid, groupId);

		if (deListView == null) {
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

			throw new NoSuchListViewException(msg.toString());
		}

		return deListView;
	}

	/**
	 * Returns the de list view where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	@Override
	public DEListView fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the de list view where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	@Override
	public DEListView fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = new Object[] {uuid, groupId};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof DEListView) {
			DEListView deListView = (DEListView)result;

			if (!Objects.equals(uuid, deListView.getUuid()) ||
				(groupId != deListView.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DELISTVIEW_WHERE);

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

				List<DEListView> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(
						_finderPathFetchByUUID_G, finderArgs, list);
				}
				else {
					DEListView deListView = list.get(0);

					result = deListView;

					cacheResult(deListView);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathFetchByUUID_G, finderArgs);

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
			return (DEListView)result;
		}
	}

	/**
	 * Removes the de list view where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the de list view that was removed
	 */
	@Override
	public DEListView removeByUUID_G(String uuid, long groupId)
		throws NoSuchListViewException {

		DEListView deListView = findByUUID_G(uuid, groupId);

		return remove(deListView);
	}

	/**
	 * Returns the number of de list views where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching de list views
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DELISTVIEW_WHERE);

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
		"deListView.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(deListView.uuid IS NULL OR deListView.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"deListView.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the de list views where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching de list views
	 */
	@Override
	public List<DEListView> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the de list views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @return the range of matching de list views
	 */
	@Override
	public List<DEListView> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the de list views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de list views
	 */
	@Override
	public List<DEListView> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DEListView> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the de list views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching de list views
	 */
	@Override
	public List<DEListView> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DEListView> orderByComparator,
		boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUuid_C;
			finderArgs = new Object[] {uuid, companyId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<DEListView> list = null;

		if (retrieveFromCache) {
			list = (List<DEListView>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DEListView deListView : list) {
					if (!uuid.equals(deListView.getUuid()) ||
						(companyId != deListView.getCompanyId())) {

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

			query.append(_SQL_SELECT_DELISTVIEW_WHERE);

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
				query.append(DEListViewModelImpl.ORDER_BY_JPQL);
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
					list = (List<DEListView>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DEListView>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first de list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de list view
	 * @throws NoSuchListViewException if a matching de list view could not be found
	 */
	@Override
	public DEListView findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DEListView> orderByComparator)
		throws NoSuchListViewException {

		DEListView deListView = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (deListView != null) {
			return deListView;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchListViewException(msg.toString());
	}

	/**
	 * Returns the first de list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	@Override
	public DEListView fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DEListView> orderByComparator) {

		List<DEListView> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last de list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de list view
	 * @throws NoSuchListViewException if a matching de list view could not be found
	 */
	@Override
	public DEListView findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DEListView> orderByComparator)
		throws NoSuchListViewException {

		DEListView deListView = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (deListView != null) {
			return deListView;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchListViewException(msg.toString());
	}

	/**
	 * Returns the last de list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	@Override
	public DEListView fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DEListView> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DEListView> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the de list views before and after the current de list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param listViewId the primary key of the current de list view
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de list view
	 * @throws NoSuchListViewException if a de list view with the primary key could not be found
	 */
	@Override
	public DEListView[] findByUuid_C_PrevAndNext(
			long listViewId, String uuid, long companyId,
			OrderByComparator<DEListView> orderByComparator)
		throws NoSuchListViewException {

		uuid = Objects.toString(uuid, "");

		DEListView deListView = findByPrimaryKey(listViewId);

		Session session = null;

		try {
			session = openSession();

			DEListView[] array = new DEListViewImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, deListView, uuid, companyId, orderByComparator, true);

			array[1] = deListView;

			array[2] = getByUuid_C_PrevAndNext(
				session, deListView, uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DEListView getByUuid_C_PrevAndNext(
		Session session, DEListView deListView, String uuid, long companyId,
		OrderByComparator<DEListView> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DELISTVIEW_WHERE);

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
			query.append(DEListViewModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(deListView)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DEListView> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the de list views where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DEListView deListView :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(deListView);
		}
	}

	/**
	 * Returns the number of de list views where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching de list views
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DELISTVIEW_WHERE);

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
		"deListView.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(deListView.uuid IS NULL OR deListView.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"deListView.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_D;
	private FinderPath _finderPathWithoutPaginationFindByG_C_D;
	private FinderPath _finderPathCountByG_C_D;

	/**
	 * Returns all the de list views where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @return the matching de list views
	 */
	@Override
	public List<DEListView> findByG_C_D(
		long groupId, long companyId, long DDMStructureId) {

		return findByG_C_D(
			groupId, companyId, DDMStructureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the de list views where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @return the range of matching de list views
	 */
	@Override
	public List<DEListView> findByG_C_D(
		long groupId, long companyId, long DDMStructureId, int start, int end) {

		return findByG_C_D(
			groupId, companyId, DDMStructureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the de list views where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de list views
	 */
	@Override
	public List<DEListView> findByG_C_D(
		long groupId, long companyId, long DDMStructureId, int start, int end,
		OrderByComparator<DEListView> orderByComparator) {

		return findByG_C_D(
			groupId, companyId, DDMStructureId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the de list views where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching de list views
	 */
	@Override
	public List<DEListView> findByG_C_D(
		long groupId, long companyId, long DDMStructureId, int start, int end,
		OrderByComparator<DEListView> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_C_D;
			finderArgs = new Object[] {groupId, companyId, DDMStructureId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_C_D;
			finderArgs = new Object[] {
				groupId, companyId, DDMStructureId, start, end,
				orderByComparator
			};
		}

		List<DEListView> list = null;

		if (retrieveFromCache) {
			list = (List<DEListView>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DEListView deListView : list) {
					if ((groupId != deListView.getGroupId()) ||
						(companyId != deListView.getCompanyId()) ||
						(DDMStructureId != deListView.getDDMStructureId())) {

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

			query.append(_SQL_SELECT_DELISTVIEW_WHERE);

			query.append(_FINDER_COLUMN_G_C_D_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_D_COMPANYID_2);

			query.append(_FINDER_COLUMN_G_C_D_DDMSTRUCTUREID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(DEListViewModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(companyId);

				qPos.add(DDMStructureId);

				if (!pagination) {
					list = (List<DEListView>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DEListView>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first de list view in the ordered set where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de list view
	 * @throws NoSuchListViewException if a matching de list view could not be found
	 */
	@Override
	public DEListView findByG_C_D_First(
			long groupId, long companyId, long DDMStructureId,
			OrderByComparator<DEListView> orderByComparator)
		throws NoSuchListViewException {

		DEListView deListView = fetchByG_C_D_First(
			groupId, companyId, DDMStructureId, orderByComparator);

		if (deListView != null) {
			return deListView;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(", DDMStructureId=");
		msg.append(DDMStructureId);

		msg.append("}");

		throw new NoSuchListViewException(msg.toString());
	}

	/**
	 * Returns the first de list view in the ordered set where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	@Override
	public DEListView fetchByG_C_D_First(
		long groupId, long companyId, long DDMStructureId,
		OrderByComparator<DEListView> orderByComparator) {

		List<DEListView> list = findByG_C_D(
			groupId, companyId, DDMStructureId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last de list view in the ordered set where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de list view
	 * @throws NoSuchListViewException if a matching de list view could not be found
	 */
	@Override
	public DEListView findByG_C_D_Last(
			long groupId, long companyId, long DDMStructureId,
			OrderByComparator<DEListView> orderByComparator)
		throws NoSuchListViewException {

		DEListView deListView = fetchByG_C_D_Last(
			groupId, companyId, DDMStructureId, orderByComparator);

		if (deListView != null) {
			return deListView;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(", DDMStructureId=");
		msg.append(DDMStructureId);

		msg.append("}");

		throw new NoSuchListViewException(msg.toString());
	}

	/**
	 * Returns the last de list view in the ordered set where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	@Override
	public DEListView fetchByG_C_D_Last(
		long groupId, long companyId, long DDMStructureId,
		OrderByComparator<DEListView> orderByComparator) {

		int count = countByG_C_D(groupId, companyId, DDMStructureId);

		if (count == 0) {
			return null;
		}

		List<DEListView> list = findByG_C_D(
			groupId, companyId, DDMStructureId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the de list views before and after the current de list view in the ordered set where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param listViewId the primary key of the current de list view
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de list view
	 * @throws NoSuchListViewException if a de list view with the primary key could not be found
	 */
	@Override
	public DEListView[] findByG_C_D_PrevAndNext(
			long listViewId, long groupId, long companyId, long DDMStructureId,
			OrderByComparator<DEListView> orderByComparator)
		throws NoSuchListViewException {

		DEListView deListView = findByPrimaryKey(listViewId);

		Session session = null;

		try {
			session = openSession();

			DEListView[] array = new DEListViewImpl[3];

			array[0] = getByG_C_D_PrevAndNext(
				session, deListView, groupId, companyId, DDMStructureId,
				orderByComparator, true);

			array[1] = deListView;

			array[2] = getByG_C_D_PrevAndNext(
				session, deListView, groupId, companyId, DDMStructureId,
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

	protected DEListView getByG_C_D_PrevAndNext(
		Session session, DEListView deListView, long groupId, long companyId,
		long DDMStructureId, OrderByComparator<DEListView> orderByComparator,
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

		query.append(_SQL_SELECT_DELISTVIEW_WHERE);

		query.append(_FINDER_COLUMN_G_C_D_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_D_COMPANYID_2);

		query.append(_FINDER_COLUMN_G_C_D_DDMSTRUCTUREID_2);

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
			query.append(DEListViewModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(companyId);

		qPos.add(DDMStructureId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(deListView)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DEListView> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the de list views where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 */
	@Override
	public void removeByG_C_D(
		long groupId, long companyId, long DDMStructureId) {

		for (DEListView deListView :
				findByG_C_D(
					groupId, companyId, DDMStructureId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(deListView);
		}
	}

	/**
	 * Returns the number of de list views where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @return the number of matching de list views
	 */
	@Override
	public int countByG_C_D(long groupId, long companyId, long DDMStructureId) {
		FinderPath finderPath = _finderPathCountByG_C_D;

		Object[] finderArgs = new Object[] {groupId, companyId, DDMStructureId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DELISTVIEW_WHERE);

			query.append(_FINDER_COLUMN_G_C_D_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_D_COMPANYID_2);

			query.append(_FINDER_COLUMN_G_C_D_DDMSTRUCTUREID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(companyId);

				qPos.add(DDMStructureId);

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

	private static final String _FINDER_COLUMN_G_C_D_GROUPID_2 =
		"deListView.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_D_COMPANYID_2 =
		"deListView.companyId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_D_DDMSTRUCTUREID_2 =
		"deListView.DDMStructureId = ?";

	public DEListViewPersistenceImpl() {
		setModelClass(DEListView.class);

		setModelImplClass(DEListViewImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the de list view in the entity cache if it is enabled.
	 *
	 * @param deListView the de list view
	 */
	@Override
	public void cacheResult(DEListView deListView) {
		entityCache.putResult(
			entityCacheEnabled, DEListViewImpl.class,
			deListView.getPrimaryKey(), deListView);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {deListView.getUuid(), deListView.getGroupId()},
			deListView);

		deListView.resetOriginalValues();
	}

	/**
	 * Caches the de list views in the entity cache if it is enabled.
	 *
	 * @param deListViews the de list views
	 */
	@Override
	public void cacheResult(List<DEListView> deListViews) {
		for (DEListView deListView : deListViews) {
			if (entityCache.getResult(
					entityCacheEnabled, DEListViewImpl.class,
					deListView.getPrimaryKey()) == null) {

				cacheResult(deListView);
			}
			else {
				deListView.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all de list views.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DEListViewImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the de list view.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DEListView deListView) {
		entityCache.removeResult(
			entityCacheEnabled, DEListViewImpl.class,
			deListView.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DEListViewModelImpl)deListView, true);
	}

	@Override
	public void clearCache(List<DEListView> deListViews) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DEListView deListView : deListViews) {
			entityCache.removeResult(
				entityCacheEnabled, DEListViewImpl.class,
				deListView.getPrimaryKey());

			clearUniqueFindersCache((DEListViewModelImpl)deListView, true);
		}
	}

	protected void cacheUniqueFindersCache(
		DEListViewModelImpl deListViewModelImpl) {

		Object[] args = new Object[] {
			deListViewModelImpl.getUuid(), deListViewModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, deListViewModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		DEListViewModelImpl deListViewModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				deListViewModelImpl.getUuid(), deListViewModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((deListViewModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				deListViewModelImpl.getOriginalUuid(),
				deListViewModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}
	}

	/**
	 * Creates a new de list view with the primary key. Does not add the de list view to the database.
	 *
	 * @param listViewId the primary key for the new de list view
	 * @return the new de list view
	 */
	@Override
	public DEListView create(long listViewId) {
		DEListView deListView = new DEListViewImpl();

		deListView.setNew(true);
		deListView.setPrimaryKey(listViewId);

		String uuid = PortalUUIDUtil.generate();

		deListView.setUuid(uuid);

		deListView.setCompanyId(CompanyThreadLocal.getCompanyId());

		return deListView;
	}

	/**
	 * Removes the de list view with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param listViewId the primary key of the de list view
	 * @return the de list view that was removed
	 * @throws NoSuchListViewException if a de list view with the primary key could not be found
	 */
	@Override
	public DEListView remove(long listViewId) throws NoSuchListViewException {
		return remove((Serializable)listViewId);
	}

	/**
	 * Removes the de list view with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the de list view
	 * @return the de list view that was removed
	 * @throws NoSuchListViewException if a de list view with the primary key could not be found
	 */
	@Override
	public DEListView remove(Serializable primaryKey)
		throws NoSuchListViewException {

		Session session = null;

		try {
			session = openSession();

			DEListView deListView = (DEListView)session.get(
				DEListViewImpl.class, primaryKey);

			if (deListView == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchListViewException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(deListView);
		}
		catch (NoSuchListViewException nsee) {
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
	protected DEListView removeImpl(DEListView deListView) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(deListView)) {
				deListView = (DEListView)session.get(
					DEListViewImpl.class, deListView.getPrimaryKeyObj());
			}

			if (deListView != null) {
				session.delete(deListView);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (deListView != null) {
			clearCache(deListView);
		}

		return deListView;
	}

	@Override
	public DEListView updateImpl(DEListView deListView) {
		boolean isNew = deListView.isNew();

		if (!(deListView instanceof DEListViewModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(deListView.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(deListView);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in deListView proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DEListView implementation " +
					deListView.getClass());
		}

		DEListViewModelImpl deListViewModelImpl =
			(DEListViewModelImpl)deListView;

		if (Validator.isNull(deListView.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			deListView.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (deListView.getCreateDate() == null)) {
			if (serviceContext == null) {
				deListView.setCreateDate(now);
			}
			else {
				deListView.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!deListViewModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				deListView.setModifiedDate(now);
			}
			else {
				deListView.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (deListView.isNew()) {
				session.save(deListView);

				deListView.setNew(false);
			}
			else {
				deListView = (DEListView)session.merge(deListView);
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
			Object[] args = new Object[] {deListViewModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				deListViewModelImpl.getUuid(),
				deListViewModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {
				deListViewModelImpl.getGroupId(),
				deListViewModelImpl.getCompanyId(),
				deListViewModelImpl.getDDMStructureId()
			};

			finderCache.removeResult(_finderPathCountByG_C_D, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_C_D, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((deListViewModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					deListViewModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {deListViewModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((deListViewModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					deListViewModelImpl.getOriginalUuid(),
					deListViewModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					deListViewModelImpl.getUuid(),
					deListViewModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((deListViewModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_D.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					deListViewModelImpl.getOriginalGroupId(),
					deListViewModelImpl.getOriginalCompanyId(),
					deListViewModelImpl.getOriginalDDMStructureId()
				};

				finderCache.removeResult(_finderPathCountByG_C_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_D, args);

				args = new Object[] {
					deListViewModelImpl.getGroupId(),
					deListViewModelImpl.getCompanyId(),
					deListViewModelImpl.getDDMStructureId()
				};

				finderCache.removeResult(_finderPathCountByG_C_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_D, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, DEListViewImpl.class,
			deListView.getPrimaryKey(), deListView, false);

		clearUniqueFindersCache(deListViewModelImpl, false);
		cacheUniqueFindersCache(deListViewModelImpl);

		deListView.resetOriginalValues();

		return deListView;
	}

	/**
	 * Returns the de list view with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the de list view
	 * @return the de list view
	 * @throws NoSuchListViewException if a de list view with the primary key could not be found
	 */
	@Override
	public DEListView findByPrimaryKey(Serializable primaryKey)
		throws NoSuchListViewException {

		DEListView deListView = fetchByPrimaryKey(primaryKey);

		if (deListView == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchListViewException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return deListView;
	}

	/**
	 * Returns the de list view with the primary key or throws a <code>NoSuchListViewException</code> if it could not be found.
	 *
	 * @param listViewId the primary key of the de list view
	 * @return the de list view
	 * @throws NoSuchListViewException if a de list view with the primary key could not be found
	 */
	@Override
	public DEListView findByPrimaryKey(long listViewId)
		throws NoSuchListViewException {

		return findByPrimaryKey((Serializable)listViewId);
	}

	/**
	 * Returns the de list view with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param listViewId the primary key of the de list view
	 * @return the de list view, or <code>null</code> if a de list view with the primary key could not be found
	 */
	@Override
	public DEListView fetchByPrimaryKey(long listViewId) {
		return fetchByPrimaryKey((Serializable)listViewId);
	}

	/**
	 * Returns all the de list views.
	 *
	 * @return the de list views
	 */
	@Override
	public List<DEListView> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the de list views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @return the range of de list views
	 */
	@Override
	public List<DEListView> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the de list views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of de list views
	 */
	@Override
	public List<DEListView> findAll(
		int start, int end, OrderByComparator<DEListView> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the de list views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of de list views
	 */
	@Override
	public List<DEListView> findAll(
		int start, int end, OrderByComparator<DEListView> orderByComparator,
		boolean retrieveFromCache) {

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
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<DEListView> list = null;

		if (retrieveFromCache) {
			list = (List<DEListView>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_DELISTVIEW);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DELISTVIEW;

				if (pagination) {
					sql = sql.concat(DEListViewModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DEListView>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DEListView>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the de list views from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DEListView deListView : findAll()) {
			remove(deListView);
		}
	}

	/**
	 * Returns the number of de list views.
	 *
	 * @return the number of de list views
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DELISTVIEW);

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
		return "listViewId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DELISTVIEW;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DEListViewModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the de list view persistence.
	 */
	@Activate
	public void activate() {
		DEListViewModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		DEListViewModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DEListViewImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DEListViewImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DEListViewImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DEListViewImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			DEListViewModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DEListViewImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			DEListViewModelImpl.UUID_COLUMN_BITMASK |
			DEListViewModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DEListViewImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DEListViewImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			DEListViewModelImpl.UUID_COLUMN_BITMASK |
			DEListViewModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByG_C_D = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DEListViewImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_D = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DEListViewImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			DEListViewModelImpl.GROUPID_COLUMN_BITMASK |
			DEListViewModelImpl.COMPANYID_COLUMN_BITMASK |
			DEListViewModelImpl.DDMSTRUCTUREID_COLUMN_BITMASK);

		_finderPathCountByG_C_D = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(DEListViewImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = DEPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.data.engine.model.DEListView"),
			true);
	}

	@Override
	@Reference(
		target = DEPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DEPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_DELISTVIEW =
		"SELECT deListView FROM DEListView deListView";

	private static final String _SQL_SELECT_DELISTVIEW_WHERE =
		"SELECT deListView FROM DEListView deListView WHERE ";

	private static final String _SQL_COUNT_DELISTVIEW =
		"SELECT COUNT(deListView) FROM DEListView deListView";

	private static final String _SQL_COUNT_DELISTVIEW_WHERE =
		"SELECT COUNT(deListView) FROM DEListView deListView WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "deListView.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DEListView exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DEListView exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DEListViewPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

}