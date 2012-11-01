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

package com.liferay.portlet.mobiledevicerules.service.persistence;

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

import com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupImpl;
import com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the m d r rule group service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see MDRRuleGroupPersistence
 * @see MDRRuleGroupUtil
 * @generated
 */
public class MDRRuleGroupPersistenceImpl extends BasePersistenceImpl<MDRRuleGroup>
	implements MDRRuleGroupPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link MDRRuleGroupUtil} to access the m d r rule group persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = MDRRuleGroupImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(MDRRuleGroupModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleGroupModelImpl.FINDER_CACHE_ENABLED, MDRRuleGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(MDRRuleGroupModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleGroupModelImpl.FINDER_CACHE_ENABLED, MDRRuleGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			MDRRuleGroupModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(MDRRuleGroupModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleGroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(MDRRuleGroupModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleGroupModelImpl.FINDER_CACHE_ENABLED, MDRRuleGroupImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			MDRRuleGroupModelImpl.UUID_COLUMN_BITMASK |
			MDRRuleGroupModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(MDRRuleGroupModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleGroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(MDRRuleGroupModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleGroupModelImpl.FINDER_CACHE_ENABLED, MDRRuleGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(MDRRuleGroupModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleGroupModelImpl.FINDER_CACHE_ENABLED, MDRRuleGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			MDRRuleGroupModelImpl.UUID_COLUMN_BITMASK |
			MDRRuleGroupModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(MDRRuleGroupModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleGroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(MDRRuleGroupModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleGroupModelImpl.FINDER_CACHE_ENABLED, MDRRuleGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(MDRRuleGroupModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleGroupModelImpl.FINDER_CACHE_ENABLED, MDRRuleGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			MDRRuleGroupModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(MDRRuleGroupModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleGroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(MDRRuleGroupModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleGroupModelImpl.FINDER_CACHE_ENABLED, MDRRuleGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(MDRRuleGroupModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleGroupModelImpl.FINDER_CACHE_ENABLED, MDRRuleGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MDRRuleGroupModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleGroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the m d r rule group in the entity cache if it is enabled.
	 *
	 * @param mdrRuleGroup the m d r rule group
	 */
	public void cacheResult(MDRRuleGroup mdrRuleGroup) {
		EntityCacheUtil.putResult(MDRRuleGroupModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleGroupImpl.class, mdrRuleGroup.getPrimaryKey(), mdrRuleGroup);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				mdrRuleGroup.getUuid(), Long.valueOf(mdrRuleGroup.getGroupId())
			}, mdrRuleGroup);

		mdrRuleGroup.resetOriginalValues();
	}

	/**
	 * Caches the m d r rule groups in the entity cache if it is enabled.
	 *
	 * @param mdrRuleGroups the m d r rule groups
	 */
	public void cacheResult(List<MDRRuleGroup> mdrRuleGroups) {
		for (MDRRuleGroup mdrRuleGroup : mdrRuleGroups) {
			if (EntityCacheUtil.getResult(
						MDRRuleGroupModelImpl.ENTITY_CACHE_ENABLED,
						MDRRuleGroupImpl.class, mdrRuleGroup.getPrimaryKey()) == null) {
				cacheResult(mdrRuleGroup);
			}
			else {
				mdrRuleGroup.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all m d r rule groups.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(MDRRuleGroupImpl.class.getName());
		}

		EntityCacheUtil.clearCache(MDRRuleGroupImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the m d r rule group.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MDRRuleGroup mdrRuleGroup) {
		EntityCacheUtil.removeResult(MDRRuleGroupModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleGroupImpl.class, mdrRuleGroup.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(mdrRuleGroup);
	}

	@Override
	public void clearCache(List<MDRRuleGroup> mdrRuleGroups) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MDRRuleGroup mdrRuleGroup : mdrRuleGroups) {
			EntityCacheUtil.removeResult(MDRRuleGroupModelImpl.ENTITY_CACHE_ENABLED,
				MDRRuleGroupImpl.class, mdrRuleGroup.getPrimaryKey());

			clearUniqueFindersCache(mdrRuleGroup);
		}
	}

	protected void clearUniqueFindersCache(MDRRuleGroup mdrRuleGroup) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				mdrRuleGroup.getUuid(), Long.valueOf(mdrRuleGroup.getGroupId())
			});
	}

	/**
	 * Creates a new m d r rule group with the primary key. Does not add the m d r rule group to the database.
	 *
	 * @param ruleGroupId the primary key for the new m d r rule group
	 * @return the new m d r rule group
	 */
	public MDRRuleGroup create(long ruleGroupId) {
		MDRRuleGroup mdrRuleGroup = new MDRRuleGroupImpl();

		mdrRuleGroup.setNew(true);
		mdrRuleGroup.setPrimaryKey(ruleGroupId);

		String uuid = PortalUUIDUtil.generate();

		mdrRuleGroup.setUuid(uuid);

		return mdrRuleGroup;
	}

	/**
	 * Removes the m d r rule group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ruleGroupId the primary key of the m d r rule group
	 * @return the m d r rule group that was removed
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a m d r rule group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup remove(long ruleGroupId)
		throws NoSuchRuleGroupException, SystemException {
		return remove(Long.valueOf(ruleGroupId));
	}

	/**
	 * Removes the m d r rule group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the m d r rule group
	 * @return the m d r rule group that was removed
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a m d r rule group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MDRRuleGroup remove(Serializable primaryKey)
		throws NoSuchRuleGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MDRRuleGroup mdrRuleGroup = (MDRRuleGroup)session.get(MDRRuleGroupImpl.class,
					primaryKey);

			if (mdrRuleGroup == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRuleGroupException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(mdrRuleGroup);
		}
		catch (NoSuchRuleGroupException nsee) {
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
	protected MDRRuleGroup removeImpl(MDRRuleGroup mdrRuleGroup)
		throws SystemException {
		mdrRuleGroup = toUnwrappedModel(mdrRuleGroup);

		Session session = null;

		try {
			session = openSession();

			if (mdrRuleGroup.isCachedModel()) {
				mdrRuleGroup = (MDRRuleGroup)session.get(MDRRuleGroupImpl.class,
						mdrRuleGroup.getPrimaryKeyObj());
			}

			if (mdrRuleGroup != null) {
				session.delete(mdrRuleGroup);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (mdrRuleGroup != null) {
			clearCache(mdrRuleGroup);
		}

		return mdrRuleGroup;
	}

	@Override
	public MDRRuleGroup updateImpl(
		com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup mdrRuleGroup)
		throws SystemException {
		mdrRuleGroup = toUnwrappedModel(mdrRuleGroup);

		boolean isNew = mdrRuleGroup.isNew();

		MDRRuleGroupModelImpl mdrRuleGroupModelImpl = (MDRRuleGroupModelImpl)mdrRuleGroup;

		if (Validator.isNull(mdrRuleGroup.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			mdrRuleGroup.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (mdrRuleGroup.isNew()) {
				session.save(mdrRuleGroup);

				mdrRuleGroup.setNew(false);
			}
			else {
				session.merge(mdrRuleGroup);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !MDRRuleGroupModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((mdrRuleGroupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mdrRuleGroupModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { mdrRuleGroupModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((mdrRuleGroupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mdrRuleGroupModelImpl.getOriginalUuid(),
						Long.valueOf(mdrRuleGroupModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						mdrRuleGroupModelImpl.getUuid(),
						Long.valueOf(mdrRuleGroupModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((mdrRuleGroupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mdrRuleGroupModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(mdrRuleGroupModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}
		}

		EntityCacheUtil.putResult(MDRRuleGroupModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleGroupImpl.class, mdrRuleGroup.getPrimaryKey(), mdrRuleGroup);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					mdrRuleGroup.getUuid(),
					Long.valueOf(mdrRuleGroup.getGroupId())
				}, mdrRuleGroup);
		}
		else {
			if ((mdrRuleGroupModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mdrRuleGroupModelImpl.getOriginalUuid(),
						Long.valueOf(mdrRuleGroupModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						mdrRuleGroup.getUuid(),
						Long.valueOf(mdrRuleGroup.getGroupId())
					}, mdrRuleGroup);
			}
		}

		return mdrRuleGroup;
	}

	protected MDRRuleGroup toUnwrappedModel(MDRRuleGroup mdrRuleGroup) {
		if (mdrRuleGroup instanceof MDRRuleGroupImpl) {
			return mdrRuleGroup;
		}

		MDRRuleGroupImpl mdrRuleGroupImpl = new MDRRuleGroupImpl();

		mdrRuleGroupImpl.setNew(mdrRuleGroup.isNew());
		mdrRuleGroupImpl.setPrimaryKey(mdrRuleGroup.getPrimaryKey());

		mdrRuleGroupImpl.setUuid(mdrRuleGroup.getUuid());
		mdrRuleGroupImpl.setRuleGroupId(mdrRuleGroup.getRuleGroupId());
		mdrRuleGroupImpl.setGroupId(mdrRuleGroup.getGroupId());
		mdrRuleGroupImpl.setCompanyId(mdrRuleGroup.getCompanyId());
		mdrRuleGroupImpl.setUserId(mdrRuleGroup.getUserId());
		mdrRuleGroupImpl.setUserName(mdrRuleGroup.getUserName());
		mdrRuleGroupImpl.setCreateDate(mdrRuleGroup.getCreateDate());
		mdrRuleGroupImpl.setModifiedDate(mdrRuleGroup.getModifiedDate());
		mdrRuleGroupImpl.setName(mdrRuleGroup.getName());
		mdrRuleGroupImpl.setDescription(mdrRuleGroup.getDescription());

		return mdrRuleGroupImpl;
	}

	/**
	 * Returns the m d r rule group with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the m d r rule group
	 * @return the m d r rule group
	 * @throws com.liferay.portal.NoSuchModelException if a m d r rule group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MDRRuleGroup findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the m d r rule group with the primary key or throws a {@link com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException} if it could not be found.
	 *
	 * @param ruleGroupId the primary key of the m d r rule group
	 * @return the m d r rule group
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a m d r rule group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup findByPrimaryKey(long ruleGroupId)
		throws NoSuchRuleGroupException, SystemException {
		MDRRuleGroup mdrRuleGroup = fetchByPrimaryKey(ruleGroupId);

		if (mdrRuleGroup == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + ruleGroupId);
			}

			throw new NoSuchRuleGroupException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				ruleGroupId);
		}

		return mdrRuleGroup;
	}

	/**
	 * Returns the m d r rule group with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the m d r rule group
	 * @return the m d r rule group, or <code>null</code> if a m d r rule group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MDRRuleGroup fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the m d r rule group with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ruleGroupId the primary key of the m d r rule group
	 * @return the m d r rule group, or <code>null</code> if a m d r rule group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup fetchByPrimaryKey(long ruleGroupId)
		throws SystemException {
		MDRRuleGroup mdrRuleGroup = (MDRRuleGroup)EntityCacheUtil.getResult(MDRRuleGroupModelImpl.ENTITY_CACHE_ENABLED,
				MDRRuleGroupImpl.class, ruleGroupId);

		if (mdrRuleGroup == _nullMDRRuleGroup) {
			return null;
		}

		if (mdrRuleGroup == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				mdrRuleGroup = (MDRRuleGroup)session.get(MDRRuleGroupImpl.class,
						Long.valueOf(ruleGroupId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (mdrRuleGroup != null) {
					cacheResult(mdrRuleGroup);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(MDRRuleGroupModelImpl.ENTITY_CACHE_ENABLED,
						MDRRuleGroupImpl.class, ruleGroupId, _nullMDRRuleGroup);
				}

				closeSession(session);
			}
		}

		return mdrRuleGroup;
	}

	/**
	 * Returns all the m d r rule groups where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching m d r rule groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRuleGroup> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the m d r rule groups where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of m d r rule groups
	 * @param end the upper bound of the range of m d r rule groups (not inclusive)
	 * @return the range of matching m d r rule groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRuleGroup> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the m d r rule groups where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of m d r rule groups
	 * @param end the upper bound of the range of m d r rule groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching m d r rule groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRuleGroup> findByUuid(String uuid, int start, int end,
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

		List<MDRRuleGroup> list = (List<MDRRuleGroup>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MDRRuleGroup mdrRuleGroup : list) {
				if (!Validator.equals(uuid, mdrRuleGroup.getUuid())) {
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

			query.append(_SQL_SELECT_MDRRULEGROUP_WHERE);

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

				list = (List<MDRRuleGroup>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first m d r rule group in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r rule group
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a matching m d r rule group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchRuleGroupException, SystemException {
		MDRRuleGroup mdrRuleGroup = fetchByUuid_First(uuid, orderByComparator);

		if (mdrRuleGroup != null) {
			return mdrRuleGroup;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRuleGroupException(msg.toString());
	}

	/**
	 * Returns the first m d r rule group in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r rule group, or <code>null</code> if a matching m d r rule group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<MDRRuleGroup> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last m d r rule group in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r rule group
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a matching m d r rule group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchRuleGroupException, SystemException {
		MDRRuleGroup mdrRuleGroup = fetchByUuid_Last(uuid, orderByComparator);

		if (mdrRuleGroup != null) {
			return mdrRuleGroup;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRuleGroupException(msg.toString());
	}

	/**
	 * Returns the last m d r rule group in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r rule group, or <code>null</code> if a matching m d r rule group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<MDRRuleGroup> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the m d r rule groups before and after the current m d r rule group in the ordered set where uuid = &#63;.
	 *
	 * @param ruleGroupId the primary key of the current m d r rule group
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next m d r rule group
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a m d r rule group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup[] findByUuid_PrevAndNext(long ruleGroupId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchRuleGroupException, SystemException {
		MDRRuleGroup mdrRuleGroup = findByPrimaryKey(ruleGroupId);

		Session session = null;

		try {
			session = openSession();

			MDRRuleGroup[] array = new MDRRuleGroupImpl[3];

			array[0] = getByUuid_PrevAndNext(session, mdrRuleGroup, uuid,
					orderByComparator, true);

			array[1] = mdrRuleGroup;

			array[2] = getByUuid_PrevAndNext(session, mdrRuleGroup, uuid,
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

	protected MDRRuleGroup getByUuid_PrevAndNext(Session session,
		MDRRuleGroup mdrRuleGroup, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MDRRULEGROUP_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(mdrRuleGroup);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MDRRuleGroup> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the m d r rule group where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching m d r rule group
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a matching m d r rule group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup findByUUID_G(String uuid, long groupId)
		throws NoSuchRuleGroupException, SystemException {
		MDRRuleGroup mdrRuleGroup = fetchByUUID_G(uuid, groupId);

		if (mdrRuleGroup == null) {
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

			throw new NoSuchRuleGroupException(msg.toString());
		}

		return mdrRuleGroup;
	}

	/**
	 * Returns the m d r rule group where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching m d r rule group, or <code>null</code> if a matching m d r rule group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the m d r rule group where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching m d r rule group, or <code>null</code> if a matching m d r rule group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof MDRRuleGroup) {
			MDRRuleGroup mdrRuleGroup = (MDRRuleGroup)result;

			if (!Validator.equals(uuid, mdrRuleGroup.getUuid()) ||
					(groupId != mdrRuleGroup.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_MDRRULEGROUP_WHERE);

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

				List<MDRRuleGroup> list = q.list();

				result = list;

				MDRRuleGroup mdrRuleGroup = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					mdrRuleGroup = list.get(0);

					cacheResult(mdrRuleGroup);

					if ((mdrRuleGroup.getUuid() == null) ||
							!mdrRuleGroup.getUuid().equals(uuid) ||
							(mdrRuleGroup.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, mdrRuleGroup);
					}
				}

				return mdrRuleGroup;
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
				return (MDRRuleGroup)result;
			}
		}
	}

	/**
	 * Returns all the m d r rule groups where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching m d r rule groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRuleGroup> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the m d r rule groups where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of m d r rule groups
	 * @param end the upper bound of the range of m d r rule groups (not inclusive)
	 * @return the range of matching m d r rule groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRuleGroup> findByUuid_C(String uuid, long companyId,
		int start, int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the m d r rule groups where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of m d r rule groups
	 * @param end the upper bound of the range of m d r rule groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching m d r rule groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRuleGroup> findByUuid_C(String uuid, long companyId,
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

		List<MDRRuleGroup> list = (List<MDRRuleGroup>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MDRRuleGroup mdrRuleGroup : list) {
				if (!Validator.equals(uuid, mdrRuleGroup.getUuid()) ||
						(companyId != mdrRuleGroup.getCompanyId())) {
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
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_MDRRULEGROUP_WHERE);

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

				list = (List<MDRRuleGroup>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first m d r rule group in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r rule group
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a matching m d r rule group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRuleGroupException, SystemException {
		MDRRuleGroup mdrRuleGroup = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (mdrRuleGroup != null) {
			return mdrRuleGroup;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRuleGroupException(msg.toString());
	}

	/**
	 * Returns the first m d r rule group in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r rule group, or <code>null</code> if a matching m d r rule group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MDRRuleGroup> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last m d r rule group in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r rule group
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a matching m d r rule group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRuleGroupException, SystemException {
		MDRRuleGroup mdrRuleGroup = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (mdrRuleGroup != null) {
			return mdrRuleGroup;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRuleGroupException(msg.toString());
	}

	/**
	 * Returns the last m d r rule group in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r rule group, or <code>null</code> if a matching m d r rule group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<MDRRuleGroup> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the m d r rule groups before and after the current m d r rule group in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param ruleGroupId the primary key of the current m d r rule group
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next m d r rule group
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a m d r rule group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup[] findByUuid_C_PrevAndNext(long ruleGroupId,
		String uuid, long companyId, OrderByComparator orderByComparator)
		throws NoSuchRuleGroupException, SystemException {
		MDRRuleGroup mdrRuleGroup = findByPrimaryKey(ruleGroupId);

		Session session = null;

		try {
			session = openSession();

			MDRRuleGroup[] array = new MDRRuleGroupImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, mdrRuleGroup, uuid,
					companyId, orderByComparator, true);

			array[1] = mdrRuleGroup;

			array[2] = getByUuid_C_PrevAndNext(session, mdrRuleGroup, uuid,
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

	protected MDRRuleGroup getByUuid_C_PrevAndNext(Session session,
		MDRRuleGroup mdrRuleGroup, String uuid, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MDRRULEGROUP_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(mdrRuleGroup);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MDRRuleGroup> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the m d r rule groups where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching m d r rule groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRuleGroup> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the m d r rule groups where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of m d r rule groups
	 * @param end the upper bound of the range of m d r rule groups (not inclusive)
	 * @return the range of matching m d r rule groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRuleGroup> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the m d r rule groups where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of m d r rule groups
	 * @param end the upper bound of the range of m d r rule groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching m d r rule groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRuleGroup> findByGroupId(long groupId, int start, int end,
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

		List<MDRRuleGroup> list = (List<MDRRuleGroup>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MDRRuleGroup mdrRuleGroup : list) {
				if ((groupId != mdrRuleGroup.getGroupId())) {
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

			query.append(_SQL_SELECT_MDRRULEGROUP_WHERE);

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

				list = (List<MDRRuleGroup>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first m d r rule group in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r rule group
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a matching m d r rule group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchRuleGroupException, SystemException {
		MDRRuleGroup mdrRuleGroup = fetchByGroupId_First(groupId,
				orderByComparator);

		if (mdrRuleGroup != null) {
			return mdrRuleGroup;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRuleGroupException(msg.toString());
	}

	/**
	 * Returns the first m d r rule group in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r rule group, or <code>null</code> if a matching m d r rule group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MDRRuleGroup> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last m d r rule group in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r rule group
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a matching m d r rule group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchRuleGroupException, SystemException {
		MDRRuleGroup mdrRuleGroup = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (mdrRuleGroup != null) {
			return mdrRuleGroup;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRuleGroupException(msg.toString());
	}

	/**
	 * Returns the last m d r rule group in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r rule group, or <code>null</code> if a matching m d r rule group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<MDRRuleGroup> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the m d r rule groups before and after the current m d r rule group in the ordered set where groupId = &#63;.
	 *
	 * @param ruleGroupId the primary key of the current m d r rule group
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next m d r rule group
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a m d r rule group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup[] findByGroupId_PrevAndNext(long ruleGroupId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchRuleGroupException, SystemException {
		MDRRuleGroup mdrRuleGroup = findByPrimaryKey(ruleGroupId);

		Session session = null;

		try {
			session = openSession();

			MDRRuleGroup[] array = new MDRRuleGroupImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, mdrRuleGroup, groupId,
					orderByComparator, true);

			array[1] = mdrRuleGroup;

			array[2] = getByGroupId_PrevAndNext(session, mdrRuleGroup, groupId,
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

	protected MDRRuleGroup getByGroupId_PrevAndNext(Session session,
		MDRRuleGroup mdrRuleGroup, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MDRRULEGROUP_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(mdrRuleGroup);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MDRRuleGroup> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the m d r rule groups that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching m d r rule groups that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRuleGroup> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the m d r rule groups that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of m d r rule groups
	 * @param end the upper bound of the range of m d r rule groups (not inclusive)
	 * @return the range of matching m d r rule groups that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRuleGroup> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the m d r rule groups that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of m d r rule groups
	 * @param end the upper bound of the range of m d r rule groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching m d r rule groups that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRuleGroup> filterFindByGroupId(long groupId, int start,
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
			query = new StringBundler(2);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MDRRULEGROUP_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MDRRULEGROUP_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MDRRULEGROUP_NO_INLINE_DISTINCT_WHERE_2);
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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MDRRuleGroup.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MDRRuleGroupImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MDRRuleGroupImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<MDRRuleGroup>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the m d r rule groups before and after the current m d r rule group in the ordered set of m d r rule groups that the user has permission to view where groupId = &#63;.
	 *
	 * @param ruleGroupId the primary key of the current m d r rule group
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next m d r rule group
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a m d r rule group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup[] filterFindByGroupId_PrevAndNext(long ruleGroupId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchRuleGroupException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(ruleGroupId, groupId,
				orderByComparator);
		}

		MDRRuleGroup mdrRuleGroup = findByPrimaryKey(ruleGroupId);

		Session session = null;

		try {
			session = openSession();

			MDRRuleGroup[] array = new MDRRuleGroupImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, mdrRuleGroup,
					groupId, orderByComparator, true);

			array[1] = mdrRuleGroup;

			array[2] = filterGetByGroupId_PrevAndNext(session, mdrRuleGroup,
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

	protected MDRRuleGroup filterGetByGroupId_PrevAndNext(Session session,
		MDRRuleGroup mdrRuleGroup, long groupId,
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
			query.append(_FILTER_SQL_SELECT_MDRRULEGROUP_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MDRRULEGROUP_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MDRRULEGROUP_NO_INLINE_DISTINCT_WHERE_2);
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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MDRRuleGroup.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MDRRuleGroupImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MDRRuleGroupImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mdrRuleGroup);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MDRRuleGroup> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the m d r rule groups.
	 *
	 * @return the m d r rule groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRuleGroup> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the m d r rule groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of m d r rule groups
	 * @param end the upper bound of the range of m d r rule groups (not inclusive)
	 * @return the range of m d r rule groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRuleGroup> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the m d r rule groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of m d r rule groups
	 * @param end the upper bound of the range of m d r rule groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of m d r rule groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRuleGroup> findAll(int start, int end,
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

		List<MDRRuleGroup> list = (List<MDRRuleGroup>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_MDRRULEGROUP);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MDRRULEGROUP;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<MDRRuleGroup>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<MDRRuleGroup>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the m d r rule groups where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (MDRRuleGroup mdrRuleGroup : findByUuid(uuid)) {
			remove(mdrRuleGroup);
		}
	}

	/**
	 * Removes the m d r rule group where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the m d r rule group that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRuleGroup removeByUUID_G(String uuid, long groupId)
		throws NoSuchRuleGroupException, SystemException {
		MDRRuleGroup mdrRuleGroup = findByUUID_G(uuid, groupId);

		return remove(mdrRuleGroup);
	}

	/**
	 * Removes all the m d r rule groups where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (MDRRuleGroup mdrRuleGroup : findByUuid_C(uuid, companyId)) {
			remove(mdrRuleGroup);
		}
	}

	/**
	 * Removes all the m d r rule groups where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (MDRRuleGroup mdrRuleGroup : findByGroupId(groupId)) {
			remove(mdrRuleGroup);
		}
	}

	/**
	 * Removes all the m d r rule groups from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (MDRRuleGroup mdrRuleGroup : findAll()) {
			remove(mdrRuleGroup);
		}
	}

	/**
	 * Returns the number of m d r rule groups where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching m d r rule groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MDRRULEGROUP_WHERE);

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
	 * Returns the number of m d r rule groups where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching m d r rule groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MDRRULEGROUP_WHERE);

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
	 * Returns the number of m d r rule groups where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching m d r rule groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MDRRULEGROUP_WHERE);

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
	 * Returns the number of m d r rule groups where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching m d r rule groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MDRRULEGROUP_WHERE);

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
	 * Returns the number of m d r rule groups that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching m d r rule groups that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_MDRRULEGROUP_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MDRRuleGroup.class.getName(),
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
	 * Returns the number of m d r rule groups.
	 *
	 * @return the number of m d r rule groups
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MDRRULEGROUP);

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
	 * Initializes the m d r rule group persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<MDRRuleGroup>> listenersList = new ArrayList<ModelListener<MDRRuleGroup>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<MDRRuleGroup>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(MDRRuleGroupImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = MDRActionPersistence.class)
	protected MDRActionPersistence mdrActionPersistence;
	@BeanReference(type = MDRRulePersistence.class)
	protected MDRRulePersistence mdrRulePersistence;
	@BeanReference(type = MDRRuleGroupPersistence.class)
	protected MDRRuleGroupPersistence mdrRuleGroupPersistence;
	@BeanReference(type = MDRRuleGroupInstancePersistence.class)
	protected MDRRuleGroupInstancePersistence mdrRuleGroupInstancePersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_MDRRULEGROUP = "SELECT mdrRuleGroup FROM MDRRuleGroup mdrRuleGroup";
	private static final String _SQL_SELECT_MDRRULEGROUP_WHERE = "SELECT mdrRuleGroup FROM MDRRuleGroup mdrRuleGroup WHERE ";
	private static final String _SQL_COUNT_MDRRULEGROUP = "SELECT COUNT(mdrRuleGroup) FROM MDRRuleGroup mdrRuleGroup";
	private static final String _SQL_COUNT_MDRRULEGROUP_WHERE = "SELECT COUNT(mdrRuleGroup) FROM MDRRuleGroup mdrRuleGroup WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "mdrRuleGroup.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "mdrRuleGroup.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(mdrRuleGroup.uuid IS NULL OR mdrRuleGroup.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "mdrRuleGroup.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "mdrRuleGroup.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(mdrRuleGroup.uuid IS NULL OR mdrRuleGroup.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "mdrRuleGroup.groupId = ?";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "mdrRuleGroup.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "mdrRuleGroup.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(mdrRuleGroup.uuid IS NULL OR mdrRuleGroup.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "mdrRuleGroup.companyId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "mdrRuleGroup.groupId = ?";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "mdrRuleGroup.ruleGroupId";
	private static final String _FILTER_SQL_SELECT_MDRRULEGROUP_WHERE = "SELECT DISTINCT {mdrRuleGroup.*} FROM MDRRuleGroup mdrRuleGroup WHERE ";
	private static final String _FILTER_SQL_SELECT_MDRRULEGROUP_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {MDRRuleGroup.*} FROM (SELECT DISTINCT mdrRuleGroup.ruleGroupId FROM MDRRuleGroup mdrRuleGroup WHERE ";
	private static final String _FILTER_SQL_SELECT_MDRRULEGROUP_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN MDRRuleGroup ON TEMP_TABLE.ruleGroupId = MDRRuleGroup.ruleGroupId";
	private static final String _FILTER_SQL_COUNT_MDRRULEGROUP_WHERE = "SELECT COUNT(DISTINCT mdrRuleGroup.ruleGroupId) AS COUNT_VALUE FROM MDRRuleGroup mdrRuleGroup WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "mdrRuleGroup";
	private static final String _FILTER_ENTITY_TABLE = "MDRRuleGroup";
	private static final String _ORDER_BY_ENTITY_ALIAS = "mdrRuleGroup.";
	private static final String _ORDER_BY_ENTITY_TABLE = "MDRRuleGroup.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MDRRuleGroup exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MDRRuleGroup exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(MDRRuleGroupPersistenceImpl.class);
	private static MDRRuleGroup _nullMDRRuleGroup = new MDRRuleGroupImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<MDRRuleGroup> toCacheModel() {
				return _nullMDRRuleGroupCacheModel;
			}
		};

	private static CacheModel<MDRRuleGroup> _nullMDRRuleGroupCacheModel = new CacheModel<MDRRuleGroup>() {
			public MDRRuleGroup toEntityModel() {
				return _nullMDRRuleGroup;
			}
		};
}