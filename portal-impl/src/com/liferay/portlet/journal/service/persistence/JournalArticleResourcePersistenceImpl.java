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

package com.liferay.portlet.journal.service.persistence;

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

import com.liferay.portlet.journal.NoSuchArticleResourceException;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.impl.JournalArticleResourceImpl;
import com.liferay.portlet.journal.model.impl.JournalArticleResourceModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the journal article resource service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleResourcePersistence
 * @see JournalArticleResourceUtil
 * @generated
 */
public class JournalArticleResourcePersistenceImpl extends BasePersistenceImpl<JournalArticleResource>
	implements JournalArticleResourcePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link JournalArticleResourceUtil} to access the journal article resource persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = JournalArticleResourceImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			JournalArticleResourceModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleResourceImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			JournalArticleResourceModelImpl.UUID_COLUMN_BITMASK |
			JournalArticleResourceModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			JournalArticleResourceModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_A = new FinderPath(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleResourceImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_A",
			new String[] { Long.class.getName(), String.class.getName() },
			JournalArticleResourceModelImpl.GROUPID_COLUMN_BITMASK |
			JournalArticleResourceModelImpl.ARTICLEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A = new FinderPath(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the journal article resource in the entity cache if it is enabled.
	 *
	 * @param journalArticleResource the journal article resource
	 */
	public void cacheResult(JournalArticleResource journalArticleResource) {
		EntityCacheUtil.putResult(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceImpl.class,
			journalArticleResource.getPrimaryKey(), journalArticleResource);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalArticleResource.getUuid(),
				Long.valueOf(journalArticleResource.getGroupId())
			}, journalArticleResource);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A,
			new Object[] {
				Long.valueOf(journalArticleResource.getGroupId()),
				
			journalArticleResource.getArticleId()
			}, journalArticleResource);

		journalArticleResource.resetOriginalValues();
	}

	/**
	 * Caches the journal article resources in the entity cache if it is enabled.
	 *
	 * @param journalArticleResources the journal article resources
	 */
	public void cacheResult(
		List<JournalArticleResource> journalArticleResources) {
		for (JournalArticleResource journalArticleResource : journalArticleResources) {
			if (EntityCacheUtil.getResult(
						JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
						JournalArticleResourceImpl.class,
						journalArticleResource.getPrimaryKey()) == null) {
				cacheResult(journalArticleResource);
			}
			else {
				journalArticleResource.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all journal article resources.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(JournalArticleResourceImpl.class.getName());
		}

		EntityCacheUtil.clearCache(JournalArticleResourceImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the journal article resource.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(JournalArticleResource journalArticleResource) {
		EntityCacheUtil.removeResult(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceImpl.class,
			journalArticleResource.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(journalArticleResource);
	}

	@Override
	public void clearCache(List<JournalArticleResource> journalArticleResources) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (JournalArticleResource journalArticleResource : journalArticleResources) {
			EntityCacheUtil.removeResult(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
				JournalArticleResourceImpl.class,
				journalArticleResource.getPrimaryKey());

			clearUniqueFindersCache(journalArticleResource);
		}
	}

	protected void clearUniqueFindersCache(
		JournalArticleResource journalArticleResource) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalArticleResource.getUuid(),
				Long.valueOf(journalArticleResource.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_A,
			new Object[] {
				Long.valueOf(journalArticleResource.getGroupId()),
				
			journalArticleResource.getArticleId()
			});
	}

	/**
	 * Creates a new journal article resource with the primary key. Does not add the journal article resource to the database.
	 *
	 * @param resourcePrimKey the primary key for the new journal article resource
	 * @return the new journal article resource
	 */
	public JournalArticleResource create(long resourcePrimKey) {
		JournalArticleResource journalArticleResource = new JournalArticleResourceImpl();

		journalArticleResource.setNew(true);
		journalArticleResource.setPrimaryKey(resourcePrimKey);

		String uuid = PortalUUIDUtil.generate();

		journalArticleResource.setUuid(uuid);

		return journalArticleResource;
	}

	/**
	 * Removes the journal article resource with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourcePrimKey the primary key of the journal article resource
	 * @return the journal article resource that was removed
	 * @throws com.liferay.portlet.journal.NoSuchArticleResourceException if a journal article resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleResource remove(long resourcePrimKey)
		throws NoSuchArticleResourceException, SystemException {
		return remove(Long.valueOf(resourcePrimKey));
	}

	/**
	 * Removes the journal article resource with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the journal article resource
	 * @return the journal article resource that was removed
	 * @throws com.liferay.portlet.journal.NoSuchArticleResourceException if a journal article resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalArticleResource remove(Serializable primaryKey)
		throws NoSuchArticleResourceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalArticleResource journalArticleResource = (JournalArticleResource)session.get(JournalArticleResourceImpl.class,
					primaryKey);

			if (journalArticleResource == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchArticleResourceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(journalArticleResource);
		}
		catch (NoSuchArticleResourceException nsee) {
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
	protected JournalArticleResource removeImpl(
		JournalArticleResource journalArticleResource)
		throws SystemException {
		journalArticleResource = toUnwrappedModel(journalArticleResource);

		Session session = null;

		try {
			session = openSession();

			if (journalArticleResource.isCachedModel()) {
				journalArticleResource = (JournalArticleResource)session.get(JournalArticleResourceImpl.class,
						journalArticleResource.getPrimaryKeyObj());
			}

			if (journalArticleResource != null) {
				session.delete(journalArticleResource);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (journalArticleResource != null) {
			clearCache(journalArticleResource);
		}

		return journalArticleResource;
	}

	@Override
	public JournalArticleResource updateImpl(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource)
		throws SystemException {
		journalArticleResource = toUnwrappedModel(journalArticleResource);

		boolean isNew = journalArticleResource.isNew();

		JournalArticleResourceModelImpl journalArticleResourceModelImpl = (JournalArticleResourceModelImpl)journalArticleResource;

		if (Validator.isNull(journalArticleResource.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			journalArticleResource.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (journalArticleResource.isNew()) {
				session.save(journalArticleResource);

				journalArticleResource.setNew(false);
			}
			else {
				session.merge(journalArticleResource);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !JournalArticleResourceModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((journalArticleResourceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleResourceModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { journalArticleResourceModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((journalArticleResourceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(journalArticleResourceModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(journalArticleResourceModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}
		}

		EntityCacheUtil.putResult(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceImpl.class,
			journalArticleResource.getPrimaryKey(), journalArticleResource);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					journalArticleResource.getUuid(),
					Long.valueOf(journalArticleResource.getGroupId())
				}, journalArticleResource);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A,
				new Object[] {
					Long.valueOf(journalArticleResource.getGroupId()),
					
				journalArticleResource.getArticleId()
				}, journalArticleResource);
		}
		else {
			if ((journalArticleResourceModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleResourceModelImpl.getOriginalUuid(),
						Long.valueOf(journalArticleResourceModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						journalArticleResource.getUuid(),
						Long.valueOf(journalArticleResource.getGroupId())
					}, journalArticleResource);
			}

			if ((journalArticleResourceModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(journalArticleResourceModelImpl.getOriginalGroupId()),
						
						journalArticleResourceModelImpl.getOriginalArticleId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_A, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_A, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A,
					new Object[] {
						Long.valueOf(journalArticleResource.getGroupId()),
						
					journalArticleResource.getArticleId()
					}, journalArticleResource);
			}
		}

		return journalArticleResource;
	}

	protected JournalArticleResource toUnwrappedModel(
		JournalArticleResource journalArticleResource) {
		if (journalArticleResource instanceof JournalArticleResourceImpl) {
			return journalArticleResource;
		}

		JournalArticleResourceImpl journalArticleResourceImpl = new JournalArticleResourceImpl();

		journalArticleResourceImpl.setNew(journalArticleResource.isNew());
		journalArticleResourceImpl.setPrimaryKey(journalArticleResource.getPrimaryKey());

		journalArticleResourceImpl.setUuid(journalArticleResource.getUuid());
		journalArticleResourceImpl.setResourcePrimKey(journalArticleResource.getResourcePrimKey());
		journalArticleResourceImpl.setGroupId(journalArticleResource.getGroupId());
		journalArticleResourceImpl.setArticleId(journalArticleResource.getArticleId());

		return journalArticleResourceImpl;
	}

	/**
	 * Returns the journal article resource with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal article resource
	 * @return the journal article resource
	 * @throws com.liferay.portal.NoSuchModelException if a journal article resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalArticleResource findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the journal article resource with the primary key or throws a {@link com.liferay.portlet.journal.NoSuchArticleResourceException} if it could not be found.
	 *
	 * @param resourcePrimKey the primary key of the journal article resource
	 * @return the journal article resource
	 * @throws com.liferay.portlet.journal.NoSuchArticleResourceException if a journal article resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleResource findByPrimaryKey(long resourcePrimKey)
		throws NoSuchArticleResourceException, SystemException {
		JournalArticleResource journalArticleResource = fetchByPrimaryKey(resourcePrimKey);

		if (journalArticleResource == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + resourcePrimKey);
			}

			throw new NoSuchArticleResourceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				resourcePrimKey);
		}

		return journalArticleResource;
	}

	/**
	 * Returns the journal article resource with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal article resource
	 * @return the journal article resource, or <code>null</code> if a journal article resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalArticleResource fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the journal article resource with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param resourcePrimKey the primary key of the journal article resource
	 * @return the journal article resource, or <code>null</code> if a journal article resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleResource fetchByPrimaryKey(long resourcePrimKey)
		throws SystemException {
		JournalArticleResource journalArticleResource = (JournalArticleResource)EntityCacheUtil.getResult(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
				JournalArticleResourceImpl.class, resourcePrimKey);

		if (journalArticleResource == _nullJournalArticleResource) {
			return null;
		}

		if (journalArticleResource == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				journalArticleResource = (JournalArticleResource)session.get(JournalArticleResourceImpl.class,
						Long.valueOf(resourcePrimKey));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (journalArticleResource != null) {
					cacheResult(journalArticleResource);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
						JournalArticleResourceImpl.class, resourcePrimKey,
						_nullJournalArticleResource);
				}

				closeSession(session);
			}
		}

		return journalArticleResource;
	}

	/**
	 * Returns all the journal article resources where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching journal article resources
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticleResource> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal article resources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal article resources
	 * @param end the upper bound of the range of journal article resources (not inclusive)
	 * @return the range of matching journal article resources
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticleResource> findByUuid(String uuid, int start,
		int end) throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal article resources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal article resources
	 * @param end the upper bound of the range of journal article resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal article resources
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticleResource> findByUuid(String uuid, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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

		List<JournalArticleResource> list = (List<JournalArticleResource>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticleResource journalArticleResource : list) {
				if (!Validator.equals(uuid, journalArticleResource.getUuid())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLERESOURCE_WHERE);

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

				list = (List<JournalArticleResource>)QueryUtil.list(q,
						getDialect(), start, end);
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
	 * Returns the first journal article resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article resource
	 * @throws com.liferay.portlet.journal.NoSuchArticleResourceException if a matching journal article resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleResource findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchArticleResourceException, SystemException {
		JournalArticleResource journalArticleResource = fetchByUuid_First(uuid,
				orderByComparator);

		if (journalArticleResource != null) {
			return journalArticleResource;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleResourceException(msg.toString());
	}

	/**
	 * Returns the first journal article resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleResource fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalArticleResource> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article resource
	 * @throws com.liferay.portlet.journal.NoSuchArticleResourceException if a matching journal article resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleResource findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchArticleResourceException, SystemException {
		JournalArticleResource journalArticleResource = fetchByUuid_Last(uuid,
				orderByComparator);

		if (journalArticleResource != null) {
			return journalArticleResource;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleResourceException(msg.toString());
	}

	/**
	 * Returns the last journal article resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleResource fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<JournalArticleResource> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal article resources before and after the current journal article resource in the ordered set where uuid = &#63;.
	 *
	 * @param resourcePrimKey the primary key of the current journal article resource
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article resource
	 * @throws com.liferay.portlet.journal.NoSuchArticleResourceException if a journal article resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleResource[] findByUuid_PrevAndNext(
		long resourcePrimKey, String uuid, OrderByComparator orderByComparator)
		throws NoSuchArticleResourceException, SystemException {
		JournalArticleResource journalArticleResource = findByPrimaryKey(resourcePrimKey);

		Session session = null;

		try {
			session = openSession();

			JournalArticleResource[] array = new JournalArticleResourceImpl[3];

			array[0] = getByUuid_PrevAndNext(session, journalArticleResource,
					uuid, orderByComparator, true);

			array[1] = journalArticleResource;

			array[2] = getByUuid_PrevAndNext(session, journalArticleResource,
					uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticleResource getByUuid_PrevAndNext(Session session,
		JournalArticleResource journalArticleResource, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLERESOURCE_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticleResource);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticleResource> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the journal article resource where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchArticleResourceException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal article resource
	 * @throws com.liferay.portlet.journal.NoSuchArticleResourceException if a matching journal article resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleResource findByUUID_G(String uuid, long groupId)
		throws NoSuchArticleResourceException, SystemException {
		JournalArticleResource journalArticleResource = fetchByUUID_G(uuid,
				groupId);

		if (journalArticleResource == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchArticleResourceException(msg.toString());
		}

		return journalArticleResource;
	}

	/**
	 * Returns the journal article resource where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleResource fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the journal article resource where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleResource fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof JournalArticleResource) {
			JournalArticleResource journalArticleResource = (JournalArticleResource)result;

			if (!Validator.equals(uuid, journalArticleResource.getUuid()) ||
					(groupId != journalArticleResource.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_JOURNALARTICLERESOURCE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_G_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<JournalArticleResource> list = q.list();

				result = list;

				JournalArticleResource journalArticleResource = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					journalArticleResource = list.get(0);

					cacheResult(journalArticleResource);

					if ((journalArticleResource.getUuid() == null) ||
							!journalArticleResource.getUuid().equals(uuid) ||
							(journalArticleResource.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, journalArticleResource);
					}
				}

				return journalArticleResource;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
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
				return (JournalArticleResource)result;
			}
		}
	}

	/**
	 * Returns all the journal article resources where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal article resources
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticleResource> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal article resources where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal article resources
	 * @param end the upper bound of the range of journal article resources (not inclusive)
	 * @return the range of matching journal article resources
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticleResource> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal article resources where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal article resources
	 * @param end the upper bound of the range of journal article resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal article resources
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticleResource> findByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<JournalArticleResource> list = (List<JournalArticleResource>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticleResource journalArticleResource : list) {
				if ((groupId != journalArticleResource.getGroupId())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLERESOURCE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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

				qPos.add(groupId);

				list = (List<JournalArticleResource>)QueryUtil.list(q,
						getDialect(), start, end);
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
	 * Returns the first journal article resource in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article resource
	 * @throws com.liferay.portlet.journal.NoSuchArticleResourceException if a matching journal article resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleResource findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchArticleResourceException, SystemException {
		JournalArticleResource journalArticleResource = fetchByGroupId_First(groupId,
				orderByComparator);

		if (journalArticleResource != null) {
			return journalArticleResource;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleResourceException(msg.toString());
	}

	/**
	 * Returns the first journal article resource in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleResource fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalArticleResource> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article resource in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article resource
	 * @throws com.liferay.portlet.journal.NoSuchArticleResourceException if a matching journal article resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleResource findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchArticleResourceException, SystemException {
		JournalArticleResource journalArticleResource = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (journalArticleResource != null) {
			return journalArticleResource;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleResourceException(msg.toString());
	}

	/**
	 * Returns the last journal article resource in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleResource fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<JournalArticleResource> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal article resources before and after the current journal article resource in the ordered set where groupId = &#63;.
	 *
	 * @param resourcePrimKey the primary key of the current journal article resource
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article resource
	 * @throws com.liferay.portlet.journal.NoSuchArticleResourceException if a journal article resource with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleResource[] findByGroupId_PrevAndNext(
		long resourcePrimKey, long groupId, OrderByComparator orderByComparator)
		throws NoSuchArticleResourceException, SystemException {
		JournalArticleResource journalArticleResource = findByPrimaryKey(resourcePrimKey);

		Session session = null;

		try {
			session = openSession();

			JournalArticleResource[] array = new JournalArticleResourceImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					journalArticleResource, groupId, orderByComparator, true);

			array[1] = journalArticleResource;

			array[2] = getByGroupId_PrevAndNext(session,
					journalArticleResource, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticleResource getByGroupId_PrevAndNext(Session session,
		JournalArticleResource journalArticleResource, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLERESOURCE_WHERE);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticleResource);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticleResource> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the journal article resource where groupId = &#63; and articleId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchArticleResourceException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the matching journal article resource
	 * @throws com.liferay.portlet.journal.NoSuchArticleResourceException if a matching journal article resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleResource findByG_A(long groupId, String articleId)
		throws NoSuchArticleResourceException, SystemException {
		JournalArticleResource journalArticleResource = fetchByG_A(groupId,
				articleId);

		if (journalArticleResource == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", articleId=");
			msg.append(articleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchArticleResourceException(msg.toString());
		}

		return journalArticleResource;
	}

	/**
	 * Returns the journal article resource where groupId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleResource fetchByG_A(long groupId, String articleId)
		throws SystemException {
		return fetchByG_A(groupId, articleId, true);
	}

	/**
	 * Returns the journal article resource where groupId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleResource fetchByG_A(long groupId, String articleId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, articleId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_A,
					finderArgs, this);
		}

		if (result instanceof JournalArticleResource) {
			JournalArticleResource journalArticleResource = (JournalArticleResource)result;

			if ((groupId != journalArticleResource.getGroupId()) ||
					!Validator.equals(articleId,
						journalArticleResource.getArticleId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_JOURNALARTICLERESOURCE_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_ARTICLEID_1);
			}
			else {
				if (articleId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_A_ARTICLEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_A_ARTICLEID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				List<JournalArticleResource> list = q.list();

				result = list;

				JournalArticleResource journalArticleResource = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A,
						finderArgs, list);
				}
				else {
					journalArticleResource = list.get(0);

					cacheResult(journalArticleResource);

					if ((journalArticleResource.getGroupId() != groupId) ||
							(journalArticleResource.getArticleId() == null) ||
							!journalArticleResource.getArticleId()
													   .equals(articleId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A,
							finderArgs, journalArticleResource);
					}
				}

				return journalArticleResource;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_A,
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
				return (JournalArticleResource)result;
			}
		}
	}

	/**
	 * Returns all the journal article resources.
	 *
	 * @return the journal article resources
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticleResource> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal article resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal article resources
	 * @param end the upper bound of the range of journal article resources (not inclusive)
	 * @return the range of journal article resources
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticleResource> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal article resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal article resources
	 * @param end the upper bound of the range of journal article resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of journal article resources
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalArticleResource> findAll(int start, int end,
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

		List<JournalArticleResource> list = (List<JournalArticleResource>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_JOURNALARTICLERESOURCE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_JOURNALARTICLERESOURCE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<JournalArticleResource>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<JournalArticleResource>)QueryUtil.list(q,
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
	 * Removes all the journal article resources where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (JournalArticleResource journalArticleResource : findByUuid(uuid)) {
			remove(journalArticleResource);
		}
	}

	/**
	 * Removes the journal article resource where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the journal article resource that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleResource removeByUUID_G(String uuid, long groupId)
		throws NoSuchArticleResourceException, SystemException {
		JournalArticleResource journalArticleResource = findByUUID_G(uuid,
				groupId);

		return remove(journalArticleResource);
	}

	/**
	 * Removes all the journal article resources where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (JournalArticleResource journalArticleResource : findByGroupId(
				groupId)) {
			remove(journalArticleResource);
		}
	}

	/**
	 * Removes the journal article resource where groupId = &#63; and articleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the journal article resource that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public JournalArticleResource removeByG_A(long groupId, String articleId)
		throws NoSuchArticleResourceException, SystemException {
		JournalArticleResource journalArticleResource = findByG_A(groupId,
				articleId);

		return remove(journalArticleResource);
	}

	/**
	 * Removes all the journal article resources from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (JournalArticleResource journalArticleResource : findAll()) {
			remove(journalArticleResource);
		}
	}

	/**
	 * Returns the number of journal article resources where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching journal article resources
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALARTICLERESOURCE_WHERE);

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
	 * Returns the number of journal article resources where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching journal article resources
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLERESOURCE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_G_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal article resources where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal article resources
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALARTICLERESOURCE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal article resources where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the number of matching journal article resources
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_A(long groupId, String articleId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, articleId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_A,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLERESOURCE_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_ARTICLEID_1);
			}
			else {
				if (articleId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_A_ARTICLEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_A_ARTICLEID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_A, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal article resources.
	 *
	 * @return the number of journal article resources
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_JOURNALARTICLERESOURCE);

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
	 * Initializes the journal article resource persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.journal.model.JournalArticleResource")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<JournalArticleResource>> listenersList = new ArrayList<ModelListener<JournalArticleResource>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<JournalArticleResource>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(JournalArticleResourceImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = JournalArticlePersistence.class)
	protected JournalArticlePersistence journalArticlePersistence;
	@BeanReference(type = JournalArticleImagePersistence.class)
	protected JournalArticleImagePersistence journalArticleImagePersistence;
	@BeanReference(type = JournalArticleResourcePersistence.class)
	protected JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(type = JournalContentSearchPersistence.class)
	protected JournalContentSearchPersistence journalContentSearchPersistence;
	@BeanReference(type = JournalFeedPersistence.class)
	protected JournalFeedPersistence journalFeedPersistence;
	@BeanReference(type = JournalFolderPersistence.class)
	protected JournalFolderPersistence journalFolderPersistence;
	@BeanReference(type = JournalStructurePersistence.class)
	protected JournalStructurePersistence journalStructurePersistence;
	@BeanReference(type = JournalTemplatePersistence.class)
	protected JournalTemplatePersistence journalTemplatePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_JOURNALARTICLERESOURCE = "SELECT journalArticleResource FROM JournalArticleResource journalArticleResource";
	private static final String _SQL_SELECT_JOURNALARTICLERESOURCE_WHERE = "SELECT journalArticleResource FROM JournalArticleResource journalArticleResource WHERE ";
	private static final String _SQL_COUNT_JOURNALARTICLERESOURCE = "SELECT COUNT(journalArticleResource) FROM JournalArticleResource journalArticleResource";
	private static final String _SQL_COUNT_JOURNALARTICLERESOURCE_WHERE = "SELECT COUNT(journalArticleResource) FROM JournalArticleResource journalArticleResource WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "journalArticleResource.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "journalArticleResource.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(journalArticleResource.uuid IS NULL OR journalArticleResource.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "journalArticleResource.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "journalArticleResource.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(journalArticleResource.uuid IS NULL OR journalArticleResource.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "journalArticleResource.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "journalArticleResource.groupId = ?";
	private static final String _FINDER_COLUMN_G_A_GROUPID_2 = "journalArticleResource.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_ARTICLEID_1 = "journalArticleResource.articleId IS NULL";
	private static final String _FINDER_COLUMN_G_A_ARTICLEID_2 = "journalArticleResource.articleId = ?";
	private static final String _FINDER_COLUMN_G_A_ARTICLEID_3 = "(journalArticleResource.articleId IS NULL OR journalArticleResource.articleId = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "journalArticleResource.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No JournalArticleResource exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No JournalArticleResource exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(JournalArticleResourcePersistenceImpl.class);
	private static JournalArticleResource _nullJournalArticleResource = new JournalArticleResourceImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<JournalArticleResource> toCacheModel() {
				return _nullJournalArticleResourceCacheModel;
			}
		};

	private static CacheModel<JournalArticleResource> _nullJournalArticleResourceCacheModel =
		new CacheModel<JournalArticleResource>() {
			public JournalArticleResource toEntityModel() {
				return _nullJournalArticleResource;
			}
		};
}