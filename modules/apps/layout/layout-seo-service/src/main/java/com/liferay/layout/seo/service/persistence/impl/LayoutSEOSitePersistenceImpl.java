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

import com.liferay.layout.seo.exception.NoSuchSiteException;
import com.liferay.layout.seo.model.LayoutSEOSite;
import com.liferay.layout.seo.model.LayoutSEOSiteTable;
import com.liferay.layout.seo.model.impl.LayoutSEOSiteImpl;
import com.liferay.layout.seo.model.impl.LayoutSEOSiteModelImpl;
import com.liferay.layout.seo.service.persistence.LayoutSEOSitePersistence;
import com.liferay.layout.seo.service.persistence.impl.constants.LayoutSEOPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
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
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
 * The persistence implementation for the layout seo site service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {LayoutSEOSitePersistence.class, BasePersistence.class})
public class LayoutSEOSitePersistenceImpl
	extends BasePersistenceImpl<LayoutSEOSite>
	implements LayoutSEOSitePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LayoutSEOSiteUtil</code> to access the layout seo site persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LayoutSEOSiteImpl.class.getName();

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
	 * Returns all the layout seo sites where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout seo sites
	 */
	@Override
	public List<LayoutSEOSite> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout seo sites where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @return the range of matching layout seo sites
	 */
	@Override
	public List<LayoutSEOSite> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout seo sites where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo sites
	 */
	@Override
	public List<LayoutSEOSite> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutSEOSite> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout seo sites where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo sites
	 */
	@Override
	public List<LayoutSEOSite> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutSEOSite> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutSEOSite.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<LayoutSEOSite> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutSEOSite>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutSEOSite layoutSEOSite : list) {
					if (!uuid.equals(layoutSEOSite.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_LAYOUTSEOSITE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutSEOSiteModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<LayoutSEOSite>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout seo site in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo site
	 * @throws NoSuchSiteException if a matching layout seo site could not be found
	 */
	@Override
	public LayoutSEOSite findByUuid_First(
			String uuid, OrderByComparator<LayoutSEOSite> orderByComparator)
		throws NoSuchSiteException {

		LayoutSEOSite layoutSEOSite = fetchByUuid_First(
			uuid, orderByComparator);

		if (layoutSEOSite != null) {
			return layoutSEOSite;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchSiteException(sb.toString());
	}

	/**
	 * Returns the first layout seo site in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	@Override
	public LayoutSEOSite fetchByUuid_First(
		String uuid, OrderByComparator<LayoutSEOSite> orderByComparator) {

		List<LayoutSEOSite> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout seo site in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo site
	 * @throws NoSuchSiteException if a matching layout seo site could not be found
	 */
	@Override
	public LayoutSEOSite findByUuid_Last(
			String uuid, OrderByComparator<LayoutSEOSite> orderByComparator)
		throws NoSuchSiteException {

		LayoutSEOSite layoutSEOSite = fetchByUuid_Last(uuid, orderByComparator);

		if (layoutSEOSite != null) {
			return layoutSEOSite;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchSiteException(sb.toString());
	}

	/**
	 * Returns the last layout seo site in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	@Override
	public LayoutSEOSite fetchByUuid_Last(
		String uuid, OrderByComparator<LayoutSEOSite> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<LayoutSEOSite> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout seo sites before and after the current layout seo site in the ordered set where uuid = &#63;.
	 *
	 * @param layoutSEOSiteId the primary key of the current layout seo site
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo site
	 * @throws NoSuchSiteException if a layout seo site with the primary key could not be found
	 */
	@Override
	public LayoutSEOSite[] findByUuid_PrevAndNext(
			long layoutSEOSiteId, String uuid,
			OrderByComparator<LayoutSEOSite> orderByComparator)
		throws NoSuchSiteException {

		uuid = Objects.toString(uuid, "");

		LayoutSEOSite layoutSEOSite = findByPrimaryKey(layoutSEOSiteId);

		Session session = null;

		try {
			session = openSession();

			LayoutSEOSite[] array = new LayoutSEOSiteImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, layoutSEOSite, uuid, orderByComparator, true);

			array[1] = layoutSEOSite;

			array[2] = getByUuid_PrevAndNext(
				session, layoutSEOSite, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutSEOSite getByUuid_PrevAndNext(
		Session session, LayoutSEOSite layoutSEOSite, String uuid,
		OrderByComparator<LayoutSEOSite> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_LAYOUTSEOSITE_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(LayoutSEOSiteModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutSEOSite)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutSEOSite> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout seo sites where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (LayoutSEOSite layoutSEOSite :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutSEOSite);
		}
	}

	/**
	 * Returns the number of layout seo sites where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout seo sites
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutSEOSite.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LAYOUTSEOSITE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"layoutSEOSite.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(layoutSEOSite.uuid IS NULL OR layoutSEOSite.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the layout seo site where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSiteException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout seo site
	 * @throws NoSuchSiteException if a matching layout seo site could not be found
	 */
	@Override
	public LayoutSEOSite findByUUID_G(String uuid, long groupId)
		throws NoSuchSiteException {

		LayoutSEOSite layoutSEOSite = fetchByUUID_G(uuid, groupId);

		if (layoutSEOSite == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchSiteException(sb.toString());
		}

		return layoutSEOSite;
	}

	/**
	 * Returns the layout seo site where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	@Override
	public LayoutSEOSite fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the layout seo site where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	@Override
	public LayoutSEOSite fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutSEOSite.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs);
		}

		if (result instanceof LayoutSEOSite) {
			LayoutSEOSite layoutSEOSite = (LayoutSEOSite)result;

			if (!Objects.equals(uuid, layoutSEOSite.getUuid()) ||
				(groupId != layoutSEOSite.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_LAYOUTSEOSITE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				List<LayoutSEOSite> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					LayoutSEOSite layoutSEOSite = list.get(0);

					result = layoutSEOSite;

					cacheResult(layoutSEOSite);
				}
			}
			catch (Exception exception) {
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
			return (LayoutSEOSite)result;
		}
	}

	/**
	 * Removes the layout seo site where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout seo site that was removed
	 */
	@Override
	public LayoutSEOSite removeByUUID_G(String uuid, long groupId)
		throws NoSuchSiteException {

		LayoutSEOSite layoutSEOSite = findByUUID_G(uuid, groupId);

		return remove(layoutSEOSite);
	}

	/**
	 * Returns the number of layout seo sites where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout seo sites
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutSEOSite.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUUID_G;

			finderArgs = new Object[] {uuid, groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LAYOUTSEOSITE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"layoutSEOSite.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(layoutSEOSite.uuid IS NULL OR layoutSEOSite.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"layoutSEOSite.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the layout seo sites where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout seo sites
	 */
	@Override
	public List<LayoutSEOSite> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout seo sites where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @return the range of matching layout seo sites
	 */
	@Override
	public List<LayoutSEOSite> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout seo sites where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo sites
	 */
	@Override
	public List<LayoutSEOSite> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutSEOSite> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout seo sites where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo sites
	 */
	@Override
	public List<LayoutSEOSite> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutSEOSite> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutSEOSite.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<LayoutSEOSite> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutSEOSite>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutSEOSite layoutSEOSite : list) {
					if (!uuid.equals(layoutSEOSite.getUuid()) ||
						(companyId != layoutSEOSite.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_LAYOUTSEOSITE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutSEOSiteModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<LayoutSEOSite>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout seo site in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo site
	 * @throws NoSuchSiteException if a matching layout seo site could not be found
	 */
	@Override
	public LayoutSEOSite findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LayoutSEOSite> orderByComparator)
		throws NoSuchSiteException {

		LayoutSEOSite layoutSEOSite = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (layoutSEOSite != null) {
			return layoutSEOSite;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchSiteException(sb.toString());
	}

	/**
	 * Returns the first layout seo site in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	@Override
	public LayoutSEOSite fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LayoutSEOSite> orderByComparator) {

		List<LayoutSEOSite> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout seo site in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo site
	 * @throws NoSuchSiteException if a matching layout seo site could not be found
	 */
	@Override
	public LayoutSEOSite findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LayoutSEOSite> orderByComparator)
		throws NoSuchSiteException {

		LayoutSEOSite layoutSEOSite = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (layoutSEOSite != null) {
			return layoutSEOSite;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchSiteException(sb.toString());
	}

	/**
	 * Returns the last layout seo site in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	@Override
	public LayoutSEOSite fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LayoutSEOSite> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<LayoutSEOSite> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout seo sites before and after the current layout seo site in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutSEOSiteId the primary key of the current layout seo site
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo site
	 * @throws NoSuchSiteException if a layout seo site with the primary key could not be found
	 */
	@Override
	public LayoutSEOSite[] findByUuid_C_PrevAndNext(
			long layoutSEOSiteId, String uuid, long companyId,
			OrderByComparator<LayoutSEOSite> orderByComparator)
		throws NoSuchSiteException {

		uuid = Objects.toString(uuid, "");

		LayoutSEOSite layoutSEOSite = findByPrimaryKey(layoutSEOSiteId);

		Session session = null;

		try {
			session = openSession();

			LayoutSEOSite[] array = new LayoutSEOSiteImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, layoutSEOSite, uuid, companyId, orderByComparator,
				true);

			array[1] = layoutSEOSite;

			array[2] = getByUuid_C_PrevAndNext(
				session, layoutSEOSite, uuid, companyId, orderByComparator,
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

	protected LayoutSEOSite getByUuid_C_PrevAndNext(
		Session session, LayoutSEOSite layoutSEOSite, String uuid,
		long companyId, OrderByComparator<LayoutSEOSite> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_LAYOUTSEOSITE_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(LayoutSEOSiteModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutSEOSite)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutSEOSite> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout seo sites where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (LayoutSEOSite layoutSEOSite :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutSEOSite);
		}
	}

	/**
	 * Returns the number of layout seo sites where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout seo sites
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutSEOSite.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LAYOUTSEOSITE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"layoutSEOSite.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(layoutSEOSite.uuid IS NULL OR layoutSEOSite.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"layoutSEOSite.companyId = ?";

	private FinderPath _finderPathFetchByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns the layout seo site where groupId = &#63; or throws a <code>NoSuchSiteException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @return the matching layout seo site
	 * @throws NoSuchSiteException if a matching layout seo site could not be found
	 */
	@Override
	public LayoutSEOSite findByGroupId(long groupId)
		throws NoSuchSiteException {

		LayoutSEOSite layoutSEOSite = fetchByGroupId(groupId);

		if (layoutSEOSite == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchSiteException(sb.toString());
		}

		return layoutSEOSite;
	}

	/**
	 * Returns the layout seo site where groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @return the matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	@Override
	public LayoutSEOSite fetchByGroupId(long groupId) {
		return fetchByGroupId(groupId, true);
	}

	/**
	 * Returns the layout seo site where groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	@Override
	public LayoutSEOSite fetchByGroupId(long groupId, boolean useFinderCache) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutSEOSite.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByGroupId, finderArgs);
		}

		if (result instanceof LayoutSEOSite) {
			LayoutSEOSite layoutSEOSite = (LayoutSEOSite)result;

			if (groupId != layoutSEOSite.getGroupId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_LAYOUTSEOSITE_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				List<LayoutSEOSite> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByGroupId, finderArgs, list);
					}
				}
				else {
					LayoutSEOSite layoutSEOSite = list.get(0);

					result = layoutSEOSite;

					cacheResult(layoutSEOSite);
				}
			}
			catch (Exception exception) {
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
			return (LayoutSEOSite)result;
		}
	}

	/**
	 * Removes the layout seo site where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @return the layout seo site that was removed
	 */
	@Override
	public LayoutSEOSite removeByGroupId(long groupId)
		throws NoSuchSiteException {

		LayoutSEOSite layoutSEOSite = findByGroupId(groupId);

		return remove(layoutSEOSite);
	}

	/**
	 * Returns the number of layout seo sites where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout seo sites
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutSEOSite.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByGroupId;

			finderArgs = new Object[] {groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LAYOUTSEOSITE_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"layoutSEOSite.groupId = ?";

	public LayoutSEOSitePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(LayoutSEOSite.class);

		setModelImplClass(LayoutSEOSiteImpl.class);
		setModelPKClass(long.class);

		setTable(LayoutSEOSiteTable.INSTANCE);
	}

	/**
	 * Caches the layout seo site in the entity cache if it is enabled.
	 *
	 * @param layoutSEOSite the layout seo site
	 */
	@Override
	public void cacheResult(LayoutSEOSite layoutSEOSite) {
		if (layoutSEOSite.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			LayoutSEOSiteImpl.class, layoutSEOSite.getPrimaryKey(),
			layoutSEOSite);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {layoutSEOSite.getUuid(), layoutSEOSite.getGroupId()},
			layoutSEOSite);

		finderCache.putResult(
			_finderPathFetchByGroupId,
			new Object[] {layoutSEOSite.getGroupId()}, layoutSEOSite);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the layout seo sites in the entity cache if it is enabled.
	 *
	 * @param layoutSEOSites the layout seo sites
	 */
	@Override
	public void cacheResult(List<LayoutSEOSite> layoutSEOSites) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (layoutSEOSites.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (LayoutSEOSite layoutSEOSite : layoutSEOSites) {
			if (layoutSEOSite.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					LayoutSEOSiteImpl.class, layoutSEOSite.getPrimaryKey()) ==
						null) {

				cacheResult(layoutSEOSite);
			}
		}
	}

	/**
	 * Clears the cache for all layout seo sites.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LayoutSEOSiteImpl.class);

		finderCache.clearCache(LayoutSEOSiteImpl.class);
	}

	/**
	 * Clears the cache for the layout seo site.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutSEOSite layoutSEOSite) {
		entityCache.removeResult(LayoutSEOSiteImpl.class, layoutSEOSite);
	}

	@Override
	public void clearCache(List<LayoutSEOSite> layoutSEOSites) {
		for (LayoutSEOSite layoutSEOSite : layoutSEOSites) {
			entityCache.removeResult(LayoutSEOSiteImpl.class, layoutSEOSite);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(LayoutSEOSiteImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(LayoutSEOSiteImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LayoutSEOSiteModelImpl layoutSEOSiteModelImpl) {

		Object[] args = new Object[] {
			layoutSEOSiteModelImpl.getUuid(),
			layoutSEOSiteModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, layoutSEOSiteModelImpl);

		args = new Object[] {layoutSEOSiteModelImpl.getGroupId()};

		finderCache.putResult(_finderPathCountByGroupId, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByGroupId, args, layoutSEOSiteModelImpl);
	}

	/**
	 * Creates a new layout seo site with the primary key. Does not add the layout seo site to the database.
	 *
	 * @param layoutSEOSiteId the primary key for the new layout seo site
	 * @return the new layout seo site
	 */
	@Override
	public LayoutSEOSite create(long layoutSEOSiteId) {
		LayoutSEOSite layoutSEOSite = new LayoutSEOSiteImpl();

		layoutSEOSite.setNew(true);
		layoutSEOSite.setPrimaryKey(layoutSEOSiteId);

		String uuid = PortalUUIDUtil.generate();

		layoutSEOSite.setUuid(uuid);

		layoutSEOSite.setCompanyId(CompanyThreadLocal.getCompanyId());

		return layoutSEOSite;
	}

	/**
	 * Removes the layout seo site with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOSiteId the primary key of the layout seo site
	 * @return the layout seo site that was removed
	 * @throws NoSuchSiteException if a layout seo site with the primary key could not be found
	 */
	@Override
	public LayoutSEOSite remove(long layoutSEOSiteId)
		throws NoSuchSiteException {

		return remove((Serializable)layoutSEOSiteId);
	}

	/**
	 * Removes the layout seo site with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout seo site
	 * @return the layout seo site that was removed
	 * @throws NoSuchSiteException if a layout seo site with the primary key could not be found
	 */
	@Override
	public LayoutSEOSite remove(Serializable primaryKey)
		throws NoSuchSiteException {

		Session session = null;

		try {
			session = openSession();

			LayoutSEOSite layoutSEOSite = (LayoutSEOSite)session.get(
				LayoutSEOSiteImpl.class, primaryKey);

			if (layoutSEOSite == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSiteException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(layoutSEOSite);
		}
		catch (NoSuchSiteException noSuchEntityException) {
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
	protected LayoutSEOSite removeImpl(LayoutSEOSite layoutSEOSite) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutSEOSite)) {
				layoutSEOSite = (LayoutSEOSite)session.get(
					LayoutSEOSiteImpl.class, layoutSEOSite.getPrimaryKeyObj());
			}

			if ((layoutSEOSite != null) &&
				ctPersistenceHelper.isRemove(layoutSEOSite)) {

				session.delete(layoutSEOSite);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (layoutSEOSite != null) {
			clearCache(layoutSEOSite);
		}

		return layoutSEOSite;
	}

	@Override
	public LayoutSEOSite updateImpl(LayoutSEOSite layoutSEOSite) {
		boolean isNew = layoutSEOSite.isNew();

		if (!(layoutSEOSite instanceof LayoutSEOSiteModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(layoutSEOSite.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					layoutSEOSite);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in layoutSEOSite proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LayoutSEOSite implementation " +
					layoutSEOSite.getClass());
		}

		LayoutSEOSiteModelImpl layoutSEOSiteModelImpl =
			(LayoutSEOSiteModelImpl)layoutSEOSite;

		if (Validator.isNull(layoutSEOSite.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			layoutSEOSite.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (layoutSEOSite.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutSEOSite.setCreateDate(date);
			}
			else {
				layoutSEOSite.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!layoutSEOSiteModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutSEOSite.setModifiedDate(date);
			}
			else {
				layoutSEOSite.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(layoutSEOSite)) {
				if (!isNew) {
					session.evict(
						LayoutSEOSiteImpl.class,
						layoutSEOSite.getPrimaryKeyObj());
				}

				session.save(layoutSEOSite);
			}
			else {
				layoutSEOSite = (LayoutSEOSite)session.merge(layoutSEOSite);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (layoutSEOSite.getCtCollectionId() != 0) {
			if (isNew) {
				layoutSEOSite.setNew(false);
			}

			layoutSEOSite.resetOriginalValues();

			return layoutSEOSite;
		}

		entityCache.putResult(
			LayoutSEOSiteImpl.class, layoutSEOSiteModelImpl, false, true);

		cacheUniqueFindersCache(layoutSEOSiteModelImpl);

		if (isNew) {
			layoutSEOSite.setNew(false);
		}

		layoutSEOSite.resetOriginalValues();

		return layoutSEOSite;
	}

	/**
	 * Returns the layout seo site with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout seo site
	 * @return the layout seo site
	 * @throws NoSuchSiteException if a layout seo site with the primary key could not be found
	 */
	@Override
	public LayoutSEOSite findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSiteException {

		LayoutSEOSite layoutSEOSite = fetchByPrimaryKey(primaryKey);

		if (layoutSEOSite == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSiteException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return layoutSEOSite;
	}

	/**
	 * Returns the layout seo site with the primary key or throws a <code>NoSuchSiteException</code> if it could not be found.
	 *
	 * @param layoutSEOSiteId the primary key of the layout seo site
	 * @return the layout seo site
	 * @throws NoSuchSiteException if a layout seo site with the primary key could not be found
	 */
	@Override
	public LayoutSEOSite findByPrimaryKey(long layoutSEOSiteId)
		throws NoSuchSiteException {

		return findByPrimaryKey((Serializable)layoutSEOSiteId);
	}

	/**
	 * Returns the layout seo site with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout seo site
	 * @return the layout seo site, or <code>null</code> if a layout seo site with the primary key could not be found
	 */
	@Override
	public LayoutSEOSite fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(LayoutSEOSite.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		LayoutSEOSite layoutSEOSite = null;

		Session session = null;

		try {
			session = openSession();

			layoutSEOSite = (LayoutSEOSite)session.get(
				LayoutSEOSiteImpl.class, primaryKey);

			if (layoutSEOSite != null) {
				cacheResult(layoutSEOSite);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return layoutSEOSite;
	}

	/**
	 * Returns the layout seo site with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutSEOSiteId the primary key of the layout seo site
	 * @return the layout seo site, or <code>null</code> if a layout seo site with the primary key could not be found
	 */
	@Override
	public LayoutSEOSite fetchByPrimaryKey(long layoutSEOSiteId) {
		return fetchByPrimaryKey((Serializable)layoutSEOSiteId);
	}

	@Override
	public Map<Serializable, LayoutSEOSite> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(LayoutSEOSite.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LayoutSEOSite> map =
			new HashMap<Serializable, LayoutSEOSite>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LayoutSEOSite layoutSEOSite = fetchByPrimaryKey(primaryKey);

			if (layoutSEOSite != null) {
				map.put(primaryKey, layoutSEOSite);
			}

			return map;
		}

		if ((databaseInMaxParameters > 0) &&
			(primaryKeys.size() > databaseInMaxParameters)) {

			Iterator<Serializable> iterator = primaryKeys.iterator();

			while (iterator.hasNext()) {
				Set<Serializable> page = new HashSet<>();

				for (int i = 0;
					 (i < databaseInMaxParameters) && iterator.hasNext(); i++) {

					page.add(iterator.next());
				}

				map.putAll(fetchByPrimaryKeys(page));
			}

			return map;
		}

		StringBundler sb = new StringBundler((primaryKeys.size() * 2) + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (LayoutSEOSite layoutSEOSite :
					(List<LayoutSEOSite>)query.list()) {

				map.put(layoutSEOSite.getPrimaryKeyObj(), layoutSEOSite);

				cacheResult(layoutSEOSite);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the layout seo sites.
	 *
	 * @return the layout seo sites
	 */
	@Override
	public List<LayoutSEOSite> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout seo sites.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @return the range of layout seo sites
	 */
	@Override
	public List<LayoutSEOSite> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout seo sites.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout seo sites
	 */
	@Override
	public List<LayoutSEOSite> findAll(
		int start, int end,
		OrderByComparator<LayoutSEOSite> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout seo sites.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout seo sites
	 */
	@Override
	public List<LayoutSEOSite> findAll(
		int start, int end, OrderByComparator<LayoutSEOSite> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutSEOSite.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<LayoutSEOSite> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutSEOSite>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LAYOUTSEOSITE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTSEOSITE;

				sql = sql.concat(LayoutSEOSiteModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LayoutSEOSite>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the layout seo sites from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutSEOSite layoutSEOSite : findAll()) {
			remove(layoutSEOSite);
		}
	}

	/**
	 * Returns the number of layout seo sites.
	 *
	 * @return the number of layout seo sites
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutSEOSite.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_LAYOUTSEOSITE);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
			}
			catch (Exception exception) {
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
		return "layoutSEOSiteId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAYOUTSEOSITE;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.getOrDefault(
			ctColumnResolutionType, Collections.emptySet());
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return LayoutSEOSiteModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "LayoutSEOSite";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("openGraphEnabled");
		ctStrictColumnNames.add("openGraphImageAlt");
		ctStrictColumnNames.add("openGraphImageFileEntryId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("layoutSEOSiteId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(new String[] {"groupId"});
	}

	/**
	 * Initializes the layout seo site persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathCountByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathFetchByGroupId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_finderPathCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(LayoutSEOSiteImpl.class.getName());
	}

	@Override
	@Reference(
		target = LayoutSEOPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
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

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_LAYOUTSEOSITE =
		"SELECT layoutSEOSite FROM LayoutSEOSite layoutSEOSite";

	private static final String _SQL_SELECT_LAYOUTSEOSITE_WHERE =
		"SELECT layoutSEOSite FROM LayoutSEOSite layoutSEOSite WHERE ";

	private static final String _SQL_COUNT_LAYOUTSEOSITE =
		"SELECT COUNT(layoutSEOSite) FROM LayoutSEOSite layoutSEOSite";

	private static final String _SQL_COUNT_LAYOUTSEOSITE_WHERE =
		"SELECT COUNT(layoutSEOSite) FROM LayoutSEOSite layoutSEOSite WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutSEOSite.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LayoutSEOSite exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LayoutSEOSite exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSEOSitePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private LayoutSEOSiteModelArgumentsResolver
		_layoutSEOSiteModelArgumentsResolver;

}