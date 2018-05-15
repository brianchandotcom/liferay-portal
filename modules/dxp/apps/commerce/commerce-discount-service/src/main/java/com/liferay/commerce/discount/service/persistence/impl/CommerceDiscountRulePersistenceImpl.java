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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.discount.exception.NoSuchDiscountRuleException;
import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.model.impl.CommerceDiscountRuleImpl;
import com.liferay.commerce.discount.model.impl.CommerceDiscountRuleModelImpl;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountRulePersistence;

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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
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
 * The persistence implementation for the commerce discount rule service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountRulePersistence
 * @see com.liferay.commerce.discount.service.persistence.CommerceDiscountRuleUtil
 * @generated
 */
@ProviderType
public class CommerceDiscountRulePersistenceImpl extends BasePersistenceImpl<CommerceDiscountRule>
	implements CommerceDiscountRulePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceDiscountRuleUtil} to access the commerce discount rule persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceDiscountRuleImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceDiscountRuleModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountRuleModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountRuleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceDiscountRuleModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountRuleModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountRuleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceDiscountRuleModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountRuleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEDISCOUNTID =
		new FinderPath(CommerceDiscountRuleModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountRuleModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountRuleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceDiscountId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEDISCOUNTID =
		new FinderPath(CommerceDiscountRuleModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountRuleModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountRuleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceDiscountId", new String[] { Long.class.getName() },
			CommerceDiscountRuleModelImpl.COMMERCEDISCOUNTID_COLUMN_BITMASK |
			CommerceDiscountRuleModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEDISCOUNTID = new FinderPath(CommerceDiscountRuleModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountRuleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceDiscountId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce discount rules where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount rules
	 */
	@Override
	public List<CommerceDiscountRule> findByCommerceDiscountId(
		long commerceDiscountId) {
		return findByCommerceDiscountId(commerceDiscountId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount rules where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount rules
	 * @param end the upper bound of the range of commerce discount rules (not inclusive)
	 * @return the range of matching commerce discount rules
	 */
	@Override
	public List<CommerceDiscountRule> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end) {
		return findByCommerceDiscountId(commerceDiscountId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount rules where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount rules
	 * @param end the upper bound of the range of commerce discount rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount rules
	 */
	@Override
	public List<CommerceDiscountRule> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountRule> orderByComparator) {
		return findByCommerceDiscountId(commerceDiscountId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount rules where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount rules
	 * @param end the upper bound of the range of commerce discount rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce discount rules
	 */
	@Override
	public List<CommerceDiscountRule> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountRule> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEDISCOUNTID;
			finderArgs = new Object[] { commerceDiscountId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEDISCOUNTID;
			finderArgs = new Object[] {
					commerceDiscountId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceDiscountRule> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceDiscountRule>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDiscountRule commerceDiscountRule : list) {
					if ((commerceDiscountId != commerceDiscountRule.getCommerceDiscountId())) {
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

			query.append(_SQL_SELECT_COMMERCEDISCOUNTRULE_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEDISCOUNTID_COMMERCEDISCOUNTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceDiscountRuleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceDiscountId);

				if (!pagination) {
					list = (List<CommerceDiscountRule>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceDiscountRule>)QueryUtil.list(q,
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
	 * Returns the first commerce discount rule in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount rule
	 * @throws NoSuchDiscountRuleException if a matching commerce discount rule could not be found
	 */
	@Override
	public CommerceDiscountRule findByCommerceDiscountId_First(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountRule> orderByComparator)
		throws NoSuchDiscountRuleException {
		CommerceDiscountRule commerceDiscountRule = fetchByCommerceDiscountId_First(commerceDiscountId,
				orderByComparator);

		if (commerceDiscountRule != null) {
			return commerceDiscountRule;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceDiscountId=");
		msg.append(commerceDiscountId);

		msg.append("}");

		throw new NoSuchDiscountRuleException(msg.toString());
	}

	/**
	 * Returns the first commerce discount rule in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount rule, or <code>null</code> if a matching commerce discount rule could not be found
	 */
	@Override
	public CommerceDiscountRule fetchByCommerceDiscountId_First(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountRule> orderByComparator) {
		List<CommerceDiscountRule> list = findByCommerceDiscountId(commerceDiscountId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce discount rule in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount rule
	 * @throws NoSuchDiscountRuleException if a matching commerce discount rule could not be found
	 */
	@Override
	public CommerceDiscountRule findByCommerceDiscountId_Last(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountRule> orderByComparator)
		throws NoSuchDiscountRuleException {
		CommerceDiscountRule commerceDiscountRule = fetchByCommerceDiscountId_Last(commerceDiscountId,
				orderByComparator);

		if (commerceDiscountRule != null) {
			return commerceDiscountRule;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceDiscountId=");
		msg.append(commerceDiscountId);

		msg.append("}");

		throw new NoSuchDiscountRuleException(msg.toString());
	}

	/**
	 * Returns the last commerce discount rule in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount rule, or <code>null</code> if a matching commerce discount rule could not be found
	 */
	@Override
	public CommerceDiscountRule fetchByCommerceDiscountId_Last(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountRule> orderByComparator) {
		int count = countByCommerceDiscountId(commerceDiscountId);

		if (count == 0) {
			return null;
		}

		List<CommerceDiscountRule> list = findByCommerceDiscountId(commerceDiscountId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce discount rules before and after the current commerce discount rule in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountRuleId the primary key of the current commerce discount rule
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount rule
	 * @throws NoSuchDiscountRuleException if a commerce discount rule with the primary key could not be found
	 */
	@Override
	public CommerceDiscountRule[] findByCommerceDiscountId_PrevAndNext(
		long commerceDiscountRuleId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountRule> orderByComparator)
		throws NoSuchDiscountRuleException {
		CommerceDiscountRule commerceDiscountRule = findByPrimaryKey(commerceDiscountRuleId);

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountRule[] array = new CommerceDiscountRuleImpl[3];

			array[0] = getByCommerceDiscountId_PrevAndNext(session,
					commerceDiscountRule, commerceDiscountId,
					orderByComparator, true);

			array[1] = commerceDiscountRule;

			array[2] = getByCommerceDiscountId_PrevAndNext(session,
					commerceDiscountRule, commerceDiscountId,
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

	protected CommerceDiscountRule getByCommerceDiscountId_PrevAndNext(
		Session session, CommerceDiscountRule commerceDiscountRule,
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountRule> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEDISCOUNTRULE_WHERE);

		query.append(_FINDER_COLUMN_COMMERCEDISCOUNTID_COMMERCEDISCOUNTID_2);

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
			query.append(CommerceDiscountRuleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceDiscountId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceDiscountRule);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceDiscountRule> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce discount rules where commerceDiscountId = &#63; from the database.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 */
	@Override
	public void removeByCommerceDiscountId(long commerceDiscountId) {
		for (CommerceDiscountRule commerceDiscountRule : findByCommerceDiscountId(
				commerceDiscountId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceDiscountRule);
		}
	}

	/**
	 * Returns the number of commerce discount rules where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the number of matching commerce discount rules
	 */
	@Override
	public int countByCommerceDiscountId(long commerceDiscountId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEDISCOUNTID;

		Object[] finderArgs = new Object[] { commerceDiscountId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEDISCOUNTRULE_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEDISCOUNTID_COMMERCEDISCOUNTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceDiscountId);

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

	private static final String _FINDER_COLUMN_COMMERCEDISCOUNTID_COMMERCEDISCOUNTID_2 =
		"commerceDiscountRule.commerceDiscountId = ?";

	public CommerceDiscountRulePersistenceImpl() {
		setModelClass(CommerceDiscountRule.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("type", "type_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the commerce discount rule in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountRule the commerce discount rule
	 */
	@Override
	public void cacheResult(CommerceDiscountRule commerceDiscountRule) {
		entityCache.putResult(CommerceDiscountRuleModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountRuleImpl.class,
			commerceDiscountRule.getPrimaryKey(), commerceDiscountRule);

		commerceDiscountRule.resetOriginalValues();
	}

	/**
	 * Caches the commerce discount rules in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountRules the commerce discount rules
	 */
	@Override
	public void cacheResult(List<CommerceDiscountRule> commerceDiscountRules) {
		for (CommerceDiscountRule commerceDiscountRule : commerceDiscountRules) {
			if (entityCache.getResult(
						CommerceDiscountRuleModelImpl.ENTITY_CACHE_ENABLED,
						CommerceDiscountRuleImpl.class,
						commerceDiscountRule.getPrimaryKey()) == null) {
				cacheResult(commerceDiscountRule);
			}
			else {
				commerceDiscountRule.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce discount rules.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceDiscountRuleImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce discount rule.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceDiscountRule commerceDiscountRule) {
		entityCache.removeResult(CommerceDiscountRuleModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountRuleImpl.class, commerceDiscountRule.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CommerceDiscountRule> commerceDiscountRules) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceDiscountRule commerceDiscountRule : commerceDiscountRules) {
			entityCache.removeResult(CommerceDiscountRuleModelImpl.ENTITY_CACHE_ENABLED,
				CommerceDiscountRuleImpl.class,
				commerceDiscountRule.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce discount rule with the primary key. Does not add the commerce discount rule to the database.
	 *
	 * @param commerceDiscountRuleId the primary key for the new commerce discount rule
	 * @return the new commerce discount rule
	 */
	@Override
	public CommerceDiscountRule create(long commerceDiscountRuleId) {
		CommerceDiscountRule commerceDiscountRule = new CommerceDiscountRuleImpl();

		commerceDiscountRule.setNew(true);
		commerceDiscountRule.setPrimaryKey(commerceDiscountRuleId);

		commerceDiscountRule.setCompanyId(companyProvider.getCompanyId());

		return commerceDiscountRule;
	}

	/**
	 * Removes the commerce discount rule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDiscountRuleId the primary key of the commerce discount rule
	 * @return the commerce discount rule that was removed
	 * @throws NoSuchDiscountRuleException if a commerce discount rule with the primary key could not be found
	 */
	@Override
	public CommerceDiscountRule remove(long commerceDiscountRuleId)
		throws NoSuchDiscountRuleException {
		return remove((Serializable)commerceDiscountRuleId);
	}

	/**
	 * Removes the commerce discount rule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce discount rule
	 * @return the commerce discount rule that was removed
	 * @throws NoSuchDiscountRuleException if a commerce discount rule with the primary key could not be found
	 */
	@Override
	public CommerceDiscountRule remove(Serializable primaryKey)
		throws NoSuchDiscountRuleException {
		Session session = null;

		try {
			session = openSession();

			CommerceDiscountRule commerceDiscountRule = (CommerceDiscountRule)session.get(CommerceDiscountRuleImpl.class,
					primaryKey);

			if (commerceDiscountRule == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDiscountRuleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceDiscountRule);
		}
		catch (NoSuchDiscountRuleException nsee) {
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
	protected CommerceDiscountRule removeImpl(
		CommerceDiscountRule commerceDiscountRule) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceDiscountRule)) {
				commerceDiscountRule = (CommerceDiscountRule)session.get(CommerceDiscountRuleImpl.class,
						commerceDiscountRule.getPrimaryKeyObj());
			}

			if (commerceDiscountRule != null) {
				session.delete(commerceDiscountRule);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceDiscountRule != null) {
			clearCache(commerceDiscountRule);
		}

		return commerceDiscountRule;
	}

	@Override
	public CommerceDiscountRule updateImpl(
		CommerceDiscountRule commerceDiscountRule) {
		boolean isNew = commerceDiscountRule.isNew();

		if (!(commerceDiscountRule instanceof CommerceDiscountRuleModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceDiscountRule.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(commerceDiscountRule);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceDiscountRule proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceDiscountRule implementation " +
				commerceDiscountRule.getClass());
		}

		CommerceDiscountRuleModelImpl commerceDiscountRuleModelImpl = (CommerceDiscountRuleModelImpl)commerceDiscountRule;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceDiscountRule.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceDiscountRule.setCreateDate(now);
			}
			else {
				commerceDiscountRule.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceDiscountRuleModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceDiscountRule.setModifiedDate(now);
			}
			else {
				commerceDiscountRule.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceDiscountRule.isNew()) {
				session.save(commerceDiscountRule);

				commerceDiscountRule.setNew(false);
			}
			else {
				commerceDiscountRule = (CommerceDiscountRule)session.merge(commerceDiscountRule);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceDiscountRuleModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceDiscountRuleModelImpl.getCommerceDiscountId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEDISCOUNTID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEDISCOUNTID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceDiscountRuleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEDISCOUNTID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceDiscountRuleModelImpl.getOriginalCommerceDiscountId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEDISCOUNTID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEDISCOUNTID,
					args);

				args = new Object[] {
						commerceDiscountRuleModelImpl.getCommerceDiscountId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEDISCOUNTID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEDISCOUNTID,
					args);
			}
		}

		entityCache.putResult(CommerceDiscountRuleModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountRuleImpl.class,
			commerceDiscountRule.getPrimaryKey(), commerceDiscountRule, false);

		commerceDiscountRule.resetOriginalValues();

		return commerceDiscountRule;
	}

	/**
	 * Returns the commerce discount rule with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce discount rule
	 * @return the commerce discount rule
	 * @throws NoSuchDiscountRuleException if a commerce discount rule with the primary key could not be found
	 */
	@Override
	public CommerceDiscountRule findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDiscountRuleException {
		CommerceDiscountRule commerceDiscountRule = fetchByPrimaryKey(primaryKey);

		if (commerceDiscountRule == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDiscountRuleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceDiscountRule;
	}

	/**
	 * Returns the commerce discount rule with the primary key or throws a {@link NoSuchDiscountRuleException} if it could not be found.
	 *
	 * @param commerceDiscountRuleId the primary key of the commerce discount rule
	 * @return the commerce discount rule
	 * @throws NoSuchDiscountRuleException if a commerce discount rule with the primary key could not be found
	 */
	@Override
	public CommerceDiscountRule findByPrimaryKey(long commerceDiscountRuleId)
		throws NoSuchDiscountRuleException {
		return findByPrimaryKey((Serializable)commerceDiscountRuleId);
	}

	/**
	 * Returns the commerce discount rule with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce discount rule
	 * @return the commerce discount rule, or <code>null</code> if a commerce discount rule with the primary key could not be found
	 */
	@Override
	public CommerceDiscountRule fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceDiscountRuleModelImpl.ENTITY_CACHE_ENABLED,
				CommerceDiscountRuleImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceDiscountRule commerceDiscountRule = (CommerceDiscountRule)serializable;

		if (commerceDiscountRule == null) {
			Session session = null;

			try {
				session = openSession();

				commerceDiscountRule = (CommerceDiscountRule)session.get(CommerceDiscountRuleImpl.class,
						primaryKey);

				if (commerceDiscountRule != null) {
					cacheResult(commerceDiscountRule);
				}
				else {
					entityCache.putResult(CommerceDiscountRuleModelImpl.ENTITY_CACHE_ENABLED,
						CommerceDiscountRuleImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceDiscountRuleModelImpl.ENTITY_CACHE_ENABLED,
					CommerceDiscountRuleImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceDiscountRule;
	}

	/**
	 * Returns the commerce discount rule with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDiscountRuleId the primary key of the commerce discount rule
	 * @return the commerce discount rule, or <code>null</code> if a commerce discount rule with the primary key could not be found
	 */
	@Override
	public CommerceDiscountRule fetchByPrimaryKey(long commerceDiscountRuleId) {
		return fetchByPrimaryKey((Serializable)commerceDiscountRuleId);
	}

	@Override
	public Map<Serializable, CommerceDiscountRule> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceDiscountRule> map = new HashMap<Serializable, CommerceDiscountRule>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceDiscountRule commerceDiscountRule = fetchByPrimaryKey(primaryKey);

			if (commerceDiscountRule != null) {
				map.put(primaryKey, commerceDiscountRule);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceDiscountRuleModelImpl.ENTITY_CACHE_ENABLED,
					CommerceDiscountRuleImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceDiscountRule)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEDISCOUNTRULE_WHERE_PKS_IN);

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

			for (CommerceDiscountRule commerceDiscountRule : (List<CommerceDiscountRule>)q.list()) {
				map.put(commerceDiscountRule.getPrimaryKeyObj(),
					commerceDiscountRule);

				cacheResult(commerceDiscountRule);

				uncachedPrimaryKeys.remove(commerceDiscountRule.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceDiscountRuleModelImpl.ENTITY_CACHE_ENABLED,
					CommerceDiscountRuleImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce discount rules.
	 *
	 * @return the commerce discount rules
	 */
	@Override
	public List<CommerceDiscountRule> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount rules
	 * @param end the upper bound of the range of commerce discount rules (not inclusive)
	 * @return the range of commerce discount rules
	 */
	@Override
	public List<CommerceDiscountRule> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount rules
	 * @param end the upper bound of the range of commerce discount rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce discount rules
	 */
	@Override
	public List<CommerceDiscountRule> findAll(int start, int end,
		OrderByComparator<CommerceDiscountRule> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount rules
	 * @param end the upper bound of the range of commerce discount rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce discount rules
	 */
	@Override
	public List<CommerceDiscountRule> findAll(int start, int end,
		OrderByComparator<CommerceDiscountRule> orderByComparator,
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

		List<CommerceDiscountRule> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceDiscountRule>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEDISCOUNTRULE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEDISCOUNTRULE;

				if (pagination) {
					sql = sql.concat(CommerceDiscountRuleModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceDiscountRule>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceDiscountRule>)QueryUtil.list(q,
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
	 * Removes all the commerce discount rules from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceDiscountRule commerceDiscountRule : findAll()) {
			remove(commerceDiscountRule);
		}
	}

	/**
	 * Returns the number of commerce discount rules.
	 *
	 * @return the number of commerce discount rules
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEDISCOUNTRULE);

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
		return CommerceDiscountRuleModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce discount rule persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceDiscountRuleImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEDISCOUNTRULE = "SELECT commerceDiscountRule FROM CommerceDiscountRule commerceDiscountRule";
	private static final String _SQL_SELECT_COMMERCEDISCOUNTRULE_WHERE_PKS_IN = "SELECT commerceDiscountRule FROM CommerceDiscountRule commerceDiscountRule WHERE commerceDiscountRuleId IN (";
	private static final String _SQL_SELECT_COMMERCEDISCOUNTRULE_WHERE = "SELECT commerceDiscountRule FROM CommerceDiscountRule commerceDiscountRule WHERE ";
	private static final String _SQL_COUNT_COMMERCEDISCOUNTRULE = "SELECT COUNT(commerceDiscountRule) FROM CommerceDiscountRule commerceDiscountRule";
	private static final String _SQL_COUNT_COMMERCEDISCOUNTRULE_WHERE = "SELECT COUNT(commerceDiscountRule) FROM CommerceDiscountRule commerceDiscountRule WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceDiscountRule.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceDiscountRule exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceDiscountRule exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceDiscountRulePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type"
			});
}