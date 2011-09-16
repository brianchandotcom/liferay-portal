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
import com.liferay.portal.NoSuchResourceException;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.model.impl.ResourceModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the resource service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourcePersistence
 * @see ResourceUtil
 * @generated
 */
public class ResourcePersistenceImpl extends BasePersistenceImpl<Resource>
	implements ResourcePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ResourceUtil} to access the resource persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ResourceImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_CODEID = new FinderPath(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceModelImpl.FINDER_CACHE_ENABLED, ResourceImpl.class,
			FINDER_CLASS_NAME_LIST, "findByCodeId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_CODEID = new FinderPath(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByCodeId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_P = new FinderPath(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceModelImpl.FINDER_CACHE_ENABLED, ResourceImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_P",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_P = new FinderPath(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByC_P",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceModelImpl.FINDER_CACHE_ENABLED, ResourceImpl.class,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the resource in the entity cache if it is enabled.
	 *
	 * @param resource the resource
	 */
	public void cacheResult(Resource resource) {
		EntityCacheUtil.putResult(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceImpl.class, resource.getPrimaryKey(), resource);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P,
			new Object[] {
				Long.valueOf(resource.getCodeId()),
				
			resource.getPrimKey()
			}, resource);

		resource.resetOriginalValues();
	}

	/**
	 * Caches the resources in the entity cache if it is enabled.
	 *
	 * @param resources the resources
	 */
	public void cacheResult(List<Resource> resources) {
		for (Resource resource : resources) {
			if (EntityCacheUtil.getResult(
						ResourceModelImpl.ENTITY_CACHE_ENABLED,
						ResourceImpl.class, resource.getPrimaryKey()) == null) {
				cacheResult(resource);
			}
		}
	}

	/**
	 * Clears the cache for all resources.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ResourceImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ResourceImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the resource.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Resource resource) {
		EntityCacheUtil.removeResult(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceImpl.class, resource.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL, FINDER_ARGS_EMPTY);

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_P,
			new Object[] {
				Long.valueOf(resource.getCodeId()),
				
			resource.getPrimKey()
			});
	}

	/**
	 * Creates a new resource with the primary key. Does not add the resource to the database.
	 *
	 * @param resourceId the primary key for the new resource
	 * @return the new resource
	 */
	public Resource create(long resourceId) {
		Resource resource = new ResourceImpl();

		resource.setNew(true);
		resource.setPrimaryKey(resourceId);

		return resource;
	}

	/**
	 * Removes the resource with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the resource
	 * @return the resource that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Resource remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the resource with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourceId the primary key of the resource
	 * @return the resource that was removed
	 * @throws com.liferay.portal.NoSuchResourceException if a resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Resource remove(long resourceId)
		throws NoSuchResourceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Resource resource = (Resource)session.get(ResourceImpl.class,
					Long.valueOf(resourceId));

			if (resource == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + resourceId);
				}

				throw new NoSuchResourceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					resourceId);
			}

			return resourcePersistence.remove(resource);
		}
		catch (NoSuchResourceException nsee) {
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
	 * Removes the resource from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resource the resource
	 * @return the resource that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Resource remove(Resource resource) throws SystemException {
		return super.remove(resource);
	}

	@Override
	protected Resource removeImpl(Resource resource) throws SystemException {
		resource = toUnwrappedModel(resource);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, resource);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		ResourceModelImpl resourceModelImpl = (ResourceModelImpl)resource;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_P,
			new Object[] {
				Long.valueOf(resourceModelImpl.getCodeId()),
				
			resourceModelImpl.getPrimKey()
			});

		EntityCacheUtil.removeResult(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceImpl.class, resource.getPrimaryKey());

		return resource;
	}

	@Override
	public Resource updateImpl(com.liferay.portal.model.Resource resource,
		boolean merge) throws SystemException {
		resource = toUnwrappedModel(resource);

		boolean isNew = resource.isNew();

		ResourceModelImpl resourceModelImpl = (ResourceModelImpl)resource;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, resource, merge);

			resource.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceImpl.class, resource.getPrimaryKey(), resource);

		if (!isNew &&
				((resource.getCodeId() != resourceModelImpl.getOriginalCodeId()) ||
				!Validator.equals(resource.getPrimKey(),
					resourceModelImpl.getOriginalPrimKey()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_P,
				new Object[] {
					Long.valueOf(resourceModelImpl.getOriginalCodeId()),
					
				resourceModelImpl.getOriginalPrimKey()
				});
		}

		if (isNew ||
				((resource.getCodeId() != resourceModelImpl.getOriginalCodeId()) ||
				!Validator.equals(resource.getPrimKey(),
					resourceModelImpl.getOriginalPrimKey()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P,
				new Object[] {
					Long.valueOf(resource.getCodeId()),
					
				resource.getPrimKey()
				}, resource);
		}

		return resource;
	}

	protected Resource toUnwrappedModel(Resource resource) {
		if (resource instanceof ResourceImpl) {
			return resource;
		}

		ResourceImpl resourceImpl = new ResourceImpl();

		resourceImpl.setNew(resource.isNew());
		resourceImpl.setPrimaryKey(resource.getPrimaryKey());

		resourceImpl.setResourceId(resource.getResourceId());
		resourceImpl.setCodeId(resource.getCodeId());
		resourceImpl.setPrimKey(resource.getPrimKey());

		return resourceImpl;
	}

	/**
	 * Returns the resource with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the resource
	 * @return the resource
	 * @throws com.liferay.portal.NoSuchModelException if a resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Resource findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the resource with the primary key or throws a {@link com.liferay.portal.NoSuchResourceException} if it could not be found.
	 *
	 * @param resourceId the primary key of the resource
	 * @return the resource
	 * @throws com.liferay.portal.NoSuchResourceException if a resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Resource findByPrimaryKey(long resourceId)
		throws NoSuchResourceException, SystemException {
		Resource resource = fetchByPrimaryKey(resourceId);

		if (resource == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + resourceId);
			}

			throw new NoSuchResourceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				resourceId);
		}

		return resource;
	}

	/**
	 * Returns the resource with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the resource
	 * @return the resource, or <code>null</code> if a resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Resource fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the resource with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param resourceId the primary key of the resource
	 * @return the resource, or <code>null</code> if a resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Resource fetchByPrimaryKey(long resourceId)
		throws SystemException {
		Resource resource = (Resource)EntityCacheUtil.getResult(ResourceModelImpl.ENTITY_CACHE_ENABLED,
				ResourceImpl.class, resourceId);

		if (resource == _nullResource) {
			return null;
		}

		if (resource == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				resource = (Resource)session.get(ResourceImpl.class,
						Long.valueOf(resourceId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (resource != null) {
					cacheResult(resource);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(ResourceModelImpl.ENTITY_CACHE_ENABLED,
						ResourceImpl.class, resourceId, _nullResource);
				}

				closeSession(session);
			}
		}

		return resource;
	}

	/**
	 * Returns all the resources where codeId = &#63;.
	 *
	 * @param codeId the code ID
	 * @return the matching resources
	 * @throws SystemException if a system exception occurred
	 */
	public List<Resource> findByCodeId(long codeId) throws SystemException {
		return findByCodeId(codeId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resources where codeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param codeId the code ID
	 * @param start the lower bound of the range of resources
	 * @param end the upper bound of the range of resources (not inclusive)
	 * @return the range of matching resources
	 * @throws SystemException if a system exception occurred
	 */
	public List<Resource> findByCodeId(long codeId, int start, int end)
		throws SystemException {
		return findByCodeId(codeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the resources where codeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param codeId the code ID
	 * @param start the lower bound of the range of resources
	 * @param end the upper bound of the range of resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching resources
	 * @throws SystemException if a system exception occurred
	 */
	public List<Resource> findByCodeId(long codeId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { codeId, start, end, orderByComparator };

		List<Resource> list = (List<Resource>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_CODEID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_RESOURCE_WHERE);

			query.append(_FINDER_COLUMN_CODEID_CODEID_2);

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

				qPos.add(codeId);

				list = (List<Resource>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_CODEID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_CODEID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first resource in the ordered set where codeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param codeId the code ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching resource
	 * @throws com.liferay.portal.NoSuchResourceException if a matching resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Resource findByCodeId_First(long codeId,
		OrderByComparator orderByComparator)
		throws NoSuchResourceException, SystemException {
		List<Resource> list = findByCodeId(codeId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("codeId=");
			msg.append(codeId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourceException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last resource in the ordered set where codeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param codeId the code ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching resource
	 * @throws com.liferay.portal.NoSuchResourceException if a matching resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Resource findByCodeId_Last(long codeId,
		OrderByComparator orderByComparator)
		throws NoSuchResourceException, SystemException {
		int count = countByCodeId(codeId);

		List<Resource> list = findByCodeId(codeId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("codeId=");
			msg.append(codeId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourceException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the resources before and after the current resource in the ordered set where codeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourceId the primary key of the current resource
	 * @param codeId the code ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next resource
	 * @throws com.liferay.portal.NoSuchResourceException if a resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Resource[] findByCodeId_PrevAndNext(long resourceId, long codeId,
		OrderByComparator orderByComparator)
		throws NoSuchResourceException, SystemException {
		Resource resource = findByPrimaryKey(resourceId);

		Session session = null;

		try {
			session = openSession();

			Resource[] array = new ResourceImpl[3];

			array[0] = getByCodeId_PrevAndNext(session, resource, codeId,
					orderByComparator, true);

			array[1] = resource;

			array[2] = getByCodeId_PrevAndNext(session, resource, codeId,
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

	protected Resource getByCodeId_PrevAndNext(Session session,
		Resource resource, long codeId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RESOURCE_WHERE);

		query.append(_FINDER_COLUMN_CODEID_CODEID_2);

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

		qPos.add(codeId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(resource);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Resource> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the resource where codeId = &#63; and primKey = &#63; or throws a {@link com.liferay.portal.NoSuchResourceException} if it could not be found.
	 *
	 * @param codeId the code ID
	 * @param primKey the prim key
	 * @return the matching resource
	 * @throws com.liferay.portal.NoSuchResourceException if a matching resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Resource findByC_P(long codeId, String primKey)
		throws NoSuchResourceException, SystemException {
		Resource resource = fetchByC_P(codeId, primKey);

		if (resource == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("codeId=");
			msg.append(codeId);

			msg.append(", primKey=");
			msg.append(primKey);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchResourceException(msg.toString());
		}

		return resource;
	}

	/**
	 * Returns the resource where codeId = &#63; and primKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param codeId the code ID
	 * @param primKey the prim key
	 * @return the matching resource, or <code>null</code> if a matching resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Resource fetchByC_P(long codeId, String primKey)
		throws SystemException {
		return fetchByC_P(codeId, primKey, true);
	}

	/**
	 * Returns the resource where codeId = &#63; and primKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param codeId the code ID
	 * @param primKey the prim key
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching resource, or <code>null</code> if a matching resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Resource fetchByC_P(long codeId, String primKey,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { codeId, primKey };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_P,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_RESOURCE_WHERE);

			query.append(_FINDER_COLUMN_C_P_CODEID_2);

			if (primKey == null) {
				query.append(_FINDER_COLUMN_C_P_PRIMKEY_1);
			}
			else {
				if (primKey.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_P_PRIMKEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_P_PRIMKEY_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(codeId);

				if (primKey != null) {
					qPos.add(primKey);
				}

				List<Resource> list = q.list();

				result = list;

				Resource resource = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P,
						finderArgs, list);
				}
				else {
					resource = list.get(0);

					cacheResult(resource);

					if ((resource.getCodeId() != codeId) ||
							(resource.getPrimKey() == null) ||
							!resource.getPrimKey().equals(primKey)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P,
							finderArgs, resource);
					}
				}

				return resource;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_P,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Resource)result;
			}
		}
	}

	/**
	 * Returns all the resources.
	 *
	 * @return the resources
	 * @throws SystemException if a system exception occurred
	 */
	public List<Resource> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of resources
	 * @param end the upper bound of the range of resources (not inclusive)
	 * @return the range of resources
	 * @throws SystemException if a system exception occurred
	 */
	public List<Resource> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of resources
	 * @param end the upper bound of the range of resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of resources
	 * @throws SystemException if a system exception occurred
	 */
	public List<Resource> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		List<Resource> list = (List<Resource>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_RESOURCE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_RESOURCE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Resource>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Resource>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the resources where codeId = &#63; from the database.
	 *
	 * @param codeId the code ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCodeId(long codeId) throws SystemException {
		for (Resource resource : findByCodeId(codeId)) {
			resourcePersistence.remove(resource);
		}
	}

	/**
	 * Removes the resource where codeId = &#63; and primKey = &#63; from the database.
	 *
	 * @param codeId the code ID
	 * @param primKey the prim key
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_P(long codeId, String primKey)
		throws NoSuchResourceException, SystemException {
		Resource resource = findByC_P(codeId, primKey);

		resourcePersistence.remove(resource);
	}

	/**
	 * Removes all the resources from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Resource resource : findAll()) {
			resourcePersistence.remove(resource);
		}
	}

	/**
	 * Returns the number of resources where codeId = &#63;.
	 *
	 * @param codeId the code ID
	 * @return the number of matching resources
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCodeId(long codeId) throws SystemException {
		Object[] finderArgs = new Object[] { codeId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CODEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_RESOURCE_WHERE);

			query.append(_FINDER_COLUMN_CODEID_CODEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(codeId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CODEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of resources where codeId = &#63; and primKey = &#63;.
	 *
	 * @param codeId the code ID
	 * @param primKey the prim key
	 * @return the number of matching resources
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_P(long codeId, String primKey)
		throws SystemException {
		Object[] finderArgs = new Object[] { codeId, primKey };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_RESOURCE_WHERE);

			query.append(_FINDER_COLUMN_C_P_CODEID_2);

			if (primKey == null) {
				query.append(_FINDER_COLUMN_C_P_PRIMKEY_1);
			}
			else {
				if (primKey.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_P_PRIMKEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_P_PRIMKEY_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(codeId);

				if (primKey != null) {
					qPos.add(primKey);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of resources.
	 *
	 * @return the number of resources
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_RESOURCE);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the resource persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Resource")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Resource>> listenersList = new ArrayList<ModelListener<Resource>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Resource>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(ResourceImpl.class.getName());
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
	@BeanReference(type = LayoutBranchPersistence.class)
	protected LayoutBranchPersistence layoutBranchPersistence;
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
	@BeanReference(type = ResourceBlockPermissionPersistence.class)
	protected ResourceBlockPermissionPersistence resourceBlockPermissionPersistence;
	@BeanReference(type = ResourceCodePersistence.class)
	protected ResourceCodePersistence resourceCodePersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = ResourceTypePermissionPersistence.class)
	protected ResourceTypePermissionPersistence resourceTypePermissionPersistence;
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
	private static final String _SQL_SELECT_RESOURCE = "SELECT resource FROM Resource resource";
	private static final String _SQL_SELECT_RESOURCE_WHERE = "SELECT resource FROM Resource resource WHERE ";
	private static final String _SQL_COUNT_RESOURCE = "SELECT COUNT(resource) FROM Resource resource";
	private static final String _SQL_COUNT_RESOURCE_WHERE = "SELECT COUNT(resource) FROM Resource resource WHERE ";
	private static final String _FINDER_COLUMN_CODEID_CODEID_2 = "resource.codeId = ?";
	private static final String _FINDER_COLUMN_C_P_CODEID_2 = "resource.codeId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_PRIMKEY_1 = "resource.primKey IS NULL";
	private static final String _FINDER_COLUMN_C_P_PRIMKEY_2 = "resource.primKey = ?";
	private static final String _FINDER_COLUMN_C_P_PRIMKEY_3 = "(resource.primKey IS NULL OR resource.primKey = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "resource.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Resource exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Resource exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(ResourcePersistenceImpl.class);
	private static Resource _nullResource = new ResourceImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Resource> toCacheModel() {
				return _nullResourceCacheModel;
			}
		};

	private static CacheModel<Resource> _nullResourceCacheModel = new CacheModel<Resource>() {
			public Resource toEntityModel() {
				return _nullResource;
			}
		};
}