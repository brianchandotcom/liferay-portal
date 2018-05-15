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

import com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionRelException;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionRelImpl;
import com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionRelModelImpl;
import com.liferay.commerce.shipping.engine.fixed.service.persistence.CommerceShippingFixedOptionRelPersistence;

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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
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
 * The persistence implementation for the commerce shipping fixed option rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionRelPersistence
 * @see com.liferay.commerce.shipping.engine.fixed.service.persistence.CommerceShippingFixedOptionRelUtil
 * @generated
 */
@ProviderType
public class CommerceShippingFixedOptionRelPersistenceImpl
	extends BasePersistenceImpl<CommerceShippingFixedOptionRel>
	implements CommerceShippingFixedOptionRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceShippingFixedOptionRelUtil} to access the commerce shipping fixed option rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceShippingFixedOptionRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingFixedOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceShippingFixedOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingFixedOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceShippingFixedOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingFixedOptionRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID =
		new FinderPath(CommerceShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingFixedOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceShippingFixedOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceShippingMethodId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID =
		new FinderPath(CommerceShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingFixedOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceShippingFixedOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceShippingMethodId",
			new String[] { Long.class.getName() },
			CommerceShippingFixedOptionRelModelImpl.COMMERCESHIPPINGMETHODID_COLUMN_BITMASK |
			CommerceShippingFixedOptionRelModelImpl.COMMERCECOUNTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCESHIPPINGMETHODID =
		new FinderPath(CommerceShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingFixedOptionRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceShippingMethodId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @return the matching commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId) {
		return findByCommerceShippingMethodId(commerceShippingMethodId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @return the range of matching commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end) {
		return findByCommerceShippingMethodId(commerceShippingMethodId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator) {
		return findByCommerceShippingMethodId(commerceShippingMethodId, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator,
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

		List<CommerceShippingFixedOptionRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceShippingFixedOptionRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceShippingFixedOptionRel commerceShippingFixedOptionRel : list) {
					if ((commerceShippingMethodId != commerceShippingFixedOptionRel.getCommerceShippingMethodId())) {
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

			query.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCESHIPPINGMETHODID_COMMERCESHIPPINGMETHODID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceShippingFixedOptionRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceShippingMethodId);

				if (!pagination) {
					list = (List<CommerceShippingFixedOptionRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceShippingFixedOptionRel>)QueryUtil.list(q,
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
	 * Returns the first commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a matching commerce shipping fixed option rel could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel findByCommerceShippingMethodId_First(
		long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator)
		throws NoSuchShippingFixedOptionRelException {
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel = fetchByCommerceShippingMethodId_First(commerceShippingMethodId,
				orderByComparator);

		if (commerceShippingFixedOptionRel != null) {
			return commerceShippingFixedOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceShippingMethodId=");
		msg.append(commerceShippingMethodId);

		msg.append("}");

		throw new NoSuchShippingFixedOptionRelException(msg.toString());
	}

	/**
	 * Returns the first commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option rel, or <code>null</code> if a matching commerce shipping fixed option rel could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel fetchByCommerceShippingMethodId_First(
		long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator) {
		List<CommerceShippingFixedOptionRel> list = findByCommerceShippingMethodId(commerceShippingMethodId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a matching commerce shipping fixed option rel could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel findByCommerceShippingMethodId_Last(
		long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator)
		throws NoSuchShippingFixedOptionRelException {
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel = fetchByCommerceShippingMethodId_Last(commerceShippingMethodId,
				orderByComparator);

		if (commerceShippingFixedOptionRel != null) {
			return commerceShippingFixedOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceShippingMethodId=");
		msg.append(commerceShippingMethodId);

		msg.append("}");

		throw new NoSuchShippingFixedOptionRelException(msg.toString());
	}

	/**
	 * Returns the last commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option rel, or <code>null</code> if a matching commerce shipping fixed option rel could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel fetchByCommerceShippingMethodId_Last(
		long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator) {
		int count = countByCommerceShippingMethodId(commerceShippingMethodId);

		if (count == 0) {
			return null;
		}

		List<CommerceShippingFixedOptionRel> list = findByCommerceShippingMethodId(commerceShippingMethodId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce shipping fixed option rels before and after the current commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingFixedOptionRelId the primary key of the current commerce shipping fixed option rel
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel[] findByCommerceShippingMethodId_PrevAndNext(
		long commerceShippingFixedOptionRelId, long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator)
		throws NoSuchShippingFixedOptionRelException {
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel = findByPrimaryKey(commerceShippingFixedOptionRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceShippingFixedOptionRel[] array = new CommerceShippingFixedOptionRelImpl[3];

			array[0] = getByCommerceShippingMethodId_PrevAndNext(session,
					commerceShippingFixedOptionRel, commerceShippingMethodId,
					orderByComparator, true);

			array[1] = commerceShippingFixedOptionRel;

			array[2] = getByCommerceShippingMethodId_PrevAndNext(session,
					commerceShippingFixedOptionRel, commerceShippingMethodId,
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

	protected CommerceShippingFixedOptionRel getByCommerceShippingMethodId_PrevAndNext(
		Session session,
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel,
		long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONREL_WHERE);

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
			query.append(CommerceShippingFixedOptionRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceShippingMethodId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceShippingFixedOptionRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceShippingFixedOptionRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce shipping fixed option rels where commerceShippingMethodId = &#63; from the database.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 */
	@Override
	public void removeByCommerceShippingMethodId(long commerceShippingMethodId) {
		for (CommerceShippingFixedOptionRel commerceShippingFixedOptionRel : findByCommerceShippingMethodId(
				commerceShippingMethodId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(commerceShippingFixedOptionRel);
		}
	}

	/**
	 * Returns the number of commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @return the number of matching commerce shipping fixed option rels
	 */
	@Override
	public int countByCommerceShippingMethodId(long commerceShippingMethodId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCESHIPPINGMETHODID;

		Object[] finderArgs = new Object[] { commerceShippingMethodId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCESHIPPINGFIXEDOPTIONREL_WHERE);

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
		"commerceShippingFixedOptionRel.commerceShippingMethodId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCESHIPPINGFIXEDOPTIONID =
		new FinderPath(CommerceShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingFixedOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceShippingFixedOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceShippingFixedOptionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGFIXEDOPTIONID =
		new FinderPath(CommerceShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingFixedOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceShippingFixedOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceShippingFixedOptionId",
			new String[] { Long.class.getName() },
			CommerceShippingFixedOptionRelModelImpl.COMMERCESHIPPINGFIXEDOPTIONID_COLUMN_BITMASK |
			CommerceShippingFixedOptionRelModelImpl.COMMERCECOUNTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCESHIPPINGFIXEDOPTIONID =
		new FinderPath(CommerceShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingFixedOptionRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceShippingFixedOptionId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the matching commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {
		return findByCommerceShippingFixedOptionId(commerceShippingFixedOptionId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @return the range of matching commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId, int start, int end) {
		return findByCommerceShippingFixedOptionId(commerceShippingFixedOptionId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId, int start, int end,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator) {
		return findByCommerceShippingFixedOptionId(commerceShippingFixedOptionId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel> findByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId, int start, int end,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator,
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

		List<CommerceShippingFixedOptionRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceShippingFixedOptionRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceShippingFixedOptionRel commerceShippingFixedOptionRel : list) {
					if ((commerceShippingFixedOptionId != commerceShippingFixedOptionRel.getCommerceShippingFixedOptionId())) {
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

			query.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCESHIPPINGFIXEDOPTIONID_COMMERCESHIPPINGFIXEDOPTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceShippingFixedOptionRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceShippingFixedOptionId);

				if (!pagination) {
					list = (List<CommerceShippingFixedOptionRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceShippingFixedOptionRel>)QueryUtil.list(q,
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
	 * Returns the first commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a matching commerce shipping fixed option rel could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel findByCommerceShippingFixedOptionId_First(
		long commerceShippingFixedOptionId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator)
		throws NoSuchShippingFixedOptionRelException {
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel = fetchByCommerceShippingFixedOptionId_First(commerceShippingFixedOptionId,
				orderByComparator);

		if (commerceShippingFixedOptionRel != null) {
			return commerceShippingFixedOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceShippingFixedOptionId=");
		msg.append(commerceShippingFixedOptionId);

		msg.append("}");

		throw new NoSuchShippingFixedOptionRelException(msg.toString());
	}

	/**
	 * Returns the first commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option rel, or <code>null</code> if a matching commerce shipping fixed option rel could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel fetchByCommerceShippingFixedOptionId_First(
		long commerceShippingFixedOptionId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator) {
		List<CommerceShippingFixedOptionRel> list = findByCommerceShippingFixedOptionId(commerceShippingFixedOptionId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a matching commerce shipping fixed option rel could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel findByCommerceShippingFixedOptionId_Last(
		long commerceShippingFixedOptionId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator)
		throws NoSuchShippingFixedOptionRelException {
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel = fetchByCommerceShippingFixedOptionId_Last(commerceShippingFixedOptionId,
				orderByComparator);

		if (commerceShippingFixedOptionRel != null) {
			return commerceShippingFixedOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceShippingFixedOptionId=");
		msg.append(commerceShippingFixedOptionId);

		msg.append("}");

		throw new NoSuchShippingFixedOptionRelException(msg.toString());
	}

	/**
	 * Returns the last commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option rel, or <code>null</code> if a matching commerce shipping fixed option rel could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel fetchByCommerceShippingFixedOptionId_Last(
		long commerceShippingFixedOptionId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator) {
		int count = countByCommerceShippingFixedOptionId(commerceShippingFixedOptionId);

		if (count == 0) {
			return null;
		}

		List<CommerceShippingFixedOptionRel> list = findByCommerceShippingFixedOptionId(commerceShippingFixedOptionId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce shipping fixed option rels before and after the current commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionRelId the primary key of the current commerce shipping fixed option rel
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel[] findByCommerceShippingFixedOptionId_PrevAndNext(
		long commerceShippingFixedOptionRelId,
		long commerceShippingFixedOptionId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator)
		throws NoSuchShippingFixedOptionRelException {
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel = findByPrimaryKey(commerceShippingFixedOptionRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceShippingFixedOptionRel[] array = new CommerceShippingFixedOptionRelImpl[3];

			array[0] = getByCommerceShippingFixedOptionId_PrevAndNext(session,
					commerceShippingFixedOptionRel,
					commerceShippingFixedOptionId, orderByComparator, true);

			array[1] = commerceShippingFixedOptionRel;

			array[2] = getByCommerceShippingFixedOptionId_PrevAndNext(session,
					commerceShippingFixedOptionRel,
					commerceShippingFixedOptionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceShippingFixedOptionRel getByCommerceShippingFixedOptionId_PrevAndNext(
		Session session,
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel,
		long commerceShippingFixedOptionId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONREL_WHERE);

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
			query.append(CommerceShippingFixedOptionRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceShippingFixedOptionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceShippingFixedOptionRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceShippingFixedOptionRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63; from the database.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 */
	@Override
	public void removeByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {
		for (CommerceShippingFixedOptionRel commerceShippingFixedOptionRel : findByCommerceShippingFixedOptionId(
				commerceShippingFixedOptionId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(commerceShippingFixedOptionRel);
		}
	}

	/**
	 * Returns the number of commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the number of matching commerce shipping fixed option rels
	 */
	@Override
	public int countByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCESHIPPINGFIXEDOPTIONID;

		Object[] finderArgs = new Object[] { commerceShippingFixedOptionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCESHIPPINGFIXEDOPTIONREL_WHERE);

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
		"commerceShippingFixedOptionRel.commerceShippingFixedOptionId = ?";

	public CommerceShippingFixedOptionRelPersistenceImpl() {
		setModelClass(CommerceShippingFixedOptionRel.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("commerceShippingFixedOptionRelId",
				"CShippingFixedOptionRelId");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the commerce shipping fixed option rel in the entity cache if it is enabled.
	 *
	 * @param commerceShippingFixedOptionRel the commerce shipping fixed option rel
	 */
	@Override
	public void cacheResult(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {
		entityCache.putResult(CommerceShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingFixedOptionRelImpl.class,
			commerceShippingFixedOptionRel.getPrimaryKey(),
			commerceShippingFixedOptionRel);

		commerceShippingFixedOptionRel.resetOriginalValues();
	}

	/**
	 * Caches the commerce shipping fixed option rels in the entity cache if it is enabled.
	 *
	 * @param commerceShippingFixedOptionRels the commerce shipping fixed option rels
	 */
	@Override
	public void cacheResult(
		List<CommerceShippingFixedOptionRel> commerceShippingFixedOptionRels) {
		for (CommerceShippingFixedOptionRel commerceShippingFixedOptionRel : commerceShippingFixedOptionRels) {
			if (entityCache.getResult(
						CommerceShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
						CommerceShippingFixedOptionRelImpl.class,
						commerceShippingFixedOptionRel.getPrimaryKey()) == null) {
				cacheResult(commerceShippingFixedOptionRel);
			}
			else {
				commerceShippingFixedOptionRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce shipping fixed option rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceShippingFixedOptionRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce shipping fixed option rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {
		entityCache.removeResult(CommerceShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingFixedOptionRelImpl.class,
			commerceShippingFixedOptionRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<CommerceShippingFixedOptionRel> commerceShippingFixedOptionRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceShippingFixedOptionRel commerceShippingFixedOptionRel : commerceShippingFixedOptionRels) {
			entityCache.removeResult(CommerceShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
				CommerceShippingFixedOptionRelImpl.class,
				commerceShippingFixedOptionRel.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce shipping fixed option rel with the primary key. Does not add the commerce shipping fixed option rel to the database.
	 *
	 * @param commerceShippingFixedOptionRelId the primary key for the new commerce shipping fixed option rel
	 * @return the new commerce shipping fixed option rel
	 */
	@Override
	public CommerceShippingFixedOptionRel create(
		long commerceShippingFixedOptionRelId) {
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel = new CommerceShippingFixedOptionRelImpl();

		commerceShippingFixedOptionRel.setNew(true);
		commerceShippingFixedOptionRel.setPrimaryKey(commerceShippingFixedOptionRelId);

		commerceShippingFixedOptionRel.setCompanyId(companyProvider.getCompanyId());

		return commerceShippingFixedOptionRel;
	}

	/**
	 * Removes the commerce shipping fixed option rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceShippingFixedOptionRelId the primary key of the commerce shipping fixed option rel
	 * @return the commerce shipping fixed option rel that was removed
	 * @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel remove(
		long commerceShippingFixedOptionRelId)
		throws NoSuchShippingFixedOptionRelException {
		return remove((Serializable)commerceShippingFixedOptionRelId);
	}

	/**
	 * Removes the commerce shipping fixed option rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce shipping fixed option rel
	 * @return the commerce shipping fixed option rel that was removed
	 * @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel remove(Serializable primaryKey)
		throws NoSuchShippingFixedOptionRelException {
		Session session = null;

		try {
			session = openSession();

			CommerceShippingFixedOptionRel commerceShippingFixedOptionRel = (CommerceShippingFixedOptionRel)session.get(CommerceShippingFixedOptionRelImpl.class,
					primaryKey);

			if (commerceShippingFixedOptionRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchShippingFixedOptionRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceShippingFixedOptionRel);
		}
		catch (NoSuchShippingFixedOptionRelException nsee) {
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
	protected CommerceShippingFixedOptionRel removeImpl(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceShippingFixedOptionRel)) {
				commerceShippingFixedOptionRel = (CommerceShippingFixedOptionRel)session.get(CommerceShippingFixedOptionRelImpl.class,
						commerceShippingFixedOptionRel.getPrimaryKeyObj());
			}

			if (commerceShippingFixedOptionRel != null) {
				session.delete(commerceShippingFixedOptionRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceShippingFixedOptionRel != null) {
			clearCache(commerceShippingFixedOptionRel);
		}

		return commerceShippingFixedOptionRel;
	}

	@Override
	public CommerceShippingFixedOptionRel updateImpl(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {
		boolean isNew = commerceShippingFixedOptionRel.isNew();

		if (!(commerceShippingFixedOptionRel instanceof CommerceShippingFixedOptionRelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
						commerceShippingFixedOptionRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(commerceShippingFixedOptionRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceShippingFixedOptionRel proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceShippingFixedOptionRel implementation " +
				commerceShippingFixedOptionRel.getClass());
		}

		CommerceShippingFixedOptionRelModelImpl commerceShippingFixedOptionRelModelImpl =
			(CommerceShippingFixedOptionRelModelImpl)commerceShippingFixedOptionRel;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceShippingFixedOptionRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceShippingFixedOptionRel.setCreateDate(now);
			}
			else {
				commerceShippingFixedOptionRel.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceShippingFixedOptionRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceShippingFixedOptionRel.setModifiedDate(now);
			}
			else {
				commerceShippingFixedOptionRel.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceShippingFixedOptionRel.isNew()) {
				session.save(commerceShippingFixedOptionRel);

				commerceShippingFixedOptionRel.setNew(false);
			}
			else {
				commerceShippingFixedOptionRel = (CommerceShippingFixedOptionRel)session.merge(commerceShippingFixedOptionRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceShippingFixedOptionRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceShippingFixedOptionRelModelImpl.getCommerceShippingMethodId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCESHIPPINGMETHODID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID,
				args);

			args = new Object[] {
					commerceShippingFixedOptionRelModelImpl.getCommerceShippingFixedOptionId()
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
			if ((commerceShippingFixedOptionRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceShippingFixedOptionRelModelImpl.getOriginalCommerceShippingMethodId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCESHIPPINGMETHODID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID,
					args);

				args = new Object[] {
						commerceShippingFixedOptionRelModelImpl.getCommerceShippingMethodId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCESHIPPINGMETHODID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID,
					args);
			}

			if ((commerceShippingFixedOptionRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGFIXEDOPTIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceShippingFixedOptionRelModelImpl.getOriginalCommerceShippingFixedOptionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCESHIPPINGFIXEDOPTIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGFIXEDOPTIONID,
					args);

				args = new Object[] {
						commerceShippingFixedOptionRelModelImpl.getCommerceShippingFixedOptionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCESHIPPINGFIXEDOPTIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGFIXEDOPTIONID,
					args);
			}
		}

		entityCache.putResult(CommerceShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingFixedOptionRelImpl.class,
			commerceShippingFixedOptionRel.getPrimaryKey(),
			commerceShippingFixedOptionRel, false);

		commerceShippingFixedOptionRel.resetOriginalValues();

		return commerceShippingFixedOptionRel;
	}

	/**
	 * Returns the commerce shipping fixed option rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce shipping fixed option rel
	 * @return the commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel findByPrimaryKey(
		Serializable primaryKey) throws NoSuchShippingFixedOptionRelException {
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel = fetchByPrimaryKey(primaryKey);

		if (commerceShippingFixedOptionRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchShippingFixedOptionRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceShippingFixedOptionRel;
	}

	/**
	 * Returns the commerce shipping fixed option rel with the primary key or throws a {@link NoSuchShippingFixedOptionRelException} if it could not be found.
	 *
	 * @param commerceShippingFixedOptionRelId the primary key of the commerce shipping fixed option rel
	 * @return the commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel findByPrimaryKey(
		long commerceShippingFixedOptionRelId)
		throws NoSuchShippingFixedOptionRelException {
		return findByPrimaryKey((Serializable)commerceShippingFixedOptionRelId);
	}

	/**
	 * Returns the commerce shipping fixed option rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce shipping fixed option rel
	 * @return the commerce shipping fixed option rel, or <code>null</code> if a commerce shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
				CommerceShippingFixedOptionRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel = (CommerceShippingFixedOptionRel)serializable;

		if (commerceShippingFixedOptionRel == null) {
			Session session = null;

			try {
				session = openSession();

				commerceShippingFixedOptionRel = (CommerceShippingFixedOptionRel)session.get(CommerceShippingFixedOptionRelImpl.class,
						primaryKey);

				if (commerceShippingFixedOptionRel != null) {
					cacheResult(commerceShippingFixedOptionRel);
				}
				else {
					entityCache.putResult(CommerceShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
						CommerceShippingFixedOptionRelImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceShippingFixedOptionRelImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceShippingFixedOptionRel;
	}

	/**
	 * Returns the commerce shipping fixed option rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceShippingFixedOptionRelId the primary key of the commerce shipping fixed option rel
	 * @return the commerce shipping fixed option rel, or <code>null</code> if a commerce shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel fetchByPrimaryKey(
		long commerceShippingFixedOptionRelId) {
		return fetchByPrimaryKey((Serializable)commerceShippingFixedOptionRelId);
	}

	@Override
	public Map<Serializable, CommerceShippingFixedOptionRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceShippingFixedOptionRel> map = new HashMap<Serializable, CommerceShippingFixedOptionRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceShippingFixedOptionRel commerceShippingFixedOptionRel = fetchByPrimaryKey(primaryKey);

			if (commerceShippingFixedOptionRel != null) {
				map.put(primaryKey, commerceShippingFixedOptionRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceShippingFixedOptionRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(CommerceShippingFixedOptionRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONREL_WHERE_PKS_IN);

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

			for (CommerceShippingFixedOptionRel commerceShippingFixedOptionRel : (List<CommerceShippingFixedOptionRel>)q.list()) {
				map.put(commerceShippingFixedOptionRel.getPrimaryKeyObj(),
					commerceShippingFixedOptionRel);

				cacheResult(commerceShippingFixedOptionRel);

				uncachedPrimaryKeys.remove(commerceShippingFixedOptionRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceShippingFixedOptionRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceShippingFixedOptionRelImpl.class, primaryKey,
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
	 * Returns all the commerce shipping fixed option rels.
	 *
	 * @return the commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipping fixed option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @return the range of commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel> findAll(int start, int end,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel> findAll(int start, int end,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator,
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

		List<CommerceShippingFixedOptionRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceShippingFixedOptionRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONREL;

				if (pagination) {
					sql = sql.concat(CommerceShippingFixedOptionRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceShippingFixedOptionRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceShippingFixedOptionRel>)QueryUtil.list(q,
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
	 * Removes all the commerce shipping fixed option rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceShippingFixedOptionRel commerceShippingFixedOptionRel : findAll()) {
			remove(commerceShippingFixedOptionRel);
		}
	}

	/**
	 * Returns the number of commerce shipping fixed option rels.
	 *
	 * @return the number of commerce shipping fixed option rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCESHIPPINGFIXEDOPTIONREL);

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
		return CommerceShippingFixedOptionRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce shipping fixed option rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceShippingFixedOptionRelImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONREL = "SELECT commerceShippingFixedOptionRel FROM CommerceShippingFixedOptionRel commerceShippingFixedOptionRel";
	private static final String _SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONREL_WHERE_PKS_IN =
		"SELECT commerceShippingFixedOptionRel FROM CommerceShippingFixedOptionRel commerceShippingFixedOptionRel WHERE CShippingFixedOptionRelId IN (";
	private static final String _SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONREL_WHERE =
		"SELECT commerceShippingFixedOptionRel FROM CommerceShippingFixedOptionRel commerceShippingFixedOptionRel WHERE ";
	private static final String _SQL_COUNT_COMMERCESHIPPINGFIXEDOPTIONREL = "SELECT COUNT(commerceShippingFixedOptionRel) FROM CommerceShippingFixedOptionRel commerceShippingFixedOptionRel";
	private static final String _SQL_COUNT_COMMERCESHIPPINGFIXEDOPTIONREL_WHERE = "SELECT COUNT(commerceShippingFixedOptionRel) FROM CommerceShippingFixedOptionRel commerceShippingFixedOptionRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceShippingFixedOptionRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceShippingFixedOptionRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceShippingFixedOptionRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceShippingFixedOptionRelPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"commerceShippingFixedOptionRelId"
			});
}