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
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
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
	extends BasePersistenceImpl<ResourceAction, NoSuchResourceActionException>
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
	private CollectionPersistenceFinder<ResourceAction>
		_collectionPersistenceFinderByName;

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

		return _collectionPersistenceFinderByName.find(
			FinderCacheUtil.getFinderCache(), new Object[] {name}, start, end,
			orderByComparator, useFinderCache);
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

		throw new NoSuchResourceActionException(
			_collectionPersistenceFinderByName.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {name}));
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

		return _collectionPersistenceFinderByName.fetchFirst(
			FinderCacheUtil.getFinderCache(), new Object[] {name},
			orderByComparator);
	}

	/**
	 * Removes all the resource actions where name = &#63; from the database.
	 *
	 * @param name the name
	 */
	@Override
	public void removeByName(String name) {
		_collectionPersistenceFinderByName.remove(
			FinderCacheUtil.getFinderCache(), new Object[] {name});
	}

	/**
	 * Returns the number of resource actions where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching resource actions
	 */
	@Override
	public int countByName(String name) {
		return _collectionPersistenceFinderByName.count(
			FinderCacheUtil.getFinderCache(), new Object[] {name});
	}

	private FinderPath _finderPathFetchByN_A;
	private UniquePersistenceFinder<ResourceAction>
		_uniquePersistenceFinderByN_A;

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
			String message =
				_uniquePersistenceFinderByN_A.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {name, actionId});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchResourceActionException(message);
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

		return _uniquePersistenceFinderByN_A.fetch(
			FinderCacheUtil.getFinderCache(), new Object[] {name, actionId},
			useFinderCache);
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
		return _uniquePersistenceFinderByN_A.count(
			FinderCacheUtil.getFinderCache(), new Object[] {name, actionId});
	}

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

	protected void cacheUniqueFindersCache(
		ResourceActionModelImpl resourceActionModelImpl) {

		Object[] args = new Object[] {
			resourceActionModelImpl.getName(),
			resourceActionModelImpl.getActionId()
		};

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

		_collectionPersistenceFinderByName = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByName,
			_finderPathWithoutPaginationFindByName, _finderPathCountByName,
			_SQL_SELECT_RESOURCEACTION_WHERE, _SQL_COUNT_RESOURCEACTION_WHERE,
			ResourceActionModelImpl.ORDER_BY_JPQL, _ORDER_BY_ENTITY_ALIAS,
			new FinderColumn<>(
				"resourceAction.", "name", FinderColumn.Type.STRING, "=", true,
				true, ResourceAction::getName));

		_finderPathFetchByN_A = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByN_A",
			new String[] {String.class.getName(), String.class.getName()},
			new String[] {"name", "actionId"}, true);

		_uniquePersistenceFinderByN_A = new UniquePersistenceFinder<>(
			this, _finderPathFetchByN_A, _SQL_SELECT_RESOURCEACTION_WHERE,
			new FinderColumn<>(
				"resourceAction.", "name", FinderColumn.Type.STRING, "=", true,
				false, ResourceAction::getName),
			new FinderColumn<>(
				"resourceAction.", "actionId", FinderColumn.Type.STRING, "=",
				true, true, ResourceAction::getActionId));

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

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ResourceAction exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ResourceActionPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:715350343