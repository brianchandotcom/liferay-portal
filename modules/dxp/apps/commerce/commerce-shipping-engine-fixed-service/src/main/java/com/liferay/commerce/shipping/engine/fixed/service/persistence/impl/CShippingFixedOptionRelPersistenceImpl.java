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

package com.liferay.commerce.shipping.engine.fixed.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.shipping.engine.fixed.exception.NoSuchCShippingFixedOptionRelException;
import com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.model.impl.CShippingFixedOptionRelImpl;
import com.liferay.commerce.shipping.engine.fixed.model.impl.CShippingFixedOptionRelModelImpl;
import com.liferay.commerce.shipping.engine.fixed.service.persistence.CShippingFixedOptionRelPersistence;

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
 * The persistence implementation for the c shipping fixed option rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CShippingFixedOptionRelPersistence
 * @see com.liferay.commerce.shipping.engine.fixed.service.persistence.CShippingFixedOptionRelUtil
 * @generated
 */
@ProviderType
public class CShippingFixedOptionRelPersistenceImpl extends BasePersistenceImpl<CShippingFixedOptionRel>
	implements CShippingFixedOptionRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CShippingFixedOptionRelUtil} to access the c shipping fixed option rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CShippingFixedOptionRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CShippingFixedOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CShippingFixedOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CShippingFixedOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CShippingFixedOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CShippingFixedOptionRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID =
		new FinderPath(CShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CShippingFixedOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CShippingFixedOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceShippingMethodId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID =
		new FinderPath(CShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CShippingFixedOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CShippingFixedOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceShippingMethodId",
			new String[] { Long.class.getName() },
			CShippingFixedOptionRelModelImpl.COMMERCESHIPPINGMETHODID_COLUMN_BITMASK |
			CShippingFixedOptionRelModelImpl.COMMERCECOUNTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCESHIPPINGMETHODID =
		new FinderPath(CShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CShippingFixedOptionRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceShippingMethodId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the c shipping fixed option rels where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @return the matching c shipping fixed option rels
	 */
	@Override
	public List<CShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId) {
		return findByCommerceShippingMethodId(commerceShippingMethodId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the c shipping fixed option rels where commerceShippingMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param start the lower bound of the range of c shipping fixed option rels
	 * @param end the upper bound of the range of c shipping fixed option rels (not inclusive)
	 * @return the range of matching c shipping fixed option rels
	 */
	@Override
	public List<CShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end) {
		return findByCommerceShippingMethodId(commerceShippingMethodId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the c shipping fixed option rels where commerceShippingMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param start the lower bound of the range of c shipping fixed option rels
	 * @param end the upper bound of the range of c shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching c shipping fixed option rels
	 */
	@Override
	public List<CShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator) {
		return findByCommerceShippingMethodId(commerceShippingMethodId, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the c shipping fixed option rels where commerceShippingMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param start the lower bound of the range of c shipping fixed option rels
	 * @param end the upper bound of the range of c shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching c shipping fixed option rels
	 */
	@Override
	public List<CShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID;
			finderArgs = new Object[] { commerceShippingMethodId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID;
			finderArgs = new Object[] {
					commerceShippingMethodId,
					
					start, end, orderByComparator
				};
		}

		List<CShippingFixedOptionRel> list = null;

		if (retrieveFromCache) {
			list = (List<CShippingFixedOptionRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CShippingFixedOptionRel cShippingFixedOptionRel : list) {
					if ((commerceShippingMethodId != cShippingFixedOptionRel.getCommerceShippingMethodId())) {
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

			query.append(_SQL_SELECT_CSHIPPINGFIXEDOPTIONREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCESHIPPINGMETHODID_COMMERCESHIPPINGMETHODID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CShippingFixedOptionRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceShippingMethodId);

				if (!pagination) {
					list = (List<CShippingFixedOptionRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CShippingFixedOptionRel>)QueryUtil.list(q,
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
	 * Returns the first c shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c shipping fixed option rel
	 * @throws NoSuchCShippingFixedOptionRelException if a matching c shipping fixed option rel could not be found
	 */
	@Override
	public CShippingFixedOptionRel findByCommerceShippingMethodId_First(
		long commerceShippingMethodId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator)
		throws NoSuchCShippingFixedOptionRelException {
		CShippingFixedOptionRel cShippingFixedOptionRel = fetchByCommerceShippingMethodId_First(commerceShippingMethodId,
				orderByComparator);

		if (cShippingFixedOptionRel != null) {
			return cShippingFixedOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceShippingMethodId=");
		msg.append(commerceShippingMethodId);

		msg.append("}");

		throw new NoSuchCShippingFixedOptionRelException(msg.toString());
	}

	/**
	 * Returns the first c shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c shipping fixed option rel, or <code>null</code> if a matching c shipping fixed option rel could not be found
	 */
	@Override
	public CShippingFixedOptionRel fetchByCommerceShippingMethodId_First(
		long commerceShippingMethodId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator) {
		List<CShippingFixedOptionRel> list = findByCommerceShippingMethodId(commerceShippingMethodId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last c shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c shipping fixed option rel
	 * @throws NoSuchCShippingFixedOptionRelException if a matching c shipping fixed option rel could not be found
	 */
	@Override
	public CShippingFixedOptionRel findByCommerceShippingMethodId_Last(
		long commerceShippingMethodId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator)
		throws NoSuchCShippingFixedOptionRelException {
		CShippingFixedOptionRel cShippingFixedOptionRel = fetchByCommerceShippingMethodId_Last(commerceShippingMethodId,
				orderByComparator);

		if (cShippingFixedOptionRel != null) {
			return cShippingFixedOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceShippingMethodId=");
		msg.append(commerceShippingMethodId);

		msg.append("}");

		throw new NoSuchCShippingFixedOptionRelException(msg.toString());
	}

	/**
	 * Returns the last c shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c shipping fixed option rel, or <code>null</code> if a matching c shipping fixed option rel could not be found
	 */
	@Override
	public CShippingFixedOptionRel fetchByCommerceShippingMethodId_Last(
		long commerceShippingMethodId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator) {
		int count = countByCommerceShippingMethodId(commerceShippingMethodId);

		if (count == 0) {
			return null;
		}

		List<CShippingFixedOptionRel> list = findByCommerceShippingMethodId(commerceShippingMethodId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the c shipping fixed option rels before and after the current c shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param CShippingFixedOptionRelId the primary key of the current c shipping fixed option rel
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next c shipping fixed option rel
	 * @throws NoSuchCShippingFixedOptionRelException if a c shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CShippingFixedOptionRel[] findByCommerceShippingMethodId_PrevAndNext(
		long CShippingFixedOptionRelId, long commerceShippingMethodId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator)
		throws NoSuchCShippingFixedOptionRelException {
		CShippingFixedOptionRel cShippingFixedOptionRel = findByPrimaryKey(CShippingFixedOptionRelId);

		Session session = null;

		try {
			session = openSession();

			CShippingFixedOptionRel[] array = new CShippingFixedOptionRelImpl[3];

			array[0] = getByCommerceShippingMethodId_PrevAndNext(session,
					cShippingFixedOptionRel, commerceShippingMethodId,
					orderByComparator, true);

			array[1] = cShippingFixedOptionRel;

			array[2] = getByCommerceShippingMethodId_PrevAndNext(session,
					cShippingFixedOptionRel, commerceShippingMethodId,
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

	protected CShippingFixedOptionRel getByCommerceShippingMethodId_PrevAndNext(
		Session session, CShippingFixedOptionRel cShippingFixedOptionRel,
		long commerceShippingMethodId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator,
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

		query.append(_SQL_SELECT_CSHIPPINGFIXEDOPTIONREL_WHERE);

		query.append(_FINDER_COLUMN_COMMERCESHIPPINGMETHODID_COMMERCESHIPPINGMETHODID_2);

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
			query.append(CShippingFixedOptionRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceShippingMethodId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cShippingFixedOptionRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CShippingFixedOptionRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the c shipping fixed option rels where commerceShippingMethodId = &#63; from the database.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 */
	@Override
	public void removeByCommerceShippingMethodId(long commerceShippingMethodId) {
		for (CShippingFixedOptionRel cShippingFixedOptionRel : findByCommerceShippingMethodId(
				commerceShippingMethodId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(cShippingFixedOptionRel);
		}
	}

	/**
	 * Returns the number of c shipping fixed option rels where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @return the number of matching c shipping fixed option rels
	 */
	@Override
	public int countByCommerceShippingMethodId(long commerceShippingMethodId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCESHIPPINGMETHODID;

		Object[] finderArgs = new Object[] { commerceShippingMethodId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CSHIPPINGFIXEDOPTIONREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCESHIPPINGMETHODID_COMMERCESHIPPINGMETHODID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceShippingMethodId);

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

	private static final String _FINDER_COLUMN_COMMERCESHIPPINGMETHODID_COMMERCESHIPPINGMETHODID_2 =
		"cShippingFixedOptionRel.commerceShippingMethodId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCESHIPPINGFIXEDOPTIONID =
		new FinderPath(CShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CShippingFixedOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CShippingFixedOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceShippingFixedOptionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGFIXEDOPTIONID =
		new FinderPath(CShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CShippingFixedOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CShippingFixedOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceShippingFixedOptionId",
			new String[] { Long.class.getName() },
			CShippingFixedOptionRelModelImpl.COMMERCESHIPPINGFIXEDOPTIONID_COLUMN_BITMASK |
			CShippingFixedOptionRelModelImpl.COMMERCECOUNTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCESHIPPINGFIXEDOPTIONID =
		new FinderPath(CShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CShippingFixedOptionRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceShippingFixedOptionId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the c shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the matching c shipping fixed option rels
	 */
	@Override
	public List<CShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {
		return findByCommerceShippingFixedOptionId(commerceShippingFixedOptionId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the c shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of c shipping fixed option rels
	 * @param end the upper bound of the range of c shipping fixed option rels (not inclusive)
	 * @return the range of matching c shipping fixed option rels
	 */
	@Override
	public List<CShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId, int start, int end) {
		return findByCommerceShippingFixedOptionId(commerceShippingFixedOptionId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the c shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of c shipping fixed option rels
	 * @param end the upper bound of the range of c shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching c shipping fixed option rels
	 */
	@Override
	public List<CShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId, int start, int end,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator) {
		return findByCommerceShippingFixedOptionId(commerceShippingFixedOptionId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the c shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of c shipping fixed option rels
	 * @param end the upper bound of the range of c shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching c shipping fixed option rels
	 */
	@Override
	public List<CShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId, int start, int end,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGFIXEDOPTIONID;
			finderArgs = new Object[] { commerceShippingFixedOptionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCESHIPPINGFIXEDOPTIONID;
			finderArgs = new Object[] {
					commerceShippingFixedOptionId,
					
					start, end, orderByComparator
				};
		}

		List<CShippingFixedOptionRel> list = null;

		if (retrieveFromCache) {
			list = (List<CShippingFixedOptionRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CShippingFixedOptionRel cShippingFixedOptionRel : list) {
					if ((commerceShippingFixedOptionId != cShippingFixedOptionRel.getCommerceShippingFixedOptionId())) {
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

			query.append(_SQL_SELECT_CSHIPPINGFIXEDOPTIONREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCESHIPPINGFIXEDOPTIONID_COMMERCESHIPPINGFIXEDOPTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CShippingFixedOptionRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceShippingFixedOptionId);

				if (!pagination) {
					list = (List<CShippingFixedOptionRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CShippingFixedOptionRel>)QueryUtil.list(q,
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
	 * Returns the first c shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c shipping fixed option rel
	 * @throws NoSuchCShippingFixedOptionRelException if a matching c shipping fixed option rel could not be found
	 */
	@Override
	public CShippingFixedOptionRel findByCommerceShippingFixedOptionId_First(
		long commerceShippingFixedOptionId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator)
		throws NoSuchCShippingFixedOptionRelException {
		CShippingFixedOptionRel cShippingFixedOptionRel = fetchByCommerceShippingFixedOptionId_First(commerceShippingFixedOptionId,
				orderByComparator);

		if (cShippingFixedOptionRel != null) {
			return cShippingFixedOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceShippingFixedOptionId=");
		msg.append(commerceShippingFixedOptionId);

		msg.append("}");

		throw new NoSuchCShippingFixedOptionRelException(msg.toString());
	}

	/**
	 * Returns the first c shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c shipping fixed option rel, or <code>null</code> if a matching c shipping fixed option rel could not be found
	 */
	@Override
	public CShippingFixedOptionRel fetchByCommerceShippingFixedOptionId_First(
		long commerceShippingFixedOptionId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator) {
		List<CShippingFixedOptionRel> list = findByCommerceShippingFixedOptionId(commerceShippingFixedOptionId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last c shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c shipping fixed option rel
	 * @throws NoSuchCShippingFixedOptionRelException if a matching c shipping fixed option rel could not be found
	 */
	@Override
	public CShippingFixedOptionRel findByCommerceShippingFixedOptionId_Last(
		long commerceShippingFixedOptionId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator)
		throws NoSuchCShippingFixedOptionRelException {
		CShippingFixedOptionRel cShippingFixedOptionRel = fetchByCommerceShippingFixedOptionId_Last(commerceShippingFixedOptionId,
				orderByComparator);

		if (cShippingFixedOptionRel != null) {
			return cShippingFixedOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceShippingFixedOptionId=");
		msg.append(commerceShippingFixedOptionId);

		msg.append("}");

		throw new NoSuchCShippingFixedOptionRelException(msg.toString());
	}

	/**
	 * Returns the last c shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c shipping fixed option rel, or <code>null</code> if a matching c shipping fixed option rel could not be found
	 */
	@Override
	public CShippingFixedOptionRel fetchByCommerceShippingFixedOptionId_Last(
		long commerceShippingFixedOptionId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator) {
		int count = countByCommerceShippingFixedOptionId(commerceShippingFixedOptionId);

		if (count == 0) {
			return null;
		}

		List<CShippingFixedOptionRel> list = findByCommerceShippingFixedOptionId(commerceShippingFixedOptionId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the c shipping fixed option rels before and after the current c shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param CShippingFixedOptionRelId the primary key of the current c shipping fixed option rel
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next c shipping fixed option rel
	 * @throws NoSuchCShippingFixedOptionRelException if a c shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CShippingFixedOptionRel[] findByCommerceShippingFixedOptionId_PrevAndNext(
		long CShippingFixedOptionRelId, long commerceShippingFixedOptionId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator)
		throws NoSuchCShippingFixedOptionRelException {
		CShippingFixedOptionRel cShippingFixedOptionRel = findByPrimaryKey(CShippingFixedOptionRelId);

		Session session = null;

		try {
			session = openSession();

			CShippingFixedOptionRel[] array = new CShippingFixedOptionRelImpl[3];

			array[0] = getByCommerceShippingFixedOptionId_PrevAndNext(session,
					cShippingFixedOptionRel, commerceShippingFixedOptionId,
					orderByComparator, true);

			array[1] = cShippingFixedOptionRel;

			array[2] = getByCommerceShippingFixedOptionId_PrevAndNext(session,
					cShippingFixedOptionRel, commerceShippingFixedOptionId,
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

	protected CShippingFixedOptionRel getByCommerceShippingFixedOptionId_PrevAndNext(
		Session session, CShippingFixedOptionRel cShippingFixedOptionRel,
		long commerceShippingFixedOptionId,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator,
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

		query.append(_SQL_SELECT_CSHIPPINGFIXEDOPTIONREL_WHERE);

		query.append(_FINDER_COLUMN_COMMERCESHIPPINGFIXEDOPTIONID_COMMERCESHIPPINGFIXEDOPTIONID_2);

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
			query.append(CShippingFixedOptionRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceShippingFixedOptionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cShippingFixedOptionRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CShippingFixedOptionRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the c shipping fixed option rels where commerceShippingFixedOptionId = &#63; from the database.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 */
	@Override
	public void removeByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {
		for (CShippingFixedOptionRel cShippingFixedOptionRel : findByCommerceShippingFixedOptionId(
				commerceShippingFixedOptionId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(cShippingFixedOptionRel);
		}
	}

	/**
	 * Returns the number of c shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the number of matching c shipping fixed option rels
	 */
	@Override
	public int countByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCESHIPPINGFIXEDOPTIONID;

		Object[] finderArgs = new Object[] { commerceShippingFixedOptionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CSHIPPINGFIXEDOPTIONREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCESHIPPINGFIXEDOPTIONID_COMMERCESHIPPINGFIXEDOPTIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceShippingFixedOptionId);

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

	private static final String _FINDER_COLUMN_COMMERCESHIPPINGFIXEDOPTIONID_COMMERCESHIPPINGFIXEDOPTIONID_2 =
		"cShippingFixedOptionRel.commerceShippingFixedOptionId = ?";

	public CShippingFixedOptionRelPersistenceImpl() {
		setModelClass(CShippingFixedOptionRel.class);
	}

	/**
	 * Caches the c shipping fixed option rel in the entity cache if it is enabled.
	 *
	 * @param cShippingFixedOptionRel the c shipping fixed option rel
	 */
	@Override
	public void cacheResult(CShippingFixedOptionRel cShippingFixedOptionRel) {
		entityCache.putResult(CShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CShippingFixedOptionRelImpl.class,
			cShippingFixedOptionRel.getPrimaryKey(), cShippingFixedOptionRel);

		cShippingFixedOptionRel.resetOriginalValues();
	}

	/**
	 * Caches the c shipping fixed option rels in the entity cache if it is enabled.
	 *
	 * @param cShippingFixedOptionRels the c shipping fixed option rels
	 */
	@Override
	public void cacheResult(
		List<CShippingFixedOptionRel> cShippingFixedOptionRels) {
		for (CShippingFixedOptionRel cShippingFixedOptionRel : cShippingFixedOptionRels) {
			if (entityCache.getResult(
						CShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
						CShippingFixedOptionRelImpl.class,
						cShippingFixedOptionRel.getPrimaryKey()) == null) {
				cacheResult(cShippingFixedOptionRel);
			}
			else {
				cShippingFixedOptionRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all c shipping fixed option rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CShippingFixedOptionRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the c shipping fixed option rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CShippingFixedOptionRel cShippingFixedOptionRel) {
		entityCache.removeResult(CShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CShippingFixedOptionRelImpl.class,
			cShippingFixedOptionRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<CShippingFixedOptionRel> cShippingFixedOptionRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CShippingFixedOptionRel cShippingFixedOptionRel : cShippingFixedOptionRels) {
			entityCache.removeResult(CShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
				CShippingFixedOptionRelImpl.class,
				cShippingFixedOptionRel.getPrimaryKey());
		}
	}

	/**
	 * Creates a new c shipping fixed option rel with the primary key. Does not add the c shipping fixed option rel to the database.
	 *
	 * @param CShippingFixedOptionRelId the primary key for the new c shipping fixed option rel
	 * @return the new c shipping fixed option rel
	 */
	@Override
	public CShippingFixedOptionRel create(long CShippingFixedOptionRelId) {
		CShippingFixedOptionRel cShippingFixedOptionRel = new CShippingFixedOptionRelImpl();

		cShippingFixedOptionRel.setNew(true);
		cShippingFixedOptionRel.setPrimaryKey(CShippingFixedOptionRelId);

		cShippingFixedOptionRel.setCompanyId(companyProvider.getCompanyId());

		return cShippingFixedOptionRel;
	}

	/**
	 * Removes the c shipping fixed option rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CShippingFixedOptionRelId the primary key of the c shipping fixed option rel
	 * @return the c shipping fixed option rel that was removed
	 * @throws NoSuchCShippingFixedOptionRelException if a c shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CShippingFixedOptionRel remove(long CShippingFixedOptionRelId)
		throws NoSuchCShippingFixedOptionRelException {
		return remove((Serializable)CShippingFixedOptionRelId);
	}

	/**
	 * Removes the c shipping fixed option rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the c shipping fixed option rel
	 * @return the c shipping fixed option rel that was removed
	 * @throws NoSuchCShippingFixedOptionRelException if a c shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CShippingFixedOptionRel remove(Serializable primaryKey)
		throws NoSuchCShippingFixedOptionRelException {
		Session session = null;

		try {
			session = openSession();

			CShippingFixedOptionRel cShippingFixedOptionRel = (CShippingFixedOptionRel)session.get(CShippingFixedOptionRelImpl.class,
					primaryKey);

			if (cShippingFixedOptionRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCShippingFixedOptionRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cShippingFixedOptionRel);
		}
		catch (NoSuchCShippingFixedOptionRelException nsee) {
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
	protected CShippingFixedOptionRel removeImpl(
		CShippingFixedOptionRel cShippingFixedOptionRel) {
		cShippingFixedOptionRel = toUnwrappedModel(cShippingFixedOptionRel);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cShippingFixedOptionRel)) {
				cShippingFixedOptionRel = (CShippingFixedOptionRel)session.get(CShippingFixedOptionRelImpl.class,
						cShippingFixedOptionRel.getPrimaryKeyObj());
			}

			if (cShippingFixedOptionRel != null) {
				session.delete(cShippingFixedOptionRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cShippingFixedOptionRel != null) {
			clearCache(cShippingFixedOptionRel);
		}

		return cShippingFixedOptionRel;
	}

	@Override
	public CShippingFixedOptionRel updateImpl(
		CShippingFixedOptionRel cShippingFixedOptionRel) {
		cShippingFixedOptionRel = toUnwrappedModel(cShippingFixedOptionRel);

		boolean isNew = cShippingFixedOptionRel.isNew();

		CShippingFixedOptionRelModelImpl cShippingFixedOptionRelModelImpl = (CShippingFixedOptionRelModelImpl)cShippingFixedOptionRel;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cShippingFixedOptionRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				cShippingFixedOptionRel.setCreateDate(now);
			}
			else {
				cShippingFixedOptionRel.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!cShippingFixedOptionRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cShippingFixedOptionRel.setModifiedDate(now);
			}
			else {
				cShippingFixedOptionRel.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cShippingFixedOptionRel.isNew()) {
				session.save(cShippingFixedOptionRel);

				cShippingFixedOptionRel.setNew(false);
			}
			else {
				cShippingFixedOptionRel = (CShippingFixedOptionRel)session.merge(cShippingFixedOptionRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CShippingFixedOptionRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					cShippingFixedOptionRelModelImpl.getCommerceShippingMethodId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCESHIPPINGMETHODID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID,
				args);

			args = new Object[] {
					cShippingFixedOptionRelModelImpl.getCommerceShippingFixedOptionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCESHIPPINGFIXEDOPTIONID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGFIXEDOPTIONID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cShippingFixedOptionRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cShippingFixedOptionRelModelImpl.getOriginalCommerceShippingMethodId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCESHIPPINGMETHODID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID,
					args);

				args = new Object[] {
						cShippingFixedOptionRelModelImpl.getCommerceShippingMethodId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCESHIPPINGMETHODID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID,
					args);
			}

			if ((cShippingFixedOptionRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGFIXEDOPTIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cShippingFixedOptionRelModelImpl.getOriginalCommerceShippingFixedOptionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCESHIPPINGFIXEDOPTIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGFIXEDOPTIONID,
					args);

				args = new Object[] {
						cShippingFixedOptionRelModelImpl.getCommerceShippingFixedOptionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCESHIPPINGFIXEDOPTIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGFIXEDOPTIONID,
					args);
			}
		}

		entityCache.putResult(CShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CShippingFixedOptionRelImpl.class,
			cShippingFixedOptionRel.getPrimaryKey(), cShippingFixedOptionRel,
			false);

		cShippingFixedOptionRel.resetOriginalValues();

		return cShippingFixedOptionRel;
	}

	protected CShippingFixedOptionRel toUnwrappedModel(
		CShippingFixedOptionRel cShippingFixedOptionRel) {
		if (cShippingFixedOptionRel instanceof CShippingFixedOptionRelImpl) {
			return cShippingFixedOptionRel;
		}

		CShippingFixedOptionRelImpl cShippingFixedOptionRelImpl = new CShippingFixedOptionRelImpl();

		cShippingFixedOptionRelImpl.setNew(cShippingFixedOptionRel.isNew());
		cShippingFixedOptionRelImpl.setPrimaryKey(cShippingFixedOptionRel.getPrimaryKey());

		cShippingFixedOptionRelImpl.setCShippingFixedOptionRelId(cShippingFixedOptionRel.getCShippingFixedOptionRelId());
		cShippingFixedOptionRelImpl.setGroupId(cShippingFixedOptionRel.getGroupId());
		cShippingFixedOptionRelImpl.setCompanyId(cShippingFixedOptionRel.getCompanyId());
		cShippingFixedOptionRelImpl.setUserId(cShippingFixedOptionRel.getUserId());
		cShippingFixedOptionRelImpl.setUserName(cShippingFixedOptionRel.getUserName());
		cShippingFixedOptionRelImpl.setCreateDate(cShippingFixedOptionRel.getCreateDate());
		cShippingFixedOptionRelImpl.setModifiedDate(cShippingFixedOptionRel.getModifiedDate());
		cShippingFixedOptionRelImpl.setCommerceShippingMethodId(cShippingFixedOptionRel.getCommerceShippingMethodId());
		cShippingFixedOptionRelImpl.setCommerceShippingFixedOptionId(cShippingFixedOptionRel.getCommerceShippingFixedOptionId());
		cShippingFixedOptionRelImpl.setCommerceWarehouseId(cShippingFixedOptionRel.getCommerceWarehouseId());
		cShippingFixedOptionRelImpl.setCommerceCountryId(cShippingFixedOptionRel.getCommerceCountryId());
		cShippingFixedOptionRelImpl.setCommerceRegionId(cShippingFixedOptionRel.getCommerceRegionId());
		cShippingFixedOptionRelImpl.setZip(cShippingFixedOptionRel.getZip());
		cShippingFixedOptionRelImpl.setWeightFrom(cShippingFixedOptionRel.getWeightFrom());
		cShippingFixedOptionRelImpl.setWeightTo(cShippingFixedOptionRel.getWeightTo());
		cShippingFixedOptionRelImpl.setFixedPrice(cShippingFixedOptionRel.getFixedPrice());
		cShippingFixedOptionRelImpl.setRateUnitWeightPrice(cShippingFixedOptionRel.getRateUnitWeightPrice());
		cShippingFixedOptionRelImpl.setRatePercentage(cShippingFixedOptionRel.getRatePercentage());

		return cShippingFixedOptionRelImpl;
	}

	/**
	 * Returns the c shipping fixed option rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the c shipping fixed option rel
	 * @return the c shipping fixed option rel
	 * @throws NoSuchCShippingFixedOptionRelException if a c shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CShippingFixedOptionRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCShippingFixedOptionRelException {
		CShippingFixedOptionRel cShippingFixedOptionRel = fetchByPrimaryKey(primaryKey);

		if (cShippingFixedOptionRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCShippingFixedOptionRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cShippingFixedOptionRel;
	}

	/**
	 * Returns the c shipping fixed option rel with the primary key or throws a {@link NoSuchCShippingFixedOptionRelException} if it could not be found.
	 *
	 * @param CShippingFixedOptionRelId the primary key of the c shipping fixed option rel
	 * @return the c shipping fixed option rel
	 * @throws NoSuchCShippingFixedOptionRelException if a c shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CShippingFixedOptionRel findByPrimaryKey(
		long CShippingFixedOptionRelId)
		throws NoSuchCShippingFixedOptionRelException {
		return findByPrimaryKey((Serializable)CShippingFixedOptionRelId);
	}

	/**
	 * Returns the c shipping fixed option rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the c shipping fixed option rel
	 * @return the c shipping fixed option rel, or <code>null</code> if a c shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CShippingFixedOptionRel fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
				CShippingFixedOptionRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CShippingFixedOptionRel cShippingFixedOptionRel = (CShippingFixedOptionRel)serializable;

		if (cShippingFixedOptionRel == null) {
			Session session = null;

			try {
				session = openSession();

				cShippingFixedOptionRel = (CShippingFixedOptionRel)session.get(CShippingFixedOptionRelImpl.class,
						primaryKey);

				if (cShippingFixedOptionRel != null) {
					cacheResult(cShippingFixedOptionRel);
				}
				else {
					entityCache.putResult(CShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
						CShippingFixedOptionRelImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
					CShippingFixedOptionRelImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cShippingFixedOptionRel;
	}

	/**
	 * Returns the c shipping fixed option rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CShippingFixedOptionRelId the primary key of the c shipping fixed option rel
	 * @return the c shipping fixed option rel, or <code>null</code> if a c shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CShippingFixedOptionRel fetchByPrimaryKey(
		long CShippingFixedOptionRelId) {
		return fetchByPrimaryKey((Serializable)CShippingFixedOptionRelId);
	}

	@Override
	public Map<Serializable, CShippingFixedOptionRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CShippingFixedOptionRel> map = new HashMap<Serializable, CShippingFixedOptionRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CShippingFixedOptionRel cShippingFixedOptionRel = fetchByPrimaryKey(primaryKey);

			if (cShippingFixedOptionRel != null) {
				map.put(primaryKey, cShippingFixedOptionRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
					CShippingFixedOptionRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CShippingFixedOptionRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CSHIPPINGFIXEDOPTIONREL_WHERE_PKS_IN);

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

			for (CShippingFixedOptionRel cShippingFixedOptionRel : (List<CShippingFixedOptionRel>)q.list()) {
				map.put(cShippingFixedOptionRel.getPrimaryKeyObj(),
					cShippingFixedOptionRel);

				cacheResult(cShippingFixedOptionRel);

				uncachedPrimaryKeys.remove(cShippingFixedOptionRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
					CShippingFixedOptionRelImpl.class, primaryKey, nullModel);
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
	 * Returns all the c shipping fixed option rels.
	 *
	 * @return the c shipping fixed option rels
	 */
	@Override
	public List<CShippingFixedOptionRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the c shipping fixed option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of c shipping fixed option rels
	 * @param end the upper bound of the range of c shipping fixed option rels (not inclusive)
	 * @return the range of c shipping fixed option rels
	 */
	@Override
	public List<CShippingFixedOptionRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the c shipping fixed option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of c shipping fixed option rels
	 * @param end the upper bound of the range of c shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of c shipping fixed option rels
	 */
	@Override
	public List<CShippingFixedOptionRel> findAll(int start, int end,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the c shipping fixed option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of c shipping fixed option rels
	 * @param end the upper bound of the range of c shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of c shipping fixed option rels
	 */
	@Override
	public List<CShippingFixedOptionRel> findAll(int start, int end,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator,
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

		List<CShippingFixedOptionRel> list = null;

		if (retrieveFromCache) {
			list = (List<CShippingFixedOptionRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CSHIPPINGFIXEDOPTIONREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CSHIPPINGFIXEDOPTIONREL;

				if (pagination) {
					sql = sql.concat(CShippingFixedOptionRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CShippingFixedOptionRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CShippingFixedOptionRel>)QueryUtil.list(q,
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
	 * Removes all the c shipping fixed option rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CShippingFixedOptionRel cShippingFixedOptionRel : findAll()) {
			remove(cShippingFixedOptionRel);
		}
	}

	/**
	 * Returns the number of c shipping fixed option rels.
	 *
	 * @return the number of c shipping fixed option rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CSHIPPINGFIXEDOPTIONREL);

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
		return CShippingFixedOptionRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the c shipping fixed option rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CShippingFixedOptionRelImpl.class.getName());
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
	private static final String _SQL_SELECT_CSHIPPINGFIXEDOPTIONREL = "SELECT cShippingFixedOptionRel FROM CShippingFixedOptionRel cShippingFixedOptionRel";
	private static final String _SQL_SELECT_CSHIPPINGFIXEDOPTIONREL_WHERE_PKS_IN =
		"SELECT cShippingFixedOptionRel FROM CShippingFixedOptionRel cShippingFixedOptionRel WHERE CShippingFixedOptionRelId IN (";
	private static final String _SQL_SELECT_CSHIPPINGFIXEDOPTIONREL_WHERE = "SELECT cShippingFixedOptionRel FROM CShippingFixedOptionRel cShippingFixedOptionRel WHERE ";
	private static final String _SQL_COUNT_CSHIPPINGFIXEDOPTIONREL = "SELECT COUNT(cShippingFixedOptionRel) FROM CShippingFixedOptionRel cShippingFixedOptionRel";
	private static final String _SQL_COUNT_CSHIPPINGFIXEDOPTIONREL_WHERE = "SELECT COUNT(cShippingFixedOptionRel) FROM CShippingFixedOptionRel cShippingFixedOptionRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cShippingFixedOptionRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CShippingFixedOptionRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CShippingFixedOptionRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CShippingFixedOptionRelPersistenceImpl.class);
}