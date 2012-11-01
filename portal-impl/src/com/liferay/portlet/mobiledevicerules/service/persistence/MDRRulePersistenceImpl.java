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

import com.liferay.portlet.mobiledevicerules.NoSuchRuleException;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;
import com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleImpl;
import com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the m d r rule service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see MDRRulePersistence
 * @see MDRRuleUtil
 * @generated
 */
public class MDRRulePersistenceImpl extends BasePersistenceImpl<MDRRule>
	implements MDRRulePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link MDRRuleUtil} to access the m d r rule persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = MDRRuleImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, MDRRuleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, MDRRuleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			MDRRuleModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, MDRRuleImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			MDRRuleModelImpl.UUID_COLUMN_BITMASK |
			MDRRuleModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, MDRRuleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, MDRRuleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			MDRRuleModelImpl.UUID_COLUMN_BITMASK |
			MDRRuleModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_RULEGROUPID =
		new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, MDRRuleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRuleGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RULEGROUPID =
		new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, MDRRuleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRuleGroupId",
			new String[] { Long.class.getName() },
			MDRRuleModelImpl.RULEGROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_RULEGROUPID = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRuleGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, MDRRuleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, MDRRuleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the m d r rule in the entity cache if it is enabled.
	 *
	 * @param mdrRule the m d r rule
	 */
	public void cacheResult(MDRRule mdrRule) {
		EntityCacheUtil.putResult(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleImpl.class, mdrRule.getPrimaryKey(), mdrRule);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { mdrRule.getUuid(), Long.valueOf(mdrRule.getGroupId()) },
			mdrRule);

		mdrRule.resetOriginalValues();
	}

	/**
	 * Caches the m d r rules in the entity cache if it is enabled.
	 *
	 * @param mdrRules the m d r rules
	 */
	public void cacheResult(List<MDRRule> mdrRules) {
		for (MDRRule mdrRule : mdrRules) {
			if (EntityCacheUtil.getResult(
						MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
						MDRRuleImpl.class, mdrRule.getPrimaryKey()) == null) {
				cacheResult(mdrRule);
			}
			else {
				mdrRule.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all m d r rules.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(MDRRuleImpl.class.getName());
		}

		EntityCacheUtil.clearCache(MDRRuleImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the m d r rule.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MDRRule mdrRule) {
		EntityCacheUtil.removeResult(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleImpl.class, mdrRule.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(mdrRule);
	}

	@Override
	public void clearCache(List<MDRRule> mdrRules) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MDRRule mdrRule : mdrRules) {
			EntityCacheUtil.removeResult(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
				MDRRuleImpl.class, mdrRule.getPrimaryKey());

			clearUniqueFindersCache(mdrRule);
		}
	}

	protected void clearUniqueFindersCache(MDRRule mdrRule) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { mdrRule.getUuid(), Long.valueOf(mdrRule.getGroupId()) });
	}

	/**
	 * Creates a new m d r rule with the primary key. Does not add the m d r rule to the database.
	 *
	 * @param ruleId the primary key for the new m d r rule
	 * @return the new m d r rule
	 */
	public MDRRule create(long ruleId) {
		MDRRule mdrRule = new MDRRuleImpl();

		mdrRule.setNew(true);
		mdrRule.setPrimaryKey(ruleId);

		String uuid = PortalUUIDUtil.generate();

		mdrRule.setUuid(uuid);

		return mdrRule;
	}

	/**
	 * Removes the m d r rule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ruleId the primary key of the m d r rule
	 * @return the m d r rule that was removed
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a m d r rule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule remove(long ruleId)
		throws NoSuchRuleException, SystemException {
		return remove(Long.valueOf(ruleId));
	}

	/**
	 * Removes the m d r rule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the m d r rule
	 * @return the m d r rule that was removed
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a m d r rule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MDRRule remove(Serializable primaryKey)
		throws NoSuchRuleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MDRRule mdrRule = (MDRRule)session.get(MDRRuleImpl.class, primaryKey);

			if (mdrRule == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRuleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(mdrRule);
		}
		catch (NoSuchRuleException nsee) {
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
	protected MDRRule removeImpl(MDRRule mdrRule) throws SystemException {
		mdrRule = toUnwrappedModel(mdrRule);

		Session session = null;

		try {
			session = openSession();

			if (mdrRule.isCachedModel()) {
				mdrRule = (MDRRule)session.get(MDRRuleImpl.class,
						mdrRule.getPrimaryKeyObj());
			}

			if (mdrRule != null) {
				session.delete(mdrRule);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (mdrRule != null) {
			clearCache(mdrRule);
		}

		return mdrRule;
	}

	@Override
	public MDRRule updateImpl(
		com.liferay.portlet.mobiledevicerules.model.MDRRule mdrRule)
		throws SystemException {
		mdrRule = toUnwrappedModel(mdrRule);

		boolean isNew = mdrRule.isNew();

		MDRRuleModelImpl mdrRuleModelImpl = (MDRRuleModelImpl)mdrRule;

		if (Validator.isNull(mdrRule.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			mdrRule.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (mdrRule.isNew()) {
				session.save(mdrRule);

				mdrRule.setNew(false);
			}
			else {
				session.merge(mdrRule);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !MDRRuleModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((mdrRuleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { mdrRuleModelImpl.getOriginalUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { mdrRuleModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((mdrRuleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mdrRuleModelImpl.getOriginalUuid(),
						Long.valueOf(mdrRuleModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						mdrRuleModelImpl.getUuid(),
						Long.valueOf(mdrRuleModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((mdrRuleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RULEGROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mdrRuleModelImpl.getOriginalRuleGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_RULEGROUPID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RULEGROUPID,
					args);

				args = new Object[] {
						Long.valueOf(mdrRuleModelImpl.getRuleGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_RULEGROUPID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RULEGROUPID,
					args);
			}
		}

		EntityCacheUtil.putResult(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleImpl.class, mdrRule.getPrimaryKey(), mdrRule);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					mdrRule.getUuid(), Long.valueOf(mdrRule.getGroupId())
				}, mdrRule);
		}
		else {
			if ((mdrRuleModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mdrRuleModelImpl.getOriginalUuid(),
						Long.valueOf(mdrRuleModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						mdrRule.getUuid(), Long.valueOf(mdrRule.getGroupId())
					}, mdrRule);
			}
		}

		return mdrRule;
	}

	protected MDRRule toUnwrappedModel(MDRRule mdrRule) {
		if (mdrRule instanceof MDRRuleImpl) {
			return mdrRule;
		}

		MDRRuleImpl mdrRuleImpl = new MDRRuleImpl();

		mdrRuleImpl.setNew(mdrRule.isNew());
		mdrRuleImpl.setPrimaryKey(mdrRule.getPrimaryKey());

		mdrRuleImpl.setUuid(mdrRule.getUuid());
		mdrRuleImpl.setRuleId(mdrRule.getRuleId());
		mdrRuleImpl.setGroupId(mdrRule.getGroupId());
		mdrRuleImpl.setCompanyId(mdrRule.getCompanyId());
		mdrRuleImpl.setUserId(mdrRule.getUserId());
		mdrRuleImpl.setUserName(mdrRule.getUserName());
		mdrRuleImpl.setCreateDate(mdrRule.getCreateDate());
		mdrRuleImpl.setModifiedDate(mdrRule.getModifiedDate());
		mdrRuleImpl.setRuleGroupId(mdrRule.getRuleGroupId());
		mdrRuleImpl.setName(mdrRule.getName());
		mdrRuleImpl.setDescription(mdrRule.getDescription());
		mdrRuleImpl.setType(mdrRule.getType());
		mdrRuleImpl.setTypeSettings(mdrRule.getTypeSettings());

		return mdrRuleImpl;
	}

	/**
	 * Returns the m d r rule with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the m d r rule
	 * @return the m d r rule
	 * @throws com.liferay.portal.NoSuchModelException if a m d r rule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MDRRule findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the m d r rule with the primary key or throws a {@link com.liferay.portlet.mobiledevicerules.NoSuchRuleException} if it could not be found.
	 *
	 * @param ruleId the primary key of the m d r rule
	 * @return the m d r rule
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a m d r rule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule findByPrimaryKey(long ruleId)
		throws NoSuchRuleException, SystemException {
		MDRRule mdrRule = fetchByPrimaryKey(ruleId);

		if (mdrRule == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + ruleId);
			}

			throw new NoSuchRuleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				ruleId);
		}

		return mdrRule;
	}

	/**
	 * Returns the m d r rule with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the m d r rule
	 * @return the m d r rule, or <code>null</code> if a m d r rule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MDRRule fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the m d r rule with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ruleId the primary key of the m d r rule
	 * @return the m d r rule, or <code>null</code> if a m d r rule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule fetchByPrimaryKey(long ruleId) throws SystemException {
		MDRRule mdrRule = (MDRRule)EntityCacheUtil.getResult(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
				MDRRuleImpl.class, ruleId);

		if (mdrRule == _nullMDRRule) {
			return null;
		}

		if (mdrRule == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				mdrRule = (MDRRule)session.get(MDRRuleImpl.class,
						Long.valueOf(ruleId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (mdrRule != null) {
					cacheResult(mdrRule);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
						MDRRuleImpl.class, ruleId, _nullMDRRule);
				}

				closeSession(session);
			}
		}

		return mdrRule;
	}

	/**
	 * Returns all the m d r rules where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching m d r rules
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRule> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the m d r rules where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of m d r rules
	 * @param end the upper bound of the range of m d r rules (not inclusive)
	 * @return the range of matching m d r rules
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRule> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the m d r rules where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of m d r rules
	 * @param end the upper bound of the range of m d r rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching m d r rules
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRule> findByUuid(String uuid, int start, int end,
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

		List<MDRRule> list = (List<MDRRule>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MDRRule mdrRule : list) {
				if (!Validator.equals(uuid, mdrRule.getUuid())) {
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

			query.append(_SQL_SELECT_MDRRULE_WHERE);

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

				list = (List<MDRRule>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first m d r rule in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r rule
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a matching m d r rule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchRuleException, SystemException {
		MDRRule mdrRule = fetchByUuid_First(uuid, orderByComparator);

		if (mdrRule != null) {
			return mdrRule;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRuleException(msg.toString());
	}

	/**
	 * Returns the first m d r rule in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<MDRRule> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last m d r rule in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r rule
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a matching m d r rule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchRuleException, SystemException {
		MDRRule mdrRule = fetchByUuid_Last(uuid, orderByComparator);

		if (mdrRule != null) {
			return mdrRule;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRuleException(msg.toString());
	}

	/**
	 * Returns the last m d r rule in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<MDRRule> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the m d r rules before and after the current m d r rule in the ordered set where uuid = &#63;.
	 *
	 * @param ruleId the primary key of the current m d r rule
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next m d r rule
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a m d r rule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule[] findByUuid_PrevAndNext(long ruleId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchRuleException, SystemException {
		MDRRule mdrRule = findByPrimaryKey(ruleId);

		Session session = null;

		try {
			session = openSession();

			MDRRule[] array = new MDRRuleImpl[3];

			array[0] = getByUuid_PrevAndNext(session, mdrRule, uuid,
					orderByComparator, true);

			array[1] = mdrRule;

			array[2] = getByUuid_PrevAndNext(session, mdrRule, uuid,
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

	protected MDRRule getByUuid_PrevAndNext(Session session, MDRRule mdrRule,
		String uuid, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MDRRULE_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(mdrRule);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MDRRule> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the m d r rule where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.mobiledevicerules.NoSuchRuleException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching m d r rule
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a matching m d r rule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule findByUUID_G(String uuid, long groupId)
		throws NoSuchRuleException, SystemException {
		MDRRule mdrRule = fetchByUUID_G(uuid, groupId);

		if (mdrRule == null) {
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

			throw new NoSuchRuleException(msg.toString());
		}

		return mdrRule;
	}

	/**
	 * Returns the m d r rule where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the m d r rule where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof MDRRule) {
			MDRRule mdrRule = (MDRRule)result;

			if (!Validator.equals(uuid, mdrRule.getUuid()) ||
					(groupId != mdrRule.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_MDRRULE_WHERE);

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

				List<MDRRule> list = q.list();

				result = list;

				MDRRule mdrRule = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					mdrRule = list.get(0);

					cacheResult(mdrRule);

					if ((mdrRule.getUuid() == null) ||
							!mdrRule.getUuid().equals(uuid) ||
							(mdrRule.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, mdrRule);
					}
				}

				return mdrRule;
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
				return (MDRRule)result;
			}
		}
	}

	/**
	 * Returns all the m d r rules where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching m d r rules
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRule> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the m d r rules where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of m d r rules
	 * @param end the upper bound of the range of m d r rules (not inclusive)
	 * @return the range of matching m d r rules
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRule> findByUuid_C(String uuid, long companyId, int start,
		int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the m d r rules where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of m d r rules
	 * @param end the upper bound of the range of m d r rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching m d r rules
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRule> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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

		List<MDRRule> list = (List<MDRRule>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MDRRule mdrRule : list) {
				if (!Validator.equals(uuid, mdrRule.getUuid()) ||
						(companyId != mdrRule.getCompanyId())) {
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

			query.append(_SQL_SELECT_MDRRULE_WHERE);

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

				list = (List<MDRRule>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first m d r rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r rule
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a matching m d r rule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRuleException, SystemException {
		MDRRule mdrRule = fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (mdrRule != null) {
			return mdrRule;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRuleException(msg.toString());
	}

	/**
	 * Returns the first m d r rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MDRRule> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last m d r rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r rule
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a matching m d r rule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRuleException, SystemException {
		MDRRule mdrRule = fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (mdrRule != null) {
			return mdrRule;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRuleException(msg.toString());
	}

	/**
	 * Returns the last m d r rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<MDRRule> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the m d r rules before and after the current m d r rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param ruleId the primary key of the current m d r rule
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next m d r rule
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a m d r rule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule[] findByUuid_C_PrevAndNext(long ruleId, String uuid,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchRuleException, SystemException {
		MDRRule mdrRule = findByPrimaryKey(ruleId);

		Session session = null;

		try {
			session = openSession();

			MDRRule[] array = new MDRRuleImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, mdrRule, uuid,
					companyId, orderByComparator, true);

			array[1] = mdrRule;

			array[2] = getByUuid_C_PrevAndNext(session, mdrRule, uuid,
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

	protected MDRRule getByUuid_C_PrevAndNext(Session session, MDRRule mdrRule,
		String uuid, long companyId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MDRRULE_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(mdrRule);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MDRRule> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the m d r rules where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @return the matching m d r rules
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRule> findByRuleGroupId(long ruleGroupId)
		throws SystemException {
		return findByRuleGroupId(ruleGroupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the m d r rules where ruleGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param ruleGroupId the rule group ID
	 * @param start the lower bound of the range of m d r rules
	 * @param end the upper bound of the range of m d r rules (not inclusive)
	 * @return the range of matching m d r rules
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRule> findByRuleGroupId(long ruleGroupId, int start, int end)
		throws SystemException {
		return findByRuleGroupId(ruleGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the m d r rules where ruleGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param ruleGroupId the rule group ID
	 * @param start the lower bound of the range of m d r rules
	 * @param end the upper bound of the range of m d r rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching m d r rules
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRule> findByRuleGroupId(long ruleGroupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RULEGROUPID;
			finderArgs = new Object[] { ruleGroupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_RULEGROUPID;
			finderArgs = new Object[] { ruleGroupId, start, end, orderByComparator };
		}

		List<MDRRule> list = (List<MDRRule>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MDRRule mdrRule : list) {
				if ((ruleGroupId != mdrRule.getRuleGroupId())) {
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

			query.append(_SQL_SELECT_MDRRULE_WHERE);

			query.append(_FINDER_COLUMN_RULEGROUPID_RULEGROUPID_2);

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

				qPos.add(ruleGroupId);

				list = (List<MDRRule>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first m d r rule in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r rule
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a matching m d r rule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule findByRuleGroupId_First(long ruleGroupId,
		OrderByComparator orderByComparator)
		throws NoSuchRuleException, SystemException {
		MDRRule mdrRule = fetchByRuleGroupId_First(ruleGroupId,
				orderByComparator);

		if (mdrRule != null) {
			return mdrRule;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ruleGroupId=");
		msg.append(ruleGroupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRuleException(msg.toString());
	}

	/**
	 * Returns the first m d r rule in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule fetchByRuleGroupId_First(long ruleGroupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MDRRule> list = findByRuleGroupId(ruleGroupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last m d r rule in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r rule
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a matching m d r rule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule findByRuleGroupId_Last(long ruleGroupId,
		OrderByComparator orderByComparator)
		throws NoSuchRuleException, SystemException {
		MDRRule mdrRule = fetchByRuleGroupId_Last(ruleGroupId, orderByComparator);

		if (mdrRule != null) {
			return mdrRule;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ruleGroupId=");
		msg.append(ruleGroupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRuleException(msg.toString());
	}

	/**
	 * Returns the last m d r rule in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule fetchByRuleGroupId_Last(long ruleGroupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByRuleGroupId(ruleGroupId);

		List<MDRRule> list = findByRuleGroupId(ruleGroupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the m d r rules before and after the current m d r rule in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleId the primary key of the current m d r rule
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next m d r rule
	 * @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleException if a m d r rule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule[] findByRuleGroupId_PrevAndNext(long ruleId,
		long ruleGroupId, OrderByComparator orderByComparator)
		throws NoSuchRuleException, SystemException {
		MDRRule mdrRule = findByPrimaryKey(ruleId);

		Session session = null;

		try {
			session = openSession();

			MDRRule[] array = new MDRRuleImpl[3];

			array[0] = getByRuleGroupId_PrevAndNext(session, mdrRule,
					ruleGroupId, orderByComparator, true);

			array[1] = mdrRule;

			array[2] = getByRuleGroupId_PrevAndNext(session, mdrRule,
					ruleGroupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MDRRule getByRuleGroupId_PrevAndNext(Session session,
		MDRRule mdrRule, long ruleGroupId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MDRRULE_WHERE);

		query.append(_FINDER_COLUMN_RULEGROUPID_RULEGROUPID_2);

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

		qPos.add(ruleGroupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mdrRule);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MDRRule> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the m d r rules.
	 *
	 * @return the m d r rules
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRule> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the m d r rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of m d r rules
	 * @param end the upper bound of the range of m d r rules (not inclusive)
	 * @return the range of m d r rules
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRule> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the m d r rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of m d r rules
	 * @param end the upper bound of the range of m d r rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of m d r rules
	 * @throws SystemException if a system exception occurred
	 */
	public List<MDRRule> findAll(int start, int end,
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

		List<MDRRule> list = (List<MDRRule>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_MDRRULE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MDRRULE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<MDRRule>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<MDRRule>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the m d r rules where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (MDRRule mdrRule : findByUuid(uuid)) {
			remove(mdrRule);
		}
	}

	/**
	 * Removes the m d r rule where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the m d r rule that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public MDRRule removeByUUID_G(String uuid, long groupId)
		throws NoSuchRuleException, SystemException {
		MDRRule mdrRule = findByUUID_G(uuid, groupId);

		return remove(mdrRule);
	}

	/**
	 * Removes all the m d r rules where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (MDRRule mdrRule : findByUuid_C(uuid, companyId)) {
			remove(mdrRule);
		}
	}

	/**
	 * Removes all the m d r rules where ruleGroupId = &#63; from the database.
	 *
	 * @param ruleGroupId the rule group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByRuleGroupId(long ruleGroupId) throws SystemException {
		for (MDRRule mdrRule : findByRuleGroupId(ruleGroupId)) {
			remove(mdrRule);
		}
	}

	/**
	 * Removes all the m d r rules from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (MDRRule mdrRule : findAll()) {
			remove(mdrRule);
		}
	}

	/**
	 * Returns the number of m d r rules where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching m d r rules
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MDRRULE_WHERE);

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
	 * Returns the number of m d r rules where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching m d r rules
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MDRRULE_WHERE);

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
	 * Returns the number of m d r rules where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching m d r rules
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MDRRULE_WHERE);

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
	 * Returns the number of m d r rules where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @return the number of matching m d r rules
	 * @throws SystemException if a system exception occurred
	 */
	public int countByRuleGroupId(long ruleGroupId) throws SystemException {
		Object[] finderArgs = new Object[] { ruleGroupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_RULEGROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MDRRULE_WHERE);

			query.append(_FINDER_COLUMN_RULEGROUPID_RULEGROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ruleGroupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_RULEGROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of m d r rules.
	 *
	 * @return the number of m d r rules
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MDRRULE);

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
	 * Initializes the m d r rule persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.mobiledevicerules.model.MDRRule")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<MDRRule>> listenersList = new ArrayList<ModelListener<MDRRule>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<MDRRule>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(MDRRuleImpl.class.getName());
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
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_MDRRULE = "SELECT mdrRule FROM MDRRule mdrRule";
	private static final String _SQL_SELECT_MDRRULE_WHERE = "SELECT mdrRule FROM MDRRule mdrRule WHERE ";
	private static final String _SQL_COUNT_MDRRULE = "SELECT COUNT(mdrRule) FROM MDRRule mdrRule";
	private static final String _SQL_COUNT_MDRRULE_WHERE = "SELECT COUNT(mdrRule) FROM MDRRule mdrRule WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "mdrRule.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "mdrRule.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(mdrRule.uuid IS NULL OR mdrRule.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "mdrRule.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "mdrRule.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(mdrRule.uuid IS NULL OR mdrRule.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "mdrRule.groupId = ?";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "mdrRule.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "mdrRule.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(mdrRule.uuid IS NULL OR mdrRule.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "mdrRule.companyId = ?";
	private static final String _FINDER_COLUMN_RULEGROUPID_RULEGROUPID_2 = "mdrRule.ruleGroupId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "mdrRule.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MDRRule exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MDRRule exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(MDRRulePersistenceImpl.class);
	private static MDRRule _nullMDRRule = new MDRRuleImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<MDRRule> toCacheModel() {
				return _nullMDRRuleCacheModel;
			}
		};

	private static CacheModel<MDRRule> _nullMDRRuleCacheModel = new CacheModel<MDRRule>() {
			public MDRRule toEntityModel() {
				return _nullMDRRule;
			}
		};
}