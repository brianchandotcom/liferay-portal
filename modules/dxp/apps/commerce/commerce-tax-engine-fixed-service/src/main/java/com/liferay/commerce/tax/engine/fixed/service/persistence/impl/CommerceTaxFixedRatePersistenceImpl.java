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

package com.liferay.commerce.tax.engine.fixed.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.tax.engine.fixed.exception.NoSuchTaxFixedRateException;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;
import com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateImpl;
import com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateModelImpl;
import com.liferay.commerce.tax.engine.fixed.service.persistence.CommerceTaxFixedRatePersistence;

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
 * The persistence implementation for the commerce tax fixed rate service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRatePersistence
 * @see com.liferay.commerce.tax.engine.fixed.service.persistence.CommerceTaxFixedRateUtil
 * @generated
 */
@ProviderType
public class CommerceTaxFixedRatePersistenceImpl extends BasePersistenceImpl<CommerceTaxFixedRate>
	implements CommerceTaxFixedRatePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceTaxFixedRateUtil} to access the commerce tax fixed rate persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceTaxFixedRateImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceTaxFixedRateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxFixedRateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceTaxFixedRateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxFixedRateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceTaxFixedRateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCETAXMETHODID =
		new FinderPath(CommerceTaxFixedRateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxFixedRateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceTaxMethodId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCETAXMETHODID =
		new FinderPath(CommerceTaxFixedRateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxFixedRateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceTaxMethodId", new String[] { Long.class.getName() },
			CommerceTaxFixedRateModelImpl.COMMERCETAXMETHODID_COLUMN_BITMASK |
			CommerceTaxFixedRateModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCETAXMETHODID = new FinderPath(CommerceTaxFixedRateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceTaxMethodId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce tax fixed rates where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the matching commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findByCommerceTaxMethodId(
		long commerceTaxMethodId) {
		return findByCommerceTaxMethodId(commerceTaxMethodId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax fixed rates where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxFixedRateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param start the lower bound of the range of commerce tax fixed rates
	 * @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	 * @return the range of matching commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end) {
		return findByCommerceTaxMethodId(commerceTaxMethodId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rates where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxFixedRateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param start the lower bound of the range of commerce tax fixed rates
	 * @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator) {
		return findByCommerceTaxMethodId(commerceTaxMethodId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rates where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxFixedRateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param start the lower bound of the range of commerce tax fixed rates
	 * @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCETAXMETHODID;
			finderArgs = new Object[] { commerceTaxMethodId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCETAXMETHODID;
			finderArgs = new Object[] {
					commerceTaxMethodId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceTaxFixedRate> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTaxFixedRate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTaxFixedRate commerceTaxFixedRate : list) {
					if ((commerceTaxMethodId != commerceTaxFixedRate.getCommerceTaxMethodId())) {
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

			query.append(_SQL_SELECT_COMMERCETAXFIXEDRATE_WHERE);

			query.append(_FINDER_COLUMN_COMMERCETAXMETHODID_COMMERCETAXMETHODID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceTaxFixedRateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceTaxMethodId);

				if (!pagination) {
					list = (List<CommerceTaxFixedRate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTaxFixedRate>)QueryUtil.list(q,
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
	 * Returns the first commerce tax fixed rate in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a matching commerce tax fixed rate could not be found
	 */
	@Override
	public CommerceTaxFixedRate findByCommerceTaxMethodId_First(
		long commerceTaxMethodId,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator)
		throws NoSuchTaxFixedRateException {
		CommerceTaxFixedRate commerceTaxFixedRate = fetchByCommerceTaxMethodId_First(commerceTaxMethodId,
				orderByComparator);

		if (commerceTaxFixedRate != null) {
			return commerceTaxFixedRate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceTaxMethodId=");
		msg.append(commerceTaxMethodId);

		msg.append("}");

		throw new NoSuchTaxFixedRateException(msg.toString());
	}

	/**
	 * Returns the first commerce tax fixed rate in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax fixed rate, or <code>null</code> if a matching commerce tax fixed rate could not be found
	 */
	@Override
	public CommerceTaxFixedRate fetchByCommerceTaxMethodId_First(
		long commerceTaxMethodId,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator) {
		List<CommerceTaxFixedRate> list = findByCommerceTaxMethodId(commerceTaxMethodId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tax fixed rate in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a matching commerce tax fixed rate could not be found
	 */
	@Override
	public CommerceTaxFixedRate findByCommerceTaxMethodId_Last(
		long commerceTaxMethodId,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator)
		throws NoSuchTaxFixedRateException {
		CommerceTaxFixedRate commerceTaxFixedRate = fetchByCommerceTaxMethodId_Last(commerceTaxMethodId,
				orderByComparator);

		if (commerceTaxFixedRate != null) {
			return commerceTaxFixedRate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceTaxMethodId=");
		msg.append(commerceTaxMethodId);

		msg.append("}");

		throw new NoSuchTaxFixedRateException(msg.toString());
	}

	/**
	 * Returns the last commerce tax fixed rate in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax fixed rate, or <code>null</code> if a matching commerce tax fixed rate could not be found
	 */
	@Override
	public CommerceTaxFixedRate fetchByCommerceTaxMethodId_Last(
		long commerceTaxMethodId,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator) {
		int count = countByCommerceTaxMethodId(commerceTaxMethodId);

		if (count == 0) {
			return null;
		}

		List<CommerceTaxFixedRate> list = findByCommerceTaxMethodId(commerceTaxMethodId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tax fixed rates before and after the current commerce tax fixed rate in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxFixedRateId the primary key of the current commerce tax fixed rate
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a commerce tax fixed rate with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRate[] findByCommerceTaxMethodId_PrevAndNext(
		long commerceTaxFixedRateId, long commerceTaxMethodId,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator)
		throws NoSuchTaxFixedRateException {
		CommerceTaxFixedRate commerceTaxFixedRate = findByPrimaryKey(commerceTaxFixedRateId);

		Session session = null;

		try {
			session = openSession();

			CommerceTaxFixedRate[] array = new CommerceTaxFixedRateImpl[3];

			array[0] = getByCommerceTaxMethodId_PrevAndNext(session,
					commerceTaxFixedRate, commerceTaxMethodId,
					orderByComparator, true);

			array[1] = commerceTaxFixedRate;

			array[2] = getByCommerceTaxMethodId_PrevAndNext(session,
					commerceTaxFixedRate, commerceTaxMethodId,
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

	protected CommerceTaxFixedRate getByCommerceTaxMethodId_PrevAndNext(
		Session session, CommerceTaxFixedRate commerceTaxFixedRate,
		long commerceTaxMethodId,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCETAXFIXEDRATE_WHERE);

		query.append(_FINDER_COLUMN_COMMERCETAXMETHODID_COMMERCETAXMETHODID_2);

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
			query.append(CommerceTaxFixedRateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceTaxMethodId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceTaxFixedRate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceTaxFixedRate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tax fixed rates where commerceTaxMethodId = &#63; from the database.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 */
	@Override
	public void removeByCommerceTaxMethodId(long commerceTaxMethodId) {
		for (CommerceTaxFixedRate commerceTaxFixedRate : findByCommerceTaxMethodId(
				commerceTaxMethodId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceTaxFixedRate);
		}
	}

	/**
	 * Returns the number of commerce tax fixed rates where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the number of matching commerce tax fixed rates
	 */
	@Override
	public int countByCommerceTaxMethodId(long commerceTaxMethodId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCETAXMETHODID;

		Object[] finderArgs = new Object[] { commerceTaxMethodId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCETAXFIXEDRATE_WHERE);

			query.append(_FINDER_COLUMN_COMMERCETAXMETHODID_COMMERCETAXMETHODID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceTaxMethodId);

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

	private static final String _FINDER_COLUMN_COMMERCETAXMETHODID_COMMERCETAXMETHODID_2 =
		"commerceTaxFixedRate.commerceTaxMethodId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_COMMERCETAXCATEGORYID = new FinderPath(CommerceTaxFixedRateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxFixedRateImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByCommerceTaxCategoryId",
			new String[] { Long.class.getName() },
			CommerceTaxFixedRateModelImpl.COMMERCETAXCATEGORYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCETAXCATEGORYID = new FinderPath(CommerceTaxFixedRateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceTaxCategoryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns the commerce tax fixed rate where commerceTaxCategoryId = &#63; or throws a {@link NoSuchTaxFixedRateException} if it could not be found.
	 *
	 * @param commerceTaxCategoryId the commerce tax category ID
	 * @return the matching commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a matching commerce tax fixed rate could not be found
	 */
	@Override
	public CommerceTaxFixedRate findByCommerceTaxCategoryId(
		long commerceTaxCategoryId) throws NoSuchTaxFixedRateException {
		CommerceTaxFixedRate commerceTaxFixedRate = fetchByCommerceTaxCategoryId(commerceTaxCategoryId);

		if (commerceTaxFixedRate == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("commerceTaxCategoryId=");
			msg.append(commerceTaxCategoryId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchTaxFixedRateException(msg.toString());
		}

		return commerceTaxFixedRate;
	}

	/**
	 * Returns the commerce tax fixed rate where commerceTaxCategoryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceTaxCategoryId the commerce tax category ID
	 * @return the matching commerce tax fixed rate, or <code>null</code> if a matching commerce tax fixed rate could not be found
	 */
	@Override
	public CommerceTaxFixedRate fetchByCommerceTaxCategoryId(
		long commerceTaxCategoryId) {
		return fetchByCommerceTaxCategoryId(commerceTaxCategoryId, true);
	}

	/**
	 * Returns the commerce tax fixed rate where commerceTaxCategoryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceTaxCategoryId the commerce tax category ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce tax fixed rate, or <code>null</code> if a matching commerce tax fixed rate could not be found
	 */
	@Override
	public CommerceTaxFixedRate fetchByCommerceTaxCategoryId(
		long commerceTaxCategoryId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { commerceTaxCategoryId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_COMMERCETAXCATEGORYID,
					finderArgs, this);
		}

		if (result instanceof CommerceTaxFixedRate) {
			CommerceTaxFixedRate commerceTaxFixedRate = (CommerceTaxFixedRate)result;

			if ((commerceTaxCategoryId != commerceTaxFixedRate.getCommerceTaxCategoryId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_COMMERCETAXFIXEDRATE_WHERE);

			query.append(_FINDER_COLUMN_COMMERCETAXCATEGORYID_COMMERCETAXCATEGORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceTaxCategoryId);

				List<CommerceTaxFixedRate> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_COMMERCETAXCATEGORYID,
						finderArgs, list);
				}
				else {
					CommerceTaxFixedRate commerceTaxFixedRate = list.get(0);

					result = commerceTaxFixedRate;

					cacheResult(commerceTaxFixedRate);

					if ((commerceTaxFixedRate.getCommerceTaxCategoryId() != commerceTaxCategoryId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_COMMERCETAXCATEGORYID,
							finderArgs, commerceTaxFixedRate);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_COMMERCETAXCATEGORYID,
					finderArgs);

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
			return (CommerceTaxFixedRate)result;
		}
	}

	/**
	 * Removes the commerce tax fixed rate where commerceTaxCategoryId = &#63; from the database.
	 *
	 * @param commerceTaxCategoryId the commerce tax category ID
	 * @return the commerce tax fixed rate that was removed
	 */
	@Override
	public CommerceTaxFixedRate removeByCommerceTaxCategoryId(
		long commerceTaxCategoryId) throws NoSuchTaxFixedRateException {
		CommerceTaxFixedRate commerceTaxFixedRate = findByCommerceTaxCategoryId(commerceTaxCategoryId);

		return remove(commerceTaxFixedRate);
	}

	/**
	 * Returns the number of commerce tax fixed rates where commerceTaxCategoryId = &#63;.
	 *
	 * @param commerceTaxCategoryId the commerce tax category ID
	 * @return the number of matching commerce tax fixed rates
	 */
	@Override
	public int countByCommerceTaxCategoryId(long commerceTaxCategoryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCETAXCATEGORYID;

		Object[] finderArgs = new Object[] { commerceTaxCategoryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCETAXFIXEDRATE_WHERE);

			query.append(_FINDER_COLUMN_COMMERCETAXCATEGORYID_COMMERCETAXCATEGORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceTaxCategoryId);

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

	private static final String _FINDER_COLUMN_COMMERCETAXCATEGORYID_COMMERCETAXCATEGORYID_2 =
		"commerceTaxFixedRate.commerceTaxCategoryId = ?";

	public CommerceTaxFixedRatePersistenceImpl() {
		setModelClass(CommerceTaxFixedRate.class);
	}

	/**
	 * Caches the commerce tax fixed rate in the entity cache if it is enabled.
	 *
	 * @param commerceTaxFixedRate the commerce tax fixed rate
	 */
	@Override
	public void cacheResult(CommerceTaxFixedRate commerceTaxFixedRate) {
		entityCache.putResult(CommerceTaxFixedRateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateImpl.class,
			commerceTaxFixedRate.getPrimaryKey(), commerceTaxFixedRate);

		finderCache.putResult(FINDER_PATH_FETCH_BY_COMMERCETAXCATEGORYID,
			new Object[] { commerceTaxFixedRate.getCommerceTaxCategoryId() },
			commerceTaxFixedRate);

		commerceTaxFixedRate.resetOriginalValues();
	}

	/**
	 * Caches the commerce tax fixed rates in the entity cache if it is enabled.
	 *
	 * @param commerceTaxFixedRates the commerce tax fixed rates
	 */
	@Override
	public void cacheResult(List<CommerceTaxFixedRate> commerceTaxFixedRates) {
		for (CommerceTaxFixedRate commerceTaxFixedRate : commerceTaxFixedRates) {
			if (entityCache.getResult(
						CommerceTaxFixedRateModelImpl.ENTITY_CACHE_ENABLED,
						CommerceTaxFixedRateImpl.class,
						commerceTaxFixedRate.getPrimaryKey()) == null) {
				cacheResult(commerceTaxFixedRate);
			}
			else {
				commerceTaxFixedRate.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce tax fixed rates.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceTaxFixedRateImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce tax fixed rate.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceTaxFixedRate commerceTaxFixedRate) {
		entityCache.removeResult(CommerceTaxFixedRateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateImpl.class, commerceTaxFixedRate.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceTaxFixedRateModelImpl)commerceTaxFixedRate,
			true);
	}

	@Override
	public void clearCache(List<CommerceTaxFixedRate> commerceTaxFixedRates) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceTaxFixedRate commerceTaxFixedRate : commerceTaxFixedRates) {
			entityCache.removeResult(CommerceTaxFixedRateModelImpl.ENTITY_CACHE_ENABLED,
				CommerceTaxFixedRateImpl.class,
				commerceTaxFixedRate.getPrimaryKey());

			clearUniqueFindersCache((CommerceTaxFixedRateModelImpl)commerceTaxFixedRate,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceTaxFixedRateModelImpl commerceTaxFixedRateModelImpl) {
		Object[] args = new Object[] {
				commerceTaxFixedRateModelImpl.getCommerceTaxCategoryId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_COMMERCETAXCATEGORYID, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_COMMERCETAXCATEGORYID, args,
			commerceTaxFixedRateModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceTaxFixedRateModelImpl commerceTaxFixedRateModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceTaxFixedRateModelImpl.getCommerceTaxCategoryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCETAXCATEGORYID,
				args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_COMMERCETAXCATEGORYID,
				args);
		}

		if ((commerceTaxFixedRateModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_COMMERCETAXCATEGORYID.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceTaxFixedRateModelImpl.getOriginalCommerceTaxCategoryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCETAXCATEGORYID,
				args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_COMMERCETAXCATEGORYID,
				args);
		}
	}

	/**
	 * Creates a new commerce tax fixed rate with the primary key. Does not add the commerce tax fixed rate to the database.
	 *
	 * @param commerceTaxFixedRateId the primary key for the new commerce tax fixed rate
	 * @return the new commerce tax fixed rate
	 */
	@Override
	public CommerceTaxFixedRate create(long commerceTaxFixedRateId) {
		CommerceTaxFixedRate commerceTaxFixedRate = new CommerceTaxFixedRateImpl();

		commerceTaxFixedRate.setNew(true);
		commerceTaxFixedRate.setPrimaryKey(commerceTaxFixedRateId);

		commerceTaxFixedRate.setCompanyId(companyProvider.getCompanyId());

		return commerceTaxFixedRate;
	}

	/**
	 * Removes the commerce tax fixed rate with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceTaxFixedRateId the primary key of the commerce tax fixed rate
	 * @return the commerce tax fixed rate that was removed
	 * @throws NoSuchTaxFixedRateException if a commerce tax fixed rate with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRate remove(long commerceTaxFixedRateId)
		throws NoSuchTaxFixedRateException {
		return remove((Serializable)commerceTaxFixedRateId);
	}

	/**
	 * Removes the commerce tax fixed rate with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce tax fixed rate
	 * @return the commerce tax fixed rate that was removed
	 * @throws NoSuchTaxFixedRateException if a commerce tax fixed rate with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRate remove(Serializable primaryKey)
		throws NoSuchTaxFixedRateException {
		Session session = null;

		try {
			session = openSession();

			CommerceTaxFixedRate commerceTaxFixedRate = (CommerceTaxFixedRate)session.get(CommerceTaxFixedRateImpl.class,
					primaryKey);

			if (commerceTaxFixedRate == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTaxFixedRateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceTaxFixedRate);
		}
		catch (NoSuchTaxFixedRateException nsee) {
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
	protected CommerceTaxFixedRate removeImpl(
		CommerceTaxFixedRate commerceTaxFixedRate) {
		commerceTaxFixedRate = toUnwrappedModel(commerceTaxFixedRate);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceTaxFixedRate)) {
				commerceTaxFixedRate = (CommerceTaxFixedRate)session.get(CommerceTaxFixedRateImpl.class,
						commerceTaxFixedRate.getPrimaryKeyObj());
			}

			if (commerceTaxFixedRate != null) {
				session.delete(commerceTaxFixedRate);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceTaxFixedRate != null) {
			clearCache(commerceTaxFixedRate);
		}

		return commerceTaxFixedRate;
	}

	@Override
	public CommerceTaxFixedRate updateImpl(
		CommerceTaxFixedRate commerceTaxFixedRate) {
		commerceTaxFixedRate = toUnwrappedModel(commerceTaxFixedRate);

		boolean isNew = commerceTaxFixedRate.isNew();

		CommerceTaxFixedRateModelImpl commerceTaxFixedRateModelImpl = (CommerceTaxFixedRateModelImpl)commerceTaxFixedRate;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceTaxFixedRate.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceTaxFixedRate.setCreateDate(now);
			}
			else {
				commerceTaxFixedRate.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceTaxFixedRateModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceTaxFixedRate.setModifiedDate(now);
			}
			else {
				commerceTaxFixedRate.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceTaxFixedRate.isNew()) {
				session.save(commerceTaxFixedRate);

				commerceTaxFixedRate.setNew(false);
			}
			else {
				commerceTaxFixedRate = (CommerceTaxFixedRate)session.merge(commerceTaxFixedRate);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceTaxFixedRateModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceTaxFixedRateModelImpl.getCommerceTaxMethodId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCETAXMETHODID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCETAXMETHODID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceTaxFixedRateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCETAXMETHODID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceTaxFixedRateModelImpl.getOriginalCommerceTaxMethodId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCETAXMETHODID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCETAXMETHODID,
					args);

				args = new Object[] {
						commerceTaxFixedRateModelImpl.getCommerceTaxMethodId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCETAXMETHODID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCETAXMETHODID,
					args);
			}
		}

		entityCache.putResult(CommerceTaxFixedRateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateImpl.class,
			commerceTaxFixedRate.getPrimaryKey(), commerceTaxFixedRate, false);

		clearUniqueFindersCache(commerceTaxFixedRateModelImpl, false);
		cacheUniqueFindersCache(commerceTaxFixedRateModelImpl);

		commerceTaxFixedRate.resetOriginalValues();

		return commerceTaxFixedRate;
	}

	protected CommerceTaxFixedRate toUnwrappedModel(
		CommerceTaxFixedRate commerceTaxFixedRate) {
		if (commerceTaxFixedRate instanceof CommerceTaxFixedRateImpl) {
			return commerceTaxFixedRate;
		}

		CommerceTaxFixedRateImpl commerceTaxFixedRateImpl = new CommerceTaxFixedRateImpl();

		commerceTaxFixedRateImpl.setNew(commerceTaxFixedRate.isNew());
		commerceTaxFixedRateImpl.setPrimaryKey(commerceTaxFixedRate.getPrimaryKey());

		commerceTaxFixedRateImpl.setCommerceTaxFixedRateId(commerceTaxFixedRate.getCommerceTaxFixedRateId());
		commerceTaxFixedRateImpl.setGroupId(commerceTaxFixedRate.getGroupId());
		commerceTaxFixedRateImpl.setCompanyId(commerceTaxFixedRate.getCompanyId());
		commerceTaxFixedRateImpl.setUserId(commerceTaxFixedRate.getUserId());
		commerceTaxFixedRateImpl.setUserName(commerceTaxFixedRate.getUserName());
		commerceTaxFixedRateImpl.setCreateDate(commerceTaxFixedRate.getCreateDate());
		commerceTaxFixedRateImpl.setModifiedDate(commerceTaxFixedRate.getModifiedDate());
		commerceTaxFixedRateImpl.setCommerceTaxMethodId(commerceTaxFixedRate.getCommerceTaxMethodId());
		commerceTaxFixedRateImpl.setCommerceTaxCategoryId(commerceTaxFixedRate.getCommerceTaxCategoryId());
		commerceTaxFixedRateImpl.setRate(commerceTaxFixedRate.getRate());

		return commerceTaxFixedRateImpl;
	}

	/**
	 * Returns the commerce tax fixed rate with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce tax fixed rate
	 * @return the commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a commerce tax fixed rate with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRate findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTaxFixedRateException {
		CommerceTaxFixedRate commerceTaxFixedRate = fetchByPrimaryKey(primaryKey);

		if (commerceTaxFixedRate == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTaxFixedRateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceTaxFixedRate;
	}

	/**
	 * Returns the commerce tax fixed rate with the primary key or throws a {@link NoSuchTaxFixedRateException} if it could not be found.
	 *
	 * @param commerceTaxFixedRateId the primary key of the commerce tax fixed rate
	 * @return the commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a commerce tax fixed rate with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRate findByPrimaryKey(long commerceTaxFixedRateId)
		throws NoSuchTaxFixedRateException {
		return findByPrimaryKey((Serializable)commerceTaxFixedRateId);
	}

	/**
	 * Returns the commerce tax fixed rate with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce tax fixed rate
	 * @return the commerce tax fixed rate, or <code>null</code> if a commerce tax fixed rate with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRate fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceTaxFixedRateModelImpl.ENTITY_CACHE_ENABLED,
				CommerceTaxFixedRateImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceTaxFixedRate commerceTaxFixedRate = (CommerceTaxFixedRate)serializable;

		if (commerceTaxFixedRate == null) {
			Session session = null;

			try {
				session = openSession();

				commerceTaxFixedRate = (CommerceTaxFixedRate)session.get(CommerceTaxFixedRateImpl.class,
						primaryKey);

				if (commerceTaxFixedRate != null) {
					cacheResult(commerceTaxFixedRate);
				}
				else {
					entityCache.putResult(CommerceTaxFixedRateModelImpl.ENTITY_CACHE_ENABLED,
						CommerceTaxFixedRateImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceTaxFixedRateModelImpl.ENTITY_CACHE_ENABLED,
					CommerceTaxFixedRateImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceTaxFixedRate;
	}

	/**
	 * Returns the commerce tax fixed rate with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceTaxFixedRateId the primary key of the commerce tax fixed rate
	 * @return the commerce tax fixed rate, or <code>null</code> if a commerce tax fixed rate with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRate fetchByPrimaryKey(long commerceTaxFixedRateId) {
		return fetchByPrimaryKey((Serializable)commerceTaxFixedRateId);
	}

	@Override
	public Map<Serializable, CommerceTaxFixedRate> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceTaxFixedRate> map = new HashMap<Serializable, CommerceTaxFixedRate>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceTaxFixedRate commerceTaxFixedRate = fetchByPrimaryKey(primaryKey);

			if (commerceTaxFixedRate != null) {
				map.put(primaryKey, commerceTaxFixedRate);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceTaxFixedRateModelImpl.ENTITY_CACHE_ENABLED,
					CommerceTaxFixedRateImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceTaxFixedRate)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCETAXFIXEDRATE_WHERE_PKS_IN);

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

			for (CommerceTaxFixedRate commerceTaxFixedRate : (List<CommerceTaxFixedRate>)q.list()) {
				map.put(commerceTaxFixedRate.getPrimaryKeyObj(),
					commerceTaxFixedRate);

				cacheResult(commerceTaxFixedRate);

				uncachedPrimaryKeys.remove(commerceTaxFixedRate.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceTaxFixedRateModelImpl.ENTITY_CACHE_ENABLED,
					CommerceTaxFixedRateImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce tax fixed rates.
	 *
	 * @return the commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax fixed rates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxFixedRateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax fixed rates
	 * @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	 * @return the range of commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxFixedRateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax fixed rates
	 * @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findAll(int start, int end,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxFixedRateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax fixed rates
	 * @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findAll(int start, int end,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator,
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

		List<CommerceTaxFixedRate> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTaxFixedRate>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCETAXFIXEDRATE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCETAXFIXEDRATE;

				if (pagination) {
					sql = sql.concat(CommerceTaxFixedRateModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceTaxFixedRate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTaxFixedRate>)QueryUtil.list(q,
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
	 * Removes all the commerce tax fixed rates from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceTaxFixedRate commerceTaxFixedRate : findAll()) {
			remove(commerceTaxFixedRate);
		}
	}

	/**
	 * Returns the number of commerce tax fixed rates.
	 *
	 * @return the number of commerce tax fixed rates
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCETAXFIXEDRATE);

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
		return CommerceTaxFixedRateModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce tax fixed rate persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceTaxFixedRateImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCETAXFIXEDRATE = "SELECT commerceTaxFixedRate FROM CommerceTaxFixedRate commerceTaxFixedRate";
	private static final String _SQL_SELECT_COMMERCETAXFIXEDRATE_WHERE_PKS_IN = "SELECT commerceTaxFixedRate FROM CommerceTaxFixedRate commerceTaxFixedRate WHERE commerceTaxFixedRateId IN (";
	private static final String _SQL_SELECT_COMMERCETAXFIXEDRATE_WHERE = "SELECT commerceTaxFixedRate FROM CommerceTaxFixedRate commerceTaxFixedRate WHERE ";
	private static final String _SQL_COUNT_COMMERCETAXFIXEDRATE = "SELECT COUNT(commerceTaxFixedRate) FROM CommerceTaxFixedRate commerceTaxFixedRate";
	private static final String _SQL_COUNT_COMMERCETAXFIXEDRATE_WHERE = "SELECT COUNT(commerceTaxFixedRate) FROM CommerceTaxFixedRate commerceTaxFixedRate WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceTaxFixedRate.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceTaxFixedRate exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceTaxFixedRate exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceTaxFixedRatePersistenceImpl.class);
}