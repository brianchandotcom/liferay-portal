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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.NoSuchVocabularyException;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.model.impl.AssetVocabularyImpl;
import com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the asset vocabulary service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyPersistence
 * @see AssetVocabularyUtil
 * @generated
 */
public class AssetVocabularyPersistenceImpl extends BasePersistenceImpl<AssetVocabulary>
	implements AssetVocabularyPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AssetVocabularyUtil} to access the asset vocabulary persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AssetVocabularyImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyModelImpl.FINDER_CACHE_ENABLED,
			AssetVocabularyImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyModelImpl.FINDER_CACHE_ENABLED,
			AssetVocabularyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			AssetVocabularyModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyModelImpl.FINDER_CACHE_ENABLED,
			AssetVocabularyImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			AssetVocabularyModelImpl.UUID_COLUMN_BITMASK |
			AssetVocabularyModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyModelImpl.FINDER_CACHE_ENABLED,
			AssetVocabularyImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyModelImpl.FINDER_CACHE_ENABLED,
			AssetVocabularyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			AssetVocabularyModelImpl.UUID_COLUMN_BITMASK |
			AssetVocabularyModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyModelImpl.FINDER_CACHE_ENABLED,
			AssetVocabularyImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyModelImpl.FINDER_CACHE_ENABLED,
			AssetVocabularyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			AssetVocabularyModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyModelImpl.FINDER_CACHE_ENABLED,
			AssetVocabularyImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyModelImpl.FINDER_CACHE_ENABLED,
			AssetVocabularyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			AssetVocabularyModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_N = new FinderPath(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyModelImpl.FINDER_CACHE_ENABLED,
			AssetVocabularyImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_N",
			new String[] { Long.class.getName(), String.class.getName() },
			AssetVocabularyModelImpl.GROUPID_COLUMN_BITMASK |
			AssetVocabularyModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N = new FinderPath(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyModelImpl.FINDER_CACHE_ENABLED,
			AssetVocabularyImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyModelImpl.FINDER_CACHE_ENABLED,
			AssetVocabularyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the asset vocabulary in the entity cache if it is enabled.
	 *
	 * @param assetVocabulary the asset vocabulary
	 */
	public void cacheResult(AssetVocabulary assetVocabulary) {
		EntityCacheUtil.putResult(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyImpl.class, assetVocabulary.getPrimaryKey(),
			assetVocabulary);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				assetVocabulary.getUuid(),
				Long.valueOf(assetVocabulary.getGroupId())
			}, assetVocabulary);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				Long.valueOf(assetVocabulary.getGroupId()),
				
			assetVocabulary.getName()
			}, assetVocabulary);

		assetVocabulary.resetOriginalValues();
	}

	/**
	 * Caches the asset vocabularies in the entity cache if it is enabled.
	 *
	 * @param assetVocabularies the asset vocabularies
	 */
	public void cacheResult(List<AssetVocabulary> assetVocabularies) {
		for (AssetVocabulary assetVocabulary : assetVocabularies) {
			if (EntityCacheUtil.getResult(
						AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
						AssetVocabularyImpl.class,
						assetVocabulary.getPrimaryKey()) == null) {
				cacheResult(assetVocabulary);
			}
			else {
				assetVocabulary.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset vocabularies.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(AssetVocabularyImpl.class.getName());
		}

		EntityCacheUtil.clearCache(AssetVocabularyImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset vocabulary.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetVocabulary assetVocabulary) {
		EntityCacheUtil.removeResult(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyImpl.class, assetVocabulary.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(assetVocabulary);
	}

	@Override
	public void clearCache(List<AssetVocabulary> assetVocabularies) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetVocabulary assetVocabulary : assetVocabularies) {
			EntityCacheUtil.removeResult(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
				AssetVocabularyImpl.class, assetVocabulary.getPrimaryKey());

			clearUniqueFindersCache(assetVocabulary);
		}
	}

	protected void clearUniqueFindersCache(AssetVocabulary assetVocabulary) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				assetVocabulary.getUuid(),
				Long.valueOf(assetVocabulary.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				Long.valueOf(assetVocabulary.getGroupId()),
				
			assetVocabulary.getName()
			});
	}

	/**
	 * Creates a new asset vocabulary with the primary key. Does not add the asset vocabulary to the database.
	 *
	 * @param vocabularyId the primary key for the new asset vocabulary
	 * @return the new asset vocabulary
	 */
	public AssetVocabulary create(long vocabularyId) {
		AssetVocabulary assetVocabulary = new AssetVocabularyImpl();

		assetVocabulary.setNew(true);
		assetVocabulary.setPrimaryKey(vocabularyId);

		String uuid = PortalUUIDUtil.generate();

		assetVocabulary.setUuid(uuid);

		return assetVocabulary;
	}

	/**
	 * Removes the asset vocabulary with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param vocabularyId the primary key of the asset vocabulary
	 * @return the asset vocabulary that was removed
	 * @throws com.liferay.portlet.asset.NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary remove(long vocabularyId)
		throws NoSuchVocabularyException, SystemException {
		return remove(Long.valueOf(vocabularyId));
	}

	/**
	 * Removes the asset vocabulary with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset vocabulary
	 * @return the asset vocabulary that was removed
	 * @throws com.liferay.portlet.asset.NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetVocabulary remove(Serializable primaryKey)
		throws NoSuchVocabularyException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AssetVocabulary assetVocabulary = (AssetVocabulary)session.get(AssetVocabularyImpl.class,
					primaryKey);

			if (assetVocabulary == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchVocabularyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(assetVocabulary);
		}
		catch (NoSuchVocabularyException nsee) {
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
	protected AssetVocabulary removeImpl(AssetVocabulary assetVocabulary)
		throws SystemException {
		assetVocabulary = toUnwrappedModel(assetVocabulary);

		Session session = null;

		try {
			session = openSession();

			if (assetVocabulary.isCachedModel()) {
				assetVocabulary = (AssetVocabulary)session.get(AssetVocabularyImpl.class,
						assetVocabulary.getPrimaryKeyObj());
			}

			if (assetVocabulary != null) {
				session.delete(assetVocabulary);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (assetVocabulary != null) {
			clearCache(assetVocabulary);
		}

		return assetVocabulary;
	}

	@Override
	public AssetVocabulary updateImpl(
		com.liferay.portlet.asset.model.AssetVocabulary assetVocabulary)
		throws SystemException {
		assetVocabulary = toUnwrappedModel(assetVocabulary);

		boolean isNew = assetVocabulary.isNew();

		AssetVocabularyModelImpl assetVocabularyModelImpl = (AssetVocabularyModelImpl)assetVocabulary;

		if (Validator.isNull(assetVocabulary.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			assetVocabulary.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (assetVocabulary.isNew()) {
				session.save(assetVocabulary);

				assetVocabulary.setNew(false);
			}
			else {
				session.merge(assetVocabulary);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !AssetVocabularyModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((assetVocabularyModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetVocabularyModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { assetVocabularyModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((assetVocabularyModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetVocabularyModelImpl.getOriginalUuid(),
						Long.valueOf(assetVocabularyModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						assetVocabularyModelImpl.getUuid(),
						Long.valueOf(assetVocabularyModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((assetVocabularyModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetVocabularyModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(assetVocabularyModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((assetVocabularyModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetVocabularyModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(assetVocabularyModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		EntityCacheUtil.putResult(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
			AssetVocabularyImpl.class, assetVocabulary.getPrimaryKey(),
			assetVocabulary);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					assetVocabulary.getUuid(),
					Long.valueOf(assetVocabulary.getGroupId())
				}, assetVocabulary);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
				new Object[] {
					Long.valueOf(assetVocabulary.getGroupId()),
					
				assetVocabulary.getName()
				}, assetVocabulary);
		}
		else {
			if ((assetVocabularyModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetVocabularyModelImpl.getOriginalUuid(),
						Long.valueOf(assetVocabularyModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						assetVocabulary.getUuid(),
						Long.valueOf(assetVocabulary.getGroupId())
					}, assetVocabulary);
			}

			if ((assetVocabularyModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetVocabularyModelImpl.getOriginalGroupId()),
						
						assetVocabularyModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_N, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
					new Object[] {
						Long.valueOf(assetVocabulary.getGroupId()),
						
					assetVocabulary.getName()
					}, assetVocabulary);
			}
		}

		return assetVocabulary;
	}

	protected AssetVocabulary toUnwrappedModel(AssetVocabulary assetVocabulary) {
		if (assetVocabulary instanceof AssetVocabularyImpl) {
			return assetVocabulary;
		}

		AssetVocabularyImpl assetVocabularyImpl = new AssetVocabularyImpl();

		assetVocabularyImpl.setNew(assetVocabulary.isNew());
		assetVocabularyImpl.setPrimaryKey(assetVocabulary.getPrimaryKey());

		assetVocabularyImpl.setUuid(assetVocabulary.getUuid());
		assetVocabularyImpl.setVocabularyId(assetVocabulary.getVocabularyId());
		assetVocabularyImpl.setGroupId(assetVocabulary.getGroupId());
		assetVocabularyImpl.setCompanyId(assetVocabulary.getCompanyId());
		assetVocabularyImpl.setUserId(assetVocabulary.getUserId());
		assetVocabularyImpl.setUserName(assetVocabulary.getUserName());
		assetVocabularyImpl.setCreateDate(assetVocabulary.getCreateDate());
		assetVocabularyImpl.setModifiedDate(assetVocabulary.getModifiedDate());
		assetVocabularyImpl.setName(assetVocabulary.getName());
		assetVocabularyImpl.setTitle(assetVocabulary.getTitle());
		assetVocabularyImpl.setDescription(assetVocabulary.getDescription());
		assetVocabularyImpl.setSettings(assetVocabulary.getSettings());

		return assetVocabularyImpl;
	}

	/**
	 * Returns the asset vocabulary with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset vocabulary
	 * @return the asset vocabulary
	 * @throws com.liferay.portal.NoSuchModelException if a asset vocabulary with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetVocabulary findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the asset vocabulary with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchVocabularyException} if it could not be found.
	 *
	 * @param vocabularyId the primary key of the asset vocabulary
	 * @return the asset vocabulary
	 * @throws com.liferay.portlet.asset.NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary findByPrimaryKey(long vocabularyId)
		throws NoSuchVocabularyException, SystemException {
		AssetVocabulary assetVocabulary = fetchByPrimaryKey(vocabularyId);

		if (assetVocabulary == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + vocabularyId);
			}

			throw new NoSuchVocabularyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				vocabularyId);
		}

		return assetVocabulary;
	}

	/**
	 * Returns the asset vocabulary with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset vocabulary
	 * @return the asset vocabulary, or <code>null</code> if a asset vocabulary with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetVocabulary fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the asset vocabulary with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param vocabularyId the primary key of the asset vocabulary
	 * @return the asset vocabulary, or <code>null</code> if a asset vocabulary with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary fetchByPrimaryKey(long vocabularyId)
		throws SystemException {
		AssetVocabulary assetVocabulary = (AssetVocabulary)EntityCacheUtil.getResult(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
				AssetVocabularyImpl.class, vocabularyId);

		if (assetVocabulary == _nullAssetVocabulary) {
			return null;
		}

		if (assetVocabulary == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				assetVocabulary = (AssetVocabulary)session.get(AssetVocabularyImpl.class,
						Long.valueOf(vocabularyId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (assetVocabulary != null) {
					cacheResult(assetVocabulary);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(AssetVocabularyModelImpl.ENTITY_CACHE_ENABLED,
						AssetVocabularyImpl.class, vocabularyId,
						_nullAssetVocabulary);
				}

				closeSession(session);
			}
		}

		return assetVocabulary;
	}

	/**
	 * Returns all the asset vocabularies where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetVocabulary> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset vocabularies where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @return the range of matching asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetVocabulary> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset vocabularies where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetVocabulary> findByUuid(String uuid, int start, int end,
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

		List<AssetVocabulary> list = (List<AssetVocabulary>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetVocabulary assetVocabulary : list) {
				if (!Validator.equals(uuid, assetVocabulary.getUuid())) {
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

			query.append(_SQL_SELECT_ASSETVOCABULARY_WHERE);

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

			else {
				query.append(AssetVocabularyModelImpl.ORDER_BY_JPQL);
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

				list = (List<AssetVocabulary>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first asset vocabulary in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary
	 * @throws com.liferay.portlet.asset.NoSuchVocabularyException if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchVocabularyException, SystemException {
		AssetVocabulary assetVocabulary = fetchByUuid_First(uuid,
				orderByComparator);

		if (assetVocabulary != null) {
			return assetVocabulary;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchVocabularyException(msg.toString());
	}

	/**
	 * Returns the first asset vocabulary in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetVocabulary> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset vocabulary in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary
	 * @throws com.liferay.portlet.asset.NoSuchVocabularyException if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchVocabularyException, SystemException {
		AssetVocabulary assetVocabulary = fetchByUuid_Last(uuid,
				orderByComparator);

		if (assetVocabulary != null) {
			return assetVocabulary;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchVocabularyException(msg.toString());
	}

	/**
	 * Returns the last asset vocabulary in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<AssetVocabulary> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset vocabularies before and after the current asset vocabulary in the ordered set where uuid = &#63;.
	 *
	 * @param vocabularyId the primary key of the current asset vocabulary
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary
	 * @throws com.liferay.portlet.asset.NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary[] findByUuid_PrevAndNext(long vocabularyId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchVocabularyException, SystemException {
		AssetVocabulary assetVocabulary = findByPrimaryKey(vocabularyId);

		Session session = null;

		try {
			session = openSession();

			AssetVocabulary[] array = new AssetVocabularyImpl[3];

			array[0] = getByUuid_PrevAndNext(session, assetVocabulary, uuid,
					orderByComparator, true);

			array[1] = assetVocabulary;

			array[2] = getByUuid_PrevAndNext(session, assetVocabulary, uuid,
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

	protected AssetVocabulary getByUuid_PrevAndNext(Session session,
		AssetVocabulary assetVocabulary, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETVOCABULARY_WHERE);

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

		else {
			query.append(AssetVocabularyModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(assetVocabulary);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetVocabulary> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the asset vocabulary where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchVocabularyException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset vocabulary
	 * @throws com.liferay.portlet.asset.NoSuchVocabularyException if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary findByUUID_G(String uuid, long groupId)
		throws NoSuchVocabularyException, SystemException {
		AssetVocabulary assetVocabulary = fetchByUUID_G(uuid, groupId);

		if (assetVocabulary == null) {
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

			throw new NoSuchVocabularyException(msg.toString());
		}

		return assetVocabulary;
	}

	/**
	 * Returns the asset vocabulary where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the asset vocabulary where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof AssetVocabulary) {
			AssetVocabulary assetVocabulary = (AssetVocabulary)result;

			if (!Validator.equals(uuid, assetVocabulary.getUuid()) ||
					(groupId != assetVocabulary.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ASSETVOCABULARY_WHERE);

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

			query.append(AssetVocabularyModelImpl.ORDER_BY_JPQL);

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

				List<AssetVocabulary> list = q.list();

				result = list;

				AssetVocabulary assetVocabulary = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					assetVocabulary = list.get(0);

					cacheResult(assetVocabulary);

					if ((assetVocabulary.getUuid() == null) ||
							!assetVocabulary.getUuid().equals(uuid) ||
							(assetVocabulary.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, assetVocabulary);
					}
				}

				return assetVocabulary;
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
				return (AssetVocabulary)result;
			}
		}
	}

	/**
	 * Returns all the asset vocabularies where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetVocabulary> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset vocabularies where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @return the range of matching asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetVocabulary> findByUuid_C(String uuid, long companyId,
		int start, int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset vocabularies where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetVocabulary> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<AssetVocabulary> list = (List<AssetVocabulary>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetVocabulary assetVocabulary : list) {
				if (!Validator.equals(uuid, assetVocabulary.getUuid()) ||
						(companyId != assetVocabulary.getCompanyId())) {
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

			query.append(_SQL_SELECT_ASSETVOCABULARY_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_C_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_C_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetVocabularyModelImpl.ORDER_BY_JPQL);
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

				qPos.add(companyId);

				list = (List<AssetVocabulary>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first asset vocabulary in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary
	 * @throws com.liferay.portlet.asset.NoSuchVocabularyException if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchVocabularyException, SystemException {
		AssetVocabulary assetVocabulary = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (assetVocabulary != null) {
			return assetVocabulary;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchVocabularyException(msg.toString());
	}

	/**
	 * Returns the first asset vocabulary in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetVocabulary> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset vocabulary in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary
	 * @throws com.liferay.portlet.asset.NoSuchVocabularyException if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchVocabularyException, SystemException {
		AssetVocabulary assetVocabulary = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (assetVocabulary != null) {
			return assetVocabulary;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchVocabularyException(msg.toString());
	}

	/**
	 * Returns the last asset vocabulary in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<AssetVocabulary> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset vocabularies before and after the current asset vocabulary in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param vocabularyId the primary key of the current asset vocabulary
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary
	 * @throws com.liferay.portlet.asset.NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary[] findByUuid_C_PrevAndNext(long vocabularyId,
		String uuid, long companyId, OrderByComparator orderByComparator)
		throws NoSuchVocabularyException, SystemException {
		AssetVocabulary assetVocabulary = findByPrimaryKey(vocabularyId);

		Session session = null;

		try {
			session = openSession();

			AssetVocabulary[] array = new AssetVocabularyImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, assetVocabulary, uuid,
					companyId, orderByComparator, true);

			array[1] = assetVocabulary;

			array[2] = getByUuid_C_PrevAndNext(session, assetVocabulary, uuid,
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

	protected AssetVocabulary getByUuid_C_PrevAndNext(Session session,
		AssetVocabulary assetVocabulary, String uuid, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETVOCABULARY_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			query.append(AssetVocabularyModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetVocabulary);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetVocabulary> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset vocabularies where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetVocabulary> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset vocabularies where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @return the range of matching asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetVocabulary> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset vocabularies where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetVocabulary> findByGroupId(long groupId, int start,
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

		List<AssetVocabulary> list = (List<AssetVocabulary>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetVocabulary assetVocabulary : list) {
				if ((groupId != assetVocabulary.getGroupId())) {
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

			query.append(_SQL_SELECT_ASSETVOCABULARY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetVocabularyModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<AssetVocabulary>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first asset vocabulary in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary
	 * @throws com.liferay.portlet.asset.NoSuchVocabularyException if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchVocabularyException, SystemException {
		AssetVocabulary assetVocabulary = fetchByGroupId_First(groupId,
				orderByComparator);

		if (assetVocabulary != null) {
			return assetVocabulary;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchVocabularyException(msg.toString());
	}

	/**
	 * Returns the first asset vocabulary in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetVocabulary> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset vocabulary in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary
	 * @throws com.liferay.portlet.asset.NoSuchVocabularyException if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchVocabularyException, SystemException {
		AssetVocabulary assetVocabulary = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (assetVocabulary != null) {
			return assetVocabulary;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchVocabularyException(msg.toString());
	}

	/**
	 * Returns the last asset vocabulary in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<AssetVocabulary> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset vocabularies before and after the current asset vocabulary in the ordered set where groupId = &#63;.
	 *
	 * @param vocabularyId the primary key of the current asset vocabulary
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary
	 * @throws com.liferay.portlet.asset.NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary[] findByGroupId_PrevAndNext(long vocabularyId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchVocabularyException, SystemException {
		AssetVocabulary assetVocabulary = findByPrimaryKey(vocabularyId);

		Session session = null;

		try {
			session = openSession();

			AssetVocabulary[] array = new AssetVocabularyImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, assetVocabulary,
					groupId, orderByComparator, true);

			array[1] = assetVocabulary;

			array[2] = getByGroupId_PrevAndNext(session, assetVocabulary,
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

	protected AssetVocabulary getByGroupId_PrevAndNext(Session session,
		AssetVocabulary assetVocabulary, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETVOCABULARY_WHERE);

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
			query.append(AssetVocabularyModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetVocabulary);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetVocabulary> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset vocabularies that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset vocabularies that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetVocabulary> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset vocabularies that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @return the range of matching asset vocabularies that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetVocabulary> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset vocabularies that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabularies that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetVocabulary> filterFindByGroupId(long groupId, int start,
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
			query.append(_FILTER_SQL_SELECT_ASSETVOCABULARY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETVOCABULARY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETVOCABULARY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(AssetVocabularyModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetVocabularyModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetVocabulary.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, AssetVocabularyImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, AssetVocabularyImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<AssetVocabulary>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset vocabularies before and after the current asset vocabulary in the ordered set of asset vocabularies that the user has permission to view where groupId = &#63;.
	 *
	 * @param vocabularyId the primary key of the current asset vocabulary
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary
	 * @throws com.liferay.portlet.asset.NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary[] filterFindByGroupId_PrevAndNext(
		long vocabularyId, long groupId, OrderByComparator orderByComparator)
		throws NoSuchVocabularyException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(vocabularyId, groupId,
				orderByComparator);
		}

		AssetVocabulary assetVocabulary = findByPrimaryKey(vocabularyId);

		Session session = null;

		try {
			session = openSession();

			AssetVocabulary[] array = new AssetVocabularyImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, assetVocabulary,
					groupId, orderByComparator, true);

			array[1] = assetVocabulary;

			array[2] = filterGetByGroupId_PrevAndNext(session, assetVocabulary,
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

	protected AssetVocabulary filterGetByGroupId_PrevAndNext(Session session,
		AssetVocabulary assetVocabulary, long groupId,
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
			query.append(_FILTER_SQL_SELECT_ASSETVOCABULARY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETVOCABULARY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETVOCABULARY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(AssetVocabularyModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetVocabularyModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetVocabulary.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, AssetVocabularyImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, AssetVocabularyImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetVocabulary);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetVocabulary> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset vocabularies where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetVocabulary> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset vocabularies where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @return the range of matching asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetVocabulary> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset vocabularies where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetVocabulary> findByCompanyId(long companyId, int start,
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

		List<AssetVocabulary> list = (List<AssetVocabulary>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetVocabulary assetVocabulary : list) {
				if ((companyId != assetVocabulary.getCompanyId())) {
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

			query.append(_SQL_SELECT_ASSETVOCABULARY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetVocabularyModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<AssetVocabulary>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first asset vocabulary in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary
	 * @throws com.liferay.portlet.asset.NoSuchVocabularyException if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchVocabularyException, SystemException {
		AssetVocabulary assetVocabulary = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (assetVocabulary != null) {
			return assetVocabulary;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchVocabularyException(msg.toString());
	}

	/**
	 * Returns the first asset vocabulary in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetVocabulary> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset vocabulary in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary
	 * @throws com.liferay.portlet.asset.NoSuchVocabularyException if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchVocabularyException, SystemException {
		AssetVocabulary assetVocabulary = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (assetVocabulary != null) {
			return assetVocabulary;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchVocabularyException(msg.toString());
	}

	/**
	 * Returns the last asset vocabulary in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<AssetVocabulary> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset vocabularies before and after the current asset vocabulary in the ordered set where companyId = &#63;.
	 *
	 * @param vocabularyId the primary key of the current asset vocabulary
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary
	 * @throws com.liferay.portlet.asset.NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary[] findByCompanyId_PrevAndNext(long vocabularyId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchVocabularyException, SystemException {
		AssetVocabulary assetVocabulary = findByPrimaryKey(vocabularyId);

		Session session = null;

		try {
			session = openSession();

			AssetVocabulary[] array = new AssetVocabularyImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, assetVocabulary,
					companyId, orderByComparator, true);

			array[1] = assetVocabulary;

			array[2] = getByCompanyId_PrevAndNext(session, assetVocabulary,
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

	protected AssetVocabulary getByCompanyId_PrevAndNext(Session session,
		AssetVocabulary assetVocabulary, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETVOCABULARY_WHERE);

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
			query.append(AssetVocabularyModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetVocabulary);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetVocabulary> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the asset vocabulary where groupId = &#63; and name = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchVocabularyException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching asset vocabulary
	 * @throws com.liferay.portlet.asset.NoSuchVocabularyException if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary findByG_N(long groupId, String name)
		throws NoSuchVocabularyException, SystemException {
		AssetVocabulary assetVocabulary = fetchByG_N(groupId, name);

		if (assetVocabulary == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchVocabularyException(msg.toString());
		}

		return assetVocabulary;
	}

	/**
	 * Returns the asset vocabulary where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary fetchByG_N(long groupId, String name)
		throws SystemException {
		return fetchByG_N(groupId, name, true);
	}

	/**
	 * Returns the asset vocabulary where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary fetchByG_N(long groupId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_N,
					finderArgs, this);
		}

		if (result instanceof AssetVocabulary) {
			AssetVocabulary assetVocabulary = (AssetVocabulary)result;

			if ((groupId != assetVocabulary.getGroupId()) ||
					!Validator.equals(name, assetVocabulary.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ASSETVOCABULARY_WHERE);

			query.append(_FINDER_COLUMN_G_N_GROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_N_NAME_2);
				}
			}

			query.append(AssetVocabularyModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
				}

				List<AssetVocabulary> list = q.list();

				result = list;

				AssetVocabulary assetVocabulary = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
						finderArgs, list);
				}
				else {
					assetVocabulary = list.get(0);

					cacheResult(assetVocabulary);

					if ((assetVocabulary.getGroupId() != groupId) ||
							(assetVocabulary.getName() == null) ||
							!assetVocabulary.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
							finderArgs, assetVocabulary);
					}
				}

				return assetVocabulary;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N,
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
				return (AssetVocabulary)result;
			}
		}
	}

	/**
	 * Returns all the asset vocabularies.
	 *
	 * @return the asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetVocabulary> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset vocabularies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @return the range of asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetVocabulary> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset vocabularies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetVocabulary> findAll(int start, int end,
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

		List<AssetVocabulary> list = (List<AssetVocabulary>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ASSETVOCABULARY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETVOCABULARY.concat(AssetVocabularyModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<AssetVocabulary>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AssetVocabulary>)QueryUtil.list(q,
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
	 * Removes all the asset vocabularies where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (AssetVocabulary assetVocabulary : findByUuid(uuid)) {
			remove(assetVocabulary);
		}
	}

	/**
	 * Removes the asset vocabulary where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset vocabulary that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary removeByUUID_G(String uuid, long groupId)
		throws NoSuchVocabularyException, SystemException {
		AssetVocabulary assetVocabulary = findByUUID_G(uuid, groupId);

		return remove(assetVocabulary);
	}

	/**
	 * Removes all the asset vocabularies where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (AssetVocabulary assetVocabulary : findByUuid_C(uuid, companyId)) {
			remove(assetVocabulary);
		}
	}

	/**
	 * Removes all the asset vocabularies where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (AssetVocabulary assetVocabulary : findByGroupId(groupId)) {
			remove(assetVocabulary);
		}
	}

	/**
	 * Removes all the asset vocabularies where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (AssetVocabulary assetVocabulary : findByCompanyId(companyId)) {
			remove(assetVocabulary);
		}
	}

	/**
	 * Removes the asset vocabulary where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the asset vocabulary that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public AssetVocabulary removeByG_N(long groupId, String name)
		throws NoSuchVocabularyException, SystemException {
		AssetVocabulary assetVocabulary = findByG_N(groupId, name);

		return remove(assetVocabulary);
	}

	/**
	 * Removes all the asset vocabularies from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (AssetVocabulary assetVocabulary : findAll()) {
			remove(assetVocabulary);
		}
	}

	/**
	 * Returns the number of asset vocabularies where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETVOCABULARY_WHERE);

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
	 * Returns the number of asset vocabularies where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETVOCABULARY_WHERE);

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
	 * Returns the number of asset vocabularies where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETVOCABULARY_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_C_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_C_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset vocabularies where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETVOCABULARY_WHERE);

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
	 * Returns the number of asset vocabularies that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset vocabularies that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_ASSETVOCABULARY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetVocabulary.class.getName(),
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
	 * Returns the number of asset vocabularies where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETVOCABULARY_WHERE);

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
	 * Returns the number of asset vocabularies where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_N(long groupId, String name) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETVOCABULARY_WHERE);

			query.append(_FINDER_COLUMN_G_N_GROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_N, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset vocabularies.
	 *
	 * @return the number of asset vocabularies
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETVOCABULARY);

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
	 * Initializes the asset vocabulary persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.asset.model.AssetVocabulary")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AssetVocabulary>> listenersList = new ArrayList<ModelListener<AssetVocabulary>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AssetVocabulary>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(AssetVocabularyImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = AssetCategoryPersistence.class)
	protected AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(type = AssetCategoryPropertyPersistence.class)
	protected AssetCategoryPropertyPersistence assetCategoryPropertyPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = AssetLinkPersistence.class)
	protected AssetLinkPersistence assetLinkPersistence;
	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(type = AssetTagPropertyPersistence.class)
	protected AssetTagPropertyPersistence assetTagPropertyPersistence;
	@BeanReference(type = AssetTagStatsPersistence.class)
	protected AssetTagStatsPersistence assetTagStatsPersistence;
	@BeanReference(type = AssetVocabularyPersistence.class)
	protected AssetVocabularyPersistence assetVocabularyPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_ASSETVOCABULARY = "SELECT assetVocabulary FROM AssetVocabulary assetVocabulary";
	private static final String _SQL_SELECT_ASSETVOCABULARY_WHERE = "SELECT assetVocabulary FROM AssetVocabulary assetVocabulary WHERE ";
	private static final String _SQL_COUNT_ASSETVOCABULARY = "SELECT COUNT(assetVocabulary) FROM AssetVocabulary assetVocabulary";
	private static final String _SQL_COUNT_ASSETVOCABULARY_WHERE = "SELECT COUNT(assetVocabulary) FROM AssetVocabulary assetVocabulary WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "assetVocabulary.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "assetVocabulary.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(assetVocabulary.uuid IS NULL OR assetVocabulary.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "assetVocabulary.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "assetVocabulary.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(assetVocabulary.uuid IS NULL OR assetVocabulary.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "assetVocabulary.groupId = ?";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "assetVocabulary.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "assetVocabulary.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(assetVocabulary.uuid IS NULL OR assetVocabulary.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "assetVocabulary.companyId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "assetVocabulary.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "assetVocabulary.companyId = ?";
	private static final String _FINDER_COLUMN_G_N_GROUPID_2 = "assetVocabulary.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_NAME_1 = "assetVocabulary.name IS NULL";
	private static final String _FINDER_COLUMN_G_N_NAME_2 = "assetVocabulary.name = ?";
	private static final String _FINDER_COLUMN_G_N_NAME_3 = "(assetVocabulary.name IS NULL OR assetVocabulary.name = ?)";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "assetVocabulary.vocabularyId";
	private static final String _FILTER_SQL_SELECT_ASSETVOCABULARY_WHERE = "SELECT DISTINCT {assetVocabulary.*} FROM AssetVocabulary assetVocabulary WHERE ";
	private static final String _FILTER_SQL_SELECT_ASSETVOCABULARY_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {AssetVocabulary.*} FROM (SELECT DISTINCT assetVocabulary.vocabularyId FROM AssetVocabulary assetVocabulary WHERE ";
	private static final String _FILTER_SQL_SELECT_ASSETVOCABULARY_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN AssetVocabulary ON TEMP_TABLE.vocabularyId = AssetVocabulary.vocabularyId";
	private static final String _FILTER_SQL_COUNT_ASSETVOCABULARY_WHERE = "SELECT COUNT(DISTINCT assetVocabulary.vocabularyId) AS COUNT_VALUE FROM AssetVocabulary assetVocabulary WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "assetVocabulary";
	private static final String _FILTER_ENTITY_TABLE = "AssetVocabulary";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetVocabulary.";
	private static final String _ORDER_BY_ENTITY_TABLE = "AssetVocabulary.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetVocabulary exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetVocabulary exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(AssetVocabularyPersistenceImpl.class);
	private static AssetVocabulary _nullAssetVocabulary = new AssetVocabularyImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<AssetVocabulary> toCacheModel() {
				return _nullAssetVocabularyCacheModel;
			}
		};

	private static CacheModel<AssetVocabulary> _nullAssetVocabularyCacheModel = new CacheModel<AssetVocabulary>() {
			public AssetVocabulary toEntityModel() {
				return _nullAssetVocabulary;
			}
		};
}