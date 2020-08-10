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

import com.liferay.commerce.discount.exception.NoSuchDiscountUsageEntryException;
import com.liferay.commerce.discount.model.CommerceDiscountUsageEntry;
import com.liferay.commerce.discount.model.impl.CommerceDiscountUsageEntryImpl;
import com.liferay.commerce.discount.model.impl.CommerceDiscountUsageEntryModelImpl;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountUsageEntryPersistence;
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
 * The persistence implementation for the commerce discount usage entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceDiscountUsageEntryPersistenceImpl
	extends BasePersistenceImpl<CommerceDiscountUsageEntry>
	implements CommerceDiscountUsageEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceDiscountUsageEntryUtil</code> to access the commerce discount usage entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceDiscountUsageEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCommerceDiscountId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceDiscountId;
	private FinderPath _finderPathCountByCommerceDiscountId;

	/**
	 * Returns all the commerce discount usage entries where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByCommerceDiscountId(
		long commerceDiscountId) {

		return findByCommerceDiscountId(
			commerceDiscountId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount usage entries where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @return the range of matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end) {

		return findByCommerceDiscountId(commerceDiscountId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return findByCommerceDiscountId(
			commerceDiscountId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceDiscountId;
				finderArgs = new Object[] {commerceDiscountId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceDiscountId;
			finderArgs = new Object[] {
				commerceDiscountId, start, end, orderByComparator
			};
		}

		List<CommerceDiscountUsageEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscountUsageEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDiscountUsageEntry commerceDiscountUsageEntry :
						list) {

					if (commerceDiscountId !=
							commerceDiscountUsageEntry.
								getCommerceDiscountId()) {

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

			sb.append(_SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEDISCOUNTID_COMMERCEDISCOUNTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceDiscountUsageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceDiscountId);

				list = (List<CommerceDiscountUsageEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce discount usage entry in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry findByCommerceDiscountId_First(
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			fetchByCommerceDiscountId_First(
				commerceDiscountId, orderByComparator);

		if (commerceDiscountUsageEntry != null) {
			return commerceDiscountUsageEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceDiscountId=");
		sb.append(commerceDiscountId);

		sb.append("}");

		throw new NoSuchDiscountUsageEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce discount usage entry in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry fetchByCommerceDiscountId_First(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		List<CommerceDiscountUsageEntry> list = findByCommerceDiscountId(
			commerceDiscountId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce discount usage entry in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry findByCommerceDiscountId_Last(
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			fetchByCommerceDiscountId_Last(
				commerceDiscountId, orderByComparator);

		if (commerceDiscountUsageEntry != null) {
			return commerceDiscountUsageEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceDiscountId=");
		sb.append(commerceDiscountId);

		sb.append("}");

		throw new NoSuchDiscountUsageEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce discount usage entry in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry fetchByCommerceDiscountId_Last(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		int count = countByCommerceDiscountId(commerceDiscountId);

		if (count == 0) {
			return null;
		}

		List<CommerceDiscountUsageEntry> list = findByCommerceDiscountId(
			commerceDiscountId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce discount usage entries before and after the current commerce discount usage entry in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountUsageEntryId the primary key of the current commerce discount usage entry
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry[] findByCommerceDiscountId_PrevAndNext(
			long commerceDiscountUsageEntryId, long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			findByPrimaryKey(commerceDiscountUsageEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountUsageEntry[] array =
				new CommerceDiscountUsageEntryImpl[3];

			array[0] = getByCommerceDiscountId_PrevAndNext(
				session, commerceDiscountUsageEntry, commerceDiscountId,
				orderByComparator, true);

			array[1] = commerceDiscountUsageEntry;

			array[2] = getByCommerceDiscountId_PrevAndNext(
				session, commerceDiscountUsageEntry, commerceDiscountId,
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

	protected CommerceDiscountUsageEntry getByCommerceDiscountId_PrevAndNext(
		Session session, CommerceDiscountUsageEntry commerceDiscountUsageEntry,
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCEDISCOUNTID_COMMERCEDISCOUNTID_2);

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
			sb.append(CommerceDiscountUsageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceDiscountId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceDiscountUsageEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceDiscountUsageEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce discount usage entries where commerceDiscountId = &#63; from the database.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 */
	@Override
	public void removeByCommerceDiscountId(long commerceDiscountId) {
		for (CommerceDiscountUsageEntry commerceDiscountUsageEntry :
				findByCommerceDiscountId(
					commerceDiscountId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceDiscountUsageEntry);
		}
	}

	/**
	 * Returns the number of commerce discount usage entries where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the number of matching commerce discount usage entries
	 */
	@Override
	public int countByCommerceDiscountId(long commerceDiscountId) {
		FinderPath finderPath = _finderPathCountByCommerceDiscountId;

		Object[] finderArgs = new Object[] {commerceDiscountId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNTUSAGEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEDISCOUNTID_COMMERCEDISCOUNTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceDiscountId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_COMMERCEDISCOUNTID_COMMERCEDISCOUNTID_2 =
			"commerceDiscountUsageEntry.commerceDiscountId = ?";

	private FinderPath _finderPathWithPaginationFindByA_D;
	private FinderPath _finderPathWithoutPaginationFindByA_D;
	private FinderPath _finderPathCountByA_D;

	/**
	 * Returns all the commerce discount usage entries where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByA_D(
		long commerceAccountId, long commerceDiscountId) {

		return findByA_D(
			commerceAccountId, commerceDiscountId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount usage entries where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @return the range of matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByA_D(
		long commerceAccountId, long commerceDiscountId, int start, int end) {

		return findByA_D(
			commerceAccountId, commerceDiscountId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByA_D(
		long commerceAccountId, long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return findByA_D(
			commerceAccountId, commerceDiscountId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByA_D(
		long commerceAccountId, long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByA_D;
				finderArgs = new Object[] {
					commerceAccountId, commerceDiscountId
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByA_D;
			finderArgs = new Object[] {
				commerceAccountId, commerceDiscountId, start, end,
				orderByComparator
			};
		}

		List<CommerceDiscountUsageEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscountUsageEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDiscountUsageEntry commerceDiscountUsageEntry :
						list) {

					if ((commerceAccountId !=
							commerceDiscountUsageEntry.
								getCommerceAccountId()) ||
						(commerceDiscountId !=
							commerceDiscountUsageEntry.
								getCommerceDiscountId())) {

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

			sb.append(_SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_A_D_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_A_D_COMMERCEDISCOUNTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceDiscountUsageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceAccountId);

				queryPos.add(commerceDiscountId);

				list = (List<CommerceDiscountUsageEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce discount usage entry in the ordered set where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry findByA_D_First(
			long commerceAccountId, long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			fetchByA_D_First(
				commerceAccountId, commerceDiscountId, orderByComparator);

		if (commerceDiscountUsageEntry != null) {
			return commerceDiscountUsageEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceAccountId=");
		sb.append(commerceAccountId);

		sb.append(", commerceDiscountId=");
		sb.append(commerceDiscountId);

		sb.append("}");

		throw new NoSuchDiscountUsageEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce discount usage entry in the ordered set where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry fetchByA_D_First(
		long commerceAccountId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		List<CommerceDiscountUsageEntry> list = findByA_D(
			commerceAccountId, commerceDiscountId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce discount usage entry in the ordered set where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry findByA_D_Last(
			long commerceAccountId, long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry = fetchByA_D_Last(
			commerceAccountId, commerceDiscountId, orderByComparator);

		if (commerceDiscountUsageEntry != null) {
			return commerceDiscountUsageEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceAccountId=");
		sb.append(commerceAccountId);

		sb.append(", commerceDiscountId=");
		sb.append(commerceDiscountId);

		sb.append("}");

		throw new NoSuchDiscountUsageEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce discount usage entry in the ordered set where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry fetchByA_D_Last(
		long commerceAccountId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		int count = countByA_D(commerceAccountId, commerceDiscountId);

		if (count == 0) {
			return null;
		}

		List<CommerceDiscountUsageEntry> list = findByA_D(
			commerceAccountId, commerceDiscountId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce discount usage entries before and after the current commerce discount usage entry in the ordered set where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountUsageEntryId the primary key of the current commerce discount usage entry
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry[] findByA_D_PrevAndNext(
			long commerceDiscountUsageEntryId, long commerceAccountId,
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			findByPrimaryKey(commerceDiscountUsageEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountUsageEntry[] array =
				new CommerceDiscountUsageEntryImpl[3];

			array[0] = getByA_D_PrevAndNext(
				session, commerceDiscountUsageEntry, commerceAccountId,
				commerceDiscountId, orderByComparator, true);

			array[1] = commerceDiscountUsageEntry;

			array[2] = getByA_D_PrevAndNext(
				session, commerceDiscountUsageEntry, commerceAccountId,
				commerceDiscountId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceDiscountUsageEntry getByA_D_PrevAndNext(
		Session session, CommerceDiscountUsageEntry commerceDiscountUsageEntry,
		long commerceAccountId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_A_D_COMMERCEACCOUNTID_2);

		sb.append(_FINDER_COLUMN_A_D_COMMERCEDISCOUNTID_2);

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
			sb.append(CommerceDiscountUsageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceAccountId);

		queryPos.add(commerceDiscountId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceDiscountUsageEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceDiscountUsageEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce discount usage entries where commerceAccountId = &#63; and commerceDiscountId = &#63; from the database.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 */
	@Override
	public void removeByA_D(long commerceAccountId, long commerceDiscountId) {
		for (CommerceDiscountUsageEntry commerceDiscountUsageEntry :
				findByA_D(
					commerceAccountId, commerceDiscountId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceDiscountUsageEntry);
		}
	}

	/**
	 * Returns the number of commerce discount usage entries where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the number of matching commerce discount usage entries
	 */
	@Override
	public int countByA_D(long commerceAccountId, long commerceDiscountId) {
		FinderPath finderPath = _finderPathCountByA_D;

		Object[] finderArgs = new Object[] {
			commerceAccountId, commerceDiscountId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNTUSAGEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_A_D_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_A_D_COMMERCEDISCOUNTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceAccountId);

				queryPos.add(commerceDiscountId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_A_D_COMMERCEACCOUNTID_2 =
		"commerceDiscountUsageEntry.commerceAccountId = ? AND ";

	private static final String _FINDER_COLUMN_A_D_COMMERCEDISCOUNTID_2 =
		"commerceDiscountUsageEntry.commerceDiscountId = ?";

	private FinderPath _finderPathWithPaginationFindByO_D;
	private FinderPath _finderPathWithoutPaginationFindByO_D;
	private FinderPath _finderPathCountByO_D;

	/**
	 * Returns all the commerce discount usage entries where commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByO_D(
		long commerceOrderId, long commerceDiscountId) {

		return findByO_D(
			commerceOrderId, commerceDiscountId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount usage entries where commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @return the range of matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByO_D(
		long commerceOrderId, long commerceDiscountId, int start, int end) {

		return findByO_D(commerceOrderId, commerceDiscountId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries where commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByO_D(
		long commerceOrderId, long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return findByO_D(
			commerceOrderId, commerceDiscountId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries where commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByO_D(
		long commerceOrderId, long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByO_D;
				finderArgs = new Object[] {commerceOrderId, commerceDiscountId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByO_D;
			finderArgs = new Object[] {
				commerceOrderId, commerceDiscountId, start, end,
				orderByComparator
			};
		}

		List<CommerceDiscountUsageEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscountUsageEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDiscountUsageEntry commerceDiscountUsageEntry :
						list) {

					if ((commerceOrderId !=
							commerceDiscountUsageEntry.getCommerceOrderId()) ||
						(commerceDiscountId !=
							commerceDiscountUsageEntry.
								getCommerceDiscountId())) {

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

			sb.append(_SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_O_D_COMMERCEORDERID_2);

			sb.append(_FINDER_COLUMN_O_D_COMMERCEDISCOUNTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceDiscountUsageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderId);

				queryPos.add(commerceDiscountId);

				list = (List<CommerceDiscountUsageEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce discount usage entry in the ordered set where commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry findByO_D_First(
			long commerceOrderId, long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			fetchByO_D_First(
				commerceOrderId, commerceDiscountId, orderByComparator);

		if (commerceDiscountUsageEntry != null) {
			return commerceDiscountUsageEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceOrderId=");
		sb.append(commerceOrderId);

		sb.append(", commerceDiscountId=");
		sb.append(commerceDiscountId);

		sb.append("}");

		throw new NoSuchDiscountUsageEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce discount usage entry in the ordered set where commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry fetchByO_D_First(
		long commerceOrderId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		List<CommerceDiscountUsageEntry> list = findByO_D(
			commerceOrderId, commerceDiscountId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce discount usage entry in the ordered set where commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry findByO_D_Last(
			long commerceOrderId, long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry = fetchByO_D_Last(
			commerceOrderId, commerceDiscountId, orderByComparator);

		if (commerceDiscountUsageEntry != null) {
			return commerceDiscountUsageEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceOrderId=");
		sb.append(commerceOrderId);

		sb.append(", commerceDiscountId=");
		sb.append(commerceDiscountId);

		sb.append("}");

		throw new NoSuchDiscountUsageEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce discount usage entry in the ordered set where commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry fetchByO_D_Last(
		long commerceOrderId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		int count = countByO_D(commerceOrderId, commerceDiscountId);

		if (count == 0) {
			return null;
		}

		List<CommerceDiscountUsageEntry> list = findByO_D(
			commerceOrderId, commerceDiscountId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce discount usage entries before and after the current commerce discount usage entry in the ordered set where commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountUsageEntryId the primary key of the current commerce discount usage entry
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry[] findByO_D_PrevAndNext(
			long commerceDiscountUsageEntryId, long commerceOrderId,
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			findByPrimaryKey(commerceDiscountUsageEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountUsageEntry[] array =
				new CommerceDiscountUsageEntryImpl[3];

			array[0] = getByO_D_PrevAndNext(
				session, commerceDiscountUsageEntry, commerceOrderId,
				commerceDiscountId, orderByComparator, true);

			array[1] = commerceDiscountUsageEntry;

			array[2] = getByO_D_PrevAndNext(
				session, commerceDiscountUsageEntry, commerceOrderId,
				commerceDiscountId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceDiscountUsageEntry getByO_D_PrevAndNext(
		Session session, CommerceDiscountUsageEntry commerceDiscountUsageEntry,
		long commerceOrderId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_O_D_COMMERCEORDERID_2);

		sb.append(_FINDER_COLUMN_O_D_COMMERCEDISCOUNTID_2);

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
			sb.append(CommerceDiscountUsageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceOrderId);

		queryPos.add(commerceDiscountId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceDiscountUsageEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceDiscountUsageEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce discount usage entries where commerceOrderId = &#63; and commerceDiscountId = &#63; from the database.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 */
	@Override
	public void removeByO_D(long commerceOrderId, long commerceDiscountId) {
		for (CommerceDiscountUsageEntry commerceDiscountUsageEntry :
				findByO_D(
					commerceOrderId, commerceDiscountId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceDiscountUsageEntry);
		}
	}

	/**
	 * Returns the number of commerce discount usage entries where commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the number of matching commerce discount usage entries
	 */
	@Override
	public int countByO_D(long commerceOrderId, long commerceDiscountId) {
		FinderPath finderPath = _finderPathCountByO_D;

		Object[] finderArgs = new Object[] {
			commerceOrderId, commerceDiscountId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNTUSAGEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_O_D_COMMERCEORDERID_2);

			sb.append(_FINDER_COLUMN_O_D_COMMERCEDISCOUNTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderId);

				queryPos.add(commerceDiscountId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_O_D_COMMERCEORDERID_2 =
		"commerceDiscountUsageEntry.commerceOrderId = ? AND ";

	private static final String _FINDER_COLUMN_O_D_COMMERCEDISCOUNTID_2 =
		"commerceDiscountUsageEntry.commerceDiscountId = ?";

	private FinderPath _finderPathWithPaginationFindByA_O_D;
	private FinderPath _finderPathWithoutPaginationFindByA_O_D;
	private FinderPath _finderPathCountByA_O_D;

	/**
	 * Returns all the commerce discount usage entries where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByA_O_D(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId) {

		return findByA_O_D(
			commerceAccountId, commerceOrderId, commerceDiscountId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount usage entries where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @return the range of matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByA_O_D(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId,
		int start, int end) {

		return findByA_O_D(
			commerceAccountId, commerceOrderId, commerceDiscountId, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByA_O_D(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId,
		int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return findByA_O_D(
			commerceAccountId, commerceOrderId, commerceDiscountId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByA_O_D(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId,
		int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByA_O_D;
				finderArgs = new Object[] {
					commerceAccountId, commerceOrderId, commerceDiscountId
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByA_O_D;
			finderArgs = new Object[] {
				commerceAccountId, commerceOrderId, commerceDiscountId, start,
				end, orderByComparator
			};
		}

		List<CommerceDiscountUsageEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscountUsageEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDiscountUsageEntry commerceDiscountUsageEntry :
						list) {

					if ((commerceAccountId !=
							commerceDiscountUsageEntry.
								getCommerceAccountId()) ||
						(commerceOrderId !=
							commerceDiscountUsageEntry.getCommerceOrderId()) ||
						(commerceDiscountId !=
							commerceDiscountUsageEntry.
								getCommerceDiscountId())) {

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

			sb.append(_SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_A_O_D_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_A_O_D_COMMERCEORDERID_2);

			sb.append(_FINDER_COLUMN_A_O_D_COMMERCEDISCOUNTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceDiscountUsageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceAccountId);

				queryPos.add(commerceOrderId);

				queryPos.add(commerceDiscountId);

				list = (List<CommerceDiscountUsageEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce discount usage entry in the ordered set where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry findByA_O_D_First(
			long commerceAccountId, long commerceOrderId,
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			fetchByA_O_D_First(
				commerceAccountId, commerceOrderId, commerceDiscountId,
				orderByComparator);

		if (commerceDiscountUsageEntry != null) {
			return commerceDiscountUsageEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceAccountId=");
		sb.append(commerceAccountId);

		sb.append(", commerceOrderId=");
		sb.append(commerceOrderId);

		sb.append(", commerceDiscountId=");
		sb.append(commerceDiscountId);

		sb.append("}");

		throw new NoSuchDiscountUsageEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce discount usage entry in the ordered set where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry fetchByA_O_D_First(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		List<CommerceDiscountUsageEntry> list = findByA_O_D(
			commerceAccountId, commerceOrderId, commerceDiscountId, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce discount usage entry in the ordered set where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry findByA_O_D_Last(
			long commerceAccountId, long commerceOrderId,
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			fetchByA_O_D_Last(
				commerceAccountId, commerceOrderId, commerceDiscountId,
				orderByComparator);

		if (commerceDiscountUsageEntry != null) {
			return commerceDiscountUsageEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceAccountId=");
		sb.append(commerceAccountId);

		sb.append(", commerceOrderId=");
		sb.append(commerceOrderId);

		sb.append(", commerceDiscountId=");
		sb.append(commerceDiscountId);

		sb.append("}");

		throw new NoSuchDiscountUsageEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce discount usage entry in the ordered set where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry fetchByA_O_D_Last(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		int count = countByA_O_D(
			commerceAccountId, commerceOrderId, commerceDiscountId);

		if (count == 0) {
			return null;
		}

		List<CommerceDiscountUsageEntry> list = findByA_O_D(
			commerceAccountId, commerceOrderId, commerceDiscountId, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce discount usage entries before and after the current commerce discount usage entry in the ordered set where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountUsageEntryId the primary key of the current commerce discount usage entry
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry[] findByA_O_D_PrevAndNext(
			long commerceDiscountUsageEntryId, long commerceAccountId,
			long commerceOrderId, long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			findByPrimaryKey(commerceDiscountUsageEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountUsageEntry[] array =
				new CommerceDiscountUsageEntryImpl[3];

			array[0] = getByA_O_D_PrevAndNext(
				session, commerceDiscountUsageEntry, commerceAccountId,
				commerceOrderId, commerceDiscountId, orderByComparator, true);

			array[1] = commerceDiscountUsageEntry;

			array[2] = getByA_O_D_PrevAndNext(
				session, commerceDiscountUsageEntry, commerceAccountId,
				commerceOrderId, commerceDiscountId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceDiscountUsageEntry getByA_O_D_PrevAndNext(
		Session session, CommerceDiscountUsageEntry commerceDiscountUsageEntry,
		long commerceAccountId, long commerceOrderId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_A_O_D_COMMERCEACCOUNTID_2);

		sb.append(_FINDER_COLUMN_A_O_D_COMMERCEORDERID_2);

		sb.append(_FINDER_COLUMN_A_O_D_COMMERCEDISCOUNTID_2);

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
			sb.append(CommerceDiscountUsageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceAccountId);

		queryPos.add(commerceOrderId);

		queryPos.add(commerceDiscountId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceDiscountUsageEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceDiscountUsageEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce discount usage entries where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63; from the database.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 */
	@Override
	public void removeByA_O_D(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId) {

		for (CommerceDiscountUsageEntry commerceDiscountUsageEntry :
				findByA_O_D(
					commerceAccountId, commerceOrderId, commerceDiscountId,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceDiscountUsageEntry);
		}
	}

	/**
	 * Returns the number of commerce discount usage entries where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the number of matching commerce discount usage entries
	 */
	@Override
	public int countByA_O_D(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId) {

		FinderPath finderPath = _finderPathCountByA_O_D;

		Object[] finderArgs = new Object[] {
			commerceAccountId, commerceOrderId, commerceDiscountId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNTUSAGEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_A_O_D_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_A_O_D_COMMERCEORDERID_2);

			sb.append(_FINDER_COLUMN_A_O_D_COMMERCEDISCOUNTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceAccountId);

				queryPos.add(commerceOrderId);

				queryPos.add(commerceDiscountId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_A_O_D_COMMERCEACCOUNTID_2 =
		"commerceDiscountUsageEntry.commerceAccountId = ? AND ";

	private static final String _FINDER_COLUMN_A_O_D_COMMERCEORDERID_2 =
		"commerceDiscountUsageEntry.commerceOrderId = ? AND ";

	private static final String _FINDER_COLUMN_A_O_D_COMMERCEDISCOUNTID_2 =
		"commerceDiscountUsageEntry.commerceDiscountId = ?";

	public CommerceDiscountUsageEntryPersistenceImpl() {
		setModelClass(CommerceDiscountUsageEntry.class);
	}

	/**
	 * Caches the commerce discount usage entry in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountUsageEntry the commerce discount usage entry
	 */
	@Override
	public void cacheResult(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {

		entityCache.putResult(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			commerceDiscountUsageEntry.getPrimaryKey(),
			commerceDiscountUsageEntry);

		commerceDiscountUsageEntry.resetOriginalValues();
	}

	/**
	 * Caches the commerce discount usage entries in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountUsageEntries the commerce discount usage entries
	 */
	@Override
	public void cacheResult(
		List<CommerceDiscountUsageEntry> commerceDiscountUsageEntries) {

		for (CommerceDiscountUsageEntry commerceDiscountUsageEntry :
				commerceDiscountUsageEntries) {

			if (entityCache.getResult(
					CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceDiscountUsageEntryImpl.class,
					commerceDiscountUsageEntry.getPrimaryKey()) == null) {

				cacheResult(commerceDiscountUsageEntry);
			}
			else {
				commerceDiscountUsageEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce discount usage entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceDiscountUsageEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce discount usage entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {

		entityCache.removeResult(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			commerceDiscountUsageEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<CommerceDiscountUsageEntry> commerceDiscountUsageEntries) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceDiscountUsageEntry commerceDiscountUsageEntry :
				commerceDiscountUsageEntries) {

			entityCache.removeResult(
				CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceDiscountUsageEntryImpl.class,
				commerceDiscountUsageEntry.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceDiscountUsageEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new commerce discount usage entry with the primary key. Does not add the commerce discount usage entry to the database.
	 *
	 * @param commerceDiscountUsageEntryId the primary key for the new commerce discount usage entry
	 * @return the new commerce discount usage entry
	 */
	@Override
	public CommerceDiscountUsageEntry create(
		long commerceDiscountUsageEntryId) {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			new CommerceDiscountUsageEntryImpl();

		commerceDiscountUsageEntry.setNew(true);
		commerceDiscountUsageEntry.setPrimaryKey(commerceDiscountUsageEntryId);

		commerceDiscountUsageEntry.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceDiscountUsageEntry;
	}

	/**
	 * Removes the commerce discount usage entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDiscountUsageEntryId the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry that was removed
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry remove(long commerceDiscountUsageEntryId)
		throws NoSuchDiscountUsageEntryException {

		return remove((Serializable)commerceDiscountUsageEntryId);
	}

	/**
	 * Removes the commerce discount usage entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry that was removed
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry remove(Serializable primaryKey)
		throws NoSuchDiscountUsageEntryException {

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountUsageEntry commerceDiscountUsageEntry =
				(CommerceDiscountUsageEntry)session.get(
					CommerceDiscountUsageEntryImpl.class, primaryKey);

			if (commerceDiscountUsageEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDiscountUsageEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceDiscountUsageEntry);
		}
		catch (NoSuchDiscountUsageEntryException noSuchEntityException) {
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
	protected CommerceDiscountUsageEntry removeImpl(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceDiscountUsageEntry)) {
				commerceDiscountUsageEntry =
					(CommerceDiscountUsageEntry)session.get(
						CommerceDiscountUsageEntryImpl.class,
						commerceDiscountUsageEntry.getPrimaryKeyObj());
			}

			if (commerceDiscountUsageEntry != null) {
				session.delete(commerceDiscountUsageEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceDiscountUsageEntry != null) {
			clearCache(commerceDiscountUsageEntry);
		}

		return commerceDiscountUsageEntry;
	}

	@Override
	public CommerceDiscountUsageEntry updateImpl(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {

		boolean isNew = commerceDiscountUsageEntry.isNew();

		if (!(commerceDiscountUsageEntry instanceof
				CommerceDiscountUsageEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceDiscountUsageEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceDiscountUsageEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceDiscountUsageEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceDiscountUsageEntry implementation " +
					commerceDiscountUsageEntry.getClass());
		}

		CommerceDiscountUsageEntryModelImpl
			commerceDiscountUsageEntryModelImpl =
				(CommerceDiscountUsageEntryModelImpl)commerceDiscountUsageEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceDiscountUsageEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceDiscountUsageEntry.setCreateDate(now);
			}
			else {
				commerceDiscountUsageEntry.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!commerceDiscountUsageEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceDiscountUsageEntry.setModifiedDate(now);
			}
			else {
				commerceDiscountUsageEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceDiscountUsageEntry.isNew()) {
				session.save(commerceDiscountUsageEntry);

				commerceDiscountUsageEntry.setNew(false);
			}
			else {
				commerceDiscountUsageEntry =
					(CommerceDiscountUsageEntry)session.merge(
						commerceDiscountUsageEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceDiscountUsageEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				commerceDiscountUsageEntryModelImpl.getCommerceDiscountId()
			};

			finderCache.removeResult(
				_finderPathCountByCommerceDiscountId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCommerceDiscountId, args);

			args = new Object[] {
				commerceDiscountUsageEntryModelImpl.getCommerceAccountId(),
				commerceDiscountUsageEntryModelImpl.getCommerceDiscountId()
			};

			finderCache.removeResult(_finderPathCountByA_D, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByA_D, args);

			args = new Object[] {
				commerceDiscountUsageEntryModelImpl.getCommerceOrderId(),
				commerceDiscountUsageEntryModelImpl.getCommerceDiscountId()
			};

			finderCache.removeResult(_finderPathCountByO_D, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByO_D, args);

			args = new Object[] {
				commerceDiscountUsageEntryModelImpl.getCommerceAccountId(),
				commerceDiscountUsageEntryModelImpl.getCommerceOrderId(),
				commerceDiscountUsageEntryModelImpl.getCommerceDiscountId()
			};

			finderCache.removeResult(_finderPathCountByA_O_D, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByA_O_D, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((commerceDiscountUsageEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCommerceDiscountId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					commerceDiscountUsageEntryModelImpl.
						getOriginalCommerceDiscountId()
				};

				finderCache.removeResult(
					_finderPathCountByCommerceDiscountId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCommerceDiscountId, args);

				args = new Object[] {
					commerceDiscountUsageEntryModelImpl.getCommerceDiscountId()
				};

				finderCache.removeResult(
					_finderPathCountByCommerceDiscountId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCommerceDiscountId, args);
			}

			if ((commerceDiscountUsageEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByA_D.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					commerceDiscountUsageEntryModelImpl.
						getOriginalCommerceAccountId(),
					commerceDiscountUsageEntryModelImpl.
						getOriginalCommerceDiscountId()
				};

				finderCache.removeResult(_finderPathCountByA_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByA_D, args);

				args = new Object[] {
					commerceDiscountUsageEntryModelImpl.getCommerceAccountId(),
					commerceDiscountUsageEntryModelImpl.getCommerceDiscountId()
				};

				finderCache.removeResult(_finderPathCountByA_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByA_D, args);
			}

			if ((commerceDiscountUsageEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByO_D.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					commerceDiscountUsageEntryModelImpl.
						getOriginalCommerceOrderId(),
					commerceDiscountUsageEntryModelImpl.
						getOriginalCommerceDiscountId()
				};

				finderCache.removeResult(_finderPathCountByO_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByO_D, args);

				args = new Object[] {
					commerceDiscountUsageEntryModelImpl.getCommerceOrderId(),
					commerceDiscountUsageEntryModelImpl.getCommerceDiscountId()
				};

				finderCache.removeResult(_finderPathCountByO_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByO_D, args);
			}

			if ((commerceDiscountUsageEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByA_O_D.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					commerceDiscountUsageEntryModelImpl.
						getOriginalCommerceAccountId(),
					commerceDiscountUsageEntryModelImpl.
						getOriginalCommerceOrderId(),
					commerceDiscountUsageEntryModelImpl.
						getOriginalCommerceDiscountId()
				};

				finderCache.removeResult(_finderPathCountByA_O_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByA_O_D, args);

				args = new Object[] {
					commerceDiscountUsageEntryModelImpl.getCommerceAccountId(),
					commerceDiscountUsageEntryModelImpl.getCommerceOrderId(),
					commerceDiscountUsageEntryModelImpl.getCommerceDiscountId()
				};

				finderCache.removeResult(_finderPathCountByA_O_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByA_O_D, args);
			}
		}

		entityCache.putResult(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			commerceDiscountUsageEntry.getPrimaryKey(),
			commerceDiscountUsageEntry, false);

		commerceDiscountUsageEntry.resetOriginalValues();

		return commerceDiscountUsageEntry;
	}

	/**
	 * Returns the commerce discount usage entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDiscountUsageEntryException {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			fetchByPrimaryKey(primaryKey);

		if (commerceDiscountUsageEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDiscountUsageEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceDiscountUsageEntry;
	}

	/**
	 * Returns the commerce discount usage entry with the primary key or throws a <code>NoSuchDiscountUsageEntryException</code> if it could not be found.
	 *
	 * @param commerceDiscountUsageEntryId the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry findByPrimaryKey(
			long commerceDiscountUsageEntryId)
		throws NoSuchDiscountUsageEntryException {

		return findByPrimaryKey((Serializable)commerceDiscountUsageEntryId);
	}

	/**
	 * Returns the commerce discount usage entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry, or <code>null</code> if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry fetchByPrimaryKey(
		Serializable primaryKey) {

		Serializable serializable = entityCache.getResult(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			(CommerceDiscountUsageEntry)serializable;

		if (commerceDiscountUsageEntry == null) {
			Session session = null;

			try {
				session = openSession();

				commerceDiscountUsageEntry =
					(CommerceDiscountUsageEntry)session.get(
						CommerceDiscountUsageEntryImpl.class, primaryKey);

				if (commerceDiscountUsageEntry != null) {
					cacheResult(commerceDiscountUsageEntry);
				}
				else {
					entityCache.putResult(
						CommerceDiscountUsageEntryModelImpl.
							ENTITY_CACHE_ENABLED,
						CommerceDiscountUsageEntryImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceDiscountUsageEntryImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceDiscountUsageEntry;
	}

	/**
	 * Returns the commerce discount usage entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDiscountUsageEntryId the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry, or <code>null</code> if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry fetchByPrimaryKey(
		long commerceDiscountUsageEntryId) {

		return fetchByPrimaryKey((Serializable)commerceDiscountUsageEntryId);
	}

	@Override
	public Map<Serializable, CommerceDiscountUsageEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceDiscountUsageEntry> map =
			new HashMap<Serializable, CommerceDiscountUsageEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceDiscountUsageEntry commerceDiscountUsageEntry =
				fetchByPrimaryKey(primaryKey);

			if (commerceDiscountUsageEntry != null) {
				map.put(primaryKey, commerceDiscountUsageEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(
				CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceDiscountUsageEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(
						primaryKey, (CommerceDiscountUsageEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			uncachedPrimaryKeys.size() * 2 + 1);

		sb.append(_SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (CommerceDiscountUsageEntry commerceDiscountUsageEntry :
					(List<CommerceDiscountUsageEntry>)query.list()) {

				map.put(
					commerceDiscountUsageEntry.getPrimaryKeyObj(),
					commerceDiscountUsageEntry);

				cacheResult(commerceDiscountUsageEntry);

				uncachedPrimaryKeys.remove(
					commerceDiscountUsageEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceDiscountUsageEntryImpl.class, primaryKey,
					nullModel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the commerce discount usage entries.
	 *
	 * @return the commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount usage entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @return the range of commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
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

		List<CommerceDiscountUsageEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscountUsageEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY;

				sql = sql.concat(
					CommerceDiscountUsageEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceDiscountUsageEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the commerce discount usage entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceDiscountUsageEntry commerceDiscountUsageEntry :
				findAll()) {

			remove(commerceDiscountUsageEntry);
		}
	}

	/**
	 * Returns the number of commerce discount usage entries.
	 *
	 * @return the number of commerce discount usage entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCEDISCOUNTUSAGEENTRY);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceDiscountUsageEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce discount usage entry persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCommerceDiscountId = new FinderPath(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceDiscountId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCommerceDiscountId = new FinderPath(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceDiscountId", new String[] {Long.class.getName()},
			CommerceDiscountUsageEntryModelImpl.
				COMMERCEDISCOUNTID_COLUMN_BITMASK |
			CommerceDiscountUsageEntryModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByCommerceDiscountId = new FinderPath(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceDiscountId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByA_D = new FinderPath(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByA_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByA_D = new FinderPath(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByA_D",
			new String[] {Long.class.getName(), Long.class.getName()},
			CommerceDiscountUsageEntryModelImpl.
				COMMERCEACCOUNTID_COLUMN_BITMASK |
			CommerceDiscountUsageEntryModelImpl.
				COMMERCEDISCOUNTID_COLUMN_BITMASK |
			CommerceDiscountUsageEntryModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByA_D = new FinderPath(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_D",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByO_D = new FinderPath(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByO_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByO_D = new FinderPath(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByO_D",
			new String[] {Long.class.getName(), Long.class.getName()},
			CommerceDiscountUsageEntryModelImpl.COMMERCEORDERID_COLUMN_BITMASK |
			CommerceDiscountUsageEntryModelImpl.
				COMMERCEDISCOUNTID_COLUMN_BITMASK |
			CommerceDiscountUsageEntryModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByO_D = new FinderPath(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByO_D",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByA_O_D = new FinderPath(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByA_O_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByA_O_D = new FinderPath(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByA_O_D",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			CommerceDiscountUsageEntryModelImpl.
				COMMERCEACCOUNTID_COLUMN_BITMASK |
			CommerceDiscountUsageEntryModelImpl.COMMERCEORDERID_COLUMN_BITMASK |
			CommerceDiscountUsageEntryModelImpl.
				COMMERCEDISCOUNTID_COLUMN_BITMASK |
			CommerceDiscountUsageEntryModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByA_O_D = new FinderPath(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByA_O_D",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	}

	public void destroy() {
		entityCache.removeCache(CommerceDiscountUsageEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY =
		"SELECT commerceDiscountUsageEntry FROM CommerceDiscountUsageEntry commerceDiscountUsageEntry";

	private static final String
		_SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY_WHERE_PKS_IN =
			"SELECT commerceDiscountUsageEntry FROM CommerceDiscountUsageEntry commerceDiscountUsageEntry WHERE commerceDiscountUsageEntryId IN (";

	private static final String _SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY_WHERE =
		"SELECT commerceDiscountUsageEntry FROM CommerceDiscountUsageEntry commerceDiscountUsageEntry WHERE ";

	private static final String _SQL_COUNT_COMMERCEDISCOUNTUSAGEENTRY =
		"SELECT COUNT(commerceDiscountUsageEntry) FROM CommerceDiscountUsageEntry commerceDiscountUsageEntry";

	private static final String _SQL_COUNT_COMMERCEDISCOUNTUSAGEENTRY_WHERE =
		"SELECT COUNT(commerceDiscountUsageEntry) FROM CommerceDiscountUsageEntry commerceDiscountUsageEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceDiscountUsageEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceDiscountUsageEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceDiscountUsageEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountUsageEntryPersistenceImpl.class);

}