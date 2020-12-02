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

import com.liferay.account.exception.NoSuchGroupAccountEntryRelException;
import com.liferay.account.model.AccountGroupAccountEntryRel;
import com.liferay.account.model.AccountGroupAccountEntryRelTable;
import com.liferay.account.model.impl.AccountGroupAccountEntryRelImpl;
import com.liferay.account.model.impl.AccountGroupAccountEntryRelModelImpl;
import com.liferay.account.service.persistence.AccountGroupAccountEntryRelPersistence;
import com.liferay.account.service.persistence.impl.constants.AccountPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
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
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the account group account entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {
		AccountGroupAccountEntryRelPersistence.class, BasePersistence.class
	}
)
public class AccountGroupAccountEntryRelPersistenceImpl
	extends BasePersistenceImpl<AccountGroupAccountEntryRel>
	implements AccountGroupAccountEntryRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AccountGroupAccountEntryRelUtil</code> to access the account group account entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AccountGroupAccountEntryRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByAccountGroupId;
	private FinderPath _finderPathWithoutPaginationFindByAccountGroupId;
	private FinderPath _finderPathCountByAccountGroupId;

	/**
	 * Returns all the account group account entry rels where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @return the matching account group account entry rels
	 */
	@Override
	public List<AccountGroupAccountEntryRel> findByAccountGroupId(
		long accountGroupId) {

		return findByAccountGroupId(
			accountGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account group account entry rels where accountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountGroupId the account group ID
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @return the range of matching account group account entry rels
	 */
	@Override
	public List<AccountGroupAccountEntryRel> findByAccountGroupId(
		long accountGroupId, int start, int end) {

		return findByAccountGroupId(accountGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the account group account entry rels where accountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountGroupId the account group ID
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account group account entry rels
	 */
	@Override
	public List<AccountGroupAccountEntryRel> findByAccountGroupId(
		long accountGroupId, int start, int end,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator) {

		return findByAccountGroupId(
			accountGroupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account group account entry rels where accountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountGroupId the account group ID
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account group account entry rels
	 */
	@Override
	public List<AccountGroupAccountEntryRel> findByAccountGroupId(
		long accountGroupId, int start, int end,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByAccountGroupId;
				finderArgs = new Object[] {accountGroupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByAccountGroupId;
			finderArgs = new Object[] {
				accountGroupId, start, end, orderByComparator
			};
		}

		List<AccountGroupAccountEntryRel> list = null;

		if (useFinderCache) {
			list = (List<AccountGroupAccountEntryRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AccountGroupAccountEntryRel accountGroupAccountEntryRel :
						list) {

					if (accountGroupId !=
							accountGroupAccountEntryRel.getAccountGroupId()) {

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

			sb.append(_SQL_SELECT_ACCOUNTGROUPACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_ACCOUNTGROUPID_ACCOUNTGROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AccountGroupAccountEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountGroupId);

				list = (List<AccountGroupAccountEntryRel>)QueryUtil.list(
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
	 * Returns the first account group account entry rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a matching account group account entry rel could not be found
	 */
	@Override
	public AccountGroupAccountEntryRel findByAccountGroupId_First(
			long accountGroupId,
			OrderByComparator<AccountGroupAccountEntryRel> orderByComparator)
		throws NoSuchGroupAccountEntryRelException {

		AccountGroupAccountEntryRel accountGroupAccountEntryRel =
			fetchByAccountGroupId_First(accountGroupId, orderByComparator);

		if (accountGroupAccountEntryRel != null) {
			return accountGroupAccountEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountGroupId=");
		sb.append(accountGroupId);

		sb.append("}");

		throw new NoSuchGroupAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the first account group account entry rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group account entry rel, or <code>null</code> if a matching account group account entry rel could not be found
	 */
	@Override
	public AccountGroupAccountEntryRel fetchByAccountGroupId_First(
		long accountGroupId,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator) {

		List<AccountGroupAccountEntryRel> list = findByAccountGroupId(
			accountGroupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last account group account entry rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a matching account group account entry rel could not be found
	 */
	@Override
	public AccountGroupAccountEntryRel findByAccountGroupId_Last(
			long accountGroupId,
			OrderByComparator<AccountGroupAccountEntryRel> orderByComparator)
		throws NoSuchGroupAccountEntryRelException {

		AccountGroupAccountEntryRel accountGroupAccountEntryRel =
			fetchByAccountGroupId_Last(accountGroupId, orderByComparator);

		if (accountGroupAccountEntryRel != null) {
			return accountGroupAccountEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountGroupId=");
		sb.append(accountGroupId);

		sb.append("}");

		throw new NoSuchGroupAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the last account group account entry rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group account entry rel, or <code>null</code> if a matching account group account entry rel could not be found
	 */
	@Override
	public AccountGroupAccountEntryRel fetchByAccountGroupId_Last(
		long accountGroupId,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator) {

		int count = countByAccountGroupId(accountGroupId);

		if (count == 0) {
			return null;
		}

		List<AccountGroupAccountEntryRel> list = findByAccountGroupId(
			accountGroupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the account group account entry rels before and after the current account group account entry rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param AccountGroupAccountEntryRelId the primary key of the current account group account entry rel
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a account group account entry rel with the primary key could not be found
	 */
	@Override
	public AccountGroupAccountEntryRel[] findByAccountGroupId_PrevAndNext(
			long AccountGroupAccountEntryRelId, long accountGroupId,
			OrderByComparator<AccountGroupAccountEntryRel> orderByComparator)
		throws NoSuchGroupAccountEntryRelException {

		AccountGroupAccountEntryRel accountGroupAccountEntryRel =
			findByPrimaryKey(AccountGroupAccountEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AccountGroupAccountEntryRel[] array =
				new AccountGroupAccountEntryRelImpl[3];

			array[0] = getByAccountGroupId_PrevAndNext(
				session, accountGroupAccountEntryRel, accountGroupId,
				orderByComparator, true);

			array[1] = accountGroupAccountEntryRel;

			array[2] = getByAccountGroupId_PrevAndNext(
				session, accountGroupAccountEntryRel, accountGroupId,
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

	protected AccountGroupAccountEntryRel getByAccountGroupId_PrevAndNext(
		Session session,
		AccountGroupAccountEntryRel accountGroupAccountEntryRel,
		long accountGroupId,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_ACCOUNTGROUPACCOUNTENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_ACCOUNTGROUPID_ACCOUNTGROUPID_2);

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
			sb.append(AccountGroupAccountEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(accountGroupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						accountGroupAccountEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AccountGroupAccountEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the account group account entry rels where accountGroupId = &#63; from the database.
	 *
	 * @param accountGroupId the account group ID
	 */
	@Override
	public void removeByAccountGroupId(long accountGroupId) {
		for (AccountGroupAccountEntryRel accountGroupAccountEntryRel :
				findByAccountGroupId(
					accountGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(accountGroupAccountEntryRel);
		}
	}

	/**
	 * Returns the number of account group account entry rels where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @return the number of matching account group account entry rels
	 */
	@Override
	public int countByAccountGroupId(long accountGroupId) {
		FinderPath finderPath = _finderPathCountByAccountGroupId;

		Object[] finderArgs = new Object[] {accountGroupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ACCOUNTGROUPACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_ACCOUNTGROUPID_ACCOUNTGROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountGroupId);

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

	private static final String _FINDER_COLUMN_ACCOUNTGROUPID_ACCOUNTGROUPID_2 =
		"accountGroupAccountEntryRel.accountGroupId = ?";

	private FinderPath _finderPathWithPaginationFindByAccountEntryId;
	private FinderPath _finderPathWithoutPaginationFindByAccountEntryId;
	private FinderPath _finderPathCountByAccountEntryId;

	/**
	 * Returns all the account group account entry rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the matching account group account entry rels
	 */
	@Override
	public List<AccountGroupAccountEntryRel> findByAccountEntryId(
		long accountEntryId) {

		return findByAccountEntryId(
			accountEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account group account entry rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @return the range of matching account group account entry rels
	 */
	@Override
	public List<AccountGroupAccountEntryRel> findByAccountEntryId(
		long accountEntryId, int start, int end) {

		return findByAccountEntryId(accountEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the account group account entry rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account group account entry rels
	 */
	@Override
	public List<AccountGroupAccountEntryRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator) {

		return findByAccountEntryId(
			accountEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account group account entry rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account group account entry rels
	 */
	@Override
	public List<AccountGroupAccountEntryRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator,
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

		List<AccountGroupAccountEntryRel> list = null;

		if (useFinderCache) {
			list = (List<AccountGroupAccountEntryRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AccountGroupAccountEntryRel accountGroupAccountEntryRel :
						list) {

					if (accountEntryId !=
							accountGroupAccountEntryRel.getAccountEntryId()) {

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

			sb.append(_SQL_SELECT_ACCOUNTGROUPACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AccountGroupAccountEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				list = (List<AccountGroupAccountEntryRel>)QueryUtil.list(
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
	 * Returns the first account group account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a matching account group account entry rel could not be found
	 */
	@Override
	public AccountGroupAccountEntryRel findByAccountEntryId_First(
			long accountEntryId,
			OrderByComparator<AccountGroupAccountEntryRel> orderByComparator)
		throws NoSuchGroupAccountEntryRelException {

		AccountGroupAccountEntryRel accountGroupAccountEntryRel =
			fetchByAccountEntryId_First(accountEntryId, orderByComparator);

		if (accountGroupAccountEntryRel != null) {
			return accountGroupAccountEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append("}");

		throw new NoSuchGroupAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the first account group account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group account entry rel, or <code>null</code> if a matching account group account entry rel could not be found
	 */
	@Override
	public AccountGroupAccountEntryRel fetchByAccountEntryId_First(
		long accountEntryId,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator) {

		List<AccountGroupAccountEntryRel> list = findByAccountEntryId(
			accountEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last account group account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a matching account group account entry rel could not be found
	 */
	@Override
	public AccountGroupAccountEntryRel findByAccountEntryId_Last(
			long accountEntryId,
			OrderByComparator<AccountGroupAccountEntryRel> orderByComparator)
		throws NoSuchGroupAccountEntryRelException {

		AccountGroupAccountEntryRel accountGroupAccountEntryRel =
			fetchByAccountEntryId_Last(accountEntryId, orderByComparator);

		if (accountGroupAccountEntryRel != null) {
			return accountGroupAccountEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append("}");

		throw new NoSuchGroupAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the last account group account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group account entry rel, or <code>null</code> if a matching account group account entry rel could not be found
	 */
	@Override
	public AccountGroupAccountEntryRel fetchByAccountEntryId_Last(
		long accountEntryId,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator) {

		int count = countByAccountEntryId(accountEntryId);

		if (count == 0) {
			return null;
		}

		List<AccountGroupAccountEntryRel> list = findByAccountEntryId(
			accountEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the account group account entry rels before and after the current account group account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param AccountGroupAccountEntryRelId the primary key of the current account group account entry rel
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a account group account entry rel with the primary key could not be found
	 */
	@Override
	public AccountGroupAccountEntryRel[] findByAccountEntryId_PrevAndNext(
			long AccountGroupAccountEntryRelId, long accountEntryId,
			OrderByComparator<AccountGroupAccountEntryRel> orderByComparator)
		throws NoSuchGroupAccountEntryRelException {

		AccountGroupAccountEntryRel accountGroupAccountEntryRel =
			findByPrimaryKey(AccountGroupAccountEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AccountGroupAccountEntryRel[] array =
				new AccountGroupAccountEntryRelImpl[3];

			array[0] = getByAccountEntryId_PrevAndNext(
				session, accountGroupAccountEntryRel, accountEntryId,
				orderByComparator, true);

			array[1] = accountGroupAccountEntryRel;

			array[2] = getByAccountEntryId_PrevAndNext(
				session, accountGroupAccountEntryRel, accountEntryId,
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

	protected AccountGroupAccountEntryRel getByAccountEntryId_PrevAndNext(
		Session session,
		AccountGroupAccountEntryRel accountGroupAccountEntryRel,
		long accountEntryId,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_ACCOUNTGROUPACCOUNTENTRYREL_WHERE);

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
			sb.append(AccountGroupAccountEntryRelModelImpl.ORDER_BY_JPQL);
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
						accountGroupAccountEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AccountGroupAccountEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the account group account entry rels where accountEntryId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 */
	@Override
	public void removeByAccountEntryId(long accountEntryId) {
		for (AccountGroupAccountEntryRel accountGroupAccountEntryRel :
				findByAccountEntryId(
					accountEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(accountGroupAccountEntryRel);
		}
	}

	/**
	 * Returns the number of account group account entry rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the number of matching account group account entry rels
	 */
	@Override
	public int countByAccountEntryId(long accountEntryId) {
		FinderPath finderPath = _finderPathCountByAccountEntryId;

		Object[] finderArgs = new Object[] {accountEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ACCOUNTGROUPACCOUNTENTRYREL_WHERE);

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
		"accountGroupAccountEntryRel.accountEntryId = ?";

	private FinderPath _finderPathFetchByAGI_AEI;
	private FinderPath _finderPathCountByAGI_AEI;

	/**
	 * Returns the account group account entry rel where accountGroupId = &#63; and accountEntryId = &#63; or throws a <code>NoSuchGroupAccountEntryRelException</code> if it could not be found.
	 *
	 * @param accountGroupId the account group ID
	 * @param accountEntryId the account entry ID
	 * @return the matching account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a matching account group account entry rel could not be found
	 */
	@Override
	public AccountGroupAccountEntryRel findByAGI_AEI(
			long accountGroupId, long accountEntryId)
		throws NoSuchGroupAccountEntryRelException {

		AccountGroupAccountEntryRel accountGroupAccountEntryRel =
			fetchByAGI_AEI(accountGroupId, accountEntryId);

		if (accountGroupAccountEntryRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("accountGroupId=");
			sb.append(accountGroupId);

			sb.append(", accountEntryId=");
			sb.append(accountEntryId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchGroupAccountEntryRelException(sb.toString());
		}

		return accountGroupAccountEntryRel;
	}

	/**
	 * Returns the account group account entry rel where accountGroupId = &#63; and accountEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accountGroupId the account group ID
	 * @param accountEntryId the account entry ID
	 * @return the matching account group account entry rel, or <code>null</code> if a matching account group account entry rel could not be found
	 */
	@Override
	public AccountGroupAccountEntryRel fetchByAGI_AEI(
		long accountGroupId, long accountEntryId) {

		return fetchByAGI_AEI(accountGroupId, accountEntryId, true);
	}

	/**
	 * Returns the account group account entry rel where accountGroupId = &#63; and accountEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accountGroupId the account group ID
	 * @param accountEntryId the account entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching account group account entry rel, or <code>null</code> if a matching account group account entry rel could not be found
	 */
	@Override
	public AccountGroupAccountEntryRel fetchByAGI_AEI(
		long accountGroupId, long accountEntryId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {accountGroupId, accountEntryId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByAGI_AEI, finderArgs);
		}

		if (result instanceof AccountGroupAccountEntryRel) {
			AccountGroupAccountEntryRel accountGroupAccountEntryRel =
				(AccountGroupAccountEntryRel)result;

			if ((accountGroupId !=
					accountGroupAccountEntryRel.getAccountGroupId()) ||
				(accountEntryId !=
					accountGroupAccountEntryRel.getAccountEntryId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ACCOUNTGROUPACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_AGI_AEI_ACCOUNTGROUPID_2);

			sb.append(_FINDER_COLUMN_AGI_AEI_ACCOUNTENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountGroupId);

				queryPos.add(accountEntryId);

				List<AccountGroupAccountEntryRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByAGI_AEI, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									accountGroupId, accountEntryId
								};
							}

							_log.warn(
								"AccountGroupAccountEntryRelPersistenceImpl.fetchByAGI_AEI(long, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					AccountGroupAccountEntryRel accountGroupAccountEntryRel =
						list.get(0);

					result = accountGroupAccountEntryRel;

					cacheResult(accountGroupAccountEntryRel);
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
			return (AccountGroupAccountEntryRel)result;
		}
	}

	/**
	 * Removes the account group account entry rel where accountGroupId = &#63; and accountEntryId = &#63; from the database.
	 *
	 * @param accountGroupId the account group ID
	 * @param accountEntryId the account entry ID
	 * @return the account group account entry rel that was removed
	 */
	@Override
	public AccountGroupAccountEntryRel removeByAGI_AEI(
			long accountGroupId, long accountEntryId)
		throws NoSuchGroupAccountEntryRelException {

		AccountGroupAccountEntryRel accountGroupAccountEntryRel = findByAGI_AEI(
			accountGroupId, accountEntryId);

		return remove(accountGroupAccountEntryRel);
	}

	/**
	 * Returns the number of account group account entry rels where accountGroupId = &#63; and accountEntryId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param accountEntryId the account entry ID
	 * @return the number of matching account group account entry rels
	 */
	@Override
	public int countByAGI_AEI(long accountGroupId, long accountEntryId) {
		FinderPath finderPath = _finderPathCountByAGI_AEI;

		Object[] finderArgs = new Object[] {accountGroupId, accountEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ACCOUNTGROUPACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_AGI_AEI_ACCOUNTGROUPID_2);

			sb.append(_FINDER_COLUMN_AGI_AEI_ACCOUNTENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountGroupId);

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

	private static final String _FINDER_COLUMN_AGI_AEI_ACCOUNTGROUPID_2 =
		"accountGroupAccountEntryRel.accountGroupId = ? AND ";

	private static final String _FINDER_COLUMN_AGI_AEI_ACCOUNTENTRYID_2 =
		"accountGroupAccountEntryRel.accountEntryId = ?";

	public AccountGroupAccountEntryRelPersistenceImpl() {
		setModelClass(AccountGroupAccountEntryRel.class);

		setModelImplClass(AccountGroupAccountEntryRelImpl.class);
		setModelPKClass(long.class);

		setTable(AccountGroupAccountEntryRelTable.INSTANCE);
	}

	/**
	 * Caches the account group account entry rel in the entity cache if it is enabled.
	 *
	 * @param accountGroupAccountEntryRel the account group account entry rel
	 */
	@Override
	public void cacheResult(
		AccountGroupAccountEntryRel accountGroupAccountEntryRel) {

		entityCache.putResult(
			AccountGroupAccountEntryRelImpl.class,
			accountGroupAccountEntryRel.getPrimaryKey(),
			accountGroupAccountEntryRel);

		finderCache.putResult(
			_finderPathFetchByAGI_AEI,
			new Object[] {
				accountGroupAccountEntryRel.getAccountGroupId(),
				accountGroupAccountEntryRel.getAccountEntryId()
			},
			accountGroupAccountEntryRel);
	}

	/**
	 * Caches the account group account entry rels in the entity cache if it is enabled.
	 *
	 * @param accountGroupAccountEntryRels the account group account entry rels
	 */
	@Override
	public void cacheResult(
		List<AccountGroupAccountEntryRel> accountGroupAccountEntryRels) {

		for (AccountGroupAccountEntryRel accountGroupAccountEntryRel :
				accountGroupAccountEntryRels) {

			if (entityCache.getResult(
					AccountGroupAccountEntryRelImpl.class,
					accountGroupAccountEntryRel.getPrimaryKey()) == null) {

				cacheResult(accountGroupAccountEntryRel);
			}
		}
	}

	/**
	 * Clears the cache for all account group account entry rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AccountGroupAccountEntryRelImpl.class);

		finderCache.clearCache(AccountGroupAccountEntryRelImpl.class);
	}

	/**
	 * Clears the cache for the account group account entry rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		AccountGroupAccountEntryRel accountGroupAccountEntryRel) {

		entityCache.removeResult(
			AccountGroupAccountEntryRelImpl.class, accountGroupAccountEntryRel);
	}

	@Override
	public void clearCache(
		List<AccountGroupAccountEntryRel> accountGroupAccountEntryRels) {

		for (AccountGroupAccountEntryRel accountGroupAccountEntryRel :
				accountGroupAccountEntryRels) {

			entityCache.removeResult(
				AccountGroupAccountEntryRelImpl.class,
				accountGroupAccountEntryRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(AccountGroupAccountEntryRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				AccountGroupAccountEntryRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AccountGroupAccountEntryRelModelImpl
			accountGroupAccountEntryRelModelImpl) {

		Object[] args = new Object[] {
			accountGroupAccountEntryRelModelImpl.getAccountGroupId(),
			accountGroupAccountEntryRelModelImpl.getAccountEntryId()
		};

		finderCache.putResult(_finderPathCountByAGI_AEI, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByAGI_AEI, args,
			accountGroupAccountEntryRelModelImpl);
	}

	/**
	 * Creates a new account group account entry rel with the primary key. Does not add the account group account entry rel to the database.
	 *
	 * @param AccountGroupAccountEntryRelId the primary key for the new account group account entry rel
	 * @return the new account group account entry rel
	 */
	@Override
	public AccountGroupAccountEntryRel create(
		long AccountGroupAccountEntryRelId) {

		AccountGroupAccountEntryRel accountGroupAccountEntryRel =
			new AccountGroupAccountEntryRelImpl();

		accountGroupAccountEntryRel.setNew(true);
		accountGroupAccountEntryRel.setPrimaryKey(
			AccountGroupAccountEntryRelId);

		accountGroupAccountEntryRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return accountGroupAccountEntryRel;
	}

	/**
	 * Removes the account group account entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param AccountGroupAccountEntryRelId the primary key of the account group account entry rel
	 * @return the account group account entry rel that was removed
	 * @throws NoSuchGroupAccountEntryRelException if a account group account entry rel with the primary key could not be found
	 */
	@Override
	public AccountGroupAccountEntryRel remove(
			long AccountGroupAccountEntryRelId)
		throws NoSuchGroupAccountEntryRelException {

		return remove((Serializable)AccountGroupAccountEntryRelId);
	}

	/**
	 * Removes the account group account entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the account group account entry rel
	 * @return the account group account entry rel that was removed
	 * @throws NoSuchGroupAccountEntryRelException if a account group account entry rel with the primary key could not be found
	 */
	@Override
	public AccountGroupAccountEntryRel remove(Serializable primaryKey)
		throws NoSuchGroupAccountEntryRelException {

		Session session = null;

		try {
			session = openSession();

			AccountGroupAccountEntryRel accountGroupAccountEntryRel =
				(AccountGroupAccountEntryRel)session.get(
					AccountGroupAccountEntryRelImpl.class, primaryKey);

			if (accountGroupAccountEntryRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchGroupAccountEntryRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(accountGroupAccountEntryRel);
		}
		catch (NoSuchGroupAccountEntryRelException noSuchEntityException) {
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
	protected AccountGroupAccountEntryRel removeImpl(
		AccountGroupAccountEntryRel accountGroupAccountEntryRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(accountGroupAccountEntryRel)) {
				accountGroupAccountEntryRel =
					(AccountGroupAccountEntryRel)session.get(
						AccountGroupAccountEntryRelImpl.class,
						accountGroupAccountEntryRel.getPrimaryKeyObj());
			}

			if (accountGroupAccountEntryRel != null) {
				session.delete(accountGroupAccountEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (accountGroupAccountEntryRel != null) {
			clearCache(accountGroupAccountEntryRel);
		}

		return accountGroupAccountEntryRel;
	}

	@Override
	public AccountGroupAccountEntryRel updateImpl(
		AccountGroupAccountEntryRel accountGroupAccountEntryRel) {

		boolean isNew = accountGroupAccountEntryRel.isNew();

		if (!(accountGroupAccountEntryRel instanceof
				AccountGroupAccountEntryRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					accountGroupAccountEntryRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					accountGroupAccountEntryRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in accountGroupAccountEntryRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AccountGroupAccountEntryRel implementation " +
					accountGroupAccountEntryRel.getClass());
		}

		AccountGroupAccountEntryRelModelImpl
			accountGroupAccountEntryRelModelImpl =
				(AccountGroupAccountEntryRelModelImpl)
					accountGroupAccountEntryRel;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(accountGroupAccountEntryRel);
			}
			else {
				accountGroupAccountEntryRel =
					(AccountGroupAccountEntryRel)session.merge(
						accountGroupAccountEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			AccountGroupAccountEntryRelImpl.class,
			accountGroupAccountEntryRelModelImpl, false, true);

		cacheUniqueFindersCache(accountGroupAccountEntryRelModelImpl);

		if (isNew) {
			accountGroupAccountEntryRel.setNew(false);
		}

		accountGroupAccountEntryRel.resetOriginalValues();

		return accountGroupAccountEntryRel;
	}

	/**
	 * Returns the account group account entry rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the account group account entry rel
	 * @return the account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a account group account entry rel with the primary key could not be found
	 */
	@Override
	public AccountGroupAccountEntryRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchGroupAccountEntryRelException {

		AccountGroupAccountEntryRel accountGroupAccountEntryRel =
			fetchByPrimaryKey(primaryKey);

		if (accountGroupAccountEntryRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchGroupAccountEntryRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return accountGroupAccountEntryRel;
	}

	/**
	 * Returns the account group account entry rel with the primary key or throws a <code>NoSuchGroupAccountEntryRelException</code> if it could not be found.
	 *
	 * @param AccountGroupAccountEntryRelId the primary key of the account group account entry rel
	 * @return the account group account entry rel
	 * @throws NoSuchGroupAccountEntryRelException if a account group account entry rel with the primary key could not be found
	 */
	@Override
	public AccountGroupAccountEntryRel findByPrimaryKey(
			long AccountGroupAccountEntryRelId)
		throws NoSuchGroupAccountEntryRelException {

		return findByPrimaryKey((Serializable)AccountGroupAccountEntryRelId);
	}

	/**
	 * Returns the account group account entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param AccountGroupAccountEntryRelId the primary key of the account group account entry rel
	 * @return the account group account entry rel, or <code>null</code> if a account group account entry rel with the primary key could not be found
	 */
	@Override
	public AccountGroupAccountEntryRel fetchByPrimaryKey(
		long AccountGroupAccountEntryRelId) {

		return fetchByPrimaryKey((Serializable)AccountGroupAccountEntryRelId);
	}

	/**
	 * Returns all the account group account entry rels.
	 *
	 * @return the account group account entry rels
	 */
	@Override
	public List<AccountGroupAccountEntryRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account group account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @return the range of account group account entry rels
	 */
	@Override
	public List<AccountGroupAccountEntryRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the account group account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of account group account entry rels
	 */
	@Override
	public List<AccountGroupAccountEntryRel> findAll(
		int start, int end,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account group account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of account group account entry rels
	 */
	@Override
	public List<AccountGroupAccountEntryRel> findAll(
		int start, int end,
		OrderByComparator<AccountGroupAccountEntryRel> orderByComparator,
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

		List<AccountGroupAccountEntryRel> list = null;

		if (useFinderCache) {
			list = (List<AccountGroupAccountEntryRel>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ACCOUNTGROUPACCOUNTENTRYREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ACCOUNTGROUPACCOUNTENTRYREL;

				sql = sql.concat(
					AccountGroupAccountEntryRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AccountGroupAccountEntryRel>)QueryUtil.list(
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
	 * Removes all the account group account entry rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AccountGroupAccountEntryRel accountGroupAccountEntryRel :
				findAll()) {

			remove(accountGroupAccountEntryRel);
		}
	}

	/**
	 * Returns the number of account group account entry rels.
	 *
	 * @return the number of account group account entry rels
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
					_SQL_COUNT_ACCOUNTGROUPACCOUNTENTRYREL);

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
		return "AccountGroupAccountEntryRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ACCOUNTGROUPACCOUNTENTRYREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AccountGroupAccountEntryRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the account group account entry rel persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new AccountGroupAccountEntryRelModelArgumentsResolver(),
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

		_finderPathWithPaginationFindByAccountGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAccountGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"accountGroupId"}, true);

		_finderPathWithoutPaginationFindByAccountGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAccountGroupId",
			new String[] {Long.class.getName()},
			new String[] {"accountGroupId"}, true);

		_finderPathCountByAccountGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAccountGroupId",
			new String[] {Long.class.getName()},
			new String[] {"accountGroupId"}, false);

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

		_finderPathFetchByAGI_AEI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByAGI_AEI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"accountGroupId", "accountEntryId"}, true);

		_finderPathCountByAGI_AEI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAGI_AEI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"accountGroupId", "accountEntryId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(
			AccountGroupAccountEntryRelImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
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

	private BundleContext _bundleContext;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ACCOUNTGROUPACCOUNTENTRYREL =
		"SELECT accountGroupAccountEntryRel FROM AccountGroupAccountEntryRel accountGroupAccountEntryRel";

	private static final String _SQL_SELECT_ACCOUNTGROUPACCOUNTENTRYREL_WHERE =
		"SELECT accountGroupAccountEntryRel FROM AccountGroupAccountEntryRel accountGroupAccountEntryRel WHERE ";

	private static final String _SQL_COUNT_ACCOUNTGROUPACCOUNTENTRYREL =
		"SELECT COUNT(accountGroupAccountEntryRel) FROM AccountGroupAccountEntryRel accountGroupAccountEntryRel";

	private static final String _SQL_COUNT_ACCOUNTGROUPACCOUNTENTRYREL_WHERE =
		"SELECT COUNT(accountGroupAccountEntryRel) FROM AccountGroupAccountEntryRel accountGroupAccountEntryRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"accountGroupAccountEntryRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AccountGroupAccountEntryRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AccountGroupAccountEntryRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AccountGroupAccountEntryRelPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class AccountGroupAccountEntryRelModelArgumentsResolver
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

			AccountGroupAccountEntryRelModelImpl
				accountGroupAccountEntryRelModelImpl =
					(AccountGroupAccountEntryRelModelImpl)baseModel;

			long columnBitmask =
				accountGroupAccountEntryRelModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					accountGroupAccountEntryRelModelImpl, columnNames,
					original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						accountGroupAccountEntryRelModelImpl.getColumnBitmask(
							columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					accountGroupAccountEntryRelModelImpl, columnNames,
					original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return AccountGroupAccountEntryRelImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return AccountGroupAccountEntryRelTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			AccountGroupAccountEntryRelModelImpl
				accountGroupAccountEntryRelModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						accountGroupAccountEntryRelModelImpl.
							getColumnOriginalValue(columnName);
				}
				else {
					arguments[i] =
						accountGroupAccountEntryRelModelImpl.getColumnValue(
							columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}