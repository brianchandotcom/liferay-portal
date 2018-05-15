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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionException;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionImpl;
import com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionModelImpl;
import com.liferay.commerce.shipping.engine.fixed.service.persistence.CommerceShippingFixedOptionPersistence;

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
 * The persistence implementation for the commerce shipping fixed option service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionPersistence
 * @see com.liferay.commerce.shipping.engine.fixed.service.persistence.CommerceShippingFixedOptionUtil
 * @generated
 */
@ProviderType
public class CommerceShippingFixedOptionPersistenceImpl
	extends BasePersistenceImpl<CommerceShippingFixedOption>
	implements CommerceShippingFixedOptionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceShippingFixedOptionUtil} to access the commerce shipping fixed option persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceShippingFixedOptionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceShippingFixedOptionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingFixedOptionModelImpl.FINDER_CACHE_ENABLED,
			CommerceShippingFixedOptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceShippingFixedOptionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingFixedOptionModelImpl.FINDER_CACHE_ENABLED,
			CommerceShippingFixedOptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceShippingFixedOptionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingFixedOptionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID =
		new FinderPath(CommerceShippingFixedOptionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingFixedOptionModelImpl.FINDER_CACHE_ENABLED,
			CommerceShippingFixedOptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceShippingMethodId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID =
		new FinderPath(CommerceShippingFixedOptionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingFixedOptionModelImpl.FINDER_CACHE_ENABLED,
			CommerceShippingFixedOptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceShippingMethodId",
			new String[] { Long.class.getName() },
			CommerceShippingFixedOptionModelImpl.COMMERCESHIPPINGMETHODID_COLUMN_BITMASK |
			CommerceShippingFixedOptionModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCESHIPPINGMETHODID =
		new FinderPath(CommerceShippingFixedOptionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingFixedOptionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceShippingMethodId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce shipping fixed options where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @return the matching commerce shipping fixed options
	 */
	@Override
	public List<CommerceShippingFixedOption> findByCommerceShippingMethodId(
		long commerceShippingMethodId) {
		return findByCommerceShippingMethodId(commerceShippingMethodId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipping fixed options where commerceShippingMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return findByCommerceShippingMethodId(commerceShippingMethodId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed options where commerceShippingMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return findByCommerceShippingMethodId(commerceShippingMethodId, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed options where commerceShippingMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param start the lower bound of the range of commerce shipping fixed options
	 * @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce shipping fixed options
	 */
	@Override
	public List<CommerceShippingFixedOption> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID;
			finderArgs = new Object[] { commerceShippingMethodId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID;
			finderArgs = new Object[] {
					commerceShippingMethodId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceShippingFixedOption> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceShippingFixedOption>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceShippingFixedOption commerceShippingFixedOption : list) {
					if ((commerceShippingMethodId != commerceShippingFixedOption.getCommerceShippingMethodId())) {
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

			query.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTION_WHERE);

			query.append(_FINDER_COLUMN_COMMERCESHIPPINGMETHODID_COMMERCESHIPPINGMETHODID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceShippingFixedOptionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceShippingMethodId);

				if (!pagination) {
					list = (List<CommerceShippingFixedOption>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceShippingFixedOption>)QueryUtil.list(q,
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
		CommerceShippingFixedOption commerceShippingFixedOption = fetchByCommerceShippingMethodId_First(commerceShippingMethodId,
				orderByComparator);

		if (commerceShippingFixedOption != null) {
			return commerceShippingFixedOption;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceShippingMethodId=");
		msg.append(commerceShippingMethodId);

		msg.append("}");

		throw new NoSuchShippingFixedOptionException(msg.toString());
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
		List<CommerceShippingFixedOption> list = findByCommerceShippingMethodId(commerceShippingMethodId,
				0, 1, orderByComparator);

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
		CommerceShippingFixedOption commerceShippingFixedOption = fetchByCommerceShippingMethodId_Last(commerceShippingMethodId,
				orderByComparator);

		if (commerceShippingFixedOption != null) {
			return commerceShippingFixedOption;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceShippingMethodId=");
		msg.append(commerceShippingMethodId);

		msg.append("}");

		throw new NoSuchShippingFixedOptionException(msg.toString());
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

		List<CommerceShippingFixedOption> list = findByCommerceShippingMethodId(commerceShippingMethodId,
				count - 1, count, orderByComparator);

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
	public CommerceShippingFixedOption[] findByCommerceShippingMethodId_PrevAndNext(
		long commerceShippingFixedOptionId, long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator)
		throws NoSuchShippingFixedOptionException {
		CommerceShippingFixedOption commerceShippingFixedOption = findByPrimaryKey(commerceShippingFixedOptionId);

		Session session = null;

		try {
			session = openSession();

			CommerceShippingFixedOption[] array = new CommerceShippingFixedOptionImpl[3];

			array[0] = getByCommerceShippingMethodId_PrevAndNext(session,
					commerceShippingFixedOption, commerceShippingMethodId,
					orderByComparator, true);

			array[1] = commerceShippingFixedOption;

			array[2] = getByCommerceShippingMethodId_PrevAndNext(session,
					commerceShippingFixedOption, commerceShippingMethodId,
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

	protected CommerceShippingFixedOption getByCommerceShippingMethodId_PrevAndNext(
		Session session,
		CommerceShippingFixedOption commerceShippingFixedOption,
		long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTION_WHERE);

		query.append(_FINDER_COLUMN_COMMERCESHIPPINGMETHODID_COMMERCESHIPPINGMETHODID_2);

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
			query.append(CommerceShippingFixedOptionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceShippingMethodId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceShippingFixedOption);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceShippingFixedOption> list = q.list();

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
	public void removeByCommerceShippingMethodId(long commerceShippingMethodId) {
		for (CommerceShippingFixedOption commerceShippingFixedOption : findByCommerceShippingMethodId(
				commerceShippingMethodId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCESHIPPINGMETHODID;

		Object[] finderArgs = new Object[] { commerceShippingMethodId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCESHIPPINGFIXEDOPTION_WHERE);

			query.append(_FINDER_COLUMN_COMMERCESHIPPINGMETHODID_COMMERCESHIPPINGMETHODID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceShippingMethodId);

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

	private static final String _FINDER_COLUMN_COMMERCESHIPPINGMETHODID_COMMERCESHIPPINGMETHODID_2 =
		"commerceShippingFixedOption.commerceShippingMethodId = ?";

	public CommerceShippingFixedOptionPersistenceImpl() {
		setModelClass(CommerceShippingFixedOption.class);
	}

	/**
	 * Caches the commerce shipping fixed option in the entity cache if it is enabled.
	 *
	 * @param commerceShippingFixedOption the commerce shipping fixed option
	 */
	@Override
	public void cacheResult(
		CommerceShippingFixedOption commerceShippingFixedOption) {
		entityCache.putResult(CommerceShippingFixedOptionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingFixedOptionImpl.class,
			commerceShippingFixedOption.getPrimaryKey(),
			commerceShippingFixedOption);

		commerceShippingFixedOption.resetOriginalValues();
	}

	/**
	 * Caches the commerce shipping fixed options in the entity cache if it is enabled.
	 *
	 * @param commerceShippingFixedOptions the commerce shipping fixed options
	 */
	@Override
	public void cacheResult(
		List<CommerceShippingFixedOption> commerceShippingFixedOptions) {
		for (CommerceShippingFixedOption commerceShippingFixedOption : commerceShippingFixedOptions) {
			if (entityCache.getResult(
						CommerceShippingFixedOptionModelImpl.ENTITY_CACHE_ENABLED,
						CommerceShippingFixedOptionImpl.class,
						commerceShippingFixedOption.getPrimaryKey()) == null) {
				cacheResult(commerceShippingFixedOption);
			}
			else {
				commerceShippingFixedOption.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce shipping fixed options.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceShippingFixedOptionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce shipping fixed option.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceShippingFixedOption commerceShippingFixedOption) {
		entityCache.removeResult(CommerceShippingFixedOptionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingFixedOptionImpl.class,
			commerceShippingFixedOption.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<CommerceShippingFixedOption> commerceShippingFixedOptions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceShippingFixedOption commerceShippingFixedOption : commerceShippingFixedOptions) {
			entityCache.removeResult(CommerceShippingFixedOptionModelImpl.ENTITY_CACHE_ENABLED,
				CommerceShippingFixedOptionImpl.class,
				commerceShippingFixedOption.getPrimaryKey());
		}
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
		CommerceShippingFixedOption commerceShippingFixedOption = new CommerceShippingFixedOptionImpl();

		commerceShippingFixedOption.setNew(true);
		commerceShippingFixedOption.setPrimaryKey(commerceShippingFixedOptionId);

		commerceShippingFixedOption.setCompanyId(companyProvider.getCompanyId());

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

			CommerceShippingFixedOption commerceShippingFixedOption = (CommerceShippingFixedOption)session.get(CommerceShippingFixedOptionImpl.class,
					primaryKey);

			if (commerceShippingFixedOption == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchShippingFixedOptionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceShippingFixedOption);
		}
		catch (NoSuchShippingFixedOptionException nsee) {
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
	protected CommerceShippingFixedOption removeImpl(
		CommerceShippingFixedOption commerceShippingFixedOption) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceShippingFixedOption)) {
				commerceShippingFixedOption = (CommerceShippingFixedOption)session.get(CommerceShippingFixedOptionImpl.class,
						commerceShippingFixedOption.getPrimaryKeyObj());
			}

			if (commerceShippingFixedOption != null) {
				session.delete(commerceShippingFixedOption);
			}
		}
		catch (Exception e) {
			throw processException(e);
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

		if (!(commerceShippingFixedOption instanceof CommerceShippingFixedOptionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceShippingFixedOption.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(commerceShippingFixedOption);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceShippingFixedOption proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceShippingFixedOption implementation " +
				commerceShippingFixedOption.getClass());
		}

		CommerceShippingFixedOptionModelImpl commerceShippingFixedOptionModelImpl =
			(CommerceShippingFixedOptionModelImpl)commerceShippingFixedOption;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceShippingFixedOption.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceShippingFixedOption.setCreateDate(now);
			}
			else {
				commerceShippingFixedOption.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceShippingFixedOptionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceShippingFixedOption.setModifiedDate(now);
			}
			else {
				commerceShippingFixedOption.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceShippingFixedOption.isNew()) {
				session.save(commerceShippingFixedOption);

				commerceShippingFixedOption.setNew(false);
			}
			else {
				commerceShippingFixedOption = (CommerceShippingFixedOption)session.merge(commerceShippingFixedOption);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceShippingFixedOptionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceShippingFixedOptionModelImpl.getCommerceShippingMethodId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCESHIPPINGMETHODID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceShippingFixedOptionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceShippingFixedOptionModelImpl.getOriginalCommerceShippingMethodId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCESHIPPINGMETHODID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID,
					args);

				args = new Object[] {
						commerceShippingFixedOptionModelImpl.getCommerceShippingMethodId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCESHIPPINGMETHODID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCESHIPPINGMETHODID,
					args);
			}
		}

		entityCache.putResult(CommerceShippingFixedOptionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingFixedOptionImpl.class,
			commerceShippingFixedOption.getPrimaryKey(),
			commerceShippingFixedOption, false);

		commerceShippingFixedOption.resetOriginalValues();

		return commerceShippingFixedOption;
	}

	/**
	 * Returns the commerce shipping fixed option with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce shipping fixed option
	 * @return the commerce shipping fixed option
	 * @throws NoSuchShippingFixedOptionException if a commerce shipping fixed option with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOption findByPrimaryKey(Serializable primaryKey)
		throws NoSuchShippingFixedOptionException {
		CommerceShippingFixedOption commerceShippingFixedOption = fetchByPrimaryKey(primaryKey);

		if (commerceShippingFixedOption == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchShippingFixedOptionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceShippingFixedOption;
	}

	/**
	 * Returns the commerce shipping fixed option with the primary key or throws a {@link NoSuchShippingFixedOptionException} if it could not be found.
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
	 * @param primaryKey the primary key of the commerce shipping fixed option
	 * @return the commerce shipping fixed option, or <code>null</code> if a commerce shipping fixed option with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOption fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceShippingFixedOptionModelImpl.ENTITY_CACHE_ENABLED,
				CommerceShippingFixedOptionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceShippingFixedOption commerceShippingFixedOption = (CommerceShippingFixedOption)serializable;

		if (commerceShippingFixedOption == null) {
			Session session = null;

			try {
				session = openSession();

				commerceShippingFixedOption = (CommerceShippingFixedOption)session.get(CommerceShippingFixedOptionImpl.class,
						primaryKey);

				if (commerceShippingFixedOption != null) {
					cacheResult(commerceShippingFixedOption);
				}
				else {
					entityCache.putResult(CommerceShippingFixedOptionModelImpl.ENTITY_CACHE_ENABLED,
						CommerceShippingFixedOptionImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceShippingFixedOptionModelImpl.ENTITY_CACHE_ENABLED,
					CommerceShippingFixedOptionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceShippingFixedOption;
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

	@Override
	public Map<Serializable, CommerceShippingFixedOption> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceShippingFixedOption> map = new HashMap<Serializable, CommerceShippingFixedOption>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceShippingFixedOption commerceShippingFixedOption = fetchByPrimaryKey(primaryKey);

			if (commerceShippingFixedOption != null) {
				map.put(primaryKey, commerceShippingFixedOption);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceShippingFixedOptionModelImpl.ENTITY_CACHE_ENABLED,
					CommerceShippingFixedOptionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(CommerceShippingFixedOption)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTION_WHERE_PKS_IN);

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

			for (CommerceShippingFixedOption commerceShippingFixedOption : (List<CommerceShippingFixedOption>)q.list()) {
				map.put(commerceShippingFixedOption.getPrimaryKeyObj(),
					commerceShippingFixedOption);

				cacheResult(commerceShippingFixedOption);

				uncachedPrimaryKeys.remove(commerceShippingFixedOption.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceShippingFixedOptionModelImpl.ENTITY_CACHE_ENABLED,
					CommerceShippingFixedOptionImpl.class, primaryKey, nullModel);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed options
	 * @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce shipping fixed options
	 */
	@Override
	public List<CommerceShippingFixedOption> findAll(int start, int end,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed options.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed options
	 * @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce shipping fixed options
	 */
	@Override
	public List<CommerceShippingFixedOption> findAll(int start, int end,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator,
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

		List<CommerceShippingFixedOption> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceShippingFixedOption>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCESHIPPINGFIXEDOPTION;

				if (pagination) {
					sql = sql.concat(CommerceShippingFixedOptionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceShippingFixedOption>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceShippingFixedOption>)QueryUtil.list(q,
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
	 * Removes all the commerce shipping fixed options from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceShippingFixedOption commerceShippingFixedOption : findAll()) {
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
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCESHIPPINGFIXEDOPTION);

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
		return CommerceShippingFixedOptionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce shipping fixed option persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceShippingFixedOptionImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCESHIPPINGFIXEDOPTION = "SELECT commerceShippingFixedOption FROM CommerceShippingFixedOption commerceShippingFixedOption";
	private static final String _SQL_SELECT_COMMERCESHIPPINGFIXEDOPTION_WHERE_PKS_IN =
		"SELECT commerceShippingFixedOption FROM CommerceShippingFixedOption commerceShippingFixedOption WHERE commerceShippingFixedOptionId IN (";
	private static final String _SQL_SELECT_COMMERCESHIPPINGFIXEDOPTION_WHERE = "SELECT commerceShippingFixedOption FROM CommerceShippingFixedOption commerceShippingFixedOption WHERE ";
	private static final String _SQL_COUNT_COMMERCESHIPPINGFIXEDOPTION = "SELECT COUNT(commerceShippingFixedOption) FROM CommerceShippingFixedOption commerceShippingFixedOption";
	private static final String _SQL_COUNT_COMMERCESHIPPINGFIXEDOPTION_WHERE = "SELECT COUNT(commerceShippingFixedOption) FROM CommerceShippingFixedOption commerceShippingFixedOption WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceShippingFixedOption.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceShippingFixedOption exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceShippingFixedOption exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceShippingFixedOptionPersistenceImpl.class);
}