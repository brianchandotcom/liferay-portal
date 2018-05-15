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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.tax.engine.fixed.exception.NoSuchTaxFixedRateAddressRelException;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateAddressRelImpl;
import com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateAddressRelModelImpl;
import com.liferay.commerce.tax.engine.fixed.service.persistence.CommerceTaxFixedRateAddressRelPersistence;

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
 * The persistence implementation for the commerce tax fixed rate address rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateAddressRelPersistence
 * @see com.liferay.commerce.tax.engine.fixed.service.persistence.CommerceTaxFixedRateAddressRelUtil
 * @generated
 */
@ProviderType
public class CommerceTaxFixedRateAddressRelPersistenceImpl
	extends BasePersistenceImpl<CommerceTaxFixedRateAddressRel>
	implements CommerceTaxFixedRateAddressRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceTaxFixedRateAddressRelUtil} to access the commerce tax fixed rate address rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceTaxFixedRateAddressRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceTaxFixedRateAddressRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateAddressRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxFixedRateAddressRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceTaxFixedRateAddressRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateAddressRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxFixedRateAddressRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceTaxFixedRateAddressRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateAddressRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCETAXMETHODID =
		new FinderPath(CommerceTaxFixedRateAddressRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateAddressRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxFixedRateAddressRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceTaxMethodId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCETAXMETHODID =
		new FinderPath(CommerceTaxFixedRateAddressRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateAddressRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxFixedRateAddressRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceTaxMethodId", new String[] { Long.class.getName() },
			CommerceTaxFixedRateAddressRelModelImpl.COMMERCETAXMETHODID_COLUMN_BITMASK |
			CommerceTaxFixedRateAddressRelModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCETAXMETHODID = new FinderPath(CommerceTaxFixedRateAddressRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateAddressRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceTaxMethodId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce tax fixed rate address rels where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the matching commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findByCommerceTaxMethodId(
		long commerceTaxMethodId) {
		return findByCommerceTaxMethodId(commerceTaxMethodId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax fixed rate address rels where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxFixedRateAddressRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxFixedRateAddressRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return findByCommerceTaxMethodId(commerceTaxMethodId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rate address rels where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxFixedRateAddressRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param start the lower bound of the range of commerce tax fixed rate address rels
	 * @param end the upper bound of the range of commerce tax fixed rate address rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCETAXMETHODID;
			finderArgs = new Object[] { commerceTaxMethodId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCETAXMETHODID;
			finderArgs = new Object[] {
					commerceTaxMethodId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceTaxFixedRateAddressRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTaxFixedRateAddressRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel : list) {
					if ((commerceTaxMethodId != commerceTaxFixedRateAddressRel.getCommerceTaxMethodId())) {
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

			query.append(_SQL_SELECT_COMMERCETAXFIXEDRATEADDRESSREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCETAXMETHODID_COMMERCETAXMETHODID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceTaxFixedRateAddressRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceTaxMethodId);

				if (!pagination) {
					list = (List<CommerceTaxFixedRateAddressRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTaxFixedRateAddressRel>)QueryUtil.list(q,
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
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel = fetchByCommerceTaxMethodId_First(commerceTaxMethodId,
				orderByComparator);

		if (commerceTaxFixedRateAddressRel != null) {
			return commerceTaxFixedRateAddressRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceTaxMethodId=");
		msg.append(commerceTaxMethodId);

		msg.append("}");

		throw new NoSuchTaxFixedRateAddressRelException(msg.toString());
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
		List<CommerceTaxFixedRateAddressRel> list = findByCommerceTaxMethodId(commerceTaxMethodId,
				0, 1, orderByComparator);

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
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel = fetchByCommerceTaxMethodId_Last(commerceTaxMethodId,
				orderByComparator);

		if (commerceTaxFixedRateAddressRel != null) {
			return commerceTaxFixedRateAddressRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceTaxMethodId=");
		msg.append(commerceTaxMethodId);

		msg.append("}");

		throw new NoSuchTaxFixedRateAddressRelException(msg.toString());
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

		List<CommerceTaxFixedRateAddressRel> list = findByCommerceTaxMethodId(commerceTaxMethodId,
				count - 1, count, orderByComparator);

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
	public CommerceTaxFixedRateAddressRel[] findByCommerceTaxMethodId_PrevAndNext(
		long commerceTaxFixedRateAddressRelId, long commerceTaxMethodId,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator)
		throws NoSuchTaxFixedRateAddressRelException {
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel = findByPrimaryKey(commerceTaxFixedRateAddressRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceTaxFixedRateAddressRel[] array = new CommerceTaxFixedRateAddressRelImpl[3];

			array[0] = getByCommerceTaxMethodId_PrevAndNext(session,
					commerceTaxFixedRateAddressRel, commerceTaxMethodId,
					orderByComparator, true);

			array[1] = commerceTaxFixedRateAddressRel;

			array[2] = getByCommerceTaxMethodId_PrevAndNext(session,
					commerceTaxFixedRateAddressRel, commerceTaxMethodId,
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

	protected CommerceTaxFixedRateAddressRel getByCommerceTaxMethodId_PrevAndNext(
		Session session,
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel,
		long commerceTaxMethodId,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCETAXFIXEDRATEADDRESSREL_WHERE);

		query.append(_FINDER_COLUMN_COMMERCETAXMETHODID_COMMERCETAXMETHODID_2);

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
			query.append(CommerceTaxFixedRateAddressRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceTaxMethodId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceTaxFixedRateAddressRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceTaxFixedRateAddressRel> list = q.list();

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
		for (CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel : findByCommerceTaxMethodId(
				commerceTaxMethodId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCETAXMETHODID;

		Object[] finderArgs = new Object[] { commerceTaxMethodId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCETAXFIXEDRATEADDRESSREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCETAXMETHODID_COMMERCETAXMETHODID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceTaxMethodId);

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

	private static final String _FINDER_COLUMN_COMMERCETAXMETHODID_COMMERCETAXMETHODID_2 =
		"commerceTaxFixedRateAddressRel.commerceTaxMethodId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CPTAXCATEGORYID =
		new FinderPath(CommerceTaxFixedRateAddressRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateAddressRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxFixedRateAddressRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPTaxCategoryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPTAXCATEGORYID =
		new FinderPath(CommerceTaxFixedRateAddressRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateAddressRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxFixedRateAddressRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPTaxCategoryId",
			new String[] { Long.class.getName() },
			CommerceTaxFixedRateAddressRelModelImpl.CPTAXCATEGORYID_COLUMN_BITMASK |
			CommerceTaxFixedRateAddressRelModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CPTAXCATEGORYID = new FinderPath(CommerceTaxFixedRateAddressRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateAddressRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCPTaxCategoryId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce tax fixed rate address rels where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the matching commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findByCPTaxCategoryId(
		long CPTaxCategoryId) {
		return findByCPTaxCategoryId(CPTaxCategoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax fixed rate address rels where CPTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxFixedRateAddressRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxFixedRateAddressRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return findByCPTaxCategoryId(CPTaxCategoryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rate address rels where CPTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxFixedRateAddressRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param start the lower bound of the range of commerce tax fixed rate address rels
	 * @param end the upper bound of the range of commerce tax fixed rate address rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPTAXCATEGORYID;
			finderArgs = new Object[] { CPTaxCategoryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CPTAXCATEGORYID;
			finderArgs = new Object[] {
					CPTaxCategoryId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceTaxFixedRateAddressRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTaxFixedRateAddressRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel : list) {
					if ((CPTaxCategoryId != commerceTaxFixedRateAddressRel.getCPTaxCategoryId())) {
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

			query.append(_SQL_SELECT_COMMERCETAXFIXEDRATEADDRESSREL_WHERE);

			query.append(_FINDER_COLUMN_CPTAXCATEGORYID_CPTAXCATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceTaxFixedRateAddressRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPTaxCategoryId);

				if (!pagination) {
					list = (List<CommerceTaxFixedRateAddressRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTaxFixedRateAddressRel>)QueryUtil.list(q,
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
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel = fetchByCPTaxCategoryId_First(CPTaxCategoryId,
				orderByComparator);

		if (commerceTaxFixedRateAddressRel != null) {
			return commerceTaxFixedRateAddressRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPTaxCategoryId=");
		msg.append(CPTaxCategoryId);

		msg.append("}");

		throw new NoSuchTaxFixedRateAddressRelException(msg.toString());
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
		List<CommerceTaxFixedRateAddressRel> list = findByCPTaxCategoryId(CPTaxCategoryId,
				0, 1, orderByComparator);

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
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel = fetchByCPTaxCategoryId_Last(CPTaxCategoryId,
				orderByComparator);

		if (commerceTaxFixedRateAddressRel != null) {
			return commerceTaxFixedRateAddressRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPTaxCategoryId=");
		msg.append(CPTaxCategoryId);

		msg.append("}");

		throw new NoSuchTaxFixedRateAddressRelException(msg.toString());
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

		List<CommerceTaxFixedRateAddressRel> list = findByCPTaxCategoryId(CPTaxCategoryId,
				count - 1, count, orderByComparator);

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
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel = findByPrimaryKey(commerceTaxFixedRateAddressRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceTaxFixedRateAddressRel[] array = new CommerceTaxFixedRateAddressRelImpl[3];

			array[0] = getByCPTaxCategoryId_PrevAndNext(session,
					commerceTaxFixedRateAddressRel, CPTaxCategoryId,
					orderByComparator, true);

			array[1] = commerceTaxFixedRateAddressRel;

			array[2] = getByCPTaxCategoryId_PrevAndNext(session,
					commerceTaxFixedRateAddressRel, CPTaxCategoryId,
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

	protected CommerceTaxFixedRateAddressRel getByCPTaxCategoryId_PrevAndNext(
		Session session,
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel,
		long CPTaxCategoryId,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCETAXFIXEDRATEADDRESSREL_WHERE);

		query.append(_FINDER_COLUMN_CPTAXCATEGORYID_CPTAXCATEGORYID_2);

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
			query.append(CommerceTaxFixedRateAddressRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(CPTaxCategoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceTaxFixedRateAddressRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceTaxFixedRateAddressRel> list = q.list();

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
		for (CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel : findByCPTaxCategoryId(
				CPTaxCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CPTAXCATEGORYID;

		Object[] finderArgs = new Object[] { CPTaxCategoryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCETAXFIXEDRATEADDRESSREL_WHERE);

			query.append(_FINDER_COLUMN_CPTAXCATEGORYID_CPTAXCATEGORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPTaxCategoryId);

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

	private static final String _FINDER_COLUMN_CPTAXCATEGORYID_CPTAXCATEGORYID_2 =
		"commerceTaxFixedRateAddressRel.CPTaxCategoryId = ?";

	public CommerceTaxFixedRateAddressRelPersistenceImpl() {
		setModelClass(CommerceTaxFixedRateAddressRel.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("commerceTaxFixedRateAddressRelId",
				"CTaxFixedRateAddressRelId");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the commerce tax fixed rate address rel in the entity cache if it is enabled.
	 *
	 * @param commerceTaxFixedRateAddressRel the commerce tax fixed rate address rel
	 */
	@Override
	public void cacheResult(
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel) {
		entityCache.putResult(CommerceTaxFixedRateAddressRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateAddressRelImpl.class,
			commerceTaxFixedRateAddressRel.getPrimaryKey(),
			commerceTaxFixedRateAddressRel);

		commerceTaxFixedRateAddressRel.resetOriginalValues();
	}

	/**
	 * Caches the commerce tax fixed rate address rels in the entity cache if it is enabled.
	 *
	 * @param commerceTaxFixedRateAddressRels the commerce tax fixed rate address rels
	 */
	@Override
	public void cacheResult(
		List<CommerceTaxFixedRateAddressRel> commerceTaxFixedRateAddressRels) {
		for (CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel : commerceTaxFixedRateAddressRels) {
			if (entityCache.getResult(
						CommerceTaxFixedRateAddressRelModelImpl.ENTITY_CACHE_ENABLED,
						CommerceTaxFixedRateAddressRelImpl.class,
						commerceTaxFixedRateAddressRel.getPrimaryKey()) == null) {
				cacheResult(commerceTaxFixedRateAddressRel);
			}
			else {
				commerceTaxFixedRateAddressRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce tax fixed rate address rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceTaxFixedRateAddressRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce tax fixed rate address rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel) {
		entityCache.removeResult(CommerceTaxFixedRateAddressRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateAddressRelImpl.class,
			commerceTaxFixedRateAddressRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<CommerceTaxFixedRateAddressRel> commerceTaxFixedRateAddressRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel : commerceTaxFixedRateAddressRels) {
			entityCache.removeResult(CommerceTaxFixedRateAddressRelModelImpl.ENTITY_CACHE_ENABLED,
				CommerceTaxFixedRateAddressRelImpl.class,
				commerceTaxFixedRateAddressRel.getPrimaryKey());
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
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel = new CommerceTaxFixedRateAddressRelImpl();

		commerceTaxFixedRateAddressRel.setNew(true);
		commerceTaxFixedRateAddressRel.setPrimaryKey(commerceTaxFixedRateAddressRelId);

		commerceTaxFixedRateAddressRel.setCompanyId(companyProvider.getCompanyId());

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

			CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel = (CommerceTaxFixedRateAddressRel)session.get(CommerceTaxFixedRateAddressRelImpl.class,
					primaryKey);

			if (commerceTaxFixedRateAddressRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTaxFixedRateAddressRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceTaxFixedRateAddressRel);
		}
		catch (NoSuchTaxFixedRateAddressRelException nsee) {
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
	protected CommerceTaxFixedRateAddressRel removeImpl(
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceTaxFixedRateAddressRel)) {
				commerceTaxFixedRateAddressRel = (CommerceTaxFixedRateAddressRel)session.get(CommerceTaxFixedRateAddressRelImpl.class,
						commerceTaxFixedRateAddressRel.getPrimaryKeyObj());
			}

			if (commerceTaxFixedRateAddressRel != null) {
				session.delete(commerceTaxFixedRateAddressRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
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

		if (!(commerceTaxFixedRateAddressRel instanceof CommerceTaxFixedRateAddressRelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
						commerceTaxFixedRateAddressRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(commerceTaxFixedRateAddressRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceTaxFixedRateAddressRel proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceTaxFixedRateAddressRel implementation " +
				commerceTaxFixedRateAddressRel.getClass());
		}

		CommerceTaxFixedRateAddressRelModelImpl commerceTaxFixedRateAddressRelModelImpl =
			(CommerceTaxFixedRateAddressRelModelImpl)commerceTaxFixedRateAddressRel;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceTaxFixedRateAddressRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceTaxFixedRateAddressRel.setCreateDate(now);
			}
			else {
				commerceTaxFixedRateAddressRel.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceTaxFixedRateAddressRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceTaxFixedRateAddressRel.setModifiedDate(now);
			}
			else {
				commerceTaxFixedRateAddressRel.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceTaxFixedRateAddressRel.isNew()) {
				session.save(commerceTaxFixedRateAddressRel);

				commerceTaxFixedRateAddressRel.setNew(false);
			}
			else {
				commerceTaxFixedRateAddressRel = (CommerceTaxFixedRateAddressRel)session.merge(commerceTaxFixedRateAddressRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceTaxFixedRateAddressRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceTaxFixedRateAddressRelModelImpl.getCommerceTaxMethodId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCETAXMETHODID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCETAXMETHODID,
				args);

			args = new Object[] {
					commerceTaxFixedRateAddressRelModelImpl.getCPTaxCategoryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPTAXCATEGORYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPTAXCATEGORYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceTaxFixedRateAddressRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCETAXMETHODID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceTaxFixedRateAddressRelModelImpl.getOriginalCommerceTaxMethodId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCETAXMETHODID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCETAXMETHODID,
					args);

				args = new Object[] {
						commerceTaxFixedRateAddressRelModelImpl.getCommerceTaxMethodId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCETAXMETHODID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCETAXMETHODID,
					args);
			}

			if ((commerceTaxFixedRateAddressRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPTAXCATEGORYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceTaxFixedRateAddressRelModelImpl.getOriginalCPTaxCategoryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPTAXCATEGORYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPTAXCATEGORYID,
					args);

				args = new Object[] {
						commerceTaxFixedRateAddressRelModelImpl.getCPTaxCategoryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPTAXCATEGORYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPTAXCATEGORYID,
					args);
			}
		}

		entityCache.putResult(CommerceTaxFixedRateAddressRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxFixedRateAddressRelImpl.class,
			commerceTaxFixedRateAddressRel.getPrimaryKey(),
			commerceTaxFixedRateAddressRel, false);

		commerceTaxFixedRateAddressRel.resetOriginalValues();

		return commerceTaxFixedRateAddressRel;
	}

	/**
	 * Returns the commerce tax fixed rate address rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce tax fixed rate address rel
	 * @return the commerce tax fixed rate address rel
	 * @throws NoSuchTaxFixedRateAddressRelException if a commerce tax fixed rate address rel with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel findByPrimaryKey(
		Serializable primaryKey) throws NoSuchTaxFixedRateAddressRelException {
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel = fetchByPrimaryKey(primaryKey);

		if (commerceTaxFixedRateAddressRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTaxFixedRateAddressRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceTaxFixedRateAddressRel;
	}

	/**
	 * Returns the commerce tax fixed rate address rel with the primary key or throws a {@link NoSuchTaxFixedRateAddressRelException} if it could not be found.
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
	 * @param primaryKey the primary key of the commerce tax fixed rate address rel
	 * @return the commerce tax fixed rate address rel, or <code>null</code> if a commerce tax fixed rate address rel with the primary key could not be found
	 */
	@Override
	public CommerceTaxFixedRateAddressRel fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceTaxFixedRateAddressRelModelImpl.ENTITY_CACHE_ENABLED,
				CommerceTaxFixedRateAddressRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel = (CommerceTaxFixedRateAddressRel)serializable;

		if (commerceTaxFixedRateAddressRel == null) {
			Session session = null;

			try {
				session = openSession();

				commerceTaxFixedRateAddressRel = (CommerceTaxFixedRateAddressRel)session.get(CommerceTaxFixedRateAddressRelImpl.class,
						primaryKey);

				if (commerceTaxFixedRateAddressRel != null) {
					cacheResult(commerceTaxFixedRateAddressRel);
				}
				else {
					entityCache.putResult(CommerceTaxFixedRateAddressRelModelImpl.ENTITY_CACHE_ENABLED,
						CommerceTaxFixedRateAddressRelImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceTaxFixedRateAddressRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceTaxFixedRateAddressRelImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceTaxFixedRateAddressRel;
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
		return fetchByPrimaryKey((Serializable)commerceTaxFixedRateAddressRelId);
	}

	@Override
	public Map<Serializable, CommerceTaxFixedRateAddressRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceTaxFixedRateAddressRel> map = new HashMap<Serializable, CommerceTaxFixedRateAddressRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel = fetchByPrimaryKey(primaryKey);

			if (commerceTaxFixedRateAddressRel != null) {
				map.put(primaryKey, commerceTaxFixedRateAddressRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceTaxFixedRateAddressRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceTaxFixedRateAddressRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(CommerceTaxFixedRateAddressRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCETAXFIXEDRATEADDRESSREL_WHERE_PKS_IN);

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

			for (CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel : (List<CommerceTaxFixedRateAddressRel>)q.list()) {
				map.put(commerceTaxFixedRateAddressRel.getPrimaryKeyObj(),
					commerceTaxFixedRateAddressRel);

				cacheResult(commerceTaxFixedRateAddressRel);

				uncachedPrimaryKeys.remove(commerceTaxFixedRateAddressRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceTaxFixedRateAddressRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceTaxFixedRateAddressRelImpl.class, primaryKey,
					nullModel);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxFixedRateAddressRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxFixedRateAddressRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax fixed rate address rels
	 * @param end the upper bound of the range of commerce tax fixed rate address rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findAll(int start, int end,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax fixed rate address rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxFixedRateAddressRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax fixed rate address rels
	 * @param end the upper bound of the range of commerce tax fixed rate address rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce tax fixed rate address rels
	 */
	@Override
	public List<CommerceTaxFixedRateAddressRel> findAll(int start, int end,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator,
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

		List<CommerceTaxFixedRateAddressRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTaxFixedRateAddressRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCETAXFIXEDRATEADDRESSREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCETAXFIXEDRATEADDRESSREL;

				if (pagination) {
					sql = sql.concat(CommerceTaxFixedRateAddressRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceTaxFixedRateAddressRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTaxFixedRateAddressRel>)QueryUtil.list(q,
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
	 * Removes all the commerce tax fixed rate address rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel : findAll()) {
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
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCETAXFIXEDRATEADDRESSREL);

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
		return CommerceTaxFixedRateAddressRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce tax fixed rate address rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceTaxFixedRateAddressRelImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCETAXFIXEDRATEADDRESSREL = "SELECT commerceTaxFixedRateAddressRel FROM CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel";
	private static final String _SQL_SELECT_COMMERCETAXFIXEDRATEADDRESSREL_WHERE_PKS_IN =
		"SELECT commerceTaxFixedRateAddressRel FROM CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel WHERE CTaxFixedRateAddressRelId IN (";
	private static final String _SQL_SELECT_COMMERCETAXFIXEDRATEADDRESSREL_WHERE =
		"SELECT commerceTaxFixedRateAddressRel FROM CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel WHERE ";
	private static final String _SQL_COUNT_COMMERCETAXFIXEDRATEADDRESSREL = "SELECT COUNT(commerceTaxFixedRateAddressRel) FROM CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel";
	private static final String _SQL_COUNT_COMMERCETAXFIXEDRATEADDRESSREL_WHERE = "SELECT COUNT(commerceTaxFixedRateAddressRel) FROM CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceTaxFixedRateAddressRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceTaxFixedRateAddressRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceTaxFixedRateAddressRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceTaxFixedRateAddressRelPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"commerceTaxFixedRateAddressRelId"
			});
}