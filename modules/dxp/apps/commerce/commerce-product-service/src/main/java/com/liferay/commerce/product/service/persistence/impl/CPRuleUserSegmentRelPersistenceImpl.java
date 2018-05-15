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

import com.liferay.commerce.product.exception.NoSuchCPRuleUserSegmentRelException;
import com.liferay.commerce.product.model.CPRuleUserSegmentRel;
import com.liferay.commerce.product.model.impl.CPRuleUserSegmentRelImpl;
import com.liferay.commerce.product.model.impl.CPRuleUserSegmentRelModelImpl;
import com.liferay.commerce.product.service.persistence.CPRuleUserSegmentRelPersistence;

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
 * The persistence implementation for the cp rule user segment rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPRuleUserSegmentRelPersistence
 * @see com.liferay.commerce.product.service.persistence.CPRuleUserSegmentRelUtil
 * @generated
 */
@ProviderType
public class CPRuleUserSegmentRelPersistenceImpl extends BasePersistenceImpl<CPRuleUserSegmentRel>
	implements CPRuleUserSegmentRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CPRuleUserSegmentRelUtil} to access the cp rule user segment rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CPRuleUserSegmentRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CPRuleUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			CPRuleUserSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CPRuleUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			CPRuleUserSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CPRuleUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleUserSegmentRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CPRULEID = new FinderPath(CPRuleUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			CPRuleUserSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPRuleId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPRULEID =
		new FinderPath(CPRuleUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			CPRuleUserSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPRuleId",
			new String[] { Long.class.getName() },
			CPRuleUserSegmentRelModelImpl.CPRULEID_COLUMN_BITMASK |
			CPRuleUserSegmentRelModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CPRULEID = new FinderPath(CPRuleUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleUserSegmentRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPRuleId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the cp rule user segment rels where CPRuleId = &#63;.
	 *
	 * @param CPRuleId the cp rule ID
	 * @return the matching cp rule user segment rels
	 */
	@Override
	public List<CPRuleUserSegmentRel> findByCPRuleId(long CPRuleId) {
		return findByCPRuleId(CPRuleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp rule user segment rels where CPRuleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPRuleId the cp rule ID
	 * @param start the lower bound of the range of cp rule user segment rels
	 * @param end the upper bound of the range of cp rule user segment rels (not inclusive)
	 * @return the range of matching cp rule user segment rels
	 */
	@Override
	public List<CPRuleUserSegmentRel> findByCPRuleId(long CPRuleId, int start,
		int end) {
		return findByCPRuleId(CPRuleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp rule user segment rels where CPRuleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPRuleId the cp rule ID
	 * @param start the lower bound of the range of cp rule user segment rels
	 * @param end the upper bound of the range of cp rule user segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp rule user segment rels
	 */
	@Override
	public List<CPRuleUserSegmentRel> findByCPRuleId(long CPRuleId, int start,
		int end, OrderByComparator<CPRuleUserSegmentRel> orderByComparator) {
		return findByCPRuleId(CPRuleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp rule user segment rels where CPRuleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPRuleId the cp rule ID
	 * @param start the lower bound of the range of cp rule user segment rels
	 * @param end the upper bound of the range of cp rule user segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp rule user segment rels
	 */
	@Override
	public List<CPRuleUserSegmentRel> findByCPRuleId(long CPRuleId, int start,
		int end, OrderByComparator<CPRuleUserSegmentRel> orderByComparator,
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

		List<CPRuleUserSegmentRel> list = null;

		if (retrieveFromCache) {
			list = (List<CPRuleUserSegmentRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPRuleUserSegmentRel cpRuleUserSegmentRel : list) {
					if ((CPRuleId != cpRuleUserSegmentRel.getCPRuleId())) {
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

			query.append(_SQL_SELECT_CPRULEUSERSEGMENTREL_WHERE);

			query.append(_FINDER_COLUMN_CPRULEID_CPRULEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPRuleUserSegmentRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPRuleId);

				if (!pagination) {
					list = (List<CPRuleUserSegmentRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPRuleUserSegmentRel>)QueryUtil.list(q,
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
	 * Returns the first cp rule user segment rel in the ordered set where CPRuleId = &#63;.
	 *
	 * @param CPRuleId the cp rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp rule user segment rel
	 * @throws NoSuchCPRuleUserSegmentRelException if a matching cp rule user segment rel could not be found
	 */
	@Override
	public CPRuleUserSegmentRel findByCPRuleId_First(long CPRuleId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator)
		throws NoSuchCPRuleUserSegmentRelException {
		CPRuleUserSegmentRel cpRuleUserSegmentRel = fetchByCPRuleId_First(CPRuleId,
				orderByComparator);

		if (cpRuleUserSegmentRel != null) {
			return cpRuleUserSegmentRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPRuleId=");
		msg.append(CPRuleId);

		msg.append("}");

		throw new NoSuchCPRuleUserSegmentRelException(msg.toString());
	}

	/**
	 * Returns the first cp rule user segment rel in the ordered set where CPRuleId = &#63;.
	 *
	 * @param CPRuleId the cp rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp rule user segment rel, or <code>null</code> if a matching cp rule user segment rel could not be found
	 */
	@Override
	public CPRuleUserSegmentRel fetchByCPRuleId_First(long CPRuleId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator) {
		List<CPRuleUserSegmentRel> list = findByCPRuleId(CPRuleId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp rule user segment rel in the ordered set where CPRuleId = &#63;.
	 *
	 * @param CPRuleId the cp rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp rule user segment rel
	 * @throws NoSuchCPRuleUserSegmentRelException if a matching cp rule user segment rel could not be found
	 */
	@Override
	public CPRuleUserSegmentRel findByCPRuleId_Last(long CPRuleId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator)
		throws NoSuchCPRuleUserSegmentRelException {
		CPRuleUserSegmentRel cpRuleUserSegmentRel = fetchByCPRuleId_Last(CPRuleId,
				orderByComparator);

		if (cpRuleUserSegmentRel != null) {
			return cpRuleUserSegmentRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPRuleId=");
		msg.append(CPRuleId);

		msg.append("}");

		throw new NoSuchCPRuleUserSegmentRelException(msg.toString());
	}

	/**
	 * Returns the last cp rule user segment rel in the ordered set where CPRuleId = &#63;.
	 *
	 * @param CPRuleId the cp rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp rule user segment rel, or <code>null</code> if a matching cp rule user segment rel could not be found
	 */
	@Override
	public CPRuleUserSegmentRel fetchByCPRuleId_Last(long CPRuleId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator) {
		int count = countByCPRuleId(CPRuleId);

		if (count == 0) {
			return null;
		}

		List<CPRuleUserSegmentRel> list = findByCPRuleId(CPRuleId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp rule user segment rels before and after the current cp rule user segment rel in the ordered set where CPRuleId = &#63;.
	 *
	 * @param CPRuleUserSegmentRelId the primary key of the current cp rule user segment rel
	 * @param CPRuleId the cp rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp rule user segment rel
	 * @throws NoSuchCPRuleUserSegmentRelException if a cp rule user segment rel with the primary key could not be found
	 */
	@Override
	public CPRuleUserSegmentRel[] findByCPRuleId_PrevAndNext(
		long CPRuleUserSegmentRelId, long CPRuleId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator)
		throws NoSuchCPRuleUserSegmentRelException {
		CPRuleUserSegmentRel cpRuleUserSegmentRel = findByPrimaryKey(CPRuleUserSegmentRelId);

		Session session = null;

		try {
			session = openSession();

			CPRuleUserSegmentRel[] array = new CPRuleUserSegmentRelImpl[3];

			array[0] = getByCPRuleId_PrevAndNext(session, cpRuleUserSegmentRel,
					CPRuleId, orderByComparator, true);

			array[1] = cpRuleUserSegmentRel;

			array[2] = getByCPRuleId_PrevAndNext(session, cpRuleUserSegmentRel,
					CPRuleId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPRuleUserSegmentRel getByCPRuleId_PrevAndNext(Session session,
		CPRuleUserSegmentRel cpRuleUserSegmentRel, long CPRuleId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator,
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

		query.append(_SQL_SELECT_CPRULEUSERSEGMENTREL_WHERE);

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
			query.append(CPRuleUserSegmentRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(CPRuleId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpRuleUserSegmentRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPRuleUserSegmentRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp rule user segment rels where CPRuleId = &#63; from the database.
	 *
	 * @param CPRuleId the cp rule ID
	 */
	@Override
	public void removeByCPRuleId(long CPRuleId) {
		for (CPRuleUserSegmentRel cpRuleUserSegmentRel : findByCPRuleId(
				CPRuleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpRuleUserSegmentRel);
		}
	}

	/**
	 * Returns the number of cp rule user segment rels where CPRuleId = &#63;.
	 *
	 * @param CPRuleId the cp rule ID
	 * @return the number of matching cp rule user segment rels
	 */
	@Override
	public int countByCPRuleId(long CPRuleId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CPRULEID;

		Object[] finderArgs = new Object[] { CPRuleId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPRULEUSERSEGMENTREL_WHERE);

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

	private static final String _FINDER_COLUMN_CPRULEID_CPRULEID_2 = "cpRuleUserSegmentRel.CPRuleId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID =
		new FinderPath(CPRuleUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			CPRuleUserSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceUserSegmentEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID =
		new FinderPath(CPRuleUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			CPRuleUserSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceUserSegmentEntryId",
			new String[] { Long.class.getName() },
			CPRuleUserSegmentRelModelImpl.COMMERCEUSERSEGMENTENTRYID_COLUMN_BITMASK |
			CPRuleUserSegmentRelModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEUSERSEGMENTENTRYID =
		new FinderPath(CPRuleUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleUserSegmentRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceUserSegmentEntryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the cp rule user segment rels where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @return the matching cp rule user segment rels
	 */
	@Override
	public List<CPRuleUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		return findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp rule user segment rels where commerceUserSegmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param start the lower bound of the range of cp rule user segment rels
	 * @param end the upper bound of the range of cp rule user segment rels (not inclusive)
	 * @return the range of matching cp rule user segment rels
	 */
	@Override
	public List<CPRuleUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end) {
		return findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp rule user segment rels where commerceUserSegmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param start the lower bound of the range of cp rule user segment rels
	 * @param end the upper bound of the range of cp rule user segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp rule user segment rels
	 */
	@Override
	public List<CPRuleUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator) {
		return findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp rule user segment rels where commerceUserSegmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param start the lower bound of the range of cp rule user segment rels
	 * @param end the upper bound of the range of cp rule user segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp rule user segment rels
	 */
	@Override
	public List<CPRuleUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID;
			finderArgs = new Object[] { commerceUserSegmentEntryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID;
			finderArgs = new Object[] {
					commerceUserSegmentEntryId,
					
					start, end, orderByComparator
				};
		}

		List<CPRuleUserSegmentRel> list = null;

		if (retrieveFromCache) {
			list = (List<CPRuleUserSegmentRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPRuleUserSegmentRel cpRuleUserSegmentRel : list) {
					if ((commerceUserSegmentEntryId != cpRuleUserSegmentRel.getCommerceUserSegmentEntryId())) {
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

			query.append(_SQL_SELECT_CPRULEUSERSEGMENTREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEUSERSEGMENTENTRYID_COMMERCEUSERSEGMENTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPRuleUserSegmentRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceUserSegmentEntryId);

				if (!pagination) {
					list = (List<CPRuleUserSegmentRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPRuleUserSegmentRel>)QueryUtil.list(q,
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
	 * Returns the first cp rule user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp rule user segment rel
	 * @throws NoSuchCPRuleUserSegmentRelException if a matching cp rule user segment rel could not be found
	 */
	@Override
	public CPRuleUserSegmentRel findByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator)
		throws NoSuchCPRuleUserSegmentRelException {
		CPRuleUserSegmentRel cpRuleUserSegmentRel = fetchByCommerceUserSegmentEntryId_First(commerceUserSegmentEntryId,
				orderByComparator);

		if (cpRuleUserSegmentRel != null) {
			return cpRuleUserSegmentRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceUserSegmentEntryId=");
		msg.append(commerceUserSegmentEntryId);

		msg.append("}");

		throw new NoSuchCPRuleUserSegmentRelException(msg.toString());
	}

	/**
	 * Returns the first cp rule user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp rule user segment rel, or <code>null</code> if a matching cp rule user segment rel could not be found
	 */
	@Override
	public CPRuleUserSegmentRel fetchByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator) {
		List<CPRuleUserSegmentRel> list = findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp rule user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp rule user segment rel
	 * @throws NoSuchCPRuleUserSegmentRelException if a matching cp rule user segment rel could not be found
	 */
	@Override
	public CPRuleUserSegmentRel findByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator)
		throws NoSuchCPRuleUserSegmentRelException {
		CPRuleUserSegmentRel cpRuleUserSegmentRel = fetchByCommerceUserSegmentEntryId_Last(commerceUserSegmentEntryId,
				orderByComparator);

		if (cpRuleUserSegmentRel != null) {
			return cpRuleUserSegmentRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceUserSegmentEntryId=");
		msg.append(commerceUserSegmentEntryId);

		msg.append("}");

		throw new NoSuchCPRuleUserSegmentRelException(msg.toString());
	}

	/**
	 * Returns the last cp rule user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp rule user segment rel, or <code>null</code> if a matching cp rule user segment rel could not be found
	 */
	@Override
	public CPRuleUserSegmentRel fetchByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator) {
		int count = countByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);

		if (count == 0) {
			return null;
		}

		List<CPRuleUserSegmentRel> list = findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp rule user segment rels before and after the current cp rule user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param CPRuleUserSegmentRelId the primary key of the current cp rule user segment rel
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp rule user segment rel
	 * @throws NoSuchCPRuleUserSegmentRelException if a cp rule user segment rel with the primary key could not be found
	 */
	@Override
	public CPRuleUserSegmentRel[] findByCommerceUserSegmentEntryId_PrevAndNext(
		long CPRuleUserSegmentRelId, long commerceUserSegmentEntryId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator)
		throws NoSuchCPRuleUserSegmentRelException {
		CPRuleUserSegmentRel cpRuleUserSegmentRel = findByPrimaryKey(CPRuleUserSegmentRelId);

		Session session = null;

		try {
			session = openSession();

			CPRuleUserSegmentRel[] array = new CPRuleUserSegmentRelImpl[3];

			array[0] = getByCommerceUserSegmentEntryId_PrevAndNext(session,
					cpRuleUserSegmentRel, commerceUserSegmentEntryId,
					orderByComparator, true);

			array[1] = cpRuleUserSegmentRel;

			array[2] = getByCommerceUserSegmentEntryId_PrevAndNext(session,
					cpRuleUserSegmentRel, commerceUserSegmentEntryId,
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

	protected CPRuleUserSegmentRel getByCommerceUserSegmentEntryId_PrevAndNext(
		Session session, CPRuleUserSegmentRel cpRuleUserSegmentRel,
		long commerceUserSegmentEntryId,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator,
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

		query.append(_SQL_SELECT_CPRULEUSERSEGMENTREL_WHERE);

		query.append(_FINDER_COLUMN_COMMERCEUSERSEGMENTENTRYID_COMMERCEUSERSEGMENTENTRYID_2);

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
			query.append(CPRuleUserSegmentRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceUserSegmentEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpRuleUserSegmentRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPRuleUserSegmentRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp rule user segment rels where commerceUserSegmentEntryId = &#63; from the database.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 */
	@Override
	public void removeByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		for (CPRuleUserSegmentRel cpRuleUserSegmentRel : findByCommerceUserSegmentEntryId(
				commerceUserSegmentEntryId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(cpRuleUserSegmentRel);
		}
	}

	/**
	 * Returns the number of cp rule user segment rels where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @return the number of matching cp rule user segment rels
	 */
	@Override
	public int countByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEUSERSEGMENTENTRYID;

		Object[] finderArgs = new Object[] { commerceUserSegmentEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPRULEUSERSEGMENTREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEUSERSEGMENTENTRYID_COMMERCEUSERSEGMENTENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceUserSegmentEntryId);

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

	private static final String _FINDER_COLUMN_COMMERCEUSERSEGMENTENTRYID_COMMERCEUSERSEGMENTENTRYID_2 =
		"cpRuleUserSegmentRel.commerceUserSegmentEntryId = ?";

	public CPRuleUserSegmentRelPersistenceImpl() {
		setModelClass(CPRuleUserSegmentRel.class);
	}

	/**
	 * Caches the cp rule user segment rel in the entity cache if it is enabled.
	 *
	 * @param cpRuleUserSegmentRel the cp rule user segment rel
	 */
	@Override
	public void cacheResult(CPRuleUserSegmentRel cpRuleUserSegmentRel) {
		entityCache.putResult(CPRuleUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleUserSegmentRelImpl.class,
			cpRuleUserSegmentRel.getPrimaryKey(), cpRuleUserSegmentRel);

		cpRuleUserSegmentRel.resetOriginalValues();
	}

	/**
	 * Caches the cp rule user segment rels in the entity cache if it is enabled.
	 *
	 * @param cpRuleUserSegmentRels the cp rule user segment rels
	 */
	@Override
	public void cacheResult(List<CPRuleUserSegmentRel> cpRuleUserSegmentRels) {
		for (CPRuleUserSegmentRel cpRuleUserSegmentRel : cpRuleUserSegmentRels) {
			if (entityCache.getResult(
						CPRuleUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
						CPRuleUserSegmentRelImpl.class,
						cpRuleUserSegmentRel.getPrimaryKey()) == null) {
				cacheResult(cpRuleUserSegmentRel);
			}
			else {
				cpRuleUserSegmentRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cp rule user segment rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPRuleUserSegmentRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cp rule user segment rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPRuleUserSegmentRel cpRuleUserSegmentRel) {
		entityCache.removeResult(CPRuleUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleUserSegmentRelImpl.class, cpRuleUserSegmentRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CPRuleUserSegmentRel> cpRuleUserSegmentRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CPRuleUserSegmentRel cpRuleUserSegmentRel : cpRuleUserSegmentRels) {
			entityCache.removeResult(CPRuleUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
				CPRuleUserSegmentRelImpl.class,
				cpRuleUserSegmentRel.getPrimaryKey());
		}
	}

	/**
	 * Creates a new cp rule user segment rel with the primary key. Does not add the cp rule user segment rel to the database.
	 *
	 * @param CPRuleUserSegmentRelId the primary key for the new cp rule user segment rel
	 * @return the new cp rule user segment rel
	 */
	@Override
	public CPRuleUserSegmentRel create(long CPRuleUserSegmentRelId) {
		CPRuleUserSegmentRel cpRuleUserSegmentRel = new CPRuleUserSegmentRelImpl();

		cpRuleUserSegmentRel.setNew(true);
		cpRuleUserSegmentRel.setPrimaryKey(CPRuleUserSegmentRelId);

		cpRuleUserSegmentRel.setCompanyId(companyProvider.getCompanyId());

		return cpRuleUserSegmentRel;
	}

	/**
	 * Removes the cp rule user segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPRuleUserSegmentRelId the primary key of the cp rule user segment rel
	 * @return the cp rule user segment rel that was removed
	 * @throws NoSuchCPRuleUserSegmentRelException if a cp rule user segment rel with the primary key could not be found
	 */
	@Override
	public CPRuleUserSegmentRel remove(long CPRuleUserSegmentRelId)
		throws NoSuchCPRuleUserSegmentRelException {
		return remove((Serializable)CPRuleUserSegmentRelId);
	}

	/**
	 * Removes the cp rule user segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp rule user segment rel
	 * @return the cp rule user segment rel that was removed
	 * @throws NoSuchCPRuleUserSegmentRelException if a cp rule user segment rel with the primary key could not be found
	 */
	@Override
	public CPRuleUserSegmentRel remove(Serializable primaryKey)
		throws NoSuchCPRuleUserSegmentRelException {
		Session session = null;

		try {
			session = openSession();

			CPRuleUserSegmentRel cpRuleUserSegmentRel = (CPRuleUserSegmentRel)session.get(CPRuleUserSegmentRelImpl.class,
					primaryKey);

			if (cpRuleUserSegmentRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPRuleUserSegmentRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cpRuleUserSegmentRel);
		}
		catch (NoSuchCPRuleUserSegmentRelException nsee) {
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
	protected CPRuleUserSegmentRel removeImpl(
		CPRuleUserSegmentRel cpRuleUserSegmentRel) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpRuleUserSegmentRel)) {
				cpRuleUserSegmentRel = (CPRuleUserSegmentRel)session.get(CPRuleUserSegmentRelImpl.class,
						cpRuleUserSegmentRel.getPrimaryKeyObj());
			}

			if (cpRuleUserSegmentRel != null) {
				session.delete(cpRuleUserSegmentRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cpRuleUserSegmentRel != null) {
			clearCache(cpRuleUserSegmentRel);
		}

		return cpRuleUserSegmentRel;
	}

	@Override
	public CPRuleUserSegmentRel updateImpl(
		CPRuleUserSegmentRel cpRuleUserSegmentRel) {
		boolean isNew = cpRuleUserSegmentRel.isNew();

		if (!(cpRuleUserSegmentRel instanceof CPRuleUserSegmentRelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpRuleUserSegmentRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(cpRuleUserSegmentRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpRuleUserSegmentRel proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPRuleUserSegmentRel implementation " +
				cpRuleUserSegmentRel.getClass());
		}

		CPRuleUserSegmentRelModelImpl cpRuleUserSegmentRelModelImpl = (CPRuleUserSegmentRelModelImpl)cpRuleUserSegmentRel;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cpRuleUserSegmentRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpRuleUserSegmentRel.setCreateDate(now);
			}
			else {
				cpRuleUserSegmentRel.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!cpRuleUserSegmentRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpRuleUserSegmentRel.setModifiedDate(now);
			}
			else {
				cpRuleUserSegmentRel.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cpRuleUserSegmentRel.isNew()) {
				session.save(cpRuleUserSegmentRel);

				cpRuleUserSegmentRel.setNew(false);
			}
			else {
				cpRuleUserSegmentRel = (CPRuleUserSegmentRel)session.merge(cpRuleUserSegmentRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CPRuleUserSegmentRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					cpRuleUserSegmentRelModelImpl.getCPRuleId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPRULEID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPRULEID,
				args);

			args = new Object[] {
					cpRuleUserSegmentRelModelImpl.getCommerceUserSegmentEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEUSERSEGMENTENTRYID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cpRuleUserSegmentRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPRULEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpRuleUserSegmentRelModelImpl.getOriginalCPRuleId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPRULEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPRULEID,
					args);

				args = new Object[] { cpRuleUserSegmentRelModelImpl.getCPRuleId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPRULEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPRULEID,
					args);
			}

			if ((cpRuleUserSegmentRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpRuleUserSegmentRelModelImpl.getOriginalCommerceUserSegmentEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEUSERSEGMENTENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID,
					args);

				args = new Object[] {
						cpRuleUserSegmentRelModelImpl.getCommerceUserSegmentEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEUSERSEGMENTENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID,
					args);
			}
		}

		entityCache.putResult(CPRuleUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CPRuleUserSegmentRelImpl.class,
			cpRuleUserSegmentRel.getPrimaryKey(), cpRuleUserSegmentRel, false);

		cpRuleUserSegmentRel.resetOriginalValues();

		return cpRuleUserSegmentRel;
	}

	/**
	 * Returns the cp rule user segment rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp rule user segment rel
	 * @return the cp rule user segment rel
	 * @throws NoSuchCPRuleUserSegmentRelException if a cp rule user segment rel with the primary key could not be found
	 */
	@Override
	public CPRuleUserSegmentRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPRuleUserSegmentRelException {
		CPRuleUserSegmentRel cpRuleUserSegmentRel = fetchByPrimaryKey(primaryKey);

		if (cpRuleUserSegmentRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPRuleUserSegmentRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cpRuleUserSegmentRel;
	}

	/**
	 * Returns the cp rule user segment rel with the primary key or throws a {@link NoSuchCPRuleUserSegmentRelException} if it could not be found.
	 *
	 * @param CPRuleUserSegmentRelId the primary key of the cp rule user segment rel
	 * @return the cp rule user segment rel
	 * @throws NoSuchCPRuleUserSegmentRelException if a cp rule user segment rel with the primary key could not be found
	 */
	@Override
	public CPRuleUserSegmentRel findByPrimaryKey(long CPRuleUserSegmentRelId)
		throws NoSuchCPRuleUserSegmentRelException {
		return findByPrimaryKey((Serializable)CPRuleUserSegmentRelId);
	}

	/**
	 * Returns the cp rule user segment rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp rule user segment rel
	 * @return the cp rule user segment rel, or <code>null</code> if a cp rule user segment rel with the primary key could not be found
	 */
	@Override
	public CPRuleUserSegmentRel fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CPRuleUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
				CPRuleUserSegmentRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CPRuleUserSegmentRel cpRuleUserSegmentRel = (CPRuleUserSegmentRel)serializable;

		if (cpRuleUserSegmentRel == null) {
			Session session = null;

			try {
				session = openSession();

				cpRuleUserSegmentRel = (CPRuleUserSegmentRel)session.get(CPRuleUserSegmentRelImpl.class,
						primaryKey);

				if (cpRuleUserSegmentRel != null) {
					cacheResult(cpRuleUserSegmentRel);
				}
				else {
					entityCache.putResult(CPRuleUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
						CPRuleUserSegmentRelImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CPRuleUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
					CPRuleUserSegmentRelImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cpRuleUserSegmentRel;
	}

	/**
	 * Returns the cp rule user segment rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPRuleUserSegmentRelId the primary key of the cp rule user segment rel
	 * @return the cp rule user segment rel, or <code>null</code> if a cp rule user segment rel with the primary key could not be found
	 */
	@Override
	public CPRuleUserSegmentRel fetchByPrimaryKey(long CPRuleUserSegmentRelId) {
		return fetchByPrimaryKey((Serializable)CPRuleUserSegmentRelId);
	}

	@Override
	public Map<Serializable, CPRuleUserSegmentRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPRuleUserSegmentRel> map = new HashMap<Serializable, CPRuleUserSegmentRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPRuleUserSegmentRel cpRuleUserSegmentRel = fetchByPrimaryKey(primaryKey);

			if (cpRuleUserSegmentRel != null) {
				map.put(primaryKey, cpRuleUserSegmentRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CPRuleUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
					CPRuleUserSegmentRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CPRuleUserSegmentRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CPRULEUSERSEGMENTREL_WHERE_PKS_IN);

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

			for (CPRuleUserSegmentRel cpRuleUserSegmentRel : (List<CPRuleUserSegmentRel>)q.list()) {
				map.put(cpRuleUserSegmentRel.getPrimaryKeyObj(),
					cpRuleUserSegmentRel);

				cacheResult(cpRuleUserSegmentRel);

				uncachedPrimaryKeys.remove(cpRuleUserSegmentRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CPRuleUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
					CPRuleUserSegmentRelImpl.class, primaryKey, nullModel);
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
	 * Returns all the cp rule user segment rels.
	 *
	 * @return the cp rule user segment rels
	 */
	@Override
	public List<CPRuleUserSegmentRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp rule user segment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp rule user segment rels
	 * @param end the upper bound of the range of cp rule user segment rels (not inclusive)
	 * @return the range of cp rule user segment rels
	 */
	@Override
	public List<CPRuleUserSegmentRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp rule user segment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp rule user segment rels
	 * @param end the upper bound of the range of cp rule user segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp rule user segment rels
	 */
	@Override
	public List<CPRuleUserSegmentRel> findAll(int start, int end,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp rule user segment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp rule user segment rels
	 * @param end the upper bound of the range of cp rule user segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cp rule user segment rels
	 */
	@Override
	public List<CPRuleUserSegmentRel> findAll(int start, int end,
		OrderByComparator<CPRuleUserSegmentRel> orderByComparator,
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

		List<CPRuleUserSegmentRel> list = null;

		if (retrieveFromCache) {
			list = (List<CPRuleUserSegmentRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CPRULEUSERSEGMENTREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CPRULEUSERSEGMENTREL;

				if (pagination) {
					sql = sql.concat(CPRuleUserSegmentRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CPRuleUserSegmentRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPRuleUserSegmentRel>)QueryUtil.list(q,
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
	 * Removes all the cp rule user segment rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPRuleUserSegmentRel cpRuleUserSegmentRel : findAll()) {
			remove(cpRuleUserSegmentRel);
		}
	}

	/**
	 * Returns the number of cp rule user segment rels.
	 *
	 * @return the number of cp rule user segment rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CPRULEUSERSEGMENTREL);

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
		return CPRuleUserSegmentRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp rule user segment rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CPRuleUserSegmentRelImpl.class.getName());
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
	private static final String _SQL_SELECT_CPRULEUSERSEGMENTREL = "SELECT cpRuleUserSegmentRel FROM CPRuleUserSegmentRel cpRuleUserSegmentRel";
	private static final String _SQL_SELECT_CPRULEUSERSEGMENTREL_WHERE_PKS_IN = "SELECT cpRuleUserSegmentRel FROM CPRuleUserSegmentRel cpRuleUserSegmentRel WHERE CPRuleUserSegmentRelId IN (";
	private static final String _SQL_SELECT_CPRULEUSERSEGMENTREL_WHERE = "SELECT cpRuleUserSegmentRel FROM CPRuleUserSegmentRel cpRuleUserSegmentRel WHERE ";
	private static final String _SQL_COUNT_CPRULEUSERSEGMENTREL = "SELECT COUNT(cpRuleUserSegmentRel) FROM CPRuleUserSegmentRel cpRuleUserSegmentRel";
	private static final String _SQL_COUNT_CPRULEUSERSEGMENTREL_WHERE = "SELECT COUNT(cpRuleUserSegmentRel) FROM CPRuleUserSegmentRel cpRuleUserSegmentRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cpRuleUserSegmentRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CPRuleUserSegmentRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CPRuleUserSegmentRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CPRuleUserSegmentRelPersistenceImpl.class);
}