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

package com.liferay.commerce.product.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.exception.NoSuchCPRuleAssetCategoryRelException;
import com.liferay.commerce.product.model.CPRuleAssetCategoryRel;
import com.liferay.commerce.product.model.impl.CPRuleAssetCategoryRelImpl;
import com.liferay.commerce.product.model.impl.CPRuleAssetCategoryRelModelImpl;
import com.liferay.commerce.product.service.persistence.CPRuleAssetCategoryRelPersistence;

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
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the cp rule asset category rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPRuleAssetCategoryRelPersistence
 * @see com.liferay.commerce.product.service.persistence.CPRuleAssetCategoryRelUtil
 * @generated
 */
@ProviderType
public class CPRuleAssetCategoryRelPersistenceImpl extends BasePersistenceImpl<CPRuleAssetCategoryRel>
	implements CPRuleAssetCategoryRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CPRuleAssetCategoryRelUtil} to access the cp rule asset category rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CPRuleAssetCategoryRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CPRuleAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleAssetCategoryRelModelImpl.FINDER_CACHE_ENABLED,
			CPRuleAssetCategoryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CPRuleAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleAssetCategoryRelModelImpl.FINDER_CACHE_ENABLED,
			CPRuleAssetCategoryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CPRuleAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleAssetCategoryRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CPRULEID = new FinderPath(CPRuleAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleAssetCategoryRelModelImpl.FINDER_CACHE_ENABLED,
			CPRuleAssetCategoryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPRuleId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPRULEID =
		new FinderPath(CPRuleAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleAssetCategoryRelModelImpl.FINDER_CACHE_ENABLED,
			CPRuleAssetCategoryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPRuleId",
			new String[] { Long.class.getName() },
			CPRuleAssetCategoryRelModelImpl.CPRULEID_COLUMN_BITMASK |
			CPRuleAssetCategoryRelModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CPRULEID = new FinderPath(CPRuleAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleAssetCategoryRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPRuleId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the cp rule asset category rels where CPRuleId = &#63;.
	 *
	 * @param CPRuleId the cp rule ID
	 * @return the matching cp rule asset category rels
	 */
	@Override
	public List<CPRuleAssetCategoryRel> findByCPRuleId(long CPRuleId) {
		return findByCPRuleId(CPRuleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp rule asset category rels where CPRuleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPRuleId the cp rule ID
	 * @param start the lower bound of the range of cp rule asset category rels
	 * @param end the upper bound of the range of cp rule asset category rels (not inclusive)
	 * @return the range of matching cp rule asset category rels
	 */
	@Override
	public List<CPRuleAssetCategoryRel> findByCPRuleId(long CPRuleId,
		int start, int end) {
		return findByCPRuleId(CPRuleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp rule asset category rels where CPRuleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPRuleId the cp rule ID
	 * @param start the lower bound of the range of cp rule asset category rels
	 * @param end the upper bound of the range of cp rule asset category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp rule asset category rels
	 */
	@Override
	public List<CPRuleAssetCategoryRel> findByCPRuleId(long CPRuleId,
		int start, int end,
		OrderByComparator<CPRuleAssetCategoryRel> orderByComparator) {
		return findByCPRuleId(CPRuleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp rule asset category rels where CPRuleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPRuleId the cp rule ID
	 * @param start the lower bound of the range of cp rule asset category rels
	 * @param end the upper bound of the range of cp rule asset category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp rule asset category rels
	 */
	@Override
	public List<CPRuleAssetCategoryRel> findByCPRuleId(long CPRuleId,
		int start, int end,
		OrderByComparator<CPRuleAssetCategoryRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPRULEID;
			finderArgs = new Object[] { CPRuleId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CPRULEID;
			finderArgs = new Object[] { CPRuleId, start, end, orderByComparator };
		}

		List<CPRuleAssetCategoryRel> list = null;

		if (retrieveFromCache) {
			list = (List<CPRuleAssetCategoryRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPRuleAssetCategoryRel cpRuleAssetCategoryRel : list) {
					if ((CPRuleId != cpRuleAssetCategoryRel.getCPRuleId())) {
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

			query.append(_SQL_SELECT_CPRULEASSETCATEGORYREL_WHERE);

			query.append(_FINDER_COLUMN_CPRULEID_CPRULEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPRuleAssetCategoryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPRuleId);

				if (!pagination) {
					list = (List<CPRuleAssetCategoryRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPRuleAssetCategoryRel>)QueryUtil.list(q,
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
	 * Returns the first cp rule asset category rel in the ordered set where CPRuleId = &#63;.
	 *
	 * @param CPRuleId the cp rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp rule asset category rel
	 * @throws NoSuchCPRuleAssetCategoryRelException if a matching cp rule asset category rel could not be found
	 */
	@Override
	public CPRuleAssetCategoryRel findByCPRuleId_First(long CPRuleId,
		OrderByComparator<CPRuleAssetCategoryRel> orderByComparator)
		throws NoSuchCPRuleAssetCategoryRelException {
		CPRuleAssetCategoryRel cpRuleAssetCategoryRel = fetchByCPRuleId_First(CPRuleId,
				orderByComparator);

		if (cpRuleAssetCategoryRel != null) {
			return cpRuleAssetCategoryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPRuleId=");
		msg.append(CPRuleId);

		msg.append("}");

		throw new NoSuchCPRuleAssetCategoryRelException(msg.toString());
	}

	/**
	 * Returns the first cp rule asset category rel in the ordered set where CPRuleId = &#63;.
	 *
	 * @param CPRuleId the cp rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp rule asset category rel, or <code>null</code> if a matching cp rule asset category rel could not be found
	 */
	@Override
	public CPRuleAssetCategoryRel fetchByCPRuleId_First(long CPRuleId,
		OrderByComparator<CPRuleAssetCategoryRel> orderByComparator) {
		List<CPRuleAssetCategoryRel> list = findByCPRuleId(CPRuleId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp rule asset category rel in the ordered set where CPRuleId = &#63;.
	 *
	 * @param CPRuleId the cp rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp rule asset category rel
	 * @throws NoSuchCPRuleAssetCategoryRelException if a matching cp rule asset category rel could not be found
	 */
	@Override
	public CPRuleAssetCategoryRel findByCPRuleId_Last(long CPRuleId,
		OrderByComparator<CPRuleAssetCategoryRel> orderByComparator)
		throws NoSuchCPRuleAssetCategoryRelException {
		CPRuleAssetCategoryRel cpRuleAssetCategoryRel = fetchByCPRuleId_Last(CPRuleId,
				orderByComparator);

		if (cpRuleAssetCategoryRel != null) {
			return cpRuleAssetCategoryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPRuleId=");
		msg.append(CPRuleId);

		msg.append("}");

		throw new NoSuchCPRuleAssetCategoryRelException(msg.toString());
	}

	/**
	 * Returns the last cp rule asset category rel in the ordered set where CPRuleId = &#63;.
	 *
	 * @param CPRuleId the cp rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp rule asset category rel, or <code>null</code> if a matching cp rule asset category rel could not be found
	 */
	@Override
	public CPRuleAssetCategoryRel fetchByCPRuleId_Last(long CPRuleId,
		OrderByComparator<CPRuleAssetCategoryRel> orderByComparator) {
		int count = countByCPRuleId(CPRuleId);

		if (count == 0) {
			return null;
		}

		List<CPRuleAssetCategoryRel> list = findByCPRuleId(CPRuleId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp rule asset category rels before and after the current cp rule asset category rel in the ordered set where CPRuleId = &#63;.
	 *
	 * @param CPRuleAssetCategoryRelId the primary key of the current cp rule asset category rel
	 * @param CPRuleId the cp rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp rule asset category rel
	 * @throws NoSuchCPRuleAssetCategoryRelException if a cp rule asset category rel with the primary key could not be found
	 */
	@Override
	public CPRuleAssetCategoryRel[] findByCPRuleId_PrevAndNext(
		long CPRuleAssetCategoryRelId, long CPRuleId,
		OrderByComparator<CPRuleAssetCategoryRel> orderByComparator)
		throws NoSuchCPRuleAssetCategoryRelException {
		CPRuleAssetCategoryRel cpRuleAssetCategoryRel = findByPrimaryKey(CPRuleAssetCategoryRelId);

		Session session = null;

		try {
			session = openSession();

			CPRuleAssetCategoryRel[] array = new CPRuleAssetCategoryRelImpl[3];

			array[0] = getByCPRuleId_PrevAndNext(session,
					cpRuleAssetCategoryRel, CPRuleId, orderByComparator, true);

			array[1] = cpRuleAssetCategoryRel;

			array[2] = getByCPRuleId_PrevAndNext(session,
					cpRuleAssetCategoryRel, CPRuleId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPRuleAssetCategoryRel getByCPRuleId_PrevAndNext(
		Session session, CPRuleAssetCategoryRel cpRuleAssetCategoryRel,
		long CPRuleId,
		OrderByComparator<CPRuleAssetCategoryRel> orderByComparator,
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

		query.append(_SQL_SELECT_CPRULEASSETCATEGORYREL_WHERE);

		query.append(_FINDER_COLUMN_CPRULEID_CPRULEID_2);

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
			query.append(CPRuleAssetCategoryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(CPRuleId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpRuleAssetCategoryRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPRuleAssetCategoryRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp rule asset category rels where CPRuleId = &#63; from the database.
	 *
	 * @param CPRuleId the cp rule ID
	 */
	@Override
	public void removeByCPRuleId(long CPRuleId) {
		for (CPRuleAssetCategoryRel cpRuleAssetCategoryRel : findByCPRuleId(
				CPRuleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpRuleAssetCategoryRel);
		}
	}

	/**
	 * Returns the number of cp rule asset category rels where CPRuleId = &#63;.
	 *
	 * @param CPRuleId the cp rule ID
	 * @return the number of matching cp rule asset category rels
	 */
	@Override
	public int countByCPRuleId(long CPRuleId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CPRULEID;

		Object[] finderArgs = new Object[] { CPRuleId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPRULEASSETCATEGORYREL_WHERE);

			query.append(_FINDER_COLUMN_CPRULEID_CPRULEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPRuleId);

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

	private static final String _FINDER_COLUMN_CPRULEID_CPRULEID_2 = "cpRuleAssetCategoryRel.CPRuleId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ASSETCATEGORYID =
		new FinderPath(CPRuleAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleAssetCategoryRelModelImpl.FINDER_CACHE_ENABLED,
			CPRuleAssetCategoryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAssetCategoryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETCATEGORYID =
		new FinderPath(CPRuleAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleAssetCategoryRelModelImpl.FINDER_CACHE_ENABLED,
			CPRuleAssetCategoryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAssetCategoryId",
			new String[] { Long.class.getName() },
			CPRuleAssetCategoryRelModelImpl.ASSETCATEGORYID_COLUMN_BITMASK |
			CPRuleAssetCategoryRelModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ASSETCATEGORYID = new FinderPath(CPRuleAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleAssetCategoryRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAssetCategoryId", new String[] { Long.class.getName() });

	/**
	 * Returns all the cp rule asset category rels where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @return the matching cp rule asset category rels
	 */
	@Override
	public List<CPRuleAssetCategoryRel> findByAssetCategoryId(
		long assetCategoryId) {
		return findByAssetCategoryId(assetCategoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp rule asset category rels where assetCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetCategoryId the asset category ID
	 * @param start the lower bound of the range of cp rule asset category rels
	 * @param end the upper bound of the range of cp rule asset category rels (not inclusive)
	 * @return the range of matching cp rule asset category rels
	 */
	@Override
	public List<CPRuleAssetCategoryRel> findByAssetCategoryId(
		long assetCategoryId, int start, int end) {
		return findByAssetCategoryId(assetCategoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp rule asset category rels where assetCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetCategoryId the asset category ID
	 * @param start the lower bound of the range of cp rule asset category rels
	 * @param end the upper bound of the range of cp rule asset category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp rule asset category rels
	 */
	@Override
	public List<CPRuleAssetCategoryRel> findByAssetCategoryId(
		long assetCategoryId, int start, int end,
		OrderByComparator<CPRuleAssetCategoryRel> orderByComparator) {
		return findByAssetCategoryId(assetCategoryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp rule asset category rels where assetCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetCategoryId the asset category ID
	 * @param start the lower bound of the range of cp rule asset category rels
	 * @param end the upper bound of the range of cp rule asset category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp rule asset category rels
	 */
	@Override
	public List<CPRuleAssetCategoryRel> findByAssetCategoryId(
		long assetCategoryId, int start, int end,
		OrderByComparator<CPRuleAssetCategoryRel> orderByComparator,
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

		List<CPRuleAssetCategoryRel> list = null;

		if (retrieveFromCache) {
			list = (List<CPRuleAssetCategoryRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPRuleAssetCategoryRel cpRuleAssetCategoryRel : list) {
					if ((assetCategoryId != cpRuleAssetCategoryRel.getAssetCategoryId())) {
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

			query.append(_SQL_SELECT_CPRULEASSETCATEGORYREL_WHERE);

			query.append(_FINDER_COLUMN_ASSETCATEGORYID_ASSETCATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPRuleAssetCategoryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetCategoryId);

				if (!pagination) {
					list = (List<CPRuleAssetCategoryRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPRuleAssetCategoryRel>)QueryUtil.list(q,
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
	 * Returns the first cp rule asset category rel in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp rule asset category rel
	 * @throws NoSuchCPRuleAssetCategoryRelException if a matching cp rule asset category rel could not be found
	 */
	@Override
	public CPRuleAssetCategoryRel findByAssetCategoryId_First(
		long assetCategoryId,
		OrderByComparator<CPRuleAssetCategoryRel> orderByComparator)
		throws NoSuchCPRuleAssetCategoryRelException {
		CPRuleAssetCategoryRel cpRuleAssetCategoryRel = fetchByAssetCategoryId_First(assetCategoryId,
				orderByComparator);

		if (cpRuleAssetCategoryRel != null) {
			return cpRuleAssetCategoryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetCategoryId=");
		msg.append(assetCategoryId);

		msg.append("}");

		throw new NoSuchCPRuleAssetCategoryRelException(msg.toString());
	}

	/**
	 * Returns the first cp rule asset category rel in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp rule asset category rel, or <code>null</code> if a matching cp rule asset category rel could not be found
	 */
	@Override
	public CPRuleAssetCategoryRel fetchByAssetCategoryId_First(
		long assetCategoryId,
		OrderByComparator<CPRuleAssetCategoryRel> orderByComparator) {
		List<CPRuleAssetCategoryRel> list = findByAssetCategoryId(assetCategoryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp rule asset category rel in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp rule asset category rel
	 * @throws NoSuchCPRuleAssetCategoryRelException if a matching cp rule asset category rel could not be found
	 */
	@Override
	public CPRuleAssetCategoryRel findByAssetCategoryId_Last(
		long assetCategoryId,
		OrderByComparator<CPRuleAssetCategoryRel> orderByComparator)
		throws NoSuchCPRuleAssetCategoryRelException {
		CPRuleAssetCategoryRel cpRuleAssetCategoryRel = fetchByAssetCategoryId_Last(assetCategoryId,
				orderByComparator);

		if (cpRuleAssetCategoryRel != null) {
			return cpRuleAssetCategoryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("assetCategoryId=");
		msg.append(assetCategoryId);

		msg.append("}");

		throw new NoSuchCPRuleAssetCategoryRelException(msg.toString());
	}

	/**
	 * Returns the last cp rule asset category rel in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp rule asset category rel, or <code>null</code> if a matching cp rule asset category rel could not be found
	 */
	@Override
	public CPRuleAssetCategoryRel fetchByAssetCategoryId_Last(
		long assetCategoryId,
		OrderByComparator<CPRuleAssetCategoryRel> orderByComparator) {
		int count = countByAssetCategoryId(assetCategoryId);

		if (count == 0) {
			return null;
		}

		List<CPRuleAssetCategoryRel> list = findByAssetCategoryId(assetCategoryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp rule asset category rels before and after the current cp rule asset category rel in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param CPRuleAssetCategoryRelId the primary key of the current cp rule asset category rel
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp rule asset category rel
	 * @throws NoSuchCPRuleAssetCategoryRelException if a cp rule asset category rel with the primary key could not be found
	 */
	@Override
	public CPRuleAssetCategoryRel[] findByAssetCategoryId_PrevAndNext(
		long CPRuleAssetCategoryRelId, long assetCategoryId,
		OrderByComparator<CPRuleAssetCategoryRel> orderByComparator)
		throws NoSuchCPRuleAssetCategoryRelException {
		CPRuleAssetCategoryRel cpRuleAssetCategoryRel = findByPrimaryKey(CPRuleAssetCategoryRelId);

		Session session = null;

		try {
			session = openSession();

			CPRuleAssetCategoryRel[] array = new CPRuleAssetCategoryRelImpl[3];

			array[0] = getByAssetCategoryId_PrevAndNext(session,
					cpRuleAssetCategoryRel, assetCategoryId, orderByComparator,
					true);

			array[1] = cpRuleAssetCategoryRel;

			array[2] = getByAssetCategoryId_PrevAndNext(session,
					cpRuleAssetCategoryRel, assetCategoryId, orderByComparator,
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

	protected CPRuleAssetCategoryRel getByAssetCategoryId_PrevAndNext(
		Session session, CPRuleAssetCategoryRel cpRuleAssetCategoryRel,
		long assetCategoryId,
		OrderByComparator<CPRuleAssetCategoryRel> orderByComparator,
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

		query.append(_SQL_SELECT_CPRULEASSETCATEGORYREL_WHERE);

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
			query.append(CPRuleAssetCategoryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(assetCategoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpRuleAssetCategoryRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPRuleAssetCategoryRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp rule asset category rels where assetCategoryId = &#63; from the database.
	 *
	 * @param assetCategoryId the asset category ID
	 */
	@Override
	public void removeByAssetCategoryId(long assetCategoryId) {
		for (CPRuleAssetCategoryRel cpRuleAssetCategoryRel : findByAssetCategoryId(
				assetCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpRuleAssetCategoryRel);
		}
	}

	/**
	 * Returns the number of cp rule asset category rels where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @return the number of matching cp rule asset category rels
	 */
	@Override
	public int countByAssetCategoryId(long assetCategoryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ASSETCATEGORYID;

		Object[] finderArgs = new Object[] { assetCategoryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPRULEASSETCATEGORYREL_WHERE);

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
		"cpRuleAssetCategoryRel.assetCategoryId = ?";

	public CPRuleAssetCategoryRelPersistenceImpl() {
		setModelClass(CPRuleAssetCategoryRel.class);
	}

	/**
	 * Caches the cp rule asset category rel in the entity cache if it is enabled.
	 *
	 * @param cpRuleAssetCategoryRel the cp rule asset category rel
	 */
	@Override
	public void cacheResult(CPRuleAssetCategoryRel cpRuleAssetCategoryRel) {
		entityCache.putResult(CPRuleAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleAssetCategoryRelImpl.class,
			cpRuleAssetCategoryRel.getPrimaryKey(), cpRuleAssetCategoryRel);

		cpRuleAssetCategoryRel.resetOriginalValues();
	}

	/**
	 * Caches the cp rule asset category rels in the entity cache if it is enabled.
	 *
	 * @param cpRuleAssetCategoryRels the cp rule asset category rels
	 */
	@Override
	public void cacheResult(
		List<CPRuleAssetCategoryRel> cpRuleAssetCategoryRels) {
		for (CPRuleAssetCategoryRel cpRuleAssetCategoryRel : cpRuleAssetCategoryRels) {
			if (entityCache.getResult(
						CPRuleAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
						CPRuleAssetCategoryRelImpl.class,
						cpRuleAssetCategoryRel.getPrimaryKey()) == null) {
				cacheResult(cpRuleAssetCategoryRel);
			}
			else {
				cpRuleAssetCategoryRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cp rule asset category rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPRuleAssetCategoryRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cp rule asset category rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPRuleAssetCategoryRel cpRuleAssetCategoryRel) {
		entityCache.removeResult(CPRuleAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleAssetCategoryRelImpl.class,
			cpRuleAssetCategoryRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CPRuleAssetCategoryRel> cpRuleAssetCategoryRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CPRuleAssetCategoryRel cpRuleAssetCategoryRel : cpRuleAssetCategoryRels) {
			entityCache.removeResult(CPRuleAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
				CPRuleAssetCategoryRelImpl.class,
				cpRuleAssetCategoryRel.getPrimaryKey());
		}
	}

	/**
	 * Creates a new cp rule asset category rel with the primary key. Does not add the cp rule asset category rel to the database.
	 *
	 * @param CPRuleAssetCategoryRelId the primary key for the new cp rule asset category rel
	 * @return the new cp rule asset category rel
	 */
	@Override
	public CPRuleAssetCategoryRel create(long CPRuleAssetCategoryRelId) {
		CPRuleAssetCategoryRel cpRuleAssetCategoryRel = new CPRuleAssetCategoryRelImpl();

		cpRuleAssetCategoryRel.setNew(true);
		cpRuleAssetCategoryRel.setPrimaryKey(CPRuleAssetCategoryRelId);

		cpRuleAssetCategoryRel.setCompanyId(companyProvider.getCompanyId());

		return cpRuleAssetCategoryRel;
	}

	/**
	 * Removes the cp rule asset category rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPRuleAssetCategoryRelId the primary key of the cp rule asset category rel
	 * @return the cp rule asset category rel that was removed
	 * @throws NoSuchCPRuleAssetCategoryRelException if a cp rule asset category rel with the primary key could not be found
	 */
	@Override
	public CPRuleAssetCategoryRel remove(long CPRuleAssetCategoryRelId)
		throws NoSuchCPRuleAssetCategoryRelException {
		return remove((Serializable)CPRuleAssetCategoryRelId);
	}

	/**
	 * Removes the cp rule asset category rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp rule asset category rel
	 * @return the cp rule asset category rel that was removed
	 * @throws NoSuchCPRuleAssetCategoryRelException if a cp rule asset category rel with the primary key could not be found
	 */
	@Override
	public CPRuleAssetCategoryRel remove(Serializable primaryKey)
		throws NoSuchCPRuleAssetCategoryRelException {
		Session session = null;

		try {
			session = openSession();

			CPRuleAssetCategoryRel cpRuleAssetCategoryRel = (CPRuleAssetCategoryRel)session.get(CPRuleAssetCategoryRelImpl.class,
					primaryKey);

			if (cpRuleAssetCategoryRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPRuleAssetCategoryRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cpRuleAssetCategoryRel);
		}
		catch (NoSuchCPRuleAssetCategoryRelException nsee) {
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
	protected CPRuleAssetCategoryRel removeImpl(
		CPRuleAssetCategoryRel cpRuleAssetCategoryRel) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpRuleAssetCategoryRel)) {
				cpRuleAssetCategoryRel = (CPRuleAssetCategoryRel)session.get(CPRuleAssetCategoryRelImpl.class,
						cpRuleAssetCategoryRel.getPrimaryKeyObj());
			}

			if (cpRuleAssetCategoryRel != null) {
				session.delete(cpRuleAssetCategoryRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cpRuleAssetCategoryRel != null) {
			clearCache(cpRuleAssetCategoryRel);
		}

		return cpRuleAssetCategoryRel;
	}

	@Override
	public CPRuleAssetCategoryRel updateImpl(
		CPRuleAssetCategoryRel cpRuleAssetCategoryRel) {
		boolean isNew = cpRuleAssetCategoryRel.isNew();

		if (!(cpRuleAssetCategoryRel instanceof CPRuleAssetCategoryRelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpRuleAssetCategoryRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(cpRuleAssetCategoryRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpRuleAssetCategoryRel proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPRuleAssetCategoryRel implementation " +
				cpRuleAssetCategoryRel.getClass());
		}

		CPRuleAssetCategoryRelModelImpl cpRuleAssetCategoryRelModelImpl = (CPRuleAssetCategoryRelModelImpl)cpRuleAssetCategoryRel;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cpRuleAssetCategoryRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpRuleAssetCategoryRel.setCreateDate(now);
			}
			else {
				cpRuleAssetCategoryRel.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!cpRuleAssetCategoryRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpRuleAssetCategoryRel.setModifiedDate(now);
			}
			else {
				cpRuleAssetCategoryRel.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cpRuleAssetCategoryRel.isNew()) {
				session.save(cpRuleAssetCategoryRel);

				cpRuleAssetCategoryRel.setNew(false);
			}
			else {
				cpRuleAssetCategoryRel = (CPRuleAssetCategoryRel)session.merge(cpRuleAssetCategoryRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CPRuleAssetCategoryRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					cpRuleAssetCategoryRelModelImpl.getCPRuleId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPRULEID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPRULEID,
				args);

			args = new Object[] {
					cpRuleAssetCategoryRelModelImpl.getAssetCategoryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETCATEGORYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETCATEGORYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cpRuleAssetCategoryRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPRULEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpRuleAssetCategoryRelModelImpl.getOriginalCPRuleId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPRULEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPRULEID,
					args);

				args = new Object[] {
						cpRuleAssetCategoryRelModelImpl.getCPRuleId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPRULEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPRULEID,
					args);
			}

			if ((cpRuleAssetCategoryRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETCATEGORYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpRuleAssetCategoryRelModelImpl.getOriginalAssetCategoryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETCATEGORYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETCATEGORYID,
					args);

				args = new Object[] {
						cpRuleAssetCategoryRelModelImpl.getAssetCategoryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETCATEGORYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETCATEGORYID,
					args);
			}
		}

		entityCache.putResult(CPRuleAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleAssetCategoryRelImpl.class,
			cpRuleAssetCategoryRel.getPrimaryKey(), cpRuleAssetCategoryRel,
			false);

		cpRuleAssetCategoryRel.resetOriginalValues();

		return cpRuleAssetCategoryRel;
	}

	/**
	 * Returns the cp rule asset category rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp rule asset category rel
	 * @return the cp rule asset category rel
	 * @throws NoSuchCPRuleAssetCategoryRelException if a cp rule asset category rel with the primary key could not be found
	 */
	@Override
	public CPRuleAssetCategoryRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPRuleAssetCategoryRelException {
		CPRuleAssetCategoryRel cpRuleAssetCategoryRel = fetchByPrimaryKey(primaryKey);

		if (cpRuleAssetCategoryRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPRuleAssetCategoryRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cpRuleAssetCategoryRel;
	}

	/**
	 * Returns the cp rule asset category rel with the primary key or throws a {@link NoSuchCPRuleAssetCategoryRelException} if it could not be found.
	 *
	 * @param CPRuleAssetCategoryRelId the primary key of the cp rule asset category rel
	 * @return the cp rule asset category rel
	 * @throws NoSuchCPRuleAssetCategoryRelException if a cp rule asset category rel with the primary key could not be found
	 */
	@Override
	public CPRuleAssetCategoryRel findByPrimaryKey(
		long CPRuleAssetCategoryRelId)
		throws NoSuchCPRuleAssetCategoryRelException {
		return findByPrimaryKey((Serializable)CPRuleAssetCategoryRelId);
	}

	/**
	 * Returns the cp rule asset category rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp rule asset category rel
	 * @return the cp rule asset category rel, or <code>null</code> if a cp rule asset category rel with the primary key could not be found
	 */
	@Override
	public CPRuleAssetCategoryRel fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CPRuleAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
				CPRuleAssetCategoryRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CPRuleAssetCategoryRel cpRuleAssetCategoryRel = (CPRuleAssetCategoryRel)serializable;

		if (cpRuleAssetCategoryRel == null) {
			Session session = null;

			try {
				session = openSession();

				cpRuleAssetCategoryRel = (CPRuleAssetCategoryRel)session.get(CPRuleAssetCategoryRelImpl.class,
						primaryKey);

				if (cpRuleAssetCategoryRel != null) {
					cacheResult(cpRuleAssetCategoryRel);
				}
				else {
					entityCache.putResult(CPRuleAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
						CPRuleAssetCategoryRelImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CPRuleAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
					CPRuleAssetCategoryRelImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cpRuleAssetCategoryRel;
	}

	/**
	 * Returns the cp rule asset category rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPRuleAssetCategoryRelId the primary key of the cp rule asset category rel
	 * @return the cp rule asset category rel, or <code>null</code> if a cp rule asset category rel with the primary key could not be found
	 */
	@Override
	public CPRuleAssetCategoryRel fetchByPrimaryKey(
		long CPRuleAssetCategoryRelId) {
		return fetchByPrimaryKey((Serializable)CPRuleAssetCategoryRelId);
	}

	@Override
	public Map<Serializable, CPRuleAssetCategoryRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPRuleAssetCategoryRel> map = new HashMap<Serializable, CPRuleAssetCategoryRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPRuleAssetCategoryRel cpRuleAssetCategoryRel = fetchByPrimaryKey(primaryKey);

			if (cpRuleAssetCategoryRel != null) {
				map.put(primaryKey, cpRuleAssetCategoryRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CPRuleAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
					CPRuleAssetCategoryRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CPRuleAssetCategoryRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CPRULEASSETCATEGORYREL_WHERE_PKS_IN);

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

			for (CPRuleAssetCategoryRel cpRuleAssetCategoryRel : (List<CPRuleAssetCategoryRel>)q.list()) {
				map.put(cpRuleAssetCategoryRel.getPrimaryKeyObj(),
					cpRuleAssetCategoryRel);

				cacheResult(cpRuleAssetCategoryRel);

				uncachedPrimaryKeys.remove(cpRuleAssetCategoryRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CPRuleAssetCategoryRelModelImpl.ENTITY_CACHE_ENABLED,
					CPRuleAssetCategoryRelImpl.class, primaryKey, nullModel);
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
	 * Returns all the cp rule asset category rels.
	 *
	 * @return the cp rule asset category rels
	 */
	@Override
	public List<CPRuleAssetCategoryRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp rule asset category rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp rule asset category rels
	 * @param end the upper bound of the range of cp rule asset category rels (not inclusive)
	 * @return the range of cp rule asset category rels
	 */
	@Override
	public List<CPRuleAssetCategoryRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp rule asset category rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp rule asset category rels
	 * @param end the upper bound of the range of cp rule asset category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp rule asset category rels
	 */
	@Override
	public List<CPRuleAssetCategoryRel> findAll(int start, int end,
		OrderByComparator<CPRuleAssetCategoryRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp rule asset category rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp rule asset category rels
	 * @param end the upper bound of the range of cp rule asset category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cp rule asset category rels
	 */
	@Override
	public List<CPRuleAssetCategoryRel> findAll(int start, int end,
		OrderByComparator<CPRuleAssetCategoryRel> orderByComparator,
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

		List<CPRuleAssetCategoryRel> list = null;

		if (retrieveFromCache) {
			list = (List<CPRuleAssetCategoryRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CPRULEASSETCATEGORYREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CPRULEASSETCATEGORYREL;

				if (pagination) {
					sql = sql.concat(CPRuleAssetCategoryRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CPRuleAssetCategoryRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPRuleAssetCategoryRel>)QueryUtil.list(q,
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
	 * Removes all the cp rule asset category rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPRuleAssetCategoryRel cpRuleAssetCategoryRel : findAll()) {
			remove(cpRuleAssetCategoryRel);
		}
	}

	/**
	 * Returns the number of cp rule asset category rels.
	 *
	 * @return the number of cp rule asset category rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CPRULEASSETCATEGORYREL);

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
		return CPRuleAssetCategoryRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp rule asset category rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CPRuleAssetCategoryRelImpl.class.getName());
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
	private static final String _SQL_SELECT_CPRULEASSETCATEGORYREL = "SELECT cpRuleAssetCategoryRel FROM CPRuleAssetCategoryRel cpRuleAssetCategoryRel";
	private static final String _SQL_SELECT_CPRULEASSETCATEGORYREL_WHERE_PKS_IN = "SELECT cpRuleAssetCategoryRel FROM CPRuleAssetCategoryRel cpRuleAssetCategoryRel WHERE CPRuleAssetCategoryRelId IN (";
	private static final String _SQL_SELECT_CPRULEASSETCATEGORYREL_WHERE = "SELECT cpRuleAssetCategoryRel FROM CPRuleAssetCategoryRel cpRuleAssetCategoryRel WHERE ";
	private static final String _SQL_COUNT_CPRULEASSETCATEGORYREL = "SELECT COUNT(cpRuleAssetCategoryRel) FROM CPRuleAssetCategoryRel cpRuleAssetCategoryRel";
	private static final String _SQL_COUNT_CPRULEASSETCATEGORYREL_WHERE = "SELECT COUNT(cpRuleAssetCategoryRel) FROM CPRuleAssetCategoryRel cpRuleAssetCategoryRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cpRuleAssetCategoryRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CPRuleAssetCategoryRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CPRuleAssetCategoryRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CPRuleAssetCategoryRelPersistenceImpl.class);
}