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

package com.liferay.commerce.inventory.service.persistence.impl;

import com.liferay.commerce.inventory.exception.NoSuchInventoryAuditException;
import com.liferay.commerce.inventory.model.CommerceInventoryAudit;
import com.liferay.commerce.inventory.model.CommerceInventoryAuditTable;
import com.liferay.commerce.inventory.model.impl.CommerceInventoryAuditImpl;
import com.liferay.commerce.inventory.model.impl.CommerceInventoryAuditModelImpl;
import com.liferay.commerce.inventory.service.persistence.CommerceInventoryAuditPersistence;
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

import java.sql.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the commerce inventory audit service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceInventoryAuditPersistenceImpl
	extends BasePersistenceImpl<CommerceInventoryAudit>
	implements CommerceInventoryAuditPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceInventoryAuditUtil</code> to access the commerce inventory audit persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceInventoryAuditImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByLtCreateDate;
	private FinderPath _finderPathWithPaginationCountByLtCreateDate;

	/**
	 * Returns all the commerce inventory audits where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @return the matching commerce inventory audits
	 */
	@Override
	public List<CommerceInventoryAudit> findByLtCreateDate(Date createDate) {
		return findByLtCreateDate(
			createDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory audits where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of commerce inventory audits
	 * @param end the upper bound of the range of commerce inventory audits (not inclusive)
	 * @return the range of matching commerce inventory audits
	 */
	@Override
	public List<CommerceInventoryAudit> findByLtCreateDate(
		Date createDate, int start, int end) {

		return findByLtCreateDate(createDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory audits where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of commerce inventory audits
	 * @param end the upper bound of the range of commerce inventory audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory audits
	 */
	@Override
	public List<CommerceInventoryAudit> findByLtCreateDate(
		Date createDate, int start, int end,
		OrderByComparator<CommerceInventoryAudit> orderByComparator) {

		return findByLtCreateDate(
			createDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory audits where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of commerce inventory audits
	 * @param end the upper bound of the range of commerce inventory audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory audits
	 */
	@Override
	public List<CommerceInventoryAudit> findByLtCreateDate(
		Date createDate, int start, int end,
		OrderByComparator<CommerceInventoryAudit> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLtCreateDate;
		finderArgs = new Object[] {
			_getTime(createDate), start, end, orderByComparator
		};

		List<CommerceInventoryAudit> list = null;

		if (useFinderCache) {
			list = (List<CommerceInventoryAudit>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceInventoryAudit commerceInventoryAudit : list) {
					if (createDate.getTime() <=
							commerceInventoryAudit.getCreateDate(
							).getTime()) {

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

			sb.append(_SQL_SELECT_COMMERCEINVENTORYAUDIT_WHERE);

			boolean bindCreateDate = false;

			if (createDate == null) {
				sb.append(_FINDER_COLUMN_LTCREATEDATE_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				sb.append(_FINDER_COLUMN_LTCREATEDATE_CREATEDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceInventoryAuditModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCreateDate) {
					queryPos.add(new Timestamp(createDate.getTime()));
				}

				list = (List<CommerceInventoryAudit>)QueryUtil.list(
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
	 * Returns the first commerce inventory audit in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory audit
	 * @throws NoSuchInventoryAuditException if a matching commerce inventory audit could not be found
	 */
	@Override
	public CommerceInventoryAudit findByLtCreateDate_First(
			Date createDate,
			OrderByComparator<CommerceInventoryAudit> orderByComparator)
		throws NoSuchInventoryAuditException {

		CommerceInventoryAudit commerceInventoryAudit =
			fetchByLtCreateDate_First(createDate, orderByComparator);

		if (commerceInventoryAudit != null) {
			return commerceInventoryAudit;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("createDate<");
		sb.append(createDate);

		sb.append("}");

		throw new NoSuchInventoryAuditException(sb.toString());
	}

	/**
	 * Returns the first commerce inventory audit in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory audit, or <code>null</code> if a matching commerce inventory audit could not be found
	 */
	@Override
	public CommerceInventoryAudit fetchByLtCreateDate_First(
		Date createDate,
		OrderByComparator<CommerceInventoryAudit> orderByComparator) {

		List<CommerceInventoryAudit> list = findByLtCreateDate(
			createDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce inventory audit in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory audit
	 * @throws NoSuchInventoryAuditException if a matching commerce inventory audit could not be found
	 */
	@Override
	public CommerceInventoryAudit findByLtCreateDate_Last(
			Date createDate,
			OrderByComparator<CommerceInventoryAudit> orderByComparator)
		throws NoSuchInventoryAuditException {

		CommerceInventoryAudit commerceInventoryAudit =
			fetchByLtCreateDate_Last(createDate, orderByComparator);

		if (commerceInventoryAudit != null) {
			return commerceInventoryAudit;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("createDate<");
		sb.append(createDate);

		sb.append("}");

		throw new NoSuchInventoryAuditException(sb.toString());
	}

	/**
	 * Returns the last commerce inventory audit in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory audit, or <code>null</code> if a matching commerce inventory audit could not be found
	 */
	@Override
	public CommerceInventoryAudit fetchByLtCreateDate_Last(
		Date createDate,
		OrderByComparator<CommerceInventoryAudit> orderByComparator) {

		int count = countByLtCreateDate(createDate);

		if (count == 0) {
			return null;
		}

		List<CommerceInventoryAudit> list = findByLtCreateDate(
			createDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce inventory audits before and after the current commerce inventory audit in the ordered set where createDate &lt; &#63;.
	 *
	 * @param commerceInventoryAuditId the primary key of the current commerce inventory audit
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory audit
	 * @throws NoSuchInventoryAuditException if a commerce inventory audit with the primary key could not be found
	 */
	@Override
	public CommerceInventoryAudit[] findByLtCreateDate_PrevAndNext(
			long commerceInventoryAuditId, Date createDate,
			OrderByComparator<CommerceInventoryAudit> orderByComparator)
		throws NoSuchInventoryAuditException {

		CommerceInventoryAudit commerceInventoryAudit = findByPrimaryKey(
			commerceInventoryAuditId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryAudit[] array = new CommerceInventoryAuditImpl[3];

			array[0] = getByLtCreateDate_PrevAndNext(
				session, commerceInventoryAudit, createDate, orderByComparator,
				true);

			array[1] = commerceInventoryAudit;

			array[2] = getByLtCreateDate_PrevAndNext(
				session, commerceInventoryAudit, createDate, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceInventoryAudit getByLtCreateDate_PrevAndNext(
		Session session, CommerceInventoryAudit commerceInventoryAudit,
		Date createDate,
		OrderByComparator<CommerceInventoryAudit> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEINVENTORYAUDIT_WHERE);

		boolean bindCreateDate = false;

		if (createDate == null) {
			sb.append(_FINDER_COLUMN_LTCREATEDATE_CREATEDATE_1);
		}
		else {
			bindCreateDate = true;

			sb.append(_FINDER_COLUMN_LTCREATEDATE_CREATEDATE_2);
		}

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
			sb.append(CommerceInventoryAuditModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindCreateDate) {
			queryPos.add(new Timestamp(createDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceInventoryAudit)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryAudit> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce inventory audits where createDate &lt; &#63; from the database.
	 *
	 * @param createDate the create date
	 */
	@Override
	public void removeByLtCreateDate(Date createDate) {
		for (CommerceInventoryAudit commerceInventoryAudit :
				findByLtCreateDate(
					createDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceInventoryAudit);
		}
	}

	/**
	 * Returns the number of commerce inventory audits where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @return the number of matching commerce inventory audits
	 */
	@Override
	public int countByLtCreateDate(Date createDate) {
		FinderPath finderPath = _finderPathWithPaginationCountByLtCreateDate;

		Object[] finderArgs = new Object[] {_getTime(createDate)};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYAUDIT_WHERE);

			boolean bindCreateDate = false;

			if (createDate == null) {
				sb.append(_FINDER_COLUMN_LTCREATEDATE_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				sb.append(_FINDER_COLUMN_LTCREATEDATE_CREATEDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCreateDate) {
					queryPos.add(new Timestamp(createDate.getTime()));
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

	private static final String _FINDER_COLUMN_LTCREATEDATE_CREATEDATE_1 =
		"commerceInventoryAudit.createDate IS NULL";

	private static final String _FINDER_COLUMN_LTCREATEDATE_CREATEDATE_2 =
		"commerceInventoryAudit.createDate < ?";

	private FinderPath _finderPathWithPaginationFindByC_S;
	private FinderPath _finderPathWithoutPaginationFindByC_S;
	private FinderPath _finderPathCountByC_S;

	/**
	 * Returns all the commerce inventory audits where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @return the matching commerce inventory audits
	 */
	@Override
	public List<CommerceInventoryAudit> findByC_S(long companyId, String sku) {
		return findByC_S(
			companyId, sku, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory audits where companyId = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param start the lower bound of the range of commerce inventory audits
	 * @param end the upper bound of the range of commerce inventory audits (not inclusive)
	 * @return the range of matching commerce inventory audits
	 */
	@Override
	public List<CommerceInventoryAudit> findByC_S(
		long companyId, String sku, int start, int end) {

		return findByC_S(companyId, sku, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory audits where companyId = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param start the lower bound of the range of commerce inventory audits
	 * @param end the upper bound of the range of commerce inventory audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory audits
	 */
	@Override
	public List<CommerceInventoryAudit> findByC_S(
		long companyId, String sku, int start, int end,
		OrderByComparator<CommerceInventoryAudit> orderByComparator) {

		return findByC_S(companyId, sku, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory audits where companyId = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param start the lower bound of the range of commerce inventory audits
	 * @param end the upper bound of the range of commerce inventory audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory audits
	 */
	@Override
	public List<CommerceInventoryAudit> findByC_S(
		long companyId, String sku, int start, int end,
		OrderByComparator<CommerceInventoryAudit> orderByComparator,
		boolean useFinderCache) {

		sku = Objects.toString(sku, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_S;
				finderArgs = new Object[] {companyId, sku};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_S;
			finderArgs = new Object[] {
				companyId, sku, start, end, orderByComparator
			};
		}

		List<CommerceInventoryAudit> list = null;

		if (useFinderCache) {
			list = (List<CommerceInventoryAudit>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceInventoryAudit commerceInventoryAudit : list) {
					if ((companyId != commerceInventoryAudit.getCompanyId()) ||
						!sku.equals(commerceInventoryAudit.getSku())) {

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

			sb.append(_SQL_SELECT_COMMERCEINVENTORYAUDIT_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			boolean bindSku = false;

			if (sku.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_S_SKU_3);
			}
			else {
				bindSku = true;

				sb.append(_FINDER_COLUMN_C_S_SKU_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceInventoryAuditModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindSku) {
					queryPos.add(sku);
				}

				list = (List<CommerceInventoryAudit>)QueryUtil.list(
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
	 * Returns the first commerce inventory audit in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory audit
	 * @throws NoSuchInventoryAuditException if a matching commerce inventory audit could not be found
	 */
	@Override
	public CommerceInventoryAudit findByC_S_First(
			long companyId, String sku,
			OrderByComparator<CommerceInventoryAudit> orderByComparator)
		throws NoSuchInventoryAuditException {

		CommerceInventoryAudit commerceInventoryAudit = fetchByC_S_First(
			companyId, sku, orderByComparator);

		if (commerceInventoryAudit != null) {
			return commerceInventoryAudit;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", sku=");
		sb.append(sku);

		sb.append("}");

		throw new NoSuchInventoryAuditException(sb.toString());
	}

	/**
	 * Returns the first commerce inventory audit in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory audit, or <code>null</code> if a matching commerce inventory audit could not be found
	 */
	@Override
	public CommerceInventoryAudit fetchByC_S_First(
		long companyId, String sku,
		OrderByComparator<CommerceInventoryAudit> orderByComparator) {

		List<CommerceInventoryAudit> list = findByC_S(
			companyId, sku, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce inventory audit in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory audit
	 * @throws NoSuchInventoryAuditException if a matching commerce inventory audit could not be found
	 */
	@Override
	public CommerceInventoryAudit findByC_S_Last(
			long companyId, String sku,
			OrderByComparator<CommerceInventoryAudit> orderByComparator)
		throws NoSuchInventoryAuditException {

		CommerceInventoryAudit commerceInventoryAudit = fetchByC_S_Last(
			companyId, sku, orderByComparator);

		if (commerceInventoryAudit != null) {
			return commerceInventoryAudit;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", sku=");
		sb.append(sku);

		sb.append("}");

		throw new NoSuchInventoryAuditException(sb.toString());
	}

	/**
	 * Returns the last commerce inventory audit in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory audit, or <code>null</code> if a matching commerce inventory audit could not be found
	 */
	@Override
	public CommerceInventoryAudit fetchByC_S_Last(
		long companyId, String sku,
		OrderByComparator<CommerceInventoryAudit> orderByComparator) {

		int count = countByC_S(companyId, sku);

		if (count == 0) {
			return null;
		}

		List<CommerceInventoryAudit> list = findByC_S(
			companyId, sku, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce inventory audits before and after the current commerce inventory audit in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param commerceInventoryAuditId the primary key of the current commerce inventory audit
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory audit
	 * @throws NoSuchInventoryAuditException if a commerce inventory audit with the primary key could not be found
	 */
	@Override
	public CommerceInventoryAudit[] findByC_S_PrevAndNext(
			long commerceInventoryAuditId, long companyId, String sku,
			OrderByComparator<CommerceInventoryAudit> orderByComparator)
		throws NoSuchInventoryAuditException {

		sku = Objects.toString(sku, "");

		CommerceInventoryAudit commerceInventoryAudit = findByPrimaryKey(
			commerceInventoryAuditId);

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryAudit[] array = new CommerceInventoryAuditImpl[3];

			array[0] = getByC_S_PrevAndNext(
				session, commerceInventoryAudit, companyId, sku,
				orderByComparator, true);

			array[1] = commerceInventoryAudit;

			array[2] = getByC_S_PrevAndNext(
				session, commerceInventoryAudit, companyId, sku,
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

	protected CommerceInventoryAudit getByC_S_PrevAndNext(
		Session session, CommerceInventoryAudit commerceInventoryAudit,
		long companyId, String sku,
		OrderByComparator<CommerceInventoryAudit> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEINVENTORYAUDIT_WHERE);

		sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		boolean bindSku = false;

		if (sku.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_S_SKU_3);
		}
		else {
			bindSku = true;

			sb.append(_FINDER_COLUMN_C_S_SKU_2);
		}

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
			sb.append(CommerceInventoryAuditModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindSku) {
			queryPos.add(sku);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceInventoryAudit)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceInventoryAudit> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce inventory audits where companyId = &#63; and sku = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 */
	@Override
	public void removeByC_S(long companyId, String sku) {
		for (CommerceInventoryAudit commerceInventoryAudit :
				findByC_S(
					companyId, sku, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceInventoryAudit);
		}
	}

	/**
	 * Returns the number of commerce inventory audits where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @return the number of matching commerce inventory audits
	 */
	@Override
	public int countByC_S(long companyId, String sku) {
		sku = Objects.toString(sku, "");

		FinderPath finderPath = _finderPathCountByC_S;

		Object[] finderArgs = new Object[] {companyId, sku};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEINVENTORYAUDIT_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			boolean bindSku = false;

			if (sku.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_S_SKU_3);
			}
			else {
				bindSku = true;

				sb.append(_FINDER_COLUMN_C_S_SKU_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindSku) {
					queryPos.add(sku);
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

	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 =
		"commerceInventoryAudit.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_S_SKU_2 =
		"commerceInventoryAudit.sku = ?";

	private static final String _FINDER_COLUMN_C_S_SKU_3 =
		"(commerceInventoryAudit.sku IS NULL OR commerceInventoryAudit.sku = '')";

	public CommerceInventoryAuditPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("commerceInventoryAuditId", "CIAuditId");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceInventoryAudit.class);

		setModelImplClass(CommerceInventoryAuditImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceInventoryAuditTable.INSTANCE);
	}

	/**
	 * Caches the commerce inventory audit in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryAudit the commerce inventory audit
	 */
	@Override
	public void cacheResult(CommerceInventoryAudit commerceInventoryAudit) {
		entityCache.putResult(
			CommerceInventoryAuditImpl.class,
			commerceInventoryAudit.getPrimaryKey(), commerceInventoryAudit);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce inventory audits in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryAudits the commerce inventory audits
	 */
	@Override
	public void cacheResult(
		List<CommerceInventoryAudit> commerceInventoryAudits) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceInventoryAudits.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceInventoryAudit commerceInventoryAudit :
				commerceInventoryAudits) {

			if (entityCache.getResult(
					CommerceInventoryAuditImpl.class,
					commerceInventoryAudit.getPrimaryKey()) == null) {

				cacheResult(commerceInventoryAudit);
			}
		}
	}

	/**
	 * Clears the cache for all commerce inventory audits.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceInventoryAuditImpl.class);

		finderCache.clearCache(CommerceInventoryAuditImpl.class);
	}

	/**
	 * Clears the cache for the commerce inventory audit.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceInventoryAudit commerceInventoryAudit) {
		entityCache.removeResult(
			CommerceInventoryAuditImpl.class, commerceInventoryAudit);
	}

	@Override
	public void clearCache(
		List<CommerceInventoryAudit> commerceInventoryAudits) {

		for (CommerceInventoryAudit commerceInventoryAudit :
				commerceInventoryAudits) {

			entityCache.removeResult(
				CommerceInventoryAuditImpl.class, commerceInventoryAudit);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceInventoryAuditImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceInventoryAuditImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new commerce inventory audit with the primary key. Does not add the commerce inventory audit to the database.
	 *
	 * @param commerceInventoryAuditId the primary key for the new commerce inventory audit
	 * @return the new commerce inventory audit
	 */
	@Override
	public CommerceInventoryAudit create(long commerceInventoryAuditId) {
		CommerceInventoryAudit commerceInventoryAudit =
			new CommerceInventoryAuditImpl();

		commerceInventoryAudit.setNew(true);
		commerceInventoryAudit.setPrimaryKey(commerceInventoryAuditId);

		commerceInventoryAudit.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commerceInventoryAudit;
	}

	/**
	 * Removes the commerce inventory audit with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceInventoryAuditId the primary key of the commerce inventory audit
	 * @return the commerce inventory audit that was removed
	 * @throws NoSuchInventoryAuditException if a commerce inventory audit with the primary key could not be found
	 */
	@Override
	public CommerceInventoryAudit remove(long commerceInventoryAuditId)
		throws NoSuchInventoryAuditException {

		return remove((Serializable)commerceInventoryAuditId);
	}

	/**
	 * Removes the commerce inventory audit with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce inventory audit
	 * @return the commerce inventory audit that was removed
	 * @throws NoSuchInventoryAuditException if a commerce inventory audit with the primary key could not be found
	 */
	@Override
	public CommerceInventoryAudit remove(Serializable primaryKey)
		throws NoSuchInventoryAuditException {

		Session session = null;

		try {
			session = openSession();

			CommerceInventoryAudit commerceInventoryAudit =
				(CommerceInventoryAudit)session.get(
					CommerceInventoryAuditImpl.class, primaryKey);

			if (commerceInventoryAudit == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchInventoryAuditException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceInventoryAudit);
		}
		catch (NoSuchInventoryAuditException noSuchEntityException) {
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
	protected CommerceInventoryAudit removeImpl(
		CommerceInventoryAudit commerceInventoryAudit) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceInventoryAudit)) {
				commerceInventoryAudit = (CommerceInventoryAudit)session.get(
					CommerceInventoryAuditImpl.class,
					commerceInventoryAudit.getPrimaryKeyObj());
			}

			if (commerceInventoryAudit != null) {
				session.delete(commerceInventoryAudit);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceInventoryAudit != null) {
			clearCache(commerceInventoryAudit);
		}

		return commerceInventoryAudit;
	}

	@Override
	public CommerceInventoryAudit updateImpl(
		CommerceInventoryAudit commerceInventoryAudit) {

		boolean isNew = commerceInventoryAudit.isNew();

		if (!(commerceInventoryAudit instanceof
				CommerceInventoryAuditModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceInventoryAudit.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceInventoryAudit);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceInventoryAudit proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceInventoryAudit implementation " +
					commerceInventoryAudit.getClass());
		}

		CommerceInventoryAuditModelImpl commerceInventoryAuditModelImpl =
			(CommerceInventoryAuditModelImpl)commerceInventoryAudit;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceInventoryAudit.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceInventoryAudit.setCreateDate(date);
			}
			else {
				commerceInventoryAudit.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceInventoryAuditModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceInventoryAudit.setModifiedDate(date);
			}
			else {
				commerceInventoryAudit.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceInventoryAudit);
			}
			else {
				commerceInventoryAudit = (CommerceInventoryAudit)session.merge(
					commerceInventoryAudit);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceInventoryAuditImpl.class, commerceInventoryAuditModelImpl,
			false, true);

		if (isNew) {
			commerceInventoryAudit.setNew(false);
		}

		commerceInventoryAudit.resetOriginalValues();

		return commerceInventoryAudit;
	}

	/**
	 * Returns the commerce inventory audit with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce inventory audit
	 * @return the commerce inventory audit
	 * @throws NoSuchInventoryAuditException if a commerce inventory audit with the primary key could not be found
	 */
	@Override
	public CommerceInventoryAudit findByPrimaryKey(Serializable primaryKey)
		throws NoSuchInventoryAuditException {

		CommerceInventoryAudit commerceInventoryAudit = fetchByPrimaryKey(
			primaryKey);

		if (commerceInventoryAudit == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchInventoryAuditException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceInventoryAudit;
	}

	/**
	 * Returns the commerce inventory audit with the primary key or throws a <code>NoSuchInventoryAuditException</code> if it could not be found.
	 *
	 * @param commerceInventoryAuditId the primary key of the commerce inventory audit
	 * @return the commerce inventory audit
	 * @throws NoSuchInventoryAuditException if a commerce inventory audit with the primary key could not be found
	 */
	@Override
	public CommerceInventoryAudit findByPrimaryKey(
			long commerceInventoryAuditId)
		throws NoSuchInventoryAuditException {

		return findByPrimaryKey((Serializable)commerceInventoryAuditId);
	}

	/**
	 * Returns the commerce inventory audit with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceInventoryAuditId the primary key of the commerce inventory audit
	 * @return the commerce inventory audit, or <code>null</code> if a commerce inventory audit with the primary key could not be found
	 */
	@Override
	public CommerceInventoryAudit fetchByPrimaryKey(
		long commerceInventoryAuditId) {

		return fetchByPrimaryKey((Serializable)commerceInventoryAuditId);
	}

	/**
	 * Returns all the commerce inventory audits.
	 *
	 * @return the commerce inventory audits
	 */
	@Override
	public List<CommerceInventoryAudit> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce inventory audits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory audits
	 * @param end the upper bound of the range of commerce inventory audits (not inclusive)
	 * @return the range of commerce inventory audits
	 */
	@Override
	public List<CommerceInventoryAudit> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce inventory audits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory audits
	 * @param end the upper bound of the range of commerce inventory audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce inventory audits
	 */
	@Override
	public List<CommerceInventoryAudit> findAll(
		int start, int end,
		OrderByComparator<CommerceInventoryAudit> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce inventory audits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory audits
	 * @param end the upper bound of the range of commerce inventory audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce inventory audits
	 */
	@Override
	public List<CommerceInventoryAudit> findAll(
		int start, int end,
		OrderByComparator<CommerceInventoryAudit> orderByComparator,
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

		List<CommerceInventoryAudit> list = null;

		if (useFinderCache) {
			list = (List<CommerceInventoryAudit>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEINVENTORYAUDIT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEINVENTORYAUDIT;

				sql = sql.concat(CommerceInventoryAuditModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceInventoryAudit>)QueryUtil.list(
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
	 * Removes all the commerce inventory audits from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceInventoryAudit commerceInventoryAudit : findAll()) {
			remove(commerceInventoryAudit);
		}
	}

	/**
	 * Returns the number of commerce inventory audits.
	 *
	 * @return the number of commerce inventory audits
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
					_SQL_COUNT_COMMERCEINVENTORYAUDIT);

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
		return "CIAuditId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEINVENTORYAUDIT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceInventoryAuditModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce inventory audit persistence.
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

		_finderPathWithPaginationFindByLtCreateDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtCreateDate",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"createDate"}, true);

		_finderPathWithPaginationCountByLtCreateDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtCreateDate",
			new String[] {Date.class.getName()}, new String[] {"createDate"},
			false);

		_finderPathWithPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "sku"}, true);

		_finderPathWithoutPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "sku"}, true);

		_finderPathCountByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "sku"}, false);
	}

	public void destroy() {
		entityCache.removeCache(CommerceInventoryAuditImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_COMMERCEINVENTORYAUDIT =
		"SELECT commerceInventoryAudit FROM CommerceInventoryAudit commerceInventoryAudit";

	private static final String _SQL_SELECT_COMMERCEINVENTORYAUDIT_WHERE =
		"SELECT commerceInventoryAudit FROM CommerceInventoryAudit commerceInventoryAudit WHERE ";

	private static final String _SQL_COUNT_COMMERCEINVENTORYAUDIT =
		"SELECT COUNT(commerceInventoryAudit) FROM CommerceInventoryAudit commerceInventoryAudit";

	private static final String _SQL_COUNT_COMMERCEINVENTORYAUDIT_WHERE =
		"SELECT COUNT(commerceInventoryAudit) FROM CommerceInventoryAudit commerceInventoryAudit WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceInventoryAudit.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceInventoryAudit exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceInventoryAudit exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceInventoryAuditPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"commerceInventoryAuditId"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}