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

import com.liferay.account.exception.NoSuchGroupRelException;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.model.AccountGroupRelTable;
import com.liferay.account.model.impl.AccountGroupRelImpl;
import com.liferay.account.model.impl.AccountGroupRelModelImpl;
import com.liferay.account.service.persistence.AccountGroupRelPersistence;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the account group rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {AccountGroupRelPersistence.class, BasePersistence.class})
public class AccountGroupRelPersistenceImpl
	extends BasePersistenceImpl<AccountGroupRel>
	implements AccountGroupRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AccountGroupRelUtil</code> to access the account group rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AccountGroupRelImpl.class.getName();

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
	 * Returns all the account group rels where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @return the matching account group rels
	 */
	@Override
	public List<AccountGroupRel> findByAccountGroupId(long accountGroupId) {
		return findByAccountGroupId(
			accountGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account group rels where accountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountGroupId the account group ID
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @return the range of matching account group rels
	 */
	@Override
	public List<AccountGroupRel> findByAccountGroupId(
		long accountGroupId, int start, int end) {

		return findByAccountGroupId(accountGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the account group rels where accountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountGroupId the account group ID
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account group rels
	 */
	@Override
	public List<AccountGroupRel> findByAccountGroupId(
		long accountGroupId, int start, int end,
		OrderByComparator<AccountGroupRel> orderByComparator) {

		return findByAccountGroupId(
			accountGroupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account group rels where accountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountGroupId the account group ID
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account group rels
	 */
	@Override
	public List<AccountGroupRel> findByAccountGroupId(
		long accountGroupId, int start, int end,
		OrderByComparator<AccountGroupRel> orderByComparator,
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

		List<AccountGroupRel> list = null;

		if (useFinderCache) {
			list = (List<AccountGroupRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AccountGroupRel accountGroupRel : list) {
					if (accountGroupId != accountGroupRel.getAccountGroupId()) {
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

			sb.append(_SQL_SELECT_ACCOUNTGROUPREL_WHERE);

			sb.append(_FINDER_COLUMN_ACCOUNTGROUPID_ACCOUNTGROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AccountGroupRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountGroupId);

				list = (List<AccountGroupRel>)QueryUtil.list(
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
	 * Returns the first account group rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group rel
	 * @throws NoSuchGroupRelException if a matching account group rel could not be found
	 */
	@Override
	public AccountGroupRel findByAccountGroupId_First(
			long accountGroupId,
			OrderByComparator<AccountGroupRel> orderByComparator)
		throws NoSuchGroupRelException {

		AccountGroupRel accountGroupRel = fetchByAccountGroupId_First(
			accountGroupId, orderByComparator);

		if (accountGroupRel != null) {
			return accountGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountGroupId=");
		sb.append(accountGroupId);

		sb.append("}");

		throw new NoSuchGroupRelException(sb.toString());
	}

	/**
	 * Returns the first account group rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	@Override
	public AccountGroupRel fetchByAccountGroupId_First(
		long accountGroupId,
		OrderByComparator<AccountGroupRel> orderByComparator) {

		List<AccountGroupRel> list = findByAccountGroupId(
			accountGroupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last account group rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group rel
	 * @throws NoSuchGroupRelException if a matching account group rel could not be found
	 */
	@Override
	public AccountGroupRel findByAccountGroupId_Last(
			long accountGroupId,
			OrderByComparator<AccountGroupRel> orderByComparator)
		throws NoSuchGroupRelException {

		AccountGroupRel accountGroupRel = fetchByAccountGroupId_Last(
			accountGroupId, orderByComparator);

		if (accountGroupRel != null) {
			return accountGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountGroupId=");
		sb.append(accountGroupId);

		sb.append("}");

		throw new NoSuchGroupRelException(sb.toString());
	}

	/**
	 * Returns the last account group rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	@Override
	public AccountGroupRel fetchByAccountGroupId_Last(
		long accountGroupId,
		OrderByComparator<AccountGroupRel> orderByComparator) {

		int count = countByAccountGroupId(accountGroupId);

		if (count == 0) {
			return null;
		}

		List<AccountGroupRel> list = findByAccountGroupId(
			accountGroupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the account group rels before and after the current account group rel in the ordered set where accountGroupId = &#63;.
	 *
	 * @param accountGroupRelId the primary key of the current account group rel
	 * @param accountGroupId the account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account group rel
	 * @throws NoSuchGroupRelException if a account group rel with the primary key could not be found
	 */
	@Override
	public AccountGroupRel[] findByAccountGroupId_PrevAndNext(
			long accountGroupRelId, long accountGroupId,
			OrderByComparator<AccountGroupRel> orderByComparator)
		throws NoSuchGroupRelException {

		AccountGroupRel accountGroupRel = findByPrimaryKey(accountGroupRelId);

		Session session = null;

		try {
			session = openSession();

			AccountGroupRel[] array = new AccountGroupRelImpl[3];

			array[0] = getByAccountGroupId_PrevAndNext(
				session, accountGroupRel, accountGroupId, orderByComparator,
				true);

			array[1] = accountGroupRel;

			array[2] = getByAccountGroupId_PrevAndNext(
				session, accountGroupRel, accountGroupId, orderByComparator,
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

	protected AccountGroupRel getByAccountGroupId_PrevAndNext(
		Session session, AccountGroupRel accountGroupRel, long accountGroupId,
		OrderByComparator<AccountGroupRel> orderByComparator,
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

		sb.append(_SQL_SELECT_ACCOUNTGROUPREL_WHERE);

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
			sb.append(AccountGroupRelModelImpl.ORDER_BY_JPQL);
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
						accountGroupRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AccountGroupRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the account group rels where accountGroupId = &#63; from the database.
	 *
	 * @param accountGroupId the account group ID
	 */
	@Override
	public void removeByAccountGroupId(long accountGroupId) {
		for (AccountGroupRel accountGroupRel :
				findByAccountGroupId(
					accountGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(accountGroupRel);
		}
	}

	/**
	 * Returns the number of account group rels where accountGroupId = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @return the number of matching account group rels
	 */
	@Override
	public int countByAccountGroupId(long accountGroupId) {
		FinderPath finderPath = _finderPathCountByAccountGroupId;

		Object[] finderArgs = new Object[] {accountGroupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ACCOUNTGROUPREL_WHERE);

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
		"accountGroupRel.accountGroupId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching account group rels
	 */
	@Override
	public List<AccountGroupRel> findByC_C(long classNameId, long classPK) {
		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @return the range of matching account group rels
	 */
	@Override
	public List<AccountGroupRel> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account group rels
	 */
	@Override
	public List<AccountGroupRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<AccountGroupRel> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account group rels
	 */
	@Override
	public List<AccountGroupRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<AccountGroupRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {classNameId, classPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<AccountGroupRel> list = null;

		if (useFinderCache) {
			list = (List<AccountGroupRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AccountGroupRel accountGroupRel : list) {
					if ((classNameId != accountGroupRel.getClassNameId()) ||
						(classPK != accountGroupRel.getClassPK())) {

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

			sb.append(_SQL_SELECT_ACCOUNTGROUPREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AccountGroupRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<AccountGroupRel>)QueryUtil.list(
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
	 * Returns the first account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group rel
	 * @throws NoSuchGroupRelException if a matching account group rel could not be found
	 */
	@Override
	public AccountGroupRel findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<AccountGroupRel> orderByComparator)
		throws NoSuchGroupRelException {

		AccountGroupRel accountGroupRel = fetchByC_C_First(
			classNameId, classPK, orderByComparator);

		if (accountGroupRel != null) {
			return accountGroupRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchGroupRelException(sb.toString());
	}

	/**
	 * Returns the first account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	@Override
	public AccountGroupRel fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<AccountGroupRel> orderByComparator) {

		List<AccountGroupRel> list = findByC_C(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group rel
	 * @throws NoSuchGroupRelException if a matching account group rel could not be found
	 */
	@Override
	public AccountGroupRel findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<AccountGroupRel> orderByComparator)
		throws NoSuchGroupRelException {

		AccountGroupRel accountGroupRel = fetchByC_C_Last(
			classNameId, classPK, orderByComparator);

		if (accountGroupRel != null) {
			return accountGroupRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchGroupRelException(sb.toString());
	}

	/**
	 * Returns the last account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	@Override
	public AccountGroupRel fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<AccountGroupRel> orderByComparator) {

		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<AccountGroupRel> list = findByC_C(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the account group rels before and after the current account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param accountGroupRelId the primary key of the current account group rel
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account group rel
	 * @throws NoSuchGroupRelException if a account group rel with the primary key could not be found
	 */
	@Override
	public AccountGroupRel[] findByC_C_PrevAndNext(
			long accountGroupRelId, long classNameId, long classPK,
			OrderByComparator<AccountGroupRel> orderByComparator)
		throws NoSuchGroupRelException {

		AccountGroupRel accountGroupRel = findByPrimaryKey(accountGroupRelId);

		Session session = null;

		try {
			session = openSession();

			AccountGroupRel[] array = new AccountGroupRelImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, accountGroupRel, classNameId, classPK,
				orderByComparator, true);

			array[1] = accountGroupRel;

			array[2] = getByC_C_PrevAndNext(
				session, accountGroupRel, classNameId, classPK,
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

	protected AccountGroupRel getByC_C_PrevAndNext(
		Session session, AccountGroupRel accountGroupRel, long classNameId,
		long classPK, OrderByComparator<AccountGroupRel> orderByComparator,
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

		sb.append(_SQL_SELECT_ACCOUNTGROUPREL_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			sb.append(AccountGroupRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						accountGroupRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AccountGroupRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the account group rels where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (AccountGroupRel accountGroupRel :
				findByC_C(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(accountGroupRel);
		}
	}

	/**
	 * Returns the number of account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching account group rels
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ACCOUNTGROUPREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"accountGroupRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"accountGroupRel.classPK = ?";

	private FinderPath _finderPathFetchByA_C_C;
	private FinderPath _finderPathCountByA_C_C;

	/**
	 * Returns the account group rel where accountGroupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchGroupRelException</code> if it could not be found.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching account group rel
	 * @throws NoSuchGroupRelException if a matching account group rel could not be found
	 */
	@Override
	public AccountGroupRel findByA_C_C(
			long accountGroupId, long classNameId, long classPK)
		throws NoSuchGroupRelException {

		AccountGroupRel accountGroupRel = fetchByA_C_C(
			accountGroupId, classNameId, classPK);

		if (accountGroupRel == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("accountGroupId=");
			sb.append(accountGroupId);

			sb.append(", classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchGroupRelException(sb.toString());
		}

		return accountGroupRel;
	}

	/**
	 * Returns the account group rel where accountGroupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	@Override
	public AccountGroupRel fetchByA_C_C(
		long accountGroupId, long classNameId, long classPK) {

		return fetchByA_C_C(accountGroupId, classNameId, classPK, true);
	}

	/**
	 * Returns the account group rel where accountGroupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching account group rel, or <code>null</code> if a matching account group rel could not be found
	 */
	@Override
	public AccountGroupRel fetchByA_C_C(
		long accountGroupId, long classNameId, long classPK,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {accountGroupId, classNameId, classPK};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByA_C_C, finderArgs);
		}

		if (result instanceof AccountGroupRel) {
			AccountGroupRel accountGroupRel = (AccountGroupRel)result;

			if ((accountGroupId != accountGroupRel.getAccountGroupId()) ||
				(classNameId != accountGroupRel.getClassNameId()) ||
				(classPK != accountGroupRel.getClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_ACCOUNTGROUPREL_WHERE);

			sb.append(_FINDER_COLUMN_A_C_C_ACCOUNTGROUPID_2);

			sb.append(_FINDER_COLUMN_A_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_A_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountGroupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				List<AccountGroupRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByA_C_C, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									accountGroupId, classNameId, classPK
								};
							}

							_log.warn(
								"AccountGroupRelPersistenceImpl.fetchByA_C_C(long, long, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					AccountGroupRel accountGroupRel = list.get(0);

					result = accountGroupRel;

					cacheResult(accountGroupRel);
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
			return (AccountGroupRel)result;
		}
	}

	/**
	 * Removes the account group rel where accountGroupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the account group rel that was removed
	 */
	@Override
	public AccountGroupRel removeByA_C_C(
			long accountGroupId, long classNameId, long classPK)
		throws NoSuchGroupRelException {

		AccountGroupRel accountGroupRel = findByA_C_C(
			accountGroupId, classNameId, classPK);

		return remove(accountGroupRel);
	}

	/**
	 * Returns the number of account group rels where accountGroupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param accountGroupId the account group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching account group rels
	 */
	@Override
	public int countByA_C_C(
		long accountGroupId, long classNameId, long classPK) {

		FinderPath finderPath = _finderPathCountByA_C_C;

		Object[] finderArgs = new Object[] {
			accountGroupId, classNameId, classPK
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ACCOUNTGROUPREL_WHERE);

			sb.append(_FINDER_COLUMN_A_C_C_ACCOUNTGROUPID_2);

			sb.append(_FINDER_COLUMN_A_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_A_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountGroupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_A_C_C_ACCOUNTGROUPID_2 =
		"accountGroupRel.accountGroupId = ? AND ";

	private static final String _FINDER_COLUMN_A_C_C_CLASSNAMEID_2 =
		"accountGroupRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_A_C_C_CLASSPK_2 =
		"accountGroupRel.classPK = ?";

	public AccountGroupRelPersistenceImpl() {
		setModelClass(AccountGroupRel.class);

		setModelImplClass(AccountGroupRelImpl.class);
		setModelPKClass(long.class);

		setTable(AccountGroupRelTable.INSTANCE);
	}

	/**
	 * Caches the account group rel in the entity cache if it is enabled.
	 *
	 * @param accountGroupRel the account group rel
	 */
	@Override
	public void cacheResult(AccountGroupRel accountGroupRel) {
		entityCache.putResult(
			AccountGroupRelImpl.class, accountGroupRel.getPrimaryKey(),
			accountGroupRel);

		finderCache.putResult(
			_finderPathFetchByA_C_C,
			new Object[] {
				accountGroupRel.getAccountGroupId(),
				accountGroupRel.getClassNameId(), accountGroupRel.getClassPK()
			},
			accountGroupRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the account group rels in the entity cache if it is enabled.
	 *
	 * @param accountGroupRels the account group rels
	 */
	@Override
	public void cacheResult(List<AccountGroupRel> accountGroupRels) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (accountGroupRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AccountGroupRel accountGroupRel : accountGroupRels) {
			if (entityCache.getResult(
					AccountGroupRelImpl.class,
					accountGroupRel.getPrimaryKey()) == null) {

				cacheResult(accountGroupRel);
			}
		}
	}

	/**
	 * Clears the cache for all account group rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AccountGroupRelImpl.class);

		finderCache.clearCache(AccountGroupRelImpl.class);
	}

	/**
	 * Clears the cache for the account group rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AccountGroupRel accountGroupRel) {
		entityCache.removeResult(AccountGroupRelImpl.class, accountGroupRel);
	}

	@Override
	public void clearCache(List<AccountGroupRel> accountGroupRels) {
		for (AccountGroupRel accountGroupRel : accountGroupRels) {
			entityCache.removeResult(
				AccountGroupRelImpl.class, accountGroupRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(AccountGroupRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(AccountGroupRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AccountGroupRelModelImpl accountGroupRelModelImpl) {

		Object[] args = new Object[] {
			accountGroupRelModelImpl.getAccountGroupId(),
			accountGroupRelModelImpl.getClassNameId(),
			accountGroupRelModelImpl.getClassPK()
		};

		finderCache.putResult(_finderPathCountByA_C_C, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByA_C_C, args, accountGroupRelModelImpl);
	}

	/**
	 * Creates a new account group rel with the primary key. Does not add the account group rel to the database.
	 *
	 * @param accountGroupRelId the primary key for the new account group rel
	 * @return the new account group rel
	 */
	@Override
	public AccountGroupRel create(long accountGroupRelId) {
		AccountGroupRel accountGroupRel = new AccountGroupRelImpl();

		accountGroupRel.setNew(true);
		accountGroupRel.setPrimaryKey(accountGroupRelId);

		accountGroupRel.setCompanyId(CompanyThreadLocal.getCompanyId());

		return accountGroupRel;
	}

	/**
	 * Removes the account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountGroupRelId the primary key of the account group rel
	 * @return the account group rel that was removed
	 * @throws NoSuchGroupRelException if a account group rel with the primary key could not be found
	 */
	@Override
	public AccountGroupRel remove(long accountGroupRelId)
		throws NoSuchGroupRelException {

		return remove((Serializable)accountGroupRelId);
	}

	/**
	 * Removes the account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the account group rel
	 * @return the account group rel that was removed
	 * @throws NoSuchGroupRelException if a account group rel with the primary key could not be found
	 */
	@Override
	public AccountGroupRel remove(Serializable primaryKey)
		throws NoSuchGroupRelException {

		Session session = null;

		try {
			session = openSession();

			AccountGroupRel accountGroupRel = (AccountGroupRel)session.get(
				AccountGroupRelImpl.class, primaryKey);

			if (accountGroupRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchGroupRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(accountGroupRel);
		}
		catch (NoSuchGroupRelException noSuchEntityException) {
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
	protected AccountGroupRel removeImpl(AccountGroupRel accountGroupRel) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(accountGroupRel)) {
				accountGroupRel = (AccountGroupRel)session.get(
					AccountGroupRelImpl.class,
					accountGroupRel.getPrimaryKeyObj());
			}

			if (accountGroupRel != null) {
				session.delete(accountGroupRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (accountGroupRel != null) {
			clearCache(accountGroupRel);
		}

		return accountGroupRel;
	}

	@Override
	public AccountGroupRel updateImpl(AccountGroupRel accountGroupRel) {
		boolean isNew = accountGroupRel.isNew();

		if (!(accountGroupRel instanceof AccountGroupRelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(accountGroupRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					accountGroupRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in accountGroupRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AccountGroupRel implementation " +
					accountGroupRel.getClass());
		}

		AccountGroupRelModelImpl accountGroupRelModelImpl =
			(AccountGroupRelModelImpl)accountGroupRel;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (accountGroupRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				accountGroupRel.setCreateDate(date);
			}
			else {
				accountGroupRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!accountGroupRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				accountGroupRel.setModifiedDate(date);
			}
			else {
				accountGroupRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(accountGroupRel);
			}
			else {
				accountGroupRel = (AccountGroupRel)session.merge(
					accountGroupRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			AccountGroupRelImpl.class, accountGroupRelModelImpl, false, true);

		cacheUniqueFindersCache(accountGroupRelModelImpl);

		if (isNew) {
			accountGroupRel.setNew(false);
		}

		accountGroupRel.resetOriginalValues();

		return accountGroupRel;
	}

	/**
	 * Returns the account group rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the account group rel
	 * @return the account group rel
	 * @throws NoSuchGroupRelException if a account group rel with the primary key could not be found
	 */
	@Override
	public AccountGroupRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchGroupRelException {

		AccountGroupRel accountGroupRel = fetchByPrimaryKey(primaryKey);

		if (accountGroupRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchGroupRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return accountGroupRel;
	}

	/**
	 * Returns the account group rel with the primary key or throws a <code>NoSuchGroupRelException</code> if it could not be found.
	 *
	 * @param accountGroupRelId the primary key of the account group rel
	 * @return the account group rel
	 * @throws NoSuchGroupRelException if a account group rel with the primary key could not be found
	 */
	@Override
	public AccountGroupRel findByPrimaryKey(long accountGroupRelId)
		throws NoSuchGroupRelException {

		return findByPrimaryKey((Serializable)accountGroupRelId);
	}

	/**
	 * Returns the account group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param accountGroupRelId the primary key of the account group rel
	 * @return the account group rel, or <code>null</code> if a account group rel with the primary key could not be found
	 */
	@Override
	public AccountGroupRel fetchByPrimaryKey(long accountGroupRelId) {
		return fetchByPrimaryKey((Serializable)accountGroupRelId);
	}

	/**
	 * Returns all the account group rels.
	 *
	 * @return the account group rels
	 */
	@Override
	public List<AccountGroupRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @return the range of account group rels
	 */
	@Override
	public List<AccountGroupRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of account group rels
	 */
	@Override
	public List<AccountGroupRel> findAll(
		int start, int end,
		OrderByComparator<AccountGroupRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of account group rels
	 */
	@Override
	public List<AccountGroupRel> findAll(
		int start, int end,
		OrderByComparator<AccountGroupRel> orderByComparator,
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

		List<AccountGroupRel> list = null;

		if (useFinderCache) {
			list = (List<AccountGroupRel>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ACCOUNTGROUPREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ACCOUNTGROUPREL;

				sql = sql.concat(AccountGroupRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AccountGroupRel>)QueryUtil.list(
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
	 * Removes all the account group rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AccountGroupRel accountGroupRel : findAll()) {
			remove(accountGroupRel);
		}
	}

	/**
	 * Returns the number of account group rels.
	 *
	 * @return the number of account group rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ACCOUNTGROUPREL);

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
		return "accountGroupRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ACCOUNTGROUPREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AccountGroupRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the account group rel persistence.
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

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, false);

		_finderPathFetchByA_C_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByA_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"accountGroupId", "classNameId", "classPK"}, true);

		_finderPathCountByA_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"accountGroupId", "classNameId", "classPK"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(AccountGroupRelImpl.class.getName());
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

	private static final String _SQL_SELECT_ACCOUNTGROUPREL =
		"SELECT accountGroupRel FROM AccountGroupRel accountGroupRel";

	private static final String _SQL_SELECT_ACCOUNTGROUPREL_WHERE =
		"SELECT accountGroupRel FROM AccountGroupRel accountGroupRel WHERE ";

	private static final String _SQL_COUNT_ACCOUNTGROUPREL =
		"SELECT COUNT(accountGroupRel) FROM AccountGroupRel accountGroupRel";

	private static final String _SQL_COUNT_ACCOUNTGROUPREL_WHERE =
		"SELECT COUNT(accountGroupRel) FROM AccountGroupRel accountGroupRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "accountGroupRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AccountGroupRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AccountGroupRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AccountGroupRelPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private AccountGroupRelModelArgumentsResolver
		_accountGroupRelModelArgumentsResolver;

}