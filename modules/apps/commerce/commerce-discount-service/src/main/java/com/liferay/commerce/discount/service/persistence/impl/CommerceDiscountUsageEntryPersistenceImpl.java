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
import com.liferay.commerce.discount.model.CommerceDiscountUsageEntryTable;
import com.liferay.commerce.discount.model.impl.CommerceDiscountUsageEntryImpl;
import com.liferay.commerce.discount.model.impl.CommerceDiscountUsageEntryModelImpl;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountUsageEntryPersistence;
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
				finderPath, finderArgs);

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

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

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

	private FinderPath _finderPathWithPaginationFindByCAI_CDI;
	private FinderPath _finderPathWithoutPaginationFindByCAI_CDI;
	private FinderPath _finderPathCountByCAI_CDI;

	/**
	 * Returns all the commerce discount usage entries where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByCAI_CDI(
		long commerceAccountId, long commerceDiscountId) {

		return findByCAI_CDI(
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
	public List<CommerceDiscountUsageEntry> findByCAI_CDI(
		long commerceAccountId, long commerceDiscountId, int start, int end) {

		return findByCAI_CDI(
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
	public List<CommerceDiscountUsageEntry> findByCAI_CDI(
		long commerceAccountId, long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return findByCAI_CDI(
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
	public List<CommerceDiscountUsageEntry> findByCAI_CDI(
		long commerceAccountId, long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCAI_CDI;
				finderArgs = new Object[] {
					commerceAccountId, commerceDiscountId
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCAI_CDI;
			finderArgs = new Object[] {
				commerceAccountId, commerceDiscountId, start, end,
				orderByComparator
			};
		}

		List<CommerceDiscountUsageEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscountUsageEntry>)finderCache.getResult(
				finderPath, finderArgs);

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

			sb.append(_FINDER_COLUMN_CAI_CDI_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_CAI_CDI_COMMERCEDISCOUNTID_2);

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
	public CommerceDiscountUsageEntry findByCAI_CDI_First(
			long commerceAccountId, long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			fetchByCAI_CDI_First(
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
	public CommerceDiscountUsageEntry fetchByCAI_CDI_First(
		long commerceAccountId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		List<CommerceDiscountUsageEntry> list = findByCAI_CDI(
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
	public CommerceDiscountUsageEntry findByCAI_CDI_Last(
			long commerceAccountId, long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			fetchByCAI_CDI_Last(
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
	public CommerceDiscountUsageEntry fetchByCAI_CDI_Last(
		long commerceAccountId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		int count = countByCAI_CDI(commerceAccountId, commerceDiscountId);

		if (count == 0) {
			return null;
		}

		List<CommerceDiscountUsageEntry> list = findByCAI_CDI(
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
	public CommerceDiscountUsageEntry[] findByCAI_CDI_PrevAndNext(
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

			array[0] = getByCAI_CDI_PrevAndNext(
				session, commerceDiscountUsageEntry, commerceAccountId,
				commerceDiscountId, orderByComparator, true);

			array[1] = commerceDiscountUsageEntry;

			array[2] = getByCAI_CDI_PrevAndNext(
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

	protected CommerceDiscountUsageEntry getByCAI_CDI_PrevAndNext(
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

		sb.append(_FINDER_COLUMN_CAI_CDI_COMMERCEACCOUNTID_2);

		sb.append(_FINDER_COLUMN_CAI_CDI_COMMERCEDISCOUNTID_2);

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
	public void removeByCAI_CDI(
		long commerceAccountId, long commerceDiscountId) {

		for (CommerceDiscountUsageEntry commerceDiscountUsageEntry :
				findByCAI_CDI(
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
	public int countByCAI_CDI(long commerceAccountId, long commerceDiscountId) {
		FinderPath finderPath = _finderPathCountByCAI_CDI;

		Object[] finderArgs = new Object[] {
			commerceAccountId, commerceDiscountId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNTUSAGEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_CAI_CDI_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_CAI_CDI_COMMERCEDISCOUNTID_2);

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
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CAI_CDI_COMMERCEACCOUNTID_2 =
		"commerceDiscountUsageEntry.commerceAccountId = ? AND ";

	private static final String _FINDER_COLUMN_CAI_CDI_COMMERCEDISCOUNTID_2 =
		"commerceDiscountUsageEntry.commerceDiscountId = ?";

	private FinderPath _finderPathWithPaginationFindByCOI_CDI;
	private FinderPath _finderPathWithoutPaginationFindByCOI_CDI;
	private FinderPath _finderPathCountByCOI_CDI;

	/**
	 * Returns all the commerce discount usage entries where commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByCOI_CDI(
		long commerceOrderId, long commerceDiscountId) {

		return findByCOI_CDI(
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
	public List<CommerceDiscountUsageEntry> findByCOI_CDI(
		long commerceOrderId, long commerceDiscountId, int start, int end) {

		return findByCOI_CDI(
			commerceOrderId, commerceDiscountId, start, end, null);
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
	public List<CommerceDiscountUsageEntry> findByCOI_CDI(
		long commerceOrderId, long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return findByCOI_CDI(
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
	public List<CommerceDiscountUsageEntry> findByCOI_CDI(
		long commerceOrderId, long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCOI_CDI;
				finderArgs = new Object[] {commerceOrderId, commerceDiscountId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCOI_CDI;
			finderArgs = new Object[] {
				commerceOrderId, commerceDiscountId, start, end,
				orderByComparator
			};
		}

		List<CommerceDiscountUsageEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscountUsageEntry>)finderCache.getResult(
				finderPath, finderArgs);

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

			sb.append(_FINDER_COLUMN_COI_CDI_COMMERCEORDERID_2);

			sb.append(_FINDER_COLUMN_COI_CDI_COMMERCEDISCOUNTID_2);

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
	public CommerceDiscountUsageEntry findByCOI_CDI_First(
			long commerceOrderId, long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			fetchByCOI_CDI_First(
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
	public CommerceDiscountUsageEntry fetchByCOI_CDI_First(
		long commerceOrderId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		List<CommerceDiscountUsageEntry> list = findByCOI_CDI(
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
	public CommerceDiscountUsageEntry findByCOI_CDI_Last(
			long commerceOrderId, long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			fetchByCOI_CDI_Last(
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
	public CommerceDiscountUsageEntry fetchByCOI_CDI_Last(
		long commerceOrderId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		int count = countByCOI_CDI(commerceOrderId, commerceDiscountId);

		if (count == 0) {
			return null;
		}

		List<CommerceDiscountUsageEntry> list = findByCOI_CDI(
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
	public CommerceDiscountUsageEntry[] findByCOI_CDI_PrevAndNext(
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

			array[0] = getByCOI_CDI_PrevAndNext(
				session, commerceDiscountUsageEntry, commerceOrderId,
				commerceDiscountId, orderByComparator, true);

			array[1] = commerceDiscountUsageEntry;

			array[2] = getByCOI_CDI_PrevAndNext(
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

	protected CommerceDiscountUsageEntry getByCOI_CDI_PrevAndNext(
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

		sb.append(_FINDER_COLUMN_COI_CDI_COMMERCEORDERID_2);

		sb.append(_FINDER_COLUMN_COI_CDI_COMMERCEDISCOUNTID_2);

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
	public void removeByCOI_CDI(long commerceOrderId, long commerceDiscountId) {
		for (CommerceDiscountUsageEntry commerceDiscountUsageEntry :
				findByCOI_CDI(
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
	public int countByCOI_CDI(long commerceOrderId, long commerceDiscountId) {
		FinderPath finderPath = _finderPathCountByCOI_CDI;

		Object[] finderArgs = new Object[] {
			commerceOrderId, commerceDiscountId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNTUSAGEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_COI_CDI_COMMERCEORDERID_2);

			sb.append(_FINDER_COLUMN_COI_CDI_COMMERCEDISCOUNTID_2);

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
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COI_CDI_COMMERCEORDERID_2 =
		"commerceDiscountUsageEntry.commerceOrderId = ? AND ";

	private static final String _FINDER_COLUMN_COI_CDI_COMMERCEDISCOUNTID_2 =
		"commerceDiscountUsageEntry.commerceDiscountId = ?";

	private FinderPath _finderPathWithPaginationFindByCAI_COI_CDI;
	private FinderPath _finderPathWithoutPaginationFindByCAI_COI_CDI;
	private FinderPath _finderPathCountByCAI_COI_CDI;

	/**
	 * Returns all the commerce discount usage entries where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByCAI_COI_CDI(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId) {

		return findByCAI_COI_CDI(
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
	public List<CommerceDiscountUsageEntry> findByCAI_COI_CDI(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId,
		int start, int end) {

		return findByCAI_COI_CDI(
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
	public List<CommerceDiscountUsageEntry> findByCAI_COI_CDI(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId,
		int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return findByCAI_COI_CDI(
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
	public List<CommerceDiscountUsageEntry> findByCAI_COI_CDI(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId,
		int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCAI_COI_CDI;
				finderArgs = new Object[] {
					commerceAccountId, commerceOrderId, commerceDiscountId
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCAI_COI_CDI;
			finderArgs = new Object[] {
				commerceAccountId, commerceOrderId, commerceDiscountId, start,
				end, orderByComparator
			};
		}

		List<CommerceDiscountUsageEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscountUsageEntry>)finderCache.getResult(
				finderPath, finderArgs);

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

			sb.append(_FINDER_COLUMN_CAI_COI_CDI_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_CAI_COI_CDI_COMMERCEORDERID_2);

			sb.append(_FINDER_COLUMN_CAI_COI_CDI_COMMERCEDISCOUNTID_2);

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
	public CommerceDiscountUsageEntry findByCAI_COI_CDI_First(
			long commerceAccountId, long commerceOrderId,
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			fetchByCAI_COI_CDI_First(
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
	public CommerceDiscountUsageEntry fetchByCAI_COI_CDI_First(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		List<CommerceDiscountUsageEntry> list = findByCAI_COI_CDI(
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
	public CommerceDiscountUsageEntry findByCAI_COI_CDI_Last(
			long commerceAccountId, long commerceOrderId,
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			fetchByCAI_COI_CDI_Last(
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
	public CommerceDiscountUsageEntry fetchByCAI_COI_CDI_Last(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		int count = countByCAI_COI_CDI(
			commerceAccountId, commerceOrderId, commerceDiscountId);

		if (count == 0) {
			return null;
		}

		List<CommerceDiscountUsageEntry> list = findByCAI_COI_CDI(
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
	public CommerceDiscountUsageEntry[] findByCAI_COI_CDI_PrevAndNext(
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

			array[0] = getByCAI_COI_CDI_PrevAndNext(
				session, commerceDiscountUsageEntry, commerceAccountId,
				commerceOrderId, commerceDiscountId, orderByComparator, true);

			array[1] = commerceDiscountUsageEntry;

			array[2] = getByCAI_COI_CDI_PrevAndNext(
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

	protected CommerceDiscountUsageEntry getByCAI_COI_CDI_PrevAndNext(
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

		sb.append(_FINDER_COLUMN_CAI_COI_CDI_COMMERCEACCOUNTID_2);

		sb.append(_FINDER_COLUMN_CAI_COI_CDI_COMMERCEORDERID_2);

		sb.append(_FINDER_COLUMN_CAI_COI_CDI_COMMERCEDISCOUNTID_2);

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
	public void removeByCAI_COI_CDI(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId) {

		for (CommerceDiscountUsageEntry commerceDiscountUsageEntry :
				findByCAI_COI_CDI(
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
	public int countByCAI_COI_CDI(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId) {

		FinderPath finderPath = _finderPathCountByCAI_COI_CDI;

		Object[] finderArgs = new Object[] {
			commerceAccountId, commerceOrderId, commerceDiscountId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNTUSAGEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_CAI_COI_CDI_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_CAI_COI_CDI_COMMERCEORDERID_2);

			sb.append(_FINDER_COLUMN_CAI_COI_CDI_COMMERCEDISCOUNTID_2);

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
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CAI_COI_CDI_COMMERCEACCOUNTID_2 =
		"commerceDiscountUsageEntry.commerceAccountId = ? AND ";

	private static final String _FINDER_COLUMN_CAI_COI_CDI_COMMERCEORDERID_2 =
		"commerceDiscountUsageEntry.commerceOrderId = ? AND ";

	private static final String
		_FINDER_COLUMN_CAI_COI_CDI_COMMERCEDISCOUNTID_2 =
			"commerceDiscountUsageEntry.commerceDiscountId = ?";

	public CommerceDiscountUsageEntryPersistenceImpl() {
		setModelClass(CommerceDiscountUsageEntry.class);

		setModelImplClass(CommerceDiscountUsageEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceDiscountUsageEntryTable.INSTANCE);
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
			CommerceDiscountUsageEntryImpl.class,
			commerceDiscountUsageEntry.getPrimaryKey(),
			commerceDiscountUsageEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce discount usage entries in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountUsageEntries the commerce discount usage entries
	 */
	@Override
	public void cacheResult(
		List<CommerceDiscountUsageEntry> commerceDiscountUsageEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceDiscountUsageEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceDiscountUsageEntry commerceDiscountUsageEntry :
				commerceDiscountUsageEntries) {

			if (entityCache.getResult(
					CommerceDiscountUsageEntryImpl.class,
					commerceDiscountUsageEntry.getPrimaryKey()) == null) {

				cacheResult(commerceDiscountUsageEntry);
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

		finderCache.clearCache(CommerceDiscountUsageEntryImpl.class);
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
			CommerceDiscountUsageEntryImpl.class, commerceDiscountUsageEntry);
	}

	@Override
	public void clearCache(
		List<CommerceDiscountUsageEntry> commerceDiscountUsageEntries) {

		for (CommerceDiscountUsageEntry commerceDiscountUsageEntry :
				commerceDiscountUsageEntries) {

			entityCache.removeResult(
				CommerceDiscountUsageEntryImpl.class,
				commerceDiscountUsageEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceDiscountUsageEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
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

		Date date = new Date();

		if (isNew && (commerceDiscountUsageEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceDiscountUsageEntry.setCreateDate(date);
			}
			else {
				commerceDiscountUsageEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceDiscountUsageEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceDiscountUsageEntry.setModifiedDate(date);
			}
			else {
				commerceDiscountUsageEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceDiscountUsageEntry);
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

		entityCache.putResult(
			CommerceDiscountUsageEntryImpl.class,
			commerceDiscountUsageEntryModelImpl, false, true);

		if (isNew) {
			commerceDiscountUsageEntry.setNew(false);
		}

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
	 * @param commerceDiscountUsageEntryId the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry, or <code>null</code> if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry fetchByPrimaryKey(
		long commerceDiscountUsageEntryId) {

		return fetchByPrimaryKey((Serializable)commerceDiscountUsageEntryId);
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
				finderPath, finderArgs);
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
			_finderPathCountAll, FINDER_ARGS_EMPTY);

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
		return "commerceDiscountUsageEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceDiscountUsageEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce discount usage entry persistence.
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

		_finderPathWithPaginationFindByCommerceDiscountId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceDiscountId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceDiscountId"}, true);

		_finderPathWithoutPaginationFindByCommerceDiscountId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceDiscountId", new String[] {Long.class.getName()},
			new String[] {"commerceDiscountId"}, true);

		_finderPathCountByCommerceDiscountId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceDiscountId", new String[] {Long.class.getName()},
			new String[] {"commerceDiscountId"}, false);

		_finderPathWithPaginationFindByCAI_CDI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCAI_CDI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"commerceAccountId", "commerceDiscountId"}, true);

		_finderPathWithoutPaginationFindByCAI_CDI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCAI_CDI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceAccountId", "commerceDiscountId"}, true);

		_finderPathCountByCAI_CDI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCAI_CDI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceAccountId", "commerceDiscountId"}, false);

		_finderPathWithPaginationFindByCOI_CDI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCOI_CDI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"commerceOrderId", "commerceDiscountId"}, true);

		_finderPathWithoutPaginationFindByCOI_CDI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCOI_CDI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceOrderId", "commerceDiscountId"}, true);

		_finderPathCountByCOI_CDI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCOI_CDI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceOrderId", "commerceDiscountId"}, false);

		_finderPathWithPaginationFindByCAI_COI_CDI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCAI_COI_CDI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"commerceAccountId", "commerceOrderId", "commerceDiscountId"
			},
			true);

		_finderPathWithoutPaginationFindByCAI_COI_CDI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCAI_COI_CDI",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"commerceAccountId", "commerceOrderId", "commerceDiscountId"
			},
			true);

		_finderPathCountByCAI_COI_CDI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCAI_COI_CDI",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"commerceAccountId", "commerceOrderId", "commerceDiscountId"
			},
			false);
	}

	public void destroy() {
		entityCache.removeCache(CommerceDiscountUsageEntryImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY =
		"SELECT commerceDiscountUsageEntry FROM CommerceDiscountUsageEntry commerceDiscountUsageEntry";

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

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}