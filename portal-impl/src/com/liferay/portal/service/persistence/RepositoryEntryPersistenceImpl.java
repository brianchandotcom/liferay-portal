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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchRepositoryEntryException;
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
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.model.impl.RepositoryEntryImpl;
import com.liferay.portal.model.impl.RepositoryEntryModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the repository entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryEntryPersistence
 * @see RepositoryEntryUtil
 * @generated
 */
public class RepositoryEntryPersistenceImpl extends BasePersistenceImpl<RepositoryEntry>
	implements RepositoryEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link RepositoryEntryUtil} to access the repository entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = RepositoryEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryModelImpl.FINDER_CACHE_ENABLED,
			RepositoryEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryModelImpl.FINDER_CACHE_ENABLED,
			RepositoryEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			RepositoryEntryModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryModelImpl.FINDER_CACHE_ENABLED,
			RepositoryEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			RepositoryEntryModelImpl.UUID_COLUMN_BITMASK |
			RepositoryEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_REPOSITORYID =
		new FinderPath(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryModelImpl.FINDER_CACHE_ENABLED,
			RepositoryEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByRepositoryId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REPOSITORYID =
		new FinderPath(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryModelImpl.FINDER_CACHE_ENABLED,
			RepositoryEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRepositoryId",
			new String[] { Long.class.getName() },
			RepositoryEntryModelImpl.REPOSITORYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_REPOSITORYID = new FinderPath(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRepositoryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_R_M = new FinderPath(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryModelImpl.FINDER_CACHE_ENABLED,
			RepositoryEntryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByR_M",
			new String[] { Long.class.getName(), String.class.getName() },
			RepositoryEntryModelImpl.REPOSITORYID_COLUMN_BITMASK |
			RepositoryEntryModelImpl.MAPPEDID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_R_M = new FinderPath(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_M",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryModelImpl.FINDER_CACHE_ENABLED,
			RepositoryEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryModelImpl.FINDER_CACHE_ENABLED,
			RepositoryEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the repository entry in the entity cache if it is enabled.
	 *
	 * @param repositoryEntry the repository entry
	 */
	public void cacheResult(RepositoryEntry repositoryEntry) {
		EntityCacheUtil.putResult(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryImpl.class, repositoryEntry.getPrimaryKey(),
			repositoryEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				repositoryEntry.getUuid(),
				Long.valueOf(repositoryEntry.getGroupId())
			}, repositoryEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_R_M,
			new Object[] {
				Long.valueOf(repositoryEntry.getRepositoryId()),
				
			repositoryEntry.getMappedId()
			}, repositoryEntry);

		repositoryEntry.resetOriginalValues();
	}

	/**
	 * Caches the repository entries in the entity cache if it is enabled.
	 *
	 * @param repositoryEntries the repository entries
	 */
	public void cacheResult(List<RepositoryEntry> repositoryEntries) {
		for (RepositoryEntry repositoryEntry : repositoryEntries) {
			if (EntityCacheUtil.getResult(
						RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
						RepositoryEntryImpl.class,
						repositoryEntry.getPrimaryKey()) == null) {
				cacheResult(repositoryEntry);
			}
			else {
				repositoryEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all repository entries.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(RepositoryEntryImpl.class.getName());
		}

		EntityCacheUtil.clearCache(RepositoryEntryImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the repository entry.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RepositoryEntry repositoryEntry) {
		EntityCacheUtil.removeResult(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryImpl.class, repositoryEntry.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(repositoryEntry);
	}

	@Override
	public void clearCache(List<RepositoryEntry> repositoryEntries) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (RepositoryEntry repositoryEntry : repositoryEntries) {
			EntityCacheUtil.removeResult(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
				RepositoryEntryImpl.class, repositoryEntry.getPrimaryKey());

			clearUniqueFindersCache(repositoryEntry);
		}
	}

	protected void clearUniqueFindersCache(RepositoryEntry repositoryEntry) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				repositoryEntry.getUuid(),
				Long.valueOf(repositoryEntry.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_R_M,
			new Object[] {
				Long.valueOf(repositoryEntry.getRepositoryId()),
				
			repositoryEntry.getMappedId()
			});
	}

	/**
	 * Creates a new repository entry with the primary key. Does not add the repository entry to the database.
	 *
	 * @param repositoryEntryId the primary key for the new repository entry
	 * @return the new repository entry
	 */
	public RepositoryEntry create(long repositoryEntryId) {
		RepositoryEntry repositoryEntry = new RepositoryEntryImpl();

		repositoryEntry.setNew(true);
		repositoryEntry.setPrimaryKey(repositoryEntryId);

		String uuid = PortalUUIDUtil.generate();

		repositoryEntry.setUuid(uuid);

		return repositoryEntry;
	}

	/**
	 * Removes the repository entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param repositoryEntryId the primary key of the repository entry
	 * @return the repository entry that was removed
	 * @throws com.liferay.portal.NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry remove(long repositoryEntryId)
		throws NoSuchRepositoryEntryException, SystemException {
		return remove(Long.valueOf(repositoryEntryId));
	}

	/**
	 * Removes the repository entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the repository entry
	 * @return the repository entry that was removed
	 * @throws com.liferay.portal.NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public RepositoryEntry remove(Serializable primaryKey)
		throws NoSuchRepositoryEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RepositoryEntry repositoryEntry = (RepositoryEntry)session.get(RepositoryEntryImpl.class,
					primaryKey);

			if (repositoryEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRepositoryEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(repositoryEntry);
		}
		catch (NoSuchRepositoryEntryException nsee) {
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
	protected RepositoryEntry removeImpl(RepositoryEntry repositoryEntry)
		throws SystemException {
		repositoryEntry = toUnwrappedModel(repositoryEntry);

		Session session = null;

		try {
			session = openSession();

			if (repositoryEntry.isCachedModel()) {
				repositoryEntry = (RepositoryEntry)session.get(RepositoryEntryImpl.class,
						repositoryEntry.getPrimaryKeyObj());
			}

			if (repositoryEntry != null) {
				session.delete(repositoryEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (repositoryEntry != null) {
			clearCache(repositoryEntry);
		}

		return repositoryEntry;
	}

	@Override
	public RepositoryEntry updateImpl(
		com.liferay.portal.model.RepositoryEntry repositoryEntry)
		throws SystemException {
		repositoryEntry = toUnwrappedModel(repositoryEntry);

		boolean isNew = repositoryEntry.isNew();

		RepositoryEntryModelImpl repositoryEntryModelImpl = (RepositoryEntryModelImpl)repositoryEntry;

		if (Validator.isNull(repositoryEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			repositoryEntry.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (repositoryEntry.isNew()) {
				session.save(repositoryEntry);

				repositoryEntry.setNew(false);
			}
			else {
				session.merge(repositoryEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !RepositoryEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((repositoryEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						repositoryEntryModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { repositoryEntryModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((repositoryEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REPOSITORYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(repositoryEntryModelImpl.getOriginalRepositoryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_REPOSITORYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REPOSITORYID,
					args);

				args = new Object[] {
						Long.valueOf(repositoryEntryModelImpl.getRepositoryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_REPOSITORYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REPOSITORYID,
					args);
			}
		}

		EntityCacheUtil.putResult(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryEntryImpl.class, repositoryEntry.getPrimaryKey(),
			repositoryEntry);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					repositoryEntry.getUuid(),
					Long.valueOf(repositoryEntry.getGroupId())
				}, repositoryEntry);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_R_M,
				new Object[] {
					Long.valueOf(repositoryEntry.getRepositoryId()),
					
				repositoryEntry.getMappedId()
				}, repositoryEntry);
		}
		else {
			if ((repositoryEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						repositoryEntryModelImpl.getOriginalUuid(),
						Long.valueOf(repositoryEntryModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						repositoryEntry.getUuid(),
						Long.valueOf(repositoryEntry.getGroupId())
					}, repositoryEntry);
			}

			if ((repositoryEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_R_M.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(repositoryEntryModelImpl.getOriginalRepositoryId()),
						
						repositoryEntryModelImpl.getOriginalMappedId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_R_M, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_R_M, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_R_M,
					new Object[] {
						Long.valueOf(repositoryEntry.getRepositoryId()),
						
					repositoryEntry.getMappedId()
					}, repositoryEntry);
			}
		}

		return repositoryEntry;
	}

	protected RepositoryEntry toUnwrappedModel(RepositoryEntry repositoryEntry) {
		if (repositoryEntry instanceof RepositoryEntryImpl) {
			return repositoryEntry;
		}

		RepositoryEntryImpl repositoryEntryImpl = new RepositoryEntryImpl();

		repositoryEntryImpl.setNew(repositoryEntry.isNew());
		repositoryEntryImpl.setPrimaryKey(repositoryEntry.getPrimaryKey());

		repositoryEntryImpl.setUuid(repositoryEntry.getUuid());
		repositoryEntryImpl.setRepositoryEntryId(repositoryEntry.getRepositoryEntryId());
		repositoryEntryImpl.setGroupId(repositoryEntry.getGroupId());
		repositoryEntryImpl.setRepositoryId(repositoryEntry.getRepositoryId());
		repositoryEntryImpl.setMappedId(repositoryEntry.getMappedId());
		repositoryEntryImpl.setManualCheckInRequired(repositoryEntry.isManualCheckInRequired());

		return repositoryEntryImpl;
	}

	/**
	 * Returns the repository entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the repository entry
	 * @return the repository entry
	 * @throws com.liferay.portal.NoSuchModelException if a repository entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public RepositoryEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the repository entry with the primary key or throws a {@link com.liferay.portal.NoSuchRepositoryEntryException} if it could not be found.
	 *
	 * @param repositoryEntryId the primary key of the repository entry
	 * @return the repository entry
	 * @throws com.liferay.portal.NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry findByPrimaryKey(long repositoryEntryId)
		throws NoSuchRepositoryEntryException, SystemException {
		RepositoryEntry repositoryEntry = fetchByPrimaryKey(repositoryEntryId);

		if (repositoryEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + repositoryEntryId);
			}

			throw new NoSuchRepositoryEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				repositoryEntryId);
		}

		return repositoryEntry;
	}

	/**
	 * Returns the repository entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the repository entry
	 * @return the repository entry, or <code>null</code> if a repository entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public RepositoryEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the repository entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param repositoryEntryId the primary key of the repository entry
	 * @return the repository entry, or <code>null</code> if a repository entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry fetchByPrimaryKey(long repositoryEntryId)
		throws SystemException {
		RepositoryEntry repositoryEntry = (RepositoryEntry)EntityCacheUtil.getResult(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
				RepositoryEntryImpl.class, repositoryEntryId);

		if (repositoryEntry == _nullRepositoryEntry) {
			return null;
		}

		if (repositoryEntry == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				repositoryEntry = (RepositoryEntry)session.get(RepositoryEntryImpl.class,
						Long.valueOf(repositoryEntryId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (repositoryEntry != null) {
					cacheResult(repositoryEntry);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(RepositoryEntryModelImpl.ENTITY_CACHE_ENABLED,
						RepositoryEntryImpl.class, repositoryEntryId,
						_nullRepositoryEntry);
				}

				closeSession(session);
			}
		}

		return repositoryEntry;
	}

	/**
	 * Returns all the repository entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RepositoryEntry> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the repository entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of repository entries
	 * @param end the upper bound of the range of repository entries (not inclusive)
	 * @return the range of matching repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RepositoryEntry> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the repository entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of repository entries
	 * @param end the upper bound of the range of repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RepositoryEntry> findByUuid(String uuid, int start, int end,
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

		List<RepositoryEntry> list = (List<RepositoryEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (RepositoryEntry repositoryEntry : list) {
				if (!Validator.equals(uuid, repositoryEntry.getUuid())) {
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

			query.append(_SQL_SELECT_REPOSITORYENTRY_WHERE);

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

				list = (List<RepositoryEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository entry
	 * @throws com.liferay.portal.NoSuchRepositoryEntryException if a matching repository entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchRepositoryEntryException, SystemException {
		RepositoryEntry repositoryEntry = fetchByUuid_First(uuid,
				orderByComparator);

		if (repositoryEntry != null) {
			return repositoryEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRepositoryEntryException(msg.toString());
	}

	/**
	 * Returns the first repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository entry, or <code>null</code> if a matching repository entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<RepositoryEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching repository entry
	 * @throws com.liferay.portal.NoSuchRepositoryEntryException if a matching repository entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchRepositoryEntryException, SystemException {
		RepositoryEntry repositoryEntry = fetchByUuid_Last(uuid,
				orderByComparator);

		if (repositoryEntry != null) {
			return repositoryEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRepositoryEntryException(msg.toString());
	}

	/**
	 * Returns the last repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching repository entry, or <code>null</code> if a matching repository entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<RepositoryEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the repository entries before and after the current repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param repositoryEntryId the primary key of the current repository entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next repository entry
	 * @throws com.liferay.portal.NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry[] findByUuid_PrevAndNext(long repositoryEntryId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchRepositoryEntryException, SystemException {
		RepositoryEntry repositoryEntry = findByPrimaryKey(repositoryEntryId);

		Session session = null;

		try {
			session = openSession();

			RepositoryEntry[] array = new RepositoryEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, repositoryEntry, uuid,
					orderByComparator, true);

			array[1] = repositoryEntry;

			array[2] = getByUuid_PrevAndNext(session, repositoryEntry, uuid,
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

	protected RepositoryEntry getByUuid_PrevAndNext(Session session,
		RepositoryEntry repositoryEntry, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_REPOSITORYENTRY_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(repositoryEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<RepositoryEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the repository entry where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portal.NoSuchRepositoryEntryException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching repository entry
	 * @throws com.liferay.portal.NoSuchRepositoryEntryException if a matching repository entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchRepositoryEntryException, SystemException {
		RepositoryEntry repositoryEntry = fetchByUUID_G(uuid, groupId);

		if (repositoryEntry == null) {
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

			throw new NoSuchRepositoryEntryException(msg.toString());
		}

		return repositoryEntry;
	}

	/**
	 * Returns the repository entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the repository entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof RepositoryEntry) {
			RepositoryEntry repositoryEntry = (RepositoryEntry)result;

			if (!Validator.equals(uuid, repositoryEntry.getUuid()) ||
					(groupId != repositoryEntry.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_REPOSITORYENTRY_WHERE);

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

				List<RepositoryEntry> list = q.list();

				result = list;

				RepositoryEntry repositoryEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					repositoryEntry = list.get(0);

					cacheResult(repositoryEntry);

					if ((repositoryEntry.getUuid() == null) ||
							!repositoryEntry.getUuid().equals(uuid) ||
							(repositoryEntry.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, repositoryEntry);
					}
				}

				return repositoryEntry;
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
				return (RepositoryEntry)result;
			}
		}
	}

	/**
	 * Returns all the repository entries where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @return the matching repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RepositoryEntry> findByRepositoryId(long repositoryId)
		throws SystemException {
		return findByRepositoryId(repositoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the repository entries where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of repository entries
	 * @param end the upper bound of the range of repository entries (not inclusive)
	 * @return the range of matching repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RepositoryEntry> findByRepositoryId(long repositoryId,
		int start, int end) throws SystemException {
		return findByRepositoryId(repositoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the repository entries where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of repository entries
	 * @param end the upper bound of the range of repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RepositoryEntry> findByRepositoryId(long repositoryId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REPOSITORYID;
			finderArgs = new Object[] { repositoryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_REPOSITORYID;
			finderArgs = new Object[] {
					repositoryId,
					
					start, end, orderByComparator
				};
		}

		List<RepositoryEntry> list = (List<RepositoryEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (RepositoryEntry repositoryEntry : list) {
				if ((repositoryId != repositoryEntry.getRepositoryId())) {
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

			query.append(_SQL_SELECT_REPOSITORYENTRY_WHERE);

			query.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

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

				qPos.add(repositoryId);

				list = (List<RepositoryEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first repository entry in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository entry
	 * @throws com.liferay.portal.NoSuchRepositoryEntryException if a matching repository entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry findByRepositoryId_First(long repositoryId,
		OrderByComparator orderByComparator)
		throws NoSuchRepositoryEntryException, SystemException {
		RepositoryEntry repositoryEntry = fetchByRepositoryId_First(repositoryId,
				orderByComparator);

		if (repositoryEntry != null) {
			return repositoryEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("repositoryId=");
		msg.append(repositoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRepositoryEntryException(msg.toString());
	}

	/**
	 * Returns the first repository entry in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository entry, or <code>null</code> if a matching repository entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry fetchByRepositoryId_First(long repositoryId,
		OrderByComparator orderByComparator) throws SystemException {
		List<RepositoryEntry> list = findByRepositoryId(repositoryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last repository entry in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching repository entry
	 * @throws com.liferay.portal.NoSuchRepositoryEntryException if a matching repository entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry findByRepositoryId_Last(long repositoryId,
		OrderByComparator orderByComparator)
		throws NoSuchRepositoryEntryException, SystemException {
		RepositoryEntry repositoryEntry = fetchByRepositoryId_Last(repositoryId,
				orderByComparator);

		if (repositoryEntry != null) {
			return repositoryEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("repositoryId=");
		msg.append(repositoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRepositoryEntryException(msg.toString());
	}

	/**
	 * Returns the last repository entry in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching repository entry, or <code>null</code> if a matching repository entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry fetchByRepositoryId_Last(long repositoryId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByRepositoryId(repositoryId);

		List<RepositoryEntry> list = findByRepositoryId(repositoryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the repository entries before and after the current repository entry in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryEntryId the primary key of the current repository entry
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next repository entry
	 * @throws com.liferay.portal.NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry[] findByRepositoryId_PrevAndNext(
		long repositoryEntryId, long repositoryId,
		OrderByComparator orderByComparator)
		throws NoSuchRepositoryEntryException, SystemException {
		RepositoryEntry repositoryEntry = findByPrimaryKey(repositoryEntryId);

		Session session = null;

		try {
			session = openSession();

			RepositoryEntry[] array = new RepositoryEntryImpl[3];

			array[0] = getByRepositoryId_PrevAndNext(session, repositoryEntry,
					repositoryId, orderByComparator, true);

			array[1] = repositoryEntry;

			array[2] = getByRepositoryId_PrevAndNext(session, repositoryEntry,
					repositoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected RepositoryEntry getByRepositoryId_PrevAndNext(Session session,
		RepositoryEntry repositoryEntry, long repositoryId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_REPOSITORYENTRY_WHERE);

		query.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

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

		qPos.add(repositoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(repositoryEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<RepositoryEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the repository entry where repositoryId = &#63; and mappedId = &#63; or throws a {@link com.liferay.portal.NoSuchRepositoryEntryException} if it could not be found.
	 *
	 * @param repositoryId the repository ID
	 * @param mappedId the mapped ID
	 * @return the matching repository entry
	 * @throws com.liferay.portal.NoSuchRepositoryEntryException if a matching repository entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry findByR_M(long repositoryId, String mappedId)
		throws NoSuchRepositoryEntryException, SystemException {
		RepositoryEntry repositoryEntry = fetchByR_M(repositoryId, mappedId);

		if (repositoryEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("repositoryId=");
			msg.append(repositoryId);

			msg.append(", mappedId=");
			msg.append(mappedId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchRepositoryEntryException(msg.toString());
		}

		return repositoryEntry;
	}

	/**
	 * Returns the repository entry where repositoryId = &#63; and mappedId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param repositoryId the repository ID
	 * @param mappedId the mapped ID
	 * @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry fetchByR_M(long repositoryId, String mappedId)
		throws SystemException {
		return fetchByR_M(repositoryId, mappedId, true);
	}

	/**
	 * Returns the repository entry where repositoryId = &#63; and mappedId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param repositoryId the repository ID
	 * @param mappedId the mapped ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry fetchByR_M(long repositoryId, String mappedId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { repositoryId, mappedId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_R_M,
					finderArgs, this);
		}

		if (result instanceof RepositoryEntry) {
			RepositoryEntry repositoryEntry = (RepositoryEntry)result;

			if ((repositoryId != repositoryEntry.getRepositoryId()) ||
					!Validator.equals(mappedId, repositoryEntry.getMappedId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_REPOSITORYENTRY_WHERE);

			query.append(_FINDER_COLUMN_R_M_REPOSITORYID_2);

			if (mappedId == null) {
				query.append(_FINDER_COLUMN_R_M_MAPPEDID_1);
			}
			else {
				if (mappedId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_R_M_MAPPEDID_3);
				}
				else {
					query.append(_FINDER_COLUMN_R_M_MAPPEDID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(repositoryId);

				if (mappedId != null) {
					qPos.add(mappedId);
				}

				List<RepositoryEntry> list = q.list();

				result = list;

				RepositoryEntry repositoryEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_R_M,
						finderArgs, list);
				}
				else {
					repositoryEntry = list.get(0);

					cacheResult(repositoryEntry);

					if ((repositoryEntry.getRepositoryId() != repositoryId) ||
							(repositoryEntry.getMappedId() == null) ||
							!repositoryEntry.getMappedId().equals(mappedId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_R_M,
							finderArgs, repositoryEntry);
					}
				}

				return repositoryEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_R_M,
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
				return (RepositoryEntry)result;
			}
		}
	}

	/**
	 * Returns all the repository entries.
	 *
	 * @return the repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RepositoryEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the repository entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository entries
	 * @param end the upper bound of the range of repository entries (not inclusive)
	 * @return the range of repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RepositoryEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the repository entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository entries
	 * @param end the upper bound of the range of repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RepositoryEntry> findAll(int start, int end,
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

		List<RepositoryEntry> list = (List<RepositoryEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_REPOSITORYENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_REPOSITORYENTRY;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<RepositoryEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<RepositoryEntry>)QueryUtil.list(q,
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
	 * Removes all the repository entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (RepositoryEntry repositoryEntry : findByUuid(uuid)) {
			remove(repositoryEntry);
		}
	}

	/**
	 * Removes the repository entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the repository entry that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchRepositoryEntryException, SystemException {
		RepositoryEntry repositoryEntry = findByUUID_G(uuid, groupId);

		return remove(repositoryEntry);
	}

	/**
	 * Removes all the repository entries where repositoryId = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByRepositoryId(long repositoryId)
		throws SystemException {
		for (RepositoryEntry repositoryEntry : findByRepositoryId(repositoryId)) {
			remove(repositoryEntry);
		}
	}

	/**
	 * Removes the repository entry where repositoryId = &#63; and mappedId = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 * @param mappedId the mapped ID
	 * @return the repository entry that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public RepositoryEntry removeByR_M(long repositoryId, String mappedId)
		throws NoSuchRepositoryEntryException, SystemException {
		RepositoryEntry repositoryEntry = findByR_M(repositoryId, mappedId);

		return remove(repositoryEntry);
	}

	/**
	 * Removes all the repository entries from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (RepositoryEntry repositoryEntry : findAll()) {
			remove(repositoryEntry);
		}
	}

	/**
	 * Returns the number of repository entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_REPOSITORYENTRY_WHERE);

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
	 * Returns the number of repository entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_REPOSITORYENTRY_WHERE);

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
	 * Returns the number of repository entries where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @return the number of matching repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByRepositoryId(long repositoryId) throws SystemException {
		Object[] finderArgs = new Object[] { repositoryId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_REPOSITORYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_REPOSITORYENTRY_WHERE);

			query.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(repositoryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_REPOSITORYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of repository entries where repositoryId = &#63; and mappedId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param mappedId the mapped ID
	 * @return the number of matching repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByR_M(long repositoryId, String mappedId)
		throws SystemException {
		Object[] finderArgs = new Object[] { repositoryId, mappedId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_R_M,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_REPOSITORYENTRY_WHERE);

			query.append(_FINDER_COLUMN_R_M_REPOSITORYID_2);

			if (mappedId == null) {
				query.append(_FINDER_COLUMN_R_M_MAPPEDID_1);
			}
			else {
				if (mappedId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_R_M_MAPPEDID_3);
				}
				else {
					query.append(_FINDER_COLUMN_R_M_MAPPEDID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(repositoryId);

				if (mappedId != null) {
					qPos.add(mappedId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_R_M, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of repository entries.
	 *
	 * @return the number of repository entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_REPOSITORYENTRY);

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
	 * Initializes the repository entry persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.RepositoryEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<RepositoryEntry>> listenersList = new ArrayList<ModelListener<RepositoryEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<RepositoryEntry>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(RepositoryEntryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
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
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceBlockPersistence.class)
	protected ResourceBlockPersistence resourceBlockPersistence;
	@BeanReference(type = ResourceBlockPermissionPersistence.class)
	protected ResourceBlockPermissionPersistence resourceBlockPermissionPersistence;
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
	private static final String _SQL_SELECT_REPOSITORYENTRY = "SELECT repositoryEntry FROM RepositoryEntry repositoryEntry";
	private static final String _SQL_SELECT_REPOSITORYENTRY_WHERE = "SELECT repositoryEntry FROM RepositoryEntry repositoryEntry WHERE ";
	private static final String _SQL_COUNT_REPOSITORYENTRY = "SELECT COUNT(repositoryEntry) FROM RepositoryEntry repositoryEntry";
	private static final String _SQL_COUNT_REPOSITORYENTRY_WHERE = "SELECT COUNT(repositoryEntry) FROM RepositoryEntry repositoryEntry WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "repositoryEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "repositoryEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(repositoryEntry.uuid IS NULL OR repositoryEntry.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "repositoryEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "repositoryEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(repositoryEntry.uuid IS NULL OR repositoryEntry.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "repositoryEntry.groupId = ?";
	private static final String _FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2 = "repositoryEntry.repositoryId = ?";
	private static final String _FINDER_COLUMN_R_M_REPOSITORYID_2 = "repositoryEntry.repositoryId = ? AND ";
	private static final String _FINDER_COLUMN_R_M_MAPPEDID_1 = "repositoryEntry.mappedId IS NULL";
	private static final String _FINDER_COLUMN_R_M_MAPPEDID_2 = "repositoryEntry.mappedId = ?";
	private static final String _FINDER_COLUMN_R_M_MAPPEDID_3 = "(repositoryEntry.mappedId IS NULL OR repositoryEntry.mappedId = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "repositoryEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No RepositoryEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No RepositoryEntry exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(RepositoryEntryPersistenceImpl.class);
	private static RepositoryEntry _nullRepositoryEntry = new RepositoryEntryImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<RepositoryEntry> toCacheModel() {
				return _nullRepositoryEntryCacheModel;
			}
		};

	private static CacheModel<RepositoryEntry> _nullRepositoryEntryCacheModel = new CacheModel<RepositoryEntry>() {
			public RepositoryEntry toEntityModel() {
				return _nullRepositoryEntry;
			}
		};
}