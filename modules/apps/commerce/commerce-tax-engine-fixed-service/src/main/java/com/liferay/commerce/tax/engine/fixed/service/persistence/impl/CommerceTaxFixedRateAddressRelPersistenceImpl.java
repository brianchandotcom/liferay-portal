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

package com.liferay.commerce.tax.engine.fixed.service.persistence.impl;

import com.liferay.commerce.tax.engine.fixed.exception.NoSuchTaxFixedRateAddressRelException;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRelTable;
import com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateAddressRelImpl;
import com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateAddressRelModelImpl;
import com.liferay.commerce.tax.engine.fixed.service.persistence.CommerceTaxFixedRateAddressRelPersistence;
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
 * The persistence implementation for the commerce tax fixed rate address rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceTaxFixedRateAddressRelPersistenceImpl
	extends BasePersistenceImpl<CommerceTaxFixedRateAddressRel>
	implements CommerceTaxFixedRateAddressRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceTaxFixedRateAddressRelUtil</code> to access the commerce tax fixed rate address rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceTaxFixedRateAddressRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCommerceTaxMethodId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceTaxMethodId;
	private FinderPath _finderPathCountByCommerceTaxMethodId;

	/**
	 * Returns all the commerce tax fixed rate address rels where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the matching commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findByCommerceTaxMethodId(
		long commerceTaxMethodId) {

		return findByCommerceTaxMethodId(
			commerceTaxMethodId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax fixed rate address rels where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateAddressRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param start the lower bound of the range of commerce tax fixed rate address rels
	 * @param end the upper bound of the range of commerce tax fixed rate address rels (not inclusive)
	 * @return the range of matching commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end) {

		return findByCommerceTaxMethodId(commerceTaxMethodId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rate address rels where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateAddressRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param start the lower bound of the range of commerce tax fixed rate address rels
	 * @param end the upper bound of the range of commerce tax fixed rate address rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator) {

		return findByCommerceTaxMethodId(
			commerceTaxMethodId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rate address rels where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateAddressRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param start the lower bound of the range of commerce tax fixed rate address rels
	 * @param end the upper bound of the range of commerce tax fixed rate address rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceTaxMethodId;
				finderArgs = new Object[] {commerceTaxMethodId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceTaxMethodId;
			finderArgs = new Object[] {
				commerceTaxMethodId, start, end, orderByComparator
			};
		}

		List<CommerceTaxFixedRateAddressRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceTaxFixedRateAddressRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTaxFixedRateAddressRel
						commerceTaxFixedRateAddressRel : list) {

					if (commerceTaxMethodId !=
							commerceTaxFixedRateAddressRel.
								getCommerceTaxMethodId()) {

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

			sb.append(_SQL_SELECT_COMMERCETAXFIXEDRATEADDRESSREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCETAXMETHODID_COMMERCETAXMETHODID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceTaxFixedRateAddressRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceTaxMethodId);

				list = (List<CommerceTaxFixedRateAddressRel>)QueryUtil.list(
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
	 * Returns the first commerce tax fixed rate address rel in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax fixed rate address rel
	 * @throws NoSuchTaxFixedRateAddressRelException if a matching commerce tax fixed rate address rel could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel findByCommerceTaxMethodId_First(
			long commerceTaxMethodId,
			OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator)
		throws NoSuchTaxFixedRateAddressRelException {

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			fetchByCommerceTaxMethodId_First(
				commerceTaxMethodId, orderByComparator);

		if (commerceTaxFixedRateAddressRel != null) {
			return commerceTaxFixedRateAddressRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceTaxMethodId=");
		sb.append(commerceTaxMethodId);

		sb.append("}");

		throw new NoSuchTaxFixedRateAddressRelException(sb.toString());
	}

	/**
	 * Returns the first commerce tax fixed rate address rel in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax fixed rate address rel, or <code>null</code> if a matching commerce tax fixed rate address rel could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel fetchByCommerceTaxMethodId_First(
		long commerceTaxMethodId,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator) {

		List<CommerceTaxFixedRateAddressRel> list = findByCommerceTaxMethodId(
			commerceTaxMethodId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tax fixed rate address rel in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax fixed rate address rel
	 * @throws NoSuchTaxFixedRateAddressRelException if a matching commerce tax fixed rate address rel could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel findByCommerceTaxMethodId_Last(
			long commerceTaxMethodId,
			OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator)
		throws NoSuchTaxFixedRateAddressRelException {

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			fetchByCommerceTaxMethodId_Last(
				commerceTaxMethodId, orderByComparator);

		if (commerceTaxFixedRateAddressRel != null) {
			return commerceTaxFixedRateAddressRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceTaxMethodId=");
		sb.append(commerceTaxMethodId);

		sb.append("}");

		throw new NoSuchTaxFixedRateAddressRelException(sb.toString());
	}

	/**
	 * Returns the last commerce tax fixed rate address rel in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax fixed rate address rel, or <code>null</code> if a matching commerce tax fixed rate address rel could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel fetchByCommerceTaxMethodId_Last(
		long commerceTaxMethodId,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator) {

		int count = countByCommerceTaxMethodId(commerceTaxMethodId);

		if (count == 0) {
			return null;
		}

		List<CommerceTaxFixedRateAddressRel> list = findByCommerceTaxMethodId(
			commerceTaxMethodId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tax fixed rate address rels before and after the current commerce tax fixed rate address rel in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxFixedRateAddressRelId the primary key of the current commerce tax fixed rate address rel
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tax fixed rate address rel
	 * @throws NoSuchTaxFixedRateAddressRelException if a commerce tax fixed rate address rel with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel[]
			findByCommerceTaxMethodId_PrevAndNext(
				long commerceTaxFixedRateAddressRelId, long commerceTaxMethodId,
				OrderByComparator<CommerceTaxFixedRateAddressRel>
					orderByComparator)
		throws NoSuchTaxFixedRateAddressRelException {

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			findByPrimaryKey(commerceTaxFixedRateAddressRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceTaxFixedRateAddressRel[] array =
				new CommerceTaxFixedRateAddressRelImpl[3];

			array[0] = getByCommerceTaxMethodId_PrevAndNext(
				session, commerceTaxFixedRateAddressRel, commerceTaxMethodId,
				orderByComparator, true);

			array[1] = commerceTaxFixedRateAddressRel;

			array[2] = getByCommerceTaxMethodId_PrevAndNext(
				session, commerceTaxFixedRateAddressRel, commerceTaxMethodId,
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

	protected CommerceTaxFixedRateAddressRel
		getByCommerceTaxMethodId_PrevAndNext(
			Session session,
			CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel,
			long commerceTaxMethodId,
			OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCETAXFIXEDRATEADDRESSREL_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCETAXMETHODID_COMMERCETAXMETHODID_2);

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
			sb.append(CommerceTaxFixedRateAddressRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceTaxMethodId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceTaxFixedRateAddressRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTaxFixedRateAddressRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tax fixed rate address rels where commerceTaxMethodId = &#63; from the database.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 */
	@Override
	public void removeByCommerceTaxMethodId(long commerceTaxMethodId) {
		for (CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel :
				findByCommerceTaxMethodId(
					commerceTaxMethodId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceTaxFixedRateAddressRel);
		}
	}

	/**
	 * Returns the number of commerce tax fixed rate address rels where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the number of matching commerce tax fixed rate address rels
	 */
	@Override
	public int countByCommerceTaxMethodId(long commerceTaxMethodId) {
		FinderPath finderPath = _finderPathCountByCommerceTaxMethodId;

		Object[] finderArgs = new Object[] {commerceTaxMethodId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCETAXFIXEDRATEADDRESSREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCETAXMETHODID_COMMERCETAXMETHODID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceTaxMethodId);

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
		_FINDER_COLUMN_COMMERCETAXMETHODID_COMMERCETAXMETHODID_2 =
			"commerceTaxFixedRateAddressRel.commerceTaxMethodId = ?";

	private FinderPath _finderPathWithPaginationFindByCPTaxCategoryId;
	private FinderPath _finderPathWithoutPaginationFindByCPTaxCategoryId;
	private FinderPath _finderPathCountByCPTaxCategoryId;

	/**
	 * Returns all the commerce tax fixed rate address rels where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the matching commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findByCPTaxCategoryId(
		long CPTaxCategoryId) {

		return findByCPTaxCategoryId(
			CPTaxCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax fixed rate address rels where CPTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateAddressRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param start the lower bound of the range of commerce tax fixed rate address rels
	 * @param end the upper bound of the range of commerce tax fixed rate address rels (not inclusive)
	 * @return the range of matching commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end) {

		return findByCPTaxCategoryId(CPTaxCategoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rate address rels where CPTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateAddressRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param start the lower bound of the range of commerce tax fixed rate address rels
	 * @param end the upper bound of the range of commerce tax fixed rate address rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator) {

		return findByCPTaxCategoryId(
			CPTaxCategoryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rate address rels where CPTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateAddressRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param start the lower bound of the range of commerce tax fixed rate address rels
	 * @param end the upper bound of the range of commerce tax fixed rate address rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCPTaxCategoryId;
				finderArgs = new Object[] {CPTaxCategoryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCPTaxCategoryId;
			finderArgs = new Object[] {
				CPTaxCategoryId, start, end, orderByComparator
			};
		}

		List<CommerceTaxFixedRateAddressRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceTaxFixedRateAddressRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTaxFixedRateAddressRel
						commerceTaxFixedRateAddressRel : list) {

					if (CPTaxCategoryId !=
							commerceTaxFixedRateAddressRel.
								getCPTaxCategoryId()) {

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

			sb.append(_SQL_SELECT_COMMERCETAXFIXEDRATEADDRESSREL_WHERE);

			sb.append(_FINDER_COLUMN_CPTAXCATEGORYID_CPTAXCATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceTaxFixedRateAddressRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPTaxCategoryId);

				list = (List<CommerceTaxFixedRateAddressRel>)QueryUtil.list(
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
	 * Returns the first commerce tax fixed rate address rel in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax fixed rate address rel
	 * @throws NoSuchTaxFixedRateAddressRelException if a matching commerce tax fixed rate address rel could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel findByCPTaxCategoryId_First(
			long CPTaxCategoryId,
			OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator)
		throws NoSuchTaxFixedRateAddressRelException {

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			fetchByCPTaxCategoryId_First(CPTaxCategoryId, orderByComparator);

		if (commerceTaxFixedRateAddressRel != null) {
			return commerceTaxFixedRateAddressRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPTaxCategoryId=");
		sb.append(CPTaxCategoryId);

		sb.append("}");

		throw new NoSuchTaxFixedRateAddressRelException(sb.toString());
	}

	/**
	 * Returns the first commerce tax fixed rate address rel in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax fixed rate address rel, or <code>null</code> if a matching commerce tax fixed rate address rel could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel fetchByCPTaxCategoryId_First(
		long CPTaxCategoryId,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator) {

		List<CommerceTaxFixedRateAddressRel> list = findByCPTaxCategoryId(
			CPTaxCategoryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tax fixed rate address rel in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax fixed rate address rel
	 * @throws NoSuchTaxFixedRateAddressRelException if a matching commerce tax fixed rate address rel could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel findByCPTaxCategoryId_Last(
			long CPTaxCategoryId,
			OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator)
		throws NoSuchTaxFixedRateAddressRelException {

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			fetchByCPTaxCategoryId_Last(CPTaxCategoryId, orderByComparator);

		if (commerceTaxFixedRateAddressRel != null) {
			return commerceTaxFixedRateAddressRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPTaxCategoryId=");
		sb.append(CPTaxCategoryId);

		sb.append("}");

		throw new NoSuchTaxFixedRateAddressRelException(sb.toString());
	}

	/**
	 * Returns the last commerce tax fixed rate address rel in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax fixed rate address rel, or <code>null</code> if a matching commerce tax fixed rate address rel could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel fetchByCPTaxCategoryId_Last(
		long CPTaxCategoryId,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator) {

		int count = countByCPTaxCategoryId(CPTaxCategoryId);

		if (count == 0) {
			return null;
		}

		List<CommerceTaxFixedRateAddressRel> list = findByCPTaxCategoryId(
			CPTaxCategoryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tax fixed rate address rels before and after the current commerce tax fixed rate address rel in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param commerceTaxFixedRateAddressRelId the primary key of the current commerce tax fixed rate address rel
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tax fixed rate address rel
	 * @throws NoSuchTaxFixedRateAddressRelException if a commerce tax fixed rate address rel with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel[] findByCPTaxCategoryId_PrevAndNext(
			long commerceTaxFixedRateAddressRelId, long CPTaxCategoryId,
			OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator)
		throws NoSuchTaxFixedRateAddressRelException {

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			findByPrimaryKey(commerceTaxFixedRateAddressRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceTaxFixedRateAddressRel[] array =
				new CommerceTaxFixedRateAddressRelImpl[3];

			array[0] = getByCPTaxCategoryId_PrevAndNext(
				session, commerceTaxFixedRateAddressRel, CPTaxCategoryId,
				orderByComparator, true);

			array[1] = commerceTaxFixedRateAddressRel;

			array[2] = getByCPTaxCategoryId_PrevAndNext(
				session, commerceTaxFixedRateAddressRel, CPTaxCategoryId,
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

	protected CommerceTaxFixedRateAddressRel getByCPTaxCategoryId_PrevAndNext(
		Session session,
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel,
		long CPTaxCategoryId,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCETAXFIXEDRATEADDRESSREL_WHERE);

		sb.append(_FINDER_COLUMN_CPTAXCATEGORYID_CPTAXCATEGORYID_2);

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
			sb.append(CommerceTaxFixedRateAddressRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CPTaxCategoryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceTaxFixedRateAddressRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTaxFixedRateAddressRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tax fixed rate address rels where CPTaxCategoryId = &#63; from the database.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 */
	@Override
	public void removeByCPTaxCategoryId(long CPTaxCategoryId) {
		for (CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel :
				findByCPTaxCategoryId(
					CPTaxCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceTaxFixedRateAddressRel);
		}
	}

	/**
	 * Returns the number of commerce tax fixed rate address rels where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the number of matching commerce tax fixed rate address rels
	 */
	@Override
	public int countByCPTaxCategoryId(long CPTaxCategoryId) {
		FinderPath finderPath = _finderPathCountByCPTaxCategoryId;

		Object[] finderArgs = new Object[] {CPTaxCategoryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCETAXFIXEDRATEADDRESSREL_WHERE);

			sb.append(_FINDER_COLUMN_CPTAXCATEGORYID_CPTAXCATEGORYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPTaxCategoryId);

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
		_FINDER_COLUMN_CPTAXCATEGORYID_CPTAXCATEGORYID_2 =
			"commerceTaxFixedRateAddressRel.CPTaxCategoryId = ?";

	private FinderPath _finderPathWithPaginationFindByCountryId;
	private FinderPath _finderPathWithoutPaginationFindByCountryId;
	private FinderPath _finderPathCountByCountryId;

	/**
	 * Returns all the commerce tax fixed rate address rels where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @return the matching commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findByCountryId(
		long countryId) {

		return findByCountryId(
			countryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax fixed rate address rels where countryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateAddressRelModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param start the lower bound of the range of commerce tax fixed rate address rels
	 * @param end the upper bound of the range of commerce tax fixed rate address rels (not inclusive)
	 * @return the range of matching commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findByCountryId(
		long countryId, int start, int end) {

		return findByCountryId(countryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rate address rels where countryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateAddressRelModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param start the lower bound of the range of commerce tax fixed rate address rels
	 * @param end the upper bound of the range of commerce tax fixed rate address rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findByCountryId(
		long countryId, int start, int end,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator) {

		return findByCountryId(countryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rate address rels where countryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateAddressRelModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param start the lower bound of the range of commerce tax fixed rate address rels
	 * @param end the upper bound of the range of commerce tax fixed rate address rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findByCountryId(
		long countryId, int start, int end,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCountryId;
				finderArgs = new Object[] {countryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCountryId;
			finderArgs = new Object[] {
				countryId, start, end, orderByComparator
			};
		}

		List<CommerceTaxFixedRateAddressRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceTaxFixedRateAddressRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTaxFixedRateAddressRel
						commerceTaxFixedRateAddressRel : list) {

					if (countryId !=
							commerceTaxFixedRateAddressRel.getCountryId()) {

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

			sb.append(_SQL_SELECT_COMMERCETAXFIXEDRATEADDRESSREL_WHERE);

			sb.append(_FINDER_COLUMN_COUNTRYID_COUNTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceTaxFixedRateAddressRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(countryId);

				list = (List<CommerceTaxFixedRateAddressRel>)QueryUtil.list(
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
	 * Returns the first commerce tax fixed rate address rel in the ordered set where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax fixed rate address rel
	 * @throws NoSuchTaxFixedRateAddressRelException if a matching commerce tax fixed rate address rel could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel findByCountryId_First(
			long countryId,
			OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator)
		throws NoSuchTaxFixedRateAddressRelException {

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			fetchByCountryId_First(countryId, orderByComparator);

		if (commerceTaxFixedRateAddressRel != null) {
			return commerceTaxFixedRateAddressRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("countryId=");
		sb.append(countryId);

		sb.append("}");

		throw new NoSuchTaxFixedRateAddressRelException(sb.toString());
	}

	/**
	 * Returns the first commerce tax fixed rate address rel in the ordered set where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax fixed rate address rel, or <code>null</code> if a matching commerce tax fixed rate address rel could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel fetchByCountryId_First(
		long countryId,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator) {

		List<CommerceTaxFixedRateAddressRel> list = findByCountryId(
			countryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tax fixed rate address rel in the ordered set where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax fixed rate address rel
	 * @throws NoSuchTaxFixedRateAddressRelException if a matching commerce tax fixed rate address rel could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel findByCountryId_Last(
			long countryId,
			OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator)
		throws NoSuchTaxFixedRateAddressRelException {

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			fetchByCountryId_Last(countryId, orderByComparator);

		if (commerceTaxFixedRateAddressRel != null) {
			return commerceTaxFixedRateAddressRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("countryId=");
		sb.append(countryId);

		sb.append("}");

		throw new NoSuchTaxFixedRateAddressRelException(sb.toString());
	}

	/**
	 * Returns the last commerce tax fixed rate address rel in the ordered set where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax fixed rate address rel, or <code>null</code> if a matching commerce tax fixed rate address rel could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel fetchByCountryId_Last(
		long countryId,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator) {

		int count = countByCountryId(countryId);

		if (count == 0) {
			return null;
		}

		List<CommerceTaxFixedRateAddressRel> list = findByCountryId(
			countryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tax fixed rate address rels before and after the current commerce tax fixed rate address rel in the ordered set where countryId = &#63;.
	 *
	 * @param commerceTaxFixedRateAddressRelId the primary key of the current commerce tax fixed rate address rel
	 * @param countryId the country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tax fixed rate address rel
	 * @throws NoSuchTaxFixedRateAddressRelException if a commerce tax fixed rate address rel with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel[] findByCountryId_PrevAndNext(
			long commerceTaxFixedRateAddressRelId, long countryId,
			OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator)
		throws NoSuchTaxFixedRateAddressRelException {

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			findByPrimaryKey(commerceTaxFixedRateAddressRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceTaxFixedRateAddressRel[] array =
				new CommerceTaxFixedRateAddressRelImpl[3];

			array[0] = getByCountryId_PrevAndNext(
				session, commerceTaxFixedRateAddressRel, countryId,
				orderByComparator, true);

			array[1] = commerceTaxFixedRateAddressRel;

			array[2] = getByCountryId_PrevAndNext(
				session, commerceTaxFixedRateAddressRel, countryId,
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

	protected CommerceTaxFixedRateAddressRel getByCountryId_PrevAndNext(
		Session session,
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel,
		long countryId,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCETAXFIXEDRATEADDRESSREL_WHERE);

		sb.append(_FINDER_COLUMN_COUNTRYID_COUNTRYID_2);

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
			sb.append(CommerceTaxFixedRateAddressRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(countryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceTaxFixedRateAddressRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTaxFixedRateAddressRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tax fixed rate address rels where countryId = &#63; from the database.
	 *
	 * @param countryId the country ID
	 */
	@Override
	public void removeByCountryId(long countryId) {
		for (CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel :
				findByCountryId(
					countryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceTaxFixedRateAddressRel);
		}
	}

	/**
	 * Returns the number of commerce tax fixed rate address rels where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @return the number of matching commerce tax fixed rate address rels
	 */
	@Override
	public int countByCountryId(long countryId) {
		FinderPath finderPath = _finderPathCountByCountryId;

		Object[] finderArgs = new Object[] {countryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCETAXFIXEDRATEADDRESSREL_WHERE);

			sb.append(_FINDER_COLUMN_COUNTRYID_COUNTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(countryId);

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

	private static final String _FINDER_COLUMN_COUNTRYID_COUNTRYID_2 =
		"commerceTaxFixedRateAddressRel.countryId = ?";

	public CommerceTaxFixedRateAddressRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"commerceTaxFixedRateAddressRelId", "CTaxFixedRateAddressRelId");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceTaxFixedRateAddressRel.class);

		setModelImplClass(CommerceTaxFixedRateAddressRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceTaxFixedRateAddressRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce tax fixed rate address rel in the entity cache if it is enabled.
	 *
	 * @param commerceTaxFixedRateAddressRel the commerce tax fixed rate address rel
	 */
	@Override
	public void cacheResult(
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel) {

		entityCache.putResult(
			CommerceTaxFixedRateAddressRelImpl.class,
			commerceTaxFixedRateAddressRel.getPrimaryKey(),
			commerceTaxFixedRateAddressRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce tax fixed rate address rels in the entity cache if it is enabled.
	 *
	 * @param commerceTaxFixedRateAddressRels the commerce tax fixed rate address rels
	 */
	@Override
	public void cacheResult(
		List<CommerceTaxFixedRateAddressRel> commerceTaxFixedRateAddressRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceTaxFixedRateAddressRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel :
				commerceTaxFixedRateAddressRels) {

			if (entityCache.getResult(
					CommerceTaxFixedRateAddressRelImpl.class,
					commerceTaxFixedRateAddressRel.getPrimaryKey()) == null) {

				cacheResult(commerceTaxFixedRateAddressRel);
			}
		}
	}

	/**
	 * Clears the cache for all commerce tax fixed rate address rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceTaxFixedRateAddressRelImpl.class);

		finderCache.clearCache(CommerceTaxFixedRateAddressRelImpl.class);
	}

	/**
	 * Clears the cache for the commerce tax fixed rate address rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel) {

		entityCache.removeResult(
			CommerceTaxFixedRateAddressRelImpl.class,
			commerceTaxFixedRateAddressRel);
	}

	@Override
	public void clearCache(
		List<CommerceTaxFixedRateAddressRel> commerceTaxFixedRateAddressRels) {

		for (CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel :
				commerceTaxFixedRateAddressRels) {

			entityCache.removeResult(
				CommerceTaxFixedRateAddressRelImpl.class,
				commerceTaxFixedRateAddressRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceTaxFixedRateAddressRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceTaxFixedRateAddressRelImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new commerce tax fixed rate address rel with the primary key. Does not add the commerce tax fixed rate address rel to the database.
	 *
	 * @param commerceTaxFixedRateAddressRelId the primary key for the new commerce tax fixed rate address rel
	 * @return the new commerce tax fixed rate address rel
	 */
	@Override
	public CommerceTaxFixedRateAddressRel create(
		long commerceTaxFixedRateAddressRelId) {

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			new CommerceTaxFixedRateAddressRelImpl();

		commerceTaxFixedRateAddressRel.setNew(true);
		commerceTaxFixedRateAddressRel.setPrimaryKey(
			commerceTaxFixedRateAddressRelId);

		commerceTaxFixedRateAddressRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceTaxFixedRateAddressRel;
	}

	/**
	 * Removes the commerce tax fixed rate address rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceTaxFixedRateAddressRelId the primary key of the commerce tax fixed rate address rel
	 * @return the commerce tax fixed rate address rel that was removed
	 * @throws NoSuchTaxFixedRateAddressRelException if a commerce tax fixed rate address rel with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel remove(
			long commerceTaxFixedRateAddressRelId)
		throws NoSuchTaxFixedRateAddressRelException {

		return remove((Serializable)commerceTaxFixedRateAddressRelId);
	}

	/**
	 * Removes the commerce tax fixed rate address rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce tax fixed rate address rel
	 * @return the commerce tax fixed rate address rel that was removed
	 * @throws NoSuchTaxFixedRateAddressRelException if a commerce tax fixed rate address rel with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel remove(Serializable primaryKey)
		throws NoSuchTaxFixedRateAddressRelException {

		Session session = null;

		try {
			session = openSession();

			CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
				(CommerceTaxFixedRateAddressRel)session.get(
					CommerceTaxFixedRateAddressRelImpl.class, primaryKey);

			if (commerceTaxFixedRateAddressRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTaxFixedRateAddressRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceTaxFixedRateAddressRel);
		}
		catch (NoSuchTaxFixedRateAddressRelException noSuchEntityException) {
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
	protected CommerceTaxFixedRateAddressRel removeImpl(
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceTaxFixedRateAddressRel)) {
				commerceTaxFixedRateAddressRel =
					(CommerceTaxFixedRateAddressRel)session.get(
						CommerceTaxFixedRateAddressRelImpl.class,
						commerceTaxFixedRateAddressRel.getPrimaryKeyObj());
			}

			if (commerceTaxFixedRateAddressRel != null) {
				session.delete(commerceTaxFixedRateAddressRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceTaxFixedRateAddressRel != null) {
			clearCache(commerceTaxFixedRateAddressRel);
		}

		return commerceTaxFixedRateAddressRel;
	}

	@Override
	public CommerceTaxFixedRateAddressRel updateImpl(
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel) {

		boolean isNew = commerceTaxFixedRateAddressRel.isNew();

		if (!(commerceTaxFixedRateAddressRel instanceof
				CommerceTaxFixedRateAddressRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceTaxFixedRateAddressRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceTaxFixedRateAddressRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceTaxFixedRateAddressRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceTaxFixedRateAddressRel implementation " +
					commerceTaxFixedRateAddressRel.getClass());
		}

		CommerceTaxFixedRateAddressRelModelImpl
			commerceTaxFixedRateAddressRelModelImpl =
				(CommerceTaxFixedRateAddressRelModelImpl)
					commerceTaxFixedRateAddressRel;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceTaxFixedRateAddressRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceTaxFixedRateAddressRel.setCreateDate(date);
			}
			else {
				commerceTaxFixedRateAddressRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceTaxFixedRateAddressRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceTaxFixedRateAddressRel.setModifiedDate(date);
			}
			else {
				commerceTaxFixedRateAddressRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceTaxFixedRateAddressRel);
			}
			else {
				commerceTaxFixedRateAddressRel =
					(CommerceTaxFixedRateAddressRel)session.merge(
						commerceTaxFixedRateAddressRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceTaxFixedRateAddressRelImpl.class,
			commerceTaxFixedRateAddressRelModelImpl, false, true);

		if (isNew) {
			commerceTaxFixedRateAddressRel.setNew(false);
		}

		commerceTaxFixedRateAddressRel.resetOriginalValues();

		return commerceTaxFixedRateAddressRel;
	}

	/**
	 * Returns the commerce tax fixed rate address rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce tax fixed rate address rel
	 * @return the commerce tax fixed rate address rel
	 * @throws NoSuchTaxFixedRateAddressRelException if a commerce tax fixed rate address rel with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchTaxFixedRateAddressRelException {

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			fetchByPrimaryKey(primaryKey);

		if (commerceTaxFixedRateAddressRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTaxFixedRateAddressRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceTaxFixedRateAddressRel;
	}

	/**
	 * Returns the commerce tax fixed rate address rel with the primary key or throws a <code>NoSuchTaxFixedRateAddressRelException</code> if it could not be found.
	 *
	 * @param commerceTaxFixedRateAddressRelId the primary key of the commerce tax fixed rate address rel
	 * @return the commerce tax fixed rate address rel
	 * @throws NoSuchTaxFixedRateAddressRelException if a commerce tax fixed rate address rel with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel findByPrimaryKey(
			long commerceTaxFixedRateAddressRelId)
		throws NoSuchTaxFixedRateAddressRelException {

		return findByPrimaryKey((Serializable)commerceTaxFixedRateAddressRelId);
	}

	/**
	 * Returns the commerce tax fixed rate address rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceTaxFixedRateAddressRelId the primary key of the commerce tax fixed rate address rel
	 * @return the commerce tax fixed rate address rel, or <code>null</code> if a commerce tax fixed rate address rel with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel fetchByPrimaryKey(
		long commerceTaxFixedRateAddressRelId) {

		return fetchByPrimaryKey(
			(Serializable)commerceTaxFixedRateAddressRelId);
	}

	/**
	 * Returns all the commerce tax fixed rate address rels.
	 *
	 * @return the commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax fixed rate address rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateAddressRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax fixed rate address rels
	 * @param end the upper bound of the range of commerce tax fixed rate address rels (not inclusive)
	 * @return the range of commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rate address rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateAddressRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax fixed rate address rels
	 * @param end the upper bound of the range of commerce tax fixed rate address rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findAll(
		int start, int end,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rate address rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxFixedRateAddressRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax fixed rate address rels
	 * @param end the upper bound of the range of commerce tax fixed rate address rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findAll(
		int start, int end,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator,
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

		List<CommerceTaxFixedRateAddressRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceTaxFixedRateAddressRel>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCETAXFIXEDRATEADDRESSREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCETAXFIXEDRATEADDRESSREL;

				sql = sql.concat(
					CommerceTaxFixedRateAddressRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceTaxFixedRateAddressRel>)QueryUtil.list(
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
	 * Removes all the commerce tax fixed rate address rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel :
				findAll()) {

			remove(commerceTaxFixedRateAddressRel);
		}
	}

	/**
	 * Returns the number of commerce tax fixed rate address rels.
	 *
	 * @return the number of commerce tax fixed rate address rels
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
					_SQL_COUNT_COMMERCETAXFIXEDRATEADDRESSREL);

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
		return "CTaxFixedRateAddressRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCETAXFIXEDRATEADDRESSREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceTaxFixedRateAddressRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce tax fixed rate address rel persistence.
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

		_finderPathWithPaginationFindByCommerceTaxMethodId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceTaxMethodId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceTaxMethodId"}, true);

		_finderPathWithoutPaginationFindByCommerceTaxMethodId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceTaxMethodId", new String[] {Long.class.getName()},
			new String[] {"commerceTaxMethodId"}, true);

		_finderPathCountByCommerceTaxMethodId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceTaxMethodId", new String[] {Long.class.getName()},
			new String[] {"commerceTaxMethodId"}, false);

		_finderPathWithPaginationFindByCPTaxCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPTaxCategoryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPTaxCategoryId"}, true);

		_finderPathWithoutPaginationFindByCPTaxCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPTaxCategoryId",
			new String[] {Long.class.getName()},
			new String[] {"CPTaxCategoryId"}, true);

		_finderPathCountByCPTaxCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPTaxCategoryId",
			new String[] {Long.class.getName()},
			new String[] {"CPTaxCategoryId"}, false);

		_finderPathWithPaginationFindByCountryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCountryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"countryId"}, true);

		_finderPathWithoutPaginationFindByCountryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCountryId",
			new String[] {Long.class.getName()}, new String[] {"countryId"},
			true);

		_finderPathCountByCountryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCountryId",
			new String[] {Long.class.getName()}, new String[] {"countryId"},
			false);
	}

	public void destroy() {
		entityCache.removeCache(
			CommerceTaxFixedRateAddressRelImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCETAXFIXEDRATEADDRESSREL =
		"SELECT commerceTaxFixedRateAddressRel FROM CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel";

	private static final String
		_SQL_SELECT_COMMERCETAXFIXEDRATEADDRESSREL_WHERE =
			"SELECT commerceTaxFixedRateAddressRel FROM CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel WHERE ";

	private static final String _SQL_COUNT_COMMERCETAXFIXEDRATEADDRESSREL =
		"SELECT COUNT(commerceTaxFixedRateAddressRel) FROM CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel";

	private static final String
		_SQL_COUNT_COMMERCETAXFIXEDRATEADDRESSREL_WHERE =
			"SELECT COUNT(commerceTaxFixedRateAddressRel) FROM CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceTaxFixedRateAddressRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceTaxFixedRateAddressRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceTaxFixedRateAddressRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceTaxFixedRateAddressRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"commerceTaxFixedRateAddressRelId"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}