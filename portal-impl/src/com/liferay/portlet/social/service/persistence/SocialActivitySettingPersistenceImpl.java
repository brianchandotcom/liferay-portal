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

package com.liferay.portlet.social.service.persistence;

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
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.social.NoSuchActivitySettingException;
import com.liferay.portlet.social.model.SocialActivitySetting;
import com.liferay.portlet.social.model.impl.SocialActivitySettingImpl;
import com.liferay.portlet.social.model.impl.SocialActivitySettingModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the social activity setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivitySettingPersistence
 * @see SocialActivitySettingUtil
 * @generated
 */
public class SocialActivitySettingPersistenceImpl extends BasePersistenceImpl<SocialActivitySetting>
	implements SocialActivitySettingPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SocialActivitySettingUtil} to access the social activity setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SocialActivitySettingImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			SocialActivitySettingModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C = new FinderPath(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C = new FinderPath(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			SocialActivitySettingModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivitySettingModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A = new FinderPath(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A = new FinderPath(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A",
			new String[] { Long.class.getName(), Integer.class.getName() },
			SocialActivitySettingModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivitySettingModelImpl.ACTIVITYTYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A = new FinderPath(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_A = new FinderPath(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_A = new FinderPath(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			SocialActivitySettingModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivitySettingModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivitySettingModelImpl.ACTIVITYTYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_A = new FinderPath(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_A_N = new FinderPath(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_A_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), String.class.getName()
			},
			SocialActivitySettingModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivitySettingModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivitySettingModelImpl.ACTIVITYTYPE_COLUMN_BITMASK |
			SocialActivitySettingModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_A_N = new FinderPath(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_A_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), String.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the social activity setting in the entity cache if it is enabled.
	 *
	 * @param socialActivitySetting the social activity setting
	 */
	public void cacheResult(SocialActivitySetting socialActivitySetting) {
		EntityCacheUtil.putResult(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			socialActivitySetting.getPrimaryKey(), socialActivitySetting);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_A_N,
			new Object[] {
				Long.valueOf(socialActivitySetting.getGroupId()),
				Long.valueOf(socialActivitySetting.getClassNameId()),
				Integer.valueOf(socialActivitySetting.getActivityType()),
				
			socialActivitySetting.getName()
			}, socialActivitySetting);

		socialActivitySetting.resetOriginalValues();
	}

	/**
	 * Caches the social activity settings in the entity cache if it is enabled.
	 *
	 * @param socialActivitySettings the social activity settings
	 */
	public void cacheResult(List<SocialActivitySetting> socialActivitySettings) {
		for (SocialActivitySetting socialActivitySetting : socialActivitySettings) {
			if (EntityCacheUtil.getResult(
						SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
						SocialActivitySettingImpl.class,
						socialActivitySetting.getPrimaryKey()) == null) {
				cacheResult(socialActivitySetting);
			}
			else {
				socialActivitySetting.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all social activity settings.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SocialActivitySettingImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SocialActivitySettingImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the social activity setting.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SocialActivitySetting socialActivitySetting) {
		EntityCacheUtil.removeResult(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			socialActivitySetting.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(socialActivitySetting);
	}

	@Override
	public void clearCache(List<SocialActivitySetting> socialActivitySettings) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SocialActivitySetting socialActivitySetting : socialActivitySettings) {
			EntityCacheUtil.removeResult(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
				SocialActivitySettingImpl.class,
				socialActivitySetting.getPrimaryKey());

			clearUniqueFindersCache(socialActivitySetting);
		}
	}

	protected void clearUniqueFindersCache(
		SocialActivitySetting socialActivitySetting) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_A_N,
			new Object[] {
				Long.valueOf(socialActivitySetting.getGroupId()),
				Long.valueOf(socialActivitySetting.getClassNameId()),
				Integer.valueOf(socialActivitySetting.getActivityType()),
				
			socialActivitySetting.getName()
			});
	}

	/**
	 * Creates a new social activity setting with the primary key. Does not add the social activity setting to the database.
	 *
	 * @param activitySettingId the primary key for the new social activity setting
	 * @return the new social activity setting
	 */
	public SocialActivitySetting create(long activitySettingId) {
		SocialActivitySetting socialActivitySetting = new SocialActivitySettingImpl();

		socialActivitySetting.setNew(true);
		socialActivitySetting.setPrimaryKey(activitySettingId);

		return socialActivitySetting;
	}

	/**
	 * Removes the social activity setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param activitySettingId the primary key of the social activity setting
	 * @return the social activity setting that was removed
	 * @throws com.liferay.portlet.social.NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting remove(long activitySettingId)
		throws NoSuchActivitySettingException, SystemException {
		return remove(Long.valueOf(activitySettingId));
	}

	/**
	 * Removes the social activity setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the social activity setting
	 * @return the social activity setting that was removed
	 * @throws com.liferay.portlet.social.NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivitySetting remove(Serializable primaryKey)
		throws NoSuchActivitySettingException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialActivitySetting socialActivitySetting = (SocialActivitySetting)session.get(SocialActivitySettingImpl.class,
					primaryKey);

			if (socialActivitySetting == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchActivitySettingException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(socialActivitySetting);
		}
		catch (NoSuchActivitySettingException nsee) {
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
	protected SocialActivitySetting removeImpl(
		SocialActivitySetting socialActivitySetting) throws SystemException {
		socialActivitySetting = toUnwrappedModel(socialActivitySetting);

		Session session = null;

		try {
			session = openSession();

			if (socialActivitySetting.isCachedModel()) {
				socialActivitySetting = (SocialActivitySetting)session.get(SocialActivitySettingImpl.class,
						socialActivitySetting.getPrimaryKeyObj());
			}

			if (socialActivitySetting != null) {
				session.delete(socialActivitySetting);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (socialActivitySetting != null) {
			clearCache(socialActivitySetting);
		}

		return socialActivitySetting;
	}

	@Override
	public SocialActivitySetting updateImpl(
		com.liferay.portlet.social.model.SocialActivitySetting socialActivitySetting)
		throws SystemException {
		socialActivitySetting = toUnwrappedModel(socialActivitySetting);

		boolean isNew = socialActivitySetting.isNew();

		SocialActivitySettingModelImpl socialActivitySettingModelImpl = (SocialActivitySettingModelImpl)socialActivitySetting;

		Session session = null;

		try {
			session = openSession();

			if (socialActivitySetting.isNew()) {
				session.save(socialActivitySetting);

				socialActivitySetting.setNew(false);
			}
			else {
				session.merge(socialActivitySetting);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SocialActivitySettingModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((socialActivitySettingModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialActivitySettingModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(socialActivitySettingModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((socialActivitySettingModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialActivitySettingModelImpl.getOriginalGroupId()),
						Long.valueOf(socialActivitySettingModelImpl.getOriginalClassNameId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);

				args = new Object[] {
						Long.valueOf(socialActivitySettingModelImpl.getGroupId()),
						Long.valueOf(socialActivitySettingModelImpl.getClassNameId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);
			}

			if ((socialActivitySettingModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialActivitySettingModelImpl.getOriginalGroupId()),
						Integer.valueOf(socialActivitySettingModelImpl.getOriginalActivityType())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);

				args = new Object[] {
						Long.valueOf(socialActivitySettingModelImpl.getGroupId()),
						Integer.valueOf(socialActivitySettingModelImpl.getActivityType())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);
			}

			if ((socialActivitySettingModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialActivitySettingModelImpl.getOriginalGroupId()),
						Long.valueOf(socialActivitySettingModelImpl.getOriginalClassNameId()),
						Integer.valueOf(socialActivitySettingModelImpl.getOriginalActivityType())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_A,
					args);

				args = new Object[] {
						Long.valueOf(socialActivitySettingModelImpl.getGroupId()),
						Long.valueOf(socialActivitySettingModelImpl.getClassNameId()),
						Integer.valueOf(socialActivitySettingModelImpl.getActivityType())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_A,
					args);
			}
		}

		EntityCacheUtil.putResult(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySettingImpl.class,
			socialActivitySetting.getPrimaryKey(), socialActivitySetting);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_A_N,
				new Object[] {
					Long.valueOf(socialActivitySetting.getGroupId()),
					Long.valueOf(socialActivitySetting.getClassNameId()),
					Integer.valueOf(socialActivitySetting.getActivityType()),
					
				socialActivitySetting.getName()
				}, socialActivitySetting);
		}
		else {
			if ((socialActivitySettingModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_C_A_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialActivitySettingModelImpl.getOriginalGroupId()),
						Long.valueOf(socialActivitySettingModelImpl.getOriginalClassNameId()),
						Integer.valueOf(socialActivitySettingModelImpl.getOriginalActivityType()),
						
						socialActivitySettingModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_A_N, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_A_N, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_A_N,
					new Object[] {
						Long.valueOf(socialActivitySetting.getGroupId()),
						Long.valueOf(socialActivitySetting.getClassNameId()),
						Integer.valueOf(socialActivitySetting.getActivityType()),
						
					socialActivitySetting.getName()
					}, socialActivitySetting);
			}
		}

		return socialActivitySetting;
	}

	protected SocialActivitySetting toUnwrappedModel(
		SocialActivitySetting socialActivitySetting) {
		if (socialActivitySetting instanceof SocialActivitySettingImpl) {
			return socialActivitySetting;
		}

		SocialActivitySettingImpl socialActivitySettingImpl = new SocialActivitySettingImpl();

		socialActivitySettingImpl.setNew(socialActivitySetting.isNew());
		socialActivitySettingImpl.setPrimaryKey(socialActivitySetting.getPrimaryKey());

		socialActivitySettingImpl.setActivitySettingId(socialActivitySetting.getActivitySettingId());
		socialActivitySettingImpl.setGroupId(socialActivitySetting.getGroupId());
		socialActivitySettingImpl.setCompanyId(socialActivitySetting.getCompanyId());
		socialActivitySettingImpl.setClassNameId(socialActivitySetting.getClassNameId());
		socialActivitySettingImpl.setActivityType(socialActivitySetting.getActivityType());
		socialActivitySettingImpl.setName(socialActivitySetting.getName());
		socialActivitySettingImpl.setValue(socialActivitySetting.getValue());

		return socialActivitySettingImpl;
	}

	/**
	 * Returns the social activity setting with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity setting
	 * @return the social activity setting
	 * @throws com.liferay.portal.NoSuchModelException if a social activity setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivitySetting findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social activity setting with the primary key or throws a {@link com.liferay.portlet.social.NoSuchActivitySettingException} if it could not be found.
	 *
	 * @param activitySettingId the primary key of the social activity setting
	 * @return the social activity setting
	 * @throws com.liferay.portlet.social.NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting findByPrimaryKey(long activitySettingId)
		throws NoSuchActivitySettingException, SystemException {
		SocialActivitySetting socialActivitySetting = fetchByPrimaryKey(activitySettingId);

		if (socialActivitySetting == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + activitySettingId);
			}

			throw new NoSuchActivitySettingException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				activitySettingId);
		}

		return socialActivitySetting;
	}

	/**
	 * Returns the social activity setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity setting
	 * @return the social activity setting, or <code>null</code> if a social activity setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivitySetting fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social activity setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param activitySettingId the primary key of the social activity setting
	 * @return the social activity setting, or <code>null</code> if a social activity setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting fetchByPrimaryKey(long activitySettingId)
		throws SystemException {
		SocialActivitySetting socialActivitySetting = (SocialActivitySetting)EntityCacheUtil.getResult(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
				SocialActivitySettingImpl.class, activitySettingId);

		if (socialActivitySetting == _nullSocialActivitySetting) {
			return null;
		}

		if (socialActivitySetting == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				socialActivitySetting = (SocialActivitySetting)session.get(SocialActivitySettingImpl.class,
						Long.valueOf(activitySettingId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (socialActivitySetting != null) {
					cacheResult(socialActivitySetting);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(SocialActivitySettingModelImpl.ENTITY_CACHE_ENABLED,
						SocialActivitySettingImpl.class, activitySettingId,
						_nullSocialActivitySetting);
				}

				closeSession(session);
			}
		}

		return socialActivitySetting;
	}

	/**
	 * Returns all the social activity settings where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching social activity settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivitySetting> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity settings where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @return the range of matching social activity settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivitySetting> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity settings where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivitySetting> findByGroupId(long groupId, int start,
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

		List<SocialActivitySetting> list = (List<SocialActivitySetting>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialActivitySetting socialActivitySetting : list) {
				if ((groupId != socialActivitySetting.getGroupId())) {
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

			query.append(_SQL_SELECT_SOCIALACTIVITYSETTING_WHERE);

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

				list = (List<SocialActivitySetting>)QueryUtil.list(q,
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
	 * Returns the first social activity setting in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity setting
	 * @throws com.liferay.portlet.social.NoSuchActivitySettingException if a matching social activity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchActivitySettingException, SystemException {
		SocialActivitySetting socialActivitySetting = fetchByGroupId_First(groupId,
				orderByComparator);

		if (socialActivitySetting != null) {
			return socialActivitySetting;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivitySettingException(msg.toString());
	}

	/**
	 * Returns the first social activity setting in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialActivitySetting> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity setting in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity setting
	 * @throws com.liferay.portlet.social.NoSuchActivitySettingException if a matching social activity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchActivitySettingException, SystemException {
		SocialActivitySetting socialActivitySetting = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (socialActivitySetting != null) {
			return socialActivitySetting;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivitySettingException(msg.toString());
	}

	/**
	 * Returns the last social activity setting in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<SocialActivitySetting> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity settings before and after the current social activity setting in the ordered set where groupId = &#63;.
	 *
	 * @param activitySettingId the primary key of the current social activity setting
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity setting
	 * @throws com.liferay.portlet.social.NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting[] findByGroupId_PrevAndNext(
		long activitySettingId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchActivitySettingException, SystemException {
		SocialActivitySetting socialActivitySetting = findByPrimaryKey(activitySettingId);

		Session session = null;

		try {
			session = openSession();

			SocialActivitySetting[] array = new SocialActivitySettingImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, socialActivitySetting,
					groupId, orderByComparator, true);

			array[1] = socialActivitySetting;

			array[2] = getByGroupId_PrevAndNext(session, socialActivitySetting,
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

	protected SocialActivitySetting getByGroupId_PrevAndNext(Session session,
		SocialActivitySetting socialActivitySetting, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYSETTING_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivitySetting);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivitySetting> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social activity settings where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching social activity settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivitySetting> findByG_C(long groupId, long classNameId)
		throws SystemException {
		return findByG_C(groupId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity settings where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @return the range of matching social activity settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivitySetting> findByG_C(long groupId,
		long classNameId, int start, int end) throws SystemException {
		return findByG_C(groupId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity settings where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivitySetting> findByG_C(long groupId,
		long classNameId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] { groupId, classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] {
					groupId, classNameId,
					
					start, end, orderByComparator
				};
		}

		List<SocialActivitySetting> list = (List<SocialActivitySetting>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialActivitySetting socialActivitySetting : list) {
				if ((groupId != socialActivitySetting.getGroupId()) ||
						(classNameId != socialActivitySetting.getClassNameId())) {
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

			query.append(_SQL_SELECT_SOCIALACTIVITYSETTING_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

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

				qPos.add(classNameId);

				list = (List<SocialActivitySetting>)QueryUtil.list(q,
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
	 * Returns the first social activity setting in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity setting
	 * @throws com.liferay.portlet.social.NoSuchActivitySettingException if a matching social activity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting findByG_C_First(long groupId,
		long classNameId, OrderByComparator orderByComparator)
		throws NoSuchActivitySettingException, SystemException {
		SocialActivitySetting socialActivitySetting = fetchByG_C_First(groupId,
				classNameId, orderByComparator);

		if (socialActivitySetting != null) {
			return socialActivitySetting;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivitySettingException(msg.toString());
	}

	/**
	 * Returns the first social activity setting in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting fetchByG_C_First(long groupId,
		long classNameId, OrderByComparator orderByComparator)
		throws SystemException {
		List<SocialActivitySetting> list = findByG_C(groupId, classNameId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity setting in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity setting
	 * @throws com.liferay.portlet.social.NoSuchActivitySettingException if a matching social activity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting findByG_C_Last(long groupId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchActivitySettingException, SystemException {
		SocialActivitySetting socialActivitySetting = fetchByG_C_Last(groupId,
				classNameId, orderByComparator);

		if (socialActivitySetting != null) {
			return socialActivitySetting;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivitySettingException(msg.toString());
	}

	/**
	 * Returns the last social activity setting in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting fetchByG_C_Last(long groupId,
		long classNameId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_C(groupId, classNameId);

		List<SocialActivitySetting> list = findByG_C(groupId, classNameId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity settings before and after the current social activity setting in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param activitySettingId the primary key of the current social activity setting
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity setting
	 * @throws com.liferay.portlet.social.NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting[] findByG_C_PrevAndNext(
		long activitySettingId, long groupId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchActivitySettingException, SystemException {
		SocialActivitySetting socialActivitySetting = findByPrimaryKey(activitySettingId);

		Session session = null;

		try {
			session = openSession();

			SocialActivitySetting[] array = new SocialActivitySettingImpl[3];

			array[0] = getByG_C_PrevAndNext(session, socialActivitySetting,
					groupId, classNameId, orderByComparator, true);

			array[1] = socialActivitySetting;

			array[2] = getByG_C_PrevAndNext(session, socialActivitySetting,
					groupId, classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivitySetting getByG_C_PrevAndNext(Session session,
		SocialActivitySetting socialActivitySetting, long groupId,
		long classNameId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYSETTING_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

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

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivitySetting);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivitySetting> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social activity settings where groupId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param activityType the activity type
	 * @return the matching social activity settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivitySetting> findByG_A(long groupId, int activityType)
		throws SystemException {
		return findByG_A(groupId, activityType, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity settings where groupId = &#63; and activityType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param activityType the activity type
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @return the range of matching social activity settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivitySetting> findByG_A(long groupId,
		int activityType, int start, int end) throws SystemException {
		return findByG_A(groupId, activityType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity settings where groupId = &#63; and activityType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param activityType the activity type
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivitySetting> findByG_A(long groupId,
		int activityType, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A;
			finderArgs = new Object[] { groupId, activityType };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A;
			finderArgs = new Object[] {
					groupId, activityType,
					
					start, end, orderByComparator
				};
		}

		List<SocialActivitySetting> list = (List<SocialActivitySetting>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialActivitySetting socialActivitySetting : list) {
				if ((groupId != socialActivitySetting.getGroupId()) ||
						(activityType != socialActivitySetting.getActivityType())) {
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

			query.append(_SQL_SELECT_SOCIALACTIVITYSETTING_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ACTIVITYTYPE_2);

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

				qPos.add(activityType);

				list = (List<SocialActivitySetting>)QueryUtil.list(q,
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
	 * Returns the first social activity setting in the ordered set where groupId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param activityType the activity type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity setting
	 * @throws com.liferay.portlet.social.NoSuchActivitySettingException if a matching social activity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting findByG_A_First(long groupId,
		int activityType, OrderByComparator orderByComparator)
		throws NoSuchActivitySettingException, SystemException {
		SocialActivitySetting socialActivitySetting = fetchByG_A_First(groupId,
				activityType, orderByComparator);

		if (socialActivitySetting != null) {
			return socialActivitySetting;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", activityType=");
		msg.append(activityType);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivitySettingException(msg.toString());
	}

	/**
	 * Returns the first social activity setting in the ordered set where groupId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param activityType the activity type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting fetchByG_A_First(long groupId,
		int activityType, OrderByComparator orderByComparator)
		throws SystemException {
		List<SocialActivitySetting> list = findByG_A(groupId, activityType, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity setting in the ordered set where groupId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param activityType the activity type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity setting
	 * @throws com.liferay.portlet.social.NoSuchActivitySettingException if a matching social activity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting findByG_A_Last(long groupId, int activityType,
		OrderByComparator orderByComparator)
		throws NoSuchActivitySettingException, SystemException {
		SocialActivitySetting socialActivitySetting = fetchByG_A_Last(groupId,
				activityType, orderByComparator);

		if (socialActivitySetting != null) {
			return socialActivitySetting;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", activityType=");
		msg.append(activityType);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivitySettingException(msg.toString());
	}

	/**
	 * Returns the last social activity setting in the ordered set where groupId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param activityType the activity type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting fetchByG_A_Last(long groupId,
		int activityType, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_A(groupId, activityType);

		List<SocialActivitySetting> list = findByG_A(groupId, activityType,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity settings before and after the current social activity setting in the ordered set where groupId = &#63; and activityType = &#63;.
	 *
	 * @param activitySettingId the primary key of the current social activity setting
	 * @param groupId the group ID
	 * @param activityType the activity type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity setting
	 * @throws com.liferay.portlet.social.NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting[] findByG_A_PrevAndNext(
		long activitySettingId, long groupId, int activityType,
		OrderByComparator orderByComparator)
		throws NoSuchActivitySettingException, SystemException {
		SocialActivitySetting socialActivitySetting = findByPrimaryKey(activitySettingId);

		Session session = null;

		try {
			session = openSession();

			SocialActivitySetting[] array = new SocialActivitySettingImpl[3];

			array[0] = getByG_A_PrevAndNext(session, socialActivitySetting,
					groupId, activityType, orderByComparator, true);

			array[1] = socialActivitySetting;

			array[2] = getByG_A_PrevAndNext(session, socialActivitySetting,
					groupId, activityType, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivitySetting getByG_A_PrevAndNext(Session session,
		SocialActivitySetting socialActivitySetting, long groupId,
		int activityType, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYSETTING_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVITYTYPE_2);

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

		qPos.add(activityType);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivitySetting);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivitySetting> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @return the matching social activity settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivitySetting> findByG_C_A(long groupId,
		long classNameId, int activityType) throws SystemException {
		return findByG_C_A(groupId, classNameId, activityType,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @return the range of matching social activity settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivitySetting> findByG_C_A(long groupId,
		long classNameId, int activityType, int start, int end)
		throws SystemException {
		return findByG_C_A(groupId, classNameId, activityType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivitySetting> findByG_C_A(long groupId,
		long classNameId, int activityType, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_A;
			finderArgs = new Object[] { groupId, classNameId, activityType };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_A;
			finderArgs = new Object[] {
					groupId, classNameId, activityType,
					
					start, end, orderByComparator
				};
		}

		List<SocialActivitySetting> list = (List<SocialActivitySetting>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialActivitySetting socialActivitySetting : list) {
				if ((groupId != socialActivitySetting.getGroupId()) ||
						(classNameId != socialActivitySetting.getClassNameId()) ||
						(activityType != socialActivitySetting.getActivityType())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_SOCIALACTIVITYSETTING_WHERE);

			query.append(_FINDER_COLUMN_G_C_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_A_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_A_ACTIVITYTYPE_2);

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

				qPos.add(classNameId);

				qPos.add(activityType);

				list = (List<SocialActivitySetting>)QueryUtil.list(q,
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
	 * Returns the first social activity setting in the ordered set where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity setting
	 * @throws com.liferay.portlet.social.NoSuchActivitySettingException if a matching social activity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting findByG_C_A_First(long groupId,
		long classNameId, int activityType, OrderByComparator orderByComparator)
		throws NoSuchActivitySettingException, SystemException {
		SocialActivitySetting socialActivitySetting = fetchByG_C_A_First(groupId,
				classNameId, activityType, orderByComparator);

		if (socialActivitySetting != null) {
			return socialActivitySetting;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", activityType=");
		msg.append(activityType);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivitySettingException(msg.toString());
	}

	/**
	 * Returns the first social activity setting in the ordered set where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting fetchByG_C_A_First(long groupId,
		long classNameId, int activityType, OrderByComparator orderByComparator)
		throws SystemException {
		List<SocialActivitySetting> list = findByG_C_A(groupId, classNameId,
				activityType, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity setting in the ordered set where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity setting
	 * @throws com.liferay.portlet.social.NoSuchActivitySettingException if a matching social activity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting findByG_C_A_Last(long groupId,
		long classNameId, int activityType, OrderByComparator orderByComparator)
		throws NoSuchActivitySettingException, SystemException {
		SocialActivitySetting socialActivitySetting = fetchByG_C_A_Last(groupId,
				classNameId, activityType, orderByComparator);

		if (socialActivitySetting != null) {
			return socialActivitySetting;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", activityType=");
		msg.append(activityType);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivitySettingException(msg.toString());
	}

	/**
	 * Returns the last social activity setting in the ordered set where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting fetchByG_C_A_Last(long groupId,
		long classNameId, int activityType, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_C_A(groupId, classNameId, activityType);

		List<SocialActivitySetting> list = findByG_C_A(groupId, classNameId,
				activityType, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity settings before and after the current social activity setting in the ordered set where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	 *
	 * @param activitySettingId the primary key of the current social activity setting
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity setting
	 * @throws com.liferay.portlet.social.NoSuchActivitySettingException if a social activity setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting[] findByG_C_A_PrevAndNext(
		long activitySettingId, long groupId, long classNameId,
		int activityType, OrderByComparator orderByComparator)
		throws NoSuchActivitySettingException, SystemException {
		SocialActivitySetting socialActivitySetting = findByPrimaryKey(activitySettingId);

		Session session = null;

		try {
			session = openSession();

			SocialActivitySetting[] array = new SocialActivitySettingImpl[3];

			array[0] = getByG_C_A_PrevAndNext(session, socialActivitySetting,
					groupId, classNameId, activityType, orderByComparator, true);

			array[1] = socialActivitySetting;

			array[2] = getByG_C_A_PrevAndNext(session, socialActivitySetting,
					groupId, classNameId, activityType, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivitySetting getByG_C_A_PrevAndNext(Session session,
		SocialActivitySetting socialActivitySetting, long groupId,
		long classNameId, int activityType,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYSETTING_WHERE);

		query.append(_FINDER_COLUMN_G_C_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_A_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_A_ACTIVITYTYPE_2);

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

		qPos.add(classNameId);

		qPos.add(activityType);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivitySetting);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivitySetting> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the social activity setting where groupId = &#63; and classNameId = &#63; and activityType = &#63; and name = &#63; or throws a {@link com.liferay.portlet.social.NoSuchActivitySettingException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param name the name
	 * @return the matching social activity setting
	 * @throws com.liferay.portlet.social.NoSuchActivitySettingException if a matching social activity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting findByG_C_A_N(long groupId, long classNameId,
		int activityType, String name)
		throws NoSuchActivitySettingException, SystemException {
		SocialActivitySetting socialActivitySetting = fetchByG_C_A_N(groupId,
				classNameId, activityType, name);

		if (socialActivitySetting == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", activityType=");
			msg.append(activityType);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchActivitySettingException(msg.toString());
		}

		return socialActivitySetting;
	}

	/**
	 * Returns the social activity setting where groupId = &#63; and classNameId = &#63; and activityType = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param name the name
	 * @return the matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting fetchByG_C_A_N(long groupId, long classNameId,
		int activityType, String name) throws SystemException {
		return fetchByG_C_A_N(groupId, classNameId, activityType, name, true);
	}

	/**
	 * Returns the social activity setting where groupId = &#63; and classNameId = &#63; and activityType = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching social activity setting, or <code>null</code> if a matching social activity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting fetchByG_C_A_N(long groupId, long classNameId,
		int activityType, String name, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, classNameId, activityType, name
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_C_A_N,
					finderArgs, this);
		}

		if (result instanceof SocialActivitySetting) {
			SocialActivitySetting socialActivitySetting = (SocialActivitySetting)result;

			if ((groupId != socialActivitySetting.getGroupId()) ||
					(classNameId != socialActivitySetting.getClassNameId()) ||
					(activityType != socialActivitySetting.getActivityType()) ||
					!Validator.equals(name, socialActivitySetting.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_SOCIALACTIVITYSETTING_WHERE);

			query.append(_FINDER_COLUMN_G_C_A_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_A_N_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_A_N_ACTIVITYTYPE_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_C_A_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_C_A_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_C_A_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(activityType);

				if (name != null) {
					qPos.add(name);
				}

				List<SocialActivitySetting> list = q.list();

				result = list;

				SocialActivitySetting socialActivitySetting = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_A_N,
						finderArgs, list);
				}
				else {
					socialActivitySetting = list.get(0);

					cacheResult(socialActivitySetting);

					if ((socialActivitySetting.getGroupId() != groupId) ||
							(socialActivitySetting.getClassNameId() != classNameId) ||
							(socialActivitySetting.getActivityType() != activityType) ||
							(socialActivitySetting.getName() == null) ||
							!socialActivitySetting.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_A_N,
							finderArgs, socialActivitySetting);
					}
				}

				return socialActivitySetting;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_A_N,
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
				return (SocialActivitySetting)result;
			}
		}
	}

	/**
	 * Returns all the social activity settings.
	 *
	 * @return the social activity settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivitySetting> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @return the range of social activity settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivitySetting> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of social activity settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivitySetting> findAll(int start, int end,
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

		List<SocialActivitySetting> list = (List<SocialActivitySetting>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SOCIALACTIVITYSETTING);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SOCIALACTIVITYSETTING;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SocialActivitySetting>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialActivitySetting>)QueryUtil.list(q,
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
	 * Removes all the social activity settings where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (SocialActivitySetting socialActivitySetting : findByGroupId(
				groupId)) {
			remove(socialActivitySetting);
		}
	}

	/**
	 * Removes all the social activity settings where groupId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_C(long groupId, long classNameId)
		throws SystemException {
		for (SocialActivitySetting socialActivitySetting : findByG_C(groupId,
				classNameId)) {
			remove(socialActivitySetting);
		}
	}

	/**
	 * Removes all the social activity settings where groupId = &#63; and activityType = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param activityType the activity type
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_A(long groupId, int activityType)
		throws SystemException {
		for (SocialActivitySetting socialActivitySetting : findByG_A(groupId,
				activityType)) {
			remove(socialActivitySetting);
		}
	}

	/**
	 * Removes all the social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_C_A(long groupId, long classNameId, int activityType)
		throws SystemException {
		for (SocialActivitySetting socialActivitySetting : findByG_C_A(
				groupId, classNameId, activityType)) {
			remove(socialActivitySetting);
		}
	}

	/**
	 * Removes the social activity setting where groupId = &#63; and classNameId = &#63; and activityType = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param name the name
	 * @return the social activity setting that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySetting removeByG_C_A_N(long groupId,
		long classNameId, int activityType, String name)
		throws NoSuchActivitySettingException, SystemException {
		SocialActivitySetting socialActivitySetting = findByG_C_A_N(groupId,
				classNameId, activityType, name);

		return remove(socialActivitySetting);
	}

	/**
	 * Removes all the social activity settings from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SocialActivitySetting socialActivitySetting : findAll()) {
			remove(socialActivitySetting);
		}
	}

	/**
	 * Returns the number of social activity settings where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching social activity settings
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALACTIVITYSETTING_WHERE);

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
	 * Returns the number of social activity settings where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching social activity settings
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C(long groupId, long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, classNameId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALACTIVITYSETTING_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social activity settings where groupId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param activityType the activity type
	 * @return the number of matching social activity settings
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_A(long groupId, int activityType)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, activityType };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_A,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALACTIVITYSETTING_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ACTIVITYTYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(activityType);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_A, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @return the number of matching social activity settings
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C_A(long groupId, long classNameId, int activityType)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, classNameId, activityType };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C_A,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SOCIALACTIVITYSETTING_WHERE);

			query.append(_FINDER_COLUMN_G_C_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_A_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_A_ACTIVITYTYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(activityType);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_A,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social activity settings where groupId = &#63; and classNameId = &#63; and activityType = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param activityType the activity type
	 * @param name the name
	 * @return the number of matching social activity settings
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C_A_N(long groupId, long classNameId, int activityType,
		String name) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, classNameId, activityType, name
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C_A_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_SOCIALACTIVITYSETTING_WHERE);

			query.append(_FINDER_COLUMN_G_C_A_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_A_N_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_A_N_ACTIVITYTYPE_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_C_A_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_C_A_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_C_A_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(activityType);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_A_N,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social activity settings.
	 *
	 * @return the number of social activity settings
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SOCIALACTIVITYSETTING);

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
	 * Initializes the social activity setting persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.social.model.SocialActivitySetting")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialActivitySetting>> listenersList = new ArrayList<ModelListener<SocialActivitySetting>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialActivitySetting>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(SocialActivitySettingImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	@BeanReference(type = SocialActivityAchievementPersistence.class)
	protected SocialActivityAchievementPersistence socialActivityAchievementPersistence;
	@BeanReference(type = SocialActivityCounterPersistence.class)
	protected SocialActivityCounterPersistence socialActivityCounterPersistence;
	@BeanReference(type = SocialActivityLimitPersistence.class)
	protected SocialActivityLimitPersistence socialActivityLimitPersistence;
	@BeanReference(type = SocialActivitySettingPersistence.class)
	protected SocialActivitySettingPersistence socialActivitySettingPersistence;
	@BeanReference(type = SocialRelationPersistence.class)
	protected SocialRelationPersistence socialRelationPersistence;
	@BeanReference(type = SocialRequestPersistence.class)
	protected SocialRequestPersistence socialRequestPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_SOCIALACTIVITYSETTING = "SELECT socialActivitySetting FROM SocialActivitySetting socialActivitySetting";
	private static final String _SQL_SELECT_SOCIALACTIVITYSETTING_WHERE = "SELECT socialActivitySetting FROM SocialActivitySetting socialActivitySetting WHERE ";
	private static final String _SQL_COUNT_SOCIALACTIVITYSETTING = "SELECT COUNT(socialActivitySetting) FROM SocialActivitySetting socialActivitySetting";
	private static final String _SQL_COUNT_SOCIALACTIVITYSETTING_WHERE = "SELECT COUNT(socialActivitySetting) FROM SocialActivitySetting socialActivitySetting WHERE ";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "socialActivitySetting.groupId = ?";
	private static final String _FINDER_COLUMN_G_C_GROUPID_2 = "socialActivitySetting.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_CLASSNAMEID_2 = "socialActivitySetting.classNameId = ?";
	private static final String _FINDER_COLUMN_G_A_GROUPID_2 = "socialActivitySetting.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_ACTIVITYTYPE_2 = "socialActivitySetting.activityType = ?";
	private static final String _FINDER_COLUMN_G_C_A_GROUPID_2 = "socialActivitySetting.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_A_CLASSNAMEID_2 = "socialActivitySetting.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_A_ACTIVITYTYPE_2 = "socialActivitySetting.activityType = ?";
	private static final String _FINDER_COLUMN_G_C_A_N_GROUPID_2 = "socialActivitySetting.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_A_N_CLASSNAMEID_2 = "socialActivitySetting.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_A_N_ACTIVITYTYPE_2 = "socialActivitySetting.activityType = ? AND ";
	private static final String _FINDER_COLUMN_G_C_A_N_NAME_1 = "socialActivitySetting.name IS NULL";
	private static final String _FINDER_COLUMN_G_C_A_N_NAME_2 = "socialActivitySetting.name = ?";
	private static final String _FINDER_COLUMN_G_C_A_N_NAME_3 = "(socialActivitySetting.name IS NULL OR socialActivitySetting.name = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialActivitySetting.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialActivitySetting exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SocialActivitySetting exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(SocialActivitySettingPersistenceImpl.class);
	private static SocialActivitySetting _nullSocialActivitySetting = new SocialActivitySettingImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SocialActivitySetting> toCacheModel() {
				return _nullSocialActivitySettingCacheModel;
			}
		};

	private static CacheModel<SocialActivitySetting> _nullSocialActivitySettingCacheModel =
		new CacheModel<SocialActivitySetting>() {
			public SocialActivitySetting toEntityModel() {
				return _nullSocialActivitySetting;
			}
		};
}