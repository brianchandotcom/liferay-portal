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

package com.liferay.portlet.asset.service.persistence;

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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.NoSuchCategoryException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.impl.AssetCategoryImpl;
import com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the asset category service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryPersistence
 * @see AssetCategoryUtil
 * @generated
 */
public class AssetCategoryPersistenceImpl extends BasePersistenceImpl<AssetCategory>
	implements AssetCategoryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AssetCategoryUtil} to access the asset category persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AssetCategoryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_PARENTCATEGORYID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST,
			"findByParentCategoryId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_PARENTCATEGORYID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByParentCategoryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_VOCABULARYID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST,
			"findByVocabularyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_VOCABULARYID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByVocabularyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST, "findByG_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_V",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_P_N = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST, "findByP_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_P_N = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByP_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_P_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST, "findByP_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_P_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByP_V",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_N_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST, "findByN_V",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_N_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByN_V",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_P_N_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByP_N_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_P_N_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByP_N_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST, "findAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the asset category in the entity cache if it is enabled.
	 *
	 * @param assetCategory the asset category
	 */
	public void cacheResult(AssetCategory assetCategory) {
		EntityCacheUtil.putResult(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryImpl.class, assetCategory.getPrimaryKey(),
			assetCategory);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				assetCategory.getUuid(),
				Long.valueOf(assetCategory.getGroupId())
			}, assetCategory);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_N_V,
			new Object[] {
				Long.valueOf(assetCategory.getParentCategoryId()),
				
			assetCategory.getName(),
				Long.valueOf(assetCategory.getVocabularyId())
			}, assetCategory);

		assetCategory.resetOriginalValues();
	}

	/**
	 * Caches the asset categories in the entity cache if it is enabled.
	 *
	 * @param assetCategories the asset categories
	 */
	public void cacheResult(List<AssetCategory> assetCategories) {
		for (AssetCategory assetCategory : assetCategories) {
			if (EntityCacheUtil.getResult(
						AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
						AssetCategoryImpl.class, assetCategory.getPrimaryKey()) == null) {
				cacheResult(assetCategory);
			}
		}
	}

	/**
	 * Clears the cache for all asset categories.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(AssetCategoryImpl.class.getName());
		}

		EntityCacheUtil.clearCache(AssetCategoryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the asset category.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetCategory assetCategory) {
		EntityCacheUtil.removeResult(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryImpl.class, assetCategory.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL, FINDER_ARGS_EMPTY);

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				assetCategory.getUuid(),
				Long.valueOf(assetCategory.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_P_N_V,
			new Object[] {
				Long.valueOf(assetCategory.getParentCategoryId()),
				
			assetCategory.getName(),
				Long.valueOf(assetCategory.getVocabularyId())
			});
	}

	/**
	 * Creates a new asset category with the primary key. Does not add the asset category to the database.
	 *
	 * @param categoryId the primary key for the new asset category
	 * @return the new asset category
	 */
	public AssetCategory create(long categoryId) {
		AssetCategory assetCategory = new AssetCategoryImpl();

		assetCategory.setNew(true);
		assetCategory.setPrimaryKey(categoryId);

		String uuid = PortalUUIDUtil.generate();

		assetCategory.setUuid(uuid);

		return assetCategory;
	}

	/**
	 * Removes the asset category with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset category
	 * @return the asset category that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the asset category with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param categoryId the primary key of the asset category
	 * @return the asset category that was removed
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory remove(long categoryId)
		throws NoSuchCategoryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AssetCategory assetCategory = (AssetCategory)session.get(AssetCategoryImpl.class,
					Long.valueOf(categoryId));

			if (assetCategory == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + categoryId);
				}

				throw new NoSuchCategoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					categoryId);
			}

			return assetCategoryPersistence.remove(assetCategory);
		}
		catch (NoSuchCategoryException nsee) {
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
	 * Removes the asset category from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetCategory the asset category
	 * @return the asset category that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory remove(AssetCategory assetCategory)
		throws SystemException {
		return super.remove(assetCategory);
	}

	@Override
	protected AssetCategory removeImpl(AssetCategory assetCategory)
		throws SystemException {
		assetCategory = toUnwrappedModel(assetCategory);

		try {
			clearAssetEntries.clear(assetCategory.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetCategoryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}

		shrinkTree(assetCategory);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, assetCategory);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		AssetCategoryModelImpl assetCategoryModelImpl = (AssetCategoryModelImpl)assetCategory;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				assetCategoryModelImpl.getUuid(),
				Long.valueOf(assetCategoryModelImpl.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_P_N_V,
			new Object[] {
				Long.valueOf(assetCategoryModelImpl.getParentCategoryId()),
				
			assetCategoryModelImpl.getName(),
				Long.valueOf(assetCategoryModelImpl.getVocabularyId())
			});

		EntityCacheUtil.removeResult(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryImpl.class, assetCategory.getPrimaryKey());

		return assetCategory;
	}

	@Override
	public AssetCategory updateImpl(
		com.liferay.portlet.asset.model.AssetCategory assetCategory,
		boolean merge) throws SystemException {
		assetCategory = toUnwrappedModel(assetCategory);

		boolean isNew = assetCategory.isNew();

		AssetCategoryModelImpl assetCategoryModelImpl = (AssetCategoryModelImpl)assetCategory;

		if (Validator.isNull(assetCategory.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			assetCategory.setUuid(uuid);
		}

		if (isNew) {
			expandTree(assetCategory);
		}
		else {
			if (assetCategory.getParentCategoryId() != assetCategoryModelImpl.getOriginalParentCategoryId()) {
				shrinkTree(assetCategory);
				expandTree(assetCategory);
			}
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, assetCategory, merge);

			assetCategory.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryImpl.class, assetCategory.getPrimaryKey(),
			assetCategory);

		if (!isNew &&
				(!Validator.equals(assetCategory.getUuid(),
					assetCategoryModelImpl.getOriginalUuid()) ||
				(assetCategory.getGroupId() != assetCategoryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					assetCategoryModelImpl.getOriginalUuid(),
					Long.valueOf(assetCategoryModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(assetCategory.getUuid(),
					assetCategoryModelImpl.getOriginalUuid()) ||
				(assetCategory.getGroupId() != assetCategoryModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					assetCategory.getUuid(),
					Long.valueOf(assetCategory.getGroupId())
				}, assetCategory);
		}

		if (!isNew &&
				((assetCategory.getParentCategoryId() != assetCategoryModelImpl.getOriginalParentCategoryId()) ||
				!Validator.equals(assetCategory.getName(),
					assetCategoryModelImpl.getOriginalName()) ||
				(assetCategory.getVocabularyId() != assetCategoryModelImpl.getOriginalVocabularyId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_P_N_V,
				new Object[] {
					Long.valueOf(
						assetCategoryModelImpl.getOriginalParentCategoryId()),
					
				assetCategoryModelImpl.getOriginalName(),
					Long.valueOf(
						assetCategoryModelImpl.getOriginalVocabularyId())
				});
		}

		if (isNew ||
				((assetCategory.getParentCategoryId() != assetCategoryModelImpl.getOriginalParentCategoryId()) ||
				!Validator.equals(assetCategory.getName(),
					assetCategoryModelImpl.getOriginalName()) ||
				(assetCategory.getVocabularyId() != assetCategoryModelImpl.getOriginalVocabularyId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_N_V,
				new Object[] {
					Long.valueOf(assetCategory.getParentCategoryId()),
					
				assetCategory.getName(),
					Long.valueOf(assetCategory.getVocabularyId())
				}, assetCategory);
		}

		return assetCategory;
	}

	protected AssetCategory toUnwrappedModel(AssetCategory assetCategory) {
		if (assetCategory instanceof AssetCategoryImpl) {
			return assetCategory;
		}

		AssetCategoryImpl assetCategoryImpl = new AssetCategoryImpl();

		assetCategoryImpl.setNew(assetCategory.isNew());
		assetCategoryImpl.setPrimaryKey(assetCategory.getPrimaryKey());

		assetCategoryImpl.setUuid(assetCategory.getUuid());
		assetCategoryImpl.setCategoryId(assetCategory.getCategoryId());
		assetCategoryImpl.setGroupId(assetCategory.getGroupId());
		assetCategoryImpl.setCompanyId(assetCategory.getCompanyId());
		assetCategoryImpl.setUserId(assetCategory.getUserId());
		assetCategoryImpl.setUserName(assetCategory.getUserName());
		assetCategoryImpl.setCreateDate(assetCategory.getCreateDate());
		assetCategoryImpl.setModifiedDate(assetCategory.getModifiedDate());
		assetCategoryImpl.setParentCategoryId(assetCategory.getParentCategoryId());
		assetCategoryImpl.setLeftCategoryId(assetCategory.getLeftCategoryId());
		assetCategoryImpl.setRightCategoryId(assetCategory.getRightCategoryId());
		assetCategoryImpl.setName(assetCategory.getName());
		assetCategoryImpl.setTitle(assetCategory.getTitle());
		assetCategoryImpl.setDescription(assetCategory.getDescription());
		assetCategoryImpl.setVocabularyId(assetCategory.getVocabularyId());

		return assetCategoryImpl;
	}

	/**
	 * Returns the asset category with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset category
	 * @return the asset category
	 * @throws com.liferay.portal.NoSuchModelException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the asset category with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchCategoryException} if it could not be found.
	 *
	 * @param categoryId the primary key of the asset category
	 * @return the asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory findByPrimaryKey(long categoryId)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByPrimaryKey(categoryId);

		if (assetCategory == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + categoryId);
			}

			throw new NoSuchCategoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				categoryId);
		}

		return assetCategory;
	}

	/**
	 * Returns the asset category with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset category
	 * @return the asset category, or <code>null</code> if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the asset category with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param categoryId the primary key of the asset category
	 * @return the asset category, or <code>null</code> if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory fetchByPrimaryKey(long categoryId)
		throws SystemException {
		AssetCategory assetCategory = (AssetCategory)EntityCacheUtil.getResult(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
				AssetCategoryImpl.class, categoryId);

		if (assetCategory == _nullAssetCategory) {
			return null;
		}

		if (assetCategory == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				assetCategory = (AssetCategory)session.get(AssetCategoryImpl.class,
						Long.valueOf(categoryId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (assetCategory != null) {
					cacheResult(assetCategory);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
						AssetCategoryImpl.class, categoryId, _nullAssetCategory);
				}

				closeSession(session);
			}
		}

		return assetCategory;
	}

	/**
	 * Returns all the asset categories where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, start, end, orderByComparator };

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

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
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
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

				list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_UUID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset category in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		List<AssetCategory> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last asset category in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		int count = countByUuid(uuid);

		List<AssetCategory> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory[] findByUuid_PrevAndNext(long categoryId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, assetCategory, uuid,
					orderByComparator, true);

			array[1] = assetCategory;

			array[2] = getByUuid_PrevAndNext(session, assetCategory, uuid,
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

	protected AssetCategory getByUuid_PrevAndNext(Session session,
		AssetCategory assetCategory, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

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

		else {
			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the asset category where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchCategoryException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory findByUUID_G(String uuid, long groupId)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByUUID_G(uuid, groupId);

		if (assetCategory == null) {
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

			throw new NoSuchCategoryException(msg.toString());
		}

		return assetCategory;
	}

	/**
	 * Returns the asset category where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the asset category where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

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

			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);

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

				List<AssetCategory> list = q.list();

				result = list;

				AssetCategory assetCategory = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					assetCategory = list.get(0);

					cacheResult(assetCategory);

					if ((assetCategory.getUuid() == null) ||
							!assetCategory.getUuid().equals(uuid) ||
							(assetCategory.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, assetCategory);
					}
				}

				return assetCategory;
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
				return (AssetCategory)result;
			}
		}
	}

	/**
	 * Returns all the asset categories where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				start, end, orderByComparator
			};

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_GROUPID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset category in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		List<AssetCategory> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last asset category in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		int count = countByGroupId(groupId);

		List<AssetCategory> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory[] findByGroupId_PrevAndNext(long categoryId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, assetCategory,
					groupId, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = getByGroupId_PrevAndNext(session, assetCategory,
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

	protected AssetCategory getByGroupId_PrevAndNext(Session session,
		AssetCategory assetCategory, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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

		else {
			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset categories that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> filterFindByGroupId(long groupId, int start,
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
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, AssetCategoryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, AssetCategoryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<AssetCategory>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the asset categories before and after the current asset category in the ordered set of asset categories that the user has permission to view where groupId = &#63;.
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory[] filterFindByGroupId_PrevAndNext(long categoryId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(categoryId, groupId,
				orderByComparator);
		}

		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, assetCategory,
					groupId, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = filterGetByGroupId_PrevAndNext(session, assetCategory,
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

	protected AssetCategory filterGetByGroupId_PrevAndNext(Session session,
		AssetCategory assetCategory, long groupId,
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
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

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
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, AssetCategoryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, AssetCategoryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset categories where parentCategoryId = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByParentCategoryId(long parentCategoryId)
		throws SystemException {
		return findByParentCategoryId(parentCategoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where parentCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param parentCategoryId the parent category ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByParentCategoryId(long parentCategoryId,
		int start, int end) throws SystemException {
		return findByParentCategoryId(parentCategoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories where parentCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param parentCategoryId the parent category ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByParentCategoryId(long parentCategoryId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				parentCategoryId,
				
				start, end, orderByComparator
			};

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_PARENTCATEGORYID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_PARENTCATEGORYID_PARENTCATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_PARENTCATEGORYID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_PARENTCATEGORYID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset category in the ordered set where parentCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param parentCategoryId the parent category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory findByParentCategoryId_First(long parentCategoryId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		List<AssetCategory> list = findByParentCategoryId(parentCategoryId, 0,
				1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("parentCategoryId=");
			msg.append(parentCategoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last asset category in the ordered set where parentCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param parentCategoryId the parent category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory findByParentCategoryId_Last(long parentCategoryId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		int count = countByParentCategoryId(parentCategoryId);

		List<AssetCategory> list = findByParentCategoryId(parentCategoryId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("parentCategoryId=");
			msg.append(parentCategoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set where parentCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param parentCategoryId the parent category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory[] findByParentCategoryId_PrevAndNext(long categoryId,
		long parentCategoryId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = getByParentCategoryId_PrevAndNext(session,
					assetCategory, parentCategoryId, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = getByParentCategoryId_PrevAndNext(session,
					assetCategory, parentCategoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategory getByParentCategoryId_PrevAndNext(Session session,
		AssetCategory assetCategory, long parentCategoryId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_PARENTCATEGORYID_PARENTCATEGORYID_2);

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

		else {
			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(parentCategoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset categories where vocabularyId = &#63;.
	 *
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByVocabularyId(long vocabularyId)
		throws SystemException {
		return findByVocabularyId(vocabularyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByVocabularyId(long vocabularyId, int start,
		int end) throws SystemException {
		return findByVocabularyId(vocabularyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories where vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByVocabularyId(long vocabularyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				vocabularyId,
				
				start, end, orderByComparator
			};

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_VOCABULARYID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_VOCABULARYID_VOCABULARYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(vocabularyId);

				list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_VOCABULARYID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_VOCABULARYID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset category in the ordered set where vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory findByVocabularyId_First(long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		List<AssetCategory> list = findByVocabularyId(vocabularyId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("vocabularyId=");
			msg.append(vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last asset category in the ordered set where vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory findByVocabularyId_Last(long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		int count = countByVocabularyId(vocabularyId);

		List<AssetCategory> list = findByVocabularyId(vocabularyId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("vocabularyId=");
			msg.append(vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set where vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory[] findByVocabularyId_PrevAndNext(long categoryId,
		long vocabularyId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = getByVocabularyId_PrevAndNext(session, assetCategory,
					vocabularyId, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = getByVocabularyId_PrevAndNext(session, assetCategory,
					vocabularyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategory getByVocabularyId_PrevAndNext(Session session,
		AssetCategory assetCategory, long vocabularyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_VOCABULARYID_VOCABULARYID_2);

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

		else {
			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(vocabularyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset categories where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByG_V(long groupId, long vocabularyId)
		throws SystemException {
		return findByG_V(groupId, vocabularyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByG_V(long groupId, long vocabularyId,
		int start, int end) throws SystemException {
		return findByG_V(groupId, vocabularyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByG_V(long groupId, long vocabularyId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, vocabularyId,
				
				start, end, orderByComparator
			};

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_V,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_G_V_GROUPID_2);

			query.append(_FINDER_COLUMN_G_V_VOCABULARYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(vocabularyId);

				list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_V,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_V,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset category in the ordered set where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory findByG_V_First(long groupId, long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		List<AssetCategory> list = findByG_V(groupId, vocabularyId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", vocabularyId=");
			msg.append(vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last asset category in the ordered set where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory findByG_V_Last(long groupId, long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		int count = countByG_V(groupId, vocabularyId);

		List<AssetCategory> list = findByG_V(groupId, vocabularyId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", vocabularyId=");
			msg.append(vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory[] findByG_V_PrevAndNext(long categoryId, long groupId,
		long vocabularyId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = getByG_V_PrevAndNext(session, assetCategory, groupId,
					vocabularyId, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = getByG_V_PrevAndNext(session, assetCategory, groupId,
					vocabularyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategory getByG_V_PrevAndNext(Session session,
		AssetCategory assetCategory, long groupId, long vocabularyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_G_V_GROUPID_2);

		query.append(_FINDER_COLUMN_G_V_VOCABULARYID_2);

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

		else {
			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(vocabularyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset categories that the user has permission to view where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> filterFindByG_V(long groupId, long vocabularyId)
		throws SystemException {
		return filterFindByG_V(groupId, vocabularyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories that the user has permission to view where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> filterFindByG_V(long groupId, long vocabularyId,
		int start, int end) throws SystemException {
		return filterFindByG_V(groupId, vocabularyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories that the user has permissions to view where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> filterFindByG_V(long groupId, long vocabularyId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_V(groupId, vocabularyId, start, end,
				orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_V_GROUPID_2);

		query.append(_FINDER_COLUMN_G_V_VOCABULARYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, AssetCategoryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, AssetCategoryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(vocabularyId);

			return (List<AssetCategory>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the asset categories before and after the current asset category in the ordered set of asset categories that the user has permission to view where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory[] filterFindByG_V_PrevAndNext(long categoryId,
		long groupId, long vocabularyId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_V_PrevAndNext(categoryId, groupId, vocabularyId,
				orderByComparator);
		}

		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = filterGetByG_V_PrevAndNext(session, assetCategory,
					groupId, vocabularyId, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = filterGetByG_V_PrevAndNext(session, assetCategory,
					groupId, vocabularyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategory filterGetByG_V_PrevAndNext(Session session,
		AssetCategory assetCategory, long groupId, long vocabularyId,
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
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_V_GROUPID_2);

		query.append(_FINDER_COLUMN_G_V_VOCABULARYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

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
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, AssetCategoryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, AssetCategoryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(vocabularyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset categories where parentCategoryId = &#63; and name = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByP_N(long parentCategoryId, String name)
		throws SystemException {
		return findByP_N(parentCategoryId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where parentCategoryId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByP_N(long parentCategoryId, String name,
		int start, int end) throws SystemException {
		return findByP_N(parentCategoryId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories where parentCategoryId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByP_N(long parentCategoryId, String name,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				parentCategoryId, name,
				
				start, end, orderByComparator
			};

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_P_N,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_P_N_PARENTCATEGORYID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_P_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_P_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_P_N_NAME_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				if (name != null) {
					qPos.add(name);
				}

				list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_P_N,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_P_N,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset category in the ordered set where parentCategoryId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory findByP_N_First(long parentCategoryId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		List<AssetCategory> list = findByP_N(parentCategoryId, name, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("parentCategoryId=");
			msg.append(parentCategoryId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last asset category in the ordered set where parentCategoryId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory findByP_N_Last(long parentCategoryId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		int count = countByP_N(parentCategoryId, name);

		List<AssetCategory> list = findByP_N(parentCategoryId, name, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("parentCategoryId=");
			msg.append(parentCategoryId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set where parentCategoryId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory[] findByP_N_PrevAndNext(long categoryId,
		long parentCategoryId, String name, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = getByP_N_PrevAndNext(session, assetCategory,
					parentCategoryId, name, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = getByP_N_PrevAndNext(session, assetCategory,
					parentCategoryId, name, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategory getByP_N_PrevAndNext(Session session,
		AssetCategory assetCategory, long parentCategoryId, String name,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_P_N_PARENTCATEGORYID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_P_N_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_P_N_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_P_N_NAME_2);
			}
		}

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

		else {
			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(parentCategoryId);

		if (name != null) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset categories where parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByP_V(long parentCategoryId,
		long vocabularyId) throws SystemException {
		return findByP_V(parentCategoryId, vocabularyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByP_V(long parentCategoryId,
		long vocabularyId, int start, int end) throws SystemException {
		return findByP_V(parentCategoryId, vocabularyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories where parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByP_V(long parentCategoryId,
		long vocabularyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				parentCategoryId, vocabularyId,
				
				start, end, orderByComparator
			};

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_P_V,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_P_V_PARENTCATEGORYID_2);

			query.append(_FINDER_COLUMN_P_V_VOCABULARYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				qPos.add(vocabularyId);

				list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_P_V,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_P_V,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset category in the ordered set where parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory findByP_V_First(long parentCategoryId,
		long vocabularyId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		List<AssetCategory> list = findByP_V(parentCategoryId, vocabularyId, 0,
				1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("parentCategoryId=");
			msg.append(parentCategoryId);

			msg.append(", vocabularyId=");
			msg.append(vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last asset category in the ordered set where parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory findByP_V_Last(long parentCategoryId,
		long vocabularyId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		int count = countByP_V(parentCategoryId, vocabularyId);

		List<AssetCategory> list = findByP_V(parentCategoryId, vocabularyId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("parentCategoryId=");
			msg.append(parentCategoryId);

			msg.append(", vocabularyId=");
			msg.append(vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set where parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory[] findByP_V_PrevAndNext(long categoryId,
		long parentCategoryId, long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = getByP_V_PrevAndNext(session, assetCategory,
					parentCategoryId, vocabularyId, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = getByP_V_PrevAndNext(session, assetCategory,
					parentCategoryId, vocabularyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategory getByP_V_PrevAndNext(Session session,
		AssetCategory assetCategory, long parentCategoryId, long vocabularyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_P_V_PARENTCATEGORYID_2);

		query.append(_FINDER_COLUMN_P_V_VOCABULARYID_2);

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

		else {
			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(parentCategoryId);

		qPos.add(vocabularyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset categories where name = &#63; and vocabularyId = &#63;.
	 *
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByN_V(String name, long vocabularyId)
		throws SystemException {
		return findByN_V(name, vocabularyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where name = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByN_V(String name, long vocabularyId,
		int start, int end) throws SystemException {
		return findByN_V(name, vocabularyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories where name = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findByN_V(String name, long vocabularyId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				name, vocabularyId,
				
				start, end, orderByComparator
			};

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_N_V,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

			if (name == null) {
				query.append(_FINDER_COLUMN_N_V_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_N_V_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_N_V_NAME_2);
				}
			}

			query.append(_FINDER_COLUMN_N_V_VOCABULARYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(vocabularyId);

				list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_N_V,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_N_V,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset category in the ordered set where name = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory findByN_V_First(String name, long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		List<AssetCategory> list = findByN_V(name, vocabularyId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("name=");
			msg.append(name);

			msg.append(", vocabularyId=");
			msg.append(vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last asset category in the ordered set where name = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory findByN_V_Last(String name, long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		int count = countByN_V(name, vocabularyId);

		List<AssetCategory> list = findByN_V(name, vocabularyId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("name=");
			msg.append(name);

			msg.append(", vocabularyId=");
			msg.append(vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set where name = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory[] findByN_V_PrevAndNext(long categoryId, String name,
		long vocabularyId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = getByN_V_PrevAndNext(session, assetCategory, name,
					vocabularyId, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = getByN_V_PrevAndNext(session, assetCategory, name,
					vocabularyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategory getByN_V_PrevAndNext(Session session,
		AssetCategory assetCategory, String name, long vocabularyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

		if (name == null) {
			query.append(_FINDER_COLUMN_N_V_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_V_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_N_V_NAME_2);
			}
		}

		query.append(_FINDER_COLUMN_N_V_VOCABULARYID_2);

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

		else {
			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (name != null) {
			qPos.add(name);
		}

		qPos.add(vocabularyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the asset category where parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchCategoryException} if it could not be found.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory findByP_N_V(long parentCategoryId, String name,
		long vocabularyId) throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByP_N_V(parentCategoryId, name,
				vocabularyId);

		if (assetCategory == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("parentCategoryId=");
			msg.append(parentCategoryId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", vocabularyId=");
			msg.append(vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCategoryException(msg.toString());
		}

		return assetCategory;
	}

	/**
	 * Returns the asset category where parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory fetchByP_N_V(long parentCategoryId, String name,
		long vocabularyId) throws SystemException {
		return fetchByP_N_V(parentCategoryId, name, vocabularyId, true);
	}

	/**
	 * Returns the asset category where parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategory fetchByP_N_V(long parentCategoryId, String name,
		long vocabularyId, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { parentCategoryId, name, vocabularyId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_P_N_V,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_P_N_V_PARENTCATEGORYID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_P_N_V_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_P_N_V_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_P_N_V_NAME_2);
				}
			}

			query.append(_FINDER_COLUMN_P_N_V_VOCABULARYID_2);

			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(vocabularyId);

				List<AssetCategory> list = q.list();

				result = list;

				AssetCategory assetCategory = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_N_V,
						finderArgs, list);
				}
				else {
					assetCategory = list.get(0);

					cacheResult(assetCategory);

					if ((assetCategory.getParentCategoryId() != parentCategoryId) ||
							(assetCategory.getName() == null) ||
							!assetCategory.getName().equals(name) ||
							(assetCategory.getVocabularyId() != vocabularyId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_N_V,
							finderArgs, assetCategory);
					}
				}

				return assetCategory;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_P_N_V,
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
				return (AssetCategory)result;
			}
		}
	}

	/**
	 * Returns all the asset categories.
	 *
	 * @return the asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategory> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ASSETCATEGORY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETCATEGORY.concat(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the asset categories where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (AssetCategory assetCategory : findByUuid(uuid)) {
			assetCategoryPersistence.remove(assetCategory);
		}
	}

	/**
	 * Removes the asset category where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByUUID_G(uuid, groupId);

		assetCategoryPersistence.remove(assetCategory);
	}

	/**
	 * Removes all the asset categories where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (AssetCategory assetCategory : findByGroupId(groupId)) {
			assetCategoryPersistence.remove(assetCategory);
		}
	}

	/**
	 * Removes all the asset categories where parentCategoryId = &#63; from the database.
	 *
	 * @param parentCategoryId the parent category ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByParentCategoryId(long parentCategoryId)
		throws SystemException {
		for (AssetCategory assetCategory : findByParentCategoryId(
				parentCategoryId)) {
			assetCategoryPersistence.remove(assetCategory);
		}
	}

	/**
	 * Removes all the asset categories where vocabularyId = &#63; from the database.
	 *
	 * @param vocabularyId the vocabulary ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByVocabularyId(long vocabularyId)
		throws SystemException {
		for (AssetCategory assetCategory : findByVocabularyId(vocabularyId)) {
			assetCategoryPersistence.remove(assetCategory);
		}
	}

	/**
	 * Removes all the asset categories where groupId = &#63; and vocabularyId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_V(long groupId, long vocabularyId)
		throws SystemException {
		for (AssetCategory assetCategory : findByG_V(groupId, vocabularyId)) {
			assetCategoryPersistence.remove(assetCategory);
		}
	}

	/**
	 * Removes all the asset categories where parentCategoryId = &#63; and name = &#63; from the database.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByP_N(long parentCategoryId, String name)
		throws SystemException {
		for (AssetCategory assetCategory : findByP_N(parentCategoryId, name)) {
			assetCategoryPersistence.remove(assetCategory);
		}
	}

	/**
	 * Removes all the asset categories where parentCategoryId = &#63; and vocabularyId = &#63; from the database.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByP_V(long parentCategoryId, long vocabularyId)
		throws SystemException {
		for (AssetCategory assetCategory : findByP_V(parentCategoryId,
				vocabularyId)) {
			assetCategoryPersistence.remove(assetCategory);
		}
	}

	/**
	 * Removes all the asset categories where name = &#63; and vocabularyId = &#63; from the database.
	 *
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByN_V(String name, long vocabularyId)
		throws SystemException {
		for (AssetCategory assetCategory : findByN_V(name, vocabularyId)) {
			assetCategoryPersistence.remove(assetCategory);
		}
	}

	/**
	 * Removes the asset category where parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63; from the database.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByP_N_V(long parentCategoryId, String name,
		long vocabularyId) throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByP_N_V(parentCategoryId, name,
				vocabularyId);

		assetCategoryPersistence.remove(assetCategory);
	}

	/**
	 * Removes all the asset categories from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (AssetCategory assetCategory : findAll()) {
			assetCategoryPersistence.remove(assetCategory);
		}
	}

	/**
	 * Returns the number of asset categories where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

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
	 * Returns the number of asset categories where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

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
	 * Returns the number of asset categories where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

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
	 * Returns the number of asset categories that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
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
	 * Returns the number of asset categories where parentCategoryId = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public int countByParentCategoryId(long parentCategoryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { parentCategoryId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PARENTCATEGORYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_PARENTCATEGORYID_PARENTCATEGORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PARENTCATEGORYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset categories where vocabularyId = &#63;.
	 *
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public int countByVocabularyId(long vocabularyId) throws SystemException {
		Object[] finderArgs = new Object[] { vocabularyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_VOCABULARYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_VOCABULARYID_VOCABULARYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(vocabularyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_VOCABULARYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset categories where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_V(long groupId, long vocabularyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, vocabularyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_V,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_G_V_GROUPID_2);

			query.append(_FINDER_COLUMN_G_V_VOCABULARYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(vocabularyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_V, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset categories that the user has permission to view where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_V(long groupId, long vocabularyId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_V(groupId, vocabularyId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_G_V_GROUPID_2);

		query.append(_FINDER_COLUMN_G_V_VOCABULARYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(vocabularyId);

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
	 * Returns the number of asset categories where parentCategoryId = &#63; and name = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public int countByP_N(long parentCategoryId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { parentCategoryId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_P_N_PARENTCATEGORYID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_P_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_P_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_P_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_N, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset categories where parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public int countByP_V(long parentCategoryId, long vocabularyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { parentCategoryId, vocabularyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_V,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_P_V_PARENTCATEGORYID_2);

			query.append(_FINDER_COLUMN_P_V_VOCABULARYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				qPos.add(vocabularyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_V, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset categories where name = &#63; and vocabularyId = &#63;.
	 *
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public int countByN_V(String name, long vocabularyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { name, vocabularyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_N_V,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

			if (name == null) {
				query.append(_FINDER_COLUMN_N_V_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_N_V_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_N_V_NAME_2);
				}
			}

			query.append(_FINDER_COLUMN_N_V_VOCABULARYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(vocabularyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_N_V, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset categories where parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public int countByP_N_V(long parentCategoryId, String name,
		long vocabularyId) throws SystemException {
		Object[] finderArgs = new Object[] { parentCategoryId, name, vocabularyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_N_V,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_P_N_V_PARENTCATEGORYID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_P_N_V_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_P_N_V_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_P_N_V_NAME_2);
				}
			}

			query.append(_FINDER_COLUMN_P_N_V_VOCABULARYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(vocabularyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_N_V,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset categories.
	 *
	 * @return the number of asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETCATEGORY);

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
	 * Returns all the asset entries associated with the asset category.
	 *
	 * @param pk the primary key of the asset category
	 * @return the asset entries associated with the asset category
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk) throws SystemException {
		return getAssetEntries(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the asset entries associated with the asset category.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the asset category
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of asset entries associated with the asset category
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end) throws SystemException {
		return getAssetEntries(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_ASSETENTRIES = new FinderPath(com.liferay.portlet.asset.model.impl.AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETCATEGORIES,
			com.liferay.portlet.asset.model.impl.AssetEntryImpl.class,
			AssetCategoryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME,
			"getAssetEntries",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	/**
	 * Returns an ordered range of all the asset entries associated with the asset category.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the asset category
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset entries associated with the asset category
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portlet.asset.model.AssetEntry> list = (List<com.liferay.portlet.asset.model.AssetEntry>)FinderCacheUtil.getResult(FINDER_PATH_GET_ASSETENTRIES,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETASSETENTRIES.concat(ORDER_BY_CLAUSE)
											  .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETASSETENTRIES;
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("AssetEntry",
					com.liferay.portlet.asset.model.impl.AssetEntryImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portlet.asset.model.AssetEntry>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_ASSETENTRIES,
						finderArgs);
				}
				else {
					assetEntryPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_ASSETENTRIES,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_ASSETENTRIES_SIZE = new FinderPath(com.liferay.portlet.asset.model.impl.AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETCATEGORIES,
			Long.class,
			AssetCategoryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME,
			"getAssetEntriesSize", new String[] { Long.class.getName() });

	/**
	 * Returns the number of asset entries associated with the asset category.
	 *
	 * @param pk the primary key of the asset category
	 * @return the number of asset entries associated with the asset category
	 * @throws SystemException if a system exception occurred
	 */
	public int getAssetEntriesSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_ASSETENTRIES_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETASSETENTRIESSIZE);

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

				FinderCacheUtil.putResult(FINDER_PATH_GET_ASSETENTRIES_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_ASSETENTRY = new FinderPath(com.liferay.portlet.asset.model.impl.AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETCATEGORIES,
			Boolean.class,
			AssetCategoryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME,
			"containsAssetEntry",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the asset entry is associated with the asset category.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntryPK the primary key of the asset entry
	 * @return <code>true</code> if the asset entry is associated with the asset category; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsAssetEntry(long pk, long assetEntryPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, assetEntryPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_ASSETENTRY,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsAssetEntry.contains(pk,
							assetEntryPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_ASSETENTRY,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the asset category has any asset entries associated with it.
	 *
	 * @param pk the primary key of the asset category to check for associations with asset entries
	 * @return <code>true</code> if the asset category has any asset entries associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsAssetEntries(long pk) throws SystemException {
		if (getAssetEntriesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the asset category and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntryPK the primary key of the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	public void addAssetEntry(long pk, long assetEntryPK)
		throws SystemException {
		try {
			addAssetEntry.add(pk, assetEntryPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetCategoryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Adds an association between the asset category and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntry the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	public void addAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws SystemException {
		try {
			addAssetEntry.add(pk, assetEntry.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetCategoryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Adds an association between the asset category and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntryPKs the primary keys of the asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public void addAssetEntries(long pk, long[] assetEntryPKs)
		throws SystemException {
		try {
			for (long assetEntryPK : assetEntryPKs) {
				addAssetEntry.add(pk, assetEntryPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetCategoryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Adds an association between the asset category and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntries the asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public void addAssetEntries(long pk,
		List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws SystemException {
		try {
			for (com.liferay.portlet.asset.model.AssetEntry assetEntry : assetEntries) {
				addAssetEntry.add(pk, assetEntry.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetCategoryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Clears all associations between the asset category and its asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category to clear the associated asset entries from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearAssetEntries(long pk) throws SystemException {
		try {
			clearAssetEntries.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetCategoryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Removes the association between the asset category and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntryPK the primary key of the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAssetEntry(long pk, long assetEntryPK)
		throws SystemException {
		try {
			removeAssetEntry.remove(pk, assetEntryPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetCategoryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Removes the association between the asset category and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntry the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws SystemException {
		try {
			removeAssetEntry.remove(pk, assetEntry.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetCategoryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Removes the association between the asset category and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntryPKs the primary keys of the asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAssetEntries(long pk, long[] assetEntryPKs)
		throws SystemException {
		try {
			for (long assetEntryPK : assetEntryPKs) {
				removeAssetEntry.remove(pk, assetEntryPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetCategoryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Removes the association between the asset category and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntries the asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAssetEntries(long pk,
		List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws SystemException {
		try {
			for (com.liferay.portlet.asset.model.AssetEntry assetEntry : assetEntries) {
				removeAssetEntry.remove(pk, assetEntry.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetCategoryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Sets the asset entries associated with the asset category, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntryPKs the primary keys of the asset entries to be associated with the asset category
	 * @throws SystemException if a system exception occurred
	 */
	public void setAssetEntries(long pk, long[] assetEntryPKs)
		throws SystemException {
		try {
			Set<Long> assetEntryPKSet = SetUtil.fromArray(assetEntryPKs);

			List<com.liferay.portlet.asset.model.AssetEntry> assetEntries = getAssetEntries(pk);

			for (com.liferay.portlet.asset.model.AssetEntry assetEntry : assetEntries) {
				if (!assetEntryPKSet.remove(assetEntry.getPrimaryKey())) {
					removeAssetEntry.remove(pk, assetEntry.getPrimaryKey());
				}
			}

			for (Long assetEntryPK : assetEntryPKSet) {
				addAssetEntry.add(pk, assetEntryPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetCategoryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Sets the asset entries associated with the asset category, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntries the asset entries to be associated with the asset category
	 * @throws SystemException if a system exception occurred
	 */
	public void setAssetEntries(long pk,
		List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws SystemException {
		try {
			long[] assetEntryPKs = new long[assetEntries.size()];

			for (int i = 0; i < assetEntries.size(); i++) {
				com.liferay.portlet.asset.model.AssetEntry assetEntry = assetEntries.get(i);

				assetEntryPKs[i] = assetEntry.getPrimaryKey();
			}

			setAssetEntries(pk, assetEntryPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetCategoryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Rebuilds the asset categories tree for the scope using the modified pre-order tree traversal algorithm.
	 *
	 * <p>
	 * Only call this method if the tree has become stale through operations other than normal CRUD. Under normal circumstances the tree is automatically rebuilt whenver necessary.
	 * </p>
	 *
	 * @param groupId the ID of the scope
	 * @param force whether to force the rebuild even if the tree is not stale
	 */
	public void rebuildTree(long groupId, boolean force)
		throws SystemException {
		if (force || (countOrphanTreeNodes(groupId) > 0)) {
			rebuildTree(groupId, 0, 1);

			CacheRegistryUtil.clear(AssetCategoryImpl.class.getName());
			EntityCacheUtil.clearCache(AssetCategoryImpl.class.getName());
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
		}
	}

	protected long countOrphanTreeNodes(long groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(
					"SELECT COUNT(*) AS COUNT_VALUE FROM AssetCategory WHERE groupId = ? AND (leftCategoryId = 0 OR leftCategoryId IS NULL OR rightCategoryId = 0 OR rightCategoryId IS NULL)");

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (Long)q.uniqueResult();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected void expandTree(AssetCategory assetCategory)
		throws SystemException {
		long groupId = assetCategory.getGroupId();

		long lastRightCategoryId = getLastRightCategoryId(groupId,
				assetCategory.getParentCategoryId());

		long leftCategoryId = 2;
		long rightCategoryId = 3;

		if (lastRightCategoryId > 0) {
			leftCategoryId = lastRightCategoryId + 1;
			rightCategoryId = lastRightCategoryId + 2;

			expandTreeLeftCategoryId.expand(groupId, lastRightCategoryId);
			expandTreeRightCategoryId.expand(groupId, lastRightCategoryId);

			CacheRegistryUtil.clear(AssetCategoryImpl.class.getName());
			EntityCacheUtil.clearCache(AssetCategoryImpl.class.getName());
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
		}

		assetCategory.setLeftCategoryId(leftCategoryId);
		assetCategory.setRightCategoryId(rightCategoryId);
	}

	protected long getLastRightCategoryId(long groupId, long parentCategoryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(
					"SELECT rightCategoryId FROM AssetCategory WHERE (groupId = ?) AND (parentCategoryId = ?) ORDER BY rightCategoryId DESC");

			q.addScalar("rightCategoryId",
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(parentCategoryId);

			List<Long> list = (List<Long>)QueryUtil.list(q, getDialect(), 0, 1);

			if (list.isEmpty()) {
				if (parentCategoryId > 0) {
					AssetCategory parentAssetCategory = findByPrimaryKey(parentCategoryId);

					return parentAssetCategory.getLeftCategoryId();
				}

				return 0;
			}
			else {
				return list.get(0);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected long rebuildTree(long groupId, long parentCategoryId,
		long leftCategoryId) throws SystemException {
		List<Long> categoryIds = null;

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(
					"SELECT categoryId FROM AssetCategory WHERE groupId = ? AND parentCategoryId = ? ORDER BY categoryId ASC");

			q.addScalar("categoryId",
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(parentCategoryId);

			categoryIds = q.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		long rightCategoryId = leftCategoryId + 1;

		for (long categoryId : categoryIds) {
			rightCategoryId = rebuildTree(groupId, categoryId, rightCategoryId);
		}

		if (parentCategoryId > 0) {
			updateTree.update(parentCategoryId, leftCategoryId, rightCategoryId);
		}

		return rightCategoryId + 1;
	}

	protected void shrinkTree(AssetCategory assetCategory) {
		long groupId = assetCategory.getGroupId();

		long leftCategoryId = assetCategory.getLeftCategoryId();
		long rightCategoryId = assetCategory.getRightCategoryId();

		long delta = (rightCategoryId - leftCategoryId) + 1;

		shrinkTreeLeftCategoryId.shrink(groupId, rightCategoryId, delta);
		shrinkTreeRightCategoryId.shrink(groupId, rightCategoryId, delta);

		CacheRegistryUtil.clear(AssetCategoryImpl.class.getName());
		EntityCacheUtil.clearCache(AssetCategoryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Initializes the asset category persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.asset.model.AssetCategory")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AssetCategory>> listenersList = new ArrayList<ModelListener<AssetCategory>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AssetCategory>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsAssetEntry = new ContainsAssetEntry(this);

		addAssetEntry = new AddAssetEntry(this);
		clearAssetEntries = new ClearAssetEntries(this);
		removeAssetEntry = new RemoveAssetEntry(this);

		expandTreeLeftCategoryId = new ExpandTreeLeftCategoryId();
		expandTreeRightCategoryId = new ExpandTreeRightCategoryId();
		shrinkTreeLeftCategoryId = new ShrinkTreeLeftCategoryId();
		shrinkTreeRightCategoryId = new ShrinkTreeRightCategoryId();
		updateTree = new UpdateTree();
	}

	public void destroy() {
		EntityCacheUtil.removeCache(AssetCategoryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
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
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	protected ContainsAssetEntry containsAssetEntry;
	protected AddAssetEntry addAssetEntry;
	protected ClearAssetEntries clearAssetEntries;
	protected RemoveAssetEntry removeAssetEntry;

	protected class ContainsAssetEntry {
		protected ContainsAssetEntry(
			AssetCategoryPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSASSETENTRY,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long categoryId, long entryId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(categoryId), new Long(entryId)
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

	protected class AddAssetEntry {
		protected AddAssetEntry(AssetCategoryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO AssetEntries_AssetCategories (categoryId, entryId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long categoryId, long entryId)
			throws SystemException {
			if (!_persistenceImpl.containsAssetEntry.contains(categoryId,
						entryId)) {
				ModelListener<com.liferay.portlet.asset.model.AssetEntry>[] assetEntryListeners =
					assetEntryPersistence.getListeners();

				for (ModelListener<AssetCategory> listener : listeners) {
					listener.onBeforeAddAssociation(categoryId,
						com.liferay.portlet.asset.model.AssetEntry.class.getName(),
						entryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetEntry> listener : assetEntryListeners) {
					listener.onBeforeAddAssociation(entryId,
						AssetCategory.class.getName(), categoryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(categoryId), new Long(entryId)
					});

				for (ModelListener<AssetCategory> listener : listeners) {
					listener.onAfterAddAssociation(categoryId,
						com.liferay.portlet.asset.model.AssetEntry.class.getName(),
						entryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetEntry> listener : assetEntryListeners) {
					listener.onAfterAddAssociation(entryId,
						AssetCategory.class.getName(), categoryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private AssetCategoryPersistenceImpl _persistenceImpl;
	}

	protected class ClearAssetEntries {
		protected ClearAssetEntries(
			AssetCategoryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM AssetEntries_AssetCategories WHERE categoryId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long categoryId) throws SystemException {
			ModelListener<com.liferay.portlet.asset.model.AssetEntry>[] assetEntryListeners =
				assetEntryPersistence.getListeners();

			List<com.liferay.portlet.asset.model.AssetEntry> assetEntries = null;

			if ((listeners.length > 0) || (assetEntryListeners.length > 0)) {
				assetEntries = getAssetEntries(categoryId);

				for (com.liferay.portlet.asset.model.AssetEntry assetEntry : assetEntries) {
					for (ModelListener<AssetCategory> listener : listeners) {
						listener.onBeforeRemoveAssociation(categoryId,
							com.liferay.portlet.asset.model.AssetEntry.class.getName(),
							assetEntry.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.asset.model.AssetEntry> listener : assetEntryListeners) {
						listener.onBeforeRemoveAssociation(assetEntry.getPrimaryKey(),
							AssetCategory.class.getName(), categoryId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(categoryId) });

			if ((listeners.length > 0) || (assetEntryListeners.length > 0)) {
				for (com.liferay.portlet.asset.model.AssetEntry assetEntry : assetEntries) {
					for (ModelListener<AssetCategory> listener : listeners) {
						listener.onAfterRemoveAssociation(categoryId,
							com.liferay.portlet.asset.model.AssetEntry.class.getName(),
							assetEntry.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.asset.model.AssetEntry> listener : assetEntryListeners) {
						listener.onAfterRemoveAssociation(assetEntry.getPrimaryKey(),
							AssetCategory.class.getName(), categoryId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveAssetEntry {
		protected RemoveAssetEntry(AssetCategoryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM AssetEntries_AssetCategories WHERE categoryId = ? AND entryId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long categoryId, long entryId)
			throws SystemException {
			if (_persistenceImpl.containsAssetEntry.contains(categoryId, entryId)) {
				ModelListener<com.liferay.portlet.asset.model.AssetEntry>[] assetEntryListeners =
					assetEntryPersistence.getListeners();

				for (ModelListener<AssetCategory> listener : listeners) {
					listener.onBeforeRemoveAssociation(categoryId,
						com.liferay.portlet.asset.model.AssetEntry.class.getName(),
						entryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetEntry> listener : assetEntryListeners) {
					listener.onBeforeRemoveAssociation(entryId,
						AssetCategory.class.getName(), categoryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(categoryId), new Long(entryId)
					});

				for (ModelListener<AssetCategory> listener : listeners) {
					listener.onAfterRemoveAssociation(categoryId,
						com.liferay.portlet.asset.model.AssetEntry.class.getName(),
						entryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetEntry> listener : assetEntryListeners) {
					listener.onAfterRemoveAssociation(entryId,
						AssetCategory.class.getName(), categoryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private AssetCategoryPersistenceImpl _persistenceImpl;
	}

	protected ExpandTreeLeftCategoryId expandTreeLeftCategoryId;
	protected ExpandTreeRightCategoryId expandTreeRightCategoryId;
	protected ShrinkTreeLeftCategoryId shrinkTreeLeftCategoryId;
	protected ShrinkTreeRightCategoryId shrinkTreeRightCategoryId;
	protected UpdateTree updateTree;

	protected class ExpandTreeLeftCategoryId {
		protected ExpandTreeLeftCategoryId() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"UPDATE AssetCategory SET leftCategoryId = (leftCategoryId + 2) WHERE (groupId = ?) AND (leftCategoryId > ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void expand(long groupId, long leftCategoryId) {
			_sqlUpdate.update(new Object[] { groupId, leftCategoryId });
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ExpandTreeRightCategoryId {
		protected ExpandTreeRightCategoryId() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"UPDATE AssetCategory SET rightCategoryId = (rightCategoryId + 2) WHERE (groupId = ?) AND (rightCategoryId > ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void expand(long groupId, long rightCategoryId) {
			_sqlUpdate.update(new Object[] { groupId, rightCategoryId });
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ShrinkTreeLeftCategoryId {
		protected ShrinkTreeLeftCategoryId() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"UPDATE AssetCategory SET leftCategoryId = (leftCategoryId - ?) WHERE (groupId = ?) AND (leftCategoryId > ?)",
					new int[] {
						java.sql.Types.BIGINT, java.sql.Types.BIGINT,
						java.sql.Types.BIGINT
					});
		}

		protected void shrink(long groupId, long leftCategoryId, long delta) {
			_sqlUpdate.update(new Object[] { delta, groupId, leftCategoryId });
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ShrinkTreeRightCategoryId {
		protected ShrinkTreeRightCategoryId() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"UPDATE AssetCategory SET rightCategoryId = (rightCategoryId - ?) WHERE (groupId = ?) AND (rightCategoryId > ?)",
					new int[] {
						java.sql.Types.BIGINT, java.sql.Types.BIGINT,
						java.sql.Types.BIGINT
					});
		}

		protected void shrink(long groupId, long rightCategoryId, long delta) {
			_sqlUpdate.update(new Object[] { delta, groupId, rightCategoryId });
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class UpdateTree {
		protected UpdateTree() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"UPDATE AssetCategory SET leftCategoryId = ?, rightCategoryId = ? WHERE categoryId = ?",
					new int[] {
						java.sql.Types.BIGINT, java.sql.Types.BIGINT,
						java.sql.Types.BIGINT
					});
		}

		protected void update(long categoryId, long leftCategoryId,
			long rightCategoryId) {
			_sqlUpdate.update(new Object[] {
					leftCategoryId, rightCategoryId, categoryId
				});
		}

		private SqlUpdate _sqlUpdate;
	}

	private static final String _SQL_SELECT_ASSETCATEGORY = "SELECT assetCategory FROM AssetCategory assetCategory";
	private static final String _SQL_SELECT_ASSETCATEGORY_WHERE = "SELECT assetCategory FROM AssetCategory assetCategory WHERE ";
	private static final String _SQL_COUNT_ASSETCATEGORY = "SELECT COUNT(assetCategory) FROM AssetCategory assetCategory";
	private static final String _SQL_COUNT_ASSETCATEGORY_WHERE = "SELECT COUNT(assetCategory) FROM AssetCategory assetCategory WHERE ";
	private static final String _SQL_GETASSETENTRIES = "SELECT {AssetEntry.*} FROM AssetEntry INNER JOIN AssetEntries_AssetCategories ON (AssetEntries_AssetCategories.entryId = AssetEntry.entryId) WHERE (AssetEntries_AssetCategories.categoryId = ?)";
	private static final String _SQL_GETASSETENTRIESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM AssetEntries_AssetCategories WHERE categoryId = ?";
	private static final String _SQL_CONTAINSASSETENTRY = "SELECT COUNT(*) AS COUNT_VALUE FROM AssetEntries_AssetCategories WHERE categoryId = ? AND entryId = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "assetCategory.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "assetCategory.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(assetCategory.uuid IS NULL OR assetCategory.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "assetCategory.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "assetCategory.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(assetCategory.uuid IS NULL OR assetCategory.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "assetCategory.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "assetCategory.groupId = ?";
	private static final String _FINDER_COLUMN_PARENTCATEGORYID_PARENTCATEGORYID_2 =
		"assetCategory.parentCategoryId = ?";
	private static final String _FINDER_COLUMN_VOCABULARYID_VOCABULARYID_2 = "assetCategory.vocabularyId = ?";
	private static final String _FINDER_COLUMN_G_V_GROUPID_2 = "assetCategory.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_V_VOCABULARYID_2 = "assetCategory.vocabularyId = ?";
	private static final String _FINDER_COLUMN_P_N_PARENTCATEGORYID_2 = "assetCategory.parentCategoryId = ? AND ";
	private static final String _FINDER_COLUMN_P_N_NAME_1 = "assetCategory.name IS NULL";
	private static final String _FINDER_COLUMN_P_N_NAME_2 = "assetCategory.name = ?";
	private static final String _FINDER_COLUMN_P_N_NAME_3 = "(assetCategory.name IS NULL OR assetCategory.name = ?)";
	private static final String _FINDER_COLUMN_P_V_PARENTCATEGORYID_2 = "assetCategory.parentCategoryId = ? AND ";
	private static final String _FINDER_COLUMN_P_V_VOCABULARYID_2 = "assetCategory.vocabularyId = ?";
	private static final String _FINDER_COLUMN_N_V_NAME_1 = "assetCategory.name IS NULL AND ";
	private static final String _FINDER_COLUMN_N_V_NAME_2 = "assetCategory.name = ? AND ";
	private static final String _FINDER_COLUMN_N_V_NAME_3 = "(assetCategory.name IS NULL OR assetCategory.name = ?) AND ";
	private static final String _FINDER_COLUMN_N_V_VOCABULARYID_2 = "assetCategory.vocabularyId = ?";
	private static final String _FINDER_COLUMN_P_N_V_PARENTCATEGORYID_2 = "assetCategory.parentCategoryId = ? AND ";
	private static final String _FINDER_COLUMN_P_N_V_NAME_1 = "assetCategory.name IS NULL AND ";
	private static final String _FINDER_COLUMN_P_N_V_NAME_2 = "assetCategory.name = ? AND ";
	private static final String _FINDER_COLUMN_P_N_V_NAME_3 = "(assetCategory.name IS NULL OR assetCategory.name = ?) AND ";
	private static final String _FINDER_COLUMN_P_N_V_VOCABULARYID_2 = "assetCategory.vocabularyId = ?";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "assetCategory.categoryId";
	private static final String _FILTER_SQL_SELECT_ASSETCATEGORY_WHERE = "SELECT DISTINCT {assetCategory.*} FROM AssetCategory assetCategory WHERE ";
	private static final String _FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {AssetCategory.*} FROM (SELECT DISTINCT assetCategory.categoryId FROM AssetCategory assetCategory WHERE ";
	private static final String _FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN AssetCategory ON TEMP_TABLE.categoryId = AssetCategory.categoryId";
	private static final String _FILTER_SQL_COUNT_ASSETCATEGORY_WHERE = "SELECT COUNT(DISTINCT assetCategory.categoryId) AS COUNT_VALUE FROM AssetCategory assetCategory WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "assetCategory";
	private static final String _FILTER_ENTITY_TABLE = "AssetCategory";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetCategory.";
	private static final String _ORDER_BY_ENTITY_TABLE = "AssetCategory.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetCategory exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetCategory exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(AssetCategoryPersistenceImpl.class);
	private static AssetCategory _nullAssetCategory = new AssetCategoryImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<AssetCategory> toCacheModel() {
				return _nullAssetCategoryCacheModel;
			}
		};

	private static CacheModel<AssetCategory> _nullAssetCategoryCacheModel = new CacheModel<AssetCategory>() {
			public AssetCategory toEntityModel() {
				return _nullAssetCategory;
			}
		};
}