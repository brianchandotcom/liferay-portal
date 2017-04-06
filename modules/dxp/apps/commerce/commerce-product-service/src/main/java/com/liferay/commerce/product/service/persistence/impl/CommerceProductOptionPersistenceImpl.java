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

package com.liferay.commerce.product.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.exception.NoSuchProductOptionException;
import com.liferay.commerce.product.model.CommerceProductOption;
import com.liferay.commerce.product.model.impl.CommerceProductOptionImpl;
import com.liferay.commerce.product.model.impl.CommerceProductOptionModelImpl;
import com.liferay.commerce.product.service.persistence.CommerceProductOptionPersistence;

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
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the commerce product option service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductOptionPersistence
 * @see com.liferay.commerce.product.service.persistence.CommerceProductOptionUtil
 * @generated
 */
@ProviderType
public class CommerceProductOptionPersistenceImpl extends BasePersistenceImpl<CommerceProductOption>
	implements CommerceProductOptionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceProductOptionUtil} to access the commerce product option persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceProductOptionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceProductOptionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductOptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceProductOptionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductOptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceProductOptionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceProductOptionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductOptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceProductOptionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductOptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceProductOptionModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceProductOptionModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceProductOptionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce product options where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce product options
	 */
	@Override
	public List<CommerceProductOption> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product options where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product options
	 * @param end the upper bound of the range of commerce product options (not inclusive)
	 * @return the range of matching commerce product options
	 */
	@Override
	public List<CommerceProductOption> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product options where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product options
	 * @param end the upper bound of the range of commerce product options (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product options
	 */
	@Override
	public List<CommerceProductOption> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceProductOption> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product options where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product options
	 * @param end the upper bound of the range of commerce product options (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product options
	 */
	@Override
	public List<CommerceProductOption> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceProductOption> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<CommerceProductOption> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductOption>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductOption commerceProductOption : list) {
					if ((groupId != commerceProductOption.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRODUCTOPTION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceProductOptionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceProductOption>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductOption>)QueryUtil.list(q,
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
	 * Returns the first commerce product option in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product option
	 * @throws NoSuchProductOptionException if a matching commerce product option could not be found
	 */
	@Override
	public CommerceProductOption findByGroupId_First(long groupId,
		OrderByComparator<CommerceProductOption> orderByComparator)
		throws NoSuchProductOptionException {
		CommerceProductOption commerceProductOption = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commerceProductOption != null) {
			return commerceProductOption;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductOptionException(msg.toString());
	}

	/**
	 * Returns the first commerce product option in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product option, or <code>null</code> if a matching commerce product option could not be found
	 */
	@Override
	public CommerceProductOption fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		List<CommerceProductOption> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product option in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product option
	 * @throws NoSuchProductOptionException if a matching commerce product option could not be found
	 */
	@Override
	public CommerceProductOption findByGroupId_Last(long groupId,
		OrderByComparator<CommerceProductOption> orderByComparator)
		throws NoSuchProductOptionException {
		CommerceProductOption commerceProductOption = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commerceProductOption != null) {
			return commerceProductOption;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductOptionException(msg.toString());
	}

	/**
	 * Returns the last commerce product option in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product option, or <code>null</code> if a matching commerce product option could not be found
	 */
	@Override
	public CommerceProductOption fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductOption> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product options before and after the current commerce product option in the ordered set where groupId = &#63;.
	 *
	 * @param commerceProductOptionId the primary key of the current commerce product option
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product option
	 * @throws NoSuchProductOptionException if a commerce product option with the primary key could not be found
	 */
	@Override
	public CommerceProductOption[] findByGroupId_PrevAndNext(
		long commerceProductOptionId, long groupId,
		OrderByComparator<CommerceProductOption> orderByComparator)
		throws NoSuchProductOptionException {
		CommerceProductOption commerceProductOption = findByPrimaryKey(commerceProductOptionId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductOption[] array = new CommerceProductOptionImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, commerceProductOption,
					groupId, orderByComparator, true);

			array[1] = commerceProductOption;

			array[2] = getByGroupId_PrevAndNext(session, commerceProductOption,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceProductOption getByGroupId_PrevAndNext(Session session,
		CommerceProductOption commerceProductOption, long groupId,
		OrderByComparator<CommerceProductOption> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRODUCTOPTION_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			query.append(CommerceProductOptionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductOption);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductOption> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce product options that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce product options that the user has permission to view
	 */
	@Override
	public List<CommerceProductOption> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product options that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product options
	 * @param end the upper bound of the range of commerce product options (not inclusive)
	 * @return the range of matching commerce product options that the user has permission to view
	 */
	@Override
	public List<CommerceProductOption> filterFindByGroupId(long groupId,
		int start, int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product options that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product options
	 * @param end the upper bound of the range of commerce product options (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product options that the user has permission to view
	 */
	@Override
	public List<CommerceProductOption> filterFindByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCEPRODUCTOPTION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_COMMERCEPRODUCTOPTION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCEPRODUCTOPTION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CommerceProductOptionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CommerceProductOptionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceProductOption.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS,
					CommerceProductOptionImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE,
					CommerceProductOptionImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<CommerceProductOption>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the commerce product options before and after the current commerce product option in the ordered set of commerce product options that the user has permission to view where groupId = &#63;.
	 *
	 * @param commerceProductOptionId the primary key of the current commerce product option
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product option
	 * @throws NoSuchProductOptionException if a commerce product option with the primary key could not be found
	 */
	@Override
	public CommerceProductOption[] filterFindByGroupId_PrevAndNext(
		long commerceProductOptionId, long groupId,
		OrderByComparator<CommerceProductOption> orderByComparator)
		throws NoSuchProductOptionException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(commerceProductOptionId, groupId,
				orderByComparator);
		}

		CommerceProductOption commerceProductOption = findByPrimaryKey(commerceProductOptionId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductOption[] array = new CommerceProductOptionImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session,
					commerceProductOption, groupId, orderByComparator, true);

			array[1] = commerceProductOption;

			array[2] = filterGetByGroupId_PrevAndNext(session,
					commerceProductOption, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceProductOption filterGetByGroupId_PrevAndNext(
		Session session, CommerceProductOption commerceProductOption,
		long groupId,
		OrderByComparator<CommerceProductOption> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCEPRODUCTOPTION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_COMMERCEPRODUCTOPTION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCEPRODUCTOPTION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CommerceProductOptionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CommerceProductOptionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceProductOption.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, CommerceProductOptionImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, CommerceProductOptionImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductOption);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductOption> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product options where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceProductOption commerceProductOption : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductOption);
		}
	}

	/**
	 * Returns the number of commerce product options where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce product options
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTOPTION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	/**
	 * Returns the number of commerce product options that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce product options that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_COMMERCEPRODUCTOPTION_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceProductOption.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceProductOption.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceProductOptionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductOptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceProductOptionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductOptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			CommerceProductOptionModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceProductOptionModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(CommerceProductOptionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce product options where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce product options
	 */
	@Override
	public List<CommerceProductOption> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce product options where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product options
	 * @param end the upper bound of the range of commerce product options (not inclusive)
	 * @return the range of matching commerce product options
	 */
	@Override
	public List<CommerceProductOption> findByCompanyId(long companyId,
		int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product options where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product options
	 * @param end the upper bound of the range of commerce product options (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product options
	 */
	@Override
	public List<CommerceProductOption> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product options where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product options
	 * @param end the upper bound of the range of commerce product options (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product options
	 */
	@Override
	public List<CommerceProductOption> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceProductOption> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<CommerceProductOption> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductOption>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductOption commerceProductOption : list) {
					if ((companyId != commerceProductOption.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRODUCTOPTION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceProductOptionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<CommerceProductOption>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductOption>)QueryUtil.list(q,
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
	 * Returns the first commerce product option in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product option
	 * @throws NoSuchProductOptionException if a matching commerce product option could not be found
	 */
	@Override
	public CommerceProductOption findByCompanyId_First(long companyId,
		OrderByComparator<CommerceProductOption> orderByComparator)
		throws NoSuchProductOptionException {
		CommerceProductOption commerceProductOption = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (commerceProductOption != null) {
			return commerceProductOption;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductOptionException(msg.toString());
	}

	/**
	 * Returns the first commerce product option in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product option, or <code>null</code> if a matching commerce product option could not be found
	 */
	@Override
	public CommerceProductOption fetchByCompanyId_First(long companyId,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		List<CommerceProductOption> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product option in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product option
	 * @throws NoSuchProductOptionException if a matching commerce product option could not be found
	 */
	@Override
	public CommerceProductOption findByCompanyId_Last(long companyId,
		OrderByComparator<CommerceProductOption> orderByComparator)
		throws NoSuchProductOptionException {
		CommerceProductOption commerceProductOption = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (commerceProductOption != null) {
			return commerceProductOption;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductOptionException(msg.toString());
	}

	/**
	 * Returns the last commerce product option in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product option, or <code>null</code> if a matching commerce product option could not be found
	 */
	@Override
	public CommerceProductOption fetchByCompanyId_Last(long companyId,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductOption> list = findByCompanyId(companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product options before and after the current commerce product option in the ordered set where companyId = &#63;.
	 *
	 * @param commerceProductOptionId the primary key of the current commerce product option
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product option
	 * @throws NoSuchProductOptionException if a commerce product option with the primary key could not be found
	 */
	@Override
	public CommerceProductOption[] findByCompanyId_PrevAndNext(
		long commerceProductOptionId, long companyId,
		OrderByComparator<CommerceProductOption> orderByComparator)
		throws NoSuchProductOptionException {
		CommerceProductOption commerceProductOption = findByPrimaryKey(commerceProductOptionId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductOption[] array = new CommerceProductOptionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session,
					commerceProductOption, companyId, orderByComparator, true);

			array[1] = commerceProductOption;

			array[2] = getByCompanyId_PrevAndNext(session,
					commerceProductOption, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceProductOption getByCompanyId_PrevAndNext(
		Session session, CommerceProductOption commerceProductOption,
		long companyId,
		OrderByComparator<CommerceProductOption> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRODUCTOPTION_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(CommerceProductOptionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductOption);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductOption> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product options where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceProductOption commerceProductOption : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductOption);
		}
	}

	/**
	 * Returns the number of commerce product options where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce product options
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTOPTION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "commerceProductOption.companyId = ?";

	public CommerceProductOptionPersistenceImpl() {
		setModelClass(CommerceProductOption.class);
	}

	/**
	 * Caches the commerce product option in the entity cache if it is enabled.
	 *
	 * @param commerceProductOption the commerce product option
	 */
	@Override
	public void cacheResult(CommerceProductOption commerceProductOption) {
		entityCache.putResult(CommerceProductOptionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionImpl.class,
			commerceProductOption.getPrimaryKey(), commerceProductOption);

		commerceProductOption.resetOriginalValues();
	}

	/**
	 * Caches the commerce product options in the entity cache if it is enabled.
	 *
	 * @param commerceProductOptions the commerce product options
	 */
	@Override
	public void cacheResult(List<CommerceProductOption> commerceProductOptions) {
		for (CommerceProductOption commerceProductOption : commerceProductOptions) {
			if (entityCache.getResult(
						CommerceProductOptionModelImpl.ENTITY_CACHE_ENABLED,
						CommerceProductOptionImpl.class,
						commerceProductOption.getPrimaryKey()) == null) {
				cacheResult(commerceProductOption);
			}
			else {
				commerceProductOption.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce product options.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceProductOptionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce product option.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceProductOption commerceProductOption) {
		entityCache.removeResult(CommerceProductOptionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionImpl.class,
			commerceProductOption.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CommerceProductOption> commerceProductOptions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceProductOption commerceProductOption : commerceProductOptions) {
			entityCache.removeResult(CommerceProductOptionModelImpl.ENTITY_CACHE_ENABLED,
				CommerceProductOptionImpl.class,
				commerceProductOption.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce product option with the primary key. Does not add the commerce product option to the database.
	 *
	 * @param commerceProductOptionId the primary key for the new commerce product option
	 * @return the new commerce product option
	 */
	@Override
	public CommerceProductOption create(long commerceProductOptionId) {
		CommerceProductOption commerceProductOption = new CommerceProductOptionImpl();

		commerceProductOption.setNew(true);
		commerceProductOption.setPrimaryKey(commerceProductOptionId);

		commerceProductOption.setCompanyId(companyProvider.getCompanyId());

		return commerceProductOption;
	}

	/**
	 * Removes the commerce product option with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceProductOptionId the primary key of the commerce product option
	 * @return the commerce product option that was removed
	 * @throws NoSuchProductOptionException if a commerce product option with the primary key could not be found
	 */
	@Override
	public CommerceProductOption remove(long commerceProductOptionId)
		throws NoSuchProductOptionException {
		return remove((Serializable)commerceProductOptionId);
	}

	/**
	 * Removes the commerce product option with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce product option
	 * @return the commerce product option that was removed
	 * @throws NoSuchProductOptionException if a commerce product option with the primary key could not be found
	 */
	@Override
	public CommerceProductOption remove(Serializable primaryKey)
		throws NoSuchProductOptionException {
		Session session = null;

		try {
			session = openSession();

			CommerceProductOption commerceProductOption = (CommerceProductOption)session.get(CommerceProductOptionImpl.class,
					primaryKey);

			if (commerceProductOption == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchProductOptionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceProductOption);
		}
		catch (NoSuchProductOptionException nsee) {
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
	protected CommerceProductOption removeImpl(
		CommerceProductOption commerceProductOption) {
		commerceProductOption = toUnwrappedModel(commerceProductOption);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceProductOption)) {
				commerceProductOption = (CommerceProductOption)session.get(CommerceProductOptionImpl.class,
						commerceProductOption.getPrimaryKeyObj());
			}

			if (commerceProductOption != null) {
				session.delete(commerceProductOption);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceProductOption != null) {
			clearCache(commerceProductOption);
		}

		return commerceProductOption;
	}

	@Override
	public CommerceProductOption updateImpl(
		CommerceProductOption commerceProductOption) {
		commerceProductOption = toUnwrappedModel(commerceProductOption);

		boolean isNew = commerceProductOption.isNew();

		CommerceProductOptionModelImpl commerceProductOptionModelImpl = (CommerceProductOptionModelImpl)commerceProductOption;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceProductOption.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceProductOption.setCreateDate(now);
			}
			else {
				commerceProductOption.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceProductOptionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceProductOption.setModifiedDate(now);
			}
			else {
				commerceProductOption.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceProductOption.isNew()) {
				session.save(commerceProductOption);

				commerceProductOption.setNew(false);
			}
			else {
				commerceProductOption = (CommerceProductOption)session.merge(commerceProductOption);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceProductOptionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceProductOptionModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] { commerceProductOptionModelImpl.getCompanyId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceProductOptionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductOptionModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { commerceProductOptionModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((commerceProductOptionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductOptionModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						commerceProductOptionModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		entityCache.putResult(CommerceProductOptionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionImpl.class,
			commerceProductOption.getPrimaryKey(), commerceProductOption, false);

		commerceProductOption.resetOriginalValues();

		return commerceProductOption;
	}

	protected CommerceProductOption toUnwrappedModel(
		CommerceProductOption commerceProductOption) {
		if (commerceProductOption instanceof CommerceProductOptionImpl) {
			return commerceProductOption;
		}

		CommerceProductOptionImpl commerceProductOptionImpl = new CommerceProductOptionImpl();

		commerceProductOptionImpl.setNew(commerceProductOption.isNew());
		commerceProductOptionImpl.setPrimaryKey(commerceProductOption.getPrimaryKey());

		commerceProductOptionImpl.setCommerceProductOptionId(commerceProductOption.getCommerceProductOptionId());
		commerceProductOptionImpl.setGroupId(commerceProductOption.getGroupId());
		commerceProductOptionImpl.setCompanyId(commerceProductOption.getCompanyId());
		commerceProductOptionImpl.setUserId(commerceProductOption.getUserId());
		commerceProductOptionImpl.setUserName(commerceProductOption.getUserName());
		commerceProductOptionImpl.setCreateDate(commerceProductOption.getCreateDate());
		commerceProductOptionImpl.setModifiedDate(commerceProductOption.getModifiedDate());
		commerceProductOptionImpl.setName(commerceProductOption.getName());
		commerceProductOptionImpl.setDescription(commerceProductOption.getDescription());
		commerceProductOptionImpl.setDDMFormFieldTypeName(commerceProductOption.getDDMFormFieldTypeName());

		return commerceProductOptionImpl;
	}

	/**
	 * Returns the commerce product option with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce product option
	 * @return the commerce product option
	 * @throws NoSuchProductOptionException if a commerce product option with the primary key could not be found
	 */
	@Override
	public CommerceProductOption findByPrimaryKey(Serializable primaryKey)
		throws NoSuchProductOptionException {
		CommerceProductOption commerceProductOption = fetchByPrimaryKey(primaryKey);

		if (commerceProductOption == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchProductOptionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceProductOption;
	}

	/**
	 * Returns the commerce product option with the primary key or throws a {@link NoSuchProductOptionException} if it could not be found.
	 *
	 * @param commerceProductOptionId the primary key of the commerce product option
	 * @return the commerce product option
	 * @throws NoSuchProductOptionException if a commerce product option with the primary key could not be found
	 */
	@Override
	public CommerceProductOption findByPrimaryKey(long commerceProductOptionId)
		throws NoSuchProductOptionException {
		return findByPrimaryKey((Serializable)commerceProductOptionId);
	}

	/**
	 * Returns the commerce product option with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce product option
	 * @return the commerce product option, or <code>null</code> if a commerce product option with the primary key could not be found
	 */
	@Override
	public CommerceProductOption fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceProductOptionModelImpl.ENTITY_CACHE_ENABLED,
				CommerceProductOptionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceProductOption commerceProductOption = (CommerceProductOption)serializable;

		if (commerceProductOption == null) {
			Session session = null;

			try {
				session = openSession();

				commerceProductOption = (CommerceProductOption)session.get(CommerceProductOptionImpl.class,
						primaryKey);

				if (commerceProductOption != null) {
					cacheResult(commerceProductOption);
				}
				else {
					entityCache.putResult(CommerceProductOptionModelImpl.ENTITY_CACHE_ENABLED,
						CommerceProductOptionImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceProductOptionModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductOptionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceProductOption;
	}

	/**
	 * Returns the commerce product option with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceProductOptionId the primary key of the commerce product option
	 * @return the commerce product option, or <code>null</code> if a commerce product option with the primary key could not be found
	 */
	@Override
	public CommerceProductOption fetchByPrimaryKey(long commerceProductOptionId) {
		return fetchByPrimaryKey((Serializable)commerceProductOptionId);
	}

	@Override
	public Map<Serializable, CommerceProductOption> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceProductOption> map = new HashMap<Serializable, CommerceProductOption>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceProductOption commerceProductOption = fetchByPrimaryKey(primaryKey);

			if (commerceProductOption != null) {
				map.put(primaryKey, commerceProductOption);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceProductOptionModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductOptionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceProductOption)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEPRODUCTOPTION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (CommerceProductOption commerceProductOption : (List<CommerceProductOption>)q.list()) {
				map.put(commerceProductOption.getPrimaryKeyObj(),
					commerceProductOption);

				cacheResult(commerceProductOption);

				uncachedPrimaryKeys.remove(commerceProductOption.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceProductOptionModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductOptionImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce product options.
	 *
	 * @return the commerce product options
	 */
	@Override
	public List<CommerceProductOption> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product options.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product options
	 * @param end the upper bound of the range of commerce product options (not inclusive)
	 * @return the range of commerce product options
	 */
	@Override
	public List<CommerceProductOption> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product options.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product options
	 * @param end the upper bound of the range of commerce product options (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce product options
	 */
	@Override
	public List<CommerceProductOption> findAll(int start, int end,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product options.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product options
	 * @param end the upper bound of the range of commerce product options (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce product options
	 */
	@Override
	public List<CommerceProductOption> findAll(int start, int end,
		OrderByComparator<CommerceProductOption> orderByComparator,
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

		List<CommerceProductOption> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductOption>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEPRODUCTOPTION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEPRODUCTOPTION;

				if (pagination) {
					sql = sql.concat(CommerceProductOptionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceProductOption>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductOption>)QueryUtil.list(q,
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
	 * Removes all the commerce product options from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceProductOption commerceProductOption : findAll()) {
			remove(commerceProductOption);
		}
	}

	/**
	 * Returns the number of commerce product options.
	 *
	 * @return the number of commerce product options
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEPRODUCTOPTION);

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
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceProductOptionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce product option persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceProductOptionImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEPRODUCTOPTION = "SELECT commerceProductOption FROM CommerceProductOption commerceProductOption";
	private static final String _SQL_SELECT_COMMERCEPRODUCTOPTION_WHERE_PKS_IN = "SELECT commerceProductOption FROM CommerceProductOption commerceProductOption WHERE commerceProductOptionId IN (";
	private static final String _SQL_SELECT_COMMERCEPRODUCTOPTION_WHERE = "SELECT commerceProductOption FROM CommerceProductOption commerceProductOption WHERE ";
	private static final String _SQL_COUNT_COMMERCEPRODUCTOPTION = "SELECT COUNT(commerceProductOption) FROM CommerceProductOption commerceProductOption";
	private static final String _SQL_COUNT_COMMERCEPRODUCTOPTION_WHERE = "SELECT COUNT(commerceProductOption) FROM CommerceProductOption commerceProductOption WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "commerceProductOption.commerceProductOptionId";
	private static final String _FILTER_SQL_SELECT_COMMERCEPRODUCTOPTION_WHERE = "SELECT DISTINCT {commerceProductOption.*} FROM CommerceProductOption commerceProductOption WHERE ";
	private static final String _FILTER_SQL_SELECT_COMMERCEPRODUCTOPTION_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {CommerceProductOption.*} FROM (SELECT DISTINCT commerceProductOption.commerceProductOptionId FROM CommerceProductOption commerceProductOption WHERE ";
	private static final String _FILTER_SQL_SELECT_COMMERCEPRODUCTOPTION_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN CommerceProductOption ON TEMP_TABLE.commerceProductOptionId = CommerceProductOption.commerceProductOptionId";
	private static final String _FILTER_SQL_COUNT_COMMERCEPRODUCTOPTION_WHERE = "SELECT COUNT(DISTINCT commerceProductOption.commerceProductOptionId) AS COUNT_VALUE FROM CommerceProductOption commerceProductOption WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "commerceProductOption";
	private static final String _FILTER_ENTITY_TABLE = "CommerceProductOption";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceProductOption.";
	private static final String _ORDER_BY_ENTITY_TABLE = "CommerceProductOption.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceProductOption exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceProductOption exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceProductOptionPersistenceImpl.class);
}