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

package com.liferay.portal.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchLayoutSetBranchException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.LayoutSetBranchPersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.model.impl.LayoutSetBranchImpl;
import com.liferay.portal.model.impl.LayoutSetBranchModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the layout set branch service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class LayoutSetBranchPersistenceImpl extends BasePersistenceImpl<LayoutSetBranch>
	implements LayoutSetBranchPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LayoutSetBranchUtil</code> to access the layout set branch persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutSetBranchImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the layout set branchs where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout set branchs
	 */
	@Override
	public List<LayoutSetBranch> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set branchs where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetBranchModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout set branchs
	 * @param end the upper bound of the range of layout set branchs (not inclusive)
	 * @return the range of matching layout set branchs
	 */
	@Override
	public List<LayoutSetBranch> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set branchs where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetBranchModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout set branchs
	 * @param end the upper bound of the range of layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set branchs
	 */
	@Override
	public List<LayoutSetBranch> findByGroupId(long groupId, int start,
		int end, OrderByComparator<LayoutSetBranch> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout set branchs where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetBranchModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout set branchs
	 * @param end the upper bound of the range of layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout set branchs
	 */
	@Override
	public List<LayoutSetBranch> findByGroupId(long groupId, int start,
		int end, OrderByComparator<LayoutSetBranch> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByGroupId;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<LayoutSetBranch> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutSetBranch>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutSetBranch layoutSetBranch : list) {
					if ((groupId != layoutSetBranch.getGroupId())) {
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

			query.append(_SQL_SELECT_LAYOUTSETBRANCH_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutSetBranchModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<LayoutSetBranch>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSetBranch>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout set branch in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set branch
	 * @throws NoSuchLayoutSetBranchException if a matching layout set branch could not be found
	 */
	@Override
	public LayoutSetBranch findByGroupId_First(long groupId,
		OrderByComparator<LayoutSetBranch> orderByComparator)
		throws NoSuchLayoutSetBranchException {
		LayoutSetBranch layoutSetBranch = fetchByGroupId_First(groupId,
				orderByComparator);

		if (layoutSetBranch != null) {
			return layoutSetBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchLayoutSetBranchException(msg.toString());
	}

	/**
	 * Returns the first layout set branch in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set branch, or <code>null</code> if a matching layout set branch could not be found
	 */
	@Override
	public LayoutSetBranch fetchByGroupId_First(long groupId,
		OrderByComparator<LayoutSetBranch> orderByComparator) {
		List<LayoutSetBranch> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout set branch in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set branch
	 * @throws NoSuchLayoutSetBranchException if a matching layout set branch could not be found
	 */
	@Override
	public LayoutSetBranch findByGroupId_Last(long groupId,
		OrderByComparator<LayoutSetBranch> orderByComparator)
		throws NoSuchLayoutSetBranchException {
		LayoutSetBranch layoutSetBranch = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (layoutSetBranch != null) {
			return layoutSetBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchLayoutSetBranchException(msg.toString());
	}

	/**
	 * Returns the last layout set branch in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set branch, or <code>null</code> if a matching layout set branch could not be found
	 */
	@Override
	public LayoutSetBranch fetchByGroupId_Last(long groupId,
		OrderByComparator<LayoutSetBranch> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<LayoutSetBranch> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout set branchs before and after the current layout set branch in the ordered set where groupId = &#63;.
	 *
	 * @param layoutSetBranchId the primary key of the current layout set branch
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set branch
	 * @throws NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	 */
	@Override
	public LayoutSetBranch[] findByGroupId_PrevAndNext(long layoutSetBranchId,
		long groupId, OrderByComparator<LayoutSetBranch> orderByComparator)
		throws NoSuchLayoutSetBranchException {
		LayoutSetBranch layoutSetBranch = findByPrimaryKey(layoutSetBranchId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetBranch[] array = new LayoutSetBranchImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, layoutSetBranch,
					groupId, orderByComparator, true);

			array[1] = layoutSetBranch;

			array[2] = getByGroupId_PrevAndNext(session, layoutSetBranch,
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

	protected LayoutSetBranch getByGroupId_PrevAndNext(Session session,
		LayoutSetBranch layoutSetBranch, long groupId,
		OrderByComparator<LayoutSetBranch> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTSETBRANCH_WHERE);

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
			query.append(LayoutSetBranchModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layoutSetBranch)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSetBranch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout set branchs that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout set branchs that the user has permission to view
	 */
	@Override
	public List<LayoutSetBranch> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set branchs that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetBranchModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout set branchs
	 * @param end the upper bound of the range of layout set branchs (not inclusive)
	 * @return the range of matching layout set branchs that the user has permission to view
	 */
	@Override
	public List<LayoutSetBranch> filterFindByGroupId(long groupId, int start,
		int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set branchs that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetBranchModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout set branchs
	 * @param end the upper bound of the range of layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set branchs that the user has permission to view
	 */
	@Override
	public List<LayoutSetBranch> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator<LayoutSetBranch> orderByComparator) {
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
			query.append(_FILTER_SQL_SELECT_LAYOUTSETBRANCH_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETBRANCH_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETBRANCH_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutSetBranchModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutSetBranchModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutSetBranch.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, LayoutSetBranchImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, LayoutSetBranchImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<LayoutSetBranch>)QueryUtil.list(q, getDialect(),
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
	 * Returns the layout set branchs before and after the current layout set branch in the ordered set of layout set branchs that the user has permission to view where groupId = &#63;.
	 *
	 * @param layoutSetBranchId the primary key of the current layout set branch
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set branch
	 * @throws NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	 */
	@Override
	public LayoutSetBranch[] filterFindByGroupId_PrevAndNext(
		long layoutSetBranchId, long groupId,
		OrderByComparator<LayoutSetBranch> orderByComparator)
		throws NoSuchLayoutSetBranchException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(layoutSetBranchId, groupId,
				orderByComparator);
		}

		LayoutSetBranch layoutSetBranch = findByPrimaryKey(layoutSetBranchId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetBranch[] array = new LayoutSetBranchImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, layoutSetBranch,
					groupId, orderByComparator, true);

			array[1] = layoutSetBranch;

			array[2] = filterGetByGroupId_PrevAndNext(session, layoutSetBranch,
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

	protected LayoutSetBranch filterGetByGroupId_PrevAndNext(Session session,
		LayoutSetBranch layoutSetBranch, long groupId,
		OrderByComparator<LayoutSetBranch> orderByComparator, boolean previous) {
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
			query.append(_FILTER_SQL_SELECT_LAYOUTSETBRANCH_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETBRANCH_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETBRANCH_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(getColumnName(_ORDER_BY_ENTITY_ALIAS,
							orderByConditionFields[i], true));
				}
				else {
					query.append(getColumnName(_ORDER_BY_ENTITY_TABLE,
							orderByConditionFields[i], true));
				}

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
					query.append(getColumnName(_ORDER_BY_ENTITY_ALIAS,
							orderByFields[i], true));
				}
				else {
					query.append(getColumnName(_ORDER_BY_ENTITY_TABLE,
							orderByFields[i], true));
				}

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
				query.append(LayoutSetBranchModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutSetBranchModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutSetBranch.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutSetBranchImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutSetBranchImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layoutSetBranch)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSetBranch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout set branchs where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (LayoutSetBranch layoutSetBranch : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutSetBranch);
		}
	}

	/**
	 * Returns the number of layout set branchs where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout set branchs
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTSETBRANCH_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of layout set branchs that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout set branchs that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_LAYOUTSETBRANCH_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutSetBranch.class.getName(),
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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "layoutSetBranch.groupId = ?";
	private FinderPath _finderPathWithPaginationFindByG_P;
	private FinderPath _finderPathWithoutPaginationFindByG_P;
	private FinderPath _finderPathCountByG_P;

	/**
	 * Returns all the layout set branchs where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the matching layout set branchs
	 */
	@Override
	public List<LayoutSetBranch> findByG_P(long groupId, boolean privateLayout) {
		return findByG_P(groupId, privateLayout, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set branchs where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetBranchModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout set branchs
	 * @param end the upper bound of the range of layout set branchs (not inclusive)
	 * @return the range of matching layout set branchs
	 */
	@Override
	public List<LayoutSetBranch> findByG_P(long groupId, boolean privateLayout,
		int start, int end) {
		return findByG_P(groupId, privateLayout, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set branchs where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetBranchModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout set branchs
	 * @param end the upper bound of the range of layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set branchs
	 */
	@Override
	public List<LayoutSetBranch> findByG_P(long groupId, boolean privateLayout,
		int start, int end, OrderByComparator<LayoutSetBranch> orderByComparator) {
		return findByG_P(groupId, privateLayout, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the layout set branchs where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetBranchModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout set branchs
	 * @param end the upper bound of the range of layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout set branchs
	 */
	@Override
	public List<LayoutSetBranch> findByG_P(long groupId, boolean privateLayout,
		int start, int end,
		OrderByComparator<LayoutSetBranch> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_P;
			finderArgs = new Object[] { groupId, privateLayout };
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_P;
			finderArgs = new Object[] {
					groupId, privateLayout,
					
					start, end, orderByComparator
				};
		}

		List<LayoutSetBranch> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutSetBranch>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutSetBranch layoutSetBranch : list) {
					if ((groupId != layoutSetBranch.getGroupId()) ||
							(privateLayout != layoutSetBranch.isPrivateLayout())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LAYOUTSETBRANCH_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutSetBranchModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (!pagination) {
					list = (List<LayoutSetBranch>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSetBranch>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set branch
	 * @throws NoSuchLayoutSetBranchException if a matching layout set branch could not be found
	 */
	@Override
	public LayoutSetBranch findByG_P_First(long groupId, boolean privateLayout,
		OrderByComparator<LayoutSetBranch> orderByComparator)
		throws NoSuchLayoutSetBranchException {
		LayoutSetBranch layoutSetBranch = fetchByG_P_First(groupId,
				privateLayout, orderByComparator);

		if (layoutSetBranch != null) {
			return layoutSetBranch;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append("}");

		throw new NoSuchLayoutSetBranchException(msg.toString());
	}

	/**
	 * Returns the first layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set branch, or <code>null</code> if a matching layout set branch could not be found
	 */
	@Override
	public LayoutSetBranch fetchByG_P_First(long groupId,
		boolean privateLayout,
		OrderByComparator<LayoutSetBranch> orderByComparator) {
		List<LayoutSetBranch> list = findByG_P(groupId, privateLayout, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set branch
	 * @throws NoSuchLayoutSetBranchException if a matching layout set branch could not be found
	 */
	@Override
	public LayoutSetBranch findByG_P_Last(long groupId, boolean privateLayout,
		OrderByComparator<LayoutSetBranch> orderByComparator)
		throws NoSuchLayoutSetBranchException {
		LayoutSetBranch layoutSetBranch = fetchByG_P_Last(groupId,
				privateLayout, orderByComparator);

		if (layoutSetBranch != null) {
			return layoutSetBranch;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append("}");

		throw new NoSuchLayoutSetBranchException(msg.toString());
	}

	/**
	 * Returns the last layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set branch, or <code>null</code> if a matching layout set branch could not be found
	 */
	@Override
	public LayoutSetBranch fetchByG_P_Last(long groupId, boolean privateLayout,
		OrderByComparator<LayoutSetBranch> orderByComparator) {
		int count = countByG_P(groupId, privateLayout);

		if (count == 0) {
			return null;
		}

		List<LayoutSetBranch> list = findByG_P(groupId, privateLayout,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout set branchs before and after the current layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param layoutSetBranchId the primary key of the current layout set branch
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set branch
	 * @throws NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	 */
	@Override
	public LayoutSetBranch[] findByG_P_PrevAndNext(long layoutSetBranchId,
		long groupId, boolean privateLayout,
		OrderByComparator<LayoutSetBranch> orderByComparator)
		throws NoSuchLayoutSetBranchException {
		LayoutSetBranch layoutSetBranch = findByPrimaryKey(layoutSetBranchId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetBranch[] array = new LayoutSetBranchImpl[3];

			array[0] = getByG_P_PrevAndNext(session, layoutSetBranch, groupId,
					privateLayout, orderByComparator, true);

			array[1] = layoutSetBranch;

			array[2] = getByG_P_PrevAndNext(session, layoutSetBranch, groupId,
					privateLayout, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutSetBranch getByG_P_PrevAndNext(Session session,
		LayoutSetBranch layoutSetBranch, long groupId, boolean privateLayout,
		OrderByComparator<LayoutSetBranch> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUTSETBRANCH_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

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
			query.append(LayoutSetBranchModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layoutSetBranch)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSetBranch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout set branchs that the user has permission to view where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the matching layout set branchs that the user has permission to view
	 */
	@Override
	public List<LayoutSetBranch> filterFindByG_P(long groupId,
		boolean privateLayout) {
		return filterFindByG_P(groupId, privateLayout, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set branchs that the user has permission to view where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetBranchModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout set branchs
	 * @param end the upper bound of the range of layout set branchs (not inclusive)
	 * @return the range of matching layout set branchs that the user has permission to view
	 */
	@Override
	public List<LayoutSetBranch> filterFindByG_P(long groupId,
		boolean privateLayout, int start, int end) {
		return filterFindByG_P(groupId, privateLayout, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set branchs that the user has permissions to view where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetBranchModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout set branchs
	 * @param end the upper bound of the range of layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set branchs that the user has permission to view
	 */
	@Override
	public List<LayoutSetBranch> filterFindByG_P(long groupId,
		boolean privateLayout, int start, int end,
		OrderByComparator<LayoutSetBranch> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P(groupId, privateLayout, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETBRANCH_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETBRANCH_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETBRANCH_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutSetBranchModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutSetBranchModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutSetBranch.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, LayoutSetBranchImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, LayoutSetBranchImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

			return (List<LayoutSetBranch>)QueryUtil.list(q, getDialect(),
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
	 * Returns the layout set branchs before and after the current layout set branch in the ordered set of layout set branchs that the user has permission to view where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param layoutSetBranchId the primary key of the current layout set branch
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set branch
	 * @throws NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	 */
	@Override
	public LayoutSetBranch[] filterFindByG_P_PrevAndNext(
		long layoutSetBranchId, long groupId, boolean privateLayout,
		OrderByComparator<LayoutSetBranch> orderByComparator)
		throws NoSuchLayoutSetBranchException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_PrevAndNext(layoutSetBranchId, groupId,
				privateLayout, orderByComparator);
		}

		LayoutSetBranch layoutSetBranch = findByPrimaryKey(layoutSetBranchId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetBranch[] array = new LayoutSetBranchImpl[3];

			array[0] = filterGetByG_P_PrevAndNext(session, layoutSetBranch,
					groupId, privateLayout, orderByComparator, true);

			array[1] = layoutSetBranch;

			array[2] = filterGetByG_P_PrevAndNext(session, layoutSetBranch,
					groupId, privateLayout, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutSetBranch filterGetByG_P_PrevAndNext(Session session,
		LayoutSetBranch layoutSetBranch, long groupId, boolean privateLayout,
		OrderByComparator<LayoutSetBranch> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETBRANCH_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETBRANCH_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETBRANCH_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(getColumnName(_ORDER_BY_ENTITY_ALIAS,
							orderByConditionFields[i], true));
				}
				else {
					query.append(getColumnName(_ORDER_BY_ENTITY_TABLE,
							orderByConditionFields[i], true));
				}

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
					query.append(getColumnName(_ORDER_BY_ENTITY_ALIAS,
							orderByFields[i], true));
				}
				else {
					query.append(getColumnName(_ORDER_BY_ENTITY_TABLE,
							orderByFields[i], true));
				}

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
				query.append(LayoutSetBranchModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutSetBranchModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutSetBranch.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutSetBranchImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutSetBranchImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layoutSetBranch)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSetBranch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout set branchs where groupId = &#63; and privateLayout = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 */
	@Override
	public void removeByG_P(long groupId, boolean privateLayout) {
		for (LayoutSetBranch layoutSetBranch : findByG_P(groupId,
				privateLayout, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutSetBranch);
		}
	}

	/**
	 * Returns the number of layout set branchs where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the number of matching layout set branchs
	 */
	@Override
	public int countByG_P(long groupId, boolean privateLayout) {
		FinderPath finderPath = _finderPathCountByG_P;

		Object[] finderArgs = new Object[] { groupId, privateLayout };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTSETBRANCH_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of layout set branchs that the user has permission to view where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the number of matching layout set branchs that the user has permission to view
	 */
	@Override
	public int filterCountByG_P(long groupId, boolean privateLayout) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P(groupId, privateLayout);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_LAYOUTSETBRANCH_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutSetBranch.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

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

	private static final String _FINDER_COLUMN_G_P_GROUPID_2 = "layoutSetBranch.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_PRIVATELAYOUT_2 = "layoutSetBranch.privateLayout = ?";
	private FinderPath _finderPathFetchByG_P_N;
	private FinderPath _finderPathCountByG_P_N;

	/**
	 * Returns the layout set branch where groupId = &#63; and privateLayout = &#63; and name = &#63; or throws a <code>NoSuchLayoutSetBranchException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param name the name
	 * @return the matching layout set branch
	 * @throws NoSuchLayoutSetBranchException if a matching layout set branch could not be found
	 */
	@Override
	public LayoutSetBranch findByG_P_N(long groupId, boolean privateLayout,
		String name) throws NoSuchLayoutSetBranchException {
		LayoutSetBranch layoutSetBranch = fetchByG_P_N(groupId, privateLayout,
				name);

		if (layoutSetBranch == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append(", name=");
			msg.append(name);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLayoutSetBranchException(msg.toString());
		}

		return layoutSetBranch;
	}

	/**
	 * Returns the layout set branch where groupId = &#63; and privateLayout = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param name the name
	 * @return the matching layout set branch, or <code>null</code> if a matching layout set branch could not be found
	 */
	@Override
	public LayoutSetBranch fetchByG_P_N(long groupId, boolean privateLayout,
		String name) {
		return fetchByG_P_N(groupId, privateLayout, name, true);
	}

	/**
	 * Returns the layout set branch where groupId = &#63; and privateLayout = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param name the name
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout set branch, or <code>null</code> if a matching layout set branch could not be found
	 */
	@Override
	public LayoutSetBranch fetchByG_P_N(long groupId, boolean privateLayout,
		String name, boolean retrieveFromCache) {
		name = Objects.toString(name, "");

		Object[] finderArgs = new Object[] { groupId, privateLayout, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(_finderPathFetchByG_P_N,
					finderArgs, this);
		}

		if (result instanceof LayoutSetBranch) {
			LayoutSetBranch layoutSetBranch = (LayoutSetBranch)result;

			if ((groupId != layoutSetBranch.getGroupId()) ||
					(privateLayout != layoutSetBranch.isPrivateLayout()) ||
					!Objects.equals(name, layoutSetBranch.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_LAYOUTSETBRANCH_WHERE);

			query.append(_FINDER_COLUMN_G_P_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_N_PRIVATELAYOUT_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_G_P_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_P_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (bindName) {
					qPos.add(name);
				}

				List<LayoutSetBranch> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(_finderPathFetchByG_P_N,
						finderArgs, list);
				}
				else {
					LayoutSetBranch layoutSetBranch = list.get(0);

					result = layoutSetBranch;

					cacheResult(layoutSetBranch);
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathFetchByG_P_N, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (LayoutSetBranch)result;
		}
	}

	/**
	 * Removes the layout set branch where groupId = &#63; and privateLayout = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param name the name
	 * @return the layout set branch that was removed
	 */
	@Override
	public LayoutSetBranch removeByG_P_N(long groupId, boolean privateLayout,
		String name) throws NoSuchLayoutSetBranchException {
		LayoutSetBranch layoutSetBranch = findByG_P_N(groupId, privateLayout,
				name);

		return remove(layoutSetBranch);
	}

	/**
	 * Returns the number of layout set branchs where groupId = &#63; and privateLayout = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param name the name
	 * @return the number of matching layout set branchs
	 */
	@Override
	public int countByG_P_N(long groupId, boolean privateLayout, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByG_P_N;

		Object[] finderArgs = new Object[] { groupId, privateLayout, name };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTSETBRANCH_WHERE);

			query.append(_FINDER_COLUMN_G_P_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_N_PRIVATELAYOUT_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_G_P_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_P_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (bindName) {
					qPos.add(name);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_P_N_GROUPID_2 = "layoutSetBranch.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_N_PRIVATELAYOUT_2 = "layoutSetBranch.privateLayout = ? AND ";
	private static final String _FINDER_COLUMN_G_P_N_NAME_2 = "layoutSetBranch.name = ?";
	private static final String _FINDER_COLUMN_G_P_N_NAME_3 = "(layoutSetBranch.name IS NULL OR layoutSetBranch.name = '')";
	private FinderPath _finderPathWithPaginationFindByG_P_M;
	private FinderPath _finderPathWithoutPaginationFindByG_P_M;
	private FinderPath _finderPathCountByG_P_M;

	/**
	 * Returns all the layout set branchs where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param master the master
	 * @return the matching layout set branchs
	 */
	@Override
	public List<LayoutSetBranch> findByG_P_M(long groupId,
		boolean privateLayout, boolean master) {
		return findByG_P_M(groupId, privateLayout, master, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set branchs where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetBranchModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param master the master
	 * @param start the lower bound of the range of layout set branchs
	 * @param end the upper bound of the range of layout set branchs (not inclusive)
	 * @return the range of matching layout set branchs
	 */
	@Override
	public List<LayoutSetBranch> findByG_P_M(long groupId,
		boolean privateLayout, boolean master, int start, int end) {
		return findByG_P_M(groupId, privateLayout, master, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set branchs where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetBranchModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param master the master
	 * @param start the lower bound of the range of layout set branchs
	 * @param end the upper bound of the range of layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set branchs
	 */
	@Override
	public List<LayoutSetBranch> findByG_P_M(long groupId,
		boolean privateLayout, boolean master, int start, int end,
		OrderByComparator<LayoutSetBranch> orderByComparator) {
		return findByG_P_M(groupId, privateLayout, master, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout set branchs where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetBranchModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param master the master
	 * @param start the lower bound of the range of layout set branchs
	 * @param end the upper bound of the range of layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout set branchs
	 */
	@Override
	public List<LayoutSetBranch> findByG_P_M(long groupId,
		boolean privateLayout, boolean master, int start, int end,
		OrderByComparator<LayoutSetBranch> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_P_M;
			finderArgs = new Object[] { groupId, privateLayout, master };
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_P_M;
			finderArgs = new Object[] {
					groupId, privateLayout, master,
					
					start, end, orderByComparator
				};
		}

		List<LayoutSetBranch> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutSetBranch>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutSetBranch layoutSetBranch : list) {
					if ((groupId != layoutSetBranch.getGroupId()) ||
							(privateLayout != layoutSetBranch.isPrivateLayout()) ||
							(master != layoutSetBranch.isMaster())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_LAYOUTSETBRANCH_WHERE);

			query.append(_FINDER_COLUMN_G_P_M_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_M_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_M_MASTER_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutSetBranchModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(master);

				if (!pagination) {
					list = (List<LayoutSetBranch>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSetBranch>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param master the master
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set branch
	 * @throws NoSuchLayoutSetBranchException if a matching layout set branch could not be found
	 */
	@Override
	public LayoutSetBranch findByG_P_M_First(long groupId,
		boolean privateLayout, boolean master,
		OrderByComparator<LayoutSetBranch> orderByComparator)
		throws NoSuchLayoutSetBranchException {
		LayoutSetBranch layoutSetBranch = fetchByG_P_M_First(groupId,
				privateLayout, master, orderByComparator);

		if (layoutSetBranch != null) {
			return layoutSetBranch;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", master=");
		msg.append(master);

		msg.append("}");

		throw new NoSuchLayoutSetBranchException(msg.toString());
	}

	/**
	 * Returns the first layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param master the master
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set branch, or <code>null</code> if a matching layout set branch could not be found
	 */
	@Override
	public LayoutSetBranch fetchByG_P_M_First(long groupId,
		boolean privateLayout, boolean master,
		OrderByComparator<LayoutSetBranch> orderByComparator) {
		List<LayoutSetBranch> list = findByG_P_M(groupId, privateLayout,
				master, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param master the master
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set branch
	 * @throws NoSuchLayoutSetBranchException if a matching layout set branch could not be found
	 */
	@Override
	public LayoutSetBranch findByG_P_M_Last(long groupId,
		boolean privateLayout, boolean master,
		OrderByComparator<LayoutSetBranch> orderByComparator)
		throws NoSuchLayoutSetBranchException {
		LayoutSetBranch layoutSetBranch = fetchByG_P_M_Last(groupId,
				privateLayout, master, orderByComparator);

		if (layoutSetBranch != null) {
			return layoutSetBranch;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", master=");
		msg.append(master);

		msg.append("}");

		throw new NoSuchLayoutSetBranchException(msg.toString());
	}

	/**
	 * Returns the last layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param master the master
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set branch, or <code>null</code> if a matching layout set branch could not be found
	 */
	@Override
	public LayoutSetBranch fetchByG_P_M_Last(long groupId,
		boolean privateLayout, boolean master,
		OrderByComparator<LayoutSetBranch> orderByComparator) {
		int count = countByG_P_M(groupId, privateLayout, master);

		if (count == 0) {
			return null;
		}

		List<LayoutSetBranch> list = findByG_P_M(groupId, privateLayout,
				master, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout set branchs before and after the current layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	 *
	 * @param layoutSetBranchId the primary key of the current layout set branch
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param master the master
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set branch
	 * @throws NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	 */
	@Override
	public LayoutSetBranch[] findByG_P_M_PrevAndNext(long layoutSetBranchId,
		long groupId, boolean privateLayout, boolean master,
		OrderByComparator<LayoutSetBranch> orderByComparator)
		throws NoSuchLayoutSetBranchException {
		LayoutSetBranch layoutSetBranch = findByPrimaryKey(layoutSetBranchId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetBranch[] array = new LayoutSetBranchImpl[3];

			array[0] = getByG_P_M_PrevAndNext(session, layoutSetBranch,
					groupId, privateLayout, master, orderByComparator, true);

			array[1] = layoutSetBranch;

			array[2] = getByG_P_M_PrevAndNext(session, layoutSetBranch,
					groupId, privateLayout, master, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutSetBranch getByG_P_M_PrevAndNext(Session session,
		LayoutSetBranch layoutSetBranch, long groupId, boolean privateLayout,
		boolean master, OrderByComparator<LayoutSetBranch> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUTSETBRANCH_WHERE);

		query.append(_FINDER_COLUMN_G_P_M_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_M_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_M_MASTER_2);

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
			query.append(LayoutSetBranchModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		qPos.add(master);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layoutSetBranch)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSetBranch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout set branchs that the user has permission to view where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param master the master
	 * @return the matching layout set branchs that the user has permission to view
	 */
	@Override
	public List<LayoutSetBranch> filterFindByG_P_M(long groupId,
		boolean privateLayout, boolean master) {
		return filterFindByG_P_M(groupId, privateLayout, master,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set branchs that the user has permission to view where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetBranchModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param master the master
	 * @param start the lower bound of the range of layout set branchs
	 * @param end the upper bound of the range of layout set branchs (not inclusive)
	 * @return the range of matching layout set branchs that the user has permission to view
	 */
	@Override
	public List<LayoutSetBranch> filterFindByG_P_M(long groupId,
		boolean privateLayout, boolean master, int start, int end) {
		return filterFindByG_P_M(groupId, privateLayout, master, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the layout set branchs that the user has permissions to view where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetBranchModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param master the master
	 * @param start the lower bound of the range of layout set branchs
	 * @param end the upper bound of the range of layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set branchs that the user has permission to view
	 */
	@Override
	public List<LayoutSetBranch> filterFindByG_P_M(long groupId,
		boolean privateLayout, boolean master, int start, int end,
		OrderByComparator<LayoutSetBranch> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_M(groupId, privateLayout, master, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETBRANCH_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETBRANCH_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_M_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_M_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_M_MASTER_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETBRANCH_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutSetBranchModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutSetBranchModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutSetBranch.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, LayoutSetBranchImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, LayoutSetBranchImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

			qPos.add(master);

			return (List<LayoutSetBranch>)QueryUtil.list(q, getDialect(),
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
	 * Returns the layout set branchs before and after the current layout set branch in the ordered set of layout set branchs that the user has permission to view where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	 *
	 * @param layoutSetBranchId the primary key of the current layout set branch
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param master the master
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set branch
	 * @throws NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	 */
	@Override
	public LayoutSetBranch[] filterFindByG_P_M_PrevAndNext(
		long layoutSetBranchId, long groupId, boolean privateLayout,
		boolean master, OrderByComparator<LayoutSetBranch> orderByComparator)
		throws NoSuchLayoutSetBranchException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_M_PrevAndNext(layoutSetBranchId, groupId,
				privateLayout, master, orderByComparator);
		}

		LayoutSetBranch layoutSetBranch = findByPrimaryKey(layoutSetBranchId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetBranch[] array = new LayoutSetBranchImpl[3];

			array[0] = filterGetByG_P_M_PrevAndNext(session, layoutSetBranch,
					groupId, privateLayout, master, orderByComparator, true);

			array[1] = layoutSetBranch;

			array[2] = filterGetByG_P_M_PrevAndNext(session, layoutSetBranch,
					groupId, privateLayout, master, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutSetBranch filterGetByG_P_M_PrevAndNext(Session session,
		LayoutSetBranch layoutSetBranch, long groupId, boolean privateLayout,
		boolean master, OrderByComparator<LayoutSetBranch> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(7 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETBRANCH_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETBRANCH_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_M_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_M_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_M_MASTER_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETBRANCH_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(getColumnName(_ORDER_BY_ENTITY_ALIAS,
							orderByConditionFields[i], true));
				}
				else {
					query.append(getColumnName(_ORDER_BY_ENTITY_TABLE,
							orderByConditionFields[i], true));
				}

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
					query.append(getColumnName(_ORDER_BY_ENTITY_ALIAS,
							orderByFields[i], true));
				}
				else {
					query.append(getColumnName(_ORDER_BY_ENTITY_TABLE,
							orderByFields[i], true));
				}

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
				query.append(LayoutSetBranchModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutSetBranchModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutSetBranch.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutSetBranchImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutSetBranchImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		qPos.add(master);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layoutSetBranch)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSetBranch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout set branchs where groupId = &#63; and privateLayout = &#63; and master = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param master the master
	 */
	@Override
	public void removeByG_P_M(long groupId, boolean privateLayout,
		boolean master) {
		for (LayoutSetBranch layoutSetBranch : findByG_P_M(groupId,
				privateLayout, master, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(layoutSetBranch);
		}
	}

	/**
	 * Returns the number of layout set branchs where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param master the master
	 * @return the number of matching layout set branchs
	 */
	@Override
	public int countByG_P_M(long groupId, boolean privateLayout, boolean master) {
		FinderPath finderPath = _finderPathCountByG_P_M;

		Object[] finderArgs = new Object[] { groupId, privateLayout, master };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTSETBRANCH_WHERE);

			query.append(_FINDER_COLUMN_G_P_M_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_M_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_M_MASTER_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(master);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of layout set branchs that the user has permission to view where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param master the master
	 * @return the number of matching layout set branchs that the user has permission to view
	 */
	@Override
	public int filterCountByG_P_M(long groupId, boolean privateLayout,
		boolean master) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P_M(groupId, privateLayout, master);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_LAYOUTSETBRANCH_WHERE);

		query.append(_FINDER_COLUMN_G_P_M_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_M_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_M_MASTER_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutSetBranch.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

			qPos.add(master);

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

	private static final String _FINDER_COLUMN_G_P_M_GROUPID_2 = "layoutSetBranch.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_M_PRIVATELAYOUT_2 = "layoutSetBranch.privateLayout = ? AND ";
	private static final String _FINDER_COLUMN_G_P_M_MASTER_2 = "layoutSetBranch.master = ?";

	public LayoutSetBranchPersistenceImpl() {
		setModelClass(LayoutSetBranch.class);

		setModelImplClass(LayoutSetBranchImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(LayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the layout set branch in the entity cache if it is enabled.
	 *
	 * @param layoutSetBranch the layout set branch
	 */
	@Override
	public void cacheResult(LayoutSetBranch layoutSetBranch) {
		EntityCacheUtil.putResult(LayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetBranchImpl.class, layoutSetBranch.getPrimaryKey(),
			layoutSetBranch);

		FinderCacheUtil.putResult(_finderPathFetchByG_P_N,
			new Object[] {
				layoutSetBranch.getGroupId(), layoutSetBranch.isPrivateLayout(),
				layoutSetBranch.getName()
			}, layoutSetBranch);

		layoutSetBranch.resetOriginalValues();
	}

	/**
	 * Caches the layout set branchs in the entity cache if it is enabled.
	 *
	 * @param layoutSetBranchs the layout set branchs
	 */
	@Override
	public void cacheResult(List<LayoutSetBranch> layoutSetBranchs) {
		for (LayoutSetBranch layoutSetBranch : layoutSetBranchs) {
			if (EntityCacheUtil.getResult(
						LayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
						LayoutSetBranchImpl.class,
						layoutSetBranch.getPrimaryKey()) == null) {
				cacheResult(layoutSetBranch);
			}
			else {
				layoutSetBranch.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout set branchs.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(LayoutSetBranchImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout set branch.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutSetBranch layoutSetBranch) {
		EntityCacheUtil.removeResult(LayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetBranchImpl.class, layoutSetBranch.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LayoutSetBranchModelImpl)layoutSetBranch, true);
	}

	@Override
	public void clearCache(List<LayoutSetBranch> layoutSetBranchs) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutSetBranch layoutSetBranch : layoutSetBranchs) {
			EntityCacheUtil.removeResult(LayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetBranchImpl.class, layoutSetBranch.getPrimaryKey());

			clearUniqueFindersCache((LayoutSetBranchModelImpl)layoutSetBranch,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		LayoutSetBranchModelImpl layoutSetBranchModelImpl) {
		Object[] args = new Object[] {
				layoutSetBranchModelImpl.getGroupId(),
				layoutSetBranchModelImpl.isPrivateLayout(),
				layoutSetBranchModelImpl.getName()
			};

		FinderCacheUtil.putResult(_finderPathCountByG_P_N, args,
			Long.valueOf(1), false);
		FinderCacheUtil.putResult(_finderPathFetchByG_P_N, args,
			layoutSetBranchModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LayoutSetBranchModelImpl layoutSetBranchModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					layoutSetBranchModelImpl.getGroupId(),
					layoutSetBranchModelImpl.isPrivateLayout(),
					layoutSetBranchModelImpl.getName()
				};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_N, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_P_N, args);
		}

		if ((layoutSetBranchModelImpl.getColumnBitmask() &
				_finderPathFetchByG_P_N.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					layoutSetBranchModelImpl.getOriginalGroupId(),
					layoutSetBranchModelImpl.getOriginalPrivateLayout(),
					layoutSetBranchModelImpl.getOriginalName()
				};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_N, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_P_N, args);
		}
	}

	/**
	 * Creates a new layout set branch with the primary key. Does not add the layout set branch to the database.
	 *
	 * @param layoutSetBranchId the primary key for the new layout set branch
	 * @return the new layout set branch
	 */
	@Override
	public LayoutSetBranch create(long layoutSetBranchId) {
		LayoutSetBranch layoutSetBranch = new LayoutSetBranchImpl();

		layoutSetBranch.setNew(true);
		layoutSetBranch.setPrimaryKey(layoutSetBranchId);

		layoutSetBranch.setCompanyId(companyProvider.getCompanyId());

		return layoutSetBranch;
	}

	/**
	 * Removes the layout set branch with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSetBranchId the primary key of the layout set branch
	 * @return the layout set branch that was removed
	 * @throws NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	 */
	@Override
	public LayoutSetBranch remove(long layoutSetBranchId)
		throws NoSuchLayoutSetBranchException {
		return remove((Serializable)layoutSetBranchId);
	}

	/**
	 * Removes the layout set branch with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout set branch
	 * @return the layout set branch that was removed
	 * @throws NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	 */
	@Override
	public LayoutSetBranch remove(Serializable primaryKey)
		throws NoSuchLayoutSetBranchException {
		Session session = null;

		try {
			session = openSession();

			LayoutSetBranch layoutSetBranch = (LayoutSetBranch)session.get(LayoutSetBranchImpl.class,
					primaryKey);

			if (layoutSetBranch == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLayoutSetBranchException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(layoutSetBranch);
		}
		catch (NoSuchLayoutSetBranchException nsee) {
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
	protected LayoutSetBranch removeImpl(LayoutSetBranch layoutSetBranch) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutSetBranch)) {
				layoutSetBranch = (LayoutSetBranch)session.get(LayoutSetBranchImpl.class,
						layoutSetBranch.getPrimaryKeyObj());
			}

			if (layoutSetBranch != null) {
				session.delete(layoutSetBranch);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutSetBranch != null) {
			clearCache(layoutSetBranch);
		}

		return layoutSetBranch;
	}

	@Override
	public LayoutSetBranch updateImpl(LayoutSetBranch layoutSetBranch) {
		boolean isNew = layoutSetBranch.isNew();

		if (!(layoutSetBranch instanceof LayoutSetBranchModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(layoutSetBranch.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(layoutSetBranch);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in layoutSetBranch proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LayoutSetBranch implementation " +
				layoutSetBranch.getClass());
		}

		LayoutSetBranchModelImpl layoutSetBranchModelImpl = (LayoutSetBranchModelImpl)layoutSetBranch;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (layoutSetBranch.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutSetBranch.setCreateDate(now);
			}
			else {
				layoutSetBranch.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!layoutSetBranchModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutSetBranch.setModifiedDate(now);
			}
			else {
				layoutSetBranch.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (layoutSetBranch.isNew()) {
				session.save(layoutSetBranch);

				layoutSetBranch.setNew(false);
			}
			else {
				layoutSetBranch = (LayoutSetBranch)session.merge(layoutSetBranch);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LayoutSetBranchModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { layoutSetBranchModelImpl.getGroupId() };

			FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByGroupId,
				args);

			args = new Object[] {
					layoutSetBranchModelImpl.getGroupId(),
					layoutSetBranchModelImpl.isPrivateLayout()
				};

			FinderCacheUtil.removeResult(_finderPathCountByG_P, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByG_P,
				args);

			args = new Object[] {
					layoutSetBranchModelImpl.getGroupId(),
					layoutSetBranchModelImpl.isPrivateLayout(),
					layoutSetBranchModelImpl.isMaster()
				};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_M, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByG_P_M,
				args);

			FinderCacheUtil.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindAll,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((layoutSetBranchModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByGroupId.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutSetBranchModelImpl.getOriginalGroupId()
					};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByGroupId,
					args);

				args = new Object[] { layoutSetBranchModelImpl.getGroupId() };

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByGroupId,
					args);
			}

			if ((layoutSetBranchModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByG_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutSetBranchModelImpl.getOriginalGroupId(),
						layoutSetBranchModelImpl.getOriginalPrivateLayout()
					};

				FinderCacheUtil.removeResult(_finderPathCountByG_P, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByG_P,
					args);

				args = new Object[] {
						layoutSetBranchModelImpl.getGroupId(),
						layoutSetBranchModelImpl.isPrivateLayout()
					};

				FinderCacheUtil.removeResult(_finderPathCountByG_P, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByG_P,
					args);
			}

			if ((layoutSetBranchModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByG_P_M.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutSetBranchModelImpl.getOriginalGroupId(),
						layoutSetBranchModelImpl.getOriginalPrivateLayout(),
						layoutSetBranchModelImpl.getOriginalMaster()
					};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_M, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByG_P_M,
					args);

				args = new Object[] {
						layoutSetBranchModelImpl.getGroupId(),
						layoutSetBranchModelImpl.isPrivateLayout(),
						layoutSetBranchModelImpl.isMaster()
					};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_M, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByG_P_M,
					args);
			}
		}

		EntityCacheUtil.putResult(LayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetBranchImpl.class, layoutSetBranch.getPrimaryKey(),
			layoutSetBranch, false);

		clearUniqueFindersCache(layoutSetBranchModelImpl, false);
		cacheUniqueFindersCache(layoutSetBranchModelImpl);

		layoutSetBranch.resetOriginalValues();

		return layoutSetBranch;
	}

	/**
	 * Returns the layout set branch with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout set branch
	 * @return the layout set branch
	 * @throws NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	 */
	@Override
	public LayoutSetBranch findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLayoutSetBranchException {
		LayoutSetBranch layoutSetBranch = fetchByPrimaryKey(primaryKey);

		if (layoutSetBranch == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLayoutSetBranchException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return layoutSetBranch;
	}

	/**
	 * Returns the layout set branch with the primary key or throws a <code>NoSuchLayoutSetBranchException</code> if it could not be found.
	 *
	 * @param layoutSetBranchId the primary key of the layout set branch
	 * @return the layout set branch
	 * @throws NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	 */
	@Override
	public LayoutSetBranch findByPrimaryKey(long layoutSetBranchId)
		throws NoSuchLayoutSetBranchException {
		return findByPrimaryKey((Serializable)layoutSetBranchId);
	}

	/**
	 * Returns the layout set branch with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutSetBranchId the primary key of the layout set branch
	 * @return the layout set branch, or <code>null</code> if a layout set branch with the primary key could not be found
	 */
	@Override
	public LayoutSetBranch fetchByPrimaryKey(long layoutSetBranchId) {
		return fetchByPrimaryKey((Serializable)layoutSetBranchId);
	}

	/**
	 * Returns all the layout set branchs.
	 *
	 * @return the layout set branchs
	 */
	@Override
	public List<LayoutSetBranch> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetBranchModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout set branchs
	 * @param end the upper bound of the range of layout set branchs (not inclusive)
	 * @return the range of layout set branchs
	 */
	@Override
	public List<LayoutSetBranch> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetBranchModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout set branchs
	 * @param end the upper bound of the range of layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout set branchs
	 */
	@Override
	public List<LayoutSetBranch> findAll(int start, int end,
		OrderByComparator<LayoutSetBranch> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout set branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetBranchModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout set branchs
	 * @param end the upper bound of the range of layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of layout set branchs
	 */
	@Override
	public List<LayoutSetBranch> findAll(int start, int end,
		OrderByComparator<LayoutSetBranch> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<LayoutSetBranch> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutSetBranch>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LAYOUTSETBRANCH);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTSETBRANCH;

				if (pagination) {
					sql = sql.concat(LayoutSetBranchModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LayoutSetBranch>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSetBranch>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the layout set branchs from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutSetBranch layoutSetBranch : findAll()) {
			remove(layoutSetBranch);
		}
	}

	/**
	 * Returns the number of layout set branchs.
	 *
	 * @return the number of layout set branchs
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(_finderPathCountAll,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUTSETBRANCH);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(_finderPathCountAll,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathCountAll,
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
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "layoutSetBranchId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAYOUTSETBRANCH;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LayoutSetBranchModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout set branch persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(LayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetBranchImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(LayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetBranchImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
				new String[0]);

		_finderPathCountAll = new FinderPath(LayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
				new String[0]);

		_finderPathWithPaginationFindByGroupId = new FinderPath(LayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetBranchImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
				new String[] {
					Long.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(LayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetBranchImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
				new String[] { Long.class.getName() },
				LayoutSetBranchModelImpl.GROUPID_COLUMN_BITMASK |
				LayoutSetBranchModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(LayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
				new String[] { Long.class.getName() });

		_finderPathWithPaginationFindByG_P = new FinderPath(LayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetBranchImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByG_P = new FinderPath(LayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetBranchImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P",
				new String[] { Long.class.getName(), Boolean.class.getName() },
				LayoutSetBranchModelImpl.GROUPID_COLUMN_BITMASK |
				LayoutSetBranchModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
				LayoutSetBranchModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByG_P = new FinderPath(LayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P",
				new String[] { Long.class.getName(), Boolean.class.getName() });

		_finderPathFetchByG_P_N = new FinderPath(LayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetBranchImpl.class, FINDER_CLASS_NAME_ENTITY,
				"fetchByG_P_N",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					String.class.getName()
				},
				LayoutSetBranchModelImpl.GROUPID_COLUMN_BITMASK |
				LayoutSetBranchModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
				LayoutSetBranchModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByG_P_N = new FinderPath(LayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_N",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					String.class.getName()
				});

		_finderPathWithPaginationFindByG_P_M = new FinderPath(LayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetBranchImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_M",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					Boolean.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByG_P_M = new FinderPath(LayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetBranchImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P_M",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					Boolean.class.getName()
				},
				LayoutSetBranchModelImpl.GROUPID_COLUMN_BITMASK |
				LayoutSetBranchModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
				LayoutSetBranchModelImpl.MASTER_COLUMN_BITMASK |
				LayoutSetBranchModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByG_P_M = new FinderPath(LayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_M",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					Boolean.class.getName()
				});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(LayoutSetBranchImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	private static final String _SQL_SELECT_LAYOUTSETBRANCH = "SELECT layoutSetBranch FROM LayoutSetBranch layoutSetBranch";
	private static final String _SQL_SELECT_LAYOUTSETBRANCH_WHERE = "SELECT layoutSetBranch FROM LayoutSetBranch layoutSetBranch WHERE ";
	private static final String _SQL_COUNT_LAYOUTSETBRANCH = "SELECT COUNT(layoutSetBranch) FROM LayoutSetBranch layoutSetBranch";
	private static final String _SQL_COUNT_LAYOUTSETBRANCH_WHERE = "SELECT COUNT(layoutSetBranch) FROM LayoutSetBranch layoutSetBranch WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "layoutSetBranch.layoutSetBranchId";
	private static final String _FILTER_SQL_SELECT_LAYOUTSETBRANCH_WHERE = "SELECT DISTINCT {layoutSetBranch.*} FROM LayoutSetBranch layoutSetBranch WHERE ";
	private static final String _FILTER_SQL_SELECT_LAYOUTSETBRANCH_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {LayoutSetBranch.*} FROM (SELECT DISTINCT layoutSetBranch.layoutSetBranchId FROM LayoutSetBranch layoutSetBranch WHERE ";
	private static final String _FILTER_SQL_SELECT_LAYOUTSETBRANCH_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN LayoutSetBranch ON TEMP_TABLE.layoutSetBranchId = LayoutSetBranch.layoutSetBranchId";
	private static final String _FILTER_SQL_COUNT_LAYOUTSETBRANCH_WHERE = "SELECT COUNT(DISTINCT layoutSetBranch.layoutSetBranchId) AS COUNT_VALUE FROM LayoutSetBranch layoutSetBranch WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "layoutSetBranch";
	private static final String _FILTER_ENTITY_TABLE = "LayoutSetBranch";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutSetBranch.";
	private static final String _ORDER_BY_ENTITY_TABLE = "LayoutSetBranch.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LayoutSetBranch exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LayoutSetBranch exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(LayoutSetBranchPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"settings"
			});
}