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

package com.liferay.asset.entry.rel.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.entry.rel.exception.NoSuchEntryAssetDisplayPageRelException;
import com.liferay.asset.entry.rel.model.AssetEntryAssetDisplayPageRel;
import com.liferay.asset.entry.rel.model.impl.AssetEntryAssetDisplayPageRelImpl;
import com.liferay.asset.entry.rel.model.impl.AssetEntryAssetDisplayPageRelModelImpl;
import com.liferay.asset.entry.rel.service.persistence.AssetEntryAssetDisplayPageRelPersistence;

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
 * The persistence implementation for the asset entry asset display page rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryAssetDisplayPageRelPersistence
 * @see com.liferay.asset.entry.rel.service.persistence.AssetEntryAssetDisplayPageRelUtil
 * @generated
 */
@ProviderType
public class AssetEntryAssetDisplayPageRelPersistenceImpl
	extends BasePersistenceImpl<AssetEntryAssetDisplayPageRel>
	implements AssetEntryAssetDisplayPageRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AssetEntryAssetDisplayPageRelUtil} to access the asset entry asset display page rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AssetEntryAssetDisplayPageRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AssetEntryAssetDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetDisplayPageRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetDisplayPageRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AssetEntryAssetDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetDisplayPageRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetDisplayPageRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetEntryAssetDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetDisplayPageRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ASSETENTRYID =
		new FinderPath(AssetEntryAssetDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetDisplayPageRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetDisplayPageRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAssetEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID =
		new FinderPath(AssetEntryAssetDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetDisplayPageRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetDisplayPageRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAssetEntryId",
			new String[] { Long.class.getName() },
			AssetEntryAssetDisplayPageRelModelImpl.ASSETENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ASSETENTRYID = new FinderPath(AssetEntryAssetDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetDisplayPageRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAssetEntryId", new String[] { Long.class.getName() });

	/**
	 * Returns all the asset entry asset display page rels where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the matching asset entry asset display page rels
	 */
	@Override
	public List<AssetEntryAssetDisplayPageRel> findByAssetEntryId(
		long assetEntryId) {
		return findByAssetEntryId(assetEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry asset display page rels where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset entry asset display page rels
	 * @param end the upper bound of the range of asset entry asset display page rels (not inclusive)
	 * @return the range of matching asset entry asset display page rels
	 */
	@Override
	public List<AssetEntryAssetDisplayPageRel> findByAssetEntryId(
		long assetEntryId, int start, int end) {
		return findByAssetEntryId(assetEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry asset display page rels where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset entry asset display page rels
	 * @param end the upper bound of the range of asset entry asset display page rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry asset display page rels
	 */
	@Override
	public List<AssetEntryAssetDisplayPageRel> findByAssetEntryId(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator) {
		return findByAssetEntryId(assetEntryId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the asset entry asset display page rels where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset entry asset display page rels
	 * @param end the upper bound of the range of asset entry asset display page rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset entry asset display page rels
	 */
	@Override
	public List<AssetEntryAssetDisplayPageRel> findByAssetEntryId(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator,
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

		List<AssetEntryAssetDisplayPageRel> list = null;

		if (retrieveFromCache) {
			list = (List<AssetEntryAssetDisplayPageRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel : list) {
					if ((assetEntryId != assetEntryAssetDisplayPageRel.getAssetEntryId())) {
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

			query.append(_SQL_SELECT_ASSETENTRYASSETDISPLAYPAGEREL_WHERE);

			query.append(_FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetEntryAssetDisplayPageRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				if (!pagination) {
					list = (List<AssetEntryAssetDisplayPageRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetEntryAssetDisplayPageRel>)QueryUtil.list(q,
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
	 * Returns the first asset entry asset display page rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset display page rel
	 * @throws NoSuchEntryAssetDisplayPageRelException if a matching asset entry asset display page rel could not be found
	 */
	@Override
	public AssetEntryAssetDisplayPageRel findByAssetEntryId_First(
		long assetEntryId,
		OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator)
		throws NoSuchEntryAssetDisplayPageRelException {
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel = fetchByAssetEntryId_First(assetEntryId,
				orderByComparator);

		if (assetEntryAssetDisplayPageRel != null) {
			return assetEntryAssetDisplayPageRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetEntryId=");
		msg.append(assetEntryId);

		msg.append("}");

		throw new NoSuchEntryAssetDisplayPageRelException(msg.toString());
	}

	/**
	 * Returns the first asset entry asset display page rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset display page rel, or <code>null</code> if a matching asset entry asset display page rel could not be found
	 */
	@Override
	public AssetEntryAssetDisplayPageRel fetchByAssetEntryId_First(
		long assetEntryId,
		OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator) {
		List<AssetEntryAssetDisplayPageRel> list = findByAssetEntryId(assetEntryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry asset display page rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset display page rel
	 * @throws NoSuchEntryAssetDisplayPageRelException if a matching asset entry asset display page rel could not be found
	 */
	@Override
	public AssetEntryAssetDisplayPageRel findByAssetEntryId_Last(
		long assetEntryId,
		OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator)
		throws NoSuchEntryAssetDisplayPageRelException {
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel = fetchByAssetEntryId_Last(assetEntryId,
				orderByComparator);

		if (assetEntryAssetDisplayPageRel != null) {
			return assetEntryAssetDisplayPageRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetEntryId=");
		msg.append(assetEntryId);

		msg.append("}");

		throw new NoSuchEntryAssetDisplayPageRelException(msg.toString());
	}

	/**
	 * Returns the last asset entry asset display page rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset display page rel, or <code>null</code> if a matching asset entry asset display page rel could not be found
	 */
	@Override
	public AssetEntryAssetDisplayPageRel fetchByAssetEntryId_Last(
		long assetEntryId,
		OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator) {
		int count = countByAssetEntryId(assetEntryId);

		if (count == 0) {
			return null;
		}

		List<AssetEntryAssetDisplayPageRel> list = findByAssetEntryId(assetEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry asset display page rels before and after the current asset entry asset display page rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryAssetDisplayPageId the primary key of the current asset entry asset display page rel
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry asset display page rel
	 * @throws NoSuchEntryAssetDisplayPageRelException if a asset entry asset display page rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetDisplayPageRel[] findByAssetEntryId_PrevAndNext(
		long assetEntryAssetDisplayPageId, long assetEntryId,
		OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator)
		throws NoSuchEntryAssetDisplayPageRelException {
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel = findByPrimaryKey(assetEntryAssetDisplayPageId);

		Session session = null;

		try {
			session = openSession();

			AssetEntryAssetDisplayPageRel[] array = new AssetEntryAssetDisplayPageRelImpl[3];

			array[0] = getByAssetEntryId_PrevAndNext(session,
					assetEntryAssetDisplayPageRel, assetEntryId,
					orderByComparator, true);

			array[1] = assetEntryAssetDisplayPageRel;

			array[2] = getByAssetEntryId_PrevAndNext(session,
					assetEntryAssetDisplayPageRel, assetEntryId,
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

	protected AssetEntryAssetDisplayPageRel getByAssetEntryId_PrevAndNext(
		Session session,
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel,
		long assetEntryId,
		OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETENTRYASSETDISPLAYPAGEREL_WHERE);

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
			query.append(AssetEntryAssetDisplayPageRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(assetEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetEntryAssetDisplayPageRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetEntryAssetDisplayPageRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry asset display page rels where assetEntryId = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 */
	@Override
	public void removeByAssetEntryId(long assetEntryId) {
		for (AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel : findByAssetEntryId(
				assetEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetEntryAssetDisplayPageRel);
		}
	}

	/**
	 * Returns the number of asset entry asset display page rels where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the number of matching asset entry asset display page rels
	 */
	@Override
	public int countByAssetEntryId(long assetEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ASSETENTRYID;

		Object[] finderArgs = new Object[] { assetEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETENTRYASSETDISPLAYPAGEREL_WHERE);

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

	private static final String _FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2 = "assetEntryAssetDisplayPageRel.assetEntryId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_A_A = new FinderPath(AssetEntryAssetDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetDisplayPageRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetDisplayPageRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByA_A",
			new String[] { Long.class.getName(), Long.class.getName() },
			AssetEntryAssetDisplayPageRelModelImpl.ASSETENTRYID_COLUMN_BITMASK |
			AssetEntryAssetDisplayPageRelModelImpl.ASSETDISPLAYPAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_A_A = new FinderPath(AssetEntryAssetDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetDisplayPageRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByA_A",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the asset entry asset display page rel where assetEntryId = &#63; and assetDisplayPageId = &#63; or throws a {@link NoSuchEntryAssetDisplayPageRelException} if it could not be found.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetDisplayPageId the asset display page ID
	 * @return the matching asset entry asset display page rel
	 * @throws NoSuchEntryAssetDisplayPageRelException if a matching asset entry asset display page rel could not be found
	 */
	@Override
	public AssetEntryAssetDisplayPageRel findByA_A(long assetEntryId,
		long assetDisplayPageId) throws NoSuchEntryAssetDisplayPageRelException {
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel = fetchByA_A(assetEntryId,
				assetDisplayPageId);

		if (assetEntryAssetDisplayPageRel == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetEntryId=");
			msg.append(assetEntryId);

			msg.append(", assetDisplayPageId=");
			msg.append(assetDisplayPageId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchEntryAssetDisplayPageRelException(msg.toString());
		}

		return assetEntryAssetDisplayPageRel;
	}

	/**
	 * Returns the asset entry asset display page rel where assetEntryId = &#63; and assetDisplayPageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetDisplayPageId the asset display page ID
	 * @return the matching asset entry asset display page rel, or <code>null</code> if a matching asset entry asset display page rel could not be found
	 */
	@Override
	public AssetEntryAssetDisplayPageRel fetchByA_A(long assetEntryId,
		long assetDisplayPageId) {
		return fetchByA_A(assetEntryId, assetDisplayPageId, true);
	}

	/**
	 * Returns the asset entry asset display page rel where assetEntryId = &#63; and assetDisplayPageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetDisplayPageId the asset display page ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching asset entry asset display page rel, or <code>null</code> if a matching asset entry asset display page rel could not be found
	 */
	@Override
	public AssetEntryAssetDisplayPageRel fetchByA_A(long assetEntryId,
		long assetDisplayPageId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { assetEntryId, assetDisplayPageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_A_A,
					finderArgs, this);
		}

		if (result instanceof AssetEntryAssetDisplayPageRel) {
			AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel = (AssetEntryAssetDisplayPageRel)result;

			if ((assetEntryId != assetEntryAssetDisplayPageRel.getAssetEntryId()) ||
					(assetDisplayPageId != assetEntryAssetDisplayPageRel.getAssetDisplayPageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ASSETENTRYASSETDISPLAYPAGEREL_WHERE);

			query.append(_FINDER_COLUMN_A_A_ASSETENTRYID_2);

			query.append(_FINDER_COLUMN_A_A_ASSETDISPLAYPAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				qPos.add(assetDisplayPageId);

				List<AssetEntryAssetDisplayPageRel> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_A_A, finderArgs,
						list);
				}
				else {
					AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel = list.get(0);

					result = assetEntryAssetDisplayPageRel;

					cacheResult(assetEntryAssetDisplayPageRel);

					if ((assetEntryAssetDisplayPageRel.getAssetEntryId() != assetEntryId) ||
							(assetEntryAssetDisplayPageRel.getAssetDisplayPageId() != assetDisplayPageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_A_A,
							finderArgs, assetEntryAssetDisplayPageRel);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_A_A, finderArgs);

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
			return (AssetEntryAssetDisplayPageRel)result;
		}
	}

	/**
	 * Removes the asset entry asset display page rel where assetEntryId = &#63; and assetDisplayPageId = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetDisplayPageId the asset display page ID
	 * @return the asset entry asset display page rel that was removed
	 */
	@Override
	public AssetEntryAssetDisplayPageRel removeByA_A(long assetEntryId,
		long assetDisplayPageId) throws NoSuchEntryAssetDisplayPageRelException {
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel = findByA_A(assetEntryId,
				assetDisplayPageId);

		return remove(assetEntryAssetDisplayPageRel);
	}

	/**
	 * Returns the number of asset entry asset display page rels where assetEntryId = &#63; and assetDisplayPageId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetDisplayPageId the asset display page ID
	 * @return the number of matching asset entry asset display page rels
	 */
	@Override
	public int countByA_A(long assetEntryId, long assetDisplayPageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_A_A;

		Object[] finderArgs = new Object[] { assetEntryId, assetDisplayPageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETENTRYASSETDISPLAYPAGEREL_WHERE);

			query.append(_FINDER_COLUMN_A_A_ASSETENTRYID_2);

			query.append(_FINDER_COLUMN_A_A_ASSETDISPLAYPAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				qPos.add(assetDisplayPageId);

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

	private static final String _FINDER_COLUMN_A_A_ASSETENTRYID_2 = "assetEntryAssetDisplayPageRel.assetEntryId = ? AND ";
	private static final String _FINDER_COLUMN_A_A_ASSETDISPLAYPAGEID_2 = "assetEntryAssetDisplayPageRel.assetDisplayPageId = ?";

	public AssetEntryAssetDisplayPageRelPersistenceImpl() {
		setModelClass(AssetEntryAssetDisplayPageRel.class);
	}

	/**
	 * Caches the asset entry asset display page rel in the entity cache if it is enabled.
	 *
	 * @param assetEntryAssetDisplayPageRel the asset entry asset display page rel
	 */
	@Override
	public void cacheResult(
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel) {
		entityCache.putResult(AssetEntryAssetDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetDisplayPageRelImpl.class,
			assetEntryAssetDisplayPageRel.getPrimaryKey(),
			assetEntryAssetDisplayPageRel);

		finderCache.putResult(FINDER_PATH_FETCH_BY_A_A,
			new Object[] {
				assetEntryAssetDisplayPageRel.getAssetEntryId(),
				assetEntryAssetDisplayPageRel.getAssetDisplayPageId()
			}, assetEntryAssetDisplayPageRel);

		assetEntryAssetDisplayPageRel.resetOriginalValues();
	}

	/**
	 * Caches the asset entry asset display page rels in the entity cache if it is enabled.
	 *
	 * @param assetEntryAssetDisplayPageRels the asset entry asset display page rels
	 */
	@Override
	public void cacheResult(
		List<AssetEntryAssetDisplayPageRel> assetEntryAssetDisplayPageRels) {
		for (AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel : assetEntryAssetDisplayPageRels) {
			if (entityCache.getResult(
						AssetEntryAssetDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
						AssetEntryAssetDisplayPageRelImpl.class,
						assetEntryAssetDisplayPageRel.getPrimaryKey()) == null) {
				cacheResult(assetEntryAssetDisplayPageRel);
			}
			else {
				assetEntryAssetDisplayPageRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset entry asset display page rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetEntryAssetDisplayPageRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset entry asset display page rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel) {
		entityCache.removeResult(AssetEntryAssetDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetDisplayPageRelImpl.class,
			assetEntryAssetDisplayPageRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((AssetEntryAssetDisplayPageRelModelImpl)assetEntryAssetDisplayPageRel,
			true);
	}

	@Override
	public void clearCache(
		List<AssetEntryAssetDisplayPageRel> assetEntryAssetDisplayPageRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel : assetEntryAssetDisplayPageRels) {
			entityCache.removeResult(AssetEntryAssetDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
				AssetEntryAssetDisplayPageRelImpl.class,
				assetEntryAssetDisplayPageRel.getPrimaryKey());

			clearUniqueFindersCache((AssetEntryAssetDisplayPageRelModelImpl)assetEntryAssetDisplayPageRel,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetEntryAssetDisplayPageRelModelImpl assetEntryAssetDisplayPageRelModelImpl) {
		Object[] args = new Object[] {
				assetEntryAssetDisplayPageRelModelImpl.getAssetEntryId(),
				assetEntryAssetDisplayPageRelModelImpl.getAssetDisplayPageId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_A_A, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_A_A, args,
			assetEntryAssetDisplayPageRelModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		AssetEntryAssetDisplayPageRelModelImpl assetEntryAssetDisplayPageRelModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					assetEntryAssetDisplayPageRelModelImpl.getAssetEntryId(),
					assetEntryAssetDisplayPageRelModelImpl.getAssetDisplayPageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_A_A, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_A_A, args);
		}

		if ((assetEntryAssetDisplayPageRelModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_A_A.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					assetEntryAssetDisplayPageRelModelImpl.getOriginalAssetEntryId(),
					assetEntryAssetDisplayPageRelModelImpl.getOriginalAssetDisplayPageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_A_A, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_A_A, args);
		}
	}

	/**
	 * Creates a new asset entry asset display page rel with the primary key. Does not add the asset entry asset display page rel to the database.
	 *
	 * @param assetEntryAssetDisplayPageId the primary key for the new asset entry asset display page rel
	 * @return the new asset entry asset display page rel
	 */
	@Override
	public AssetEntryAssetDisplayPageRel create(
		long assetEntryAssetDisplayPageId) {
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel = new AssetEntryAssetDisplayPageRelImpl();

		assetEntryAssetDisplayPageRel.setNew(true);
		assetEntryAssetDisplayPageRel.setPrimaryKey(assetEntryAssetDisplayPageId);

		return assetEntryAssetDisplayPageRel;
	}

	/**
	 * Removes the asset entry asset display page rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryAssetDisplayPageId the primary key of the asset entry asset display page rel
	 * @return the asset entry asset display page rel that was removed
	 * @throws NoSuchEntryAssetDisplayPageRelException if a asset entry asset display page rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetDisplayPageRel remove(
		long assetEntryAssetDisplayPageId)
		throws NoSuchEntryAssetDisplayPageRelException {
		return remove((Serializable)assetEntryAssetDisplayPageId);
	}

	/**
	 * Removes the asset entry asset display page rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset entry asset display page rel
	 * @return the asset entry asset display page rel that was removed
	 * @throws NoSuchEntryAssetDisplayPageRelException if a asset entry asset display page rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetDisplayPageRel remove(Serializable primaryKey)
		throws NoSuchEntryAssetDisplayPageRelException {
		Session session = null;

		try {
			session = openSession();

			AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel = (AssetEntryAssetDisplayPageRel)session.get(AssetEntryAssetDisplayPageRelImpl.class,
					primaryKey);

			if (assetEntryAssetDisplayPageRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryAssetDisplayPageRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(assetEntryAssetDisplayPageRel);
		}
		catch (NoSuchEntryAssetDisplayPageRelException nsee) {
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
	protected AssetEntryAssetDisplayPageRel removeImpl(
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel) {
		assetEntryAssetDisplayPageRel = toUnwrappedModel(assetEntryAssetDisplayPageRel);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetEntryAssetDisplayPageRel)) {
				assetEntryAssetDisplayPageRel = (AssetEntryAssetDisplayPageRel)session.get(AssetEntryAssetDisplayPageRelImpl.class,
						assetEntryAssetDisplayPageRel.getPrimaryKeyObj());
			}

			if (assetEntryAssetDisplayPageRel != null) {
				session.delete(assetEntryAssetDisplayPageRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (assetEntryAssetDisplayPageRel != null) {
			clearCache(assetEntryAssetDisplayPageRel);
		}

		return assetEntryAssetDisplayPageRel;
	}

	@Override
	public AssetEntryAssetDisplayPageRel updateImpl(
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel) {
		assetEntryAssetDisplayPageRel = toUnwrappedModel(assetEntryAssetDisplayPageRel);

		boolean isNew = assetEntryAssetDisplayPageRel.isNew();

		AssetEntryAssetDisplayPageRelModelImpl assetEntryAssetDisplayPageRelModelImpl =
			(AssetEntryAssetDisplayPageRelModelImpl)assetEntryAssetDisplayPageRel;

		Session session = null;

		try {
			session = openSession();

			if (assetEntryAssetDisplayPageRel.isNew()) {
				session.save(assetEntryAssetDisplayPageRel);

				assetEntryAssetDisplayPageRel.setNew(false);
			}
			else {
				assetEntryAssetDisplayPageRel = (AssetEntryAssetDisplayPageRel)session.merge(assetEntryAssetDisplayPageRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AssetEntryAssetDisplayPageRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					assetEntryAssetDisplayPageRelModelImpl.getAssetEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((assetEntryAssetDisplayPageRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetEntryAssetDisplayPageRelModelImpl.getOriginalAssetEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID,
					args);

				args = new Object[] {
						assetEntryAssetDisplayPageRelModelImpl.getAssetEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID,
					args);
			}
		}

		entityCache.putResult(AssetEntryAssetDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetDisplayPageRelImpl.class,
			assetEntryAssetDisplayPageRel.getPrimaryKey(),
			assetEntryAssetDisplayPageRel, false);

		clearUniqueFindersCache(assetEntryAssetDisplayPageRelModelImpl, false);
		cacheUniqueFindersCache(assetEntryAssetDisplayPageRelModelImpl);

		assetEntryAssetDisplayPageRel.resetOriginalValues();

		return assetEntryAssetDisplayPageRel;
	}

	protected AssetEntryAssetDisplayPageRel toUnwrappedModel(
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel) {
		if (assetEntryAssetDisplayPageRel instanceof AssetEntryAssetDisplayPageRelImpl) {
			return assetEntryAssetDisplayPageRel;
		}

		AssetEntryAssetDisplayPageRelImpl assetEntryAssetDisplayPageRelImpl = new AssetEntryAssetDisplayPageRelImpl();

		assetEntryAssetDisplayPageRelImpl.setNew(assetEntryAssetDisplayPageRel.isNew());
		assetEntryAssetDisplayPageRelImpl.setPrimaryKey(assetEntryAssetDisplayPageRel.getPrimaryKey());

		assetEntryAssetDisplayPageRelImpl.setAssetEntryAssetDisplayPageId(assetEntryAssetDisplayPageRel.getAssetEntryAssetDisplayPageId());
		assetEntryAssetDisplayPageRelImpl.setAssetEntryId(assetEntryAssetDisplayPageRel.getAssetEntryId());
		assetEntryAssetDisplayPageRelImpl.setAssetDisplayPageId(assetEntryAssetDisplayPageRel.getAssetDisplayPageId());

		return assetEntryAssetDisplayPageRelImpl;
	}

	/**
	 * Returns the asset entry asset display page rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset entry asset display page rel
	 * @return the asset entry asset display page rel
	 * @throws NoSuchEntryAssetDisplayPageRelException if a asset entry asset display page rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetDisplayPageRel findByPrimaryKey(
		Serializable primaryKey) throws NoSuchEntryAssetDisplayPageRelException {
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel = fetchByPrimaryKey(primaryKey);

		if (assetEntryAssetDisplayPageRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryAssetDisplayPageRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return assetEntryAssetDisplayPageRel;
	}

	/**
	 * Returns the asset entry asset display page rel with the primary key or throws a {@link NoSuchEntryAssetDisplayPageRelException} if it could not be found.
	 *
	 * @param assetEntryAssetDisplayPageId the primary key of the asset entry asset display page rel
	 * @return the asset entry asset display page rel
	 * @throws NoSuchEntryAssetDisplayPageRelException if a asset entry asset display page rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetDisplayPageRel findByPrimaryKey(
		long assetEntryAssetDisplayPageId)
		throws NoSuchEntryAssetDisplayPageRelException {
		return findByPrimaryKey((Serializable)assetEntryAssetDisplayPageId);
	}

	/**
	 * Returns the asset entry asset display page rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset entry asset display page rel
	 * @return the asset entry asset display page rel, or <code>null</code> if a asset entry asset display page rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetDisplayPageRel fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(AssetEntryAssetDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
				AssetEntryAssetDisplayPageRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel = (AssetEntryAssetDisplayPageRel)serializable;

		if (assetEntryAssetDisplayPageRel == null) {
			Session session = null;

			try {
				session = openSession();

				assetEntryAssetDisplayPageRel = (AssetEntryAssetDisplayPageRel)session.get(AssetEntryAssetDisplayPageRelImpl.class,
						primaryKey);

				if (assetEntryAssetDisplayPageRel != null) {
					cacheResult(assetEntryAssetDisplayPageRel);
				}
				else {
					entityCache.putResult(AssetEntryAssetDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
						AssetEntryAssetDisplayPageRelImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(AssetEntryAssetDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
					AssetEntryAssetDisplayPageRelImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return assetEntryAssetDisplayPageRel;
	}

	/**
	 * Returns the asset entry asset display page rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetEntryAssetDisplayPageId the primary key of the asset entry asset display page rel
	 * @return the asset entry asset display page rel, or <code>null</code> if a asset entry asset display page rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetDisplayPageRel fetchByPrimaryKey(
		long assetEntryAssetDisplayPageId) {
		return fetchByPrimaryKey((Serializable)assetEntryAssetDisplayPageId);
	}

	@Override
	public Map<Serializable, AssetEntryAssetDisplayPageRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetEntryAssetDisplayPageRel> map = new HashMap<Serializable, AssetEntryAssetDisplayPageRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel = fetchByPrimaryKey(primaryKey);

			if (assetEntryAssetDisplayPageRel != null) {
				map.put(primaryKey, assetEntryAssetDisplayPageRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(AssetEntryAssetDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
					AssetEntryAssetDisplayPageRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(AssetEntryAssetDisplayPageRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_ASSETENTRYASSETDISPLAYPAGEREL_WHERE_PKS_IN);

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

			for (AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel : (List<AssetEntryAssetDisplayPageRel>)q.list()) {
				map.put(assetEntryAssetDisplayPageRel.getPrimaryKeyObj(),
					assetEntryAssetDisplayPageRel);

				cacheResult(assetEntryAssetDisplayPageRel);

				uncachedPrimaryKeys.remove(assetEntryAssetDisplayPageRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(AssetEntryAssetDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
					AssetEntryAssetDisplayPageRelImpl.class, primaryKey,
					nullModel);
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
	 * Returns all the asset entry asset display page rels.
	 *
	 * @return the asset entry asset display page rels
	 */
	@Override
	public List<AssetEntryAssetDisplayPageRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry asset display page rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry asset display page rels
	 * @param end the upper bound of the range of asset entry asset display page rels (not inclusive)
	 * @return the range of asset entry asset display page rels
	 */
	@Override
	public List<AssetEntryAssetDisplayPageRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry asset display page rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry asset display page rels
	 * @param end the upper bound of the range of asset entry asset display page rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset entry asset display page rels
	 */
	@Override
	public List<AssetEntryAssetDisplayPageRel> findAll(int start, int end,
		OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry asset display page rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry asset display page rels
	 * @param end the upper bound of the range of asset entry asset display page rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of asset entry asset display page rels
	 */
	@Override
	public List<AssetEntryAssetDisplayPageRel> findAll(int start, int end,
		OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator,
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

		List<AssetEntryAssetDisplayPageRel> list = null;

		if (retrieveFromCache) {
			list = (List<AssetEntryAssetDisplayPageRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ASSETENTRYASSETDISPLAYPAGEREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETENTRYASSETDISPLAYPAGEREL;

				if (pagination) {
					sql = sql.concat(AssetEntryAssetDisplayPageRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AssetEntryAssetDisplayPageRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetEntryAssetDisplayPageRel>)QueryUtil.list(q,
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
	 * Removes all the asset entry asset display page rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel : findAll()) {
			remove(assetEntryAssetDisplayPageRel);
		}
	}

	/**
	 * Returns the number of asset entry asset display page rels.
	 *
	 * @return the number of asset entry asset display page rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETENTRYASSETDISPLAYPAGEREL);

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
		return AssetEntryAssetDisplayPageRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the asset entry asset display page rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(AssetEntryAssetDisplayPageRelImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_ASSETENTRYASSETDISPLAYPAGEREL = "SELECT assetEntryAssetDisplayPageRel FROM AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel";
	private static final String _SQL_SELECT_ASSETENTRYASSETDISPLAYPAGEREL_WHERE_PKS_IN =
		"SELECT assetEntryAssetDisplayPageRel FROM AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel WHERE assetEntryAssetDisplayPageId IN (";
	private static final String _SQL_SELECT_ASSETENTRYASSETDISPLAYPAGEREL_WHERE = "SELECT assetEntryAssetDisplayPageRel FROM AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel WHERE ";
	private static final String _SQL_COUNT_ASSETENTRYASSETDISPLAYPAGEREL = "SELECT COUNT(assetEntryAssetDisplayPageRel) FROM AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel";
	private static final String _SQL_COUNT_ASSETENTRYASSETDISPLAYPAGEREL_WHERE = "SELECT COUNT(assetEntryAssetDisplayPageRel) FROM AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetEntryAssetDisplayPageRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetEntryAssetDisplayPageRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetEntryAssetDisplayPageRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(AssetEntryAssetDisplayPageRelPersistenceImpl.class);
}