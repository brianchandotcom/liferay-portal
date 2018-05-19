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

import com.liferay.asset.display.page.exception.NoSuchDisplayPageEntryException;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.model.impl.AssetDisplayPageEntryImpl;
import com.liferay.asset.display.page.model.impl.AssetDisplayPageEntryModelImpl;
import com.liferay.asset.display.page.service.persistence.AssetDisplayPageEntryPersistence;

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
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the asset display page entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayPageEntryPersistence
 * @see com.liferay.asset.display.page.service.persistence.AssetDisplayPageEntryUtil
 * @generated
 */
@ProviderType
public class AssetDisplayPageEntryPersistenceImpl extends BasePersistenceImpl<AssetDisplayPageEntry>
	implements AssetDisplayPageEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AssetDisplayPageEntryUtil} to access the asset display page entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AssetDisplayPageEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AssetDisplayPageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageEntryModelImpl.FINDER_CACHE_ENABLED,
			AssetDisplayPageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AssetDisplayPageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageEntryModelImpl.FINDER_CACHE_ENABLED,
			AssetDisplayPageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetDisplayPageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_ASSETENTRYID = new FinderPath(AssetDisplayPageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageEntryModelImpl.FINDER_CACHE_ENABLED,
			AssetDisplayPageEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByAssetEntryId", new String[] { Long.class.getName() },
			AssetDisplayPageEntryModelImpl.ASSETENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ASSETENTRYID = new FinderPath(AssetDisplayPageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAssetEntryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns the asset display page entry where assetEntryId = &#63; or throws a {@link NoSuchDisplayPageEntryException} if it could not be found.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the matching asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByAssetEntryId(long assetEntryId)
		throws NoSuchDisplayPageEntryException {
		AssetDisplayPageEntry assetDisplayPageEntry = fetchByAssetEntryId(assetEntryId);

		if (assetDisplayPageEntry == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetEntryId=");
			msg.append(assetEntryId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchDisplayPageEntryException(msg.toString());
		}

		return assetDisplayPageEntry;
	}

	/**
	 * Returns the asset display page entry where assetEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByAssetEntryId(long assetEntryId) {
		return fetchByAssetEntryId(assetEntryId, true);
	}

	/**
	 * Returns the asset display page entry where assetEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByAssetEntryId(long assetEntryId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { assetEntryId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
					finderArgs, this);
		}

		if (result instanceof AssetDisplayPageEntry) {
			AssetDisplayPageEntry assetDisplayPageEntry = (AssetDisplayPageEntry)result;

			if ((assetEntryId != assetDisplayPageEntry.getAssetEntryId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				List<AssetDisplayPageEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
						finderArgs, list);
				}
				else {
					AssetDisplayPageEntry assetDisplayPageEntry = list.get(0);

					result = assetDisplayPageEntry;

					cacheResult(assetDisplayPageEntry);

					if ((assetDisplayPageEntry.getAssetEntryId() != assetEntryId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
							finderArgs, assetDisplayPageEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
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
			return (AssetDisplayPageEntry)result;
		}
	}

	/**
	 * Removes the asset display page entry where assetEntryId = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the asset display page entry that was removed
	 */
	@Override
	public AssetDisplayPageEntry removeByAssetEntryId(long assetEntryId)
		throws NoSuchDisplayPageEntryException {
		AssetDisplayPageEntry assetDisplayPageEntry = findByAssetEntryId(assetEntryId);

		return remove(assetDisplayPageEntry);
	}

	/**
	 * Returns the number of asset display page entries where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the number of matching asset display page entries
	 */
	@Override
	public int countByAssetEntryId(long assetEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ASSETENTRYID;

		Object[] finderArgs = new Object[] { assetEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETDISPLAYPAGEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2 = "assetDisplayPageEntry.assetEntryId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTPAGETEMPLATEENTRYID =
		new FinderPath(AssetDisplayPageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageEntryModelImpl.FINDER_CACHE_ENABLED,
			AssetDisplayPageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByLayoutPageTemplateEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTPAGETEMPLATEENTRYID =
		new FinderPath(AssetDisplayPageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageEntryModelImpl.FINDER_CACHE_ENABLED,
			AssetDisplayPageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByLayoutPageTemplateEntryId",
			new String[] { Long.class.getName() },
			AssetDisplayPageEntryModelImpl.LAYOUTPAGETEMPLATEENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LAYOUTPAGETEMPLATEENTRYID =
		new FinderPath(AssetDisplayPageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLayoutPageTemplateEntryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the asset display page entries where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @return the matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {
		return findByLayoutPageTemplateEntryId(layoutPageTemplateEntryId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset display page entries where layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @return the range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId, int start, int end) {
		return findByLayoutPageTemplateEntryId(layoutPageTemplateEntryId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset display page entries where layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {
		return findByLayoutPageTemplateEntryId(layoutPageTemplateEntryId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset display page entries where layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTPAGETEMPLATEENTRYID;
			finderArgs = new Object[] { layoutPageTemplateEntryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTPAGETEMPLATEENTRYID;
			finderArgs = new Object[] {
					layoutPageTemplateEntryId,
					
					start, end, orderByComparator
				};
		}

		List<AssetDisplayPageEntry> list = null;

		if (retrieveFromCache) {
			list = (List<AssetDisplayPageEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetDisplayPageEntry assetDisplayPageEntry : list) {
					if ((layoutPageTemplateEntryId != assetDisplayPageEntry.getLayoutPageTemplateEntryId())) {
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

			query.append(_SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTPAGETEMPLATEENTRYID_LAYOUTPAGETEMPLATEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetDisplayPageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutPageTemplateEntryId);

				if (!pagination) {
					list = (List<AssetDisplayPageEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetDisplayPageEntry>)QueryUtil.list(q,
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
	 * Returns the first asset display page entry in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByLayoutPageTemplateEntryId_First(
		long layoutPageTemplateEntryId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException {
		AssetDisplayPageEntry assetDisplayPageEntry = fetchByLayoutPageTemplateEntryId_First(layoutPageTemplateEntryId,
				orderByComparator);

		if (assetDisplayPageEntry != null) {
			return assetDisplayPageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutPageTemplateEntryId=");
		msg.append(layoutPageTemplateEntryId);

		msg.append("}");

		throw new NoSuchDisplayPageEntryException(msg.toString());
	}

	/**
	 * Returns the first asset display page entry in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByLayoutPageTemplateEntryId_First(
		long layoutPageTemplateEntryId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {
		List<AssetDisplayPageEntry> list = findByLayoutPageTemplateEntryId(layoutPageTemplateEntryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset display page entry in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByLayoutPageTemplateEntryId_Last(
		long layoutPageTemplateEntryId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException {
		AssetDisplayPageEntry assetDisplayPageEntry = fetchByLayoutPageTemplateEntryId_Last(layoutPageTemplateEntryId,
				orderByComparator);

		if (assetDisplayPageEntry != null) {
			return assetDisplayPageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutPageTemplateEntryId=");
		msg.append(layoutPageTemplateEntryId);

		msg.append("}");

		throw new NoSuchDisplayPageEntryException(msg.toString());
	}

	/**
	 * Returns the last asset display page entry in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByLayoutPageTemplateEntryId_Last(
		long layoutPageTemplateEntryId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {
		int count = countByLayoutPageTemplateEntryId(layoutPageTemplateEntryId);

		if (count == 0) {
			return null;
		}

		List<AssetDisplayPageEntry> list = findByLayoutPageTemplateEntryId(layoutPageTemplateEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset display page entries before and after the current asset display page entry in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param assetDisplayPageEntryId the primary key of the current asset display page entry
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a asset display page entry with the primary key could not be found
	 */
	@Override
	public AssetDisplayPageEntry[] findByLayoutPageTemplateEntryId_PrevAndNext(
		long assetDisplayPageEntryId, long layoutPageTemplateEntryId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException {
		AssetDisplayPageEntry assetDisplayPageEntry = findByPrimaryKey(assetDisplayPageEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetDisplayPageEntry[] array = new AssetDisplayPageEntryImpl[3];

			array[0] = getByLayoutPageTemplateEntryId_PrevAndNext(session,
					assetDisplayPageEntry, layoutPageTemplateEntryId,
					orderByComparator, true);

			array[1] = assetDisplayPageEntry;

			array[2] = getByLayoutPageTemplateEntryId_PrevAndNext(session,
					assetDisplayPageEntry, layoutPageTemplateEntryId,
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

	protected AssetDisplayPageEntry getByLayoutPageTemplateEntryId_PrevAndNext(
		Session session, AssetDisplayPageEntry assetDisplayPageEntry,
		long layoutPageTemplateEntryId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE);

		query.append(_FINDER_COLUMN_LAYOUTPAGETEMPLATEENTRYID_LAYOUTPAGETEMPLATEENTRYID_2);

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
			query.append(AssetDisplayPageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutPageTemplateEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetDisplayPageEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetDisplayPageEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset display page entries where layoutPageTemplateEntryId = &#63; from the database.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 */
	@Override
	public void removeByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {
		for (AssetDisplayPageEntry assetDisplayPageEntry : findByLayoutPageTemplateEntryId(
				layoutPageTemplateEntryId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(assetDisplayPageEntry);
		}
	}

	/**
	 * Returns the number of asset display page entries where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @return the number of matching asset display page entries
	 */
	@Override
	public int countByLayoutPageTemplateEntryId(long layoutPageTemplateEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_LAYOUTPAGETEMPLATEENTRYID;

		Object[] finderArgs = new Object[] { layoutPageTemplateEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETDISPLAYPAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTPAGETEMPLATEENTRYID_LAYOUTPAGETEMPLATEENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutPageTemplateEntryId);

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

	private static final String _FINDER_COLUMN_LAYOUTPAGETEMPLATEENTRYID_LAYOUTPAGETEMPLATEENTRYID_2 =
		"assetDisplayPageEntry.layoutPageTemplateEntryId = ?";

	public AssetDisplayPageEntryPersistenceImpl() {
		setModelClass(AssetDisplayPageEntry.class);
	}

	/**
	 * Caches the asset display page entry in the entity cache if it is enabled.
	 *
	 * @param assetDisplayPageEntry the asset display page entry
	 */
	@Override
	public void cacheResult(AssetDisplayPageEntry assetDisplayPageEntry) {
		entityCache.putResult(AssetDisplayPageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageEntryImpl.class,
			assetDisplayPageEntry.getPrimaryKey(), assetDisplayPageEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
			new Object[] { assetDisplayPageEntry.getAssetEntryId() },
			assetDisplayPageEntry);

		assetDisplayPageEntry.resetOriginalValues();
	}

	/**
	 * Caches the asset display page entries in the entity cache if it is enabled.
	 *
	 * @param assetDisplayPageEntries the asset display page entries
	 */
	@Override
	public void cacheResult(List<AssetDisplayPageEntry> assetDisplayPageEntries) {
		for (AssetDisplayPageEntry assetDisplayPageEntry : assetDisplayPageEntries) {
			if (entityCache.getResult(
						AssetDisplayPageEntryModelImpl.ENTITY_CACHE_ENABLED,
						AssetDisplayPageEntryImpl.class,
						assetDisplayPageEntry.getPrimaryKey()) == null) {
				cacheResult(assetDisplayPageEntry);
			}
			else {
				assetDisplayPageEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset display page entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetDisplayPageEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset display page entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetDisplayPageEntry assetDisplayPageEntry) {
		entityCache.removeResult(AssetDisplayPageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageEntryImpl.class,
			assetDisplayPageEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((AssetDisplayPageEntryModelImpl)assetDisplayPageEntry,
			true);
	}

	@Override
	public void clearCache(List<AssetDisplayPageEntry> assetDisplayPageEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetDisplayPageEntry assetDisplayPageEntry : assetDisplayPageEntries) {
			entityCache.removeResult(AssetDisplayPageEntryModelImpl.ENTITY_CACHE_ENABLED,
				AssetDisplayPageEntryImpl.class,
				assetDisplayPageEntry.getPrimaryKey());

			clearUniqueFindersCache((AssetDisplayPageEntryModelImpl)assetDisplayPageEntry,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetDisplayPageEntryModelImpl assetDisplayPageEntryModelImpl) {
		Object[] args = new Object[] {
				assetDisplayPageEntryModelImpl.getAssetEntryId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_ASSETENTRYID, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_ASSETENTRYID, args,
			assetDisplayPageEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		AssetDisplayPageEntryModelImpl assetDisplayPageEntryModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					assetDisplayPageEntryModelImpl.getAssetEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_ASSETENTRYID, args);
		}

		if ((assetDisplayPageEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_ASSETENTRYID.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					assetDisplayPageEntryModelImpl.getOriginalAssetEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_ASSETENTRYID, args);
		}
	}

	/**
	 * Creates a new asset display page entry with the primary key. Does not add the asset display page entry to the database.
	 *
	 * @param assetDisplayPageEntryId the primary key for the new asset display page entry
	 * @return the new asset display page entry
	 */
	@Override
	public AssetDisplayPageEntry create(long assetDisplayPageEntryId) {
		AssetDisplayPageEntry assetDisplayPageEntry = new AssetDisplayPageEntryImpl();

		assetDisplayPageEntry.setNew(true);
		assetDisplayPageEntry.setPrimaryKey(assetDisplayPageEntryId);

		return assetDisplayPageEntry;
	}

	/**
	 * Removes the asset display page entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetDisplayPageEntryId the primary key of the asset display page entry
	 * @return the asset display page entry that was removed
	 * @throws NoSuchDisplayPageEntryException if a asset display page entry with the primary key could not be found
	 */
	@Override
	public AssetDisplayPageEntry remove(long assetDisplayPageEntryId)
		throws NoSuchDisplayPageEntryException {
		return remove((Serializable)assetDisplayPageEntryId);
	}

	/**
	 * Removes the asset display page entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset display page entry
	 * @return the asset display page entry that was removed
	 * @throws NoSuchDisplayPageEntryException if a asset display page entry with the primary key could not be found
	 */
	@Override
	public AssetDisplayPageEntry remove(Serializable primaryKey)
		throws NoSuchDisplayPageEntryException {
		Session session = null;

		try {
			session = openSession();

			AssetDisplayPageEntry assetDisplayPageEntry = (AssetDisplayPageEntry)session.get(AssetDisplayPageEntryImpl.class,
					primaryKey);

			if (assetDisplayPageEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDisplayPageEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(assetDisplayPageEntry);
		}
		catch (NoSuchDisplayPageEntryException nsee) {
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
	protected AssetDisplayPageEntry removeImpl(
		AssetDisplayPageEntry assetDisplayPageEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetDisplayPageEntry)) {
				assetDisplayPageEntry = (AssetDisplayPageEntry)session.get(AssetDisplayPageEntryImpl.class,
						assetDisplayPageEntry.getPrimaryKeyObj());
			}

			if (assetDisplayPageEntry != null) {
				session.delete(assetDisplayPageEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (assetDisplayPageEntry != null) {
			clearCache(assetDisplayPageEntry);
		}

		return assetDisplayPageEntry;
	}

	@Override
	public AssetDisplayPageEntry updateImpl(
		AssetDisplayPageEntry assetDisplayPageEntry) {
		boolean isNew = assetDisplayPageEntry.isNew();

		if (!(assetDisplayPageEntry instanceof AssetDisplayPageEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(assetDisplayPageEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(assetDisplayPageEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetDisplayPageEntry proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetDisplayPageEntry implementation " +
				assetDisplayPageEntry.getClass());
		}

		AssetDisplayPageEntryModelImpl assetDisplayPageEntryModelImpl = (AssetDisplayPageEntryModelImpl)assetDisplayPageEntry;

		Session session = null;

		try {
			session = openSession();

			if (assetDisplayPageEntry.isNew()) {
				session.save(assetDisplayPageEntry);

				assetDisplayPageEntry.setNew(false);
			}
			else {
				assetDisplayPageEntry = (AssetDisplayPageEntry)session.merge(assetDisplayPageEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AssetDisplayPageEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					assetDisplayPageEntryModelImpl.getLayoutPageTemplateEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_LAYOUTPAGETEMPLATEENTRYID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTPAGETEMPLATEENTRYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((assetDisplayPageEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTPAGETEMPLATEENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetDisplayPageEntryModelImpl.getOriginalLayoutPageTemplateEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_LAYOUTPAGETEMPLATEENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTPAGETEMPLATEENTRYID,
					args);

				args = new Object[] {
						assetDisplayPageEntryModelImpl.getLayoutPageTemplateEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_LAYOUTPAGETEMPLATEENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTPAGETEMPLATEENTRYID,
					args);
			}
		}

		entityCache.putResult(AssetDisplayPageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetDisplayPageEntryImpl.class,
			assetDisplayPageEntry.getPrimaryKey(), assetDisplayPageEntry, false);

		clearUniqueFindersCache(assetDisplayPageEntryModelImpl, false);
		cacheUniqueFindersCache(assetDisplayPageEntryModelImpl);

		assetDisplayPageEntry.resetOriginalValues();

		return assetDisplayPageEntry;
	}

	/**
	 * Returns the asset display page entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset display page entry
	 * @return the asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a asset display page entry with the primary key could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDisplayPageEntryException {
		AssetDisplayPageEntry assetDisplayPageEntry = fetchByPrimaryKey(primaryKey);

		if (assetDisplayPageEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDisplayPageEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return assetDisplayPageEntry;
	}

	/**
	 * Returns the asset display page entry with the primary key or throws a {@link NoSuchDisplayPageEntryException} if it could not be found.
	 *
	 * @param assetDisplayPageEntryId the primary key of the asset display page entry
	 * @return the asset display page entry
	 * @throws NoSuchDisplayPageEntryException if a asset display page entry with the primary key could not be found
	 */
	@Override
	public AssetDisplayPageEntry findByPrimaryKey(long assetDisplayPageEntryId)
		throws NoSuchDisplayPageEntryException {
		return findByPrimaryKey((Serializable)assetDisplayPageEntryId);
	}

	/**
	 * Returns the asset display page entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset display page entry
	 * @return the asset display page entry, or <code>null</code> if a asset display page entry with the primary key could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(AssetDisplayPageEntryModelImpl.ENTITY_CACHE_ENABLED,
				AssetDisplayPageEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		AssetDisplayPageEntry assetDisplayPageEntry = (AssetDisplayPageEntry)serializable;

		if (assetDisplayPageEntry == null) {
			Session session = null;

			try {
				session = openSession();

				assetDisplayPageEntry = (AssetDisplayPageEntry)session.get(AssetDisplayPageEntryImpl.class,
						primaryKey);

				if (assetDisplayPageEntry != null) {
					cacheResult(assetDisplayPageEntry);
				}
				else {
					entityCache.putResult(AssetDisplayPageEntryModelImpl.ENTITY_CACHE_ENABLED,
						AssetDisplayPageEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(AssetDisplayPageEntryModelImpl.ENTITY_CACHE_ENABLED,
					AssetDisplayPageEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return assetDisplayPageEntry;
	}

	/**
	 * Returns the asset display page entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetDisplayPageEntryId the primary key of the asset display page entry
	 * @return the asset display page entry, or <code>null</code> if a asset display page entry with the primary key could not be found
	 */
	@Override
	public AssetDisplayPageEntry fetchByPrimaryKey(long assetDisplayPageEntryId) {
		return fetchByPrimaryKey((Serializable)assetDisplayPageEntryId);
	}

	@Override
	public Map<Serializable, AssetDisplayPageEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetDisplayPageEntry> map = new HashMap<Serializable, AssetDisplayPageEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetDisplayPageEntry assetDisplayPageEntry = fetchByPrimaryKey(primaryKey);

			if (assetDisplayPageEntry != null) {
				map.put(primaryKey, assetDisplayPageEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(AssetDisplayPageEntryModelImpl.ENTITY_CACHE_ENABLED,
					AssetDisplayPageEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (AssetDisplayPageEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE_PKS_IN);

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

			for (AssetDisplayPageEntry assetDisplayPageEntry : (List<AssetDisplayPageEntry>)q.list()) {
				map.put(assetDisplayPageEntry.getPrimaryKeyObj(),
					assetDisplayPageEntry);

				cacheResult(assetDisplayPageEntry);

				uncachedPrimaryKeys.remove(assetDisplayPageEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(AssetDisplayPageEntryModelImpl.ENTITY_CACHE_ENABLED,
					AssetDisplayPageEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the asset display page entries.
	 *
	 * @return the asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset display page entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @return the range of asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset display page entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findAll(int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset display page entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of asset display page entries
	 */
	@Override
	public List<AssetDisplayPageEntry> findAll(int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator,
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

		List<AssetDisplayPageEntry> list = null;

		if (retrieveFromCache) {
			list = (List<AssetDisplayPageEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ASSETDISPLAYPAGEENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETDISPLAYPAGEENTRY;

				if (pagination) {
					sql = sql.concat(AssetDisplayPageEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AssetDisplayPageEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetDisplayPageEntry>)QueryUtil.list(q,
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
	 * Removes all the asset display page entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetDisplayPageEntry assetDisplayPageEntry : findAll()) {
			remove(assetDisplayPageEntry);
		}
	}

	/**
	 * Returns the number of asset display page entries.
	 *
	 * @return the number of asset display page entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETDISPLAYPAGEENTRY);

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
		return AssetDisplayPageEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the asset display page entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(AssetDisplayPageEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_ASSETDISPLAYPAGEENTRY = "SELECT assetDisplayPageEntry FROM AssetDisplayPageEntry assetDisplayPageEntry";
	private static final String _SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE_PKS_IN = "SELECT assetDisplayPageEntry FROM AssetDisplayPageEntry assetDisplayPageEntry WHERE assetDisplayPageEntryId IN (";
	private static final String _SQL_SELECT_ASSETDISPLAYPAGEENTRY_WHERE = "SELECT assetDisplayPageEntry FROM AssetDisplayPageEntry assetDisplayPageEntry WHERE ";
	private static final String _SQL_COUNT_ASSETDISPLAYPAGEENTRY = "SELECT COUNT(assetDisplayPageEntry) FROM AssetDisplayPageEntry assetDisplayPageEntry";
	private static final String _SQL_COUNT_ASSETDISPLAYPAGEENTRY_WHERE = "SELECT COUNT(assetDisplayPageEntry) FROM AssetDisplayPageEntry assetDisplayPageEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetDisplayPageEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetDisplayPageEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetDisplayPageEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(AssetDisplayPageEntryPersistenceImpl.class);
}