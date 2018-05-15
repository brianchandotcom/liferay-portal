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

package com.liferay.commerce.discount.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.discount.exception.NoSuchDiscountUserSegmentRelException;
import com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel;
import com.liferay.commerce.discount.model.impl.CommerceDiscountUserSegmentRelImpl;
import com.liferay.commerce.discount.model.impl.CommerceDiscountUserSegmentRelModelImpl;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountUserSegmentRelPersistence;

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
 * The persistence implementation for the commerce discount user segment rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountUserSegmentRelPersistence
 * @see com.liferay.commerce.discount.service.persistence.CommerceDiscountUserSegmentRelUtil
 * @generated
 */
@ProviderType
public class CommerceDiscountUserSegmentRelPersistenceImpl
	extends BasePersistenceImpl<CommerceDiscountUserSegmentRel>
	implements CommerceDiscountUserSegmentRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceDiscountUserSegmentRelUtil} to access the commerce discount user segment rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceDiscountUserSegmentRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceDiscountUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUserSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceDiscountUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUserSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceDiscountUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEDISCOUNTID =
		new FinderPath(CommerceDiscountUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUserSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceDiscountId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEDISCOUNTID =
		new FinderPath(CommerceDiscountUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUserSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceDiscountId", new String[] { Long.class.getName() },
			CommerceDiscountUserSegmentRelModelImpl.COMMERCEDISCOUNTID_COLUMN_BITMASK |
			CommerceDiscountUserSegmentRelModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEDISCOUNTID = new FinderPath(CommerceDiscountUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceDiscountId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce discount user segment rels where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount user segment rels
	 */
	@Override
	public List<CommerceDiscountUserSegmentRel> findByCommerceDiscountId(
		long commerceDiscountId) {
		return findByCommerceDiscountId(commerceDiscountId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount user segment rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount user segment rels
	 * @param end the upper bound of the range of commerce discount user segment rels (not inclusive)
	 * @return the range of matching commerce discount user segment rels
	 */
	@Override
	public List<CommerceDiscountUserSegmentRel> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end) {
		return findByCommerceDiscountId(commerceDiscountId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount user segment rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount user segment rels
	 * @param end the upper bound of the range of commerce discount user segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount user segment rels
	 */
	@Override
	public List<CommerceDiscountUserSegmentRel> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator) {
		return findByCommerceDiscountId(commerceDiscountId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount user segment rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount user segment rels
	 * @param end the upper bound of the range of commerce discount user segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce discount user segment rels
	 */
	@Override
	public List<CommerceDiscountUserSegmentRel> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEDISCOUNTID;
			finderArgs = new Object[] { commerceDiscountId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEDISCOUNTID;
			finderArgs = new Object[] {
					commerceDiscountId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceDiscountUserSegmentRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceDiscountUserSegmentRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel : list) {
					if ((commerceDiscountId != commerceDiscountUserSegmentRel.getCommerceDiscountId())) {
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

			query.append(_SQL_SELECT_COMMERCEDISCOUNTUSERSEGMENTREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEDISCOUNTID_COMMERCEDISCOUNTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceDiscountUserSegmentRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceDiscountId);

				if (!pagination) {
					list = (List<CommerceDiscountUserSegmentRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceDiscountUserSegmentRel>)QueryUtil.list(q,
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
	 * Returns the first commerce discount user segment rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount user segment rel
	 * @throws NoSuchDiscountUserSegmentRelException if a matching commerce discount user segment rel could not be found
	 */
	@Override
	public CommerceDiscountUserSegmentRel findByCommerceDiscountId_First(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator)
		throws NoSuchDiscountUserSegmentRelException {
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel = fetchByCommerceDiscountId_First(commerceDiscountId,
				orderByComparator);

		if (commerceDiscountUserSegmentRel != null) {
			return commerceDiscountUserSegmentRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceDiscountId=");
		msg.append(commerceDiscountId);

		msg.append("}");

		throw new NoSuchDiscountUserSegmentRelException(msg.toString());
	}

	/**
	 * Returns the first commerce discount user segment rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount user segment rel, or <code>null</code> if a matching commerce discount user segment rel could not be found
	 */
	@Override
	public CommerceDiscountUserSegmentRel fetchByCommerceDiscountId_First(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator) {
		List<CommerceDiscountUserSegmentRel> list = findByCommerceDiscountId(commerceDiscountId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce discount user segment rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount user segment rel
	 * @throws NoSuchDiscountUserSegmentRelException if a matching commerce discount user segment rel could not be found
	 */
	@Override
	public CommerceDiscountUserSegmentRel findByCommerceDiscountId_Last(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator)
		throws NoSuchDiscountUserSegmentRelException {
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel = fetchByCommerceDiscountId_Last(commerceDiscountId,
				orderByComparator);

		if (commerceDiscountUserSegmentRel != null) {
			return commerceDiscountUserSegmentRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceDiscountId=");
		msg.append(commerceDiscountId);

		msg.append("}");

		throw new NoSuchDiscountUserSegmentRelException(msg.toString());
	}

	/**
	 * Returns the last commerce discount user segment rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount user segment rel, or <code>null</code> if a matching commerce discount user segment rel could not be found
	 */
	@Override
	public CommerceDiscountUserSegmentRel fetchByCommerceDiscountId_Last(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator) {
		int count = countByCommerceDiscountId(commerceDiscountId);

		if (count == 0) {
			return null;
		}

		List<CommerceDiscountUserSegmentRel> list = findByCommerceDiscountId(commerceDiscountId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce discount user segment rels before and after the current commerce discount user segment rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountUserSegmentRelId the primary key of the current commerce discount user segment rel
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount user segment rel
	 * @throws NoSuchDiscountUserSegmentRelException if a commerce discount user segment rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUserSegmentRel[] findByCommerceDiscountId_PrevAndNext(
		long commerceDiscountUserSegmentRelId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator)
		throws NoSuchDiscountUserSegmentRelException {
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel = findByPrimaryKey(commerceDiscountUserSegmentRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountUserSegmentRel[] array = new CommerceDiscountUserSegmentRelImpl[3];

			array[0] = getByCommerceDiscountId_PrevAndNext(session,
					commerceDiscountUserSegmentRel, commerceDiscountId,
					orderByComparator, true);

			array[1] = commerceDiscountUserSegmentRel;

			array[2] = getByCommerceDiscountId_PrevAndNext(session,
					commerceDiscountUserSegmentRel, commerceDiscountId,
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

	protected CommerceDiscountUserSegmentRel getByCommerceDiscountId_PrevAndNext(
		Session session,
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel,
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEDISCOUNTUSERSEGMENTREL_WHERE);

		query.append(_FINDER_COLUMN_COMMERCEDISCOUNTID_COMMERCEDISCOUNTID_2);

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
			query.append(CommerceDiscountUserSegmentRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceDiscountId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceDiscountUserSegmentRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceDiscountUserSegmentRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce discount user segment rels where commerceDiscountId = &#63; from the database.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 */
	@Override
	public void removeByCommerceDiscountId(long commerceDiscountId) {
		for (CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel : findByCommerceDiscountId(
				commerceDiscountId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceDiscountUserSegmentRel);
		}
	}

	/**
	 * Returns the number of commerce discount user segment rels where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the number of matching commerce discount user segment rels
	 */
	@Override
	public int countByCommerceDiscountId(long commerceDiscountId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEDISCOUNTID;

		Object[] finderArgs = new Object[] { commerceDiscountId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEDISCOUNTUSERSEGMENTREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEDISCOUNTID_COMMERCEDISCOUNTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceDiscountId);

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

	private static final String _FINDER_COLUMN_COMMERCEDISCOUNTID_COMMERCEDISCOUNTID_2 =
		"commerceDiscountUserSegmentRel.commerceDiscountId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID =
		new FinderPath(CommerceDiscountUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUserSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceUserSegmentEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID =
		new FinderPath(CommerceDiscountUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUserSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceUserSegmentEntryId",
			new String[] { Long.class.getName() },
			CommerceDiscountUserSegmentRelModelImpl.COMMERCEUSERSEGMENTENTRYID_COLUMN_BITMASK |
			CommerceDiscountUserSegmentRelModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEUSERSEGMENTENTRYID =
		new FinderPath(CommerceDiscountUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceUserSegmentEntryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce discount user segment rels where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @return the matching commerce discount user segment rels
	 */
	@Override
	public List<CommerceDiscountUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		return findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount user segment rels where commerceUserSegmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param start the lower bound of the range of commerce discount user segment rels
	 * @param end the upper bound of the range of commerce discount user segment rels (not inclusive)
	 * @return the range of matching commerce discount user segment rels
	 */
	@Override
	public List<CommerceDiscountUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end) {
		return findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount user segment rels where commerceUserSegmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param start the lower bound of the range of commerce discount user segment rels
	 * @param end the upper bound of the range of commerce discount user segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount user segment rels
	 */
	@Override
	public List<CommerceDiscountUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator) {
		return findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount user segment rels where commerceUserSegmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param start the lower bound of the range of commerce discount user segment rels
	 * @param end the upper bound of the range of commerce discount user segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce discount user segment rels
	 */
	@Override
	public List<CommerceDiscountUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator,
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

		List<CommerceDiscountUserSegmentRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceDiscountUserSegmentRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel : list) {
					if ((commerceUserSegmentEntryId != commerceDiscountUserSegmentRel.getCommerceUserSegmentEntryId())) {
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

			query.append(_SQL_SELECT_COMMERCEDISCOUNTUSERSEGMENTREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEUSERSEGMENTENTRYID_COMMERCEUSERSEGMENTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceDiscountUserSegmentRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceUserSegmentEntryId);

				if (!pagination) {
					list = (List<CommerceDiscountUserSegmentRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceDiscountUserSegmentRel>)QueryUtil.list(q,
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
	 * Returns the first commerce discount user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount user segment rel
	 * @throws NoSuchDiscountUserSegmentRelException if a matching commerce discount user segment rel could not be found
	 */
	@Override
	public CommerceDiscountUserSegmentRel findByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator)
		throws NoSuchDiscountUserSegmentRelException {
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel = fetchByCommerceUserSegmentEntryId_First(commerceUserSegmentEntryId,
				orderByComparator);

		if (commerceDiscountUserSegmentRel != null) {
			return commerceDiscountUserSegmentRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceUserSegmentEntryId=");
		msg.append(commerceUserSegmentEntryId);

		msg.append("}");

		throw new NoSuchDiscountUserSegmentRelException(msg.toString());
	}

	/**
	 * Returns the first commerce discount user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount user segment rel, or <code>null</code> if a matching commerce discount user segment rel could not be found
	 */
	@Override
	public CommerceDiscountUserSegmentRel fetchByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator) {
		List<CommerceDiscountUserSegmentRel> list = findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce discount user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount user segment rel
	 * @throws NoSuchDiscountUserSegmentRelException if a matching commerce discount user segment rel could not be found
	 */
	@Override
	public CommerceDiscountUserSegmentRel findByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator)
		throws NoSuchDiscountUserSegmentRelException {
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel = fetchByCommerceUserSegmentEntryId_Last(commerceUserSegmentEntryId,
				orderByComparator);

		if (commerceDiscountUserSegmentRel != null) {
			return commerceDiscountUserSegmentRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceUserSegmentEntryId=");
		msg.append(commerceUserSegmentEntryId);

		msg.append("}");

		throw new NoSuchDiscountUserSegmentRelException(msg.toString());
	}

	/**
	 * Returns the last commerce discount user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount user segment rel, or <code>null</code> if a matching commerce discount user segment rel could not be found
	 */
	@Override
	public CommerceDiscountUserSegmentRel fetchByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator) {
		int count = countByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);

		if (count == 0) {
			return null;
		}

		List<CommerceDiscountUserSegmentRel> list = findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce discount user segment rels before and after the current commerce discount user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceDiscountUserSegmentRelId the primary key of the current commerce discount user segment rel
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount user segment rel
	 * @throws NoSuchDiscountUserSegmentRelException if a commerce discount user segment rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUserSegmentRel[] findByCommerceUserSegmentEntryId_PrevAndNext(
		long commerceDiscountUserSegmentRelId, long commerceUserSegmentEntryId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator)
		throws NoSuchDiscountUserSegmentRelException {
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel = findByPrimaryKey(commerceDiscountUserSegmentRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountUserSegmentRel[] array = new CommerceDiscountUserSegmentRelImpl[3];

			array[0] = getByCommerceUserSegmentEntryId_PrevAndNext(session,
					commerceDiscountUserSegmentRel, commerceUserSegmentEntryId,
					orderByComparator, true);

			array[1] = commerceDiscountUserSegmentRel;

			array[2] = getByCommerceUserSegmentEntryId_PrevAndNext(session,
					commerceDiscountUserSegmentRel, commerceUserSegmentEntryId,
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

	protected CommerceDiscountUserSegmentRel getByCommerceUserSegmentEntryId_PrevAndNext(
		Session session,
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel,
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEDISCOUNTUSERSEGMENTREL_WHERE);

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
			query.append(CommerceDiscountUserSegmentRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceUserSegmentEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceDiscountUserSegmentRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceDiscountUserSegmentRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce discount user segment rels where commerceUserSegmentEntryId = &#63; from the database.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 */
	@Override
	public void removeByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		for (CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel : findByCommerceUserSegmentEntryId(
				commerceUserSegmentEntryId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(commerceDiscountUserSegmentRel);
		}
	}

	/**
	 * Returns the number of commerce discount user segment rels where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @return the number of matching commerce discount user segment rels
	 */
	@Override
	public int countByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEUSERSEGMENTENTRYID;

		Object[] finderArgs = new Object[] { commerceUserSegmentEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEDISCOUNTUSERSEGMENTREL_WHERE);

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
		"commerceDiscountUserSegmentRel.commerceUserSegmentEntryId = ?";

	public CommerceDiscountUserSegmentRelPersistenceImpl() {
		setModelClass(CommerceDiscountUserSegmentRel.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("commerceDiscountUserSegmentRelId",
				"CDiscountUserSegmentRelId");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the commerce discount user segment rel in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountUserSegmentRel the commerce discount user segment rel
	 */
	@Override
	public void cacheResult(
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel) {
		entityCache.putResult(CommerceDiscountUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUserSegmentRelImpl.class,
			commerceDiscountUserSegmentRel.getPrimaryKey(),
			commerceDiscountUserSegmentRel);

		commerceDiscountUserSegmentRel.resetOriginalValues();
	}

	/**
	 * Caches the commerce discount user segment rels in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountUserSegmentRels the commerce discount user segment rels
	 */
	@Override
	public void cacheResult(
		List<CommerceDiscountUserSegmentRel> commerceDiscountUserSegmentRels) {
		for (CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel : commerceDiscountUserSegmentRels) {
			if (entityCache.getResult(
						CommerceDiscountUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
						CommerceDiscountUserSegmentRelImpl.class,
						commerceDiscountUserSegmentRel.getPrimaryKey()) == null) {
				cacheResult(commerceDiscountUserSegmentRel);
			}
			else {
				commerceDiscountUserSegmentRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce discount user segment rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceDiscountUserSegmentRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce discount user segment rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel) {
		entityCache.removeResult(CommerceDiscountUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUserSegmentRelImpl.class,
			commerceDiscountUserSegmentRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<CommerceDiscountUserSegmentRel> commerceDiscountUserSegmentRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel : commerceDiscountUserSegmentRels) {
			entityCache.removeResult(CommerceDiscountUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
				CommerceDiscountUserSegmentRelImpl.class,
				commerceDiscountUserSegmentRel.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce discount user segment rel with the primary key. Does not add the commerce discount user segment rel to the database.
	 *
	 * @param commerceDiscountUserSegmentRelId the primary key for the new commerce discount user segment rel
	 * @return the new commerce discount user segment rel
	 */
	@Override
	public CommerceDiscountUserSegmentRel create(
		long commerceDiscountUserSegmentRelId) {
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel = new CommerceDiscountUserSegmentRelImpl();

		commerceDiscountUserSegmentRel.setNew(true);
		commerceDiscountUserSegmentRel.setPrimaryKey(commerceDiscountUserSegmentRelId);

		commerceDiscountUserSegmentRel.setCompanyId(companyProvider.getCompanyId());

		return commerceDiscountUserSegmentRel;
	}

	/**
	 * Removes the commerce discount user segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDiscountUserSegmentRelId the primary key of the commerce discount user segment rel
	 * @return the commerce discount user segment rel that was removed
	 * @throws NoSuchDiscountUserSegmentRelException if a commerce discount user segment rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUserSegmentRel remove(
		long commerceDiscountUserSegmentRelId)
		throws NoSuchDiscountUserSegmentRelException {
		return remove((Serializable)commerceDiscountUserSegmentRelId);
	}

	/**
	 * Removes the commerce discount user segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce discount user segment rel
	 * @return the commerce discount user segment rel that was removed
	 * @throws NoSuchDiscountUserSegmentRelException if a commerce discount user segment rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUserSegmentRel remove(Serializable primaryKey)
		throws NoSuchDiscountUserSegmentRelException {
		Session session = null;

		try {
			session = openSession();

			CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel = (CommerceDiscountUserSegmentRel)session.get(CommerceDiscountUserSegmentRelImpl.class,
					primaryKey);

			if (commerceDiscountUserSegmentRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDiscountUserSegmentRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceDiscountUserSegmentRel);
		}
		catch (NoSuchDiscountUserSegmentRelException nsee) {
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
	protected CommerceDiscountUserSegmentRel removeImpl(
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceDiscountUserSegmentRel)) {
				commerceDiscountUserSegmentRel = (CommerceDiscountUserSegmentRel)session.get(CommerceDiscountUserSegmentRelImpl.class,
						commerceDiscountUserSegmentRel.getPrimaryKeyObj());
			}

			if (commerceDiscountUserSegmentRel != null) {
				session.delete(commerceDiscountUserSegmentRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceDiscountUserSegmentRel != null) {
			clearCache(commerceDiscountUserSegmentRel);
		}

		return commerceDiscountUserSegmentRel;
	}

	@Override
	public CommerceDiscountUserSegmentRel updateImpl(
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel) {
		boolean isNew = commerceDiscountUserSegmentRel.isNew();

		if (!(commerceDiscountUserSegmentRel instanceof CommerceDiscountUserSegmentRelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
						commerceDiscountUserSegmentRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(commerceDiscountUserSegmentRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceDiscountUserSegmentRel proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceDiscountUserSegmentRel implementation " +
				commerceDiscountUserSegmentRel.getClass());
		}

		CommerceDiscountUserSegmentRelModelImpl commerceDiscountUserSegmentRelModelImpl =
			(CommerceDiscountUserSegmentRelModelImpl)commerceDiscountUserSegmentRel;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceDiscountUserSegmentRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceDiscountUserSegmentRel.setCreateDate(now);
			}
			else {
				commerceDiscountUserSegmentRel.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceDiscountUserSegmentRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceDiscountUserSegmentRel.setModifiedDate(now);
			}
			else {
				commerceDiscountUserSegmentRel.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceDiscountUserSegmentRel.isNew()) {
				session.save(commerceDiscountUserSegmentRel);

				commerceDiscountUserSegmentRel.setNew(false);
			}
			else {
				commerceDiscountUserSegmentRel = (CommerceDiscountUserSegmentRel)session.merge(commerceDiscountUserSegmentRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceDiscountUserSegmentRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceDiscountUserSegmentRelModelImpl.getCommerceDiscountId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEDISCOUNTID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEDISCOUNTID,
				args);

			args = new Object[] {
					commerceDiscountUserSegmentRelModelImpl.getCommerceUserSegmentEntryId()
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
			if ((commerceDiscountUserSegmentRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEDISCOUNTID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceDiscountUserSegmentRelModelImpl.getOriginalCommerceDiscountId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEDISCOUNTID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEDISCOUNTID,
					args);

				args = new Object[] {
						commerceDiscountUserSegmentRelModelImpl.getCommerceDiscountId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEDISCOUNTID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEDISCOUNTID,
					args);
			}

			if ((commerceDiscountUserSegmentRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceDiscountUserSegmentRelModelImpl.getOriginalCommerceUserSegmentEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEUSERSEGMENTENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID,
					args);

				args = new Object[] {
						commerceDiscountUserSegmentRelModelImpl.getCommerceUserSegmentEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEUSERSEGMENTENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID,
					args);
			}
		}

		entityCache.putResult(CommerceDiscountUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUserSegmentRelImpl.class,
			commerceDiscountUserSegmentRel.getPrimaryKey(),
			commerceDiscountUserSegmentRel, false);

		commerceDiscountUserSegmentRel.resetOriginalValues();

		return commerceDiscountUserSegmentRel;
	}

	/**
	 * Returns the commerce discount user segment rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce discount user segment rel
	 * @return the commerce discount user segment rel
	 * @throws NoSuchDiscountUserSegmentRelException if a commerce discount user segment rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUserSegmentRel findByPrimaryKey(
		Serializable primaryKey) throws NoSuchDiscountUserSegmentRelException {
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel = fetchByPrimaryKey(primaryKey);

		if (commerceDiscountUserSegmentRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDiscountUserSegmentRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceDiscountUserSegmentRel;
	}

	/**
	 * Returns the commerce discount user segment rel with the primary key or throws a {@link NoSuchDiscountUserSegmentRelException} if it could not be found.
	 *
	 * @param commerceDiscountUserSegmentRelId the primary key of the commerce discount user segment rel
	 * @return the commerce discount user segment rel
	 * @throws NoSuchDiscountUserSegmentRelException if a commerce discount user segment rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUserSegmentRel findByPrimaryKey(
		long commerceDiscountUserSegmentRelId)
		throws NoSuchDiscountUserSegmentRelException {
		return findByPrimaryKey((Serializable)commerceDiscountUserSegmentRelId);
	}

	/**
	 * Returns the commerce discount user segment rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce discount user segment rel
	 * @return the commerce discount user segment rel, or <code>null</code> if a commerce discount user segment rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUserSegmentRel fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceDiscountUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
				CommerceDiscountUserSegmentRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel = (CommerceDiscountUserSegmentRel)serializable;

		if (commerceDiscountUserSegmentRel == null) {
			Session session = null;

			try {
				session = openSession();

				commerceDiscountUserSegmentRel = (CommerceDiscountUserSegmentRel)session.get(CommerceDiscountUserSegmentRelImpl.class,
						primaryKey);

				if (commerceDiscountUserSegmentRel != null) {
					cacheResult(commerceDiscountUserSegmentRel);
				}
				else {
					entityCache.putResult(CommerceDiscountUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
						CommerceDiscountUserSegmentRelImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceDiscountUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceDiscountUserSegmentRelImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceDiscountUserSegmentRel;
	}

	/**
	 * Returns the commerce discount user segment rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDiscountUserSegmentRelId the primary key of the commerce discount user segment rel
	 * @return the commerce discount user segment rel, or <code>null</code> if a commerce discount user segment rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUserSegmentRel fetchByPrimaryKey(
		long commerceDiscountUserSegmentRelId) {
		return fetchByPrimaryKey((Serializable)commerceDiscountUserSegmentRelId);
	}

	@Override
	public Map<Serializable, CommerceDiscountUserSegmentRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceDiscountUserSegmentRel> map = new HashMap<Serializable, CommerceDiscountUserSegmentRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel = fetchByPrimaryKey(primaryKey);

			if (commerceDiscountUserSegmentRel != null) {
				map.put(primaryKey, commerceDiscountUserSegmentRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceDiscountUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceDiscountUserSegmentRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(CommerceDiscountUserSegmentRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEDISCOUNTUSERSEGMENTREL_WHERE_PKS_IN);

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

			for (CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel : (List<CommerceDiscountUserSegmentRel>)q.list()) {
				map.put(commerceDiscountUserSegmentRel.getPrimaryKeyObj(),
					commerceDiscountUserSegmentRel);

				cacheResult(commerceDiscountUserSegmentRel);

				uncachedPrimaryKeys.remove(commerceDiscountUserSegmentRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceDiscountUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceDiscountUserSegmentRelImpl.class, primaryKey,
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
	 * Returns all the commerce discount user segment rels.
	 *
	 * @return the commerce discount user segment rels
	 */
	@Override
	public List<CommerceDiscountUserSegmentRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount user segment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount user segment rels
	 * @param end the upper bound of the range of commerce discount user segment rels (not inclusive)
	 * @return the range of commerce discount user segment rels
	 */
	@Override
	public List<CommerceDiscountUserSegmentRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount user segment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount user segment rels
	 * @param end the upper bound of the range of commerce discount user segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce discount user segment rels
	 */
	@Override
	public List<CommerceDiscountUserSegmentRel> findAll(int start, int end,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount user segment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount user segment rels
	 * @param end the upper bound of the range of commerce discount user segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce discount user segment rels
	 */
	@Override
	public List<CommerceDiscountUserSegmentRel> findAll(int start, int end,
		OrderByComparator<CommerceDiscountUserSegmentRel> orderByComparator,
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

		List<CommerceDiscountUserSegmentRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceDiscountUserSegmentRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEDISCOUNTUSERSEGMENTREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEDISCOUNTUSERSEGMENTREL;

				if (pagination) {
					sql = sql.concat(CommerceDiscountUserSegmentRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceDiscountUserSegmentRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceDiscountUserSegmentRel>)QueryUtil.list(q,
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
	 * Removes all the commerce discount user segment rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel : findAll()) {
			remove(commerceDiscountUserSegmentRel);
		}
	}

	/**
	 * Returns the number of commerce discount user segment rels.
	 *
	 * @return the number of commerce discount user segment rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEDISCOUNTUSERSEGMENTREL);

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
		return CommerceDiscountUserSegmentRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce discount user segment rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceDiscountUserSegmentRelImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEDISCOUNTUSERSEGMENTREL = "SELECT commerceDiscountUserSegmentRel FROM CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel";
	private static final String _SQL_SELECT_COMMERCEDISCOUNTUSERSEGMENTREL_WHERE_PKS_IN =
		"SELECT commerceDiscountUserSegmentRel FROM CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel WHERE CDiscountUserSegmentRelId IN (";
	private static final String _SQL_SELECT_COMMERCEDISCOUNTUSERSEGMENTREL_WHERE =
		"SELECT commerceDiscountUserSegmentRel FROM CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel WHERE ";
	private static final String _SQL_COUNT_COMMERCEDISCOUNTUSERSEGMENTREL = "SELECT COUNT(commerceDiscountUserSegmentRel) FROM CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel";
	private static final String _SQL_COUNT_COMMERCEDISCOUNTUSERSEGMENTREL_WHERE = "SELECT COUNT(commerceDiscountUserSegmentRel) FROM CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceDiscountUserSegmentRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceDiscountUserSegmentRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceDiscountUserSegmentRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceDiscountUserSegmentRelPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"commerceDiscountUserSegmentRelId"
			});
}