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

package com.liferay.account.service.persistence.impl;

import com.liferay.account.exception.NoSuchEntryOrganizationRelException;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.model.impl.AccountEntryOrganizationRelImpl;
import com.liferay.account.model.impl.AccountEntryOrganizationRelModelImpl;
import com.liferay.account.service.persistence.AccountEntryOrganizationRelPersistence;
import com.liferay.account.service.persistence.impl.constants.AccountPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the account entry organization rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AccountEntryOrganizationRelPersistence.class)
public class AccountEntryOrganizationRelPersistenceImpl
	extends BasePersistenceImpl<AccountEntryOrganizationRel>
	implements AccountEntryOrganizationRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AccountEntryOrganizationRelUtil</code> to access the account entry organization rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AccountEntryOrganizationRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByAccountEntryId;
	private FinderPath _finderPathWithoutPaginationFindByAccountEntryId;
	private FinderPath _finderPathCountByAccountEntryId;

	/**
	 * Returns all the account entry organization rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the matching account entry organization rels
	 */
	@Override
	public List<AccountEntryOrganizationRel> findByAccountEntryId(
		long accountEntryId) {

		return findByAccountEntryId(
			accountEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account entry organization rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account entry organization rels
	 * @param end the upper bound of the range of account entry organization rels (not inclusive)
	 * @return the range of matching account entry organization rels
	 */
	@Override
	public List<AccountEntryOrganizationRel> findByAccountEntryId(
		long accountEntryId, int start, int end) {

		return findByAccountEntryId(accountEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the account entry organization rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account entry organization rels
	 * @param end the upper bound of the range of account entry organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account entry organization rels
	 */
	@Override
	public List<AccountEntryOrganizationRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<AccountEntryOrganizationRel> orderByComparator) {

		return findByAccountEntryId(
			accountEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account entry organization rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account entry organization rels
	 * @param end the upper bound of the range of account entry organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account entry organization rels
	 */
	@Override
	public List<AccountEntryOrganizationRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<AccountEntryOrganizationRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByAccountEntryId;
				finderArgs = new Object[] {accountEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByAccountEntryId;
			finderArgs = new Object[] {
				accountEntryId, start, end, orderByComparator
			};
		}

		List<AccountEntryOrganizationRel> list = null;

		if (useFinderCache) {
			list = (List<AccountEntryOrganizationRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AccountEntryOrganizationRel accountEntryOrganizationRel :
						list) {

					if (accountEntryId !=
							accountEntryOrganizationRel.getAccountEntryId()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ACCOUNTENTRYORGANIZATIONREL_WHERE);

			query.append(_FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(
					AccountEntryOrganizationRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(accountEntryId);

				list = (List<AccountEntryOrganizationRel>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first account entry organization rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry organization rel
	 * @throws NoSuchEntryOrganizationRelException if a matching account entry organization rel could not be found
	 */
	@Override
	public AccountEntryOrganizationRel findByAccountEntryId_First(
			long accountEntryId,
			OrderByComparator<AccountEntryOrganizationRel> orderByComparator)
		throws NoSuchEntryOrganizationRelException {

		AccountEntryOrganizationRel accountEntryOrganizationRel =
			fetchByAccountEntryId_First(accountEntryId, orderByComparator);

		if (accountEntryOrganizationRel != null) {
			return accountEntryOrganizationRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("accountEntryId=");
		msg.append(accountEntryId);

		msg.append("}");

		throw new NoSuchEntryOrganizationRelException(msg.toString());
	}

	/**
	 * Returns the first account entry organization rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry organization rel, or <code>null</code> if a matching account entry organization rel could not be found
	 */
	@Override
	public AccountEntryOrganizationRel fetchByAccountEntryId_First(
		long accountEntryId,
		OrderByComparator<AccountEntryOrganizationRel> orderByComparator) {

		List<AccountEntryOrganizationRel> list = findByAccountEntryId(
			accountEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last account entry organization rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry organization rel
	 * @throws NoSuchEntryOrganizationRelException if a matching account entry organization rel could not be found
	 */
	@Override
	public AccountEntryOrganizationRel findByAccountEntryId_Last(
			long accountEntryId,
			OrderByComparator<AccountEntryOrganizationRel> orderByComparator)
		throws NoSuchEntryOrganizationRelException {

		AccountEntryOrganizationRel accountEntryOrganizationRel =
			fetchByAccountEntryId_Last(accountEntryId, orderByComparator);

		if (accountEntryOrganizationRel != null) {
			return accountEntryOrganizationRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("accountEntryId=");
		msg.append(accountEntryId);

		msg.append("}");

		throw new NoSuchEntryOrganizationRelException(msg.toString());
	}

	/**
	 * Returns the last account entry organization rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry organization rel, or <code>null</code> if a matching account entry organization rel could not be found
	 */
	@Override
	public AccountEntryOrganizationRel fetchByAccountEntryId_Last(
		long accountEntryId,
		OrderByComparator<AccountEntryOrganizationRel> orderByComparator) {

		int count = countByAccountEntryId(accountEntryId);

		if (count == 0) {
			return null;
		}

		List<AccountEntryOrganizationRel> list = findByAccountEntryId(
			accountEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the account entry organization rels before and after the current account entry organization rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryOrganizationRelId the primary key of the current account entry organization rel
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account entry organization rel
	 * @throws NoSuchEntryOrganizationRelException if a account entry organization rel with the primary key could not be found
	 */
	@Override
	public AccountEntryOrganizationRel[] findByAccountEntryId_PrevAndNext(
			long accountEntryOrganizationRelId, long accountEntryId,
			OrderByComparator<AccountEntryOrganizationRel> orderByComparator)
		throws NoSuchEntryOrganizationRelException {

		AccountEntryOrganizationRel accountEntryOrganizationRel =
			findByPrimaryKey(accountEntryOrganizationRelId);

		Session session = null;

		try {
			session = openSession();

			AccountEntryOrganizationRel[] array =
				new AccountEntryOrganizationRelImpl[3];

			array[0] = getByAccountEntryId_PrevAndNext(
				session, accountEntryOrganizationRel, accountEntryId,
				orderByComparator, true);

			array[1] = accountEntryOrganizationRel;

			array[2] = getByAccountEntryId_PrevAndNext(
				session, accountEntryOrganizationRel, accountEntryId,
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

	protected AccountEntryOrganizationRel getByAccountEntryId_PrevAndNext(
		Session session,
		AccountEntryOrganizationRel accountEntryOrganizationRel,
		long accountEntryId,
		OrderByComparator<AccountEntryOrganizationRel> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ACCOUNTENTRYORGANIZATIONREL_WHERE);

		query.append(_FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(AccountEntryOrganizationRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(accountEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						accountEntryOrganizationRel)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AccountEntryOrganizationRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the account entry organization rels where accountEntryId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 */
	@Override
	public void removeByAccountEntryId(long accountEntryId) {
		for (AccountEntryOrganizationRel accountEntryOrganizationRel :
				findByAccountEntryId(
					accountEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(accountEntryOrganizationRel);
		}
	}

	/**
	 * Returns the number of account entry organization rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the number of matching account entry organization rels
	 */
	@Override
	public int countByAccountEntryId(long accountEntryId) {
		FinderPath finderPath = _finderPathCountByAccountEntryId;

		Object[] finderArgs = new Object[] {accountEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ACCOUNTENTRYORGANIZATIONREL_WHERE);

			query.append(_FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(accountEntryId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2 =
		"accountEntryOrganizationRel.accountEntryId = ?";

	private FinderPath _finderPathFetchByA_O;
	private FinderPath _finderPathCountByA_O;

	/**
	 * Returns the account entry organization rel where accountEntryId = &#63; and organizationId = &#63; or throws a <code>NoSuchEntryOrganizationRelException</code> if it could not be found.
	 *
	 * @param accountEntryId the account entry ID
	 * @param organizationId the organization ID
	 * @return the matching account entry organization rel
	 * @throws NoSuchEntryOrganizationRelException if a matching account entry organization rel could not be found
	 */
	@Override
	public AccountEntryOrganizationRel findByA_O(
			long accountEntryId, long organizationId)
		throws NoSuchEntryOrganizationRelException {

		AccountEntryOrganizationRel accountEntryOrganizationRel = fetchByA_O(
			accountEntryId, organizationId);

		if (accountEntryOrganizationRel == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("accountEntryId=");
			msg.append(accountEntryId);

			msg.append(", organizationId=");
			msg.append(organizationId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchEntryOrganizationRelException(msg.toString());
		}

		return accountEntryOrganizationRel;
	}

	/**
	 * Returns the account entry organization rel where accountEntryId = &#63; and organizationId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param organizationId the organization ID
	 * @return the matching account entry organization rel, or <code>null</code> if a matching account entry organization rel could not be found
	 */
	@Override
	public AccountEntryOrganizationRel fetchByA_O(
		long accountEntryId, long organizationId) {

		return fetchByA_O(accountEntryId, organizationId, true);
	}

	/**
	 * Returns the account entry organization rel where accountEntryId = &#63; and organizationId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param organizationId the organization ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching account entry organization rel, or <code>null</code> if a matching account entry organization rel could not be found
	 */
	@Override
	public AccountEntryOrganizationRel fetchByA_O(
		long accountEntryId, long organizationId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {accountEntryId, organizationId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByA_O, finderArgs, this);
		}

		if (result instanceof AccountEntryOrganizationRel) {
			AccountEntryOrganizationRel accountEntryOrganizationRel =
				(AccountEntryOrganizationRel)result;

			if ((accountEntryId !=
					accountEntryOrganizationRel.getAccountEntryId()) ||
				(organizationId !=
					accountEntryOrganizationRel.getOrganizationId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ACCOUNTENTRYORGANIZATIONREL_WHERE);

			query.append(_FINDER_COLUMN_A_O_ACCOUNTENTRYID_2);

			query.append(_FINDER_COLUMN_A_O_ORGANIZATIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(accountEntryId);

				qPos.add(organizationId);

				List<AccountEntryOrganizationRel> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByA_O, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									accountEntryId, organizationId
								};
							}

							_log.warn(
								"AccountEntryOrganizationRelPersistenceImpl.fetchByA_O(long, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					AccountEntryOrganizationRel accountEntryOrganizationRel =
						list.get(0);

					result = accountEntryOrganizationRel;

					cacheResult(accountEntryOrganizationRel);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByA_O, finderArgs);
				}

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
			return (AccountEntryOrganizationRel)result;
		}
	}

	/**
	 * Removes the account entry organization rel where accountEntryId = &#63; and organizationId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 * @param organizationId the organization ID
	 * @return the account entry organization rel that was removed
	 */
	@Override
	public AccountEntryOrganizationRel removeByA_O(
			long accountEntryId, long organizationId)
		throws NoSuchEntryOrganizationRelException {

		AccountEntryOrganizationRel accountEntryOrganizationRel = findByA_O(
			accountEntryId, organizationId);

		return remove(accountEntryOrganizationRel);
	}

	/**
	 * Returns the number of account entry organization rels where accountEntryId = &#63; and organizationId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param organizationId the organization ID
	 * @return the number of matching account entry organization rels
	 */
	@Override
	public int countByA_O(long accountEntryId, long organizationId) {
		FinderPath finderPath = _finderPathCountByA_O;

		Object[] finderArgs = new Object[] {accountEntryId, organizationId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ACCOUNTENTRYORGANIZATIONREL_WHERE);

			query.append(_FINDER_COLUMN_A_O_ACCOUNTENTRYID_2);

			query.append(_FINDER_COLUMN_A_O_ORGANIZATIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(accountEntryId);

				qPos.add(organizationId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_A_O_ACCOUNTENTRYID_2 =
		"accountEntryOrganizationRel.accountEntryId = ? AND ";

	private static final String _FINDER_COLUMN_A_O_ORGANIZATIONID_2 =
		"accountEntryOrganizationRel.organizationId = ?";

	public AccountEntryOrganizationRelPersistenceImpl() {
		setModelClass(AccountEntryOrganizationRel.class);

		setModelImplClass(AccountEntryOrganizationRelImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the account entry organization rel in the entity cache if it is enabled.
	 *
	 * @param accountEntryOrganizationRel the account entry organization rel
	 */
	@Override
	public void cacheResult(
		AccountEntryOrganizationRel accountEntryOrganizationRel) {

		entityCache.putResult(
			entityCacheEnabled, AccountEntryOrganizationRelImpl.class,
			accountEntryOrganizationRel.getPrimaryKey(),
			accountEntryOrganizationRel);

		finderCache.putResult(
			_finderPathFetchByA_O,
			new Object[] {
				accountEntryOrganizationRel.getAccountEntryId(),
				accountEntryOrganizationRel.getOrganizationId()
			},
			accountEntryOrganizationRel);

		accountEntryOrganizationRel.resetOriginalValues();
	}

	/**
	 * Caches the account entry organization rels in the entity cache if it is enabled.
	 *
	 * @param accountEntryOrganizationRels the account entry organization rels
	 */
	@Override
	public void cacheResult(
		List<AccountEntryOrganizationRel> accountEntryOrganizationRels) {

		for (AccountEntryOrganizationRel accountEntryOrganizationRel :
				accountEntryOrganizationRels) {

			if (entityCache.getResult(
					entityCacheEnabled, AccountEntryOrganizationRelImpl.class,
					accountEntryOrganizationRel.getPrimaryKey()) == null) {

				cacheResult(accountEntryOrganizationRel);
			}
			else {
				accountEntryOrganizationRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all account entry organization rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AccountEntryOrganizationRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the account entry organization rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		AccountEntryOrganizationRel accountEntryOrganizationRel) {

		entityCache.removeResult(
			entityCacheEnabled, AccountEntryOrganizationRelImpl.class,
			accountEntryOrganizationRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(AccountEntryOrganizationRelModelImpl)accountEntryOrganizationRel,
			true);
	}

	@Override
	public void clearCache(
		List<AccountEntryOrganizationRel> accountEntryOrganizationRels) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AccountEntryOrganizationRel accountEntryOrganizationRel :
				accountEntryOrganizationRels) {

			entityCache.removeResult(
				entityCacheEnabled, AccountEntryOrganizationRelImpl.class,
				accountEntryOrganizationRel.getPrimaryKey());

			clearUniqueFindersCache(
				(AccountEntryOrganizationRelModelImpl)
					accountEntryOrganizationRel,
				true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, AccountEntryOrganizationRelImpl.class,
				primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AccountEntryOrganizationRelModelImpl
			accountEntryOrganizationRelModelImpl) {

		Object[] args = new Object[] {
			accountEntryOrganizationRelModelImpl.getAccountEntryId(),
			accountEntryOrganizationRelModelImpl.getOrganizationId()
		};

		finderCache.putResult(
			_finderPathCountByA_O, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByA_O, args, accountEntryOrganizationRelModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		AccountEntryOrganizationRelModelImpl
			accountEntryOrganizationRelModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				accountEntryOrganizationRelModelImpl.getAccountEntryId(),
				accountEntryOrganizationRelModelImpl.getOrganizationId()
			};

			finderCache.removeResult(_finderPathCountByA_O, args);
			finderCache.removeResult(_finderPathFetchByA_O, args);
		}

		if ((accountEntryOrganizationRelModelImpl.getColumnBitmask() &
			 _finderPathFetchByA_O.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				accountEntryOrganizationRelModelImpl.
					getOriginalAccountEntryId(),
				accountEntryOrganizationRelModelImpl.getOriginalOrganizationId()
			};

			finderCache.removeResult(_finderPathCountByA_O, args);
			finderCache.removeResult(_finderPathFetchByA_O, args);
		}
	}

	/**
	 * Creates a new account entry organization rel with the primary key. Does not add the account entry organization rel to the database.
	 *
	 * @param accountEntryOrganizationRelId the primary key for the new account entry organization rel
	 * @return the new account entry organization rel
	 */
	@Override
	public AccountEntryOrganizationRel create(
		long accountEntryOrganizationRelId) {

		AccountEntryOrganizationRel accountEntryOrganizationRel =
			new AccountEntryOrganizationRelImpl();

		accountEntryOrganizationRel.setNew(true);
		accountEntryOrganizationRel.setPrimaryKey(
			accountEntryOrganizationRelId);

		accountEntryOrganizationRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return accountEntryOrganizationRel;
	}

	/**
	 * Removes the account entry organization rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryOrganizationRelId the primary key of the account entry organization rel
	 * @return the account entry organization rel that was removed
	 * @throws NoSuchEntryOrganizationRelException if a account entry organization rel with the primary key could not be found
	 */
	@Override
	public AccountEntryOrganizationRel remove(
			long accountEntryOrganizationRelId)
		throws NoSuchEntryOrganizationRelException {

		return remove((Serializable)accountEntryOrganizationRelId);
	}

	/**
	 * Removes the account entry organization rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the account entry organization rel
	 * @return the account entry organization rel that was removed
	 * @throws NoSuchEntryOrganizationRelException if a account entry organization rel with the primary key could not be found
	 */
	@Override
	public AccountEntryOrganizationRel remove(Serializable primaryKey)
		throws NoSuchEntryOrganizationRelException {

		Session session = null;

		try {
			session = openSession();

			AccountEntryOrganizationRel accountEntryOrganizationRel =
				(AccountEntryOrganizationRel)session.get(
					AccountEntryOrganizationRelImpl.class, primaryKey);

			if (accountEntryOrganizationRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryOrganizationRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(accountEntryOrganizationRel);
		}
		catch (NoSuchEntryOrganizationRelException noSuchEntityException) {
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
	protected AccountEntryOrganizationRel removeImpl(
		AccountEntryOrganizationRel accountEntryOrganizationRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(accountEntryOrganizationRel)) {
				accountEntryOrganizationRel =
					(AccountEntryOrganizationRel)session.get(
						AccountEntryOrganizationRelImpl.class,
						accountEntryOrganizationRel.getPrimaryKeyObj());
			}

			if (accountEntryOrganizationRel != null) {
				session.delete(accountEntryOrganizationRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (accountEntryOrganizationRel != null) {
			clearCache(accountEntryOrganizationRel);
		}

		return accountEntryOrganizationRel;
	}

	@Override
	public AccountEntryOrganizationRel updateImpl(
		AccountEntryOrganizationRel accountEntryOrganizationRel) {

		boolean isNew = accountEntryOrganizationRel.isNew();

		if (!(accountEntryOrganizationRel instanceof
				AccountEntryOrganizationRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					accountEntryOrganizationRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					accountEntryOrganizationRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in accountEntryOrganizationRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AccountEntryOrganizationRel implementation " +
					accountEntryOrganizationRel.getClass());
		}

		AccountEntryOrganizationRelModelImpl
			accountEntryOrganizationRelModelImpl =
				(AccountEntryOrganizationRelModelImpl)
					accountEntryOrganizationRel;

		Session session = null;

		try {
			session = openSession();

			if (accountEntryOrganizationRel.isNew()) {
				session.save(accountEntryOrganizationRel);

				accountEntryOrganizationRel.setNew(false);
			}
			else {
				accountEntryOrganizationRel =
					(AccountEntryOrganizationRel)session.merge(
						accountEntryOrganizationRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				accountEntryOrganizationRelModelImpl.getAccountEntryId()
			};

			finderCache.removeResult(_finderPathCountByAccountEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByAccountEntryId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((accountEntryOrganizationRelModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByAccountEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					accountEntryOrganizationRelModelImpl.
						getOriginalAccountEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByAccountEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAccountEntryId, args);

				args = new Object[] {
					accountEntryOrganizationRelModelImpl.getAccountEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByAccountEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAccountEntryId, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, AccountEntryOrganizationRelImpl.class,
			accountEntryOrganizationRel.getPrimaryKey(),
			accountEntryOrganizationRel, false);

		clearUniqueFindersCache(accountEntryOrganizationRelModelImpl, false);
		cacheUniqueFindersCache(accountEntryOrganizationRelModelImpl);

		accountEntryOrganizationRel.resetOriginalValues();

		return accountEntryOrganizationRel;
	}

	/**
	 * Returns the account entry organization rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the account entry organization rel
	 * @return the account entry organization rel
	 * @throws NoSuchEntryOrganizationRelException if a account entry organization rel with the primary key could not be found
	 */
	@Override
	public AccountEntryOrganizationRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryOrganizationRelException {

		AccountEntryOrganizationRel accountEntryOrganizationRel =
			fetchByPrimaryKey(primaryKey);

		if (accountEntryOrganizationRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryOrganizationRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return accountEntryOrganizationRel;
	}

	/**
	 * Returns the account entry organization rel with the primary key or throws a <code>NoSuchEntryOrganizationRelException</code> if it could not be found.
	 *
	 * @param accountEntryOrganizationRelId the primary key of the account entry organization rel
	 * @return the account entry organization rel
	 * @throws NoSuchEntryOrganizationRelException if a account entry organization rel with the primary key could not be found
	 */
	@Override
	public AccountEntryOrganizationRel findByPrimaryKey(
			long accountEntryOrganizationRelId)
		throws NoSuchEntryOrganizationRelException {

		return findByPrimaryKey((Serializable)accountEntryOrganizationRelId);
	}

	/**
	 * Returns the account entry organization rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param accountEntryOrganizationRelId the primary key of the account entry organization rel
	 * @return the account entry organization rel, or <code>null</code> if a account entry organization rel with the primary key could not be found
	 */
	@Override
	public AccountEntryOrganizationRel fetchByPrimaryKey(
		long accountEntryOrganizationRelId) {

		return fetchByPrimaryKey((Serializable)accountEntryOrganizationRelId);
	}

	/**
	 * Returns all the account entry organization rels.
	 *
	 * @return the account entry organization rels
	 */
	@Override
	public List<AccountEntryOrganizationRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account entry organization rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry organization rels
	 * @param end the upper bound of the range of account entry organization rels (not inclusive)
	 * @return the range of account entry organization rels
	 */
	@Override
	public List<AccountEntryOrganizationRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the account entry organization rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry organization rels
	 * @param end the upper bound of the range of account entry organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of account entry organization rels
	 */
	@Override
	public List<AccountEntryOrganizationRel> findAll(
		int start, int end,
		OrderByComparator<AccountEntryOrganizationRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account entry organization rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry organization rels
	 * @param end the upper bound of the range of account entry organization rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of account entry organization rels
	 */
	@Override
	public List<AccountEntryOrganizationRel> findAll(
		int start, int end,
		OrderByComparator<AccountEntryOrganizationRel> orderByComparator,
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

		List<AccountEntryOrganizationRel> list = null;

		if (useFinderCache) {
			list = (List<AccountEntryOrganizationRel>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ACCOUNTENTRYORGANIZATIONREL);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ACCOUNTENTRYORGANIZATIONREL;

				sql = sql.concat(
					AccountEntryOrganizationRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<AccountEntryOrganizationRel>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the account entry organization rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AccountEntryOrganizationRel accountEntryOrganizationRel :
				findAll()) {

			remove(accountEntryOrganizationRel);
		}
	}

	/**
	 * Returns the number of account entry organization rels.
	 *
	 * @return the number of account entry organization rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
					_SQL_COUNT_ACCOUNTENTRYORGANIZATIONREL);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

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
		return "accountEntryOrganizationRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ACCOUNTENTRYORGANIZATIONREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AccountEntryOrganizationRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the account entry organization rel persistence.
	 */
	@Activate
	public void activate() {
		AccountEntryOrganizationRelModelImpl.setEntityCacheEnabled(
			entityCacheEnabled);
		AccountEntryOrganizationRelModelImpl.setFinderCacheEnabled(
			finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AccountEntryOrganizationRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AccountEntryOrganizationRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByAccountEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AccountEntryOrganizationRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAccountEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByAccountEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AccountEntryOrganizationRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAccountEntryId",
			new String[] {Long.class.getName()},
			AccountEntryOrganizationRelModelImpl.ACCOUNTENTRYID_COLUMN_BITMASK);

		_finderPathCountByAccountEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAccountEntryId",
			new String[] {Long.class.getName()});

		_finderPathFetchByA_O = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AccountEntryOrganizationRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByA_O",
			new String[] {Long.class.getName(), Long.class.getName()},
			AccountEntryOrganizationRelModelImpl.ACCOUNTENTRYID_COLUMN_BITMASK |
			AccountEntryOrganizationRelModelImpl.ORGANIZATIONID_COLUMN_BITMASK);

		_finderPathCountByA_O = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_O",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(
			AccountEntryOrganizationRelImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = AccountPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.account.model.AccountEntryOrganizationRel"),
			true);
	}

	@Override
	@Reference(
		target = AccountPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = AccountPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ACCOUNTENTRYORGANIZATIONREL =
		"SELECT accountEntryOrganizationRel FROM AccountEntryOrganizationRel accountEntryOrganizationRel";

	private static final String _SQL_SELECT_ACCOUNTENTRYORGANIZATIONREL_WHERE =
		"SELECT accountEntryOrganizationRel FROM AccountEntryOrganizationRel accountEntryOrganizationRel WHERE ";

	private static final String _SQL_COUNT_ACCOUNTENTRYORGANIZATIONREL =
		"SELECT COUNT(accountEntryOrganizationRel) FROM AccountEntryOrganizationRel accountEntryOrganizationRel";

	private static final String _SQL_COUNT_ACCOUNTENTRYORGANIZATIONREL_WHERE =
		"SELECT COUNT(accountEntryOrganizationRel) FROM AccountEntryOrganizationRel accountEntryOrganizationRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"accountEntryOrganizationRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AccountEntryOrganizationRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AccountEntryOrganizationRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AccountEntryOrganizationRelPersistenceImpl.class);

	static {
		try {
			Class.forName(AccountPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}