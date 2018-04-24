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

import com.liferay.asset.entry.rel.exception.NoSuchEntryAssetCategoryRelException;
import com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel;
import com.liferay.asset.entry.rel.model.impl.AssetEntryAssetCategoryRelImpl;
import com.liferay.asset.entry.rel.model.impl.AssetEntryAssetCategoryRelModelImpl;
import com.liferay.asset.entry.rel.service.persistence.AssetEntryAssetCategoryRelPersistence;

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
 * The persistence implementation for the asset entry asset category rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryAssetCategoryRelPersistence
 * @see com.liferay.asset.entry.rel.service.persistence.AssetEntryAssetCategoryRelUtil
 * @generated
 */
@ProviderType
public class AssetEntryAssetCategoryRelPersistenceImpl
	extends BasePersistenceImpl<AssetEntryAssetCategoryRel>
	implements AssetEntryAssetCategoryRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AssetEntryAssetCategoryRelUtil} to access the asset entry asset category rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AssetEntryAssetCategoryRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AssetEntryAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetCategoryRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetCategoryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AssetEntryAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetCategoryRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetCategoryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetEntryAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetCategoryRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ASSETENTRYID =
		new FinderPath(AssetEntryAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetCategoryRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetCategoryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAssetEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID =
		new FinderPath(AssetEntryAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetCategoryRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetCategoryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAssetEntryId",
			new String[] { Long.class.getName() },
			AssetEntryAssetCategoryRelModelImpl.ASSETENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ASSETENTRYID = new FinderPath(AssetEntryAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetCategoryRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAssetEntryId", new String[] { Long.class.getName() });

	/**
	 * Returns all the asset entry asset category rels where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the matching asset entry asset category rels
	 */
	@Override
	public List<AssetEntryAssetCategoryRel> findByAssetEntryId(
		long assetEntryId) {
		return findByAssetEntryId(assetEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry asset category rels where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset entry asset category rels
	 * @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	 * @return the range of matching asset entry asset category rels
	 */
	@Override
	public List<AssetEntryAssetCategoryRel> findByAssetEntryId(
		long assetEntryId, int start, int end) {
		return findByAssetEntryId(assetEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry asset category rels where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset entry asset category rels
	 * @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry asset category rels
	 */
	@Override
	public List<AssetEntryAssetCategoryRel> findByAssetEntryId(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {
		return findByAssetEntryId(assetEntryId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the asset entry asset category rels where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset entry asset category rels
	 * @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset entry asset category rels
	 */
	@Override
	public List<AssetEntryAssetCategoryRel> findByAssetEntryId(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator,
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

		List<AssetEntryAssetCategoryRel> list = null;

		if (retrieveFromCache) {
			list = (List<AssetEntryAssetCategoryRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntryAssetCategoryRel assetEntryAssetCategoryRel : list) {
					if ((assetEntryId != assetEntryAssetCategoryRel.getAssetEntryId())) {
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

			query.append(_SQL_SELECT_ASSETENTRYASSETCATEGORYREL_WHERE);

			query.append(_FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetEntryAssetCategoryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				if (!pagination) {
					list = (List<AssetEntryAssetCategoryRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetEntryAssetCategoryRel>)QueryUtil.list(q,
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
	 * Returns the first asset entry asset category rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a matching asset entry asset category rel could not be found
	 */
	@Override
	public AssetEntryAssetCategoryRel findByAssetEntryId_First(
		long assetEntryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator)
		throws NoSuchEntryAssetCategoryRelException {
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel = fetchByAssetEntryId_First(assetEntryId,
				orderByComparator);

		if (assetEntryAssetCategoryRel != null) {
			return assetEntryAssetCategoryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetEntryId=");
		msg.append(assetEntryId);

		msg.append("}");

		throw new NoSuchEntryAssetCategoryRelException(msg.toString());
	}

	/**
	 * Returns the first asset entry asset category rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset category rel, or <code>null</code> if a matching asset entry asset category rel could not be found
	 */
	@Override
	public AssetEntryAssetCategoryRel fetchByAssetEntryId_First(
		long assetEntryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {
		List<AssetEntryAssetCategoryRel> list = findByAssetEntryId(assetEntryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry asset category rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a matching asset entry asset category rel could not be found
	 */
	@Override
	public AssetEntryAssetCategoryRel findByAssetEntryId_Last(
		long assetEntryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator)
		throws NoSuchEntryAssetCategoryRelException {
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel = fetchByAssetEntryId_Last(assetEntryId,
				orderByComparator);

		if (assetEntryAssetCategoryRel != null) {
			return assetEntryAssetCategoryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetEntryId=");
		msg.append(assetEntryId);

		msg.append("}");

		throw new NoSuchEntryAssetCategoryRelException(msg.toString());
	}

	/**
	 * Returns the last asset entry asset category rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset category rel, or <code>null</code> if a matching asset entry asset category rel could not be found
	 */
	@Override
	public AssetEntryAssetCategoryRel fetchByAssetEntryId_Last(
		long assetEntryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {
		int count = countByAssetEntryId(assetEntryId);

		if (count == 0) {
			return null;
		}

		List<AssetEntryAssetCategoryRel> list = findByAssetEntryId(assetEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry asset category rels before and after the current asset entry asset category rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryAssetCategoryRelId the primary key of the current asset entry asset category rel
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a asset entry asset category rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetCategoryRel[] findByAssetEntryId_PrevAndNext(
		long assetEntryAssetCategoryRelId, long assetEntryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator)
		throws NoSuchEntryAssetCategoryRelException {
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel = findByPrimaryKey(assetEntryAssetCategoryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetEntryAssetCategoryRel[] array = new AssetEntryAssetCategoryRelImpl[3];

			array[0] = getByAssetEntryId_PrevAndNext(session,
					assetEntryAssetCategoryRel, assetEntryId,
					orderByComparator, true);

			array[1] = assetEntryAssetCategoryRel;

			array[2] = getByAssetEntryId_PrevAndNext(session,
					assetEntryAssetCategoryRel, assetEntryId,
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

	protected AssetEntryAssetCategoryRel getByAssetEntryId_PrevAndNext(
		Session session, AssetEntryAssetCategoryRel assetEntryAssetCategoryRel,
		long assetEntryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETENTRYASSETCATEGORYREL_WHERE);

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
			query.append(AssetEntryAssetCategoryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(assetEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetEntryAssetCategoryRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetEntryAssetCategoryRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry asset category rels where assetEntryId = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 */
	@Override
	public void removeByAssetEntryId(long assetEntryId) {
		for (AssetEntryAssetCategoryRel assetEntryAssetCategoryRel : findByAssetEntryId(
				assetEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetEntryAssetCategoryRel);
		}
	}

	/**
	 * Returns the number of asset entry asset category rels where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the number of matching asset entry asset category rels
	 */
	@Override
	public int countByAssetEntryId(long assetEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ASSETENTRYID;

		Object[] finderArgs = new Object[] { assetEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETENTRYASSETCATEGORYREL_WHERE);

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

	private static final String _FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2 = "assetEntryAssetCategoryRel.assetEntryId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ASSETCATEGORYID =
		new FinderPath(AssetEntryAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetCategoryRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetCategoryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAssetCategoryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETCATEGORYID =
		new FinderPath(AssetEntryAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetCategoryRelModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryAssetCategoryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAssetCategoryId",
			new String[] { Long.class.getName() },
			AssetEntryAssetCategoryRelModelImpl.ASSETCATEGORYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ASSETCATEGORYID = new FinderPath(AssetEntryAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetCategoryRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAssetCategoryId", new String[] { Long.class.getName() });

	/**
	 * Returns all the asset entry asset category rels where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @return the matching asset entry asset category rels
	 */
	@Override
	public List<AssetEntryAssetCategoryRel> findByAssetCategoryId(
		long assetCategoryId) {
		return findByAssetCategoryId(assetCategoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry asset category rels where assetCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetCategoryId the asset category ID
	 * @param start the lower bound of the range of asset entry asset category rels
	 * @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	 * @return the range of matching asset entry asset category rels
	 */
	@Override
	public List<AssetEntryAssetCategoryRel> findByAssetCategoryId(
		long assetCategoryId, int start, int end) {
		return findByAssetCategoryId(assetCategoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry asset category rels where assetCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetCategoryId the asset category ID
	 * @param start the lower bound of the range of asset entry asset category rels
	 * @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry asset category rels
	 */
	@Override
	public List<AssetEntryAssetCategoryRel> findByAssetCategoryId(
		long assetCategoryId, int start, int end,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {
		return findByAssetCategoryId(assetCategoryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry asset category rels where assetCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetCategoryId the asset category ID
	 * @param start the lower bound of the range of asset entry asset category rels
	 * @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset entry asset category rels
	 */
	@Override
	public List<AssetEntryAssetCategoryRel> findByAssetCategoryId(
		long assetCategoryId, int start, int end,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETCATEGORYID;
			finderArgs = new Object[] { assetCategoryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ASSETCATEGORYID;
			finderArgs = new Object[] {
					assetCategoryId,
					
					start, end, orderByComparator
				};
		}

		List<AssetEntryAssetCategoryRel> list = null;

		if (retrieveFromCache) {
			list = (List<AssetEntryAssetCategoryRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntryAssetCategoryRel assetEntryAssetCategoryRel : list) {
					if ((assetCategoryId != assetEntryAssetCategoryRel.getAssetCategoryId())) {
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

			query.append(_SQL_SELECT_ASSETENTRYASSETCATEGORYREL_WHERE);

			query.append(_FINDER_COLUMN_ASSETCATEGORYID_ASSETCATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetEntryAssetCategoryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetCategoryId);

				if (!pagination) {
					list = (List<AssetEntryAssetCategoryRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetEntryAssetCategoryRel>)QueryUtil.list(q,
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
	 * Returns the first asset entry asset category rel in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a matching asset entry asset category rel could not be found
	 */
	@Override
	public AssetEntryAssetCategoryRel findByAssetCategoryId_First(
		long assetCategoryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator)
		throws NoSuchEntryAssetCategoryRelException {
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel = fetchByAssetCategoryId_First(assetCategoryId,
				orderByComparator);

		if (assetEntryAssetCategoryRel != null) {
			return assetEntryAssetCategoryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetCategoryId=");
		msg.append(assetCategoryId);

		msg.append("}");

		throw new NoSuchEntryAssetCategoryRelException(msg.toString());
	}

	/**
	 * Returns the first asset entry asset category rel in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset category rel, or <code>null</code> if a matching asset entry asset category rel could not be found
	 */
	@Override
	public AssetEntryAssetCategoryRel fetchByAssetCategoryId_First(
		long assetCategoryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {
		List<AssetEntryAssetCategoryRel> list = findByAssetCategoryId(assetCategoryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry asset category rel in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a matching asset entry asset category rel could not be found
	 */
	@Override
	public AssetEntryAssetCategoryRel findByAssetCategoryId_Last(
		long assetCategoryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator)
		throws NoSuchEntryAssetCategoryRelException {
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel = fetchByAssetCategoryId_Last(assetCategoryId,
				orderByComparator);

		if (assetEntryAssetCategoryRel != null) {
			return assetEntryAssetCategoryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetCategoryId=");
		msg.append(assetCategoryId);

		msg.append("}");

		throw new NoSuchEntryAssetCategoryRelException(msg.toString());
	}

	/**
	 * Returns the last asset entry asset category rel in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset category rel, or <code>null</code> if a matching asset entry asset category rel could not be found
	 */
	@Override
	public AssetEntryAssetCategoryRel fetchByAssetCategoryId_Last(
		long assetCategoryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {
		int count = countByAssetCategoryId(assetCategoryId);

		if (count == 0) {
			return null;
		}

		List<AssetEntryAssetCategoryRel> list = findByAssetCategoryId(assetCategoryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry asset category rels before and after the current asset entry asset category rel in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetEntryAssetCategoryRelId the primary key of the current asset entry asset category rel
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a asset entry asset category rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetCategoryRel[] findByAssetCategoryId_PrevAndNext(
		long assetEntryAssetCategoryRelId, long assetCategoryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator)
		throws NoSuchEntryAssetCategoryRelException {
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel = findByPrimaryKey(assetEntryAssetCategoryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetEntryAssetCategoryRel[] array = new AssetEntryAssetCategoryRelImpl[3];

			array[0] = getByAssetCategoryId_PrevAndNext(session,
					assetEntryAssetCategoryRel, assetCategoryId,
					orderByComparator, true);

			array[1] = assetEntryAssetCategoryRel;

			array[2] = getByAssetCategoryId_PrevAndNext(session,
					assetEntryAssetCategoryRel, assetCategoryId,
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

	protected AssetEntryAssetCategoryRel getByAssetCategoryId_PrevAndNext(
		Session session, AssetEntryAssetCategoryRel assetEntryAssetCategoryRel,
		long assetCategoryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator,
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

		query.append(_SQL_SELECT_ASSETENTRYASSETCATEGORYREL_WHERE);

		query.append(_FINDER_COLUMN_ASSETCATEGORYID_ASSETCATEGORYID_2);

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
			query.append(AssetEntryAssetCategoryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(assetCategoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetEntryAssetCategoryRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetEntryAssetCategoryRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry asset category rels where assetCategoryId = &#63; from the database.
	 *
	 * @param assetCategoryId the asset category ID
	 */
	@Override
	public void removeByAssetCategoryId(long assetCategoryId) {
		for (AssetEntryAssetCategoryRel assetEntryAssetCategoryRel : findByAssetCategoryId(
				assetCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetEntryAssetCategoryRel);
		}
	}

	/**
	 * Returns the number of asset entry asset category rels where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @return the number of matching asset entry asset category rels
	 */
	@Override
	public int countByAssetCategoryId(long assetCategoryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ASSETCATEGORYID;

		Object[] finderArgs = new Object[] { assetCategoryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETENTRYASSETCATEGORYREL_WHERE);

			query.append(_FINDER_COLUMN_ASSETCATEGORYID_ASSETCATEGORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetCategoryId);

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

	private static final String _FINDER_COLUMN_ASSETCATEGORYID_ASSETCATEGORYID_2 =
		"assetEntryAssetCategoryRel.assetCategoryId = ?";

	public AssetEntryAssetCategoryRelPersistenceImpl() {
		setModelClass(AssetEntryAssetCategoryRel.class);
	}

	/**
	 * Caches the asset entry asset category rel in the entity cache if it is enabled.
	 *
	 * @param assetEntryAssetCategoryRel the asset entry asset category rel
	 */
	@Override
	public void cacheResult(
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel) {
		entityCache.putResult(AssetEntryAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetCategoryRelImpl.class,
			assetEntryAssetCategoryRel.getPrimaryKey(),
			assetEntryAssetCategoryRel);

		assetEntryAssetCategoryRel.resetOriginalValues();
	}

	/**
	 * Caches the asset entry asset category rels in the entity cache if it is enabled.
	 *
	 * @param assetEntryAssetCategoryRels the asset entry asset category rels
	 */
	@Override
	public void cacheResult(
		List<AssetEntryAssetCategoryRel> assetEntryAssetCategoryRels) {
		for (AssetEntryAssetCategoryRel assetEntryAssetCategoryRel : assetEntryAssetCategoryRels) {
			if (entityCache.getResult(
						AssetEntryAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
						AssetEntryAssetCategoryRelImpl.class,
						assetEntryAssetCategoryRel.getPrimaryKey()) == null) {
				cacheResult(assetEntryAssetCategoryRel);
			}
			else {
				assetEntryAssetCategoryRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset entry asset category rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetEntryAssetCategoryRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset entry asset category rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel) {
		entityCache.removeResult(AssetEntryAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetCategoryRelImpl.class,
			assetEntryAssetCategoryRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<AssetEntryAssetCategoryRel> assetEntryAssetCategoryRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetEntryAssetCategoryRel assetEntryAssetCategoryRel : assetEntryAssetCategoryRels) {
			entityCache.removeResult(AssetEntryAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
				AssetEntryAssetCategoryRelImpl.class,
				assetEntryAssetCategoryRel.getPrimaryKey());
		}
	}

	/**
	 * Creates a new asset entry asset category rel with the primary key. Does not add the asset entry asset category rel to the database.
	 *
	 * @param assetEntryAssetCategoryRelId the primary key for the new asset entry asset category rel
	 * @return the new asset entry asset category rel
	 */
	@Override
	public AssetEntryAssetCategoryRel create(long assetEntryAssetCategoryRelId) {
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel = new AssetEntryAssetCategoryRelImpl();

		assetEntryAssetCategoryRel.setNew(true);
		assetEntryAssetCategoryRel.setPrimaryKey(assetEntryAssetCategoryRelId);

		return assetEntryAssetCategoryRel;
	}

	/**
	 * Removes the asset entry asset category rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryAssetCategoryRelId the primary key of the asset entry asset category rel
	 * @return the asset entry asset category rel that was removed
	 * @throws NoSuchEntryAssetCategoryRelException if a asset entry asset category rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetCategoryRel remove(long assetEntryAssetCategoryRelId)
		throws NoSuchEntryAssetCategoryRelException {
		return remove((Serializable)assetEntryAssetCategoryRelId);
	}

	/**
	 * Removes the asset entry asset category rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset entry asset category rel
	 * @return the asset entry asset category rel that was removed
	 * @throws NoSuchEntryAssetCategoryRelException if a asset entry asset category rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetCategoryRel remove(Serializable primaryKey)
		throws NoSuchEntryAssetCategoryRelException {
		Session session = null;

		try {
			session = openSession();

			AssetEntryAssetCategoryRel assetEntryAssetCategoryRel = (AssetEntryAssetCategoryRel)session.get(AssetEntryAssetCategoryRelImpl.class,
					primaryKey);

			if (assetEntryAssetCategoryRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryAssetCategoryRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(assetEntryAssetCategoryRel);
		}
		catch (NoSuchEntryAssetCategoryRelException nsee) {
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
	protected AssetEntryAssetCategoryRel removeImpl(
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel) {
		assetEntryAssetCategoryRel = toUnwrappedModel(assetEntryAssetCategoryRel);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetEntryAssetCategoryRel)) {
				assetEntryAssetCategoryRel = (AssetEntryAssetCategoryRel)session.get(AssetEntryAssetCategoryRelImpl.class,
						assetEntryAssetCategoryRel.getPrimaryKeyObj());
			}

			if (assetEntryAssetCategoryRel != null) {
				session.delete(assetEntryAssetCategoryRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (assetEntryAssetCategoryRel != null) {
			clearCache(assetEntryAssetCategoryRel);
		}

		return assetEntryAssetCategoryRel;
	}

	@Override
	public AssetEntryAssetCategoryRel updateImpl(
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel) {
		assetEntryAssetCategoryRel = toUnwrappedModel(assetEntryAssetCategoryRel);

		boolean isNew = assetEntryAssetCategoryRel.isNew();

		AssetEntryAssetCategoryRelModelImpl assetEntryAssetCategoryRelModelImpl = (AssetEntryAssetCategoryRelModelImpl)assetEntryAssetCategoryRel;

		Session session = null;

		try {
			session = openSession();

			if (assetEntryAssetCategoryRel.isNew()) {
				session.save(assetEntryAssetCategoryRel);

				assetEntryAssetCategoryRel.setNew(false);
			}
			else {
				assetEntryAssetCategoryRel = (AssetEntryAssetCategoryRel)session.merge(assetEntryAssetCategoryRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AssetEntryAssetCategoryRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					assetEntryAssetCategoryRelModelImpl.getAssetEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID,
				args);

			args = new Object[] {
					assetEntryAssetCategoryRelModelImpl.getAssetCategoryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETCATEGORYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETCATEGORYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((assetEntryAssetCategoryRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetEntryAssetCategoryRelModelImpl.getOriginalAssetEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID,
					args);

				args = new Object[] {
						assetEntryAssetCategoryRelModelImpl.getAssetEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID,
					args);
			}

			if ((assetEntryAssetCategoryRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETCATEGORYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetEntryAssetCategoryRelModelImpl.getOriginalAssetCategoryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETCATEGORYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETCATEGORYID,
					args);

				args = new Object[] {
						assetEntryAssetCategoryRelModelImpl.getAssetCategoryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETCATEGORYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETCATEGORYID,
					args);
			}
		}

		entityCache.putResult(AssetEntryAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryAssetCategoryRelImpl.class,
			assetEntryAssetCategoryRel.getPrimaryKey(),
			assetEntryAssetCategoryRel, false);

		assetEntryAssetCategoryRel.resetOriginalValues();

		return assetEntryAssetCategoryRel;
	}

	protected AssetEntryAssetCategoryRel toUnwrappedModel(
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel) {
		if (assetEntryAssetCategoryRel instanceof AssetEntryAssetCategoryRelImpl) {
			return assetEntryAssetCategoryRel;
		}

		AssetEntryAssetCategoryRelImpl assetEntryAssetCategoryRelImpl = new AssetEntryAssetCategoryRelImpl();

		assetEntryAssetCategoryRelImpl.setNew(assetEntryAssetCategoryRel.isNew());
		assetEntryAssetCategoryRelImpl.setPrimaryKey(assetEntryAssetCategoryRel.getPrimaryKey());

		assetEntryAssetCategoryRelImpl.setAssetEntryAssetCategoryRelId(assetEntryAssetCategoryRel.getAssetEntryAssetCategoryRelId());
		assetEntryAssetCategoryRelImpl.setAssetEntryId(assetEntryAssetCategoryRel.getAssetEntryId());
		assetEntryAssetCategoryRelImpl.setAssetCategoryId(assetEntryAssetCategoryRel.getAssetCategoryId());
		assetEntryAssetCategoryRelImpl.setPriority(assetEntryAssetCategoryRel.getPriority());

		return assetEntryAssetCategoryRelImpl;
	}

	/**
	 * Returns the asset entry asset category rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset entry asset category rel
	 * @return the asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a asset entry asset category rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetCategoryRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryAssetCategoryRelException {
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel = fetchByPrimaryKey(primaryKey);

		if (assetEntryAssetCategoryRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryAssetCategoryRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return assetEntryAssetCategoryRel;
	}

	/**
	 * Returns the asset entry asset category rel with the primary key or throws a {@link NoSuchEntryAssetCategoryRelException} if it could not be found.
	 *
	 * @param assetEntryAssetCategoryRelId the primary key of the asset entry asset category rel
	 * @return the asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a asset entry asset category rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetCategoryRel findByPrimaryKey(
		long assetEntryAssetCategoryRelId)
		throws NoSuchEntryAssetCategoryRelException {
		return findByPrimaryKey((Serializable)assetEntryAssetCategoryRelId);
	}

	/**
	 * Returns the asset entry asset category rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset entry asset category rel
	 * @return the asset entry asset category rel, or <code>null</code> if a asset entry asset category rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetCategoryRel fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(AssetEntryAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
				AssetEntryAssetCategoryRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel = (AssetEntryAssetCategoryRel)serializable;

		if (assetEntryAssetCategoryRel == null) {
			Session session = null;

			try {
				session = openSession();

				assetEntryAssetCategoryRel = (AssetEntryAssetCategoryRel)session.get(AssetEntryAssetCategoryRelImpl.class,
						primaryKey);

				if (assetEntryAssetCategoryRel != null) {
					cacheResult(assetEntryAssetCategoryRel);
				}
				else {
					entityCache.putResult(AssetEntryAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
						AssetEntryAssetCategoryRelImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(AssetEntryAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
					AssetEntryAssetCategoryRelImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return assetEntryAssetCategoryRel;
	}

	/**
	 * Returns the asset entry asset category rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetEntryAssetCategoryRelId the primary key of the asset entry asset category rel
	 * @return the asset entry asset category rel, or <code>null</code> if a asset entry asset category rel with the primary key could not be found
	 */
	@Override
	public AssetEntryAssetCategoryRel fetchByPrimaryKey(
		long assetEntryAssetCategoryRelId) {
		return fetchByPrimaryKey((Serializable)assetEntryAssetCategoryRelId);
	}

	@Override
	public Map<Serializable, AssetEntryAssetCategoryRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetEntryAssetCategoryRel> map = new HashMap<Serializable, AssetEntryAssetCategoryRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetEntryAssetCategoryRel assetEntryAssetCategoryRel = fetchByPrimaryKey(primaryKey);

			if (assetEntryAssetCategoryRel != null) {
				map.put(primaryKey, assetEntryAssetCategoryRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(AssetEntryAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
					AssetEntryAssetCategoryRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (AssetEntryAssetCategoryRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_ASSETENTRYASSETCATEGORYREL_WHERE_PKS_IN);

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

			for (AssetEntryAssetCategoryRel assetEntryAssetCategoryRel : (List<AssetEntryAssetCategoryRel>)q.list()) {
				map.put(assetEntryAssetCategoryRel.getPrimaryKeyObj(),
					assetEntryAssetCategoryRel);

				cacheResult(assetEntryAssetCategoryRel);

				uncachedPrimaryKeys.remove(assetEntryAssetCategoryRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(AssetEntryAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
					AssetEntryAssetCategoryRelImpl.class, primaryKey, nullModel);
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
	 * Returns all the asset entry asset category rels.
	 *
	 * @return the asset entry asset category rels
	 */
	@Override
	public List<AssetEntryAssetCategoryRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry asset category rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry asset category rels
	 * @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	 * @return the range of asset entry asset category rels
	 */
	@Override
	public List<AssetEntryAssetCategoryRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry asset category rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry asset category rels
	 * @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset entry asset category rels
	 */
	@Override
	public List<AssetEntryAssetCategoryRel> findAll(int start, int end,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry asset category rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry asset category rels
	 * @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of asset entry asset category rels
	 */
	@Override
	public List<AssetEntryAssetCategoryRel> findAll(int start, int end,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator,
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

		List<AssetEntryAssetCategoryRel> list = null;

		if (retrieveFromCache) {
			list = (List<AssetEntryAssetCategoryRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ASSETENTRYASSETCATEGORYREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETENTRYASSETCATEGORYREL;

				if (pagination) {
					sql = sql.concat(AssetEntryAssetCategoryRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AssetEntryAssetCategoryRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetEntryAssetCategoryRel>)QueryUtil.list(q,
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
	 * Removes all the asset entry asset category rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetEntryAssetCategoryRel assetEntryAssetCategoryRel : findAll()) {
			remove(assetEntryAssetCategoryRel);
		}
	}

	/**
	 * Returns the number of asset entry asset category rels.
	 *
	 * @return the number of asset entry asset category rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETENTRYASSETCATEGORYREL);

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
		return AssetEntryAssetCategoryRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the asset entry asset category rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(AssetEntryAssetCategoryRelImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_ASSETENTRYASSETCATEGORYREL = "SELECT assetEntryAssetCategoryRel FROM AssetEntryAssetCategoryRel assetEntryAssetCategoryRel";
	private static final String _SQL_SELECT_ASSETENTRYASSETCATEGORYREL_WHERE_PKS_IN =
		"SELECT assetEntryAssetCategoryRel FROM AssetEntryAssetCategoryRel assetEntryAssetCategoryRel WHERE assetEntryAssetCategoryRelId IN (";
	private static final String _SQL_SELECT_ASSETENTRYASSETCATEGORYREL_WHERE = "SELECT assetEntryAssetCategoryRel FROM AssetEntryAssetCategoryRel assetEntryAssetCategoryRel WHERE ";
	private static final String _SQL_COUNT_ASSETENTRYASSETCATEGORYREL = "SELECT COUNT(assetEntryAssetCategoryRel) FROM AssetEntryAssetCategoryRel assetEntryAssetCategoryRel";
	private static final String _SQL_COUNT_ASSETENTRYASSETCATEGORYREL_WHERE = "SELECT COUNT(assetEntryAssetCategoryRel) FROM AssetEntryAssetCategoryRel assetEntryAssetCategoryRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetEntryAssetCategoryRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetEntryAssetCategoryRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetEntryAssetCategoryRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(AssetEntryAssetCategoryRelPersistenceImpl.class);
}