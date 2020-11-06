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

package com.liferay.commerce.account.service.persistence.impl;

import com.liferay.commerce.account.exception.NoSuchAccountException;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountTable;
import com.liferay.commerce.account.model.impl.CommerceAccountImpl;
import com.liferay.commerce.account.model.impl.CommerceAccountModelImpl;
import com.liferay.commerce.account.service.persistence.CommerceAccountPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * The persistence implementation for the commerce account service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceAccountPersistenceImpl
	extends BasePersistenceImpl<CommerceAccount>
	implements CommerceAccountPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceAccountUtil</code> to access the commerce account persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceAccountImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the commerce accounts where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce accounts
	 */
	@Override
	public List<CommerceAccount> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce accounts where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @return the range of matching commerce accounts
	 */
	@Override
	public List<CommerceAccount> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce accounts where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce accounts
	 */
	@Override
	public List<CommerceAccount> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceAccount> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce accounts where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce accounts
	 */
	@Override
	public List<CommerceAccount> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceAccount> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<CommerceAccount> list = null;

		if (useFinderCache) {
			list = (List<CommerceAccount>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAccount commerceAccount : list) {
					if (companyId != commerceAccount.getCompanyId()) {
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

			sb.append(_SQL_SELECT_COMMERCEACCOUNT_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceAccountModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<CommerceAccount>)QueryUtil.list(
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
	 * Returns the first commerce account in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account
	 * @throws NoSuchAccountException if a matching commerce account could not be found
	 */
	@Override
	public CommerceAccount findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceAccount> orderByComparator)
		throws NoSuchAccountException {

		CommerceAccount commerceAccount = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (commerceAccount != null) {
			return commerceAccount;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchAccountException(sb.toString());
	}

	/**
	 * Returns the first commerce account in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account, or <code>null</code> if a matching commerce account could not be found
	 */
	@Override
	public CommerceAccount fetchByCompanyId_First(
		long companyId, OrderByComparator<CommerceAccount> orderByComparator) {

		List<CommerceAccount> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce account in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account
	 * @throws NoSuchAccountException if a matching commerce account could not be found
	 */
	@Override
	public CommerceAccount findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommerceAccount> orderByComparator)
		throws NoSuchAccountException {

		CommerceAccount commerceAccount = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (commerceAccount != null) {
			return commerceAccount;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchAccountException(sb.toString());
	}

	/**
	 * Returns the last commerce account in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account, or <code>null</code> if a matching commerce account could not be found
	 */
	@Override
	public CommerceAccount fetchByCompanyId_Last(
		long companyId, OrderByComparator<CommerceAccount> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceAccount> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce accounts before and after the current commerce account in the ordered set where companyId = &#63;.
	 *
	 * @param commerceAccountId the primary key of the current commerce account
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account
	 * @throws NoSuchAccountException if a commerce account with the primary key could not be found
	 */
	@Override
	public CommerceAccount[] findByCompanyId_PrevAndNext(
			long commerceAccountId, long companyId,
			OrderByComparator<CommerceAccount> orderByComparator)
		throws NoSuchAccountException {

		CommerceAccount commerceAccount = findByPrimaryKey(commerceAccountId);

		Session session = null;

		try {
			session = openSession();

			CommerceAccount[] array = new CommerceAccountImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, commerceAccount, companyId, orderByComparator, true);

			array[1] = commerceAccount;

			array[2] = getByCompanyId_PrevAndNext(
				session, commerceAccount, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceAccount getByCompanyId_PrevAndNext(
		Session session, CommerceAccount commerceAccount, long companyId,
		OrderByComparator<CommerceAccount> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEACCOUNT_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			sb.append(CommerceAccountModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceAccount)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceAccount> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce accounts that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce accounts that the user has permission to view
	 */
	@Override
	public List<CommerceAccount> filterFindByCompanyId(long companyId) {
		return filterFindByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce accounts that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @return the range of matching commerce accounts that the user has permission to view
	 */
	@Override
	public List<CommerceAccount> filterFindByCompanyId(
		long companyId, int start, int end) {

		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce accounts that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce accounts that the user has permission to view
	 */
	@Override
	public List<CommerceAccount> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceAccount> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId(companyId, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEACCOUNT_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEACCOUNT_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEACCOUNT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CommerceAccountModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceAccountModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceAccount.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceAccountImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceAccountImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			return (List<CommerceAccount>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the commerce accounts before and after the current commerce account in the ordered set of commerce accounts that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceAccountId the primary key of the current commerce account
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account
	 * @throws NoSuchAccountException if a commerce account with the primary key could not be found
	 */
	@Override
	public CommerceAccount[] filterFindByCompanyId_PrevAndNext(
			long commerceAccountId, long companyId,
			OrderByComparator<CommerceAccount> orderByComparator)
		throws NoSuchAccountException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId_PrevAndNext(
				commerceAccountId, companyId, orderByComparator);
		}

		CommerceAccount commerceAccount = findByPrimaryKey(commerceAccountId);

		Session session = null;

		try {
			session = openSession();

			CommerceAccount[] array = new CommerceAccountImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(
				session, commerceAccount, companyId, orderByComparator, true);

			array[1] = commerceAccount;

			array[2] = filterGetByCompanyId_PrevAndNext(
				session, commerceAccount, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceAccount filterGetByCompanyId_PrevAndNext(
		Session session, CommerceAccount commerceAccount, long companyId,
		OrderByComparator<CommerceAccount> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEACCOUNT_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEACCOUNT_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEACCOUNT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CommerceAccountModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceAccountModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceAccount.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, CommerceAccountImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, CommerceAccountImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceAccount)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceAccount> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce accounts where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceAccount commerceAccount :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceAccount);
		}
	}

	/**
	 * Returns the number of commerce accounts where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce accounts
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEACCOUNT_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

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

	/**
	 * Returns the number of commerce accounts that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce accounts that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByCompanyId(companyId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_COMMERCEACCOUNT_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceAccount.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"commerceAccount.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByU_T;
	private FinderPath _finderPathWithoutPaginationFindByU_T;
	private FinderPath _finderPathCountByU_T;

	/**
	 * Returns all the commerce accounts where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching commerce accounts
	 */
	@Override
	public List<CommerceAccount> findByU_T(long userId, int type) {
		return findByU_T(
			userId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce accounts where userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @return the range of matching commerce accounts
	 */
	@Override
	public List<CommerceAccount> findByU_T(
		long userId, int type, int start, int end) {

		return findByU_T(userId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce accounts where userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce accounts
	 */
	@Override
	public List<CommerceAccount> findByU_T(
		long userId, int type, int start, int end,
		OrderByComparator<CommerceAccount> orderByComparator) {

		return findByU_T(userId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce accounts where userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce accounts
	 */
	@Override
	public List<CommerceAccount> findByU_T(
		long userId, int type, int start, int end,
		OrderByComparator<CommerceAccount> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_T;
				finderArgs = new Object[] {userId, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_T;
			finderArgs = new Object[] {
				userId, type, start, end, orderByComparator
			};
		}

		List<CommerceAccount> list = null;

		if (useFinderCache) {
			list = (List<CommerceAccount>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAccount commerceAccount : list) {
					if ((userId != commerceAccount.getUserId()) ||
						(type != commerceAccount.getType())) {

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

			sb.append(_SQL_SELECT_COMMERCEACCOUNT_WHERE);

			sb.append(_FINDER_COLUMN_U_T_USERID_2);

			sb.append(_FINDER_COLUMN_U_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceAccountModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(type);

				list = (List<CommerceAccount>)QueryUtil.list(
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
	 * Returns the first commerce account in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account
	 * @throws NoSuchAccountException if a matching commerce account could not be found
	 */
	@Override
	public CommerceAccount findByU_T_First(
			long userId, int type,
			OrderByComparator<CommerceAccount> orderByComparator)
		throws NoSuchAccountException {

		CommerceAccount commerceAccount = fetchByU_T_First(
			userId, type, orderByComparator);

		if (commerceAccount != null) {
			return commerceAccount;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchAccountException(sb.toString());
	}

	/**
	 * Returns the first commerce account in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account, or <code>null</code> if a matching commerce account could not be found
	 */
	@Override
	public CommerceAccount fetchByU_T_First(
		long userId, int type,
		OrderByComparator<CommerceAccount> orderByComparator) {

		List<CommerceAccount> list = findByU_T(
			userId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce account in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account
	 * @throws NoSuchAccountException if a matching commerce account could not be found
	 */
	@Override
	public CommerceAccount findByU_T_Last(
			long userId, int type,
			OrderByComparator<CommerceAccount> orderByComparator)
		throws NoSuchAccountException {

		CommerceAccount commerceAccount = fetchByU_T_Last(
			userId, type, orderByComparator);

		if (commerceAccount != null) {
			return commerceAccount;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchAccountException(sb.toString());
	}

	/**
	 * Returns the last commerce account in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account, or <code>null</code> if a matching commerce account could not be found
	 */
	@Override
	public CommerceAccount fetchByU_T_Last(
		long userId, int type,
		OrderByComparator<CommerceAccount> orderByComparator) {

		int count = countByU_T(userId, type);

		if (count == 0) {
			return null;
		}

		List<CommerceAccount> list = findByU_T(
			userId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce accounts before and after the current commerce account in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param commerceAccountId the primary key of the current commerce account
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account
	 * @throws NoSuchAccountException if a commerce account with the primary key could not be found
	 */
	@Override
	public CommerceAccount[] findByU_T_PrevAndNext(
			long commerceAccountId, long userId, int type,
			OrderByComparator<CommerceAccount> orderByComparator)
		throws NoSuchAccountException {

		CommerceAccount commerceAccount = findByPrimaryKey(commerceAccountId);

		Session session = null;

		try {
			session = openSession();

			CommerceAccount[] array = new CommerceAccountImpl[3];

			array[0] = getByU_T_PrevAndNext(
				session, commerceAccount, userId, type, orderByComparator,
				true);

			array[1] = commerceAccount;

			array[2] = getByU_T_PrevAndNext(
				session, commerceAccount, userId, type, orderByComparator,
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

	protected CommerceAccount getByU_T_PrevAndNext(
		Session session, CommerceAccount commerceAccount, long userId, int type,
		OrderByComparator<CommerceAccount> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEACCOUNT_WHERE);

		sb.append(_FINDER_COLUMN_U_T_USERID_2);

		sb.append(_FINDER_COLUMN_U_T_TYPE_2);

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
			sb.append(CommerceAccountModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceAccount)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceAccount> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce accounts that the user has permission to view where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching commerce accounts that the user has permission to view
	 */
	@Override
	public List<CommerceAccount> filterFindByU_T(long userId, int type) {
		return filterFindByU_T(
			userId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce accounts that the user has permission to view where userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @return the range of matching commerce accounts that the user has permission to view
	 */
	@Override
	public List<CommerceAccount> filterFindByU_T(
		long userId, int type, int start, int end) {

		return filterFindByU_T(userId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce accounts that the user has permissions to view where userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce accounts that the user has permission to view
	 */
	@Override
	public List<CommerceAccount> filterFindByU_T(
		long userId, int type, int start, int end,
		OrderByComparator<CommerceAccount> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByU_T(userId, type, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEACCOUNT_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEACCOUNT_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_U_T_USERID_2);

		sb.append(_FINDER_COLUMN_U_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEACCOUNT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CommerceAccountModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceAccountModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceAccount.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceAccountImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceAccountImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(userId);

			queryPos.add(type);

			return (List<CommerceAccount>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the commerce accounts before and after the current commerce account in the ordered set of commerce accounts that the user has permission to view where userId = &#63; and type = &#63;.
	 *
	 * @param commerceAccountId the primary key of the current commerce account
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account
	 * @throws NoSuchAccountException if a commerce account with the primary key could not be found
	 */
	@Override
	public CommerceAccount[] filterFindByU_T_PrevAndNext(
			long commerceAccountId, long userId, int type,
			OrderByComparator<CommerceAccount> orderByComparator)
		throws NoSuchAccountException {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByU_T_PrevAndNext(
				commerceAccountId, userId, type, orderByComparator);
		}

		CommerceAccount commerceAccount = findByPrimaryKey(commerceAccountId);

		Session session = null;

		try {
			session = openSession();

			CommerceAccount[] array = new CommerceAccountImpl[3];

			array[0] = filterGetByU_T_PrevAndNext(
				session, commerceAccount, userId, type, orderByComparator,
				true);

			array[1] = commerceAccount;

			array[2] = filterGetByU_T_PrevAndNext(
				session, commerceAccount, userId, type, orderByComparator,
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

	protected CommerceAccount filterGetByU_T_PrevAndNext(
		Session session, CommerceAccount commerceAccount, long userId, int type,
		OrderByComparator<CommerceAccount> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEACCOUNT_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEACCOUNT_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_U_T_USERID_2);

		sb.append(_FINDER_COLUMN_U_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEACCOUNT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(CommerceAccountModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceAccountModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceAccount.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, CommerceAccountImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, CommerceAccountImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(userId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceAccount)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceAccount> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce accounts where userId = &#63; and type = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param type the type
	 */
	@Override
	public void removeByU_T(long userId, int type) {
		for (CommerceAccount commerceAccount :
				findByU_T(
					userId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceAccount);
		}
	}

	/**
	 * Returns the number of commerce accounts where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the number of matching commerce accounts
	 */
	@Override
	public int countByU_T(long userId, int type) {
		FinderPath finderPath = _finderPathCountByU_T;

		Object[] finderArgs = new Object[] {userId, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEACCOUNT_WHERE);

			sb.append(_FINDER_COLUMN_U_T_USERID_2);

			sb.append(_FINDER_COLUMN_U_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(type);

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

	/**
	 * Returns the number of commerce accounts that the user has permission to view where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the number of matching commerce accounts that the user has permission to view
	 */
	@Override
	public int filterCountByU_T(long userId, int type) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByU_T(userId, type);
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_COMMERCEACCOUNT_WHERE);

		sb.append(_FINDER_COLUMN_U_T_USERID_2);

		sb.append(_FINDER_COLUMN_U_T_TYPE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceAccount.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(userId);

			queryPos.add(type);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_U_T_USERID_2 =
		"commerceAccount.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_T_TYPE_2 =
		"commerceAccount.type = ?";

	private static final String _FINDER_COLUMN_U_T_TYPE_2_SQL =
		"commerceAccount.type_ = ?";

	private FinderPath _finderPathFetchByC_ERC;
	private FinderPath _finderPathCountByC_ERC;

	/**
	 * Returns the commerce account where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchAccountException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce account
	 * @throws NoSuchAccountException if a matching commerce account could not be found
	 */
	@Override
	public CommerceAccount findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchAccountException {

		CommerceAccount commerceAccount = fetchByC_ERC(
			companyId, externalReferenceCode);

		if (commerceAccount == null) {
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

			throw new NoSuchAccountException(sb.toString());
		}

		return commerceAccount;
	}

	/**
	 * Returns the commerce account where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce account, or <code>null</code> if a matching commerce account could not be found
	 */
	@Override
	public CommerceAccount fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return fetchByC_ERC(companyId, externalReferenceCode, true);
	}

	/**
	 * Returns the commerce account where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce account, or <code>null</code> if a matching commerce account could not be found
	 */
	@Override
	public CommerceAccount fetchByC_ERC(
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

		if (result instanceof CommerceAccount) {
			CommerceAccount commerceAccount = (CommerceAccount)result;

			if ((companyId != commerceAccount.getCompanyId()) ||
				!Objects.equals(
					externalReferenceCode,
					commerceAccount.getExternalReferenceCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCEACCOUNT_WHERE);

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

				List<CommerceAccount> list = query.list();

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
								"CommerceAccountPersistenceImpl.fetchByC_ERC(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CommerceAccount commerceAccount = list.get(0);

					result = commerceAccount;

					cacheResult(commerceAccount);
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
			return (CommerceAccount)result;
		}
	}

	/**
	 * Removes the commerce account where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce account that was removed
	 */
	@Override
	public CommerceAccount removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchAccountException {

		CommerceAccount commerceAccount = findByC_ERC(
			companyId, externalReferenceCode);

		return remove(commerceAccount);
	}

	/**
	 * Returns the number of commerce accounts where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce accounts
	 */
	@Override
	public int countByC_ERC(long companyId, String externalReferenceCode) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		FinderPath finderPath = _finderPathCountByC_ERC;

		Object[] finderArgs = new Object[] {companyId, externalReferenceCode};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEACCOUNT_WHERE);

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
		"commerceAccount.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2 =
		"commerceAccount.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3 =
		"(commerceAccount.externalReferenceCode IS NULL OR commerceAccount.externalReferenceCode = '')";

	public CommerceAccountPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");
		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceAccount.class);

		setModelImplClass(CommerceAccountImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceAccountTable.INSTANCE);
	}

	/**
	 * Caches the commerce account in the entity cache if it is enabled.
	 *
	 * @param commerceAccount the commerce account
	 */
	@Override
	public void cacheResult(CommerceAccount commerceAccount) {
		entityCache.putResult(
			CommerceAccountImpl.class, commerceAccount.getPrimaryKey(),
			commerceAccount);

		finderCache.putResult(
			_finderPathFetchByC_ERC,
			new Object[] {
				commerceAccount.getCompanyId(),
				commerceAccount.getExternalReferenceCode()
			},
			commerceAccount);
	}

	/**
	 * Caches the commerce accounts in the entity cache if it is enabled.
	 *
	 * @param commerceAccounts the commerce accounts
	 */
	@Override
	public void cacheResult(List<CommerceAccount> commerceAccounts) {
		for (CommerceAccount commerceAccount : commerceAccounts) {
			if (entityCache.getResult(
					CommerceAccountImpl.class,
					commerceAccount.getPrimaryKey()) == null) {

				cacheResult(commerceAccount);
			}
		}
	}

	/**
	 * Clears the cache for all commerce accounts.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceAccountImpl.class);

		finderCache.clearCache(CommerceAccountImpl.class);
	}

	/**
	 * Clears the cache for the commerce account.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceAccount commerceAccount) {
		entityCache.removeResult(CommerceAccountImpl.class, commerceAccount);
	}

	@Override
	public void clearCache(List<CommerceAccount> commerceAccounts) {
		for (CommerceAccount commerceAccount : commerceAccounts) {
			entityCache.removeResult(
				CommerceAccountImpl.class, commerceAccount);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceAccountImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CommerceAccountImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceAccountModelImpl commerceAccountModelImpl) {

		Object[] args = new Object[] {
			commerceAccountModelImpl.getCompanyId(),
			commerceAccountModelImpl.getExternalReferenceCode()
		};

		finderCache.putResult(_finderPathCountByC_ERC, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_ERC, args, commerceAccountModelImpl);
	}

	/**
	 * Creates a new commerce account with the primary key. Does not add the commerce account to the database.
	 *
	 * @param commerceAccountId the primary key for the new commerce account
	 * @return the new commerce account
	 */
	@Override
	public CommerceAccount create(long commerceAccountId) {
		CommerceAccount commerceAccount = new CommerceAccountImpl();

		commerceAccount.setNew(true);
		commerceAccount.setPrimaryKey(commerceAccountId);

		commerceAccount.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commerceAccount;
	}

	/**
	 * Removes the commerce account with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAccountId the primary key of the commerce account
	 * @return the commerce account that was removed
	 * @throws NoSuchAccountException if a commerce account with the primary key could not be found
	 */
	@Override
	public CommerceAccount remove(long commerceAccountId)
		throws NoSuchAccountException {

		return remove((Serializable)commerceAccountId);
	}

	/**
	 * Removes the commerce account with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce account
	 * @return the commerce account that was removed
	 * @throws NoSuchAccountException if a commerce account with the primary key could not be found
	 */
	@Override
	public CommerceAccount remove(Serializable primaryKey)
		throws NoSuchAccountException {

		Session session = null;

		try {
			session = openSession();

			CommerceAccount commerceAccount = (CommerceAccount)session.get(
				CommerceAccountImpl.class, primaryKey);

			if (commerceAccount == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAccountException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceAccount);
		}
		catch (NoSuchAccountException noSuchEntityException) {
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
	protected CommerceAccount removeImpl(CommerceAccount commerceAccount) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceAccount)) {
				commerceAccount = (CommerceAccount)session.get(
					CommerceAccountImpl.class,
					commerceAccount.getPrimaryKeyObj());
			}

			if (commerceAccount != null) {
				session.delete(commerceAccount);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceAccount != null) {
			clearCache(commerceAccount);
		}

		return commerceAccount;
	}

	@Override
	public CommerceAccount updateImpl(CommerceAccount commerceAccount) {
		boolean isNew = commerceAccount.isNew();

		if (!(commerceAccount instanceof CommerceAccountModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceAccount.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceAccount);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceAccount proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceAccount implementation " +
					commerceAccount.getClass());
		}

		CommerceAccountModelImpl commerceAccountModelImpl =
			(CommerceAccountModelImpl)commerceAccount;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceAccount.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceAccount.setCreateDate(now);
			}
			else {
				commerceAccount.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!commerceAccountModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceAccount.setModifiedDate(now);
			}
			else {
				commerceAccount.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceAccount);
			}
			else {
				commerceAccount = (CommerceAccount)session.merge(
					commerceAccount);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceAccountImpl.class, commerceAccountModelImpl, false, true);

		cacheUniqueFindersCache(commerceAccountModelImpl);

		if (isNew) {
			commerceAccount.setNew(false);
		}

		commerceAccount.resetOriginalValues();

		return commerceAccount;
	}

	/**
	 * Returns the commerce account with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce account
	 * @return the commerce account
	 * @throws NoSuchAccountException if a commerce account with the primary key could not be found
	 */
	@Override
	public CommerceAccount findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAccountException {

		CommerceAccount commerceAccount = fetchByPrimaryKey(primaryKey);

		if (commerceAccount == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAccountException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceAccount;
	}

	/**
	 * Returns the commerce account with the primary key or throws a <code>NoSuchAccountException</code> if it could not be found.
	 *
	 * @param commerceAccountId the primary key of the commerce account
	 * @return the commerce account
	 * @throws NoSuchAccountException if a commerce account with the primary key could not be found
	 */
	@Override
	public CommerceAccount findByPrimaryKey(long commerceAccountId)
		throws NoSuchAccountException {

		return findByPrimaryKey((Serializable)commerceAccountId);
	}

	/**
	 * Returns the commerce account with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceAccountId the primary key of the commerce account
	 * @return the commerce account, or <code>null</code> if a commerce account with the primary key could not be found
	 */
	@Override
	public CommerceAccount fetchByPrimaryKey(long commerceAccountId) {
		return fetchByPrimaryKey((Serializable)commerceAccountId);
	}

	/**
	 * Returns all the commerce accounts.
	 *
	 * @return the commerce accounts
	 */
	@Override
	public List<CommerceAccount> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce accounts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @return the range of commerce accounts
	 */
	@Override
	public List<CommerceAccount> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce accounts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce accounts
	 */
	@Override
	public List<CommerceAccount> findAll(
		int start, int end,
		OrderByComparator<CommerceAccount> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce accounts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce accounts
	 */
	@Override
	public List<CommerceAccount> findAll(
		int start, int end,
		OrderByComparator<CommerceAccount> orderByComparator,
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

		List<CommerceAccount> list = null;

		if (useFinderCache) {
			list = (List<CommerceAccount>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEACCOUNT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEACCOUNT;

				sql = sql.concat(CommerceAccountModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceAccount>)QueryUtil.list(
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
	 * Removes all the commerce accounts from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceAccount commerceAccount : findAll()) {
			remove(commerceAccount);
		}
	}

	/**
	 * Returns the number of commerce accounts.
	 *
	 * @return the number of commerce accounts
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_COMMERCEACCOUNT);

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
		return "commerceAccountId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEACCOUNT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceAccountModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce account persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceAccountPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new CommerceAccountModelArgumentsResolver(),
			new HashMapDictionary<>());

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_finderPathWithPaginationFindByU_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"userId", "type_"}, true);

		_finderPathWithoutPaginationFindByU_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"userId", "type_"}, true);

		_finderPathCountByU_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"userId", "type_"}, false);

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
		entityCache.removeCache(CommerceAccountImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEACCOUNT =
		"SELECT commerceAccount FROM CommerceAccount commerceAccount";

	private static final String _SQL_SELECT_COMMERCEACCOUNT_WHERE =
		"SELECT commerceAccount FROM CommerceAccount commerceAccount WHERE ";

	private static final String _SQL_COUNT_COMMERCEACCOUNT =
		"SELECT COUNT(commerceAccount) FROM CommerceAccount commerceAccount";

	private static final String _SQL_COUNT_COMMERCEACCOUNT_WHERE =
		"SELECT COUNT(commerceAccount) FROM CommerceAccount commerceAccount WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"commerceAccount.commerceAccountId";

	private static final String _FILTER_SQL_SELECT_COMMERCEACCOUNT_WHERE =
		"SELECT DISTINCT {commerceAccount.*} FROM CommerceAccount commerceAccount WHERE ";

	private static final String
		_FILTER_SQL_SELECT_COMMERCEACCOUNT_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {CommerceAccount.*} FROM (SELECT DISTINCT commerceAccount.commerceAccountId FROM CommerceAccount commerceAccount WHERE ";

	private static final String
		_FILTER_SQL_SELECT_COMMERCEACCOUNT_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN CommerceAccount ON TEMP_TABLE.commerceAccountId = CommerceAccount.commerceAccountId";

	private static final String _FILTER_SQL_COUNT_COMMERCEACCOUNT_WHERE =
		"SELECT COUNT(DISTINCT commerceAccount.commerceAccountId) AS COUNT_VALUE FROM CommerceAccount commerceAccount WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "commerceAccount";

	private static final String _FILTER_ENTITY_TABLE = "CommerceAccount";

	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceAccount.";

	private static final String _ORDER_BY_ENTITY_TABLE = "CommerceAccount.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceAccount exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceAccount exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAccountPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type", "active"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class CommerceAccountModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			CommerceAccountModelImpl commerceAccountModelImpl =
				(CommerceAccountModelImpl)baseModel;

			long columnBitmask = commerceAccountModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					commerceAccountModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						commerceAccountModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					commerceAccountModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return CommerceAccountImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return CommerceAccountTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			CommerceAccountModelImpl commerceAccountModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						commerceAccountModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = commerceAccountModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}