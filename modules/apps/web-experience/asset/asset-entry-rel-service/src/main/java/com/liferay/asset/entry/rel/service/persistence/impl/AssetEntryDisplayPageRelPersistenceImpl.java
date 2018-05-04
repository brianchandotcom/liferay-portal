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

import com.liferay.asset.entry.rel.exception.NoSuchEntryDisplayPageRelException;
import com.liferay.asset.entry.rel.model.AssetEntryDisplayPageRel;
import com.liferay.asset.entry.rel.model.impl.AssetEntryDisplayPageRelImpl;
import com.liferay.asset.entry.rel.model.impl.AssetEntryDisplayPageRelModelImpl;
import com.liferay.asset.entry.rel.service.persistence.AssetEntryDisplayPageRelPersistence;

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
 * The persistence implementation for the asset entry display page rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryDisplayPageRelPersistence
 * @see com.liferay.asset.entry.rel.service.persistence.AssetEntryDisplayPageRelUtil
 * @generated
 */
@ProviderType
public class AssetEntryDisplayPageRelPersistenceImpl extends BasePersistenceImpl<AssetEntryDisplayPageRel>
	implements AssetEntryDisplayPageRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AssetEntryDisplayPageRelUtil} to access the asset entry display page rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AssetEntryDisplayPageRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AssetEntryDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryDisplayPageRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryDisplayPageRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AssetEntryDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryDisplayPageRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryDisplayPageRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetEntryDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryDisplayPageRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ASSETENTRYID =
		new FinderPath(AssetEntryDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryDisplayPageRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryDisplayPageRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAssetEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID =
		new FinderPath(AssetEntryDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryDisplayPageRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryDisplayPageRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAssetEntryId",
			new String[] { Long.class.getName() },
			AssetEntryDisplayPageRelModelImpl.ASSETENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ASSETENTRYID = new FinderPath(AssetEntryDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryDisplayPageRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAssetEntryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the asset entry display page rels where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the matching asset entry display page rels
	 */
	@Override
	public List<AssetEntryDisplayPageRel> findByAssetEntryId(long assetEntryId) {
		return findByAssetEntryId(assetEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry display page rels where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset entry display page rels
	 * @param end the upper bound of the range of asset entry display page rels (not inclusive)
	 * @return the range of matching asset entry display page rels
	 */
	@Override
	public List<AssetEntryDisplayPageRel> findByAssetEntryId(
		long assetEntryId, int start, int end) {
		return findByAssetEntryId(assetEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry display page rels where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset entry display page rels
	 * @param end the upper bound of the range of asset entry display page rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry display page rels
	 */
	@Override
	public List<AssetEntryDisplayPageRel> findByAssetEntryId(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetEntryDisplayPageRel> orderByComparator) {
		return findByAssetEntryId(assetEntryId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the asset entry display page rels where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset entry display page rels
	 * @param end the upper bound of the range of asset entry display page rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset entry display page rels
	 */
	@Override
	public List<AssetEntryDisplayPageRel> findByAssetEntryId(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetEntryDisplayPageRel> orderByComparator,
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

		List<AssetEntryDisplayPageRel> list = null;

		if (retrieveFromCache) {
			list = (List<AssetEntryDisplayPageRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntryDisplayPageRel assetEntryDisplayPageRel : list) {
					if ((assetEntryId != assetEntryDisplayPageRel.getAssetEntryId())) {
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

			query.append(_SQL_SELECT_ASSETENTRYDISPLAYPAGEREL_WHERE);

			query.append(_FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetEntryDisplayPageRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				if (!pagination) {
					list = (List<AssetEntryDisplayPageRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetEntryDisplayPageRel>)QueryUtil.list(q,
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
	 * Returns the first asset entry display page rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry display page rel
	 * @throws NoSuchEntryDisplayPageRelException if a matching asset entry display page rel could not be found
	 */
	@Override
	public AssetEntryDisplayPageRel findByAssetEntryId_First(
		long assetEntryId,
		OrderByComparator<AssetEntryDisplayPageRel> orderByComparator)
		throws NoSuchEntryDisplayPageRelException {
		AssetEntryDisplayPageRel assetEntryDisplayPageRel = fetchByAssetEntryId_First(assetEntryId,
				orderByComparator);

		if (assetEntryDisplayPageRel != null) {
			return assetEntryDisplayPageRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetEntryId=");
		msg.append(assetEntryId);

		msg.append("}");

		throw new NoSuchEntryDisplayPageRelException(msg.toString());
	}

	/**
	 * Returns the first asset entry display page rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry display page rel, or <code>null</code> if a matching asset entry display page rel could not be found
	 */
	@Override
	public AssetEntryDisplayPageRel fetchByAssetEntryId_First(
		long assetEntryId,
		OrderByComparator<AssetEntryDisplayPageRel> orderByComparator) {
		List<AssetEntryDisplayPageRel> list = findByAssetEntryId(assetEntryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry display page rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry display page rel
	 * @throws NoSuchEntryDisplayPageRelException if a matching asset entry display page rel could not be found
	 */
	@Override
	public AssetEntryDisplayPageRel findByAssetEntryId_Last(long assetEntryId,
		OrderByComparator<AssetEntryDisplayPageRel> orderByComparator)
		throws NoSuchEntryDisplayPageRelException {
		AssetEntryDisplayPageRel assetEntryDisplayPageRel = fetchByAssetEntryId_Last(assetEntryId,
				orderByComparator);

		if (assetEntryDisplayPageRel != null) {
			return assetEntryDisplayPageRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetEntryId=");
		msg.append(assetEntryId);

		msg.append("}");

		throw new NoSuchEntryDisplayPageRelException(msg.toString());
	}

	/**
	 * Returns the last asset entry display page rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry display page rel, or <code>null</code> if a matching asset entry display page rel could not be found
	 */
	@Override
	public AssetEntryDisplayPageRel fetchByAssetEntryId_Last(
		long assetEntryId,
		OrderByComparator<AssetEntryDisplayPageRel> orderByComparator) {
		int count = countByAssetEntryId(assetEntryId);

		if (count == 0) {
			return null;
		}

		List<AssetEntryDisplayPageRel> list = findByAssetEntryId(assetEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry display page rels before and after the current asset entry display page rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryDisplayPageRelId the primary key of the current asset entry display page rel
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry display page rel
	 * @throws NoSuchEntryDisplayPageRelException if a asset entry display page rel with the primary key could not be found
	 */
	@Override
	public AssetEntryDisplayPageRel[] findByAssetEntryId_PrevAndNext(
		long assetEntryDisplayPageRelId, long assetEntryId,
		OrderByComparator<AssetEntryDisplayPageRel> orderByComparator)
		throws NoSuchEntryDisplayPageRelException {
		AssetEntryDisplayPageRel assetEntryDisplayPageRel = findByPrimaryKey(assetEntryDisplayPageRelId);

		Session session = null;

		try {
			session = openSession();

			AssetEntryDisplayPageRel[] array = new AssetEntryDisplayPageRelImpl[3];

			array[0] = getByAssetEntryId_PrevAndNext(session,
					assetEntryDisplayPageRel, assetEntryId, orderByComparator,
					true);

			array[1] = assetEntryDisplayPageRel;

			array[2] = getByAssetEntryId_PrevAndNext(session,
					assetEntryDisplayPageRel, assetEntryId, orderByComparator,
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

	protected AssetEntryDisplayPageRel getByAssetEntryId_PrevAndNext(
		Session session, AssetEntryDisplayPageRel assetEntryDisplayPageRel,
		long assetEntryId,
		OrderByComparator<AssetEntryDisplayPageRel> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETENTRYDISPLAYPAGEREL_WHERE);

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
			query.append(AssetEntryDisplayPageRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(assetEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetEntryDisplayPageRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetEntryDisplayPageRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry display page rels where assetEntryId = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 */
	@Override
	public void removeByAssetEntryId(long assetEntryId) {
		for (AssetEntryDisplayPageRel assetEntryDisplayPageRel : findByAssetEntryId(
				assetEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetEntryDisplayPageRel);
		}
	}

	/**
	 * Returns the number of asset entry display page rels where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the number of matching asset entry display page rels
	 */
	@Override
	public int countByAssetEntryId(long assetEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ASSETENTRYID;

		Object[] finderArgs = new Object[] { assetEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETENTRYDISPLAYPAGEREL_WHERE);

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

	private static final String _FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2 = "assetEntryDisplayPageRel.assetEntryId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_A_D = new FinderPath(AssetEntryDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryDisplayPageRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryDisplayPageRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByA_D",
			new String[] { Long.class.getName(), Long.class.getName() },
			AssetEntryDisplayPageRelModelImpl.ASSETENTRYID_COLUMN_BITMASK |
			AssetEntryDisplayPageRelModelImpl.DISPLAYPAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_A_D = new FinderPath(AssetEntryDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryDisplayPageRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_D",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the asset entry display page rel where assetEntryId = &#63; and displayPageId = &#63; or throws a {@link NoSuchEntryDisplayPageRelException} if it could not be found.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param displayPageId the display page ID
	 * @return the matching asset entry display page rel
	 * @throws NoSuchEntryDisplayPageRelException if a matching asset entry display page rel could not be found
	 */
	@Override
	public AssetEntryDisplayPageRel findByA_D(long assetEntryId,
		long displayPageId) throws NoSuchEntryDisplayPageRelException {
		AssetEntryDisplayPageRel assetEntryDisplayPageRel = fetchByA_D(assetEntryId,
				displayPageId);

		if (assetEntryDisplayPageRel == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetEntryId=");
			msg.append(assetEntryId);

			msg.append(", displayPageId=");
			msg.append(displayPageId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchEntryDisplayPageRelException(msg.toString());
		}

		return assetEntryDisplayPageRel;
	}

	/**
	 * Returns the asset entry display page rel where assetEntryId = &#63; and displayPageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param displayPageId the display page ID
	 * @return the matching asset entry display page rel, or <code>null</code> if a matching asset entry display page rel could not be found
	 */
	@Override
	public AssetEntryDisplayPageRel fetchByA_D(long assetEntryId,
		long displayPageId) {
		return fetchByA_D(assetEntryId, displayPageId, true);
	}

	/**
	 * Returns the asset entry display page rel where assetEntryId = &#63; and displayPageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param displayPageId the display page ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching asset entry display page rel, or <code>null</code> if a matching asset entry display page rel could not be found
	 */
	@Override
	public AssetEntryDisplayPageRel fetchByA_D(long assetEntryId,
		long displayPageId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { assetEntryId, displayPageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_A_D,
					finderArgs, this);
		}

		if (result instanceof AssetEntryDisplayPageRel) {
			AssetEntryDisplayPageRel assetEntryDisplayPageRel = (AssetEntryDisplayPageRel)result;

			if ((assetEntryId != assetEntryDisplayPageRel.getAssetEntryId()) ||
					(displayPageId != assetEntryDisplayPageRel.getDisplayPageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ASSETENTRYDISPLAYPAGEREL_WHERE);

			query.append(_FINDER_COLUMN_A_D_ASSETENTRYID_2);

			query.append(_FINDER_COLUMN_A_D_DISPLAYPAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				qPos.add(displayPageId);

				List<AssetEntryDisplayPageRel> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_A_D, finderArgs,
						list);
				}
				else {
					AssetEntryDisplayPageRel assetEntryDisplayPageRel = list.get(0);

					result = assetEntryDisplayPageRel;

					cacheResult(assetEntryDisplayPageRel);

					if ((assetEntryDisplayPageRel.getAssetEntryId() != assetEntryId) ||
							(assetEntryDisplayPageRel.getDisplayPageId() != displayPageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_A_D,
							finderArgs, assetEntryDisplayPageRel);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_A_D, finderArgs);

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
			return (AssetEntryDisplayPageRel)result;
		}
	}

	/**
	 * Removes the asset entry display page rel where assetEntryId = &#63; and displayPageId = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param displayPageId the display page ID
	 * @return the asset entry display page rel that was removed
	 */
	@Override
	public AssetEntryDisplayPageRel removeByA_D(long assetEntryId,
		long displayPageId) throws NoSuchEntryDisplayPageRelException {
		AssetEntryDisplayPageRel assetEntryDisplayPageRel = findByA_D(assetEntryId,
				displayPageId);

		return remove(assetEntryDisplayPageRel);
	}

	/**
	 * Returns the number of asset entry display page rels where assetEntryId = &#63; and displayPageId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param displayPageId the display page ID
	 * @return the number of matching asset entry display page rels
	 */
	@Override
	public int countByA_D(long assetEntryId, long displayPageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_A_D;

		Object[] finderArgs = new Object[] { assetEntryId, displayPageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETENTRYDISPLAYPAGEREL_WHERE);

			query.append(_FINDER_COLUMN_A_D_ASSETENTRYID_2);

			query.append(_FINDER_COLUMN_A_D_DISPLAYPAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				qPos.add(displayPageId);

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

	private static final String _FINDER_COLUMN_A_D_ASSETENTRYID_2 = "assetEntryDisplayPageRel.assetEntryId = ? AND ";
	private static final String _FINDER_COLUMN_A_D_DISPLAYPAGEID_2 = "assetEntryDisplayPageRel.displayPageId = ?";

	public AssetEntryDisplayPageRelPersistenceImpl() {
		setModelClass(AssetEntryDisplayPageRel.class);
	}

	/**
	 * Caches the asset entry display page rel in the entity cache if it is enabled.
	 *
	 * @param assetEntryDisplayPageRel the asset entry display page rel
	 */
	@Override
	public void cacheResult(AssetEntryDisplayPageRel assetEntryDisplayPageRel) {
		entityCache.putResult(AssetEntryDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryDisplayPageRelImpl.class,
			assetEntryDisplayPageRel.getPrimaryKey(), assetEntryDisplayPageRel);

		finderCache.putResult(FINDER_PATH_FETCH_BY_A_D,
			new Object[] {
				assetEntryDisplayPageRel.getAssetEntryId(),
				assetEntryDisplayPageRel.getDisplayPageId()
			}, assetEntryDisplayPageRel);

		assetEntryDisplayPageRel.resetOriginalValues();
	}

	/**
	 * Caches the asset entry display page rels in the entity cache if it is enabled.
	 *
	 * @param assetEntryDisplayPageRels the asset entry display page rels
	 */
	@Override
	public void cacheResult(
		List<AssetEntryDisplayPageRel> assetEntryDisplayPageRels) {
		for (AssetEntryDisplayPageRel assetEntryDisplayPageRel : assetEntryDisplayPageRels) {
			if (entityCache.getResult(
						AssetEntryDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
						AssetEntryDisplayPageRelImpl.class,
						assetEntryDisplayPageRel.getPrimaryKey()) == null) {
				cacheResult(assetEntryDisplayPageRel);
			}
			else {
				assetEntryDisplayPageRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset entry display page rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetEntryDisplayPageRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset entry display page rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetEntryDisplayPageRel assetEntryDisplayPageRel) {
		entityCache.removeResult(AssetEntryDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryDisplayPageRelImpl.class,
			assetEntryDisplayPageRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((AssetEntryDisplayPageRelModelImpl)assetEntryDisplayPageRel,
			true);
	}

	@Override
	public void clearCache(
		List<AssetEntryDisplayPageRel> assetEntryDisplayPageRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetEntryDisplayPageRel assetEntryDisplayPageRel : assetEntryDisplayPageRels) {
			entityCache.removeResult(AssetEntryDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
				AssetEntryDisplayPageRelImpl.class,
				assetEntryDisplayPageRel.getPrimaryKey());

			clearUniqueFindersCache((AssetEntryDisplayPageRelModelImpl)assetEntryDisplayPageRel,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetEntryDisplayPageRelModelImpl assetEntryDisplayPageRelModelImpl) {
		Object[] args = new Object[] {
				assetEntryDisplayPageRelModelImpl.getAssetEntryId(),
				assetEntryDisplayPageRelModelImpl.getDisplayPageId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_A_D, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_A_D, args,
			assetEntryDisplayPageRelModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		AssetEntryDisplayPageRelModelImpl assetEntryDisplayPageRelModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					assetEntryDisplayPageRelModelImpl.getAssetEntryId(),
					assetEntryDisplayPageRelModelImpl.getDisplayPageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_A_D, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_A_D, args);
		}

		if ((assetEntryDisplayPageRelModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_A_D.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					assetEntryDisplayPageRelModelImpl.getOriginalAssetEntryId(),
					assetEntryDisplayPageRelModelImpl.getOriginalDisplayPageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_A_D, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_A_D, args);
		}
	}

	/**
	 * Creates a new asset entry display page rel with the primary key. Does not add the asset entry display page rel to the database.
	 *
	 * @param assetEntryDisplayPageRelId the primary key for the new asset entry display page rel
	 * @return the new asset entry display page rel
	 */
	@Override
	public AssetEntryDisplayPageRel create(long assetEntryDisplayPageRelId) {
		AssetEntryDisplayPageRel assetEntryDisplayPageRel = new AssetEntryDisplayPageRelImpl();

		assetEntryDisplayPageRel.setNew(true);
		assetEntryDisplayPageRel.setPrimaryKey(assetEntryDisplayPageRelId);

		return assetEntryDisplayPageRel;
	}

	/**
	 * Removes the asset entry display page rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryDisplayPageRelId the primary key of the asset entry display page rel
	 * @return the asset entry display page rel that was removed
	 * @throws NoSuchEntryDisplayPageRelException if a asset entry display page rel with the primary key could not be found
	 */
	@Override
	public AssetEntryDisplayPageRel remove(long assetEntryDisplayPageRelId)
		throws NoSuchEntryDisplayPageRelException {
		return remove((Serializable)assetEntryDisplayPageRelId);
	}

	/**
	 * Removes the asset entry display page rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset entry display page rel
	 * @return the asset entry display page rel that was removed
	 * @throws NoSuchEntryDisplayPageRelException if a asset entry display page rel with the primary key could not be found
	 */
	@Override
	public AssetEntryDisplayPageRel remove(Serializable primaryKey)
		throws NoSuchEntryDisplayPageRelException {
		Session session = null;

		try {
			session = openSession();

			AssetEntryDisplayPageRel assetEntryDisplayPageRel = (AssetEntryDisplayPageRel)session.get(AssetEntryDisplayPageRelImpl.class,
					primaryKey);

			if (assetEntryDisplayPageRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryDisplayPageRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(assetEntryDisplayPageRel);
		}
		catch (NoSuchEntryDisplayPageRelException nsee) {
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
	protected AssetEntryDisplayPageRel removeImpl(
		AssetEntryDisplayPageRel assetEntryDisplayPageRel) {
		assetEntryDisplayPageRel = toUnwrappedModel(assetEntryDisplayPageRel);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetEntryDisplayPageRel)) {
				assetEntryDisplayPageRel = (AssetEntryDisplayPageRel)session.get(AssetEntryDisplayPageRelImpl.class,
						assetEntryDisplayPageRel.getPrimaryKeyObj());
			}

			if (assetEntryDisplayPageRel != null) {
				session.delete(assetEntryDisplayPageRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (assetEntryDisplayPageRel != null) {
			clearCache(assetEntryDisplayPageRel);
		}

		return assetEntryDisplayPageRel;
	}

	@Override
	public AssetEntryDisplayPageRel updateImpl(
		AssetEntryDisplayPageRel assetEntryDisplayPageRel) {
		assetEntryDisplayPageRel = toUnwrappedModel(assetEntryDisplayPageRel);

		boolean isNew = assetEntryDisplayPageRel.isNew();

		AssetEntryDisplayPageRelModelImpl assetEntryDisplayPageRelModelImpl = (AssetEntryDisplayPageRelModelImpl)assetEntryDisplayPageRel;

		Session session = null;

		try {
			session = openSession();

			if (assetEntryDisplayPageRel.isNew()) {
				session.save(assetEntryDisplayPageRel);

				assetEntryDisplayPageRel.setNew(false);
			}
			else {
				assetEntryDisplayPageRel = (AssetEntryDisplayPageRel)session.merge(assetEntryDisplayPageRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AssetEntryDisplayPageRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					assetEntryDisplayPageRelModelImpl.getAssetEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((assetEntryDisplayPageRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetEntryDisplayPageRelModelImpl.getOriginalAssetEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID,
					args);

				args = new Object[] {
						assetEntryDisplayPageRelModelImpl.getAssetEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID,
					args);
			}
		}

		entityCache.putResult(AssetEntryDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryDisplayPageRelImpl.class,
			assetEntryDisplayPageRel.getPrimaryKey(), assetEntryDisplayPageRel,
			false);

		clearUniqueFindersCache(assetEntryDisplayPageRelModelImpl, false);
		cacheUniqueFindersCache(assetEntryDisplayPageRelModelImpl);

		assetEntryDisplayPageRel.resetOriginalValues();

		return assetEntryDisplayPageRel;
	}

	protected AssetEntryDisplayPageRel toUnwrappedModel(
		AssetEntryDisplayPageRel assetEntryDisplayPageRel) {
		if (assetEntryDisplayPageRel instanceof AssetEntryDisplayPageRelImpl) {
			return assetEntryDisplayPageRel;
		}

		AssetEntryDisplayPageRelImpl assetEntryDisplayPageRelImpl = new AssetEntryDisplayPageRelImpl();

		assetEntryDisplayPageRelImpl.setNew(assetEntryDisplayPageRel.isNew());
		assetEntryDisplayPageRelImpl.setPrimaryKey(assetEntryDisplayPageRel.getPrimaryKey());

		assetEntryDisplayPageRelImpl.setAssetEntryDisplayPageRelId(assetEntryDisplayPageRel.getAssetEntryDisplayPageRelId());
		assetEntryDisplayPageRelImpl.setAssetEntryId(assetEntryDisplayPageRel.getAssetEntryId());
		assetEntryDisplayPageRelImpl.setDisplayPageId(assetEntryDisplayPageRel.getDisplayPageId());

		return assetEntryDisplayPageRelImpl;
	}

	/**
	 * Returns the asset entry display page rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset entry display page rel
	 * @return the asset entry display page rel
	 * @throws NoSuchEntryDisplayPageRelException if a asset entry display page rel with the primary key could not be found
	 */
	@Override
	public AssetEntryDisplayPageRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryDisplayPageRelException {
		AssetEntryDisplayPageRel assetEntryDisplayPageRel = fetchByPrimaryKey(primaryKey);

		if (assetEntryDisplayPageRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryDisplayPageRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return assetEntryDisplayPageRel;
	}

	/**
	 * Returns the asset entry display page rel with the primary key or throws a {@link NoSuchEntryDisplayPageRelException} if it could not be found.
	 *
	 * @param assetEntryDisplayPageRelId the primary key of the asset entry display page rel
	 * @return the asset entry display page rel
	 * @throws NoSuchEntryDisplayPageRelException if a asset entry display page rel with the primary key could not be found
	 */
	@Override
	public AssetEntryDisplayPageRel findByPrimaryKey(
		long assetEntryDisplayPageRelId)
		throws NoSuchEntryDisplayPageRelException {
		return findByPrimaryKey((Serializable)assetEntryDisplayPageRelId);
	}

	/**
	 * Returns the asset entry display page rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset entry display page rel
	 * @return the asset entry display page rel, or <code>null</code> if a asset entry display page rel with the primary key could not be found
	 */
	@Override
	public AssetEntryDisplayPageRel fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(AssetEntryDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
				AssetEntryDisplayPageRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		AssetEntryDisplayPageRel assetEntryDisplayPageRel = (AssetEntryDisplayPageRel)serializable;

		if (assetEntryDisplayPageRel == null) {
			Session session = null;

			try {
				session = openSession();

				assetEntryDisplayPageRel = (AssetEntryDisplayPageRel)session.get(AssetEntryDisplayPageRelImpl.class,
						primaryKey);

				if (assetEntryDisplayPageRel != null) {
					cacheResult(assetEntryDisplayPageRel);
				}
				else {
					entityCache.putResult(AssetEntryDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
						AssetEntryDisplayPageRelImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(AssetEntryDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
					AssetEntryDisplayPageRelImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return assetEntryDisplayPageRel;
	}

	/**
	 * Returns the asset entry display page rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetEntryDisplayPageRelId the primary key of the asset entry display page rel
	 * @return the asset entry display page rel, or <code>null</code> if a asset entry display page rel with the primary key could not be found
	 */
	@Override
	public AssetEntryDisplayPageRel fetchByPrimaryKey(
		long assetEntryDisplayPageRelId) {
		return fetchByPrimaryKey((Serializable)assetEntryDisplayPageRelId);
	}

	@Override
	public Map<Serializable, AssetEntryDisplayPageRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetEntryDisplayPageRel> map = new HashMap<Serializable, AssetEntryDisplayPageRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetEntryDisplayPageRel assetEntryDisplayPageRel = fetchByPrimaryKey(primaryKey);

			if (assetEntryDisplayPageRel != null) {
				map.put(primaryKey, assetEntryDisplayPageRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(AssetEntryDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
					AssetEntryDisplayPageRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (AssetEntryDisplayPageRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_ASSETENTRYDISPLAYPAGEREL_WHERE_PKS_IN);

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

			for (AssetEntryDisplayPageRel assetEntryDisplayPageRel : (List<AssetEntryDisplayPageRel>)q.list()) {
				map.put(assetEntryDisplayPageRel.getPrimaryKeyObj(),
					assetEntryDisplayPageRel);

				cacheResult(assetEntryDisplayPageRel);

				uncachedPrimaryKeys.remove(assetEntryDisplayPageRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(AssetEntryDisplayPageRelModelImpl.ENTITY_CACHE_ENABLED,
					AssetEntryDisplayPageRelImpl.class, primaryKey, nullModel);
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
	 * Returns all the asset entry display page rels.
	 *
	 * @return the asset entry display page rels
	 */
	@Override
	public List<AssetEntryDisplayPageRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry display page rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry display page rels
	 * @param end the upper bound of the range of asset entry display page rels (not inclusive)
	 * @return the range of asset entry display page rels
	 */
	@Override
	public List<AssetEntryDisplayPageRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry display page rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry display page rels
	 * @param end the upper bound of the range of asset entry display page rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset entry display page rels
	 */
	@Override
	public List<AssetEntryDisplayPageRel> findAll(int start, int end,
		OrderByComparator<AssetEntryDisplayPageRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry display page rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry display page rels
	 * @param end the upper bound of the range of asset entry display page rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of asset entry display page rels
	 */
	@Override
	public List<AssetEntryDisplayPageRel> findAll(int start, int end,
		OrderByComparator<AssetEntryDisplayPageRel> orderByComparator,
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

		List<AssetEntryDisplayPageRel> list = null;

		if (retrieveFromCache) {
			list = (List<AssetEntryDisplayPageRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ASSETENTRYDISPLAYPAGEREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETENTRYDISPLAYPAGEREL;

				if (pagination) {
					sql = sql.concat(AssetEntryDisplayPageRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AssetEntryDisplayPageRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetEntryDisplayPageRel>)QueryUtil.list(q,
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
	 * Removes all the asset entry display page rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetEntryDisplayPageRel assetEntryDisplayPageRel : findAll()) {
			remove(assetEntryDisplayPageRel);
		}
	}

	/**
	 * Returns the number of asset entry display page rels.
	 *
	 * @return the number of asset entry display page rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETENTRYDISPLAYPAGEREL);

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
		return AssetEntryDisplayPageRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the asset entry display page rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(AssetEntryDisplayPageRelImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_ASSETENTRYDISPLAYPAGEREL = "SELECT assetEntryDisplayPageRel FROM AssetEntryDisplayPageRel assetEntryDisplayPageRel";
	private static final String _SQL_SELECT_ASSETENTRYDISPLAYPAGEREL_WHERE_PKS_IN =
		"SELECT assetEntryDisplayPageRel FROM AssetEntryDisplayPageRel assetEntryDisplayPageRel WHERE assetEntryDisplayPageRelId IN (";
	private static final String _SQL_SELECT_ASSETENTRYDISPLAYPAGEREL_WHERE = "SELECT assetEntryDisplayPageRel FROM AssetEntryDisplayPageRel assetEntryDisplayPageRel WHERE ";
	private static final String _SQL_COUNT_ASSETENTRYDISPLAYPAGEREL = "SELECT COUNT(assetEntryDisplayPageRel) FROM AssetEntryDisplayPageRel assetEntryDisplayPageRel";
	private static final String _SQL_COUNT_ASSETENTRYDISPLAYPAGEREL_WHERE = "SELECT COUNT(assetEntryDisplayPageRel) FROM AssetEntryDisplayPageRel assetEntryDisplayPageRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetEntryDisplayPageRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetEntryDisplayPageRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetEntryDisplayPageRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(AssetEntryDisplayPageRelPersistenceImpl.class);
}