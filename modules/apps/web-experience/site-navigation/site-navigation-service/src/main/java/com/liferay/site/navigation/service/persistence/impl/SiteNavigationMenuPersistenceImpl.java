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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.site.navigation.exception.NoSuchMenuException;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.impl.SiteNavigationMenuImpl;
import com.liferay.site.navigation.model.impl.SiteNavigationMenuModelImpl;
import com.liferay.site.navigation.service.persistence.SiteNavigationMenuPersistence;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the site navigation menu service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuPersistence
 * @see com.liferay.site.navigation.service.persistence.SiteNavigationMenuUtil
 * @generated
 */
@ProviderType
public class SiteNavigationMenuPersistenceImpl extends BasePersistenceImpl<SiteNavigationMenu>
	implements SiteNavigationMenuPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SiteNavigationMenuUtil} to access the site navigation menu persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SiteNavigationMenuImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuModelImpl.FINDER_CACHE_ENABLED,
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuModelImpl.FINDER_CACHE_ENABLED,
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuModelImpl.FINDER_CACHE_ENABLED,
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuModelImpl.FINDER_CACHE_ENABLED,
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			SiteNavigationMenuModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the site navigation menus where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menus where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByGroupId(long groupId, int start,
		int end, OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByGroupId(long groupId, int start,
		int end, OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean retrieveFromCache) {
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

		List<SiteNavigationMenu> list = null;

		if (retrieveFromCache) {
			list = (List<SiteNavigationMenu>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenu siteNavigationMenu : list) {
					if ((groupId != siteNavigationMenu.getGroupId())) {
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

			query.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<SiteNavigationMenu>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SiteNavigationMenu>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first site navigation menu in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByGroupId_First(long groupId,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {
		SiteNavigationMenu siteNavigationMenu = fetchByGroupId_First(groupId,
				orderByComparator);

		if (siteNavigationMenu != null) {
			return siteNavigationMenu;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchMenuException(msg.toString());
	}

	/**
	 * Returns the first site navigation menu in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByGroupId_First(long groupId,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		List<SiteNavigationMenu> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last site navigation menu in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByGroupId_Last(long groupId,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {
		SiteNavigationMenu siteNavigationMenu = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (siteNavigationMenu != null) {
			return siteNavigationMenu;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchMenuException(msg.toString());
	}

	/**
	 * Returns the last site navigation menu in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByGroupId_Last(long groupId,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<SiteNavigationMenu> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the site navigation menus before and after the current site navigation menu in the ordered set where groupId = &#63;.
	 *
	 * @param siteNavigationMenuId the primary key of the current site navigation menu
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu[] findByGroupId_PrevAndNext(
		long siteNavigationMenuId, long groupId,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {
		SiteNavigationMenu siteNavigationMenu = findByPrimaryKey(siteNavigationMenuId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenu[] array = new SiteNavigationMenuImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, siteNavigationMenu,
					groupId, orderByComparator, true);

			array[1] = siteNavigationMenu;

			array[2] = getByGroupId_PrevAndNext(session, siteNavigationMenu,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SiteNavigationMenu getByGroupId_PrevAndNext(Session session,
		SiteNavigationMenu siteNavigationMenu, long groupId,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
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

		query.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

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
			query.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(siteNavigationMenu);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SiteNavigationMenu> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the site navigation menus that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menus that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByGroupId(long groupId,
		int start, int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByGroupId(long groupId,
		int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
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
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SiteNavigationMenuModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SiteNavigationMenu.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SiteNavigationMenuImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SiteNavigationMenuImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<SiteNavigationMenu>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the site navigation menus before and after the current site navigation menu in the ordered set of site navigation menus that the user has permission to view where groupId = &#63;.
	 *
	 * @param siteNavigationMenuId the primary key of the current site navigation menu
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu[] filterFindByGroupId_PrevAndNext(
		long siteNavigationMenuId, long groupId,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(siteNavigationMenuId, groupId,
				orderByComparator);
		}

		SiteNavigationMenu siteNavigationMenu = findByPrimaryKey(siteNavigationMenuId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenu[] array = new SiteNavigationMenuImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session,
					siteNavigationMenu, groupId, orderByComparator, true);

			array[1] = siteNavigationMenu;

			array[2] = filterGetByGroupId_PrevAndNext(session,
					siteNavigationMenu, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SiteNavigationMenu filterGetByGroupId_PrevAndNext(
		Session session, SiteNavigationMenu siteNavigationMenu, long groupId,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SiteNavigationMenuModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SiteNavigationMenu.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SiteNavigationMenuImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SiteNavigationMenuImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(siteNavigationMenu);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SiteNavigationMenu> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the site navigation menus where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (SiteNavigationMenu siteNavigationMenu : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(siteNavigationMenu);
		}
	}

	/**
	 * Returns the number of site navigation menus where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching site navigation menus
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

	/**
	 * Returns the number of site navigation menus that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching site navigation menus that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SiteNavigationMenu.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "siteNavigationMenu.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_N = new FinderPath(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuModelImpl.FINDER_CACHE_ENABLED,
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_N = new FinderPath(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the site navigation menus where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_N(long groupId, String name) {
		return findByG_N(groupId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the site navigation menus where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_N(long groupId, String name,
		int start, int end) {
		return findByG_N(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_N(long groupId, String name,
		int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return findByG_N(groupId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_N(long groupId, String name,
		int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_N;
		finderArgs = new Object[] { groupId, name, start, end, orderByComparator };

		List<SiteNavigationMenu> list = null;

		if (retrieveFromCache) {
			list = (List<SiteNavigationMenu>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenu siteNavigationMenu : list) {
					if ((groupId != siteNavigationMenu.getGroupId()) ||
							!StringUtil.wildcardMatches(
								siteNavigationMenu.getName(), name, '_', '%',
								'\\', true)) {
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

			query.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

			query.append(_FINDER_COLUMN_G_N_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_NAME_1);
			}
			else if (name.equals("")) {
				query.append(_FINDER_COLUMN_G_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_N_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(name);
				}

				if (!pagination) {
					list = (List<SiteNavigationMenu>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SiteNavigationMenu>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first site navigation menu in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByG_N_First(long groupId, String name,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {
		SiteNavigationMenu siteNavigationMenu = fetchByG_N_First(groupId, name,
				orderByComparator);

		if (siteNavigationMenu != null) {
			return siteNavigationMenu;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append("}");

		throw new NoSuchMenuException(msg.toString());
	}

	/**
	 * Returns the first site navigation menu in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByG_N_First(long groupId, String name,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		List<SiteNavigationMenu> list = findByG_N(groupId, name, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last site navigation menu in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByG_N_Last(long groupId, String name,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {
		SiteNavigationMenu siteNavigationMenu = fetchByG_N_Last(groupId, name,
				orderByComparator);

		if (siteNavigationMenu != null) {
			return siteNavigationMenu;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append("}");

		throw new NoSuchMenuException(msg.toString());
	}

	/**
	 * Returns the last site navigation menu in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByG_N_Last(long groupId, String name,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		int count = countByG_N(groupId, name);

		if (count == 0) {
			return null;
		}

		List<SiteNavigationMenu> list = findByG_N(groupId, name, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the site navigation menus before and after the current site navigation menu in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param siteNavigationMenuId the primary key of the current site navigation menu
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu[] findByG_N_PrevAndNext(
		long siteNavigationMenuId, long groupId, String name,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {
		SiteNavigationMenu siteNavigationMenu = findByPrimaryKey(siteNavigationMenuId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenu[] array = new SiteNavigationMenuImpl[3];

			array[0] = getByG_N_PrevAndNext(session, siteNavigationMenu,
					groupId, name, orderByComparator, true);

			array[1] = siteNavigationMenu;

			array[2] = getByG_N_PrevAndNext(session, siteNavigationMenu,
					groupId, name, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SiteNavigationMenu getByG_N_PrevAndNext(Session session,
		SiteNavigationMenu siteNavigationMenu, long groupId, String name,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
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

		query.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

		query.append(_FINDER_COLUMN_G_N_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_N_NAME_1);
		}
		else if (name.equals("")) {
			query.append(_FINDER_COLUMN_G_N_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_N_NAME_2);
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
			query.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindName) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(siteNavigationMenu);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SiteNavigationMenu> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the site navigation menus that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByG_N(long groupId, String name) {
		return filterFindByG_N(groupId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menus that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByG_N(long groupId, String name,
		int start, int end) {
		return filterFindByG_N(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus that the user has permissions to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByG_N(long groupId, String name,
		int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_N(groupId, name, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_N_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_N_NAME_1);
		}
		else if (name.equals("")) {
			query.append(_FINDER_COLUMN_G_N_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_N_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SiteNavigationMenuModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SiteNavigationMenu.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SiteNavigationMenuImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SiteNavigationMenuImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindName) {
				qPos.add(name);
			}

			return (List<SiteNavigationMenu>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the site navigation menus before and after the current site navigation menu in the ordered set of site navigation menus that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param siteNavigationMenuId the primary key of the current site navigation menu
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu[] filterFindByG_N_PrevAndNext(
		long siteNavigationMenuId, long groupId, String name,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_N_PrevAndNext(siteNavigationMenuId, groupId, name,
				orderByComparator);
		}

		SiteNavigationMenu siteNavigationMenu = findByPrimaryKey(siteNavigationMenuId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenu[] array = new SiteNavigationMenuImpl[3];

			array[0] = filterGetByG_N_PrevAndNext(session, siteNavigationMenu,
					groupId, name, orderByComparator, true);

			array[1] = siteNavigationMenu;

			array[2] = filterGetByG_N_PrevAndNext(session, siteNavigationMenu,
					groupId, name, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SiteNavigationMenu filterGetByG_N_PrevAndNext(Session session,
		SiteNavigationMenu siteNavigationMenu, long groupId, String name,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
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
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_N_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_N_NAME_1);
		}
		else if (name.equals("")) {
			query.append(_FINDER_COLUMN_G_N_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_N_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SiteNavigationMenuModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SiteNavigationMenu.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SiteNavigationMenuImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SiteNavigationMenuImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindName) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(siteNavigationMenu);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SiteNavigationMenu> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the site navigation menus where groupId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 */
	@Override
	public void removeByG_N(long groupId, String name) {
		for (SiteNavigationMenu siteNavigationMenu : findByG_N(groupId, name,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(siteNavigationMenu);
		}
	}

	/**
	 * Returns the number of site navigation menus where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching site navigation menus
	 */
	@Override
	public int countByG_N(long groupId, String name) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_N;

		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

			query.append(_FINDER_COLUMN_G_N_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_NAME_1);
			}
			else if (name.equals("")) {
				query.append(_FINDER_COLUMN_G_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(name);
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

	/**
	 * Returns the number of site navigation menus that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching site navigation menus that the user has permission to view
	 */
	@Override
	public int filterCountByG_N(long groupId, String name) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_N(groupId, name);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

		query.append(_FINDER_COLUMN_G_N_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_N_NAME_1);
		}
		else if (name.equals("")) {
			query.append(_FINDER_COLUMN_G_N_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_N_NAME_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SiteNavigationMenu.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindName) {
				qPos.add(name);
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

	private static final String _FINDER_COLUMN_G_N_GROUPID_2 = "siteNavigationMenu.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_NAME_1 = "siteNavigationMenu.name IS NULL";
	private static final String _FINDER_COLUMN_G_N_NAME_2 = "siteNavigationMenu.name LIKE ?";
	private static final String _FINDER_COLUMN_G_N_NAME_3 = "(siteNavigationMenu.name IS NULL OR siteNavigationMenu.name LIKE '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T = new FinderPath(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuModelImpl.FINDER_CACHE_ENABLED,
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T = new FinderPath(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuModelImpl.FINDER_CACHE_ENABLED,
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T",
			new String[] { Long.class.getName(), Integer.class.getName() },
			SiteNavigationMenuModelImpl.GROUPID_COLUMN_BITMASK |
			SiteNavigationMenuModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_T = new FinderPath(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the site navigation menus where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_T(long groupId, int type) {
		return findByG_T(groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the site navigation menus where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_T(long groupId, int type,
		int start, int end) {
		return findByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_T(long groupId, int type,
		int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return findByG_T(groupId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_T(long groupId, int type,
		int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T;
			finderArgs = new Object[] { groupId, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T;
			finderArgs = new Object[] {
					groupId, type,
					
					start, end, orderByComparator
				};
		}

		List<SiteNavigationMenu> list = null;

		if (retrieveFromCache) {
			list = (List<SiteNavigationMenu>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenu siteNavigationMenu : list) {
					if ((groupId != siteNavigationMenu.getGroupId()) ||
							(type != siteNavigationMenu.getType())) {
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

			query.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(type);

				if (!pagination) {
					list = (List<SiteNavigationMenu>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SiteNavigationMenu>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first site navigation menu in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByG_T_First(long groupId, int type,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {
		SiteNavigationMenu siteNavigationMenu = fetchByG_T_First(groupId, type,
				orderByComparator);

		if (siteNavigationMenu != null) {
			return siteNavigationMenu;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchMenuException(msg.toString());
	}

	/**
	 * Returns the first site navigation menu in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByG_T_First(long groupId, int type,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		List<SiteNavigationMenu> list = findByG_T(groupId, type, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last site navigation menu in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByG_T_Last(long groupId, int type,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {
		SiteNavigationMenu siteNavigationMenu = fetchByG_T_Last(groupId, type,
				orderByComparator);

		if (siteNavigationMenu != null) {
			return siteNavigationMenu;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchMenuException(msg.toString());
	}

	/**
	 * Returns the last site navigation menu in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByG_T_Last(long groupId, int type,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		int count = countByG_T(groupId, type);

		if (count == 0) {
			return null;
		}

		List<SiteNavigationMenu> list = findByG_T(groupId, type, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the site navigation menus before and after the current site navigation menu in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param siteNavigationMenuId the primary key of the current site navigation menu
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu[] findByG_T_PrevAndNext(
		long siteNavigationMenuId, long groupId, int type,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {
		SiteNavigationMenu siteNavigationMenu = findByPrimaryKey(siteNavigationMenuId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenu[] array = new SiteNavigationMenuImpl[3];

			array[0] = getByG_T_PrevAndNext(session, siteNavigationMenu,
					groupId, type, orderByComparator, true);

			array[1] = siteNavigationMenu;

			array[2] = getByG_T_PrevAndNext(session, siteNavigationMenu,
					groupId, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SiteNavigationMenu getByG_T_PrevAndNext(Session session,
		SiteNavigationMenu siteNavigationMenu, long groupId, int type,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
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

		query.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2);

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
			query.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(siteNavigationMenu);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SiteNavigationMenu> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the site navigation menus that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByG_T(long groupId, int type) {
		return filterFindByG_T(groupId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menus that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByG_T(long groupId, int type,
		int start, int end) {
		return filterFindByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus that the user has permissions to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByG_T(long groupId, int type,
		int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T(groupId, type, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SiteNavigationMenuModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SiteNavigationMenu.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SiteNavigationMenuImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SiteNavigationMenuImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(type);

			return (List<SiteNavigationMenu>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the site navigation menus before and after the current site navigation menu in the ordered set of site navigation menus that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param siteNavigationMenuId the primary key of the current site navigation menu
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu[] filterFindByG_T_PrevAndNext(
		long siteNavigationMenuId, long groupId, int type,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_PrevAndNext(siteNavigationMenuId, groupId, type,
				orderByComparator);
		}

		SiteNavigationMenu siteNavigationMenu = findByPrimaryKey(siteNavigationMenuId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenu[] array = new SiteNavigationMenuImpl[3];

			array[0] = filterGetByG_T_PrevAndNext(session, siteNavigationMenu,
					groupId, type, orderByComparator, true);

			array[1] = siteNavigationMenu;

			array[2] = filterGetByG_T_PrevAndNext(session, siteNavigationMenu,
					groupId, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SiteNavigationMenu filterGetByG_T_PrevAndNext(Session session,
		SiteNavigationMenu siteNavigationMenu, long groupId, int type,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
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
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SiteNavigationMenuModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SiteNavigationMenu.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SiteNavigationMenuImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SiteNavigationMenuImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(siteNavigationMenu);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SiteNavigationMenu> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the site navigation menus where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	@Override
	public void removeByG_T(long groupId, int type) {
		for (SiteNavigationMenu siteNavigationMenu : findByG_T(groupId, type,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(siteNavigationMenu);
		}
	}

	/**
	 * Returns the number of site navigation menus where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching site navigation menus
	 */
	@Override
	public int countByG_T(long groupId, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_T;

		Object[] finderArgs = new Object[] { groupId, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	/**
	 * Returns the number of site navigation menus that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching site navigation menus that the user has permission to view
	 */
	@Override
	public int filterCountByG_T(long groupId, int type) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_T(groupId, type);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SiteNavigationMenu.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(type);

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

	private static final String _FINDER_COLUMN_G_T_GROUPID_2 = "siteNavigationMenu.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_TYPE_2 = "siteNavigationMenu.type = ?";
	private static final String _FINDER_COLUMN_G_T_TYPE_2_SQL = "siteNavigationMenu.type_ = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A = new FinderPath(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuModelImpl.FINDER_CACHE_ENABLED,
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A = new FinderPath(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuModelImpl.FINDER_CACHE_ENABLED,
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			SiteNavigationMenuModelImpl.GROUPID_COLUMN_BITMASK |
			SiteNavigationMenuModelImpl.AUTO_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A = new FinderPath(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the site navigation menus where groupId = &#63; and auto = &#63;.
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @return the matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_A(long groupId, boolean auto) {
		return findByG_A(groupId, auto, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the site navigation menus where groupId = &#63; and auto = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_A(long groupId, boolean auto,
		int start, int end) {
		return findByG_A(groupId, auto, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63; and auto = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_A(long groupId, boolean auto,
		int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return findByG_A(groupId, auto, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63; and auto = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_A(long groupId, boolean auto,
		int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A;
			finderArgs = new Object[] { groupId, auto };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A;
			finderArgs = new Object[] {
					groupId, auto,
					
					start, end, orderByComparator
				};
		}

		List<SiteNavigationMenu> list = null;

		if (retrieveFromCache) {
			list = (List<SiteNavigationMenu>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenu siteNavigationMenu : list) {
					if ((groupId != siteNavigationMenu.getGroupId()) ||
							(auto != siteNavigationMenu.getAuto())) {
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

			query.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_AUTO_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(auto);

				if (!pagination) {
					list = (List<SiteNavigationMenu>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SiteNavigationMenu>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first site navigation menu in the ordered set where groupId = &#63; and auto = &#63;.
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByG_A_First(long groupId, boolean auto,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {
		SiteNavigationMenu siteNavigationMenu = fetchByG_A_First(groupId, auto,
				orderByComparator);

		if (siteNavigationMenu != null) {
			return siteNavigationMenu;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", auto=");
		msg.append(auto);

		msg.append("}");

		throw new NoSuchMenuException(msg.toString());
	}

	/**
	 * Returns the first site navigation menu in the ordered set where groupId = &#63; and auto = &#63;.
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByG_A_First(long groupId, boolean auto,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		List<SiteNavigationMenu> list = findByG_A(groupId, auto, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last site navigation menu in the ordered set where groupId = &#63; and auto = &#63;.
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByG_A_Last(long groupId, boolean auto,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {
		SiteNavigationMenu siteNavigationMenu = fetchByG_A_Last(groupId, auto,
				orderByComparator);

		if (siteNavigationMenu != null) {
			return siteNavigationMenu;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", auto=");
		msg.append(auto);

		msg.append("}");

		throw new NoSuchMenuException(msg.toString());
	}

	/**
	 * Returns the last site navigation menu in the ordered set where groupId = &#63; and auto = &#63;.
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByG_A_Last(long groupId, boolean auto,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		int count = countByG_A(groupId, auto);

		if (count == 0) {
			return null;
		}

		List<SiteNavigationMenu> list = findByG_A(groupId, auto, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the site navigation menus before and after the current site navigation menu in the ordered set where groupId = &#63; and auto = &#63;.
	 *
	 * @param siteNavigationMenuId the primary key of the current site navigation menu
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu[] findByG_A_PrevAndNext(
		long siteNavigationMenuId, long groupId, boolean auto,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {
		SiteNavigationMenu siteNavigationMenu = findByPrimaryKey(siteNavigationMenuId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenu[] array = new SiteNavigationMenuImpl[3];

			array[0] = getByG_A_PrevAndNext(session, siteNavigationMenu,
					groupId, auto, orderByComparator, true);

			array[1] = siteNavigationMenu;

			array[2] = getByG_A_PrevAndNext(session, siteNavigationMenu,
					groupId, auto, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SiteNavigationMenu getByG_A_PrevAndNext(Session session,
		SiteNavigationMenu siteNavigationMenu, long groupId, boolean auto,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
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

		query.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_AUTO_2);

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
			query.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(auto);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(siteNavigationMenu);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SiteNavigationMenu> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the site navigation menus that the user has permission to view where groupId = &#63; and auto = &#63;.
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @return the matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByG_A(long groupId, boolean auto) {
		return filterFindByG_A(groupId, auto, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menus that the user has permission to view where groupId = &#63; and auto = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByG_A(long groupId, boolean auto,
		int start, int end) {
		return filterFindByG_A(groupId, auto, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus that the user has permissions to view where groupId = &#63; and auto = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByG_A(long groupId, boolean auto,
		int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A(groupId, auto, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_AUTO_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SiteNavigationMenuModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SiteNavigationMenu.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SiteNavigationMenuImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SiteNavigationMenuImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(auto);

			return (List<SiteNavigationMenu>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the site navigation menus before and after the current site navigation menu in the ordered set of site navigation menus that the user has permission to view where groupId = &#63; and auto = &#63;.
	 *
	 * @param siteNavigationMenuId the primary key of the current site navigation menu
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu[] filterFindByG_A_PrevAndNext(
		long siteNavigationMenuId, long groupId, boolean auto,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_PrevAndNext(siteNavigationMenuId, groupId, auto,
				orderByComparator);
		}

		SiteNavigationMenu siteNavigationMenu = findByPrimaryKey(siteNavigationMenuId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenu[] array = new SiteNavigationMenuImpl[3];

			array[0] = filterGetByG_A_PrevAndNext(session, siteNavigationMenu,
					groupId, auto, orderByComparator, true);

			array[1] = siteNavigationMenu;

			array[2] = filterGetByG_A_PrevAndNext(session, siteNavigationMenu,
					groupId, auto, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SiteNavigationMenu filterGetByG_A_PrevAndNext(Session session,
		SiteNavigationMenu siteNavigationMenu, long groupId, boolean auto,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
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
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_AUTO_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SiteNavigationMenuModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SiteNavigationMenu.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SiteNavigationMenuImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SiteNavigationMenuImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(auto);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(siteNavigationMenu);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SiteNavigationMenu> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the site navigation menus where groupId = &#63; and auto = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 */
	@Override
	public void removeByG_A(long groupId, boolean auto) {
		for (SiteNavigationMenu siteNavigationMenu : findByG_A(groupId, auto,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(siteNavigationMenu);
		}
	}

	/**
	 * Returns the number of site navigation menus where groupId = &#63; and auto = &#63;.
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @return the number of matching site navigation menus
	 */
	@Override
	public int countByG_A(long groupId, boolean auto) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_A;

		Object[] finderArgs = new Object[] { groupId, auto };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_AUTO_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(auto);

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

	/**
	 * Returns the number of site navigation menus that the user has permission to view where groupId = &#63; and auto = &#63;.
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @return the number of matching site navigation menus that the user has permission to view
	 */
	@Override
	public int filterCountByG_A(long groupId, boolean auto) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_A(groupId, auto);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_AUTO_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SiteNavigationMenu.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(auto);

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

	private static final String _FINDER_COLUMN_G_A_GROUPID_2 = "siteNavigationMenu.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_AUTO_2 = "siteNavigationMenu.auto = ?";
	private static final String _FINDER_COLUMN_G_A_AUTO_2_SQL = "siteNavigationMenu.auto_ = ?";

	public SiteNavigationMenuPersistenceImpl() {
		setModelClass(SiteNavigationMenu.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("type", "type_");
			dbColumnNames.put("auto", "auto_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the site navigation menu in the entity cache if it is enabled.
	 *
	 * @param siteNavigationMenu the site navigation menu
	 */
	@Override
	public void cacheResult(SiteNavigationMenu siteNavigationMenu) {
		entityCache.putResult(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuImpl.class, siteNavigationMenu.getPrimaryKey(),
			siteNavigationMenu);

		siteNavigationMenu.resetOriginalValues();
	}

	/**
	 * Caches the site navigation menus in the entity cache if it is enabled.
	 *
	 * @param siteNavigationMenus the site navigation menus
	 */
	@Override
	public void cacheResult(List<SiteNavigationMenu> siteNavigationMenus) {
		for (SiteNavigationMenu siteNavigationMenu : siteNavigationMenus) {
			if (entityCache.getResult(
						SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
						SiteNavigationMenuImpl.class,
						siteNavigationMenu.getPrimaryKey()) == null) {
				cacheResult(siteNavigationMenu);
			}
			else {
				siteNavigationMenu.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all site navigation menus.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SiteNavigationMenuImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the site navigation menu.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SiteNavigationMenu siteNavigationMenu) {
		entityCache.removeResult(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuImpl.class, siteNavigationMenu.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<SiteNavigationMenu> siteNavigationMenus) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SiteNavigationMenu siteNavigationMenu : siteNavigationMenus) {
			entityCache.removeResult(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
				SiteNavigationMenuImpl.class, siteNavigationMenu.getPrimaryKey());
		}
	}

	/**
	 * Creates a new site navigation menu with the primary key. Does not add the site navigation menu to the database.
	 *
	 * @param siteNavigationMenuId the primary key for the new site navigation menu
	 * @return the new site navigation menu
	 */
	@Override
	public SiteNavigationMenu create(long siteNavigationMenuId) {
		SiteNavigationMenu siteNavigationMenu = new SiteNavigationMenuImpl();

		siteNavigationMenu.setNew(true);
		siteNavigationMenu.setPrimaryKey(siteNavigationMenuId);

		siteNavigationMenu.setCompanyId(companyProvider.getCompanyId());

		return siteNavigationMenu;
	}

	/**
	 * Removes the site navigation menu with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteNavigationMenuId the primary key of the site navigation menu
	 * @return the site navigation menu that was removed
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu remove(long siteNavigationMenuId)
		throws NoSuchMenuException {
		return remove((Serializable)siteNavigationMenuId);
	}

	/**
	 * Removes the site navigation menu with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the site navigation menu
	 * @return the site navigation menu that was removed
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu remove(Serializable primaryKey)
		throws NoSuchMenuException {
		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenu siteNavigationMenu = (SiteNavigationMenu)session.get(SiteNavigationMenuImpl.class,
					primaryKey);

			if (siteNavigationMenu == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMenuException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(siteNavigationMenu);
		}
		catch (NoSuchMenuException nsee) {
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
	protected SiteNavigationMenu removeImpl(
		SiteNavigationMenu siteNavigationMenu) {
		siteNavigationMenu = toUnwrappedModel(siteNavigationMenu);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(siteNavigationMenu)) {
				siteNavigationMenu = (SiteNavigationMenu)session.get(SiteNavigationMenuImpl.class,
						siteNavigationMenu.getPrimaryKeyObj());
			}

			if (siteNavigationMenu != null) {
				session.delete(siteNavigationMenu);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (siteNavigationMenu != null) {
			clearCache(siteNavigationMenu);
		}

		return siteNavigationMenu;
	}

	@Override
	public SiteNavigationMenu updateImpl(SiteNavigationMenu siteNavigationMenu) {
		siteNavigationMenu = toUnwrappedModel(siteNavigationMenu);

		boolean isNew = siteNavigationMenu.isNew();

		SiteNavigationMenuModelImpl siteNavigationMenuModelImpl = (SiteNavigationMenuModelImpl)siteNavigationMenu;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (siteNavigationMenu.getCreateDate() == null)) {
			if (serviceContext == null) {
				siteNavigationMenu.setCreateDate(now);
			}
			else {
				siteNavigationMenu.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!siteNavigationMenuModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				siteNavigationMenu.setModifiedDate(now);
			}
			else {
				siteNavigationMenu.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (siteNavigationMenu.isNew()) {
				session.save(siteNavigationMenu);

				siteNavigationMenu.setNew(false);
			}
			else {
				siteNavigationMenu = (SiteNavigationMenu)session.merge(siteNavigationMenu);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!SiteNavigationMenuModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					siteNavigationMenuModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					siteNavigationMenuModelImpl.getGroupId(),
					siteNavigationMenuModelImpl.getType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
				args);

			args = new Object[] {
					siteNavigationMenuModelImpl.getGroupId(),
					siteNavigationMenuModelImpl.getAuto()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((siteNavigationMenuModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						siteNavigationMenuModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { siteNavigationMenuModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((siteNavigationMenuModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						siteNavigationMenuModelImpl.getOriginalGroupId(),
						siteNavigationMenuModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);

				args = new Object[] {
						siteNavigationMenuModelImpl.getGroupId(),
						siteNavigationMenuModelImpl.getType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);
			}

			if ((siteNavigationMenuModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						siteNavigationMenuModelImpl.getOriginalGroupId(),
						siteNavigationMenuModelImpl.getOriginalAuto()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);

				args = new Object[] {
						siteNavigationMenuModelImpl.getGroupId(),
						siteNavigationMenuModelImpl.getAuto()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);
			}
		}

		entityCache.putResult(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuImpl.class, siteNavigationMenu.getPrimaryKey(),
			siteNavigationMenu, false);

		siteNavigationMenu.resetOriginalValues();

		return siteNavigationMenu;
	}

	protected SiteNavigationMenu toUnwrappedModel(
		SiteNavigationMenu siteNavigationMenu) {
		if (siteNavigationMenu instanceof SiteNavigationMenuImpl) {
			return siteNavigationMenu;
		}

		SiteNavigationMenuImpl siteNavigationMenuImpl = new SiteNavigationMenuImpl();

		siteNavigationMenuImpl.setNew(siteNavigationMenu.isNew());
		siteNavigationMenuImpl.setPrimaryKey(siteNavigationMenu.getPrimaryKey());

		siteNavigationMenuImpl.setSiteNavigationMenuId(siteNavigationMenu.getSiteNavigationMenuId());
		siteNavigationMenuImpl.setGroupId(siteNavigationMenu.getGroupId());
		siteNavigationMenuImpl.setCompanyId(siteNavigationMenu.getCompanyId());
		siteNavigationMenuImpl.setUserId(siteNavigationMenu.getUserId());
		siteNavigationMenuImpl.setUserName(siteNavigationMenu.getUserName());
		siteNavigationMenuImpl.setCreateDate(siteNavigationMenu.getCreateDate());
		siteNavigationMenuImpl.setModifiedDate(siteNavigationMenu.getModifiedDate());
		siteNavigationMenuImpl.setName(siteNavigationMenu.getName());
		siteNavigationMenuImpl.setType(siteNavigationMenu.getType());
		siteNavigationMenuImpl.setAuto(siteNavigationMenu.isAuto());

		return siteNavigationMenuImpl;
	}

	/**
	 * Returns the site navigation menu with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the site navigation menu
	 * @return the site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu findByPrimaryKey(Serializable primaryKey)
		throws NoSuchMenuException {
		SiteNavigationMenu siteNavigationMenu = fetchByPrimaryKey(primaryKey);

		if (siteNavigationMenu == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchMenuException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return siteNavigationMenu;
	}

	/**
	 * Returns the site navigation menu with the primary key or throws a {@link NoSuchMenuException} if it could not be found.
	 *
	 * @param siteNavigationMenuId the primary key of the site navigation menu
	 * @return the site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu findByPrimaryKey(long siteNavigationMenuId)
		throws NoSuchMenuException {
		return findByPrimaryKey((Serializable)siteNavigationMenuId);
	}

	/**
	 * Returns the site navigation menu with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the site navigation menu
	 * @return the site navigation menu, or <code>null</code> if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
				SiteNavigationMenuImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		SiteNavigationMenu siteNavigationMenu = (SiteNavigationMenu)serializable;

		if (siteNavigationMenu == null) {
			Session session = null;

			try {
				session = openSession();

				siteNavigationMenu = (SiteNavigationMenu)session.get(SiteNavigationMenuImpl.class,
						primaryKey);

				if (siteNavigationMenu != null) {
					cacheResult(siteNavigationMenu);
				}
				else {
					entityCache.putResult(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
						SiteNavigationMenuImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
					SiteNavigationMenuImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return siteNavigationMenu;
	}

	/**
	 * Returns the site navigation menu with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param siteNavigationMenuId the primary key of the site navigation menu
	 * @return the site navigation menu, or <code>null</code> if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByPrimaryKey(long siteNavigationMenuId) {
		return fetchByPrimaryKey((Serializable)siteNavigationMenuId);
	}

	@Override
	public Map<Serializable, SiteNavigationMenu> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SiteNavigationMenu> map = new HashMap<Serializable, SiteNavigationMenu>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SiteNavigationMenu siteNavigationMenu = fetchByPrimaryKey(primaryKey);

			if (siteNavigationMenu != null) {
				map.put(primaryKey, siteNavigationMenu);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
					SiteNavigationMenuImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (SiteNavigationMenu)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (SiteNavigationMenu siteNavigationMenu : (List<SiteNavigationMenu>)q.list()) {
				map.put(siteNavigationMenu.getPrimaryKeyObj(),
					siteNavigationMenu);

				cacheResult(siteNavigationMenu);

				uncachedPrimaryKeys.remove(siteNavigationMenu.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(SiteNavigationMenuModelImpl.ENTITY_CACHE_ENABLED,
					SiteNavigationMenuImpl.class, primaryKey, nullModel);
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
	 * Returns all the site navigation menus.
	 *
	 * @return the site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menus.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findAll(int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menus.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findAll(int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean retrieveFromCache) {
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

		List<SiteNavigationMenu> list = null;

		if (retrieveFromCache) {
			list = (List<SiteNavigationMenu>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SITENAVIGATIONMENU);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SITENAVIGATIONMENU;

				if (pagination) {
					sql = sql.concat(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SiteNavigationMenu>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SiteNavigationMenu>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the site navigation menus from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SiteNavigationMenu siteNavigationMenu : findAll()) {
			remove(siteNavigationMenu);
		}
	}

	/**
	 * Returns the number of site navigation menus.
	 *
	 * @return the number of site navigation menus
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SITENAVIGATIONMENU);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
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
	protected Map<String, Integer> getTableColumnsMap() {
		return SiteNavigationMenuModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the site navigation menu persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(SiteNavigationMenuImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_SITENAVIGATIONMENU = "SELECT siteNavigationMenu FROM SiteNavigationMenu siteNavigationMenu";
	private static final String _SQL_SELECT_SITENAVIGATIONMENU_WHERE_PKS_IN = "SELECT siteNavigationMenu FROM SiteNavigationMenu siteNavigationMenu WHERE siteNavigationMenuId IN (";
	private static final String _SQL_SELECT_SITENAVIGATIONMENU_WHERE = "SELECT siteNavigationMenu FROM SiteNavigationMenu siteNavigationMenu WHERE ";
	private static final String _SQL_COUNT_SITENAVIGATIONMENU = "SELECT COUNT(siteNavigationMenu) FROM SiteNavigationMenu siteNavigationMenu";
	private static final String _SQL_COUNT_SITENAVIGATIONMENU_WHERE = "SELECT COUNT(siteNavigationMenu) FROM SiteNavigationMenu siteNavigationMenu WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "siteNavigationMenu.siteNavigationMenuId";
	private static final String _FILTER_SQL_SELECT_SITENAVIGATIONMENU_WHERE = "SELECT DISTINCT {siteNavigationMenu.*} FROM SiteNavigationMenu siteNavigationMenu WHERE ";
	private static final String _FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {SiteNavigationMenu.*} FROM (SELECT DISTINCT siteNavigationMenu.siteNavigationMenuId FROM SiteNavigationMenu siteNavigationMenu WHERE ";
	private static final String _FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN SiteNavigationMenu ON TEMP_TABLE.siteNavigationMenuId = SiteNavigationMenu.siteNavigationMenuId";
	private static final String _FILTER_SQL_COUNT_SITENAVIGATIONMENU_WHERE = "SELECT COUNT(DISTINCT siteNavigationMenu.siteNavigationMenuId) AS COUNT_VALUE FROM SiteNavigationMenu siteNavigationMenu WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "siteNavigationMenu";
	private static final String _FILTER_ENTITY_TABLE = "SiteNavigationMenu";
	private static final String _ORDER_BY_ENTITY_ALIAS = "siteNavigationMenu.";
	private static final String _ORDER_BY_ENTITY_TABLE = "SiteNavigationMenu.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SiteNavigationMenu exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SiteNavigationMenu exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(SiteNavigationMenuPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type", "auto"
			});
}