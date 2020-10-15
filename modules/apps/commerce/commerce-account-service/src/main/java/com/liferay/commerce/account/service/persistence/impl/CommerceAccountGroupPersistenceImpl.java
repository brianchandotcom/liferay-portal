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

import com.liferay.commerce.account.exception.NoSuchAccountGroupException;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.model.CommerceAccountGroupTable;
import com.liferay.commerce.account.model.impl.CommerceAccountGroupImpl;
import com.liferay.commerce.account.model.impl.CommerceAccountGroupModelImpl;
import com.liferay.commerce.account.service.persistence.CommerceAccountGroupPersistence;
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
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
import java.util.HashSet;
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
 * The persistence implementation for the commerce account group service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceAccountGroupPersistenceImpl
	extends BasePersistenceImpl<CommerceAccountGroup>
	implements CommerceAccountGroupPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceAccountGroupUtil</code> to access the commerce account group persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceAccountGroupImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCommerceAccountGroupIds;
	private FinderPath
		_finderPathWithoutPaginationFindByCommerceAccountGroupIds;
	private FinderPath _finderPathCountByCommerceAccountGroupIds;
	private FinderPath _finderPathWithPaginationCountByCommerceAccountGroupIds;

	/**
	 * Returns all the commerce account groups where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce account groups
	 */
	@Override
	public List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long commerceAccountGroupId) {

		return findByCommerceAccountGroupIds(
			commerceAccountGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce account groups where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups
	 */
	@Override
	public List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long commerceAccountGroupId, int start, int end) {

		return findByCommerceAccountGroupIds(
			commerceAccountGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce account groups where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups
	 */
	@Override
	public List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long commerceAccountGroupId, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return findByCommerceAccountGroupIds(
			commerceAccountGroupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce account groups where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account groups
	 */
	@Override
	public List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long commerceAccountGroupId, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceAccountGroupIds;
				finderArgs = new Object[] {commerceAccountGroupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceAccountGroupIds;
			finderArgs = new Object[] {
				commerceAccountGroupId, start, end, orderByComparator
			};
		}

		List<CommerceAccountGroup> list = null;

		if (useFinderCache) {
			list = (List<CommerceAccountGroup>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAccountGroup commerceAccountGroup : list) {
					if (commerceAccountGroupId !=
							commerceAccountGroup.getCommerceAccountGroupId()) {

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

			sb.append(_SQL_SELECT_COMMERCEACCOUNTGROUP_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEACCOUNTGROUPIDS_COMMERCEACCOUNTGROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceAccountGroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceAccountGroupId);

				list = (List<CommerceAccountGroup>)QueryUtil.list(
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
	 * Returns the first commerce account group in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group
	 * @throws NoSuchAccountGroupException if a matching commerce account group could not be found
	 */
	@Override
	public CommerceAccountGroup findByCommerceAccountGroupIds_First(
			long commerceAccountGroupId,
			OrderByComparator<CommerceAccountGroup> orderByComparator)
		throws NoSuchAccountGroupException {

		CommerceAccountGroup commerceAccountGroup =
			fetchByCommerceAccountGroupIds_First(
				commerceAccountGroupId, orderByComparator);

		if (commerceAccountGroup != null) {
			return commerceAccountGroup;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceAccountGroupId=");
		sb.append(commerceAccountGroupId);

		sb.append("}");

		throw new NoSuchAccountGroupException(sb.toString());
	}

	/**
	 * Returns the first commerce account group in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	@Override
	public CommerceAccountGroup fetchByCommerceAccountGroupIds_First(
		long commerceAccountGroupId,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		List<CommerceAccountGroup> list = findByCommerceAccountGroupIds(
			commerceAccountGroupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce account group in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group
	 * @throws NoSuchAccountGroupException if a matching commerce account group could not be found
	 */
	@Override
	public CommerceAccountGroup findByCommerceAccountGroupIds_Last(
			long commerceAccountGroupId,
			OrderByComparator<CommerceAccountGroup> orderByComparator)
		throws NoSuchAccountGroupException {

		CommerceAccountGroup commerceAccountGroup =
			fetchByCommerceAccountGroupIds_Last(
				commerceAccountGroupId, orderByComparator);

		if (commerceAccountGroup != null) {
			return commerceAccountGroup;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceAccountGroupId=");
		sb.append(commerceAccountGroupId);

		sb.append("}");

		throw new NoSuchAccountGroupException(sb.toString());
	}

	/**
	 * Returns the last commerce account group in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	@Override
	public CommerceAccountGroup fetchByCommerceAccountGroupIds_Last(
		long commerceAccountGroupId,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		int count = countByCommerceAccountGroupIds(commerceAccountGroupId);

		if (count == 0) {
			return null;
		}

		List<CommerceAccountGroup> list = findByCommerceAccountGroupIds(
			commerceAccountGroupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the commerce account groups that the user has permission to view where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce account groups that the user has permission to view
	 */
	@Override
	public List<CommerceAccountGroup> filterFindByCommerceAccountGroupIds(
		long commerceAccountGroupId) {

		return filterFindByCommerceAccountGroupIds(
			commerceAccountGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce account groups that the user has permission to view where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups that the user has permission to view
	 */
	@Override
	public List<CommerceAccountGroup> filterFindByCommerceAccountGroupIds(
		long commerceAccountGroupId, int start, int end) {

		return filterFindByCommerceAccountGroupIds(
			commerceAccountGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce account groups that the user has permissions to view where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups that the user has permission to view
	 */
	@Override
	public List<CommerceAccountGroup> filterFindByCommerceAccountGroupIds(
		long commerceAccountGroupId, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByCommerceAccountGroupIds(
				commerceAccountGroupId, start, end, orderByComparator);
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
			sb.append(_FILTER_SQL_SELECT_COMMERCEACCOUNTGROUP_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEACCOUNTGROUP_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(
			_FINDER_COLUMN_COMMERCEACCOUNTGROUPIDS_COMMERCEACCOUNTGROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEACCOUNTGROUP_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(CommerceAccountGroupModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceAccountGroupModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceAccountGroup.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceAccountGroupImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceAccountGroupImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(commerceAccountGroupId);

			return (List<CommerceAccountGroup>)QueryUtil.list(
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
	 * Returns all the commerce account groups that the user has permission to view where commerceAccountGroupId = any &#63;.
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @return the matching commerce account groups that the user has permission to view
	 */
	@Override
	public List<CommerceAccountGroup> filterFindByCommerceAccountGroupIds(
		long[] commerceAccountGroupIds) {

		return filterFindByCommerceAccountGroupIds(
			commerceAccountGroupIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce account groups that the user has permission to view where commerceAccountGroupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups that the user has permission to view
	 */
	@Override
	public List<CommerceAccountGroup> filterFindByCommerceAccountGroupIds(
		long[] commerceAccountGroupIds, int start, int end) {

		return filterFindByCommerceAccountGroupIds(
			commerceAccountGroupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce account groups that the user has permission to view where commerceAccountGroupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups that the user has permission to view
	 */
	@Override
	public List<CommerceAccountGroup> filterFindByCommerceAccountGroupIds(
		long[] commerceAccountGroupIds, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByCommerceAccountGroupIds(
				commerceAccountGroupIds, start, end, orderByComparator);
		}

		if (commerceAccountGroupIds == null) {
			commerceAccountGroupIds = new long[0];
		}
		else if (commerceAccountGroupIds.length > 1) {
			commerceAccountGroupIds = ArrayUtil.sortedUnique(
				commerceAccountGroupIds);
		}

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEACCOUNTGROUP_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEACCOUNTGROUP_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (commerceAccountGroupIds.length > 0) {
			sb.append("(");

			sb.append(
				_FINDER_COLUMN_COMMERCEACCOUNTGROUPIDS_COMMERCEACCOUNTGROUPID_7);

			sb.append(StringUtil.merge(commerceAccountGroupIds));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEACCOUNTGROUP_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(CommerceAccountGroupModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceAccountGroupModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceAccountGroup.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceAccountGroupImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceAccountGroupImpl.class);
			}

			return (List<CommerceAccountGroup>)QueryUtil.list(
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
	 * Returns all the commerce account groups where commerceAccountGroupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @return the matching commerce account groups
	 */
	@Override
	public List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long[] commerceAccountGroupIds) {

		return findByCommerceAccountGroupIds(
			commerceAccountGroupIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce account groups where commerceAccountGroupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups
	 */
	@Override
	public List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long[] commerceAccountGroupIds, int start, int end) {

		return findByCommerceAccountGroupIds(
			commerceAccountGroupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce account groups where commerceAccountGroupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups
	 */
	@Override
	public List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long[] commerceAccountGroupIds, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return findByCommerceAccountGroupIds(
			commerceAccountGroupIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce account groups where commerceAccountGroupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account groups
	 */
	@Override
	public List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long[] commerceAccountGroupIds, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator,
		boolean useFinderCache) {

		if (commerceAccountGroupIds == null) {
			commerceAccountGroupIds = new long[0];
		}
		else if (commerceAccountGroupIds.length > 1) {
			commerceAccountGroupIds = ArrayUtil.sortedUnique(
				commerceAccountGroupIds);
		}

		if (commerceAccountGroupIds.length == 1) {
			return findByCommerceAccountGroupIds(
				commerceAccountGroupIds[0], start, end, orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					StringUtil.merge(commerceAccountGroupIds)
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				StringUtil.merge(commerceAccountGroupIds), start, end,
				orderByComparator
			};
		}

		List<CommerceAccountGroup> list = null;

		if (useFinderCache) {
			list = (List<CommerceAccountGroup>)finderCache.getResult(
				_finderPathWithPaginationFindByCommerceAccountGroupIds,
				finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAccountGroup commerceAccountGroup : list) {
					if (!ArrayUtil.contains(
							commerceAccountGroupIds,
							commerceAccountGroup.getCommerceAccountGroupId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_COMMERCEACCOUNTGROUP_WHERE);

			if (commerceAccountGroupIds.length > 0) {
				sb.append("(");

				sb.append(
					_FINDER_COLUMN_COMMERCEACCOUNTGROUPIDS_COMMERCEACCOUNTGROUPID_7);

				sb.append(StringUtil.merge(commerceAccountGroupIds));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceAccountGroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceAccountGroup>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByCommerceAccountGroupIds,
						finderArgs, list);
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
	 * Removes all the commerce account groups where commerceAccountGroupId = &#63; from the database.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 */
	@Override
	public void removeByCommerceAccountGroupIds(long commerceAccountGroupId) {
		for (CommerceAccountGroup commerceAccountGroup :
				findByCommerceAccountGroupIds(
					commerceAccountGroupId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceAccountGroup);
		}
	}

	/**
	 * Returns the number of commerce account groups where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce account groups
	 */
	@Override
	public int countByCommerceAccountGroupIds(long commerceAccountGroupId) {
		FinderPath finderPath = _finderPathCountByCommerceAccountGroupIds;

		Object[] finderArgs = new Object[] {commerceAccountGroupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEACCOUNTGROUP_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEACCOUNTGROUPIDS_COMMERCEACCOUNTGROUPID_2);

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

	/**
	 * Returns the number of commerce account groups where commerceAccountGroupId = any &#63;.
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @return the number of matching commerce account groups
	 */
	@Override
	public int countByCommerceAccountGroupIds(long[] commerceAccountGroupIds) {
		if (commerceAccountGroupIds == null) {
			commerceAccountGroupIds = new long[0];
		}
		else if (commerceAccountGroupIds.length > 1) {
			commerceAccountGroupIds = ArrayUtil.sortedUnique(
				commerceAccountGroupIds);
		}

		Object[] finderArgs = new Object[] {
			StringUtil.merge(commerceAccountGroupIds)
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByCommerceAccountGroupIds, finderArgs,
			this);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_COMMERCEACCOUNTGROUP_WHERE);

			if (commerceAccountGroupIds.length > 0) {
				sb.append("(");

				sb.append(
					_FINDER_COLUMN_COMMERCEACCOUNTGROUPIDS_COMMERCEACCOUNTGROUPID_7);

				sb.append(StringUtil.merge(commerceAccountGroupIds));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByCommerceAccountGroupIds,
					finderArgs, count);
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
	 * Returns the number of commerce account groups that the user has permission to view where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce account groups that the user has permission to view
	 */
	@Override
	public int filterCountByCommerceAccountGroupIds(
		long commerceAccountGroupId) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByCommerceAccountGroupIds(commerceAccountGroupId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_COMMERCEACCOUNTGROUP_WHERE);

		sb.append(
			_FINDER_COLUMN_COMMERCEACCOUNTGROUPIDS_COMMERCEACCOUNTGROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceAccountGroup.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(commerceAccountGroupId);

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

	/**
	 * Returns the number of commerce account groups that the user has permission to view where commerceAccountGroupId = any &#63;.
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @return the number of matching commerce account groups that the user has permission to view
	 */
	@Override
	public int filterCountByCommerceAccountGroupIds(
		long[] commerceAccountGroupIds) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByCommerceAccountGroupIds(commerceAccountGroupIds);
		}

		if (commerceAccountGroupIds == null) {
			commerceAccountGroupIds = new long[0];
		}
		else if (commerceAccountGroupIds.length > 1) {
			commerceAccountGroupIds = ArrayUtil.sortedUnique(
				commerceAccountGroupIds);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_COMMERCEACCOUNTGROUP_WHERE);

		if (commerceAccountGroupIds.length > 0) {
			sb.append("(");

			sb.append(
				_FINDER_COLUMN_COMMERCEACCOUNTGROUPIDS_COMMERCEACCOUNTGROUPID_7);

			sb.append(StringUtil.merge(commerceAccountGroupIds));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceAccountGroup.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

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

	private static final String
		_FINDER_COLUMN_COMMERCEACCOUNTGROUPIDS_COMMERCEACCOUNTGROUPID_2 =
			"commerceAccountGroup.commerceAccountGroupId = ?";

	private static final String
		_FINDER_COLUMN_COMMERCEACCOUNTGROUPIDS_COMMERCEACCOUNTGROUPID_7 =
			"commerceAccountGroup.commerceAccountGroupId IN (";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the commerce account groups where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce account groups
	 */
	@Override
	public List<CommerceAccountGroup> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce account groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups
	 */
	@Override
	public List<CommerceAccountGroup> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce account groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups
	 */
	@Override
	public List<CommerceAccountGroup> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce account groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account groups
	 */
	@Override
	public List<CommerceAccountGroup> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator,
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

		List<CommerceAccountGroup> list = null;

		if (useFinderCache) {
			list = (List<CommerceAccountGroup>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAccountGroup commerceAccountGroup : list) {
					if (companyId != commerceAccountGroup.getCompanyId()) {
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

			sb.append(_SQL_SELECT_COMMERCEACCOUNTGROUP_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceAccountGroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<CommerceAccountGroup>)QueryUtil.list(
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
	 * Returns the first commerce account group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group
	 * @throws NoSuchAccountGroupException if a matching commerce account group could not be found
	 */
	@Override
	public CommerceAccountGroup findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceAccountGroup> orderByComparator)
		throws NoSuchAccountGroupException {

		CommerceAccountGroup commerceAccountGroup = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (commerceAccountGroup != null) {
			return commerceAccountGroup;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchAccountGroupException(sb.toString());
	}

	/**
	 * Returns the first commerce account group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	@Override
	public CommerceAccountGroup fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		List<CommerceAccountGroup> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce account group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group
	 * @throws NoSuchAccountGroupException if a matching commerce account group could not be found
	 */
	@Override
	public CommerceAccountGroup findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommerceAccountGroup> orderByComparator)
		throws NoSuchAccountGroupException {

		CommerceAccountGroup commerceAccountGroup = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (commerceAccountGroup != null) {
			return commerceAccountGroup;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchAccountGroupException(sb.toString());
	}

	/**
	 * Returns the last commerce account group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	@Override
	public CommerceAccountGroup fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceAccountGroup> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce account groups before and after the current commerce account group in the ordered set where companyId = &#63;.
	 *
	 * @param commerceAccountGroupId the primary key of the current commerce account group
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group
	 * @throws NoSuchAccountGroupException if a commerce account group with the primary key could not be found
	 */
	@Override
	public CommerceAccountGroup[] findByCompanyId_PrevAndNext(
			long commerceAccountGroupId, long companyId,
			OrderByComparator<CommerceAccountGroup> orderByComparator)
		throws NoSuchAccountGroupException {

		CommerceAccountGroup commerceAccountGroup = findByPrimaryKey(
			commerceAccountGroupId);

		Session session = null;

		try {
			session = openSession();

			CommerceAccountGroup[] array = new CommerceAccountGroupImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, commerceAccountGroup, companyId, orderByComparator,
				true);

			array[1] = commerceAccountGroup;

			array[2] = getByCompanyId_PrevAndNext(
				session, commerceAccountGroup, companyId, orderByComparator,
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

	protected CommerceAccountGroup getByCompanyId_PrevAndNext(
		Session session, CommerceAccountGroup commerceAccountGroup,
		long companyId,
		OrderByComparator<CommerceAccountGroup> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEACCOUNTGROUP_WHERE);

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
			sb.append(CommerceAccountGroupModelImpl.ORDER_BY_JPQL);
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
						commerceAccountGroup)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceAccountGroup> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce account groups that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce account groups that the user has permission to view
	 */
	@Override
	public List<CommerceAccountGroup> filterFindByCompanyId(long companyId) {
		return filterFindByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce account groups that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups that the user has permission to view
	 */
	@Override
	public List<CommerceAccountGroup> filterFindByCompanyId(
		long companyId, int start, int end) {

		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce account groups that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups that the user has permission to view
	 */
	@Override
	public List<CommerceAccountGroup> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_COMMERCEACCOUNTGROUP_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEACCOUNTGROUP_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEACCOUNTGROUP_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(CommerceAccountGroupModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceAccountGroupModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceAccountGroup.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceAccountGroupImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceAccountGroupImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			return (List<CommerceAccountGroup>)QueryUtil.list(
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
	 * Returns the commerce account groups before and after the current commerce account group in the ordered set of commerce account groups that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceAccountGroupId the primary key of the current commerce account group
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group
	 * @throws NoSuchAccountGroupException if a commerce account group with the primary key could not be found
	 */
	@Override
	public CommerceAccountGroup[] filterFindByCompanyId_PrevAndNext(
			long commerceAccountGroupId, long companyId,
			OrderByComparator<CommerceAccountGroup> orderByComparator)
		throws NoSuchAccountGroupException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId_PrevAndNext(
				commerceAccountGroupId, companyId, orderByComparator);
		}

		CommerceAccountGroup commerceAccountGroup = findByPrimaryKey(
			commerceAccountGroupId);

		Session session = null;

		try {
			session = openSession();

			CommerceAccountGroup[] array = new CommerceAccountGroupImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(
				session, commerceAccountGroup, companyId, orderByComparator,
				true);

			array[1] = commerceAccountGroup;

			array[2] = filterGetByCompanyId_PrevAndNext(
				session, commerceAccountGroup, companyId, orderByComparator,
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

	protected CommerceAccountGroup filterGetByCompanyId_PrevAndNext(
		Session session, CommerceAccountGroup commerceAccountGroup,
		long companyId,
		OrderByComparator<CommerceAccountGroup> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_COMMERCEACCOUNTGROUP_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEACCOUNTGROUP_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEACCOUNTGROUP_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(CommerceAccountGroupModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceAccountGroupModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceAccountGroup.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, CommerceAccountGroupImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, CommerceAccountGroupImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceAccountGroup)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceAccountGroup> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce account groups where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceAccountGroup commerceAccountGroup :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceAccountGroup);
		}
	}

	/**
	 * Returns the number of commerce account groups where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce account groups
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEACCOUNTGROUP_WHERE);

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
	 * Returns the number of commerce account groups that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce account groups that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByCompanyId(companyId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_COMMERCEACCOUNTGROUP_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceAccountGroup.class.getName(),
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
		"commerceAccountGroup.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByC_T;
	private FinderPath _finderPathWithoutPaginationFindByC_T;
	private FinderPath _finderPathCountByC_T;

	/**
	 * Returns all the commerce account groups where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce account groups
	 */
	@Override
	public List<CommerceAccountGroup> findByC_T(long companyId, int type) {
		return findByC_T(
			companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce account groups where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups
	 */
	@Override
	public List<CommerceAccountGroup> findByC_T(
		long companyId, int type, int start, int end) {

		return findByC_T(companyId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce account groups where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups
	 */
	@Override
	public List<CommerceAccountGroup> findByC_T(
		long companyId, int type, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return findByC_T(companyId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce account groups where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account groups
	 */
	@Override
	public List<CommerceAccountGroup> findByC_T(
		long companyId, int type, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_T;
				finderArgs = new Object[] {companyId, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_T;
			finderArgs = new Object[] {
				companyId, type, start, end, orderByComparator
			};
		}

		List<CommerceAccountGroup> list = null;

		if (useFinderCache) {
			list = (List<CommerceAccountGroup>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAccountGroup commerceAccountGroup : list) {
					if ((companyId != commerceAccountGroup.getCompanyId()) ||
						(type != commerceAccountGroup.getType())) {

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

			sb.append(_SQL_SELECT_COMMERCEACCOUNTGROUP_WHERE);

			sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceAccountGroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(type);

				list = (List<CommerceAccountGroup>)QueryUtil.list(
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
	 * Returns the first commerce account group in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group
	 * @throws NoSuchAccountGroupException if a matching commerce account group could not be found
	 */
	@Override
	public CommerceAccountGroup findByC_T_First(
			long companyId, int type,
			OrderByComparator<CommerceAccountGroup> orderByComparator)
		throws NoSuchAccountGroupException {

		CommerceAccountGroup commerceAccountGroup = fetchByC_T_First(
			companyId, type, orderByComparator);

		if (commerceAccountGroup != null) {
			return commerceAccountGroup;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchAccountGroupException(sb.toString());
	}

	/**
	 * Returns the first commerce account group in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	@Override
	public CommerceAccountGroup fetchByC_T_First(
		long companyId, int type,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		List<CommerceAccountGroup> list = findByC_T(
			companyId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce account group in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group
	 * @throws NoSuchAccountGroupException if a matching commerce account group could not be found
	 */
	@Override
	public CommerceAccountGroup findByC_T_Last(
			long companyId, int type,
			OrderByComparator<CommerceAccountGroup> orderByComparator)
		throws NoSuchAccountGroupException {

		CommerceAccountGroup commerceAccountGroup = fetchByC_T_Last(
			companyId, type, orderByComparator);

		if (commerceAccountGroup != null) {
			return commerceAccountGroup;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchAccountGroupException(sb.toString());
	}

	/**
	 * Returns the last commerce account group in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	@Override
	public CommerceAccountGroup fetchByC_T_Last(
		long companyId, int type,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		int count = countByC_T(companyId, type);

		if (count == 0) {
			return null;
		}

		List<CommerceAccountGroup> list = findByC_T(
			companyId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce account groups before and after the current commerce account group in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param commerceAccountGroupId the primary key of the current commerce account group
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group
	 * @throws NoSuchAccountGroupException if a commerce account group with the primary key could not be found
	 */
	@Override
	public CommerceAccountGroup[] findByC_T_PrevAndNext(
			long commerceAccountGroupId, long companyId, int type,
			OrderByComparator<CommerceAccountGroup> orderByComparator)
		throws NoSuchAccountGroupException {

		CommerceAccountGroup commerceAccountGroup = findByPrimaryKey(
			commerceAccountGroupId);

		Session session = null;

		try {
			session = openSession();

			CommerceAccountGroup[] array = new CommerceAccountGroupImpl[3];

			array[0] = getByC_T_PrevAndNext(
				session, commerceAccountGroup, companyId, type,
				orderByComparator, true);

			array[1] = commerceAccountGroup;

			array[2] = getByC_T_PrevAndNext(
				session, commerceAccountGroup, companyId, type,
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

	protected CommerceAccountGroup getByC_T_PrevAndNext(
		Session session, CommerceAccountGroup commerceAccountGroup,
		long companyId, int type,
		OrderByComparator<CommerceAccountGroup> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEACCOUNTGROUP_WHERE);

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_T_TYPE_2);

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
			sb.append(CommerceAccountGroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceAccountGroup)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceAccountGroup> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce account groups that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce account groups that the user has permission to view
	 */
	@Override
	public List<CommerceAccountGroup> filterFindByC_T(
		long companyId, int type) {

		return filterFindByC_T(
			companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce account groups that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups that the user has permission to view
	 */
	@Override
	public List<CommerceAccountGroup> filterFindByC_T(
		long companyId, int type, int start, int end) {

		return filterFindByC_T(companyId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce account groups that the user has permissions to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups that the user has permission to view
	 */
	@Override
	public List<CommerceAccountGroup> filterFindByC_T(
		long companyId, int type, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_T(companyId, type, start, end, orderByComparator);
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
			sb.append(_FILTER_SQL_SELECT_COMMERCEACCOUNTGROUP_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEACCOUNTGROUP_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEACCOUNTGROUP_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(CommerceAccountGroupModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceAccountGroupModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceAccountGroup.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, CommerceAccountGroupImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, CommerceAccountGroupImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(type);

			return (List<CommerceAccountGroup>)QueryUtil.list(
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
	 * Returns the commerce account groups before and after the current commerce account group in the ordered set of commerce account groups that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param commerceAccountGroupId the primary key of the current commerce account group
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group
	 * @throws NoSuchAccountGroupException if a commerce account group with the primary key could not be found
	 */
	@Override
	public CommerceAccountGroup[] filterFindByC_T_PrevAndNext(
			long commerceAccountGroupId, long companyId, int type,
			OrderByComparator<CommerceAccountGroup> orderByComparator)
		throws NoSuchAccountGroupException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_T_PrevAndNext(
				commerceAccountGroupId, companyId, type, orderByComparator);
		}

		CommerceAccountGroup commerceAccountGroup = findByPrimaryKey(
			commerceAccountGroupId);

		Session session = null;

		try {
			session = openSession();

			CommerceAccountGroup[] array = new CommerceAccountGroupImpl[3];

			array[0] = filterGetByC_T_PrevAndNext(
				session, commerceAccountGroup, companyId, type,
				orderByComparator, true);

			array[1] = commerceAccountGroup;

			array[2] = filterGetByC_T_PrevAndNext(
				session, commerceAccountGroup, companyId, type,
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

	protected CommerceAccountGroup filterGetByC_T_PrevAndNext(
		Session session, CommerceAccountGroup commerceAccountGroup,
		long companyId, int type,
		OrderByComparator<CommerceAccountGroup> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_COMMERCEACCOUNTGROUP_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEACCOUNTGROUP_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEACCOUNTGROUP_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(CommerceAccountGroupModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceAccountGroupModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceAccountGroup.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, CommerceAccountGroupImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, CommerceAccountGroupImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceAccountGroup)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceAccountGroup> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce account groups where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	@Override
	public void removeByC_T(long companyId, int type) {
		for (CommerceAccountGroup commerceAccountGroup :
				findByC_T(
					companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceAccountGroup);
		}
	}

	/**
	 * Returns the number of commerce account groups where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce account groups
	 */
	@Override
	public int countByC_T(long companyId, int type) {
		FinderPath finderPath = _finderPathCountByC_T;

		Object[] finderArgs = new Object[] {companyId, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEACCOUNTGROUP_WHERE);

			sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

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
	 * Returns the number of commerce account groups that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce account groups that the user has permission to view
	 */
	@Override
	public int filterCountByC_T(long companyId, int type) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_T(companyId, type);
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_COMMERCEACCOUNTGROUP_WHERE);

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_T_TYPE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceAccountGroup.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

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

	private static final String _FINDER_COLUMN_C_T_COMPANYID_2 =
		"commerceAccountGroup.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_T_TYPE_2 =
		"commerceAccountGroup.type = ?";

	private static final String _FINDER_COLUMN_C_T_TYPE_2_SQL =
		"commerceAccountGroup.type_ = ?";

	private FinderPath _finderPathFetchByC_ERC;
	private FinderPath _finderPathCountByC_ERC;

	/**
	 * Returns the commerce account group where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchAccountGroupException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce account group
	 * @throws NoSuchAccountGroupException if a matching commerce account group could not be found
	 */
	@Override
	public CommerceAccountGroup findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchAccountGroupException {

		CommerceAccountGroup commerceAccountGroup = fetchByC_ERC(
			companyId, externalReferenceCode);

		if (commerceAccountGroup == null) {
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

			throw new NoSuchAccountGroupException(sb.toString());
		}

		return commerceAccountGroup;
	}

	/**
	 * Returns the commerce account group where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	@Override
	public CommerceAccountGroup fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return fetchByC_ERC(companyId, externalReferenceCode, true);
	}

	/**
	 * Returns the commerce account group where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	@Override
	public CommerceAccountGroup fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, externalReferenceCode};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_ERC, finderArgs, this);
		}

		if (result instanceof CommerceAccountGroup) {
			CommerceAccountGroup commerceAccountGroup =
				(CommerceAccountGroup)result;

			if ((companyId != commerceAccountGroup.getCompanyId()) ||
				!Objects.equals(
					externalReferenceCode,
					commerceAccountGroup.getExternalReferenceCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCEACCOUNTGROUP_WHERE);

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

				List<CommerceAccountGroup> list = query.list();

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
								"CommerceAccountGroupPersistenceImpl.fetchByC_ERC(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CommerceAccountGroup commerceAccountGroup = list.get(0);

					result = commerceAccountGroup;

					cacheResult(commerceAccountGroup);
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
			return (CommerceAccountGroup)result;
		}
	}

	/**
	 * Removes the commerce account group where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce account group that was removed
	 */
	@Override
	public CommerceAccountGroup removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchAccountGroupException {

		CommerceAccountGroup commerceAccountGroup = findByC_ERC(
			companyId, externalReferenceCode);

		return remove(commerceAccountGroup);
	}

	/**
	 * Returns the number of commerce account groups where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce account groups
	 */
	@Override
	public int countByC_ERC(long companyId, String externalReferenceCode) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		FinderPath finderPath = _finderPathCountByC_ERC;

		Object[] finderArgs = new Object[] {companyId, externalReferenceCode};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEACCOUNTGROUP_WHERE);

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
		"commerceAccountGroup.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2 =
		"commerceAccountGroup.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3 =
		"(commerceAccountGroup.externalReferenceCode IS NULL OR commerceAccountGroup.externalReferenceCode = '')";

	public CommerceAccountGroupPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");
		dbColumnNames.put("system", "system_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceAccountGroup.class);

		setModelImplClass(CommerceAccountGroupImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceAccountGroupTable.INSTANCE);
	}

	/**
	 * Caches the commerce account group in the entity cache if it is enabled.
	 *
	 * @param commerceAccountGroup the commerce account group
	 */
	@Override
	public void cacheResult(CommerceAccountGroup commerceAccountGroup) {
		entityCache.putResult(
			CommerceAccountGroupImpl.class,
			commerceAccountGroup.getPrimaryKey(), commerceAccountGroup);

		finderCache.putResult(
			_finderPathFetchByC_ERC,
			new Object[] {
				commerceAccountGroup.getCompanyId(),
				commerceAccountGroup.getExternalReferenceCode()
			},
			commerceAccountGroup);
	}

	/**
	 * Caches the commerce account groups in the entity cache if it is enabled.
	 *
	 * @param commerceAccountGroups the commerce account groups
	 */
	@Override
	public void cacheResult(List<CommerceAccountGroup> commerceAccountGroups) {
		for (CommerceAccountGroup commerceAccountGroup :
				commerceAccountGroups) {

			if (entityCache.getResult(
					CommerceAccountGroupImpl.class,
					commerceAccountGroup.getPrimaryKey()) == null) {

				cacheResult(commerceAccountGroup);
			}
		}
	}

	/**
	 * Clears the cache for all commerce account groups.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceAccountGroupImpl.class);

		finderCache.clearCache(CommerceAccountGroupImpl.class);
	}

	/**
	 * Clears the cache for the commerce account group.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceAccountGroup commerceAccountGroup) {
		entityCache.removeResult(
			CommerceAccountGroupImpl.class, commerceAccountGroup);
	}

	@Override
	public void clearCache(List<CommerceAccountGroup> commerceAccountGroups) {
		for (CommerceAccountGroup commerceAccountGroup :
				commerceAccountGroups) {

			entityCache.removeResult(
				CommerceAccountGroupImpl.class, commerceAccountGroup);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceAccountGroupImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceAccountGroupImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceAccountGroupModelImpl commerceAccountGroupModelImpl) {

		Object[] args = new Object[] {
			commerceAccountGroupModelImpl.getCompanyId(),
			commerceAccountGroupModelImpl.getExternalReferenceCode()
		};

		finderCache.putResult(
			_finderPathCountByC_ERC, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_ERC, args, commerceAccountGroupModelImpl,
			false);
	}

	/**
	 * Creates a new commerce account group with the primary key. Does not add the commerce account group to the database.
	 *
	 * @param commerceAccountGroupId the primary key for the new commerce account group
	 * @return the new commerce account group
	 */
	@Override
	public CommerceAccountGroup create(long commerceAccountGroupId) {
		CommerceAccountGroup commerceAccountGroup =
			new CommerceAccountGroupImpl();

		commerceAccountGroup.setNew(true);
		commerceAccountGroup.setPrimaryKey(commerceAccountGroupId);

		commerceAccountGroup.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commerceAccountGroup;
	}

	/**
	 * Removes the commerce account group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAccountGroupId the primary key of the commerce account group
	 * @return the commerce account group that was removed
	 * @throws NoSuchAccountGroupException if a commerce account group with the primary key could not be found
	 */
	@Override
	public CommerceAccountGroup remove(long commerceAccountGroupId)
		throws NoSuchAccountGroupException {

		return remove((Serializable)commerceAccountGroupId);
	}

	/**
	 * Removes the commerce account group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce account group
	 * @return the commerce account group that was removed
	 * @throws NoSuchAccountGroupException if a commerce account group with the primary key could not be found
	 */
	@Override
	public CommerceAccountGroup remove(Serializable primaryKey)
		throws NoSuchAccountGroupException {

		Session session = null;

		try {
			session = openSession();

			CommerceAccountGroup commerceAccountGroup =
				(CommerceAccountGroup)session.get(
					CommerceAccountGroupImpl.class, primaryKey);

			if (commerceAccountGroup == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAccountGroupException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceAccountGroup);
		}
		catch (NoSuchAccountGroupException noSuchEntityException) {
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
	protected CommerceAccountGroup removeImpl(
		CommerceAccountGroup commerceAccountGroup) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceAccountGroup)) {
				commerceAccountGroup = (CommerceAccountGroup)session.get(
					CommerceAccountGroupImpl.class,
					commerceAccountGroup.getPrimaryKeyObj());
			}

			if (commerceAccountGroup != null) {
				session.delete(commerceAccountGroup);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceAccountGroup != null) {
			clearCache(commerceAccountGroup);
		}

		return commerceAccountGroup;
	}

	@Override
	public CommerceAccountGroup updateImpl(
		CommerceAccountGroup commerceAccountGroup) {

		boolean isNew = commerceAccountGroup.isNew();

		if (!(commerceAccountGroup instanceof CommerceAccountGroupModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceAccountGroup.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceAccountGroup);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceAccountGroup proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceAccountGroup implementation " +
					commerceAccountGroup.getClass());
		}

		CommerceAccountGroupModelImpl commerceAccountGroupModelImpl =
			(CommerceAccountGroupModelImpl)commerceAccountGroup;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceAccountGroup.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceAccountGroup.setCreateDate(now);
			}
			else {
				commerceAccountGroup.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!commerceAccountGroupModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceAccountGroup.setModifiedDate(now);
			}
			else {
				commerceAccountGroup.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceAccountGroup);
			}
			else {
				commerceAccountGroup = (CommerceAccountGroup)session.merge(
					commerceAccountGroup);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceAccountGroupImpl.class, commerceAccountGroupModelImpl,
			false, true);

		cacheUniqueFindersCache(commerceAccountGroupModelImpl);

		if (isNew) {
			commerceAccountGroup.setNew(false);
		}

		commerceAccountGroup.resetOriginalValues();

		return commerceAccountGroup;
	}

	/**
	 * Returns the commerce account group with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce account group
	 * @return the commerce account group
	 * @throws NoSuchAccountGroupException if a commerce account group with the primary key could not be found
	 */
	@Override
	public CommerceAccountGroup findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAccountGroupException {

		CommerceAccountGroup commerceAccountGroup = fetchByPrimaryKey(
			primaryKey);

		if (commerceAccountGroup == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAccountGroupException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceAccountGroup;
	}

	/**
	 * Returns the commerce account group with the primary key or throws a <code>NoSuchAccountGroupException</code> if it could not be found.
	 *
	 * @param commerceAccountGroupId the primary key of the commerce account group
	 * @return the commerce account group
	 * @throws NoSuchAccountGroupException if a commerce account group with the primary key could not be found
	 */
	@Override
	public CommerceAccountGroup findByPrimaryKey(long commerceAccountGroupId)
		throws NoSuchAccountGroupException {

		return findByPrimaryKey((Serializable)commerceAccountGroupId);
	}

	/**
	 * Returns the commerce account group with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceAccountGroupId the primary key of the commerce account group
	 * @return the commerce account group, or <code>null</code> if a commerce account group with the primary key could not be found
	 */
	@Override
	public CommerceAccountGroup fetchByPrimaryKey(long commerceAccountGroupId) {
		return fetchByPrimaryKey((Serializable)commerceAccountGroupId);
	}

	/**
	 * Returns all the commerce account groups.
	 *
	 * @return the commerce account groups
	 */
	@Override
	public List<CommerceAccountGroup> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce account groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of commerce account groups
	 */
	@Override
	public List<CommerceAccountGroup> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce account groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce account groups
	 */
	@Override
	public List<CommerceAccountGroup> findAll(
		int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce account groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce account groups
	 */
	@Override
	public List<CommerceAccountGroup> findAll(
		int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator,
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

		List<CommerceAccountGroup> list = null;

		if (useFinderCache) {
			list = (List<CommerceAccountGroup>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEACCOUNTGROUP);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEACCOUNTGROUP;

				sql = sql.concat(CommerceAccountGroupModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceAccountGroup>)QueryUtil.list(
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
	 * Removes all the commerce account groups from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceAccountGroup commerceAccountGroup : findAll()) {
			remove(commerceAccountGroup);
		}
	}

	/**
	 * Returns the number of commerce account groups.
	 *
	 * @return the number of commerce account groups
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCEACCOUNTGROUP);

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
		return "commerceAccountGroupId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEACCOUNTGROUP;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceAccountGroupModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce account group persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceAccountGroupPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new CommerceAccountGroupModelArgumentsResolver(),
			MapUtil.singletonDictionary(
				"model.class.name", CommerceAccountGroup.class.getName()));

		_finderPathWithPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByCommerceAccountGroupIds =
			_createFinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommerceAccountGroupIds",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"commerceAccountGroupId"}, true);

		_finderPathWithoutPaginationFindByCommerceAccountGroupIds =
			_createFinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceAccountGroupIds",
				new String[] {Long.class.getName()},
				new String[] {"commerceAccountGroupId"}, true);

		_finderPathCountByCommerceAccountGroupIds = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceAccountGroupIds",
			new String[] {Long.class.getName()},
			new String[] {"commerceAccountGroupId"}, false);

		_finderPathWithPaginationCountByCommerceAccountGroupIds =
			_createFinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"countByCommerceAccountGroupIds",
				new String[] {Long.class.getName()},
				new String[] {"commerceAccountGroupId"}, false);

		_finderPathWithPaginationFindByCompanyId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_finderPathWithPaginationFindByC_T = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "type_"}, true);

		_finderPathWithoutPaginationFindByC_T = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "type_"}, true);

		_finderPathCountByC_T = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "type_"}, false);

		_finderPathFetchByC_ERC = _createFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "externalReferenceCode"}, true);

		_finderPathCountByC_ERC = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "externalReferenceCode"}, false);
	}

	public void destroy() {
		entityCache.removeCache(CommerceAccountGroupImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();

		for (ServiceRegistration<FinderPath> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEACCOUNTGROUP =
		"SELECT commerceAccountGroup FROM CommerceAccountGroup commerceAccountGroup";

	private static final String _SQL_SELECT_COMMERCEACCOUNTGROUP_WHERE =
		"SELECT commerceAccountGroup FROM CommerceAccountGroup commerceAccountGroup WHERE ";

	private static final String _SQL_COUNT_COMMERCEACCOUNTGROUP =
		"SELECT COUNT(commerceAccountGroup) FROM CommerceAccountGroup commerceAccountGroup";

	private static final String _SQL_COUNT_COMMERCEACCOUNTGROUP_WHERE =
		"SELECT COUNT(commerceAccountGroup) FROM CommerceAccountGroup commerceAccountGroup WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"commerceAccountGroup.commerceAccountGroupId";

	private static final String _FILTER_SQL_SELECT_COMMERCEACCOUNTGROUP_WHERE =
		"SELECT DISTINCT {commerceAccountGroup.*} FROM CommerceAccountGroup commerceAccountGroup WHERE ";

	private static final String
		_FILTER_SQL_SELECT_COMMERCEACCOUNTGROUP_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {CommerceAccountGroup.*} FROM (SELECT DISTINCT commerceAccountGroup.commerceAccountGroupId FROM CommerceAccountGroup commerceAccountGroup WHERE ";

	private static final String
		_FILTER_SQL_SELECT_COMMERCEACCOUNTGROUP_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN CommerceAccountGroup ON TEMP_TABLE.commerceAccountGroupId = CommerceAccountGroup.commerceAccountGroupId";

	private static final String _FILTER_SQL_COUNT_COMMERCEACCOUNTGROUP_WHERE =
		"SELECT COUNT(DISTINCT commerceAccountGroup.commerceAccountGroupId) AS COUNT_VALUE FROM CommerceAccountGroup commerceAccountGroup WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "commerceAccountGroup";

	private static final String _FILTER_ENTITY_TABLE = "CommerceAccountGroup";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceAccountGroup.";

	private static final String _ORDER_BY_ENTITY_TABLE =
		"CommerceAccountGroup.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceAccountGroup exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceAccountGroup exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAccountGroupPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type", "system"});

	private FinderPath _createFinderPath(
		String cacheName, String methodName, String[] params,
		String[] columnNames, boolean baseModelResult) {

		FinderPath finderPath = new FinderPath(
			cacheName, methodName, params, columnNames, baseModelResult);

		if (!cacheName.equals(FINDER_CLASS_NAME_LIST_WITH_PAGINATION)) {
			_serviceRegistrations.add(
				_bundleContext.registerService(
					FinderPath.class, finderPath,
					MapUtil.singletonDictionary("cache.name", cacheName)));
		}

		return finderPath;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;
	private Set<ServiceRegistration<FinderPath>> _serviceRegistrations =
		new HashSet<>();

	private static class CommerceAccountGroupModelArgumentsResolver
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

			CommerceAccountGroupModelImpl commerceAccountGroupModelImpl =
				(CommerceAccountGroupModelImpl)baseModel;

			long columnBitmask =
				commerceAccountGroupModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					commerceAccountGroupModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						commerceAccountGroupModelImpl.getColumnBitmask(
							columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					commerceAccountGroupModelImpl, columnNames, original);
			}

			return null;
		}

		private Object[] _getValue(
			CommerceAccountGroupModelImpl commerceAccountGroupModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						commerceAccountGroupModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = commerceAccountGroupModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}