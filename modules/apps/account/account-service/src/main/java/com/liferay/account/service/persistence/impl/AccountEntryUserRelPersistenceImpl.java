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

import com.liferay.account.exception.NoSuchEntryUserRelException;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.model.AccountEntryUserRelTable;
import com.liferay.account.model.impl.AccountEntryUserRelImpl;
import com.liferay.account.model.impl.AccountEntryUserRelModelImpl;
import com.liferay.account.service.persistence.AccountEntryUserRelPersistence;
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
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
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
 * The persistence implementation for the account entry user rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {AccountEntryUserRelPersistence.class, BasePersistence.class}
)
public class AccountEntryUserRelPersistenceImpl
	extends BasePersistenceImpl<AccountEntryUserRel>
	implements AccountEntryUserRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AccountEntryUserRelUtil</code> to access the account entry user rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AccountEntryUserRelImpl.class.getName();

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
	 * Returns all the account entry user rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the matching account entry user rels
	 */
	@Override
	public List<AccountEntryUserRel> findByAccountEntryId(long accountEntryId) {
		return findByAccountEntryId(
			accountEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account entry user rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @return the range of matching account entry user rels
	 */
	@Override
	public List<AccountEntryUserRel> findByAccountEntryId(
		long accountEntryId, int start, int end) {

		return findByAccountEntryId(accountEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the account entry user rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account entry user rels
	 */
	@Override
	public List<AccountEntryUserRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<AccountEntryUserRel> orderByComparator) {

		return findByAccountEntryId(
			accountEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account entry user rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account entry user rels
	 */
	@Override
	public List<AccountEntryUserRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<AccountEntryUserRel> orderByComparator,
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

		List<AccountEntryUserRel> list = null;

		if (useFinderCache) {
			list = (List<AccountEntryUserRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AccountEntryUserRel accountEntryUserRel : list) {
					if (accountEntryId !=
							accountEntryUserRel.getAccountEntryId()) {

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

			sb.append(_SQL_SELECT_ACCOUNTENTRYUSERREL_WHERE);

			sb.append(_FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AccountEntryUserRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				list = (List<AccountEntryUserRel>)QueryUtil.list(
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
	 * Returns the first account entry user rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry user rel
	 * @throws NoSuchEntryUserRelException if a matching account entry user rel could not be found
	 */
	@Override
	public AccountEntryUserRel findByAccountEntryId_First(
			long accountEntryId,
			OrderByComparator<AccountEntryUserRel> orderByComparator)
		throws NoSuchEntryUserRelException {

		AccountEntryUserRel accountEntryUserRel = fetchByAccountEntryId_First(
			accountEntryId, orderByComparator);

		if (accountEntryUserRel != null) {
			return accountEntryUserRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append("}");

		throw new NoSuchEntryUserRelException(sb.toString());
	}

	/**
	 * Returns the first account entry user rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry user rel, or <code>null</code> if a matching account entry user rel could not be found
	 */
	@Override
	public AccountEntryUserRel fetchByAccountEntryId_First(
		long accountEntryId,
		OrderByComparator<AccountEntryUserRel> orderByComparator) {

		List<AccountEntryUserRel> list = findByAccountEntryId(
			accountEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last account entry user rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry user rel
	 * @throws NoSuchEntryUserRelException if a matching account entry user rel could not be found
	 */
	@Override
	public AccountEntryUserRel findByAccountEntryId_Last(
			long accountEntryId,
			OrderByComparator<AccountEntryUserRel> orderByComparator)
		throws NoSuchEntryUserRelException {

		AccountEntryUserRel accountEntryUserRel = fetchByAccountEntryId_Last(
			accountEntryId, orderByComparator);

		if (accountEntryUserRel != null) {
			return accountEntryUserRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append("}");

		throw new NoSuchEntryUserRelException(sb.toString());
	}

	/**
	 * Returns the last account entry user rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry user rel, or <code>null</code> if a matching account entry user rel could not be found
	 */
	@Override
	public AccountEntryUserRel fetchByAccountEntryId_Last(
		long accountEntryId,
		OrderByComparator<AccountEntryUserRel> orderByComparator) {

		int count = countByAccountEntryId(accountEntryId);

		if (count == 0) {
			return null;
		}

		List<AccountEntryUserRel> list = findByAccountEntryId(
			accountEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the account entry user rels before and after the current account entry user rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryUserRelId the primary key of the current account entry user rel
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account entry user rel
	 * @throws NoSuchEntryUserRelException if a account entry user rel with the primary key could not be found
	 */
	@Override
	public AccountEntryUserRel[] findByAccountEntryId_PrevAndNext(
			long accountEntryUserRelId, long accountEntryId,
			OrderByComparator<AccountEntryUserRel> orderByComparator)
		throws NoSuchEntryUserRelException {

		AccountEntryUserRel accountEntryUserRel = findByPrimaryKey(
			accountEntryUserRelId);

		Session session = null;

		try {
			session = openSession();

			AccountEntryUserRel[] array = new AccountEntryUserRelImpl[3];

			array[0] = getByAccountEntryId_PrevAndNext(
				session, accountEntryUserRel, accountEntryId, orderByComparator,
				true);

			array[1] = accountEntryUserRel;

			array[2] = getByAccountEntryId_PrevAndNext(
				session, accountEntryUserRel, accountEntryId, orderByComparator,
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

	protected AccountEntryUserRel getByAccountEntryId_PrevAndNext(
		Session session, AccountEntryUserRel accountEntryUserRel,
		long accountEntryId,
		OrderByComparator<AccountEntryUserRel> orderByComparator,
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

		sb.append(_SQL_SELECT_ACCOUNTENTRYUSERREL_WHERE);

		sb.append(_FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2);

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
			sb.append(AccountEntryUserRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(accountEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						accountEntryUserRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AccountEntryUserRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the account entry user rels where accountEntryId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 */
	@Override
	public void removeByAccountEntryId(long accountEntryId) {
		for (AccountEntryUserRel accountEntryUserRel :
				findByAccountEntryId(
					accountEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(accountEntryUserRel);
		}
	}

	/**
	 * Returns the number of account entry user rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the number of matching account entry user rels
	 */
	@Override
	public int countByAccountEntryId(long accountEntryId) {
		FinderPath finderPath = _finderPathCountByAccountEntryId;

		Object[] finderArgs = new Object[] {accountEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ACCOUNTENTRYUSERREL_WHERE);

			sb.append(_FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

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

	private static final String _FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2 =
		"accountEntryUserRel.accountEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByAccountUserId;
	private FinderPath _finderPathWithoutPaginationFindByAccountUserId;
	private FinderPath _finderPathCountByAccountUserId;

	/**
	 * Returns all the account entry user rels where accountUserId = &#63;.
	 *
	 * @param accountUserId the account user ID
	 * @return the matching account entry user rels
	 */
	@Override
	public List<AccountEntryUserRel> findByAccountUserId(long accountUserId) {
		return findByAccountUserId(
			accountUserId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account entry user rels where accountUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountUserId the account user ID
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @return the range of matching account entry user rels
	 */
	@Override
	public List<AccountEntryUserRel> findByAccountUserId(
		long accountUserId, int start, int end) {

		return findByAccountUserId(accountUserId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the account entry user rels where accountUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountUserId the account user ID
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account entry user rels
	 */
	@Override
	public List<AccountEntryUserRel> findByAccountUserId(
		long accountUserId, int start, int end,
		OrderByComparator<AccountEntryUserRel> orderByComparator) {

		return findByAccountUserId(
			accountUserId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account entry user rels where accountUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountUserId the account user ID
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account entry user rels
	 */
	@Override
	public List<AccountEntryUserRel> findByAccountUserId(
		long accountUserId, int start, int end,
		OrderByComparator<AccountEntryUserRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByAccountUserId;
				finderArgs = new Object[] {accountUserId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByAccountUserId;
			finderArgs = new Object[] {
				accountUserId, start, end, orderByComparator
			};
		}

		List<AccountEntryUserRel> list = null;

		if (useFinderCache) {
			list = (List<AccountEntryUserRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AccountEntryUserRel accountEntryUserRel : list) {
					if (accountUserId !=
							accountEntryUserRel.getAccountUserId()) {

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

			sb.append(_SQL_SELECT_ACCOUNTENTRYUSERREL_WHERE);

			sb.append(_FINDER_COLUMN_ACCOUNTUSERID_ACCOUNTUSERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AccountEntryUserRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountUserId);

				list = (List<AccountEntryUserRel>)QueryUtil.list(
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
	 * Returns the first account entry user rel in the ordered set where accountUserId = &#63;.
	 *
	 * @param accountUserId the account user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry user rel
	 * @throws NoSuchEntryUserRelException if a matching account entry user rel could not be found
	 */
	@Override
	public AccountEntryUserRel findByAccountUserId_First(
			long accountUserId,
			OrderByComparator<AccountEntryUserRel> orderByComparator)
		throws NoSuchEntryUserRelException {

		AccountEntryUserRel accountEntryUserRel = fetchByAccountUserId_First(
			accountUserId, orderByComparator);

		if (accountEntryUserRel != null) {
			return accountEntryUserRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountUserId=");
		sb.append(accountUserId);

		sb.append("}");

		throw new NoSuchEntryUserRelException(sb.toString());
	}

	/**
	 * Returns the first account entry user rel in the ordered set where accountUserId = &#63;.
	 *
	 * @param accountUserId the account user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account entry user rel, or <code>null</code> if a matching account entry user rel could not be found
	 */
	@Override
	public AccountEntryUserRel fetchByAccountUserId_First(
		long accountUserId,
		OrderByComparator<AccountEntryUserRel> orderByComparator) {

		List<AccountEntryUserRel> list = findByAccountUserId(
			accountUserId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last account entry user rel in the ordered set where accountUserId = &#63;.
	 *
	 * @param accountUserId the account user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry user rel
	 * @throws NoSuchEntryUserRelException if a matching account entry user rel could not be found
	 */
	@Override
	public AccountEntryUserRel findByAccountUserId_Last(
			long accountUserId,
			OrderByComparator<AccountEntryUserRel> orderByComparator)
		throws NoSuchEntryUserRelException {

		AccountEntryUserRel accountEntryUserRel = fetchByAccountUserId_Last(
			accountUserId, orderByComparator);

		if (accountEntryUserRel != null) {
			return accountEntryUserRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountUserId=");
		sb.append(accountUserId);

		sb.append("}");

		throw new NoSuchEntryUserRelException(sb.toString());
	}

	/**
	 * Returns the last account entry user rel in the ordered set where accountUserId = &#63;.
	 *
	 * @param accountUserId the account user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account entry user rel, or <code>null</code> if a matching account entry user rel could not be found
	 */
	@Override
	public AccountEntryUserRel fetchByAccountUserId_Last(
		long accountUserId,
		OrderByComparator<AccountEntryUserRel> orderByComparator) {

		int count = countByAccountUserId(accountUserId);

		if (count == 0) {
			return null;
		}

		List<AccountEntryUserRel> list = findByAccountUserId(
			accountUserId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the account entry user rels before and after the current account entry user rel in the ordered set where accountUserId = &#63;.
	 *
	 * @param accountEntryUserRelId the primary key of the current account entry user rel
	 * @param accountUserId the account user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account entry user rel
	 * @throws NoSuchEntryUserRelException if a account entry user rel with the primary key could not be found
	 */
	@Override
	public AccountEntryUserRel[] findByAccountUserId_PrevAndNext(
			long accountEntryUserRelId, long accountUserId,
			OrderByComparator<AccountEntryUserRel> orderByComparator)
		throws NoSuchEntryUserRelException {

		AccountEntryUserRel accountEntryUserRel = findByPrimaryKey(
			accountEntryUserRelId);

		Session session = null;

		try {
			session = openSession();

			AccountEntryUserRel[] array = new AccountEntryUserRelImpl[3];

			array[0] = getByAccountUserId_PrevAndNext(
				session, accountEntryUserRel, accountUserId, orderByComparator,
				true);

			array[1] = accountEntryUserRel;

			array[2] = getByAccountUserId_PrevAndNext(
				session, accountEntryUserRel, accountUserId, orderByComparator,
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

	protected AccountEntryUserRel getByAccountUserId_PrevAndNext(
		Session session, AccountEntryUserRel accountEntryUserRel,
		long accountUserId,
		OrderByComparator<AccountEntryUserRel> orderByComparator,
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

		sb.append(_SQL_SELECT_ACCOUNTENTRYUSERREL_WHERE);

		sb.append(_FINDER_COLUMN_ACCOUNTUSERID_ACCOUNTUSERID_2);

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
			sb.append(AccountEntryUserRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(accountUserId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						accountEntryUserRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AccountEntryUserRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the account entry user rels where accountUserId = &#63; from the database.
	 *
	 * @param accountUserId the account user ID
	 */
	@Override
	public void removeByAccountUserId(long accountUserId) {
		for (AccountEntryUserRel accountEntryUserRel :
				findByAccountUserId(
					accountUserId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(accountEntryUserRel);
		}
	}

	/**
	 * Returns the number of account entry user rels where accountUserId = &#63;.
	 *
	 * @param accountUserId the account user ID
	 * @return the number of matching account entry user rels
	 */
	@Override
	public int countByAccountUserId(long accountUserId) {
		FinderPath finderPath = _finderPathCountByAccountUserId;

		Object[] finderArgs = new Object[] {accountUserId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ACCOUNTENTRYUSERREL_WHERE);

			sb.append(_FINDER_COLUMN_ACCOUNTUSERID_ACCOUNTUSERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountUserId);

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

	private static final String _FINDER_COLUMN_ACCOUNTUSERID_ACCOUNTUSERID_2 =
		"accountEntryUserRel.accountUserId = ?";

	private FinderPath _finderPathFetchByAEI_AUI;
	private FinderPath _finderPathCountByAEI_AUI;

	/**
	 * Returns the account entry user rel where accountEntryId = &#63; and accountUserId = &#63; or throws a <code>NoSuchEntryUserRelException</code> if it could not be found.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountUserId the account user ID
	 * @return the matching account entry user rel
	 * @throws NoSuchEntryUserRelException if a matching account entry user rel could not be found
	 */
	@Override
	public AccountEntryUserRel findByAEI_AUI(
			long accountEntryId, long accountUserId)
		throws NoSuchEntryUserRelException {

		AccountEntryUserRel accountEntryUserRel = fetchByAEI_AUI(
			accountEntryId, accountUserId);

		if (accountEntryUserRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("accountEntryId=");
			sb.append(accountEntryId);

			sb.append(", accountUserId=");
			sb.append(accountUserId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryUserRelException(sb.toString());
		}

		return accountEntryUserRel;
	}

	/**
	 * Returns the account entry user rel where accountEntryId = &#63; and accountUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountUserId the account user ID
	 * @return the matching account entry user rel, or <code>null</code> if a matching account entry user rel could not be found
	 */
	@Override
	public AccountEntryUserRel fetchByAEI_AUI(
		long accountEntryId, long accountUserId) {

		return fetchByAEI_AUI(accountEntryId, accountUserId, true);
	}

	/**
	 * Returns the account entry user rel where accountEntryId = &#63; and accountUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountUserId the account user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching account entry user rel, or <code>null</code> if a matching account entry user rel could not be found
	 */
	@Override
	public AccountEntryUserRel fetchByAEI_AUI(
		long accountEntryId, long accountUserId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {accountEntryId, accountUserId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByAEI_AUI, finderArgs);
		}

		if (result instanceof AccountEntryUserRel) {
			AccountEntryUserRel accountEntryUserRel =
				(AccountEntryUserRel)result;

			if ((accountEntryId != accountEntryUserRel.getAccountEntryId()) ||
				(accountUserId != accountEntryUserRel.getAccountUserId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ACCOUNTENTRYUSERREL_WHERE);

			sb.append(_FINDER_COLUMN_AEI_AUI_ACCOUNTENTRYID_2);

			sb.append(_FINDER_COLUMN_AEI_AUI_ACCOUNTUSERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				queryPos.add(accountUserId);

				List<AccountEntryUserRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByAEI_AUI, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									accountEntryId, accountUserId
								};
							}

							_log.warn(
								"AccountEntryUserRelPersistenceImpl.fetchByAEI_AUI(long, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					AccountEntryUserRel accountEntryUserRel = list.get(0);

					result = accountEntryUserRel;

					cacheResult(accountEntryUserRel);
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
			return (AccountEntryUserRel)result;
		}
	}

	/**
	 * Removes the account entry user rel where accountEntryId = &#63; and accountUserId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountUserId the account user ID
	 * @return the account entry user rel that was removed
	 */
	@Override
	public AccountEntryUserRel removeByAEI_AUI(
			long accountEntryId, long accountUserId)
		throws NoSuchEntryUserRelException {

		AccountEntryUserRel accountEntryUserRel = findByAEI_AUI(
			accountEntryId, accountUserId);

		return remove(accountEntryUserRel);
	}

	/**
	 * Returns the number of account entry user rels where accountEntryId = &#63; and accountUserId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param accountUserId the account user ID
	 * @return the number of matching account entry user rels
	 */
	@Override
	public int countByAEI_AUI(long accountEntryId, long accountUserId) {
		FinderPath finderPath = _finderPathCountByAEI_AUI;

		Object[] finderArgs = new Object[] {accountEntryId, accountUserId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ACCOUNTENTRYUSERREL_WHERE);

			sb.append(_FINDER_COLUMN_AEI_AUI_ACCOUNTENTRYID_2);

			sb.append(_FINDER_COLUMN_AEI_AUI_ACCOUNTUSERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				queryPos.add(accountUserId);

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

	private static final String _FINDER_COLUMN_AEI_AUI_ACCOUNTENTRYID_2 =
		"accountEntryUserRel.accountEntryId = ? AND ";

	private static final String _FINDER_COLUMN_AEI_AUI_ACCOUNTUSERID_2 =
		"accountEntryUserRel.accountUserId = ?";

	public AccountEntryUserRelPersistenceImpl() {
		setModelClass(AccountEntryUserRel.class);

		setModelImplClass(AccountEntryUserRelImpl.class);
		setModelPKClass(long.class);

		setTable(AccountEntryUserRelTable.INSTANCE);
	}

	/**
	 * Caches the account entry user rel in the entity cache if it is enabled.
	 *
	 * @param accountEntryUserRel the account entry user rel
	 */
	@Override
	public void cacheResult(AccountEntryUserRel accountEntryUserRel) {
		entityCache.putResult(
			AccountEntryUserRelImpl.class, accountEntryUserRel.getPrimaryKey(),
			accountEntryUserRel);

		finderCache.putResult(
			_finderPathFetchByAEI_AUI,
			new Object[] {
				accountEntryUserRel.getAccountEntryId(),
				accountEntryUserRel.getAccountUserId()
			},
			accountEntryUserRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the account entry user rels in the entity cache if it is enabled.
	 *
	 * @param accountEntryUserRels the account entry user rels
	 */
	@Override
	public void cacheResult(List<AccountEntryUserRel> accountEntryUserRels) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (accountEntryUserRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AccountEntryUserRel accountEntryUserRel : accountEntryUserRels) {
			if (entityCache.getResult(
					AccountEntryUserRelImpl.class,
					accountEntryUserRel.getPrimaryKey()) == null) {

				cacheResult(accountEntryUserRel);
			}
		}
	}

	/**
	 * Clears the cache for all account entry user rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AccountEntryUserRelImpl.class);

		finderCache.clearCache(AccountEntryUserRelImpl.class);
	}

	/**
	 * Clears the cache for the account entry user rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AccountEntryUserRel accountEntryUserRel) {
		entityCache.removeResult(
			AccountEntryUserRelImpl.class, accountEntryUserRel);
	}

	@Override
	public void clearCache(List<AccountEntryUserRel> accountEntryUserRels) {
		for (AccountEntryUserRel accountEntryUserRel : accountEntryUserRels) {
			entityCache.removeResult(
				AccountEntryUserRelImpl.class, accountEntryUserRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(AccountEntryUserRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(AccountEntryUserRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AccountEntryUserRelModelImpl accountEntryUserRelModelImpl) {

		Object[] args = new Object[] {
			accountEntryUserRelModelImpl.getAccountEntryId(),
			accountEntryUserRelModelImpl.getAccountUserId()
		};

		finderCache.putResult(_finderPathCountByAEI_AUI, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByAEI_AUI, args, accountEntryUserRelModelImpl);
	}

	/**
	 * Creates a new account entry user rel with the primary key. Does not add the account entry user rel to the database.
	 *
	 * @param accountEntryUserRelId the primary key for the new account entry user rel
	 * @return the new account entry user rel
	 */
	@Override
	public AccountEntryUserRel create(long accountEntryUserRelId) {
		AccountEntryUserRel accountEntryUserRel = new AccountEntryUserRelImpl();

		accountEntryUserRel.setNew(true);
		accountEntryUserRel.setPrimaryKey(accountEntryUserRelId);

		accountEntryUserRel.setCompanyId(CompanyThreadLocal.getCompanyId());

		return accountEntryUserRel;
	}

	/**
	 * Removes the account entry user rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryUserRelId the primary key of the account entry user rel
	 * @return the account entry user rel that was removed
	 * @throws NoSuchEntryUserRelException if a account entry user rel with the primary key could not be found
	 */
	@Override
	public AccountEntryUserRel remove(long accountEntryUserRelId)
		throws NoSuchEntryUserRelException {

		return remove((Serializable)accountEntryUserRelId);
	}

	/**
	 * Removes the account entry user rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the account entry user rel
	 * @return the account entry user rel that was removed
	 * @throws NoSuchEntryUserRelException if a account entry user rel with the primary key could not be found
	 */
	@Override
	public AccountEntryUserRel remove(Serializable primaryKey)
		throws NoSuchEntryUserRelException {

		Session session = null;

		try {
			session = openSession();

			AccountEntryUserRel accountEntryUserRel =
				(AccountEntryUserRel)session.get(
					AccountEntryUserRelImpl.class, primaryKey);

			if (accountEntryUserRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryUserRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(accountEntryUserRel);
		}
		catch (NoSuchEntryUserRelException noSuchEntityException) {
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
	protected AccountEntryUserRel removeImpl(
		AccountEntryUserRel accountEntryUserRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(accountEntryUserRel)) {
				accountEntryUserRel = (AccountEntryUserRel)session.get(
					AccountEntryUserRelImpl.class,
					accountEntryUserRel.getPrimaryKeyObj());
			}

			if (accountEntryUserRel != null) {
				session.delete(accountEntryUserRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (accountEntryUserRel != null) {
			clearCache(accountEntryUserRel);
		}

		return accountEntryUserRel;
	}

	@Override
	public AccountEntryUserRel updateImpl(
		AccountEntryUserRel accountEntryUserRel) {

		boolean isNew = accountEntryUserRel.isNew();

		if (!(accountEntryUserRel instanceof AccountEntryUserRelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(accountEntryUserRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					accountEntryUserRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in accountEntryUserRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AccountEntryUserRel implementation " +
					accountEntryUserRel.getClass());
		}

		AccountEntryUserRelModelImpl accountEntryUserRelModelImpl =
			(AccountEntryUserRelModelImpl)accountEntryUserRel;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(accountEntryUserRel);
			}
			else {
				accountEntryUserRel = (AccountEntryUserRel)session.merge(
					accountEntryUserRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			AccountEntryUserRelImpl.class, accountEntryUserRelModelImpl, false,
			true);

		cacheUniqueFindersCache(accountEntryUserRelModelImpl);

		if (isNew) {
			accountEntryUserRel.setNew(false);
		}

		accountEntryUserRel.resetOriginalValues();

		return accountEntryUserRel;
	}

	/**
	 * Returns the account entry user rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the account entry user rel
	 * @return the account entry user rel
	 * @throws NoSuchEntryUserRelException if a account entry user rel with the primary key could not be found
	 */
	@Override
	public AccountEntryUserRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryUserRelException {

		AccountEntryUserRel accountEntryUserRel = fetchByPrimaryKey(primaryKey);

		if (accountEntryUserRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryUserRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return accountEntryUserRel;
	}

	/**
	 * Returns the account entry user rel with the primary key or throws a <code>NoSuchEntryUserRelException</code> if it could not be found.
	 *
	 * @param accountEntryUserRelId the primary key of the account entry user rel
	 * @return the account entry user rel
	 * @throws NoSuchEntryUserRelException if a account entry user rel with the primary key could not be found
	 */
	@Override
	public AccountEntryUserRel findByPrimaryKey(long accountEntryUserRelId)
		throws NoSuchEntryUserRelException {

		return findByPrimaryKey((Serializable)accountEntryUserRelId);
	}

	/**
	 * Returns the account entry user rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param accountEntryUserRelId the primary key of the account entry user rel
	 * @return the account entry user rel, or <code>null</code> if a account entry user rel with the primary key could not be found
	 */
	@Override
	public AccountEntryUserRel fetchByPrimaryKey(long accountEntryUserRelId) {
		return fetchByPrimaryKey((Serializable)accountEntryUserRelId);
	}

	/**
	 * Returns all the account entry user rels.
	 *
	 * @return the account entry user rels
	 */
	@Override
	public List<AccountEntryUserRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account entry user rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @return the range of account entry user rels
	 */
	@Override
	public List<AccountEntryUserRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the account entry user rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of account entry user rels
	 */
	@Override
	public List<AccountEntryUserRel> findAll(
		int start, int end,
		OrderByComparator<AccountEntryUserRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account entry user rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of account entry user rels
	 */
	@Override
	public List<AccountEntryUserRel> findAll(
		int start, int end,
		OrderByComparator<AccountEntryUserRel> orderByComparator,
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

		List<AccountEntryUserRel> list = null;

		if (useFinderCache) {
			list = (List<AccountEntryUserRel>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ACCOUNTENTRYUSERREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ACCOUNTENTRYUSERREL;

				sql = sql.concat(AccountEntryUserRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AccountEntryUserRel>)QueryUtil.list(
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
	 * Removes all the account entry user rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AccountEntryUserRel accountEntryUserRel : findAll()) {
			remove(accountEntryUserRel);
		}
	}

	/**
	 * Returns the number of account entry user rels.
	 *
	 * @return the number of account entry user rels
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
					_SQL_COUNT_ACCOUNTENTRYUSERREL);

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
		return "accountEntryUserRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ACCOUNTENTRYUSERREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AccountEntryUserRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the account entry user rel persistence.
	 */
	@Activate
	public void activate() {
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

		_finderPathWithPaginationFindByAccountEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAccountEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"accountEntryId"}, true);

		_finderPathWithoutPaginationFindByAccountEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAccountEntryId",
			new String[] {Long.class.getName()},
			new String[] {"accountEntryId"}, true);

		_finderPathCountByAccountEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAccountEntryId",
			new String[] {Long.class.getName()},
			new String[] {"accountEntryId"}, false);

		_finderPathWithPaginationFindByAccountUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAccountUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"accountUserId"}, true);

		_finderPathWithoutPaginationFindByAccountUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAccountUserId",
			new String[] {Long.class.getName()}, new String[] {"accountUserId"},
			true);

		_finderPathCountByAccountUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAccountUserId",
			new String[] {Long.class.getName()}, new String[] {"accountUserId"},
			false);

		_finderPathFetchByAEI_AUI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByAEI_AUI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"accountEntryId", "accountUserId"}, true);

		_finderPathCountByAEI_AUI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAEI_AUI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"accountEntryId", "accountUserId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(AccountEntryUserRelImpl.class.getName());
	}

	@Override
	@Reference(
		target = AccountPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
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

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ACCOUNTENTRYUSERREL =
		"SELECT accountEntryUserRel FROM AccountEntryUserRel accountEntryUserRel";

	private static final String _SQL_SELECT_ACCOUNTENTRYUSERREL_WHERE =
		"SELECT accountEntryUserRel FROM AccountEntryUserRel accountEntryUserRel WHERE ";

	private static final String _SQL_COUNT_ACCOUNTENTRYUSERREL =
		"SELECT COUNT(accountEntryUserRel) FROM AccountEntryUserRel accountEntryUserRel";

	private static final String _SQL_COUNT_ACCOUNTENTRYUSERREL_WHERE =
		"SELECT COUNT(accountEntryUserRel) FROM AccountEntryUserRel accountEntryUserRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "accountEntryUserRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AccountEntryUserRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AccountEntryUserRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AccountEntryUserRelPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private AccountEntryUserRelModelArgumentsResolver
		_accountEntryUserRelModelArgumentsResolver;

}