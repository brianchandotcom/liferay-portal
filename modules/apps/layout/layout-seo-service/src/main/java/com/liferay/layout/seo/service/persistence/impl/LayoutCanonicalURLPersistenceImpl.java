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

import com.liferay.layout.seo.exception.NoSuchCanonicalURLException;
import com.liferay.layout.seo.model.LayoutCanonicalURL;
import com.liferay.layout.seo.model.impl.LayoutCanonicalURLImpl;
import com.liferay.layout.seo.model.impl.LayoutCanonicalURLModelImpl;
import com.liferay.layout.seo.service.persistence.LayoutCanonicalURLPersistence;
import com.liferay.layout.seo.service.persistence.impl.constants.LayoutPersistenceConstants;
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
 * The persistence implementation for the layout canonical url service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = LayoutCanonicalURLPersistence.class)
@ProviderType
public class LayoutCanonicalURLPersistenceImpl
	extends BasePersistenceImpl<LayoutCanonicalURL>
	implements LayoutCanonicalURLPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LayoutCanonicalURLUtil</code> to access the layout canonical url persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LayoutCanonicalURLImpl.class.getName();

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
	 * Returns all the layout canonical urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout canonical urls
	 */
	@Override
	public List<LayoutCanonicalURL> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout canonical urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @return the range of matching layout canonical urls
	 */
	@Override
	public List<LayoutCanonicalURL> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout canonical urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #findByUuid(String, int, int, OrderByComparator)}
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout canonical urls
	 */
	@Deprecated
	@Override
	public List<LayoutCanonicalURL> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutCanonicalURL> orderByComparator,
		boolean useFinderCache) {

		return findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout canonical urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout canonical urls
	 */
	@Override
	public List<LayoutCanonicalURL> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutCanonicalURL> orderByComparator) {

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

		List<LayoutCanonicalURL> list =
			(List<LayoutCanonicalURL>)finderCache.getResult(
				finderPath, finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LayoutCanonicalURL layoutCanonicalURL : list) {
				if (!uuid.equals(layoutCanonicalURL.getUuid())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_LAYOUTCANONICALURL_WHERE);

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
				query.append(LayoutCanonicalURLModelImpl.ORDER_BY_JPQL);
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
					list = (List<LayoutCanonicalURL>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutCanonicalURL>)QueryUtil.list(
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
	 * Returns the first layout canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout canonical url could not be found
	 */
	@Override
	public LayoutCanonicalURL findByUuid_First(
			String uuid,
			OrderByComparator<LayoutCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException {

		LayoutCanonicalURL layoutCanonicalURL = fetchByUuid_First(
			uuid, orderByComparator);

		if (layoutCanonicalURL != null) {
			return layoutCanonicalURL;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCanonicalURLException(msg.toString());
	}

	/**
	 * Returns the first layout canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	@Override
	public LayoutCanonicalURL fetchByUuid_First(
		String uuid, OrderByComparator<LayoutCanonicalURL> orderByComparator) {

		List<LayoutCanonicalURL> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout canonical url could not be found
	 */
	@Override
	public LayoutCanonicalURL findByUuid_Last(
			String uuid,
			OrderByComparator<LayoutCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException {

		LayoutCanonicalURL layoutCanonicalURL = fetchByUuid_Last(
			uuid, orderByComparator);

		if (layoutCanonicalURL != null) {
			return layoutCanonicalURL;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCanonicalURLException(msg.toString());
	}

	/**
	 * Returns the last layout canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	@Override
	public LayoutCanonicalURL fetchByUuid_Last(
		String uuid, OrderByComparator<LayoutCanonicalURL> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<LayoutCanonicalURL> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout canonical urls before and after the current layout canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param layoutCanonicalURLId the primary key of the current layout canonical url
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout canonical url
	 * @throws NoSuchCanonicalURLException if a layout canonical url with the primary key could not be found
	 */
	@Override
	public LayoutCanonicalURL[] findByUuid_PrevAndNext(
			long layoutCanonicalURLId, String uuid,
			OrderByComparator<LayoutCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException {

		uuid = Objects.toString(uuid, "");

		LayoutCanonicalURL layoutCanonicalURL = findByPrimaryKey(
			layoutCanonicalURLId);

		Session session = null;

		try {
			session = openSession();

			LayoutCanonicalURL[] array = new LayoutCanonicalURLImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, layoutCanonicalURL, uuid, orderByComparator, true);

			array[1] = layoutCanonicalURL;

			array[2] = getByUuid_PrevAndNext(
				session, layoutCanonicalURL, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutCanonicalURL getByUuid_PrevAndNext(
		Session session, LayoutCanonicalURL layoutCanonicalURL, String uuid,
		OrderByComparator<LayoutCanonicalURL> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTCANONICALURL_WHERE);

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
			query.append(LayoutCanonicalURLModelImpl.ORDER_BY_JPQL);
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
						layoutCanonicalURL)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutCanonicalURL> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout canonical urls where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (LayoutCanonicalURL layoutCanonicalURL :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutCanonicalURL);
		}
	}

	/**
	 * Returns the number of layout canonical urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout canonical urls
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTCANONICALURL_WHERE);

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
		"layoutCanonicalURL.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(layoutCanonicalURL.uuid IS NULL OR layoutCanonicalURL.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the layout canonical url where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCanonicalURLException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout canonical url could not be found
	 */
	@Override
	public LayoutCanonicalURL findByUUID_G(String uuid, long groupId)
		throws NoSuchCanonicalURLException {

		LayoutCanonicalURL layoutCanonicalURL = fetchByUUID_G(uuid, groupId);

		if (layoutCanonicalURL == null) {
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

			throw new NoSuchCanonicalURLException(msg.toString());
		}

		return layoutCanonicalURL;
	}

	/**
	 * Returns the layout canonical url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #fetchByUUID_G(String,long)}
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	@Deprecated
	@Override
	public LayoutCanonicalURL fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the layout canonical url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	@Override
	public LayoutCanonicalURL fetchByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = new Object[] {uuid, groupId};

		Object result = finderCache.getResult(
			_finderPathFetchByUUID_G, finderArgs, this);

		if (result instanceof LayoutCanonicalURL) {
			LayoutCanonicalURL layoutCanonicalURL = (LayoutCanonicalURL)result;

			if (!Objects.equals(uuid, layoutCanonicalURL.getUuid()) ||
				(groupId != layoutCanonicalURL.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LAYOUTCANONICALURL_WHERE);

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

				List<LayoutCanonicalURL> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(
						_finderPathFetchByUUID_G, finderArgs, list);
				}
				else {
					LayoutCanonicalURL layoutCanonicalURL = list.get(0);

					result = layoutCanonicalURL;

					cacheResult(layoutCanonicalURL);
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
			return (LayoutCanonicalURL)result;
		}
	}

	/**
	 * Removes the layout canonical url where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout canonical url that was removed
	 */
	@Override
	public LayoutCanonicalURL removeByUUID_G(String uuid, long groupId)
		throws NoSuchCanonicalURLException {

		LayoutCanonicalURL layoutCanonicalURL = findByUUID_G(uuid, groupId);

		return remove(layoutCanonicalURL);
	}

	/**
	 * Returns the number of layout canonical urls where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout canonical urls
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTCANONICALURL_WHERE);

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
		"layoutCanonicalURL.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(layoutCanonicalURL.uuid IS NULL OR layoutCanonicalURL.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"layoutCanonicalURL.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the layout canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout canonical urls
	 */
	@Override
	public List<LayoutCanonicalURL> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @return the range of matching layout canonical urls
	 */
	@Override
	public List<LayoutCanonicalURL> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #findByUuid_C(String,long, int, int, OrderByComparator)}
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout canonical urls
	 */
	@Deprecated
	@Override
	public List<LayoutCanonicalURL> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutCanonicalURL> orderByComparator,
		boolean useFinderCache) {

		return findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout canonical urls
	 */
	@Override
	public List<LayoutCanonicalURL> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutCanonicalURL> orderByComparator) {

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

		List<LayoutCanonicalURL> list =
			(List<LayoutCanonicalURL>)finderCache.getResult(
				finderPath, finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LayoutCanonicalURL layoutCanonicalURL : list) {
				if (!uuid.equals(layoutCanonicalURL.getUuid()) ||
					(companyId != layoutCanonicalURL.getCompanyId())) {

					list = null;

					break;
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

			query.append(_SQL_SELECT_LAYOUTCANONICALURL_WHERE);

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
				query.append(LayoutCanonicalURLModelImpl.ORDER_BY_JPQL);
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
					list = (List<LayoutCanonicalURL>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutCanonicalURL>)QueryUtil.list(
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
	 * Returns the first layout canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout canonical url could not be found
	 */
	@Override
	public LayoutCanonicalURL findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LayoutCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException {

		LayoutCanonicalURL layoutCanonicalURL = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (layoutCanonicalURL != null) {
			return layoutCanonicalURL;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCanonicalURLException(msg.toString());
	}

	/**
	 * Returns the first layout canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	@Override
	public LayoutCanonicalURL fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LayoutCanonicalURL> orderByComparator) {

		List<LayoutCanonicalURL> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout canonical url could not be found
	 */
	@Override
	public LayoutCanonicalURL findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LayoutCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException {

		LayoutCanonicalURL layoutCanonicalURL = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (layoutCanonicalURL != null) {
			return layoutCanonicalURL;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCanonicalURLException(msg.toString());
	}

	/**
	 * Returns the last layout canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	@Override
	public LayoutCanonicalURL fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LayoutCanonicalURL> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<LayoutCanonicalURL> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout canonical urls before and after the current layout canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutCanonicalURLId the primary key of the current layout canonical url
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout canonical url
	 * @throws NoSuchCanonicalURLException if a layout canonical url with the primary key could not be found
	 */
	@Override
	public LayoutCanonicalURL[] findByUuid_C_PrevAndNext(
			long layoutCanonicalURLId, String uuid, long companyId,
			OrderByComparator<LayoutCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException {

		uuid = Objects.toString(uuid, "");

		LayoutCanonicalURL layoutCanonicalURL = findByPrimaryKey(
			layoutCanonicalURLId);

		Session session = null;

		try {
			session = openSession();

			LayoutCanonicalURL[] array = new LayoutCanonicalURLImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, layoutCanonicalURL, uuid, companyId, orderByComparator,
				true);

			array[1] = layoutCanonicalURL;

			array[2] = getByUuid_C_PrevAndNext(
				session, layoutCanonicalURL, uuid, companyId, orderByComparator,
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

	protected LayoutCanonicalURL getByUuid_C_PrevAndNext(
		Session session, LayoutCanonicalURL layoutCanonicalURL, String uuid,
		long companyId, OrderByComparator<LayoutCanonicalURL> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTCANONICALURL_WHERE);

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
			query.append(LayoutCanonicalURLModelImpl.ORDER_BY_JPQL);
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
						layoutCanonicalURL)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutCanonicalURL> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout canonical urls where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (LayoutCanonicalURL layoutCanonicalURL :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutCanonicalURL);
		}
	}

	/**
	 * Returns the number of layout canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout canonical urls
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTCANONICALURL_WHERE);

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
		"layoutCanonicalURL.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(layoutCanonicalURL.uuid IS NULL OR layoutCanonicalURL.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"layoutCanonicalURL.companyId = ?";

	private FinderPath _finderPathFetchByG_P_L;
	private FinderPath _finderPathCountByG_P_L;

	/**
	 * Returns the layout canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or throws a <code>NoSuchCanonicalURLException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching layout canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout canonical url could not be found
	 */
	@Override
	public LayoutCanonicalURL findByG_P_L(
			long groupId, boolean privateLayout, long layoutId)
		throws NoSuchCanonicalURLException {

		LayoutCanonicalURL layoutCanonicalURL = fetchByG_P_L(
			groupId, privateLayout, layoutId);

		if (layoutCanonicalURL == null) {
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

			throw new NoSuchCanonicalURLException(msg.toString());
		}

		return layoutCanonicalURL;
	}

	/**
	 * Returns the layout canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #fetchByG_P_L(long,boolean,long)}
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	@Deprecated
	@Override
	public LayoutCanonicalURL fetchByG_P_L(
		long groupId, boolean privateLayout, long layoutId,
		boolean useFinderCache) {

		return fetchByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	 * Returns the layout canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	@Override
	public LayoutCanonicalURL fetchByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		Object[] finderArgs = new Object[] {groupId, privateLayout, layoutId};

		Object result = finderCache.getResult(
			_finderPathFetchByG_P_L, finderArgs, this);

		if (result instanceof LayoutCanonicalURL) {
			LayoutCanonicalURL layoutCanonicalURL = (LayoutCanonicalURL)result;

			if ((groupId != layoutCanonicalURL.getGroupId()) ||
				(privateLayout != layoutCanonicalURL.isPrivateLayout()) ||
				(layoutId != layoutCanonicalURL.getLayoutId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_LAYOUTCANONICALURL_WHERE);

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

				List<LayoutCanonicalURL> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(
						_finderPathFetchByG_P_L, finderArgs, list);
				}
				else {
					LayoutCanonicalURL layoutCanonicalURL = list.get(0);

					result = layoutCanonicalURL;

					cacheResult(layoutCanonicalURL);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathFetchByG_P_L, finderArgs);

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
			return (LayoutCanonicalURL)result;
		}
	}

	/**
	 * Removes the layout canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the layout canonical url that was removed
	 */
	@Override
	public LayoutCanonicalURL removeByG_P_L(
			long groupId, boolean privateLayout, long layoutId)
		throws NoSuchCanonicalURLException {

		LayoutCanonicalURL layoutCanonicalURL = findByG_P_L(
			groupId, privateLayout, layoutId);

		return remove(layoutCanonicalURL);
	}

	/**
	 * Returns the number of layout canonical urls where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the number of matching layout canonical urls
	 */
	@Override
	public int countByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		FinderPath finderPath = _finderPathCountByG_P_L;

		Object[] finderArgs = new Object[] {groupId, privateLayout, layoutId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTCANONICALURL_WHERE);

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
		"layoutCanonicalURL.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2 =
		"layoutCanonicalURL.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_LAYOUTID_2 =
		"layoutCanonicalURL.layoutId = ?";

	public LayoutCanonicalURLPersistenceImpl() {
		setModelClass(LayoutCanonicalURL.class);

		setModelImplClass(LayoutCanonicalURLImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the layout canonical url in the entity cache if it is enabled.
	 *
	 * @param layoutCanonicalURL the layout canonical url
	 */
	@Override
	public void cacheResult(LayoutCanonicalURL layoutCanonicalURL) {
		entityCache.putResult(
			entityCacheEnabled, LayoutCanonicalURLImpl.class,
			layoutCanonicalURL.getPrimaryKey(), layoutCanonicalURL);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				layoutCanonicalURL.getUuid(), layoutCanonicalURL.getGroupId()
			},
			layoutCanonicalURL);

		finderCache.putResult(
			_finderPathFetchByG_P_L,
			new Object[] {
				layoutCanonicalURL.getGroupId(),
				layoutCanonicalURL.isPrivateLayout(),
				layoutCanonicalURL.getLayoutId()
			},
			layoutCanonicalURL);

		layoutCanonicalURL.resetOriginalValues();
	}

	/**
	 * Caches the layout canonical urls in the entity cache if it is enabled.
	 *
	 * @param layoutCanonicalURLs the layout canonical urls
	 */
	@Override
	public void cacheResult(List<LayoutCanonicalURL> layoutCanonicalURLs) {
		for (LayoutCanonicalURL layoutCanonicalURL : layoutCanonicalURLs) {
			if (entityCache.getResult(
					entityCacheEnabled, LayoutCanonicalURLImpl.class,
					layoutCanonicalURL.getPrimaryKey()) == null) {

				cacheResult(layoutCanonicalURL);
			}
			else {
				layoutCanonicalURL.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout canonical urls.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LayoutCanonicalURLImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout canonical url.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutCanonicalURL layoutCanonicalURL) {
		entityCache.removeResult(
			entityCacheEnabled, LayoutCanonicalURLImpl.class,
			layoutCanonicalURL.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(LayoutCanonicalURLModelImpl)layoutCanonicalURL, true);
	}

	@Override
	public void clearCache(List<LayoutCanonicalURL> layoutCanonicalURLs) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutCanonicalURL layoutCanonicalURL : layoutCanonicalURLs) {
			entityCache.removeResult(
				entityCacheEnabled, LayoutCanonicalURLImpl.class,
				layoutCanonicalURL.getPrimaryKey());

			clearUniqueFindersCache(
				(LayoutCanonicalURLModelImpl)layoutCanonicalURL, true);
		}
	}

	protected void cacheUniqueFindersCache(
		LayoutCanonicalURLModelImpl layoutCanonicalURLModelImpl) {

		Object[] args = new Object[] {
			layoutCanonicalURLModelImpl.getUuid(),
			layoutCanonicalURLModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, layoutCanonicalURLModelImpl, false);

		args = new Object[] {
			layoutCanonicalURLModelImpl.getGroupId(),
			layoutCanonicalURLModelImpl.isPrivateLayout(),
			layoutCanonicalURLModelImpl.getLayoutId()
		};

		finderCache.putResult(
			_finderPathCountByG_P_L, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_P_L, args, layoutCanonicalURLModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LayoutCanonicalURLModelImpl layoutCanonicalURLModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				layoutCanonicalURLModelImpl.getUuid(),
				layoutCanonicalURLModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((layoutCanonicalURLModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				layoutCanonicalURLModelImpl.getOriginalUuid(),
				layoutCanonicalURLModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				layoutCanonicalURLModelImpl.getGroupId(),
				layoutCanonicalURLModelImpl.isPrivateLayout(),
				layoutCanonicalURLModelImpl.getLayoutId()
			};

			finderCache.removeResult(_finderPathCountByG_P_L, args);
			finderCache.removeResult(_finderPathFetchByG_P_L, args);
		}

		if ((layoutCanonicalURLModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_P_L.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				layoutCanonicalURLModelImpl.getOriginalGroupId(),
				layoutCanonicalURLModelImpl.getOriginalPrivateLayout(),
				layoutCanonicalURLModelImpl.getOriginalLayoutId()
			};

			finderCache.removeResult(_finderPathCountByG_P_L, args);
			finderCache.removeResult(_finderPathFetchByG_P_L, args);
		}
	}

	/**
	 * Creates a new layout canonical url with the primary key. Does not add the layout canonical url to the database.
	 *
	 * @param layoutCanonicalURLId the primary key for the new layout canonical url
	 * @return the new layout canonical url
	 */
	@Override
	public LayoutCanonicalURL create(long layoutCanonicalURLId) {
		LayoutCanonicalURL layoutCanonicalURL = new LayoutCanonicalURLImpl();

		layoutCanonicalURL.setNew(true);
		layoutCanonicalURL.setPrimaryKey(layoutCanonicalURLId);

		String uuid = PortalUUIDUtil.generate();

		layoutCanonicalURL.setUuid(uuid);

		layoutCanonicalURL.setCompanyId(CompanyThreadLocal.getCompanyId());

		return layoutCanonicalURL;
	}

	/**
	 * Removes the layout canonical url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutCanonicalURLId the primary key of the layout canonical url
	 * @return the layout canonical url that was removed
	 * @throws NoSuchCanonicalURLException if a layout canonical url with the primary key could not be found
	 */
	@Override
	public LayoutCanonicalURL remove(long layoutCanonicalURLId)
		throws NoSuchCanonicalURLException {

		return remove((Serializable)layoutCanonicalURLId);
	}

	/**
	 * Removes the layout canonical url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout canonical url
	 * @return the layout canonical url that was removed
	 * @throws NoSuchCanonicalURLException if a layout canonical url with the primary key could not be found
	 */
	@Override
	public LayoutCanonicalURL remove(Serializable primaryKey)
		throws NoSuchCanonicalURLException {

		Session session = null;

		try {
			session = openSession();

			LayoutCanonicalURL layoutCanonicalURL =
				(LayoutCanonicalURL)session.get(
					LayoutCanonicalURLImpl.class, primaryKey);

			if (layoutCanonicalURL == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCanonicalURLException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(layoutCanonicalURL);
		}
		catch (NoSuchCanonicalURLException nsee) {
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
	protected LayoutCanonicalURL removeImpl(
		LayoutCanonicalURL layoutCanonicalURL) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutCanonicalURL)) {
				layoutCanonicalURL = (LayoutCanonicalURL)session.get(
					LayoutCanonicalURLImpl.class,
					layoutCanonicalURL.getPrimaryKeyObj());
			}

			if (layoutCanonicalURL != null) {
				session.delete(layoutCanonicalURL);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutCanonicalURL != null) {
			clearCache(layoutCanonicalURL);
		}

		return layoutCanonicalURL;
	}

	@Override
	public LayoutCanonicalURL updateImpl(
		LayoutCanonicalURL layoutCanonicalURL) {

		boolean isNew = layoutCanonicalURL.isNew();

		if (!(layoutCanonicalURL instanceof LayoutCanonicalURLModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(layoutCanonicalURL.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					layoutCanonicalURL);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in layoutCanonicalURL proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LayoutCanonicalURL implementation " +
					layoutCanonicalURL.getClass());
		}

		LayoutCanonicalURLModelImpl layoutCanonicalURLModelImpl =
			(LayoutCanonicalURLModelImpl)layoutCanonicalURL;

		if (Validator.isNull(layoutCanonicalURL.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			layoutCanonicalURL.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (layoutCanonicalURL.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutCanonicalURL.setCreateDate(now);
			}
			else {
				layoutCanonicalURL.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!layoutCanonicalURLModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutCanonicalURL.setModifiedDate(now);
			}
			else {
				layoutCanonicalURL.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (layoutCanonicalURL.isNew()) {
				session.save(layoutCanonicalURL);

				layoutCanonicalURL.setNew(false);
			}
			else {
				layoutCanonicalURL = (LayoutCanonicalURL)session.merge(
					layoutCanonicalURL);
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
				layoutCanonicalURLModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				layoutCanonicalURLModelImpl.getUuid(),
				layoutCanonicalURLModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((layoutCanonicalURLModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutCanonicalURLModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {layoutCanonicalURLModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((layoutCanonicalURLModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutCanonicalURLModelImpl.getOriginalUuid(),
					layoutCanonicalURLModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					layoutCanonicalURLModelImpl.getUuid(),
					layoutCanonicalURLModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, LayoutCanonicalURLImpl.class,
			layoutCanonicalURL.getPrimaryKey(), layoutCanonicalURL, false);

		clearUniqueFindersCache(layoutCanonicalURLModelImpl, false);
		cacheUniqueFindersCache(layoutCanonicalURLModelImpl);

		layoutCanonicalURL.resetOriginalValues();

		return layoutCanonicalURL;
	}

	/**
	 * Returns the layout canonical url with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout canonical url
	 * @return the layout canonical url
	 * @throws NoSuchCanonicalURLException if a layout canonical url with the primary key could not be found
	 */
	@Override
	public LayoutCanonicalURL findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCanonicalURLException {

		LayoutCanonicalURL layoutCanonicalURL = fetchByPrimaryKey(primaryKey);

		if (layoutCanonicalURL == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCanonicalURLException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return layoutCanonicalURL;
	}

	/**
	 * Returns the layout canonical url with the primary key or throws a <code>NoSuchCanonicalURLException</code> if it could not be found.
	 *
	 * @param layoutCanonicalURLId the primary key of the layout canonical url
	 * @return the layout canonical url
	 * @throws NoSuchCanonicalURLException if a layout canonical url with the primary key could not be found
	 */
	@Override
	public LayoutCanonicalURL findByPrimaryKey(long layoutCanonicalURLId)
		throws NoSuchCanonicalURLException {

		return findByPrimaryKey((Serializable)layoutCanonicalURLId);
	}

	/**
	 * Returns the layout canonical url with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutCanonicalURLId the primary key of the layout canonical url
	 * @return the layout canonical url, or <code>null</code> if a layout canonical url with the primary key could not be found
	 */
	@Override
	public LayoutCanonicalURL fetchByPrimaryKey(long layoutCanonicalURLId) {
		return fetchByPrimaryKey((Serializable)layoutCanonicalURLId);
	}

	/**
	 * Returns all the layout canonical urls.
	 *
	 * @return the layout canonical urls
	 */
	@Override
	public List<LayoutCanonicalURL> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout canonical urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @return the range of layout canonical urls
	 */
	@Override
	public List<LayoutCanonicalURL> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout canonical urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #findAll(int, int, OrderByComparator)}
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout canonical urls
	 */
	@Deprecated
	@Override
	public List<LayoutCanonicalURL> findAll(
		int start, int end,
		OrderByComparator<LayoutCanonicalURL> orderByComparator,
		boolean useFinderCache) {

		return findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout canonical urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout canonical urls
	 */
	@Override
	public List<LayoutCanonicalURL> findAll(
		int start, int end,
		OrderByComparator<LayoutCanonicalURL> orderByComparator) {

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

		List<LayoutCanonicalURL> list =
			(List<LayoutCanonicalURL>)finderCache.getResult(
				finderPath, finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LAYOUTCANONICALURL);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTCANONICALURL;

				if (pagination) {
					sql = sql.concat(LayoutCanonicalURLModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LayoutCanonicalURL>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutCanonicalURL>)QueryUtil.list(
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
	 * Removes all the layout canonical urls from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutCanonicalURL layoutCanonicalURL : findAll()) {
			remove(layoutCanonicalURL);
		}
	}

	/**
	 * Returns the number of layout canonical urls.
	 *
	 * @return the number of layout canonical urls
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUTCANONICALURL);

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
		return "layoutCanonicalURLId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAYOUTCANONICALURL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LayoutCanonicalURLModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout canonical url persistence.
	 */
	@Activate
	public void activate() {
		LayoutCanonicalURLModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		LayoutCanonicalURLModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutCanonicalURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutCanonicalURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutCanonicalURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutCanonicalURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			LayoutCanonicalURLModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutCanonicalURLImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			LayoutCanonicalURLModelImpl.UUID_COLUMN_BITMASK |
			LayoutCanonicalURLModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutCanonicalURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutCanonicalURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			LayoutCanonicalURLModelImpl.UUID_COLUMN_BITMASK |
			LayoutCanonicalURLModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathFetchByG_P_L = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutCanonicalURLImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			},
			LayoutCanonicalURLModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutCanonicalURLModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutCanonicalURLModelImpl.LAYOUTID_COLUMN_BITMASK);

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
		entityCache.removeCache(LayoutCanonicalURLImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = LayoutPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.layout.seo.model.LayoutCanonicalURL"),
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

	private static final String _SQL_SELECT_LAYOUTCANONICALURL =
		"SELECT layoutCanonicalURL FROM LayoutCanonicalURL layoutCanonicalURL";

	private static final String _SQL_SELECT_LAYOUTCANONICALURL_WHERE =
		"SELECT layoutCanonicalURL FROM LayoutCanonicalURL layoutCanonicalURL WHERE ";

	private static final String _SQL_COUNT_LAYOUTCANONICALURL =
		"SELECT COUNT(layoutCanonicalURL) FROM LayoutCanonicalURL layoutCanonicalURL";

	private static final String _SQL_COUNT_LAYOUTCANONICALURL_WHERE =
		"SELECT COUNT(layoutCanonicalURL) FROM LayoutCanonicalURL layoutCanonicalURL WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutCanonicalURL.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LayoutCanonicalURL exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LayoutCanonicalURL exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutCanonicalURLPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	static {
		try {
			Class.forName(LayoutPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}