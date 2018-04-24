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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchBigDecimalEntryException;
import com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry;
import com.liferay.portal.tools.service.builder.test.model.impl.BigDecimalEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.BigDecimalEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.BigDecimalEntryPersistence;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
 * @see BigDecimalEntryPersistence
 * @see com.liferay.portal.tools.service.builder.test.service.persistence.BigDecimalEntryUtil
 * @generated
 */
@ProviderType
public class BigDecimalEntryPersistenceImpl extends BasePersistenceImpl<BigDecimalEntry>
	implements BigDecimalEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link BigDecimalEntryUtil} to access the big decimal entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = BigDecimalEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryModelImpl.FINDER_CACHE_ENABLED,
			BigDecimalEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryModelImpl.FINDER_CACHE_ENABLED,
			BigDecimalEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_BIGDECIMALVALUE =
		new FinderPath(BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryModelImpl.FINDER_CACHE_ENABLED,
			BigDecimalEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByBigDecimalValue",
			new String[] {
				BigDecimal.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_BIGDECIMALVALUE =
		new FinderPath(BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryModelImpl.FINDER_CACHE_ENABLED,
			BigDecimalEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByBigDecimalValue",
			new String[] { BigDecimal.class.getName() },
			BigDecimalEntryModelImpl.BIGDECIMALVALUE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_BIGDECIMALVALUE = new FinderPath(BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByBigDecimalValue",
			new String[] { BigDecimal.class.getName() });

	/**
	 * Returns all the big decimal entries where bigDecimalValue = &#63;.
	 *
	 * @param bigDecimalValue the big decimal value
	 * @return the matching big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findByBigDecimalValue(
		BigDecimal bigDecimalValue) {
		return findByBigDecimalValue(bigDecimalValue, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the big decimal entries where bigDecimalValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return findByBigDecimalValue(bigDecimalValue, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the big decimal entries where bigDecimalValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findByBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end,
		OrderByComparator<BigDecimalEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_BIGDECIMALVALUE;
			finderArgs = new Object[] { bigDecimalValue };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_BIGDECIMALVALUE;
			finderArgs = new Object[] {
					bigDecimalValue,
					
					start, end, orderByComparator
				};
		}

		List<BigDecimalEntry> list = null;

		if (retrieveFromCache) {
			list = (List<BigDecimalEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BigDecimalEntry bigDecimalEntry : list) {
					if (!Objects.equals(bigDecimalValue,
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
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
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
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
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

				if (!pagination) {
					list = (List<BigDecimalEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BigDecimalEntry>)QueryUtil.list(q,
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
		BigDecimalEntry bigDecimalEntry = fetchByBigDecimalValue_First(bigDecimalValue,
				orderByComparator);

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
		List<BigDecimalEntry> list = findByBigDecimalValue(bigDecimalValue, 0,
				1, orderByComparator);

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
		BigDecimalEntry bigDecimalEntry = fetchByBigDecimalValue_Last(bigDecimalValue,
				orderByComparator);

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

		List<BigDecimalEntry> list = findByBigDecimalValue(bigDecimalValue,
				count - 1, count, orderByComparator);

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

			array[0] = getByBigDecimalValue_PrevAndNext(session,
					bigDecimalEntry, bigDecimalValue, orderByComparator, true);

			array[1] = bigDecimalEntry;

			array[2] = getByBigDecimalValue_PrevAndNext(session,
					bigDecimalEntry, bigDecimalValue, orderByComparator, false);

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
		OrderByComparator<BigDecimalEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
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
			Object[] values = orderByComparator.getOrderByConditionValues(bigDecimalEntry);

			for (Object value : values) {
				qPos.add(value);
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
		for (BigDecimalEntry bigDecimalEntry : findByBigDecimalValue(
				bigDecimalValue, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_BIGDECIMALVALUE;

		Object[] finderArgs = new Object[] { bigDecimalValue };

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

	private static final String _FINDER_COLUMN_BIGDECIMALVALUE_BIGDECIMALVALUE_1 =
		"bigDecimalEntry.bigDecimalValue IS NULL";
	private static final String _FINDER_COLUMN_BIGDECIMALVALUE_BIGDECIMALVALUE_2 =
		"bigDecimalEntry.bigDecimalValue = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GTBIGDECIMALVALUE =
		new FinderPath(BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryModelImpl.FINDER_CACHE_ENABLED,
			BigDecimalEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGtBigDecimalValue",
			new String[] {
				BigDecimal.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_GTBIGDECIMALVALUE =
		new FinderPath(BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByGtBigDecimalValue",
			new String[] { BigDecimal.class.getName() });

	/**
	 * Returns all the big decimal entries where bigDecimalValue &gt; &#63;.
	 *
	 * @param bigDecimalValue the big decimal value
	 * @return the matching big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findByGtBigDecimalValue(
		BigDecimal bigDecimalValue) {
		return findByGtBigDecimalValue(bigDecimalValue, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the big decimal entries where bigDecimalValue &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return findByGtBigDecimalValue(bigDecimalValue, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the big decimal entries where bigDecimalValue &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findByGtBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end,
		OrderByComparator<BigDecimalEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GTBIGDECIMALVALUE;
		finderArgs = new Object[] { bigDecimalValue, start, end, orderByComparator };

		List<BigDecimalEntry> list = null;

		if (retrieveFromCache) {
			list = (List<BigDecimalEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BigDecimalEntry bigDecimalEntry : list) {
					if ((bigDecimalValue.compareTo(
								bigDecimalEntry.getBigDecimalValue()) >= 0)) {
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
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
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

				if (!pagination) {
					list = (List<BigDecimalEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BigDecimalEntry>)QueryUtil.list(q,
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
		BigDecimalEntry bigDecimalEntry = fetchByGtBigDecimalValue_First(bigDecimalValue,
				orderByComparator);

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
		List<BigDecimalEntry> list = findByGtBigDecimalValue(bigDecimalValue,
				0, 1, orderByComparator);

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
		BigDecimalEntry bigDecimalEntry = fetchByGtBigDecimalValue_Last(bigDecimalValue,
				orderByComparator);

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

		List<BigDecimalEntry> list = findByGtBigDecimalValue(bigDecimalValue,
				count - 1, count, orderByComparator);

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

			array[0] = getByGtBigDecimalValue_PrevAndNext(session,
					bigDecimalEntry, bigDecimalValue, orderByComparator, true);

			array[1] = bigDecimalEntry;

			array[2] = getByGtBigDecimalValue_PrevAndNext(session,
					bigDecimalEntry, bigDecimalValue, orderByComparator, false);

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
		OrderByComparator<BigDecimalEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
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
			Object[] values = orderByComparator.getOrderByConditionValues(bigDecimalEntry);

			for (Object value : values) {
				qPos.add(value);
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
		for (BigDecimalEntry bigDecimalEntry : findByGtBigDecimalValue(
				bigDecimalValue, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_GTBIGDECIMALVALUE;

		Object[] finderArgs = new Object[] { bigDecimalValue };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BIGDECIMALENTRY_WHERE);

			boolean bindBigDecimalValue = false;

			if (bigDecimalValue == null) {
				query.append(_FINDER_COLUMN_GTBIGDECIMALVALUE_BIGDECIMALVALUE_1);
			}
			else {
				bindBigDecimalValue = true;

				query.append(_FINDER_COLUMN_GTBIGDECIMALVALUE_BIGDECIMALVALUE_2);
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

	private static final String _FINDER_COLUMN_GTBIGDECIMALVALUE_BIGDECIMALVALUE_1 =
		"bigDecimalEntry.bigDecimalValue IS NULL";
	private static final String _FINDER_COLUMN_GTBIGDECIMALVALUE_BIGDECIMALVALUE_2 =
		"bigDecimalEntry.bigDecimalValue > ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LTBIGDECIMALVALUE =
		new FinderPath(BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryModelImpl.FINDER_CACHE_ENABLED,
			BigDecimalEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByLtBigDecimalValue",
			new String[] {
				BigDecimal.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_LTBIGDECIMALVALUE =
		new FinderPath(BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtBigDecimalValue",
			new String[] { BigDecimal.class.getName() });

	/**
	 * Returns all the big decimal entries where bigDecimalValue &lt; &#63;.
	 *
	 * @param bigDecimalValue the big decimal value
	 * @return the matching big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findByLtBigDecimalValue(
		BigDecimal bigDecimalValue) {
		return findByLtBigDecimalValue(bigDecimalValue, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the big decimal entries where bigDecimalValue &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return findByLtBigDecimalValue(bigDecimalValue, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the big decimal entries where bigDecimalValue &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param bigDecimalValue the big decimal value
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findByLtBigDecimalValue(
		BigDecimal bigDecimalValue, int start, int end,
		OrderByComparator<BigDecimalEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LTBIGDECIMALVALUE;
		finderArgs = new Object[] { bigDecimalValue, start, end, orderByComparator };

		List<BigDecimalEntry> list = null;

		if (retrieveFromCache) {
			list = (List<BigDecimalEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BigDecimalEntry bigDecimalEntry : list) {
					if ((bigDecimalValue.compareTo(
								bigDecimalEntry.getBigDecimalValue()) <= 0)) {
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
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
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

				if (!pagination) {
					list = (List<BigDecimalEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BigDecimalEntry>)QueryUtil.list(q,
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
		BigDecimalEntry bigDecimalEntry = fetchByLtBigDecimalValue_First(bigDecimalValue,
				orderByComparator);

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
		List<BigDecimalEntry> list = findByLtBigDecimalValue(bigDecimalValue,
				0, 1, orderByComparator);

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
		BigDecimalEntry bigDecimalEntry = fetchByLtBigDecimalValue_Last(bigDecimalValue,
				orderByComparator);

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

		List<BigDecimalEntry> list = findByLtBigDecimalValue(bigDecimalValue,
				count - 1, count, orderByComparator);

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

			array[0] = getByLtBigDecimalValue_PrevAndNext(session,
					bigDecimalEntry, bigDecimalValue, orderByComparator, true);

			array[1] = bigDecimalEntry;

			array[2] = getByLtBigDecimalValue_PrevAndNext(session,
					bigDecimalEntry, bigDecimalValue, orderByComparator, false);

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
		OrderByComparator<BigDecimalEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
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
			Object[] values = orderByComparator.getOrderByConditionValues(bigDecimalEntry);

			for (Object value : values) {
				qPos.add(value);
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
		for (BigDecimalEntry bigDecimalEntry : findByLtBigDecimalValue(
				bigDecimalValue, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_LTBIGDECIMALVALUE;

		Object[] finderArgs = new Object[] { bigDecimalValue };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BIGDECIMALENTRY_WHERE);

			boolean bindBigDecimalValue = false;

			if (bigDecimalValue == null) {
				query.append(_FINDER_COLUMN_LTBIGDECIMALVALUE_BIGDECIMALVALUE_1);
			}
			else {
				bindBigDecimalValue = true;

				query.append(_FINDER_COLUMN_LTBIGDECIMALVALUE_BIGDECIMALVALUE_2);
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

	private static final String _FINDER_COLUMN_LTBIGDECIMALVALUE_BIGDECIMALVALUE_1 =
		"bigDecimalEntry.bigDecimalValue IS NULL";
	private static final String _FINDER_COLUMN_LTBIGDECIMALVALUE_BIGDECIMALVALUE_2 =
		"bigDecimalEntry.bigDecimalValue < ?";

	public BigDecimalEntryPersistenceImpl() {
		setModelClass(BigDecimalEntry.class);
	}

	/**
	 * Caches the big decimal entry in the entity cache if it is enabled.
	 *
	 * @param bigDecimalEntry the big decimal entry
	 */
	@Override
	public void cacheResult(BigDecimalEntry bigDecimalEntry) {
		entityCache.putResult(BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
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
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
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
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BigDecimalEntry bigDecimalEntry) {
		entityCache.removeResult(BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryImpl.class, bigDecimalEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<BigDecimalEntry> bigDecimalEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (BigDecimalEntry bigDecimalEntry : bigDecimalEntries) {
			entityCache.removeResult(BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
				BigDecimalEntryImpl.class, bigDecimalEntry.getPrimaryKey());
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

			BigDecimalEntry bigDecimalEntry = (BigDecimalEntry)session.get(BigDecimalEntryImpl.class,
					primaryKey);

			if (bigDecimalEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchBigDecimalEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
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
		bigDecimalEntry = toUnwrappedModel(bigDecimalEntry);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(bigDecimalEntry)) {
				bigDecimalEntry = (BigDecimalEntry)session.get(BigDecimalEntryImpl.class,
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
		bigDecimalEntry = toUnwrappedModel(bigDecimalEntry);

		boolean isNew = bigDecimalEntry.isNew();

		BigDecimalEntryModelImpl bigDecimalEntryModelImpl = (BigDecimalEntryModelImpl)bigDecimalEntry;

		Session session = null;

		try {
			session = openSession();

			if (bigDecimalEntry.isNew()) {
				session.save(bigDecimalEntry);

				bigDecimalEntry.setNew(false);
			}
			else {
				bigDecimalEntry = (BigDecimalEntry)session.merge(bigDecimalEntry);
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
		else
		 if (isNew) {
			Object[] args = new Object[] {
					bigDecimalEntryModelImpl.getBigDecimalValue()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_BIGDECIMALVALUE, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_BIGDECIMALVALUE,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((bigDecimalEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_BIGDECIMALVALUE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						bigDecimalEntryModelImpl.getOriginalBigDecimalValue()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_BIGDECIMALVALUE,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_BIGDECIMALVALUE,
					args);

				args = new Object[] {
						bigDecimalEntryModelImpl.getBigDecimalValue()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_BIGDECIMALVALUE,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_BIGDECIMALVALUE,
					args);
			}
		}

		entityCache.putResult(BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
			BigDecimalEntryImpl.class, bigDecimalEntry.getPrimaryKey(),
			bigDecimalEntry, false);

		bigDecimalEntry.resetOriginalValues();

		return bigDecimalEntry;
	}

	protected BigDecimalEntry toUnwrappedModel(BigDecimalEntry bigDecimalEntry) {
		if (bigDecimalEntry instanceof BigDecimalEntryImpl) {
			return bigDecimalEntry;
		}

		BigDecimalEntryImpl bigDecimalEntryImpl = new BigDecimalEntryImpl();

		bigDecimalEntryImpl.setNew(bigDecimalEntry.isNew());
		bigDecimalEntryImpl.setPrimaryKey(bigDecimalEntry.getPrimaryKey());

		bigDecimalEntryImpl.setBigDecimalEntryId(bigDecimalEntry.getBigDecimalEntryId());
		bigDecimalEntryImpl.setBigDecimalValue(bigDecimalEntry.getBigDecimalValue());

		return bigDecimalEntryImpl;
	}

	/**
	 * Returns the big decimal entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
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

			throw new NoSuchBigDecimalEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return bigDecimalEntry;
	}

	/**
	 * Returns the big decimal entry with the primary key or throws a {@link NoSuchBigDecimalEntryException} if it could not be found.
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
	 * @param primaryKey the primary key of the big decimal entry
	 * @return the big decimal entry, or <code>null</code> if a big decimal entry with the primary key could not be found
	 */
	@Override
	public BigDecimalEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
				BigDecimalEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		BigDecimalEntry bigDecimalEntry = (BigDecimalEntry)serializable;

		if (bigDecimalEntry == null) {
			Session session = null;

			try {
				session = openSession();

				bigDecimalEntry = (BigDecimalEntry)session.get(BigDecimalEntryImpl.class,
						primaryKey);

				if (bigDecimalEntry != null) {
					cacheResult(bigDecimalEntry);
				}
				else {
					entityCache.putResult(BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
						BigDecimalEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
					BigDecimalEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return bigDecimalEntry;
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

	@Override
	public Map<Serializable, BigDecimalEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, BigDecimalEntry> map = new HashMap<Serializable, BigDecimalEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			BigDecimalEntry bigDecimalEntry = fetchByPrimaryKey(primaryKey);

			if (bigDecimalEntry != null) {
				map.put(primaryKey, bigDecimalEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
					BigDecimalEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (BigDecimalEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_BIGDECIMALENTRY_WHERE_PKS_IN);

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

			for (BigDecimalEntry bigDecimalEntry : (List<BigDecimalEntry>)q.list()) {
				map.put(bigDecimalEntry.getPrimaryKeyObj(), bigDecimalEntry);

				cacheResult(bigDecimalEntry);

				uncachedPrimaryKeys.remove(bigDecimalEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(BigDecimalEntryModelImpl.ENTITY_CACHE_ENABLED,
					BigDecimalEntryImpl.class, primaryKey, nullModel);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findAll(int start, int end,
		OrderByComparator<BigDecimalEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the big decimal entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BigDecimalEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of big decimal entries
	 * @param end the upper bound of the range of big decimal entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of big decimal entries
	 */
	@Override
	public List<BigDecimalEntry> findAll(int start, int end,
		OrderByComparator<BigDecimalEntry> orderByComparator,
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

		List<BigDecimalEntry> list = null;

		if (retrieveFromCache) {
			list = (List<BigDecimalEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_BIGDECIMALENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_BIGDECIMALENTRY;

				if (pagination) {
					sql = sql.concat(BigDecimalEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<BigDecimalEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BigDecimalEntry>)QueryUtil.list(q,
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
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_BIGDECIMALENTRY);

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
		return BigDecimalEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the big decimal entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(BigDecimalEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_BIGDECIMALENTRY = "SELECT bigDecimalEntry FROM BigDecimalEntry bigDecimalEntry";
	private static final String _SQL_SELECT_BIGDECIMALENTRY_WHERE_PKS_IN = "SELECT bigDecimalEntry FROM BigDecimalEntry bigDecimalEntry WHERE bigDecimalEntryId IN (";
	private static final String _SQL_SELECT_BIGDECIMALENTRY_WHERE = "SELECT bigDecimalEntry FROM BigDecimalEntry bigDecimalEntry WHERE ";
	private static final String _SQL_COUNT_BIGDECIMALENTRY = "SELECT COUNT(bigDecimalEntry) FROM BigDecimalEntry bigDecimalEntry";
	private static final String _SQL_COUNT_BIGDECIMALENTRY_WHERE = "SELECT COUNT(bigDecimalEntry) FROM BigDecimalEntry bigDecimalEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "bigDecimalEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No BigDecimalEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No BigDecimalEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(BigDecimalEntryPersistenceImpl.class);
}