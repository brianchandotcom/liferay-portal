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

import com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionException;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionTable;
import com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionImpl;
import com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionModelImpl;
import com.liferay.commerce.shipping.engine.fixed.service.persistence.CommerceShippingFixedOptionPersistence;
import com.liferay.commerce.shipping.engine.fixed.service.persistence.CommerceShippingFixedOptionUtil;
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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the commerce shipping fixed option service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceShippingFixedOptionPersistenceImpl
	extends BasePersistenceImpl<CommerceShippingFixedOption>
	implements CommerceShippingFixedOptionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceShippingFixedOptionUtil</code> to access the commerce shipping fixed option persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceShippingFixedOptionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCommerceShippingMethodId;
	private FinderPath
		_finderPathWithoutPaginationFindByCommerceShippingMethodId;
	private FinderPath _finderPathCountByCommerceShippingMethodId;

	/**
	 * Returns all the commerce shipping fixed options where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @return the matching commerce shipping fixed options
	 */
	@Override
	public List<CommerceShippingFixedOption> findByCommerceShippingMethodId(
		long commerceShippingMethodId) {

		return findByCommerceShippingMethodId(
			commerceShippingMethodId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce shipping fixed options where commerceShippingMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param start the lower bound of the range of commerce shipping fixed options
	 * @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	 * @return the range of matching commerce shipping fixed options
	 */
	@Override
	public List<CommerceShippingFixedOption> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end) {

		return findByCommerceShippingMethodId(
			commerceShippingMethodId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed options where commerceShippingMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param start the lower bound of the range of commerce shipping fixed options
	 * @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping fixed options
	 */
	@Override
	public List<CommerceShippingFixedOption> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator) {

		return findByCommerceShippingMethodId(
			commerceShippingMethodId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed options where commerceShippingMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param start the lower bound of the range of commerce shipping fixed options
	 * @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping fixed options
	 */
	@Override
	public List<CommerceShippingFixedOption> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceShippingMethodId;
				finderArgs = new Object[] {commerceShippingMethodId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByCommerceShippingMethodId;
			finderArgs = new Object[] {
				commerceShippingMethodId, start, end, orderByComparator
			};
		}

		List<CommerceShippingFixedOption> list = null;

		if (useFinderCache) {
			list = (List<CommerceShippingFixedOption>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceShippingFixedOption commerceShippingFixedOption :
						list) {

					if (commerceShippingMethodId !=
							commerceShippingFixedOption.
								getCommerceShippingMethodId()) {

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

			sb.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTION_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCESHIPPINGMETHODID_COMMERCESHIPPINGMETHODID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceShippingFixedOptionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceShippingMethodId);

				list = (List<CommerceShippingFixedOption>)QueryUtil.list(
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
	 * Returns the first commerce shipping fixed option in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option
	 * @throws NoSuchShippingFixedOptionException if a matching commerce shipping fixed option could not be found
	 */
	@Override
	public CommerceShippingFixedOption findByCommerceShippingMethodId_First(
			long commerceShippingMethodId,
			OrderByComparator<CommerceShippingFixedOption> orderByComparator)
		throws NoSuchShippingFixedOptionException {

		CommerceShippingFixedOption commerceShippingFixedOption =
			fetchByCommerceShippingMethodId_First(
				commerceShippingMethodId, orderByComparator);

		if (commerceShippingFixedOption != null) {
			return commerceShippingFixedOption;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceShippingMethodId=");
		sb.append(commerceShippingMethodId);

		sb.append("}");

		throw new NoSuchShippingFixedOptionException(sb.toString());
	}

	/**
	 * Returns the first commerce shipping fixed option in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option, or <code>null</code> if a matching commerce shipping fixed option could not be found
	 */
	@Override
	public CommerceShippingFixedOption fetchByCommerceShippingMethodId_First(
		long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator) {

		List<CommerceShippingFixedOption> list = findByCommerceShippingMethodId(
			commerceShippingMethodId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce shipping fixed option in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option
	 * @throws NoSuchShippingFixedOptionException if a matching commerce shipping fixed option could not be found
	 */
	@Override
	public CommerceShippingFixedOption findByCommerceShippingMethodId_Last(
			long commerceShippingMethodId,
			OrderByComparator<CommerceShippingFixedOption> orderByComparator)
		throws NoSuchShippingFixedOptionException {

		CommerceShippingFixedOption commerceShippingFixedOption =
			fetchByCommerceShippingMethodId_Last(
				commerceShippingMethodId, orderByComparator);

		if (commerceShippingFixedOption != null) {
			return commerceShippingFixedOption;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceShippingMethodId=");
		sb.append(commerceShippingMethodId);

		sb.append("}");

		throw new NoSuchShippingFixedOptionException(sb.toString());
	}

	/**
	 * Returns the last commerce shipping fixed option in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option, or <code>null</code> if a matching commerce shipping fixed option could not be found
	 */
	@Override
	public CommerceShippingFixedOption fetchByCommerceShippingMethodId_Last(
		long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator) {

		int count = countByCommerceShippingMethodId(commerceShippingMethodId);

		if (count == 0) {
			return null;
		}

		List<CommerceShippingFixedOption> list = findByCommerceShippingMethodId(
			commerceShippingMethodId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce shipping fixed options before and after the current commerce shipping fixed option in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the primary key of the current commerce shipping fixed option
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping fixed option
	 * @throws NoSuchShippingFixedOptionException if a commerce shipping fixed option with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOption[]
			findByCommerceShippingMethodId_PrevAndNext(
				long commerceShippingFixedOptionId,
				long commerceShippingMethodId,
				OrderByComparator<CommerceShippingFixedOption>
					orderByComparator)
		throws NoSuchShippingFixedOptionException {

		CommerceShippingFixedOption commerceShippingFixedOption =
			findByPrimaryKey(commerceShippingFixedOptionId);

		Session session = null;

		try {
			session = openSession();

			CommerceShippingFixedOption[] array =
				new CommerceShippingFixedOptionImpl[3];

			array[0] = getByCommerceShippingMethodId_PrevAndNext(
				session, commerceShippingFixedOption, commerceShippingMethodId,
				orderByComparator, true);

			array[1] = commerceShippingFixedOption;

			array[2] = getByCommerceShippingMethodId_PrevAndNext(
				session, commerceShippingFixedOption, commerceShippingMethodId,
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

	protected CommerceShippingFixedOption
		getByCommerceShippingMethodId_PrevAndNext(
			Session session,
			CommerceShippingFixedOption commerceShippingFixedOption,
			long commerceShippingMethodId,
			OrderByComparator<CommerceShippingFixedOption> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTION_WHERE);

		sb.append(
			_FINDER_COLUMN_COMMERCESHIPPINGMETHODID_COMMERCESHIPPINGMETHODID_2);

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
			sb.append(CommerceShippingFixedOptionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceShippingMethodId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceShippingFixedOption)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceShippingFixedOption> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce shipping fixed options where commerceShippingMethodId = &#63; from the database.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 */
	@Override
	public void removeByCommerceShippingMethodId(
		long commerceShippingMethodId) {

		for (CommerceShippingFixedOption commerceShippingFixedOption :
				findByCommerceShippingMethodId(
					commerceShippingMethodId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceShippingFixedOption);
		}
	}

	/**
	 * Returns the number of commerce shipping fixed options where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @return the number of matching commerce shipping fixed options
	 */
	@Override
	public int countByCommerceShippingMethodId(long commerceShippingMethodId) {
		FinderPath finderPath = _finderPathCountByCommerceShippingMethodId;

		Object[] finderArgs = new Object[] {commerceShippingMethodId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCESHIPPINGFIXEDOPTION_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCESHIPPINGMETHODID_COMMERCESHIPPINGMETHODID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceShippingMethodId);

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
		_FINDER_COLUMN_COMMERCESHIPPINGMETHODID_COMMERCESHIPPINGMETHODID_2 =
			"commerceShippingFixedOption.commerceShippingMethodId = ?";

	private FinderPath _finderPathFetchByC_K;
	private FinderPath _finderPathCountByC_K;

	/**
	 * Returns the commerce shipping fixed option where companyId = &#63; and key = &#63; or throws a <code>NoSuchShippingFixedOptionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the matching commerce shipping fixed option
	 * @throws NoSuchShippingFixedOptionException if a matching commerce shipping fixed option could not be found
	 */
	@Override
	public CommerceShippingFixedOption findByC_K(long companyId, String key)
		throws NoSuchShippingFixedOptionException {

		CommerceShippingFixedOption commerceShippingFixedOption = fetchByC_K(
			companyId, key);

		if (commerceShippingFixedOption == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", key=");
			sb.append(key);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchShippingFixedOptionException(sb.toString());
		}

		return commerceShippingFixedOption;
	}

	/**
	 * Returns the commerce shipping fixed option where companyId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the matching commerce shipping fixed option, or <code>null</code> if a matching commerce shipping fixed option could not be found
	 */
	@Override
	public CommerceShippingFixedOption fetchByC_K(long companyId, String key) {
		return fetchByC_K(companyId, key, true);
	}

	/**
	 * Returns the commerce shipping fixed option where companyId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce shipping fixed option, or <code>null</code> if a matching commerce shipping fixed option could not be found
	 */
	@Override
	public CommerceShippingFixedOption fetchByC_K(
		long companyId, String key, boolean useFinderCache) {

		key = Objects.toString(key, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, key};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_K, finderArgs);
		}

		if (result instanceof CommerceShippingFixedOption) {
			CommerceShippingFixedOption commerceShippingFixedOption =
				(CommerceShippingFixedOption)result;

			if ((companyId != commerceShippingFixedOption.getCompanyId()) ||
				!Objects.equals(key, commerceShippingFixedOption.getKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTION_WHERE);

			sb.append(_FINDER_COLUMN_C_K_COMPANYID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_K_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_C_K_KEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindKey) {
					queryPos.add(key);
				}

				List<CommerceShippingFixedOption> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_K, finderArgs, list);
					}
				}
				else {
					CommerceShippingFixedOption commerceShippingFixedOption =
						list.get(0);

					result = commerceShippingFixedOption;

					cacheResult(commerceShippingFixedOption);
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
			return (CommerceShippingFixedOption)result;
		}
	}

	/**
	 * Removes the commerce shipping fixed option where companyId = &#63; and key = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the commerce shipping fixed option that was removed
	 */
	@Override
	public CommerceShippingFixedOption removeByC_K(long companyId, String key)
		throws NoSuchShippingFixedOptionException {

		CommerceShippingFixedOption commerceShippingFixedOption = findByC_K(
			companyId, key);

		return remove(commerceShippingFixedOption);
	}

	/**
	 * Returns the number of commerce shipping fixed options where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the number of matching commerce shipping fixed options
	 */
	@Override
	public int countByC_K(long companyId, String key) {
		key = Objects.toString(key, "");

		FinderPath finderPath = _finderPathCountByC_K;

		Object[] finderArgs = new Object[] {companyId, key};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCESHIPPINGFIXEDOPTION_WHERE);

			sb.append(_FINDER_COLUMN_C_K_COMPANYID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_K_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_C_K_KEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindKey) {
					queryPos.add(key);
				}

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

	private static final String _FINDER_COLUMN_C_K_COMPANYID_2 =
		"commerceShippingFixedOption.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_K_KEY_2 =
		"commerceShippingFixedOption.key = ?";

	private static final String _FINDER_COLUMN_C_K_KEY_3 =
		"(commerceShippingFixedOption.key IS NULL OR commerceShippingFixedOption.key = '')";

	public CommerceShippingFixedOptionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("key", "key_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceShippingFixedOption.class);

		setModelImplClass(CommerceShippingFixedOptionImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceShippingFixedOptionTable.INSTANCE);
	}

	/**
	 * Caches the commerce shipping fixed option in the entity cache if it is enabled.
	 *
	 * @param commerceShippingFixedOption the commerce shipping fixed option
	 */
	@Override
	public void cacheResult(
		CommerceShippingFixedOption commerceShippingFixedOption) {

		entityCache.putResult(
			CommerceShippingFixedOptionImpl.class,
			commerceShippingFixedOption.getPrimaryKey(),
			commerceShippingFixedOption);

		finderCache.putResult(
			_finderPathFetchByC_K,
			new Object[] {
				commerceShippingFixedOption.getCompanyId(),
				commerceShippingFixedOption.getKey()
			},
			commerceShippingFixedOption);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce shipping fixed options in the entity cache if it is enabled.
	 *
	 * @param commerceShippingFixedOptions the commerce shipping fixed options
	 */
	@Override
	public void cacheResult(
		List<CommerceShippingFixedOption> commerceShippingFixedOptions) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceShippingFixedOptions.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceShippingFixedOption commerceShippingFixedOption :
				commerceShippingFixedOptions) {

			if (entityCache.getResult(
					CommerceShippingFixedOptionImpl.class,
					commerceShippingFixedOption.getPrimaryKey()) == null) {

				cacheResult(commerceShippingFixedOption);
			}
		}
	}

	/**
	 * Clears the cache for all commerce shipping fixed options.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceShippingFixedOptionImpl.class);

		finderCache.clearCache(CommerceShippingFixedOptionImpl.class);
	}

	/**
	 * Clears the cache for the commerce shipping fixed option.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceShippingFixedOption commerceShippingFixedOption) {

		entityCache.removeResult(
			CommerceShippingFixedOptionImpl.class, commerceShippingFixedOption);
	}

	@Override
	public void clearCache(
		List<CommerceShippingFixedOption> commerceShippingFixedOptions) {

		for (CommerceShippingFixedOption commerceShippingFixedOption :
				commerceShippingFixedOptions) {

			entityCache.removeResult(
				CommerceShippingFixedOptionImpl.class,
				commerceShippingFixedOption);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceShippingFixedOptionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceShippingFixedOptionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceShippingFixedOptionModelImpl
			commerceShippingFixedOptionModelImpl) {

		Object[] args = new Object[] {
			commerceShippingFixedOptionModelImpl.getCompanyId(),
			commerceShippingFixedOptionModelImpl.getKey()
		};

		finderCache.putResult(_finderPathCountByC_K, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_K, args, commerceShippingFixedOptionModelImpl);
	}

	/**
	 * Creates a new commerce shipping fixed option with the primary key. Does not add the commerce shipping fixed option to the database.
	 *
	 * @param commerceShippingFixedOptionId the primary key for the new commerce shipping fixed option
	 * @return the new commerce shipping fixed option
	 */
	@Override
	public CommerceShippingFixedOption create(
		long commerceShippingFixedOptionId) {

		CommerceShippingFixedOption commerceShippingFixedOption =
			new CommerceShippingFixedOptionImpl();

		commerceShippingFixedOption.setNew(true);
		commerceShippingFixedOption.setPrimaryKey(
			commerceShippingFixedOptionId);

		commerceShippingFixedOption.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceShippingFixedOption;
	}

	/**
	 * Removes the commerce shipping fixed option with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceShippingFixedOptionId the primary key of the commerce shipping fixed option
	 * @return the commerce shipping fixed option that was removed
	 * @throws NoSuchShippingFixedOptionException if a commerce shipping fixed option with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOption remove(
			long commerceShippingFixedOptionId)
		throws NoSuchShippingFixedOptionException {

		return remove((Serializable)commerceShippingFixedOptionId);
	}

	/**
	 * Removes the commerce shipping fixed option with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce shipping fixed option
	 * @return the commerce shipping fixed option that was removed
	 * @throws NoSuchShippingFixedOptionException if a commerce shipping fixed option with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOption remove(Serializable primaryKey)
		throws NoSuchShippingFixedOptionException {

		Session session = null;

		try {
			session = openSession();

			CommerceShippingFixedOption commerceShippingFixedOption =
				(CommerceShippingFixedOption)session.get(
					CommerceShippingFixedOptionImpl.class, primaryKey);

			if (commerceShippingFixedOption == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchShippingFixedOptionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceShippingFixedOption);
		}
		catch (NoSuchShippingFixedOptionException noSuchEntityException) {
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
	protected CommerceShippingFixedOption removeImpl(
		CommerceShippingFixedOption commerceShippingFixedOption) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceShippingFixedOption)) {
				commerceShippingFixedOption =
					(CommerceShippingFixedOption)session.get(
						CommerceShippingFixedOptionImpl.class,
						commerceShippingFixedOption.getPrimaryKeyObj());
			}

			if (commerceShippingFixedOption != null) {
				session.delete(commerceShippingFixedOption);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceShippingFixedOption != null) {
			clearCache(commerceShippingFixedOption);
		}

		return commerceShippingFixedOption;
	}

	@Override
	public CommerceShippingFixedOption updateImpl(
		CommerceShippingFixedOption commerceShippingFixedOption) {

		boolean isNew = commerceShippingFixedOption.isNew();

		if (!(commerceShippingFixedOption instanceof
				CommerceShippingFixedOptionModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceShippingFixedOption.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceShippingFixedOption);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceShippingFixedOption proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceShippingFixedOption implementation " +
					commerceShippingFixedOption.getClass());
		}

		CommerceShippingFixedOptionModelImpl
			commerceShippingFixedOptionModelImpl =
				(CommerceShippingFixedOptionModelImpl)
					commerceShippingFixedOption;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceShippingFixedOption.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceShippingFixedOption.setCreateDate(date);
			}
			else {
				commerceShippingFixedOption.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceShippingFixedOptionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceShippingFixedOption.setModifiedDate(date);
			}
			else {
				commerceShippingFixedOption.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceShippingFixedOption);
			}
			else {
				commerceShippingFixedOption =
					(CommerceShippingFixedOption)session.merge(
						commerceShippingFixedOption);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceShippingFixedOptionImpl.class,
			commerceShippingFixedOptionModelImpl, false, true);

		cacheUniqueFindersCache(commerceShippingFixedOptionModelImpl);

		if (isNew) {
			commerceShippingFixedOption.setNew(false);
		}

		commerceShippingFixedOption.resetOriginalValues();

		return commerceShippingFixedOption;
	}

	/**
	 * Returns the commerce shipping fixed option with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce shipping fixed option
	 * @return the commerce shipping fixed option
	 * @throws NoSuchShippingFixedOptionException if a commerce shipping fixed option with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOption findByPrimaryKey(Serializable primaryKey)
		throws NoSuchShippingFixedOptionException {

		CommerceShippingFixedOption commerceShippingFixedOption =
			fetchByPrimaryKey(primaryKey);

		if (commerceShippingFixedOption == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchShippingFixedOptionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceShippingFixedOption;
	}

	/**
	 * Returns the commerce shipping fixed option with the primary key or throws a <code>NoSuchShippingFixedOptionException</code> if it could not be found.
	 *
	 * @param commerceShippingFixedOptionId the primary key of the commerce shipping fixed option
	 * @return the commerce shipping fixed option
	 * @throws NoSuchShippingFixedOptionException if a commerce shipping fixed option with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOption findByPrimaryKey(
			long commerceShippingFixedOptionId)
		throws NoSuchShippingFixedOptionException {

		return findByPrimaryKey((Serializable)commerceShippingFixedOptionId);
	}

	/**
	 * Returns the commerce shipping fixed option with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceShippingFixedOptionId the primary key of the commerce shipping fixed option
	 * @return the commerce shipping fixed option, or <code>null</code> if a commerce shipping fixed option with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOption fetchByPrimaryKey(
		long commerceShippingFixedOptionId) {

		return fetchByPrimaryKey((Serializable)commerceShippingFixedOptionId);
	}

	/**
	 * Returns all the commerce shipping fixed options.
	 *
	 * @return the commerce shipping fixed options
	 */
	@Override
	public List<CommerceShippingFixedOption> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipping fixed options.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed options
	 * @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	 * @return the range of commerce shipping fixed options
	 */
	@Override
	public List<CommerceShippingFixedOption> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed options.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed options
	 * @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce shipping fixed options
	 */
	@Override
	public List<CommerceShippingFixedOption> findAll(
		int start, int end,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed options.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed options
	 * @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce shipping fixed options
	 */
	@Override
	public List<CommerceShippingFixedOption> findAll(
		int start, int end,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator,
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

		List<CommerceShippingFixedOption> list = null;

		if (useFinderCache) {
			list = (List<CommerceShippingFixedOption>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCESHIPPINGFIXEDOPTION;

				sql = sql.concat(
					CommerceShippingFixedOptionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceShippingFixedOption>)QueryUtil.list(
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
	 * Removes all the commerce shipping fixed options from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceShippingFixedOption commerceShippingFixedOption :
				findAll()) {

			remove(commerceShippingFixedOption);
		}
	}

	/**
	 * Returns the number of commerce shipping fixed options.
	 *
	 * @return the number of commerce shipping fixed options
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
					_SQL_COUNT_COMMERCESHIPPINGFIXEDOPTION);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "commerceShippingFixedOptionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCESHIPPINGFIXEDOPTION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceShippingFixedOptionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce shipping fixed option persistence.
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

		_finderPathWithPaginationFindByCommerceShippingMethodId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommerceShippingMethodId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"commerceShippingMethodId"}, true);

		_finderPathWithoutPaginationFindByCommerceShippingMethodId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceShippingMethodId",
				new String[] {Long.class.getName()},
				new String[] {"commerceShippingMethodId"}, true);

		_finderPathCountByCommerceShippingMethodId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceShippingMethodId",
			new String[] {Long.class.getName()},
			new String[] {"commerceShippingMethodId"}, false);

		_finderPathFetchByC_K = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_K",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "key_"}, true);

		_finderPathCountByC_K = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_K",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "key_"}, false);

		_setCommerceShippingFixedOptionUtilPersistence(this);
	}

	public void destroy() {
		_setCommerceShippingFixedOptionUtilPersistence(null);

		entityCache.removeCache(
			CommerceShippingFixedOptionImpl.class.getName());
	}

	private void _setCommerceShippingFixedOptionUtilPersistence(
		CommerceShippingFixedOptionPersistence
			commerceShippingFixedOptionPersistence) {

		try {
			Field field =
				CommerceShippingFixedOptionUtil.class.getDeclaredField(
					"_persistence");

			field.setAccessible(true);

			field.set(null, commerceShippingFixedOptionPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCESHIPPINGFIXEDOPTION =
		"SELECT commerceShippingFixedOption FROM CommerceShippingFixedOption commerceShippingFixedOption";

	private static final String _SQL_SELECT_COMMERCESHIPPINGFIXEDOPTION_WHERE =
		"SELECT commerceShippingFixedOption FROM CommerceShippingFixedOption commerceShippingFixedOption WHERE ";

	private static final String _SQL_COUNT_COMMERCESHIPPINGFIXEDOPTION =
		"SELECT COUNT(commerceShippingFixedOption) FROM CommerceShippingFixedOption commerceShippingFixedOption";

	private static final String _SQL_COUNT_COMMERCESHIPPINGFIXEDOPTION_WHERE =
		"SELECT COUNT(commerceShippingFixedOption) FROM CommerceShippingFixedOption commerceShippingFixedOption WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceShippingFixedOption.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceShippingFixedOption exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceShippingFixedOption exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceShippingFixedOptionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"key"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}