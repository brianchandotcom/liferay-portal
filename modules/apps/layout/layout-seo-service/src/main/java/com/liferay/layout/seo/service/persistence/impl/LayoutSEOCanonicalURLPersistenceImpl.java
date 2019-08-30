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
import com.liferay.layout.seo.model.LayoutSEOCanonicalURL;
import com.liferay.layout.seo.model.impl.LayoutSEOCanonicalURLImpl;
import com.liferay.layout.seo.model.impl.LayoutSEOCanonicalURLModelImpl;
import com.liferay.layout.seo.service.persistence.LayoutSEOCanonicalURLPersistence;
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

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the layout seo canonical url service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = LayoutSEOCanonicalURLPersistence.class)
@ProviderType
public class LayoutSEOCanonicalURLPersistenceImpl
	extends BasePersistenceImpl<LayoutSEOCanonicalURL>
	implements LayoutSEOCanonicalURLPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LayoutSEOCanonicalURLUtil</code> to access the layout seo canonical url persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LayoutSEOCanonicalURLImpl.class.getName();

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
	 * Returns all the layout seo canonical urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout seo canonical urls
	 */
	@Override
	public List<LayoutSEOCanonicalURL> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout seo canonical urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @return the range of matching layout seo canonical urls
	 */
	@Override
	public List<LayoutSEOCanonicalURL> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout seo canonical urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #findByUuid(String, int, int, OrderByComparator)}
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo canonical urls
	 */
	@Deprecated
	@Override
	public List<LayoutSEOCanonicalURL> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator,
		boolean useFinderCache) {

		return findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout seo canonical urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo canonical urls
	 */
	@Override
	public List<LayoutSEOCanonicalURL> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator) {

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

		List<LayoutSEOCanonicalURL> list =
			(List<LayoutSEOCanonicalURL>)finderCache.getResult(
				finderPath, finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LayoutSEOCanonicalURL layoutSEOCanonicalURL : list) {
				if (!uuid.equals(layoutSEOCanonicalURL.getUuid())) {
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

			query.append(_SQL_SELECT_LAYOUTSEOCANONICALURL_WHERE);

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
				query.append(LayoutSEOCanonicalURLModelImpl.ORDER_BY_JPQL);
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
					list = (List<LayoutSEOCanonicalURL>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSEOCanonicalURL>)QueryUtil.list(
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
	 * Returns the first layout seo canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout seo canonical url could not be found
	 */
	@Override
	public LayoutSEOCanonicalURL findByUuid_First(
			String uuid,
			OrderByComparator<LayoutSEOCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException {

		LayoutSEOCanonicalURL layoutSEOCanonicalURL = fetchByUuid_First(
			uuid, orderByComparator);

		if (layoutSEOCanonicalURL != null) {
			return layoutSEOCanonicalURL;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCanonicalURLException(msg.toString());
	}

	/**
	 * Returns the first layout seo canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	@Override
	public LayoutSEOCanonicalURL fetchByUuid_First(
		String uuid,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator) {

		List<LayoutSEOCanonicalURL> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout seo canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout seo canonical url could not be found
	 */
	@Override
	public LayoutSEOCanonicalURL findByUuid_Last(
			String uuid,
			OrderByComparator<LayoutSEOCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException {

		LayoutSEOCanonicalURL layoutSEOCanonicalURL = fetchByUuid_Last(
			uuid, orderByComparator);

		if (layoutSEOCanonicalURL != null) {
			return layoutSEOCanonicalURL;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCanonicalURLException(msg.toString());
	}

	/**
	 * Returns the last layout seo canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	@Override
	public LayoutSEOCanonicalURL fetchByUuid_Last(
		String uuid,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<LayoutSEOCanonicalURL> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout seo canonical urls before and after the current layout seo canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param layoutSEOCanonicalURLId the primary key of the current layout seo canonical url
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a layout seo canonical url with the primary key could not be found
	 */
	@Override
	public LayoutSEOCanonicalURL[] findByUuid_PrevAndNext(
			long layoutSEOCanonicalURLId, String uuid,
			OrderByComparator<LayoutSEOCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException {

		uuid = Objects.toString(uuid, "");

		LayoutSEOCanonicalURL layoutSEOCanonicalURL = findByPrimaryKey(
			layoutSEOCanonicalURLId);

		Session session = null;

		try {
			session = openSession();

			LayoutSEOCanonicalURL[] array = new LayoutSEOCanonicalURLImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, layoutSEOCanonicalURL, uuid, orderByComparator, true);

			array[1] = layoutSEOCanonicalURL;

			array[2] = getByUuid_PrevAndNext(
				session, layoutSEOCanonicalURL, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutSEOCanonicalURL getByUuid_PrevAndNext(
		Session session, LayoutSEOCanonicalURL layoutSEOCanonicalURL,
		String uuid, OrderByComparator<LayoutSEOCanonicalURL> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTSEOCANONICALURL_WHERE);

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
			query.append(LayoutSEOCanonicalURLModelImpl.ORDER_BY_JPQL);
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
						layoutSEOCanonicalURL)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSEOCanonicalURL> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout seo canonical urls where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (LayoutSEOCanonicalURL layoutSEOCanonicalURL :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutSEOCanonicalURL);
		}
	}

	/**
	 * Returns the number of layout seo canonical urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout seo canonical urls
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTSEOCANONICALURL_WHERE);

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
		"layoutSEOCanonicalURL.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(layoutSEOCanonicalURL.uuid IS NULL OR layoutSEOCanonicalURL.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the layout seo canonical url where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCanonicalURLException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout seo canonical url could not be found
	 */
	@Override
	public LayoutSEOCanonicalURL findByUUID_G(String uuid, long groupId)
		throws NoSuchCanonicalURLException {

		LayoutSEOCanonicalURL layoutSEOCanonicalURL = fetchByUUID_G(
			uuid, groupId);

		if (layoutSEOCanonicalURL == null) {
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

		return layoutSEOCanonicalURL;
	}

	/**
	 * Returns the layout seo canonical url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #fetchByUUID_G(String,long)}
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	@Deprecated
	@Override
	public LayoutSEOCanonicalURL fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the layout seo canonical url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	@Override
	public LayoutSEOCanonicalURL fetchByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = new Object[] {uuid, groupId};

		Object result = finderCache.getResult(
			_finderPathFetchByUUID_G, finderArgs, this);

		if (result instanceof LayoutSEOCanonicalURL) {
			LayoutSEOCanonicalURL layoutSEOCanonicalURL =
				(LayoutSEOCanonicalURL)result;

			if (!Objects.equals(uuid, layoutSEOCanonicalURL.getUuid()) ||
				(groupId != layoutSEOCanonicalURL.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LAYOUTSEOCANONICALURL_WHERE);

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

				List<LayoutSEOCanonicalURL> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(
						_finderPathFetchByUUID_G, finderArgs, list);
				}
				else {
					LayoutSEOCanonicalURL layoutSEOCanonicalURL = list.get(0);

					result = layoutSEOCanonicalURL;

					cacheResult(layoutSEOCanonicalURL);
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
			return (LayoutSEOCanonicalURL)result;
		}
	}

	/**
	 * Removes the layout seo canonical url where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout seo canonical url that was removed
	 */
	@Override
	public LayoutSEOCanonicalURL removeByUUID_G(String uuid, long groupId)
		throws NoSuchCanonicalURLException {

		LayoutSEOCanonicalURL layoutSEOCanonicalURL = findByUUID_G(
			uuid, groupId);

		return remove(layoutSEOCanonicalURL);
	}

	/**
	 * Returns the number of layout seo canonical urls where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout seo canonical urls
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTSEOCANONICALURL_WHERE);

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
		"layoutSEOCanonicalURL.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(layoutSEOCanonicalURL.uuid IS NULL OR layoutSEOCanonicalURL.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"layoutSEOCanonicalURL.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the layout seo canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout seo canonical urls
	 */
	@Override
	public List<LayoutSEOCanonicalURL> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout seo canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @return the range of matching layout seo canonical urls
	 */
	@Override
	public List<LayoutSEOCanonicalURL> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout seo canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #findByUuid_C(String,long, int, int, OrderByComparator)}
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo canonical urls
	 */
	@Deprecated
	@Override
	public List<LayoutSEOCanonicalURL> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator,
		boolean useFinderCache) {

		return findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout seo canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo canonical urls
	 */
	@Override
	public List<LayoutSEOCanonicalURL> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator) {

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

		List<LayoutSEOCanonicalURL> list =
			(List<LayoutSEOCanonicalURL>)finderCache.getResult(
				finderPath, finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LayoutSEOCanonicalURL layoutSEOCanonicalURL : list) {
				if (!uuid.equals(layoutSEOCanonicalURL.getUuid()) ||
					(companyId != layoutSEOCanonicalURL.getCompanyId())) {

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

			query.append(_SQL_SELECT_LAYOUTSEOCANONICALURL_WHERE);

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
				query.append(LayoutSEOCanonicalURLModelImpl.ORDER_BY_JPQL);
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
					list = (List<LayoutSEOCanonicalURL>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSEOCanonicalURL>)QueryUtil.list(
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
	 * Returns the first layout seo canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout seo canonical url could not be found
	 */
	@Override
	public LayoutSEOCanonicalURL findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LayoutSEOCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException {

		LayoutSEOCanonicalURL layoutSEOCanonicalURL = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (layoutSEOCanonicalURL != null) {
			return layoutSEOCanonicalURL;
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
	 * Returns the first layout seo canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	@Override
	public LayoutSEOCanonicalURL fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator) {

		List<LayoutSEOCanonicalURL> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout seo canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout seo canonical url could not be found
	 */
	@Override
	public LayoutSEOCanonicalURL findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LayoutSEOCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException {

		LayoutSEOCanonicalURL layoutSEOCanonicalURL = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (layoutSEOCanonicalURL != null) {
			return layoutSEOCanonicalURL;
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
	 * Returns the last layout seo canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	@Override
	public LayoutSEOCanonicalURL fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<LayoutSEOCanonicalURL> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout seo canonical urls before and after the current layout seo canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutSEOCanonicalURLId the primary key of the current layout seo canonical url
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a layout seo canonical url with the primary key could not be found
	 */
	@Override
	public LayoutSEOCanonicalURL[] findByUuid_C_PrevAndNext(
			long layoutSEOCanonicalURLId, String uuid, long companyId,
			OrderByComparator<LayoutSEOCanonicalURL> orderByComparator)
		throws NoSuchCanonicalURLException {

		uuid = Objects.toString(uuid, "");

		LayoutSEOCanonicalURL layoutSEOCanonicalURL = findByPrimaryKey(
			layoutSEOCanonicalURLId);

		Session session = null;

		try {
			session = openSession();

			LayoutSEOCanonicalURL[] array = new LayoutSEOCanonicalURLImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, layoutSEOCanonicalURL, uuid, companyId,
				orderByComparator, true);

			array[1] = layoutSEOCanonicalURL;

			array[2] = getByUuid_C_PrevAndNext(
				session, layoutSEOCanonicalURL, uuid, companyId,
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

	protected LayoutSEOCanonicalURL getByUuid_C_PrevAndNext(
		Session session, LayoutSEOCanonicalURL layoutSEOCanonicalURL,
		String uuid, long companyId,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTSEOCANONICALURL_WHERE);

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
			query.append(LayoutSEOCanonicalURLModelImpl.ORDER_BY_JPQL);
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
						layoutSEOCanonicalURL)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSEOCanonicalURL> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout seo canonical urls where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (LayoutSEOCanonicalURL layoutSEOCanonicalURL :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutSEOCanonicalURL);
		}
	}

	/**
	 * Returns the number of layout seo canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout seo canonical urls
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTSEOCANONICALURL_WHERE);

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
		"layoutSEOCanonicalURL.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(layoutSEOCanonicalURL.uuid IS NULL OR layoutSEOCanonicalURL.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"layoutSEOCanonicalURL.companyId = ?";

	private FinderPath _finderPathFetchByG_P_L;
	private FinderPath _finderPathCountByG_P_L;

	/**
	 * Returns the layout seo canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or throws a <code>NoSuchCanonicalURLException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout seo canonical url could not be found
	 */
	@Override
	public LayoutSEOCanonicalURL findByG_P_L(
			long groupId, boolean privateLayout, long layoutId)
		throws NoSuchCanonicalURLException {

		LayoutSEOCanonicalURL layoutSEOCanonicalURL = fetchByG_P_L(
			groupId, privateLayout, layoutId);

		if (layoutSEOCanonicalURL == null) {
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

		return layoutSEOCanonicalURL;
	}

	/**
	 * Returns the layout seo canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #fetchByG_P_L(long,boolean,long)}
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	@Deprecated
	@Override
	public LayoutSEOCanonicalURL fetchByG_P_L(
		long groupId, boolean privateLayout, long layoutId,
		boolean useFinderCache) {

		return fetchByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	 * Returns the layout seo canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	@Override
	public LayoutSEOCanonicalURL fetchByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		Object[] finderArgs = new Object[] {groupId, privateLayout, layoutId};

		Object result = finderCache.getResult(
			_finderPathFetchByG_P_L, finderArgs, this);

		if (result instanceof LayoutSEOCanonicalURL) {
			LayoutSEOCanonicalURL layoutSEOCanonicalURL =
				(LayoutSEOCanonicalURL)result;

			if ((groupId != layoutSEOCanonicalURL.getGroupId()) ||
				(privateLayout != layoutSEOCanonicalURL.isPrivateLayout()) ||
				(layoutId != layoutSEOCanonicalURL.getLayoutId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_LAYOUTSEOCANONICALURL_WHERE);

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

				List<LayoutSEOCanonicalURL> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(
						_finderPathFetchByG_P_L, finderArgs, list);
				}
				else {
					LayoutSEOCanonicalURL layoutSEOCanonicalURL = list.get(0);

					result = layoutSEOCanonicalURL;

					cacheResult(layoutSEOCanonicalURL);
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
			return (LayoutSEOCanonicalURL)result;
		}
	}

	/**
	 * Removes the layout seo canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the layout seo canonical url that was removed
	 */
	@Override
	public LayoutSEOCanonicalURL removeByG_P_L(
			long groupId, boolean privateLayout, long layoutId)
		throws NoSuchCanonicalURLException {

		LayoutSEOCanonicalURL layoutSEOCanonicalURL = findByG_P_L(
			groupId, privateLayout, layoutId);

		return remove(layoutSEOCanonicalURL);
	}

	/**
	 * Returns the number of layout seo canonical urls where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the number of matching layout seo canonical urls
	 */
	@Override
	public int countByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		FinderPath finderPath = _finderPathCountByG_P_L;

		Object[] finderArgs = new Object[] {groupId, privateLayout, layoutId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTSEOCANONICALURL_WHERE);

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
		"layoutSEOCanonicalURL.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2 =
		"layoutSEOCanonicalURL.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_LAYOUTID_2 =
		"layoutSEOCanonicalURL.layoutId = ?";

	public LayoutSEOCanonicalURLPersistenceImpl() {
		setModelClass(LayoutSEOCanonicalURL.class);

		setModelImplClass(LayoutSEOCanonicalURLImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the layout seo canonical url in the entity cache if it is enabled.
	 *
	 * @param layoutSEOCanonicalURL the layout seo canonical url
	 */
	@Override
	public void cacheResult(LayoutSEOCanonicalURL layoutSEOCanonicalURL) {
		entityCache.putResult(
			entityCacheEnabled, LayoutSEOCanonicalURLImpl.class,
			layoutSEOCanonicalURL.getPrimaryKey(), layoutSEOCanonicalURL);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				layoutSEOCanonicalURL.getUuid(),
				layoutSEOCanonicalURL.getGroupId()
			},
			layoutSEOCanonicalURL);

		finderCache.putResult(
			_finderPathFetchByG_P_L,
			new Object[] {
				layoutSEOCanonicalURL.getGroupId(),
				layoutSEOCanonicalURL.isPrivateLayout(),
				layoutSEOCanonicalURL.getLayoutId()
			},
			layoutSEOCanonicalURL);

		layoutSEOCanonicalURL.resetOriginalValues();
	}

	/**
	 * Caches the layout seo canonical urls in the entity cache if it is enabled.
	 *
	 * @param layoutSEOCanonicalURLs the layout seo canonical urls
	 */
	@Override
	public void cacheResult(
		List<LayoutSEOCanonicalURL> layoutSEOCanonicalURLs) {

		for (LayoutSEOCanonicalURL layoutSEOCanonicalURL :
				layoutSEOCanonicalURLs) {

			if (entityCache.getResult(
					entityCacheEnabled, LayoutSEOCanonicalURLImpl.class,
					layoutSEOCanonicalURL.getPrimaryKey()) == null) {

				cacheResult(layoutSEOCanonicalURL);
			}
			else {
				layoutSEOCanonicalURL.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout seo canonical urls.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LayoutSEOCanonicalURLImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout seo canonical url.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutSEOCanonicalURL layoutSEOCanonicalURL) {
		entityCache.removeResult(
			entityCacheEnabled, LayoutSEOCanonicalURLImpl.class,
			layoutSEOCanonicalURL.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(LayoutSEOCanonicalURLModelImpl)layoutSEOCanonicalURL, true);
	}

	@Override
	public void clearCache(List<LayoutSEOCanonicalURL> layoutSEOCanonicalURLs) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutSEOCanonicalURL layoutSEOCanonicalURL :
				layoutSEOCanonicalURLs) {

			entityCache.removeResult(
				entityCacheEnabled, LayoutSEOCanonicalURLImpl.class,
				layoutSEOCanonicalURL.getPrimaryKey());

			clearUniqueFindersCache(
				(LayoutSEOCanonicalURLModelImpl)layoutSEOCanonicalURL, true);
		}
	}

	protected void cacheUniqueFindersCache(
		LayoutSEOCanonicalURLModelImpl layoutSEOCanonicalURLModelImpl) {

		Object[] args = new Object[] {
			layoutSEOCanonicalURLModelImpl.getUuid(),
			layoutSEOCanonicalURLModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, layoutSEOCanonicalURLModelImpl,
			false);

		args = new Object[] {
			layoutSEOCanonicalURLModelImpl.getGroupId(),
			layoutSEOCanonicalURLModelImpl.isPrivateLayout(),
			layoutSEOCanonicalURLModelImpl.getLayoutId()
		};

		finderCache.putResult(
			_finderPathCountByG_P_L, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_P_L, args, layoutSEOCanonicalURLModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		LayoutSEOCanonicalURLModelImpl layoutSEOCanonicalURLModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				layoutSEOCanonicalURLModelImpl.getUuid(),
				layoutSEOCanonicalURLModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((layoutSEOCanonicalURLModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				layoutSEOCanonicalURLModelImpl.getOriginalUuid(),
				layoutSEOCanonicalURLModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				layoutSEOCanonicalURLModelImpl.getGroupId(),
				layoutSEOCanonicalURLModelImpl.isPrivateLayout(),
				layoutSEOCanonicalURLModelImpl.getLayoutId()
			};

			finderCache.removeResult(_finderPathCountByG_P_L, args);
			finderCache.removeResult(_finderPathFetchByG_P_L, args);
		}

		if ((layoutSEOCanonicalURLModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_P_L.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				layoutSEOCanonicalURLModelImpl.getOriginalGroupId(),
				layoutSEOCanonicalURLModelImpl.getOriginalPrivateLayout(),
				layoutSEOCanonicalURLModelImpl.getOriginalLayoutId()
			};

			finderCache.removeResult(_finderPathCountByG_P_L, args);
			finderCache.removeResult(_finderPathFetchByG_P_L, args);
		}
	}

	/**
	 * Creates a new layout seo canonical url with the primary key. Does not add the layout seo canonical url to the database.
	 *
	 * @param layoutSEOCanonicalURLId the primary key for the new layout seo canonical url
	 * @return the new layout seo canonical url
	 */
	@Override
	public LayoutSEOCanonicalURL create(long layoutSEOCanonicalURLId) {
		LayoutSEOCanonicalURL layoutSEOCanonicalURL =
			new LayoutSEOCanonicalURLImpl();

		layoutSEOCanonicalURL.setNew(true);
		layoutSEOCanonicalURL.setPrimaryKey(layoutSEOCanonicalURLId);

		String uuid = PortalUUIDUtil.generate();

		layoutSEOCanonicalURL.setUuid(uuid);

		layoutSEOCanonicalURL.setCompanyId(CompanyThreadLocal.getCompanyId());

		return layoutSEOCanonicalURL;
	}

	/**
	 * Removes the layout seo canonical url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOCanonicalURLId the primary key of the layout seo canonical url
	 * @return the layout seo canonical url that was removed
	 * @throws NoSuchCanonicalURLException if a layout seo canonical url with the primary key could not be found
	 */
	@Override
	public LayoutSEOCanonicalURL remove(long layoutSEOCanonicalURLId)
		throws NoSuchCanonicalURLException {

		return remove((Serializable)layoutSEOCanonicalURLId);
	}

	/**
	 * Removes the layout seo canonical url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout seo canonical url
	 * @return the layout seo canonical url that was removed
	 * @throws NoSuchCanonicalURLException if a layout seo canonical url with the primary key could not be found
	 */
	@Override
	public LayoutSEOCanonicalURL remove(Serializable primaryKey)
		throws NoSuchCanonicalURLException {

		Session session = null;

		try {
			session = openSession();

			LayoutSEOCanonicalURL layoutSEOCanonicalURL =
				(LayoutSEOCanonicalURL)session.get(
					LayoutSEOCanonicalURLImpl.class, primaryKey);

			if (layoutSEOCanonicalURL == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCanonicalURLException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(layoutSEOCanonicalURL);
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
	protected LayoutSEOCanonicalURL removeImpl(
		LayoutSEOCanonicalURL layoutSEOCanonicalURL) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutSEOCanonicalURL)) {
				layoutSEOCanonicalURL = (LayoutSEOCanonicalURL)session.get(
					LayoutSEOCanonicalURLImpl.class,
					layoutSEOCanonicalURL.getPrimaryKeyObj());
			}

			if (layoutSEOCanonicalURL != null) {
				session.delete(layoutSEOCanonicalURL);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutSEOCanonicalURL != null) {
			clearCache(layoutSEOCanonicalURL);
		}

		return layoutSEOCanonicalURL;
	}

	@Override
	public LayoutSEOCanonicalURL updateImpl(
		LayoutSEOCanonicalURL layoutSEOCanonicalURL) {

		boolean isNew = layoutSEOCanonicalURL.isNew();

		if (!(layoutSEOCanonicalURL instanceof
				LayoutSEOCanonicalURLModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(layoutSEOCanonicalURL.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					layoutSEOCanonicalURL);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in layoutSEOCanonicalURL proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LayoutSEOCanonicalURL implementation " +
					layoutSEOCanonicalURL.getClass());
		}

		LayoutSEOCanonicalURLModelImpl layoutSEOCanonicalURLModelImpl =
			(LayoutSEOCanonicalURLModelImpl)layoutSEOCanonicalURL;

		if (Validator.isNull(layoutSEOCanonicalURL.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			layoutSEOCanonicalURL.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (layoutSEOCanonicalURL.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutSEOCanonicalURL.setCreateDate(now);
			}
			else {
				layoutSEOCanonicalURL.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!layoutSEOCanonicalURLModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutSEOCanonicalURL.setModifiedDate(now);
			}
			else {
				layoutSEOCanonicalURL.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (layoutSEOCanonicalURL.isNew()) {
				session.save(layoutSEOCanonicalURL);

				layoutSEOCanonicalURL.setNew(false);
			}
			else {
				layoutSEOCanonicalURL = (LayoutSEOCanonicalURL)session.merge(
					layoutSEOCanonicalURL);
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
				layoutSEOCanonicalURLModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				layoutSEOCanonicalURLModelImpl.getUuid(),
				layoutSEOCanonicalURLModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((layoutSEOCanonicalURLModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutSEOCanonicalURLModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {layoutSEOCanonicalURLModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((layoutSEOCanonicalURLModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutSEOCanonicalURLModelImpl.getOriginalUuid(),
					layoutSEOCanonicalURLModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					layoutSEOCanonicalURLModelImpl.getUuid(),
					layoutSEOCanonicalURLModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, LayoutSEOCanonicalURLImpl.class,
			layoutSEOCanonicalURL.getPrimaryKey(), layoutSEOCanonicalURL,
			false);

		clearUniqueFindersCache(layoutSEOCanonicalURLModelImpl, false);
		cacheUniqueFindersCache(layoutSEOCanonicalURLModelImpl);

		layoutSEOCanonicalURL.resetOriginalValues();

		return layoutSEOCanonicalURL;
	}

	/**
	 * Returns the layout seo canonical url with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout seo canonical url
	 * @return the layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a layout seo canonical url with the primary key could not be found
	 */
	@Override
	public LayoutSEOCanonicalURL findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCanonicalURLException {

		LayoutSEOCanonicalURL layoutSEOCanonicalURL = fetchByPrimaryKey(
			primaryKey);

		if (layoutSEOCanonicalURL == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCanonicalURLException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return layoutSEOCanonicalURL;
	}

	/**
	 * Returns the layout seo canonical url with the primary key or throws a <code>NoSuchCanonicalURLException</code> if it could not be found.
	 *
	 * @param layoutSEOCanonicalURLId the primary key of the layout seo canonical url
	 * @return the layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a layout seo canonical url with the primary key could not be found
	 */
	@Override
	public LayoutSEOCanonicalURL findByPrimaryKey(long layoutSEOCanonicalURLId)
		throws NoSuchCanonicalURLException {

		return findByPrimaryKey((Serializable)layoutSEOCanonicalURLId);
	}

	/**
	 * Returns the layout seo canonical url with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutSEOCanonicalURLId the primary key of the layout seo canonical url
	 * @return the layout seo canonical url, or <code>null</code> if a layout seo canonical url with the primary key could not be found
	 */
	@Override
	public LayoutSEOCanonicalURL fetchByPrimaryKey(
		long layoutSEOCanonicalURLId) {

		return fetchByPrimaryKey((Serializable)layoutSEOCanonicalURLId);
	}

	/**
	 * Returns all the layout seo canonical urls.
	 *
	 * @return the layout seo canonical urls
	 */
	@Override
	public List<LayoutSEOCanonicalURL> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout seo canonical urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @return the range of layout seo canonical urls
	 */
	@Override
	public List<LayoutSEOCanonicalURL> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout seo canonical urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #findAll(int, int, OrderByComparator)}
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout seo canonical urls
	 */
	@Deprecated
	@Override
	public List<LayoutSEOCanonicalURL> findAll(
		int start, int end,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator,
		boolean useFinderCache) {

		return findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout seo canonical urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout seo canonical urls
	 */
	@Override
	public List<LayoutSEOCanonicalURL> findAll(
		int start, int end,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator) {

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

		List<LayoutSEOCanonicalURL> list =
			(List<LayoutSEOCanonicalURL>)finderCache.getResult(
				finderPath, finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LAYOUTSEOCANONICALURL);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTSEOCANONICALURL;

				if (pagination) {
					sql = sql.concat(
						LayoutSEOCanonicalURLModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LayoutSEOCanonicalURL>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSEOCanonicalURL>)QueryUtil.list(
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
	 * Removes all the layout seo canonical urls from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutSEOCanonicalURL layoutSEOCanonicalURL : findAll()) {
			remove(layoutSEOCanonicalURL);
		}
	}

	/**
	 * Returns the number of layout seo canonical urls.
	 *
	 * @return the number of layout seo canonical urls
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUTSEOCANONICALURL);

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
		return "layoutSEOCanonicalURLId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAYOUTSEOCANONICALURL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LayoutSEOCanonicalURLModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout seo canonical url persistence.
	 */
	@Activate
	public void activate() {
		LayoutSEOCanonicalURLModelImpl.setEntityCacheEnabled(
			entityCacheEnabled);
		LayoutSEOCanonicalURLModelImpl.setFinderCacheEnabled(
			finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutSEOCanonicalURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutSEOCanonicalURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutSEOCanonicalURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutSEOCanonicalURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			LayoutSEOCanonicalURLModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutSEOCanonicalURLImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			LayoutSEOCanonicalURLModelImpl.UUID_COLUMN_BITMASK |
			LayoutSEOCanonicalURLModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutSEOCanonicalURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutSEOCanonicalURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			LayoutSEOCanonicalURLModelImpl.UUID_COLUMN_BITMASK |
			LayoutSEOCanonicalURLModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathFetchByG_P_L = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			LayoutSEOCanonicalURLImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			},
			LayoutSEOCanonicalURLModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutSEOCanonicalURLModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutSEOCanonicalURLModelImpl.LAYOUTID_COLUMN_BITMASK);

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
		entityCache.removeCache(LayoutSEOCanonicalURLImpl.class.getName());
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
				"value.object.column.bitmask.enabled.com.liferay.layout.seo.model.LayoutSEOCanonicalURL"),
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

	private static final String _SQL_SELECT_LAYOUTSEOCANONICALURL =
		"SELECT layoutSEOCanonicalURL FROM LayoutSEOCanonicalURL layoutSEOCanonicalURL";

	private static final String _SQL_SELECT_LAYOUTSEOCANONICALURL_WHERE =
		"SELECT layoutSEOCanonicalURL FROM LayoutSEOCanonicalURL layoutSEOCanonicalURL WHERE ";

	private static final String _SQL_COUNT_LAYOUTSEOCANONICALURL =
		"SELECT COUNT(layoutSEOCanonicalURL) FROM LayoutSEOCanonicalURL layoutSEOCanonicalURL";

	private static final String _SQL_COUNT_LAYOUTSEOCANONICALURL_WHERE =
		"SELECT COUNT(layoutSEOCanonicalURL) FROM LayoutSEOCanonicalURL layoutSEOCanonicalURL WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"layoutSEOCanonicalURL.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LayoutSEOCanonicalURL exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LayoutSEOCanonicalURL exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSEOCanonicalURLPersistenceImpl.class);

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