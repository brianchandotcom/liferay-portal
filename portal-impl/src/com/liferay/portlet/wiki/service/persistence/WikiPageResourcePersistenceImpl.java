/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.service.persistence;

import com.liferay.portal.NoSuchModelException;
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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.wiki.NoSuchPageResourceException;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.model.impl.WikiPageResourceImpl;
import com.liferay.portlet.wiki.model.impl.WikiPageResourceModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the wiki page resource service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageResourcePersistence
 * @see WikiPageResourceUtil
 * @generated
 */
public class WikiPageResourcePersistenceImpl extends BasePersistenceImpl<WikiPageResource>
	implements WikiPageResourcePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link WikiPageResourceUtil} to access the wiki page resource persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = WikiPageResourceImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceModelImpl.FINDER_CACHE_ENABLED,
			WikiPageResourceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceModelImpl.FINDER_CACHE_ENABLED,
			WikiPageResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			WikiPageResourceModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_N_T = new FinderPath(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceModelImpl.FINDER_CACHE_ENABLED,
			WikiPageResourceImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByN_T",
			new String[] { Long.class.getName(), String.class.getName() },
			WikiPageResourceModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageResourceModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_N_T = new FinderPath(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceModelImpl.FINDER_CACHE_ENABLED,
			WikiPageResourceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceModelImpl.FINDER_CACHE_ENABLED,
			WikiPageResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the wiki page resource in the entity cache if it is enabled.
	 *
	 * @param wikiPageResource the wiki page resource
	 */
	public void cacheResult(WikiPageResource wikiPageResource) {
		EntityCacheUtil.putResult(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceImpl.class, wikiPageResource.getPrimaryKey(),
			wikiPageResource);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_N_T,
			new Object[] {
				Long.valueOf(wikiPageResource.getNodeId()),
				
			wikiPageResource.getTitle()
			}, wikiPageResource);

		wikiPageResource.resetOriginalValues();
	}

	/**
	 * Caches the wiki page resources in the entity cache if it is enabled.
	 *
	 * @param wikiPageResources the wiki page resources
	 */
	public void cacheResult(List<WikiPageResource> wikiPageResources) {
		for (WikiPageResource wikiPageResource : wikiPageResources) {
			if (EntityCacheUtil.getResult(
						WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
						WikiPageResourceImpl.class,
						wikiPageResource.getPrimaryKey()) == null) {
				cacheResult(wikiPageResource);
			}
			else {
				wikiPageResource.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all wiki page resources.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(WikiPageResourceImpl.class.getName());
		}

		EntityCacheUtil.clearCache(WikiPageResourceImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the wiki page resource.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(WikiPageResource wikiPageResource) {
		EntityCacheUtil.removeResult(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceImpl.class, wikiPageResource.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(wikiPageResource);
	}

	@Override
	public void clearCache(List<WikiPageResource> wikiPageResources) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (WikiPageResource wikiPageResource : wikiPageResources) {
			EntityCacheUtil.removeResult(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
				WikiPageResourceImpl.class, wikiPageResource.getPrimaryKey());

			clearUniqueFindersCache(wikiPageResource);
		}
	}

	protected void clearUniqueFindersCache(WikiPageResource wikiPageResource) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_N_T,
			new Object[] {
				Long.valueOf(wikiPageResource.getNodeId()),
				
			wikiPageResource.getTitle()
			});
	}

	/**
	 * Creates a new wiki page resource with the primary key. Does not add the wiki page resource to the database.
	 *
	 * @param resourcePrimKey the primary key for the new wiki page resource
	 * @return the new wiki page resource
	 */
	public WikiPageResource create(long resourcePrimKey) {
		WikiPageResource wikiPageResource = new WikiPageResourceImpl();

		wikiPageResource.setNew(true);
		wikiPageResource.setPrimaryKey(resourcePrimKey);

		String uuid = PortalUUIDUtil.generate();

		wikiPageResource.setUuid(uuid);

		return wikiPageResource;
	}

	/**
	 * Removes the wiki page resource with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourcePrimKey the primary key of the wiki page resource
	 * @return the wiki page resource that was removed
	 * @throws com.liferay.portlet.wiki.NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public WikiPageResource remove(long resourcePrimKey)
		throws NoSuchPageResourceException, SystemException {
		return remove(Long.valueOf(resourcePrimKey));
	}

	/**
	 * Removes the wiki page resource with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the wiki page resource
	 * @return the wiki page resource that was removed
	 * @throws com.liferay.portlet.wiki.NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public WikiPageResource remove(Serializable primaryKey)
		throws NoSuchPageResourceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			WikiPageResource wikiPageResource = (WikiPageResource)session.get(WikiPageResourceImpl.class,
					primaryKey);

			if (wikiPageResource == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPageResourceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(wikiPageResource);
		}
		catch (NoSuchPageResourceException nsee) {
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
	protected WikiPageResource removeImpl(WikiPageResource wikiPageResource)
		throws SystemException {
		wikiPageResource = toUnwrappedModel(wikiPageResource);

		Session session = null;

		try {
			session = openSession();

			if (wikiPageResource.isCachedModel()) {
				wikiPageResource = (WikiPageResource)session.get(WikiPageResourceImpl.class,
						wikiPageResource.getPrimaryKeyObj());
			}

			if (wikiPageResource != null) {
				session.delete(wikiPageResource);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (wikiPageResource != null) {
			clearCache(wikiPageResource);
		}

		return wikiPageResource;
	}

	@Override
	public WikiPageResource updateImpl(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource)
		throws SystemException {
		wikiPageResource = toUnwrappedModel(wikiPageResource);

		boolean isNew = wikiPageResource.isNew();

		WikiPageResourceModelImpl wikiPageResourceModelImpl = (WikiPageResourceModelImpl)wikiPageResource;

		if (Validator.isNull(wikiPageResource.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			wikiPageResource.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (wikiPageResource.isNew()) {
				session.save(wikiPageResource);

				wikiPageResource.setNew(false);
			}
			else {
				session.merge(wikiPageResource);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !WikiPageResourceModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((wikiPageResourceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageResourceModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { wikiPageResourceModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}
		}

		EntityCacheUtil.putResult(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceImpl.class, wikiPageResource.getPrimaryKey(),
			wikiPageResource);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_N_T,
				new Object[] {
					Long.valueOf(wikiPageResource.getNodeId()),
					
				wikiPageResource.getTitle()
				}, wikiPageResource);
		}
		else {
			if ((wikiPageResourceModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_N_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(wikiPageResourceModelImpl.getOriginalNodeId()),
						
						wikiPageResourceModelImpl.getOriginalTitle()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_N_T, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_N_T, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_N_T,
					new Object[] {
						Long.valueOf(wikiPageResource.getNodeId()),
						
					wikiPageResource.getTitle()
					}, wikiPageResource);
			}
		}

		return wikiPageResource;
	}

	protected WikiPageResource toUnwrappedModel(
		WikiPageResource wikiPageResource) {
		if (wikiPageResource instanceof WikiPageResourceImpl) {
			return wikiPageResource;
		}

		WikiPageResourceImpl wikiPageResourceImpl = new WikiPageResourceImpl();

		wikiPageResourceImpl.setNew(wikiPageResource.isNew());
		wikiPageResourceImpl.setPrimaryKey(wikiPageResource.getPrimaryKey());

		wikiPageResourceImpl.setUuid(wikiPageResource.getUuid());
		wikiPageResourceImpl.setResourcePrimKey(wikiPageResource.getResourcePrimKey());
		wikiPageResourceImpl.setNodeId(wikiPageResource.getNodeId());
		wikiPageResourceImpl.setTitle(wikiPageResource.getTitle());

		return wikiPageResourceImpl;
	}

	/**
	 * Returns the wiki page resource with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the wiki page resource
	 * @return the wiki page resource
	 * @throws com.liferay.portal.NoSuchModelException if a wiki page resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public WikiPageResource findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the wiki page resource with the primary key or throws a {@link com.liferay.portlet.wiki.NoSuchPageResourceException} if it could not be found.
	 *
	 * @param resourcePrimKey the primary key of the wiki page resource
	 * @return the wiki page resource
	 * @throws com.liferay.portlet.wiki.NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public WikiPageResource findByPrimaryKey(long resourcePrimKey)
		throws NoSuchPageResourceException, SystemException {
		WikiPageResource wikiPageResource = fetchByPrimaryKey(resourcePrimKey);

		if (wikiPageResource == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + resourcePrimKey);
			}

			throw new NoSuchPageResourceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				resourcePrimKey);
		}

		return wikiPageResource;
	}

	/**
	 * Returns the wiki page resource with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the wiki page resource
	 * @return the wiki page resource, or <code>null</code> if a wiki page resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public WikiPageResource fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the wiki page resource with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param resourcePrimKey the primary key of the wiki page resource
	 * @return the wiki page resource, or <code>null</code> if a wiki page resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public WikiPageResource fetchByPrimaryKey(long resourcePrimKey)
		throws SystemException {
		WikiPageResource wikiPageResource = (WikiPageResource)EntityCacheUtil.getResult(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
				WikiPageResourceImpl.class, resourcePrimKey);

		if (wikiPageResource == _nullWikiPageResource) {
			return null;
		}

		if (wikiPageResource == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				wikiPageResource = (WikiPageResource)session.get(WikiPageResourceImpl.class,
						Long.valueOf(resourcePrimKey));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (wikiPageResource != null) {
					cacheResult(wikiPageResource);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
						WikiPageResourceImpl.class, resourcePrimKey,
						_nullWikiPageResource);
				}

				closeSession(session);
			}
		}

		return wikiPageResource;
	}

	/**
	 * Returns all the wiki page resources where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching wiki page resources
	 * @throws SystemException if a system exception occurred
	 */
	public List<WikiPageResource> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki page resources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @return the range of matching wiki page resources
	 * @throws SystemException if a system exception occurred
	 */
	public List<WikiPageResource> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki page resources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki page resources
	 * @throws SystemException if a system exception occurred
	 */
	public List<WikiPageResource> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<WikiPageResource> list = (List<WikiPageResource>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (WikiPageResource wikiPageResource : list) {
				if (!Validator.equals(uuid, wikiPageResource.getUuid())) {
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
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_WIKIPAGERESOURCE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

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

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<WikiPageResource>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first wiki page resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page resource
	 * @throws com.liferay.portlet.wiki.NoSuchPageResourceException if a matching wiki page resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public WikiPageResource findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchPageResourceException, SystemException {
		WikiPageResource wikiPageResource = fetchByUuid_First(uuid,
				orderByComparator);

		if (wikiPageResource != null) {
			return wikiPageResource;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageResourceException(msg.toString());
	}

	/**
	 * Returns the first wiki page resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public WikiPageResource fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<WikiPageResource> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page resource
	 * @throws com.liferay.portlet.wiki.NoSuchPageResourceException if a matching wiki page resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public WikiPageResource findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchPageResourceException, SystemException {
		WikiPageResource wikiPageResource = fetchByUuid_Last(uuid,
				orderByComparator);

		if (wikiPageResource != null) {
			return wikiPageResource;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageResourceException(msg.toString());
	}

	/**
	 * Returns the last wiki page resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public WikiPageResource fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<WikiPageResource> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki page resources before and after the current wiki page resource in the ordered set where uuid = &#63;.
	 *
	 * @param resourcePrimKey the primary key of the current wiki page resource
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page resource
	 * @throws com.liferay.portlet.wiki.NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public WikiPageResource[] findByUuid_PrevAndNext(long resourcePrimKey,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchPageResourceException, SystemException {
		WikiPageResource wikiPageResource = findByPrimaryKey(resourcePrimKey);

		Session session = null;

		try {
			session = openSession();

			WikiPageResource[] array = new WikiPageResourceImpl[3];

			array[0] = getByUuid_PrevAndNext(session, wikiPageResource, uuid,
					orderByComparator, true);

			array[1] = wikiPageResource;

			array[2] = getByUuid_PrevAndNext(session, wikiPageResource, uuid,
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

	protected WikiPageResource getByUuid_PrevAndNext(Session session,
		WikiPageResource wikiPageResource, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGERESOURCE_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}
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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPageResource);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPageResource> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the wiki page resource where nodeId = &#63; and title = &#63; or throws a {@link com.liferay.portlet.wiki.NoSuchPageResourceException} if it could not be found.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @return the matching wiki page resource
	 * @throws com.liferay.portlet.wiki.NoSuchPageResourceException if a matching wiki page resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public WikiPageResource findByN_T(long nodeId, String title)
		throws NoSuchPageResourceException, SystemException {
		WikiPageResource wikiPageResource = fetchByN_T(nodeId, title);

		if (wikiPageResource == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(", title=");
			msg.append(title);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPageResourceException(msg.toString());
		}

		return wikiPageResource;
	}

	/**
	 * Returns the wiki page resource where nodeId = &#63; and title = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @return the matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public WikiPageResource fetchByN_T(long nodeId, String title)
		throws SystemException {
		return fetchByN_T(nodeId, title, true);
	}

	/**
	 * Returns the wiki page resource where nodeId = &#63; and title = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public WikiPageResource fetchByN_T(long nodeId, String title,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { nodeId, title };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_N_T,
					finderArgs, this);
		}

		if (result instanceof WikiPageResource) {
			WikiPageResource wikiPageResource = (WikiPageResource)result;

			if ((nodeId != wikiPageResource.getNodeId()) ||
					!Validator.equals(title, wikiPageResource.getTitle())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_WIKIPAGERESOURCE_WHERE);

			query.append(_FINDER_COLUMN_N_T_NODEID_2);

			if (title == null) {
				query.append(_FINDER_COLUMN_N_T_TITLE_1);
			}
			else {
				if (title.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_N_T_TITLE_3);
				}
				else {
					query.append(_FINDER_COLUMN_N_T_TITLE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (title != null) {
					qPos.add(title);
				}

				List<WikiPageResource> list = q.list();

				result = list;

				WikiPageResource wikiPageResource = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_N_T,
						finderArgs, list);
				}
				else {
					wikiPageResource = list.get(0);

					cacheResult(wikiPageResource);

					if ((wikiPageResource.getNodeId() != nodeId) ||
							(wikiPageResource.getTitle() == null) ||
							!wikiPageResource.getTitle().equals(title)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_N_T,
							finderArgs, wikiPageResource);
					}
				}

				return wikiPageResource;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_N_T,
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
				return (WikiPageResource)result;
			}
		}
	}

	/**
	 * Returns all the wiki page resources.
	 *
	 * @return the wiki page resources
	 * @throws SystemException if a system exception occurred
	 */
	public List<WikiPageResource> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki page resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @return the range of wiki page resources
	 * @throws SystemException if a system exception occurred
	 */
	public List<WikiPageResource> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki page resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of wiki page resources
	 * @throws SystemException if a system exception occurred
	 */
	public List<WikiPageResource> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<WikiPageResource> list = (List<WikiPageResource>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_WIKIPAGERESOURCE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_WIKIPAGERESOURCE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<WikiPageResource>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<WikiPageResource>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the wiki page resources where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (WikiPageResource wikiPageResource : findByUuid(uuid)) {
			remove(wikiPageResource);
		}
	}

	/**
	 * Removes the wiki page resource where nodeId = &#63; and title = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @return the wiki page resource that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public WikiPageResource removeByN_T(long nodeId, String title)
		throws NoSuchPageResourceException, SystemException {
		WikiPageResource wikiPageResource = findByN_T(nodeId, title);

		return remove(wikiPageResource);
	}

	/**
	 * Removes all the wiki page resources from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (WikiPageResource wikiPageResource : findAll()) {
			remove(wikiPageResource);
		}
	}

	/**
	 * Returns the number of wiki page resources where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching wiki page resources
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_WIKIPAGERESOURCE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of wiki page resources where nodeId = &#63; and title = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @return the number of matching wiki page resources
	 * @throws SystemException if a system exception occurred
	 */
	public int countByN_T(long nodeId, String title) throws SystemException {
		Object[] finderArgs = new Object[] { nodeId, title };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_N_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WIKIPAGERESOURCE_WHERE);

			query.append(_FINDER_COLUMN_N_T_NODEID_2);

			if (title == null) {
				query.append(_FINDER_COLUMN_N_T_TITLE_1);
			}
			else {
				if (title.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_N_T_TITLE_3);
				}
				else {
					query.append(_FINDER_COLUMN_N_T_TITLE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (title != null) {
					qPos.add(title);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_N_T, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of wiki page resources.
	 *
	 * @return the number of wiki page resources
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_WIKIPAGERESOURCE);

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
	 * Initializes the wiki page resource persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.wiki.model.WikiPageResource")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<WikiPageResource>> listenersList = new ArrayList<ModelListener<WikiPageResource>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<WikiPageResource>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(WikiPageResourceImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = WikiNodePersistence.class)
	protected WikiNodePersistence wikiNodePersistence;
	@BeanReference(type = WikiPagePersistence.class)
	protected WikiPagePersistence wikiPagePersistence;
	@BeanReference(type = WikiPageResourcePersistence.class)
	protected WikiPageResourcePersistence wikiPageResourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_WIKIPAGERESOURCE = "SELECT wikiPageResource FROM WikiPageResource wikiPageResource";
	private static final String _SQL_SELECT_WIKIPAGERESOURCE_WHERE = "SELECT wikiPageResource FROM WikiPageResource wikiPageResource WHERE ";
	private static final String _SQL_COUNT_WIKIPAGERESOURCE = "SELECT COUNT(wikiPageResource) FROM WikiPageResource wikiPageResource";
	private static final String _SQL_COUNT_WIKIPAGERESOURCE_WHERE = "SELECT COUNT(wikiPageResource) FROM WikiPageResource wikiPageResource WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "wikiPageResource.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "wikiPageResource.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(wikiPageResource.uuid IS NULL OR wikiPageResource.uuid = ?)";
	private static final String _FINDER_COLUMN_N_T_NODEID_2 = "wikiPageResource.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_T_TITLE_1 = "wikiPageResource.title IS NULL";
	private static final String _FINDER_COLUMN_N_T_TITLE_2 = "wikiPageResource.title = ?";
	private static final String _FINDER_COLUMN_N_T_TITLE_3 = "(wikiPageResource.title IS NULL OR wikiPageResource.title = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "wikiPageResource.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No WikiPageResource exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No WikiPageResource exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(WikiPageResourcePersistenceImpl.class);
	private static WikiPageResource _nullWikiPageResource = new WikiPageResourceImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<WikiPageResource> toCacheModel() {
				return _nullWikiPageResourceCacheModel;
			}
		};

	private static CacheModel<WikiPageResource> _nullWikiPageResourceCacheModel = new CacheModel<WikiPageResource>() {
			public WikiPageResource toEntityModel() {
				return _nullWikiPageResource;
			}
		};
}