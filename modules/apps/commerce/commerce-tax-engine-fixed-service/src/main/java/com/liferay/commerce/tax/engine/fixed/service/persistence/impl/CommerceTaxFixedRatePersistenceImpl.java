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

import com.liferay.commerce.tax.engine.fixed.exception.NoSuchTaxFixedRateException;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateTable;
import com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateImpl;
import com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateModelImpl;
import com.liferay.commerce.tax.engine.fixed.service.persistence.CommerceTaxFixedRatePersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
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
 * @generated
 */
public class CommerceTaxFixedRatePersistenceImpl
	extends BasePersistenceImpl<CommerceTaxFixedRate>
	implements CommerceTaxFixedRatePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceTaxFixedRateUtil</code> to access the commerce tax fixed rate persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceTaxFixedRateImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCPTaxCategoryId;
	private FinderPath _finderPathWithoutPaginationFindByCPTaxCategoryId;
	private FinderPath _finderPathCountByCPTaxCategoryId;

	/**
	 * Returns all the commerce tax fixed rates where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the matching commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findByCPTaxCategoryId(
		long CPTaxCategoryId) {

		return findByCPTaxCategoryId(
			CPTaxCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax fixed rates where CPTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateModelImpl</code>.
	 * </p>
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param start the lower bound of the range of commerce tax fixed rates
	 * @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	 * @return the range of matching commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end) {

		return findByCPTaxCategoryId(CPTaxCategoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rates where CPTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateModelImpl</code>.
	 * </p>
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param start the lower bound of the range of commerce tax fixed rates
	 * @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator) {

		return findByCPTaxCategoryId(
			CPTaxCategoryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rates where CPTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateModelImpl</code>.
	 * </p>
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param start the lower bound of the range of commerce tax fixed rates
	 * @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCPTaxCategoryId;
				finderArgs = new Object[] {CPTaxCategoryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCPTaxCategoryId;
			finderArgs = new Object[] {
				CPTaxCategoryId, start, end, orderByComparator
			};
		}

		List<CommerceTaxFixedRate> list = null;

		if (useFinderCache) {
			list = (List<CommerceTaxFixedRate>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTaxFixedRate commerceTaxFixedRate : list) {
					if (CPTaxCategoryId !=
							commerceTaxFixedRate.getCPTaxCategoryId()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_COMMERCETAXFIXEDRATE_WHERE);

			sb.append(_FINDER_COLUMN_CPTAXCATEGORYID_CPTAXCATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceTaxFixedRateModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPTaxCategoryId);

				list = (List<CommerceTaxFixedRate>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce tax fixed rate in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a matching commerce tax fixed rate could not be found
	 */
	@Override
	public CommerceTaxFixedRate findByCPTaxCategoryId_First(
			long CPTaxCategoryId,
			OrderByComparator<CommerceTaxFixedRate> orderByComparator)
		throws NoSuchTaxFixedRateException {

		CommerceTaxFixedRate commerceTaxFixedRate =
			fetchByCPTaxCategoryId_First(CPTaxCategoryId, orderByComparator);

		if (commerceTaxFixedRate != null) {
			return commerceTaxFixedRate;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPTaxCategoryId=");
		sb.append(CPTaxCategoryId);

		sb.append("}");

		throw new NoSuchTaxFixedRateException(sb.toString());
	}

	/**
	 * Returns the first commerce tax fixed rate in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax fixed rate, or <code>null</code> if a matching commerce tax fixed rate could not be found
	 */
	@Override
	public CommerceTaxFixedRate fetchByCPTaxCategoryId_First(
		long CPTaxCategoryId,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator) {

		List<CommerceTaxFixedRate> list = findByCPTaxCategoryId(
			CPTaxCategoryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tax fixed rate in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a matching commerce tax fixed rate could not be found
	 */
	@Override
	public CommerceTaxFixedRate findByCPTaxCategoryId_Last(
			long CPTaxCategoryId,
			OrderByComparator<CommerceTaxFixedRate> orderByComparator)
		throws NoSuchTaxFixedRateException {

		CommerceTaxFixedRate commerceTaxFixedRate = fetchByCPTaxCategoryId_Last(
			CPTaxCategoryId, orderByComparator);

		if (commerceTaxFixedRate != null) {
			return commerceTaxFixedRate;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPTaxCategoryId=");
		sb.append(CPTaxCategoryId);

		sb.append("}");

		throw new NoSuchTaxFixedRateException(sb.toString());
	}

	/**
	 * Returns the last commerce tax fixed rate in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax fixed rate, or <code>null</code> if a matching commerce tax fixed rate could not be found
	 */
	@Override
	public CommerceTaxFixedRate fetchByCPTaxCategoryId_Last(
		long CPTaxCategoryId,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator) {

		int count = countByCPTaxCategoryId(CPTaxCategoryId);

		if (count == 0) {
			return null;
		}

		List<CommerceTaxFixedRate> list = findByCPTaxCategoryId(
			CPTaxCategoryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tax fixed rates before and after the current commerce tax fixed rate in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param commerceTaxFixedRateId the primary key of the current commerce tax fixed rate
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a commerce tax fixed rate with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRate[] findByCPTaxCategoryId_PrevAndNext(
			long commerceTaxFixedRateId, long CPTaxCategoryId,
			OrderByComparator<CommerceTaxFixedRate> orderByComparator)
		throws NoSuchTaxFixedRateException {

		CommerceTaxFixedRate commerceTaxFixedRate = findByPrimaryKey(
			commerceTaxFixedRateId);

		Session session = null;

		try {
			session = openSession();

			CommerceTaxFixedRate[] array = new CommerceTaxFixedRateImpl[3];

			array[0] = getByCPTaxCategoryId_PrevAndNext(
				session, commerceTaxFixedRate, CPTaxCategoryId,
				orderByComparator, true);

			array[1] = commerceTaxFixedRate;

			array[2] = getByCPTaxCategoryId_PrevAndNext(
				session, commerceTaxFixedRate, CPTaxCategoryId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceTaxFixedRate getByCPTaxCategoryId_PrevAndNext(
		Session session, CommerceTaxFixedRate commerceTaxFixedRate,
		long CPTaxCategoryId,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_COMMERCETAXFIXEDRATE_WHERE);

		sb.append(_FINDER_COLUMN_CPTAXCATEGORYID_CPTAXCATEGORYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CommerceTaxFixedRateModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CPTaxCategoryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceTaxFixedRate)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTaxFixedRate> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tax fixed rates where CPTaxCategoryId = &#63; from the database.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 */
	@Override
	public void removeByCPTaxCategoryId(long CPTaxCategoryId) {
		for (CommerceTaxFixedRate commerceTaxFixedRate :
				findByCPTaxCategoryId(
					CPTaxCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceTaxFixedRate);
		}
	}

	/**
	 * Returns the number of commerce tax fixed rates where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the number of matching commerce tax fixed rates
	 */
	@Override
	public int countByCPTaxCategoryId(long CPTaxCategoryId) {
		FinderPath finderPath = _finderPathCountByCPTaxCategoryId;

		Object[] finderArgs = new Object[] {CPTaxCategoryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCETAXFIXEDRATE_WHERE);

			sb.append(_FINDER_COLUMN_CPTAXCATEGORYID_CPTAXCATEGORYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPTaxCategoryId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_CPTAXCATEGORYID_CPTAXCATEGORYID_2 =
			"commerceTaxFixedRate.CPTaxCategoryId = ?";

	private FinderPath _finderPathWithPaginationFindByCommerceTaxMethodId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceTaxMethodId;
	private FinderPath _finderPathCountByCommerceTaxMethodId;

	/**
	 * Returns all the commerce tax fixed rates where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the matching commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findByCommerceTaxMethodId(
		long commerceTaxMethodId) {

		return findByCommerceTaxMethodId(
			commerceTaxMethodId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax fixed rates where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateModelImpl</code>.
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

		return findByCommerceTaxMethodId(
			commerceTaxMethodId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rates where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param start the lower bound of the range of commerce tax fixed rates
	 * @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceTaxMethodId;
				finderArgs = new Object[] {commerceTaxMethodId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceTaxMethodId;
			finderArgs = new Object[] {
				commerceTaxMethodId, start, end, orderByComparator
			};
		}

		List<CommerceTaxFixedRate> list = null;

		if (useFinderCache) {
			list = (List<CommerceTaxFixedRate>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTaxFixedRate commerceTaxFixedRate : list) {
					if (commerceTaxMethodId !=
							commerceTaxFixedRate.getCommerceTaxMethodId()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_COMMERCETAXFIXEDRATE_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCETAXMETHODID_COMMERCETAXMETHODID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceTaxFixedRateModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceTaxMethodId);

				list = (List<CommerceTaxFixedRate>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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

		CommerceTaxFixedRate commerceTaxFixedRate =
			fetchByCommerceTaxMethodId_First(
				commerceTaxMethodId, orderByComparator);

		if (commerceTaxFixedRate != null) {
			return commerceTaxFixedRate;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceTaxMethodId=");
		sb.append(commerceTaxMethodId);

		sb.append("}");

		throw new NoSuchTaxFixedRateException(sb.toString());
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

		List<CommerceTaxFixedRate> list = findByCommerceTaxMethodId(
			commerceTaxMethodId, 0, 1, orderByComparator);

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

		CommerceTaxFixedRate commerceTaxFixedRate =
			fetchByCommerceTaxMethodId_Last(
				commerceTaxMethodId, orderByComparator);

		if (commerceTaxFixedRate != null) {
			return commerceTaxFixedRate;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceTaxMethodId=");
		sb.append(commerceTaxMethodId);

		sb.append("}");

		throw new NoSuchTaxFixedRateException(sb.toString());
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

		List<CommerceTaxFixedRate> list = findByCommerceTaxMethodId(
			commerceTaxMethodId, count - 1, count, orderByComparator);

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

		CommerceTaxFixedRate commerceTaxFixedRate = findByPrimaryKey(
			commerceTaxFixedRateId);

		Session session = null;

		try {
			session = openSession();

			CommerceTaxFixedRate[] array = new CommerceTaxFixedRateImpl[3];

			array[0] = getByCommerceTaxMethodId_PrevAndNext(
				session, commerceTaxFixedRate, commerceTaxMethodId,
				orderByComparator, true);

			array[1] = commerceTaxFixedRate;

			array[2] = getByCommerceTaxMethodId_PrevAndNext(
				session, commerceTaxFixedRate, commerceTaxMethodId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
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

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_COMMERCETAXFIXEDRATE_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCETAXMETHODID_COMMERCETAXMETHODID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CommerceTaxFixedRateModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceTaxMethodId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceTaxFixedRate)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTaxFixedRate> list = query.list();

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
		for (CommerceTaxFixedRate commerceTaxFixedRate :
				findByCommerceTaxMethodId(
					commerceTaxMethodId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

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
		FinderPath finderPath = _finderPathCountByCommerceTaxMethodId;

		Object[] finderArgs = new Object[] {commerceTaxMethodId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCETAXFIXEDRATE_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCETAXMETHODID_COMMERCETAXMETHODID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceTaxMethodId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_COMMERCETAXMETHODID_COMMERCETAXMETHODID_2 =
			"commerceTaxFixedRate.commerceTaxMethodId = ?";

	private FinderPath _finderPathFetchByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns the commerce tax fixed rate where CPTaxCategoryId = &#63; and commerceTaxMethodId = &#63; or throws a <code>NoSuchTaxFixedRateException</code> if it could not be found.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the matching commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a matching commerce tax fixed rate could not be found
	 */
	@Override
	public CommerceTaxFixedRate findByC_C(
			long CPTaxCategoryId, long commerceTaxMethodId)
		throws NoSuchTaxFixedRateException {

		CommerceTaxFixedRate commerceTaxFixedRate = fetchByC_C(
			CPTaxCategoryId, commerceTaxMethodId);

		if (commerceTaxFixedRate == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("CPTaxCategoryId=");
			sb.append(CPTaxCategoryId);

			sb.append(", commerceTaxMethodId=");
			sb.append(commerceTaxMethodId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTaxFixedRateException(sb.toString());
		}

		return commerceTaxFixedRate;
	}

	/**
	 * Returns the commerce tax fixed rate where CPTaxCategoryId = &#63; and commerceTaxMethodId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the matching commerce tax fixed rate, or <code>null</code> if a matching commerce tax fixed rate could not be found
	 */
	@Override
	public CommerceTaxFixedRate fetchByC_C(
		long CPTaxCategoryId, long commerceTaxMethodId) {

		return fetchByC_C(CPTaxCategoryId, commerceTaxMethodId, true);
	}

	/**
	 * Returns the commerce tax fixed rate where CPTaxCategoryId = &#63; and commerceTaxMethodId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce tax fixed rate, or <code>null</code> if a matching commerce tax fixed rate could not be found
	 */
	@Override
	public CommerceTaxFixedRate fetchByC_C(
		long CPTaxCategoryId, long commerceTaxMethodId,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {CPTaxCategoryId, commerceTaxMethodId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_C, finderArgs);
		}

		if (result instanceof CommerceTaxFixedRate) {
			CommerceTaxFixedRate commerceTaxFixedRate =
				(CommerceTaxFixedRate)result;

			if ((CPTaxCategoryId !=
					commerceTaxFixedRate.getCPTaxCategoryId()) ||
				(commerceTaxMethodId !=
					commerceTaxFixedRate.getCommerceTaxMethodId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCETAXFIXEDRATE_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CPTAXCATEGORYID_2);

			sb.append(_FINDER_COLUMN_C_C_COMMERCETAXMETHODID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPTaxCategoryId);

				queryPos.add(commerceTaxMethodId);

				List<CommerceTaxFixedRate> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C, finderArgs, list);
					}
				}
				else {
					CommerceTaxFixedRate commerceTaxFixedRate = list.get(0);

					result = commerceTaxFixedRate;

					cacheResult(commerceTaxFixedRate);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
	 * Removes the commerce tax fixed rate where CPTaxCategoryId = &#63; and commerceTaxMethodId = &#63; from the database.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the commerce tax fixed rate that was removed
	 */
	@Override
	public CommerceTaxFixedRate removeByC_C(
			long CPTaxCategoryId, long commerceTaxMethodId)
		throws NoSuchTaxFixedRateException {

		CommerceTaxFixedRate commerceTaxFixedRate = findByC_C(
			CPTaxCategoryId, commerceTaxMethodId);

		return remove(commerceTaxFixedRate);
	}

	/**
	 * Returns the number of commerce tax fixed rates where CPTaxCategoryId = &#63; and commerceTaxMethodId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the number of matching commerce tax fixed rates
	 */
	@Override
	public int countByC_C(long CPTaxCategoryId, long commerceTaxMethodId) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {
			CPTaxCategoryId, commerceTaxMethodId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCETAXFIXEDRATE_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CPTAXCATEGORYID_2);

			sb.append(_FINDER_COLUMN_C_C_COMMERCETAXMETHODID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPTaxCategoryId);

				queryPos.add(commerceTaxMethodId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_CPTAXCATEGORYID_2 =
		"commerceTaxFixedRate.CPTaxCategoryId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_COMMERCETAXMETHODID_2 =
		"commerceTaxFixedRate.commerceTaxMethodId = ?";

	public CommerceTaxFixedRatePersistenceImpl() {
		setModelClass(CommerceTaxFixedRate.class);

		setModelImplClass(CommerceTaxFixedRateImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceTaxFixedRateTable.INSTANCE);
	}

	/**
	 * Caches the commerce tax fixed rate in the entity cache if it is enabled.
	 *
	 * @param commerceTaxFixedRate the commerce tax fixed rate
	 */
	@Override
	public void cacheResult(CommerceTaxFixedRate commerceTaxFixedRate) {
		entityCache.putResult(
			CommerceTaxFixedRateImpl.class,
			commerceTaxFixedRate.getPrimaryKey(), commerceTaxFixedRate);

		finderCache.putResult(
			_finderPathFetchByC_C,
			new Object[] {
				commerceTaxFixedRate.getCPTaxCategoryId(),
				commerceTaxFixedRate.getCommerceTaxMethodId()
			},
			commerceTaxFixedRate);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce tax fixed rates in the entity cache if it is enabled.
	 *
	 * @param commerceTaxFixedRates the commerce tax fixed rates
	 */
	@Override
	public void cacheResult(List<CommerceTaxFixedRate> commerceTaxFixedRates) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceTaxFixedRates.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceTaxFixedRate commerceTaxFixedRate :
				commerceTaxFixedRates) {

			if (entityCache.getResult(
					CommerceTaxFixedRateImpl.class,
					commerceTaxFixedRate.getPrimaryKey()) == null) {

				cacheResult(commerceTaxFixedRate);
			}
		}
	}

	/**
	 * Clears the cache for all commerce tax fixed rates.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceTaxFixedRateImpl.class);

		finderCache.clearCache(CommerceTaxFixedRateImpl.class);
	}

	/**
	 * Clears the cache for the commerce tax fixed rate.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceTaxFixedRate commerceTaxFixedRate) {
		entityCache.removeResult(
			CommerceTaxFixedRateImpl.class, commerceTaxFixedRate);
	}

	@Override
	public void clearCache(List<CommerceTaxFixedRate> commerceTaxFixedRates) {
		for (CommerceTaxFixedRate commerceTaxFixedRate :
				commerceTaxFixedRates) {

			entityCache.removeResult(
				CommerceTaxFixedRateImpl.class, commerceTaxFixedRate);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceTaxFixedRateImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceTaxFixedRateImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceTaxFixedRateModelImpl commerceTaxFixedRateModelImpl) {

		Object[] args = new Object[] {
			commerceTaxFixedRateModelImpl.getCPTaxCategoryId(),
			commerceTaxFixedRateModelImpl.getCommerceTaxMethodId()
		};

		finderCache.putResult(_finderPathCountByC_C, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_C, args, commerceTaxFixedRateModelImpl);
	}

	/**
	 * Creates a new commerce tax fixed rate with the primary key. Does not add the commerce tax fixed rate to the database.
	 *
	 * @param commerceTaxFixedRateId the primary key for the new commerce tax fixed rate
	 * @return the new commerce tax fixed rate
	 */
	@Override
	public CommerceTaxFixedRate create(long commerceTaxFixedRateId) {
		CommerceTaxFixedRate commerceTaxFixedRate =
			new CommerceTaxFixedRateImpl();

		commerceTaxFixedRate.setNew(true);
		commerceTaxFixedRate.setPrimaryKey(commerceTaxFixedRateId);

		commerceTaxFixedRate.setCompanyId(CompanyThreadLocal.getCompanyId());

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

			CommerceTaxFixedRate commerceTaxFixedRate =
				(CommerceTaxFixedRate)session.get(
					CommerceTaxFixedRateImpl.class, primaryKey);

			if (commerceTaxFixedRate == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTaxFixedRateException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceTaxFixedRate);
		}
		catch (NoSuchTaxFixedRateException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected CommerceTaxFixedRate removeImpl(
		CommerceTaxFixedRate commerceTaxFixedRate) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceTaxFixedRate)) {
				commerceTaxFixedRate = (CommerceTaxFixedRate)session.get(
					CommerceTaxFixedRateImpl.class,
					commerceTaxFixedRate.getPrimaryKeyObj());
			}

			if (commerceTaxFixedRate != null) {
				session.delete(commerceTaxFixedRate);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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

		boolean isNew = commerceTaxFixedRate.isNew();

		if (!(commerceTaxFixedRate instanceof CommerceTaxFixedRateModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceTaxFixedRate.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceTaxFixedRate);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceTaxFixedRate proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceTaxFixedRate implementation " +
					commerceTaxFixedRate.getClass());
		}

		CommerceTaxFixedRateModelImpl commerceTaxFixedRateModelImpl =
			(CommerceTaxFixedRateModelImpl)commerceTaxFixedRate;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceTaxFixedRate.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceTaxFixedRate.setCreateDate(date);
			}
			else {
				commerceTaxFixedRate.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceTaxFixedRateModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceTaxFixedRate.setModifiedDate(date);
			}
			else {
				commerceTaxFixedRate.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceTaxFixedRate);
			}
			else {
				commerceTaxFixedRate = (CommerceTaxFixedRate)session.merge(
					commerceTaxFixedRate);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceTaxFixedRateImpl.class, commerceTaxFixedRateModelImpl,
			false, true);

		cacheUniqueFindersCache(commerceTaxFixedRateModelImpl);

		if (isNew) {
			commerceTaxFixedRate.setNew(false);
		}

		commerceTaxFixedRate.resetOriginalValues();

		return commerceTaxFixedRate;
	}

	/**
	 * Returns the commerce tax fixed rate with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce tax fixed rate
	 * @return the commerce tax fixed rate
	 * @throws NoSuchTaxFixedRateException if a commerce tax fixed rate with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRate findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTaxFixedRateException {

		CommerceTaxFixedRate commerceTaxFixedRate = fetchByPrimaryKey(
			primaryKey);

		if (commerceTaxFixedRate == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTaxFixedRateException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceTaxFixedRate;
	}

	/**
	 * Returns the commerce tax fixed rate with the primary key or throws a <code>NoSuchTaxFixedRateException</code> if it could not be found.
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
	 * @param commerceTaxFixedRateId the primary key of the commerce tax fixed rate
	 * @return the commerce tax fixed rate, or <code>null</code> if a commerce tax fixed rate with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRate fetchByPrimaryKey(long commerceTaxFixedRateId) {
		return fetchByPrimaryKey((Serializable)commerceTaxFixedRateId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax fixed rates
	 * @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findAll(
		int start, int end,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax fixed rates
	 * @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce tax fixed rates
	 */
	@Override
	public List<CommerceTaxFixedRate> findAll(
		int start, int end,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<CommerceTaxFixedRate> list = null;

		if (useFinderCache) {
			list = (List<CommerceTaxFixedRate>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCETAXFIXEDRATE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCETAXFIXEDRATE;

				sql = sql.concat(CommerceTaxFixedRateModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceTaxFixedRate>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCETAXFIXEDRATE);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "commerceTaxFixedRateId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCETAXFIXEDRATE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceTaxFixedRateModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce tax fixed rate persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByCPTaxCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPTaxCategoryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPTaxCategoryId"}, true);

		_finderPathWithoutPaginationFindByCPTaxCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPTaxCategoryId",
			new String[] {Long.class.getName()},
			new String[] {"CPTaxCategoryId"}, true);

		_finderPathCountByCPTaxCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPTaxCategoryId",
			new String[] {Long.class.getName()},
			new String[] {"CPTaxCategoryId"}, false);

		_finderPathWithPaginationFindByCommerceTaxMethodId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceTaxMethodId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceTaxMethodId"}, true);

		_finderPathWithoutPaginationFindByCommerceTaxMethodId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceTaxMethodId", new String[] {Long.class.getName()},
			new String[] {"commerceTaxMethodId"}, true);

		_finderPathCountByCommerceTaxMethodId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceTaxMethodId", new String[] {Long.class.getName()},
			new String[] {"commerceTaxMethodId"}, false);

		_finderPathFetchByC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPTaxCategoryId", "commerceTaxMethodId"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPTaxCategoryId", "commerceTaxMethodId"}, false);
	}

	public void destroy() {
		entityCache.removeCache(CommerceTaxFixedRateImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCETAXFIXEDRATE =
		"SELECT commerceTaxFixedRate FROM CommerceTaxFixedRate commerceTaxFixedRate";

	private static final String _SQL_SELECT_COMMERCETAXFIXEDRATE_WHERE =
		"SELECT commerceTaxFixedRate FROM CommerceTaxFixedRate commerceTaxFixedRate WHERE ";

	private static final String _SQL_COUNT_COMMERCETAXFIXEDRATE =
		"SELECT COUNT(commerceTaxFixedRate) FROM CommerceTaxFixedRate commerceTaxFixedRate";

	private static final String _SQL_COUNT_COMMERCETAXFIXEDRATE_WHERE =
		"SELECT COUNT(commerceTaxFixedRate) FROM CommerceTaxFixedRate commerceTaxFixedRate WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceTaxFixedRate.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceTaxFixedRate exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceTaxFixedRate exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceTaxFixedRatePersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}