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

package com.liferay.commerce.forecast.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.forecast.exception.NoSuchForecastEntryException;
import com.liferay.commerce.forecast.model.CommerceForecastEntry;
import com.liferay.commerce.forecast.model.impl.CommerceForecastEntryImpl;
import com.liferay.commerce.forecast.model.impl.CommerceForecastEntryModelImpl;
import com.liferay.commerce.forecast.service.persistence.CommerceForecastEntryPersistence;

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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the commerce forecast entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceForecastEntryPersistence
 * @see com.liferay.commerce.forecast.service.persistence.CommerceForecastEntryUtil
 * @generated
 */
@ProviderType
public class CommerceForecastEntryPersistenceImpl extends BasePersistenceImpl<CommerceForecastEntry>
	implements CommerceForecastEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceForecastEntryUtil} to access the commerce forecast entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceForecastEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceForecastEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceForecastEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceForecastEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceForecastEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceForecastEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceForecastEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceForecastEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceForecastEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceForecastEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			CommerceForecastEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceForecastEntryModelImpl.DATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(CommerceForecastEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce forecast entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce forecast entries
	 */
	@Override
	public List<CommerceForecastEntry> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce forecast entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce forecast entries
	 * @param end the upper bound of the range of commerce forecast entries (not inclusive)
	 * @return the range of matching commerce forecast entries
	 */
	@Override
	public List<CommerceForecastEntry> findByCompanyId(long companyId,
		int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce forecast entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce forecast entries
	 * @param end the upper bound of the range of commerce forecast entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce forecast entries
	 */
	@Override
	public List<CommerceForecastEntry> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceForecastEntry> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce forecast entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce forecast entries
	 * @param end the upper bound of the range of commerce forecast entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce forecast entries
	 */
	@Override
	public List<CommerceForecastEntry> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceForecastEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<CommerceForecastEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceForecastEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceForecastEntry commerceForecastEntry : list) {
					if ((companyId != commerceForecastEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMMERCEFORECASTENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceForecastEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<CommerceForecastEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceForecastEntry>)QueryUtil.list(q,
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
	 * Returns the first commerce forecast entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce forecast entry
	 * @throws NoSuchForecastEntryException if a matching commerce forecast entry could not be found
	 */
	@Override
	public CommerceForecastEntry findByCompanyId_First(long companyId,
		OrderByComparator<CommerceForecastEntry> orderByComparator)
		throws NoSuchForecastEntryException {
		CommerceForecastEntry commerceForecastEntry = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (commerceForecastEntry != null) {
			return commerceForecastEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchForecastEntryException(msg.toString());
	}

	/**
	 * Returns the first commerce forecast entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce forecast entry, or <code>null</code> if a matching commerce forecast entry could not be found
	 */
	@Override
	public CommerceForecastEntry fetchByCompanyId_First(long companyId,
		OrderByComparator<CommerceForecastEntry> orderByComparator) {
		List<CommerceForecastEntry> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce forecast entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce forecast entry
	 * @throws NoSuchForecastEntryException if a matching commerce forecast entry could not be found
	 */
	@Override
	public CommerceForecastEntry findByCompanyId_Last(long companyId,
		OrderByComparator<CommerceForecastEntry> orderByComparator)
		throws NoSuchForecastEntryException {
		CommerceForecastEntry commerceForecastEntry = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (commerceForecastEntry != null) {
			return commerceForecastEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchForecastEntryException(msg.toString());
	}

	/**
	 * Returns the last commerce forecast entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce forecast entry, or <code>null</code> if a matching commerce forecast entry could not be found
	 */
	@Override
	public CommerceForecastEntry fetchByCompanyId_Last(long companyId,
		OrderByComparator<CommerceForecastEntry> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceForecastEntry> list = findByCompanyId(companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce forecast entries before and after the current commerce forecast entry in the ordered set where companyId = &#63;.
	 *
	 * @param commerceForecastEntryId the primary key of the current commerce forecast entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce forecast entry
	 * @throws NoSuchForecastEntryException if a commerce forecast entry with the primary key could not be found
	 */
	@Override
	public CommerceForecastEntry[] findByCompanyId_PrevAndNext(
		long commerceForecastEntryId, long companyId,
		OrderByComparator<CommerceForecastEntry> orderByComparator)
		throws NoSuchForecastEntryException {
		CommerceForecastEntry commerceForecastEntry = findByPrimaryKey(commerceForecastEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceForecastEntry[] array = new CommerceForecastEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session,
					commerceForecastEntry, companyId, orderByComparator, true);

			array[1] = commerceForecastEntry;

			array[2] = getByCompanyId_PrevAndNext(session,
					commerceForecastEntry, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceForecastEntry getByCompanyId_PrevAndNext(
		Session session, CommerceForecastEntry commerceForecastEntry,
		long companyId,
		OrderByComparator<CommerceForecastEntry> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEFORECASTENTRY_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(CommerceForecastEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceForecastEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceForecastEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce forecast entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceForecastEntry commerceForecastEntry : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceForecastEntry);
		}
	}

	/**
	 * Returns the number of commerce forecast entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce forecast entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEFORECASTENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "commerceForecastEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_P_T_C_S = new FinderPath(CommerceForecastEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceForecastEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_P_T_C_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			CommerceForecastEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceForecastEntryModelImpl.PERIOD_COLUMN_BITMASK |
			CommerceForecastEntryModelImpl.TARGET_COLUMN_BITMASK |
			CommerceForecastEntryModelImpl.CUSTOMERID_COLUMN_BITMASK |
			CommerceForecastEntryModelImpl.SKU_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_P_T_C_S = new FinderPath(CommerceForecastEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_P_T_C_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the commerce forecast entry where companyId = &#63; and period = &#63; and target = &#63; and customerId = &#63; and sku = &#63; or throws a {@link NoSuchForecastEntryException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param period the period
	 * @param target the target
	 * @param customerId the customer ID
	 * @param sku the sku
	 * @return the matching commerce forecast entry
	 * @throws NoSuchForecastEntryException if a matching commerce forecast entry could not be found
	 */
	@Override
	public CommerceForecastEntry findByC_P_T_C_S(long companyId, int period,
		int target, long customerId, String sku)
		throws NoSuchForecastEntryException {
		CommerceForecastEntry commerceForecastEntry = fetchByC_P_T_C_S(companyId,
				period, target, customerId, sku);

		if (commerceForecastEntry == null) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", period=");
			msg.append(period);

			msg.append(", target=");
			msg.append(target);

			msg.append(", customerId=");
			msg.append(customerId);

			msg.append(", sku=");
			msg.append(sku);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchForecastEntryException(msg.toString());
		}

		return commerceForecastEntry;
	}

	/**
	 * Returns the commerce forecast entry where companyId = &#63; and period = &#63; and target = &#63; and customerId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param period the period
	 * @param target the target
	 * @param customerId the customer ID
	 * @param sku the sku
	 * @return the matching commerce forecast entry, or <code>null</code> if a matching commerce forecast entry could not be found
	 */
	@Override
	public CommerceForecastEntry fetchByC_P_T_C_S(long companyId, int period,
		int target, long customerId, String sku) {
		return fetchByC_P_T_C_S(companyId, period, target, customerId, sku, true);
	}

	/**
	 * Returns the commerce forecast entry where companyId = &#63; and period = &#63; and target = &#63; and customerId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param period the period
	 * @param target the target
	 * @param customerId the customer ID
	 * @param sku the sku
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce forecast entry, or <code>null</code> if a matching commerce forecast entry could not be found
	 */
	@Override
	public CommerceForecastEntry fetchByC_P_T_C_S(long companyId, int period,
		int target, long customerId, String sku, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				companyId, period, target, customerId, sku
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_P_T_C_S,
					finderArgs, this);
		}

		if (result instanceof CommerceForecastEntry) {
			CommerceForecastEntry commerceForecastEntry = (CommerceForecastEntry)result;

			if ((companyId != commerceForecastEntry.getCompanyId()) ||
					(period != commerceForecastEntry.getPeriod()) ||
					(target != commerceForecastEntry.getTarget()) ||
					(customerId != commerceForecastEntry.getCustomerId()) ||
					!Objects.equals(sku, commerceForecastEntry.getSku())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_SELECT_COMMERCEFORECASTENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_P_T_C_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_T_C_S_PERIOD_2);

			query.append(_FINDER_COLUMN_C_P_T_C_S_TARGET_2);

			query.append(_FINDER_COLUMN_C_P_T_C_S_CUSTOMERID_2);

			boolean bindSku = false;

			if (sku == null) {
				query.append(_FINDER_COLUMN_C_P_T_C_S_SKU_1);
			}
			else if (sku.equals("")) {
				query.append(_FINDER_COLUMN_C_P_T_C_S_SKU_3);
			}
			else {
				bindSku = true;

				query.append(_FINDER_COLUMN_C_P_T_C_S_SKU_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(period);

				qPos.add(target);

				qPos.add(customerId);

				if (bindSku) {
					qPos.add(sku);
				}

				List<CommerceForecastEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_P_T_C_S,
						finderArgs, list);
				}
				else {
					CommerceForecastEntry commerceForecastEntry = list.get(0);

					result = commerceForecastEntry;

					cacheResult(commerceForecastEntry);

					if ((commerceForecastEntry.getCompanyId() != companyId) ||
							(commerceForecastEntry.getPeriod() != period) ||
							(commerceForecastEntry.getTarget() != target) ||
							(commerceForecastEntry.getCustomerId() != customerId) ||
							(commerceForecastEntry.getSku() == null) ||
							!commerceForecastEntry.getSku().equals(sku)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_P_T_C_S,
							finderArgs, commerceForecastEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_P_T_C_S,
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
			return (CommerceForecastEntry)result;
		}
	}

	/**
	 * Removes the commerce forecast entry where companyId = &#63; and period = &#63; and target = &#63; and customerId = &#63; and sku = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param period the period
	 * @param target the target
	 * @param customerId the customer ID
	 * @param sku the sku
	 * @return the commerce forecast entry that was removed
	 */
	@Override
	public CommerceForecastEntry removeByC_P_T_C_S(long companyId, int period,
		int target, long customerId, String sku)
		throws NoSuchForecastEntryException {
		CommerceForecastEntry commerceForecastEntry = findByC_P_T_C_S(companyId,
				period, target, customerId, sku);

		return remove(commerceForecastEntry);
	}

	/**
	 * Returns the number of commerce forecast entries where companyId = &#63; and period = &#63; and target = &#63; and customerId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param period the period
	 * @param target the target
	 * @param customerId the customer ID
	 * @param sku the sku
	 * @return the number of matching commerce forecast entries
	 */
	@Override
	public int countByC_P_T_C_S(long companyId, int period, int target,
		long customerId, String sku) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_P_T_C_S;

		Object[] finderArgs = new Object[] {
				companyId, period, target, customerId, sku
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_COMMERCEFORECASTENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_P_T_C_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_T_C_S_PERIOD_2);

			query.append(_FINDER_COLUMN_C_P_T_C_S_TARGET_2);

			query.append(_FINDER_COLUMN_C_P_T_C_S_CUSTOMERID_2);

			boolean bindSku = false;

			if (sku == null) {
				query.append(_FINDER_COLUMN_C_P_T_C_S_SKU_1);
			}
			else if (sku.equals("")) {
				query.append(_FINDER_COLUMN_C_P_T_C_S_SKU_3);
			}
			else {
				bindSku = true;

				query.append(_FINDER_COLUMN_C_P_T_C_S_SKU_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(period);

				qPos.add(target);

				qPos.add(customerId);

				if (bindSku) {
					qPos.add(sku);
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

	private static final String _FINDER_COLUMN_C_P_T_C_S_COMPANYID_2 = "commerceForecastEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_T_C_S_PERIOD_2 = "commerceForecastEntry.period = ? AND ";
	private static final String _FINDER_COLUMN_C_P_T_C_S_TARGET_2 = "commerceForecastEntry.target = ? AND ";
	private static final String _FINDER_COLUMN_C_P_T_C_S_CUSTOMERID_2 = "commerceForecastEntry.customerId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_T_C_S_SKU_1 = "commerceForecastEntry.sku IS NULL";
	private static final String _FINDER_COLUMN_C_P_T_C_S_SKU_2 = "commerceForecastEntry.sku = ?";
	private static final String _FINDER_COLUMN_C_P_T_C_S_SKU_3 = "(commerceForecastEntry.sku IS NULL OR commerceForecastEntry.sku = '')";

	public CommerceForecastEntryPersistenceImpl() {
		setModelClass(CommerceForecastEntry.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("date", "date_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the commerce forecast entry in the entity cache if it is enabled.
	 *
	 * @param commerceForecastEntry the commerce forecast entry
	 */
	@Override
	public void cacheResult(CommerceForecastEntry commerceForecastEntry) {
		entityCache.putResult(CommerceForecastEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastEntryImpl.class,
			commerceForecastEntry.getPrimaryKey(), commerceForecastEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_P_T_C_S,
			new Object[] {
				commerceForecastEntry.getCompanyId(),
				commerceForecastEntry.getPeriod(),
				commerceForecastEntry.getTarget(),
				commerceForecastEntry.getCustomerId(),
				commerceForecastEntry.getSku()
			}, commerceForecastEntry);

		commerceForecastEntry.resetOriginalValues();
	}

	/**
	 * Caches the commerce forecast entries in the entity cache if it is enabled.
	 *
	 * @param commerceForecastEntries the commerce forecast entries
	 */
	@Override
	public void cacheResult(List<CommerceForecastEntry> commerceForecastEntries) {
		for (CommerceForecastEntry commerceForecastEntry : commerceForecastEntries) {
			if (entityCache.getResult(
						CommerceForecastEntryModelImpl.ENTITY_CACHE_ENABLED,
						CommerceForecastEntryImpl.class,
						commerceForecastEntry.getPrimaryKey()) == null) {
				cacheResult(commerceForecastEntry);
			}
			else {
				commerceForecastEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce forecast entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceForecastEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce forecast entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceForecastEntry commerceForecastEntry) {
		entityCache.removeResult(CommerceForecastEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastEntryImpl.class,
			commerceForecastEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceForecastEntryModelImpl)commerceForecastEntry,
			true);
	}

	@Override
	public void clearCache(List<CommerceForecastEntry> commerceForecastEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceForecastEntry commerceForecastEntry : commerceForecastEntries) {
			entityCache.removeResult(CommerceForecastEntryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceForecastEntryImpl.class,
				commerceForecastEntry.getPrimaryKey());

			clearUniqueFindersCache((CommerceForecastEntryModelImpl)commerceForecastEntry,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceForecastEntryModelImpl commerceForecastEntryModelImpl) {
		Object[] args = new Object[] {
				commerceForecastEntryModelImpl.getCompanyId(),
				commerceForecastEntryModelImpl.getPeriod(),
				commerceForecastEntryModelImpl.getTarget(),
				commerceForecastEntryModelImpl.getCustomerId(),
				commerceForecastEntryModelImpl.getSku()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_P_T_C_S, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_P_T_C_S, args,
			commerceForecastEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceForecastEntryModelImpl commerceForecastEntryModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceForecastEntryModelImpl.getCompanyId(),
					commerceForecastEntryModelImpl.getPeriod(),
					commerceForecastEntryModelImpl.getTarget(),
					commerceForecastEntryModelImpl.getCustomerId(),
					commerceForecastEntryModelImpl.getSku()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_P_T_C_S, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_P_T_C_S, args);
		}

		if ((commerceForecastEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_P_T_C_S.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceForecastEntryModelImpl.getOriginalCompanyId(),
					commerceForecastEntryModelImpl.getOriginalPeriod(),
					commerceForecastEntryModelImpl.getOriginalTarget(),
					commerceForecastEntryModelImpl.getOriginalCustomerId(),
					commerceForecastEntryModelImpl.getOriginalSku()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_P_T_C_S, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_P_T_C_S, args);
		}
	}

	/**
	 * Creates a new commerce forecast entry with the primary key. Does not add the commerce forecast entry to the database.
	 *
	 * @param commerceForecastEntryId the primary key for the new commerce forecast entry
	 * @return the new commerce forecast entry
	 */
	@Override
	public CommerceForecastEntry create(long commerceForecastEntryId) {
		CommerceForecastEntry commerceForecastEntry = new CommerceForecastEntryImpl();

		commerceForecastEntry.setNew(true);
		commerceForecastEntry.setPrimaryKey(commerceForecastEntryId);

		commerceForecastEntry.setCompanyId(companyProvider.getCompanyId());

		return commerceForecastEntry;
	}

	/**
	 * Removes the commerce forecast entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceForecastEntryId the primary key of the commerce forecast entry
	 * @return the commerce forecast entry that was removed
	 * @throws NoSuchForecastEntryException if a commerce forecast entry with the primary key could not be found
	 */
	@Override
	public CommerceForecastEntry remove(long commerceForecastEntryId)
		throws NoSuchForecastEntryException {
		return remove((Serializable)commerceForecastEntryId);
	}

	/**
	 * Removes the commerce forecast entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce forecast entry
	 * @return the commerce forecast entry that was removed
	 * @throws NoSuchForecastEntryException if a commerce forecast entry with the primary key could not be found
	 */
	@Override
	public CommerceForecastEntry remove(Serializable primaryKey)
		throws NoSuchForecastEntryException {
		Session session = null;

		try {
			session = openSession();

			CommerceForecastEntry commerceForecastEntry = (CommerceForecastEntry)session.get(CommerceForecastEntryImpl.class,
					primaryKey);

			if (commerceForecastEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchForecastEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceForecastEntry);
		}
		catch (NoSuchForecastEntryException nsee) {
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
	protected CommerceForecastEntry removeImpl(
		CommerceForecastEntry commerceForecastEntry) {
		commerceForecastEntry = toUnwrappedModel(commerceForecastEntry);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceForecastEntry)) {
				commerceForecastEntry = (CommerceForecastEntry)session.get(CommerceForecastEntryImpl.class,
						commerceForecastEntry.getPrimaryKeyObj());
			}

			if (commerceForecastEntry != null) {
				session.delete(commerceForecastEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceForecastEntry != null) {
			clearCache(commerceForecastEntry);
		}

		return commerceForecastEntry;
	}

	@Override
	public CommerceForecastEntry updateImpl(
		CommerceForecastEntry commerceForecastEntry) {
		commerceForecastEntry = toUnwrappedModel(commerceForecastEntry);

		boolean isNew = commerceForecastEntry.isNew();

		CommerceForecastEntryModelImpl commerceForecastEntryModelImpl = (CommerceForecastEntryModelImpl)commerceForecastEntry;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceForecastEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceForecastEntry.setCreateDate(now);
			}
			else {
				commerceForecastEntry.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceForecastEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceForecastEntry.setModifiedDate(now);
			}
			else {
				commerceForecastEntry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceForecastEntry.isNew()) {
				session.save(commerceForecastEntry);

				commerceForecastEntry.setNew(false);
			}
			else {
				commerceForecastEntry = (CommerceForecastEntry)session.merge(commerceForecastEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceForecastEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceForecastEntryModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceForecastEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceForecastEntryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						commerceForecastEntryModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		entityCache.putResult(CommerceForecastEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastEntryImpl.class,
			commerceForecastEntry.getPrimaryKey(), commerceForecastEntry, false);

		clearUniqueFindersCache(commerceForecastEntryModelImpl, false);
		cacheUniqueFindersCache(commerceForecastEntryModelImpl);

		commerceForecastEntry.resetOriginalValues();

		return commerceForecastEntry;
	}

	protected CommerceForecastEntry toUnwrappedModel(
		CommerceForecastEntry commerceForecastEntry) {
		if (commerceForecastEntry instanceof CommerceForecastEntryImpl) {
			return commerceForecastEntry;
		}

		CommerceForecastEntryImpl commerceForecastEntryImpl = new CommerceForecastEntryImpl();

		commerceForecastEntryImpl.setNew(commerceForecastEntry.isNew());
		commerceForecastEntryImpl.setPrimaryKey(commerceForecastEntry.getPrimaryKey());

		commerceForecastEntryImpl.setCommerceForecastEntryId(commerceForecastEntry.getCommerceForecastEntryId());
		commerceForecastEntryImpl.setCompanyId(commerceForecastEntry.getCompanyId());
		commerceForecastEntryImpl.setUserId(commerceForecastEntry.getUserId());
		commerceForecastEntryImpl.setUserName(commerceForecastEntry.getUserName());
		commerceForecastEntryImpl.setCreateDate(commerceForecastEntry.getCreateDate());
		commerceForecastEntryImpl.setModifiedDate(commerceForecastEntry.getModifiedDate());
		commerceForecastEntryImpl.setDate(commerceForecastEntry.getDate());
		commerceForecastEntryImpl.setPeriod(commerceForecastEntry.getPeriod());
		commerceForecastEntryImpl.setTarget(commerceForecastEntry.getTarget());
		commerceForecastEntryImpl.setCustomerId(commerceForecastEntry.getCustomerId());
		commerceForecastEntryImpl.setSku(commerceForecastEntry.getSku());
		commerceForecastEntryImpl.setAssertivity(commerceForecastEntry.getAssertivity());

		return commerceForecastEntryImpl;
	}

	/**
	 * Returns the commerce forecast entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce forecast entry
	 * @return the commerce forecast entry
	 * @throws NoSuchForecastEntryException if a commerce forecast entry with the primary key could not be found
	 */
	@Override
	public CommerceForecastEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchForecastEntryException {
		CommerceForecastEntry commerceForecastEntry = fetchByPrimaryKey(primaryKey);

		if (commerceForecastEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchForecastEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceForecastEntry;
	}

	/**
	 * Returns the commerce forecast entry with the primary key or throws a {@link NoSuchForecastEntryException} if it could not be found.
	 *
	 * @param commerceForecastEntryId the primary key of the commerce forecast entry
	 * @return the commerce forecast entry
	 * @throws NoSuchForecastEntryException if a commerce forecast entry with the primary key could not be found
	 */
	@Override
	public CommerceForecastEntry findByPrimaryKey(long commerceForecastEntryId)
		throws NoSuchForecastEntryException {
		return findByPrimaryKey((Serializable)commerceForecastEntryId);
	}

	/**
	 * Returns the commerce forecast entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce forecast entry
	 * @return the commerce forecast entry, or <code>null</code> if a commerce forecast entry with the primary key could not be found
	 */
	@Override
	public CommerceForecastEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceForecastEntryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceForecastEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceForecastEntry commerceForecastEntry = (CommerceForecastEntry)serializable;

		if (commerceForecastEntry == null) {
			Session session = null;

			try {
				session = openSession();

				commerceForecastEntry = (CommerceForecastEntry)session.get(CommerceForecastEntryImpl.class,
						primaryKey);

				if (commerceForecastEntry != null) {
					cacheResult(commerceForecastEntry);
				}
				else {
					entityCache.putResult(CommerceForecastEntryModelImpl.ENTITY_CACHE_ENABLED,
						CommerceForecastEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceForecastEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceForecastEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceForecastEntry;
	}

	/**
	 * Returns the commerce forecast entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceForecastEntryId the primary key of the commerce forecast entry
	 * @return the commerce forecast entry, or <code>null</code> if a commerce forecast entry with the primary key could not be found
	 */
	@Override
	public CommerceForecastEntry fetchByPrimaryKey(long commerceForecastEntryId) {
		return fetchByPrimaryKey((Serializable)commerceForecastEntryId);
	}

	@Override
	public Map<Serializable, CommerceForecastEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceForecastEntry> map = new HashMap<Serializable, CommerceForecastEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceForecastEntry commerceForecastEntry = fetchByPrimaryKey(primaryKey);

			if (commerceForecastEntry != null) {
				map.put(primaryKey, commerceForecastEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceForecastEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceForecastEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceForecastEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEFORECASTENTRY_WHERE_PKS_IN);

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

			for (CommerceForecastEntry commerceForecastEntry : (List<CommerceForecastEntry>)q.list()) {
				map.put(commerceForecastEntry.getPrimaryKeyObj(),
					commerceForecastEntry);

				cacheResult(commerceForecastEntry);

				uncachedPrimaryKeys.remove(commerceForecastEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceForecastEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceForecastEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce forecast entries.
	 *
	 * @return the commerce forecast entries
	 */
	@Override
	public List<CommerceForecastEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce forecast entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce forecast entries
	 * @param end the upper bound of the range of commerce forecast entries (not inclusive)
	 * @return the range of commerce forecast entries
	 */
	@Override
	public List<CommerceForecastEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce forecast entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce forecast entries
	 * @param end the upper bound of the range of commerce forecast entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce forecast entries
	 */
	@Override
	public List<CommerceForecastEntry> findAll(int start, int end,
		OrderByComparator<CommerceForecastEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce forecast entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce forecast entries
	 * @param end the upper bound of the range of commerce forecast entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce forecast entries
	 */
	@Override
	public List<CommerceForecastEntry> findAll(int start, int end,
		OrderByComparator<CommerceForecastEntry> orderByComparator,
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

		List<CommerceForecastEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceForecastEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEFORECASTENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEFORECASTENTRY;

				if (pagination) {
					sql = sql.concat(CommerceForecastEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceForecastEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceForecastEntry>)QueryUtil.list(q,
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
	 * Removes all the commerce forecast entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceForecastEntry commerceForecastEntry : findAll()) {
			remove(commerceForecastEntry);
		}
	}

	/**
	 * Returns the number of commerce forecast entries.
	 *
	 * @return the number of commerce forecast entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEFORECASTENTRY);

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
		return CommerceForecastEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce forecast entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceForecastEntryImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEFORECASTENTRY = "SELECT commerceForecastEntry FROM CommerceForecastEntry commerceForecastEntry";
	private static final String _SQL_SELECT_COMMERCEFORECASTENTRY_WHERE_PKS_IN = "SELECT commerceForecastEntry FROM CommerceForecastEntry commerceForecastEntry WHERE commerceForecastEntryId IN (";
	private static final String _SQL_SELECT_COMMERCEFORECASTENTRY_WHERE = "SELECT commerceForecastEntry FROM CommerceForecastEntry commerceForecastEntry WHERE ";
	private static final String _SQL_COUNT_COMMERCEFORECASTENTRY = "SELECT COUNT(commerceForecastEntry) FROM CommerceForecastEntry commerceForecastEntry";
	private static final String _SQL_COUNT_COMMERCEFORECASTENTRY_WHERE = "SELECT COUNT(commerceForecastEntry) FROM CommerceForecastEntry commerceForecastEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceForecastEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceForecastEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceForecastEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceForecastEntryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"date"
			});
}