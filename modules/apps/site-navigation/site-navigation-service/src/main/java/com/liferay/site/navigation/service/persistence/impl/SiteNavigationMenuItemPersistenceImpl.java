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
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.site.navigation.exception.NoSuchMenuItemException;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.model.impl.SiteNavigationMenuItemImpl;
import com.liferay.site.navigation.model.impl.SiteNavigationMenuItemModelImpl;
import com.liferay.site.navigation.service.persistence.SiteNavigationMenuItemPersistence;

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
 * The persistence implementation for the site navigation menu item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuItemPersistence
 * @see com.liferay.site.navigation.service.persistence.SiteNavigationMenuItemUtil
 * @generated
 */
@ProviderType
public class SiteNavigationMenuItemPersistenceImpl extends BasePersistenceImpl<SiteNavigationMenuItem>
	implements SiteNavigationMenuItemPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SiteNavigationMenuItemUtil} to access the site navigation menu item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SiteNavigationMenuItemImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuItemModelImpl.FINDER_CACHE_ENABLED,
			SiteNavigationMenuItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuItemModelImpl.FINDER_CACHE_ENABLED,
			SiteNavigationMenuItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SITENAVIGATIONMENUID =
		new FinderPath(SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuItemModelImpl.FINDER_CACHE_ENABLED,
			SiteNavigationMenuItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findBySiteNavigationMenuId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SITENAVIGATIONMENUID =
		new FinderPath(SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuItemModelImpl.FINDER_CACHE_ENABLED,
			SiteNavigationMenuItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findBySiteNavigationMenuId",
			new String[] { Long.class.getName() },
			SiteNavigationMenuItemModelImpl.SITENAVIGATIONMENUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SITENAVIGATIONMENUID = new FinderPath(SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySiteNavigationMenuId", new String[] { Long.class.getName() });

	/**
	 * Returns all the site navigation menu items where siteNavigationMenuId = &#63;.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @return the matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findBySiteNavigationMenuId(
		long siteNavigationMenuId) {
		return findBySiteNavigationMenuId(siteNavigationMenuId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menu items where siteNavigationMenuId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return findBySiteNavigationMenuId(siteNavigationMenuId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items where siteNavigationMenuId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return findBySiteNavigationMenuId(siteNavigationMenuId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items where siteNavigationMenuId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findBySiteNavigationMenuId(
		long siteNavigationMenuId, int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SITENAVIGATIONMENUID;
			finderArgs = new Object[] { siteNavigationMenuId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SITENAVIGATIONMENUID;
			finderArgs = new Object[] {
					siteNavigationMenuId,
					
					start, end, orderByComparator
				};
		}

		List<SiteNavigationMenuItem> list = null;

		if (retrieveFromCache) {
			list = (List<SiteNavigationMenuItem>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenuItem siteNavigationMenuItem : list) {
					if ((siteNavigationMenuId != siteNavigationMenuItem.getSiteNavigationMenuId())) {
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

			query.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE);

			query.append(_FINDER_COLUMN_SITENAVIGATIONMENUID_SITENAVIGATIONMENUID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(siteNavigationMenuId);

				if (!pagination) {
					list = (List<SiteNavigationMenuItem>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SiteNavigationMenuItem>)QueryUtil.list(q,
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
		SiteNavigationMenuItem siteNavigationMenuItem = fetchBySiteNavigationMenuId_First(siteNavigationMenuId,
				orderByComparator);

		if (siteNavigationMenuItem != null) {
			return siteNavigationMenuItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("siteNavigationMenuId=");
		msg.append(siteNavigationMenuId);

		msg.append("}");

		throw new NoSuchMenuItemException(msg.toString());
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
		List<SiteNavigationMenuItem> list = findBySiteNavigationMenuId(siteNavigationMenuId,
				0, 1, orderByComparator);

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
		SiteNavigationMenuItem siteNavigationMenuItem = fetchBySiteNavigationMenuId_Last(siteNavigationMenuId,
				orderByComparator);

		if (siteNavigationMenuItem != null) {
			return siteNavigationMenuItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("siteNavigationMenuId=");
		msg.append(siteNavigationMenuId);

		msg.append("}");

		throw new NoSuchMenuItemException(msg.toString());
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

		List<SiteNavigationMenuItem> list = findBySiteNavigationMenuId(siteNavigationMenuId,
				count - 1, count, orderByComparator);

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
		SiteNavigationMenuItem siteNavigationMenuItem = findByPrimaryKey(siteNavigationMenuItemId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenuItem[] array = new SiteNavigationMenuItemImpl[3];

			array[0] = getBySiteNavigationMenuId_PrevAndNext(session,
					siteNavigationMenuItem, siteNavigationMenuId,
					orderByComparator, true);

			array[1] = siteNavigationMenuItem;

			array[2] = getBySiteNavigationMenuId_PrevAndNext(session,
					siteNavigationMenuItem, siteNavigationMenuId,
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

	protected SiteNavigationMenuItem getBySiteNavigationMenuId_PrevAndNext(
		Session session, SiteNavigationMenuItem siteNavigationMenuItem,
		long siteNavigationMenuId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
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

		query.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE);

		query.append(_FINDER_COLUMN_SITENAVIGATIONMENUID_SITENAVIGATIONMENUID_2);

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
			query.append(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(siteNavigationMenuId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(siteNavigationMenuItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SiteNavigationMenuItem> list = q.list();

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
		for (SiteNavigationMenuItem siteNavigationMenuItem : findBySiteNavigationMenuId(
				siteNavigationMenuId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SITENAVIGATIONMENUID;

		Object[] finderArgs = new Object[] { siteNavigationMenuId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SITENAVIGATIONMENUITEM_WHERE);

			query.append(_FINDER_COLUMN_SITENAVIGATIONMENUID_SITENAVIGATIONMENUID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(siteNavigationMenuId);

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

	private static final String _FINDER_COLUMN_SITENAVIGATIONMENUID_SITENAVIGATIONMENUID_2 =
		"siteNavigationMenuItem.siteNavigationMenuId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PARENTSITENAVIGATIONMENUITEMID =
		new FinderPath(SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuItemModelImpl.FINDER_CACHE_ENABLED,
			SiteNavigationMenuItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByParentSiteNavigationMenuItemId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARENTSITENAVIGATIONMENUITEMID =
		new FinderPath(SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuItemModelImpl.FINDER_CACHE_ENABLED,
			SiteNavigationMenuItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByParentSiteNavigationMenuItemId",
			new String[] { Long.class.getName() },
			SiteNavigationMenuItemModelImpl.PARENTSITENAVIGATIONMENUITEMID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PARENTSITENAVIGATIONMENUITEMID =
		new FinderPath(SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByParentSiteNavigationMenuItemId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the site navigation menu items where parentSiteNavigationMenuItemId = &#63;.
	 *
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @return the matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId) {
		return findByParentSiteNavigationMenuItemId(parentSiteNavigationMenuItemId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menu items where parentSiteNavigationMenuItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return findByParentSiteNavigationMenuItemId(parentSiteNavigationMenuItemId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items where parentSiteNavigationMenuItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return findByParentSiteNavigationMenuItemId(parentSiteNavigationMenuItemId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items where parentSiteNavigationMenuItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId, int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARENTSITENAVIGATIONMENUITEMID;
			finderArgs = new Object[] { parentSiteNavigationMenuItemId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PARENTSITENAVIGATIONMENUITEMID;
			finderArgs = new Object[] {
					parentSiteNavigationMenuItemId,
					
					start, end, orderByComparator
				};
		}

		List<SiteNavigationMenuItem> list = null;

		if (retrieveFromCache) {
			list = (List<SiteNavigationMenuItem>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenuItem siteNavigationMenuItem : list) {
					if ((parentSiteNavigationMenuItemId != siteNavigationMenuItem.getParentSiteNavigationMenuItemId())) {
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

			query.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE);

			query.append(_FINDER_COLUMN_PARENTSITENAVIGATIONMENUITEMID_PARENTSITENAVIGATIONMENUITEMID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentSiteNavigationMenuItemId);

				if (!pagination) {
					list = (List<SiteNavigationMenuItem>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SiteNavigationMenuItem>)QueryUtil.list(q,
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
		SiteNavigationMenuItem siteNavigationMenuItem = fetchByParentSiteNavigationMenuItemId_First(parentSiteNavigationMenuItemId,
				orderByComparator);

		if (siteNavigationMenuItem != null) {
			return siteNavigationMenuItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentSiteNavigationMenuItemId=");
		msg.append(parentSiteNavigationMenuItemId);

		msg.append("}");

		throw new NoSuchMenuItemException(msg.toString());
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
		List<SiteNavigationMenuItem> list = findByParentSiteNavigationMenuItemId(parentSiteNavigationMenuItemId,
				0, 1, orderByComparator);

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
		SiteNavigationMenuItem siteNavigationMenuItem = fetchByParentSiteNavigationMenuItemId_Last(parentSiteNavigationMenuItemId,
				orderByComparator);

		if (siteNavigationMenuItem != null) {
			return siteNavigationMenuItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentSiteNavigationMenuItemId=");
		msg.append(parentSiteNavigationMenuItemId);

		msg.append("}");

		throw new NoSuchMenuItemException(msg.toString());
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
		int count = countByParentSiteNavigationMenuItemId(parentSiteNavigationMenuItemId);

		if (count == 0) {
			return null;
		}

		List<SiteNavigationMenuItem> list = findByParentSiteNavigationMenuItemId(parentSiteNavigationMenuItemId,
				count - 1, count, orderByComparator);

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
	public SiteNavigationMenuItem[] findByParentSiteNavigationMenuItemId_PrevAndNext(
		long siteNavigationMenuItemId, long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {
		SiteNavigationMenuItem siteNavigationMenuItem = findByPrimaryKey(siteNavigationMenuItemId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenuItem[] array = new SiteNavigationMenuItemImpl[3];

			array[0] = getByParentSiteNavigationMenuItemId_PrevAndNext(session,
					siteNavigationMenuItem, parentSiteNavigationMenuItemId,
					orderByComparator, true);

			array[1] = siteNavigationMenuItem;

			array[2] = getByParentSiteNavigationMenuItemId_PrevAndNext(session,
					siteNavigationMenuItem, parentSiteNavigationMenuItemId,
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

	protected SiteNavigationMenuItem getByParentSiteNavigationMenuItemId_PrevAndNext(
		Session session, SiteNavigationMenuItem siteNavigationMenuItem,
		long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
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

		query.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE);

		query.append(_FINDER_COLUMN_PARENTSITENAVIGATIONMENUITEMID_PARENTSITENAVIGATIONMENUITEMID_2);

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
			query.append(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(parentSiteNavigationMenuItemId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(siteNavigationMenuItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SiteNavigationMenuItem> list = q.list();

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
		for (SiteNavigationMenuItem siteNavigationMenuItem : findByParentSiteNavigationMenuItemId(
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PARENTSITENAVIGATIONMENUITEMID;

		Object[] finderArgs = new Object[] { parentSiteNavigationMenuItemId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SITENAVIGATIONMENUITEM_WHERE);

			query.append(_FINDER_COLUMN_PARENTSITENAVIGATIONMENUITEMID_PARENTSITENAVIGATIONMENUITEMID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentSiteNavigationMenuItemId);

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

	private static final String _FINDER_COLUMN_PARENTSITENAVIGATIONMENUITEMID_PARENTSITENAVIGATIONMENUITEMID_2 =
		"siteNavigationMenuItem.parentSiteNavigationMenuItemId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_S_P = new FinderPath(SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuItemModelImpl.FINDER_CACHE_ENABLED,
			SiteNavigationMenuItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByS_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_P = new FinderPath(SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuItemModelImpl.FINDER_CACHE_ENABLED,
			SiteNavigationMenuItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByS_P",
			new String[] { Long.class.getName(), Long.class.getName() },
			SiteNavigationMenuItemModelImpl.SITENAVIGATIONMENUID_COLUMN_BITMASK |
			SiteNavigationMenuItemModelImpl.PARENTSITENAVIGATIONMENUITEMID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_S_P = new FinderPath(SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByS_P",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the site navigation menu items where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @return the matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByS_P(long siteNavigationMenuId,
		long parentSiteNavigationMenuItemId) {
		return findByS_P(siteNavigationMenuId, parentSiteNavigationMenuItemId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menu items where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @return the range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByS_P(long siteNavigationMenuId,
		long parentSiteNavigationMenuItemId, int start, int end) {
		return findByS_P(siteNavigationMenuId, parentSiteNavigationMenuItemId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<SiteNavigationMenuItem> findByS_P(long siteNavigationMenuId,
		long parentSiteNavigationMenuItemId, int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {
		return findByS_P(siteNavigationMenuId, parentSiteNavigationMenuItemId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findByS_P(long siteNavigationMenuId,
		long parentSiteNavigationMenuItemId, int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_P;
			finderArgs = new Object[] {
					siteNavigationMenuId, parentSiteNavigationMenuItemId
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_S_P;
			finderArgs = new Object[] {
					siteNavigationMenuId, parentSiteNavigationMenuItemId,
					
					start, end, orderByComparator
				};
		}

		List<SiteNavigationMenuItem> list = null;

		if (retrieveFromCache) {
			list = (List<SiteNavigationMenuItem>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenuItem siteNavigationMenuItem : list) {
					if ((siteNavigationMenuId != siteNavigationMenuItem.getSiteNavigationMenuId()) ||
							(parentSiteNavigationMenuItemId != siteNavigationMenuItem.getParentSiteNavigationMenuItemId())) {
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

			query.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE);

			query.append(_FINDER_COLUMN_S_P_SITENAVIGATIONMENUID_2);

			query.append(_FINDER_COLUMN_S_P_PARENTSITENAVIGATIONMENUITEMID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(siteNavigationMenuId);

				qPos.add(parentSiteNavigationMenuItemId);

				if (!pagination) {
					list = (List<SiteNavigationMenuItem>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SiteNavigationMenuItem>)QueryUtil.list(q,
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
	 * Returns the first site navigation menu item in the ordered set where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu item
	 * @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	 */
	@Override
	public SiteNavigationMenuItem findByS_P_First(long siteNavigationMenuId,
		long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {
		SiteNavigationMenuItem siteNavigationMenuItem = fetchByS_P_First(siteNavigationMenuId,
				parentSiteNavigationMenuItemId, orderByComparator);

		if (siteNavigationMenuItem != null) {
			return siteNavigationMenuItem;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("siteNavigationMenuId=");
		msg.append(siteNavigationMenuId);

		msg.append(", parentSiteNavigationMenuItemId=");
		msg.append(parentSiteNavigationMenuItemId);

		msg.append("}");

		throw new NoSuchMenuItemException(msg.toString());
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
	public SiteNavigationMenuItem fetchByS_P_First(long siteNavigationMenuId,
		long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {
		List<SiteNavigationMenuItem> list = findByS_P(siteNavigationMenuId,
				parentSiteNavigationMenuItemId, 0, 1, orderByComparator);

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
	public SiteNavigationMenuItem findByS_P_Last(long siteNavigationMenuId,
		long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException {
		SiteNavigationMenuItem siteNavigationMenuItem = fetchByS_P_Last(siteNavigationMenuId,
				parentSiteNavigationMenuItemId, orderByComparator);

		if (siteNavigationMenuItem != null) {
			return siteNavigationMenuItem;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("siteNavigationMenuId=");
		msg.append(siteNavigationMenuId);

		msg.append(", parentSiteNavigationMenuItemId=");
		msg.append(parentSiteNavigationMenuItemId);

		msg.append("}");

		throw new NoSuchMenuItemException(msg.toString());
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
	public SiteNavigationMenuItem fetchByS_P_Last(long siteNavigationMenuId,
		long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {
		int count = countByS_P(siteNavigationMenuId,
				parentSiteNavigationMenuItemId);

		if (count == 0) {
			return null;
		}

		List<SiteNavigationMenuItem> list = findByS_P(siteNavigationMenuId,
				parentSiteNavigationMenuItemId, count - 1, count,
				orderByComparator);

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
		SiteNavigationMenuItem siteNavigationMenuItem = findByPrimaryKey(siteNavigationMenuItemId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenuItem[] array = new SiteNavigationMenuItemImpl[3];

			array[0] = getByS_P_PrevAndNext(session, siteNavigationMenuItem,
					siteNavigationMenuId, parentSiteNavigationMenuItemId,
					orderByComparator, true);

			array[1] = siteNavigationMenuItem;

			array[2] = getByS_P_PrevAndNext(session, siteNavigationMenuItem,
					siteNavigationMenuId, parentSiteNavigationMenuItemId,
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

	protected SiteNavigationMenuItem getByS_P_PrevAndNext(Session session,
		SiteNavigationMenuItem siteNavigationMenuItem,
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
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

		query.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE);

		query.append(_FINDER_COLUMN_S_P_SITENAVIGATIONMENUID_2);

		query.append(_FINDER_COLUMN_S_P_PARENTSITENAVIGATIONMENUITEMID_2);

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
			query.append(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(siteNavigationMenuId);

		qPos.add(parentSiteNavigationMenuItemId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(siteNavigationMenuItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SiteNavigationMenuItem> list = q.list();

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
	public void removeByS_P(long siteNavigationMenuId,
		long parentSiteNavigationMenuItemId) {
		for (SiteNavigationMenuItem siteNavigationMenuItem : findByS_P(
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
	public int countByS_P(long siteNavigationMenuId,
		long parentSiteNavigationMenuItemId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_S_P;

		Object[] finderArgs = new Object[] {
				siteNavigationMenuId, parentSiteNavigationMenuItemId
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SITENAVIGATIONMENUITEM_WHERE);

			query.append(_FINDER_COLUMN_S_P_SITENAVIGATIONMENUID_2);

			query.append(_FINDER_COLUMN_S_P_PARENTSITENAVIGATIONMENUITEMID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(siteNavigationMenuId);

				qPos.add(parentSiteNavigationMenuItemId);

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

	private static final String _FINDER_COLUMN_S_P_SITENAVIGATIONMENUID_2 = "siteNavigationMenuItem.siteNavigationMenuId = ? AND ";
	private static final String _FINDER_COLUMN_S_P_PARENTSITENAVIGATIONMENUITEMID_2 =
		"siteNavigationMenuItem.parentSiteNavigationMenuItemId = ?";

	public SiteNavigationMenuItemPersistenceImpl() {
		setModelClass(SiteNavigationMenuItem.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("type", "type_");
			dbColumnNames.put("order", "order_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the site navigation menu item in the entity cache if it is enabled.
	 *
	 * @param siteNavigationMenuItem the site navigation menu item
	 */
	@Override
	public void cacheResult(SiteNavigationMenuItem siteNavigationMenuItem) {
		entityCache.putResult(SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuItemImpl.class,
			siteNavigationMenuItem.getPrimaryKey(), siteNavigationMenuItem);

		siteNavigationMenuItem.resetOriginalValues();
	}

	/**
	 * Caches the site navigation menu items in the entity cache if it is enabled.
	 *
	 * @param siteNavigationMenuItems the site navigation menu items
	 */
	@Override
	public void cacheResult(
		List<SiteNavigationMenuItem> siteNavigationMenuItems) {
		for (SiteNavigationMenuItem siteNavigationMenuItem : siteNavigationMenuItems) {
			if (entityCache.getResult(
						SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
						SiteNavigationMenuItemImpl.class,
						siteNavigationMenuItem.getPrimaryKey()) == null) {
				cacheResult(siteNavigationMenuItem);
			}
			else {
				siteNavigationMenuItem.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all site navigation menu items.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SiteNavigationMenuItemImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the site navigation menu item.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SiteNavigationMenuItem siteNavigationMenuItem) {
		entityCache.removeResult(SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuItemImpl.class,
			siteNavigationMenuItem.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<SiteNavigationMenuItem> siteNavigationMenuItems) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SiteNavigationMenuItem siteNavigationMenuItem : siteNavigationMenuItems) {
			entityCache.removeResult(SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
				SiteNavigationMenuItemImpl.class,
				siteNavigationMenuItem.getPrimaryKey());
		}
	}

	/**
	 * Creates a new site navigation menu item with the primary key. Does not add the site navigation menu item to the database.
	 *
	 * @param siteNavigationMenuItemId the primary key for the new site navigation menu item
	 * @return the new site navigation menu item
	 */
	@Override
	public SiteNavigationMenuItem create(long siteNavigationMenuItemId) {
		SiteNavigationMenuItem siteNavigationMenuItem = new SiteNavigationMenuItemImpl();

		siteNavigationMenuItem.setNew(true);
		siteNavigationMenuItem.setPrimaryKey(siteNavigationMenuItemId);

		siteNavigationMenuItem.setCompanyId(companyProvider.getCompanyId());

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

			SiteNavigationMenuItem siteNavigationMenuItem = (SiteNavigationMenuItem)session.get(SiteNavigationMenuItemImpl.class,
					primaryKey);

			if (siteNavigationMenuItem == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMenuItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(siteNavigationMenuItem);
		}
		catch (NoSuchMenuItemException nsee) {
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
	protected SiteNavigationMenuItem removeImpl(
		SiteNavigationMenuItem siteNavigationMenuItem) {
		siteNavigationMenuItem = toUnwrappedModel(siteNavigationMenuItem);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(siteNavigationMenuItem)) {
				siteNavigationMenuItem = (SiteNavigationMenuItem)session.get(SiteNavigationMenuItemImpl.class,
						siteNavigationMenuItem.getPrimaryKeyObj());
			}

			if (siteNavigationMenuItem != null) {
				session.delete(siteNavigationMenuItem);
			}
		}
		catch (Exception e) {
			throw processException(e);
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
		siteNavigationMenuItem = toUnwrappedModel(siteNavigationMenuItem);

		boolean isNew = siteNavigationMenuItem.isNew();

		SiteNavigationMenuItemModelImpl siteNavigationMenuItemModelImpl = (SiteNavigationMenuItemModelImpl)siteNavigationMenuItem;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (siteNavigationMenuItem.getCreateDate() == null)) {
			if (serviceContext == null) {
				siteNavigationMenuItem.setCreateDate(now);
			}
			else {
				siteNavigationMenuItem.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!siteNavigationMenuItemModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				siteNavigationMenuItem.setModifiedDate(now);
			}
			else {
				siteNavigationMenuItem.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (siteNavigationMenuItem.isNew()) {
				session.save(siteNavigationMenuItem);

				siteNavigationMenuItem.setNew(false);
			}
			else {
				siteNavigationMenuItem = (SiteNavigationMenuItem)session.merge(siteNavigationMenuItem);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!SiteNavigationMenuItemModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					siteNavigationMenuItemModelImpl.getSiteNavigationMenuId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_SITENAVIGATIONMENUID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SITENAVIGATIONMENUID,
				args);

			args = new Object[] {
					siteNavigationMenuItemModelImpl.getParentSiteNavigationMenuItemId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_PARENTSITENAVIGATIONMENUITEMID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARENTSITENAVIGATIONMENUITEMID,
				args);

			args = new Object[] {
					siteNavigationMenuItemModelImpl.getSiteNavigationMenuId(),
					siteNavigationMenuItemModelImpl.getParentSiteNavigationMenuItemId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_S_P, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_P,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((siteNavigationMenuItemModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SITENAVIGATIONMENUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						siteNavigationMenuItemModelImpl.getOriginalSiteNavigationMenuId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SITENAVIGATIONMENUID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SITENAVIGATIONMENUID,
					args);

				args = new Object[] {
						siteNavigationMenuItemModelImpl.getSiteNavigationMenuId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SITENAVIGATIONMENUID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SITENAVIGATIONMENUID,
					args);
			}

			if ((siteNavigationMenuItemModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARENTSITENAVIGATIONMENUITEMID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						siteNavigationMenuItemModelImpl.getOriginalParentSiteNavigationMenuItemId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_PARENTSITENAVIGATIONMENUITEMID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARENTSITENAVIGATIONMENUITEMID,
					args);

				args = new Object[] {
						siteNavigationMenuItemModelImpl.getParentSiteNavigationMenuItemId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_PARENTSITENAVIGATIONMENUITEMID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARENTSITENAVIGATIONMENUITEMID,
					args);
			}

			if ((siteNavigationMenuItemModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						siteNavigationMenuItemModelImpl.getOriginalSiteNavigationMenuId(),
						siteNavigationMenuItemModelImpl.getOriginalParentSiteNavigationMenuItemId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_S_P, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_P,
					args);

				args = new Object[] {
						siteNavigationMenuItemModelImpl.getSiteNavigationMenuId(),
						siteNavigationMenuItemModelImpl.getParentSiteNavigationMenuItemId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_S_P, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_P,
					args);
			}
		}

		entityCache.putResult(SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
			SiteNavigationMenuItemImpl.class,
			siteNavigationMenuItem.getPrimaryKey(), siteNavigationMenuItem,
			false);

		siteNavigationMenuItem.resetOriginalValues();

		return siteNavigationMenuItem;
	}

	protected SiteNavigationMenuItem toUnwrappedModel(
		SiteNavigationMenuItem siteNavigationMenuItem) {
		if (siteNavigationMenuItem instanceof SiteNavigationMenuItemImpl) {
			return siteNavigationMenuItem;
		}

		SiteNavigationMenuItemImpl siteNavigationMenuItemImpl = new SiteNavigationMenuItemImpl();

		siteNavigationMenuItemImpl.setNew(siteNavigationMenuItem.isNew());
		siteNavigationMenuItemImpl.setPrimaryKey(siteNavigationMenuItem.getPrimaryKey());

		siteNavigationMenuItemImpl.setSiteNavigationMenuItemId(siteNavigationMenuItem.getSiteNavigationMenuItemId());
		siteNavigationMenuItemImpl.setGroupId(siteNavigationMenuItem.getGroupId());
		siteNavigationMenuItemImpl.setCompanyId(siteNavigationMenuItem.getCompanyId());
		siteNavigationMenuItemImpl.setUserId(siteNavigationMenuItem.getUserId());
		siteNavigationMenuItemImpl.setUserName(siteNavigationMenuItem.getUserName());
		siteNavigationMenuItemImpl.setCreateDate(siteNavigationMenuItem.getCreateDate());
		siteNavigationMenuItemImpl.setModifiedDate(siteNavigationMenuItem.getModifiedDate());
		siteNavigationMenuItemImpl.setSiteNavigationMenuId(siteNavigationMenuItem.getSiteNavigationMenuId());
		siteNavigationMenuItemImpl.setParentSiteNavigationMenuItemId(siteNavigationMenuItem.getParentSiteNavigationMenuItemId());
		siteNavigationMenuItemImpl.setType(siteNavigationMenuItem.getType());
		siteNavigationMenuItemImpl.setTypeSettings(siteNavigationMenuItem.getTypeSettings());
		siteNavigationMenuItemImpl.setOrder(siteNavigationMenuItem.getOrder());

		return siteNavigationMenuItemImpl;
	}

	/**
	 * Returns the site navigation menu item with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the site navigation menu item
	 * @return the site navigation menu item
	 * @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenuItem findByPrimaryKey(Serializable primaryKey)
		throws NoSuchMenuItemException {
		SiteNavigationMenuItem siteNavigationMenuItem = fetchByPrimaryKey(primaryKey);

		if (siteNavigationMenuItem == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchMenuItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return siteNavigationMenuItem;
	}

	/**
	 * Returns the site navigation menu item with the primary key or throws a {@link NoSuchMenuItemException} if it could not be found.
	 *
	 * @param siteNavigationMenuItemId the primary key of the site navigation menu item
	 * @return the site navigation menu item
	 * @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenuItem findByPrimaryKey(
		long siteNavigationMenuItemId) throws NoSuchMenuItemException {
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
		Serializable serializable = entityCache.getResult(SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
				SiteNavigationMenuItemImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		SiteNavigationMenuItem siteNavigationMenuItem = (SiteNavigationMenuItem)serializable;

		if (siteNavigationMenuItem == null) {
			Session session = null;

			try {
				session = openSession();

				siteNavigationMenuItem = (SiteNavigationMenuItem)session.get(SiteNavigationMenuItemImpl.class,
						primaryKey);

				if (siteNavigationMenuItem != null) {
					cacheResult(siteNavigationMenuItem);
				}
				else {
					entityCache.putResult(SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
						SiteNavigationMenuItemImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
					SiteNavigationMenuItemImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
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
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SiteNavigationMenuItem> map = new HashMap<Serializable, SiteNavigationMenuItem>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SiteNavigationMenuItem siteNavigationMenuItem = fetchByPrimaryKey(primaryKey);

			if (siteNavigationMenuItem != null) {
				map.put(primaryKey, siteNavigationMenuItem);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
					SiteNavigationMenuItemImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (SiteNavigationMenuItem)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE_PKS_IN);

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

			for (SiteNavigationMenuItem siteNavigationMenuItem : (List<SiteNavigationMenuItem>)q.list()) {
				map.put(siteNavigationMenuItem.getPrimaryKeyObj(),
					siteNavigationMenuItem);

				cacheResult(siteNavigationMenuItem);

				uncachedPrimaryKeys.remove(siteNavigationMenuItem.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(SiteNavigationMenuItemModelImpl.ENTITY_CACHE_ENABLED,
					SiteNavigationMenuItemImpl.class, primaryKey, nullModel);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findAll(int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menu items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of site navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> findAll(int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
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

		List<SiteNavigationMenuItem> list = null;

		if (retrieveFromCache) {
			list = (List<SiteNavigationMenuItem>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SITENAVIGATIONMENUITEM);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SITENAVIGATIONMENUITEM;

				if (pagination) {
					sql = sql.concat(SiteNavigationMenuItemModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SiteNavigationMenuItem>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SiteNavigationMenuItem>)QueryUtil.list(q,
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
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SITENAVIGATIONMENUITEM);

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
		return SiteNavigationMenuItemModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the site navigation menu item persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(SiteNavigationMenuItemImpl.class.getName());
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
	private static final String _SQL_SELECT_SITENAVIGATIONMENUITEM = "SELECT siteNavigationMenuItem FROM SiteNavigationMenuItem siteNavigationMenuItem";
	private static final String _SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE_PKS_IN = "SELECT siteNavigationMenuItem FROM SiteNavigationMenuItem siteNavigationMenuItem WHERE siteNavigationMenuItemId IN (";
	private static final String _SQL_SELECT_SITENAVIGATIONMENUITEM_WHERE = "SELECT siteNavigationMenuItem FROM SiteNavigationMenuItem siteNavigationMenuItem WHERE ";
	private static final String _SQL_COUNT_SITENAVIGATIONMENUITEM = "SELECT COUNT(siteNavigationMenuItem) FROM SiteNavigationMenuItem siteNavigationMenuItem";
	private static final String _SQL_COUNT_SITENAVIGATIONMENUITEM_WHERE = "SELECT COUNT(siteNavigationMenuItem) FROM SiteNavigationMenuItem siteNavigationMenuItem WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "siteNavigationMenuItem.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SiteNavigationMenuItem exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SiteNavigationMenuItem exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(SiteNavigationMenuItemPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type", "order"
			});
}