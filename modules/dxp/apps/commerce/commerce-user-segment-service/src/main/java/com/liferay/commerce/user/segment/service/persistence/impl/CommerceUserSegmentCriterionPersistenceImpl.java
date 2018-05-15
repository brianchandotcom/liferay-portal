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

package com.liferay.commerce.user.segment.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.user.segment.exception.NoSuchUserSegmentCriterionException;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;
import com.liferay.commerce.user.segment.model.impl.CommerceUserSegmentCriterionImpl;
import com.liferay.commerce.user.segment.model.impl.CommerceUserSegmentCriterionModelImpl;
import com.liferay.commerce.user.segment.service.persistence.CommerceUserSegmentCriterionPersistence;

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
 * The persistence implementation for the commerce user segment criterion service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceUserSegmentCriterionPersistence
 * @see com.liferay.commerce.user.segment.service.persistence.CommerceUserSegmentCriterionUtil
 * @generated
 */
@ProviderType
public class CommerceUserSegmentCriterionPersistenceImpl
	extends BasePersistenceImpl<CommerceUserSegmentCriterion>
	implements CommerceUserSegmentCriterionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceUserSegmentCriterionUtil} to access the commerce user segment criterion persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceUserSegmentCriterionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceUserSegmentCriterionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceUserSegmentCriterionModelImpl.FINDER_CACHE_ENABLED,
			CommerceUserSegmentCriterionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceUserSegmentCriterionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceUserSegmentCriterionModelImpl.FINDER_CACHE_ENABLED,
			CommerceUserSegmentCriterionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceUserSegmentCriterionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceUserSegmentCriterionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID =
		new FinderPath(CommerceUserSegmentCriterionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceUserSegmentCriterionModelImpl.FINDER_CACHE_ENABLED,
			CommerceUserSegmentCriterionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceUserSegmentEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID =
		new FinderPath(CommerceUserSegmentCriterionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceUserSegmentCriterionModelImpl.FINDER_CACHE_ENABLED,
			CommerceUserSegmentCriterionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceUserSegmentEntryId",
			new String[] { Long.class.getName() },
			CommerceUserSegmentCriterionModelImpl.COMMERCEUSERSEGMENTENTRYID_COLUMN_BITMASK |
			CommerceUserSegmentCriterionModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEUSERSEGMENTENTRYID =
		new FinderPath(CommerceUserSegmentCriterionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceUserSegmentCriterionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceUserSegmentEntryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce user segment criterions where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @return the matching commerce user segment criterions
	 */
	@Override
	public List<CommerceUserSegmentCriterion> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		return findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce user segment criterions where commerceUserSegmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentCriterionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param start the lower bound of the range of commerce user segment criterions
	 * @param end the upper bound of the range of commerce user segment criterions (not inclusive)
	 * @return the range of matching commerce user segment criterions
	 */
	@Override
	public List<CommerceUserSegmentCriterion> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end) {
		return findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce user segment criterions where commerceUserSegmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentCriterionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param start the lower bound of the range of commerce user segment criterions
	 * @param end the upper bound of the range of commerce user segment criterions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce user segment criterions
	 */
	@Override
	public List<CommerceUserSegmentCriterion> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator) {
		return findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce user segment criterions where commerceUserSegmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentCriterionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param start the lower bound of the range of commerce user segment criterions
	 * @param end the upper bound of the range of commerce user segment criterions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce user segment criterions
	 */
	@Override
	public List<CommerceUserSegmentCriterion> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator,
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

		List<CommerceUserSegmentCriterion> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceUserSegmentCriterion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceUserSegmentCriterion commerceUserSegmentCriterion : list) {
					if ((commerceUserSegmentEntryId != commerceUserSegmentCriterion.getCommerceUserSegmentEntryId())) {
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

			query.append(_SQL_SELECT_COMMERCEUSERSEGMENTCRITERION_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEUSERSEGMENTENTRYID_COMMERCEUSERSEGMENTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceUserSegmentCriterionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceUserSegmentEntryId);

				if (!pagination) {
					list = (List<CommerceUserSegmentCriterion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceUserSegmentCriterion>)QueryUtil.list(q,
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
	 * Returns the first commerce user segment criterion in the ordered set where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce user segment criterion
	 * @throws NoSuchUserSegmentCriterionException if a matching commerce user segment criterion could not be found
	 */
	@Override
	public CommerceUserSegmentCriterion findByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator)
		throws NoSuchUserSegmentCriterionException {
		CommerceUserSegmentCriterion commerceUserSegmentCriterion = fetchByCommerceUserSegmentEntryId_First(commerceUserSegmentEntryId,
				orderByComparator);

		if (commerceUserSegmentCriterion != null) {
			return commerceUserSegmentCriterion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceUserSegmentEntryId=");
		msg.append(commerceUserSegmentEntryId);

		msg.append("}");

		throw new NoSuchUserSegmentCriterionException(msg.toString());
	}

	/**
	 * Returns the first commerce user segment criterion in the ordered set where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce user segment criterion, or <code>null</code> if a matching commerce user segment criterion could not be found
	 */
	@Override
	public CommerceUserSegmentCriterion fetchByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator) {
		List<CommerceUserSegmentCriterion> list = findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce user segment criterion in the ordered set where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce user segment criterion
	 * @throws NoSuchUserSegmentCriterionException if a matching commerce user segment criterion could not be found
	 */
	@Override
	public CommerceUserSegmentCriterion findByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator)
		throws NoSuchUserSegmentCriterionException {
		CommerceUserSegmentCriterion commerceUserSegmentCriterion = fetchByCommerceUserSegmentEntryId_Last(commerceUserSegmentEntryId,
				orderByComparator);

		if (commerceUserSegmentCriterion != null) {
			return commerceUserSegmentCriterion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceUserSegmentEntryId=");
		msg.append(commerceUserSegmentEntryId);

		msg.append("}");

		throw new NoSuchUserSegmentCriterionException(msg.toString());
	}

	/**
	 * Returns the last commerce user segment criterion in the ordered set where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce user segment criterion, or <code>null</code> if a matching commerce user segment criterion could not be found
	 */
	@Override
	public CommerceUserSegmentCriterion fetchByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator) {
		int count = countByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);

		if (count == 0) {
			return null;
		}

		List<CommerceUserSegmentCriterion> list = findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce user segment criterions before and after the current commerce user segment criterion in the ordered set where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentCriterionId the primary key of the current commerce user segment criterion
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce user segment criterion
	 * @throws NoSuchUserSegmentCriterionException if a commerce user segment criterion with the primary key could not be found
	 */
	@Override
	public CommerceUserSegmentCriterion[] findByCommerceUserSegmentEntryId_PrevAndNext(
		long commerceUserSegmentCriterionId, long commerceUserSegmentEntryId,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator)
		throws NoSuchUserSegmentCriterionException {
		CommerceUserSegmentCriterion commerceUserSegmentCriterion = findByPrimaryKey(commerceUserSegmentCriterionId);

		Session session = null;

		try {
			session = openSession();

			CommerceUserSegmentCriterion[] array = new CommerceUserSegmentCriterionImpl[3];

			array[0] = getByCommerceUserSegmentEntryId_PrevAndNext(session,
					commerceUserSegmentCriterion, commerceUserSegmentEntryId,
					orderByComparator, true);

			array[1] = commerceUserSegmentCriterion;

			array[2] = getByCommerceUserSegmentEntryId_PrevAndNext(session,
					commerceUserSegmentCriterion, commerceUserSegmentEntryId,
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

	protected CommerceUserSegmentCriterion getByCommerceUserSegmentEntryId_PrevAndNext(
		Session session,
		CommerceUserSegmentCriterion commerceUserSegmentCriterion,
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEUSERSEGMENTCRITERION_WHERE);

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
			query.append(CommerceUserSegmentCriterionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceUserSegmentEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceUserSegmentCriterion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceUserSegmentCriterion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce user segment criterions where commerceUserSegmentEntryId = &#63; from the database.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 */
	@Override
	public void removeByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		for (CommerceUserSegmentCriterion commerceUserSegmentCriterion : findByCommerceUserSegmentEntryId(
				commerceUserSegmentEntryId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(commerceUserSegmentCriterion);
		}
	}

	/**
	 * Returns the number of commerce user segment criterions where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @return the number of matching commerce user segment criterions
	 */
	@Override
	public int countByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEUSERSEGMENTENTRYID;

		Object[] finderArgs = new Object[] { commerceUserSegmentEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEUSERSEGMENTCRITERION_WHERE);

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
		"commerceUserSegmentCriterion.commerceUserSegmentEntryId = ?";

	public CommerceUserSegmentCriterionPersistenceImpl() {
		setModelClass(CommerceUserSegmentCriterion.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("type", "type_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the commerce user segment criterion in the entity cache if it is enabled.
	 *
	 * @param commerceUserSegmentCriterion the commerce user segment criterion
	 */
	@Override
	public void cacheResult(
		CommerceUserSegmentCriterion commerceUserSegmentCriterion) {
		entityCache.putResult(CommerceUserSegmentCriterionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceUserSegmentCriterionImpl.class,
			commerceUserSegmentCriterion.getPrimaryKey(),
			commerceUserSegmentCriterion);

		commerceUserSegmentCriterion.resetOriginalValues();
	}

	/**
	 * Caches the commerce user segment criterions in the entity cache if it is enabled.
	 *
	 * @param commerceUserSegmentCriterions the commerce user segment criterions
	 */
	@Override
	public void cacheResult(
		List<CommerceUserSegmentCriterion> commerceUserSegmentCriterions) {
		for (CommerceUserSegmentCriterion commerceUserSegmentCriterion : commerceUserSegmentCriterions) {
			if (entityCache.getResult(
						CommerceUserSegmentCriterionModelImpl.ENTITY_CACHE_ENABLED,
						CommerceUserSegmentCriterionImpl.class,
						commerceUserSegmentCriterion.getPrimaryKey()) == null) {
				cacheResult(commerceUserSegmentCriterion);
			}
			else {
				commerceUserSegmentCriterion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce user segment criterions.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceUserSegmentCriterionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce user segment criterion.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceUserSegmentCriterion commerceUserSegmentCriterion) {
		entityCache.removeResult(CommerceUserSegmentCriterionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceUserSegmentCriterionImpl.class,
			commerceUserSegmentCriterion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<CommerceUserSegmentCriterion> commerceUserSegmentCriterions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceUserSegmentCriterion commerceUserSegmentCriterion : commerceUserSegmentCriterions) {
			entityCache.removeResult(CommerceUserSegmentCriterionModelImpl.ENTITY_CACHE_ENABLED,
				CommerceUserSegmentCriterionImpl.class,
				commerceUserSegmentCriterion.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce user segment criterion with the primary key. Does not add the commerce user segment criterion to the database.
	 *
	 * @param commerceUserSegmentCriterionId the primary key for the new commerce user segment criterion
	 * @return the new commerce user segment criterion
	 */
	@Override
	public CommerceUserSegmentCriterion create(
		long commerceUserSegmentCriterionId) {
		CommerceUserSegmentCriterion commerceUserSegmentCriterion = new CommerceUserSegmentCriterionImpl();

		commerceUserSegmentCriterion.setNew(true);
		commerceUserSegmentCriterion.setPrimaryKey(commerceUserSegmentCriterionId);

		commerceUserSegmentCriterion.setCompanyId(companyProvider.getCompanyId());

		return commerceUserSegmentCriterion;
	}

	/**
	 * Removes the commerce user segment criterion with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceUserSegmentCriterionId the primary key of the commerce user segment criterion
	 * @return the commerce user segment criterion that was removed
	 * @throws NoSuchUserSegmentCriterionException if a commerce user segment criterion with the primary key could not be found
	 */
	@Override
	public CommerceUserSegmentCriterion remove(
		long commerceUserSegmentCriterionId)
		throws NoSuchUserSegmentCriterionException {
		return remove((Serializable)commerceUserSegmentCriterionId);
	}

	/**
	 * Removes the commerce user segment criterion with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce user segment criterion
	 * @return the commerce user segment criterion that was removed
	 * @throws NoSuchUserSegmentCriterionException if a commerce user segment criterion with the primary key could not be found
	 */
	@Override
	public CommerceUserSegmentCriterion remove(Serializable primaryKey)
		throws NoSuchUserSegmentCriterionException {
		Session session = null;

		try {
			session = openSession();

			CommerceUserSegmentCriterion commerceUserSegmentCriterion = (CommerceUserSegmentCriterion)session.get(CommerceUserSegmentCriterionImpl.class,
					primaryKey);

			if (commerceUserSegmentCriterion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserSegmentCriterionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceUserSegmentCriterion);
		}
		catch (NoSuchUserSegmentCriterionException nsee) {
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
	protected CommerceUserSegmentCriterion removeImpl(
		CommerceUserSegmentCriterion commerceUserSegmentCriterion) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceUserSegmentCriterion)) {
				commerceUserSegmentCriterion = (CommerceUserSegmentCriterion)session.get(CommerceUserSegmentCriterionImpl.class,
						commerceUserSegmentCriterion.getPrimaryKeyObj());
			}

			if (commerceUserSegmentCriterion != null) {
				session.delete(commerceUserSegmentCriterion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceUserSegmentCriterion != null) {
			clearCache(commerceUserSegmentCriterion);
		}

		return commerceUserSegmentCriterion;
	}

	@Override
	public CommerceUserSegmentCriterion updateImpl(
		CommerceUserSegmentCriterion commerceUserSegmentCriterion) {
		boolean isNew = commerceUserSegmentCriterion.isNew();

		if (!(commerceUserSegmentCriterion instanceof CommerceUserSegmentCriterionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceUserSegmentCriterion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(commerceUserSegmentCriterion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceUserSegmentCriterion proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceUserSegmentCriterion implementation " +
				commerceUserSegmentCriterion.getClass());
		}

		CommerceUserSegmentCriterionModelImpl commerceUserSegmentCriterionModelImpl =
			(CommerceUserSegmentCriterionModelImpl)commerceUserSegmentCriterion;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceUserSegmentCriterion.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceUserSegmentCriterion.setCreateDate(now);
			}
			else {
				commerceUserSegmentCriterion.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceUserSegmentCriterionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceUserSegmentCriterion.setModifiedDate(now);
			}
			else {
				commerceUserSegmentCriterion.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceUserSegmentCriterion.isNew()) {
				session.save(commerceUserSegmentCriterion);

				commerceUserSegmentCriterion.setNew(false);
			}
			else {
				commerceUserSegmentCriterion = (CommerceUserSegmentCriterion)session.merge(commerceUserSegmentCriterion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceUserSegmentCriterionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceUserSegmentCriterionModelImpl.getCommerceUserSegmentEntryId()
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
			if ((commerceUserSegmentCriterionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceUserSegmentCriterionModelImpl.getOriginalCommerceUserSegmentEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEUSERSEGMENTENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID,
					args);

				args = new Object[] {
						commerceUserSegmentCriterionModelImpl.getCommerceUserSegmentEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEUSERSEGMENTENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID,
					args);
			}
		}

		entityCache.putResult(CommerceUserSegmentCriterionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceUserSegmentCriterionImpl.class,
			commerceUserSegmentCriterion.getPrimaryKey(),
			commerceUserSegmentCriterion, false);

		commerceUserSegmentCriterion.resetOriginalValues();

		return commerceUserSegmentCriterion;
	}

	/**
	 * Returns the commerce user segment criterion with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce user segment criterion
	 * @return the commerce user segment criterion
	 * @throws NoSuchUserSegmentCriterionException if a commerce user segment criterion with the primary key could not be found
	 */
	@Override
	public CommerceUserSegmentCriterion findByPrimaryKey(
		Serializable primaryKey) throws NoSuchUserSegmentCriterionException {
		CommerceUserSegmentCriterion commerceUserSegmentCriterion = fetchByPrimaryKey(primaryKey);

		if (commerceUserSegmentCriterion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchUserSegmentCriterionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceUserSegmentCriterion;
	}

	/**
	 * Returns the commerce user segment criterion with the primary key or throws a {@link NoSuchUserSegmentCriterionException} if it could not be found.
	 *
	 * @param commerceUserSegmentCriterionId the primary key of the commerce user segment criterion
	 * @return the commerce user segment criterion
	 * @throws NoSuchUserSegmentCriterionException if a commerce user segment criterion with the primary key could not be found
	 */
	@Override
	public CommerceUserSegmentCriterion findByPrimaryKey(
		long commerceUserSegmentCriterionId)
		throws NoSuchUserSegmentCriterionException {
		return findByPrimaryKey((Serializable)commerceUserSegmentCriterionId);
	}

	/**
	 * Returns the commerce user segment criterion with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce user segment criterion
	 * @return the commerce user segment criterion, or <code>null</code> if a commerce user segment criterion with the primary key could not be found
	 */
	@Override
	public CommerceUserSegmentCriterion fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceUserSegmentCriterionModelImpl.ENTITY_CACHE_ENABLED,
				CommerceUserSegmentCriterionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceUserSegmentCriterion commerceUserSegmentCriterion = (CommerceUserSegmentCriterion)serializable;

		if (commerceUserSegmentCriterion == null) {
			Session session = null;

			try {
				session = openSession();

				commerceUserSegmentCriterion = (CommerceUserSegmentCriterion)session.get(CommerceUserSegmentCriterionImpl.class,
						primaryKey);

				if (commerceUserSegmentCriterion != null) {
					cacheResult(commerceUserSegmentCriterion);
				}
				else {
					entityCache.putResult(CommerceUserSegmentCriterionModelImpl.ENTITY_CACHE_ENABLED,
						CommerceUserSegmentCriterionImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceUserSegmentCriterionModelImpl.ENTITY_CACHE_ENABLED,
					CommerceUserSegmentCriterionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceUserSegmentCriterion;
	}

	/**
	 * Returns the commerce user segment criterion with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceUserSegmentCriterionId the primary key of the commerce user segment criterion
	 * @return the commerce user segment criterion, or <code>null</code> if a commerce user segment criterion with the primary key could not be found
	 */
	@Override
	public CommerceUserSegmentCriterion fetchByPrimaryKey(
		long commerceUserSegmentCriterionId) {
		return fetchByPrimaryKey((Serializable)commerceUserSegmentCriterionId);
	}

	@Override
	public Map<Serializable, CommerceUserSegmentCriterion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceUserSegmentCriterion> map = new HashMap<Serializable, CommerceUserSegmentCriterion>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceUserSegmentCriterion commerceUserSegmentCriterion = fetchByPrimaryKey(primaryKey);

			if (commerceUserSegmentCriterion != null) {
				map.put(primaryKey, commerceUserSegmentCriterion);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceUserSegmentCriterionModelImpl.ENTITY_CACHE_ENABLED,
					CommerceUserSegmentCriterionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(CommerceUserSegmentCriterion)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEUSERSEGMENTCRITERION_WHERE_PKS_IN);

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

			for (CommerceUserSegmentCriterion commerceUserSegmentCriterion : (List<CommerceUserSegmentCriterion>)q.list()) {
				map.put(commerceUserSegmentCriterion.getPrimaryKeyObj(),
					commerceUserSegmentCriterion);

				cacheResult(commerceUserSegmentCriterion);

				uncachedPrimaryKeys.remove(commerceUserSegmentCriterion.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceUserSegmentCriterionModelImpl.ENTITY_CACHE_ENABLED,
					CommerceUserSegmentCriterionImpl.class, primaryKey,
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
	 * Returns all the commerce user segment criterions.
	 *
	 * @return the commerce user segment criterions
	 */
	@Override
	public List<CommerceUserSegmentCriterion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce user segment criterions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentCriterionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce user segment criterions
	 * @param end the upper bound of the range of commerce user segment criterions (not inclusive)
	 * @return the range of commerce user segment criterions
	 */
	@Override
	public List<CommerceUserSegmentCriterion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce user segment criterions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentCriterionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce user segment criterions
	 * @param end the upper bound of the range of commerce user segment criterions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce user segment criterions
	 */
	@Override
	public List<CommerceUserSegmentCriterion> findAll(int start, int end,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce user segment criterions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentCriterionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce user segment criterions
	 * @param end the upper bound of the range of commerce user segment criterions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce user segment criterions
	 */
	@Override
	public List<CommerceUserSegmentCriterion> findAll(int start, int end,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator,
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

		List<CommerceUserSegmentCriterion> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceUserSegmentCriterion>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEUSERSEGMENTCRITERION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEUSERSEGMENTCRITERION;

				if (pagination) {
					sql = sql.concat(CommerceUserSegmentCriterionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceUserSegmentCriterion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceUserSegmentCriterion>)QueryUtil.list(q,
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
	 * Removes all the commerce user segment criterions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceUserSegmentCriterion commerceUserSegmentCriterion : findAll()) {
			remove(commerceUserSegmentCriterion);
		}
	}

	/**
	 * Returns the number of commerce user segment criterions.
	 *
	 * @return the number of commerce user segment criterions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEUSERSEGMENTCRITERION);

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
		return CommerceUserSegmentCriterionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce user segment criterion persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceUserSegmentCriterionImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEUSERSEGMENTCRITERION = "SELECT commerceUserSegmentCriterion FROM CommerceUserSegmentCriterion commerceUserSegmentCriterion";
	private static final String _SQL_SELECT_COMMERCEUSERSEGMENTCRITERION_WHERE_PKS_IN =
		"SELECT commerceUserSegmentCriterion FROM CommerceUserSegmentCriterion commerceUserSegmentCriterion WHERE commerceUserSegmentCriterionId IN (";
	private static final String _SQL_SELECT_COMMERCEUSERSEGMENTCRITERION_WHERE = "SELECT commerceUserSegmentCriterion FROM CommerceUserSegmentCriterion commerceUserSegmentCriterion WHERE ";
	private static final String _SQL_COUNT_COMMERCEUSERSEGMENTCRITERION = "SELECT COUNT(commerceUserSegmentCriterion) FROM CommerceUserSegmentCriterion commerceUserSegmentCriterion";
	private static final String _SQL_COUNT_COMMERCEUSERSEGMENTCRITERION_WHERE = "SELECT COUNT(commerceUserSegmentCriterion) FROM CommerceUserSegmentCriterion commerceUserSegmentCriterion WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceUserSegmentCriterion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceUserSegmentCriterion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceUserSegmentCriterion exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceUserSegmentCriterionPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type"
			});
}