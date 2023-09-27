/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchResourceActionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceActionTable;
import com.liferay.portal.kernel.service.persistence.ResourceActionPersistence;
import com.liferay.portal.kernel.service.persistence.ResourceActionUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.impl.ResourceActionImpl;
import com.liferay.portal.model.impl.ResourceActionModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the resource action service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ResourceActionPersistenceImpl
	extends BasePersistenceImpl<ResourceAction>
	implements ResourceActionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ResourceActionUtil</code> to access the resource action persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ResourceActionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByName;
	private FinderPath _finderPathWithoutPaginationFindByName;
	private FinderPath _finderPathCountByName;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourceActionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourceActionModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of resource actions
	 * @param end the upper bound of the range of resource actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource actions
	 */
	@Override
	public List<ResourceAction> findByName(
		String name, int start, int end,
		OrderByComparator<ResourceAction> orderByComparator) {

		return findByName(name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the resource actions where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourceActionModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of resource actions
	 * @param end the upper bound of the range of resource actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching resource actions
	 */
	@Override
	public List<ResourceAction> findByName(
		String name, int start, int end,
		OrderByComparator<ResourceAction> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByName;
				finderArgs = new Object[] {name};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByName;
			finderArgs = new Object[] {name, start, end, orderByComparator};
		}

		List<ResourceAction> list = null;

		if (useFinderCache) {
			list = (List<ResourceAction>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ResourceAction resourceAction : list) {
					if (!name.equals(resourceAction.getName())) {
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

			sb.append(_SQL_SELECT_RESOURCEACTION_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ResourceActionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				list = (List<ResourceAction>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first resource action in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource action
	 * @throws NoSuchResourceActionException if a matching resource action could not be found
	 */
	@Override
	public ResourceAction findByName_First(
			String name, OrderByComparator<ResourceAction> orderByComparator)
		throws NoSuchResourceActionException {

		ResourceAction resourceAction = fetchByName_First(
			name, orderByComparator);

		if (resourceAction != null) {
			return resourceAction;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("name=");
		sb.append(name);

		sb.append("}");

		throw new NoSuchResourceActionException(sb.toString());
	}

	/**
	 * Returns the first resource action in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource action, or <code>null</code> if a matching resource action could not be found
	 */
	@Override
	public ResourceAction fetchByName_First(
		String name, OrderByComparator<ResourceAction> orderByComparator) {

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
	 * @throws NoSuchResourceActionException if a matching resource action could not be found
	 */
	@Override
	public ResourceAction findByName_Last(
			String name, OrderByComparator<ResourceAction> orderByComparator)
		throws NoSuchResourceActionException {

		ResourceAction resourceAction = fetchByName_Last(
			name, orderByComparator);

		if (resourceAction != null) {
			return resourceAction;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("name=");
		sb.append(name);

		sb.append("}");

		throw new NoSuchResourceActionException(sb.toString());
	}

	/**
	 * Returns the last resource action in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource action, or <code>null</code> if a matching resource action could not be found
	 */
	@Override
	public ResourceAction fetchByName_Last(
		String name, OrderByComparator<ResourceAction> orderByComparator) {

		int count = countByName(name);

		if (count == 0) {
			return null;
		}

		List<ResourceAction> list = findByName(
			name, count - 1, count, orderByComparator);

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
	 * @throws NoSuchResourceActionException if a resource action with the primary key could not be found
	 */
	@Override
	public ResourceAction[] findByName_PrevAndNext(
			long resourceActionId, String name,
			OrderByComparator<ResourceAction> orderByComparator)
		throws NoSuchResourceActionException {

		name = Objects.toString(name, "");

		ResourceAction resourceAction = findByPrimaryKey(resourceActionId);

		Session session = null;

		try {
			session = openSession();

			ResourceAction[] array = new ResourceActionImpl[3];

			array[0] = getByName_PrevAndNext(
				session, resourceAction, name, orderByComparator, true);

			array[1] = resourceAction;

			array[2] = getByName_PrevAndNext(
				session, resourceAction, name, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ResourceAction getByName_PrevAndNext(
		Session session, ResourceAction resourceAction, String name,
		OrderByComparator<ResourceAction> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_RESOURCEACTION_WHERE);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_NAME_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_NAME_NAME_2);
		}

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
			sb.append(ResourceActionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindName) {
			queryPos.add(name);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						resourceAction)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ResourceAction> list = query.list();

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
		for (ResourceAction resourceAction :
				findByName(name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

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
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByName;

		Object[] finderArgs = new Object[] {name};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_RESOURCEACTION_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_NAME_NAME_2 =
		"resourceAction.name = ?";

	private static final String _FINDER_COLUMN_NAME_NAME_3 =
		"(resourceAction.name IS NULL OR resourceAction.name = '')";

	private FinderPath _finderPathFetchByN_A;
	private FinderPath _finderPathCountByN_A;

	/**
	 * Returns the resource action where name = &#63; and actionId = &#63; or throws a <code>NoSuchResourceActionException</code> if it could not be found.
	 *
	 * @param name the name
	 * @param actionId the action ID
	 * @return the matching resource action
	 * @throws NoSuchResourceActionException if a matching resource action could not be found
	 */
	@Override
	public ResourceAction findByN_A(String name, String actionId)
		throws NoSuchResourceActionException {

		ResourceAction resourceAction = fetchByN_A(name, actionId);

		if (resourceAction == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("name=");
			sb.append(name);

			sb.append(", actionId=");
			sb.append(actionId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchResourceActionException(sb.toString());
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
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching resource action, or <code>null</code> if a matching resource action could not be found
	 */
	@Override
	public ResourceAction fetchByN_A(
		String name, String actionId, boolean useFinderCache) {

		name = Objects.toString(name, "");
		actionId = Objects.toString(actionId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {name, actionId};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByN_A, finderArgs, this);
		}

		if (result instanceof ResourceAction) {
			ResourceAction resourceAction = (ResourceAction)result;

			if (!Objects.equals(name, resourceAction.getName()) ||
				!Objects.equals(actionId, resourceAction.getActionId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_RESOURCEACTION_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_N_A_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_N_A_NAME_2);
			}

			boolean bindActionId = false;

			if (actionId.isEmpty()) {
				sb.append(_FINDER_COLUMN_N_A_ACTIONID_3);
			}
			else {
				bindActionId = true;

				sb.append(_FINDER_COLUMN_N_A_ACTIONID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindActionId) {
					queryPos.add(actionId);
				}

				List<ResourceAction> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByN_A, finderArgs, list);
					}
				}
				else {
					ResourceAction resourceAction = list.get(0);

					result = resourceAction;

					cacheResult(resourceAction);
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
		name = Objects.toString(name, "");
		actionId = Objects.toString(actionId, "");

		FinderPath finderPath = _finderPathCountByN_A;

		Object[] finderArgs = new Object[] {name, actionId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_RESOURCEACTION_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_N_A_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_N_A_NAME_2);
			}

			boolean bindActionId = false;

			if (actionId.isEmpty()) {
				sb.append(_FINDER_COLUMN_N_A_ACTIONID_3);
			}
			else {
				bindActionId = true;

				sb.append(_FINDER_COLUMN_N_A_ACTIONID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindActionId) {
					queryPos.add(actionId);
				}

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_N_A_NAME_2 =
		"resourceAction.name = ? AND ";

	private static final String _FINDER_COLUMN_N_A_NAME_3 =
		"(resourceAction.name IS NULL OR resourceAction.name = '') AND ";

	private static final String _FINDER_COLUMN_N_A_ACTIONID_2 =
		"resourceAction.actionId = ?";

	private static final String _FINDER_COLUMN_N_A_ACTIONID_3 =
		"(resourceAction.actionId IS NULL OR resourceAction.actionId = '')";

	public ResourceActionPersistenceImpl() {
		setModelClass(ResourceAction.class);

		setModelImplClass(ResourceActionImpl.class);
		setModelPKClass(long.class);

		setTable(ResourceActionTable.INSTANCE);
	}

	/**
	 * Caches the resource action in the entity cache if it is enabled.
	 *
	 * @param resourceAction the resource action
	 */
	@Override
	public void cacheResult(ResourceAction resourceAction) {
		EntityCacheUtil.putResult(
			ResourceActionImpl.class, resourceAction.getPrimaryKey(),
			resourceAction);

		FinderCacheUtil.putResult(
			_finderPathFetchByN_A,
			new Object[] {
				resourceAction.getName(), resourceAction.getActionId()
			},
			resourceAction);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the resource actions in the entity cache if it is enabled.
	 *
	 * @param resourceActions the resource actions
	 */
	@Override
	public void cacheResult(List<ResourceAction> resourceActions) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (resourceActions.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ResourceAction resourceAction : resourceActions) {
			if (EntityCacheUtil.getResult(
					ResourceActionImpl.class, resourceAction.getPrimaryKey()) ==
						null) {

				cacheResult(resourceAction);
			}
		}
	}

	/**
	 * Clears the cache for all resource actions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(ResourceActionImpl.class);

		FinderCacheUtil.clearCache(ResourceActionImpl.class);
	}

	/**
	 * Clears the cache for the resource action.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ResourceAction resourceAction) {
		EntityCacheUtil.removeResult(ResourceActionImpl.class, resourceAction);
	}

	@Override
	public void clearCache(List<ResourceAction> resourceActions) {
		for (ResourceAction resourceAction : resourceActions) {
			EntityCacheUtil.removeResult(
				ResourceActionImpl.class, resourceAction);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(ResourceActionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(ResourceActionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ResourceActionModelImpl resourceActionModelImpl) {

		Object[] args = new Object[] {
			resourceActionModelImpl.getName(),
			resourceActionModelImpl.getActionId()
		};

		FinderCacheUtil.putResult(_finderPathCountByN_A, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByN_A, args, resourceActionModelImpl);
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
	 * @throws NoSuchResourceActionException if a resource action with the primary key could not be found
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
	 * @throws NoSuchResourceActionException if a resource action with the primary key could not be found
	 */
	@Override
	public ResourceAction remove(Serializable primaryKey)
		throws NoSuchResourceActionException {

		Session session = null;

		try {
			session = openSession();

			ResourceAction resourceAction = (ResourceAction)session.get(
				ResourceActionImpl.class, primaryKey);

			if (resourceAction == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchResourceActionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(resourceAction);
		}
		catch (NoSuchResourceActionException noSuchEntityException) {
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
	protected ResourceAction removeImpl(ResourceAction resourceAction) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(resourceAction)) {
				resourceAction = (ResourceAction)session.get(
					ResourceActionImpl.class,
					resourceAction.getPrimaryKeyObj());
			}

			if (resourceAction != null) {
				session.delete(resourceAction);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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
	public ResourceAction updateImpl(ResourceAction resourceAction) {
		boolean isNew = resourceAction.isNew();

		if (!(resourceAction instanceof ResourceActionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(resourceAction.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					resourceAction);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in resourceAction proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ResourceAction implementation " +
					resourceAction.getClass());
		}

		ResourceActionModelImpl resourceActionModelImpl =
			(ResourceActionModelImpl)resourceAction;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(resourceAction);
			}
			else {
				resourceAction = (ResourceAction)session.merge(resourceAction);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			ResourceActionImpl.class, resourceActionModelImpl, false, true);

		cacheUniqueFindersCache(resourceActionModelImpl);

		if (isNew) {
			resourceAction.setNew(false);
		}

		resourceAction.resetOriginalValues();

		return resourceAction;
	}

	/**
	 * Returns the resource action with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the resource action
	 * @return the resource action
	 * @throws NoSuchResourceActionException if a resource action with the primary key could not be found
	 */
	@Override
	public ResourceAction findByPrimaryKey(Serializable primaryKey)
		throws NoSuchResourceActionException {

		ResourceAction resourceAction = fetchByPrimaryKey(primaryKey);

		if (resourceAction == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchResourceActionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return resourceAction;
	}

	/**
	 * Returns the resource action with the primary key or throws a <code>NoSuchResourceActionException</code> if it could not be found.
	 *
	 * @param resourceActionId the primary key of the resource action
	 * @return the resource action
	 * @throws NoSuchResourceActionException if a resource action with the primary key could not be found
	 */
	@Override
	public ResourceAction findByPrimaryKey(long resourceActionId)
		throws NoSuchResourceActionException {

		return findByPrimaryKey((Serializable)resourceActionId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourceActionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourceActionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource actions
	 * @param end the upper bound of the range of resource actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of resource actions
	 */
	@Override
	public List<ResourceAction> findAll(
		int start, int end,
		OrderByComparator<ResourceAction> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the resource actions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ResourceActionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource actions
	 * @param end the upper bound of the range of resource actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of resource actions
	 */
	@Override
	public List<ResourceAction> findAll(
		int start, int end, OrderByComparator<ResourceAction> orderByComparator,
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

		List<ResourceAction> list = null;

		if (useFinderCache) {
			list = (List<ResourceAction>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_RESOURCEACTION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_RESOURCEACTION;

				sql = sql.concat(ResourceActionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ResourceAction>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_RESOURCEACTION);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "resourceActionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_RESOURCEACTION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ResourceActionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the resource action persistence.
	 */
	public void afterPropertiesSet() {
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

		_finderPathWithPaginationFindByName = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByName",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"name"}, true);

		_finderPathWithoutPaginationFindByName = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByName",
			new String[] {String.class.getName()}, new String[] {"name"}, true);

		_finderPathCountByName = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByName",
			new String[] {String.class.getName()}, new String[] {"name"},
			false);

		_finderPathFetchByN_A = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByN_A",
			new String[] {String.class.getName(), String.class.getName()},
			new String[] {"name", "actionId"}, true);

		_finderPathCountByN_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_A",
			new String[] {String.class.getName(), String.class.getName()},
			new String[] {"name", "actionId"}, false);

		ResourceActionUtil.setPersistence(this);
	}

	public void destroy() {
		ResourceActionUtil.setPersistence(null);

		EntityCacheUtil.removeCache(ResourceActionImpl.class.getName());
	}

	private static final String _SQL_SELECT_RESOURCEACTION =
		"SELECT resourceAction FROM ResourceAction resourceAction";

	private static final String _SQL_SELECT_RESOURCEACTION_WHERE =
		"SELECT resourceAction FROM ResourceAction resourceAction WHERE ";

	private static final String _SQL_COUNT_RESOURCEACTION =
		"SELECT COUNT(resourceAction) FROM ResourceAction resourceAction";

	private static final String _SQL_COUNT_RESOURCEACTION_WHERE =
		"SELECT COUNT(resourceAction) FROM ResourceAction resourceAction WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "resourceAction.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ResourceAction exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ResourceAction exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ResourceActionPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}