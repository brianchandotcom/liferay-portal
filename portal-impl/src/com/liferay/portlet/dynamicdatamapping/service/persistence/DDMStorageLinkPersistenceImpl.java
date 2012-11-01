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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

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

import com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStorageLinkImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStorageLinkModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the d d m storage link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStorageLinkPersistence
 * @see DDMStorageLinkUtil
 * @generated
 */
public class DDMStorageLinkPersistenceImpl extends BasePersistenceImpl<DDMStorageLink>
	implements DDMStorageLinkPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDMStorageLinkUtil} to access the d d m storage link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDMStorageLinkImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStorageLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStorageLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			DDMStorageLinkModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_CLASSPK = new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStorageLinkImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByClassPK", new String[] { Long.class.getName() },
			DDMStorageLinkModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CLASSPK = new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByClassPK",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_STRUCTUREID =
		new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStorageLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByStructureId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID =
		new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStorageLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByStructureId",
			new String[] { Long.class.getName() },
			DDMStorageLinkModelImpl.STRUCTUREID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_STRUCTUREID = new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByStructureId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStorageLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStorageLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the d d m storage link in the entity cache if it is enabled.
	 *
	 * @param ddmStorageLink the d d m storage link
	 */
	public void cacheResult(DDMStorageLink ddmStorageLink) {
		EntityCacheUtil.putResult(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkImpl.class, ddmStorageLink.getPrimaryKey(),
			ddmStorageLink);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CLASSPK,
			new Object[] { Long.valueOf(ddmStorageLink.getClassPK()) },
			ddmStorageLink);

		ddmStorageLink.resetOriginalValues();
	}

	/**
	 * Caches the d d m storage links in the entity cache if it is enabled.
	 *
	 * @param ddmStorageLinks the d d m storage links
	 */
	public void cacheResult(List<DDMStorageLink> ddmStorageLinks) {
		for (DDMStorageLink ddmStorageLink : ddmStorageLinks) {
			if (EntityCacheUtil.getResult(
						DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
						DDMStorageLinkImpl.class, ddmStorageLink.getPrimaryKey()) == null) {
				cacheResult(ddmStorageLink);
			}
			else {
				ddmStorageLink.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all d d m storage links.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DDMStorageLinkImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DDMStorageLinkImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the d d m storage link.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMStorageLink ddmStorageLink) {
		EntityCacheUtil.removeResult(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkImpl.class, ddmStorageLink.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(ddmStorageLink);
	}

	@Override
	public void clearCache(List<DDMStorageLink> ddmStorageLinks) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMStorageLink ddmStorageLink : ddmStorageLinks) {
			EntityCacheUtil.removeResult(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
				DDMStorageLinkImpl.class, ddmStorageLink.getPrimaryKey());

			clearUniqueFindersCache(ddmStorageLink);
		}
	}

	protected void clearUniqueFindersCache(DDMStorageLink ddmStorageLink) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CLASSPK,
			new Object[] { Long.valueOf(ddmStorageLink.getClassPK()) });
	}

	/**
	 * Creates a new d d m storage link with the primary key. Does not add the d d m storage link to the database.
	 *
	 * @param storageLinkId the primary key for the new d d m storage link
	 * @return the new d d m storage link
	 */
	public DDMStorageLink create(long storageLinkId) {
		DDMStorageLink ddmStorageLink = new DDMStorageLinkImpl();

		ddmStorageLink.setNew(true);
		ddmStorageLink.setPrimaryKey(storageLinkId);

		String uuid = PortalUUIDUtil.generate();

		ddmStorageLink.setUuid(uuid);

		return ddmStorageLink;
	}

	/**
	 * Removes the d d m storage link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param storageLinkId the primary key of the d d m storage link
	 * @return the d d m storage link that was removed
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStorageLink remove(long storageLinkId)
		throws NoSuchStorageLinkException, SystemException {
		return remove(Long.valueOf(storageLinkId));
	}

	/**
	 * Removes the d d m storage link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d d m storage link
	 * @return the d d m storage link that was removed
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStorageLink remove(Serializable primaryKey)
		throws NoSuchStorageLinkException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DDMStorageLink ddmStorageLink = (DDMStorageLink)session.get(DDMStorageLinkImpl.class,
					primaryKey);

			if (ddmStorageLink == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStorageLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ddmStorageLink);
		}
		catch (NoSuchStorageLinkException nsee) {
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
	protected DDMStorageLink removeImpl(DDMStorageLink ddmStorageLink)
		throws SystemException {
		ddmStorageLink = toUnwrappedModel(ddmStorageLink);

		Session session = null;

		try {
			session = openSession();

			if (ddmStorageLink.isCachedModel()) {
				ddmStorageLink = (DDMStorageLink)session.get(DDMStorageLinkImpl.class,
						ddmStorageLink.getPrimaryKeyObj());
			}

			if (ddmStorageLink != null) {
				session.delete(ddmStorageLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ddmStorageLink != null) {
			clearCache(ddmStorageLink);
		}

		return ddmStorageLink;
	}

	@Override
	public DDMStorageLink updateImpl(
		com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink ddmStorageLink)
		throws SystemException {
		ddmStorageLink = toUnwrappedModel(ddmStorageLink);

		boolean isNew = ddmStorageLink.isNew();

		DDMStorageLinkModelImpl ddmStorageLinkModelImpl = (DDMStorageLinkModelImpl)ddmStorageLink;

		if (Validator.isNull(ddmStorageLink.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ddmStorageLink.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (ddmStorageLink.isNew()) {
				session.save(ddmStorageLink);

				ddmStorageLink.setNew(false);
			}
			else {
				session.merge(ddmStorageLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DDMStorageLinkModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((ddmStorageLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStorageLinkModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { ddmStorageLinkModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((ddmStorageLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ddmStorageLinkModelImpl.getOriginalStructureId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID,
					args);

				args = new Object[] {
						Long.valueOf(ddmStorageLinkModelImpl.getStructureId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID,
					args);
			}
		}

		EntityCacheUtil.putResult(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkImpl.class, ddmStorageLink.getPrimaryKey(),
			ddmStorageLink);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CLASSPK,
				new Object[] { Long.valueOf(ddmStorageLink.getClassPK()) },
				ddmStorageLink);
		}
		else {
			if ((ddmStorageLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_CLASSPK.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ddmStorageLinkModelImpl.getOriginalClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CLASSPK, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CLASSPK, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CLASSPK,
					new Object[] { Long.valueOf(ddmStorageLink.getClassPK()) },
					ddmStorageLink);
			}
		}

		return ddmStorageLink;
	}

	protected DDMStorageLink toUnwrappedModel(DDMStorageLink ddmStorageLink) {
		if (ddmStorageLink instanceof DDMStorageLinkImpl) {
			return ddmStorageLink;
		}

		DDMStorageLinkImpl ddmStorageLinkImpl = new DDMStorageLinkImpl();

		ddmStorageLinkImpl.setNew(ddmStorageLink.isNew());
		ddmStorageLinkImpl.setPrimaryKey(ddmStorageLink.getPrimaryKey());

		ddmStorageLinkImpl.setUuid(ddmStorageLink.getUuid());
		ddmStorageLinkImpl.setStorageLinkId(ddmStorageLink.getStorageLinkId());
		ddmStorageLinkImpl.setClassNameId(ddmStorageLink.getClassNameId());
		ddmStorageLinkImpl.setClassPK(ddmStorageLink.getClassPK());
		ddmStorageLinkImpl.setStructureId(ddmStorageLink.getStructureId());

		return ddmStorageLinkImpl;
	}

	/**
	 * Returns the d d m storage link with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m storage link
	 * @return the d d m storage link
	 * @throws com.liferay.portal.NoSuchModelException if a d d m storage link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStorageLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the d d m storage link with the primary key or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException} if it could not be found.
	 *
	 * @param storageLinkId the primary key of the d d m storage link
	 * @return the d d m storage link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStorageLink findByPrimaryKey(long storageLinkId)
		throws NoSuchStorageLinkException, SystemException {
		DDMStorageLink ddmStorageLink = fetchByPrimaryKey(storageLinkId);

		if (ddmStorageLink == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + storageLinkId);
			}

			throw new NoSuchStorageLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				storageLinkId);
		}

		return ddmStorageLink;
	}

	/**
	 * Returns the d d m storage link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m storage link
	 * @return the d d m storage link, or <code>null</code> if a d d m storage link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStorageLink fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the d d m storage link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param storageLinkId the primary key of the d d m storage link
	 * @return the d d m storage link, or <code>null</code> if a d d m storage link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStorageLink fetchByPrimaryKey(long storageLinkId)
		throws SystemException {
		DDMStorageLink ddmStorageLink = (DDMStorageLink)EntityCacheUtil.getResult(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
				DDMStorageLinkImpl.class, storageLinkId);

		if (ddmStorageLink == _nullDDMStorageLink) {
			return null;
		}

		if (ddmStorageLink == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				ddmStorageLink = (DDMStorageLink)session.get(DDMStorageLinkImpl.class,
						Long.valueOf(storageLinkId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (ddmStorageLink != null) {
					cacheResult(ddmStorageLink);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
						DDMStorageLinkImpl.class, storageLinkId,
						_nullDDMStorageLink);
				}

				closeSession(session);
			}
		}

		return ddmStorageLink;
	}

	/**
	 * Returns all the d d m storage links where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching d d m storage links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStorageLink> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m storage links where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m storage links
	 * @param end the upper bound of the range of d d m storage links (not inclusive)
	 * @return the range of matching d d m storage links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStorageLink> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m storage links where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m storage links
	 * @param end the upper bound of the range of d d m storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m storage links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStorageLink> findByUuid(String uuid, int start, int end,
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

		List<DDMStorageLink> list = (List<DDMStorageLink>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStorageLink ddmStorageLink : list) {
				if (!Validator.equals(uuid, ddmStorageLink.getUuid())) {
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

			query.append(_SQL_SELECT_DDMSTORAGELINK_WHERE);

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

				list = (List<DDMStorageLink>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m storage link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m storage link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStorageLink findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchStorageLinkException, SystemException {
		DDMStorageLink ddmStorageLink = fetchByUuid_First(uuid,
				orderByComparator);

		if (ddmStorageLink != null) {
			return ddmStorageLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStorageLinkException(msg.toString());
	}

	/**
	 * Returns the first d d m storage link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStorageLink fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<DDMStorageLink> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m storage link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m storage link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStorageLink findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchStorageLinkException, SystemException {
		DDMStorageLink ddmStorageLink = fetchByUuid_Last(uuid, orderByComparator);

		if (ddmStorageLink != null) {
			return ddmStorageLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStorageLinkException(msg.toString());
	}

	/**
	 * Returns the last d d m storage link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStorageLink fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<DDMStorageLink> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m storage links before and after the current d d m storage link in the ordered set where uuid = &#63;.
	 *
	 * @param storageLinkId the primary key of the current d d m storage link
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m storage link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStorageLink[] findByUuid_PrevAndNext(long storageLinkId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchStorageLinkException, SystemException {
		DDMStorageLink ddmStorageLink = findByPrimaryKey(storageLinkId);

		Session session = null;

		try {
			session = openSession();

			DDMStorageLink[] array = new DDMStorageLinkImpl[3];

			array[0] = getByUuid_PrevAndNext(session, ddmStorageLink, uuid,
					orderByComparator, true);

			array[1] = ddmStorageLink;

			array[2] = getByUuid_PrevAndNext(session, ddmStorageLink, uuid,
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

	protected DDMStorageLink getByUuid_PrevAndNext(Session session,
		DDMStorageLink ddmStorageLink, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTORAGELINK_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStorageLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStorageLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the d d m storage link where classPK = &#63; or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException} if it could not be found.
	 *
	 * @param classPK the class p k
	 * @return the matching d d m storage link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStorageLink findByClassPK(long classPK)
		throws NoSuchStorageLinkException, SystemException {
		DDMStorageLink ddmStorageLink = fetchByClassPK(classPK);

		if (ddmStorageLink == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStorageLinkException(msg.toString());
		}

		return ddmStorageLink;
	}

	/**
	 * Returns the d d m storage link where classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classPK the class p k
	 * @return the matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStorageLink fetchByClassPK(long classPK)
		throws SystemException {
		return fetchByClassPK(classPK, true);
	}

	/**
	 * Returns the d d m storage link where classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classPK the class p k
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStorageLink fetchByClassPK(long classPK, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_CLASSPK,
					finderArgs, this);
		}

		if (result instanceof DDMStorageLink) {
			DDMStorageLink ddmStorageLink = (DDMStorageLink)result;

			if ((classPK != ddmStorageLink.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_DDMSTORAGELINK_WHERE);

			query.append(_FINDER_COLUMN_CLASSPK_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classPK);

				List<DDMStorageLink> list = q.list();

				result = list;

				DDMStorageLink ddmStorageLink = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CLASSPK,
						finderArgs, list);
				}
				else {
					ddmStorageLink = list.get(0);

					cacheResult(ddmStorageLink);

					if ((ddmStorageLink.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CLASSPK,
							finderArgs, ddmStorageLink);
					}
				}

				return ddmStorageLink;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CLASSPK,
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
				return (DDMStorageLink)result;
			}
		}
	}

	/**
	 * Returns all the d d m storage links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the matching d d m storage links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStorageLink> findByStructureId(long structureId)
		throws SystemException {
		return findByStructureId(structureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m storage links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of d d m storage links
	 * @param end the upper bound of the range of d d m storage links (not inclusive)
	 * @return the range of matching d d m storage links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStorageLink> findByStructureId(long structureId, int start,
		int end) throws SystemException {
		return findByStructureId(structureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m storage links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of d d m storage links
	 * @param end the upper bound of the range of d d m storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m storage links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStorageLink> findByStructureId(long structureId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID;
			finderArgs = new Object[] { structureId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_STRUCTUREID;
			finderArgs = new Object[] { structureId, start, end, orderByComparator };
		}

		List<DDMStorageLink> list = (List<DDMStorageLink>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStorageLink ddmStorageLink : list) {
				if ((structureId != ddmStorageLink.getStructureId())) {
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

			query.append(_SQL_SELECT_DDMSTORAGELINK_WHERE);

			query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

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

				qPos.add(structureId);

				list = (List<DDMStorageLink>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d d m storage link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m storage link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStorageLink findByStructureId_First(long structureId,
		OrderByComparator orderByComparator)
		throws NoSuchStorageLinkException, SystemException {
		DDMStorageLink ddmStorageLink = fetchByStructureId_First(structureId,
				orderByComparator);

		if (ddmStorageLink != null) {
			return ddmStorageLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("structureId=");
		msg.append(structureId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStorageLinkException(msg.toString());
	}

	/**
	 * Returns the first d d m storage link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStorageLink fetchByStructureId_First(long structureId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DDMStorageLink> list = findByStructureId(structureId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m storage link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m storage link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStorageLink findByStructureId_Last(long structureId,
		OrderByComparator orderByComparator)
		throws NoSuchStorageLinkException, SystemException {
		DDMStorageLink ddmStorageLink = fetchByStructureId_Last(structureId,
				orderByComparator);

		if (ddmStorageLink != null) {
			return ddmStorageLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("structureId=");
		msg.append(structureId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStorageLinkException(msg.toString());
	}

	/**
	 * Returns the last d d m storage link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStorageLink fetchByStructureId_Last(long structureId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByStructureId(structureId);

		List<DDMStorageLink> list = findByStructureId(structureId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m storage links before and after the current d d m storage link in the ordered set where structureId = &#63;.
	 *
	 * @param storageLinkId the primary key of the current d d m storage link
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m storage link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStorageLink[] findByStructureId_PrevAndNext(long storageLinkId,
		long structureId, OrderByComparator orderByComparator)
		throws NoSuchStorageLinkException, SystemException {
		DDMStorageLink ddmStorageLink = findByPrimaryKey(storageLinkId);

		Session session = null;

		try {
			session = openSession();

			DDMStorageLink[] array = new DDMStorageLinkImpl[3];

			array[0] = getByStructureId_PrevAndNext(session, ddmStorageLink,
					structureId, orderByComparator, true);

			array[1] = ddmStorageLink;

			array[2] = getByStructureId_PrevAndNext(session, ddmStorageLink,
					structureId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStorageLink getByStructureId_PrevAndNext(Session session,
		DDMStorageLink ddmStorageLink, long structureId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTORAGELINK_WHERE);

		query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

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

		qPos.add(structureId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStorageLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStorageLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m storage links.
	 *
	 * @return the d d m storage links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStorageLink> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m storage links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m storage links
	 * @param end the upper bound of the range of d d m storage links (not inclusive)
	 * @return the range of d d m storage links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStorageLink> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m storage links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m storage links
	 * @param end the upper bound of the range of d d m storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d m storage links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStorageLink> findAll(int start, int end,
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

		List<DDMStorageLink> list = (List<DDMStorageLink>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DDMSTORAGELINK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDMSTORAGELINK;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DDMStorageLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DDMStorageLink>)QueryUtil.list(q,
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
	 * Removes all the d d m storage links where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (DDMStorageLink ddmStorageLink : findByUuid(uuid)) {
			remove(ddmStorageLink);
		}
	}

	/**
	 * Removes the d d m storage link where classPK = &#63; from the database.
	 *
	 * @param classPK the class p k
	 * @return the d d m storage link that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStorageLink removeByClassPK(long classPK)
		throws NoSuchStorageLinkException, SystemException {
		DDMStorageLink ddmStorageLink = findByClassPK(classPK);

		return remove(ddmStorageLink);
	}

	/**
	 * Removes all the d d m storage links where structureId = &#63; from the database.
	 *
	 * @param structureId the structure ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByStructureId(long structureId) throws SystemException {
		for (DDMStorageLink ddmStorageLink : findByStructureId(structureId)) {
			remove(ddmStorageLink);
		}
	}

	/**
	 * Removes all the d d m storage links from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DDMStorageLink ddmStorageLink : findAll()) {
			remove(ddmStorageLink);
		}
	}

	/**
	 * Returns the number of d d m storage links where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching d d m storage links
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTORAGELINK_WHERE);

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
	 * Returns the number of d d m storage links where classPK = &#63;.
	 *
	 * @param classPK the class p k
	 * @return the number of matching d d m storage links
	 * @throws SystemException if a system exception occurred
	 */
	public int countByClassPK(long classPK) throws SystemException {
		Object[] finderArgs = new Object[] { classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CLASSPK,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTORAGELINK_WHERE);

			query.append(_FINDER_COLUMN_CLASSPK_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CLASSPK,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m storage links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the number of matching d d m storage links
	 * @throws SystemException if a system exception occurred
	 */
	public int countByStructureId(long structureId) throws SystemException {
		Object[] finderArgs = new Object[] { structureId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTORAGELINK_WHERE);

			query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(structureId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m storage links.
	 *
	 * @return the number of d d m storage links
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDMSTORAGELINK);

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
	 * Initializes the d d m storage link persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DDMStorageLink>> listenersList = new ArrayList<ModelListener<DDMStorageLink>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DDMStorageLink>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(DDMStorageLinkImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = DDMContentPersistence.class)
	protected DDMContentPersistence ddmContentPersistence;
	@BeanReference(type = DDMStorageLinkPersistence.class)
	protected DDMStorageLinkPersistence ddmStorageLinkPersistence;
	@BeanReference(type = DDMStructurePersistence.class)
	protected DDMStructurePersistence ddmStructurePersistence;
	@BeanReference(type = DDMStructureLinkPersistence.class)
	protected DDMStructureLinkPersistence ddmStructureLinkPersistence;
	@BeanReference(type = DDMTemplatePersistence.class)
	protected DDMTemplatePersistence ddmTemplatePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_DDMSTORAGELINK = "SELECT ddmStorageLink FROM DDMStorageLink ddmStorageLink";
	private static final String _SQL_SELECT_DDMSTORAGELINK_WHERE = "SELECT ddmStorageLink FROM DDMStorageLink ddmStorageLink WHERE ";
	private static final String _SQL_COUNT_DDMSTORAGELINK = "SELECT COUNT(ddmStorageLink) FROM DDMStorageLink ddmStorageLink";
	private static final String _SQL_COUNT_DDMSTORAGELINK_WHERE = "SELECT COUNT(ddmStorageLink) FROM DDMStorageLink ddmStorageLink WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "ddmStorageLink.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "ddmStorageLink.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(ddmStorageLink.uuid IS NULL OR ddmStorageLink.uuid = ?)";
	private static final String _FINDER_COLUMN_CLASSPK_CLASSPK_2 = "ddmStorageLink.classPK = ?";
	private static final String _FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2 = "ddmStorageLink.structureId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmStorageLink.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDMStorageLink exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDMStorageLink exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DDMStorageLinkPersistenceImpl.class);
	private static DDMStorageLink _nullDDMStorageLink = new DDMStorageLinkImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DDMStorageLink> toCacheModel() {
				return _nullDDMStorageLinkCacheModel;
			}
		};

	private static CacheModel<DDMStorageLink> _nullDDMStorageLinkCacheModel = new CacheModel<DDMStorageLink>() {
			public DDMStorageLink toEntityModel() {
				return _nullDDMStorageLink;
			}
		};
}