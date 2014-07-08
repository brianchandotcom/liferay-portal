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

import com.liferay.portal.NoSuchResourceActionException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.impl.ResourceActionImpl;
import com.liferay.portal.model.impl.ResourceActionModelImpl;
import com.liferay.portal.service.persistence.ResourceActionPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the resource action service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceActionPersistence
 * @see ResourceActionUtil
 * @generated
 */
public class ResourceActionPersistenceImpl extends BasePersistenceImpl<ResourceAction>
	implements ResourceActionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ResourceActionUtil} to access the resource action persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ResourceActionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceActionModelImpl.FINDER_CACHE_ENABLED,
			ResourceActionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceActionModelImpl.FINDER_CACHE_ENABLED,
			ResourceActionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceActionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_NAME = new FinderPath(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceActionModelImpl.FINDER_CACHE_ENABLED,
			ResourceActionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByName",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NAME = new FinderPath(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceActionModelImpl.FINDER_CACHE_ENABLED,
			ResourceActionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByName",
			new String[] { String.class.getName() },
			ResourceActionModelImpl.NAME_COLUMN_BITMASK |
			ResourceActionModelImpl.BITWISEVALUE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_NAME = new FinderPath(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceActionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByName",
			new String[] { String.class.getName() });

	/**
	 * Returns all the resource actions where name = &#63;.
	 *
	 * @param name the name
	 * @return the matching resource actions
	 */
	@Override
	public List<ResourceAction> findByName(String name) {
		return findByName(name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource actions where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of resource actions
	 * @param end the upper bound of the range of resource actions (not inclusive)
	 * @return the range of matching resource actions
	 */
	@Override
	public List<ResourceAction> findByName(String name, int start, int end) {
		return findByName(name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource actions where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of resource actions
	 * @param end the upper bound of the range of resource actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource actions
	 */
	@Override
	public List<ResourceAction> findByName(String name, int start, int end,
		OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NAME;
			finderArgs = new Object[] { name };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_NAME;
			finderArgs = new Object[] { name, start, end, orderByComparator };
		}

		List<ResourceAction> list = (List<ResourceAction>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ResourceAction resourceAction : list) {
				if (!Validator.equals(name, resourceAction.getName())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_RESOURCEACTION_WHERE);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_NAME_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ResourceActionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindName) {
					qPos.add(name);
				}

				if (!pagination) {
					list = (List<ResourceAction>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ResourceAction>)QueryUtil.list(q,
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
	 * Returns the first resource action in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource action
	 * @throws com.liferay.portal.NoSuchResourceActionException if a matching resource action could not be found
	 */
	@Override
	public ResourceAction findByName_First(String name,
		OrderByComparator orderByComparator)
		throws NoSuchResourceActionException {
		ResourceAction resourceAction = fetchByName_First(name,
				orderByComparator);

		if (resourceAction != null) {
			return resourceAction;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchResourceActionException(msg.toString());
	}

	/**
	 * Returns the first resource action in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource action, or <code>null</code> if a matching resource action could not be found
	 */
	@Override
	public ResourceAction fetchByName_First(String name,
		OrderByComparator orderByComparator) {
		List<ResourceAction> list = findByName(name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last resource action in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource action
	 * @throws com.liferay.portal.NoSuchResourceActionException if a matching resource action could not be found
	 */
	@Override
	public ResourceAction findByName_Last(String name,
		OrderByComparator orderByComparator)
		throws NoSuchResourceActionException {
		ResourceAction resourceAction = fetchByName_Last(name, orderByComparator);

		if (resourceAction != null) {
			return resourceAction;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchResourceActionException(msg.toString());
	}

	/**
	 * Returns the last resource action in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource action, or <code>null</code> if a matching resource action could not be found
	 */
	@Override
	public ResourceAction fetchByName_Last(String name,
		OrderByComparator orderByComparator) {
		int count = countByName(name);

		if (count == 0) {
			return null;
		}

		List<ResourceAction> list = findByName(name, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the resource actions before and after the current resource action in the ordered set where name = &#63;.
	 *
	 * @param resourceActionId the primary key of the current resource action
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next resource action
	 * @throws com.liferay.portal.NoSuchResourceActionException if a resource action with the primary key could not be found
	 */
	@Override
	public ResourceAction[] findByName_PrevAndNext(long resourceActionId,
		String name, OrderByComparator orderByComparator)
		throws NoSuchResourceActionException {
		ResourceAction resourceAction = findByPrimaryKey(resourceActionId);

		Session session = null;

		try {
			session = openSession();

			ResourceAction[] array = new ResourceActionImpl[3];

			array[0] = getByName_PrevAndNext(session, resourceAction, name,
					orderByComparator, true);

			array[1] = resourceAction;

			array[2] = getByName_PrevAndNext(session, resourceAction, name,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ResourceAction getByName_PrevAndNext(Session session,
		ResourceAction resourceAction, String name,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RESOURCEACTION_WHERE);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_NAME_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_NAME_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_NAME_NAME_2);
		}

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
			query.append(ResourceActionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindName) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(resourceAction);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ResourceAction> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the resource actions where name = &#63; from the database.
	 *
	 * @param name the name
	 */
	@Override
	public void removeByName(String name) {
		for (ResourceAction resourceAction : findByName(name,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(resourceAction);
		}
	}

	/**
	 * Returns the number of resource actions where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching resource actions
	 */
	@Override
	public int countByName(String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_NAME;

		Object[] finderArgs = new Object[] { name };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_RESOURCEACTION_WHERE);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_NAME_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

	private static final String _FINDER_COLUMN_NAME_NAME_1 = "resourceAction.name IS NULL";
	private static final String _FINDER_COLUMN_NAME_NAME_2 = "resourceAction.name = ?";
	private static final String _FINDER_COLUMN_NAME_NAME_3 = "(resourceAction.name IS NULL OR resourceAction.name = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_N_A = new FinderPath(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceActionModelImpl.FINDER_CACHE_ENABLED,
			ResourceActionImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByN_A",
			new String[] { String.class.getName(), String.class.getName() },
			ResourceActionModelImpl.NAME_COLUMN_BITMASK |
			ResourceActionModelImpl.ACTIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_N_A = new FinderPath(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceActionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_A",
			new String[] { String.class.getName(), String.class.getName() });

	/**
	 * Returns the resource action where name = &#63; and actionId = &#63; or throws a {@link com.liferay.portal.NoSuchResourceActionException} if it could not be found.
	 *
	 * @param name the name
	 * @param actionId the action ID
	 * @return the matching resource action
	 * @throws com.liferay.portal.NoSuchResourceActionException if a matching resource action could not be found
	 */
	@Override
	public ResourceAction findByN_A(String name, String actionId)
		throws NoSuchResourceActionException {
		ResourceAction resourceAction = fetchByN_A(name, actionId);

		if (resourceAction == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("name=");
			msg.append(name);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchResourceActionException(msg.toString());
		}

		return resourceAction;
	}

	/**
	 * Returns the resource action where name = &#63; and actionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @param actionId the action ID
	 * @return the matching resource action, or <code>null</code> if a matching resource action could not be found
	 */
	@Override
	public ResourceAction fetchByN_A(String name, String actionId) {
		return fetchByN_A(name, actionId, true);
	}

	/**
	 * Returns the resource action where name = &#63; and actionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param actionId the action ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching resource action, or <code>null</code> if a matching resource action could not be found
	 */
	@Override
	public ResourceAction fetchByN_A(String name, String actionId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { name, actionId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_N_A,
					finderArgs, this);
		}

		if (result instanceof ResourceAction) {
			ResourceAction resourceAction = (ResourceAction)result;

			if (!Validator.equals(name, resourceAction.getName()) ||
					!Validator.equals(actionId, resourceAction.getActionId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_RESOURCEACTION_WHERE);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_N_A_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_A_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_N_A_NAME_2);
			}

			boolean bindActionId = false;

			if (actionId == null) {
				query.append(_FINDER_COLUMN_N_A_ACTIONID_1);
			}
			else if (actionId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_A_ACTIONID_3);
			}
			else {
				bindActionId = true;

				query.append(_FINDER_COLUMN_N_A_ACTIONID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindName) {
					qPos.add(name);
				}

				if (bindActionId) {
					qPos.add(actionId);
				}

				List<ResourceAction> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_N_A,
						finderArgs, list);
				}
				else {
					ResourceAction resourceAction = list.get(0);

					result = resourceAction;

					cacheResult(resourceAction);

					if ((resourceAction.getName() == null) ||
							!resourceAction.getName().equals(name) ||
							(resourceAction.getActionId() == null) ||
							!resourceAction.getActionId().equals(actionId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_N_A,
							finderArgs, resourceAction);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_N_A,
					finderArgs);

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
			return (ResourceAction)result;
		}
	}

	/**
	 * Removes the resource action where name = &#63; and actionId = &#63; from the database.
	 *
	 * @param name the name
	 * @param actionId the action ID
	 * @return the resource action that was removed
	 */
	@Override
	public ResourceAction removeByN_A(String name, String actionId)
		throws NoSuchResourceActionException {
		ResourceAction resourceAction = findByN_A(name, actionId);

		return remove(resourceAction);
	}

	/**
	 * Returns the number of resource actions where name = &#63; and actionId = &#63;.
	 *
	 * @param name the name
	 * @param actionId the action ID
	 * @return the number of matching resource actions
	 */
	@Override
	public int countByN_A(String name, String actionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_N_A;

		Object[] finderArgs = new Object[] { name, actionId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_RESOURCEACTION_WHERE);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_N_A_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_A_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_N_A_NAME_2);
			}

			boolean bindActionId = false;

			if (actionId == null) {
				query.append(_FINDER_COLUMN_N_A_ACTIONID_1);
			}
			else if (actionId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_A_ACTIONID_3);
			}
			else {
				bindActionId = true;

				query.append(_FINDER_COLUMN_N_A_ACTIONID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindName) {
					qPos.add(name);
				}

				if (bindActionId) {
					qPos.add(actionId);
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

	private static final String _FINDER_COLUMN_N_A_NAME_1 = "resourceAction.name IS NULL AND ";
	private static final String _FINDER_COLUMN_N_A_NAME_2 = "resourceAction.name = ? AND ";
	private static final String _FINDER_COLUMN_N_A_NAME_3 = "(resourceAction.name IS NULL OR resourceAction.name = '') AND ";
	private static final String _FINDER_COLUMN_N_A_ACTIONID_1 = "resourceAction.actionId IS NULL";
	private static final String _FINDER_COLUMN_N_A_ACTIONID_2 = "resourceAction.actionId = ?";
	private static final String _FINDER_COLUMN_N_A_ACTIONID_3 = "(resourceAction.actionId IS NULL OR resourceAction.actionId = '')";

	public ResourceActionPersistenceImpl() {
		setModelClass(ResourceAction.class);
	}

	/**
	 * Caches the resource action in the entity cache if it is enabled.
	 *
	 * @param resourceAction the resource action
	 */
	@Override
	public void cacheResult(ResourceAction resourceAction) {
		EntityCacheUtil.putResult(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceActionImpl.class, resourceAction.getPrimaryKey(),
			resourceAction);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_N_A,
			new Object[] { resourceAction.getName(), resourceAction.getActionId() },
			resourceAction);

		resourceAction.resetOriginalValues();
	}

	/**
	 * Caches the resource actions in the entity cache if it is enabled.
	 *
	 * @param resourceActions the resource actions
	 */
	@Override
	public void cacheResult(List<ResourceAction> resourceActions) {
		for (ResourceAction resourceAction : resourceActions) {
			if (EntityCacheUtil.getResult(
						ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
						ResourceActionImpl.class, resourceAction.getPrimaryKey()) == null) {
				cacheResult(resourceAction);
			}
			else {
				resourceAction.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all resource actions.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ResourceActionImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ResourceActionImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the resource action.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ResourceAction resourceAction) {
		EntityCacheUtil.removeResult(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceActionImpl.class, resourceAction.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(resourceAction);
	}

	@Override
	public void clearCache(List<ResourceAction> resourceActions) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ResourceAction resourceAction : resourceActions) {
			EntityCacheUtil.removeResult(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceActionImpl.class, resourceAction.getPrimaryKey());

			clearUniqueFindersCache(resourceAction);
		}
	}

	protected void cacheUniqueFindersCache(ResourceAction resourceAction) {
		if (resourceAction.isNew()) {
			Object[] args = new Object[] {
					resourceAction.getName(), resourceAction.getActionId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_N_A, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_N_A, args,
				resourceAction);
		}
		else {
			ResourceActionModelImpl resourceActionModelImpl = (ResourceActionModelImpl)resourceAction;

			if ((resourceActionModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_N_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						resourceAction.getName(), resourceAction.getActionId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_N_A, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_N_A, args,
					resourceAction);
			}
		}
	}

	protected void clearUniqueFindersCache(ResourceAction resourceAction) {
		ResourceActionModelImpl resourceActionModelImpl = (ResourceActionModelImpl)resourceAction;

		Object[] args = new Object[] {
				resourceAction.getName(), resourceAction.getActionId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_N_A, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_N_A, args);

		if ((resourceActionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_N_A.getColumnBitmask()) != 0) {
			args = new Object[] {
					resourceActionModelImpl.getOriginalName(),
					resourceActionModelImpl.getOriginalActionId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_N_A, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_N_A, args);
		}
	}

	/**
	 * Creates a new resource action with the primary key. Does not add the resource action to the database.
	 *
	 * @param resourceActionId the primary key for the new resource action
	 * @return the new resource action
	 */
	@Override
	public ResourceAction create(long resourceActionId) {
		ResourceAction resourceAction = new ResourceActionImpl();

		resourceAction.setNew(true);
		resourceAction.setPrimaryKey(resourceActionId);

		return resourceAction;
	}

	/**
	 * Removes the resource action with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourceActionId the primary key of the resource action
	 * @return the resource action that was removed
	 * @throws com.liferay.portal.NoSuchResourceActionException if a resource action with the primary key could not be found
	 */
	@Override
	public ResourceAction remove(long resourceActionId)
		throws NoSuchResourceActionException {
		return remove((Serializable)resourceActionId);
	}

	/**
	 * Removes the resource action with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the resource action
	 * @return the resource action that was removed
	 * @throws com.liferay.portal.NoSuchResourceActionException if a resource action with the primary key could not be found
	 */
	@Override
	public ResourceAction remove(Serializable primaryKey)
		throws NoSuchResourceActionException {
		Session session = null;

		try {
			session = openSession();

			ResourceAction resourceAction = (ResourceAction)session.get(ResourceActionImpl.class,
					primaryKey);

			if (resourceAction == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchResourceActionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(resourceAction);
		}
		catch (NoSuchResourceActionException nsee) {
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
	protected ResourceAction removeImpl(ResourceAction resourceAction) {
		resourceAction = toUnwrappedModel(resourceAction);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(resourceAction)) {
				resourceAction = (ResourceAction)session.get(ResourceActionImpl.class,
						resourceAction.getPrimaryKeyObj());
			}

			if (resourceAction != null) {
				session.delete(resourceAction);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (resourceAction != null) {
			clearCache(resourceAction);
		}

		return resourceAction;
	}

	@Override
	public ResourceAction updateImpl(
		com.liferay.portal.model.ResourceAction resourceAction) {
		resourceAction = toUnwrappedModel(resourceAction);

		boolean isNew = resourceAction.isNew();

		ResourceActionModelImpl resourceActionModelImpl = (ResourceActionModelImpl)resourceAction;

		Session session = null;

		try {
			session = openSession();

			if (resourceAction.isNew()) {
				session.save(resourceAction);

				resourceAction.setNew(false);
			}
			else {
				session.merge(resourceAction);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ResourceActionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((resourceActionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NAME.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						resourceActionModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_NAME, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NAME,
					args);

				args = new Object[] { resourceActionModelImpl.getName() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_NAME, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NAME,
					args);
			}
		}

		EntityCacheUtil.putResult(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceActionImpl.class, resourceAction.getPrimaryKey(),
			resourceAction, false);

		clearUniqueFindersCache(resourceAction);
		cacheUniqueFindersCache(resourceAction);

		resourceAction.resetOriginalValues();

		return resourceAction;
	}

	protected ResourceAction toUnwrappedModel(ResourceAction resourceAction) {
		if (resourceAction instanceof ResourceActionImpl) {
			return resourceAction;
		}

		ResourceActionImpl resourceActionImpl = new ResourceActionImpl();

		resourceActionImpl.setNew(resourceAction.isNew());
		resourceActionImpl.setPrimaryKey(resourceAction.getPrimaryKey());

		resourceActionImpl.setMvccVersion(resourceAction.getMvccVersion());
		resourceActionImpl.setResourceActionId(resourceAction.getResourceActionId());
		resourceActionImpl.setName(resourceAction.getName());
		resourceActionImpl.setActionId(resourceAction.getActionId());
		resourceActionImpl.setBitwiseValue(resourceAction.getBitwiseValue());

		return resourceActionImpl;
	}

	/**
	 * Returns the resource action with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the resource action
	 * @return the resource action
	 * @throws com.liferay.portal.NoSuchResourceActionException if a resource action with the primary key could not be found
	 */
	@Override
	public ResourceAction findByPrimaryKey(Serializable primaryKey)
		throws NoSuchResourceActionException {
		ResourceAction resourceAction = fetchByPrimaryKey(primaryKey);

		if (resourceAction == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchResourceActionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return resourceAction;
	}

	/**
	 * Returns the resource action with the primary key or throws a {@link com.liferay.portal.NoSuchResourceActionException} if it could not be found.
	 *
	 * @param resourceActionId the primary key of the resource action
	 * @return the resource action
	 * @throws com.liferay.portal.NoSuchResourceActionException if a resource action with the primary key could not be found
	 */
	@Override
	public ResourceAction findByPrimaryKey(long resourceActionId)
		throws NoSuchResourceActionException {
		return findByPrimaryKey((Serializable)resourceActionId);
	}

	/**
	 * Returns the resource action with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the resource action
	 * @return the resource action, or <code>null</code> if a resource action with the primary key could not be found
	 */
	@Override
	public ResourceAction fetchByPrimaryKey(Serializable primaryKey) {
		ResourceAction resourceAction = (ResourceAction)EntityCacheUtil.getResult(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceActionImpl.class, primaryKey);

		if (resourceAction == _nullResourceAction) {
			return null;
		}

		if (resourceAction == null) {
			Session session = null;

			try {
				session = openSession();

				resourceAction = (ResourceAction)session.get(ResourceActionImpl.class,
						primaryKey);

				if (resourceAction != null) {
					cacheResult(resourceAction);
				}
				else {
					EntityCacheUtil.putResult(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
						ResourceActionImpl.class, primaryKey,
						_nullResourceAction);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
					ResourceActionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return resourceAction;
	}

	/**
	 * Returns the resource action with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param resourceActionId the primary key of the resource action
	 * @return the resource action, or <code>null</code> if a resource action with the primary key could not be found
	 */
	@Override
	public ResourceAction fetchByPrimaryKey(long resourceActionId) {
		return fetchByPrimaryKey((Serializable)resourceActionId);
	}

	@Override
	public Map<Serializable, ResourceAction> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ResourceAction> map = new HashMap<Serializable, ResourceAction>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ResourceAction resourceAction = fetchByPrimaryKey(primaryKey);

			if (resourceAction != null) {
				map.put(primaryKey, resourceAction);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			ResourceAction resourceAction = (ResourceAction)EntityCacheUtil.getResult(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
					ResourceActionImpl.class, primaryKey);

			if (resourceAction == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, resourceAction);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_RESOURCEACTION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (ResourceAction resourceAction : (List<ResourceAction>)q.list()) {
				map.put(resourceAction.getPrimaryKeyObj(), resourceAction);

				cacheResult(resourceAction);

				uncachedPrimaryKeys.remove(resourceAction.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(ResourceActionModelImpl.ENTITY_CACHE_ENABLED,
					ResourceActionImpl.class, primaryKey, _nullResourceAction);
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
	 * Returns all the resource actions.
	 *
	 * @return the resource actions
	 */
	@Override
	public List<ResourceAction> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource actions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource actions
	 * @param end the upper bound of the range of resource actions (not inclusive)
	 * @return the range of resource actions
	 */
	@Override
	public List<ResourceAction> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource actions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource actions
	 * @param end the upper bound of the range of resource actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of resource actions
	 */
	@Override
	public List<ResourceAction> findAll(int start, int end,
		OrderByComparator orderByComparator) {
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

		List<ResourceAction> list = (List<ResourceAction>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_RESOURCEACTION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_RESOURCEACTION;

				if (pagination) {
					sql = sql.concat(ResourceActionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ResourceAction>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ResourceAction>)QueryUtil.list(q,
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
	 * Removes all the resource actions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ResourceAction resourceAction : findAll()) {
			remove(resourceAction);
		}
	}

	/**
	 * Returns the number of resource actions.
	 *
	 * @return the number of resource actions
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_RESOURCEACTION);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the resource action persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.ResourceAction")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ResourceAction>> listenersList = new ArrayList<ModelListener<ResourceAction>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ResourceAction>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ResourceActionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_RESOURCEACTION = "SELECT resourceAction FROM ResourceAction resourceAction";
	private static final String _SQL_SELECT_RESOURCEACTION_WHERE_PKS_IN = "SELECT resourceAction FROM ResourceAction resourceAction WHERE resourceActionId IN (";
	private static final String _SQL_SELECT_RESOURCEACTION_WHERE = "SELECT resourceAction FROM ResourceAction resourceAction WHERE ";
	private static final String _SQL_COUNT_RESOURCEACTION = "SELECT COUNT(resourceAction) FROM ResourceAction resourceAction";
	private static final String _SQL_COUNT_RESOURCEACTION_WHERE = "SELECT COUNT(resourceAction) FROM ResourceAction resourceAction WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "resourceAction.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ResourceAction exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ResourceAction exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(ResourceActionPersistenceImpl.class);
	private static ResourceAction _nullResourceAction = new ResourceActionImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ResourceAction> toCacheModel() {
				return _nullResourceActionCacheModel;
			}
		};

	private static CacheModel<ResourceAction> _nullResourceActionCacheModel = new NullCacheModel();

	private static class NullCacheModel implements CacheModel<ResourceAction>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return 0;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public ResourceAction toEntityModel() {
			return _nullResourceAction;
		}
	}
}