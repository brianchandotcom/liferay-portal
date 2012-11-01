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
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;
import com.liferay.portlet.softwarecatalog.model.SCProductVersion;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionImpl;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the s c product version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SCProductVersionPersistence
 * @see SCProductVersionUtil
 * @generated
 */
public class SCProductVersionPersistenceImpl extends BasePersistenceImpl<SCProductVersion>
	implements SCProductVersionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SCProductVersionUtil} to access the s c product version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SCProductVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PRODUCTENTRYID =
		new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			SCProductVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByProductEntryId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PRODUCTENTRYID =
		new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			SCProductVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByProductEntryId",
			new String[] { Long.class.getName() },
			SCProductVersionModelImpl.PRODUCTENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PRODUCTENTRYID = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByProductEntryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			SCProductVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByDirectDownloadURL",
			new String[] { String.class.getName() },
			SCProductVersionModelImpl.DIRECTDOWNLOADURL_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_DIRECTDOWNLOADURL = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByDirectDownloadURL", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			SCProductVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED,
			SCProductVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the s c product version in the entity cache if it is enabled.
	 *
	 * @param scProductVersion the s c product version
	 */
	public void cacheResult(SCProductVersion scProductVersion) {
		EntityCacheUtil.putResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionImpl.class, scProductVersion.getPrimaryKey(),
			scProductVersion);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
			new Object[] { scProductVersion.getDirectDownloadURL() },
			scProductVersion);

		scProductVersion.resetOriginalValues();
	}

	/**
	 * Caches the s c product versions in the entity cache if it is enabled.
	 *
	 * @param scProductVersions the s c product versions
	 */
	public void cacheResult(List<SCProductVersion> scProductVersions) {
		for (SCProductVersion scProductVersion : scProductVersions) {
			if (EntityCacheUtil.getResult(
						SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
						SCProductVersionImpl.class,
						scProductVersion.getPrimaryKey()) == null) {
				cacheResult(scProductVersion);
			}
			else {
				scProductVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all s c product versions.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SCProductVersionImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SCProductVersionImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the s c product version.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SCProductVersion scProductVersion) {
		EntityCacheUtil.removeResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionImpl.class, scProductVersion.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(scProductVersion);
	}

	@Override
	public void clearCache(List<SCProductVersion> scProductVersions) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SCProductVersion scProductVersion : scProductVersions) {
			EntityCacheUtil.removeResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
				SCProductVersionImpl.class, scProductVersion.getPrimaryKey());

			clearUniqueFindersCache(scProductVersion);
		}
	}

	protected void clearUniqueFindersCache(SCProductVersion scProductVersion) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
			new Object[] { scProductVersion.getDirectDownloadURL() });
	}

	/**
	 * Creates a new s c product version with the primary key. Does not add the s c product version to the database.
	 *
	 * @param productVersionId the primary key for the new s c product version
	 * @return the new s c product version
	 */
	public SCProductVersion create(long productVersionId) {
		SCProductVersion scProductVersion = new SCProductVersionImpl();

		scProductVersion.setNew(true);
		scProductVersion.setPrimaryKey(productVersionId);

		return scProductVersion;
	}

	/**
	 * Removes the s c product version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param productVersionId the primary key of the s c product version
	 * @return the s c product version that was removed
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException if a s c product version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductVersion remove(long productVersionId)
		throws NoSuchProductVersionException, SystemException {
		return remove(Long.valueOf(productVersionId));
	}

	/**
	 * Removes the s c product version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the s c product version
	 * @return the s c product version that was removed
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException if a s c product version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCProductVersion remove(Serializable primaryKey)
		throws NoSuchProductVersionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SCProductVersion scProductVersion = (SCProductVersion)session.get(SCProductVersionImpl.class,
					primaryKey);

			if (scProductVersion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchProductVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(scProductVersion);
		}
		catch (NoSuchProductVersionException nsee) {
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
	protected SCProductVersion removeImpl(SCProductVersion scProductVersion)
		throws SystemException {
		scProductVersion = toUnwrappedModel(scProductVersion);

		try {
			clearSCFrameworkVersions.clear(scProductVersion.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			if (scProductVersion.isCachedModel()) {
				scProductVersion = (SCProductVersion)session.get(SCProductVersionImpl.class,
						scProductVersion.getPrimaryKeyObj());
			}

			if (scProductVersion != null) {
				session.delete(scProductVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (scProductVersion != null) {
			clearCache(scProductVersion);
		}

		return scProductVersion;
	}

	@Override
	public SCProductVersion updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion)
		throws SystemException {
		scProductVersion = toUnwrappedModel(scProductVersion);

		boolean isNew = scProductVersion.isNew();

		SCProductVersionModelImpl scProductVersionModelImpl = (SCProductVersionModelImpl)scProductVersion;

		Session session = null;

		try {
			session = openSession();

			if (scProductVersion.isNew()) {
				session.save(scProductVersion);

				scProductVersion.setNew(false);
			}
			else {
				session.merge(scProductVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SCProductVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((scProductVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PRODUCTENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(scProductVersionModelImpl.getOriginalProductEntryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PRODUCTENTRYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PRODUCTENTRYID,
					args);

				args = new Object[] {
						Long.valueOf(scProductVersionModelImpl.getProductEntryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PRODUCTENTRYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PRODUCTENTRYID,
					args);
			}
		}

		EntityCacheUtil.putResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionImpl.class, scProductVersion.getPrimaryKey(),
			scProductVersion);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
				new Object[] { scProductVersion.getDirectDownloadURL() },
				scProductVersion);
		}
		else {
			if ((scProductVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						scProductVersionModelImpl.getOriginalDirectDownloadURL()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_DIRECTDOWNLOADURL,
					args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
					args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
					new Object[] { scProductVersion.getDirectDownloadURL() },
					scProductVersion);
			}
		}

		return scProductVersion;
	}

	protected SCProductVersion toUnwrappedModel(
		SCProductVersion scProductVersion) {
		if (scProductVersion instanceof SCProductVersionImpl) {
			return scProductVersion;
		}

		SCProductVersionImpl scProductVersionImpl = new SCProductVersionImpl();

		scProductVersionImpl.setNew(scProductVersion.isNew());
		scProductVersionImpl.setPrimaryKey(scProductVersion.getPrimaryKey());

		scProductVersionImpl.setProductVersionId(scProductVersion.getProductVersionId());
		scProductVersionImpl.setCompanyId(scProductVersion.getCompanyId());
		scProductVersionImpl.setUserId(scProductVersion.getUserId());
		scProductVersionImpl.setUserName(scProductVersion.getUserName());
		scProductVersionImpl.setCreateDate(scProductVersion.getCreateDate());
		scProductVersionImpl.setModifiedDate(scProductVersion.getModifiedDate());
		scProductVersionImpl.setProductEntryId(scProductVersion.getProductEntryId());
		scProductVersionImpl.setVersion(scProductVersion.getVersion());
		scProductVersionImpl.setChangeLog(scProductVersion.getChangeLog());
		scProductVersionImpl.setDownloadPageURL(scProductVersion.getDownloadPageURL());
		scProductVersionImpl.setDirectDownloadURL(scProductVersion.getDirectDownloadURL());
		scProductVersionImpl.setRepoStoreArtifact(scProductVersion.isRepoStoreArtifact());

		return scProductVersionImpl;
	}

	/**
	 * Returns the s c product version with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the s c product version
	 * @return the s c product version
	 * @throws com.liferay.portal.NoSuchModelException if a s c product version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCProductVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the s c product version with the primary key or throws a {@link com.liferay.portlet.softwarecatalog.NoSuchProductVersionException} if it could not be found.
	 *
	 * @param productVersionId the primary key of the s c product version
	 * @return the s c product version
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException if a s c product version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductVersion findByPrimaryKey(long productVersionId)
		throws NoSuchProductVersionException, SystemException {
		SCProductVersion scProductVersion = fetchByPrimaryKey(productVersionId);

		if (scProductVersion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + productVersionId);
			}

			throw new NoSuchProductVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				productVersionId);
		}

		return scProductVersion;
	}

	/**
	 * Returns the s c product version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the s c product version
	 * @return the s c product version, or <code>null</code> if a s c product version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCProductVersion fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the s c product version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param productVersionId the primary key of the s c product version
	 * @return the s c product version, or <code>null</code> if a s c product version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductVersion fetchByPrimaryKey(long productVersionId)
		throws SystemException {
		SCProductVersion scProductVersion = (SCProductVersion)EntityCacheUtil.getResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
				SCProductVersionImpl.class, productVersionId);

		if (scProductVersion == _nullSCProductVersion) {
			return null;
		}

		if (scProductVersion == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				scProductVersion = (SCProductVersion)session.get(SCProductVersionImpl.class,
						Long.valueOf(productVersionId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (scProductVersion != null) {
					cacheResult(scProductVersion);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
						SCProductVersionImpl.class, productVersionId,
						_nullSCProductVersion);
				}

				closeSession(session);
			}
		}

		return scProductVersion;
	}

	/**
	 * Returns all the s c product versions where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @return the matching s c product versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductVersion> findByProductEntryId(long productEntryId)
		throws SystemException {
		return findByProductEntryId(productEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c product versions where productEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param productEntryId the product entry ID
	 * @param start the lower bound of the range of s c product versions
	 * @param end the upper bound of the range of s c product versions (not inclusive)
	 * @return the range of matching s c product versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductVersion> findByProductEntryId(long productEntryId,
		int start, int end) throws SystemException {
		return findByProductEntryId(productEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c product versions where productEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param productEntryId the product entry ID
	 * @param start the lower bound of the range of s c product versions
	 * @param end the upper bound of the range of s c product versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c product versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductVersion> findByProductEntryId(long productEntryId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PRODUCTENTRYID;
			finderArgs = new Object[] { productEntryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PRODUCTENTRYID;
			finderArgs = new Object[] {
					productEntryId,
					
					start, end, orderByComparator
				};
		}

		List<SCProductVersion> list = (List<SCProductVersion>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SCProductVersion scProductVersion : list) {
				if ((productEntryId != scProductVersion.getProductEntryId())) {
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

			query.append(_SQL_SELECT_SCPRODUCTVERSION_WHERE);

			query.append(_FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SCProductVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				list = (List<SCProductVersion>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first s c product version in the ordered set where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c product version
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException if a matching s c product version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductVersion findByProductEntryId_First(long productEntryId,
		OrderByComparator orderByComparator)
		throws NoSuchProductVersionException, SystemException {
		SCProductVersion scProductVersion = fetchByProductEntryId_First(productEntryId,
				orderByComparator);

		if (scProductVersion != null) {
			return scProductVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("productEntryId=");
		msg.append(productEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductVersionException(msg.toString());
	}

	/**
	 * Returns the first s c product version in the ordered set where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c product version, or <code>null</code> if a matching s c product version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductVersion fetchByProductEntryId_First(long productEntryId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SCProductVersion> list = findByProductEntryId(productEntryId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s c product version in the ordered set where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c product version
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException if a matching s c product version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductVersion findByProductEntryId_Last(long productEntryId,
		OrderByComparator orderByComparator)
		throws NoSuchProductVersionException, SystemException {
		SCProductVersion scProductVersion = fetchByProductEntryId_Last(productEntryId,
				orderByComparator);

		if (scProductVersion != null) {
			return scProductVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("productEntryId=");
		msg.append(productEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductVersionException(msg.toString());
	}

	/**
	 * Returns the last s c product version in the ordered set where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c product version, or <code>null</code> if a matching s c product version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductVersion fetchByProductEntryId_Last(long productEntryId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByProductEntryId(productEntryId);

		List<SCProductVersion> list = findByProductEntryId(productEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s c product versions before and after the current s c product version in the ordered set where productEntryId = &#63;.
	 *
	 * @param productVersionId the primary key of the current s c product version
	 * @param productEntryId the product entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c product version
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException if a s c product version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductVersion[] findByProductEntryId_PrevAndNext(
		long productVersionId, long productEntryId,
		OrderByComparator orderByComparator)
		throws NoSuchProductVersionException, SystemException {
		SCProductVersion scProductVersion = findByPrimaryKey(productVersionId);

		Session session = null;

		try {
			session = openSession();

			SCProductVersion[] array = new SCProductVersionImpl[3];

			array[0] = getByProductEntryId_PrevAndNext(session,
					scProductVersion, productEntryId, orderByComparator, true);

			array[1] = scProductVersion;

			array[2] = getByProductEntryId_PrevAndNext(session,
					scProductVersion, productEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCProductVersion getByProductEntryId_PrevAndNext(
		Session session, SCProductVersion scProductVersion,
		long productEntryId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCPRODUCTVERSION_WHERE);

		query.append(_FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2);

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
			query.append(SCProductVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(productEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scProductVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCProductVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the s c product version where directDownloadURL = &#63; or throws a {@link com.liferay.portlet.softwarecatalog.NoSuchProductVersionException} if it could not be found.
	 *
	 * @param directDownloadURL the direct download u r l
	 * @return the matching s c product version
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException if a matching s c product version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductVersion findByDirectDownloadURL(String directDownloadURL)
		throws NoSuchProductVersionException, SystemException {
		SCProductVersion scProductVersion = fetchByDirectDownloadURL(directDownloadURL);

		if (scProductVersion == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("directDownloadURL=");
			msg.append(directDownloadURL);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProductVersionException(msg.toString());
		}

		return scProductVersion;
	}

	/**
	 * Returns the s c product version where directDownloadURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param directDownloadURL the direct download u r l
	 * @return the matching s c product version, or <code>null</code> if a matching s c product version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductVersion fetchByDirectDownloadURL(String directDownloadURL)
		throws SystemException {
		return fetchByDirectDownloadURL(directDownloadURL, true);
	}

	/**
	 * Returns the s c product version where directDownloadURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param directDownloadURL the direct download u r l
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching s c product version, or <code>null</code> if a matching s c product version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductVersion fetchByDirectDownloadURL(String directDownloadURL,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { directDownloadURL };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
					finderArgs, this);
		}

		if (result instanceof SCProductVersion) {
			SCProductVersion scProductVersion = (SCProductVersion)result;

			if (!Validator.equals(directDownloadURL,
						scProductVersion.getDirectDownloadURL())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_SCPRODUCTVERSION_WHERE);

			if (directDownloadURL == null) {
				query.append(_FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_1);
			}
			else {
				if (directDownloadURL.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_3);
				}
				else {
					query.append(_FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_2);
				}
			}

			query.append(SCProductVersionModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (directDownloadURL != null) {
					qPos.add(directDownloadURL);
				}

				List<SCProductVersion> list = q.list();

				result = list;

				SCProductVersion scProductVersion = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
						finderArgs, list);
				}
				else {
					scProductVersion = list.get(0);

					cacheResult(scProductVersion);

					if ((scProductVersion.getDirectDownloadURL() == null) ||
							!scProductVersion.getDirectDownloadURL()
												 .equals(directDownloadURL)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
							finderArgs, scProductVersion);
					}
				}

				return scProductVersion;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_DIRECTDOWNLOADURL,
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
				return (SCProductVersion)result;
			}
		}
	}

	/**
	 * Returns all the s c product versions.
	 *
	 * @return the s c product versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductVersion> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c product versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of s c product versions
	 * @param end the upper bound of the range of s c product versions (not inclusive)
	 * @return the range of s c product versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductVersion> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c product versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of s c product versions
	 * @param end the upper bound of the range of s c product versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of s c product versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductVersion> findAll(int start, int end,
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

		List<SCProductVersion> list = (List<SCProductVersion>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SCPRODUCTVERSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SCPRODUCTVERSION.concat(SCProductVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SCProductVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SCProductVersion>)QueryUtil.list(q,
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
	 * Removes all the s c product versions where productEntryId = &#63; from the database.
	 *
	 * @param productEntryId the product entry ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByProductEntryId(long productEntryId)
		throws SystemException {
		for (SCProductVersion scProductVersion : findByProductEntryId(
				productEntryId)) {
			remove(scProductVersion);
		}
	}

	/**
	 * Removes the s c product version where directDownloadURL = &#63; from the database.
	 *
	 * @param directDownloadURL the direct download u r l
	 * @return the s c product version that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductVersion removeByDirectDownloadURL(String directDownloadURL)
		throws NoSuchProductVersionException, SystemException {
		SCProductVersion scProductVersion = findByDirectDownloadURL(directDownloadURL);

		return remove(scProductVersion);
	}

	/**
	 * Removes all the s c product versions from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SCProductVersion scProductVersion : findAll()) {
			remove(scProductVersion);
		}
	}

	/**
	 * Returns the number of s c product versions where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @return the number of matching s c product versions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByProductEntryId(long productEntryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { productEntryId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PRODUCTENTRYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCPRODUCTVERSION_WHERE);

			query.append(_FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PRODUCTENTRYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of s c product versions where directDownloadURL = &#63;.
	 *
	 * @param directDownloadURL the direct download u r l
	 * @return the number of matching s c product versions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByDirectDownloadURL(String directDownloadURL)
		throws SystemException {
		Object[] finderArgs = new Object[] { directDownloadURL };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_DIRECTDOWNLOADURL,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCPRODUCTVERSION_WHERE);

			if (directDownloadURL == null) {
				query.append(_FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_1);
			}
			else {
				if (directDownloadURL.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_3);
				}
				else {
					query.append(_FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (directDownloadURL != null) {
					qPos.add(directDownloadURL);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_DIRECTDOWNLOADURL,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of s c product versions.
	 *
	 * @return the number of s c product versions
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SCPRODUCTVERSION);

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
	 * Returns all the s c framework versions associated with the s c product version.
	 *
	 * @param pk the primary key of the s c product version
	 * @return the s c framework versions associated with the s c product version
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk) throws SystemException {
		return getSCFrameworkVersions(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the s c framework versions associated with the s c product version.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the s c product version
	 * @param start the lower bound of the range of s c product versions
	 * @param end the upper bound of the range of s c product versions (not inclusive)
	 * @return the range of s c framework versions associated with the s c product version
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk, int start, int end) throws SystemException {
		return getSCFrameworkVersions(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_SCFRAMEWORKVERSIONS = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED_SCFRAMEWORKVERSI_SCPRODUCTVERS,
			com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionImpl.class,
			SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME,
			"getSCFrameworkVersions",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_SCFRAMEWORKVERSIONS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the s c framework versions associated with the s c product version.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the s c product version
	 * @param start the lower bound of the range of s c product versions
	 * @param end the upper bound of the range of s c product versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of s c framework versions associated with the s c product version
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> list = (List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion>)FinderCacheUtil.getResult(FINDER_PATH_GET_SCFRAMEWORKVERSIONS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETSCFRAMEWORKVERSIONS.concat(ORDER_BY_CLAUSE)
													 .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETSCFRAMEWORKVERSIONS.concat(com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("SCFrameworkVersion",
					com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_SCFRAMEWORKVERSIONS,
						finderArgs);
				}
				else {
					scFrameworkVersionPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_SCFRAMEWORKVERSIONS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_SCFRAMEWORKVERSIONS_SIZE = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED_SCFRAMEWORKVERSI_SCPRODUCTVERS,
			Long.class,
			SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME,
			"getSCFrameworkVersionsSize", new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_SCFRAMEWORKVERSIONS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of s c framework versions associated with the s c product version.
	 *
	 * @param pk the primary key of the s c product version
	 * @return the number of s c framework versions associated with the s c product version
	 * @throws SystemException if a system exception occurred
	 */
	public int getSCFrameworkVersionsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_SCFRAMEWORKVERSIONS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETSCFRAMEWORKVERSIONSSIZE);

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

				FinderCacheUtil.putResult(FINDER_PATH_GET_SCFRAMEWORKVERSIONS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_SCFRAMEWORKVERSION = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCProductVersionModelImpl.FINDER_CACHE_ENABLED_SCFRAMEWORKVERSI_SCPRODUCTVERS,
			Boolean.class,
			SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME,
			"containsSCFrameworkVersion",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the s c framework version is associated with the s c product version.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersionPK the primary key of the s c framework version
	 * @return <code>true</code> if the s c framework version is associated with the s c product version; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsSCFrameworkVersion(long pk, long scFrameworkVersionPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, scFrameworkVersionPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_SCFRAMEWORKVERSION,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsSCFrameworkVersion.contains(
							pk, scFrameworkVersionPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_SCFRAMEWORKVERSION,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the s c product version has any s c framework versions associated with it.
	 *
	 * @param pk the primary key of the s c product version to check for associations with s c framework versions
	 * @return <code>true</code> if the s c product version has any s c framework versions associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsSCFrameworkVersions(long pk)
		throws SystemException {
		if (getSCFrameworkVersionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the s c product version and the s c framework version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersionPK the primary key of the s c framework version
	 * @throws SystemException if a system exception occurred
	 */
	public void addSCFrameworkVersion(long pk, long scFrameworkVersionPK)
		throws SystemException {
		try {
			addSCFrameworkVersion.add(pk, scFrameworkVersionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Adds an association between the s c product version and the s c framework version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersion the s c framework version
	 * @throws SystemException if a system exception occurred
	 */
	public void addSCFrameworkVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws SystemException {
		try {
			addSCFrameworkVersion.add(pk, scFrameworkVersion.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Adds an association between the s c product version and the s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersionPKs the primary keys of the s c framework versions
	 * @throws SystemException if a system exception occurred
	 */
	public void addSCFrameworkVersions(long pk, long[] scFrameworkVersionPKs)
		throws SystemException {
		try {
			for (long scFrameworkVersionPK : scFrameworkVersionPKs) {
				addSCFrameworkVersion.add(pk, scFrameworkVersionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Adds an association between the s c product version and the s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersions the s c framework versions
	 * @throws SystemException if a system exception occurred
	 */
	public void addSCFrameworkVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions)
		throws SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
				addSCFrameworkVersion.add(pk, scFrameworkVersion.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Clears all associations between the s c product version and its s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version to clear the associated s c framework versions from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearSCFrameworkVersions(long pk) throws SystemException {
		try {
			clearSCFrameworkVersions.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Removes the association between the s c product version and the s c framework version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersionPK the primary key of the s c framework version
	 * @throws SystemException if a system exception occurred
	 */
	public void removeSCFrameworkVersion(long pk, long scFrameworkVersionPK)
		throws SystemException {
		try {
			removeSCFrameworkVersion.remove(pk, scFrameworkVersionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Removes the association between the s c product version and the s c framework version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersion the s c framework version
	 * @throws SystemException if a system exception occurred
	 */
	public void removeSCFrameworkVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws SystemException {
		try {
			removeSCFrameworkVersion.remove(pk,
				scFrameworkVersion.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Removes the association between the s c product version and the s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersionPKs the primary keys of the s c framework versions
	 * @throws SystemException if a system exception occurred
	 */
	public void removeSCFrameworkVersions(long pk, long[] scFrameworkVersionPKs)
		throws SystemException {
		try {
			for (long scFrameworkVersionPK : scFrameworkVersionPKs) {
				removeSCFrameworkVersion.remove(pk, scFrameworkVersionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Removes the association between the s c product version and the s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersions the s c framework versions
	 * @throws SystemException if a system exception occurred
	 */
	public void removeSCFrameworkVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions)
		throws SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
				removeSCFrameworkVersion.remove(pk,
					scFrameworkVersion.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Sets the s c framework versions associated with the s c product version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersionPKs the primary keys of the s c framework versions to be associated with the s c product version
	 * @throws SystemException if a system exception occurred
	 */
	public void setSCFrameworkVersions(long pk, long[] scFrameworkVersionPKs)
		throws SystemException {
		try {
			Set<Long> scFrameworkVersionPKSet = SetUtil.fromArray(scFrameworkVersionPKs);

			List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions =
				getSCFrameworkVersions(pk);

			for (com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
				if (!scFrameworkVersionPKSet.remove(
							scFrameworkVersion.getPrimaryKey())) {
					removeSCFrameworkVersion.remove(pk,
						scFrameworkVersion.getPrimaryKey());
				}
			}

			for (Long scFrameworkVersionPK : scFrameworkVersionPKSet) {
				addSCFrameworkVersion.add(pk, scFrameworkVersionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Sets the s c framework versions associated with the s c product version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c product version
	 * @param scFrameworkVersions the s c framework versions to be associated with the s c product version
	 * @throws SystemException if a system exception occurred
	 */
	public void setSCFrameworkVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions)
		throws SystemException {
		try {
			long[] scFrameworkVersionPKs = new long[scFrameworkVersions.size()];

			for (int i = 0; i < scFrameworkVersions.size(); i++) {
				com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion =
					scFrameworkVersions.get(i);

				scFrameworkVersionPKs[i] = scFrameworkVersion.getPrimaryKey();
			}

			setSCFrameworkVersions(pk, scFrameworkVersionPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCProductVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	/**
	 * Initializes the s c product version persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.softwarecatalog.model.SCProductVersion")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SCProductVersion>> listenersList = new ArrayList<ModelListener<SCProductVersion>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SCProductVersion>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsSCFrameworkVersion = new ContainsSCFrameworkVersion();

		addSCFrameworkVersion = new AddSCFrameworkVersion();
		clearSCFrameworkVersions = new ClearSCFrameworkVersions();
		removeSCFrameworkVersion = new RemoveSCFrameworkVersion();
	}

	public void destroy() {
		EntityCacheUtil.removeCache(SCProductVersionImpl.class.getName());
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
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	protected ContainsSCFrameworkVersion containsSCFrameworkVersion;
	protected AddSCFrameworkVersion addSCFrameworkVersion;
	protected ClearSCFrameworkVersions clearSCFrameworkVersions;
	protected RemoveSCFrameworkVersion removeSCFrameworkVersion;

	protected class ContainsSCFrameworkVersion {
		protected ContainsSCFrameworkVersion() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSSCFRAMEWORKVERSION,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long productVersionId,
			long frameworkVersionId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(productVersionId), new Long(frameworkVersionId)
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

	protected class AddSCFrameworkVersion {
		protected AddSCFrameworkVersion() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO SCFrameworkVersi_SCProductVers (productVersionId, frameworkVersionId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long productVersionId, long frameworkVersionId)
			throws SystemException {
			if (!containsSCFrameworkVersion.contains(productVersionId,
						frameworkVersionId)) {
				ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion>[] scFrameworkVersionListeners =
					scFrameworkVersionPersistence.getListeners();

				for (ModelListener<SCProductVersion> listener : listeners) {
					listener.onBeforeAddAssociation(productVersionId,
						com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion.class.getName(),
						frameworkVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> listener : scFrameworkVersionListeners) {
					listener.onBeforeAddAssociation(frameworkVersionId,
						SCProductVersion.class.getName(), productVersionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(productVersionId), new Long(frameworkVersionId)
					});

				for (ModelListener<SCProductVersion> listener : listeners) {
					listener.onAfterAddAssociation(productVersionId,
						com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion.class.getName(),
						frameworkVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> listener : scFrameworkVersionListeners) {
					listener.onAfterAddAssociation(frameworkVersionId,
						SCProductVersion.class.getName(), productVersionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearSCFrameworkVersions {
		protected ClearSCFrameworkVersions() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM SCFrameworkVersi_SCProductVers WHERE productVersionId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long productVersionId) throws SystemException {
			ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion>[] scFrameworkVersionListeners =
				scFrameworkVersionPersistence.getListeners();

			List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions =
				null;

			if ((listeners.length > 0) ||
					(scFrameworkVersionListeners.length > 0)) {
				scFrameworkVersions = getSCFrameworkVersions(productVersionId);

				for (com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
					for (ModelListener<SCProductVersion> listener : listeners) {
						listener.onBeforeRemoveAssociation(productVersionId,
							com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion.class.getName(),
							scFrameworkVersion.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> listener : scFrameworkVersionListeners) {
						listener.onBeforeRemoveAssociation(scFrameworkVersion.getPrimaryKey(),
							SCProductVersion.class.getName(), productVersionId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(productVersionId) });

			if ((listeners.length > 0) ||
					(scFrameworkVersionListeners.length > 0)) {
				for (com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
					for (ModelListener<SCProductVersion> listener : listeners) {
						listener.onAfterRemoveAssociation(productVersionId,
							com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion.class.getName(),
							scFrameworkVersion.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> listener : scFrameworkVersionListeners) {
						listener.onAfterRemoveAssociation(scFrameworkVersion.getPrimaryKey(),
							SCProductVersion.class.getName(), productVersionId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveSCFrameworkVersion {
		protected RemoveSCFrameworkVersion() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM SCFrameworkVersi_SCProductVers WHERE productVersionId = ? AND frameworkVersionId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long productVersionId, long frameworkVersionId)
			throws SystemException {
			if (containsSCFrameworkVersion.contains(productVersionId,
						frameworkVersionId)) {
				ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion>[] scFrameworkVersionListeners =
					scFrameworkVersionPersistence.getListeners();

				for (ModelListener<SCProductVersion> listener : listeners) {
					listener.onBeforeRemoveAssociation(productVersionId,
						com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion.class.getName(),
						frameworkVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> listener : scFrameworkVersionListeners) {
					listener.onBeforeRemoveAssociation(frameworkVersionId,
						SCProductVersion.class.getName(), productVersionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(productVersionId), new Long(frameworkVersionId)
					});

				for (ModelListener<SCProductVersion> listener : listeners) {
					listener.onAfterRemoveAssociation(productVersionId,
						com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion.class.getName(),
						frameworkVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> listener : scFrameworkVersionListeners) {
					listener.onAfterRemoveAssociation(frameworkVersionId,
						SCProductVersion.class.getName(), productVersionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	private static final String _SQL_SELECT_SCPRODUCTVERSION = "SELECT scProductVersion FROM SCProductVersion scProductVersion";
	private static final String _SQL_SELECT_SCPRODUCTVERSION_WHERE = "SELECT scProductVersion FROM SCProductVersion scProductVersion WHERE ";
	private static final String _SQL_COUNT_SCPRODUCTVERSION = "SELECT COUNT(scProductVersion) FROM SCProductVersion scProductVersion";
	private static final String _SQL_COUNT_SCPRODUCTVERSION_WHERE = "SELECT COUNT(scProductVersion) FROM SCProductVersion scProductVersion WHERE ";
	private static final String _SQL_GETSCFRAMEWORKVERSIONS = "SELECT {SCFrameworkVersion.*} FROM SCFrameworkVersion INNER JOIN SCFrameworkVersi_SCProductVers ON (SCFrameworkVersi_SCProductVers.frameworkVersionId = SCFrameworkVersion.frameworkVersionId) WHERE (SCFrameworkVersi_SCProductVers.productVersionId = ?)";
	private static final String _SQL_GETSCFRAMEWORKVERSIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM SCFrameworkVersi_SCProductVers WHERE productVersionId = ?";
	private static final String _SQL_CONTAINSSCFRAMEWORKVERSION = "SELECT COUNT(*) AS COUNT_VALUE FROM SCFrameworkVersi_SCProductVers WHERE productVersionId = ? AND frameworkVersionId = ?";
	private static final String _FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2 = "scProductVersion.productEntryId = ?";
	private static final String _FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_1 =
		"scProductVersion.directDownloadURL IS NULL";
	private static final String _FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_2 =
		"lower(scProductVersion.directDownloadURL) = lower(CAST_TEXT(?))";
	private static final String _FINDER_COLUMN_DIRECTDOWNLOADURL_DIRECTDOWNLOADURL_3 =
		"(scProductVersion.directDownloadURL IS NULL OR lower(scProductVersion.directDownloadURL) = lower(CAST_TEXT(?)))";
	private static final String _ORDER_BY_ENTITY_ALIAS = "scProductVersion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SCProductVersion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SCProductVersion exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(SCProductVersionPersistenceImpl.class);
	private static SCProductVersion _nullSCProductVersion = new SCProductVersionImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SCProductVersion> toCacheModel() {
				return _nullSCProductVersionCacheModel;
			}
		};

	private static CacheModel<SCProductVersion> _nullSCProductVersionCacheModel = new CacheModel<SCProductVersion>() {
			public SCProductVersion toEntityModel() {
				return _nullSCProductVersion;
			}
		};
}