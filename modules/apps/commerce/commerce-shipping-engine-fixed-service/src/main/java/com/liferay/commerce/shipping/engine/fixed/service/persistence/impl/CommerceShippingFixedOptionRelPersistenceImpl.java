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

import com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionRelException;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRelTable;
import com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionRelImpl;
import com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionRelModelImpl;
import com.liferay.commerce.shipping.engine.fixed.service.persistence.CommerceShippingFixedOptionRelPersistence;
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
 * The persistence implementation for the commerce shipping fixed option rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceShippingFixedOptionRelPersistenceImpl
	extends BasePersistenceImpl<CommerceShippingFixedOptionRel>
	implements CommerceShippingFixedOptionRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceShippingFixedOptionRelUtil</code> to access the commerce shipping fixed option rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceShippingFixedOptionRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCommerceShippingMethodId;
	private FinderPath
		_finderPathWithoutPaginationFindByCommerceShippingMethodId;
	private FinderPath _finderPathCountByCommerceShippingMethodId;

	/**
	 * Returns all the commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @return the matching commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId) {

		return findByCommerceShippingMethodId(
			commerceShippingMethodId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @return the range of matching commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end) {

		return findByCommerceShippingMethodId(
			commerceShippingMethodId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator) {

		return findByCommerceShippingMethodId(
			commerceShippingMethodId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceShippingMethodId;
				finderArgs = new Object[] {commerceShippingMethodId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByCommerceShippingMethodId;
			finderArgs = new Object[] {
				commerceShippingMethodId, start, end, orderByComparator
			};
		}

		List<CommerceShippingFixedOptionRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceShippingFixedOptionRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceShippingFixedOptionRel
						commerceShippingFixedOptionRel : list) {

					if (commerceShippingMethodId !=
							commerceShippingFixedOptionRel.
								getCommerceShippingMethodId()) {

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

			sb.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONREL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCESHIPPINGMETHODID_COMMERCESHIPPINGMETHODID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceShippingFixedOptionRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceShippingMethodId);

				list = (List<CommerceShippingFixedOptionRel>)QueryUtil.list(
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
	 * Returns the first commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a matching commerce shipping fixed option rel could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel findByCommerceShippingMethodId_First(
			long commerceShippingMethodId,
			OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator)
		throws NoSuchShippingFixedOptionRelException {

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel =
			fetchByCommerceShippingMethodId_First(
				commerceShippingMethodId, orderByComparator);

		if (commerceShippingFixedOptionRel != null) {
			return commerceShippingFixedOptionRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceShippingMethodId=");
		sb.append(commerceShippingMethodId);

		sb.append("}");

		throw new NoSuchShippingFixedOptionRelException(sb.toString());
	}

	/**
	 * Returns the first commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option rel, or <code>null</code> if a matching commerce shipping fixed option rel could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel fetchByCommerceShippingMethodId_First(
		long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator) {

		List<CommerceShippingFixedOptionRel> list =
			findByCommerceShippingMethodId(
				commerceShippingMethodId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a matching commerce shipping fixed option rel could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel findByCommerceShippingMethodId_Last(
			long commerceShippingMethodId,
			OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator)
		throws NoSuchShippingFixedOptionRelException {

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel =
			fetchByCommerceShippingMethodId_Last(
				commerceShippingMethodId, orderByComparator);

		if (commerceShippingFixedOptionRel != null) {
			return commerceShippingFixedOptionRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceShippingMethodId=");
		sb.append(commerceShippingMethodId);

		sb.append("}");

		throw new NoSuchShippingFixedOptionRelException(sb.toString());
	}

	/**
	 * Returns the last commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option rel, or <code>null</code> if a matching commerce shipping fixed option rel could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel fetchByCommerceShippingMethodId_Last(
		long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator) {

		int count = countByCommerceShippingMethodId(commerceShippingMethodId);

		if (count == 0) {
			return null;
		}

		List<CommerceShippingFixedOptionRel> list =
			findByCommerceShippingMethodId(
				commerceShippingMethodId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce shipping fixed option rels before and after the current commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingFixedOptionRelId the primary key of the current commerce shipping fixed option rel
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel[]
			findByCommerceShippingMethodId_PrevAndNext(
				long commerceShippingFixedOptionRelId,
				long commerceShippingMethodId,
				OrderByComparator<CommerceShippingFixedOptionRel>
					orderByComparator)
		throws NoSuchShippingFixedOptionRelException {

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel =
			findByPrimaryKey(commerceShippingFixedOptionRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceShippingFixedOptionRel[] array =
				new CommerceShippingFixedOptionRelImpl[3];

			array[0] = getByCommerceShippingMethodId_PrevAndNext(
				session, commerceShippingFixedOptionRel,
				commerceShippingMethodId, orderByComparator, true);

			array[1] = commerceShippingFixedOptionRel;

			array[2] = getByCommerceShippingMethodId_PrevAndNext(
				session, commerceShippingFixedOptionRel,
				commerceShippingMethodId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceShippingFixedOptionRel
		getByCommerceShippingMethodId_PrevAndNext(
			Session session,
			CommerceShippingFixedOptionRel commerceShippingFixedOptionRel,
			long commerceShippingMethodId,
			OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONREL_WHERE);

		sb.append(
			_FINDER_COLUMN_COMMERCESHIPPINGMETHODID_COMMERCESHIPPINGMETHODID_2);

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
			sb.append(CommerceShippingFixedOptionRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceShippingMethodId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceShippingFixedOptionRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceShippingFixedOptionRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce shipping fixed option rels where commerceShippingMethodId = &#63; from the database.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 */
	@Override
	public void removeByCommerceShippingMethodId(
		long commerceShippingMethodId) {

		for (CommerceShippingFixedOptionRel commerceShippingFixedOptionRel :
				findByCommerceShippingMethodId(
					commerceShippingMethodId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceShippingFixedOptionRel);
		}
	}

	/**
	 * Returns the number of commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @return the number of matching commerce shipping fixed option rels
	 */
	@Override
	public int countByCommerceShippingMethodId(long commerceShippingMethodId) {
		FinderPath finderPath = _finderPathCountByCommerceShippingMethodId;

		Object[] finderArgs = new Object[] {commerceShippingMethodId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCESHIPPINGFIXEDOPTIONREL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCESHIPPINGMETHODID_COMMERCESHIPPINGMETHODID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceShippingMethodId);

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
		_FINDER_COLUMN_COMMERCESHIPPINGMETHODID_COMMERCESHIPPINGMETHODID_2 =
			"commerceShippingFixedOptionRel.commerceShippingMethodId = ?";

	private FinderPath
		_finderPathWithPaginationFindByCommerceShippingFixedOptionId;
	private FinderPath
		_finderPathWithoutPaginationFindByCommerceShippingFixedOptionId;
	private FinderPath _finderPathCountByCommerceShippingFixedOptionId;

	/**
	 * Returns all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the matching commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel>
		findByCommerceShippingFixedOptionId(
			long commerceShippingFixedOptionId) {

		return findByCommerceShippingFixedOptionId(
			commerceShippingFixedOptionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @return the range of matching commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel>
		findByCommerceShippingFixedOptionId(
			long commerceShippingFixedOptionId, int start, int end) {

		return findByCommerceShippingFixedOptionId(
			commerceShippingFixedOptionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel>
		findByCommerceShippingFixedOptionId(
			long commerceShippingFixedOptionId, int start, int end,
			OrderByComparator<CommerceShippingFixedOptionRel>
				orderByComparator) {

		return findByCommerceShippingFixedOptionId(
			commerceShippingFixedOptionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel>
		findByCommerceShippingFixedOptionId(
			long commerceShippingFixedOptionId, int start, int end,
			OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator,
			boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceShippingFixedOptionId;
				finderArgs = new Object[] {commerceShippingFixedOptionId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByCommerceShippingFixedOptionId;
			finderArgs = new Object[] {
				commerceShippingFixedOptionId, start, end, orderByComparator
			};
		}

		List<CommerceShippingFixedOptionRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceShippingFixedOptionRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceShippingFixedOptionRel
						commerceShippingFixedOptionRel : list) {

					if (commerceShippingFixedOptionId !=
							commerceShippingFixedOptionRel.
								getCommerceShippingFixedOptionId()) {

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

			sb.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONREL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCESHIPPINGFIXEDOPTIONID_COMMERCESHIPPINGFIXEDOPTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceShippingFixedOptionRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceShippingFixedOptionId);

				list = (List<CommerceShippingFixedOptionRel>)QueryUtil.list(
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
	 * Returns the first commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a matching commerce shipping fixed option rel could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel
			findByCommerceShippingFixedOptionId_First(
				long commerceShippingFixedOptionId,
				OrderByComparator<CommerceShippingFixedOptionRel>
					orderByComparator)
		throws NoSuchShippingFixedOptionRelException {

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel =
			fetchByCommerceShippingFixedOptionId_First(
				commerceShippingFixedOptionId, orderByComparator);

		if (commerceShippingFixedOptionRel != null) {
			return commerceShippingFixedOptionRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceShippingFixedOptionId=");
		sb.append(commerceShippingFixedOptionId);

		sb.append("}");

		throw new NoSuchShippingFixedOptionRelException(sb.toString());
	}

	/**
	 * Returns the first commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option rel, or <code>null</code> if a matching commerce shipping fixed option rel could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel
		fetchByCommerceShippingFixedOptionId_First(
			long commerceShippingFixedOptionId,
			OrderByComparator<CommerceShippingFixedOptionRel>
				orderByComparator) {

		List<CommerceShippingFixedOptionRel> list =
			findByCommerceShippingFixedOptionId(
				commerceShippingFixedOptionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a matching commerce shipping fixed option rel could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel
			findByCommerceShippingFixedOptionId_Last(
				long commerceShippingFixedOptionId,
				OrderByComparator<CommerceShippingFixedOptionRel>
					orderByComparator)
		throws NoSuchShippingFixedOptionRelException {

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel =
			fetchByCommerceShippingFixedOptionId_Last(
				commerceShippingFixedOptionId, orderByComparator);

		if (commerceShippingFixedOptionRel != null) {
			return commerceShippingFixedOptionRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceShippingFixedOptionId=");
		sb.append(commerceShippingFixedOptionId);

		sb.append("}");

		throw new NoSuchShippingFixedOptionRelException(sb.toString());
	}

	/**
	 * Returns the last commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option rel, or <code>null</code> if a matching commerce shipping fixed option rel could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel
		fetchByCommerceShippingFixedOptionId_Last(
			long commerceShippingFixedOptionId,
			OrderByComparator<CommerceShippingFixedOptionRel>
				orderByComparator) {

		int count = countByCommerceShippingFixedOptionId(
			commerceShippingFixedOptionId);

		if (count == 0) {
			return null;
		}

		List<CommerceShippingFixedOptionRel> list =
			findByCommerceShippingFixedOptionId(
				commerceShippingFixedOptionId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce shipping fixed option rels before and after the current commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionRelId the primary key of the current commerce shipping fixed option rel
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel[]
			findByCommerceShippingFixedOptionId_PrevAndNext(
				long commerceShippingFixedOptionRelId,
				long commerceShippingFixedOptionId,
				OrderByComparator<CommerceShippingFixedOptionRel>
					orderByComparator)
		throws NoSuchShippingFixedOptionRelException {

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel =
			findByPrimaryKey(commerceShippingFixedOptionRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceShippingFixedOptionRel[] array =
				new CommerceShippingFixedOptionRelImpl[3];

			array[0] = getByCommerceShippingFixedOptionId_PrevAndNext(
				session, commerceShippingFixedOptionRel,
				commerceShippingFixedOptionId, orderByComparator, true);

			array[1] = commerceShippingFixedOptionRel;

			array[2] = getByCommerceShippingFixedOptionId_PrevAndNext(
				session, commerceShippingFixedOptionRel,
				commerceShippingFixedOptionId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceShippingFixedOptionRel
		getByCommerceShippingFixedOptionId_PrevAndNext(
			Session session,
			CommerceShippingFixedOptionRel commerceShippingFixedOptionRel,
			long commerceShippingFixedOptionId,
			OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONREL_WHERE);

		sb.append(
			_FINDER_COLUMN_COMMERCESHIPPINGFIXEDOPTIONID_COMMERCESHIPPINGFIXEDOPTIONID_2);

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
			sb.append(CommerceShippingFixedOptionRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceShippingFixedOptionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceShippingFixedOptionRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceShippingFixedOptionRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63; from the database.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 */
	@Override
	public void removeByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {

		for (CommerceShippingFixedOptionRel commerceShippingFixedOptionRel :
				findByCommerceShippingFixedOptionId(
					commerceShippingFixedOptionId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceShippingFixedOptionRel);
		}
	}

	/**
	 * Returns the number of commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the number of matching commerce shipping fixed option rels
	 */
	@Override
	public int countByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {

		FinderPath finderPath = _finderPathCountByCommerceShippingFixedOptionId;

		Object[] finderArgs = new Object[] {commerceShippingFixedOptionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCESHIPPINGFIXEDOPTIONREL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCESHIPPINGFIXEDOPTIONID_COMMERCESHIPPINGFIXEDOPTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceShippingFixedOptionId);

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
		_FINDER_COLUMN_COMMERCESHIPPINGFIXEDOPTIONID_COMMERCESHIPPINGFIXEDOPTIONID_2 =
			"commerceShippingFixedOptionRel.commerceShippingFixedOptionId = ?";

	public CommerceShippingFixedOptionRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"commerceShippingFixedOptionRelId", "CShippingFixedOptionRelId");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceShippingFixedOptionRel.class);

		setModelImplClass(CommerceShippingFixedOptionRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceShippingFixedOptionRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce shipping fixed option rel in the entity cache if it is enabled.
	 *
	 * @param commerceShippingFixedOptionRel the commerce shipping fixed option rel
	 */
	@Override
	public void cacheResult(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {

		entityCache.putResult(
			CommerceShippingFixedOptionRelImpl.class,
			commerceShippingFixedOptionRel.getPrimaryKey(),
			commerceShippingFixedOptionRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce shipping fixed option rels in the entity cache if it is enabled.
	 *
	 * @param commerceShippingFixedOptionRels the commerce shipping fixed option rels
	 */
	@Override
	public void cacheResult(
		List<CommerceShippingFixedOptionRel> commerceShippingFixedOptionRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceShippingFixedOptionRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceShippingFixedOptionRel commerceShippingFixedOptionRel :
				commerceShippingFixedOptionRels) {

			if (entityCache.getResult(
					CommerceShippingFixedOptionRelImpl.class,
					commerceShippingFixedOptionRel.getPrimaryKey()) == null) {

				cacheResult(commerceShippingFixedOptionRel);
			}
		}
	}

	/**
	 * Clears the cache for all commerce shipping fixed option rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceShippingFixedOptionRelImpl.class);

		finderCache.clearCache(CommerceShippingFixedOptionRelImpl.class);
	}

	/**
	 * Clears the cache for the commerce shipping fixed option rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {

		entityCache.removeResult(
			CommerceShippingFixedOptionRelImpl.class,
			commerceShippingFixedOptionRel);
	}

	@Override
	public void clearCache(
		List<CommerceShippingFixedOptionRel> commerceShippingFixedOptionRels) {

		for (CommerceShippingFixedOptionRel commerceShippingFixedOptionRel :
				commerceShippingFixedOptionRels) {

			entityCache.removeResult(
				CommerceShippingFixedOptionRelImpl.class,
				commerceShippingFixedOptionRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceShippingFixedOptionRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceShippingFixedOptionRelImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new commerce shipping fixed option rel with the primary key. Does not add the commerce shipping fixed option rel to the database.
	 *
	 * @param commerceShippingFixedOptionRelId the primary key for the new commerce shipping fixed option rel
	 * @return the new commerce shipping fixed option rel
	 */
	@Override
	public CommerceShippingFixedOptionRel create(
		long commerceShippingFixedOptionRelId) {

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel =
			new CommerceShippingFixedOptionRelImpl();

		commerceShippingFixedOptionRel.setNew(true);
		commerceShippingFixedOptionRel.setPrimaryKey(
			commerceShippingFixedOptionRelId);

		commerceShippingFixedOptionRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceShippingFixedOptionRel;
	}

	/**
	 * Removes the commerce shipping fixed option rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceShippingFixedOptionRelId the primary key of the commerce shipping fixed option rel
	 * @return the commerce shipping fixed option rel that was removed
	 * @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel remove(
			long commerceShippingFixedOptionRelId)
		throws NoSuchShippingFixedOptionRelException {

		return remove((Serializable)commerceShippingFixedOptionRelId);
	}

	/**
	 * Removes the commerce shipping fixed option rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce shipping fixed option rel
	 * @return the commerce shipping fixed option rel that was removed
	 * @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel remove(Serializable primaryKey)
		throws NoSuchShippingFixedOptionRelException {

		Session session = null;

		try {
			session = openSession();

			CommerceShippingFixedOptionRel commerceShippingFixedOptionRel =
				(CommerceShippingFixedOptionRel)session.get(
					CommerceShippingFixedOptionRelImpl.class, primaryKey);

			if (commerceShippingFixedOptionRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchShippingFixedOptionRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceShippingFixedOptionRel);
		}
		catch (NoSuchShippingFixedOptionRelException noSuchEntityException) {
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
	protected CommerceShippingFixedOptionRel removeImpl(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceShippingFixedOptionRel)) {
				commerceShippingFixedOptionRel =
					(CommerceShippingFixedOptionRel)session.get(
						CommerceShippingFixedOptionRelImpl.class,
						commerceShippingFixedOptionRel.getPrimaryKeyObj());
			}

			if (commerceShippingFixedOptionRel != null) {
				session.delete(commerceShippingFixedOptionRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceShippingFixedOptionRel != null) {
			clearCache(commerceShippingFixedOptionRel);
		}

		return commerceShippingFixedOptionRel;
	}

	@Override
	public CommerceShippingFixedOptionRel updateImpl(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {

		boolean isNew = commerceShippingFixedOptionRel.isNew();

		if (!(commerceShippingFixedOptionRel instanceof
				CommerceShippingFixedOptionRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceShippingFixedOptionRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceShippingFixedOptionRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceShippingFixedOptionRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceShippingFixedOptionRel implementation " +
					commerceShippingFixedOptionRel.getClass());
		}

		CommerceShippingFixedOptionRelModelImpl
			commerceShippingFixedOptionRelModelImpl =
				(CommerceShippingFixedOptionRelModelImpl)
					commerceShippingFixedOptionRel;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceShippingFixedOptionRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceShippingFixedOptionRel.setCreateDate(date);
			}
			else {
				commerceShippingFixedOptionRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceShippingFixedOptionRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceShippingFixedOptionRel.setModifiedDate(date);
			}
			else {
				commerceShippingFixedOptionRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceShippingFixedOptionRel);
			}
			else {
				commerceShippingFixedOptionRel =
					(CommerceShippingFixedOptionRel)session.merge(
						commerceShippingFixedOptionRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceShippingFixedOptionRelImpl.class,
			commerceShippingFixedOptionRelModelImpl, false, true);

		if (isNew) {
			commerceShippingFixedOptionRel.setNew(false);
		}

		commerceShippingFixedOptionRel.resetOriginalValues();

		return commerceShippingFixedOptionRel;
	}

	/**
	 * Returns the commerce shipping fixed option rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce shipping fixed option rel
	 * @return the commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchShippingFixedOptionRelException {

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel =
			fetchByPrimaryKey(primaryKey);

		if (commerceShippingFixedOptionRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchShippingFixedOptionRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceShippingFixedOptionRel;
	}

	/**
	 * Returns the commerce shipping fixed option rel with the primary key or throws a <code>NoSuchShippingFixedOptionRelException</code> if it could not be found.
	 *
	 * @param commerceShippingFixedOptionRelId the primary key of the commerce shipping fixed option rel
	 * @return the commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel findByPrimaryKey(
			long commerceShippingFixedOptionRelId)
		throws NoSuchShippingFixedOptionRelException {

		return findByPrimaryKey((Serializable)commerceShippingFixedOptionRelId);
	}

	/**
	 * Returns the commerce shipping fixed option rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceShippingFixedOptionRelId the primary key of the commerce shipping fixed option rel
	 * @return the commerce shipping fixed option rel, or <code>null</code> if a commerce shipping fixed option rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionRel fetchByPrimaryKey(
		long commerceShippingFixedOptionRelId) {

		return fetchByPrimaryKey(
			(Serializable)commerceShippingFixedOptionRelId);
	}

	/**
	 * Returns all the commerce shipping fixed option rels.
	 *
	 * @return the commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipping fixed option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @return the range of commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel> findAll(
		int start, int end,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce shipping fixed option rels
	 */
	@Override
	public List<CommerceShippingFixedOptionRel> findAll(
		int start, int end,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator,
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

		List<CommerceShippingFixedOptionRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceShippingFixedOptionRel>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONREL;

				sql = sql.concat(
					CommerceShippingFixedOptionRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceShippingFixedOptionRel>)QueryUtil.list(
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
	 * Removes all the commerce shipping fixed option rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceShippingFixedOptionRel commerceShippingFixedOptionRel :
				findAll()) {

			remove(commerceShippingFixedOptionRel);
		}
	}

	/**
	 * Returns the number of commerce shipping fixed option rels.
	 *
	 * @return the number of commerce shipping fixed option rels
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
					_SQL_COUNT_COMMERCESHIPPINGFIXEDOPTIONREL);

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
		return "CShippingFixedOptionRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceShippingFixedOptionRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce shipping fixed option rel persistence.
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

		_finderPathWithPaginationFindByCommerceShippingMethodId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommerceShippingMethodId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"commerceShippingMethodId"}, true);

		_finderPathWithoutPaginationFindByCommerceShippingMethodId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceShippingMethodId",
				new String[] {Long.class.getName()},
				new String[] {"commerceShippingMethodId"}, true);

		_finderPathCountByCommerceShippingMethodId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceShippingMethodId",
			new String[] {Long.class.getName()},
			new String[] {"commerceShippingMethodId"}, false);

		_finderPathWithPaginationFindByCommerceShippingFixedOptionId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommerceShippingFixedOptionId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"commerceShippingFixedOptionId"}, true);

		_finderPathWithoutPaginationFindByCommerceShippingFixedOptionId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceShippingFixedOptionId",
				new String[] {Long.class.getName()},
				new String[] {"commerceShippingFixedOptionId"}, true);

		_finderPathCountByCommerceShippingFixedOptionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceShippingFixedOptionId",
			new String[] {Long.class.getName()},
			new String[] {"commerceShippingFixedOptionId"}, false);
	}

	public void destroy() {
		entityCache.removeCache(
			CommerceShippingFixedOptionRelImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONREL =
		"SELECT commerceShippingFixedOptionRel FROM CommerceShippingFixedOptionRel commerceShippingFixedOptionRel";

	private static final String
		_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONREL_WHERE =
			"SELECT commerceShippingFixedOptionRel FROM CommerceShippingFixedOptionRel commerceShippingFixedOptionRel WHERE ";

	private static final String _SQL_COUNT_COMMERCESHIPPINGFIXEDOPTIONREL =
		"SELECT COUNT(commerceShippingFixedOptionRel) FROM CommerceShippingFixedOptionRel commerceShippingFixedOptionRel";

	private static final String
		_SQL_COUNT_COMMERCESHIPPINGFIXEDOPTIONREL_WHERE =
			"SELECT COUNT(commerceShippingFixedOptionRel) FROM CommerceShippingFixedOptionRel commerceShippingFixedOptionRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceShippingFixedOptionRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceShippingFixedOptionRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceShippingFixedOptionRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceShippingFixedOptionRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"commerceShippingFixedOptionRelId"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}