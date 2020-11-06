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

package com.liferay.commerce.service.persistence.impl;

import com.liferay.commerce.exception.NoSuchAddressException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceAddressTable;
import com.liferay.commerce.model.impl.CommerceAddressImpl;
import com.liferay.commerce.model.impl.CommerceAddressModelImpl;
import com.liferay.commerce.service.persistence.CommerceAddressPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * The persistence implementation for the commerce address service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceAddressPersistenceImpl
	extends BasePersistenceImpl<CommerceAddress>
	implements CommerceAddressPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceAddressUtil</code> to access the commerce address persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceAddressImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCommerceRegionId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceRegionId;
	private FinderPath _finderPathCountByCommerceRegionId;

	/**
	 * Returns all the commerce addresses where commerceRegionId = &#63;.
	 *
	 * @param commerceRegionId the commerce region ID
	 * @return the matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByCommerceRegionId(long commerceRegionId) {
		return findByCommerceRegionId(
			commerceRegionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce addresses where commerceRegionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param commerceRegionId the commerce region ID
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @return the range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByCommerceRegionId(
		long commerceRegionId, int start, int end) {

		return findByCommerceRegionId(commerceRegionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where commerceRegionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param commerceRegionId the commerce region ID
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByCommerceRegionId(
		long commerceRegionId, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {

		return findByCommerceRegionId(
			commerceRegionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where commerceRegionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param commerceRegionId the commerce region ID
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByCommerceRegionId(
		long commerceRegionId, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCommerceRegionId;
				finderArgs = new Object[] {commerceRegionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceRegionId;
			finderArgs = new Object[] {
				commerceRegionId, start, end, orderByComparator
			};
		}

		List<CommerceAddress> list = null;

		if (useFinderCache) {
			list = (List<CommerceAddress>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAddress commerceAddress : list) {
					if (commerceRegionId !=
							commerceAddress.getCommerceRegionId()) {

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

			sb.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEREGIONID_COMMERCEREGIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceRegionId);

				list = (List<CommerceAddress>)QueryUtil.list(
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
	 * Returns the first commerce address in the ordered set where commerceRegionId = &#63;.
	 *
	 * @param commerceRegionId the commerce region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByCommerceRegionId_First(
			long commerceRegionId,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = fetchByCommerceRegionId_First(
			commerceRegionId, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceRegionId=");
		sb.append(commerceRegionId);

		sb.append("}");

		throw new NoSuchAddressException(sb.toString());
	}

	/**
	 * Returns the first commerce address in the ordered set where commerceRegionId = &#63;.
	 *
	 * @param commerceRegionId the commerce region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByCommerceRegionId_First(
		long commerceRegionId,
		OrderByComparator<CommerceAddress> orderByComparator) {

		List<CommerceAddress> list = findByCommerceRegionId(
			commerceRegionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce address in the ordered set where commerceRegionId = &#63;.
	 *
	 * @param commerceRegionId the commerce region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByCommerceRegionId_Last(
			long commerceRegionId,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = fetchByCommerceRegionId_Last(
			commerceRegionId, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceRegionId=");
		sb.append(commerceRegionId);

		sb.append("}");

		throw new NoSuchAddressException(sb.toString());
	}

	/**
	 * Returns the last commerce address in the ordered set where commerceRegionId = &#63;.
	 *
	 * @param commerceRegionId the commerce region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByCommerceRegionId_Last(
		long commerceRegionId,
		OrderByComparator<CommerceAddress> orderByComparator) {

		int count = countByCommerceRegionId(commerceRegionId);

		if (count == 0) {
			return null;
		}

		List<CommerceAddress> list = findByCommerceRegionId(
			commerceRegionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce addresses before and after the current commerce address in the ordered set where commerceRegionId = &#63;.
	 *
	 * @param commerceAddressId the primary key of the current commerce address
	 * @param commerceRegionId the commerce region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce address
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress[] findByCommerceRegionId_PrevAndNext(
			long commerceAddressId, long commerceRegionId,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = findByPrimaryKey(commerceAddressId);

		Session session = null;

		try {
			session = openSession();

			CommerceAddress[] array = new CommerceAddressImpl[3];

			array[0] = getByCommerceRegionId_PrevAndNext(
				session, commerceAddress, commerceRegionId, orderByComparator,
				true);

			array[1] = commerceAddress;

			array[2] = getByCommerceRegionId_PrevAndNext(
				session, commerceAddress, commerceRegionId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceAddress getByCommerceRegionId_PrevAndNext(
		Session session, CommerceAddress commerceAddress, long commerceRegionId,
		OrderByComparator<CommerceAddress> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCEREGIONID_COMMERCEREGIONID_2);

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
			sb.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceRegionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceAddress)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceAddress> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce addresses where commerceRegionId = &#63; from the database.
	 *
	 * @param commerceRegionId the commerce region ID
	 */
	@Override
	public void removeByCommerceRegionId(long commerceRegionId) {
		for (CommerceAddress commerceAddress :
				findByCommerceRegionId(
					commerceRegionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceAddress);
		}
	}

	/**
	 * Returns the number of commerce addresses where commerceRegionId = &#63;.
	 *
	 * @param commerceRegionId the commerce region ID
	 * @return the number of matching commerce addresses
	 */
	@Override
	public int countByCommerceRegionId(long commerceRegionId) {
		FinderPath finderPath = _finderPathCountByCommerceRegionId;

		Object[] finderArgs = new Object[] {commerceRegionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEADDRESS_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEREGIONID_COMMERCEREGIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceRegionId);

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
		_FINDER_COLUMN_COMMERCEREGIONID_COMMERCEREGIONID_2 =
			"commerceAddress.commerceRegionId = ?";

	private FinderPath _finderPathWithPaginationFindByCommerceCountryId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceCountryId;
	private FinderPath _finderPathCountByCommerceCountryId;

	/**
	 * Returns all the commerce addresses where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @return the matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByCommerceCountryId(
		long commerceCountryId) {

		return findByCommerceCountryId(
			commerceCountryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce addresses where commerceCountryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @return the range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByCommerceCountryId(
		long commerceCountryId, int start, int end) {

		return findByCommerceCountryId(commerceCountryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where commerceCountryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {

		return findByCommerceCountryId(
			commerceCountryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where commerceCountryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceCountryId;
				finderArgs = new Object[] {commerceCountryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceCountryId;
			finderArgs = new Object[] {
				commerceCountryId, start, end, orderByComparator
			};
		}

		List<CommerceAddress> list = null;

		if (useFinderCache) {
			list = (List<CommerceAddress>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAddress commerceAddress : list) {
					if (commerceCountryId !=
							commerceAddress.getCommerceCountryId()) {

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

			sb.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCECOUNTRYID_COMMERCECOUNTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceCountryId);

				list = (List<CommerceAddress>)QueryUtil.list(
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
	 * Returns the first commerce address in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByCommerceCountryId_First(
			long commerceCountryId,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = fetchByCommerceCountryId_First(
			commerceCountryId, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceCountryId=");
		sb.append(commerceCountryId);

		sb.append("}");

		throw new NoSuchAddressException(sb.toString());
	}

	/**
	 * Returns the first commerce address in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByCommerceCountryId_First(
		long commerceCountryId,
		OrderByComparator<CommerceAddress> orderByComparator) {

		List<CommerceAddress> list = findByCommerceCountryId(
			commerceCountryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce address in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByCommerceCountryId_Last(
			long commerceCountryId,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = fetchByCommerceCountryId_Last(
			commerceCountryId, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceCountryId=");
		sb.append(commerceCountryId);

		sb.append("}");

		throw new NoSuchAddressException(sb.toString());
	}

	/**
	 * Returns the last commerce address in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByCommerceCountryId_Last(
		long commerceCountryId,
		OrderByComparator<CommerceAddress> orderByComparator) {

		int count = countByCommerceCountryId(commerceCountryId);

		if (count == 0) {
			return null;
		}

		List<CommerceAddress> list = findByCommerceCountryId(
			commerceCountryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce addresses before and after the current commerce address in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceAddressId the primary key of the current commerce address
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce address
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress[] findByCommerceCountryId_PrevAndNext(
			long commerceAddressId, long commerceCountryId,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = findByPrimaryKey(commerceAddressId);

		Session session = null;

		try {
			session = openSession();

			CommerceAddress[] array = new CommerceAddressImpl[3];

			array[0] = getByCommerceCountryId_PrevAndNext(
				session, commerceAddress, commerceCountryId, orderByComparator,
				true);

			array[1] = commerceAddress;

			array[2] = getByCommerceCountryId_PrevAndNext(
				session, commerceAddress, commerceCountryId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceAddress getByCommerceCountryId_PrevAndNext(
		Session session, CommerceAddress commerceAddress,
		long commerceCountryId,
		OrderByComparator<CommerceAddress> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCECOUNTRYID_COMMERCECOUNTRYID_2);

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
			sb.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceCountryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceAddress)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceAddress> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce addresses where commerceCountryId = &#63; from the database.
	 *
	 * @param commerceCountryId the commerce country ID
	 */
	@Override
	public void removeByCommerceCountryId(long commerceCountryId) {
		for (CommerceAddress commerceAddress :
				findByCommerceCountryId(
					commerceCountryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceAddress);
		}
	}

	/**
	 * Returns the number of commerce addresses where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @return the number of matching commerce addresses
	 */
	@Override
	public int countByCommerceCountryId(long commerceCountryId) {
		FinderPath finderPath = _finderPathCountByCommerceCountryId;

		Object[] finderArgs = new Object[] {commerceCountryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEADDRESS_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCECOUNTRYID_COMMERCECOUNTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceCountryId);

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
		_FINDER_COLUMN_COMMERCECOUNTRYID_COMMERCECOUNTRYID_2 =
			"commerceAddress.commerceCountryId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the commerce addresses where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByC_C(long classNameId, long classPK) {
		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce addresses where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @return the range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {classNameId, classPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<CommerceAddress> list = null;

		if (useFinderCache) {
			list = (List<CommerceAddress>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAddress commerceAddress : list) {
					if ((classNameId != commerceAddress.getClassNameId()) ||
						(classPK != commerceAddress.getClassPK())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<CommerceAddress>)QueryUtil.list(
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
	 * Returns the first commerce address in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = fetchByC_C_First(
			classNameId, classPK, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchAddressException(sb.toString());
	}

	/**
	 * Returns the first commerce address in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<CommerceAddress> orderByComparator) {

		List<CommerceAddress> list = findByC_C(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce address in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = fetchByC_C_Last(
			classNameId, classPK, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchAddressException(sb.toString());
	}

	/**
	 * Returns the last commerce address in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<CommerceAddress> orderByComparator) {

		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<CommerceAddress> list = findByC_C(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce addresses before and after the current commerce address in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commerceAddressId the primary key of the current commerce address
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce address
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress[] findByC_C_PrevAndNext(
			long commerceAddressId, long classNameId, long classPK,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = findByPrimaryKey(commerceAddressId);

		Session session = null;

		try {
			session = openSession();

			CommerceAddress[] array = new CommerceAddressImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, commerceAddress, classNameId, classPK,
				orderByComparator, true);

			array[1] = commerceAddress;

			array[2] = getByC_C_PrevAndNext(
				session, commerceAddress, classNameId, classPK,
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

	protected CommerceAddress getByC_C_PrevAndNext(
		Session session, CommerceAddress commerceAddress, long classNameId,
		long classPK, OrderByComparator<CommerceAddress> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			sb.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceAddress)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceAddress> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce addresses where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (CommerceAddress commerceAddress :
				findByC_C(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceAddress);
		}
	}

	/**
	 * Returns the number of commerce addresses where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching commerce addresses
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEADDRESS_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"commerceAddress.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"commerceAddress.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_C;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C;
	private FinderPath _finderPathCountByG_C_C;

	/**
	 * Returns all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_C_C(
		long groupId, long classNameId, long classPK) {

		return findByG_C_C(
			groupId, classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @return the range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end) {

		return findByG_C_C(groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {

		return findByG_C_C(
			groupId, classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_C_C;
				finderArgs = new Object[] {groupId, classNameId, classPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_C_C;
			finderArgs = new Object[] {
				groupId, classNameId, classPK, start, end, orderByComparator
			};
		}

		List<CommerceAddress> list = null;

		if (useFinderCache) {
			list = (List<CommerceAddress>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAddress commerceAddress : list) {
					if ((groupId != commerceAddress.getGroupId()) ||
						(classNameId != commerceAddress.getClassNameId()) ||
						(classPK != commerceAddress.getClassPK())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<CommerceAddress>)QueryUtil.list(
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
	 * Returns the first commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByG_C_C_First(
			long groupId, long classNameId, long classPK,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = fetchByG_C_C_First(
			groupId, classNameId, classPK, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchAddressException(sb.toString());
	}

	/**
	 * Returns the first commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByG_C_C_First(
		long groupId, long classNameId, long classPK,
		OrderByComparator<CommerceAddress> orderByComparator) {

		List<CommerceAddress> list = findByG_C_C(
			groupId, classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByG_C_C_Last(
			long groupId, long classNameId, long classPK,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = fetchByG_C_C_Last(
			groupId, classNameId, classPK, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchAddressException(sb.toString());
	}

	/**
	 * Returns the last commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByG_C_C_Last(
		long groupId, long classNameId, long classPK,
		OrderByComparator<CommerceAddress> orderByComparator) {

		int count = countByG_C_C(groupId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<CommerceAddress> list = findByG_C_C(
			groupId, classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce addresses before and after the current commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commerceAddressId the primary key of the current commerce address
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce address
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress[] findByG_C_C_PrevAndNext(
			long commerceAddressId, long groupId, long classNameId,
			long classPK, OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = findByPrimaryKey(commerceAddressId);

		Session session = null;

		try {
			session = openSession();

			CommerceAddress[] array = new CommerceAddressImpl[3];

			array[0] = getByG_C_C_PrevAndNext(
				session, commerceAddress, groupId, classNameId, classPK,
				orderByComparator, true);

			array[1] = commerceAddress;

			array[2] = getByG_C_C_PrevAndNext(
				session, commerceAddress, groupId, classNameId, classPK,
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

	protected CommerceAddress getByG_C_C_PrevAndNext(
		Session session, CommerceAddress commerceAddress, long groupId,
		long classNameId, long classPK,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

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
			sb.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceAddress)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceAddress> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByG_C_C(long groupId, long classNameId, long classPK) {
		for (CommerceAddress commerceAddress :
				findByG_C_C(
					groupId, classNameId, classPK, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceAddress);
		}
	}

	/**
	 * Returns the number of commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching commerce addresses
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByG_C_C;

		Object[] finderArgs = new Object[] {groupId, classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCEADDRESS_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 =
		"commerceAddress.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 =
		"commerceAddress.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_CLASSPK_2 =
		"commerceAddress.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByC_C_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C_C;
	private FinderPath _finderPathCountByC_C_C;

	/**
	 * Returns all the commerce addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByC_C_C(
		long companyId, long classNameId, long classPK) {

		return findByC_C_C(
			companyId, classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @return the range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end) {

		return findByC_C_C(companyId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {

		return findByC_C_C(
			companyId, classNameId, classPK, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C_C;
				finderArgs = new Object[] {companyId, classNameId, classPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C_C;
			finderArgs = new Object[] {
				companyId, classNameId, classPK, start, end, orderByComparator
			};
		}

		List<CommerceAddress> list = null;

		if (useFinderCache) {
			list = (List<CommerceAddress>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAddress commerceAddress : list) {
					if ((companyId != commerceAddress.getCompanyId()) ||
						(classNameId != commerceAddress.getClassNameId()) ||
						(classPK != commerceAddress.getClassPK())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<CommerceAddress>)QueryUtil.list(
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
	 * Returns the first commerce address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByC_C_C_First(
			long companyId, long classNameId, long classPK,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = fetchByC_C_C_First(
			companyId, classNameId, classPK, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchAddressException(sb.toString());
	}

	/**
	 * Returns the first commerce address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByC_C_C_First(
		long companyId, long classNameId, long classPK,
		OrderByComparator<CommerceAddress> orderByComparator) {

		List<CommerceAddress> list = findByC_C_C(
			companyId, classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByC_C_C_Last(
			long companyId, long classNameId, long classPK,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = fetchByC_C_C_Last(
			companyId, classNameId, classPK, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchAddressException(sb.toString());
	}

	/**
	 * Returns the last commerce address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByC_C_C_Last(
		long companyId, long classNameId, long classPK,
		OrderByComparator<CommerceAddress> orderByComparator) {

		int count = countByC_C_C(companyId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<CommerceAddress> list = findByC_C_C(
			companyId, classNameId, classPK, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce addresses before and after the current commerce address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commerceAddressId the primary key of the current commerce address
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce address
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress[] findByC_C_C_PrevAndNext(
			long commerceAddressId, long companyId, long classNameId,
			long classPK, OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = findByPrimaryKey(commerceAddressId);

		Session session = null;

		try {
			session = openSession();

			CommerceAddress[] array = new CommerceAddressImpl[3];

			array[0] = getByC_C_C_PrevAndNext(
				session, commerceAddress, companyId, classNameId, classPK,
				orderByComparator, true);

			array[1] = commerceAddress;

			array[2] = getByC_C_C_PrevAndNext(
				session, commerceAddress, companyId, classNameId, classPK,
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

	protected CommerceAddress getByC_C_C_PrevAndNext(
		Session session, CommerceAddress commerceAddress, long companyId,
		long classNameId, long classPK,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

		sb.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

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
			sb.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceAddress)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceAddress> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C_C(long companyId, long classNameId, long classPK) {
		for (CommerceAddress commerceAddress :
				findByC_C_C(
					companyId, classNameId, classPK, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceAddress);
		}
	}

	/**
	 * Returns the number of commerce addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching commerce addresses
	 */
	@Override
	public int countByC_C_C(long companyId, long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C_C;

		Object[] finderArgs = new Object[] {companyId, classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCEADDRESS_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_C_COMPANYID_2 =
		"commerceAddress.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CLASSNAMEID_2 =
		"commerceAddress.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 =
		"commerceAddress.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_C_DB;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C_DB;
	private FinderPath _finderPathCountByG_C_C_DB;

	/**
	 * Returns all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultBilling the default billing
	 * @return the matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_C_C_DB(
		long groupId, long classNameId, long classPK, boolean defaultBilling) {

		return findByG_C_C_DB(
			groupId, classNameId, classPK, defaultBilling, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultBilling the default billing
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @return the range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_C_C_DB(
		long groupId, long classNameId, long classPK, boolean defaultBilling,
		int start, int end) {

		return findByG_C_C_DB(
			groupId, classNameId, classPK, defaultBilling, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultBilling the default billing
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_C_C_DB(
		long groupId, long classNameId, long classPK, boolean defaultBilling,
		int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {

		return findByG_C_C_DB(
			groupId, classNameId, classPK, defaultBilling, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultBilling the default billing
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_C_C_DB(
		long groupId, long classNameId, long classPK, boolean defaultBilling,
		int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_C_C_DB;
				finderArgs = new Object[] {
					groupId, classNameId, classPK, defaultBilling
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_C_C_DB;
			finderArgs = new Object[] {
				groupId, classNameId, classPK, defaultBilling, start, end,
				orderByComparator
			};
		}

		List<CommerceAddress> list = null;

		if (useFinderCache) {
			list = (List<CommerceAddress>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAddress commerceAddress : list) {
					if ((groupId != commerceAddress.getGroupId()) ||
						(classNameId != commerceAddress.getClassNameId()) ||
						(classPK != commerceAddress.getClassPK()) ||
						(defaultBilling !=
							commerceAddress.isDefaultBilling())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_DB_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_DB_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_DB_CLASSPK_2);

			sb.append(_FINDER_COLUMN_G_C_C_DB_DEFAULTBILLING_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(defaultBilling);

				list = (List<CommerceAddress>)QueryUtil.list(
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
	 * Returns the first commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultBilling the default billing
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByG_C_C_DB_First(
			long groupId, long classNameId, long classPK,
			boolean defaultBilling,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = fetchByG_C_C_DB_First(
			groupId, classNameId, classPK, defaultBilling, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", defaultBilling=");
		sb.append(defaultBilling);

		sb.append("}");

		throw new NoSuchAddressException(sb.toString());
	}

	/**
	 * Returns the first commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultBilling the default billing
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByG_C_C_DB_First(
		long groupId, long classNameId, long classPK, boolean defaultBilling,
		OrderByComparator<CommerceAddress> orderByComparator) {

		List<CommerceAddress> list = findByG_C_C_DB(
			groupId, classNameId, classPK, defaultBilling, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultBilling the default billing
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByG_C_C_DB_Last(
			long groupId, long classNameId, long classPK,
			boolean defaultBilling,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = fetchByG_C_C_DB_Last(
			groupId, classNameId, classPK, defaultBilling, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", defaultBilling=");
		sb.append(defaultBilling);

		sb.append("}");

		throw new NoSuchAddressException(sb.toString());
	}

	/**
	 * Returns the last commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultBilling the default billing
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByG_C_C_DB_Last(
		long groupId, long classNameId, long classPK, boolean defaultBilling,
		OrderByComparator<CommerceAddress> orderByComparator) {

		int count = countByG_C_C_DB(
			groupId, classNameId, classPK, defaultBilling);

		if (count == 0) {
			return null;
		}

		List<CommerceAddress> list = findByG_C_C_DB(
			groupId, classNameId, classPK, defaultBilling, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce addresses before and after the current commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	 *
	 * @param commerceAddressId the primary key of the current commerce address
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultBilling the default billing
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce address
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress[] findByG_C_C_DB_PrevAndNext(
			long commerceAddressId, long groupId, long classNameId,
			long classPK, boolean defaultBilling,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = findByPrimaryKey(commerceAddressId);

		Session session = null;

		try {
			session = openSession();

			CommerceAddress[] array = new CommerceAddressImpl[3];

			array[0] = getByG_C_C_DB_PrevAndNext(
				session, commerceAddress, groupId, classNameId, classPK,
				defaultBilling, orderByComparator, true);

			array[1] = commerceAddress;

			array[2] = getByG_C_C_DB_PrevAndNext(
				session, commerceAddress, groupId, classNameId, classPK,
				defaultBilling, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceAddress getByG_C_C_DB_PrevAndNext(
		Session session, CommerceAddress commerceAddress, long groupId,
		long classNameId, long classPK, boolean defaultBilling,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_DB_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_DB_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_DB_CLASSPK_2);

		sb.append(_FINDER_COLUMN_G_C_C_DB_DEFAULTBILLING_2);

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
			sb.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		queryPos.add(defaultBilling);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceAddress)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceAddress> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultBilling the default billing
	 */
	@Override
	public void removeByG_C_C_DB(
		long groupId, long classNameId, long classPK, boolean defaultBilling) {

		for (CommerceAddress commerceAddress :
				findByG_C_C_DB(
					groupId, classNameId, classPK, defaultBilling,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceAddress);
		}
	}

	/**
	 * Returns the number of commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultBilling the default billing
	 * @return the number of matching commerce addresses
	 */
	@Override
	public int countByG_C_C_DB(
		long groupId, long classNameId, long classPK, boolean defaultBilling) {

		FinderPath finderPath = _finderPathCountByG_C_C_DB;

		Object[] finderArgs = new Object[] {
			groupId, classNameId, classPK, defaultBilling
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_COMMERCEADDRESS_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_DB_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_DB_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_DB_CLASSPK_2);

			sb.append(_FINDER_COLUMN_G_C_C_DB_DEFAULTBILLING_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(defaultBilling);

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

	private static final String _FINDER_COLUMN_G_C_C_DB_GROUPID_2 =
		"commerceAddress.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_DB_CLASSNAMEID_2 =
		"commerceAddress.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_DB_CLASSPK_2 =
		"commerceAddress.classPK = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_DB_DEFAULTBILLING_2 =
		"commerceAddress.defaultBilling = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_C_DS;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C_DS;
	private FinderPath _finderPathCountByG_C_C_DS;

	/**
	 * Returns all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultShipping the default shipping
	 * @return the matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_C_C_DS(
		long groupId, long classNameId, long classPK, boolean defaultShipping) {

		return findByG_C_C_DS(
			groupId, classNameId, classPK, defaultShipping, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultShipping the default shipping
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @return the range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_C_C_DS(
		long groupId, long classNameId, long classPK, boolean defaultShipping,
		int start, int end) {

		return findByG_C_C_DS(
			groupId, classNameId, classPK, defaultShipping, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultShipping the default shipping
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_C_C_DS(
		long groupId, long classNameId, long classPK, boolean defaultShipping,
		int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {

		return findByG_C_C_DS(
			groupId, classNameId, classPK, defaultShipping, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultShipping the default shipping
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_C_C_DS(
		long groupId, long classNameId, long classPK, boolean defaultShipping,
		int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_C_C_DS;
				finderArgs = new Object[] {
					groupId, classNameId, classPK, defaultShipping
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_C_C_DS;
			finderArgs = new Object[] {
				groupId, classNameId, classPK, defaultShipping, start, end,
				orderByComparator
			};
		}

		List<CommerceAddress> list = null;

		if (useFinderCache) {
			list = (List<CommerceAddress>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAddress commerceAddress : list) {
					if ((groupId != commerceAddress.getGroupId()) ||
						(classNameId != commerceAddress.getClassNameId()) ||
						(classPK != commerceAddress.getClassPK()) ||
						(defaultShipping !=
							commerceAddress.isDefaultShipping())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_DS_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_DS_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_DS_CLASSPK_2);

			sb.append(_FINDER_COLUMN_G_C_C_DS_DEFAULTSHIPPING_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(defaultShipping);

				list = (List<CommerceAddress>)QueryUtil.list(
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
	 * Returns the first commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultShipping the default shipping
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByG_C_C_DS_First(
			long groupId, long classNameId, long classPK,
			boolean defaultShipping,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = fetchByG_C_C_DS_First(
			groupId, classNameId, classPK, defaultShipping, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", defaultShipping=");
		sb.append(defaultShipping);

		sb.append("}");

		throw new NoSuchAddressException(sb.toString());
	}

	/**
	 * Returns the first commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultShipping the default shipping
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByG_C_C_DS_First(
		long groupId, long classNameId, long classPK, boolean defaultShipping,
		OrderByComparator<CommerceAddress> orderByComparator) {

		List<CommerceAddress> list = findByG_C_C_DS(
			groupId, classNameId, classPK, defaultShipping, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultShipping the default shipping
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByG_C_C_DS_Last(
			long groupId, long classNameId, long classPK,
			boolean defaultShipping,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = fetchByG_C_C_DS_Last(
			groupId, classNameId, classPK, defaultShipping, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", defaultShipping=");
		sb.append(defaultShipping);

		sb.append("}");

		throw new NoSuchAddressException(sb.toString());
	}

	/**
	 * Returns the last commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultShipping the default shipping
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByG_C_C_DS_Last(
		long groupId, long classNameId, long classPK, boolean defaultShipping,
		OrderByComparator<CommerceAddress> orderByComparator) {

		int count = countByG_C_C_DS(
			groupId, classNameId, classPK, defaultShipping);

		if (count == 0) {
			return null;
		}

		List<CommerceAddress> list = findByG_C_C_DS(
			groupId, classNameId, classPK, defaultShipping, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce addresses before and after the current commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	 *
	 * @param commerceAddressId the primary key of the current commerce address
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultShipping the default shipping
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce address
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress[] findByG_C_C_DS_PrevAndNext(
			long commerceAddressId, long groupId, long classNameId,
			long classPK, boolean defaultShipping,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = findByPrimaryKey(commerceAddressId);

		Session session = null;

		try {
			session = openSession();

			CommerceAddress[] array = new CommerceAddressImpl[3];

			array[0] = getByG_C_C_DS_PrevAndNext(
				session, commerceAddress, groupId, classNameId, classPK,
				defaultShipping, orderByComparator, true);

			array[1] = commerceAddress;

			array[2] = getByG_C_C_DS_PrevAndNext(
				session, commerceAddress, groupId, classNameId, classPK,
				defaultShipping, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceAddress getByG_C_C_DS_PrevAndNext(
		Session session, CommerceAddress commerceAddress, long groupId,
		long classNameId, long classPK, boolean defaultShipping,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_DS_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_DS_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_DS_CLASSPK_2);

		sb.append(_FINDER_COLUMN_G_C_C_DS_DEFAULTSHIPPING_2);

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
			sb.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		queryPos.add(defaultShipping);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceAddress)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceAddress> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultShipping the default shipping
	 */
	@Override
	public void removeByG_C_C_DS(
		long groupId, long classNameId, long classPK, boolean defaultShipping) {

		for (CommerceAddress commerceAddress :
				findByG_C_C_DS(
					groupId, classNameId, classPK, defaultShipping,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceAddress);
		}
	}

	/**
	 * Returns the number of commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param defaultShipping the default shipping
	 * @return the number of matching commerce addresses
	 */
	@Override
	public int countByG_C_C_DS(
		long groupId, long classNameId, long classPK, boolean defaultShipping) {

		FinderPath finderPath = _finderPathCountByG_C_C_DS;

		Object[] finderArgs = new Object[] {
			groupId, classNameId, classPK, defaultShipping
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_COMMERCEADDRESS_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_DS_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_DS_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_DS_CLASSPK_2);

			sb.append(_FINDER_COLUMN_G_C_C_DS_DEFAULTSHIPPING_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(defaultShipping);

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

	private static final String _FINDER_COLUMN_G_C_C_DS_GROUPID_2 =
		"commerceAddress.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_DS_CLASSNAMEID_2 =
		"commerceAddress.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_DS_CLASSPK_2 =
		"commerceAddress.classPK = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_DS_DEFAULTSHIPPING_2 =
		"commerceAddress.defaultShipping = ?";

	private FinderPath _finderPathWithPaginationFindByC_C_C_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C_C_C;
	private FinderPath _finderPathCountByC_C_C_C;

	/**
	 * Returns all the commerce addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByC_C_C_C(
		long companyId, long classNameId, long classPK, int type) {

		return findByC_C_C_C(
			companyId, classNameId, classPK, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @return the range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByC_C_C_C(
		long companyId, long classNameId, long classPK, int type, int start,
		int end) {

		return findByC_C_C_C(
			companyId, classNameId, classPK, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByC_C_C_C(
		long companyId, long classNameId, long classPK, int type, int start,
		int end, OrderByComparator<CommerceAddress> orderByComparator) {

		return findByC_C_C_C(
			companyId, classNameId, classPK, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByC_C_C_C(
		long companyId, long classNameId, long classPK, int type, int start,
		int end, OrderByComparator<CommerceAddress> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C_C_C;
				finderArgs = new Object[] {
					companyId, classNameId, classPK, type
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C_C_C;
			finderArgs = new Object[] {
				companyId, classNameId, classPK, type, start, end,
				orderByComparator
			};
		}

		List<CommerceAddress> list = null;

		if (useFinderCache) {
			list = (List<CommerceAddress>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAddress commerceAddress : list) {
					if ((companyId != commerceAddress.getCompanyId()) ||
						(classNameId != commerceAddress.getClassNameId()) ||
						(classPK != commerceAddress.getClassPK()) ||
						(type != commerceAddress.getType())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_C_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_C_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_C_C_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(type);

				list = (List<CommerceAddress>)QueryUtil.list(
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
	 * Returns the first commerce address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByC_C_C_C_First(
			long companyId, long classNameId, long classPK, int type,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = fetchByC_C_C_C_First(
			companyId, classNameId, classPK, type, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchAddressException(sb.toString());
	}

	/**
	 * Returns the first commerce address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByC_C_C_C_First(
		long companyId, long classNameId, long classPK, int type,
		OrderByComparator<CommerceAddress> orderByComparator) {

		List<CommerceAddress> list = findByC_C_C_C(
			companyId, classNameId, classPK, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByC_C_C_C_Last(
			long companyId, long classNameId, long classPK, int type,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = fetchByC_C_C_C_Last(
			companyId, classNameId, classPK, type, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchAddressException(sb.toString());
	}

	/**
	 * Returns the last commerce address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByC_C_C_C_Last(
		long companyId, long classNameId, long classPK, int type,
		OrderByComparator<CommerceAddress> orderByComparator) {

		int count = countByC_C_C_C(companyId, classNameId, classPK, type);

		if (count == 0) {
			return null;
		}

		List<CommerceAddress> list = findByC_C_C_C(
			companyId, classNameId, classPK, type, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce addresses before and after the current commerce address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param commerceAddressId the primary key of the current commerce address
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce address
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress[] findByC_C_C_C_PrevAndNext(
			long commerceAddressId, long companyId, long classNameId,
			long classPK, int type,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = findByPrimaryKey(commerceAddressId);

		Session session = null;

		try {
			session = openSession();

			CommerceAddress[] array = new CommerceAddressImpl[3];

			array[0] = getByC_C_C_C_PrevAndNext(
				session, commerceAddress, companyId, classNameId, classPK, type,
				orderByComparator, true);

			array[1] = commerceAddress;

			array[2] = getByC_C_C_C_PrevAndNext(
				session, commerceAddress, companyId, classNameId, classPK, type,
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

	protected CommerceAddress getByC_C_C_C_PrevAndNext(
		Session session, CommerceAddress commerceAddress, long companyId,
		long classNameId, long classPK, int type,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

		sb.append(_FINDER_COLUMN_C_C_C_C_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_C_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_C_C_CLASSPK_2);

		sb.append(_FINDER_COLUMN_C_C_C_C_TYPE_2);

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
			sb.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceAddress)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceAddress> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 */
	@Override
	public void removeByC_C_C_C(
		long companyId, long classNameId, long classPK, int type) {

		for (CommerceAddress commerceAddress :
				findByC_C_C_C(
					companyId, classNameId, classPK, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceAddress);
		}
	}

	/**
	 * Returns the number of commerce addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the number of matching commerce addresses
	 */
	@Override
	public int countByC_C_C_C(
		long companyId, long classNameId, long classPK, int type) {

		FinderPath finderPath = _finderPathCountByC_C_C_C;

		Object[] finderArgs = new Object[] {
			companyId, classNameId, classPK, type
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_COMMERCEADDRESS_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_C_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_C_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_C_C_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(type);

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

	private static final String _FINDER_COLUMN_C_C_C_C_COMPANYID_2 =
		"commerceAddress.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_C_CLASSNAMEID_2 =
		"commerceAddress.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_C_CLASSPK_2 =
		"commerceAddress.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_C_TYPE_2 =
		"commerceAddress.type = ?";

	private FinderPath _finderPathFetchByC_ERC;
	private FinderPath _finderPathCountByC_ERC;

	/**
	 * Returns the commerce address where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchAddressException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = fetchByC_ERC(
			companyId, externalReferenceCode);

		if (commerceAddress == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", externalReferenceCode=");
			sb.append(externalReferenceCode);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchAddressException(sb.toString());
		}

		return commerceAddress;
	}

	/**
	 * Returns the commerce address where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return fetchByC_ERC(companyId, externalReferenceCode, true);
	}

	/**
	 * Returns the commerce address where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, externalReferenceCode};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_ERC, finderArgs);
		}

		if (result instanceof CommerceAddress) {
			CommerceAddress commerceAddress = (CommerceAddress)result;

			if ((companyId != commerceAddress.getCompanyId()) ||
				!Objects.equals(
					externalReferenceCode,
					commerceAddress.getExternalReferenceCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

			sb.append(_FINDER_COLUMN_C_ERC_COMPANYID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				List<CommerceAddress> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_ERC, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									companyId, externalReferenceCode
								};
							}

							_log.warn(
								"CommerceAddressPersistenceImpl.fetchByC_ERC(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CommerceAddress commerceAddress = list.get(0);

					result = commerceAddress;

					cacheResult(commerceAddress);
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
			return (CommerceAddress)result;
		}
	}

	/**
	 * Removes the commerce address where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce address that was removed
	 */
	@Override
	public CommerceAddress removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = findByC_ERC(
			companyId, externalReferenceCode);

		return remove(commerceAddress);
	}

	/**
	 * Returns the number of commerce addresses where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce addresses
	 */
	@Override
	public int countByC_ERC(long companyId, String externalReferenceCode) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		FinderPath finderPath = _finderPathCountByC_ERC;

		Object[] finderArgs = new Object[] {companyId, externalReferenceCode};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEADDRESS_WHERE);

			sb.append(_FINDER_COLUMN_C_ERC_COMPANYID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
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

	private static final String _FINDER_COLUMN_C_ERC_COMPANYID_2 =
		"commerceAddress.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2 =
		"commerceAddress.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3 =
		"(commerceAddress.externalReferenceCode IS NULL OR commerceAddress.externalReferenceCode = '')";

	public CommerceAddressPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceAddress.class);

		setModelImplClass(CommerceAddressImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceAddressTable.INSTANCE);
	}

	/**
	 * Caches the commerce address in the entity cache if it is enabled.
	 *
	 * @param commerceAddress the commerce address
	 */
	@Override
	public void cacheResult(CommerceAddress commerceAddress) {
		entityCache.putResult(
			CommerceAddressImpl.class, commerceAddress.getPrimaryKey(),
			commerceAddress);

		finderCache.putResult(
			_finderPathFetchByC_ERC,
			new Object[] {
				commerceAddress.getCompanyId(),
				commerceAddress.getExternalReferenceCode()
			},
			commerceAddress);
	}

	/**
	 * Caches the commerce addresses in the entity cache if it is enabled.
	 *
	 * @param commerceAddresses the commerce addresses
	 */
	@Override
	public void cacheResult(List<CommerceAddress> commerceAddresses) {
		for (CommerceAddress commerceAddress : commerceAddresses) {
			if (entityCache.getResult(
					CommerceAddressImpl.class,
					commerceAddress.getPrimaryKey()) == null) {

				cacheResult(commerceAddress);
			}
		}
	}

	/**
	 * Clears the cache for all commerce addresses.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceAddressImpl.class);

		finderCache.clearCache(CommerceAddressImpl.class);
	}

	/**
	 * Clears the cache for the commerce address.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceAddress commerceAddress) {
		entityCache.removeResult(CommerceAddressImpl.class, commerceAddress);
	}

	@Override
	public void clearCache(List<CommerceAddress> commerceAddresses) {
		for (CommerceAddress commerceAddress : commerceAddresses) {
			entityCache.removeResult(
				CommerceAddressImpl.class, commerceAddress);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceAddressImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CommerceAddressImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceAddressModelImpl commerceAddressModelImpl) {

		Object[] args = new Object[] {
			commerceAddressModelImpl.getCompanyId(),
			commerceAddressModelImpl.getExternalReferenceCode()
		};

		finderCache.putResult(_finderPathCountByC_ERC, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_ERC, args, commerceAddressModelImpl);
	}

	/**
	 * Creates a new commerce address with the primary key. Does not add the commerce address to the database.
	 *
	 * @param commerceAddressId the primary key for the new commerce address
	 * @return the new commerce address
	 */
	@Override
	public CommerceAddress create(long commerceAddressId) {
		CommerceAddress commerceAddress = new CommerceAddressImpl();

		commerceAddress.setNew(true);
		commerceAddress.setPrimaryKey(commerceAddressId);

		commerceAddress.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commerceAddress;
	}

	/**
	 * Removes the commerce address with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAddressId the primary key of the commerce address
	 * @return the commerce address that was removed
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress remove(long commerceAddressId)
		throws NoSuchAddressException {

		return remove((Serializable)commerceAddressId);
	}

	/**
	 * Removes the commerce address with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce address
	 * @return the commerce address that was removed
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress remove(Serializable primaryKey)
		throws NoSuchAddressException {

		Session session = null;

		try {
			session = openSession();

			CommerceAddress commerceAddress = (CommerceAddress)session.get(
				CommerceAddressImpl.class, primaryKey);

			if (commerceAddress == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAddressException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceAddress);
		}
		catch (NoSuchAddressException noSuchEntityException) {
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
	protected CommerceAddress removeImpl(CommerceAddress commerceAddress) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceAddress)) {
				commerceAddress = (CommerceAddress)session.get(
					CommerceAddressImpl.class,
					commerceAddress.getPrimaryKeyObj());
			}

			if (commerceAddress != null) {
				session.delete(commerceAddress);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceAddress != null) {
			clearCache(commerceAddress);
		}

		return commerceAddress;
	}

	@Override
	public CommerceAddress updateImpl(CommerceAddress commerceAddress) {
		boolean isNew = commerceAddress.isNew();

		if (!(commerceAddress instanceof CommerceAddressModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceAddress.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceAddress);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceAddress proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceAddress implementation " +
					commerceAddress.getClass());
		}

		CommerceAddressModelImpl commerceAddressModelImpl =
			(CommerceAddressModelImpl)commerceAddress;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceAddress.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceAddress.setCreateDate(now);
			}
			else {
				commerceAddress.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!commerceAddressModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceAddress.setModifiedDate(now);
			}
			else {
				commerceAddress.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceAddress);
			}
			else {
				commerceAddress = (CommerceAddress)session.merge(
					commerceAddress);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceAddressImpl.class, commerceAddressModelImpl, false, true);

		cacheUniqueFindersCache(commerceAddressModelImpl);

		if (isNew) {
			commerceAddress.setNew(false);
		}

		commerceAddress.resetOriginalValues();

		return commerceAddress;
	}

	/**
	 * Returns the commerce address with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce address
	 * @return the commerce address
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAddressException {

		CommerceAddress commerceAddress = fetchByPrimaryKey(primaryKey);

		if (commerceAddress == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAddressException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceAddress;
	}

	/**
	 * Returns the commerce address with the primary key or throws a <code>NoSuchAddressException</code> if it could not be found.
	 *
	 * @param commerceAddressId the primary key of the commerce address
	 * @return the commerce address
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress findByPrimaryKey(long commerceAddressId)
		throws NoSuchAddressException {

		return findByPrimaryKey((Serializable)commerceAddressId);
	}

	/**
	 * Returns the commerce address with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceAddressId the primary key of the commerce address
	 * @return the commerce address, or <code>null</code> if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress fetchByPrimaryKey(long commerceAddressId) {
		return fetchByPrimaryKey((Serializable)commerceAddressId);
	}

	/**
	 * Returns all the commerce addresses.
	 *
	 * @return the commerce addresses
	 */
	@Override
	public List<CommerceAddress> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce addresses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @return the range of commerce addresses
	 */
	@Override
	public List<CommerceAddress> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce addresses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce addresses
	 */
	@Override
	public List<CommerceAddress> findAll(
		int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce addresses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce addresses
	 */
	@Override
	public List<CommerceAddress> findAll(
		int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator,
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

		List<CommerceAddress> list = null;

		if (useFinderCache) {
			list = (List<CommerceAddress>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEADDRESS);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEADDRESS;

				sql = sql.concat(CommerceAddressModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceAddress>)QueryUtil.list(
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
	 * Removes all the commerce addresses from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceAddress commerceAddress : findAll()) {
			remove(commerceAddress);
		}
	}

	/**
	 * Returns the number of commerce addresses.
	 *
	 * @return the number of commerce addresses
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_COMMERCEADDRESS);

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
		return "commerceAddressId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEADDRESS;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceAddressModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce address persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceAddressPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new CommerceAddressModelArgumentsResolver(),
			new HashMapDictionary<>());

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByCommerceRegionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceRegionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceRegionId"}, true);

		_finderPathWithoutPaginationFindByCommerceRegionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCommerceRegionId",
			new String[] {Long.class.getName()},
			new String[] {"commerceRegionId"}, true);

		_finderPathCountByCommerceRegionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceRegionId", new String[] {Long.class.getName()},
			new String[] {"commerceRegionId"}, false);

		_finderPathWithPaginationFindByCommerceCountryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceCountryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceCountryId"}, true);

		_finderPathWithoutPaginationFindByCommerceCountryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceCountryId", new String[] {Long.class.getName()},
			new String[] {"commerceCountryId"}, true);

		_finderPathCountByCommerceCountryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceCountryId", new String[] {Long.class.getName()},
			new String[] {"commerceCountryId"}, false);

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, false);

		_finderPathWithPaginationFindByG_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK"}, true);

		_finderPathWithoutPaginationFindByG_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK"}, true);

		_finderPathCountByG_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK"}, false);

		_finderPathWithPaginationFindByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "classNameId", "classPK"}, true);

		_finderPathWithoutPaginationFindByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"companyId", "classNameId", "classPK"}, true);

		_finderPathCountByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"companyId", "classNameId", "classPK"}, false);

		_finderPathWithPaginationFindByG_C_C_DB = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_DB",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "classNameId", "classPK", "defaultBilling"
			},
			true);

		_finderPathWithoutPaginationFindByG_C_C_DB = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C_DB",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			},
			new String[] {
				"groupId", "classNameId", "classPK", "defaultBilling"
			},
			true);

		_finderPathCountByG_C_C_DB = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_DB",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			},
			new String[] {
				"groupId", "classNameId", "classPK", "defaultBilling"
			},
			false);

		_finderPathWithPaginationFindByG_C_C_DS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_DS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "classNameId", "classPK", "defaultShipping"
			},
			true);

		_finderPathWithoutPaginationFindByG_C_C_DS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C_DS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			},
			new String[] {
				"groupId", "classNameId", "classPK", "defaultShipping"
			},
			true);

		_finderPathCountByG_C_C_DS = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_DS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			},
			new String[] {
				"groupId", "classNameId", "classPK", "defaultShipping"
			},
			false);

		_finderPathWithPaginationFindByC_C_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "classNameId", "classPK", "type_"},
			true);

		_finderPathWithoutPaginationFindByC_C_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			new String[] {"companyId", "classNameId", "classPK", "type_"},
			true);

		_finderPathCountByC_C_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			new String[] {"companyId", "classNameId", "classPK", "type_"},
			false);

		_finderPathFetchByC_ERC = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "externalReferenceCode"}, true);

		_finderPathCountByC_ERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "externalReferenceCode"}, false);
	}

	public void destroy() {
		entityCache.removeCache(CommerceAddressImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEADDRESS =
		"SELECT commerceAddress FROM CommerceAddress commerceAddress";

	private static final String _SQL_SELECT_COMMERCEADDRESS_WHERE =
		"SELECT commerceAddress FROM CommerceAddress commerceAddress WHERE ";

	private static final String _SQL_COUNT_COMMERCEADDRESS =
		"SELECT COUNT(commerceAddress) FROM CommerceAddress commerceAddress";

	private static final String _SQL_COUNT_COMMERCEADDRESS_WHERE =
		"SELECT COUNT(commerceAddress) FROM CommerceAddress commerceAddress WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceAddress.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceAddress exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceAddress exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAddressPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class CommerceAddressModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			CommerceAddressModelImpl commerceAddressModelImpl =
				(CommerceAddressModelImpl)baseModel;

			long columnBitmask = commerceAddressModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					commerceAddressModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						commerceAddressModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					commerceAddressModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return CommerceAddressImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return CommerceAddressTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			CommerceAddressModelImpl commerceAddressModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						commerceAddressModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = commerceAddressModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}