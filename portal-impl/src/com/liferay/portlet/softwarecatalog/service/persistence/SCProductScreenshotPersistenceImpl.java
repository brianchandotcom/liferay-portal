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
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.ImagePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException;
import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductScreenshotImpl;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductScreenshotModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the s c product screenshot service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SCProductScreenshotPersistence
 * @see SCProductScreenshotUtil
 * @generated
 */
public class SCProductScreenshotPersistenceImpl extends BasePersistenceImpl<SCProductScreenshot>
	implements SCProductScreenshotPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SCProductScreenshotUtil} to access the s c product screenshot persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SCProductScreenshotImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PRODUCTENTRYID =
		new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			SCProductScreenshotImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByProductEntryId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PRODUCTENTRYID =
		new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			SCProductScreenshotImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByProductEntryId",
			new String[] { Long.class.getName() },
			SCProductScreenshotModelImpl.PRODUCTENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PRODUCTENTRYID = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByProductEntryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_THUMBNAILID = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			SCProductScreenshotImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByThumbnailId", new String[] { Long.class.getName() },
			SCProductScreenshotModelImpl.THUMBNAILID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_THUMBNAILID = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByThumbnailId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_FULLIMAGEID = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			SCProductScreenshotImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByFullImageId", new String[] { Long.class.getName() },
			SCProductScreenshotModelImpl.FULLIMAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FULLIMAGEID = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFullImageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_P_P = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			SCProductScreenshotImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByP_P",
			new String[] { Long.class.getName(), Integer.class.getName() },
			SCProductScreenshotModelImpl.PRODUCTENTRYID_COLUMN_BITMASK |
			SCProductScreenshotModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_P_P = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_P",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			SCProductScreenshotImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			SCProductScreenshotImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the s c product screenshot in the entity cache if it is enabled.
	 *
	 * @param scProductScreenshot the s c product screenshot
	 */
	public void cacheResult(SCProductScreenshot scProductScreenshot) {
		EntityCacheUtil.putResult(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotImpl.class, scProductScreenshot.getPrimaryKey(),
			scProductScreenshot);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
			new Object[] { Long.valueOf(scProductScreenshot.getThumbnailId()) },
			scProductScreenshot);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
			new Object[] { Long.valueOf(scProductScreenshot.getFullImageId()) },
			scProductScreenshot);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_P,
			new Object[] {
				Long.valueOf(scProductScreenshot.getProductEntryId()),
				Integer.valueOf(scProductScreenshot.getPriority())
			}, scProductScreenshot);

		scProductScreenshot.resetOriginalValues();
	}

	/**
	 * Caches the s c product screenshots in the entity cache if it is enabled.
	 *
	 * @param scProductScreenshots the s c product screenshots
	 */
	public void cacheResult(List<SCProductScreenshot> scProductScreenshots) {
		for (SCProductScreenshot scProductScreenshot : scProductScreenshots) {
			if (EntityCacheUtil.getResult(
						SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
						SCProductScreenshotImpl.class,
						scProductScreenshot.getPrimaryKey()) == null) {
				cacheResult(scProductScreenshot);
			}
			else {
				scProductScreenshot.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all s c product screenshots.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SCProductScreenshotImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SCProductScreenshotImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the s c product screenshot.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SCProductScreenshot scProductScreenshot) {
		EntityCacheUtil.removeResult(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotImpl.class, scProductScreenshot.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(scProductScreenshot);
	}

	@Override
	public void clearCache(List<SCProductScreenshot> scProductScreenshots) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SCProductScreenshot scProductScreenshot : scProductScreenshots) {
			EntityCacheUtil.removeResult(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
				SCProductScreenshotImpl.class,
				scProductScreenshot.getPrimaryKey());

			clearUniqueFindersCache(scProductScreenshot);
		}
	}

	protected void clearUniqueFindersCache(
		SCProductScreenshot scProductScreenshot) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
			new Object[] { Long.valueOf(scProductScreenshot.getThumbnailId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
			new Object[] { Long.valueOf(scProductScreenshot.getFullImageId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_P_P,
			new Object[] {
				Long.valueOf(scProductScreenshot.getProductEntryId()),
				Integer.valueOf(scProductScreenshot.getPriority())
			});
	}

	/**
	 * Creates a new s c product screenshot with the primary key. Does not add the s c product screenshot to the database.
	 *
	 * @param productScreenshotId the primary key for the new s c product screenshot
	 * @return the new s c product screenshot
	 */
	public SCProductScreenshot create(long productScreenshotId) {
		SCProductScreenshot scProductScreenshot = new SCProductScreenshotImpl();

		scProductScreenshot.setNew(true);
		scProductScreenshot.setPrimaryKey(productScreenshotId);

		return scProductScreenshot;
	}

	/**
	 * Removes the s c product screenshot with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param productScreenshotId the primary key of the s c product screenshot
	 * @return the s c product screenshot that was removed
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException if a s c product screenshot with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductScreenshot remove(long productScreenshotId)
		throws NoSuchProductScreenshotException, SystemException {
		return remove(Long.valueOf(productScreenshotId));
	}

	/**
	 * Removes the s c product screenshot with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the s c product screenshot
	 * @return the s c product screenshot that was removed
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException if a s c product screenshot with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCProductScreenshot remove(Serializable primaryKey)
		throws NoSuchProductScreenshotException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SCProductScreenshot scProductScreenshot = (SCProductScreenshot)session.get(SCProductScreenshotImpl.class,
					primaryKey);

			if (scProductScreenshot == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchProductScreenshotException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(scProductScreenshot);
		}
		catch (NoSuchProductScreenshotException nsee) {
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
	protected SCProductScreenshot removeImpl(
		SCProductScreenshot scProductScreenshot) throws SystemException {
		scProductScreenshot = toUnwrappedModel(scProductScreenshot);

		Session session = null;

		try {
			session = openSession();

			if (scProductScreenshot.isCachedModel()) {
				scProductScreenshot = (SCProductScreenshot)session.get(SCProductScreenshotImpl.class,
						scProductScreenshot.getPrimaryKeyObj());
			}

			if (scProductScreenshot != null) {
				session.delete(scProductScreenshot);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (scProductScreenshot != null) {
			clearCache(scProductScreenshot);
		}

		return scProductScreenshot;
	}

	@Override
	public SCProductScreenshot updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot)
		throws SystemException {
		scProductScreenshot = toUnwrappedModel(scProductScreenshot);

		boolean isNew = scProductScreenshot.isNew();

		SCProductScreenshotModelImpl scProductScreenshotModelImpl = (SCProductScreenshotModelImpl)scProductScreenshot;

		Session session = null;

		try {
			session = openSession();

			if (scProductScreenshot.isNew()) {
				session.save(scProductScreenshot);

				scProductScreenshot.setNew(false);
			}
			else {
				session.merge(scProductScreenshot);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SCProductScreenshotModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((scProductScreenshotModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PRODUCTENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(scProductScreenshotModelImpl.getOriginalProductEntryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PRODUCTENTRYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PRODUCTENTRYID,
					args);

				args = new Object[] {
						Long.valueOf(scProductScreenshotModelImpl.getProductEntryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PRODUCTENTRYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PRODUCTENTRYID,
					args);
			}
		}

		EntityCacheUtil.putResult(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotImpl.class, scProductScreenshot.getPrimaryKey(),
			scProductScreenshot);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
				new Object[] { Long.valueOf(
						scProductScreenshot.getThumbnailId()) },
				scProductScreenshot);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
				new Object[] { Long.valueOf(
						scProductScreenshot.getFullImageId()) },
				scProductScreenshot);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_P,
				new Object[] {
					Long.valueOf(scProductScreenshot.getProductEntryId()),
					Integer.valueOf(scProductScreenshot.getPriority())
				}, scProductScreenshot);
		}
		else {
			if ((scProductScreenshotModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_THUMBNAILID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(scProductScreenshotModelImpl.getOriginalThumbnailId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_THUMBNAILID,
					args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
					args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
					new Object[] {
						Long.valueOf(scProductScreenshot.getThumbnailId())
					}, scProductScreenshot);
			}

			if ((scProductScreenshotModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_FULLIMAGEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(scProductScreenshotModelImpl.getOriginalFullImageId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_FULLIMAGEID,
					args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
					args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
					new Object[] {
						Long.valueOf(scProductScreenshot.getFullImageId())
					}, scProductScreenshot);
			}

			if ((scProductScreenshotModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_P_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(scProductScreenshotModelImpl.getOriginalProductEntryId()),
						Integer.valueOf(scProductScreenshotModelImpl.getOriginalPriority())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_P_P, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_P_P, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_P,
					new Object[] {
						Long.valueOf(scProductScreenshot.getProductEntryId()),
						Integer.valueOf(scProductScreenshot.getPriority())
					}, scProductScreenshot);
			}
		}

		return scProductScreenshot;
	}

	protected SCProductScreenshot toUnwrappedModel(
		SCProductScreenshot scProductScreenshot) {
		if (scProductScreenshot instanceof SCProductScreenshotImpl) {
			return scProductScreenshot;
		}

		SCProductScreenshotImpl scProductScreenshotImpl = new SCProductScreenshotImpl();

		scProductScreenshotImpl.setNew(scProductScreenshot.isNew());
		scProductScreenshotImpl.setPrimaryKey(scProductScreenshot.getPrimaryKey());

		scProductScreenshotImpl.setProductScreenshotId(scProductScreenshot.getProductScreenshotId());
		scProductScreenshotImpl.setCompanyId(scProductScreenshot.getCompanyId());
		scProductScreenshotImpl.setGroupId(scProductScreenshot.getGroupId());
		scProductScreenshotImpl.setProductEntryId(scProductScreenshot.getProductEntryId());
		scProductScreenshotImpl.setThumbnailId(scProductScreenshot.getThumbnailId());
		scProductScreenshotImpl.setFullImageId(scProductScreenshot.getFullImageId());
		scProductScreenshotImpl.setPriority(scProductScreenshot.getPriority());

		return scProductScreenshotImpl;
	}

	/**
	 * Returns the s c product screenshot with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the s c product screenshot
	 * @return the s c product screenshot
	 * @throws com.liferay.portal.NoSuchModelException if a s c product screenshot with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCProductScreenshot findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the s c product screenshot with the primary key or throws a {@link com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException} if it could not be found.
	 *
	 * @param productScreenshotId the primary key of the s c product screenshot
	 * @return the s c product screenshot
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException if a s c product screenshot with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductScreenshot findByPrimaryKey(long productScreenshotId)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = fetchByPrimaryKey(productScreenshotId);

		if (scProductScreenshot == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					productScreenshotId);
			}

			throw new NoSuchProductScreenshotException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				productScreenshotId);
		}

		return scProductScreenshot;
	}

	/**
	 * Returns the s c product screenshot with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the s c product screenshot
	 * @return the s c product screenshot, or <code>null</code> if a s c product screenshot with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SCProductScreenshot fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the s c product screenshot with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param productScreenshotId the primary key of the s c product screenshot
	 * @return the s c product screenshot, or <code>null</code> if a s c product screenshot with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductScreenshot fetchByPrimaryKey(long productScreenshotId)
		throws SystemException {
		SCProductScreenshot scProductScreenshot = (SCProductScreenshot)EntityCacheUtil.getResult(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
				SCProductScreenshotImpl.class, productScreenshotId);

		if (scProductScreenshot == _nullSCProductScreenshot) {
			return null;
		}

		if (scProductScreenshot == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				scProductScreenshot = (SCProductScreenshot)session.get(SCProductScreenshotImpl.class,
						Long.valueOf(productScreenshotId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (scProductScreenshot != null) {
					cacheResult(scProductScreenshot);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
						SCProductScreenshotImpl.class, productScreenshotId,
						_nullSCProductScreenshot);
				}

				closeSession(session);
			}
		}

		return scProductScreenshot;
	}

	/**
	 * Returns all the s c product screenshots where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @return the matching s c product screenshots
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductScreenshot> findByProductEntryId(long productEntryId)
		throws SystemException {
		return findByProductEntryId(productEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c product screenshots where productEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param productEntryId the product entry ID
	 * @param start the lower bound of the range of s c product screenshots
	 * @param end the upper bound of the range of s c product screenshots (not inclusive)
	 * @return the range of matching s c product screenshots
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductScreenshot> findByProductEntryId(long productEntryId,
		int start, int end) throws SystemException {
		return findByProductEntryId(productEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c product screenshots where productEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param productEntryId the product entry ID
	 * @param start the lower bound of the range of s c product screenshots
	 * @param end the upper bound of the range of s c product screenshots (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c product screenshots
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductScreenshot> findByProductEntryId(long productEntryId,
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

		List<SCProductScreenshot> list = (List<SCProductScreenshot>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SCProductScreenshot scProductScreenshot : list) {
				if ((productEntryId != scProductScreenshot.getProductEntryId())) {
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

			query.append(_SQL_SELECT_SCPRODUCTSCREENSHOT_WHERE);

			query.append(_FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SCProductScreenshotModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				list = (List<SCProductScreenshot>)QueryUtil.list(q,
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
	 * Returns the first s c product screenshot in the ordered set where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c product screenshot
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException if a matching s c product screenshot could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductScreenshot findByProductEntryId_First(long productEntryId,
		OrderByComparator orderByComparator)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = fetchByProductEntryId_First(productEntryId,
				orderByComparator);

		if (scProductScreenshot != null) {
			return scProductScreenshot;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("productEntryId=");
		msg.append(productEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductScreenshotException(msg.toString());
	}

	/**
	 * Returns the first s c product screenshot in the ordered set where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductScreenshot fetchByProductEntryId_First(
		long productEntryId, OrderByComparator orderByComparator)
		throws SystemException {
		List<SCProductScreenshot> list = findByProductEntryId(productEntryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s c product screenshot in the ordered set where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c product screenshot
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException if a matching s c product screenshot could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductScreenshot findByProductEntryId_Last(long productEntryId,
		OrderByComparator orderByComparator)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = fetchByProductEntryId_Last(productEntryId,
				orderByComparator);

		if (scProductScreenshot != null) {
			return scProductScreenshot;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("productEntryId=");
		msg.append(productEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductScreenshotException(msg.toString());
	}

	/**
	 * Returns the last s c product screenshot in the ordered set where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductScreenshot fetchByProductEntryId_Last(long productEntryId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByProductEntryId(productEntryId);

		List<SCProductScreenshot> list = findByProductEntryId(productEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s c product screenshots before and after the current s c product screenshot in the ordered set where productEntryId = &#63;.
	 *
	 * @param productScreenshotId the primary key of the current s c product screenshot
	 * @param productEntryId the product entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c product screenshot
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException if a s c product screenshot with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductScreenshot[] findByProductEntryId_PrevAndNext(
		long productScreenshotId, long productEntryId,
		OrderByComparator orderByComparator)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = findByPrimaryKey(productScreenshotId);

		Session session = null;

		try {
			session = openSession();

			SCProductScreenshot[] array = new SCProductScreenshotImpl[3];

			array[0] = getByProductEntryId_PrevAndNext(session,
					scProductScreenshot, productEntryId, orderByComparator, true);

			array[1] = scProductScreenshot;

			array[2] = getByProductEntryId_PrevAndNext(session,
					scProductScreenshot, productEntryId, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCProductScreenshot getByProductEntryId_PrevAndNext(
		Session session, SCProductScreenshot scProductScreenshot,
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

		query.append(_SQL_SELECT_SCPRODUCTSCREENSHOT_WHERE);

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
			query.append(SCProductScreenshotModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(productEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scProductScreenshot);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCProductScreenshot> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the s c product screenshot where thumbnailId = &#63; or throws a {@link com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException} if it could not be found.
	 *
	 * @param thumbnailId the thumbnail ID
	 * @return the matching s c product screenshot
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException if a matching s c product screenshot could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductScreenshot findByThumbnailId(long thumbnailId)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = fetchByThumbnailId(thumbnailId);

		if (scProductScreenshot == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("thumbnailId=");
			msg.append(thumbnailId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProductScreenshotException(msg.toString());
		}

		return scProductScreenshot;
	}

	/**
	 * Returns the s c product screenshot where thumbnailId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param thumbnailId the thumbnail ID
	 * @return the matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductScreenshot fetchByThumbnailId(long thumbnailId)
		throws SystemException {
		return fetchByThumbnailId(thumbnailId, true);
	}

	/**
	 * Returns the s c product screenshot where thumbnailId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param thumbnailId the thumbnail ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductScreenshot fetchByThumbnailId(long thumbnailId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { thumbnailId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
					finderArgs, this);
		}

		if (result instanceof SCProductScreenshot) {
			SCProductScreenshot scProductScreenshot = (SCProductScreenshot)result;

			if ((thumbnailId != scProductScreenshot.getThumbnailId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_SCPRODUCTSCREENSHOT_WHERE);

			query.append(_FINDER_COLUMN_THUMBNAILID_THUMBNAILID_2);

			query.append(SCProductScreenshotModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(thumbnailId);

				List<SCProductScreenshot> list = q.list();

				result = list;

				SCProductScreenshot scProductScreenshot = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
						finderArgs, list);
				}
				else {
					scProductScreenshot = list.get(0);

					cacheResult(scProductScreenshot);

					if ((scProductScreenshot.getThumbnailId() != thumbnailId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
							finderArgs, scProductScreenshot);
					}
				}

				return scProductScreenshot;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
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
				return (SCProductScreenshot)result;
			}
		}
	}

	/**
	 * Returns the s c product screenshot where fullImageId = &#63; or throws a {@link com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException} if it could not be found.
	 *
	 * @param fullImageId the full image ID
	 * @return the matching s c product screenshot
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException if a matching s c product screenshot could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductScreenshot findByFullImageId(long fullImageId)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = fetchByFullImageId(fullImageId);

		if (scProductScreenshot == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("fullImageId=");
			msg.append(fullImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProductScreenshotException(msg.toString());
		}

		return scProductScreenshot;
	}

	/**
	 * Returns the s c product screenshot where fullImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fullImageId the full image ID
	 * @return the matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductScreenshot fetchByFullImageId(long fullImageId)
		throws SystemException {
		return fetchByFullImageId(fullImageId, true);
	}

	/**
	 * Returns the s c product screenshot where fullImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fullImageId the full image ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductScreenshot fetchByFullImageId(long fullImageId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { fullImageId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
					finderArgs, this);
		}

		if (result instanceof SCProductScreenshot) {
			SCProductScreenshot scProductScreenshot = (SCProductScreenshot)result;

			if ((fullImageId != scProductScreenshot.getFullImageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_SCPRODUCTSCREENSHOT_WHERE);

			query.append(_FINDER_COLUMN_FULLIMAGEID_FULLIMAGEID_2);

			query.append(SCProductScreenshotModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fullImageId);

				List<SCProductScreenshot> list = q.list();

				result = list;

				SCProductScreenshot scProductScreenshot = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
						finderArgs, list);
				}
				else {
					scProductScreenshot = list.get(0);

					cacheResult(scProductScreenshot);

					if ((scProductScreenshot.getFullImageId() != fullImageId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
							finderArgs, scProductScreenshot);
					}
				}

				return scProductScreenshot;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
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
				return (SCProductScreenshot)result;
			}
		}
	}

	/**
	 * Returns the s c product screenshot where productEntryId = &#63; and priority = &#63; or throws a {@link com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException} if it could not be found.
	 *
	 * @param productEntryId the product entry ID
	 * @param priority the priority
	 * @return the matching s c product screenshot
	 * @throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException if a matching s c product screenshot could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductScreenshot findByP_P(long productEntryId, int priority)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = fetchByP_P(productEntryId,
				priority);

		if (scProductScreenshot == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("productEntryId=");
			msg.append(productEntryId);

			msg.append(", priority=");
			msg.append(priority);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProductScreenshotException(msg.toString());
		}

		return scProductScreenshot;
	}

	/**
	 * Returns the s c product screenshot where productEntryId = &#63; and priority = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param productEntryId the product entry ID
	 * @param priority the priority
	 * @return the matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductScreenshot fetchByP_P(long productEntryId, int priority)
		throws SystemException {
		return fetchByP_P(productEntryId, priority, true);
	}

	/**
	 * Returns the s c product screenshot where productEntryId = &#63; and priority = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param productEntryId the product entry ID
	 * @param priority the priority
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductScreenshot fetchByP_P(long productEntryId, int priority,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { productEntryId, priority };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_P_P,
					finderArgs, this);
		}

		if (result instanceof SCProductScreenshot) {
			SCProductScreenshot scProductScreenshot = (SCProductScreenshot)result;

			if ((productEntryId != scProductScreenshot.getProductEntryId()) ||
					(priority != scProductScreenshot.getPriority())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SCPRODUCTSCREENSHOT_WHERE);

			query.append(_FINDER_COLUMN_P_P_PRODUCTENTRYID_2);

			query.append(_FINDER_COLUMN_P_P_PRIORITY_2);

			query.append(SCProductScreenshotModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				qPos.add(priority);

				List<SCProductScreenshot> list = q.list();

				result = list;

				SCProductScreenshot scProductScreenshot = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_P,
						finderArgs, list);
				}
				else {
					scProductScreenshot = list.get(0);

					cacheResult(scProductScreenshot);

					if ((scProductScreenshot.getProductEntryId() != productEntryId) ||
							(scProductScreenshot.getPriority() != priority)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_P,
							finderArgs, scProductScreenshot);
					}
				}

				return scProductScreenshot;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_P_P,
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
				return (SCProductScreenshot)result;
			}
		}
	}

	/**
	 * Returns all the s c product screenshots.
	 *
	 * @return the s c product screenshots
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductScreenshot> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c product screenshots.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of s c product screenshots
	 * @param end the upper bound of the range of s c product screenshots (not inclusive)
	 * @return the range of s c product screenshots
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductScreenshot> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c product screenshots.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of s c product screenshots
	 * @param end the upper bound of the range of s c product screenshots (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of s c product screenshots
	 * @throws SystemException if a system exception occurred
	 */
	public List<SCProductScreenshot> findAll(int start, int end,
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

		List<SCProductScreenshot> list = (List<SCProductScreenshot>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SCPRODUCTSCREENSHOT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SCPRODUCTSCREENSHOT.concat(SCProductScreenshotModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SCProductScreenshot>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SCProductScreenshot>)QueryUtil.list(q,
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
	 * Removes all the s c product screenshots where productEntryId = &#63; from the database.
	 *
	 * @param productEntryId the product entry ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByProductEntryId(long productEntryId)
		throws SystemException {
		for (SCProductScreenshot scProductScreenshot : findByProductEntryId(
				productEntryId)) {
			remove(scProductScreenshot);
		}
	}

	/**
	 * Removes the s c product screenshot where thumbnailId = &#63; from the database.
	 *
	 * @param thumbnailId the thumbnail ID
	 * @return the s c product screenshot that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductScreenshot removeByThumbnailId(long thumbnailId)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = findByThumbnailId(thumbnailId);

		return remove(scProductScreenshot);
	}

	/**
	 * Removes the s c product screenshot where fullImageId = &#63; from the database.
	 *
	 * @param fullImageId the full image ID
	 * @return the s c product screenshot that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductScreenshot removeByFullImageId(long fullImageId)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = findByFullImageId(fullImageId);

		return remove(scProductScreenshot);
	}

	/**
	 * Removes the s c product screenshot where productEntryId = &#63; and priority = &#63; from the database.
	 *
	 * @param productEntryId the product entry ID
	 * @param priority the priority
	 * @return the s c product screenshot that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public SCProductScreenshot removeByP_P(long productEntryId, int priority)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = findByP_P(productEntryId,
				priority);

		return remove(scProductScreenshot);
	}

	/**
	 * Removes all the s c product screenshots from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SCProductScreenshot scProductScreenshot : findAll()) {
			remove(scProductScreenshot);
		}
	}

	/**
	 * Returns the number of s c product screenshots where productEntryId = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @return the number of matching s c product screenshots
	 * @throws SystemException if a system exception occurred
	 */
	public int countByProductEntryId(long productEntryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { productEntryId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PRODUCTENTRYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCPRODUCTSCREENSHOT_WHERE);

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
	 * Returns the number of s c product screenshots where thumbnailId = &#63;.
	 *
	 * @param thumbnailId the thumbnail ID
	 * @return the number of matching s c product screenshots
	 * @throws SystemException if a system exception occurred
	 */
	public int countByThumbnailId(long thumbnailId) throws SystemException {
		Object[] finderArgs = new Object[] { thumbnailId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_THUMBNAILID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCPRODUCTSCREENSHOT_WHERE);

			query.append(_FINDER_COLUMN_THUMBNAILID_THUMBNAILID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(thumbnailId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_THUMBNAILID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of s c product screenshots where fullImageId = &#63;.
	 *
	 * @param fullImageId the full image ID
	 * @return the number of matching s c product screenshots
	 * @throws SystemException if a system exception occurred
	 */
	public int countByFullImageId(long fullImageId) throws SystemException {
		Object[] finderArgs = new Object[] { fullImageId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FULLIMAGEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCPRODUCTSCREENSHOT_WHERE);

			query.append(_FINDER_COLUMN_FULLIMAGEID_FULLIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fullImageId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_FULLIMAGEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of s c product screenshots where productEntryId = &#63; and priority = &#63;.
	 *
	 * @param productEntryId the product entry ID
	 * @param priority the priority
	 * @return the number of matching s c product screenshots
	 * @throws SystemException if a system exception occurred
	 */
	public int countByP_P(long productEntryId, int priority)
		throws SystemException {
		Object[] finderArgs = new Object[] { productEntryId, priority };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SCPRODUCTSCREENSHOT_WHERE);

			query.append(_FINDER_COLUMN_P_P_PRODUCTENTRYID_2);

			query.append(_FINDER_COLUMN_P_P_PRIORITY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				qPos.add(priority);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of s c product screenshots.
	 *
	 * @return the number of s c product screenshots
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SCPRODUCTSCREENSHOT);

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
	 * Initializes the s c product screenshot persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.softwarecatalog.model.SCProductScreenshot")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SCProductScreenshot>> listenersList = new ArrayList<ModelListener<SCProductScreenshot>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SCProductScreenshot>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(SCProductScreenshotImpl.class.getName());
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
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_SCPRODUCTSCREENSHOT = "SELECT scProductScreenshot FROM SCProductScreenshot scProductScreenshot";
	private static final String _SQL_SELECT_SCPRODUCTSCREENSHOT_WHERE = "SELECT scProductScreenshot FROM SCProductScreenshot scProductScreenshot WHERE ";
	private static final String _SQL_COUNT_SCPRODUCTSCREENSHOT = "SELECT COUNT(scProductScreenshot) FROM SCProductScreenshot scProductScreenshot";
	private static final String _SQL_COUNT_SCPRODUCTSCREENSHOT_WHERE = "SELECT COUNT(scProductScreenshot) FROM SCProductScreenshot scProductScreenshot WHERE ";
	private static final String _FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2 = "scProductScreenshot.productEntryId = ?";
	private static final String _FINDER_COLUMN_THUMBNAILID_THUMBNAILID_2 = "scProductScreenshot.thumbnailId = ?";
	private static final String _FINDER_COLUMN_FULLIMAGEID_FULLIMAGEID_2 = "scProductScreenshot.fullImageId = ?";
	private static final String _FINDER_COLUMN_P_P_PRODUCTENTRYID_2 = "scProductScreenshot.productEntryId = ? AND ";
	private static final String _FINDER_COLUMN_P_P_PRIORITY_2 = "scProductScreenshot.priority = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "scProductScreenshot.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SCProductScreenshot exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SCProductScreenshot exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(SCProductScreenshotPersistenceImpl.class);
	private static SCProductScreenshot _nullSCProductScreenshot = new SCProductScreenshotImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SCProductScreenshot> toCacheModel() {
				return _nullSCProductScreenshotCacheModel;
			}
		};

	private static CacheModel<SCProductScreenshot> _nullSCProductScreenshotCacheModel =
		new CacheModel<SCProductScreenshot>() {
			public SCProductScreenshot toEntityModel() {
				return _nullSCProductScreenshot;
			}
		};
}