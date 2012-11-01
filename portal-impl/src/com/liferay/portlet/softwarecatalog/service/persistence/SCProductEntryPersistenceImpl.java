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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.ImagePersistence;
import com.liferay.portal.service.persistence.SubscriptionPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence;
import com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryImpl;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the s c product entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SCProductEntryPersistence
 * @see SCProductEntryUtil
 * @generated
 */
public class SCProductEntryPersistenceImpl extends BasePersistenceImpl<SCProductEntry>
	implements SCProductEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SCProductEntryUtil} to access the s c product entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SCProductEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			SCProductEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			SCProductEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			SCProductEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			SCProductEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			SCProductEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			SCProductEntryModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			SCProductEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			SCProductEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U",
			new String[] { Long.class.getName(), Long.class.getName() },
			SCProductEntryModelImpl.GROUPID_COLUMN_BITMASK |
			SCProductEntryModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_RG_RA = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			SCProductEntryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByRG_RA",
			new String[] { String.class.getName(), String.class.getName() },
			SCProductEntryModelImpl.REPOGROUPID_COLUMN_BITMASK |
			SCProductEntryModelImpl.REPOARTIFACTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_RG_RA = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRG_RA",
			new String[] { String.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			SCProductEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED,
			SCProductEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the s c product entry in the entity cache if it is enabled.
	 *
	 * @param scProductEntry the s c product entry
	 */
	public void cacheResult(SCProductEntry scProductEntry) {
		EntityCacheUtil.putResult(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryImpl.class, scProductEntry.getPrimaryKey(),
			scProductEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_RG_RA,
			new Object[] {
				scProductEntry.getRepoGroupId(),
				
			scProductEntry.getRepoArtifactId()
			}, scProductEntry);

		scProductEntry.resetOriginalValues();
	}

	/**
	 * Caches the s c product entries in the entity cache if it is enabled.
	 *
	 * @param scProductEntries the s c product entries
	 */
	public void cacheResult(List<SCProductEntry> scProductEntries) {
		for (SCProductEntry scProductEntry : scProductEntries) {
			if (EntityCacheUtil.getResult(
						SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
						SCProductEntryImpl.class, scProductEntry.getPrimaryKey()) == null) {
				cacheResult(scProductEntry);
			}
			else {
				scProductEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all s c product entries.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SCProductEntryImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SCProductEntryImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the s c product entry.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SCProductEntry scProductEntry) {
		EntityCacheUtil.removeResult(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryImpl.class, scProductEntry.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(scProductEntry);
	}

	@Override
	public void clearCache(List<SCProductEntry> scProductEntries) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SCProductEntry scProductEntry : scProductEntries) {
			EntityCacheUtil.removeResult(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
				SCProductEntryImpl.class, scProductEntry.getPrimaryKey());

			clearUniqueFindersCache(scProductEntry);
		}
	}

	protected void clearUniqueFindersCache(SCProductEntry scProductEntry) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_RG_RA,
			new Object[] {
				scProductEntry.getRepoGroupId(),
				
			scProductEntry.getRepoArtifactId()
			});
	}

	/**
	 * Creates a new s c product entry with the primary key. Does not add the s c product entry to the database.
	 *
	 * @param productEntryId the primary key for the new s c product entry
	 * @return the new s c product entry
	 */
	public SCProductEntry create(long productEntryId) {
		SCProductEntry scProductEntry = new SCProductEntryImpl();

		scProductEntry.setNew(true);
		scProductEntry.setPrimaryKey(productEntryId);

		return scProductEntry;
	}

	/**
	 * Removes the s c product entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param productEntryId the primary key of the s c product entry
	 * @return the s c product entry that was removed
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a s c product entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry remove(long productEntryId)
		throws NoSuchProductEntryException, SystemException {
		return remove(Long.valueOf(productEntryId));
	}

	/**
	 * Removes the s c product entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the s c product entry
	 * @return the s c product entry that was removed
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a s c product entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCProductEntry remove(Serializable primaryKey)
		throws NoSuchProductEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SCProductEntry scProductEntry = (SCProductEntry)session.get(SCProductEntryImpl.class,
					primaryKey);

			if (scProductEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchProductEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(scProductEntry);
		}
		catch (NoSuchProductEntryException nsee) {
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
	protected SCProductEntry removeImpl(SCProductEntry scProductEntry)
		throws SystemException {
		scProductEntry = toUnwrappedModel(scProductEntry);

		try {
			clearSCLicenses.clear(scProductEntry.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			if (scProductEntry.isCachedModel()) {
				scProductEntry = (SCProductEntry)session.get(SCProductEntryImpl.class,
						scProductEntry.getPrimaryKeyObj());
			}

			if (scProductEntry != null) {
				session.delete(scProductEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (scProductEntry != null) {
			clearCache(scProductEntry);
		}

		return scProductEntry;
	}

	@Override
	public SCProductEntry updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws SystemException {
		scProductEntry = toUnwrappedModel(scProductEntry);

		boolean isNew = scProductEntry.isNew();

		SCProductEntryModelImpl scProductEntryModelImpl = (SCProductEntryModelImpl)scProductEntry;

		Session session = null;

		try {
			session = openSession();

			if (scProductEntry.isNew()) {
				session.save(scProductEntry);

				scProductEntry.setNew(false);
			}
			else {
				session.merge(scProductEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SCProductEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((scProductEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(scProductEntryModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(scProductEntryModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((scProductEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(scProductEntryModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(scProductEntryModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((scProductEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(scProductEntryModelImpl.getOriginalGroupId()),
						Long.valueOf(scProductEntryModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U,
					args);

				args = new Object[] {
						Long.valueOf(scProductEntryModelImpl.getGroupId()),
						Long.valueOf(scProductEntryModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U,
					args);
			}
		}

		EntityCacheUtil.putResult(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryImpl.class, scProductEntry.getPrimaryKey(),
			scProductEntry);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_RG_RA,
				new Object[] {
					scProductEntry.getRepoGroupId(),
					
				scProductEntry.getRepoArtifactId()
				}, scProductEntry);
		}
		else {
			if ((scProductEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_RG_RA.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						scProductEntryModelImpl.getOriginalRepoGroupId(),
						
						scProductEntryModelImpl.getOriginalRepoArtifactId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_RG_RA, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_RG_RA, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_RG_RA,
					new Object[] {
						scProductEntry.getRepoGroupId(),
						
					scProductEntry.getRepoArtifactId()
					}, scProductEntry);
			}
		}

		return scProductEntry;
	}

	protected SCProductEntry toUnwrappedModel(SCProductEntry scProductEntry) {
		if (scProductEntry instanceof SCProductEntryImpl) {
			return scProductEntry;
		}

		SCProductEntryImpl scProductEntryImpl = new SCProductEntryImpl();

		scProductEntryImpl.setNew(scProductEntry.isNew());
		scProductEntryImpl.setPrimaryKey(scProductEntry.getPrimaryKey());

		scProductEntryImpl.setProductEntryId(scProductEntry.getProductEntryId());
		scProductEntryImpl.setGroupId(scProductEntry.getGroupId());
		scProductEntryImpl.setCompanyId(scProductEntry.getCompanyId());
		scProductEntryImpl.setUserId(scProductEntry.getUserId());
		scProductEntryImpl.setUserName(scProductEntry.getUserName());
		scProductEntryImpl.setCreateDate(scProductEntry.getCreateDate());
		scProductEntryImpl.setModifiedDate(scProductEntry.getModifiedDate());
		scProductEntryImpl.setName(scProductEntry.getName());
		scProductEntryImpl.setType(scProductEntry.getType());
		scProductEntryImpl.setTags(scProductEntry.getTags());
		scProductEntryImpl.setShortDescription(scProductEntry.getShortDescription());
		scProductEntryImpl.setLongDescription(scProductEntry.getLongDescription());
		scProductEntryImpl.setPageURL(scProductEntry.getPageURL());
		scProductEntryImpl.setAuthor(scProductEntry.getAuthor());
		scProductEntryImpl.setRepoGroupId(scProductEntry.getRepoGroupId());
		scProductEntryImpl.setRepoArtifactId(scProductEntry.getRepoArtifactId());

		return scProductEntryImpl;
	}

	/**
	 * Returns the s c product entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the s c product entry
	 * @return the s c product entry
	 * @throws com.liferay.portal.NoSuchModelException if a s c product entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCProductEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the s c product entry with the primary key or throws a {@link com.liferay.portlet.softwarecatalog.NoSuchProductEntryException} if it could not be found.
	 *
	 * @param productEntryId the primary key of the s c product entry
	 * @return the s c product entry
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a s c product entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry findByPrimaryKey(long productEntryId)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = fetchByPrimaryKey(productEntryId);

		if (scProductEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + productEntryId);
			}

			throw new NoSuchProductEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				productEntryId);
		}

		return scProductEntry;
	}

	/**
	 * Returns the s c product entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the s c product entry
	 * @return the s c product entry, or <code>null</code> if a s c product entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCProductEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the s c product entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param productEntryId the primary key of the s c product entry
	 * @return the s c product entry, or <code>null</code> if a s c product entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry fetchByPrimaryKey(long productEntryId)
		throws SystemException {
		SCProductEntry scProductEntry = (SCProductEntry)EntityCacheUtil.getResult(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
				SCProductEntryImpl.class, productEntryId);

		if (scProductEntry == _nullSCProductEntry) {
			return null;
		}

		if (scProductEntry == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				scProductEntry = (SCProductEntry)session.get(SCProductEntryImpl.class,
						Long.valueOf(productEntryId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (scProductEntry != null) {
					cacheResult(scProductEntry);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(SCProductEntryModelImpl.ENTITY_CACHE_ENABLED,
						SCProductEntryImpl.class, productEntryId,
						_nullSCProductEntry);
				}

				closeSession(session);
			}
		}

		return scProductEntry;
	}

	/**
	 * Returns all the s c product entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching s c product entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductEntry> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c product entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c product entries
	 * @param end the upper bound of the range of s c product entries (not inclusive)
	 * @return the range of matching s c product entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductEntry> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c product entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c product entries
	 * @param end the upper bound of the range of s c product entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c product entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<SCProductEntry> list = (List<SCProductEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SCProductEntry scProductEntry : list) {
				if ((groupId != scProductEntry.getGroupId())) {
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

			query.append(_SQL_SELECT_SCPRODUCTENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SCProductEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<SCProductEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first s c product entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c product entry
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a matching s c product entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = fetchByGroupId_First(groupId,
				orderByComparator);

		if (scProductEntry != null) {
			return scProductEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductEntryException(msg.toString());
	}

	/**
	 * Returns the first s c product entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SCProductEntry> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s c product entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c product entry
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a matching s c product entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (scProductEntry != null) {
			return scProductEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductEntryException(msg.toString());
	}

	/**
	 * Returns the last s c product entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<SCProductEntry> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s c product entries before and after the current s c product entry in the ordered set where groupId = &#63;.
	 *
	 * @param productEntryId the primary key of the current s c product entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c product entry
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a s c product entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry[] findByGroupId_PrevAndNext(long productEntryId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = findByPrimaryKey(productEntryId);

		Session session = null;

		try {
			session = openSession();

			SCProductEntry[] array = new SCProductEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, scProductEntry,
					groupId, orderByComparator, true);

			array[1] = scProductEntry;

			array[2] = getByGroupId_PrevAndNext(session, scProductEntry,
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

	protected SCProductEntry getByGroupId_PrevAndNext(Session session,
		SCProductEntry scProductEntry, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCPRODUCTENTRY_WHERE);

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
			query.append(SCProductEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scProductEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCProductEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s c product entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching s c product entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductEntry> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c product entries that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c product entries
	 * @param end the upper bound of the range of s c product entries (not inclusive)
	 * @return the range of matching s c product entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductEntry> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c product entries that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c product entries
	 * @param end the upper bound of the range of s c product entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c product entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductEntry> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCPRODUCTENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCPRODUCTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCPRODUCTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SCProductEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCProductEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCProductEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SCProductEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SCProductEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<SCProductEntry>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the s c product entries before and after the current s c product entry in the ordered set of s c product entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param productEntryId the primary key of the current s c product entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c product entry
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a s c product entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry[] filterFindByGroupId_PrevAndNext(
		long productEntryId, long groupId, OrderByComparator orderByComparator)
		throws NoSuchProductEntryException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(productEntryId, groupId,
				orderByComparator);
		}

		SCProductEntry scProductEntry = findByPrimaryKey(productEntryId);

		Session session = null;

		try {
			session = openSession();

			SCProductEntry[] array = new SCProductEntryImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, scProductEntry,
					groupId, orderByComparator, true);

			array[1] = scProductEntry;

			array[2] = filterGetByGroupId_PrevAndNext(session, scProductEntry,
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

	protected SCProductEntry filterGetByGroupId_PrevAndNext(Session session,
		SCProductEntry scProductEntry, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCPRODUCTENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCPRODUCTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCPRODUCTENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SCProductEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCProductEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCProductEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SCProductEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SCProductEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scProductEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCProductEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s c product entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching s c product entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductEntry> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the s c product entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of s c product entries
	 * @param end the upper bound of the range of s c product entries (not inclusive)
	 * @return the range of matching s c product entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductEntry> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c product entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of s c product entries
	 * @param end the upper bound of the range of s c product entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c product entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductEntry> findByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<SCProductEntry> list = (List<SCProductEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SCProductEntry scProductEntry : list) {
				if ((companyId != scProductEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_SCPRODUCTENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SCProductEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<SCProductEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first s c product entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c product entry
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a matching s c product entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (scProductEntry != null) {
			return scProductEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductEntryException(msg.toString());
	}

	/**
	 * Returns the first s c product entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SCProductEntry> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s c product entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c product entry
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a matching s c product entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (scProductEntry != null) {
			return scProductEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductEntryException(msg.toString());
	}

	/**
	 * Returns the last s c product entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<SCProductEntry> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s c product entries before and after the current s c product entry in the ordered set where companyId = &#63;.
	 *
	 * @param productEntryId the primary key of the current s c product entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c product entry
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a s c product entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry[] findByCompanyId_PrevAndNext(long productEntryId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = findByPrimaryKey(productEntryId);

		Session session = null;

		try {
			session = openSession();

			SCProductEntry[] array = new SCProductEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, scProductEntry,
					companyId, orderByComparator, true);

			array[1] = scProductEntry;

			array[2] = getByCompanyId_PrevAndNext(session, scProductEntry,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCProductEntry getByCompanyId_PrevAndNext(Session session,
		SCProductEntry scProductEntry, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCPRODUCTENTRY_WHERE);

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
			query.append(SCProductEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scProductEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCProductEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s c product entries where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching s c product entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductEntry> findByG_U(long groupId, long userId)
		throws SystemException {
		return findByG_U(groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the s c product entries where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of s c product entries
	 * @param end the upper bound of the range of s c product entries (not inclusive)
	 * @return the range of matching s c product entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductEntry> findByG_U(long groupId, long userId, int start,
		int end) throws SystemException {
		return findByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c product entries where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of s c product entries
	 * @param end the upper bound of the range of s c product entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c product entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductEntry> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U;
			finderArgs = new Object[] { groupId, userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U;
			finderArgs = new Object[] {
					groupId, userId,
					
					start, end, orderByComparator
				};
		}

		List<SCProductEntry> list = (List<SCProductEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SCProductEntry scProductEntry : list) {
				if ((groupId != scProductEntry.getGroupId()) ||
						(userId != scProductEntry.getUserId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_SCPRODUCTENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SCProductEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = (List<SCProductEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first s c product entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c product entry
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a matching s c product entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry findByG_U_First(long groupId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = fetchByG_U_First(groupId, userId,
				orderByComparator);

		if (scProductEntry != null) {
			return scProductEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductEntryException(msg.toString());
	}

	/**
	 * Returns the first s c product entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry fetchByG_U_First(long groupId, long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SCProductEntry> list = findByG_U(groupId, userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s c product entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c product entry
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a matching s c product entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry findByG_U_Last(long groupId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = fetchByG_U_Last(groupId, userId,
				orderByComparator);

		if (scProductEntry != null) {
			return scProductEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductEntryException(msg.toString());
	}

	/**
	 * Returns the last s c product entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry fetchByG_U_Last(long groupId, long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_U(groupId, userId);

		List<SCProductEntry> list = findByG_U(groupId, userId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s c product entries before and after the current s c product entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param productEntryId the primary key of the current s c product entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c product entry
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a s c product entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry[] findByG_U_PrevAndNext(long productEntryId,
		long groupId, long userId, OrderByComparator orderByComparator)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = findByPrimaryKey(productEntryId);

		Session session = null;

		try {
			session = openSession();

			SCProductEntry[] array = new SCProductEntryImpl[3];

			array[0] = getByG_U_PrevAndNext(session, scProductEntry, groupId,
					userId, orderByComparator, true);

			array[1] = scProductEntry;

			array[2] = getByG_U_PrevAndNext(session, scProductEntry, groupId,
					userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCProductEntry getByG_U_PrevAndNext(Session session,
		SCProductEntry scProductEntry, long groupId, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCPRODUCTENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

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
			query.append(SCProductEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scProductEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCProductEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s c product entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching s c product entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductEntry> filterFindByG_U(long groupId, long userId)
		throws SystemException {
		return filterFindByG_U(groupId, userId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c product entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of s c product entries
	 * @param end the upper bound of the range of s c product entries (not inclusive)
	 * @return the range of matching s c product entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductEntry> filterFindByG_U(long groupId, long userId,
		int start, int end) throws SystemException {
		return filterFindByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c product entries that the user has permissions to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of s c product entries
	 * @param end the upper bound of the range of s c product entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c product entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductEntry> filterFindByG_U(long groupId, long userId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U(groupId, userId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCPRODUCTENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCPRODUCTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCPRODUCTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SCProductEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCProductEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCProductEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SCProductEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SCProductEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			return (List<SCProductEntry>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the s c product entries before and after the current s c product entry in the ordered set of s c product entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param productEntryId the primary key of the current s c product entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c product entry
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a s c product entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry[] filterFindByG_U_PrevAndNext(long productEntryId,
		long groupId, long userId, OrderByComparator orderByComparator)
		throws NoSuchProductEntryException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_PrevAndNext(productEntryId, groupId, userId,
				orderByComparator);
		}

		SCProductEntry scProductEntry = findByPrimaryKey(productEntryId);

		Session session = null;

		try {
			session = openSession();

			SCProductEntry[] array = new SCProductEntryImpl[3];

			array[0] = filterGetByG_U_PrevAndNext(session, scProductEntry,
					groupId, userId, orderByComparator, true);

			array[1] = scProductEntry;

			array[2] = filterGetByG_U_PrevAndNext(session, scProductEntry,
					groupId, userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCProductEntry filterGetByG_U_PrevAndNext(Session session,
		SCProductEntry scProductEntry, long groupId, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCPRODUCTENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCPRODUCTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCPRODUCTENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SCProductEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCProductEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCProductEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SCProductEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SCProductEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scProductEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCProductEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the s c product entry where repoGroupId = &#63; and repoArtifactId = &#63; or throws a {@link com.liferay.portlet.softwarecatalog.NoSuchProductEntryException} if it could not be found.
	 *
	 * @param repoGroupId the repo group ID
	 * @param repoArtifactId the repo artifact ID
	 * @return the matching s c product entry
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductEntryException if a matching s c product entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry findByRG_RA(String repoGroupId, String repoArtifactId)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = fetchByRG_RA(repoGroupId, repoArtifactId);

		if (scProductEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("repoGroupId=");
			msg.append(repoGroupId);

			msg.append(", repoArtifactId=");
			msg.append(repoArtifactId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProductEntryException(msg.toString());
		}

		return scProductEntry;
	}

	/**
	 * Returns the s c product entry where repoGroupId = &#63; and repoArtifactId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param repoGroupId the repo group ID
	 * @param repoArtifactId the repo artifact ID
	 * @return the matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry fetchByRG_RA(String repoGroupId, String repoArtifactId)
		throws SystemException {
		return fetchByRG_RA(repoGroupId, repoArtifactId, true);
	}

	/**
	 * Returns the s c product entry where repoGroupId = &#63; and repoArtifactId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param repoGroupId the repo group ID
	 * @param repoArtifactId the repo artifact ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching s c product entry, or <code>null</code> if a matching s c product entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry fetchByRG_RA(String repoGroupId,
		String repoArtifactId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { repoGroupId, repoArtifactId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_RG_RA,
					finderArgs, this);
		}

		if (result instanceof SCProductEntry) {
			SCProductEntry scProductEntry = (SCProductEntry)result;

			if (!Validator.equals(repoGroupId, scProductEntry.getRepoGroupId()) ||
					!Validator.equals(repoArtifactId,
						scProductEntry.getRepoArtifactId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SCPRODUCTENTRY_WHERE);

			if (repoGroupId == null) {
				query.append(_FINDER_COLUMN_RG_RA_REPOGROUPID_1);
			}
			else {
				if (repoGroupId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_RG_RA_REPOGROUPID_3);
				}
				else {
					query.append(_FINDER_COLUMN_RG_RA_REPOGROUPID_2);
				}
			}

			if (repoArtifactId == null) {
				query.append(_FINDER_COLUMN_RG_RA_REPOARTIFACTID_1);
			}
			else {
				if (repoArtifactId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_RG_RA_REPOARTIFACTID_3);
				}
				else {
					query.append(_FINDER_COLUMN_RG_RA_REPOARTIFACTID_2);
				}
			}

			query.append(SCProductEntryModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (repoGroupId != null) {
					qPos.add(repoGroupId);
				}

				if (repoArtifactId != null) {
					qPos.add(repoArtifactId);
				}

				List<SCProductEntry> list = q.list();

				result = list;

				SCProductEntry scProductEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_RG_RA,
						finderArgs, list);
				}
				else {
					scProductEntry = list.get(0);

					cacheResult(scProductEntry);

					if ((scProductEntry.getRepoGroupId() == null) ||
							!scProductEntry.getRepoGroupId().equals(repoGroupId) ||
							(scProductEntry.getRepoArtifactId() == null) ||
							!scProductEntry.getRepoArtifactId()
											   .equals(repoArtifactId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_RG_RA,
							finderArgs, scProductEntry);
					}
				}

				return scProductEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_RG_RA,
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
				return (SCProductEntry)result;
			}
		}
	}

	/**
	 * Returns all the s c product entries.
	 *
	 * @return the s c product entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c product entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of s c product entries
	 * @param end the upper bound of the range of s c product entries (not inclusive)
	 * @return the range of s c product entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c product entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of s c product entries
	 * @param end the upper bound of the range of s c product entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of s c product entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductEntry> findAll(int start, int end,
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

		List<SCProductEntry> list = (List<SCProductEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SCPRODUCTENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SCPRODUCTENTRY.concat(SCProductEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SCProductEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SCProductEntry>)QueryUtil.list(q,
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
	 * Removes all the s c product entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (SCProductEntry scProductEntry : findByGroupId(groupId)) {
			remove(scProductEntry);
		}
	}

	/**
	 * Removes all the s c product entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (SCProductEntry scProductEntry : findByCompanyId(companyId)) {
			remove(scProductEntry);
		}
	}

	/**
	 * Removes all the s c product entries where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_U(long groupId, long userId)
		throws SystemException {
		for (SCProductEntry scProductEntry : findByG_U(groupId, userId)) {
			remove(scProductEntry);
		}
	}

	/**
	 * Removes the s c product entry where repoGroupId = &#63; and repoArtifactId = &#63; from the database.
	 *
	 * @param repoGroupId the repo group ID
	 * @param repoArtifactId the repo artifact ID
	 * @return the s c product entry that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductEntry removeByRG_RA(String repoGroupId,
		String repoArtifactId)
		throws NoSuchProductEntryException, SystemException {
		SCProductEntry scProductEntry = findByRG_RA(repoGroupId, repoArtifactId);

		return remove(scProductEntry);
	}

	/**
	 * Removes all the s c product entries from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SCProductEntry scProductEntry : findAll()) {
			remove(scProductEntry);
		}
	}

	/**
	 * Returns the number of s c product entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching s c product entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCPRODUCTENTRY_WHERE);

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
	 * Returns the number of s c product entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching s c product entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_SCPRODUCTENTRY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCProductEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

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

	/**
	 * Returns the number of s c product entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching s c product entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCPRODUCTENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of s c product entries where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching s c product entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SCPRODUCTENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of s c product entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching s c product entries that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_U(long groupId, long userId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U(groupId, userId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_SCPRODUCTENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCProductEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

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

	/**
	 * Returns the number of s c product entries where repoGroupId = &#63; and repoArtifactId = &#63;.
	 *
	 * @param repoGroupId the repo group ID
	 * @param repoArtifactId the repo artifact ID
	 * @return the number of matching s c product entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByRG_RA(String repoGroupId, String repoArtifactId)
		throws SystemException {
		Object[] finderArgs = new Object[] { repoGroupId, repoArtifactId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_RG_RA,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SCPRODUCTENTRY_WHERE);

			if (repoGroupId == null) {
				query.append(_FINDER_COLUMN_RG_RA_REPOGROUPID_1);
			}
			else {
				if (repoGroupId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_RG_RA_REPOGROUPID_3);
				}
				else {
					query.append(_FINDER_COLUMN_RG_RA_REPOGROUPID_2);
				}
			}

			if (repoArtifactId == null) {
				query.append(_FINDER_COLUMN_RG_RA_REPOARTIFACTID_1);
			}
			else {
				if (repoArtifactId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_RG_RA_REPOARTIFACTID_3);
				}
				else {
					query.append(_FINDER_COLUMN_RG_RA_REPOARTIFACTID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (repoGroupId != null) {
					qPos.add(repoGroupId);
				}

				if (repoArtifactId != null) {
					qPos.add(repoArtifactId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_RG_RA,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of s c product entries.
	 *
	 * @return the number of s c product entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SCPRODUCTENTRY);

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
	 * Returns all the s c licenses associated with the s c product entry.
	 *
	 * @param pk the primary key of the s c product entry
	 * @return the s c licenses associated with the s c product entry
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		long pk) throws SystemException {
		return getSCLicenses(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the s c licenses associated with the s c product entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the s c product entry
	 * @param start the lower bound of the range of s c product entries
	 * @param end the upper bound of the range of s c product entries (not inclusive)
	 * @return the range of s c licenses associated with the s c product entry
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		long pk, int start, int end) throws SystemException {
		return getSCLicenses(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_SCLICENSES = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED_SCLICENSES_SCPRODUCTENTRIES,
			com.liferay.portlet.softwarecatalog.model.impl.SCLicenseImpl.class,
			SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME,
			"getSCLicenses",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_SCLICENSES.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the s c licenses associated with the s c product entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the s c product entry
	 * @param start the lower bound of the range of s c product entries
	 * @param end the upper bound of the range of s c product entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of s c licenses associated with the s c product entry
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portlet.softwarecatalog.model.SCLicense> list = (List<com.liferay.portlet.softwarecatalog.model.SCLicense>)FinderCacheUtil.getResult(FINDER_PATH_GET_SCLICENSES,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETSCLICENSES.concat(ORDER_BY_CLAUSE)
											.concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETSCLICENSES.concat(com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("SCLicense",
					com.liferay.portlet.softwarecatalog.model.impl.SCLicenseImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portlet.softwarecatalog.model.SCLicense>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_SCLICENSES,
						finderArgs);
				}
				else {
					scLicensePersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_SCLICENSES,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_SCLICENSES_SIZE = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED_SCLICENSES_SCPRODUCTENTRIES,
			Long.class,
			SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME,
			"getSCLicensesSize", new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_SCLICENSES_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of s c licenses associated with the s c product entry.
	 *
	 * @param pk the primary key of the s c product entry
	 * @return the number of s c licenses associated with the s c product entry
	 * @throws SystemException if a system exception occurred
	 */
	public int getSCLicensesSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_SCLICENSES_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETSCLICENSESSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_SCLICENSES_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_SCLICENSE = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl.ENTITY_CACHE_ENABLED,
			SCProductEntryModelImpl.FINDER_CACHE_ENABLED_SCLICENSES_SCPRODUCTENTRIES,
			Boolean.class,
			SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME,
			"containsSCLicense",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the s c license is associated with the s c product entry.
	 *
	 * @param pk the primary key of the s c product entry
	 * @param scLicensePK the primary key of the s c license
	 * @return <code>true</code> if the s c license is associated with the s c product entry; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsSCLicense(long pk, long scLicensePK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, scLicensePK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_SCLICENSE,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsSCLicense.contains(pk,
							scLicensePK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_SCLICENSE,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the s c product entry has any s c licenses associated with it.
	 *
	 * @param pk the primary key of the s c product entry to check for associations with s c licenses
	 * @return <code>true</code> if the s c product entry has any s c licenses associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsSCLicenses(long pk) throws SystemException {
		if (getSCLicensesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the s c product entry and the s c license. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product entry
	 * @param scLicensePK the primary key of the s c license
	 * @throws SystemException if a system exception occurred
	 */
	public void addSCLicense(long pk, long scLicensePK)
		throws SystemException {
		try {
			addSCLicense.add(pk, scLicensePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	/**
	 * Adds an association between the s c product entry and the s c license. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product entry
	 * @param scLicense the s c license
	 * @throws SystemException if a system exception occurred
	 */
	public void addSCLicense(long pk,
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws SystemException {
		try {
			addSCLicense.add(pk, scLicense.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	/**
	 * Adds an association between the s c product entry and the s c licenses. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product entry
	 * @param scLicensePKs the primary keys of the s c licenses
	 * @throws SystemException if a system exception occurred
	 */
	public void addSCLicenses(long pk, long[] scLicensePKs)
		throws SystemException {
		try {
			for (long scLicensePK : scLicensePKs) {
				addSCLicense.add(pk, scLicensePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	/**
	 * Adds an association between the s c product entry and the s c licenses. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product entry
	 * @param scLicenses the s c licenses
	 * @throws SystemException if a system exception occurred
	 */
	public void addSCLicenses(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses)
		throws SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCLicense scLicense : scLicenses) {
				addSCLicense.add(pk, scLicense.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	/**
	 * Clears all associations between the s c product entry and its s c licenses. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product entry to clear the associated s c licenses from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearSCLicenses(long pk) throws SystemException {
		try {
			clearSCLicenses.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	/**
	 * Removes the association between the s c product entry and the s c license. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product entry
	 * @param scLicensePK the primary key of the s c license
	 * @throws SystemException if a system exception occurred
	 */
	public void removeSCLicense(long pk, long scLicensePK)
		throws SystemException {
		try {
			removeSCLicense.remove(pk, scLicensePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	/**
	 * Removes the association between the s c product entry and the s c license. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product entry
	 * @param scLicense the s c license
	 * @throws SystemException if a system exception occurred
	 */
	public void removeSCLicense(long pk,
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws SystemException {
		try {
			removeSCLicense.remove(pk, scLicense.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	/**
	 * Removes the association between the s c product entry and the s c licenses. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product entry
	 * @param scLicensePKs the primary keys of the s c licenses
	 * @throws SystemException if a system exception occurred
	 */
	public void removeSCLicenses(long pk, long[] scLicensePKs)
		throws SystemException {
		try {
			for (long scLicensePK : scLicensePKs) {
				removeSCLicense.remove(pk, scLicensePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	/**
	 * Removes the association between the s c product entry and the s c licenses. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product entry
	 * @param scLicenses the s c licenses
	 * @throws SystemException if a system exception occurred
	 */
	public void removeSCLicenses(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses)
		throws SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCLicense scLicense : scLicenses) {
				removeSCLicense.remove(pk, scLicense.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	/**
	 * Sets the s c licenses associated with the s c product entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product entry
	 * @param scLicensePKs the primary keys of the s c licenses to be associated with the s c product entry
	 * @throws SystemException if a system exception occurred
	 */
	public void setSCLicenses(long pk, long[] scLicensePKs)
		throws SystemException {
		try {
			Set<Long> scLicensePKSet = SetUtil.fromArray(scLicensePKs);

			List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses =
				getSCLicenses(pk);

			for (com.liferay.portlet.softwarecatalog.model.SCLicense scLicense : scLicenses) {
				if (!scLicensePKSet.remove(scLicense.getPrimaryKey())) {
					removeSCLicense.remove(pk, scLicense.getPrimaryKey());
				}
			}

			for (Long scLicensePK : scLicensePKSet) {
				addSCLicense.add(pk, scLicensePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	/**
	 * Sets the s c licenses associated with the s c product entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product entry
	 * @param scLicenses the s c licenses to be associated with the s c product entry
	 * @throws SystemException if a system exception occurred
	 */
	public void setSCLicenses(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses)
		throws SystemException {
		try {
			long[] scLicensePKs = new long[scLicenses.size()];

			for (int i = 0; i < scLicenses.size(); i++) {
				com.liferay.portlet.softwarecatalog.model.SCLicense scLicense = scLicenses.get(i);

				scLicensePKs[i] = scLicense.getPrimaryKey();
			}

			setSCLicenses(pk, scLicensePKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductEntryModelImpl.MAPPING_TABLE_SCLICENSES_SCPRODUCTENTRIES_NAME);
		}
	}

	/**
	 * Initializes the s c product entry persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.softwarecatalog.model.SCProductEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SCProductEntry>> listenersList = new ArrayList<ModelListener<SCProductEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SCProductEntry>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsSCLicense = new ContainsSCLicense();

		addSCLicense = new AddSCLicense();
		clearSCLicenses = new ClearSCLicenses();
		removeSCLicense = new RemoveSCLicense();
	}

	public void destroy() {
		EntityCacheUtil.removeCache(SCProductEntryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = SCFrameworkVersionPersistence.class)
	protected SCFrameworkVersionPersistence scFrameworkVersionPersistence;
	@BeanReference(type = SCLicensePersistence.class)
	protected SCLicensePersistence scLicensePersistence;
	@BeanReference(type = SCProductEntryPersistence.class)
	protected SCProductEntryPersistence scProductEntryPersistence;
	@BeanReference(type = SCProductScreenshotPersistence.class)
	protected SCProductScreenshotPersistence scProductScreenshotPersistence;
	@BeanReference(type = SCProductVersionPersistence.class)
	protected SCProductVersionPersistence scProductVersionPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = MBMessagePersistence.class)
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(type = RatingsStatsPersistence.class)
	protected RatingsStatsPersistence ratingsStatsPersistence;
	protected ContainsSCLicense containsSCLicense;
	protected AddSCLicense addSCLicense;
	protected ClearSCLicenses clearSCLicenses;
	protected RemoveSCLicense removeSCLicense;

	protected class ContainsSCLicense {
		protected ContainsSCLicense() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSSCLICENSE,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long productEntryId, long licenseId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(productEntryId), new Long(licenseId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddSCLicense {
		protected AddSCLicense() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO SCLicenses_SCProductEntries (productEntryId, licenseId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long productEntryId, long licenseId)
			throws SystemException {
			if (!containsSCLicense.contains(productEntryId, licenseId)) {
				ModelListener<com.liferay.portlet.softwarecatalog.model.SCLicense>[] scLicenseListeners =
					scLicensePersistence.getListeners();

				for (ModelListener<SCProductEntry> listener : listeners) {
					listener.onBeforeAddAssociation(productEntryId,
						com.liferay.portlet.softwarecatalog.model.SCLicense.class.getName(),
						licenseId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCLicense> listener : scLicenseListeners) {
					listener.onBeforeAddAssociation(licenseId,
						SCProductEntry.class.getName(), productEntryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(productEntryId), new Long(licenseId)
					});

				for (ModelListener<SCProductEntry> listener : listeners) {
					listener.onAfterAddAssociation(productEntryId,
						com.liferay.portlet.softwarecatalog.model.SCLicense.class.getName(),
						licenseId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCLicense> listener : scLicenseListeners) {
					listener.onAfterAddAssociation(licenseId,
						SCProductEntry.class.getName(), productEntryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearSCLicenses {
		protected ClearSCLicenses() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM SCLicenses_SCProductEntries WHERE productEntryId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long productEntryId) throws SystemException {
			ModelListener<com.liferay.portlet.softwarecatalog.model.SCLicense>[] scLicenseListeners =
				scLicensePersistence.getListeners();

			List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses =
				null;

			if ((listeners.length > 0) || (scLicenseListeners.length > 0)) {
				scLicenses = getSCLicenses(productEntryId);

				for (com.liferay.portlet.softwarecatalog.model.SCLicense scLicense : scLicenses) {
					for (ModelListener<SCProductEntry> listener : listeners) {
						listener.onBeforeRemoveAssociation(productEntryId,
							com.liferay.portlet.softwarecatalog.model.SCLicense.class.getName(),
							scLicense.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCLicense> listener : scLicenseListeners) {
						listener.onBeforeRemoveAssociation(scLicense.getPrimaryKey(),
							SCProductEntry.class.getName(), productEntryId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(productEntryId) });

			if ((listeners.length > 0) || (scLicenseListeners.length > 0)) {
				for (com.liferay.portlet.softwarecatalog.model.SCLicense scLicense : scLicenses) {
					for (ModelListener<SCProductEntry> listener : listeners) {
						listener.onAfterRemoveAssociation(productEntryId,
							com.liferay.portlet.softwarecatalog.model.SCLicense.class.getName(),
							scLicense.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCLicense> listener : scLicenseListeners) {
						listener.onAfterRemoveAssociation(scLicense.getPrimaryKey(),
							SCProductEntry.class.getName(), productEntryId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveSCLicense {
		protected RemoveSCLicense() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM SCLicenses_SCProductEntries WHERE productEntryId = ? AND licenseId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long productEntryId, long licenseId)
			throws SystemException {
			if (containsSCLicense.contains(productEntryId, licenseId)) {
				ModelListener<com.liferay.portlet.softwarecatalog.model.SCLicense>[] scLicenseListeners =
					scLicensePersistence.getListeners();

				for (ModelListener<SCProductEntry> listener : listeners) {
					listener.onBeforeRemoveAssociation(productEntryId,
						com.liferay.portlet.softwarecatalog.model.SCLicense.class.getName(),
						licenseId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCLicense> listener : scLicenseListeners) {
					listener.onBeforeRemoveAssociation(licenseId,
						SCProductEntry.class.getName(), productEntryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(productEntryId), new Long(licenseId)
					});

				for (ModelListener<SCProductEntry> listener : listeners) {
					listener.onAfterRemoveAssociation(productEntryId,
						com.liferay.portlet.softwarecatalog.model.SCLicense.class.getName(),
						licenseId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCLicense> listener : scLicenseListeners) {
					listener.onAfterRemoveAssociation(licenseId,
						SCProductEntry.class.getName(), productEntryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	private static final String _SQL_SELECT_SCPRODUCTENTRY = "SELECT scProductEntry FROM SCProductEntry scProductEntry";
	private static final String _SQL_SELECT_SCPRODUCTENTRY_WHERE = "SELECT scProductEntry FROM SCProductEntry scProductEntry WHERE ";
	private static final String _SQL_COUNT_SCPRODUCTENTRY = "SELECT COUNT(scProductEntry) FROM SCProductEntry scProductEntry";
	private static final String _SQL_COUNT_SCPRODUCTENTRY_WHERE = "SELECT COUNT(scProductEntry) FROM SCProductEntry scProductEntry WHERE ";
	private static final String _SQL_GETSCLICENSES = "SELECT {SCLicense.*} FROM SCLicense INNER JOIN SCLicenses_SCProductEntries ON (SCLicenses_SCProductEntries.licenseId = SCLicense.licenseId) WHERE (SCLicenses_SCProductEntries.productEntryId = ?)";
	private static final String _SQL_GETSCLICENSESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM SCLicenses_SCProductEntries WHERE productEntryId = ?";
	private static final String _SQL_CONTAINSSCLICENSE = "SELECT COUNT(*) AS COUNT_VALUE FROM SCLicenses_SCProductEntries WHERE productEntryId = ? AND licenseId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "scProductEntry.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "scProductEntry.companyId = ?";
	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "scProductEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "scProductEntry.userId = ?";
	private static final String _FINDER_COLUMN_RG_RA_REPOGROUPID_1 = "scProductEntry.repoGroupId IS NULL AND ";
	private static final String _FINDER_COLUMN_RG_RA_REPOGROUPID_2 = "lower(scProductEntry.repoGroupId) = lower(CAST_TEXT(?)) AND ";
	private static final String _FINDER_COLUMN_RG_RA_REPOGROUPID_3 = "(scProductEntry.repoGroupId IS NULL OR lower(scProductEntry.repoGroupId) = lower(CAST_TEXT(?))) AND ";
	private static final String _FINDER_COLUMN_RG_RA_REPOARTIFACTID_1 = "scProductEntry.repoArtifactId IS NULL";
	private static final String _FINDER_COLUMN_RG_RA_REPOARTIFACTID_2 = "lower(scProductEntry.repoArtifactId) = lower(CAST_TEXT(?))";
	private static final String _FINDER_COLUMN_RG_RA_REPOARTIFACTID_3 = "(scProductEntry.repoArtifactId IS NULL OR lower(scProductEntry.repoArtifactId) = lower(CAST_TEXT(?)))";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "scProductEntry.productEntryId";
	private static final String _FILTER_SQL_SELECT_SCPRODUCTENTRY_WHERE = "SELECT DISTINCT {scProductEntry.*} FROM SCProductEntry scProductEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_SCPRODUCTENTRY_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {SCProductEntry.*} FROM (SELECT DISTINCT scProductEntry.productEntryId FROM SCProductEntry scProductEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_SCPRODUCTENTRY_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN SCProductEntry ON TEMP_TABLE.productEntryId = SCProductEntry.productEntryId";
	private static final String _FILTER_SQL_COUNT_SCPRODUCTENTRY_WHERE = "SELECT COUNT(DISTINCT scProductEntry.productEntryId) AS COUNT_VALUE FROM SCProductEntry scProductEntry WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "scProductEntry";
	private static final String _FILTER_ENTITY_TABLE = "SCProductEntry";
	private static final String _ORDER_BY_ENTITY_ALIAS = "scProductEntry.";
	private static final String _ORDER_BY_ENTITY_TABLE = "SCProductEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SCProductEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SCProductEntry exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(SCProductEntryPersistenceImpl.class);
	private static SCProductEntry _nullSCProductEntry = new SCProductEntryImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SCProductEntry> toCacheModel() {
				return _nullSCProductEntryCacheModel;
			}
		};

	private static CacheModel<SCProductEntry> _nullSCProductEntryCacheModel = new CacheModel<SCProductEntry>() {
			public SCProductEntry toEntityModel() {
				return _nullSCProductEntry;
			}
		};
}