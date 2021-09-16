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

package com.liferay.mail.reader.service.persistence.impl;

import com.liferay.mail.reader.exception.NoSuchAccountException;
import com.liferay.mail.reader.model.Account;
import com.liferay.mail.reader.model.AccountTable;
import com.liferay.mail.reader.model.impl.AccountImpl;
import com.liferay.mail.reader.model.impl.AccountModelImpl;
import com.liferay.mail.reader.service.persistence.AccountPersistence;
import com.liferay.mail.reader.service.persistence.impl.constants.MailPersistenceConstants;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the account service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {AccountPersistence.class, BasePersistence.class})
public class AccountPersistenceImpl
	extends BasePersistenceImpl<Account> implements AccountPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AccountUtil</code> to access the account persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AccountImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the accounts where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching accounts
	 */
	@Override
	public List<Account> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the accounts where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of accounts
	 * @param end the upper bound of the range of accounts (not inclusive)
	 * @return the range of matching accounts
	 */
	@Override
	public List<Account> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the accounts where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of accounts
	 * @param end the upper bound of the range of accounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching accounts
	 */
	@Override
	public List<Account> findByUserId(
		long userId, int start, int end,
		OrderByComparator<Account> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the accounts where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of accounts
	 * @param end the upper bound of the range of accounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching accounts
	 */
	@Override
	public List<Account> findByUserId(
		long userId, int start, int end,
		OrderByComparator<Account> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUserId;
				finderArgs = new Object[] {userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUserId;
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<Account> list = null;

		if (useFinderCache) {
			list = (List<Account>)finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (Account account : list) {
					if (userId != account.getUserId()) {
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

			sb.append(_SQL_SELECT_ACCOUNT_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AccountModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				list = (List<Account>)QueryUtil.list(
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
	 * Returns the first account in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account
	 * @throws NoSuchAccountException if a matching account could not be found
	 */
	@Override
	public Account findByUserId_First(
			long userId, OrderByComparator<Account> orderByComparator)
		throws NoSuchAccountException {

		Account account = fetchByUserId_First(userId, orderByComparator);

		if (account != null) {
			return account;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchAccountException(sb.toString());
	}

	/**
	 * Returns the first account in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account, or <code>null</code> if a matching account could not be found
	 */
	@Override
	public Account fetchByUserId_First(
		long userId, OrderByComparator<Account> orderByComparator) {

		List<Account> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last account in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account
	 * @throws NoSuchAccountException if a matching account could not be found
	 */
	@Override
	public Account findByUserId_Last(
			long userId, OrderByComparator<Account> orderByComparator)
		throws NoSuchAccountException {

		Account account = fetchByUserId_Last(userId, orderByComparator);

		if (account != null) {
			return account;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchAccountException(sb.toString());
	}

	/**
	 * Returns the last account in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account, or <code>null</code> if a matching account could not be found
	 */
	@Override
	public Account fetchByUserId_Last(
		long userId, OrderByComparator<Account> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<Account> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the accounts before and after the current account in the ordered set where userId = &#63;.
	 *
	 * @param accountId the primary key of the current account
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account
	 * @throws NoSuchAccountException if a account with the primary key could not be found
	 */
	@Override
	public Account[] findByUserId_PrevAndNext(
			long accountId, long userId,
			OrderByComparator<Account> orderByComparator)
		throws NoSuchAccountException {

		Account account = findByPrimaryKey(accountId);

		Session session = null;

		try {
			session = openSession();

			Account[] array = new AccountImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, account, userId, orderByComparator, true);

			array[1] = account;

			array[2] = getByUserId_PrevAndNext(
				session, account, userId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Account getByUserId_PrevAndNext(
		Session session, Account account, long userId,
		OrderByComparator<Account> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ACCOUNT_WHERE);

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

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
			sb.append(AccountModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(account)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Account> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the accounts where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (Account account :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(account);
		}
	}

	/**
	 * Returns the number of accounts where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching accounts
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ACCOUNT_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"account.userId = ?";

	private FinderPath _finderPathFetchByU_A;
	private FinderPath _finderPathCountByU_A;

	/**
	 * Returns the account where userId = &#63; and address = &#63; or throws a <code>NoSuchAccountException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param address the address
	 * @return the matching account
	 * @throws NoSuchAccountException if a matching account could not be found
	 */
	@Override
	public Account findByU_A(long userId, String address)
		throws NoSuchAccountException {

		Account account = fetchByU_A(userId, address);

		if (account == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("userId=");
			sb.append(userId);

			sb.append(", address=");
			sb.append(address);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchAccountException(sb.toString());
		}

		return account;
	}

	/**
	 * Returns the account where userId = &#63; and address = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param address the address
	 * @return the matching account, or <code>null</code> if a matching account could not be found
	 */
	@Override
	public Account fetchByU_A(long userId, String address) {
		return fetchByU_A(userId, address, true);
	}

	/**
	 * Returns the account where userId = &#63; and address = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param address the address
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching account, or <code>null</code> if a matching account could not be found
	 */
	@Override
	public Account fetchByU_A(
		long userId, String address, boolean useFinderCache) {

		address = Objects.toString(address, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {userId, address};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByU_A, finderArgs);
		}

		if (result instanceof Account) {
			Account account = (Account)result;

			if ((userId != account.getUserId()) ||
				!Objects.equals(address, account.getAddress())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ACCOUNT_WHERE);

			sb.append(_FINDER_COLUMN_U_A_USERID_2);

			boolean bindAddress = false;

			if (address.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_A_ADDRESS_3);
			}
			else {
				bindAddress = true;

				sb.append(_FINDER_COLUMN_U_A_ADDRESS_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindAddress) {
					queryPos.add(address);
				}

				List<Account> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByU_A, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {userId, address};
							}

							_log.warn(
								"AccountPersistenceImpl.fetchByU_A(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					Account account = list.get(0);

					result = account;

					cacheResult(account);
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
			return (Account)result;
		}
	}

	/**
	 * Removes the account where userId = &#63; and address = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param address the address
	 * @return the account that was removed
	 */
	@Override
	public Account removeByU_A(long userId, String address)
		throws NoSuchAccountException {

		Account account = findByU_A(userId, address);

		return remove(account);
	}

	/**
	 * Returns the number of accounts where userId = &#63; and address = &#63;.
	 *
	 * @param userId the user ID
	 * @param address the address
	 * @return the number of matching accounts
	 */
	@Override
	public int countByU_A(long userId, String address) {
		address = Objects.toString(address, "");

		FinderPath finderPath = _finderPathCountByU_A;

		Object[] finderArgs = new Object[] {userId, address};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ACCOUNT_WHERE);

			sb.append(_FINDER_COLUMN_U_A_USERID_2);

			boolean bindAddress = false;

			if (address.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_A_ADDRESS_3);
			}
			else {
				bindAddress = true;

				sb.append(_FINDER_COLUMN_U_A_ADDRESS_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindAddress) {
					queryPos.add(address);
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

	private static final String _FINDER_COLUMN_U_A_USERID_2 =
		"account.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_A_ADDRESS_2 =
		"account.address = ?";

	private static final String _FINDER_COLUMN_U_A_ADDRESS_3 =
		"(account.address IS NULL OR account.address = '')";

	public AccountPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("password", "password_");

		setDBColumnNames(dbColumnNames);

		setModelClass(Account.class);

		setModelImplClass(AccountImpl.class);
		setModelPKClass(long.class);

		setTable(AccountTable.INSTANCE);
	}

	/**
	 * Caches the account in the entity cache if it is enabled.
	 *
	 * @param account the account
	 */
	@Override
	public void cacheResult(Account account) {
		entityCache.putResult(
			AccountImpl.class, account.getPrimaryKey(), account);

		finderCache.putResult(
			_finderPathFetchByU_A,
			new Object[] {account.getUserId(), account.getAddress()}, account);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the accounts in the entity cache if it is enabled.
	 *
	 * @param accounts the accounts
	 */
	@Override
	public void cacheResult(List<Account> accounts) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (accounts.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (Account account : accounts) {
			if (entityCache.getResult(
					AccountImpl.class, account.getPrimaryKey()) == null) {

				cacheResult(account);
			}
		}
	}

	/**
	 * Clears the cache for all accounts.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AccountImpl.class);

		finderCache.clearCache(AccountImpl.class);
	}

	/**
	 * Clears the cache for the account.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Account account) {
		entityCache.removeResult(AccountImpl.class, account);
	}

	@Override
	public void clearCache(List<Account> accounts) {
		for (Account account : accounts) {
			entityCache.removeResult(AccountImpl.class, account);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(AccountImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(AccountImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(AccountModelImpl accountModelImpl) {
		Object[] args = new Object[] {
			accountModelImpl.getUserId(), accountModelImpl.getAddress()
		};

		finderCache.putResult(_finderPathCountByU_A, args, Long.valueOf(1));
		finderCache.putResult(_finderPathFetchByU_A, args, accountModelImpl);
	}

	/**
	 * Creates a new account with the primary key. Does not add the account to the database.
	 *
	 * @param accountId the primary key for the new account
	 * @return the new account
	 */
	@Override
	public Account create(long accountId) {
		Account account = new AccountImpl();

		account.setNew(true);
		account.setPrimaryKey(accountId);

		account.setCompanyId(CompanyThreadLocal.getCompanyId());

		return account;
	}

	/**
	 * Removes the account with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountId the primary key of the account
	 * @return the account that was removed
	 * @throws NoSuchAccountException if a account with the primary key could not be found
	 */
	@Override
	public Account remove(long accountId) throws NoSuchAccountException {
		return remove((Serializable)accountId);
	}

	/**
	 * Removes the account with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the account
	 * @return the account that was removed
	 * @throws NoSuchAccountException if a account with the primary key could not be found
	 */
	@Override
	public Account remove(Serializable primaryKey)
		throws NoSuchAccountException {

		Session session = null;

		try {
			session = openSession();

			Account account = (Account)session.get(
				AccountImpl.class, primaryKey);

			if (account == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAccountException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(account);
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
	protected Account removeImpl(Account account) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(account)) {
				account = (Account)session.get(
					AccountImpl.class, account.getPrimaryKeyObj());
			}

			if (account != null) {
				session.delete(account);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (account != null) {
			clearCache(account);
		}

		return account;
	}

	@Override
	public Account updateImpl(Account account) {
		boolean isNew = account.isNew();

		if (!(account instanceof AccountModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(account.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(account);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in account proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Account implementation " +
					account.getClass());
		}

		AccountModelImpl accountModelImpl = (AccountModelImpl)account;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (account.getCreateDate() == null)) {
			if (serviceContext == null) {
				account.setCreateDate(date);
			}
			else {
				account.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!accountModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				account.setModifiedDate(date);
			}
			else {
				account.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(account);
			}
			else {
				account = (Account)session.merge(account);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(AccountImpl.class, accountModelImpl, false, true);

		cacheUniqueFindersCache(accountModelImpl);

		if (isNew) {
			account.setNew(false);
		}

		account.resetOriginalValues();

		return account;
	}

	/**
	 * Returns the account with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the account
	 * @return the account
	 * @throws NoSuchAccountException if a account with the primary key could not be found
	 */
	@Override
	public Account findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAccountException {

		Account account = fetchByPrimaryKey(primaryKey);

		if (account == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAccountException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return account;
	}

	/**
	 * Returns the account with the primary key or throws a <code>NoSuchAccountException</code> if it could not be found.
	 *
	 * @param accountId the primary key of the account
	 * @return the account
	 * @throws NoSuchAccountException if a account with the primary key could not be found
	 */
	@Override
	public Account findByPrimaryKey(long accountId)
		throws NoSuchAccountException {

		return findByPrimaryKey((Serializable)accountId);
	}

	/**
	 * Returns the account with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param accountId the primary key of the account
	 * @return the account, or <code>null</code> if a account with the primary key could not be found
	 */
	@Override
	public Account fetchByPrimaryKey(long accountId) {
		return fetchByPrimaryKey((Serializable)accountId);
	}

	/**
	 * Returns all the accounts.
	 *
	 * @return the accounts
	 */
	@Override
	public List<Account> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the accounts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of accounts
	 * @param end the upper bound of the range of accounts (not inclusive)
	 * @return the range of accounts
	 */
	@Override
	public List<Account> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the accounts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of accounts
	 * @param end the upper bound of the range of accounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of accounts
	 */
	@Override
	public List<Account> findAll(
		int start, int end, OrderByComparator<Account> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the accounts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of accounts
	 * @param end the upper bound of the range of accounts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of accounts
	 */
	@Override
	public List<Account> findAll(
		int start, int end, OrderByComparator<Account> orderByComparator,
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

		List<Account> list = null;

		if (useFinderCache) {
			list = (List<Account>)finderCache.getResult(finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ACCOUNT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ACCOUNT;

				sql = sql.concat(AccountModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<Account>)QueryUtil.list(
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
	 * Removes all the accounts from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Account account : findAll()) {
			remove(account);
		}
	}

	/**
	 * Returns the number of accounts.
	 *
	 * @return the number of accounts
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ACCOUNT);

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
		return "accountId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ACCOUNT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AccountModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the account persistence.
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

		_finderPathWithPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"userId"}, true);

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"}, true);

		_finderPathCountByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"},
			false);

		_finderPathFetchByU_A = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByU_A",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"userId", "address"}, true);

		_finderPathCountByU_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_A",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"userId", "address"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(AccountImpl.class.getName());
	}

	@Override
	@Reference(
		target = MailPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = MailPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = MailPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ACCOUNT =
		"SELECT account FROM Account account";

	private static final String _SQL_SELECT_ACCOUNT_WHERE =
		"SELECT account FROM Account account WHERE ";

	private static final String _SQL_COUNT_ACCOUNT =
		"SELECT COUNT(account) FROM Account account";

	private static final String _SQL_COUNT_ACCOUNT_WHERE =
		"SELECT COUNT(account) FROM Account account WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "account.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Account exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Account exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AccountPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"password"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private AccountModelArgumentsResolver _accountModelArgumentsResolver;

}