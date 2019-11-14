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

package com.liferay.portal.tools.service.builder.test.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.BeanReference;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchBigDecimalEntryException;
import com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry;
import com.liferay.portal.tools.service.builder.test.model.impl.BigDecimalEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.BigDecimalEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.BigDecimalEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.LVEntryPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.math.BigDecimal;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the big decimal entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class BigDecimalEntryPersistenceImpl
	extends BasePersistenceImpl<BigDecimalEntry>
	implements BigDecimalEntryPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>BigDecimalEntryUtil</code> to access the big decimal entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		BigDecimalEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByBigDecimalValue;
	private FinderPath _finderPathWithoutPaginationFindByBigDecimalValue;
	private FinderPath _finderPathCountByBigDecimalValue;

	/**
	 * Returns all the big decimal entries where bigDecimalValue = &#63;.
	 *
	 * @param bigDecimalValue the big decimal value
	 * @return the matching big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findByBigDecimalValue(
		BigDecimal bigDecimalValue) {

		return findByBigDecimalValue(
			bigDecimalValue, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the big decimal entries where bigDecimalValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
	 * </p>
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @return the range of matching big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findByBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end) {

		return findByBigDecimalValue(bigDecimalValue, start, end, null);
	}

	/**
	 * Returns an ordered range of all the big decimal entries where bigDecimalValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
	 * </p>
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findByBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end,
		OrderByComparator<BigDecimalEntry> orderByComparator) {

		return findByBigDecimalValue(
			bigDecimalValue, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the big decimal entries where bigDecimalValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
	 * </p>
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findByBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end,
		OrderByComparator<BigDecimalEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByBigDecimalValue;
				finderArgs = new Object[] {bigDecimalValue};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByBigDecimalValue;
			finderArgs = new Object[] {
				bigDecimalValue, start, end, orderByComparator
			};
		}

		List<BigDecimalEntry> list = null;

		if (useFinderCache) {
			list = (List<BigDecimalEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BigDecimalEntry bigDecimalEntry : list) {
					if (!Objects.equals(
							bigDecimalValue,
							bigDecimalEntry.getBigDecimalValue())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_BIGDECIMALENTRY_WHERE);

			boolean bindBigDecimalValue = false;

			if (bigDecimalValue == null) {
				query.append(_FINDER_COLUMN_BIGDECIMALVALUE_BIGDECIMALVALUE_1);
			}
			else {
				bindBigDecimalValue = true;

				query.append(_FINDER_COLUMN_BIGDECIMALVALUE_BIGDECIMALVALUE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(BigDecimalEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindBigDecimalValue) {
					qPos.add(bigDecimalValue);
				}

				list = (List<BigDecimalEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first big decimal entry in the ordered set where bigDecimalValue = &#63;.
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching big decimal entry
	 * @throws NoSuchBigDecimalEntryException if a matching big decimal entry could not be found
	 */
	@Override
	public BigDecimalEntry findByBigDecimalValue_First(
			BigDecimal bigDecimalValue,
			OrderByComparator<BigDecimalEntry> orderByComparator)
		throws NoSuchBigDecimalEntryException {

		BigDecimalEntry bigDecimalEntry = fetchByBigDecimalValue_First(
			bigDecimalValue, orderByComparator);

		if (bigDecimalEntry != null) {
			return bigDecimalEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("bigDecimalValue=");
		msg.append(bigDecimalValue);

		msg.append("}");

		throw new NoSuchBigDecimalEntryException(msg.toString());
	}

	/**
	 * Returns the first big decimal entry in the ordered set where bigDecimalValue = &#63;.
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching big decimal entry, or <code>null</code> if a matching big decimal entry could not be found
	 */
	@Override
	public BigDecimalEntry fetchByBigDecimalValue_First(
		BigDecimal bigDecimalValue,
		OrderByComparator<BigDecimalEntry> orderByComparator) {

		List<BigDecimalEntry> list = findByBigDecimalValue(
			bigDecimalValue, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last big decimal entry in the ordered set where bigDecimalValue = &#63;.
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching big decimal entry
	 * @throws NoSuchBigDecimalEntryException if a matching big decimal entry could not be found
	 */
	@Override
	public BigDecimalEntry findByBigDecimalValue_Last(
			BigDecimal bigDecimalValue,
			OrderByComparator<BigDecimalEntry> orderByComparator)
		throws NoSuchBigDecimalEntryException {

		BigDecimalEntry bigDecimalEntry = fetchByBigDecimalValue_Last(
			bigDecimalValue, orderByComparator);

		if (bigDecimalEntry != null) {
			return bigDecimalEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("bigDecimalValue=");
		msg.append(bigDecimalValue);

		msg.append("}");

		throw new NoSuchBigDecimalEntryException(msg.toString());
	}

	/**
	 * Returns the last big decimal entry in the ordered set where bigDecimalValue = &#63;.
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching big decimal entry, or <code>null</code> if a matching big decimal entry could not be found
	 */
	@Override
	public BigDecimalEntry fetchByBigDecimalValue_Last(
		BigDecimal bigDecimalValue,
		OrderByComparator<BigDecimalEntry> orderByComparator) {

		int count = countByBigDecimalValue(bigDecimalValue);

		if (count == 0) {
			return null;
		}

		List<BigDecimalEntry> list = findByBigDecimalValue(
			bigDecimalValue, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the big decimal entries before and after the current big decimal entry in the ordered set where bigDecimalValue = &#63;.
	 *
	 * @param bigDecimalEntryId the primary key of the current big decimal entry
	 * @param bigDecimalValue the big decimal value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next big decimal entry
	 * @throws NoSuchBigDecimalEntryException if a big decimal entry with the primary key could not be found
	 */
	@Override
	public BigDecimalEntry[] findByBigDecimalValue_PrevAndNext(
			long bigDecimalEntryId, BigDecimal bigDecimalValue,
			OrderByComparator<BigDecimalEntry> orderByComparator)
		throws NoSuchBigDecimalEntryException {

		BigDecimalEntry bigDecimalEntry = findByPrimaryKey(bigDecimalEntryId);

		Session session = null;

		try {
			session = openSession();

			BigDecimalEntry[] array = new BigDecimalEntryImpl[3];

			array[0] = getByBigDecimalValue_PrevAndNext(
				session, bigDecimalEntry, bigDecimalValue, orderByComparator,
				true);

			array[1] = bigDecimalEntry;

			array[2] = getByBigDecimalValue_PrevAndNext(
				session, bigDecimalEntry, bigDecimalValue, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BigDecimalEntry getByBigDecimalValue_PrevAndNext(
		Session session, BigDecimalEntry bigDecimalEntry,
		BigDecimal bigDecimalValue,
		OrderByComparator<BigDecimalEntry> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BIGDECIMALENTRY_WHERE);

		boolean bindBigDecimalValue = false;

		if (bigDecimalValue == null) {
			query.append(_FINDER_COLUMN_BIGDECIMALVALUE_BIGDECIMALVALUE_1);
		}
		else {
			bindBigDecimalValue = true;

			query.append(_FINDER_COLUMN_BIGDECIMALVALUE_BIGDECIMALVALUE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(BigDecimalEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindBigDecimalValue) {
			qPos.add(bigDecimalValue);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						bigDecimalEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<BigDecimalEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the big decimal entries where bigDecimalValue = &#63; from the database.
	 *
	 * @param bigDecimalValue the big decimal value
	 */
	@Override
	public void removeByBigDecimalValue(BigDecimal bigDecimalValue) {
		for (BigDecimalEntry bigDecimalEntry :
				findByBigDecimalValue(
					bigDecimalValue, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(bigDecimalEntry);
		}
	}

	/**
	 * Returns the number of big decimal entries where bigDecimalValue = &#63;.
	 *
	 * @param bigDecimalValue the big decimal value
	 * @return the number of matching big decimal entries
	 */
	@Override
	public int countByBigDecimalValue(BigDecimal bigDecimalValue) {
		FinderPath finderPath = _finderPathCountByBigDecimalValue;

		Object[] finderArgs = new Object[] {bigDecimalValue};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BIGDECIMALENTRY_WHERE);

			boolean bindBigDecimalValue = false;

			if (bigDecimalValue == null) {
				query.append(_FINDER_COLUMN_BIGDECIMALVALUE_BIGDECIMALVALUE_1);
			}
			else {
				bindBigDecimalValue = true;

				query.append(_FINDER_COLUMN_BIGDECIMALVALUE_BIGDECIMALVALUE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindBigDecimalValue) {
					qPos.add(bigDecimalValue);
				}

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

	private static final String
		_FINDER_COLUMN_BIGDECIMALVALUE_BIGDECIMALVALUE_1 =
			"bigDecimalEntry.bigDecimalValue IS NULL";

	private static final String
		_FINDER_COLUMN_BIGDECIMALVALUE_BIGDECIMALVALUE_2 =
			"bigDecimalEntry.bigDecimalValue = ?";

	private FinderPath _finderPathWithPaginationFindByGtBigDecimalValue;
	private FinderPath _finderPathWithPaginationCountByGtBigDecimalValue;

	/**
	 * Returns all the big decimal entries where bigDecimalValue &gt; &#63;.
	 *
	 * @param bigDecimalValue the big decimal value
	 * @return the matching big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findByGtBigDecimalValue(
		BigDecimal bigDecimalValue) {

		return findByGtBigDecimalValue(
			bigDecimalValue, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the big decimal entries where bigDecimalValue &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
	 * </p>
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @return the range of matching big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findByGtBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end) {

		return findByGtBigDecimalValue(bigDecimalValue, start, end, null);
	}

	/**
	 * Returns an ordered range of all the big decimal entries where bigDecimalValue &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
	 * </p>
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findByGtBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end,
		OrderByComparator<BigDecimalEntry> orderByComparator) {

		return findByGtBigDecimalValue(
			bigDecimalValue, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the big decimal entries where bigDecimalValue &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
	 * </p>
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findByGtBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end,
		OrderByComparator<BigDecimalEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByGtBigDecimalValue;
		finderArgs = new Object[] {
			bigDecimalValue, start, end, orderByComparator
		};

		List<BigDecimalEntry> list = null;

		if (useFinderCache) {
			list = (List<BigDecimalEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BigDecimalEntry bigDecimalEntry : list) {
					if (bigDecimalValue.compareTo(
							bigDecimalEntry.getBigDecimalValue()) >= 0) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_BIGDECIMALENTRY_WHERE);

			boolean bindBigDecimalValue = false;

			if (bigDecimalValue == null) {
				query.append(
					_FINDER_COLUMN_GTBIGDECIMALVALUE_BIGDECIMALVALUE_1);
			}
			else {
				bindBigDecimalValue = true;

				query.append(
					_FINDER_COLUMN_GTBIGDECIMALVALUE_BIGDECIMALVALUE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(BigDecimalEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindBigDecimalValue) {
					qPos.add(bigDecimalValue);
				}

				list = (List<BigDecimalEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first big decimal entry in the ordered set where bigDecimalValue &gt; &#63;.
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching big decimal entry
	 * @throws NoSuchBigDecimalEntryException if a matching big decimal entry could not be found
	 */
	@Override
	public BigDecimalEntry findByGtBigDecimalValue_First(
			BigDecimal bigDecimalValue,
			OrderByComparator<BigDecimalEntry> orderByComparator)
		throws NoSuchBigDecimalEntryException {

		BigDecimalEntry bigDecimalEntry = fetchByGtBigDecimalValue_First(
			bigDecimalValue, orderByComparator);

		if (bigDecimalEntry != null) {
			return bigDecimalEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("bigDecimalValue>");
		msg.append(bigDecimalValue);

		msg.append("}");

		throw new NoSuchBigDecimalEntryException(msg.toString());
	}

	/**
	 * Returns the first big decimal entry in the ordered set where bigDecimalValue &gt; &#63;.
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching big decimal entry, or <code>null</code> if a matching big decimal entry could not be found
	 */
	@Override
	public BigDecimalEntry fetchByGtBigDecimalValue_First(
		BigDecimal bigDecimalValue,
		OrderByComparator<BigDecimalEntry> orderByComparator) {

		List<BigDecimalEntry> list = findByGtBigDecimalValue(
			bigDecimalValue, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last big decimal entry in the ordered set where bigDecimalValue &gt; &#63;.
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching big decimal entry
	 * @throws NoSuchBigDecimalEntryException if a matching big decimal entry could not be found
	 */
	@Override
	public BigDecimalEntry findByGtBigDecimalValue_Last(
			BigDecimal bigDecimalValue,
			OrderByComparator<BigDecimalEntry> orderByComparator)
		throws NoSuchBigDecimalEntryException {

		BigDecimalEntry bigDecimalEntry = fetchByGtBigDecimalValue_Last(
			bigDecimalValue, orderByComparator);

		if (bigDecimalEntry != null) {
			return bigDecimalEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("bigDecimalValue>");
		msg.append(bigDecimalValue);

		msg.append("}");

		throw new NoSuchBigDecimalEntryException(msg.toString());
	}

	/**
	 * Returns the last big decimal entry in the ordered set where bigDecimalValue &gt; &#63;.
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching big decimal entry, or <code>null</code> if a matching big decimal entry could not be found
	 */
	@Override
	public BigDecimalEntry fetchByGtBigDecimalValue_Last(
		BigDecimal bigDecimalValue,
		OrderByComparator<BigDecimalEntry> orderByComparator) {

		int count = countByGtBigDecimalValue(bigDecimalValue);

		if (count == 0) {
			return null;
		}

		List<BigDecimalEntry> list = findByGtBigDecimalValue(
			bigDecimalValue, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the big decimal entries before and after the current big decimal entry in the ordered set where bigDecimalValue &gt; &#63;.
	 *
	 * @param bigDecimalEntryId the primary key of the current big decimal entry
	 * @param bigDecimalValue the big decimal value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next big decimal entry
	 * @throws NoSuchBigDecimalEntryException if a big decimal entry with the primary key could not be found
	 */
	@Override
	public BigDecimalEntry[] findByGtBigDecimalValue_PrevAndNext(
			long bigDecimalEntryId, BigDecimal bigDecimalValue,
			OrderByComparator<BigDecimalEntry> orderByComparator)
		throws NoSuchBigDecimalEntryException {

		BigDecimalEntry bigDecimalEntry = findByPrimaryKey(bigDecimalEntryId);

		Session session = null;

		try {
			session = openSession();

			BigDecimalEntry[] array = new BigDecimalEntryImpl[3];

			array[0] = getByGtBigDecimalValue_PrevAndNext(
				session, bigDecimalEntry, bigDecimalValue, orderByComparator,
				true);

			array[1] = bigDecimalEntry;

			array[2] = getByGtBigDecimalValue_PrevAndNext(
				session, bigDecimalEntry, bigDecimalValue, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BigDecimalEntry getByGtBigDecimalValue_PrevAndNext(
		Session session, BigDecimalEntry bigDecimalEntry,
		BigDecimal bigDecimalValue,
		OrderByComparator<BigDecimalEntry> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BIGDECIMALENTRY_WHERE);

		boolean bindBigDecimalValue = false;

		if (bigDecimalValue == null) {
			query.append(_FINDER_COLUMN_GTBIGDECIMALVALUE_BIGDECIMALVALUE_1);
		}
		else {
			bindBigDecimalValue = true;

			query.append(_FINDER_COLUMN_GTBIGDECIMALVALUE_BIGDECIMALVALUE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(BigDecimalEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindBigDecimalValue) {
			qPos.add(bigDecimalValue);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						bigDecimalEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<BigDecimalEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the big decimal entries where bigDecimalValue &gt; &#63; from the database.
	 *
	 * @param bigDecimalValue the big decimal value
	 */
	@Override
	public void removeByGtBigDecimalValue(BigDecimal bigDecimalValue) {
		for (BigDecimalEntry bigDecimalEntry :
				findByGtBigDecimalValue(
					bigDecimalValue, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(bigDecimalEntry);
		}
	}

	/**
	 * Returns the number of big decimal entries where bigDecimalValue &gt; &#63;.
	 *
	 * @param bigDecimalValue the big decimal value
	 * @return the number of matching big decimal entries
	 */
	@Override
	public int countByGtBigDecimalValue(BigDecimal bigDecimalValue) {
		FinderPath finderPath =
			_finderPathWithPaginationCountByGtBigDecimalValue;

		Object[] finderArgs = new Object[] {bigDecimalValue};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BIGDECIMALENTRY_WHERE);

			boolean bindBigDecimalValue = false;

			if (bigDecimalValue == null) {
				query.append(
					_FINDER_COLUMN_GTBIGDECIMALVALUE_BIGDECIMALVALUE_1);
			}
			else {
				bindBigDecimalValue = true;

				query.append(
					_FINDER_COLUMN_GTBIGDECIMALVALUE_BIGDECIMALVALUE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindBigDecimalValue) {
					qPos.add(bigDecimalValue);
				}

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

	private static final String
		_FINDER_COLUMN_GTBIGDECIMALVALUE_BIGDECIMALVALUE_1 =
			"bigDecimalEntry.bigDecimalValue IS NULL";

	private static final String
		_FINDER_COLUMN_GTBIGDECIMALVALUE_BIGDECIMALVALUE_2 =
			"bigDecimalEntry.bigDecimalValue > ?";

	private FinderPath _finderPathWithPaginationFindByLtBigDecimalValue;
	private FinderPath _finderPathWithPaginationCountByLtBigDecimalValue;

	/**
	 * Returns all the big decimal entries where bigDecimalValue &lt; &#63;.
	 *
	 * @param bigDecimalValue the big decimal value
	 * @return the matching big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findByLtBigDecimalValue(
		BigDecimal bigDecimalValue) {

		return findByLtBigDecimalValue(
			bigDecimalValue, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the big decimal entries where bigDecimalValue &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
	 * </p>
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @return the range of matching big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findByLtBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end) {

		return findByLtBigDecimalValue(bigDecimalValue, start, end, null);
	}

	/**
	 * Returns an ordered range of all the big decimal entries where bigDecimalValue &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
	 * </p>
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findByLtBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end,
		OrderByComparator<BigDecimalEntry> orderByComparator) {

		return findByLtBigDecimalValue(
			bigDecimalValue, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the big decimal entries where bigDecimalValue &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
	 * </p>
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findByLtBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end,
		OrderByComparator<BigDecimalEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLtBigDecimalValue;
		finderArgs = new Object[] {
			bigDecimalValue, start, end, orderByComparator
		};

		List<BigDecimalEntry> list = null;

		if (useFinderCache) {
			list = (List<BigDecimalEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BigDecimalEntry bigDecimalEntry : list) {
					if (bigDecimalValue.compareTo(
							bigDecimalEntry.getBigDecimalValue()) <= 0) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_BIGDECIMALENTRY_WHERE);

			boolean bindBigDecimalValue = false;

			if (bigDecimalValue == null) {
				query.append(
					_FINDER_COLUMN_LTBIGDECIMALVALUE_BIGDECIMALVALUE_1);
			}
			else {
				bindBigDecimalValue = true;

				query.append(
					_FINDER_COLUMN_LTBIGDECIMALVALUE_BIGDECIMALVALUE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(BigDecimalEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindBigDecimalValue) {
					qPos.add(bigDecimalValue);
				}

				list = (List<BigDecimalEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first big decimal entry in the ordered set where bigDecimalValue &lt; &#63;.
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching big decimal entry
	 * @throws NoSuchBigDecimalEntryException if a matching big decimal entry could not be found
	 */
	@Override
	public BigDecimalEntry findByLtBigDecimalValue_First(
			BigDecimal bigDecimalValue,
			OrderByComparator<BigDecimalEntry> orderByComparator)
		throws NoSuchBigDecimalEntryException {

		BigDecimalEntry bigDecimalEntry = fetchByLtBigDecimalValue_First(
			bigDecimalValue, orderByComparator);

		if (bigDecimalEntry != null) {
			return bigDecimalEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("bigDecimalValue<");
		msg.append(bigDecimalValue);

		msg.append("}");

		throw new NoSuchBigDecimalEntryException(msg.toString());
	}

	/**
	 * Returns the first big decimal entry in the ordered set where bigDecimalValue &lt; &#63;.
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching big decimal entry, or <code>null</code> if a matching big decimal entry could not be found
	 */
	@Override
	public BigDecimalEntry fetchByLtBigDecimalValue_First(
		BigDecimal bigDecimalValue,
		OrderByComparator<BigDecimalEntry> orderByComparator) {

		List<BigDecimalEntry> list = findByLtBigDecimalValue(
			bigDecimalValue, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last big decimal entry in the ordered set where bigDecimalValue &lt; &#63;.
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching big decimal entry
	 * @throws NoSuchBigDecimalEntryException if a matching big decimal entry could not be found
	 */
	@Override
	public BigDecimalEntry findByLtBigDecimalValue_Last(
			BigDecimal bigDecimalValue,
			OrderByComparator<BigDecimalEntry> orderByComparator)
		throws NoSuchBigDecimalEntryException {

		BigDecimalEntry bigDecimalEntry = fetchByLtBigDecimalValue_Last(
			bigDecimalValue, orderByComparator);

		if (bigDecimalEntry != null) {
			return bigDecimalEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("bigDecimalValue<");
		msg.append(bigDecimalValue);

		msg.append("}");

		throw new NoSuchBigDecimalEntryException(msg.toString());
	}

	/**
	 * Returns the last big decimal entry in the ordered set where bigDecimalValue &lt; &#63;.
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching big decimal entry, or <code>null</code> if a matching big decimal entry could not be found
	 */
	@Override
	public BigDecimalEntry fetchByLtBigDecimalValue_Last(
		BigDecimal bigDecimalValue,
		OrderByComparator<BigDecimalEntry> orderByComparator) {

		int count = countByLtBigDecimalValue(bigDecimalValue);

		if (count == 0) {
			return null;
		}

		List<BigDecimalEntry> list = findByLtBigDecimalValue(
			bigDecimalValue, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the big decimal entries before and after the current big decimal entry in the ordered set where bigDecimalValue &lt; &#63;.
	 *
	 * @param bigDecimalEntryId the primary key of the current big decimal entry
	 * @param bigDecimalValue the big decimal value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next big decimal entry
	 * @throws NoSuchBigDecimalEntryException if a big decimal entry with the primary key could not be found
	 */
	@Override
	public BigDecimalEntry[] findByLtBigDecimalValue_PrevAndNext(
			long bigDecimalEntryId, BigDecimal bigDecimalValue,
			OrderByComparator<BigDecimalEntry> orderByComparator)
		throws NoSuchBigDecimalEntryException {

		BigDecimalEntry bigDecimalEntry = findByPrimaryKey(bigDecimalEntryId);

		Session session = null;

		try {
			session = openSession();

			BigDecimalEntry[] array = new BigDecimalEntryImpl[3];

			array[0] = getByLtBigDecimalValue_PrevAndNext(
				session, bigDecimalEntry, bigDecimalValue, orderByComparator,
				true);

			array[1] = bigDecimalEntry;

			array[2] = getByLtBigDecimalValue_PrevAndNext(
				session, bigDecimalEntry, bigDecimalValue, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BigDecimalEntry getByLtBigDecimalValue_PrevAndNext(
		Session session, BigDecimalEntry bigDecimalEntry,
		BigDecimal bigDecimalValue,
		OrderByComparator<BigDecimalEntry> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BIGDECIMALENTRY_WHERE);

		boolean bindBigDecimalValue = false;

		if (bigDecimalValue == null) {
			query.append(_FINDER_COLUMN_LTBIGDECIMALVALUE_BIGDECIMALVALUE_1);
		}
		else {
			bindBigDecimalValue = true;

			query.append(_FINDER_COLUMN_LTBIGDECIMALVALUE_BIGDECIMALVALUE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(BigDecimalEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindBigDecimalValue) {
			qPos.add(bigDecimalValue);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						bigDecimalEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<BigDecimalEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the big decimal entries where bigDecimalValue &lt; &#63; from the database.
	 *
	 * @param bigDecimalValue the big decimal value
	 */
	@Override
	public void removeByLtBigDecimalValue(BigDecimal bigDecimalValue) {
		for (BigDecimalEntry bigDecimalEntry :
				findByLtBigDecimalValue(
					bigDecimalValue, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(bigDecimalEntry);
		}
	}

	/**
	 * Returns the number of big decimal entries where bigDecimalValue &lt; &#63;.
	 *
	 * @param bigDecimalValue the big decimal value
	 * @return the number of matching big decimal entries
	 */
	@Override
	public int countByLtBigDecimalValue(BigDecimal bigDecimalValue) {
		FinderPath finderPath =
			_finderPathWithPaginationCountByLtBigDecimalValue;

		Object[] finderArgs = new Object[] {bigDecimalValue};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BIGDECIMALENTRY_WHERE);

			boolean bindBigDecimalValue = false;

			if (bigDecimalValue == null) {
				query.append(
					_FINDER_COLUMN_LTBIGDECIMALVALUE_BIGDECIMALVALUE_1);
			}
			else {
				bindBigDecimalValue = true;

				query.append(
					_FINDER_COLUMN_LTBIGDECIMALVALUE_BIGDECIMALVALUE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindBigDecimalValue) {
					qPos.add(bigDecimalValue);
				}

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

	private static final String
		_FINDER_COLUMN_LTBIGDECIMALVALUE_BIGDECIMALVALUE_1 =
			"bigDecimalEntry.bigDecimalValue IS NULL";

	private static final String
		_FINDER_COLUMN_LTBIGDECIMALVALUE_BIGDECIMALVALUE_2 =
			"bigDecimalEntry.bigDecimalValue < ?";

	public BigDecimalEntryPersistenceImpl() {
		setModelClass(BigDecimalEntry.class);

		setModelImplClass(BigDecimalEntryImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the big decimal entry in the entity cache if it is enabled.
	 *
	 * @param bigDecimalEntry the big decimal entry
	 */
	@Override
	public void cacheResult(BigDecimalEntry bigDecimalEntry) {
		entityCache.putResult(
			BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryImpl.class, bigDecimalEntry.getPrimaryKey(),
			bigDecimalEntry);

		bigDecimalEntry.resetOriginalValues();
	}

	/**
	 * Caches the big decimal entries in the entity cache if it is enabled.
	 *
	 * @param bigDecimalEntries the big decimal entries
	 */
	@Override
	public void cacheResult(List<BigDecimalEntry> bigDecimalEntries) {
		for (BigDecimalEntry bigDecimalEntry : bigDecimalEntries) {
			if (entityCache.getResult(
					BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
					BigDecimalEntryImpl.class,
					bigDecimalEntry.getPrimaryKey()) == null) {

				cacheResult(bigDecimalEntry);
			}
			else {
				bigDecimalEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all big decimal entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(BigDecimalEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the big decimal entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BigDecimalEntry bigDecimalEntry) {
		entityCache.removeResult(
			BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryImpl.class, bigDecimalEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<BigDecimalEntry> bigDecimalEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (BigDecimalEntry bigDecimalEntry : bigDecimalEntries) {
			entityCache.removeResult(
				BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
				BigDecimalEntryImpl.class, bigDecimalEntry.getPrimaryKey());
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
				BigDecimalEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new big decimal entry with the primary key. Does not add the big decimal entry to the database.
	 *
	 * @param bigDecimalEntryId the primary key for the new big decimal entry
	 * @return the new big decimal entry
	 */
	@Override
	public BigDecimalEntry create(long bigDecimalEntryId) {
		BigDecimalEntry bigDecimalEntry = new BigDecimalEntryImpl();

		bigDecimalEntry.setNew(true);
		bigDecimalEntry.setPrimaryKey(bigDecimalEntryId);

		bigDecimalEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return bigDecimalEntry;
	}

	/**
	 * Removes the big decimal entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param bigDecimalEntryId the primary key of the big decimal entry
	 * @return the big decimal entry that was removed
	 * @throws NoSuchBigDecimalEntryException if a big decimal entry with the primary key could not be found
	 */
	@Override
	public BigDecimalEntry remove(long bigDecimalEntryId)
		throws NoSuchBigDecimalEntryException {

		return remove((Serializable)bigDecimalEntryId);
	}

	/**
	 * Removes the big decimal entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the big decimal entry
	 * @return the big decimal entry that was removed
	 * @throws NoSuchBigDecimalEntryException if a big decimal entry with the primary key could not be found
	 */
	@Override
	public BigDecimalEntry remove(Serializable primaryKey)
		throws NoSuchBigDecimalEntryException {

		Session session = null;

		try {
			session = openSession();

			BigDecimalEntry bigDecimalEntry = (BigDecimalEntry)session.get(
				BigDecimalEntryImpl.class, primaryKey);

			if (bigDecimalEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchBigDecimalEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(bigDecimalEntry);
		}
		catch (NoSuchBigDecimalEntryException nsee) {
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
	protected BigDecimalEntry removeImpl(BigDecimalEntry bigDecimalEntry) {
		bigDecimalEntryToLVEntryTableMapper.deleteLeftPrimaryKeyTableMappings(
			bigDecimalEntry.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(bigDecimalEntry)) {
				bigDecimalEntry = (BigDecimalEntry)session.get(
					BigDecimalEntryImpl.class,
					bigDecimalEntry.getPrimaryKeyObj());
			}

			if (bigDecimalEntry != null) {
				session.delete(bigDecimalEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (bigDecimalEntry != null) {
			clearCache(bigDecimalEntry);
		}

		return bigDecimalEntry;
	}

	@Override
	public BigDecimalEntry updateImpl(BigDecimalEntry bigDecimalEntry) {
		boolean isNew = bigDecimalEntry.isNew();

		if (!(bigDecimalEntry instanceof BigDecimalEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(bigDecimalEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					bigDecimalEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in bigDecimalEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BigDecimalEntry implementation " +
					bigDecimalEntry.getClass());
		}

		BigDecimalEntryModelImpl bigDecimalEntryModelImpl =
			(BigDecimalEntryModelImpl)bigDecimalEntry;

		Session session = null;

		try {
			session = openSession();

			if (bigDecimalEntry.isNew()) {
				session.save(bigDecimalEntry);

				bigDecimalEntry.setNew(false);
			}
			else {
				bigDecimalEntry = (BigDecimalEntry)session.merge(
					bigDecimalEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!BigDecimalEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				bigDecimalEntryModelImpl.getBigDecimalValue()
			};

			finderCache.removeResult(_finderPathCountByBigDecimalValue, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByBigDecimalValue, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((bigDecimalEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByBigDecimalValue.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					bigDecimalEntryModelImpl.getOriginalBigDecimalValue()
				};

				finderCache.removeResult(
					_finderPathCountByBigDecimalValue, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByBigDecimalValue, args);

				args = new Object[] {
					bigDecimalEntryModelImpl.getBigDecimalValue()
				};

				finderCache.removeResult(
					_finderPathCountByBigDecimalValue, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByBigDecimalValue, args);
			}
		}

		entityCache.putResult(
			BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryImpl.class, bigDecimalEntry.getPrimaryKey(),
			bigDecimalEntry, false);

		bigDecimalEntry.resetOriginalValues();

		return bigDecimalEntry;
	}

	/**
	 * Returns the big decimal entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the big decimal entry
	 * @return the big decimal entry
	 * @throws NoSuchBigDecimalEntryException if a big decimal entry with the primary key could not be found
	 */
	@Override
	public BigDecimalEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchBigDecimalEntryException {

		BigDecimalEntry bigDecimalEntry = fetchByPrimaryKey(primaryKey);

		if (bigDecimalEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchBigDecimalEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return bigDecimalEntry;
	}

	/**
	 * Returns the big decimal entry with the primary key or throws a <code>NoSuchBigDecimalEntryException</code> if it could not be found.
	 *
	 * @param bigDecimalEntryId the primary key of the big decimal entry
	 * @return the big decimal entry
	 * @throws NoSuchBigDecimalEntryException if a big decimal entry with the primary key could not be found
	 */
	@Override
	public BigDecimalEntry findByPrimaryKey(long bigDecimalEntryId)
		throws NoSuchBigDecimalEntryException {

		return findByPrimaryKey((Serializable)bigDecimalEntryId);
	}

	/**
	 * Returns the big decimal entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param bigDecimalEntryId the primary key of the big decimal entry
	 * @return the big decimal entry, or <code>null</code> if a big decimal entry with the primary key could not be found
	 */
	@Override
	public BigDecimalEntry fetchByPrimaryKey(long bigDecimalEntryId) {
		return fetchByPrimaryKey((Serializable)bigDecimalEntryId);
	}

	/**
	 * Returns all the big decimal entries.
	 *
	 * @return the big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the big decimal entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @return the range of big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the big decimal entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findAll(
		int start, int end,
		OrderByComparator<BigDecimalEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the big decimal entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findAll(
		int start, int end,
		OrderByComparator<BigDecimalEntry> orderByComparator,
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

		List<BigDecimalEntry> list = null;

		if (useFinderCache) {
			list = (List<BigDecimalEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_BIGDECIMALENTRY);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_BIGDECIMALENTRY;

				sql = sql.concat(BigDecimalEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<BigDecimalEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the big decimal entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BigDecimalEntry bigDecimalEntry : findAll()) {
			remove(bigDecimalEntry);
		}
	}

	/**
	 * Returns the number of big decimal entries.
	 *
	 * @return the number of big decimal entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_BIGDECIMALENTRY);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the primaryKeys of lv entries associated with the big decimal entry.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @return long[] of the primaryKeys of lv entries associated with the big decimal entry
	 */
	@Override
	public long[] getLVEntryPrimaryKeys(long pk) {
		long[] pks = bigDecimalEntryToLVEntryTableMapper.getRightPrimaryKeys(
			pk);

		return pks.clone();
	}

	/**
	 * Returns all the lv entries associated with the big decimal entry.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @return the lv entries associated with the big decimal entry
	 */
	@Override
	public List<com.liferay.portal.tools.service.builder.test.model.LVEntry>
		getLVEntries(long pk) {

		return getLVEntries(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the lv entries associated with the big decimal entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @return the range of lv entries associated with the big decimal entry
	 */
	@Override
	public List<com.liferay.portal.tools.service.builder.test.model.LVEntry>
		getLVEntries(long pk, int start, int end) {

		return getLVEntries(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entries associated with the big decimal entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BigDecimalEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of lv entries associated with the big decimal entry
	 */
	@Override
	public List<com.liferay.portal.tools.service.builder.test.model.LVEntry>
		getLVEntries(
			long pk, int start, int end,
			OrderByComparator
				<com.liferay.portal.tools.service.builder.test.model.LVEntry>
					orderByComparator) {

		return bigDecimalEntryToLVEntryTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of lv entries associated with the big decimal entry.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @return the number of lv entries associated with the big decimal entry
	 */
	@Override
	public int getLVEntriesSize(long pk) {
		long[] pks = bigDecimalEntryToLVEntryTableMapper.getRightPrimaryKeys(
			pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the lv entry is associated with the big decimal entry.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntryPK the primary key of the lv entry
	 * @return <code>true</code> if the lv entry is associated with the big decimal entry; <code>false</code> otherwise
	 */
	@Override
	public boolean containsLVEntry(long pk, long lvEntryPK) {
		return bigDecimalEntryToLVEntryTableMapper.containsTableMapping(
			pk, lvEntryPK);
	}

	/**
	 * Returns <code>true</code> if the big decimal entry has any lv entries associated with it.
	 *
	 * @param pk the primary key of the big decimal entry to check for associations with lv entries
	 * @return <code>true</code> if the big decimal entry has any lv entries associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsLVEntries(long pk) {
		if (getLVEntriesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the big decimal entry and the lv entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntryPK the primary key of the lv entry
	 */
	@Override
	public void addLVEntry(long pk, long lvEntryPK) {
		BigDecimalEntry bigDecimalEntry = fetchByPrimaryKey(pk);

		if (bigDecimalEntry == null) {
			bigDecimalEntryToLVEntryTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, lvEntryPK);
		}
		else {
			bigDecimalEntryToLVEntryTableMapper.addTableMapping(
				bigDecimalEntry.getCompanyId(), pk, lvEntryPK);
		}
	}

	/**
	 * Adds an association between the big decimal entry and the lv entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntry the lv entry
	 */
	@Override
	public void addLVEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.LVEntry lvEntry) {

		BigDecimalEntry bigDecimalEntry = fetchByPrimaryKey(pk);

		if (bigDecimalEntry == null) {
			bigDecimalEntryToLVEntryTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, lvEntry.getPrimaryKey());
		}
		else {
			bigDecimalEntryToLVEntryTableMapper.addTableMapping(
				bigDecimalEntry.getCompanyId(), pk, lvEntry.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the big decimal entry and the lv entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntryPKs the primary keys of the lv entries
	 */
	@Override
	public void addLVEntries(long pk, long[] lvEntryPKs) {
		long companyId = 0;

		BigDecimalEntry bigDecimalEntry = fetchByPrimaryKey(pk);

		if (bigDecimalEntry == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = bigDecimalEntry.getCompanyId();
		}

		bigDecimalEntryToLVEntryTableMapper.addTableMappings(
			companyId, pk, lvEntryPKs);
	}

	/**
	 * Adds an association between the big decimal entry and the lv entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntries the lv entries
	 */
	@Override
	public void addLVEntries(
		long pk,
		List<com.liferay.portal.tools.service.builder.test.model.LVEntry>
			lvEntries) {

		addLVEntries(
			pk,
			ListUtil.toLongArray(
				lvEntries,
				com.liferay.portal.tools.service.builder.test.model.LVEntry.
					LV_ENTRY_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the big decimal entry and its lv entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry to clear the associated lv entries from
	 */
	@Override
	public void clearLVEntries(long pk) {
		bigDecimalEntryToLVEntryTableMapper.deleteLeftPrimaryKeyTableMappings(
			pk);
	}

	/**
	 * Removes the association between the big decimal entry and the lv entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntryPK the primary key of the lv entry
	 */
	@Override
	public void removeLVEntry(long pk, long lvEntryPK) {
		bigDecimalEntryToLVEntryTableMapper.deleteTableMapping(pk, lvEntryPK);
	}

	/**
	 * Removes the association between the big decimal entry and the lv entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntry the lv entry
	 */
	@Override
	public void removeLVEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.LVEntry lvEntry) {

		bigDecimalEntryToLVEntryTableMapper.deleteTableMapping(
			pk, lvEntry.getPrimaryKey());
	}

	/**
	 * Removes the association between the big decimal entry and the lv entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntryPKs the primary keys of the lv entries
	 */
	@Override
	public void removeLVEntries(long pk, long[] lvEntryPKs) {
		bigDecimalEntryToLVEntryTableMapper.deleteTableMappings(pk, lvEntryPKs);
	}

	/**
	 * Removes the association between the big decimal entry and the lv entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntries the lv entries
	 */
	@Override
	public void removeLVEntries(
		long pk,
		List<com.liferay.portal.tools.service.builder.test.model.LVEntry>
			lvEntries) {

		removeLVEntries(
			pk,
			ListUtil.toLongArray(
				lvEntries,
				com.liferay.portal.tools.service.builder.test.model.LVEntry.
					LV_ENTRY_ID_ACCESSOR));
	}

	/**
	 * Sets the lv entries associated with the big decimal entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntryPKs the primary keys of the lv entries to be associated with the big decimal entry
	 */
	@Override
	public void setLVEntries(long pk, long[] lvEntryPKs) {
		Set<Long> newLVEntryPKsSet = SetUtil.fromArray(lvEntryPKs);
		Set<Long> oldLVEntryPKsSet = SetUtil.fromArray(
			bigDecimalEntryToLVEntryTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeLVEntryPKsSet = new HashSet<Long>(oldLVEntryPKsSet);

		removeLVEntryPKsSet.removeAll(newLVEntryPKsSet);

		bigDecimalEntryToLVEntryTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeLVEntryPKsSet));

		newLVEntryPKsSet.removeAll(oldLVEntryPKsSet);

		long companyId = 0;

		BigDecimalEntry bigDecimalEntry = fetchByPrimaryKey(pk);

		if (bigDecimalEntry == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = bigDecimalEntry.getCompanyId();
		}

		bigDecimalEntryToLVEntryTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newLVEntryPKsSet));
	}

	/**
	 * Sets the lv entries associated with the big decimal entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the big decimal entry
	 * @param lvEntries the lv entries to be associated with the big decimal entry
	 */
	@Override
	public void setLVEntries(
		long pk,
		List<com.liferay.portal.tools.service.builder.test.model.LVEntry>
			lvEntries) {

		try {
			long[] lvEntryPKs = new long[lvEntries.size()];

			for (int i = 0; i < lvEntries.size(); i++) {
				com.liferay.portal.tools.service.builder.test.model.LVEntry
					lvEntry = lvEntries.get(i);

				lvEntryPKs[i] = lvEntry.getPrimaryKey();
			}

			setLVEntries(pk, lvEntryPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "bigDecimalEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_BIGDECIMALENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return BigDecimalEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the big decimal entry persistence.
	 */
	public void afterPropertiesSet() {
		bigDecimalEntryToLVEntryTableMapper = TableMapperFactory.getTableMapper(
			"BigDecimalEntries_LVEntries", "companyId", "bigDecimalEntryId",
			"lvEntryId", this, lvEntryPersistence);

		_finderPathWithPaginationFindAll = new FinderPath(
			BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryModelImpl.FINDER_CACHE_ENABLED,
			BigDecimalEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryModelImpl.FINDER_CACHE_ENABLED,
			BigDecimalEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByBigDecimalValue = new FinderPath(
			BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryModelImpl.FINDER_CACHE_ENABLED,
			BigDecimalEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByBigDecimalValue",
			new String[] {
				BigDecimal.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByBigDecimalValue = new FinderPath(
			BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryModelImpl.FINDER_CACHE_ENABLED,
			BigDecimalEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByBigDecimalValue",
			new String[] {BigDecimal.class.getName()},
			BigDecimalEntryModelImpl.BIGDECIMALVALUE_COLUMN_BITMASK);

		_finderPathCountByBigDecimalValue = new FinderPath(
			BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByBigDecimalValue",
			new String[] {BigDecimal.class.getName()});

		_finderPathWithPaginationFindByGtBigDecimalValue = new FinderPath(
			BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryModelImpl.FINDER_CACHE_ENABLED,
			BigDecimalEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGtBigDecimalValue",
			new String[] {
				BigDecimal.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByGtBigDecimalValue = new FinderPath(
			BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByGtBigDecimalValue",
			new String[] {BigDecimal.class.getName()});

		_finderPathWithPaginationFindByLtBigDecimalValue = new FinderPath(
			BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryModelImpl.FINDER_CACHE_ENABLED,
			BigDecimalEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByLtBigDecimalValue",
			new String[] {
				BigDecimal.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByLtBigDecimalValue = new FinderPath(
			BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtBigDecimalValue",
			new String[] {BigDecimal.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(BigDecimalEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper("BigDecimalEntries_LVEntries");
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	@BeanReference(type = LVEntryPersistence.class)
	protected LVEntryPersistence lvEntryPersistence;

	protected TableMapper
		<BigDecimalEntry,
		 com.liferay.portal.tools.service.builder.test.model.LVEntry>
			bigDecimalEntryToLVEntryTableMapper;

	private static final String _SQL_SELECT_BIGDECIMALENTRY =
		"SELECT bigDecimalEntry FROM BigDecimalEntry bigDecimalEntry";

	private static final String _SQL_SELECT_BIGDECIMALENTRY_WHERE =
		"SELECT bigDecimalEntry FROM BigDecimalEntry bigDecimalEntry WHERE ";

	private static final String _SQL_COUNT_BIGDECIMALENTRY =
		"SELECT COUNT(bigDecimalEntry) FROM BigDecimalEntry bigDecimalEntry";

	private static final String _SQL_COUNT_BIGDECIMALENTRY_WHERE =
		"SELECT COUNT(bigDecimalEntry) FROM BigDecimalEntry bigDecimalEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "bigDecimalEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No BigDecimalEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BigDecimalEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BigDecimalEntryPersistenceImpl.class);

}