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

import com.liferay.commerce.discount.exception.NoSuchDiscountRuleException;
import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.model.CommerceDiscountRuleTable;
import com.liferay.commerce.discount.model.impl.CommerceDiscountRuleImpl;
import com.liferay.commerce.discount.model.impl.CommerceDiscountRuleModelImpl;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountRulePersistence;
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

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
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
 * @generated
 */
public class CommerceDiscountRulePersistenceImpl
	extends BasePersistenceImpl<CommerceDiscountRule>
	implements CommerceDiscountRulePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceDiscountRuleUtil</code> to access the commerce discount rule persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceDiscountRuleImpl.class.getName();

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
	 * Returns all the commerce discount rules where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount rules
	 */
	@Override
	public List<CommerceDiscountRule> findByCommerceDiscountId(
		long commerceDiscountId) {

		return findByCommerceDiscountId(
			commerceDiscountId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount rules where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountRuleModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountRuleModelImpl</code>.
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

		return findByCommerceDiscountId(
			commerceDiscountId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount rules where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountRuleModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount rules
	 * @param end the upper bound of the range of commerce discount rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount rules
	 */
	@Override
	public List<CommerceDiscountRule> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountRule> orderByComparator,
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

		List<CommerceDiscountRule> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscountRule>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDiscountRule commerceDiscountRule : list) {
					if (commerceDiscountId !=
							commerceDiscountRule.getCommerceDiscountId()) {

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

			sb.append(_SQL_SELECT_COMMERCEDISCOUNTRULE_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEDISCOUNTID_COMMERCEDISCOUNTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceDiscountRuleModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceDiscountId);

				list = (List<CommerceDiscountRule>)QueryUtil.list(
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

		CommerceDiscountRule commerceDiscountRule =
			fetchByCommerceDiscountId_First(
				commerceDiscountId, orderByComparator);

		if (commerceDiscountRule != null) {
			return commerceDiscountRule;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceDiscountId=");
		sb.append(commerceDiscountId);

		sb.append("}");

		throw new NoSuchDiscountRuleException(sb.toString());
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

		List<CommerceDiscountRule> list = findByCommerceDiscountId(
			commerceDiscountId, 0, 1, orderByComparator);

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

		CommerceDiscountRule commerceDiscountRule =
			fetchByCommerceDiscountId_Last(
				commerceDiscountId, orderByComparator);

		if (commerceDiscountRule != null) {
			return commerceDiscountRule;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceDiscountId=");
		sb.append(commerceDiscountId);

		sb.append("}");

		throw new NoSuchDiscountRuleException(sb.toString());
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

		List<CommerceDiscountRule> list = findByCommerceDiscountId(
			commerceDiscountId, count - 1, count, orderByComparator);

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

		CommerceDiscountRule commerceDiscountRule = findByPrimaryKey(
			commerceDiscountRuleId);

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountRule[] array = new CommerceDiscountRuleImpl[3];

			array[0] = getByCommerceDiscountId_PrevAndNext(
				session, commerceDiscountRule, commerceDiscountId,
				orderByComparator, true);

			array[1] = commerceDiscountRule;

			array[2] = getByCommerceDiscountId_PrevAndNext(
				session, commerceDiscountRule, commerceDiscountId,
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

	protected CommerceDiscountRule getByCommerceDiscountId_PrevAndNext(
		Session session, CommerceDiscountRule commerceDiscountRule,
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountRule> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEDISCOUNTRULE_WHERE);

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
			sb.append(CommerceDiscountRuleModelImpl.ORDER_BY_JPQL);
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
						commerceDiscountRule)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceDiscountRule> list = query.list();

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
		for (CommerceDiscountRule commerceDiscountRule :
				findByCommerceDiscountId(
					commerceDiscountId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

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
		FinderPath finderPath = _finderPathCountByCommerceDiscountId;

		Object[] finderArgs = new Object[] {commerceDiscountId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNTRULE_WHERE);

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
			"commerceDiscountRule.commerceDiscountId = ?";

	public CommerceDiscountRulePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceDiscountRule.class);

		setModelImplClass(CommerceDiscountRuleImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceDiscountRuleTable.INSTANCE);
	}

	/**
	 * Caches the commerce discount rule in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountRule the commerce discount rule
	 */
	@Override
	public void cacheResult(CommerceDiscountRule commerceDiscountRule) {
		entityCache.putResult(
			CommerceDiscountRuleImpl.class,
			commerceDiscountRule.getPrimaryKey(), commerceDiscountRule);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce discount rules in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountRules the commerce discount rules
	 */
	@Override
	public void cacheResult(List<CommerceDiscountRule> commerceDiscountRules) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceDiscountRules.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceDiscountRule commerceDiscountRule :
				commerceDiscountRules) {

			if (entityCache.getResult(
					CommerceDiscountRuleImpl.class,
					commerceDiscountRule.getPrimaryKey()) == null) {

				cacheResult(commerceDiscountRule);
			}
		}
	}

	/**
	 * Clears the cache for all commerce discount rules.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceDiscountRuleImpl.class);

		finderCache.clearCache(CommerceDiscountRuleImpl.class);
	}

	/**
	 * Clears the cache for the commerce discount rule.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceDiscountRule commerceDiscountRule) {
		entityCache.removeResult(
			CommerceDiscountRuleImpl.class, commerceDiscountRule);
	}

	@Override
	public void clearCache(List<CommerceDiscountRule> commerceDiscountRules) {
		for (CommerceDiscountRule commerceDiscountRule :
				commerceDiscountRules) {

			entityCache.removeResult(
				CommerceDiscountRuleImpl.class, commerceDiscountRule);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceDiscountRuleImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceDiscountRuleImpl.class, primaryKey);
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
		CommerceDiscountRule commerceDiscountRule =
			new CommerceDiscountRuleImpl();

		commerceDiscountRule.setNew(true);
		commerceDiscountRule.setPrimaryKey(commerceDiscountRuleId);

		commerceDiscountRule.setCompanyId(CompanyThreadLocal.getCompanyId());

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

			CommerceDiscountRule commerceDiscountRule =
				(CommerceDiscountRule)session.get(
					CommerceDiscountRuleImpl.class, primaryKey);

			if (commerceDiscountRule == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDiscountRuleException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceDiscountRule);
		}
		catch (NoSuchDiscountRuleException noSuchEntityException) {
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
	protected CommerceDiscountRule removeImpl(
		CommerceDiscountRule commerceDiscountRule) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceDiscountRule)) {
				commerceDiscountRule = (CommerceDiscountRule)session.get(
					CommerceDiscountRuleImpl.class,
					commerceDiscountRule.getPrimaryKeyObj());
			}

			if (commerceDiscountRule != null) {
				session.delete(commerceDiscountRule);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceDiscountRule);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceDiscountRule proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceDiscountRule implementation " +
					commerceDiscountRule.getClass());
		}

		CommerceDiscountRuleModelImpl commerceDiscountRuleModelImpl =
			(CommerceDiscountRuleModelImpl)commerceDiscountRule;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceDiscountRule.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceDiscountRule.setCreateDate(date);
			}
			else {
				commerceDiscountRule.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceDiscountRuleModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceDiscountRule.setModifiedDate(date);
			}
			else {
				commerceDiscountRule.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceDiscountRule);
			}
			else {
				commerceDiscountRule = (CommerceDiscountRule)session.merge(
					commerceDiscountRule);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceDiscountRuleImpl.class, commerceDiscountRuleModelImpl,
			false, true);

		if (isNew) {
			commerceDiscountRule.setNew(false);
		}

		commerceDiscountRule.resetOriginalValues();

		return commerceDiscountRule;
	}

	/**
	 * Returns the commerce discount rule with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce discount rule
	 * @return the commerce discount rule
	 * @throws NoSuchDiscountRuleException if a commerce discount rule with the primary key could not be found
	 */
	@Override
	public CommerceDiscountRule findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDiscountRuleException {

		CommerceDiscountRule commerceDiscountRule = fetchByPrimaryKey(
			primaryKey);

		if (commerceDiscountRule == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDiscountRuleException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceDiscountRule;
	}

	/**
	 * Returns the commerce discount rule with the primary key or throws a <code>NoSuchDiscountRuleException</code> if it could not be found.
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
	 * @param commerceDiscountRuleId the primary key of the commerce discount rule
	 * @return the commerce discount rule, or <code>null</code> if a commerce discount rule with the primary key could not be found
	 */
	@Override
	public CommerceDiscountRule fetchByPrimaryKey(long commerceDiscountRuleId) {
		return fetchByPrimaryKey((Serializable)commerceDiscountRuleId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountRuleModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountRuleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount rules
	 * @param end the upper bound of the range of commerce discount rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce discount rules
	 */
	@Override
	public List<CommerceDiscountRule> findAll(
		int start, int end,
		OrderByComparator<CommerceDiscountRule> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountRuleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount rules
	 * @param end the upper bound of the range of commerce discount rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce discount rules
	 */
	@Override
	public List<CommerceDiscountRule> findAll(
		int start, int end,
		OrderByComparator<CommerceDiscountRule> orderByComparator,
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

		List<CommerceDiscountRule> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscountRule>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEDISCOUNTRULE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEDISCOUNTRULE;

				sql = sql.concat(CommerceDiscountRuleModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceDiscountRule>)QueryUtil.list(
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
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCEDISCOUNTRULE);

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
		return "commerceDiscountRuleId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEDISCOUNTRULE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceDiscountRuleModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce discount rule persistence.
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
	}

	public void destroy() {
		entityCache.removeCache(CommerceDiscountRuleImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEDISCOUNTRULE =
		"SELECT commerceDiscountRule FROM CommerceDiscountRule commerceDiscountRule";

	private static final String _SQL_SELECT_COMMERCEDISCOUNTRULE_WHERE =
		"SELECT commerceDiscountRule FROM CommerceDiscountRule commerceDiscountRule WHERE ";

	private static final String _SQL_COUNT_COMMERCEDISCOUNTRULE =
		"SELECT COUNT(commerceDiscountRule) FROM CommerceDiscountRule commerceDiscountRule";

	private static final String _SQL_COUNT_COMMERCEDISCOUNTRULE_WHERE =
		"SELECT COUNT(commerceDiscountRule) FROM CommerceDiscountRule commerceDiscountRule WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceDiscountRule.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceDiscountRule exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceDiscountRule exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountRulePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}