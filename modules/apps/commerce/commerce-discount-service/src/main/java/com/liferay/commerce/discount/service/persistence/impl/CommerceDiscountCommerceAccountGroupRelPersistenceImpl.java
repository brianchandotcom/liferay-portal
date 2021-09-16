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

import com.liferay.commerce.discount.exception.NoSuchDiscountCommerceAccountGroupRelException;
import com.liferay.commerce.discount.model.CommerceDiscountCommerceAccountGroupRel;
import com.liferay.commerce.discount.model.CommerceDiscountCommerceAccountGroupRelTable;
import com.liferay.commerce.discount.model.impl.CommerceDiscountCommerceAccountGroupRelImpl;
import com.liferay.commerce.discount.model.impl.CommerceDiscountCommerceAccountGroupRelModelImpl;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountCommerceAccountGroupRelPersistence;
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
 * The persistence implementation for the commerce discount commerce account group rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceDiscountCommerceAccountGroupRelPersistenceImpl
	extends BasePersistenceImpl<CommerceDiscountCommerceAccountGroupRel>
	implements CommerceDiscountCommerceAccountGroupRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceDiscountCommerceAccountGroupRelUtil</code> to access the commerce discount commerce account group rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceDiscountCommerceAccountGroupRelImpl.class.getName();

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
	 * Returns all the commerce discount commerce account group rels where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount commerce account group rels
	 */
	@Override
	public List<CommerceDiscountCommerceAccountGroupRel>
		findByCommerceDiscountId(long commerceDiscountId) {

		return findByCommerceDiscountId(
			commerceDiscountId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount commerce account group rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount commerce account group rels
	 * @param end the upper bound of the range of commerce discount commerce account group rels (not inclusive)
	 * @return the range of matching commerce discount commerce account group rels
	 */
	@Override
	public List<CommerceDiscountCommerceAccountGroupRel>
		findByCommerceDiscountId(long commerceDiscountId, int start, int end) {

		return findByCommerceDiscountId(commerceDiscountId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount commerce account group rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount commerce account group rels
	 * @param end the upper bound of the range of commerce discount commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount commerce account group rels
	 */
	@Override
	public List<CommerceDiscountCommerceAccountGroupRel>
		findByCommerceDiscountId(
			long commerceDiscountId, int start, int end,
			OrderByComparator<CommerceDiscountCommerceAccountGroupRel>
				orderByComparator) {

		return findByCommerceDiscountId(
			commerceDiscountId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount commerce account group rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount commerce account group rels
	 * @param end the upper bound of the range of commerce discount commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount commerce account group rels
	 */
	@Override
	public List<CommerceDiscountCommerceAccountGroupRel>
		findByCommerceDiscountId(
			long commerceDiscountId, int start, int end,
			OrderByComparator<CommerceDiscountCommerceAccountGroupRel>
				orderByComparator,
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

		List<CommerceDiscountCommerceAccountGroupRel> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceDiscountCommerceAccountGroupRel>)
					finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDiscountCommerceAccountGroupRel
						commerceDiscountCommerceAccountGroupRel : list) {

					if (commerceDiscountId !=
							commerceDiscountCommerceAccountGroupRel.
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

			sb.append(
				_SQL_SELECT_COMMERCEDISCOUNTCOMMERCEACCOUNTGROUPREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEDISCOUNTID_COMMERCEDISCOUNTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceDiscountCommerceAccountGroupRelModelImpl.
						ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceDiscountId);

				list =
					(List<CommerceDiscountCommerceAccountGroupRel>)
						QueryUtil.list(query, getDialect(), start, end);

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
	 * Returns the first commerce discount commerce account group rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount commerce account group rel
	 * @throws NoSuchDiscountCommerceAccountGroupRelException if a matching commerce discount commerce account group rel could not be found
	 */
	@Override
	public CommerceDiscountCommerceAccountGroupRel
			findByCommerceDiscountId_First(
				long commerceDiscountId,
				OrderByComparator<CommerceDiscountCommerceAccountGroupRel>
					orderByComparator)
		throws NoSuchDiscountCommerceAccountGroupRelException {

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel =
				fetchByCommerceDiscountId_First(
					commerceDiscountId, orderByComparator);

		if (commerceDiscountCommerceAccountGroupRel != null) {
			return commerceDiscountCommerceAccountGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceDiscountId=");
		sb.append(commerceDiscountId);

		sb.append("}");

		throw new NoSuchDiscountCommerceAccountGroupRelException(sb.toString());
	}

	/**
	 * Returns the first commerce discount commerce account group rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount commerce account group rel, or <code>null</code> if a matching commerce discount commerce account group rel could not be found
	 */
	@Override
	public CommerceDiscountCommerceAccountGroupRel
		fetchByCommerceDiscountId_First(
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountCommerceAccountGroupRel>
				orderByComparator) {

		List<CommerceDiscountCommerceAccountGroupRel> list =
			findByCommerceDiscountId(
				commerceDiscountId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce discount commerce account group rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount commerce account group rel
	 * @throws NoSuchDiscountCommerceAccountGroupRelException if a matching commerce discount commerce account group rel could not be found
	 */
	@Override
	public CommerceDiscountCommerceAccountGroupRel
			findByCommerceDiscountId_Last(
				long commerceDiscountId,
				OrderByComparator<CommerceDiscountCommerceAccountGroupRel>
					orderByComparator)
		throws NoSuchDiscountCommerceAccountGroupRelException {

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel =
				fetchByCommerceDiscountId_Last(
					commerceDiscountId, orderByComparator);

		if (commerceDiscountCommerceAccountGroupRel != null) {
			return commerceDiscountCommerceAccountGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceDiscountId=");
		sb.append(commerceDiscountId);

		sb.append("}");

		throw new NoSuchDiscountCommerceAccountGroupRelException(sb.toString());
	}

	/**
	 * Returns the last commerce discount commerce account group rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount commerce account group rel, or <code>null</code> if a matching commerce discount commerce account group rel could not be found
	 */
	@Override
	public CommerceDiscountCommerceAccountGroupRel
		fetchByCommerceDiscountId_Last(
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountCommerceAccountGroupRel>
				orderByComparator) {

		int count = countByCommerceDiscountId(commerceDiscountId);

		if (count == 0) {
			return null;
		}

		List<CommerceDiscountCommerceAccountGroupRel> list =
			findByCommerceDiscountId(
				commerceDiscountId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce discount commerce account group rels before and after the current commerce discount commerce account group rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountCommerceAccountGroupRelId the primary key of the current commerce discount commerce account group rel
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount commerce account group rel
	 * @throws NoSuchDiscountCommerceAccountGroupRelException if a commerce discount commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountCommerceAccountGroupRel[]
			findByCommerceDiscountId_PrevAndNext(
				long commerceDiscountCommerceAccountGroupRelId,
				long commerceDiscountId,
				OrderByComparator<CommerceDiscountCommerceAccountGroupRel>
					orderByComparator)
		throws NoSuchDiscountCommerceAccountGroupRelException {

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel = findByPrimaryKey(
				commerceDiscountCommerceAccountGroupRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountCommerceAccountGroupRel[] array =
				new CommerceDiscountCommerceAccountGroupRelImpl[3];

			array[0] = getByCommerceDiscountId_PrevAndNext(
				session, commerceDiscountCommerceAccountGroupRel,
				commerceDiscountId, orderByComparator, true);

			array[1] = commerceDiscountCommerceAccountGroupRel;

			array[2] = getByCommerceDiscountId_PrevAndNext(
				session, commerceDiscountCommerceAccountGroupRel,
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

	protected CommerceDiscountCommerceAccountGroupRel
		getByCommerceDiscountId_PrevAndNext(
			Session session,
			CommerceDiscountCommerceAccountGroupRel
				commerceDiscountCommerceAccountGroupRel,
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountCommerceAccountGroupRel>
				orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEDISCOUNTCOMMERCEACCOUNTGROUPREL_WHERE);

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
			sb.append(
				CommerceDiscountCommerceAccountGroupRelModelImpl.ORDER_BY_JPQL);
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
						commerceDiscountCommerceAccountGroupRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceDiscountCommerceAccountGroupRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce discount commerce account group rels where commerceDiscountId = &#63; from the database.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 */
	@Override
	public void removeByCommerceDiscountId(long commerceDiscountId) {
		for (CommerceDiscountCommerceAccountGroupRel
				commerceDiscountCommerceAccountGroupRel :
					findByCommerceDiscountId(
						commerceDiscountId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

			remove(commerceDiscountCommerceAccountGroupRel);
		}
	}

	/**
	 * Returns the number of commerce discount commerce account group rels where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the number of matching commerce discount commerce account group rels
	 */
	@Override
	public int countByCommerceDiscountId(long commerceDiscountId) {
		FinderPath finderPath = _finderPathCountByCommerceDiscountId;

		Object[] finderArgs = new Object[] {commerceDiscountId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNTCOMMERCEACCOUNTGROUPREL_WHERE);

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
			"commerceDiscountCommerceAccountGroupRel.commerceDiscountId = ?";

	private FinderPath _finderPathWithPaginationFindByCommerceAccountGroupId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceAccountGroupId;
	private FinderPath _finderPathCountByCommerceAccountGroupId;

	/**
	 * Returns all the commerce discount commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce discount commerce account group rels
	 */
	@Override
	public List<CommerceDiscountCommerceAccountGroupRel>
		findByCommerceAccountGroupId(long commerceAccountGroupId) {

		return findByCommerceAccountGroupId(
			commerceAccountGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce discount commerce account group rels
	 * @param end the upper bound of the range of commerce discount commerce account group rels (not inclusive)
	 * @return the range of matching commerce discount commerce account group rels
	 */
	@Override
	public List<CommerceDiscountCommerceAccountGroupRel>
		findByCommerceAccountGroupId(
			long commerceAccountGroupId, int start, int end) {

		return findByCommerceAccountGroupId(
			commerceAccountGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce discount commerce account group rels
	 * @param end the upper bound of the range of commerce discount commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount commerce account group rels
	 */
	@Override
	public List<CommerceDiscountCommerceAccountGroupRel>
		findByCommerceAccountGroupId(
			long commerceAccountGroupId, int start, int end,
			OrderByComparator<CommerceDiscountCommerceAccountGroupRel>
				orderByComparator) {

		return findByCommerceAccountGroupId(
			commerceAccountGroupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce discount commerce account group rels
	 * @param end the upper bound of the range of commerce discount commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount commerce account group rels
	 */
	@Override
	public List<CommerceDiscountCommerceAccountGroupRel>
		findByCommerceAccountGroupId(
			long commerceAccountGroupId, int start, int end,
			OrderByComparator<CommerceDiscountCommerceAccountGroupRel>
				orderByComparator,
			boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceAccountGroupId;
				finderArgs = new Object[] {commerceAccountGroupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceAccountGroupId;
			finderArgs = new Object[] {
				commerceAccountGroupId, start, end, orderByComparator
			};
		}

		List<CommerceDiscountCommerceAccountGroupRel> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceDiscountCommerceAccountGroupRel>)
					finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDiscountCommerceAccountGroupRel
						commerceDiscountCommerceAccountGroupRel : list) {

					if (commerceAccountGroupId !=
							commerceDiscountCommerceAccountGroupRel.
								getCommerceAccountGroupId()) {

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

			sb.append(
				_SQL_SELECT_COMMERCEDISCOUNTCOMMERCEACCOUNTGROUPREL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEACCOUNTGROUPID_COMMERCEACCOUNTGROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceDiscountCommerceAccountGroupRelModelImpl.
						ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceAccountGroupId);

				list =
					(List<CommerceDiscountCommerceAccountGroupRel>)
						QueryUtil.list(query, getDialect(), start, end);

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
	 * Returns the first commerce discount commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount commerce account group rel
	 * @throws NoSuchDiscountCommerceAccountGroupRelException if a matching commerce discount commerce account group rel could not be found
	 */
	@Override
	public CommerceDiscountCommerceAccountGroupRel
			findByCommerceAccountGroupId_First(
				long commerceAccountGroupId,
				OrderByComparator<CommerceDiscountCommerceAccountGroupRel>
					orderByComparator)
		throws NoSuchDiscountCommerceAccountGroupRelException {

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel =
				fetchByCommerceAccountGroupId_First(
					commerceAccountGroupId, orderByComparator);

		if (commerceDiscountCommerceAccountGroupRel != null) {
			return commerceDiscountCommerceAccountGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceAccountGroupId=");
		sb.append(commerceAccountGroupId);

		sb.append("}");

		throw new NoSuchDiscountCommerceAccountGroupRelException(sb.toString());
	}

	/**
	 * Returns the first commerce discount commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount commerce account group rel, or <code>null</code> if a matching commerce discount commerce account group rel could not be found
	 */
	@Override
	public CommerceDiscountCommerceAccountGroupRel
		fetchByCommerceAccountGroupId_First(
			long commerceAccountGroupId,
			OrderByComparator<CommerceDiscountCommerceAccountGroupRel>
				orderByComparator) {

		List<CommerceDiscountCommerceAccountGroupRel> list =
			findByCommerceAccountGroupId(
				commerceAccountGroupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce discount commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount commerce account group rel
	 * @throws NoSuchDiscountCommerceAccountGroupRelException if a matching commerce discount commerce account group rel could not be found
	 */
	@Override
	public CommerceDiscountCommerceAccountGroupRel
			findByCommerceAccountGroupId_Last(
				long commerceAccountGroupId,
				OrderByComparator<CommerceDiscountCommerceAccountGroupRel>
					orderByComparator)
		throws NoSuchDiscountCommerceAccountGroupRelException {

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel =
				fetchByCommerceAccountGroupId_Last(
					commerceAccountGroupId, orderByComparator);

		if (commerceDiscountCommerceAccountGroupRel != null) {
			return commerceDiscountCommerceAccountGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceAccountGroupId=");
		sb.append(commerceAccountGroupId);

		sb.append("}");

		throw new NoSuchDiscountCommerceAccountGroupRelException(sb.toString());
	}

	/**
	 * Returns the last commerce discount commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount commerce account group rel, or <code>null</code> if a matching commerce discount commerce account group rel could not be found
	 */
	@Override
	public CommerceDiscountCommerceAccountGroupRel
		fetchByCommerceAccountGroupId_Last(
			long commerceAccountGroupId,
			OrderByComparator<CommerceDiscountCommerceAccountGroupRel>
				orderByComparator) {

		int count = countByCommerceAccountGroupId(commerceAccountGroupId);

		if (count == 0) {
			return null;
		}

		List<CommerceDiscountCommerceAccountGroupRel> list =
			findByCommerceAccountGroupId(
				commerceAccountGroupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce discount commerce account group rels before and after the current commerce discount commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceDiscountCommerceAccountGroupRelId the primary key of the current commerce discount commerce account group rel
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount commerce account group rel
	 * @throws NoSuchDiscountCommerceAccountGroupRelException if a commerce discount commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountCommerceAccountGroupRel[]
			findByCommerceAccountGroupId_PrevAndNext(
				long commerceDiscountCommerceAccountGroupRelId,
				long commerceAccountGroupId,
				OrderByComparator<CommerceDiscountCommerceAccountGroupRel>
					orderByComparator)
		throws NoSuchDiscountCommerceAccountGroupRelException {

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel = findByPrimaryKey(
				commerceDiscountCommerceAccountGroupRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountCommerceAccountGroupRel[] array =
				new CommerceDiscountCommerceAccountGroupRelImpl[3];

			array[0] = getByCommerceAccountGroupId_PrevAndNext(
				session, commerceDiscountCommerceAccountGroupRel,
				commerceAccountGroupId, orderByComparator, true);

			array[1] = commerceDiscountCommerceAccountGroupRel;

			array[2] = getByCommerceAccountGroupId_PrevAndNext(
				session, commerceDiscountCommerceAccountGroupRel,
				commerceAccountGroupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceDiscountCommerceAccountGroupRel
		getByCommerceAccountGroupId_PrevAndNext(
			Session session,
			CommerceDiscountCommerceAccountGroupRel
				commerceDiscountCommerceAccountGroupRel,
			long commerceAccountGroupId,
			OrderByComparator<CommerceDiscountCommerceAccountGroupRel>
				orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEDISCOUNTCOMMERCEACCOUNTGROUPREL_WHERE);

		sb.append(
			_FINDER_COLUMN_COMMERCEACCOUNTGROUPID_COMMERCEACCOUNTGROUPID_2);

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
			sb.append(
				CommerceDiscountCommerceAccountGroupRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceAccountGroupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceDiscountCommerceAccountGroupRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceDiscountCommerceAccountGroupRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce discount commerce account group rels where commerceAccountGroupId = &#63; from the database.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 */
	@Override
	public void removeByCommerceAccountGroupId(long commerceAccountGroupId) {
		for (CommerceDiscountCommerceAccountGroupRel
				commerceDiscountCommerceAccountGroupRel :
					findByCommerceAccountGroupId(
						commerceAccountGroupId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

			remove(commerceDiscountCommerceAccountGroupRel);
		}
	}

	/**
	 * Returns the number of commerce discount commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce discount commerce account group rels
	 */
	@Override
	public int countByCommerceAccountGroupId(long commerceAccountGroupId) {
		FinderPath finderPath = _finderPathCountByCommerceAccountGroupId;

		Object[] finderArgs = new Object[] {commerceAccountGroupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNTCOMMERCEACCOUNTGROUPREL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEACCOUNTGROUPID_COMMERCEACCOUNTGROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceAccountGroupId);

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
		_FINDER_COLUMN_COMMERCEACCOUNTGROUPID_COMMERCEACCOUNTGROUPID_2 =
			"commerceDiscountCommerceAccountGroupRel.commerceAccountGroupId = ?";

	private FinderPath _finderPathFetchByCDI_CAGI;
	private FinderPath _finderPathCountByCDI_CAGI;

	/**
	 * Returns the commerce discount commerce account group rel where commerceDiscountId = &#63; and commerceAccountGroupId = &#63; or throws a <code>NoSuchDiscountCommerceAccountGroupRelException</code> if it could not be found.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce discount commerce account group rel
	 * @throws NoSuchDiscountCommerceAccountGroupRelException if a matching commerce discount commerce account group rel could not be found
	 */
	@Override
	public CommerceDiscountCommerceAccountGroupRel findByCDI_CAGI(
			long commerceDiscountId, long commerceAccountGroupId)
		throws NoSuchDiscountCommerceAccountGroupRelException {

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel = fetchByCDI_CAGI(
				commerceDiscountId, commerceAccountGroupId);

		if (commerceDiscountCommerceAccountGroupRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("commerceDiscountId=");
			sb.append(commerceDiscountId);

			sb.append(", commerceAccountGroupId=");
			sb.append(commerceAccountGroupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchDiscountCommerceAccountGroupRelException(
				sb.toString());
		}

		return commerceDiscountCommerceAccountGroupRel;
	}

	/**
	 * Returns the commerce discount commerce account group rel where commerceDiscountId = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce discount commerce account group rel, or <code>null</code> if a matching commerce discount commerce account group rel could not be found
	 */
	@Override
	public CommerceDiscountCommerceAccountGroupRel fetchByCDI_CAGI(
		long commerceDiscountId, long commerceAccountGroupId) {

		return fetchByCDI_CAGI(
			commerceDiscountId, commerceAccountGroupId, true);
	}

	/**
	 * Returns the commerce discount commerce account group rel where commerceDiscountId = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce discount commerce account group rel, or <code>null</code> if a matching commerce discount commerce account group rel could not be found
	 */
	@Override
	public CommerceDiscountCommerceAccountGroupRel fetchByCDI_CAGI(
		long commerceDiscountId, long commerceAccountGroupId,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				commerceDiscountId, commerceAccountGroupId
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCDI_CAGI, finderArgs);
		}

		if (result instanceof CommerceDiscountCommerceAccountGroupRel) {
			CommerceDiscountCommerceAccountGroupRel
				commerceDiscountCommerceAccountGroupRel =
					(CommerceDiscountCommerceAccountGroupRel)result;

			if ((commerceDiscountId !=
					commerceDiscountCommerceAccountGroupRel.
						getCommerceDiscountId()) ||
				(commerceAccountGroupId !=
					commerceDiscountCommerceAccountGroupRel.
						getCommerceAccountGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(
				_SQL_SELECT_COMMERCEDISCOUNTCOMMERCEACCOUNTGROUPREL_WHERE);

			sb.append(_FINDER_COLUMN_CDI_CAGI_COMMERCEDISCOUNTID_2);

			sb.append(_FINDER_COLUMN_CDI_CAGI_COMMERCEACCOUNTGROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceDiscountId);

				queryPos.add(commerceAccountGroupId);

				List<CommerceDiscountCommerceAccountGroupRel> list =
					query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCDI_CAGI, finderArgs, list);
					}
				}
				else {
					CommerceDiscountCommerceAccountGroupRel
						commerceDiscountCommerceAccountGroupRel = list.get(0);

					result = commerceDiscountCommerceAccountGroupRel;

					cacheResult(commerceDiscountCommerceAccountGroupRel);
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
			return (CommerceDiscountCommerceAccountGroupRel)result;
		}
	}

	/**
	 * Removes the commerce discount commerce account group rel where commerceDiscountId = &#63; and commerceAccountGroupId = &#63; from the database.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the commerce discount commerce account group rel that was removed
	 */
	@Override
	public CommerceDiscountCommerceAccountGroupRel removeByCDI_CAGI(
			long commerceDiscountId, long commerceAccountGroupId)
		throws NoSuchDiscountCommerceAccountGroupRelException {

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel = findByCDI_CAGI(
				commerceDiscountId, commerceAccountGroupId);

		return remove(commerceDiscountCommerceAccountGroupRel);
	}

	/**
	 * Returns the number of commerce discount commerce account group rels where commerceDiscountId = &#63; and commerceAccountGroupId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce discount commerce account group rels
	 */
	@Override
	public int countByCDI_CAGI(
		long commerceDiscountId, long commerceAccountGroupId) {

		FinderPath finderPath = _finderPathCountByCDI_CAGI;

		Object[] finderArgs = new Object[] {
			commerceDiscountId, commerceAccountGroupId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNTCOMMERCEACCOUNTGROUPREL_WHERE);

			sb.append(_FINDER_COLUMN_CDI_CAGI_COMMERCEDISCOUNTID_2);

			sb.append(_FINDER_COLUMN_CDI_CAGI_COMMERCEACCOUNTGROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceDiscountId);

				queryPos.add(commerceAccountGroupId);

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

	private static final String _FINDER_COLUMN_CDI_CAGI_COMMERCEDISCOUNTID_2 =
		"commerceDiscountCommerceAccountGroupRel.commerceDiscountId = ? AND ";

	private static final String
		_FINDER_COLUMN_CDI_CAGI_COMMERCEACCOUNTGROUPID_2 =
			"commerceDiscountCommerceAccountGroupRel.commerceAccountGroupId = ?";

	public CommerceDiscountCommerceAccountGroupRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"commerceDiscountCommerceAccountGroupRelId",
			"CDiscountCAccountGroupRelId");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceDiscountCommerceAccountGroupRel.class);

		setModelImplClass(CommerceDiscountCommerceAccountGroupRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceDiscountCommerceAccountGroupRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce discount commerce account group rel in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountCommerceAccountGroupRel the commerce discount commerce account group rel
	 */
	@Override
	public void cacheResult(
		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel) {

		entityCache.putResult(
			CommerceDiscountCommerceAccountGroupRelImpl.class,
			commerceDiscountCommerceAccountGroupRel.getPrimaryKey(),
			commerceDiscountCommerceAccountGroupRel);

		finderCache.putResult(
			_finderPathFetchByCDI_CAGI,
			new Object[] {
				commerceDiscountCommerceAccountGroupRel.getCommerceDiscountId(),
				commerceDiscountCommerceAccountGroupRel.
					getCommerceAccountGroupId()
			},
			commerceDiscountCommerceAccountGroupRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce discount commerce account group rels in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountCommerceAccountGroupRels the commerce discount commerce account group rels
	 */
	@Override
	public void cacheResult(
		List<CommerceDiscountCommerceAccountGroupRel>
			commerceDiscountCommerceAccountGroupRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceDiscountCommerceAccountGroupRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceDiscountCommerceAccountGroupRel
				commerceDiscountCommerceAccountGroupRel :
					commerceDiscountCommerceAccountGroupRels) {

			if (entityCache.getResult(
					CommerceDiscountCommerceAccountGroupRelImpl.class,
					commerceDiscountCommerceAccountGroupRel.getPrimaryKey()) ==
						null) {

				cacheResult(commerceDiscountCommerceAccountGroupRel);
			}
		}
	}

	/**
	 * Clears the cache for all commerce discount commerce account group rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(
			CommerceDiscountCommerceAccountGroupRelImpl.class);

		finderCache.clearCache(
			CommerceDiscountCommerceAccountGroupRelImpl.class);
	}

	/**
	 * Clears the cache for the commerce discount commerce account group rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel) {

		entityCache.removeResult(
			CommerceDiscountCommerceAccountGroupRelImpl.class,
			commerceDiscountCommerceAccountGroupRel);
	}

	@Override
	public void clearCache(
		List<CommerceDiscountCommerceAccountGroupRel>
			commerceDiscountCommerceAccountGroupRels) {

		for (CommerceDiscountCommerceAccountGroupRel
				commerceDiscountCommerceAccountGroupRel :
					commerceDiscountCommerceAccountGroupRels) {

			entityCache.removeResult(
				CommerceDiscountCommerceAccountGroupRelImpl.class,
				commerceDiscountCommerceAccountGroupRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(
			CommerceDiscountCommerceAccountGroupRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceDiscountCommerceAccountGroupRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceDiscountCommerceAccountGroupRelModelImpl
			commerceDiscountCommerceAccountGroupRelModelImpl) {

		Object[] args = new Object[] {
			commerceDiscountCommerceAccountGroupRelModelImpl.
				getCommerceDiscountId(),
			commerceDiscountCommerceAccountGroupRelModelImpl.
				getCommerceAccountGroupId()
		};

		finderCache.putResult(
			_finderPathCountByCDI_CAGI, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCDI_CAGI, args,
			commerceDiscountCommerceAccountGroupRelModelImpl);
	}

	/**
	 * Creates a new commerce discount commerce account group rel with the primary key. Does not add the commerce discount commerce account group rel to the database.
	 *
	 * @param commerceDiscountCommerceAccountGroupRelId the primary key for the new commerce discount commerce account group rel
	 * @return the new commerce discount commerce account group rel
	 */
	@Override
	public CommerceDiscountCommerceAccountGroupRel create(
		long commerceDiscountCommerceAccountGroupRelId) {

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel =
				new CommerceDiscountCommerceAccountGroupRelImpl();

		commerceDiscountCommerceAccountGroupRel.setNew(true);
		commerceDiscountCommerceAccountGroupRel.setPrimaryKey(
			commerceDiscountCommerceAccountGroupRelId);

		commerceDiscountCommerceAccountGroupRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceDiscountCommerceAccountGroupRel;
	}

	/**
	 * Removes the commerce discount commerce account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDiscountCommerceAccountGroupRelId the primary key of the commerce discount commerce account group rel
	 * @return the commerce discount commerce account group rel that was removed
	 * @throws NoSuchDiscountCommerceAccountGroupRelException if a commerce discount commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountCommerceAccountGroupRel remove(
			long commerceDiscountCommerceAccountGroupRelId)
		throws NoSuchDiscountCommerceAccountGroupRelException {

		return remove((Serializable)commerceDiscountCommerceAccountGroupRelId);
	}

	/**
	 * Removes the commerce discount commerce account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce discount commerce account group rel
	 * @return the commerce discount commerce account group rel that was removed
	 * @throws NoSuchDiscountCommerceAccountGroupRelException if a commerce discount commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountCommerceAccountGroupRel remove(
			Serializable primaryKey)
		throws NoSuchDiscountCommerceAccountGroupRelException {

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountCommerceAccountGroupRel
				commerceDiscountCommerceAccountGroupRel =
					(CommerceDiscountCommerceAccountGroupRel)session.get(
						CommerceDiscountCommerceAccountGroupRelImpl.class,
						primaryKey);

			if (commerceDiscountCommerceAccountGroupRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDiscountCommerceAccountGroupRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceDiscountCommerceAccountGroupRel);
		}
		catch (NoSuchDiscountCommerceAccountGroupRelException
					noSuchEntityException) {

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
	protected CommerceDiscountCommerceAccountGroupRel removeImpl(
		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceDiscountCommerceAccountGroupRel)) {
				commerceDiscountCommerceAccountGroupRel =
					(CommerceDiscountCommerceAccountGroupRel)session.get(
						CommerceDiscountCommerceAccountGroupRelImpl.class,
						commerceDiscountCommerceAccountGroupRel.
							getPrimaryKeyObj());
			}

			if (commerceDiscountCommerceAccountGroupRel != null) {
				session.delete(commerceDiscountCommerceAccountGroupRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceDiscountCommerceAccountGroupRel != null) {
			clearCache(commerceDiscountCommerceAccountGroupRel);
		}

		return commerceDiscountCommerceAccountGroupRel;
	}

	@Override
	public CommerceDiscountCommerceAccountGroupRel updateImpl(
		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel) {

		boolean isNew = commerceDiscountCommerceAccountGroupRel.isNew();

		if (!(commerceDiscountCommerceAccountGroupRel instanceof
				CommerceDiscountCommerceAccountGroupRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceDiscountCommerceAccountGroupRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceDiscountCommerceAccountGroupRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceDiscountCommerceAccountGroupRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceDiscountCommerceAccountGroupRel implementation " +
					commerceDiscountCommerceAccountGroupRel.getClass());
		}

		CommerceDiscountCommerceAccountGroupRelModelImpl
			commerceDiscountCommerceAccountGroupRelModelImpl =
				(CommerceDiscountCommerceAccountGroupRelModelImpl)
					commerceDiscountCommerceAccountGroupRel;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew &&
			(commerceDiscountCommerceAccountGroupRel.getCreateDate() == null)) {

			if (serviceContext == null) {
				commerceDiscountCommerceAccountGroupRel.setCreateDate(date);
			}
			else {
				commerceDiscountCommerceAccountGroupRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceDiscountCommerceAccountGroupRelModelImpl.
				hasSetModifiedDate()) {

			if (serviceContext == null) {
				commerceDiscountCommerceAccountGroupRel.setModifiedDate(date);
			}
			else {
				commerceDiscountCommerceAccountGroupRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceDiscountCommerceAccountGroupRel);
			}
			else {
				commerceDiscountCommerceAccountGroupRel =
					(CommerceDiscountCommerceAccountGroupRel)session.merge(
						commerceDiscountCommerceAccountGroupRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceDiscountCommerceAccountGroupRelImpl.class,
			commerceDiscountCommerceAccountGroupRelModelImpl, false, true);

		cacheUniqueFindersCache(
			commerceDiscountCommerceAccountGroupRelModelImpl);

		if (isNew) {
			commerceDiscountCommerceAccountGroupRel.setNew(false);
		}

		commerceDiscountCommerceAccountGroupRel.resetOriginalValues();

		return commerceDiscountCommerceAccountGroupRel;
	}

	/**
	 * Returns the commerce discount commerce account group rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce discount commerce account group rel
	 * @return the commerce discount commerce account group rel
	 * @throws NoSuchDiscountCommerceAccountGroupRelException if a commerce discount commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountCommerceAccountGroupRel findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchDiscountCommerceAccountGroupRelException {

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel = fetchByPrimaryKey(
				primaryKey);

		if (commerceDiscountCommerceAccountGroupRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDiscountCommerceAccountGroupRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceDiscountCommerceAccountGroupRel;
	}

	/**
	 * Returns the commerce discount commerce account group rel with the primary key or throws a <code>NoSuchDiscountCommerceAccountGroupRelException</code> if it could not be found.
	 *
	 * @param commerceDiscountCommerceAccountGroupRelId the primary key of the commerce discount commerce account group rel
	 * @return the commerce discount commerce account group rel
	 * @throws NoSuchDiscountCommerceAccountGroupRelException if a commerce discount commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountCommerceAccountGroupRel findByPrimaryKey(
			long commerceDiscountCommerceAccountGroupRelId)
		throws NoSuchDiscountCommerceAccountGroupRelException {

		return findByPrimaryKey(
			(Serializable)commerceDiscountCommerceAccountGroupRelId);
	}

	/**
	 * Returns the commerce discount commerce account group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDiscountCommerceAccountGroupRelId the primary key of the commerce discount commerce account group rel
	 * @return the commerce discount commerce account group rel, or <code>null</code> if a commerce discount commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountCommerceAccountGroupRel fetchByPrimaryKey(
		long commerceDiscountCommerceAccountGroupRelId) {

		return fetchByPrimaryKey(
			(Serializable)commerceDiscountCommerceAccountGroupRelId);
	}

	/**
	 * Returns all the commerce discount commerce account group rels.
	 *
	 * @return the commerce discount commerce account group rels
	 */
	@Override
	public List<CommerceDiscountCommerceAccountGroupRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount commerce account group rels
	 * @param end the upper bound of the range of commerce discount commerce account group rels (not inclusive)
	 * @return the range of commerce discount commerce account group rels
	 */
	@Override
	public List<CommerceDiscountCommerceAccountGroupRel> findAll(
		int start, int end) {

		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount commerce account group rels
	 * @param end the upper bound of the range of commerce discount commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce discount commerce account group rels
	 */
	@Override
	public List<CommerceDiscountCommerceAccountGroupRel> findAll(
		int start, int end,
		OrderByComparator<CommerceDiscountCommerceAccountGroupRel>
			orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount commerce account group rels
	 * @param end the upper bound of the range of commerce discount commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce discount commerce account group rels
	 */
	@Override
	public List<CommerceDiscountCommerceAccountGroupRel> findAll(
		int start, int end,
		OrderByComparator<CommerceDiscountCommerceAccountGroupRel>
			orderByComparator,
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

		List<CommerceDiscountCommerceAccountGroupRel> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceDiscountCommerceAccountGroupRel>)
					finderCache.getResult(finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEDISCOUNTCOMMERCEACCOUNTGROUPREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEDISCOUNTCOMMERCEACCOUNTGROUPREL;

				sql = sql.concat(
					CommerceDiscountCommerceAccountGroupRelModelImpl.
						ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list =
					(List<CommerceDiscountCommerceAccountGroupRel>)
						QueryUtil.list(query, getDialect(), start, end);

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
	 * Removes all the commerce discount commerce account group rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceDiscountCommerceAccountGroupRel
				commerceDiscountCommerceAccountGroupRel : findAll()) {

			remove(commerceDiscountCommerceAccountGroupRel);
		}
	}

	/**
	 * Returns the number of commerce discount commerce account group rels.
	 *
	 * @return the number of commerce discount commerce account group rels
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
					_SQL_COUNT_COMMERCEDISCOUNTCOMMERCEACCOUNTGROUPREL);

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
		return "CDiscountCAccountGroupRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEDISCOUNTCOMMERCEACCOUNTGROUPREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceDiscountCommerceAccountGroupRelModelImpl.
			TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce discount commerce account group rel persistence.
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

		_finderPathWithPaginationFindByCommerceAccountGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceAccountGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceAccountGroupId"}, true);

		_finderPathWithoutPaginationFindByCommerceAccountGroupId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceAccountGroupId",
				new String[] {Long.class.getName()},
				new String[] {"commerceAccountGroupId"}, true);

		_finderPathCountByCommerceAccountGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceAccountGroupId",
			new String[] {Long.class.getName()},
			new String[] {"commerceAccountGroupId"}, false);

		_finderPathFetchByCDI_CAGI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCDI_CAGI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceDiscountId", "commerceAccountGroupId"},
			true);

		_finderPathCountByCDI_CAGI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCDI_CAGI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceDiscountId", "commerceAccountGroupId"},
			false);
	}

	public void destroy() {
		entityCache.removeCache(
			CommerceDiscountCommerceAccountGroupRelImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String
		_SQL_SELECT_COMMERCEDISCOUNTCOMMERCEACCOUNTGROUPREL =
			"SELECT commerceDiscountCommerceAccountGroupRel FROM CommerceDiscountCommerceAccountGroupRel commerceDiscountCommerceAccountGroupRel";

	private static final String
		_SQL_SELECT_COMMERCEDISCOUNTCOMMERCEACCOUNTGROUPREL_WHERE =
			"SELECT commerceDiscountCommerceAccountGroupRel FROM CommerceDiscountCommerceAccountGroupRel commerceDiscountCommerceAccountGroupRel WHERE ";

	private static final String
		_SQL_COUNT_COMMERCEDISCOUNTCOMMERCEACCOUNTGROUPREL =
			"SELECT COUNT(commerceDiscountCommerceAccountGroupRel) FROM CommerceDiscountCommerceAccountGroupRel commerceDiscountCommerceAccountGroupRel";

	private static final String
		_SQL_COUNT_COMMERCEDISCOUNTCOMMERCEACCOUNTGROUPREL_WHERE =
			"SELECT COUNT(commerceDiscountCommerceAccountGroupRel) FROM CommerceDiscountCommerceAccountGroupRel commerceDiscountCommerceAccountGroupRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceDiscountCommerceAccountGroupRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceDiscountCommerceAccountGroupRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceDiscountCommerceAccountGroupRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountCommerceAccountGroupRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"commerceDiscountCommerceAccountGroupRelId"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}