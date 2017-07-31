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

package com.liferay.commerce.address.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.address.exception.NoSuchRegionException;
import com.liferay.commerce.address.model.CommerceRegion;
import com.liferay.commerce.address.model.impl.CommerceRegionImpl;
import com.liferay.commerce.address.model.impl.CommerceRegionModelImpl;
import com.liferay.commerce.address.service.persistence.CommerceRegionPersistence;

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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the commerce region service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceRegionPersistence
 * @see com.liferay.commerce.address.service.persistence.CommerceRegionUtil
 * @generated
 */
@ProviderType
public class CommerceRegionPersistenceImpl extends BasePersistenceImpl<CommerceRegion>
	implements CommerceRegionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceRegionUtil} to access the commerce region persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceRegionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceRegionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceRegionModelImpl.FINDER_CACHE_ENABLED,
			CommerceRegionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceRegionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceRegionModelImpl.FINDER_CACHE_ENABLED,
			CommerceRegionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceRegionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceRegionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCECOUNTRYID =
		new FinderPath(CommerceRegionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceRegionModelImpl.FINDER_CACHE_ENABLED,
			CommerceRegionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceCountryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECOUNTRYID =
		new FinderPath(CommerceRegionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceRegionModelImpl.FINDER_CACHE_ENABLED,
			CommerceRegionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceCountryId", new String[] { Long.class.getName() },
			CommerceRegionModelImpl.COMMERCECOUNTRYID_COLUMN_BITMASK |
			CommerceRegionModelImpl.NAME_COLUMN_BITMASK |
			CommerceRegionModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCECOUNTRYID = new FinderPath(CommerceRegionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceRegionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceCountryId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce regions where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @return the matching commerce regions
	 */
	@Override
	public List<CommerceRegion> findByCommerceCountryId(long commerceCountryId) {
		return findByCommerceCountryId(commerceCountryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce regions where commerceCountryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param start the lower bound of the range of commerce regions
	 * @param end the upper bound of the range of commerce regions (not inclusive)
	 * @return the range of matching commerce regions
	 */
	@Override
	public List<CommerceRegion> findByCommerceCountryId(
		long commerceCountryId, int start, int end) {
		return findByCommerceCountryId(commerceCountryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce regions where commerceCountryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param start the lower bound of the range of commerce regions
	 * @param end the upper bound of the range of commerce regions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce regions
	 */
	@Override
	public List<CommerceRegion> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator) {
		return findByCommerceCountryId(commerceCountryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce regions where commerceCountryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param start the lower bound of the range of commerce regions
	 * @param end the upper bound of the range of commerce regions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce regions
	 */
	@Override
	public List<CommerceRegion> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECOUNTRYID;
			finderArgs = new Object[] { commerceCountryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCECOUNTRYID;
			finderArgs = new Object[] {
					commerceCountryId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceRegion> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceRegion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceRegion commerceRegion : list) {
					if ((commerceCountryId != commerceRegion.getCommerceCountryId())) {
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

			query.append(_SQL_SELECT_COMMERCEREGION_WHERE);

			query.append(_FINDER_COLUMN_COMMERCECOUNTRYID_COMMERCECOUNTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceRegionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceCountryId);

				if (!pagination) {
					list = (List<CommerceRegion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceRegion>)QueryUtil.list(q,
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
	 * Returns the first commerce region in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce region
	 * @throws NoSuchRegionException if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion findByCommerceCountryId_First(
		long commerceCountryId,
		OrderByComparator<CommerceRegion> orderByComparator)
		throws NoSuchRegionException {
		CommerceRegion commerceRegion = fetchByCommerceCountryId_First(commerceCountryId,
				orderByComparator);

		if (commerceRegion != null) {
			return commerceRegion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceCountryId=");
		msg.append(commerceCountryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRegionException(msg.toString());
	}

	/**
	 * Returns the first commerce region in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce region, or <code>null</code> if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion fetchByCommerceCountryId_First(
		long commerceCountryId,
		OrderByComparator<CommerceRegion> orderByComparator) {
		List<CommerceRegion> list = findByCommerceCountryId(commerceCountryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce region in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce region
	 * @throws NoSuchRegionException if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion findByCommerceCountryId_Last(long commerceCountryId,
		OrderByComparator<CommerceRegion> orderByComparator)
		throws NoSuchRegionException {
		CommerceRegion commerceRegion = fetchByCommerceCountryId_Last(commerceCountryId,
				orderByComparator);

		if (commerceRegion != null) {
			return commerceRegion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceCountryId=");
		msg.append(commerceCountryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRegionException(msg.toString());
	}

	/**
	 * Returns the last commerce region in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce region, or <code>null</code> if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion fetchByCommerceCountryId_Last(
		long commerceCountryId,
		OrderByComparator<CommerceRegion> orderByComparator) {
		int count = countByCommerceCountryId(commerceCountryId);

		if (count == 0) {
			return null;
		}

		List<CommerceRegion> list = findByCommerceCountryId(commerceCountryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce regions before and after the current commerce region in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceRegionId the primary key of the current commerce region
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce region
	 * @throws NoSuchRegionException if a commerce region with the primary key could not be found
	 */
	@Override
	public CommerceRegion[] findByCommerceCountryId_PrevAndNext(
		long commerceRegionId, long commerceCountryId,
		OrderByComparator<CommerceRegion> orderByComparator)
		throws NoSuchRegionException {
		CommerceRegion commerceRegion = findByPrimaryKey(commerceRegionId);

		Session session = null;

		try {
			session = openSession();

			CommerceRegion[] array = new CommerceRegionImpl[3];

			array[0] = getByCommerceCountryId_PrevAndNext(session,
					commerceRegion, commerceCountryId, orderByComparator, true);

			array[1] = commerceRegion;

			array[2] = getByCommerceCountryId_PrevAndNext(session,
					commerceRegion, commerceCountryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceRegion getByCommerceCountryId_PrevAndNext(
		Session session, CommerceRegion commerceRegion, long commerceCountryId,
		OrderByComparator<CommerceRegion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCEREGION_WHERE);

		query.append(_FINDER_COLUMN_COMMERCECOUNTRYID_COMMERCECOUNTRYID_2);

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
			query.append(CommerceRegionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceCountryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceRegion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceRegion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce regions where commerceCountryId = &#63; from the database.
	 *
	 * @param commerceCountryId the commerce country ID
	 */
	@Override
	public void removeByCommerceCountryId(long commerceCountryId) {
		for (CommerceRegion commerceRegion : findByCommerceCountryId(
				commerceCountryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceRegion);
		}
	}

	/**
	 * Returns the number of commerce regions where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @return the number of matching commerce regions
	 */
	@Override
	public int countByCommerceCountryId(long commerceCountryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCECOUNTRYID;

		Object[] finderArgs = new Object[] { commerceCountryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEREGION_WHERE);

			query.append(_FINDER_COLUMN_COMMERCECOUNTRYID_COMMERCECOUNTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceCountryId);

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

	private static final String _FINDER_COLUMN_COMMERCECOUNTRYID_COMMERCECOUNTRYID_2 =
		"commerceRegion.commerceCountryId = ?";

	public CommerceRegionPersistenceImpl() {
		setModelClass(CommerceRegion.class);
	}

	/**
	 * Caches the commerce region in the entity cache if it is enabled.
	 *
	 * @param commerceRegion the commerce region
	 */
	@Override
	public void cacheResult(CommerceRegion commerceRegion) {
		entityCache.putResult(CommerceRegionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceRegionImpl.class, commerceRegion.getPrimaryKey(),
			commerceRegion);

		commerceRegion.resetOriginalValues();
	}

	/**
	 * Caches the commerce regions in the entity cache if it is enabled.
	 *
	 * @param commerceRegions the commerce regions
	 */
	@Override
	public void cacheResult(List<CommerceRegion> commerceRegions) {
		for (CommerceRegion commerceRegion : commerceRegions) {
			if (entityCache.getResult(
						CommerceRegionModelImpl.ENTITY_CACHE_ENABLED,
						CommerceRegionImpl.class, commerceRegion.getPrimaryKey()) == null) {
				cacheResult(commerceRegion);
			}
			else {
				commerceRegion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce regions.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceRegionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce region.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceRegion commerceRegion) {
		entityCache.removeResult(CommerceRegionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceRegionImpl.class, commerceRegion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CommerceRegion> commerceRegions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceRegion commerceRegion : commerceRegions) {
			entityCache.removeResult(CommerceRegionModelImpl.ENTITY_CACHE_ENABLED,
				CommerceRegionImpl.class, commerceRegion.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce region with the primary key. Does not add the commerce region to the database.
	 *
	 * @param commerceRegionId the primary key for the new commerce region
	 * @return the new commerce region
	 */
	@Override
	public CommerceRegion create(long commerceRegionId) {
		CommerceRegion commerceRegion = new CommerceRegionImpl();

		commerceRegion.setNew(true);
		commerceRegion.setPrimaryKey(commerceRegionId);

		commerceRegion.setCompanyId(companyProvider.getCompanyId());

		return commerceRegion;
	}

	/**
	 * Removes the commerce region with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceRegionId the primary key of the commerce region
	 * @return the commerce region that was removed
	 * @throws NoSuchRegionException if a commerce region with the primary key could not be found
	 */
	@Override
	public CommerceRegion remove(long commerceRegionId)
		throws NoSuchRegionException {
		return remove((Serializable)commerceRegionId);
	}

	/**
	 * Removes the commerce region with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce region
	 * @return the commerce region that was removed
	 * @throws NoSuchRegionException if a commerce region with the primary key could not be found
	 */
	@Override
	public CommerceRegion remove(Serializable primaryKey)
		throws NoSuchRegionException {
		Session session = null;

		try {
			session = openSession();

			CommerceRegion commerceRegion = (CommerceRegion)session.get(CommerceRegionImpl.class,
					primaryKey);

			if (commerceRegion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRegionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceRegion);
		}
		catch (NoSuchRegionException nsee) {
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
	protected CommerceRegion removeImpl(CommerceRegion commerceRegion) {
		commerceRegion = toUnwrappedModel(commerceRegion);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceRegion)) {
				commerceRegion = (CommerceRegion)session.get(CommerceRegionImpl.class,
						commerceRegion.getPrimaryKeyObj());
			}

			if (commerceRegion != null) {
				session.delete(commerceRegion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceRegion != null) {
			clearCache(commerceRegion);
		}

		return commerceRegion;
	}

	@Override
	public CommerceRegion updateImpl(CommerceRegion commerceRegion) {
		commerceRegion = toUnwrappedModel(commerceRegion);

		boolean isNew = commerceRegion.isNew();

		CommerceRegionModelImpl commerceRegionModelImpl = (CommerceRegionModelImpl)commerceRegion;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceRegion.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceRegion.setCreateDate(now);
			}
			else {
				commerceRegion.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!commerceRegionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceRegion.setModifiedDate(now);
			}
			else {
				commerceRegion.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceRegion.isNew()) {
				session.save(commerceRegion);

				commerceRegion.setNew(false);
			}
			else {
				commerceRegion = (CommerceRegion)session.merge(commerceRegion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceRegionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceRegionModelImpl.getCommerceCountryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCECOUNTRYID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECOUNTRYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceRegionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECOUNTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceRegionModelImpl.getOriginalCommerceCountryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCECOUNTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECOUNTRYID,
					args);

				args = new Object[] {
						commerceRegionModelImpl.getCommerceCountryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCECOUNTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECOUNTRYID,
					args);
			}
		}

		entityCache.putResult(CommerceRegionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceRegionImpl.class, commerceRegion.getPrimaryKey(),
			commerceRegion, false);

		commerceRegion.resetOriginalValues();

		return commerceRegion;
	}

	protected CommerceRegion toUnwrappedModel(CommerceRegion commerceRegion) {
		if (commerceRegion instanceof CommerceRegionImpl) {
			return commerceRegion;
		}

		CommerceRegionImpl commerceRegionImpl = new CommerceRegionImpl();

		commerceRegionImpl.setNew(commerceRegion.isNew());
		commerceRegionImpl.setPrimaryKey(commerceRegion.getPrimaryKey());

		commerceRegionImpl.setCommerceRegionId(commerceRegion.getCommerceRegionId());
		commerceRegionImpl.setGroupId(commerceRegion.getGroupId());
		commerceRegionImpl.setCompanyId(commerceRegion.getCompanyId());
		commerceRegionImpl.setUserId(commerceRegion.getUserId());
		commerceRegionImpl.setUserName(commerceRegion.getUserName());
		commerceRegionImpl.setCreateDate(commerceRegion.getCreateDate());
		commerceRegionImpl.setModifiedDate(commerceRegion.getModifiedDate());
		commerceRegionImpl.setCommerceCountryId(commerceRegion.getCommerceCountryId());
		commerceRegionImpl.setName(commerceRegion.getName());
		commerceRegionImpl.setAbbreviation(commerceRegion.getAbbreviation());
		commerceRegionImpl.setPriority(commerceRegion.getPriority());
		commerceRegionImpl.setPublished(commerceRegion.isPublished());

		return commerceRegionImpl;
	}

	/**
	 * Returns the commerce region with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce region
	 * @return the commerce region
	 * @throws NoSuchRegionException if a commerce region with the primary key could not be found
	 */
	@Override
	public CommerceRegion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRegionException {
		CommerceRegion commerceRegion = fetchByPrimaryKey(primaryKey);

		if (commerceRegion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRegionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceRegion;
	}

	/**
	 * Returns the commerce region with the primary key or throws a {@link NoSuchRegionException} if it could not be found.
	 *
	 * @param commerceRegionId the primary key of the commerce region
	 * @return the commerce region
	 * @throws NoSuchRegionException if a commerce region with the primary key could not be found
	 */
	@Override
	public CommerceRegion findByPrimaryKey(long commerceRegionId)
		throws NoSuchRegionException {
		return findByPrimaryKey((Serializable)commerceRegionId);
	}

	/**
	 * Returns the commerce region with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce region
	 * @return the commerce region, or <code>null</code> if a commerce region with the primary key could not be found
	 */
	@Override
	public CommerceRegion fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceRegionModelImpl.ENTITY_CACHE_ENABLED,
				CommerceRegionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceRegion commerceRegion = (CommerceRegion)serializable;

		if (commerceRegion == null) {
			Session session = null;

			try {
				session = openSession();

				commerceRegion = (CommerceRegion)session.get(CommerceRegionImpl.class,
						primaryKey);

				if (commerceRegion != null) {
					cacheResult(commerceRegion);
				}
				else {
					entityCache.putResult(CommerceRegionModelImpl.ENTITY_CACHE_ENABLED,
						CommerceRegionImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceRegionModelImpl.ENTITY_CACHE_ENABLED,
					CommerceRegionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceRegion;
	}

	/**
	 * Returns the commerce region with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceRegionId the primary key of the commerce region
	 * @return the commerce region, or <code>null</code> if a commerce region with the primary key could not be found
	 */
	@Override
	public CommerceRegion fetchByPrimaryKey(long commerceRegionId) {
		return fetchByPrimaryKey((Serializable)commerceRegionId);
	}

	@Override
	public Map<Serializable, CommerceRegion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceRegion> map = new HashMap<Serializable, CommerceRegion>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceRegion commerceRegion = fetchByPrimaryKey(primaryKey);

			if (commerceRegion != null) {
				map.put(primaryKey, commerceRegion);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceRegionModelImpl.ENTITY_CACHE_ENABLED,
					CommerceRegionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceRegion)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEREGION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (CommerceRegion commerceRegion : (List<CommerceRegion>)q.list()) {
				map.put(commerceRegion.getPrimaryKeyObj(), commerceRegion);

				cacheResult(commerceRegion);

				uncachedPrimaryKeys.remove(commerceRegion.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceRegionModelImpl.ENTITY_CACHE_ENABLED,
					CommerceRegionImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce regions.
	 *
	 * @return the commerce regions
	 */
	@Override
	public List<CommerceRegion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce regions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce regions
	 * @param end the upper bound of the range of commerce regions (not inclusive)
	 * @return the range of commerce regions
	 */
	@Override
	public List<CommerceRegion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce regions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce regions
	 * @param end the upper bound of the range of commerce regions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce regions
	 */
	@Override
	public List<CommerceRegion> findAll(int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce regions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce regions
	 * @param end the upper bound of the range of commerce regions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce regions
	 */
	@Override
	public List<CommerceRegion> findAll(int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator,
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

		List<CommerceRegion> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceRegion>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEREGION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEREGION;

				if (pagination) {
					sql = sql.concat(CommerceRegionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceRegion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceRegion>)QueryUtil.list(q,
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
	 * Removes all the commerce regions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceRegion commerceRegion : findAll()) {
			remove(commerceRegion);
		}
	}

	/**
	 * Returns the number of commerce regions.
	 *
	 * @return the number of commerce regions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEREGION);

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
		return CommerceRegionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce region persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceRegionImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEREGION = "SELECT commerceRegion FROM CommerceRegion commerceRegion";
	private static final String _SQL_SELECT_COMMERCEREGION_WHERE_PKS_IN = "SELECT commerceRegion FROM CommerceRegion commerceRegion WHERE commerceRegionId IN (";
	private static final String _SQL_SELECT_COMMERCEREGION_WHERE = "SELECT commerceRegion FROM CommerceRegion commerceRegion WHERE ";
	private static final String _SQL_COUNT_COMMERCEREGION = "SELECT COUNT(commerceRegion) FROM CommerceRegion commerceRegion";
	private static final String _SQL_COUNT_COMMERCEREGION_WHERE = "SELECT COUNT(commerceRegion) FROM CommerceRegion commerceRegion WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceRegion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceRegion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceRegion exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceRegionPersistenceImpl.class);
}