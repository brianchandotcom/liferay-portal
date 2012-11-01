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
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.NoSuchCategoryPropertyException;
import com.liferay.portlet.asset.model.AssetCategoryProperty;
import com.liferay.portlet.asset.model.impl.AssetCategoryPropertyImpl;
import com.liferay.portlet.asset.model.impl.AssetCategoryPropertyModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the asset category property service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryPropertyPersistence
 * @see AssetCategoryPropertyUtil
 * @generated
 */
public class AssetCategoryPropertyPersistenceImpl extends BasePersistenceImpl<AssetCategoryProperty>
	implements AssetCategoryPropertyPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AssetCategoryPropertyUtil} to access the asset category property persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AssetCategoryPropertyImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryPropertyImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryPropertyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			AssetCategoryPropertyModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CATEGORYID =
		new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryPropertyImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCategoryId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CATEGORYID =
		new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryPropertyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCategoryId",
			new String[] { Long.class.getName() },
			AssetCategoryPropertyModelImpl.CATEGORYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CATEGORYID = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCategoryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_K = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryPropertyImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_K",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_K = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryPropertyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_K",
			new String[] { Long.class.getName(), String.class.getName() },
			AssetCategoryPropertyModelImpl.COMPANYID_COLUMN_BITMASK |
			AssetCategoryPropertyModelImpl.KEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_K = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_K",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_CA_K = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryPropertyImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByCA_K",
			new String[] { Long.class.getName(), String.class.getName() },
			AssetCategoryPropertyModelImpl.CATEGORYID_COLUMN_BITMASK |
			AssetCategoryPropertyModelImpl.KEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CA_K = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCA_K",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryPropertyImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryPropertyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the asset category property in the entity cache if it is enabled.
	 *
	 * @param assetCategoryProperty the asset category property
	 */
	public void cacheResult(AssetCategoryProperty assetCategoryProperty) {
		EntityCacheUtil.putResult(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyImpl.class,
			assetCategoryProperty.getPrimaryKey(), assetCategoryProperty);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CA_K,
			new Object[] {
				Long.valueOf(assetCategoryProperty.getCategoryId()),
				
			assetCategoryProperty.getKey()
			}, assetCategoryProperty);

		assetCategoryProperty.resetOriginalValues();
	}

	/**
	 * Caches the asset category properties in the entity cache if it is enabled.
	 *
	 * @param assetCategoryProperties the asset category properties
	 */
	public void cacheResult(List<AssetCategoryProperty> assetCategoryProperties) {
		for (AssetCategoryProperty assetCategoryProperty : assetCategoryProperties) {
			if (EntityCacheUtil.getResult(
						AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
						AssetCategoryPropertyImpl.class,
						assetCategoryProperty.getPrimaryKey()) == null) {
				cacheResult(assetCategoryProperty);
			}
			else {
				assetCategoryProperty.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset category properties.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(AssetCategoryPropertyImpl.class.getName());
		}

		EntityCacheUtil.clearCache(AssetCategoryPropertyImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset category property.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetCategoryProperty assetCategoryProperty) {
		EntityCacheUtil.removeResult(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyImpl.class,
			assetCategoryProperty.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(assetCategoryProperty);
	}

	@Override
	public void clearCache(List<AssetCategoryProperty> assetCategoryProperties) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetCategoryProperty assetCategoryProperty : assetCategoryProperties) {
			EntityCacheUtil.removeResult(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
				AssetCategoryPropertyImpl.class,
				assetCategoryProperty.getPrimaryKey());

			clearUniqueFindersCache(assetCategoryProperty);
		}
	}

	protected void clearUniqueFindersCache(
		AssetCategoryProperty assetCategoryProperty) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CA_K,
			new Object[] {
				Long.valueOf(assetCategoryProperty.getCategoryId()),
				
			assetCategoryProperty.getKey()
			});
	}

	/**
	 * Creates a new asset category property with the primary key. Does not add the asset category property to the database.
	 *
	 * @param categoryPropertyId the primary key for the new asset category property
	 * @return the new asset category property
	 */
	public AssetCategoryProperty create(long categoryPropertyId) {
		AssetCategoryProperty assetCategoryProperty = new AssetCategoryPropertyImpl();

		assetCategoryProperty.setNew(true);
		assetCategoryProperty.setPrimaryKey(categoryPropertyId);

		return assetCategoryProperty;
	}

	/**
	 * Removes the asset category property with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param categoryPropertyId the primary key of the asset category property
	 * @return the asset category property that was removed
	 * @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty remove(long categoryPropertyId)
		throws NoSuchCategoryPropertyException, SystemException {
		return remove(Long.valueOf(categoryPropertyId));
	}

	/**
	 * Removes the asset category property with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset category property
	 * @return the asset category property that was removed
	 * @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategoryProperty remove(Serializable primaryKey)
		throws NoSuchCategoryPropertyException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AssetCategoryProperty assetCategoryProperty = (AssetCategoryProperty)session.get(AssetCategoryPropertyImpl.class,
					primaryKey);

			if (assetCategoryProperty == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCategoryPropertyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(assetCategoryProperty);
		}
		catch (NoSuchCategoryPropertyException nsee) {
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
	protected AssetCategoryProperty removeImpl(
		AssetCategoryProperty assetCategoryProperty) throws SystemException {
		assetCategoryProperty = toUnwrappedModel(assetCategoryProperty);

		Session session = null;

		try {
			session = openSession();

			if (assetCategoryProperty.isCachedModel()) {
				assetCategoryProperty = (AssetCategoryProperty)session.get(AssetCategoryPropertyImpl.class,
						assetCategoryProperty.getPrimaryKeyObj());
			}

			if (assetCategoryProperty != null) {
				session.delete(assetCategoryProperty);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (assetCategoryProperty != null) {
			clearCache(assetCategoryProperty);
		}

		return assetCategoryProperty;
	}

	@Override
	public AssetCategoryProperty updateImpl(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty)
		throws SystemException {
		assetCategoryProperty = toUnwrappedModel(assetCategoryProperty);

		boolean isNew = assetCategoryProperty.isNew();

		AssetCategoryPropertyModelImpl assetCategoryPropertyModelImpl = (AssetCategoryPropertyModelImpl)assetCategoryProperty;

		Session session = null;

		try {
			session = openSession();

			if (assetCategoryProperty.isNew()) {
				session.save(assetCategoryProperty);

				assetCategoryProperty.setNew(false);
			}
			else {
				session.merge(assetCategoryProperty);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !AssetCategoryPropertyModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((assetCategoryPropertyModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetCategoryPropertyModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(assetCategoryPropertyModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((assetCategoryPropertyModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CATEGORYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetCategoryPropertyModelImpl.getOriginalCategoryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CATEGORYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CATEGORYID,
					args);

				args = new Object[] {
						Long.valueOf(assetCategoryPropertyModelImpl.getCategoryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CATEGORYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CATEGORYID,
					args);
			}

			if ((assetCategoryPropertyModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_K.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetCategoryPropertyModelImpl.getOriginalCompanyId()),
						
						assetCategoryPropertyModelImpl.getOriginalKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_K, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_K,
					args);

				args = new Object[] {
						Long.valueOf(assetCategoryPropertyModelImpl.getCompanyId()),
						
						assetCategoryPropertyModelImpl.getKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_K, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_K,
					args);
			}
		}

		EntityCacheUtil.putResult(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryPropertyImpl.class,
			assetCategoryProperty.getPrimaryKey(), assetCategoryProperty);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CA_K,
				new Object[] {
					Long.valueOf(assetCategoryProperty.getCategoryId()),
					
				assetCategoryProperty.getKey()
				}, assetCategoryProperty);
		}
		else {
			if ((assetCategoryPropertyModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_CA_K.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetCategoryPropertyModelImpl.getOriginalCategoryId()),
						
						assetCategoryPropertyModelImpl.getOriginalKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CA_K, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CA_K, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CA_K,
					new Object[] {
						Long.valueOf(assetCategoryProperty.getCategoryId()),
						
					assetCategoryProperty.getKey()
					}, assetCategoryProperty);
			}
		}

		return assetCategoryProperty;
	}

	protected AssetCategoryProperty toUnwrappedModel(
		AssetCategoryProperty assetCategoryProperty) {
		if (assetCategoryProperty instanceof AssetCategoryPropertyImpl) {
			return assetCategoryProperty;
		}

		AssetCategoryPropertyImpl assetCategoryPropertyImpl = new AssetCategoryPropertyImpl();

		assetCategoryPropertyImpl.setNew(assetCategoryProperty.isNew());
		assetCategoryPropertyImpl.setPrimaryKey(assetCategoryProperty.getPrimaryKey());

		assetCategoryPropertyImpl.setCategoryPropertyId(assetCategoryProperty.getCategoryPropertyId());
		assetCategoryPropertyImpl.setCompanyId(assetCategoryProperty.getCompanyId());
		assetCategoryPropertyImpl.setUserId(assetCategoryProperty.getUserId());
		assetCategoryPropertyImpl.setUserName(assetCategoryProperty.getUserName());
		assetCategoryPropertyImpl.setCreateDate(assetCategoryProperty.getCreateDate());
		assetCategoryPropertyImpl.setModifiedDate(assetCategoryProperty.getModifiedDate());
		assetCategoryPropertyImpl.setCategoryId(assetCategoryProperty.getCategoryId());
		assetCategoryPropertyImpl.setKey(assetCategoryProperty.getKey());
		assetCategoryPropertyImpl.setValue(assetCategoryProperty.getValue());

		return assetCategoryPropertyImpl;
	}

	/**
	 * Returns the asset category property with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset category property
	 * @return the asset category property
	 * @throws com.liferay.portal.NoSuchModelException if a asset category property with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategoryProperty findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the asset category property with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchCategoryPropertyException} if it could not be found.
	 *
	 * @param categoryPropertyId the primary key of the asset category property
	 * @return the asset category property
	 * @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty findByPrimaryKey(long categoryPropertyId)
		throws NoSuchCategoryPropertyException, SystemException {
		AssetCategoryProperty assetCategoryProperty = fetchByPrimaryKey(categoryPropertyId);

		if (assetCategoryProperty == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					categoryPropertyId);
			}

			throw new NoSuchCategoryPropertyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				categoryPropertyId);
		}

		return assetCategoryProperty;
	}

	/**
	 * Returns the asset category property with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset category property
	 * @return the asset category property, or <code>null</code> if a asset category property with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategoryProperty fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the asset category property with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param categoryPropertyId the primary key of the asset category property
	 * @return the asset category property, or <code>null</code> if a asset category property with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty fetchByPrimaryKey(long categoryPropertyId)
		throws SystemException {
		AssetCategoryProperty assetCategoryProperty = (AssetCategoryProperty)EntityCacheUtil.getResult(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
				AssetCategoryPropertyImpl.class, categoryPropertyId);

		if (assetCategoryProperty == _nullAssetCategoryProperty) {
			return null;
		}

		if (assetCategoryProperty == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				assetCategoryProperty = (AssetCategoryProperty)session.get(AssetCategoryPropertyImpl.class,
						Long.valueOf(categoryPropertyId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (assetCategoryProperty != null) {
					cacheResult(assetCategoryProperty);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(AssetCategoryPropertyModelImpl.ENTITY_CACHE_ENABLED,
						AssetCategoryPropertyImpl.class, categoryPropertyId,
						_nullAssetCategoryProperty);
				}

				closeSession(session);
			}
		}

		return assetCategoryProperty;
	}

	/**
	 * Returns all the asset category properties where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching asset category properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategoryProperty> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset category properties where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @return the range of matching asset category properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategoryProperty> findByCompanyId(long companyId,
		int start, int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset category properties where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset category properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategoryProperty> findByCompanyId(long companyId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
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

		List<AssetCategoryProperty> list = (List<AssetCategoryProperty>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetCategoryProperty assetCategoryProperty : list) {
				if ((companyId != assetCategoryProperty.getCompanyId())) {
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

			query.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<AssetCategoryProperty>)QueryUtil.list(q,
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
	 * Returns the first asset category property in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category property
	 * @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a matching asset category property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryPropertyException, SystemException {
		AssetCategoryProperty assetCategoryProperty = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (assetCategoryProperty != null) {
			return assetCategoryProperty;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryPropertyException(msg.toString());
	}

	/**
	 * Returns the first asset category property in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetCategoryProperty> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset category property in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category property
	 * @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a matching asset category property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryPropertyException, SystemException {
		AssetCategoryProperty assetCategoryProperty = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (assetCategoryProperty != null) {
			return assetCategoryProperty;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryPropertyException(msg.toString());
	}

	/**
	 * Returns the last asset category property in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<AssetCategoryProperty> list = findByCompanyId(companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset category properties before and after the current asset category property in the ordered set where companyId = &#63;.
	 *
	 * @param categoryPropertyId the primary key of the current asset category property
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category property
	 * @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty[] findByCompanyId_PrevAndNext(
		long categoryPropertyId, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryPropertyException, SystemException {
		AssetCategoryProperty assetCategoryProperty = findByPrimaryKey(categoryPropertyId);

		Session session = null;

		try {
			session = openSession();

			AssetCategoryProperty[] array = new AssetCategoryPropertyImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session,
					assetCategoryProperty, companyId, orderByComparator, true);

			array[1] = assetCategoryProperty;

			array[2] = getByCompanyId_PrevAndNext(session,
					assetCategoryProperty, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategoryProperty getByCompanyId_PrevAndNext(
		Session session, AssetCategoryProperty assetCategoryProperty,
		long companyId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

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
			query.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetCategoryProperty);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategoryProperty> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset category properties where categoryId = &#63;.
	 *
	 * @param categoryId the category ID
	 * @return the matching asset category properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategoryProperty> findByCategoryId(long categoryId)
		throws SystemException {
		return findByCategoryId(categoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset category properties where categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @return the range of matching asset category properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategoryProperty> findByCategoryId(long categoryId,
		int start, int end) throws SystemException {
		return findByCategoryId(categoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset category properties where categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset category properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategoryProperty> findByCategoryId(long categoryId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CATEGORYID;
			finderArgs = new Object[] { categoryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CATEGORYID;
			finderArgs = new Object[] { categoryId, start, end, orderByComparator };
		}

		List<AssetCategoryProperty> list = (List<AssetCategoryProperty>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetCategoryProperty assetCategoryProperty : list) {
				if ((categoryId != assetCategoryProperty.getCategoryId())) {
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

			query.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

			query.append(_FINDER_COLUMN_CATEGORYID_CATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

				list = (List<AssetCategoryProperty>)QueryUtil.list(q,
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
	 * Returns the first asset category property in the ordered set where categoryId = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category property
	 * @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a matching asset category property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty findByCategoryId_First(long categoryId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryPropertyException, SystemException {
		AssetCategoryProperty assetCategoryProperty = fetchByCategoryId_First(categoryId,
				orderByComparator);

		if (assetCategoryProperty != null) {
			return assetCategoryProperty;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("categoryId=");
		msg.append(categoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryPropertyException(msg.toString());
	}

	/**
	 * Returns the first asset category property in the ordered set where categoryId = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty fetchByCategoryId_First(long categoryId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetCategoryProperty> list = findByCategoryId(categoryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset category property in the ordered set where categoryId = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category property
	 * @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a matching asset category property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty findByCategoryId_Last(long categoryId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryPropertyException, SystemException {
		AssetCategoryProperty assetCategoryProperty = fetchByCategoryId_Last(categoryId,
				orderByComparator);

		if (assetCategoryProperty != null) {
			return assetCategoryProperty;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("categoryId=");
		msg.append(categoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryPropertyException(msg.toString());
	}

	/**
	 * Returns the last asset category property in the ordered set where categoryId = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty fetchByCategoryId_Last(long categoryId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCategoryId(categoryId);

		List<AssetCategoryProperty> list = findByCategoryId(categoryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset category properties before and after the current asset category property in the ordered set where categoryId = &#63;.
	 *
	 * @param categoryPropertyId the primary key of the current asset category property
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category property
	 * @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty[] findByCategoryId_PrevAndNext(
		long categoryPropertyId, long categoryId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryPropertyException, SystemException {
		AssetCategoryProperty assetCategoryProperty = findByPrimaryKey(categoryPropertyId);

		Session session = null;

		try {
			session = openSession();

			AssetCategoryProperty[] array = new AssetCategoryPropertyImpl[3];

			array[0] = getByCategoryId_PrevAndNext(session,
					assetCategoryProperty, categoryId, orderByComparator, true);

			array[1] = assetCategoryProperty;

			array[2] = getByCategoryId_PrevAndNext(session,
					assetCategoryProperty, categoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategoryProperty getByCategoryId_PrevAndNext(
		Session session, AssetCategoryProperty assetCategoryProperty,
		long categoryId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

		query.append(_FINDER_COLUMN_CATEGORYID_CATEGORYID_2);

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
			query.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(categoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetCategoryProperty);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategoryProperty> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset category properties where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the matching asset category properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategoryProperty> findByC_K(long companyId, String key)
		throws SystemException {
		return findByC_K(companyId, key, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset category properties where companyId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @return the range of matching asset category properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategoryProperty> findByC_K(long companyId, String key,
		int start, int end) throws SystemException {
		return findByC_K(companyId, key, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset category properties where companyId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset category properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategoryProperty> findByC_K(long companyId, String key,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_K;
			finderArgs = new Object[] { companyId, key };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_K;
			finderArgs = new Object[] {
					companyId, key,
					
					start, end, orderByComparator
				};
		}

		List<AssetCategoryProperty> list = (List<AssetCategoryProperty>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetCategoryProperty assetCategoryProperty : list) {
				if ((companyId != assetCategoryProperty.getCompanyId()) ||
						!Validator.equals(key, assetCategoryProperty.getKey())) {
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

			query.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

			query.append(_FINDER_COLUMN_C_K_COMPANYID_2);

			if (key == null) {
				query.append(_FINDER_COLUMN_C_K_KEY_1);
			}
			else {
				if (key.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_K_KEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_K_KEY_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (key != null) {
					qPos.add(key);
				}

				list = (List<AssetCategoryProperty>)QueryUtil.list(q,
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
	 * Returns the first asset category property in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category property
	 * @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a matching asset category property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty findByC_K_First(long companyId, String key,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryPropertyException, SystemException {
		AssetCategoryProperty assetCategoryProperty = fetchByC_K_First(companyId,
				key, orderByComparator);

		if (assetCategoryProperty != null) {
			return assetCategoryProperty;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", key=");
		msg.append(key);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryPropertyException(msg.toString());
	}

	/**
	 * Returns the first asset category property in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty fetchByC_K_First(long companyId, String key,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetCategoryProperty> list = findByC_K(companyId, key, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset category property in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category property
	 * @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a matching asset category property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty findByC_K_Last(long companyId, String key,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryPropertyException, SystemException {
		AssetCategoryProperty assetCategoryProperty = fetchByC_K_Last(companyId,
				key, orderByComparator);

		if (assetCategoryProperty != null) {
			return assetCategoryProperty;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", key=");
		msg.append(key);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryPropertyException(msg.toString());
	}

	/**
	 * Returns the last asset category property in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty fetchByC_K_Last(long companyId, String key,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_K(companyId, key);

		List<AssetCategoryProperty> list = findByC_K(companyId, key, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset category properties before and after the current asset category property in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param categoryPropertyId the primary key of the current asset category property
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category property
	 * @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty[] findByC_K_PrevAndNext(
		long categoryPropertyId, long companyId, String key,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryPropertyException, SystemException {
		AssetCategoryProperty assetCategoryProperty = findByPrimaryKey(categoryPropertyId);

		Session session = null;

		try {
			session = openSession();

			AssetCategoryProperty[] array = new AssetCategoryPropertyImpl[3];

			array[0] = getByC_K_PrevAndNext(session, assetCategoryProperty,
					companyId, key, orderByComparator, true);

			array[1] = assetCategoryProperty;

			array[2] = getByC_K_PrevAndNext(session, assetCategoryProperty,
					companyId, key, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategoryProperty getByC_K_PrevAndNext(Session session,
		AssetCategoryProperty assetCategoryProperty, long companyId,
		String key, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

		query.append(_FINDER_COLUMN_C_K_COMPANYID_2);

		if (key == null) {
			query.append(_FINDER_COLUMN_C_K_KEY_1);
		}
		else {
			if (key.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_K_KEY_3);
			}
			else {
				query.append(_FINDER_COLUMN_C_K_KEY_2);
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
			query.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (key != null) {
			qPos.add(key);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetCategoryProperty);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategoryProperty> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the asset category property where categoryId = &#63; and key = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchCategoryPropertyException} if it could not be found.
	 *
	 * @param categoryId the category ID
	 * @param key the key
	 * @return the matching asset category property
	 * @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a matching asset category property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty findByCA_K(long categoryId, String key)
		throws NoSuchCategoryPropertyException, SystemException {
		AssetCategoryProperty assetCategoryProperty = fetchByCA_K(categoryId,
				key);

		if (assetCategoryProperty == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("categoryId=");
			msg.append(categoryId);

			msg.append(", key=");
			msg.append(key);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCategoryPropertyException(msg.toString());
		}

		return assetCategoryProperty;
	}

	/**
	 * Returns the asset category property where categoryId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param categoryId the category ID
	 * @param key the key
	 * @return the matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty fetchByCA_K(long categoryId, String key)
		throws SystemException {
		return fetchByCA_K(categoryId, key, true);
	}

	/**
	 * Returns the asset category property where categoryId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param categoryId the category ID
	 * @param key the key
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty fetchByCA_K(long categoryId, String key,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { categoryId, key };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_CA_K,
					finderArgs, this);
		}

		if (result instanceof AssetCategoryProperty) {
			AssetCategoryProperty assetCategoryProperty = (AssetCategoryProperty)result;

			if ((categoryId != assetCategoryProperty.getCategoryId()) ||
					!Validator.equals(key, assetCategoryProperty.getKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

			query.append(_FINDER_COLUMN_CA_K_CATEGORYID_2);

			if (key == null) {
				query.append(_FINDER_COLUMN_CA_K_KEY_1);
			}
			else {
				if (key.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_CA_K_KEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_CA_K_KEY_2);
				}
			}

			query.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

				if (key != null) {
					qPos.add(key);
				}

				List<AssetCategoryProperty> list = q.list();

				result = list;

				AssetCategoryProperty assetCategoryProperty = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CA_K,
						finderArgs, list);
				}
				else {
					assetCategoryProperty = list.get(0);

					cacheResult(assetCategoryProperty);

					if ((assetCategoryProperty.getCategoryId() != categoryId) ||
							(assetCategoryProperty.getKey() == null) ||
							!assetCategoryProperty.getKey().equals(key)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CA_K,
							finderArgs, assetCategoryProperty);
					}
				}

				return assetCategoryProperty;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CA_K,
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
				return (AssetCategoryProperty)result;
			}
		}
	}

	/**
	 * Returns all the asset category properties.
	 *
	 * @return the asset category properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategoryProperty> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset category properties.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @return the range of asset category properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategoryProperty> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset category properties.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset category properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetCategoryProperty> findAll(int start, int end,
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

		List<AssetCategoryProperty> list = (List<AssetCategoryProperty>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ASSETCATEGORYPROPERTY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETCATEGORYPROPERTY.concat(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<AssetCategoryProperty>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AssetCategoryProperty>)QueryUtil.list(q,
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
	 * Removes all the asset category properties where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (AssetCategoryProperty assetCategoryProperty : findByCompanyId(
				companyId)) {
			remove(assetCategoryProperty);
		}
	}

	/**
	 * Removes all the asset category properties where categoryId = &#63; from the database.
	 *
	 * @param categoryId the category ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCategoryId(long categoryId) throws SystemException {
		for (AssetCategoryProperty assetCategoryProperty : findByCategoryId(
				categoryId)) {
			remove(assetCategoryProperty);
		}
	}

	/**
	 * Removes all the asset category properties where companyId = &#63; and key = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_K(long companyId, String key)
		throws SystemException {
		for (AssetCategoryProperty assetCategoryProperty : findByC_K(
				companyId, key)) {
			remove(assetCategoryProperty);
		}
	}

	/**
	 * Removes the asset category property where categoryId = &#63; and key = &#63; from the database.
	 *
	 * @param categoryId the category ID
	 * @param key the key
	 * @return the asset category property that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public AssetCategoryProperty removeByCA_K(long categoryId, String key)
		throws NoSuchCategoryPropertyException, SystemException {
		AssetCategoryProperty assetCategoryProperty = findByCA_K(categoryId, key);

		return remove(assetCategoryProperty);
	}

	/**
	 * Removes all the asset category properties from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (AssetCategoryProperty assetCategoryProperty : findAll()) {
			remove(assetCategoryProperty);
		}
	}

	/**
	 * Returns the number of asset category properties where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching asset category properties
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETCATEGORYPROPERTY_WHERE);

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
	 * Returns the number of asset category properties where categoryId = &#63;.
	 *
	 * @param categoryId the category ID
	 * @return the number of matching asset category properties
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCategoryId(long categoryId) throws SystemException {
		Object[] finderArgs = new Object[] { categoryId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CATEGORYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETCATEGORYPROPERTY_WHERE);

			query.append(_FINDER_COLUMN_CATEGORYID_CATEGORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CATEGORYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset category properties where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the number of matching asset category properties
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_K(long companyId, String key) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, key };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_K,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETCATEGORYPROPERTY_WHERE);

			query.append(_FINDER_COLUMN_C_K_COMPANYID_2);

			if (key == null) {
				query.append(_FINDER_COLUMN_C_K_KEY_1);
			}
			else {
				if (key.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_K_KEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_K_KEY_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (key != null) {
					qPos.add(key);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_K, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset category properties where categoryId = &#63; and key = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param key the key
	 * @return the number of matching asset category properties
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCA_K(long categoryId, String key)
		throws SystemException {
		Object[] finderArgs = new Object[] { categoryId, key };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CA_K,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETCATEGORYPROPERTY_WHERE);

			query.append(_FINDER_COLUMN_CA_K_CATEGORYID_2);

			if (key == null) {
				query.append(_FINDER_COLUMN_CA_K_KEY_1);
			}
			else {
				if (key.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_CA_K_KEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_CA_K_KEY_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

				if (key != null) {
					qPos.add(key);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CA_K,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset category properties.
	 *
	 * @return the number of asset category properties
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETCATEGORYPROPERTY);

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
	 * Initializes the asset category property persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.asset.model.AssetCategoryProperty")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AssetCategoryProperty>> listenersList = new ArrayList<ModelListener<AssetCategoryProperty>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AssetCategoryProperty>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(AssetCategoryPropertyImpl.class.getName());
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
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_ASSETCATEGORYPROPERTY = "SELECT assetCategoryProperty FROM AssetCategoryProperty assetCategoryProperty";
	private static final String _SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE = "SELECT assetCategoryProperty FROM AssetCategoryProperty assetCategoryProperty WHERE ";
	private static final String _SQL_COUNT_ASSETCATEGORYPROPERTY = "SELECT COUNT(assetCategoryProperty) FROM AssetCategoryProperty assetCategoryProperty";
	private static final String _SQL_COUNT_ASSETCATEGORYPROPERTY_WHERE = "SELECT COUNT(assetCategoryProperty) FROM AssetCategoryProperty assetCategoryProperty WHERE ";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "assetCategoryProperty.companyId = ?";
	private static final String _FINDER_COLUMN_CATEGORYID_CATEGORYID_2 = "assetCategoryProperty.categoryId = ?";
	private static final String _FINDER_COLUMN_C_K_COMPANYID_2 = "assetCategoryProperty.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_K_KEY_1 = "assetCategoryProperty.key IS NULL";
	private static final String _FINDER_COLUMN_C_K_KEY_2 = "assetCategoryProperty.key = ?";
	private static final String _FINDER_COLUMN_C_K_KEY_3 = "(assetCategoryProperty.key IS NULL OR assetCategoryProperty.key = ?)";
	private static final String _FINDER_COLUMN_CA_K_CATEGORYID_2 = "assetCategoryProperty.categoryId = ? AND ";
	private static final String _FINDER_COLUMN_CA_K_KEY_1 = "assetCategoryProperty.key IS NULL";
	private static final String _FINDER_COLUMN_CA_K_KEY_2 = "assetCategoryProperty.key = ?";
	private static final String _FINDER_COLUMN_CA_K_KEY_3 = "(assetCategoryProperty.key IS NULL OR assetCategoryProperty.key = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetCategoryProperty.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetCategoryProperty exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetCategoryProperty exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(AssetCategoryPropertyPersistenceImpl.class);
	private static AssetCategoryProperty _nullAssetCategoryProperty = new AssetCategoryPropertyImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<AssetCategoryProperty> toCacheModel() {
				return _nullAssetCategoryPropertyCacheModel;
			}
		};

	private static CacheModel<AssetCategoryProperty> _nullAssetCategoryPropertyCacheModel =
		new CacheModel<AssetCategoryProperty>() {
			public AssetCategoryProperty toEntityModel() {
				return _nullAssetCategoryProperty;
			}
		};
}