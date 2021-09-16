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

import com.liferay.commerce.exception.NoSuchOrderTypeRelException;
import com.liferay.commerce.model.CommerceOrderTypeRel;
import com.liferay.commerce.model.CommerceOrderTypeRelTable;
import com.liferay.commerce.model.impl.CommerceOrderTypeRelImpl;
import com.liferay.commerce.model.impl.CommerceOrderTypeRelModelImpl;
import com.liferay.commerce.service.persistence.CommerceOrderTypeRelPersistence;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the commerce order type rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceOrderTypeRelPersistenceImpl
	extends BasePersistenceImpl<CommerceOrderTypeRel>
	implements CommerceOrderTypeRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceOrderTypeRelUtil</code> to access the commerce order type rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceOrderTypeRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCommerceOrderTypeId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceOrderTypeId;
	private FinderPath _finderPathCountByCommerceOrderTypeId;

	/**
	 * Returns all the commerce order type rels where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce order type rels
	 */
	@Override
	public List<CommerceOrderTypeRel> findByCommerceOrderTypeId(
		long commerceOrderTypeId) {

		return findByCommerceOrderTypeId(
			commerceOrderTypeId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce order type rels where commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @return the range of matching commerce order type rels
	 */
	@Override
	public List<CommerceOrderTypeRel> findByCommerceOrderTypeId(
		long commerceOrderTypeId, int start, int end) {

		return findByCommerceOrderTypeId(commerceOrderTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce order type rels where commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order type rels
	 */
	@Override
	public List<CommerceOrderTypeRel> findByCommerceOrderTypeId(
		long commerceOrderTypeId, int start, int end,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator) {

		return findByCommerceOrderTypeId(
			commerceOrderTypeId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce order type rels where commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order type rels
	 */
	@Override
	public List<CommerceOrderTypeRel> findByCommerceOrderTypeId(
		long commerceOrderTypeId, int start, int end,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceOrderTypeId;
				finderArgs = new Object[] {commerceOrderTypeId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceOrderTypeId;
			finderArgs = new Object[] {
				commerceOrderTypeId, start, end, orderByComparator
			};
		}

		List<CommerceOrderTypeRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceOrderTypeRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceOrderTypeRel commerceOrderTypeRel : list) {
					if (commerceOrderTypeId !=
							commerceOrderTypeRel.getCommerceOrderTypeId()) {

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

			sb.append(_SQL_SELECT_COMMERCEORDERTYPEREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEORDERTYPEID_COMMERCEORDERTYPEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceOrderTypeRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderTypeId);

				list = (List<CommerceOrderTypeRel>)QueryUtil.list(
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
	 * Returns the first commerce order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a matching commerce order type rel could not be found
	 */
	@Override
	public CommerceOrderTypeRel findByCommerceOrderTypeId_First(
			long commerceOrderTypeId,
			OrderByComparator<CommerceOrderTypeRel> orderByComparator)
		throws NoSuchOrderTypeRelException {

		CommerceOrderTypeRel commerceOrderTypeRel =
			fetchByCommerceOrderTypeId_First(
				commerceOrderTypeId, orderByComparator);

		if (commerceOrderTypeRel != null) {
			return commerceOrderTypeRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceOrderTypeId=");
		sb.append(commerceOrderTypeId);

		sb.append("}");

		throw new NoSuchOrderTypeRelException(sb.toString());
	}

	/**
	 * Returns the first commerce order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	@Override
	public CommerceOrderTypeRel fetchByCommerceOrderTypeId_First(
		long commerceOrderTypeId,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator) {

		List<CommerceOrderTypeRel> list = findByCommerceOrderTypeId(
			commerceOrderTypeId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a matching commerce order type rel could not be found
	 */
	@Override
	public CommerceOrderTypeRel findByCommerceOrderTypeId_Last(
			long commerceOrderTypeId,
			OrderByComparator<CommerceOrderTypeRel> orderByComparator)
		throws NoSuchOrderTypeRelException {

		CommerceOrderTypeRel commerceOrderTypeRel =
			fetchByCommerceOrderTypeId_Last(
				commerceOrderTypeId, orderByComparator);

		if (commerceOrderTypeRel != null) {
			return commerceOrderTypeRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceOrderTypeId=");
		sb.append(commerceOrderTypeId);

		sb.append("}");

		throw new NoSuchOrderTypeRelException(sb.toString());
	}

	/**
	 * Returns the last commerce order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	@Override
	public CommerceOrderTypeRel fetchByCommerceOrderTypeId_Last(
		long commerceOrderTypeId,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator) {

		int count = countByCommerceOrderTypeId(commerceOrderTypeId);

		if (count == 0) {
			return null;
		}

		List<CommerceOrderTypeRel> list = findByCommerceOrderTypeId(
			commerceOrderTypeId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce order type rels before and after the current commerce order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeRelId the primary key of the current commerce order type rel
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a commerce order type rel with the primary key could not be found
	 */
	@Override
	public CommerceOrderTypeRel[] findByCommerceOrderTypeId_PrevAndNext(
			long commerceOrderTypeRelId, long commerceOrderTypeId,
			OrderByComparator<CommerceOrderTypeRel> orderByComparator)
		throws NoSuchOrderTypeRelException {

		CommerceOrderTypeRel commerceOrderTypeRel = findByPrimaryKey(
			commerceOrderTypeRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceOrderTypeRel[] array = new CommerceOrderTypeRelImpl[3];

			array[0] = getByCommerceOrderTypeId_PrevAndNext(
				session, commerceOrderTypeRel, commerceOrderTypeId,
				orderByComparator, true);

			array[1] = commerceOrderTypeRel;

			array[2] = getByCommerceOrderTypeId_PrevAndNext(
				session, commerceOrderTypeRel, commerceOrderTypeId,
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

	protected CommerceOrderTypeRel getByCommerceOrderTypeId_PrevAndNext(
		Session session, CommerceOrderTypeRel commerceOrderTypeRel,
		long commerceOrderTypeId,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEORDERTYPEREL_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCEORDERTYPEID_COMMERCEORDERTYPEID_2);

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
			sb.append(CommerceOrderTypeRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceOrderTypeId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceOrderTypeRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceOrderTypeRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce order type rels where commerceOrderTypeId = &#63; from the database.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 */
	@Override
	public void removeByCommerceOrderTypeId(long commerceOrderTypeId) {
		for (CommerceOrderTypeRel commerceOrderTypeRel :
				findByCommerceOrderTypeId(
					commerceOrderTypeId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceOrderTypeRel);
		}
	}

	/**
	 * Returns the number of commerce order type rels where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the number of matching commerce order type rels
	 */
	@Override
	public int countByCommerceOrderTypeId(long commerceOrderTypeId) {
		FinderPath finderPath = _finderPathCountByCommerceOrderTypeId;

		Object[] finderArgs = new Object[] {commerceOrderTypeId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEORDERTYPEREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEORDERTYPEID_COMMERCEORDERTYPEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderTypeId);

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
		_FINDER_COLUMN_COMMERCEORDERTYPEID_COMMERCEORDERTYPEID_2 =
			"commerceOrderTypeRel.commerceOrderTypeId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the commerce order type rels where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce order type rels
	 */
	@Override
	public List<CommerceOrderTypeRel> findByC_C(
		long classNameId, long commerceOrderTypeId) {

		return findByC_C(
			classNameId, commerceOrderTypeId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce order type rels where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @return the range of matching commerce order type rels
	 */
	@Override
	public List<CommerceOrderTypeRel> findByC_C(
		long classNameId, long commerceOrderTypeId, int start, int end) {

		return findByC_C(classNameId, commerceOrderTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce order type rels where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order type rels
	 */
	@Override
	public List<CommerceOrderTypeRel> findByC_C(
		long classNameId, long commerceOrderTypeId, int start, int end,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator) {

		return findByC_C(
			classNameId, commerceOrderTypeId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce order type rels where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order type rels
	 */
	@Override
	public List<CommerceOrderTypeRel> findByC_C(
		long classNameId, long commerceOrderTypeId, int start, int end,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {classNameId, commerceOrderTypeId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, commerceOrderTypeId, start, end, orderByComparator
			};
		}

		List<CommerceOrderTypeRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceOrderTypeRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceOrderTypeRel commerceOrderTypeRel : list) {
					if ((classNameId !=
							commerceOrderTypeRel.getClassNameId()) ||
						(commerceOrderTypeId !=
							commerceOrderTypeRel.getCommerceOrderTypeId())) {

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

			sb.append(_SQL_SELECT_COMMERCEORDERTYPEREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_COMMERCEORDERTYPEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceOrderTypeRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(commerceOrderTypeId);

				list = (List<CommerceOrderTypeRel>)QueryUtil.list(
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
	 * Returns the first commerce order type rel in the ordered set where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a matching commerce order type rel could not be found
	 */
	@Override
	public CommerceOrderTypeRel findByC_C_First(
			long classNameId, long commerceOrderTypeId,
			OrderByComparator<CommerceOrderTypeRel> orderByComparator)
		throws NoSuchOrderTypeRelException {

		CommerceOrderTypeRel commerceOrderTypeRel = fetchByC_C_First(
			classNameId, commerceOrderTypeId, orderByComparator);

		if (commerceOrderTypeRel != null) {
			return commerceOrderTypeRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", commerceOrderTypeId=");
		sb.append(commerceOrderTypeId);

		sb.append("}");

		throw new NoSuchOrderTypeRelException(sb.toString());
	}

	/**
	 * Returns the first commerce order type rel in the ordered set where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	@Override
	public CommerceOrderTypeRel fetchByC_C_First(
		long classNameId, long commerceOrderTypeId,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator) {

		List<CommerceOrderTypeRel> list = findByC_C(
			classNameId, commerceOrderTypeId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce order type rel in the ordered set where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a matching commerce order type rel could not be found
	 */
	@Override
	public CommerceOrderTypeRel findByC_C_Last(
			long classNameId, long commerceOrderTypeId,
			OrderByComparator<CommerceOrderTypeRel> orderByComparator)
		throws NoSuchOrderTypeRelException {

		CommerceOrderTypeRel commerceOrderTypeRel = fetchByC_C_Last(
			classNameId, commerceOrderTypeId, orderByComparator);

		if (commerceOrderTypeRel != null) {
			return commerceOrderTypeRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", commerceOrderTypeId=");
		sb.append(commerceOrderTypeId);

		sb.append("}");

		throw new NoSuchOrderTypeRelException(sb.toString());
	}

	/**
	 * Returns the last commerce order type rel in the ordered set where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	@Override
	public CommerceOrderTypeRel fetchByC_C_Last(
		long classNameId, long commerceOrderTypeId,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator) {

		int count = countByC_C(classNameId, commerceOrderTypeId);

		if (count == 0) {
			return null;
		}

		List<CommerceOrderTypeRel> list = findByC_C(
			classNameId, commerceOrderTypeId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce order type rels before and after the current commerce order type rel in the ordered set where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeRelId the primary key of the current commerce order type rel
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a commerce order type rel with the primary key could not be found
	 */
	@Override
	public CommerceOrderTypeRel[] findByC_C_PrevAndNext(
			long commerceOrderTypeRelId, long classNameId,
			long commerceOrderTypeId,
			OrderByComparator<CommerceOrderTypeRel> orderByComparator)
		throws NoSuchOrderTypeRelException {

		CommerceOrderTypeRel commerceOrderTypeRel = findByPrimaryKey(
			commerceOrderTypeRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceOrderTypeRel[] array = new CommerceOrderTypeRelImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, commerceOrderTypeRel, classNameId, commerceOrderTypeId,
				orderByComparator, true);

			array[1] = commerceOrderTypeRel;

			array[2] = getByC_C_PrevAndNext(
				session, commerceOrderTypeRel, classNameId, commerceOrderTypeId,
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

	protected CommerceOrderTypeRel getByC_C_PrevAndNext(
		Session session, CommerceOrderTypeRel commerceOrderTypeRel,
		long classNameId, long commerceOrderTypeId,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEORDERTYPEREL_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_COMMERCEORDERTYPEID_2);

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
			sb.append(CommerceOrderTypeRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(commerceOrderTypeId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceOrderTypeRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceOrderTypeRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce order type rels where classNameId = &#63; and commerceOrderTypeId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 */
	@Override
	public void removeByC_C(long classNameId, long commerceOrderTypeId) {
		for (CommerceOrderTypeRel commerceOrderTypeRel :
				findByC_C(
					classNameId, commerceOrderTypeId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceOrderTypeRel);
		}
	}

	/**
	 * Returns the number of commerce order type rels where classNameId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the number of matching commerce order type rels
	 */
	@Override
	public int countByC_C(long classNameId, long commerceOrderTypeId) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, commerceOrderTypeId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEORDERTYPEREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_COMMERCEORDERTYPEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(commerceOrderTypeId);

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
		"commerceOrderTypeRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_COMMERCEORDERTYPEID_2 =
		"commerceOrderTypeRel.commerceOrderTypeId = ?";

	private FinderPath _finderPathFetchByC_C_C;
	private FinderPath _finderPathCountByC_C_C;

	/**
	 * Returns the commerce order type rel where classNameId = &#63; and classPK = &#63; and commerceOrderTypeId = &#63; or throws a <code>NoSuchOrderTypeRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a matching commerce order type rel could not be found
	 */
	@Override
	public CommerceOrderTypeRel findByC_C_C(
			long classNameId, long classPK, long commerceOrderTypeId)
		throws NoSuchOrderTypeRelException {

		CommerceOrderTypeRel commerceOrderTypeRel = fetchByC_C_C(
			classNameId, classPK, commerceOrderTypeId);

		if (commerceOrderTypeRel == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append(", commerceOrderTypeId=");
			sb.append(commerceOrderTypeId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchOrderTypeRelException(sb.toString());
		}

		return commerceOrderTypeRel;
	}

	/**
	 * Returns the commerce order type rel where classNameId = &#63; and classPK = &#63; and commerceOrderTypeId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	@Override
	public CommerceOrderTypeRel fetchByC_C_C(
		long classNameId, long classPK, long commerceOrderTypeId) {

		return fetchByC_C_C(classNameId, classPK, commerceOrderTypeId, true);
	}

	/**
	 * Returns the commerce order type rel where classNameId = &#63; and classPK = &#63; and commerceOrderTypeId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	@Override
	public CommerceOrderTypeRel fetchByC_C_C(
		long classNameId, long classPK, long commerceOrderTypeId,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				classNameId, classPK, commerceOrderTypeId
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_C_C, finderArgs);
		}

		if (result instanceof CommerceOrderTypeRel) {
			CommerceOrderTypeRel commerceOrderTypeRel =
				(CommerceOrderTypeRel)result;

			if ((classNameId != commerceOrderTypeRel.getClassNameId()) ||
				(classPK != commerceOrderTypeRel.getClassPK()) ||
				(commerceOrderTypeId !=
					commerceOrderTypeRel.getCommerceOrderTypeId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_COMMERCEORDERTYPEREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_C_COMMERCEORDERTYPEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(commerceOrderTypeId);

				List<CommerceOrderTypeRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C_C, finderArgs, list);
					}
				}
				else {
					CommerceOrderTypeRel commerceOrderTypeRel = list.get(0);

					result = commerceOrderTypeRel;

					cacheResult(commerceOrderTypeRel);
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
			return (CommerceOrderTypeRel)result;
		}
	}

	/**
	 * Removes the commerce order type rel where classNameId = &#63; and classPK = &#63; and commerceOrderTypeId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the commerce order type rel that was removed
	 */
	@Override
	public CommerceOrderTypeRel removeByC_C_C(
			long classNameId, long classPK, long commerceOrderTypeId)
		throws NoSuchOrderTypeRelException {

		CommerceOrderTypeRel commerceOrderTypeRel = findByC_C_C(
			classNameId, classPK, commerceOrderTypeId);

		return remove(commerceOrderTypeRel);
	}

	/**
	 * Returns the number of commerce order type rels where classNameId = &#63; and classPK = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the number of matching commerce order type rels
	 */
	@Override
	public int countByC_C_C(
		long classNameId, long classPK, long commerceOrderTypeId) {

		FinderPath finderPath = _finderPathCountByC_C_C;

		Object[] finderArgs = new Object[] {
			classNameId, classPK, commerceOrderTypeId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCEORDERTYPEREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_C_COMMERCEORDERTYPEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(commerceOrderTypeId);

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

	private static final String _FINDER_COLUMN_C_C_C_CLASSNAMEID_2 =
		"commerceOrderTypeRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 =
		"commerceOrderTypeRel.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_COMMERCEORDERTYPEID_2 =
		"commerceOrderTypeRel.commerceOrderTypeId = ?";

	private FinderPath _finderPathFetchByC_ERC;
	private FinderPath _finderPathCountByC_ERC;

	/**
	 * Returns the commerce order type rel where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchOrderTypeRelException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a matching commerce order type rel could not be found
	 */
	@Override
	public CommerceOrderTypeRel findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchOrderTypeRelException {

		CommerceOrderTypeRel commerceOrderTypeRel = fetchByC_ERC(
			companyId, externalReferenceCode);

		if (commerceOrderTypeRel == null) {
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

			throw new NoSuchOrderTypeRelException(sb.toString());
		}

		return commerceOrderTypeRel;
	}

	/**
	 * Returns the commerce order type rel where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	@Override
	public CommerceOrderTypeRel fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return fetchByC_ERC(companyId, externalReferenceCode, true);
	}

	/**
	 * Returns the commerce order type rel where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	@Override
	public CommerceOrderTypeRel fetchByC_ERC(
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

		if (result instanceof CommerceOrderTypeRel) {
			CommerceOrderTypeRel commerceOrderTypeRel =
				(CommerceOrderTypeRel)result;

			if ((companyId != commerceOrderTypeRel.getCompanyId()) ||
				!Objects.equals(
					externalReferenceCode,
					commerceOrderTypeRel.getExternalReferenceCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCEORDERTYPEREL_WHERE);

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

				List<CommerceOrderTypeRel> list = query.list();

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
								"CommerceOrderTypeRelPersistenceImpl.fetchByC_ERC(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CommerceOrderTypeRel commerceOrderTypeRel = list.get(0);

					result = commerceOrderTypeRel;

					cacheResult(commerceOrderTypeRel);
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
			return (CommerceOrderTypeRel)result;
		}
	}

	/**
	 * Removes the commerce order type rel where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce order type rel that was removed
	 */
	@Override
	public CommerceOrderTypeRel removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchOrderTypeRelException {

		CommerceOrderTypeRel commerceOrderTypeRel = findByC_ERC(
			companyId, externalReferenceCode);

		return remove(commerceOrderTypeRel);
	}

	/**
	 * Returns the number of commerce order type rels where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce order type rels
	 */
	@Override
	public int countByC_ERC(long companyId, String externalReferenceCode) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		FinderPath finderPath = _finderPathCountByC_ERC;

		Object[] finderArgs = new Object[] {companyId, externalReferenceCode};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEORDERTYPEREL_WHERE);

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
		"commerceOrderTypeRel.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2 =
		"commerceOrderTypeRel.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3 =
		"(commerceOrderTypeRel.externalReferenceCode IS NULL OR commerceOrderTypeRel.externalReferenceCode = '')";

	public CommerceOrderTypeRelPersistenceImpl() {
		setModelClass(CommerceOrderTypeRel.class);

		setModelImplClass(CommerceOrderTypeRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceOrderTypeRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce order type rel in the entity cache if it is enabled.
	 *
	 * @param commerceOrderTypeRel the commerce order type rel
	 */
	@Override
	public void cacheResult(CommerceOrderTypeRel commerceOrderTypeRel) {
		entityCache.putResult(
			CommerceOrderTypeRelImpl.class,
			commerceOrderTypeRel.getPrimaryKey(), commerceOrderTypeRel);

		finderCache.putResult(
			_finderPathFetchByC_C_C,
			new Object[] {
				commerceOrderTypeRel.getClassNameId(),
				commerceOrderTypeRel.getClassPK(),
				commerceOrderTypeRel.getCommerceOrderTypeId()
			},
			commerceOrderTypeRel);

		finderCache.putResult(
			_finderPathFetchByC_ERC,
			new Object[] {
				commerceOrderTypeRel.getCompanyId(),
				commerceOrderTypeRel.getExternalReferenceCode()
			},
			commerceOrderTypeRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce order type rels in the entity cache if it is enabled.
	 *
	 * @param commerceOrderTypeRels the commerce order type rels
	 */
	@Override
	public void cacheResult(List<CommerceOrderTypeRel> commerceOrderTypeRels) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceOrderTypeRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceOrderTypeRel commerceOrderTypeRel :
				commerceOrderTypeRels) {

			if (entityCache.getResult(
					CommerceOrderTypeRelImpl.class,
					commerceOrderTypeRel.getPrimaryKey()) == null) {

				cacheResult(commerceOrderTypeRel);
			}
		}
	}

	/**
	 * Clears the cache for all commerce order type rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceOrderTypeRelImpl.class);

		finderCache.clearCache(CommerceOrderTypeRelImpl.class);
	}

	/**
	 * Clears the cache for the commerce order type rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceOrderTypeRel commerceOrderTypeRel) {
		entityCache.removeResult(
			CommerceOrderTypeRelImpl.class, commerceOrderTypeRel);
	}

	@Override
	public void clearCache(List<CommerceOrderTypeRel> commerceOrderTypeRels) {
		for (CommerceOrderTypeRel commerceOrderTypeRel :
				commerceOrderTypeRels) {

			entityCache.removeResult(
				CommerceOrderTypeRelImpl.class, commerceOrderTypeRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceOrderTypeRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceOrderTypeRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceOrderTypeRelModelImpl commerceOrderTypeRelModelImpl) {

		Object[] args = new Object[] {
			commerceOrderTypeRelModelImpl.getClassNameId(),
			commerceOrderTypeRelModelImpl.getClassPK(),
			commerceOrderTypeRelModelImpl.getCommerceOrderTypeId()
		};

		finderCache.putResult(_finderPathCountByC_C_C, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_C_C, args, commerceOrderTypeRelModelImpl);

		args = new Object[] {
			commerceOrderTypeRelModelImpl.getCompanyId(),
			commerceOrderTypeRelModelImpl.getExternalReferenceCode()
		};

		finderCache.putResult(_finderPathCountByC_ERC, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_ERC, args, commerceOrderTypeRelModelImpl);
	}

	/**
	 * Creates a new commerce order type rel with the primary key. Does not add the commerce order type rel to the database.
	 *
	 * @param commerceOrderTypeRelId the primary key for the new commerce order type rel
	 * @return the new commerce order type rel
	 */
	@Override
	public CommerceOrderTypeRel create(long commerceOrderTypeRelId) {
		CommerceOrderTypeRel commerceOrderTypeRel =
			new CommerceOrderTypeRelImpl();

		commerceOrderTypeRel.setNew(true);
		commerceOrderTypeRel.setPrimaryKey(commerceOrderTypeRelId);

		commerceOrderTypeRel.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commerceOrderTypeRel;
	}

	/**
	 * Removes the commerce order type rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceOrderTypeRelId the primary key of the commerce order type rel
	 * @return the commerce order type rel that was removed
	 * @throws NoSuchOrderTypeRelException if a commerce order type rel with the primary key could not be found
	 */
	@Override
	public CommerceOrderTypeRel remove(long commerceOrderTypeRelId)
		throws NoSuchOrderTypeRelException {

		return remove((Serializable)commerceOrderTypeRelId);
	}

	/**
	 * Removes the commerce order type rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce order type rel
	 * @return the commerce order type rel that was removed
	 * @throws NoSuchOrderTypeRelException if a commerce order type rel with the primary key could not be found
	 */
	@Override
	public CommerceOrderTypeRel remove(Serializable primaryKey)
		throws NoSuchOrderTypeRelException {

		Session session = null;

		try {
			session = openSession();

			CommerceOrderTypeRel commerceOrderTypeRel =
				(CommerceOrderTypeRel)session.get(
					CommerceOrderTypeRelImpl.class, primaryKey);

			if (commerceOrderTypeRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOrderTypeRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceOrderTypeRel);
		}
		catch (NoSuchOrderTypeRelException noSuchEntityException) {
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
	protected CommerceOrderTypeRel removeImpl(
		CommerceOrderTypeRel commerceOrderTypeRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceOrderTypeRel)) {
				commerceOrderTypeRel = (CommerceOrderTypeRel)session.get(
					CommerceOrderTypeRelImpl.class,
					commerceOrderTypeRel.getPrimaryKeyObj());
			}

			if (commerceOrderTypeRel != null) {
				session.delete(commerceOrderTypeRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceOrderTypeRel != null) {
			clearCache(commerceOrderTypeRel);
		}

		return commerceOrderTypeRel;
	}

	@Override
	public CommerceOrderTypeRel updateImpl(
		CommerceOrderTypeRel commerceOrderTypeRel) {

		boolean isNew = commerceOrderTypeRel.isNew();

		if (!(commerceOrderTypeRel instanceof CommerceOrderTypeRelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceOrderTypeRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceOrderTypeRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceOrderTypeRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceOrderTypeRel implementation " +
					commerceOrderTypeRel.getClass());
		}

		CommerceOrderTypeRelModelImpl commerceOrderTypeRelModelImpl =
			(CommerceOrderTypeRelModelImpl)commerceOrderTypeRel;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceOrderTypeRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceOrderTypeRel.setCreateDate(date);
			}
			else {
				commerceOrderTypeRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceOrderTypeRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceOrderTypeRel.setModifiedDate(date);
			}
			else {
				commerceOrderTypeRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceOrderTypeRel);
			}
			else {
				commerceOrderTypeRel = (CommerceOrderTypeRel)session.merge(
					commerceOrderTypeRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceOrderTypeRelImpl.class, commerceOrderTypeRelModelImpl,
			false, true);

		cacheUniqueFindersCache(commerceOrderTypeRelModelImpl);

		if (isNew) {
			commerceOrderTypeRel.setNew(false);
		}

		commerceOrderTypeRel.resetOriginalValues();

		return commerceOrderTypeRel;
	}

	/**
	 * Returns the commerce order type rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce order type rel
	 * @return the commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a commerce order type rel with the primary key could not be found
	 */
	@Override
	public CommerceOrderTypeRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchOrderTypeRelException {

		CommerceOrderTypeRel commerceOrderTypeRel = fetchByPrimaryKey(
			primaryKey);

		if (commerceOrderTypeRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOrderTypeRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceOrderTypeRel;
	}

	/**
	 * Returns the commerce order type rel with the primary key or throws a <code>NoSuchOrderTypeRelException</code> if it could not be found.
	 *
	 * @param commerceOrderTypeRelId the primary key of the commerce order type rel
	 * @return the commerce order type rel
	 * @throws NoSuchOrderTypeRelException if a commerce order type rel with the primary key could not be found
	 */
	@Override
	public CommerceOrderTypeRel findByPrimaryKey(long commerceOrderTypeRelId)
		throws NoSuchOrderTypeRelException {

		return findByPrimaryKey((Serializable)commerceOrderTypeRelId);
	}

	/**
	 * Returns the commerce order type rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceOrderTypeRelId the primary key of the commerce order type rel
	 * @return the commerce order type rel, or <code>null</code> if a commerce order type rel with the primary key could not be found
	 */
	@Override
	public CommerceOrderTypeRel fetchByPrimaryKey(long commerceOrderTypeRelId) {
		return fetchByPrimaryKey((Serializable)commerceOrderTypeRelId);
	}

	/**
	 * Returns all the commerce order type rels.
	 *
	 * @return the commerce order type rels
	 */
	@Override
	public List<CommerceOrderTypeRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @return the range of commerce order type rels
	 */
	@Override
	public List<CommerceOrderTypeRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce order type rels
	 */
	@Override
	public List<CommerceOrderTypeRel> findAll(
		int start, int end,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce order type rels
	 */
	@Override
	public List<CommerceOrderTypeRel> findAll(
		int start, int end,
		OrderByComparator<CommerceOrderTypeRel> orderByComparator,
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

		List<CommerceOrderTypeRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceOrderTypeRel>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEORDERTYPEREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEORDERTYPEREL;

				sql = sql.concat(CommerceOrderTypeRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceOrderTypeRel>)QueryUtil.list(
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
	 * Removes all the commerce order type rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceOrderTypeRel commerceOrderTypeRel : findAll()) {
			remove(commerceOrderTypeRel);
		}
	}

	/**
	 * Returns the number of commerce order type rels.
	 *
	 * @return the number of commerce order type rels
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
					_SQL_COUNT_COMMERCEORDERTYPEREL);

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
		return "commerceOrderTypeRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEORDERTYPEREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceOrderTypeRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce order type rel persistence.
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

		_finderPathWithPaginationFindByCommerceOrderTypeId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceOrderTypeId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceOrderTypeId"}, true);

		_finderPathWithoutPaginationFindByCommerceOrderTypeId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceOrderTypeId", new String[] {Long.class.getName()},
			new String[] {"commerceOrderTypeId"}, true);

		_finderPathCountByCommerceOrderTypeId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceOrderTypeId", new String[] {Long.class.getName()},
			new String[] {"commerceOrderTypeId"}, false);

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "commerceOrderTypeId"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "commerceOrderTypeId"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "commerceOrderTypeId"}, false);

		_finderPathFetchByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"classNameId", "classPK", "commerceOrderTypeId"},
			true);

		_finderPathCountByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"classNameId", "classPK", "commerceOrderTypeId"},
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
		entityCache.removeCache(CommerceOrderTypeRelImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEORDERTYPEREL =
		"SELECT commerceOrderTypeRel FROM CommerceOrderTypeRel commerceOrderTypeRel";

	private static final String _SQL_SELECT_COMMERCEORDERTYPEREL_WHERE =
		"SELECT commerceOrderTypeRel FROM CommerceOrderTypeRel commerceOrderTypeRel WHERE ";

	private static final String _SQL_COUNT_COMMERCEORDERTYPEREL =
		"SELECT COUNT(commerceOrderTypeRel) FROM CommerceOrderTypeRel commerceOrderTypeRel";

	private static final String _SQL_COUNT_COMMERCEORDERTYPEREL_WHERE =
		"SELECT COUNT(commerceOrderTypeRel) FROM CommerceOrderTypeRel commerceOrderTypeRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceOrderTypeRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceOrderTypeRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceOrderTypeRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderTypeRelPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}