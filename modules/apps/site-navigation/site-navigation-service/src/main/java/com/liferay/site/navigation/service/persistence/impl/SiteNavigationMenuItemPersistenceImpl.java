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

package com.liferay.site.navigation.service.persistence.impl;

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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.site.navigation.exception.NoSuchMenuItemException;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.model.SiteNavigationMenuItemTable;
import com.liferay.site.navigation.model.impl.SiteNavigationMenuItemImpl;
import com.liferay.site.navigation.model.impl.SiteNavigationMenuItemModelImpl;
import com.liferay.site.navigation.service.persistence.SiteNavigationMenuItemPersistence;
import com.liferay.site.navigation.service.persistence.impl.constants.SiteNavigationPersistenceConstants;

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
 * The persistence implementation for the site navigation menu item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {SiteNavigationMenuItemPersistence.class, BasePersistence.class}
)
public class SiteNavigationMenuItemPersistenceImpl
	extends BasePersistenceImpl<SiteNavigationMenuItem>
	implements SiteNavigationMenuItemPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SiteNavigationMenuItemUtil</code> to access the site navigation menu item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SiteNavigationMenuItemImpl.class.getName();

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
	 * Returns all the site navigation menu items where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menu items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @return the range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SiteNavigationMenuItem.class);

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

		List<SiteNavigationMenuItem> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SiteNavigationMenuItem>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenuItem siteNavigationMenuItem : list) {
					if (!uuid.equals(siteNavigationMenuItem.getUuid())) {
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

			sb.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE);

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
				sb.append(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
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

				list = (List<SiteNavigationMenuItem>)QueryUtil.list(
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
	 * Returns the first site navigation menu item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu item
	 * @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem findByUuid_First(
			String uuid,
			OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {

		SiteNavigationMenuItem siteNavigationMenuItem = fetchByUuid_First(
			uuid, orderByComparator);

		if (siteNavigationMenuItem != null) {
			return siteNavigationMenuItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchMenuItemException(sb.toString());
	}

	/**
	 * Returns the first site navigation menu item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem fetchByUuid_First(
		String uuid,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		List<SiteNavigationMenuItem> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last site navigation menu item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu item
	 * @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem findByUuid_Last(
			String uuid,
			OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {

		SiteNavigationMenuItem siteNavigationMenuItem = fetchByUuid_Last(
			uuid, orderByComparator);

		if (siteNavigationMenuItem != null) {
			return siteNavigationMenuItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchMenuItemException(sb.toString());
	}

	/**
	 * Returns the last site navigation menu item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem fetchByUuid_Last(
		String uuid,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<SiteNavigationMenuItem> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the site navigation menu items before and after the current site navigation menu item in the ordered set where uuid = &#63;.
	 *
	 * @param siteNavigationMenuItemId the primary key of the current site navigation menu item
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu item
	 * @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenuItem[] findByUuid_PrevAndNext(
			long siteNavigationMenuItemId, String uuid,
			OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {

		uuid = Objects.toString(uuid, "");

		SiteNavigationMenuItem siteNavigationMenuItem = findByPrimaryKey(
			siteNavigationMenuItemId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenuItem[] array = new SiteNavigationMenuItemImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, siteNavigationMenuItem, uuid, orderByComparator, true);

			array[1] = siteNavigationMenuItem;

			array[2] = getByUuid_PrevAndNext(
				session, siteNavigationMenuItem, uuid, orderByComparator,
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

	protected SiteNavigationMenuItem getByUuid_PrevAndNext(
		Session session, SiteNavigationMenuItem siteNavigationMenuItem,
		String uuid,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE);

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
			sb.append(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
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
						siteNavigationMenuItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SiteNavigationMenuItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the site navigation menu items where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (SiteNavigationMenuItem siteNavigationMenuItem :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(siteNavigationMenuItem);
		}
	}

	/**
	 * Returns the number of site navigation menu items where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching site navigation menu items
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SiteNavigationMenuItem.class);

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

			sb.append(_SQL_COUNT_SITENAVIGATIONMENUITEM_WHERE);

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
		"siteNavigationMenuItem.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(siteNavigationMenuItem.uuid IS NULL OR siteNavigationMenuItem.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the site navigation menu item where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchMenuItemException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching site navigation menu item
	 * @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem findByUUID_G(String uuid, long groupId)
		throws NoSuchMenuItemException {

		SiteNavigationMenuItem siteNavigationMenuItem = fetchByUUID_G(
			uuid, groupId);

		if (siteNavigationMenuItem == null) {
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

			throw new NoSuchMenuItemException(sb.toString());
		}

		return siteNavigationMenuItem;
	}

	/**
	 * Returns the site navigation menu item where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the site navigation menu item where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SiteNavigationMenuItem.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs);
		}

		if (result instanceof SiteNavigationMenuItem) {
			SiteNavigationMenuItem siteNavigationMenuItem =
				(SiteNavigationMenuItem)result;

			if (!Objects.equals(uuid, siteNavigationMenuItem.getUuid()) ||
				(groupId != siteNavigationMenuItem.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE);

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

				List<SiteNavigationMenuItem> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					SiteNavigationMenuItem siteNavigationMenuItem = list.get(0);

					result = siteNavigationMenuItem;

					cacheResult(siteNavigationMenuItem);
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
			return (SiteNavigationMenuItem)result;
		}
	}

	/**
	 * Removes the site navigation menu item where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the site navigation menu item that was removed
	 */
	@Override
	public SiteNavigationMenuItem removeByUUID_G(String uuid, long groupId)
		throws NoSuchMenuItemException {

		SiteNavigationMenuItem siteNavigationMenuItem = findByUUID_G(
			uuid, groupId);

		return remove(siteNavigationMenuItem);
	}

	/**
	 * Returns the number of site navigation menu items where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching site navigation menu items
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SiteNavigationMenuItem.class);

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

			sb.append(_SQL_COUNT_SITENAVIGATIONMENUITEM_WHERE);

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
		"siteNavigationMenuItem.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(siteNavigationMenuItem.uuid IS NULL OR siteNavigationMenuItem.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"siteNavigationMenuItem.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the site navigation menu items where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menu items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @return the range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SiteNavigationMenuItem.class);

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

		List<SiteNavigationMenuItem> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SiteNavigationMenuItem>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenuItem siteNavigationMenuItem : list) {
					if (!uuid.equals(siteNavigationMenuItem.getUuid()) ||
						(companyId != siteNavigationMenuItem.getCompanyId())) {

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

			sb.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE);

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
				sb.append(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
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

				list = (List<SiteNavigationMenuItem>)QueryUtil.list(
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
	 * Returns the first site navigation menu item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu item
	 * @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {

		SiteNavigationMenuItem siteNavigationMenuItem = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (siteNavigationMenuItem != null) {
			return siteNavigationMenuItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchMenuItemException(sb.toString());
	}

	/**
	 * Returns the first site navigation menu item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		List<SiteNavigationMenuItem> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last site navigation menu item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu item
	 * @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {

		SiteNavigationMenuItem siteNavigationMenuItem = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (siteNavigationMenuItem != null) {
			return siteNavigationMenuItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchMenuItemException(sb.toString());
	}

	/**
	 * Returns the last site navigation menu item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<SiteNavigationMenuItem> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the site navigation menu items before and after the current site navigation menu item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param siteNavigationMenuItemId the primary key of the current site navigation menu item
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu item
	 * @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenuItem[] findByUuid_C_PrevAndNext(
			long siteNavigationMenuItemId, String uuid, long companyId,
			OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {

		uuid = Objects.toString(uuid, "");

		SiteNavigationMenuItem siteNavigationMenuItem = findByPrimaryKey(
			siteNavigationMenuItemId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenuItem[] array = new SiteNavigationMenuItemImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, siteNavigationMenuItem, uuid, companyId,
				orderByComparator, true);

			array[1] = siteNavigationMenuItem;

			array[2] = getByUuid_C_PrevAndNext(
				session, siteNavigationMenuItem, uuid, companyId,
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

	protected SiteNavigationMenuItem getByUuid_C_PrevAndNext(
		Session session, SiteNavigationMenuItem siteNavigationMenuItem,
		String uuid, long companyId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
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

		sb.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE);

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
			sb.append(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
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
						siteNavigationMenuItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SiteNavigationMenuItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the site navigation menu items where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (SiteNavigationMenuItem siteNavigationMenuItem :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(siteNavigationMenuItem);
		}
	}

	/**
	 * Returns the number of site navigation menu items where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching site navigation menu items
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SiteNavigationMenuItem.class);

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

			sb.append(_SQL_COUNT_SITENAVIGATIONMENUITEM_WHERE);

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
		"siteNavigationMenuItem.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(siteNavigationMenuItem.uuid IS NULL OR siteNavigationMenuItem.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"siteNavigationMenuItem.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the site navigation menu items where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menu items where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @return the range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SiteNavigationMenuItem.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<SiteNavigationMenuItem> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SiteNavigationMenuItem>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenuItem siteNavigationMenuItem : list) {
					if (companyId != siteNavigationMenuItem.getCompanyId()) {
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

			sb.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<SiteNavigationMenuItem>)QueryUtil.list(
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
	 * Returns the first site navigation menu item in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu item
	 * @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem findByCompanyId_First(
			long companyId,
			OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {

		SiteNavigationMenuItem siteNavigationMenuItem = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (siteNavigationMenuItem != null) {
			return siteNavigationMenuItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchMenuItemException(sb.toString());
	}

	/**
	 * Returns the first site navigation menu item in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem fetchByCompanyId_First(
		long companyId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		List<SiteNavigationMenuItem> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last site navigation menu item in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu item
	 * @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem findByCompanyId_Last(
			long companyId,
			OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {

		SiteNavigationMenuItem siteNavigationMenuItem = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (siteNavigationMenuItem != null) {
			return siteNavigationMenuItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchMenuItemException(sb.toString());
	}

	/**
	 * Returns the last site navigation menu item in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<SiteNavigationMenuItem> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the site navigation menu items before and after the current site navigation menu item in the ordered set where companyId = &#63;.
	 *
	 * @param siteNavigationMenuItemId the primary key of the current site navigation menu item
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu item
	 * @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenuItem[] findByCompanyId_PrevAndNext(
			long siteNavigationMenuItemId, long companyId,
			OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {

		SiteNavigationMenuItem siteNavigationMenuItem = findByPrimaryKey(
			siteNavigationMenuItemId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenuItem[] array = new SiteNavigationMenuItemImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, siteNavigationMenuItem, companyId, orderByComparator,
				true);

			array[1] = siteNavigationMenuItem;

			array[2] = getByCompanyId_PrevAndNext(
				session, siteNavigationMenuItem, companyId, orderByComparator,
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

	protected SiteNavigationMenuItem getByCompanyId_PrevAndNext(
		Session session, SiteNavigationMenuItem siteNavigationMenuItem,
		long companyId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			sb.append(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						siteNavigationMenuItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SiteNavigationMenuItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the site navigation menu items where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (SiteNavigationMenuItem siteNavigationMenuItem :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(siteNavigationMenuItem);
		}
	}

	/**
	 * Returns the number of site navigation menu items where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching site navigation menu items
	 */
	@Override
	public int countByCompanyId(long companyId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SiteNavigationMenuItem.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCompanyId;

			finderArgs = new Object[] {companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SITENAVIGATIONMENUITEM_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"siteNavigationMenuItem.companyId = ?";

	private FinderPath _finderPathWithPaginationFindBySiteNavigationMenuId;
	private FinderPath _finderPathWithoutPaginationFindBySiteNavigationMenuId;
	private FinderPath _finderPathCountBySiteNavigationMenuId;

	/**
	 * Returns all the site navigation menu items where siteNavigationMenuId = &#63;.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @return the matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findBySiteNavigationMenuId(
		long siteNavigationMenuId) {

		return findBySiteNavigationMenuId(
			siteNavigationMenuId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menu items where siteNavigationMenuId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @return the range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findBySiteNavigationMenuId(
		long siteNavigationMenuId, int start, int end) {

		return findBySiteNavigationMenuId(
			siteNavigationMenuId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items where siteNavigationMenuId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findBySiteNavigationMenuId(
		long siteNavigationMenuId, int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		return findBySiteNavigationMenuId(
			siteNavigationMenuId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items where siteNavigationMenuId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findBySiteNavigationMenuId(
		long siteNavigationMenuId, int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SiteNavigationMenuItem.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindBySiteNavigationMenuId;
				finderArgs = new Object[] {siteNavigationMenuId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindBySiteNavigationMenuId;
			finderArgs = new Object[] {
				siteNavigationMenuId, start, end, orderByComparator
			};
		}

		List<SiteNavigationMenuItem> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SiteNavigationMenuItem>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenuItem siteNavigationMenuItem : list) {
					if (siteNavigationMenuId !=
							siteNavigationMenuItem.getSiteNavigationMenuId()) {

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

			sb.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE);

			sb.append(
				_FINDER_COLUMN_SITENAVIGATIONMENUID_SITENAVIGATIONMENUID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(siteNavigationMenuId);

				list = (List<SiteNavigationMenuItem>)QueryUtil.list(
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
	 * Returns the first site navigation menu item in the ordered set where siteNavigationMenuId = &#63;.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu item
	 * @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem findBySiteNavigationMenuId_First(
			long siteNavigationMenuId,
			OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {

		SiteNavigationMenuItem siteNavigationMenuItem =
			fetchBySiteNavigationMenuId_First(
				siteNavigationMenuId, orderByComparator);

		if (siteNavigationMenuItem != null) {
			return siteNavigationMenuItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("siteNavigationMenuId=");
		sb.append(siteNavigationMenuId);

		sb.append("}");

		throw new NoSuchMenuItemException(sb.toString());
	}

	/**
	 * Returns the first site navigation menu item in the ordered set where siteNavigationMenuId = &#63;.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem fetchBySiteNavigationMenuId_First(
		long siteNavigationMenuId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		List<SiteNavigationMenuItem> list = findBySiteNavigationMenuId(
			siteNavigationMenuId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last site navigation menu item in the ordered set where siteNavigationMenuId = &#63;.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu item
	 * @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem findBySiteNavigationMenuId_Last(
			long siteNavigationMenuId,
			OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {

		SiteNavigationMenuItem siteNavigationMenuItem =
			fetchBySiteNavigationMenuId_Last(
				siteNavigationMenuId, orderByComparator);

		if (siteNavigationMenuItem != null) {
			return siteNavigationMenuItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("siteNavigationMenuId=");
		sb.append(siteNavigationMenuId);

		sb.append("}");

		throw new NoSuchMenuItemException(sb.toString());
	}

	/**
	 * Returns the last site navigation menu item in the ordered set where siteNavigationMenuId = &#63;.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem fetchBySiteNavigationMenuId_Last(
		long siteNavigationMenuId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		int count = countBySiteNavigationMenuId(siteNavigationMenuId);

		if (count == 0) {
			return null;
		}

		List<SiteNavigationMenuItem> list = findBySiteNavigationMenuId(
			siteNavigationMenuId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the site navigation menu items before and after the current site navigation menu item in the ordered set where siteNavigationMenuId = &#63;.
	 *
	 * @param siteNavigationMenuItemId the primary key of the current site navigation menu item
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu item
	 * @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenuItem[] findBySiteNavigationMenuId_PrevAndNext(
			long siteNavigationMenuItemId, long siteNavigationMenuId,
			OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {

		SiteNavigationMenuItem siteNavigationMenuItem = findByPrimaryKey(
			siteNavigationMenuItemId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenuItem[] array = new SiteNavigationMenuItemImpl[3];

			array[0] = getBySiteNavigationMenuId_PrevAndNext(
				session, siteNavigationMenuItem, siteNavigationMenuId,
				orderByComparator, true);

			array[1] = siteNavigationMenuItem;

			array[2] = getBySiteNavigationMenuId_PrevAndNext(
				session, siteNavigationMenuItem, siteNavigationMenuId,
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

	protected SiteNavigationMenuItem getBySiteNavigationMenuId_PrevAndNext(
		Session session, SiteNavigationMenuItem siteNavigationMenuItem,
		long siteNavigationMenuId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE);

		sb.append(_FINDER_COLUMN_SITENAVIGATIONMENUID_SITENAVIGATIONMENUID_2);

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
			sb.append(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(siteNavigationMenuId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						siteNavigationMenuItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SiteNavigationMenuItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the site navigation menu items where siteNavigationMenuId = &#63; from the database.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 */
	@Override
	public void removeBySiteNavigationMenuId(long siteNavigationMenuId) {
		for (SiteNavigationMenuItem siteNavigationMenuItem :
				findBySiteNavigationMenuId(
					siteNavigationMenuId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(siteNavigationMenuItem);
		}
	}

	/**
	 * Returns the number of site navigation menu items where siteNavigationMenuId = &#63;.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @return the number of matching site navigation menu items
	 */
	@Override
	public int countBySiteNavigationMenuId(long siteNavigationMenuId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SiteNavigationMenuItem.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountBySiteNavigationMenuId;

			finderArgs = new Object[] {siteNavigationMenuId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SITENAVIGATIONMENUITEM_WHERE);

			sb.append(
				_FINDER_COLUMN_SITENAVIGATIONMENUID_SITENAVIGATIONMENUID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(siteNavigationMenuId);

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

	private static final String
		_FINDER_COLUMN_SITENAVIGATIONMENUID_SITENAVIGATIONMENUID_2 =
			"siteNavigationMenuItem.siteNavigationMenuId = ?";

	private FinderPath
		_finderPathWithPaginationFindByParentSiteNavigationMenuItemId;
	private FinderPath
		_finderPathWithoutPaginationFindByParentSiteNavigationMenuItemId;
	private FinderPath _finderPathCountByParentSiteNavigationMenuItemId;

	/**
	 * Returns all the site navigation menu items where parentSiteNavigationMenuItemId = &#63;.
	 *
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @return the matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId) {

		return findByParentSiteNavigationMenuItemId(
			parentSiteNavigationMenuItemId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menu items where parentSiteNavigationMenuItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @return the range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId, int start, int end) {

		return findByParentSiteNavigationMenuItemId(
			parentSiteNavigationMenuItemId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items where parentSiteNavigationMenuItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId, int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		return findByParentSiteNavigationMenuItemId(
			parentSiteNavigationMenuItemId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items where parentSiteNavigationMenuItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId, int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SiteNavigationMenuItem.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByParentSiteNavigationMenuItemId;
				finderArgs = new Object[] {parentSiteNavigationMenuItemId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath =
				_finderPathWithPaginationFindByParentSiteNavigationMenuItemId;
			finderArgs = new Object[] {
				parentSiteNavigationMenuItemId, start, end, orderByComparator
			};
		}

		List<SiteNavigationMenuItem> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SiteNavigationMenuItem>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenuItem siteNavigationMenuItem : list) {
					if (parentSiteNavigationMenuItemId !=
							siteNavigationMenuItem.
								getParentSiteNavigationMenuItemId()) {

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

			sb.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE);

			sb.append(
				_FINDER_COLUMN_PARENTSITENAVIGATIONMENUITEMID_PARENTSITENAVIGATIONMENUITEMID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(parentSiteNavigationMenuItemId);

				list = (List<SiteNavigationMenuItem>)QueryUtil.list(
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
	 * Returns the first site navigation menu item in the ordered set where parentSiteNavigationMenuItemId = &#63;.
	 *
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu item
	 * @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem findByParentSiteNavigationMenuItemId_First(
			long parentSiteNavigationMenuItemId,
			OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {

		SiteNavigationMenuItem siteNavigationMenuItem =
			fetchByParentSiteNavigationMenuItemId_First(
				parentSiteNavigationMenuItemId, orderByComparator);

		if (siteNavigationMenuItem != null) {
			return siteNavigationMenuItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("parentSiteNavigationMenuItemId=");
		sb.append(parentSiteNavigationMenuItemId);

		sb.append("}");

		throw new NoSuchMenuItemException(sb.toString());
	}

	/**
	 * Returns the first site navigation menu item in the ordered set where parentSiteNavigationMenuItemId = &#63;.
	 *
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem fetchByParentSiteNavigationMenuItemId_First(
		long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		List<SiteNavigationMenuItem> list =
			findByParentSiteNavigationMenuItemId(
				parentSiteNavigationMenuItemId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last site navigation menu item in the ordered set where parentSiteNavigationMenuItemId = &#63;.
	 *
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu item
	 * @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem findByParentSiteNavigationMenuItemId_Last(
			long parentSiteNavigationMenuItemId,
			OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {

		SiteNavigationMenuItem siteNavigationMenuItem =
			fetchByParentSiteNavigationMenuItemId_Last(
				parentSiteNavigationMenuItemId, orderByComparator);

		if (siteNavigationMenuItem != null) {
			return siteNavigationMenuItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("parentSiteNavigationMenuItemId=");
		sb.append(parentSiteNavigationMenuItemId);

		sb.append("}");

		throw new NoSuchMenuItemException(sb.toString());
	}

	/**
	 * Returns the last site navigation menu item in the ordered set where parentSiteNavigationMenuItemId = &#63;.
	 *
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem fetchByParentSiteNavigationMenuItemId_Last(
		long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		int count = countByParentSiteNavigationMenuItemId(
			parentSiteNavigationMenuItemId);

		if (count == 0) {
			return null;
		}

		List<SiteNavigationMenuItem> list =
			findByParentSiteNavigationMenuItemId(
				parentSiteNavigationMenuItemId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the site navigation menu items before and after the current site navigation menu item in the ordered set where parentSiteNavigationMenuItemId = &#63;.
	 *
	 * @param siteNavigationMenuItemId the primary key of the current site navigation menu item
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu item
	 * @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenuItem[]
			findByParentSiteNavigationMenuItemId_PrevAndNext(
				long siteNavigationMenuItemId,
				long parentSiteNavigationMenuItemId,
				OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {

		SiteNavigationMenuItem siteNavigationMenuItem = findByPrimaryKey(
			siteNavigationMenuItemId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenuItem[] array = new SiteNavigationMenuItemImpl[3];

			array[0] = getByParentSiteNavigationMenuItemId_PrevAndNext(
				session, siteNavigationMenuItem, parentSiteNavigationMenuItemId,
				orderByComparator, true);

			array[1] = siteNavigationMenuItem;

			array[2] = getByParentSiteNavigationMenuItemId_PrevAndNext(
				session, siteNavigationMenuItem, parentSiteNavigationMenuItemId,
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

	protected SiteNavigationMenuItem
		getByParentSiteNavigationMenuItemId_PrevAndNext(
			Session session, SiteNavigationMenuItem siteNavigationMenuItem,
			long parentSiteNavigationMenuItemId,
			OrderByComparator<SiteNavigationMenuItem> orderByComparator,
			boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE);

		sb.append(
			_FINDER_COLUMN_PARENTSITENAVIGATIONMENUITEMID_PARENTSITENAVIGATIONMENUITEMID_2);

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
			sb.append(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(parentSiteNavigationMenuItemId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						siteNavigationMenuItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SiteNavigationMenuItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the site navigation menu items where parentSiteNavigationMenuItemId = &#63; from the database.
	 *
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 */
	@Override
	public void removeByParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId) {

		for (SiteNavigationMenuItem siteNavigationMenuItem :
				findByParentSiteNavigationMenuItemId(
					parentSiteNavigationMenuItemId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(siteNavigationMenuItem);
		}
	}

	/**
	 * Returns the number of site navigation menu items where parentSiteNavigationMenuItemId = &#63;.
	 *
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @return the number of matching site navigation menu items
	 */
	@Override
	public int countByParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SiteNavigationMenuItem.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByParentSiteNavigationMenuItemId;

			finderArgs = new Object[] {parentSiteNavigationMenuItemId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SITENAVIGATIONMENUITEM_WHERE);

			sb.append(
				_FINDER_COLUMN_PARENTSITENAVIGATIONMENUITEMID_PARENTSITENAVIGATIONMENUITEMID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(parentSiteNavigationMenuItemId);

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

	private static final String
		_FINDER_COLUMN_PARENTSITENAVIGATIONMENUITEMID_PARENTSITENAVIGATIONMENUITEMID_2 =
			"siteNavigationMenuItem.parentSiteNavigationMenuItemId = ?";

	private FinderPath _finderPathWithPaginationFindByS_P;
	private FinderPath _finderPathWithoutPaginationFindByS_P;
	private FinderPath _finderPathCountByS_P;

	/**
	 * Returns all the site navigation menu items where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @return the matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByS_P(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId) {

		return findByS_P(
			siteNavigationMenuId, parentSiteNavigationMenuItemId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menu items where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @return the range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByS_P(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
		int start, int end) {

		return findByS_P(
			siteNavigationMenuId, parentSiteNavigationMenuItemId, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByS_P(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
		int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		return findByS_P(
			siteNavigationMenuId, parentSiteNavigationMenuItemId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByS_P(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
		int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SiteNavigationMenuItem.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByS_P;
				finderArgs = new Object[] {
					siteNavigationMenuId, parentSiteNavigationMenuItemId
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByS_P;
			finderArgs = new Object[] {
				siteNavigationMenuId, parentSiteNavigationMenuItemId, start,
				end, orderByComparator
			};
		}

		List<SiteNavigationMenuItem> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SiteNavigationMenuItem>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenuItem siteNavigationMenuItem : list) {
					if ((siteNavigationMenuId !=
							siteNavigationMenuItem.getSiteNavigationMenuId()) ||
						(parentSiteNavigationMenuItemId !=
							siteNavigationMenuItem.
								getParentSiteNavigationMenuItemId())) {

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

			sb.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE);

			sb.append(_FINDER_COLUMN_S_P_SITENAVIGATIONMENUID_2);

			sb.append(_FINDER_COLUMN_S_P_PARENTSITENAVIGATIONMENUITEMID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(siteNavigationMenuId);

				queryPos.add(parentSiteNavigationMenuItemId);

				list = (List<SiteNavigationMenuItem>)QueryUtil.list(
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
	 * Returns the first site navigation menu item in the ordered set where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu item
	 * @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem findByS_P_First(
			long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
			OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {

		SiteNavigationMenuItem siteNavigationMenuItem = fetchByS_P_First(
			siteNavigationMenuId, parentSiteNavigationMenuItemId,
			orderByComparator);

		if (siteNavigationMenuItem != null) {
			return siteNavigationMenuItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("siteNavigationMenuId=");
		sb.append(siteNavigationMenuId);

		sb.append(", parentSiteNavigationMenuItemId=");
		sb.append(parentSiteNavigationMenuItemId);

		sb.append("}");

		throw new NoSuchMenuItemException(sb.toString());
	}

	/**
	 * Returns the first site navigation menu item in the ordered set where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem fetchByS_P_First(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		List<SiteNavigationMenuItem> list = findByS_P(
			siteNavigationMenuId, parentSiteNavigationMenuItemId, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last site navigation menu item in the ordered set where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu item
	 * @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem findByS_P_Last(
			long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
			OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {

		SiteNavigationMenuItem siteNavigationMenuItem = fetchByS_P_Last(
			siteNavigationMenuId, parentSiteNavigationMenuItemId,
			orderByComparator);

		if (siteNavigationMenuItem != null) {
			return siteNavigationMenuItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("siteNavigationMenuId=");
		sb.append(siteNavigationMenuId);

		sb.append(", parentSiteNavigationMenuItemId=");
		sb.append(parentSiteNavigationMenuItemId);

		sb.append("}");

		throw new NoSuchMenuItemException(sb.toString());
	}

	/**
	 * Returns the last site navigation menu item in the ordered set where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem fetchByS_P_Last(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		int count = countByS_P(
			siteNavigationMenuId, parentSiteNavigationMenuItemId);

		if (count == 0) {
			return null;
		}

		List<SiteNavigationMenuItem> list = findByS_P(
			siteNavigationMenuId, parentSiteNavigationMenuItemId, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the site navigation menu items before and after the current site navigation menu item in the ordered set where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	 *
	 * @param siteNavigationMenuItemId the primary key of the current site navigation menu item
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu item
	 * @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenuItem[] findByS_P_PrevAndNext(
			long siteNavigationMenuItemId, long siteNavigationMenuId,
			long parentSiteNavigationMenuItemId,
			OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {

		SiteNavigationMenuItem siteNavigationMenuItem = findByPrimaryKey(
			siteNavigationMenuItemId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenuItem[] array = new SiteNavigationMenuItemImpl[3];

			array[0] = getByS_P_PrevAndNext(
				session, siteNavigationMenuItem, siteNavigationMenuId,
				parentSiteNavigationMenuItemId, orderByComparator, true);

			array[1] = siteNavigationMenuItem;

			array[2] = getByS_P_PrevAndNext(
				session, siteNavigationMenuItem, siteNavigationMenuId,
				parentSiteNavigationMenuItemId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SiteNavigationMenuItem getByS_P_PrevAndNext(
		Session session, SiteNavigationMenuItem siteNavigationMenuItem,
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
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

		sb.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE);

		sb.append(_FINDER_COLUMN_S_P_SITENAVIGATIONMENUID_2);

		sb.append(_FINDER_COLUMN_S_P_PARENTSITENAVIGATIONMENUITEMID_2);

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
			sb.append(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(siteNavigationMenuId);

		queryPos.add(parentSiteNavigationMenuItemId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						siteNavigationMenuItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SiteNavigationMenuItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the site navigation menu items where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63; from the database.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 */
	@Override
	public void removeByS_P(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId) {

		for (SiteNavigationMenuItem siteNavigationMenuItem :
				findByS_P(
					siteNavigationMenuId, parentSiteNavigationMenuItemId,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(siteNavigationMenuItem);
		}
	}

	/**
	 * Returns the number of site navigation menu items where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @return the number of matching site navigation menu items
	 */
	@Override
	public int countByS_P(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SiteNavigationMenuItem.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByS_P;

			finderArgs = new Object[] {
				siteNavigationMenuId, parentSiteNavigationMenuItemId
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SITENAVIGATIONMENUITEM_WHERE);

			sb.append(_FINDER_COLUMN_S_P_SITENAVIGATIONMENUID_2);

			sb.append(_FINDER_COLUMN_S_P_PARENTSITENAVIGATIONMENUITEMID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(siteNavigationMenuId);

				queryPos.add(parentSiteNavigationMenuItemId);

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

	private static final String _FINDER_COLUMN_S_P_SITENAVIGATIONMENUID_2 =
		"siteNavigationMenuItem.siteNavigationMenuId = ? AND ";

	private static final String
		_FINDER_COLUMN_S_P_PARENTSITENAVIGATIONMENUITEMID_2 =
			"siteNavigationMenuItem.parentSiteNavigationMenuItemId = ?";

	private FinderPath _finderPathWithPaginationFindByS_LikeN;
	private FinderPath _finderPathWithPaginationCountByS_LikeN;

	/**
	 * Returns all the site navigation menu items where siteNavigationMenuId = &#63; and name LIKE &#63;.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param name the name
	 * @return the matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByS_LikeN(
		long siteNavigationMenuId, String name) {

		return findByS_LikeN(
			siteNavigationMenuId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the site navigation menu items where siteNavigationMenuId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @return the range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByS_LikeN(
		long siteNavigationMenuId, String name, int start, int end) {

		return findByS_LikeN(siteNavigationMenuId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items where siteNavigationMenuId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByS_LikeN(
		long siteNavigationMenuId, String name, int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		return findByS_LikeN(
			siteNavigationMenuId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items where siteNavigationMenuId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByS_LikeN(
		long siteNavigationMenuId, String name, int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SiteNavigationMenuItem.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByS_LikeN;
		finderArgs = new Object[] {
			siteNavigationMenuId, name, start, end, orderByComparator
		};

		List<SiteNavigationMenuItem> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SiteNavigationMenuItem>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenuItem siteNavigationMenuItem : list) {
					if ((siteNavigationMenuId !=
							siteNavigationMenuItem.getSiteNavigationMenuId()) ||
						!StringUtil.wildcardMatches(
							siteNavigationMenuItem.getName(), name, '_', '%',
							'\\', true)) {

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

			sb.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE);

			sb.append(_FINDER_COLUMN_S_LIKEN_SITENAVIGATIONMENUID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_S_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_S_LIKEN_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(siteNavigationMenuId);

				if (bindName) {
					queryPos.add(name);
				}

				list = (List<SiteNavigationMenuItem>)QueryUtil.list(
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
	 * Returns the first site navigation menu item in the ordered set where siteNavigationMenuId = &#63; and name LIKE &#63;.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu item
	 * @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem findByS_LikeN_First(
			long siteNavigationMenuId, String name,
			OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {

		SiteNavigationMenuItem siteNavigationMenuItem = fetchByS_LikeN_First(
			siteNavigationMenuId, name, orderByComparator);

		if (siteNavigationMenuItem != null) {
			return siteNavigationMenuItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("siteNavigationMenuId=");
		sb.append(siteNavigationMenuId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchMenuItemException(sb.toString());
	}

	/**
	 * Returns the first site navigation menu item in the ordered set where siteNavigationMenuId = &#63; and name LIKE &#63;.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem fetchByS_LikeN_First(
		long siteNavigationMenuId, String name,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		List<SiteNavigationMenuItem> list = findByS_LikeN(
			siteNavigationMenuId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last site navigation menu item in the ordered set where siteNavigationMenuId = &#63; and name LIKE &#63;.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu item
	 * @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem findByS_LikeN_Last(
			long siteNavigationMenuId, String name,
			OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {

		SiteNavigationMenuItem siteNavigationMenuItem = fetchByS_LikeN_Last(
			siteNavigationMenuId, name, orderByComparator);

		if (siteNavigationMenuItem != null) {
			return siteNavigationMenuItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("siteNavigationMenuId=");
		sb.append(siteNavigationMenuId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchMenuItemException(sb.toString());
	}

	/**
	 * Returns the last site navigation menu item in the ordered set where siteNavigationMenuId = &#63; and name LIKE &#63;.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem fetchByS_LikeN_Last(
		long siteNavigationMenuId, String name,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		int count = countByS_LikeN(siteNavigationMenuId, name);

		if (count == 0) {
			return null;
		}

		List<SiteNavigationMenuItem> list = findByS_LikeN(
			siteNavigationMenuId, name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the site navigation menu items before and after the current site navigation menu item in the ordered set where siteNavigationMenuId = &#63; and name LIKE &#63;.
	 *
	 * @param siteNavigationMenuItemId the primary key of the current site navigation menu item
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu item
	 * @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenuItem[] findByS_LikeN_PrevAndNext(
			long siteNavigationMenuItemId, long siteNavigationMenuId,
			String name,
			OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {

		name = Objects.toString(name, "");

		SiteNavigationMenuItem siteNavigationMenuItem = findByPrimaryKey(
			siteNavigationMenuItemId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenuItem[] array = new SiteNavigationMenuItemImpl[3];

			array[0] = getByS_LikeN_PrevAndNext(
				session, siteNavigationMenuItem, siteNavigationMenuId, name,
				orderByComparator, true);

			array[1] = siteNavigationMenuItem;

			array[2] = getByS_LikeN_PrevAndNext(
				session, siteNavigationMenuItem, siteNavigationMenuId, name,
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

	protected SiteNavigationMenuItem getByS_LikeN_PrevAndNext(
		Session session, SiteNavigationMenuItem siteNavigationMenuItem,
		long siteNavigationMenuId, String name,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
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

		sb.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE);

		sb.append(_FINDER_COLUMN_S_LIKEN_SITENAVIGATIONMENUID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_S_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_S_LIKEN_NAME_2);
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
			sb.append(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(siteNavigationMenuId);

		if (bindName) {
			queryPos.add(name);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						siteNavigationMenuItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SiteNavigationMenuItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the site navigation menu items where siteNavigationMenuId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param name the name
	 */
	@Override
	public void removeByS_LikeN(long siteNavigationMenuId, String name) {
		for (SiteNavigationMenuItem siteNavigationMenuItem :
				findByS_LikeN(
					siteNavigationMenuId, name, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(siteNavigationMenuItem);
		}
	}

	/**
	 * Returns the number of site navigation menu items where siteNavigationMenuId = &#63; and name LIKE &#63;.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param name the name
	 * @return the number of matching site navigation menu items
	 */
	@Override
	public int countByS_LikeN(long siteNavigationMenuId, String name) {
		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SiteNavigationMenuItem.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByS_LikeN;

			finderArgs = new Object[] {siteNavigationMenuId, name};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SITENAVIGATIONMENUITEM_WHERE);

			sb.append(_FINDER_COLUMN_S_LIKEN_SITENAVIGATIONMENUID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_S_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_S_LIKEN_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(siteNavigationMenuId);

				if (bindName) {
					queryPos.add(name);
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

	private static final String _FINDER_COLUMN_S_LIKEN_SITENAVIGATIONMENUID_2 =
		"siteNavigationMenuItem.siteNavigationMenuId = ? AND ";

	private static final String _FINDER_COLUMN_S_LIKEN_NAME_2 =
		"siteNavigationMenuItem.name LIKE ?";

	private static final String _FINDER_COLUMN_S_LIKEN_NAME_3 =
		"(siteNavigationMenuItem.name IS NULL OR siteNavigationMenuItem.name LIKE '')";

	public SiteNavigationMenuItemPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");
		dbColumnNames.put("order", "order_");

		setDBColumnNames(dbColumnNames);

		setModelClass(SiteNavigationMenuItem.class);

		setModelImplClass(SiteNavigationMenuItemImpl.class);
		setModelPKClass(long.class);

		setTable(SiteNavigationMenuItemTable.INSTANCE);
	}

	/**
	 * Caches the site navigation menu item in the entity cache if it is enabled.
	 *
	 * @param siteNavigationMenuItem the site navigation menu item
	 */
	@Override
	public void cacheResult(SiteNavigationMenuItem siteNavigationMenuItem) {
		if (siteNavigationMenuItem.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			SiteNavigationMenuItemImpl.class,
			siteNavigationMenuItem.getPrimaryKey(), siteNavigationMenuItem);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				siteNavigationMenuItem.getUuid(),
				siteNavigationMenuItem.getGroupId()
			},
			siteNavigationMenuItem);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the site navigation menu items in the entity cache if it is enabled.
	 *
	 * @param siteNavigationMenuItems the site navigation menu items
	 */
	@Override
	public void cacheResult(
		List<SiteNavigationMenuItem> siteNavigationMenuItems) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (siteNavigationMenuItems.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (SiteNavigationMenuItem siteNavigationMenuItem :
				siteNavigationMenuItems) {

			if (siteNavigationMenuItem.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					SiteNavigationMenuItemImpl.class,
					siteNavigationMenuItem.getPrimaryKey()) == null) {

				cacheResult(siteNavigationMenuItem);
			}
		}
	}

	/**
	 * Clears the cache for all site navigation menu items.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SiteNavigationMenuItemImpl.class);

		finderCache.clearCache(SiteNavigationMenuItemImpl.class);
	}

	/**
	 * Clears the cache for the site navigation menu item.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SiteNavigationMenuItem siteNavigationMenuItem) {
		entityCache.removeResult(
			SiteNavigationMenuItemImpl.class, siteNavigationMenuItem);
	}

	@Override
	public void clearCache(
		List<SiteNavigationMenuItem> siteNavigationMenuItems) {

		for (SiteNavigationMenuItem siteNavigationMenuItem :
				siteNavigationMenuItems) {

			entityCache.removeResult(
				SiteNavigationMenuItemImpl.class, siteNavigationMenuItem);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(SiteNavigationMenuItemImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				SiteNavigationMenuItemImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		SiteNavigationMenuItemModelImpl siteNavigationMenuItemModelImpl) {

		Object[] args = new Object[] {
			siteNavigationMenuItemModelImpl.getUuid(),
			siteNavigationMenuItemModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, siteNavigationMenuItemModelImpl);
	}

	/**
	 * Creates a new site navigation menu item with the primary key. Does not add the site navigation menu item to the database.
	 *
	 * @param siteNavigationMenuItemId the primary key for the new site navigation menu item
	 * @return the new site navigation menu item
	 */
	@Override
	public SiteNavigationMenuItem create(long siteNavigationMenuItemId) {
		SiteNavigationMenuItem siteNavigationMenuItem =
			new SiteNavigationMenuItemImpl();

		siteNavigationMenuItem.setNew(true);
		siteNavigationMenuItem.setPrimaryKey(siteNavigationMenuItemId);

		String uuid = PortalUUIDUtil.generate();

		siteNavigationMenuItem.setUuid(uuid);

		siteNavigationMenuItem.setCompanyId(CompanyThreadLocal.getCompanyId());

		return siteNavigationMenuItem;
	}

	/**
	 * Removes the site navigation menu item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteNavigationMenuItemId the primary key of the site navigation menu item
	 * @return the site navigation menu item that was removed
	 * @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenuItem remove(long siteNavigationMenuItemId)
		throws NoSuchMenuItemException {

		return remove((Serializable)siteNavigationMenuItemId);
	}

	/**
	 * Removes the site navigation menu item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the site navigation menu item
	 * @return the site navigation menu item that was removed
	 * @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenuItem remove(Serializable primaryKey)
		throws NoSuchMenuItemException {

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenuItem siteNavigationMenuItem =
				(SiteNavigationMenuItem)session.get(
					SiteNavigationMenuItemImpl.class, primaryKey);

			if (siteNavigationMenuItem == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMenuItemException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(siteNavigationMenuItem);
		}
		catch (NoSuchMenuItemException noSuchEntityException) {
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
	protected SiteNavigationMenuItem removeImpl(
		SiteNavigationMenuItem siteNavigationMenuItem) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(siteNavigationMenuItem)) {
				siteNavigationMenuItem = (SiteNavigationMenuItem)session.get(
					SiteNavigationMenuItemImpl.class,
					siteNavigationMenuItem.getPrimaryKeyObj());
			}

			if ((siteNavigationMenuItem != null) &&
				ctPersistenceHelper.isRemove(siteNavigationMenuItem)) {

				session.delete(siteNavigationMenuItem);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (siteNavigationMenuItem != null) {
			clearCache(siteNavigationMenuItem);
		}

		return siteNavigationMenuItem;
	}

	@Override
	public SiteNavigationMenuItem updateImpl(
		SiteNavigationMenuItem siteNavigationMenuItem) {

		boolean isNew = siteNavigationMenuItem.isNew();

		if (!(siteNavigationMenuItem instanceof
				SiteNavigationMenuItemModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(siteNavigationMenuItem.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					siteNavigationMenuItem);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in siteNavigationMenuItem proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SiteNavigationMenuItem implementation " +
					siteNavigationMenuItem.getClass());
		}

		SiteNavigationMenuItemModelImpl siteNavigationMenuItemModelImpl =
			(SiteNavigationMenuItemModelImpl)siteNavigationMenuItem;

		if (Validator.isNull(siteNavigationMenuItem.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			siteNavigationMenuItem.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (siteNavigationMenuItem.getCreateDate() == null)) {
			if (serviceContext == null) {
				siteNavigationMenuItem.setCreateDate(date);
			}
			else {
				siteNavigationMenuItem.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!siteNavigationMenuItemModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				siteNavigationMenuItem.setModifiedDate(date);
			}
			else {
				siteNavigationMenuItem.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(siteNavigationMenuItem)) {
				if (!isNew) {
					session.evict(
						SiteNavigationMenuItemImpl.class,
						siteNavigationMenuItem.getPrimaryKeyObj());
				}

				session.save(siteNavigationMenuItem);
			}
			else {
				siteNavigationMenuItem = (SiteNavigationMenuItem)session.merge(
					siteNavigationMenuItem);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (siteNavigationMenuItem.getCtCollectionId() != 0) {
			if (isNew) {
				siteNavigationMenuItem.setNew(false);
			}

			siteNavigationMenuItem.resetOriginalValues();

			return siteNavigationMenuItem;
		}

		entityCache.putResult(
			SiteNavigationMenuItemImpl.class, siteNavigationMenuItemModelImpl,
			false, true);

		cacheUniqueFindersCache(siteNavigationMenuItemModelImpl);

		if (isNew) {
			siteNavigationMenuItem.setNew(false);
		}

		siteNavigationMenuItem.resetOriginalValues();

		return siteNavigationMenuItem;
	}

	/**
	 * Returns the site navigation menu item with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the site navigation menu item
	 * @return the site navigation menu item
	 * @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenuItem findByPrimaryKey(Serializable primaryKey)
		throws NoSuchMenuItemException {

		SiteNavigationMenuItem siteNavigationMenuItem = fetchByPrimaryKey(
			primaryKey);

		if (siteNavigationMenuItem == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchMenuItemException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return siteNavigationMenuItem;
	}

	/**
	 * Returns the site navigation menu item with the primary key or throws a <code>NoSuchMenuItemException</code> if it could not be found.
	 *
	 * @param siteNavigationMenuItemId the primary key of the site navigation menu item
	 * @return the site navigation menu item
	 * @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenuItem findByPrimaryKey(
			long siteNavigationMenuItemId)
		throws NoSuchMenuItemException {

		return findByPrimaryKey((Serializable)siteNavigationMenuItemId);
	}

	/**
	 * Returns the site navigation menu item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the site navigation menu item
	 * @return the site navigation menu item, or <code>null</code> if a site navigation menu item with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenuItem fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				SiteNavigationMenuItem.class)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		SiteNavigationMenuItem siteNavigationMenuItem = null;

		Session session = null;

		try {
			session = openSession();

			siteNavigationMenuItem = (SiteNavigationMenuItem)session.get(
				SiteNavigationMenuItemImpl.class, primaryKey);

			if (siteNavigationMenuItem != null) {
				cacheResult(siteNavigationMenuItem);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return siteNavigationMenuItem;
	}

	/**
	 * Returns the site navigation menu item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param siteNavigationMenuItemId the primary key of the site navigation menu item
	 * @return the site navigation menu item, or <code>null</code> if a site navigation menu item with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenuItem fetchByPrimaryKey(
		long siteNavigationMenuItemId) {

		return fetchByPrimaryKey((Serializable)siteNavigationMenuItemId);
	}

	@Override
	public Map<Serializable, SiteNavigationMenuItem> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				SiteNavigationMenuItem.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SiteNavigationMenuItem> map =
			new HashMap<Serializable, SiteNavigationMenuItem>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SiteNavigationMenuItem siteNavigationMenuItem = fetchByPrimaryKey(
				primaryKey);

			if (siteNavigationMenuItem != null) {
				map.put(primaryKey, siteNavigationMenuItem);
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

			for (SiteNavigationMenuItem siteNavigationMenuItem :
					(List<SiteNavigationMenuItem>)query.list()) {

				map.put(
					siteNavigationMenuItem.getPrimaryKeyObj(),
					siteNavigationMenuItem);

				cacheResult(siteNavigationMenuItem);
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
	 * Returns all the site navigation menu items.
	 *
	 * @return the site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menu items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @return the range of site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findAll(
		int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findAll(
		int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SiteNavigationMenuItem.class);

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

		List<SiteNavigationMenuItem> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SiteNavigationMenuItem>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SITENAVIGATIONMENUITEM);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SITENAVIGATIONMENUITEM;

				sql = sql.concat(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SiteNavigationMenuItem>)QueryUtil.list(
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
	 * Removes all the site navigation menu items from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SiteNavigationMenuItem siteNavigationMenuItem : findAll()) {
			remove(siteNavigationMenuItem);
		}
	}

	/**
	 * Returns the number of site navigation menu items.
	 *
	 * @return the number of site navigation menu items
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SiteNavigationMenuItem.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_SITENAVIGATIONMENUITEM);

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
		return "siteNavigationMenuItemId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SITENAVIGATIONMENUITEM;
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
		return SiteNavigationMenuItemModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "SiteNavigationMenuItem";
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
		ctStrictColumnNames.add("siteNavigationMenuId");
		ctStrictColumnNames.add("parentSiteNavigationMenuItemId");
		ctStrictColumnNames.add("name");
		ctStrictColumnNames.add("type_");
		ctStrictColumnNames.add("typeSettings");
		ctStrictColumnNames.add("order_");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("siteNavigationMenuItemId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});
	}

	/**
	 * Initializes the site navigation menu item persistence.
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

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_finderPathWithPaginationFindBySiteNavigationMenuId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findBySiteNavigationMenuId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"siteNavigationMenuId"}, true);

		_finderPathWithoutPaginationFindBySiteNavigationMenuId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findBySiteNavigationMenuId", new String[] {Long.class.getName()},
			new String[] {"siteNavigationMenuId"}, true);

		_finderPathCountBySiteNavigationMenuId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySiteNavigationMenuId", new String[] {Long.class.getName()},
			new String[] {"siteNavigationMenuId"}, false);

		_finderPathWithPaginationFindByParentSiteNavigationMenuItemId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByParentSiteNavigationMenuItemId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"parentSiteNavigationMenuItemId"}, true);

		_finderPathWithoutPaginationFindByParentSiteNavigationMenuItemId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByParentSiteNavigationMenuItemId",
				new String[] {Long.class.getName()},
				new String[] {"parentSiteNavigationMenuItemId"}, true);

		_finderPathCountByParentSiteNavigationMenuItemId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByParentSiteNavigationMenuItemId",
			new String[] {Long.class.getName()},
			new String[] {"parentSiteNavigationMenuItemId"}, false);

		_finderPathWithPaginationFindByS_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByS_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"siteNavigationMenuId", "parentSiteNavigationMenuItemId"
			},
			true);

		_finderPathWithoutPaginationFindByS_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByS_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {
				"siteNavigationMenuId", "parentSiteNavigationMenuItemId"
			},
			true);

		_finderPathCountByS_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByS_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {
				"siteNavigationMenuId", "parentSiteNavigationMenuItemId"
			},
			false);

		_finderPathWithPaginationFindByS_LikeN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByS_LikeN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"siteNavigationMenuId", "name"}, true);

		_finderPathWithPaginationCountByS_LikeN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByS_LikeN",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"siteNavigationMenuId", "name"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(SiteNavigationMenuItemImpl.class.getName());
	}

	@Override
	@Reference(
		target = SiteNavigationPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = SiteNavigationPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = SiteNavigationPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_SITENAVIGATIONMENUITEM =
		"SELECT siteNavigationMenuItem FROM SiteNavigationMenuItem siteNavigationMenuItem";

	private static final String _SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE =
		"SELECT siteNavigationMenuItem FROM SiteNavigationMenuItem siteNavigationMenuItem WHERE ";

	private static final String _SQL_COUNT_SITENAVIGATIONMENUITEM =
		"SELECT COUNT(siteNavigationMenuItem) FROM SiteNavigationMenuItem siteNavigationMenuItem";

	private static final String _SQL_COUNT_SITENAVIGATIONMENUITEM_WHERE =
		"SELECT COUNT(siteNavigationMenuItem) FROM SiteNavigationMenuItem siteNavigationMenuItem WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"siteNavigationMenuItem.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SiteNavigationMenuItem exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SiteNavigationMenuItem exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SiteNavigationMenuItemPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type", "order"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private SiteNavigationMenuItemModelArgumentsResolver
		_siteNavigationMenuItemModelArgumentsResolver;

}