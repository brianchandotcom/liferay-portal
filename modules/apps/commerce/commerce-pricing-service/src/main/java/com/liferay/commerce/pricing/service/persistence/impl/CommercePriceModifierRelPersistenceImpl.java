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

package com.liferay.commerce.pricing.service.persistence.impl;

import com.liferay.commerce.pricing.exception.NoSuchPriceModifierRelException;
import com.liferay.commerce.pricing.model.CommercePriceModifierRel;
import com.liferay.commerce.pricing.model.CommercePriceModifierRelTable;
import com.liferay.commerce.pricing.model.impl.CommercePriceModifierRelImpl;
import com.liferay.commerce.pricing.model.impl.CommercePriceModifierRelModelImpl;
import com.liferay.commerce.pricing.service.persistence.CommercePriceModifierRelPersistence;
import com.liferay.commerce.pricing.service.persistence.CommercePriceModifierRelUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
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
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the commerce price modifier rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Riccardo Alberti
 * @generated
 */
public class CommercePriceModifierRelPersistenceImpl
	extends BasePersistenceImpl<CommercePriceModifierRel>
	implements CommercePriceModifierRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommercePriceModifierRelUtil</code> to access the commerce price modifier rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommercePriceModifierRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCommercePriceModifierId;
	private FinderPath
		_finderPathWithoutPaginationFindByCommercePriceModifierId;
	private FinderPath _finderPathCountByCommercePriceModifierId;

	/**
	 * Returns all the commerce price modifier rels where commercePriceModifierId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @return the matching commerce price modifier rels
	 */
	@Override
	public List<CommercePriceModifierRel> findByCommercePriceModifierId(
		long commercePriceModifierId) {

		return findByCommercePriceModifierId(
			commercePriceModifierId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce price modifier rels where commercePriceModifierId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @return the range of matching commerce price modifier rels
	 */
	@Override
	public List<CommercePriceModifierRel> findByCommercePriceModifierId(
		long commercePriceModifierId, int start, int end) {

		return findByCommercePriceModifierId(
			commercePriceModifierId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price modifier rels where commercePriceModifierId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price modifier rels
	 */
	@Override
	public List<CommercePriceModifierRel> findByCommercePriceModifierId(
		long commercePriceModifierId, int start, int end,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		return findByCommercePriceModifierId(
			commercePriceModifierId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price modifier rels where commercePriceModifierId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price modifier rels
	 */
	@Override
	public List<CommercePriceModifierRel> findByCommercePriceModifierId(
		long commercePriceModifierId, int start, int end,
		OrderByComparator<CommercePriceModifierRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceModifierRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByCommercePriceModifierId;
				finderArgs = new Object[] {commercePriceModifierId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCommercePriceModifierId;
			finderArgs = new Object[] {
				commercePriceModifierId, start, end, orderByComparator
			};
		}

		List<CommercePriceModifierRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceModifierRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceModifierRel commercePriceModifierRel : list) {
					if (commercePriceModifierId !=
							commercePriceModifierRel.
								getCommercePriceModifierId()) {

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

			sb.append(_SQL_SELECT_COMMERCEPRICEMODIFIERREL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEPRICEMODIFIERID_COMMERCEPRICEMODIFIERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommercePriceModifierRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceModifierId);

				list = (List<CommercePriceModifierRel>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first commerce price modifier rel in the ordered set where commercePriceModifierId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a matching commerce price modifier rel could not be found
	 */
	@Override
	public CommercePriceModifierRel findByCommercePriceModifierId_First(
			long commercePriceModifierId,
			OrderByComparator<CommercePriceModifierRel> orderByComparator)
		throws NoSuchPriceModifierRelException {

		CommercePriceModifierRel commercePriceModifierRel =
			fetchByCommercePriceModifierId_First(
				commercePriceModifierId, orderByComparator);

		if (commercePriceModifierRel != null) {
			return commercePriceModifierRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceModifierId=");
		sb.append(commercePriceModifierId);

		sb.append("}");

		throw new NoSuchPriceModifierRelException(sb.toString());
	}

	/**
	 * Returns the first commerce price modifier rel in the ordered set where commercePriceModifierId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	@Override
	public CommercePriceModifierRel fetchByCommercePriceModifierId_First(
		long commercePriceModifierId,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		List<CommercePriceModifierRel> list = findByCommercePriceModifierId(
			commercePriceModifierId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price modifier rel in the ordered set where commercePriceModifierId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a matching commerce price modifier rel could not be found
	 */
	@Override
	public CommercePriceModifierRel findByCommercePriceModifierId_Last(
			long commercePriceModifierId,
			OrderByComparator<CommercePriceModifierRel> orderByComparator)
		throws NoSuchPriceModifierRelException {

		CommercePriceModifierRel commercePriceModifierRel =
			fetchByCommercePriceModifierId_Last(
				commercePriceModifierId, orderByComparator);

		if (commercePriceModifierRel != null) {
			return commercePriceModifierRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceModifierId=");
		sb.append(commercePriceModifierId);

		sb.append("}");

		throw new NoSuchPriceModifierRelException(sb.toString());
	}

	/**
	 * Returns the last commerce price modifier rel in the ordered set where commercePriceModifierId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	@Override
	public CommercePriceModifierRel fetchByCommercePriceModifierId_Last(
		long commercePriceModifierId,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		int count = countByCommercePriceModifierId(commercePriceModifierId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceModifierRel> list = findByCommercePriceModifierId(
			commercePriceModifierId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price modifier rels before and after the current commerce price modifier rel in the ordered set where commercePriceModifierId = &#63;.
	 *
	 * @param commercePriceModifierRelId the primary key of the current commerce price modifier rel
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a commerce price modifier rel with the primary key could not be found
	 */
	@Override
	public CommercePriceModifierRel[] findByCommercePriceModifierId_PrevAndNext(
			long commercePriceModifierRelId, long commercePriceModifierId,
			OrderByComparator<CommercePriceModifierRel> orderByComparator)
		throws NoSuchPriceModifierRelException {

		CommercePriceModifierRel commercePriceModifierRel = findByPrimaryKey(
			commercePriceModifierRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceModifierRel[] array =
				new CommercePriceModifierRelImpl[3];

			array[0] = getByCommercePriceModifierId_PrevAndNext(
				session, commercePriceModifierRel, commercePriceModifierId,
				orderByComparator, true);

			array[1] = commercePriceModifierRel;

			array[2] = getByCommercePriceModifierId_PrevAndNext(
				session, commercePriceModifierRel, commercePriceModifierId,
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

	protected CommercePriceModifierRel getByCommercePriceModifierId_PrevAndNext(
		Session session, CommercePriceModifierRel commercePriceModifierRel,
		long commercePriceModifierId,
		OrderByComparator<CommercePriceModifierRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEPRICEMODIFIERREL_WHERE);

		sb.append(
			_FINDER_COLUMN_COMMERCEPRICEMODIFIERID_COMMERCEPRICEMODIFIERID_2);

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
			sb.append(CommercePriceModifierRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commercePriceModifierId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commercePriceModifierRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceModifierRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price modifier rels where commercePriceModifierId = &#63; from the database.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 */
	@Override
	public void removeByCommercePriceModifierId(long commercePriceModifierId) {
		for (CommercePriceModifierRel commercePriceModifierRel :
				findByCommercePriceModifierId(
					commercePriceModifierId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commercePriceModifierRel);
		}
	}

	/**
	 * Returns the number of commerce price modifier rels where commercePriceModifierId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @return the number of matching commerce price modifier rels
	 */
	@Override
	public int countByCommercePriceModifierId(long commercePriceModifierId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceModifierRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCommercePriceModifierId;

			finderArgs = new Object[] {commercePriceModifierId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEPRICEMODIFIERREL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEPRICEMODIFIERID_COMMERCEPRICEMODIFIERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceModifierId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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
		_FINDER_COLUMN_COMMERCEPRICEMODIFIERID_COMMERCEPRICEMODIFIERID_2 =
			"commercePriceModifierRel.commercePriceModifierId = ?";

	private FinderPath _finderPathWithPaginationFindByCPM_CN;
	private FinderPath _finderPathWithoutPaginationFindByCPM_CN;
	private FinderPath _finderPathCountByCPM_CN;

	/**
	 * Returns all the commerce price modifier rels where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @return the matching commerce price modifier rels
	 */
	@Override
	public List<CommercePriceModifierRel> findByCPM_CN(
		long commercePriceModifierId, long classNameId) {

		return findByCPM_CN(
			commercePriceModifierId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price modifier rels where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @return the range of matching commerce price modifier rels
	 */
	@Override
	public List<CommercePriceModifierRel> findByCPM_CN(
		long commercePriceModifierId, long classNameId, int start, int end) {

		return findByCPM_CN(
			commercePriceModifierId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price modifier rels where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price modifier rels
	 */
	@Override
	public List<CommercePriceModifierRel> findByCPM_CN(
		long commercePriceModifierId, long classNameId, int start, int end,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		return findByCPM_CN(
			commercePriceModifierId, classNameId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce price modifier rels where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price modifier rels
	 */
	@Override
	public List<CommercePriceModifierRel> findByCPM_CN(
		long commercePriceModifierId, long classNameId, int start, int end,
		OrderByComparator<CommercePriceModifierRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceModifierRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByCPM_CN;
				finderArgs = new Object[] {
					commercePriceModifierId, classNameId
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCPM_CN;
			finderArgs = new Object[] {
				commercePriceModifierId, classNameId, start, end,
				orderByComparator
			};
		}

		List<CommercePriceModifierRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceModifierRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceModifierRel commercePriceModifierRel : list) {
					if ((commercePriceModifierId !=
							commercePriceModifierRel.
								getCommercePriceModifierId()) ||
						(classNameId !=
							commercePriceModifierRel.getClassNameId())) {

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

			sb.append(_SQL_SELECT_COMMERCEPRICEMODIFIERREL_WHERE);

			sb.append(_FINDER_COLUMN_CPM_CN_COMMERCEPRICEMODIFIERID_2);

			sb.append(_FINDER_COLUMN_CPM_CN_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommercePriceModifierRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceModifierId);

				queryPos.add(classNameId);

				list = (List<CommercePriceModifierRel>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first commerce price modifier rel in the ordered set where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a matching commerce price modifier rel could not be found
	 */
	@Override
	public CommercePriceModifierRel findByCPM_CN_First(
			long commercePriceModifierId, long classNameId,
			OrderByComparator<CommercePriceModifierRel> orderByComparator)
		throws NoSuchPriceModifierRelException {

		CommercePriceModifierRel commercePriceModifierRel = fetchByCPM_CN_First(
			commercePriceModifierId, classNameId, orderByComparator);

		if (commercePriceModifierRel != null) {
			return commercePriceModifierRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceModifierId=");
		sb.append(commercePriceModifierId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append("}");

		throw new NoSuchPriceModifierRelException(sb.toString());
	}

	/**
	 * Returns the first commerce price modifier rel in the ordered set where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	@Override
	public CommercePriceModifierRel fetchByCPM_CN_First(
		long commercePriceModifierId, long classNameId,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		List<CommercePriceModifierRel> list = findByCPM_CN(
			commercePriceModifierId, classNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price modifier rel in the ordered set where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a matching commerce price modifier rel could not be found
	 */
	@Override
	public CommercePriceModifierRel findByCPM_CN_Last(
			long commercePriceModifierId, long classNameId,
			OrderByComparator<CommercePriceModifierRel> orderByComparator)
		throws NoSuchPriceModifierRelException {

		CommercePriceModifierRel commercePriceModifierRel = fetchByCPM_CN_Last(
			commercePriceModifierId, classNameId, orderByComparator);

		if (commercePriceModifierRel != null) {
			return commercePriceModifierRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceModifierId=");
		sb.append(commercePriceModifierId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append("}");

		throw new NoSuchPriceModifierRelException(sb.toString());
	}

	/**
	 * Returns the last commerce price modifier rel in the ordered set where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	@Override
	public CommercePriceModifierRel fetchByCPM_CN_Last(
		long commercePriceModifierId, long classNameId,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		int count = countByCPM_CN(commercePriceModifierId, classNameId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceModifierRel> list = findByCPM_CN(
			commercePriceModifierId, classNameId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price modifier rels before and after the current commerce price modifier rel in the ordered set where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceModifierRelId the primary key of the current commerce price modifier rel
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a commerce price modifier rel with the primary key could not be found
	 */
	@Override
	public CommercePriceModifierRel[] findByCPM_CN_PrevAndNext(
			long commercePriceModifierRelId, long commercePriceModifierId,
			long classNameId,
			OrderByComparator<CommercePriceModifierRel> orderByComparator)
		throws NoSuchPriceModifierRelException {

		CommercePriceModifierRel commercePriceModifierRel = findByPrimaryKey(
			commercePriceModifierRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceModifierRel[] array =
				new CommercePriceModifierRelImpl[3];

			array[0] = getByCPM_CN_PrevAndNext(
				session, commercePriceModifierRel, commercePriceModifierId,
				classNameId, orderByComparator, true);

			array[1] = commercePriceModifierRel;

			array[2] = getByCPM_CN_PrevAndNext(
				session, commercePriceModifierRel, commercePriceModifierId,
				classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommercePriceModifierRel getByCPM_CN_PrevAndNext(
		Session session, CommercePriceModifierRel commercePriceModifierRel,
		long commercePriceModifierId, long classNameId,
		OrderByComparator<CommercePriceModifierRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEPRICEMODIFIERREL_WHERE);

		sb.append(_FINDER_COLUMN_CPM_CN_COMMERCEPRICEMODIFIERID_2);

		sb.append(_FINDER_COLUMN_CPM_CN_CLASSNAMEID_2);

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
			sb.append(CommercePriceModifierRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commercePriceModifierId);

		queryPos.add(classNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commercePriceModifierRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceModifierRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price modifier rels where commercePriceModifierId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByCPM_CN(long commercePriceModifierId, long classNameId) {
		for (CommercePriceModifierRel commercePriceModifierRel :
				findByCPM_CN(
					commercePriceModifierId, classNameId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commercePriceModifierRel);
		}
	}

	/**
	 * Returns the number of commerce price modifier rels where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @return the number of matching commerce price modifier rels
	 */
	@Override
	public int countByCPM_CN(long commercePriceModifierId, long classNameId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceModifierRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCPM_CN;

			finderArgs = new Object[] {commercePriceModifierId, classNameId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEPRICEMODIFIERREL_WHERE);

			sb.append(_FINDER_COLUMN_CPM_CN_COMMERCEPRICEMODIFIERID_2);

			sb.append(_FINDER_COLUMN_CPM_CN_CLASSNAMEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceModifierId);

				queryPos.add(classNameId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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
		_FINDER_COLUMN_CPM_CN_COMMERCEPRICEMODIFIERID_2 =
			"commercePriceModifierRel.commercePriceModifierId = ? AND ";

	private static final String _FINDER_COLUMN_CPM_CN_CLASSNAMEID_2 =
		"commercePriceModifierRel.classNameId = ?";

	private FinderPath _finderPathWithPaginationFindByCN_CPK;
	private FinderPath _finderPathWithoutPaginationFindByCN_CPK;
	private FinderPath _finderPathCountByCN_CPK;

	/**
	 * Returns all the commerce price modifier rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce price modifier rels
	 */
	@Override
	public List<CommercePriceModifierRel> findByCN_CPK(
		long classNameId, long classPK) {

		return findByCN_CPK(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price modifier rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @return the range of matching commerce price modifier rels
	 */
	@Override
	public List<CommercePriceModifierRel> findByCN_CPK(
		long classNameId, long classPK, int start, int end) {

		return findByCN_CPK(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price modifier rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price modifier rels
	 */
	@Override
	public List<CommercePriceModifierRel> findByCN_CPK(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		return findByCN_CPK(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price modifier rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price modifier rels
	 */
	@Override
	public List<CommercePriceModifierRel> findByCN_CPK(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CommercePriceModifierRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceModifierRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByCN_CPK;
				finderArgs = new Object[] {classNameId, classPK};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCN_CPK;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<CommercePriceModifierRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceModifierRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceModifierRel commercePriceModifierRel : list) {
					if ((classNameId !=
							commercePriceModifierRel.getClassNameId()) ||
						(classPK != commercePriceModifierRel.getClassPK())) {

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

			sb.append(_SQL_SELECT_COMMERCEPRICEMODIFIERREL_WHERE);

			sb.append(_FINDER_COLUMN_CN_CPK_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_CN_CPK_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommercePriceModifierRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<CommercePriceModifierRel>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first commerce price modifier rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a matching commerce price modifier rel could not be found
	 */
	@Override
	public CommercePriceModifierRel findByCN_CPK_First(
			long classNameId, long classPK,
			OrderByComparator<CommercePriceModifierRel> orderByComparator)
		throws NoSuchPriceModifierRelException {

		CommercePriceModifierRel commercePriceModifierRel = fetchByCN_CPK_First(
			classNameId, classPK, orderByComparator);

		if (commercePriceModifierRel != null) {
			return commercePriceModifierRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchPriceModifierRelException(sb.toString());
	}

	/**
	 * Returns the first commerce price modifier rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	@Override
	public CommercePriceModifierRel fetchByCN_CPK_First(
		long classNameId, long classPK,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		List<CommercePriceModifierRel> list = findByCN_CPK(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price modifier rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a matching commerce price modifier rel could not be found
	 */
	@Override
	public CommercePriceModifierRel findByCN_CPK_Last(
			long classNameId, long classPK,
			OrderByComparator<CommercePriceModifierRel> orderByComparator)
		throws NoSuchPriceModifierRelException {

		CommercePriceModifierRel commercePriceModifierRel = fetchByCN_CPK_Last(
			classNameId, classPK, orderByComparator);

		if (commercePriceModifierRel != null) {
			return commercePriceModifierRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchPriceModifierRelException(sb.toString());
	}

	/**
	 * Returns the last commerce price modifier rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	@Override
	public CommercePriceModifierRel fetchByCN_CPK_Last(
		long classNameId, long classPK,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		int count = countByCN_CPK(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<CommercePriceModifierRel> list = findByCN_CPK(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price modifier rels before and after the current commerce price modifier rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commercePriceModifierRelId the primary key of the current commerce price modifier rel
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a commerce price modifier rel with the primary key could not be found
	 */
	@Override
	public CommercePriceModifierRel[] findByCN_CPK_PrevAndNext(
			long commercePriceModifierRelId, long classNameId, long classPK,
			OrderByComparator<CommercePriceModifierRel> orderByComparator)
		throws NoSuchPriceModifierRelException {

		CommercePriceModifierRel commercePriceModifierRel = findByPrimaryKey(
			commercePriceModifierRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceModifierRel[] array =
				new CommercePriceModifierRelImpl[3];

			array[0] = getByCN_CPK_PrevAndNext(
				session, commercePriceModifierRel, classNameId, classPK,
				orderByComparator, true);

			array[1] = commercePriceModifierRel;

			array[2] = getByCN_CPK_PrevAndNext(
				session, commercePriceModifierRel, classNameId, classPK,
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

	protected CommercePriceModifierRel getByCN_CPK_PrevAndNext(
		Session session, CommercePriceModifierRel commercePriceModifierRel,
		long classNameId, long classPK,
		OrderByComparator<CommercePriceModifierRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEPRICEMODIFIERREL_WHERE);

		sb.append(_FINDER_COLUMN_CN_CPK_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_CN_CPK_CLASSPK_2);

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
			sb.append(CommercePriceModifierRelModelImpl.ORDER_BY_JPQL);
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
						commercePriceModifierRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceModifierRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price modifier rels where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByCN_CPK(long classNameId, long classPK) {
		for (CommercePriceModifierRel commercePriceModifierRel :
				findByCN_CPK(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commercePriceModifierRel);
		}
	}

	/**
	 * Returns the number of commerce price modifier rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching commerce price modifier rels
	 */
	@Override
	public int countByCN_CPK(long classNameId, long classPK) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceModifierRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCN_CPK;

			finderArgs = new Object[] {classNameId, classPK};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEPRICEMODIFIERREL_WHERE);

			sb.append(_FINDER_COLUMN_CN_CPK_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_CN_CPK_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_CN_CPK_CLASSNAMEID_2 =
		"commercePriceModifierRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_CN_CPK_CLASSPK_2 =
		"commercePriceModifierRel.classPK = ?";

	private FinderPath _finderPathFetchByCPM_CN_CPK;
	private FinderPath _finderPathCountByCPM_CN_CPK;

	/**
	 * Returns the commerce price modifier rel where commercePriceModifierId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchPriceModifierRelException</code> if it could not be found.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a matching commerce price modifier rel could not be found
	 */
	@Override
	public CommercePriceModifierRel findByCPM_CN_CPK(
			long commercePriceModifierId, long classNameId, long classPK)
		throws NoSuchPriceModifierRelException {

		CommercePriceModifierRel commercePriceModifierRel = fetchByCPM_CN_CPK(
			commercePriceModifierId, classNameId, classPK);

		if (commercePriceModifierRel == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("commercePriceModifierId=");
			sb.append(commercePriceModifierId);

			sb.append(", classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPriceModifierRelException(sb.toString());
		}

		return commercePriceModifierRel;
	}

	/**
	 * Returns the commerce price modifier rel where commercePriceModifierId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	@Override
	public CommercePriceModifierRel fetchByCPM_CN_CPK(
		long commercePriceModifierId, long classNameId, long classPK) {

		return fetchByCPM_CN_CPK(
			commercePriceModifierId, classNameId, classPK, true);
	}

	/**
	 * Returns the commerce price modifier rel where commercePriceModifierId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	@Override
	public CommercePriceModifierRel fetchByCPM_CN_CPK(
		long commercePriceModifierId, long classNameId, long classPK,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceModifierRel.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				commercePriceModifierId, classNameId, classPK
			};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByCPM_CN_CPK, finderArgs);
		}

		if (result instanceof CommercePriceModifierRel) {
			CommercePriceModifierRel commercePriceModifierRel =
				(CommercePriceModifierRel)result;

			if ((commercePriceModifierId !=
					commercePriceModifierRel.getCommercePriceModifierId()) ||
				(classNameId != commercePriceModifierRel.getClassNameId()) ||
				(classPK != commercePriceModifierRel.getClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_COMMERCEPRICEMODIFIERREL_WHERE);

			sb.append(_FINDER_COLUMN_CPM_CN_CPK_COMMERCEPRICEMODIFIERID_2);

			sb.append(_FINDER_COLUMN_CPM_CN_CPK_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_CPM_CN_CPK_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceModifierId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				List<CommercePriceModifierRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByCPM_CN_CPK, finderArgs, list);
					}
				}
				else {
					CommercePriceModifierRel commercePriceModifierRel =
						list.get(0);

					result = commercePriceModifierRel;

					cacheResult(commercePriceModifierRel);
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
			return (CommercePriceModifierRel)result;
		}
	}

	/**
	 * Removes the commerce price modifier rel where commercePriceModifierId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the commerce price modifier rel that was removed
	 */
	@Override
	public CommercePriceModifierRel removeByCPM_CN_CPK(
			long commercePriceModifierId, long classNameId, long classPK)
		throws NoSuchPriceModifierRelException {

		CommercePriceModifierRel commercePriceModifierRel = findByCPM_CN_CPK(
			commercePriceModifierId, classNameId, classPK);

		return remove(commercePriceModifierRel);
	}

	/**
	 * Returns the number of commerce price modifier rels where commercePriceModifierId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching commerce price modifier rels
	 */
	@Override
	public int countByCPM_CN_CPK(
		long commercePriceModifierId, long classNameId, long classPK) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceModifierRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCPM_CN_CPK;

			finderArgs = new Object[] {
				commercePriceModifierId, classNameId, classPK
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCEPRICEMODIFIERREL_WHERE);

			sb.append(_FINDER_COLUMN_CPM_CN_CPK_COMMERCEPRICEMODIFIERID_2);

			sb.append(_FINDER_COLUMN_CPM_CN_CPK_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_CPM_CN_CPK_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceModifierId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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
		_FINDER_COLUMN_CPM_CN_CPK_COMMERCEPRICEMODIFIERID_2 =
			"commercePriceModifierRel.commercePriceModifierId = ? AND ";

	private static final String _FINDER_COLUMN_CPM_CN_CPK_CLASSNAMEID_2 =
		"commercePriceModifierRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_CPM_CN_CPK_CLASSPK_2 =
		"commercePriceModifierRel.classPK = ?";

	public CommercePriceModifierRelPersistenceImpl() {
		setModelClass(CommercePriceModifierRel.class);

		setModelImplClass(CommercePriceModifierRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommercePriceModifierRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce price modifier rel in the entity cache if it is enabled.
	 *
	 * @param commercePriceModifierRel the commerce price modifier rel
	 */
	@Override
	public void cacheResult(CommercePriceModifierRel commercePriceModifierRel) {
		if (commercePriceModifierRel.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			CommercePriceModifierRelImpl.class,
			commercePriceModifierRel.getPrimaryKey(), commercePriceModifierRel);

		finderCache.putResult(
			_finderPathFetchByCPM_CN_CPK,
			new Object[] {
				commercePriceModifierRel.getCommercePriceModifierId(),
				commercePriceModifierRel.getClassNameId(),
				commercePriceModifierRel.getClassPK()
			},
			commercePriceModifierRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce price modifier rels in the entity cache if it is enabled.
	 *
	 * @param commercePriceModifierRels the commerce price modifier rels
	 */
	@Override
	public void cacheResult(
		List<CommercePriceModifierRel> commercePriceModifierRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commercePriceModifierRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommercePriceModifierRel commercePriceModifierRel :
				commercePriceModifierRels) {

			if (commercePriceModifierRel.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					CommercePriceModifierRelImpl.class,
					commercePriceModifierRel.getPrimaryKey()) == null) {

				cacheResult(commercePriceModifierRel);
			}
		}
	}

	/**
	 * Clears the cache for all commerce price modifier rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommercePriceModifierRelImpl.class);

		finderCache.clearCache(CommercePriceModifierRelImpl.class);
	}

	/**
	 * Clears the cache for the commerce price modifier rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommercePriceModifierRel commercePriceModifierRel) {
		entityCache.removeResult(
			CommercePriceModifierRelImpl.class, commercePriceModifierRel);
	}

	@Override
	public void clearCache(
		List<CommercePriceModifierRel> commercePriceModifierRels) {

		for (CommercePriceModifierRel commercePriceModifierRel :
				commercePriceModifierRels) {

			entityCache.removeResult(
				CommercePriceModifierRelImpl.class, commercePriceModifierRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommercePriceModifierRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommercePriceModifierRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommercePriceModifierRelModelImpl commercePriceModifierRelModelImpl) {

		Object[] args = new Object[] {
			commercePriceModifierRelModelImpl.getCommercePriceModifierId(),
			commercePriceModifierRelModelImpl.getClassNameId(),
			commercePriceModifierRelModelImpl.getClassPK()
		};

		finderCache.putResult(
			_finderPathCountByCPM_CN_CPK, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCPM_CN_CPK, args,
			commercePriceModifierRelModelImpl);
	}

	/**
	 * Creates a new commerce price modifier rel with the primary key. Does not add the commerce price modifier rel to the database.
	 *
	 * @param commercePriceModifierRelId the primary key for the new commerce price modifier rel
	 * @return the new commerce price modifier rel
	 */
	@Override
	public CommercePriceModifierRel create(long commercePriceModifierRelId) {
		CommercePriceModifierRel commercePriceModifierRel =
			new CommercePriceModifierRelImpl();

		commercePriceModifierRel.setNew(true);
		commercePriceModifierRel.setPrimaryKey(commercePriceModifierRelId);

		commercePriceModifierRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commercePriceModifierRel;
	}

	/**
	 * Removes the commerce price modifier rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceModifierRelId the primary key of the commerce price modifier rel
	 * @return the commerce price modifier rel that was removed
	 * @throws NoSuchPriceModifierRelException if a commerce price modifier rel with the primary key could not be found
	 */
	@Override
	public CommercePriceModifierRel remove(long commercePriceModifierRelId)
		throws NoSuchPriceModifierRelException {

		return remove((Serializable)commercePriceModifierRelId);
	}

	/**
	 * Removes the commerce price modifier rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce price modifier rel
	 * @return the commerce price modifier rel that was removed
	 * @throws NoSuchPriceModifierRelException if a commerce price modifier rel with the primary key could not be found
	 */
	@Override
	public CommercePriceModifierRel remove(Serializable primaryKey)
		throws NoSuchPriceModifierRelException {

		Session session = null;

		try {
			session = openSession();

			CommercePriceModifierRel commercePriceModifierRel =
				(CommercePriceModifierRel)session.get(
					CommercePriceModifierRelImpl.class, primaryKey);

			if (commercePriceModifierRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPriceModifierRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commercePriceModifierRel);
		}
		catch (NoSuchPriceModifierRelException noSuchEntityException) {
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
	protected CommercePriceModifierRel removeImpl(
		CommercePriceModifierRel commercePriceModifierRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commercePriceModifierRel)) {
				commercePriceModifierRel =
					(CommercePriceModifierRel)session.get(
						CommercePriceModifierRelImpl.class,
						commercePriceModifierRel.getPrimaryKeyObj());
			}

			if ((commercePriceModifierRel != null) &&
				ctPersistenceHelper.isRemove(commercePriceModifierRel)) {

				session.delete(commercePriceModifierRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commercePriceModifierRel != null) {
			clearCache(commercePriceModifierRel);
		}

		return commercePriceModifierRel;
	}

	@Override
	public CommercePriceModifierRel updateImpl(
		CommercePriceModifierRel commercePriceModifierRel) {

		boolean isNew = commercePriceModifierRel.isNew();

		if (!(commercePriceModifierRel instanceof
				CommercePriceModifierRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commercePriceModifierRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commercePriceModifierRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commercePriceModifierRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommercePriceModifierRel implementation " +
					commercePriceModifierRel.getClass());
		}

		CommercePriceModifierRelModelImpl commercePriceModifierRelModelImpl =
			(CommercePriceModifierRelModelImpl)commercePriceModifierRel;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commercePriceModifierRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commercePriceModifierRel.setCreateDate(date);
			}
			else {
				commercePriceModifierRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commercePriceModifierRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commercePriceModifierRel.setModifiedDate(date);
			}
			else {
				commercePriceModifierRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(commercePriceModifierRel)) {
				if (!isNew) {
					session.evict(
						CommercePriceModifierRelImpl.class,
						commercePriceModifierRel.getPrimaryKeyObj());
				}

				session.save(commercePriceModifierRel);
			}
			else {
				commercePriceModifierRel =
					(CommercePriceModifierRel)session.merge(
						commercePriceModifierRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commercePriceModifierRel.getCtCollectionId() != 0) {
			if (isNew) {
				commercePriceModifierRel.setNew(false);
			}

			commercePriceModifierRel.resetOriginalValues();

			return commercePriceModifierRel;
		}

		entityCache.putResult(
			CommercePriceModifierRelImpl.class,
			commercePriceModifierRelModelImpl, false, true);

		cacheUniqueFindersCache(commercePriceModifierRelModelImpl);

		if (isNew) {
			commercePriceModifierRel.setNew(false);
		}

		commercePriceModifierRel.resetOriginalValues();

		return commercePriceModifierRel;
	}

	/**
	 * Returns the commerce price modifier rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce price modifier rel
	 * @return the commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a commerce price modifier rel with the primary key could not be found
	 */
	@Override
	public CommercePriceModifierRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPriceModifierRelException {

		CommercePriceModifierRel commercePriceModifierRel = fetchByPrimaryKey(
			primaryKey);

		if (commercePriceModifierRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPriceModifierRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commercePriceModifierRel;
	}

	/**
	 * Returns the commerce price modifier rel with the primary key or throws a <code>NoSuchPriceModifierRelException</code> if it could not be found.
	 *
	 * @param commercePriceModifierRelId the primary key of the commerce price modifier rel
	 * @return the commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a commerce price modifier rel with the primary key could not be found
	 */
	@Override
	public CommercePriceModifierRel findByPrimaryKey(
			long commercePriceModifierRelId)
		throws NoSuchPriceModifierRelException {

		return findByPrimaryKey((Serializable)commercePriceModifierRelId);
	}

	/**
	 * Returns the commerce price modifier rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce price modifier rel
	 * @return the commerce price modifier rel, or <code>null</code> if a commerce price modifier rel with the primary key could not be found
	 */
	@Override
	public CommercePriceModifierRel fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				CommercePriceModifierRel.class)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		CommercePriceModifierRel commercePriceModifierRel = null;

		Session session = null;

		try {
			session = openSession();

			commercePriceModifierRel = (CommercePriceModifierRel)session.get(
				CommercePriceModifierRelImpl.class, primaryKey);

			if (commercePriceModifierRel != null) {
				cacheResult(commercePriceModifierRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return commercePriceModifierRel;
	}

	/**
	 * Returns the commerce price modifier rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePriceModifierRelId the primary key of the commerce price modifier rel
	 * @return the commerce price modifier rel, or <code>null</code> if a commerce price modifier rel with the primary key could not be found
	 */
	@Override
	public CommercePriceModifierRel fetchByPrimaryKey(
		long commercePriceModifierRelId) {

		return fetchByPrimaryKey((Serializable)commercePriceModifierRelId);
	}

	@Override
	public Map<Serializable, CommercePriceModifierRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				CommercePriceModifierRel.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommercePriceModifierRel> map =
			new HashMap<Serializable, CommercePriceModifierRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommercePriceModifierRel commercePriceModifierRel =
				fetchByPrimaryKey(primaryKey);

			if (commercePriceModifierRel != null) {
				map.put(primaryKey, commercePriceModifierRel);
			}

			return map;
		}

		if ((databaseInMaxParameters > 0) &&
			(primaryKeys.size() > databaseInMaxParameters)) {

			Iterator<Serializable> iterator = primaryKeys.iterator();

			while (iterator.hasNext()) {
				Set<Serializable> page = new HashSet<>();

				for (int i = 0;
					 (i < databaseInMaxParameters) && iterator.hasNext(); i++) {

					page.add(iterator.next());
				}

				map.putAll(fetchByPrimaryKeys(page));
			}

			return map;
		}

		StringBundler sb = new StringBundler((primaryKeys.size() * 2) + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
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

			for (CommercePriceModifierRel commercePriceModifierRel :
					(List<CommercePriceModifierRel>)query.list()) {

				map.put(
					commercePriceModifierRel.getPrimaryKeyObj(),
					commercePriceModifierRel);

				cacheResult(commercePriceModifierRel);
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
	 * Returns all the commerce price modifier rels.
	 *
	 * @return the commerce price modifier rels
	 */
	@Override
	public List<CommercePriceModifierRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price modifier rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @return the range of commerce price modifier rels
	 */
	@Override
	public List<CommercePriceModifierRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price modifier rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price modifier rels
	 */
	@Override
	public List<CommercePriceModifierRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price modifier rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price modifier rels
	 */
	@Override
	public List<CommercePriceModifierRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceModifierRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceModifierRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<CommercePriceModifierRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceModifierRel>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEPRICEMODIFIERREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEPRICEMODIFIERREL;

				sql = sql.concat(
					CommercePriceModifierRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommercePriceModifierRel>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Removes all the commerce price modifier rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommercePriceModifierRel commercePriceModifierRel : findAll()) {
			remove(commercePriceModifierRel);
		}
	}

	/**
	 * Returns the number of commerce price modifier rels.
	 *
	 * @return the number of commerce price modifier rels
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceModifierRel.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCEPRICEMODIFIERREL);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
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
		return "commercePriceModifierRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEPRICEMODIFIERREL;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.getOrDefault(
			ctColumnResolutionType, Collections.emptySet());
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return CommercePriceModifierRelModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CommercePriceModifierRel";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("commercePriceModifierId");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("commercePriceModifierRelId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"commercePriceModifierId", "classNameId", "classPK"});
	}

	/**
	 * Initializes the commerce price modifier rel persistence.
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

		_finderPathWithPaginationFindByCommercePriceModifierId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommercePriceModifierId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commercePriceModifierId"}, true);

		_finderPathWithoutPaginationFindByCommercePriceModifierId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommercePriceModifierId",
				new String[] {Long.class.getName()},
				new String[] {"commercePriceModifierId"}, true);

		_finderPathCountByCommercePriceModifierId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommercePriceModifierId",
			new String[] {Long.class.getName()},
			new String[] {"commercePriceModifierId"}, false);

		_finderPathWithPaginationFindByCPM_CN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPM_CN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"commercePriceModifierId", "classNameId"}, true);

		_finderPathWithoutPaginationFindByCPM_CN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPM_CN",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commercePriceModifierId", "classNameId"}, true);

		_finderPathCountByCPM_CN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPM_CN",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commercePriceModifierId", "classNameId"}, false);

		_finderPathWithPaginationFindByCN_CPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCN_CPK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathWithoutPaginationFindByCN_CPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCN_CPK",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathCountByCN_CPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCN_CPK",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, false);

		_finderPathFetchByCPM_CN_CPK = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCPM_CN_CPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"commercePriceModifierId", "classNameId", "classPK"},
			true);

		_finderPathCountByCPM_CN_CPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPM_CN_CPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"commercePriceModifierId", "classNameId", "classPK"},
			false);

		_setCommercePriceModifierRelUtilPersistence(this);
	}

	public void destroy() {
		_setCommercePriceModifierRelUtilPersistence(null);

		entityCache.removeCache(CommercePriceModifierRelImpl.class.getName());
	}

	private void _setCommercePriceModifierRelUtilPersistence(
		CommercePriceModifierRelPersistence
			commercePriceModifierRelPersistence) {

		try {
			Field field = CommercePriceModifierRelUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, commercePriceModifierRelPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@ServiceReference(type = CTPersistenceHelper.class)
	protected CTPersistenceHelper ctPersistenceHelper;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEPRICEMODIFIERREL =
		"SELECT commercePriceModifierRel FROM CommercePriceModifierRel commercePriceModifierRel";

	private static final String _SQL_SELECT_COMMERCEPRICEMODIFIERREL_WHERE =
		"SELECT commercePriceModifierRel FROM CommercePriceModifierRel commercePriceModifierRel WHERE ";

	private static final String _SQL_COUNT_COMMERCEPRICEMODIFIERREL =
		"SELECT COUNT(commercePriceModifierRel) FROM CommercePriceModifierRel commercePriceModifierRel";

	private static final String _SQL_COUNT_COMMERCEPRICEMODIFIERREL_WHERE =
		"SELECT COUNT(commercePriceModifierRel) FROM CommercePriceModifierRel commercePriceModifierRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commercePriceModifierRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommercePriceModifierRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommercePriceModifierRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceModifierRelPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}