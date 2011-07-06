/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchResourceBlockRoleActionException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.ResourceBlockRoleAction;
import com.liferay.portal.model.impl.ResourceBlockRoleActionImpl;
import com.liferay.portal.model.impl.ResourceBlockRoleActionModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the resource block role action service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockRoleActionPersistence
 * @see ResourceBlockRoleActionUtil
 * @generated
 */
public class ResourceBlockRoleActionPersistenceImpl extends BasePersistenceImpl<ResourceBlockRoleAction>
	implements ResourceBlockRoleActionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ResourceBlockRoleActionUtil} to access the resource block role action persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ResourceBlockRoleActionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_R_A = new FinderPath(ResourceBlockRoleActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockRoleActionModelImpl.FINDER_CACHE_ENABLED,
			ResourceBlockRoleActionImpl.class, FINDER_CLASS_NAME_LIST,
			"findByR_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_R_A = new FinderPath(ResourceBlockRoleActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockRoleActionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByR_A",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ResourceBlockRoleActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockRoleActionModelImpl.FINDER_CACHE_ENABLED,
			ResourceBlockRoleActionImpl.class, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ResourceBlockRoleActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockRoleActionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the resource block role action in the entity cache if it is enabled.
	 *
	 * @param resourceBlockRoleAction the resource block role action
	 */
	public void cacheResult(ResourceBlockRoleAction resourceBlockRoleAction) {
		EntityCacheUtil.putResult(ResourceBlockRoleActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockRoleActionImpl.class,
			resourceBlockRoleAction.getPrimaryKey(), resourceBlockRoleAction);

		resourceBlockRoleAction.resetOriginalValues();
	}

	/**
	 * Caches the resource block role actions in the entity cache if it is enabled.
	 *
	 * @param resourceBlockRoleActions the resource block role actions
	 */
	public void cacheResult(
		List<ResourceBlockRoleAction> resourceBlockRoleActions) {
		for (ResourceBlockRoleAction resourceBlockRoleAction : resourceBlockRoleActions) {
			if (EntityCacheUtil.getResult(
						ResourceBlockRoleActionModelImpl.ENTITY_CACHE_ENABLED,
						ResourceBlockRoleActionImpl.class,
						resourceBlockRoleAction.getPrimaryKey(), this) == null) {
				cacheResult(resourceBlockRoleAction);
			}
		}
	}

	/**
	 * Clears the cache for all resource block role actions.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ResourceBlockRoleActionImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ResourceBlockRoleActionImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the resource block role action.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ResourceBlockRoleAction resourceBlockRoleAction) {
		EntityCacheUtil.removeResult(ResourceBlockRoleActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockRoleActionImpl.class,
			resourceBlockRoleAction.getPrimaryKey());
	}

	/**
	 * Creates a new resource block role action with the primary key. Does not add the resource block role action to the database.
	 *
	 * @param resourceBlockRoleActionPK the primary key for the new resource block role action
	 * @return the new resource block role action
	 */
	public ResourceBlockRoleAction create(
		ResourceBlockRoleActionPK resourceBlockRoleActionPK) {
		ResourceBlockRoleAction resourceBlockRoleAction = new ResourceBlockRoleActionImpl();

		resourceBlockRoleAction.setNew(true);
		resourceBlockRoleAction.setPrimaryKey(resourceBlockRoleActionPK);

		return resourceBlockRoleAction;
	}

	/**
	 * Removes the resource block role action with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the resource block role action
	 * @return the resource block role action that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a resource block role action with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockRoleAction remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove((ResourceBlockRoleActionPK)primaryKey);
	}

	/**
	 * Removes the resource block role action with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourceBlockRoleActionPK the primary key of the resource block role action
	 * @return the resource block role action that was removed
	 * @throws com.liferay.portal.NoSuchResourceBlockRoleActionException if a resource block role action with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceBlockRoleAction remove(
		ResourceBlockRoleActionPK resourceBlockRoleActionPK)
		throws NoSuchResourceBlockRoleActionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ResourceBlockRoleAction resourceBlockRoleAction = (ResourceBlockRoleAction)session.get(ResourceBlockRoleActionImpl.class,
					resourceBlockRoleActionPK);

			if (resourceBlockRoleAction == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						resourceBlockRoleActionPK);
				}

				throw new NoSuchResourceBlockRoleActionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					resourceBlockRoleActionPK);
			}

			return resourceBlockRoleActionPersistence.remove(resourceBlockRoleAction);
		}
		catch (NoSuchResourceBlockRoleActionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes the resource block role action from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourceBlockRoleAction the resource block role action
	 * @return the resource block role action that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockRoleAction remove(
		ResourceBlockRoleAction resourceBlockRoleAction)
		throws SystemException {
		return super.remove(resourceBlockRoleAction);
	}

	@Override
	protected ResourceBlockRoleAction removeImpl(
		ResourceBlockRoleAction resourceBlockRoleAction)
		throws SystemException {
		resourceBlockRoleAction = toUnwrappedModel(resourceBlockRoleAction);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, resourceBlockRoleAction);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(ResourceBlockRoleActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockRoleActionImpl.class,
			resourceBlockRoleAction.getPrimaryKey());

		return resourceBlockRoleAction;
	}

	@Override
	public ResourceBlockRoleAction updateImpl(
		com.liferay.portal.model.ResourceBlockRoleAction resourceBlockRoleAction,
		boolean merge) throws SystemException {
		resourceBlockRoleAction = toUnwrappedModel(resourceBlockRoleAction);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, resourceBlockRoleAction, merge);

			resourceBlockRoleAction.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ResourceBlockRoleActionModelImpl.ENTITY_CACHE_ENABLED,
			ResourceBlockRoleActionImpl.class,
			resourceBlockRoleAction.getPrimaryKey(), resourceBlockRoleAction);

		return resourceBlockRoleAction;
	}

	protected ResourceBlockRoleAction toUnwrappedModel(
		ResourceBlockRoleAction resourceBlockRoleAction) {
		if (resourceBlockRoleAction instanceof ResourceBlockRoleActionImpl) {
			return resourceBlockRoleAction;
		}

		ResourceBlockRoleActionImpl resourceBlockRoleActionImpl = new ResourceBlockRoleActionImpl();

		resourceBlockRoleActionImpl.setNew(resourceBlockRoleAction.isNew());
		resourceBlockRoleActionImpl.setPrimaryKey(resourceBlockRoleAction.getPrimaryKey());

		resourceBlockRoleActionImpl.setActionId(resourceBlockRoleAction.getActionId());
		resourceBlockRoleActionImpl.setResourceBlockId(resourceBlockRoleAction.getResourceBlockId());
		resourceBlockRoleActionImpl.setRoleId(resourceBlockRoleAction.getRoleId());

		return resourceBlockRoleActionImpl;
	}

	/**
	 * Returns the resource block role action with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the resource block role action
	 * @return the resource block role action
	 * @throws com.liferay.portal.NoSuchModelException if a resource block role action with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockRoleAction findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey((ResourceBlockRoleActionPK)primaryKey);
	}

	/**
	 * Returns the resource block role action with the primary key or throws a {@link com.liferay.portal.NoSuchResourceBlockRoleActionException} if it could not be found.
	 *
	 * @param resourceBlockRoleActionPK the primary key of the resource block role action
	 * @return the resource block role action
	 * @throws com.liferay.portal.NoSuchResourceBlockRoleActionException if a resource block role action with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceBlockRoleAction findByPrimaryKey(
		ResourceBlockRoleActionPK resourceBlockRoleActionPK)
		throws NoSuchResourceBlockRoleActionException, SystemException {
		ResourceBlockRoleAction resourceBlockRoleAction = fetchByPrimaryKey(resourceBlockRoleActionPK);

		if (resourceBlockRoleAction == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					resourceBlockRoleActionPK);
			}

			throw new NoSuchResourceBlockRoleActionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				resourceBlockRoleActionPK);
		}

		return resourceBlockRoleAction;
	}

	/**
	 * Returns the resource block role action with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the resource block role action
	 * @return the resource block role action, or <code>null</code> if a resource block role action with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ResourceBlockRoleAction fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey((ResourceBlockRoleActionPK)primaryKey);
	}

	/**
	 * Returns the resource block role action with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param resourceBlockRoleActionPK the primary key of the resource block role action
	 * @return the resource block role action, or <code>null</code> if a resource block role action with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceBlockRoleAction fetchByPrimaryKey(
		ResourceBlockRoleActionPK resourceBlockRoleActionPK)
		throws SystemException {
		ResourceBlockRoleAction resourceBlockRoleAction = (ResourceBlockRoleAction)EntityCacheUtil.getResult(ResourceBlockRoleActionModelImpl.ENTITY_CACHE_ENABLED,
				ResourceBlockRoleActionImpl.class, resourceBlockRoleActionPK,
				this);

		if (resourceBlockRoleAction == _nullResourceBlockRoleAction) {
			return null;
		}

		if (resourceBlockRoleAction == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				resourceBlockRoleAction = (ResourceBlockRoleAction)session.get(ResourceBlockRoleActionImpl.class,
						resourceBlockRoleActionPK);
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (resourceBlockRoleAction != null) {
					cacheResult(resourceBlockRoleAction);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(ResourceBlockRoleActionModelImpl.ENTITY_CACHE_ENABLED,
						ResourceBlockRoleActionImpl.class,
						resourceBlockRoleActionPK, _nullResourceBlockRoleAction);
				}

				closeSession(session);
			}
		}

		return resourceBlockRoleAction;
	}

	/**
	 * Returns all the resource block role actions where roleId = &#63; and actionId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param actionId the action ID
	 * @return the matching resource block role actions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourceBlockRoleAction> findByR_A(long roleId, long actionId)
		throws SystemException {
		return findByR_A(roleId, actionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource block role actions where roleId = &#63; and actionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param actionId the action ID
	 * @param start the lower bound of the range of resource block role actions
	 * @param end the upper bound of the range of resource block role actions (not inclusive)
	 * @return the range of matching resource block role actions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourceBlockRoleAction> findByR_A(long roleId, long actionId,
		int start, int end) throws SystemException {
		return findByR_A(roleId, actionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource block role actions where roleId = &#63; and actionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param actionId the action ID
	 * @param start the lower bound of the range of resource block role actions
	 * @param end the upper bound of the range of resource block role actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resource block role actions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourceBlockRoleAction> findByR_A(long roleId, long actionId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				roleId, actionId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ResourceBlockRoleAction> list = (List<ResourceBlockRoleAction>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_R_A,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_RESOURCEBLOCKROLEACTION_WHERE);

			query.append(_FINDER_COLUMN_R_A_ROLEID_2);

			query.append(_FINDER_COLUMN_R_A_ACTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				qPos.add(actionId);

				list = (List<ResourceBlockRoleAction>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_R_A,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_R_A,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first resource block role action in the ordered set where roleId = &#63; and actionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param actionId the action ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource block role action
	 * @throws com.liferay.portal.NoSuchResourceBlockRoleActionException if a matching resource block role action could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceBlockRoleAction findByR_A_First(long roleId, long actionId,
		OrderByComparator orderByComparator)
		throws NoSuchResourceBlockRoleActionException, SystemException {
		List<ResourceBlockRoleAction> list = findByR_A(roleId, actionId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("roleId=");
			msg.append(roleId);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourceBlockRoleActionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last resource block role action in the ordered set where roleId = &#63; and actionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param actionId the action ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource block role action
	 * @throws com.liferay.portal.NoSuchResourceBlockRoleActionException if a matching resource block role action could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceBlockRoleAction findByR_A_Last(long roleId, long actionId,
		OrderByComparator orderByComparator)
		throws NoSuchResourceBlockRoleActionException, SystemException {
		int count = countByR_A(roleId, actionId);

		List<ResourceBlockRoleAction> list = findByR_A(roleId, actionId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("roleId=");
			msg.append(roleId);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourceBlockRoleActionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the resource block role actions before and after the current resource block role action in the ordered set where roleId = &#63; and actionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourceBlockRoleActionPK the primary key of the current resource block role action
	 * @param roleId the role ID
	 * @param actionId the action ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next resource block role action
	 * @throws com.liferay.portal.NoSuchResourceBlockRoleActionException if a resource block role action with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourceBlockRoleAction[] findByR_A_PrevAndNext(
		ResourceBlockRoleActionPK resourceBlockRoleActionPK, long roleId,
		long actionId, OrderByComparator orderByComparator)
		throws NoSuchResourceBlockRoleActionException, SystemException {
		ResourceBlockRoleAction resourceBlockRoleAction = findByPrimaryKey(resourceBlockRoleActionPK);

		Session session = null;

		try {
			session = openSession();

			ResourceBlockRoleAction[] array = new ResourceBlockRoleActionImpl[3];

			array[0] = getByR_A_PrevAndNext(session, resourceBlockRoleAction,
					roleId, actionId, orderByComparator, true);

			array[1] = resourceBlockRoleAction;

			array[2] = getByR_A_PrevAndNext(session, resourceBlockRoleAction,
					roleId, actionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ResourceBlockRoleAction getByR_A_PrevAndNext(Session session,
		ResourceBlockRoleAction resourceBlockRoleAction, long roleId,
		long actionId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RESOURCEBLOCKROLEACTION_WHERE);

		query.append(_FINDER_COLUMN_R_A_ROLEID_2);

		query.append(_FINDER_COLUMN_R_A_ACTIONID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(roleId);

		qPos.add(actionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(resourceBlockRoleAction);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ResourceBlockRoleAction> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the resource block role actions.
	 *
	 * @return the resource block role actions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourceBlockRoleAction> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resource block role actions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource block role actions
	 * @param end the upper bound of the range of resource block role actions (not inclusive)
	 * @return the range of resource block role actions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourceBlockRoleAction> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the resource block role actions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource block role actions
	 * @param end the upper bound of the range of resource block role actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of resource block role actions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourceBlockRoleAction> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ResourceBlockRoleAction> list = (List<ResourceBlockRoleAction>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_RESOURCEBLOCKROLEACTION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_RESOURCEBLOCKROLEACTION;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<ResourceBlockRoleAction>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ResourceBlockRoleAction>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs,
						list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the resource block role actions where roleId = &#63; and actionId = &#63; from the database.
	 *
	 * @param roleId the role ID
	 * @param actionId the action ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByR_A(long roleId, long actionId)
		throws SystemException {
		for (ResourceBlockRoleAction resourceBlockRoleAction : findByR_A(
				roleId, actionId)) {
			resourceBlockRoleActionPersistence.remove(resourceBlockRoleAction);
		}
	}

	/**
	 * Removes all the resource block role actions from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (ResourceBlockRoleAction resourceBlockRoleAction : findAll()) {
			resourceBlockRoleActionPersistence.remove(resourceBlockRoleAction);
		}
	}

	/**
	 * Returns the number of resource block role actions where roleId = &#63; and actionId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param actionId the action ID
	 * @return the number of matching resource block role actions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByR_A(long roleId, long actionId) throws SystemException {
		Object[] finderArgs = new Object[] { roleId, actionId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_R_A,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_RESOURCEBLOCKROLEACTION_WHERE);

			query.append(_FINDER_COLUMN_R_A_ROLEID_2);

			query.append(_FINDER_COLUMN_R_A_ACTIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				qPos.add(actionId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_R_A, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of resource block role actions.
	 *
	 * @return the number of resource block role actions
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_RESOURCEBLOCKROLEACTION);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the resource block role action persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.ResourceBlockRoleAction")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ResourceBlockRoleAction>> listenersList = new ArrayList<ModelListener<ResourceBlockRoleAction>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ResourceBlockRoleAction>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ResourceBlockRoleActionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = ClusterGroupPersistence.class)
	protected ClusterGroupPersistence clusterGroupPersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutRevisionPersistence.class)
	protected LayoutRevisionPersistence layoutRevisionPersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetBranchPersistence.class)
	protected LayoutSetBranchPersistence layoutSetBranchPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupPermissionPersistence.class)
	protected OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PermissionPersistence.class)
	protected PermissionPersistence permissionPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortalPreferencesPersistence.class)
	protected PortalPreferencesPersistence portalPreferencesPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = RepositoryPersistence.class)
	protected RepositoryPersistence repositoryPersistence;
	@BeanReference(type = RepositoryEntryPersistence.class)
	protected RepositoryEntryPersistence repositoryEntryPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceBlockPersistence.class)
	protected ResourceBlockPersistence resourceBlockPersistence;
	@BeanReference(type = ResourceBlockRoleActionPersistence.class)
	protected ResourceBlockRoleActionPersistence resourceBlockRoleActionPersistence;
	@BeanReference(type = ResourceCodePersistence.class)
	protected ResourceCodePersistence resourceCodePersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = TicketPersistence.class)
	protected TicketPersistence ticketPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserNotificationEventPersistence.class)
	protected UserNotificationEventPersistence userNotificationEventPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = VirtualHostPersistence.class)
	protected VirtualHostPersistence virtualHostPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_RESOURCEBLOCKROLEACTION = "SELECT resourceBlockRoleAction FROM ResourceBlockRoleAction resourceBlockRoleAction";
	private static final String _SQL_SELECT_RESOURCEBLOCKROLEACTION_WHERE = "SELECT resourceBlockRoleAction FROM ResourceBlockRoleAction resourceBlockRoleAction WHERE ";
	private static final String _SQL_COUNT_RESOURCEBLOCKROLEACTION = "SELECT COUNT(resourceBlockRoleAction) FROM ResourceBlockRoleAction resourceBlockRoleAction";
	private static final String _SQL_COUNT_RESOURCEBLOCKROLEACTION_WHERE = "SELECT COUNT(resourceBlockRoleAction) FROM ResourceBlockRoleAction resourceBlockRoleAction WHERE ";
	private static final String _FINDER_COLUMN_R_A_ROLEID_2 = "resourceBlockRoleAction.id.roleId = ? AND ";
	private static final String _FINDER_COLUMN_R_A_ACTIONID_2 = "resourceBlockRoleAction.id.actionId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "resourceBlockRoleAction.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ResourceBlockRoleAction exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ResourceBlockRoleAction exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(ResourceBlockRoleActionPersistenceImpl.class);
	private static ResourceBlockRoleAction _nullResourceBlockRoleAction = new ResourceBlockRoleActionImpl() {
			public Object clone() {
				return this;
			}
		};
}