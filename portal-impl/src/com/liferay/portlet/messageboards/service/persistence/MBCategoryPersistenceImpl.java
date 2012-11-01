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

package com.liferay.portlet.messageboards.service.persistence;

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
import com.liferay.portal.kernel.util.ArrayUtil;
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
import com.liferay.portal.service.persistence.SubscriptionPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.service.persistence.AssetTagPersistence;
import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.messageboards.NoSuchCategoryException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the message boards category service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBCategoryPersistence
 * @see MBCategoryUtil
 * @generated
 */
public class MBCategoryPersistenceImpl extends BasePersistenceImpl<MBCategory>
	implements MBCategoryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link MBCategoryUtil} to access the message boards category persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = MBCategoryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, MBCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, MBCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			MBCategoryModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, MBCategoryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			MBCategoryModelImpl.UUID_COLUMN_BITMASK |
			MBCategoryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, MBCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, MBCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			MBCategoryModelImpl.UUID_COLUMN_BITMASK |
			MBCategoryModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, MBCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, MBCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			MBCategoryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, MBCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, MBCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			MBCategoryModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, MBCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, MBCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P",
			new String[] { Long.class.getName(), Long.class.getName() },
			MBCategoryModelImpl.GROUPID_COLUMN_BITMASK |
			MBCategoryModelImpl.PARENTCATEGORYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_P = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, MBCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, MBCategoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the message boards category in the entity cache if it is enabled.
	 *
	 * @param mbCategory the message boards category
	 */
	public void cacheResult(MBCategory mbCategory) {
		EntityCacheUtil.putResult(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryImpl.class, mbCategory.getPrimaryKey(), mbCategory);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				mbCategory.getUuid(), Long.valueOf(mbCategory.getGroupId())
			}, mbCategory);

		mbCategory.resetOriginalValues();
	}

	/**
	 * Caches the message boards categories in the entity cache if it is enabled.
	 *
	 * @param mbCategories the message boards categories
	 */
	public void cacheResult(List<MBCategory> mbCategories) {
		for (MBCategory mbCategory : mbCategories) {
			if (EntityCacheUtil.getResult(
						MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
						MBCategoryImpl.class, mbCategory.getPrimaryKey()) == null) {
				cacheResult(mbCategory);
			}
			else {
				mbCategory.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all message boards categories.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(MBCategoryImpl.class.getName());
		}

		EntityCacheUtil.clearCache(MBCategoryImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the message boards category.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MBCategory mbCategory) {
		EntityCacheUtil.removeResult(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryImpl.class, mbCategory.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(mbCategory);
	}

	@Override
	public void clearCache(List<MBCategory> mbCategories) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MBCategory mbCategory : mbCategories) {
			EntityCacheUtil.removeResult(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
				MBCategoryImpl.class, mbCategory.getPrimaryKey());

			clearUniqueFindersCache(mbCategory);
		}
	}

	protected void clearUniqueFindersCache(MBCategory mbCategory) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				mbCategory.getUuid(), Long.valueOf(mbCategory.getGroupId())
			});
	}

	/**
	 * Creates a new message boards category with the primary key. Does not add the message boards category to the database.
	 *
	 * @param categoryId the primary key for the new message boards category
	 * @return the new message boards category
	 */
	public MBCategory create(long categoryId) {
		MBCategory mbCategory = new MBCategoryImpl();

		mbCategory.setNew(true);
		mbCategory.setPrimaryKey(categoryId);

		String uuid = PortalUUIDUtil.generate();

		mbCategory.setUuid(uuid);

		return mbCategory;
	}

	/**
	 * Removes the message boards category with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param categoryId the primary key of the message boards category
	 * @return the message boards category that was removed
	 * @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory remove(long categoryId)
		throws NoSuchCategoryException, SystemException {
		return remove(Long.valueOf(categoryId));
	}

	/**
	 * Removes the message boards category with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the message boards category
	 * @return the message boards category that was removed
	 * @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBCategory remove(Serializable primaryKey)
		throws NoSuchCategoryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBCategory mbCategory = (MBCategory)session.get(MBCategoryImpl.class,
					primaryKey);

			if (mbCategory == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCategoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(mbCategory);
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

	@Override
	protected MBCategory removeImpl(MBCategory mbCategory)
		throws SystemException {
		mbCategory = toUnwrappedModel(mbCategory);

		Session session = null;

		try {
			session = openSession();

			if (mbCategory.isCachedModel()) {
				mbCategory = (MBCategory)session.get(MBCategoryImpl.class,
						mbCategory.getPrimaryKeyObj());
			}

			if (mbCategory != null) {
				session.delete(mbCategory);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (mbCategory != null) {
			clearCache(mbCategory);
		}

		return mbCategory;
	}

	@Override
	public MBCategory updateImpl(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory)
		throws SystemException {
		mbCategory = toUnwrappedModel(mbCategory);

		boolean isNew = mbCategory.isNew();

		MBCategoryModelImpl mbCategoryModelImpl = (MBCategoryModelImpl)mbCategory;

		if (Validator.isNull(mbCategory.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			mbCategory.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (mbCategory.isNew()) {
				session.save(mbCategory);

				mbCategory.setNew(false);
			}
			else {
				session.merge(mbCategory);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !MBCategoryModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((mbCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbCategoryModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { mbCategoryModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((mbCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbCategoryModelImpl.getOriginalUuid(),
						Long.valueOf(mbCategoryModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						mbCategoryModelImpl.getUuid(),
						Long.valueOf(mbCategoryModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((mbCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbCategoryModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(mbCategoryModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((mbCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbCategoryModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(mbCategoryModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((mbCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbCategoryModelImpl.getOriginalGroupId()),
						Long.valueOf(mbCategoryModelImpl.getOriginalParentCategoryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P,
					args);

				args = new Object[] {
						Long.valueOf(mbCategoryModelImpl.getGroupId()),
						Long.valueOf(mbCategoryModelImpl.getParentCategoryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P,
					args);
			}
		}

		EntityCacheUtil.putResult(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
			MBCategoryImpl.class, mbCategory.getPrimaryKey(), mbCategory);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					mbCategory.getUuid(), Long.valueOf(mbCategory.getGroupId())
				}, mbCategory);
		}
		else {
			if ((mbCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbCategoryModelImpl.getOriginalUuid(),
						Long.valueOf(mbCategoryModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						mbCategory.getUuid(),
						Long.valueOf(mbCategory.getGroupId())
					}, mbCategory);
			}
		}

		return mbCategory;
	}

	protected MBCategory toUnwrappedModel(MBCategory mbCategory) {
		if (mbCategory instanceof MBCategoryImpl) {
			return mbCategory;
		}

		MBCategoryImpl mbCategoryImpl = new MBCategoryImpl();

		mbCategoryImpl.setNew(mbCategory.isNew());
		mbCategoryImpl.setPrimaryKey(mbCategory.getPrimaryKey());

		mbCategoryImpl.setUuid(mbCategory.getUuid());
		mbCategoryImpl.setCategoryId(mbCategory.getCategoryId());
		mbCategoryImpl.setGroupId(mbCategory.getGroupId());
		mbCategoryImpl.setCompanyId(mbCategory.getCompanyId());
		mbCategoryImpl.setUserId(mbCategory.getUserId());
		mbCategoryImpl.setUserName(mbCategory.getUserName());
		mbCategoryImpl.setCreateDate(mbCategory.getCreateDate());
		mbCategoryImpl.setModifiedDate(mbCategory.getModifiedDate());
		mbCategoryImpl.setParentCategoryId(mbCategory.getParentCategoryId());
		mbCategoryImpl.setName(mbCategory.getName());
		mbCategoryImpl.setDescription(mbCategory.getDescription());
		mbCategoryImpl.setDisplayStyle(mbCategory.getDisplayStyle());
		mbCategoryImpl.setThreadCount(mbCategory.getThreadCount());
		mbCategoryImpl.setMessageCount(mbCategory.getMessageCount());
		mbCategoryImpl.setLastPostDate(mbCategory.getLastPostDate());

		return mbCategoryImpl;
	}

	/**
	 * Returns the message boards category with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards category
	 * @return the message boards category
	 * @throws com.liferay.portal.NoSuchModelException if a message boards category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBCategory findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the message boards category with the primary key or throws a {@link com.liferay.portlet.messageboards.NoSuchCategoryException} if it could not be found.
	 *
	 * @param categoryId the primary key of the message boards category
	 * @return the message boards category
	 * @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory findByPrimaryKey(long categoryId)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = fetchByPrimaryKey(categoryId);

		if (mbCategory == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + categoryId);
			}

			throw new NoSuchCategoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				categoryId);
		}

		return mbCategory;
	}

	/**
	 * Returns the message boards category with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards category
	 * @return the message boards category, or <code>null</code> if a message boards category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBCategory fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the message boards category with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param categoryId the primary key of the message boards category
	 * @return the message boards category, or <code>null</code> if a message boards category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory fetchByPrimaryKey(long categoryId)
		throws SystemException {
		MBCategory mbCategory = (MBCategory)EntityCacheUtil.getResult(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
				MBCategoryImpl.class, categoryId);

		if (mbCategory == _nullMBCategory) {
			return null;
		}

		if (mbCategory == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				mbCategory = (MBCategory)session.get(MBCategoryImpl.class,
						Long.valueOf(categoryId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (mbCategory != null) {
					cacheResult(mbCategory);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(MBCategoryModelImpl.ENTITY_CACHE_ENABLED,
						MBCategoryImpl.class, categoryId, _nullMBCategory);
				}

				closeSession(session);
			}
		}

		return mbCategory;
	}

	/**
	 * Returns all the message boards categories where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards categories where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards categories
	 * @param end the upper bound of the range of message boards categories (not inclusive)
	 * @return the range of matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards categories where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards categories
	 * @param end the upper bound of the range of message boards categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> findByUuid(String uuid, int start, int end,
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

		List<MBCategory> list = (List<MBCategory>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBCategory mbCategory : list) {
				if (!Validator.equals(uuid, mbCategory.getUuid())) {
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

			query.append(_SQL_SELECT_MBCATEGORY_WHERE);

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
				query.append(MBCategoryModelImpl.ORDER_BY_JPQL);
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

				list = (List<MBCategory>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first message boards category in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards category
	 * @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = fetchByUuid_First(uuid, orderByComparator);

		if (mbCategory != null) {
			return mbCategory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the first message boards category in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards category, or <code>null</code> if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBCategory> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards category in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards category
	 * @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = fetchByUuid_Last(uuid, orderByComparator);

		if (mbCategory != null) {
			return mbCategory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the last message boards category in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards category, or <code>null</code> if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<MBCategory> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards categories before and after the current message boards category in the ordered set where uuid = &#63;.
	 *
	 * @param categoryId the primary key of the current message boards category
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards category
	 * @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory[] findByUuid_PrevAndNext(long categoryId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			MBCategory[] array = new MBCategoryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, mbCategory, uuid,
					orderByComparator, true);

			array[1] = mbCategory;

			array[2] = getByUuid_PrevAndNext(session, mbCategory, uuid,
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

	protected MBCategory getByUuid_PrevAndNext(Session session,
		MBCategory mbCategory, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBCATEGORY_WHERE);

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
			query.append(MBCategoryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the message boards category where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchCategoryException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message boards category
	 * @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory findByUUID_G(String uuid, long groupId)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = fetchByUUID_G(uuid, groupId);

		if (mbCategory == null) {
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

		return mbCategory;
	}

	/**
	 * Returns the message boards category where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message boards category, or <code>null</code> if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the message boards category where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching message boards category, or <code>null</code> if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof MBCategory) {
			MBCategory mbCategory = (MBCategory)result;

			if (!Validator.equals(uuid, mbCategory.getUuid()) ||
					(groupId != mbCategory.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_MBCATEGORY_WHERE);

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

			query.append(MBCategoryModelImpl.ORDER_BY_JPQL);

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

				List<MBCategory> list = q.list();

				result = list;

				MBCategory mbCategory = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					mbCategory = list.get(0);

					cacheResult(mbCategory);

					if ((mbCategory.getUuid() == null) ||
							!mbCategory.getUuid().equals(uuid) ||
							(mbCategory.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, mbCategory);
					}
				}

				return mbCategory;
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
				return (MBCategory)result;
			}
		}
	}

	/**
	 * Returns all the message boards categories where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards categories where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards categories
	 * @param end the upper bound of the range of message boards categories (not inclusive)
	 * @return the range of matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> findByUuid_C(String uuid, long companyId,
		int start, int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards categories where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards categories
	 * @param end the upper bound of the range of message boards categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> findByUuid_C(String uuid, long companyId,
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

		List<MBCategory> list = (List<MBCategory>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBCategory mbCategory : list) {
				if (!Validator.equals(uuid, mbCategory.getUuid()) ||
						(companyId != mbCategory.getCompanyId())) {
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

			query.append(_SQL_SELECT_MBCATEGORY_WHERE);

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
				query.append(MBCategoryModelImpl.ORDER_BY_JPQL);
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

				list = (List<MBCategory>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first message boards category in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards category
	 * @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (mbCategory != null) {
			return mbCategory;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the first message boards category in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards category, or <code>null</code> if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBCategory> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards category in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards category
	 * @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (mbCategory != null) {
			return mbCategory;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the last message boards category in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards category, or <code>null</code> if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<MBCategory> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards categories before and after the current message boards category in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param categoryId the primary key of the current message boards category
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards category
	 * @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory[] findByUuid_C_PrevAndNext(long categoryId, String uuid,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			MBCategory[] array = new MBCategoryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, mbCategory, uuid,
					companyId, orderByComparator, true);

			array[1] = mbCategory;

			array[2] = getByUuid_C_PrevAndNext(session, mbCategory, uuid,
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

	protected MBCategory getByUuid_C_PrevAndNext(Session session,
		MBCategory mbCategory, String uuid, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBCATEGORY_WHERE);

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
			query.append(MBCategoryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards categories where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards categories where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards categories
	 * @param end the upper bound of the range of message boards categories (not inclusive)
	 * @return the range of matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards categories where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards categories
	 * @param end the upper bound of the range of message boards categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> findByGroupId(long groupId, int start, int end,
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

		List<MBCategory> list = (List<MBCategory>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBCategory mbCategory : list) {
				if ((groupId != mbCategory.getGroupId())) {
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

			query.append(_SQL_SELECT_MBCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<MBCategory>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first message boards category in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards category
	 * @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = fetchByGroupId_First(groupId, orderByComparator);

		if (mbCategory != null) {
			return mbCategory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the first message boards category in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards category, or <code>null</code> if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBCategory> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards category in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards category
	 * @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = fetchByGroupId_Last(groupId, orderByComparator);

		if (mbCategory != null) {
			return mbCategory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the last message boards category in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards category, or <code>null</code> if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<MBCategory> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards categories before and after the current message boards category in the ordered set where groupId = &#63;.
	 *
	 * @param categoryId the primary key of the current message boards category
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards category
	 * @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory[] findByGroupId_PrevAndNext(long categoryId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			MBCategory[] array = new MBCategoryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, mbCategory, groupId,
					orderByComparator, true);

			array[1] = mbCategory;

			array[2] = getByGroupId_PrevAndNext(session, mbCategory, groupId,
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

	protected MBCategory getByGroupId_PrevAndNext(Session session,
		MBCategory mbCategory, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBCATEGORY_WHERE);

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
			query.append(MBCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards categories that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching message boards categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards categories that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards categories
	 * @param end the upper bound of the range of message boards categories (not inclusive)
	 * @return the range of matching message boards categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> filterFindByGroupId(long groupId, int start, int end)
		throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards categories that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards categories
	 * @param end the upper bound of the range of message boards categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> filterFindByGroupId(long groupId, int start,
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
			query.append(_FILTER_SQL_SELECT_MBCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBCategoryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBCategoryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<MBCategory>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the message boards categories before and after the current message boards category in the ordered set of message boards categories that the user has permission to view where groupId = &#63;.
	 *
	 * @param categoryId the primary key of the current message boards category
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards category
	 * @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory[] filterFindByGroupId_PrevAndNext(long categoryId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(categoryId, groupId,
				orderByComparator);
		}

		MBCategory mbCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			MBCategory[] array = new MBCategoryImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, mbCategory,
					groupId, orderByComparator, true);

			array[1] = mbCategory;

			array[2] = filterGetByGroupId_PrevAndNext(session, mbCategory,
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

	protected MBCategory filterGetByGroupId_PrevAndNext(Session session,
		MBCategory mbCategory, long groupId,
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
			query.append(_FILTER_SQL_SELECT_MBCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MBCategoryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MBCategoryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards categories where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards categories where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards categories
	 * @param end the upper bound of the range of message boards categories (not inclusive)
	 * @return the range of matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards categories where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards categories
	 * @param end the upper bound of the range of message boards categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> findByCompanyId(long companyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<MBCategory> list = (List<MBCategory>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBCategory mbCategory : list) {
				if ((companyId != mbCategory.getCompanyId())) {
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

			query.append(_SQL_SELECT_MBCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<MBCategory>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first message boards category in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards category
	 * @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (mbCategory != null) {
			return mbCategory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the first message boards category in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards category, or <code>null</code> if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBCategory> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards category in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards category
	 * @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (mbCategory != null) {
			return mbCategory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the last message boards category in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards category, or <code>null</code> if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<MBCategory> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards categories before and after the current message boards category in the ordered set where companyId = &#63;.
	 *
	 * @param categoryId the primary key of the current message boards category
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards category
	 * @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory[] findByCompanyId_PrevAndNext(long categoryId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			MBCategory[] array = new MBCategoryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, mbCategory,
					companyId, orderByComparator, true);

			array[1] = mbCategory;

			array[2] = getByCompanyId_PrevAndNext(session, mbCategory,
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

	protected MBCategory getByCompanyId_PrevAndNext(Session session,
		MBCategory mbCategory, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBCATEGORY_WHERE);

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
			query.append(MBCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards categories where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @return the matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> findByG_P(long groupId, long parentCategoryId)
		throws SystemException {
		return findByG_P(groupId, parentCategoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards categories where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param start the lower bound of the range of message boards categories
	 * @param end the upper bound of the range of message boards categories (not inclusive)
	 * @return the range of matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> findByG_P(long groupId, long parentCategoryId,
		int start, int end) throws SystemException {
		return findByG_P(groupId, parentCategoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards categories where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param start the lower bound of the range of message boards categories
	 * @param end the upper bound of the range of message boards categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> findByG_P(long groupId, long parentCategoryId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P;
			finderArgs = new Object[] { groupId, parentCategoryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P;
			finderArgs = new Object[] {
					groupId, parentCategoryId,
					
					start, end, orderByComparator
				};
		}

		List<MBCategory> list = (List<MBCategory>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBCategory mbCategory : list) {
				if ((groupId != mbCategory.getGroupId()) ||
						(parentCategoryId != mbCategory.getParentCategoryId())) {
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

			query.append(_SQL_SELECT_MBCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PARENTCATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentCategoryId);

				list = (List<MBCategory>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first message boards category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards category
	 * @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory findByG_P_First(long groupId, long parentCategoryId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = fetchByG_P_First(groupId, parentCategoryId,
				orderByComparator);

		if (mbCategory != null) {
			return mbCategory;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentCategoryId=");
		msg.append(parentCategoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the first message boards category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards category, or <code>null</code> if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory fetchByG_P_First(long groupId, long parentCategoryId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBCategory> list = findByG_P(groupId, parentCategoryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards category
	 * @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory findByG_P_Last(long groupId, long parentCategoryId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = fetchByG_P_Last(groupId, parentCategoryId,
				orderByComparator);

		if (mbCategory != null) {
			return mbCategory;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentCategoryId=");
		msg.append(parentCategoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the last message boards category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards category, or <code>null</code> if a matching message boards category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory fetchByG_P_Last(long groupId, long parentCategoryId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_P(groupId, parentCategoryId);

		List<MBCategory> list = findByG_P(groupId, parentCategoryId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards categories before and after the current message boards category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * @param categoryId the primary key of the current message boards category
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards category
	 * @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory[] findByG_P_PrevAndNext(long categoryId, long groupId,
		long parentCategoryId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			MBCategory[] array = new MBCategoryImpl[3];

			array[0] = getByG_P_PrevAndNext(session, mbCategory, groupId,
					parentCategoryId, orderByComparator, true);

			array[1] = mbCategory;

			array[2] = getByG_P_PrevAndNext(session, mbCategory, groupId,
					parentCategoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBCategory getByG_P_PrevAndNext(Session session,
		MBCategory mbCategory, long groupId, long parentCategoryId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTCATEGORYID_2);

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
			query.append(MBCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentCategoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards categories where groupId = &#63; and parentCategoryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryIds the parent category IDs
	 * @return the matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> findByG_P(long groupId, long[] parentCategoryIds)
		throws SystemException {
		return findByG_P(groupId, parentCategoryIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards categories where groupId = &#63; and parentCategoryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryIds the parent category IDs
	 * @param start the lower bound of the range of message boards categories
	 * @param end the upper bound of the range of message boards categories (not inclusive)
	 * @return the range of matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> findByG_P(long groupId, long[] parentCategoryIds,
		int start, int end) throws SystemException {
		return findByG_P(groupId, parentCategoryIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards categories where groupId = &#63; and parentCategoryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryIds the parent category IDs
	 * @param start the lower bound of the range of message boards categories
	 * @param end the upper bound of the range of message boards categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> findByG_P(long groupId, long[] parentCategoryIds,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderArgs = new Object[] {
					groupId, StringUtil.merge(parentCategoryIds)
				};
		}
		else {
			finderArgs = new Object[] {
					groupId, StringUtil.merge(parentCategoryIds),
					
					start, end, orderByComparator
				};
		}

		List<MBCategory> list = (List<MBCategory>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBCategory mbCategory : list) {
				if ((groupId != mbCategory.getGroupId()) ||
						!ArrayUtil.contains(parentCategoryIds,
							mbCategory.getParentCategoryId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_MBCATEGORY_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_P_GROUPID_5);

			conjunctionable = true;

			if ((parentCategoryIds == null) || (parentCategoryIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < parentCategoryIds.length; i++) {
					query.append(_FINDER_COLUMN_G_P_PARENTCATEGORYID_5);

					if ((i + 1) < parentCategoryIds.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (parentCategoryIds != null) {
					qPos.add(parentCategoryIds);
				}

				list = (List<MBCategory>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns all the message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @return the matching message boards categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> filterFindByG_P(long groupId, long parentCategoryId)
		throws SystemException {
		return filterFindByG_P(groupId, parentCategoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param start the lower bound of the range of message boards categories
	 * @param end the upper bound of the range of message boards categories (not inclusive)
	 * @return the range of matching message boards categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> filterFindByG_P(long groupId,
		long parentCategoryId, int start, int end) throws SystemException {
		return filterFindByG_P(groupId, parentCategoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards categories that the user has permissions to view where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param start the lower bound of the range of message boards categories
	 * @param end the upper bound of the range of message boards categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> filterFindByG_P(long groupId,
		long parentCategoryId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P(groupId, parentCategoryId, start, end,
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
			query.append(_FILTER_SQL_SELECT_MBCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTCATEGORYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBCategoryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBCategoryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentCategoryId);

			return (List<MBCategory>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the message boards categories before and after the current message boards category in the ordered set of message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * @param categoryId the primary key of the current message boards category
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards category
	 * @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory[] filterFindByG_P_PrevAndNext(long categoryId,
		long groupId, long parentCategoryId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_PrevAndNext(categoryId, groupId, parentCategoryId,
				orderByComparator);
		}

		MBCategory mbCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			MBCategory[] array = new MBCategoryImpl[3];

			array[0] = filterGetByG_P_PrevAndNext(session, mbCategory, groupId,
					parentCategoryId, orderByComparator, true);

			array[1] = mbCategory;

			array[2] = filterGetByG_P_PrevAndNext(session, mbCategory, groupId,
					parentCategoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBCategory filterGetByG_P_PrevAndNext(Session session,
		MBCategory mbCategory, long groupId, long parentCategoryId,
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
			query.append(_FILTER_SQL_SELECT_MBCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTCATEGORYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MBCategoryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MBCategoryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentCategoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryIds the parent category IDs
	 * @return the matching message boards categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> filterFindByG_P(long groupId,
		long[] parentCategoryIds) throws SystemException {
		return filterFindByG_P(groupId, parentCategoryIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryIds the parent category IDs
	 * @param start the lower bound of the range of message boards categories
	 * @param end the upper bound of the range of message boards categories (not inclusive)
	 * @return the range of matching message boards categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> filterFindByG_P(long groupId,
		long[] parentCategoryIds, int start, int end) throws SystemException {
		return filterFindByG_P(groupId, parentCategoryIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryIds the parent category IDs
	 * @param start the lower bound of the range of message boards categories
	 * @param end the upper bound of the range of message boards categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> filterFindByG_P(long groupId,
		long[] parentCategoryIds, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P(groupId, parentCategoryIds, start, end,
				orderByComparator);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_5);

		conjunctionable = true;

		if ((parentCategoryIds == null) || (parentCategoryIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < parentCategoryIds.length; i++) {
				query.append(_FINDER_COLUMN_G_P_PARENTCATEGORYID_5);

				if ((i + 1) < parentCategoryIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBCategoryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBCategoryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (parentCategoryIds != null) {
				qPos.add(parentCategoryIds);
			}

			return (List<MBCategory>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the message boards categories.
	 *
	 * @return the message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards categories
	 * @param end the upper bound of the range of message boards categories (not inclusive)
	 * @return the range of message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards categories
	 * @param end the upper bound of the range of message boards categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBCategory> findAll(int start, int end,
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

		List<MBCategory> list = (List<MBCategory>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_MBCATEGORY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MBCATEGORY.concat(MBCategoryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<MBCategory>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<MBCategory>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the message boards categories where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (MBCategory mbCategory : findByUuid(uuid)) {
			remove(mbCategory);
		}
	}

	/**
	 * Removes the message boards category where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the message boards category that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public MBCategory removeByUUID_G(String uuid, long groupId)
		throws NoSuchCategoryException, SystemException {
		MBCategory mbCategory = findByUUID_G(uuid, groupId);

		return remove(mbCategory);
	}

	/**
	 * Removes all the message boards categories where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (MBCategory mbCategory : findByUuid_C(uuid, companyId)) {
			remove(mbCategory);
		}
	}

	/**
	 * Removes all the message boards categories where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (MBCategory mbCategory : findByGroupId(groupId)) {
			remove(mbCategory);
		}
	}

	/**
	 * Removes all the message boards categories where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (MBCategory mbCategory : findByCompanyId(companyId)) {
			remove(mbCategory);
		}
	}

	/**
	 * Removes all the message boards categories where groupId = &#63; and parentCategoryId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_P(long groupId, long parentCategoryId)
		throws SystemException {
		for (MBCategory mbCategory : findByG_P(groupId, parentCategoryId)) {
			remove(mbCategory);
		}
	}

	/**
	 * Removes all the message boards categories from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (MBCategory mbCategory : findAll()) {
			remove(mbCategory);
		}
	}

	/**
	 * Returns the number of message boards categories where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBCATEGORY_WHERE);

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
	 * Returns the number of message boards categories where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBCATEGORY_WHERE);

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
	 * Returns the number of message boards categories where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBCATEGORY_WHERE);

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
	 * Returns the number of message boards categories where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBCATEGORY_WHERE);

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
	 * Returns the number of message boards categories that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching message boards categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_MBCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBCategory.class.getName(),
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
	 * Returns the number of message boards categories where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBCATEGORY_WHERE);

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
	 * Returns the number of message boards categories where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @return the number of matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_P(long groupId, long parentCategoryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, parentCategoryId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PARENTCATEGORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message boards categories where groupId = &#63; and parentCategoryId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryIds the parent category IDs
	 * @return the number of matching message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_P(long groupId, long[] parentCategoryIds)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, StringUtil.merge(parentCategoryIds)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_MBCATEGORY_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_P_GROUPID_5);

			conjunctionable = true;

			if ((parentCategoryIds == null) || (parentCategoryIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < parentCategoryIds.length; i++) {
					query.append(_FINDER_COLUMN_G_P_PARENTCATEGORYID_5);

					if ((i + 1) < parentCategoryIds.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (parentCategoryIds != null) {
					qPos.add(parentCategoryIds);
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

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_P,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @return the number of matching message boards categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_P(long groupId, long parentCategoryId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P(groupId, parentCategoryId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_MBCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTCATEGORYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentCategoryId);

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
	 * Returns the number of message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryIds the parent category IDs
	 * @return the number of matching message boards categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_P(long groupId, long[] parentCategoryIds)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P(groupId, parentCategoryIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_MBCATEGORY_WHERE);

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_5);

		conjunctionable = true;

		if ((parentCategoryIds == null) || (parentCategoryIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < parentCategoryIds.length; i++) {
				query.append(_FINDER_COLUMN_G_P_PARENTCATEGORYID_5);

				if ((i + 1) < parentCategoryIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (parentCategoryIds != null) {
				qPos.add(parentCategoryIds);
			}

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
	 * Returns the number of message boards categories.
	 *
	 * @return the number of message boards categories
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MBCATEGORY);

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
	 * Initializes the message boards category persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.messageboards.model.MBCategory")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<MBCategory>> listenersList = new ArrayList<ModelListener<MBCategory>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<MBCategory>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(MBCategoryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = MBBanPersistence.class)
	protected MBBanPersistence mbBanPersistence;
	@BeanReference(type = MBCategoryPersistence.class)
	protected MBCategoryPersistence mbCategoryPersistence;
	@BeanReference(type = MBDiscussionPersistence.class)
	protected MBDiscussionPersistence mbDiscussionPersistence;
	@BeanReference(type = MBMailingListPersistence.class)
	protected MBMailingListPersistence mbMailingListPersistence;
	@BeanReference(type = MBMessagePersistence.class)
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(type = MBStatsUserPersistence.class)
	protected MBStatsUserPersistence mbStatsUserPersistence;
	@BeanReference(type = MBThreadPersistence.class)
	protected MBThreadPersistence mbThreadPersistence;
	@BeanReference(type = MBThreadFlagPersistence.class)
	protected MBThreadFlagPersistence mbThreadFlagPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	private static final String _SQL_SELECT_MBCATEGORY = "SELECT mbCategory FROM MBCategory mbCategory";
	private static final String _SQL_SELECT_MBCATEGORY_WHERE = "SELECT mbCategory FROM MBCategory mbCategory WHERE ";
	private static final String _SQL_COUNT_MBCATEGORY = "SELECT COUNT(mbCategory) FROM MBCategory mbCategory";
	private static final String _SQL_COUNT_MBCATEGORY_WHERE = "SELECT COUNT(mbCategory) FROM MBCategory mbCategory WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "mbCategory.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "mbCategory.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(mbCategory.uuid IS NULL OR mbCategory.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "mbCategory.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "mbCategory.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(mbCategory.uuid IS NULL OR mbCategory.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "mbCategory.groupId = ?";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "mbCategory.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "mbCategory.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(mbCategory.uuid IS NULL OR mbCategory.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "mbCategory.companyId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "mbCategory.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "mbCategory.companyId = ?";
	private static final String _FINDER_COLUMN_G_P_GROUPID_2 = "mbCategory.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_GROUPID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_G_P_GROUPID_2) + ")";
	private static final String _FINDER_COLUMN_G_P_PARENTCATEGORYID_2 = "mbCategory.parentCategoryId = ?";
	private static final String _FINDER_COLUMN_G_P_PARENTCATEGORYID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_G_P_PARENTCATEGORYID_2) + ")";

	private static String _removeConjunction(String sql) {
		int pos = sql.indexOf(" AND ");

		if (pos != -1) {
			sql = sql.substring(0, pos);
		}

		return sql;
	}

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "mbCategory.categoryId";
	private static final String _FILTER_SQL_SELECT_MBCATEGORY_WHERE = "SELECT DISTINCT {mbCategory.*} FROM MBCategory mbCategory WHERE ";
	private static final String _FILTER_SQL_SELECT_MBCATEGORY_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {MBCategory.*} FROM (SELECT DISTINCT mbCategory.categoryId FROM MBCategory mbCategory WHERE ";
	private static final String _FILTER_SQL_SELECT_MBCATEGORY_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN MBCategory ON TEMP_TABLE.categoryId = MBCategory.categoryId";
	private static final String _FILTER_SQL_COUNT_MBCATEGORY_WHERE = "SELECT COUNT(DISTINCT mbCategory.categoryId) AS COUNT_VALUE FROM MBCategory mbCategory WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "mbCategory";
	private static final String _FILTER_ENTITY_TABLE = "MBCategory";
	private static final String _ORDER_BY_ENTITY_ALIAS = "mbCategory.";
	private static final String _ORDER_BY_ENTITY_TABLE = "MBCategory.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MBCategory exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MBCategory exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(MBCategoryPersistenceImpl.class);
	private static MBCategory _nullMBCategory = new MBCategoryImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<MBCategory> toCacheModel() {
				return _nullMBCategoryCacheModel;
			}
		};

	private static CacheModel<MBCategory> _nullMBCategoryCacheModel = new CacheModel<MBCategory>() {
			public MBCategory toEntityModel() {
				return _nullMBCategory;
			}
		};
}