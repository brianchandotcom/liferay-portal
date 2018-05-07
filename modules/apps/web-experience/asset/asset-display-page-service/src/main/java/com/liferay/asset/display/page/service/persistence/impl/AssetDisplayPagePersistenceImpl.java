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

package com.liferay.asset.display.page.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.display.page.exception.NoSuchDisplayPageException;
import com.liferay.asset.display.page.model.AssetDisplayPage;
import com.liferay.asset.display.page.model.impl.AssetDisplayPageImpl;
import com.liferay.asset.display.page.model.impl.AssetDisplayPageModelImpl;
import com.liferay.asset.display.page.service.persistence.AssetDisplayPagePersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the asset display page service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayPagePersistence
 * @see com.liferay.asset.display.page.service.persistence.AssetDisplayPageUtil
 * @generated
 */
@ProviderType
public class AssetDisplayPagePersistenceImpl extends BasePersistenceImpl<AssetDisplayPage>
	implements AssetDisplayPagePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AssetDisplayPageUtil} to access the asset display page persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AssetDisplayPageImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AssetDisplayPageModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageModelImpl.FINDER_CACHE_ENABLED,
			AssetDisplayPageImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AssetDisplayPageModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageModelImpl.FINDER_CACHE_ENABLED,
			AssetDisplayPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetDisplayPageModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ASSETENTRYID =
		new FinderPath(AssetDisplayPageModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageModelImpl.FINDER_CACHE_ENABLED,
			AssetDisplayPageImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByAssetEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID =
		new FinderPath(AssetDisplayPageModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageModelImpl.FINDER_CACHE_ENABLED,
			AssetDisplayPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAssetEntryId",
			new String[] { Long.class.getName() },
			AssetDisplayPageModelImpl.ASSETENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ASSETENTRYID = new FinderPath(AssetDisplayPageModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAssetEntryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the asset display pages where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the matching asset display pages
	 */
	@Override
	public List<AssetDisplayPage> findByAssetEntryId(long assetEntryId) {
		return findByAssetEntryId(assetEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset display pages where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset display pages
	 * @param end the upper bound of the range of asset display pages (not inclusive)
	 * @return the range of matching asset display pages
	 */
	@Override
	public List<AssetDisplayPage> findByAssetEntryId(long assetEntryId,
		int start, int end) {
		return findByAssetEntryId(assetEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset display pages where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset display pages
	 * @param end the upper bound of the range of asset display pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset display pages
	 */
	@Override
	public List<AssetDisplayPage> findByAssetEntryId(long assetEntryId,
		int start, int end,
		OrderByComparator<AssetDisplayPage> orderByComparator) {
		return findByAssetEntryId(assetEntryId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the asset display pages where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset display pages
	 * @param end the upper bound of the range of asset display pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset display pages
	 */
	@Override
	public List<AssetDisplayPage> findByAssetEntryId(long assetEntryId,
		int start, int end,
		OrderByComparator<AssetDisplayPage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID;
			finderArgs = new Object[] { assetEntryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ASSETENTRYID;
			finderArgs = new Object[] {
					assetEntryId,
					
					start, end, orderByComparator
				};
		}

		List<AssetDisplayPage> list = null;

		if (retrieveFromCache) {
			list = (List<AssetDisplayPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetDisplayPage assetDisplayPage : list) {
					if ((assetEntryId != assetDisplayPage.getAssetEntryId())) {
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

			query.append(_SQL_SELECT_ASSETDISPLAYPAGE_WHERE);

			query.append(_FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetDisplayPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				if (!pagination) {
					list = (List<AssetDisplayPage>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetDisplayPage>)QueryUtil.list(q,
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
	 * Returns the first asset display page in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page
	 * @throws NoSuchDisplayPageException if a matching asset display page could not be found
	 */
	@Override
	public AssetDisplayPage findByAssetEntryId_First(long assetEntryId,
		OrderByComparator<AssetDisplayPage> orderByComparator)
		throws NoSuchDisplayPageException {
		AssetDisplayPage assetDisplayPage = fetchByAssetEntryId_First(assetEntryId,
				orderByComparator);

		if (assetDisplayPage != null) {
			return assetDisplayPage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetEntryId=");
		msg.append(assetEntryId);

		msg.append("}");

		throw new NoSuchDisplayPageException(msg.toString());
	}

	/**
	 * Returns the first asset display page in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page, or <code>null</code> if a matching asset display page could not be found
	 */
	@Override
	public AssetDisplayPage fetchByAssetEntryId_First(long assetEntryId,
		OrderByComparator<AssetDisplayPage> orderByComparator) {
		List<AssetDisplayPage> list = findByAssetEntryId(assetEntryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset display page in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset display page
	 * @throws NoSuchDisplayPageException if a matching asset display page could not be found
	 */
	@Override
	public AssetDisplayPage findByAssetEntryId_Last(long assetEntryId,
		OrderByComparator<AssetDisplayPage> orderByComparator)
		throws NoSuchDisplayPageException {
		AssetDisplayPage assetDisplayPage = fetchByAssetEntryId_Last(assetEntryId,
				orderByComparator);

		if (assetDisplayPage != null) {
			return assetDisplayPage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetEntryId=");
		msg.append(assetEntryId);

		msg.append("}");

		throw new NoSuchDisplayPageException(msg.toString());
	}

	/**
	 * Returns the last asset display page in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset display page, or <code>null</code> if a matching asset display page could not be found
	 */
	@Override
	public AssetDisplayPage fetchByAssetEntryId_Last(long assetEntryId,
		OrderByComparator<AssetDisplayPage> orderByComparator) {
		int count = countByAssetEntryId(assetEntryId);

		if (count == 0) {
			return null;
		}

		List<AssetDisplayPage> list = findByAssetEntryId(assetEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset display pages before and after the current asset display page in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetDisplayPageId the primary key of the current asset display page
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset display page
	 * @throws NoSuchDisplayPageException if a asset display page with the primary key could not be found
	 */
	@Override
	public AssetDisplayPage[] findByAssetEntryId_PrevAndNext(
		long assetDisplayPageId, long assetEntryId,
		OrderByComparator<AssetDisplayPage> orderByComparator)
		throws NoSuchDisplayPageException {
		AssetDisplayPage assetDisplayPage = findByPrimaryKey(assetDisplayPageId);

		Session session = null;

		try {
			session = openSession();

			AssetDisplayPage[] array = new AssetDisplayPageImpl[3];

			array[0] = getByAssetEntryId_PrevAndNext(session, assetDisplayPage,
					assetEntryId, orderByComparator, true);

			array[1] = assetDisplayPage;

			array[2] = getByAssetEntryId_PrevAndNext(session, assetDisplayPage,
					assetEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetDisplayPage getByAssetEntryId_PrevAndNext(Session session,
		AssetDisplayPage assetDisplayPage, long assetEntryId,
		OrderByComparator<AssetDisplayPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETDISPLAYPAGE_WHERE);

		query.append(_FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2);

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
			query.append(AssetDisplayPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(assetEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetDisplayPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetDisplayPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset display pages where assetEntryId = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 */
	@Override
	public void removeByAssetEntryId(long assetEntryId) {
		for (AssetDisplayPage assetDisplayPage : findByAssetEntryId(
				assetEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetDisplayPage);
		}
	}

	/**
	 * Returns the number of asset display pages where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the number of matching asset display pages
	 */
	@Override
	public int countByAssetEntryId(long assetEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ASSETENTRYID;

		Object[] finderArgs = new Object[] { assetEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETDISPLAYPAGE_WHERE);

			query.append(_FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

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

	private static final String _FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2 = "assetDisplayPage.assetEntryId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_A_L = new FinderPath(AssetDisplayPageModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageModelImpl.FINDER_CACHE_ENABLED,
			AssetDisplayPageImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByA_L",
			new String[] { Long.class.getName(), Long.class.getName() },
			AssetDisplayPageModelImpl.ASSETENTRYID_COLUMN_BITMASK |
			AssetDisplayPageModelImpl.LAYOUTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_A_L = new FinderPath(AssetDisplayPageModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_L",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the asset display page where assetEntryId = &#63; and layoutId = &#63; or throws a {@link NoSuchDisplayPageException} if it could not be found.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param layoutId the layout ID
	 * @return the matching asset display page
	 * @throws NoSuchDisplayPageException if a matching asset display page could not be found
	 */
	@Override
	public AssetDisplayPage findByA_L(long assetEntryId, long layoutId)
		throws NoSuchDisplayPageException {
		AssetDisplayPage assetDisplayPage = fetchByA_L(assetEntryId, layoutId);

		if (assetDisplayPage == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetEntryId=");
			msg.append(assetEntryId);

			msg.append(", layoutId=");
			msg.append(layoutId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchDisplayPageException(msg.toString());
		}

		return assetDisplayPage;
	}

	/**
	 * Returns the asset display page where assetEntryId = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param layoutId the layout ID
	 * @return the matching asset display page, or <code>null</code> if a matching asset display page could not be found
	 */
	@Override
	public AssetDisplayPage fetchByA_L(long assetEntryId, long layoutId) {
		return fetchByA_L(assetEntryId, layoutId, true);
	}

	/**
	 * Returns the asset display page where assetEntryId = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param layoutId the layout ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching asset display page, or <code>null</code> if a matching asset display page could not be found
	 */
	@Override
	public AssetDisplayPage fetchByA_L(long assetEntryId, long layoutId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { assetEntryId, layoutId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_A_L,
					finderArgs, this);
		}

		if (result instanceof AssetDisplayPage) {
			AssetDisplayPage assetDisplayPage = (AssetDisplayPage)result;

			if ((assetEntryId != assetDisplayPage.getAssetEntryId()) ||
					(layoutId != assetDisplayPage.getLayoutId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ASSETDISPLAYPAGE_WHERE);

			query.append(_FINDER_COLUMN_A_L_ASSETENTRYID_2);

			query.append(_FINDER_COLUMN_A_L_LAYOUTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				qPos.add(layoutId);

				List<AssetDisplayPage> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_A_L, finderArgs,
						list);
				}
				else {
					AssetDisplayPage assetDisplayPage = list.get(0);

					result = assetDisplayPage;

					cacheResult(assetDisplayPage);

					if ((assetDisplayPage.getAssetEntryId() != assetEntryId) ||
							(assetDisplayPage.getLayoutId() != layoutId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_A_L,
							finderArgs, assetDisplayPage);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_A_L, finderArgs);

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
			return (AssetDisplayPage)result;
		}
	}

	/**
	 * Removes the asset display page where assetEntryId = &#63; and layoutId = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param layoutId the layout ID
	 * @return the asset display page that was removed
	 */
	@Override
	public AssetDisplayPage removeByA_L(long assetEntryId, long layoutId)
		throws NoSuchDisplayPageException {
		AssetDisplayPage assetDisplayPage = findByA_L(assetEntryId, layoutId);

		return remove(assetDisplayPage);
	}

	/**
	 * Returns the number of asset display pages where assetEntryId = &#63; and layoutId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param layoutId the layout ID
	 * @return the number of matching asset display pages
	 */
	@Override
	public int countByA_L(long assetEntryId, long layoutId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_A_L;

		Object[] finderArgs = new Object[] { assetEntryId, layoutId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETDISPLAYPAGE_WHERE);

			query.append(_FINDER_COLUMN_A_L_ASSETENTRYID_2);

			query.append(_FINDER_COLUMN_A_L_LAYOUTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

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

	private static final String _FINDER_COLUMN_A_L_ASSETENTRYID_2 = "assetDisplayPage.assetEntryId = ? AND ";
	private static final String _FINDER_COLUMN_A_L_LAYOUTID_2 = "assetDisplayPage.layoutId = ?";

	public AssetDisplayPagePersistenceImpl() {
		setModelClass(AssetDisplayPage.class);
	}

	/**
	 * Caches the asset display page in the entity cache if it is enabled.
	 *
	 * @param assetDisplayPage the asset display page
	 */
	@Override
	public void cacheResult(AssetDisplayPage assetDisplayPage) {
		entityCache.putResult(AssetDisplayPageModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageImpl.class, assetDisplayPage.getPrimaryKey(),
			assetDisplayPage);

		finderCache.putResult(FINDER_PATH_FETCH_BY_A_L,
			new Object[] {
				assetDisplayPage.getAssetEntryId(),
				assetDisplayPage.getLayoutId()
			}, assetDisplayPage);

		assetDisplayPage.resetOriginalValues();
	}

	/**
	 * Caches the asset display pages in the entity cache if it is enabled.
	 *
	 * @param assetDisplayPages the asset display pages
	 */
	@Override
	public void cacheResult(List<AssetDisplayPage> assetDisplayPages) {
		for (AssetDisplayPage assetDisplayPage : assetDisplayPages) {
			if (entityCache.getResult(
						AssetDisplayPageModelImpl.ENTITY_CACHE_ENABLED,
						AssetDisplayPageImpl.class,
						assetDisplayPage.getPrimaryKey()) == null) {
				cacheResult(assetDisplayPage);
			}
			else {
				assetDisplayPage.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset display pages.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetDisplayPageImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset display page.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetDisplayPage assetDisplayPage) {
		entityCache.removeResult(AssetDisplayPageModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageImpl.class, assetDisplayPage.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((AssetDisplayPageModelImpl)assetDisplayPage,
			true);
	}

	@Override
	public void clearCache(List<AssetDisplayPage> assetDisplayPages) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetDisplayPage assetDisplayPage : assetDisplayPages) {
			entityCache.removeResult(AssetDisplayPageModelImpl.ENTITY_CACHE_ENABLED,
				AssetDisplayPageImpl.class, assetDisplayPage.getPrimaryKey());

			clearUniqueFindersCache((AssetDisplayPageModelImpl)assetDisplayPage,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetDisplayPageModelImpl assetDisplayPageModelImpl) {
		Object[] args = new Object[] {
				assetDisplayPageModelImpl.getAssetEntryId(),
				assetDisplayPageModelImpl.getLayoutId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_A_L, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_A_L, args,
			assetDisplayPageModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		AssetDisplayPageModelImpl assetDisplayPageModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					assetDisplayPageModelImpl.getAssetEntryId(),
					assetDisplayPageModelImpl.getLayoutId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_A_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_A_L, args);
		}

		if ((assetDisplayPageModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_A_L.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					assetDisplayPageModelImpl.getOriginalAssetEntryId(),
					assetDisplayPageModelImpl.getOriginalLayoutId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_A_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_A_L, args);
		}
	}

	/**
	 * Creates a new asset display page with the primary key. Does not add the asset display page to the database.
	 *
	 * @param assetDisplayPageId the primary key for the new asset display page
	 * @return the new asset display page
	 */
	@Override
	public AssetDisplayPage create(long assetDisplayPageId) {
		AssetDisplayPage assetDisplayPage = new AssetDisplayPageImpl();

		assetDisplayPage.setNew(true);
		assetDisplayPage.setPrimaryKey(assetDisplayPageId);

		return assetDisplayPage;
	}

	/**
	 * Removes the asset display page with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetDisplayPageId the primary key of the asset display page
	 * @return the asset display page that was removed
	 * @throws NoSuchDisplayPageException if a asset display page with the primary key could not be found
	 */
	@Override
	public AssetDisplayPage remove(long assetDisplayPageId)
		throws NoSuchDisplayPageException {
		return remove((Serializable)assetDisplayPageId);
	}

	/**
	 * Removes the asset display page with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset display page
	 * @return the asset display page that was removed
	 * @throws NoSuchDisplayPageException if a asset display page with the primary key could not be found
	 */
	@Override
	public AssetDisplayPage remove(Serializable primaryKey)
		throws NoSuchDisplayPageException {
		Session session = null;

		try {
			session = openSession();

			AssetDisplayPage assetDisplayPage = (AssetDisplayPage)session.get(AssetDisplayPageImpl.class,
					primaryKey);

			if (assetDisplayPage == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDisplayPageException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(assetDisplayPage);
		}
		catch (NoSuchDisplayPageException nsee) {
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
	protected AssetDisplayPage removeImpl(AssetDisplayPage assetDisplayPage) {
		assetDisplayPage = toUnwrappedModel(assetDisplayPage);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetDisplayPage)) {
				assetDisplayPage = (AssetDisplayPage)session.get(AssetDisplayPageImpl.class,
						assetDisplayPage.getPrimaryKeyObj());
			}

			if (assetDisplayPage != null) {
				session.delete(assetDisplayPage);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (assetDisplayPage != null) {
			clearCache(assetDisplayPage);
		}

		return assetDisplayPage;
	}

	@Override
	public AssetDisplayPage updateImpl(AssetDisplayPage assetDisplayPage) {
		assetDisplayPage = toUnwrappedModel(assetDisplayPage);

		boolean isNew = assetDisplayPage.isNew();

		AssetDisplayPageModelImpl assetDisplayPageModelImpl = (AssetDisplayPageModelImpl)assetDisplayPage;

		Session session = null;

		try {
			session = openSession();

			if (assetDisplayPage.isNew()) {
				session.save(assetDisplayPage);

				assetDisplayPage.setNew(false);
			}
			else {
				assetDisplayPage = (AssetDisplayPage)session.merge(assetDisplayPage);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AssetDisplayPageModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					assetDisplayPageModelImpl.getAssetEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((assetDisplayPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetDisplayPageModelImpl.getOriginalAssetEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID,
					args);

				args = new Object[] { assetDisplayPageModelImpl.getAssetEntryId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID,
					args);
			}
		}

		entityCache.putResult(AssetDisplayPageModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageImpl.class, assetDisplayPage.getPrimaryKey(),
			assetDisplayPage, false);

		clearUniqueFindersCache(assetDisplayPageModelImpl, false);
		cacheUniqueFindersCache(assetDisplayPageModelImpl);

		assetDisplayPage.resetOriginalValues();

		return assetDisplayPage;
	}

	protected AssetDisplayPage toUnwrappedModel(
		AssetDisplayPage assetDisplayPage) {
		if (assetDisplayPage instanceof AssetDisplayPageImpl) {
			return assetDisplayPage;
		}

		AssetDisplayPageImpl assetDisplayPageImpl = new AssetDisplayPageImpl();

		assetDisplayPageImpl.setNew(assetDisplayPage.isNew());
		assetDisplayPageImpl.setPrimaryKey(assetDisplayPage.getPrimaryKey());

		assetDisplayPageImpl.setAssetDisplayPageId(assetDisplayPage.getAssetDisplayPageId());
		assetDisplayPageImpl.setAssetEntryId(assetDisplayPage.getAssetEntryId());
		assetDisplayPageImpl.setLayoutId(assetDisplayPage.getLayoutId());

		return assetDisplayPageImpl;
	}

	/**
	 * Returns the asset display page with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset display page
	 * @return the asset display page
	 * @throws NoSuchDisplayPageException if a asset display page with the primary key could not be found
	 */
	@Override
	public AssetDisplayPage findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDisplayPageException {
		AssetDisplayPage assetDisplayPage = fetchByPrimaryKey(primaryKey);

		if (assetDisplayPage == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDisplayPageException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return assetDisplayPage;
	}

	/**
	 * Returns the asset display page with the primary key or throws a {@link NoSuchDisplayPageException} if it could not be found.
	 *
	 * @param assetDisplayPageId the primary key of the asset display page
	 * @return the asset display page
	 * @throws NoSuchDisplayPageException if a asset display page with the primary key could not be found
	 */
	@Override
	public AssetDisplayPage findByPrimaryKey(long assetDisplayPageId)
		throws NoSuchDisplayPageException {
		return findByPrimaryKey((Serializable)assetDisplayPageId);
	}

	/**
	 * Returns the asset display page with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset display page
	 * @return the asset display page, or <code>null</code> if a asset display page with the primary key could not be found
	 */
	@Override
	public AssetDisplayPage fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(AssetDisplayPageModelImpl.ENTITY_CACHE_ENABLED,
				AssetDisplayPageImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		AssetDisplayPage assetDisplayPage = (AssetDisplayPage)serializable;

		if (assetDisplayPage == null) {
			Session session = null;

			try {
				session = openSession();

				assetDisplayPage = (AssetDisplayPage)session.get(AssetDisplayPageImpl.class,
						primaryKey);

				if (assetDisplayPage != null) {
					cacheResult(assetDisplayPage);
				}
				else {
					entityCache.putResult(AssetDisplayPageModelImpl.ENTITY_CACHE_ENABLED,
						AssetDisplayPageImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(AssetDisplayPageModelImpl.ENTITY_CACHE_ENABLED,
					AssetDisplayPageImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return assetDisplayPage;
	}

	/**
	 * Returns the asset display page with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetDisplayPageId the primary key of the asset display page
	 * @return the asset display page, or <code>null</code> if a asset display page with the primary key could not be found
	 */
	@Override
	public AssetDisplayPage fetchByPrimaryKey(long assetDisplayPageId) {
		return fetchByPrimaryKey((Serializable)assetDisplayPageId);
	}

	@Override
	public Map<Serializable, AssetDisplayPage> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetDisplayPage> map = new HashMap<Serializable, AssetDisplayPage>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetDisplayPage assetDisplayPage = fetchByPrimaryKey(primaryKey);

			if (assetDisplayPage != null) {
				map.put(primaryKey, assetDisplayPage);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(AssetDisplayPageModelImpl.ENTITY_CACHE_ENABLED,
					AssetDisplayPageImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (AssetDisplayPage)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_ASSETDISPLAYPAGE_WHERE_PKS_IN);

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

			for (AssetDisplayPage assetDisplayPage : (List<AssetDisplayPage>)q.list()) {
				map.put(assetDisplayPage.getPrimaryKeyObj(), assetDisplayPage);

				cacheResult(assetDisplayPage);

				uncachedPrimaryKeys.remove(assetDisplayPage.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(AssetDisplayPageModelImpl.ENTITY_CACHE_ENABLED,
					AssetDisplayPageImpl.class, primaryKey, nullModel);
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
	 * Returns all the asset display pages.
	 *
	 * @return the asset display pages
	 */
	@Override
	public List<AssetDisplayPage> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset display pages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset display pages
	 * @param end the upper bound of the range of asset display pages (not inclusive)
	 * @return the range of asset display pages
	 */
	@Override
	public List<AssetDisplayPage> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset display pages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset display pages
	 * @param end the upper bound of the range of asset display pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset display pages
	 */
	@Override
	public List<AssetDisplayPage> findAll(int start, int end,
		OrderByComparator<AssetDisplayPage> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset display pages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset display pages
	 * @param end the upper bound of the range of asset display pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of asset display pages
	 */
	@Override
	public List<AssetDisplayPage> findAll(int start, int end,
		OrderByComparator<AssetDisplayPage> orderByComparator,
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

		List<AssetDisplayPage> list = null;

		if (retrieveFromCache) {
			list = (List<AssetDisplayPage>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ASSETDISPLAYPAGE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETDISPLAYPAGE;

				if (pagination) {
					sql = sql.concat(AssetDisplayPageModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AssetDisplayPage>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetDisplayPage>)QueryUtil.list(q,
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
	 * Removes all the asset display pages from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetDisplayPage assetDisplayPage : findAll()) {
			remove(assetDisplayPage);
		}
	}

	/**
	 * Returns the number of asset display pages.
	 *
	 * @return the number of asset display pages
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETDISPLAYPAGE);

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
	protected Map<String, Integer> getTableColumnsMap() {
		return AssetDisplayPageModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the asset display page persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(AssetDisplayPageImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_ASSETDISPLAYPAGE = "SELECT assetDisplayPage FROM AssetDisplayPage assetDisplayPage";
	private static final String _SQL_SELECT_ASSETDISPLAYPAGE_WHERE_PKS_IN = "SELECT assetDisplayPage FROM AssetDisplayPage assetDisplayPage WHERE assetDisplayPageId IN (";
	private static final String _SQL_SELECT_ASSETDISPLAYPAGE_WHERE = "SELECT assetDisplayPage FROM AssetDisplayPage assetDisplayPage WHERE ";
	private static final String _SQL_COUNT_ASSETDISPLAYPAGE = "SELECT COUNT(assetDisplayPage) FROM AssetDisplayPage assetDisplayPage";
	private static final String _SQL_COUNT_ASSETDISPLAYPAGE_WHERE = "SELECT COUNT(assetDisplayPage) FROM AssetDisplayPage assetDisplayPage WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetDisplayPage.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetDisplayPage exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetDisplayPage exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(AssetDisplayPagePersistenceImpl.class);
}