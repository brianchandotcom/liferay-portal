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
import com.liferay.portal.service.persistence.LockPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.social.NoSuchActivityCounterException;
import com.liferay.portlet.social.model.SocialActivityCounter;
import com.liferay.portlet.social.model.impl.SocialActivityCounterImpl;
import com.liferay.portlet.social.model.impl.SocialActivityCounterModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the social activity counter service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityCounterPersistence
 * @see SocialActivityCounterUtil
 * @generated
 */
public class SocialActivityCounterPersistenceImpl extends BasePersistenceImpl<SocialActivityCounter>
	implements SocialActivityCounterPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SocialActivityCounterUtil} to access the social activity counter persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SocialActivityCounterImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			SocialActivityCounterModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C_O = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_O",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_O =
		new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C_O",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			SocialActivityCounterModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.CLASSPK_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.OWNERTYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C_O = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_O",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_C_N_O_S = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityCounterImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_C_N_O_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			},
			SocialActivityCounterModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.CLASSPK_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.NAME_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.OWNERTYPE_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.STARTPERIOD_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C_N_O_S = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_N_O_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_C_N_O_E = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityCounterImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_C_N_O_E",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			},
			SocialActivityCounterModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.CLASSPK_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.NAME_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.OWNERTYPE_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.ENDPERIOD_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C_N_O_E = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_N_O_E",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the social activity counter in the entity cache if it is enabled.
	 *
	 * @param socialActivityCounter the social activity counter
	 */
	public void cacheResult(SocialActivityCounter socialActivityCounter) {
		EntityCacheUtil.putResult(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterImpl.class,
			socialActivityCounter.getPrimaryKey(), socialActivityCounter);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_S,
			new Object[] {
				Long.valueOf(socialActivityCounter.getGroupId()),
				Long.valueOf(socialActivityCounter.getClassNameId()),
				Long.valueOf(socialActivityCounter.getClassPK()),
				
			socialActivityCounter.getName(),
				Integer.valueOf(socialActivityCounter.getOwnerType()),
				Integer.valueOf(socialActivityCounter.getStartPeriod())
			}, socialActivityCounter);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_E,
			new Object[] {
				Long.valueOf(socialActivityCounter.getGroupId()),
				Long.valueOf(socialActivityCounter.getClassNameId()),
				Long.valueOf(socialActivityCounter.getClassPK()),
				
			socialActivityCounter.getName(),
				Integer.valueOf(socialActivityCounter.getOwnerType()),
				Integer.valueOf(socialActivityCounter.getEndPeriod())
			}, socialActivityCounter);

		socialActivityCounter.resetOriginalValues();
	}

	/**
	 * Caches the social activity counters in the entity cache if it is enabled.
	 *
	 * @param socialActivityCounters the social activity counters
	 */
	public void cacheResult(List<SocialActivityCounter> socialActivityCounters) {
		for (SocialActivityCounter socialActivityCounter : socialActivityCounters) {
			if (EntityCacheUtil.getResult(
						SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
						SocialActivityCounterImpl.class,
						socialActivityCounter.getPrimaryKey()) == null) {
				cacheResult(socialActivityCounter);
			}
			else {
				socialActivityCounter.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all social activity counters.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SocialActivityCounterImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SocialActivityCounterImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the social activity counter.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SocialActivityCounter socialActivityCounter) {
		EntityCacheUtil.removeResult(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterImpl.class,
			socialActivityCounter.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(socialActivityCounter);
	}

	@Override
	public void clearCache(List<SocialActivityCounter> socialActivityCounters) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SocialActivityCounter socialActivityCounter : socialActivityCounters) {
			EntityCacheUtil.removeResult(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
				SocialActivityCounterImpl.class,
				socialActivityCounter.getPrimaryKey());

			clearUniqueFindersCache(socialActivityCounter);
		}
	}

	protected void clearUniqueFindersCache(
		SocialActivityCounter socialActivityCounter) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_S,
			new Object[] {
				Long.valueOf(socialActivityCounter.getGroupId()),
				Long.valueOf(socialActivityCounter.getClassNameId()),
				Long.valueOf(socialActivityCounter.getClassPK()),
				
			socialActivityCounter.getName(),
				Integer.valueOf(socialActivityCounter.getOwnerType()),
				Integer.valueOf(socialActivityCounter.getStartPeriod())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_E,
			new Object[] {
				Long.valueOf(socialActivityCounter.getGroupId()),
				Long.valueOf(socialActivityCounter.getClassNameId()),
				Long.valueOf(socialActivityCounter.getClassPK()),
				
			socialActivityCounter.getName(),
				Integer.valueOf(socialActivityCounter.getOwnerType()),
				Integer.valueOf(socialActivityCounter.getEndPeriod())
			});
	}

	/**
	 * Creates a new social activity counter with the primary key. Does not add the social activity counter to the database.
	 *
	 * @param activityCounterId the primary key for the new social activity counter
	 * @return the new social activity counter
	 */
	public SocialActivityCounter create(long activityCounterId) {
		SocialActivityCounter socialActivityCounter = new SocialActivityCounterImpl();

		socialActivityCounter.setNew(true);
		socialActivityCounter.setPrimaryKey(activityCounterId);

		return socialActivityCounter;
	}

	/**
	 * Removes the social activity counter with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param activityCounterId the primary key of the social activity counter
	 * @return the social activity counter that was removed
	 * @throws com.liferay.portlet.social.NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityCounter remove(long activityCounterId)
		throws NoSuchActivityCounterException, SystemException {
		return remove(Long.valueOf(activityCounterId));
	}

	/**
	 * Removes the social activity counter with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the social activity counter
	 * @return the social activity counter that was removed
	 * @throws com.liferay.portlet.social.NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivityCounter remove(Serializable primaryKey)
		throws NoSuchActivityCounterException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialActivityCounter socialActivityCounter = (SocialActivityCounter)session.get(SocialActivityCounterImpl.class,
					primaryKey);

			if (socialActivityCounter == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchActivityCounterException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(socialActivityCounter);
		}
		catch (NoSuchActivityCounterException nsee) {
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
	protected SocialActivityCounter removeImpl(
		SocialActivityCounter socialActivityCounter) throws SystemException {
		socialActivityCounter = toUnwrappedModel(socialActivityCounter);

		Session session = null;

		try {
			session = openSession();

			if (socialActivityCounter.isCachedModel()) {
				socialActivityCounter = (SocialActivityCounter)session.get(SocialActivityCounterImpl.class,
						socialActivityCounter.getPrimaryKeyObj());
			}

			if (socialActivityCounter != null) {
				session.delete(socialActivityCounter);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (socialActivityCounter != null) {
			clearCache(socialActivityCounter);
		}

		return socialActivityCounter;
	}

	@Override
	public SocialActivityCounter updateImpl(
		com.liferay.portlet.social.model.SocialActivityCounter socialActivityCounter)
		throws SystemException {
		socialActivityCounter = toUnwrappedModel(socialActivityCounter);

		boolean isNew = socialActivityCounter.isNew();

		SocialActivityCounterModelImpl socialActivityCounterModelImpl = (SocialActivityCounterModelImpl)socialActivityCounter;

		Session session = null;

		try {
			session = openSession();

			if (socialActivityCounter.isNew()) {
				session.save(socialActivityCounter);

				socialActivityCounter.setNew(false);
			}
			else {
				session.merge(socialActivityCounter);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SocialActivityCounterModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((socialActivityCounterModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialActivityCounterModelImpl.getOriginalClassNameId()),
						Long.valueOf(socialActivityCounterModelImpl.getOriginalClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						Long.valueOf(socialActivityCounterModelImpl.getClassNameId()),
						Long.valueOf(socialActivityCounterModelImpl.getClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}

			if ((socialActivityCounterModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_O.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialActivityCounterModelImpl.getOriginalGroupId()),
						Long.valueOf(socialActivityCounterModelImpl.getOriginalClassNameId()),
						Long.valueOf(socialActivityCounterModelImpl.getOriginalClassPK()),
						Integer.valueOf(socialActivityCounterModelImpl.getOriginalOwnerType())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_C_O, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_O,
					args);

				args = new Object[] {
						Long.valueOf(socialActivityCounterModelImpl.getGroupId()),
						Long.valueOf(socialActivityCounterModelImpl.getClassNameId()),
						Long.valueOf(socialActivityCounterModelImpl.getClassPK()),
						Integer.valueOf(socialActivityCounterModelImpl.getOwnerType())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_C_O, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_O,
					args);
			}
		}

		EntityCacheUtil.putResult(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterImpl.class,
			socialActivityCounter.getPrimaryKey(), socialActivityCounter);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_S,
				new Object[] {
					Long.valueOf(socialActivityCounter.getGroupId()),
					Long.valueOf(socialActivityCounter.getClassNameId()),
					Long.valueOf(socialActivityCounter.getClassPK()),
					
				socialActivityCounter.getName(),
					Integer.valueOf(socialActivityCounter.getOwnerType()),
					Integer.valueOf(socialActivityCounter.getStartPeriod())
				}, socialActivityCounter);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_E,
				new Object[] {
					Long.valueOf(socialActivityCounter.getGroupId()),
					Long.valueOf(socialActivityCounter.getClassNameId()),
					Long.valueOf(socialActivityCounter.getClassPK()),
					
				socialActivityCounter.getName(),
					Integer.valueOf(socialActivityCounter.getOwnerType()),
					Integer.valueOf(socialActivityCounter.getEndPeriod())
				}, socialActivityCounter);
		}
		else {
			if ((socialActivityCounterModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_C_C_N_O_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialActivityCounterModelImpl.getOriginalGroupId()),
						Long.valueOf(socialActivityCounterModelImpl.getOriginalClassNameId()),
						Long.valueOf(socialActivityCounterModelImpl.getOriginalClassPK()),
						
						socialActivityCounterModelImpl.getOriginalName(),
						Integer.valueOf(socialActivityCounterModelImpl.getOriginalOwnerType()),
						Integer.valueOf(socialActivityCounterModelImpl.getOriginalStartPeriod())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_C_N_O_S,
					args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_S,
					args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_S,
					new Object[] {
						Long.valueOf(socialActivityCounter.getGroupId()),
						Long.valueOf(socialActivityCounter.getClassNameId()),
						Long.valueOf(socialActivityCounter.getClassPK()),
						
					socialActivityCounter.getName(),
						Integer.valueOf(socialActivityCounter.getOwnerType()),
						Integer.valueOf(socialActivityCounter.getStartPeriod())
					}, socialActivityCounter);
			}

			if ((socialActivityCounterModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_C_C_N_O_E.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialActivityCounterModelImpl.getOriginalGroupId()),
						Long.valueOf(socialActivityCounterModelImpl.getOriginalClassNameId()),
						Long.valueOf(socialActivityCounterModelImpl.getOriginalClassPK()),
						
						socialActivityCounterModelImpl.getOriginalName(),
						Integer.valueOf(socialActivityCounterModelImpl.getOriginalOwnerType()),
						Integer.valueOf(socialActivityCounterModelImpl.getOriginalEndPeriod())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_C_N_O_E,
					args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_E,
					args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_E,
					new Object[] {
						Long.valueOf(socialActivityCounter.getGroupId()),
						Long.valueOf(socialActivityCounter.getClassNameId()),
						Long.valueOf(socialActivityCounter.getClassPK()),
						
					socialActivityCounter.getName(),
						Integer.valueOf(socialActivityCounter.getOwnerType()),
						Integer.valueOf(socialActivityCounter.getEndPeriod())
					}, socialActivityCounter);
			}
		}

		return socialActivityCounter;
	}

	protected SocialActivityCounter toUnwrappedModel(
		SocialActivityCounter socialActivityCounter) {
		if (socialActivityCounter instanceof SocialActivityCounterImpl) {
			return socialActivityCounter;
		}

		SocialActivityCounterImpl socialActivityCounterImpl = new SocialActivityCounterImpl();

		socialActivityCounterImpl.setNew(socialActivityCounter.isNew());
		socialActivityCounterImpl.setPrimaryKey(socialActivityCounter.getPrimaryKey());

		socialActivityCounterImpl.setActivityCounterId(socialActivityCounter.getActivityCounterId());
		socialActivityCounterImpl.setGroupId(socialActivityCounter.getGroupId());
		socialActivityCounterImpl.setCompanyId(socialActivityCounter.getCompanyId());
		socialActivityCounterImpl.setClassNameId(socialActivityCounter.getClassNameId());
		socialActivityCounterImpl.setClassPK(socialActivityCounter.getClassPK());
		socialActivityCounterImpl.setName(socialActivityCounter.getName());
		socialActivityCounterImpl.setOwnerType(socialActivityCounter.getOwnerType());
		socialActivityCounterImpl.setCurrentValue(socialActivityCounter.getCurrentValue());
		socialActivityCounterImpl.setTotalValue(socialActivityCounter.getTotalValue());
		socialActivityCounterImpl.setGraceValue(socialActivityCounter.getGraceValue());
		socialActivityCounterImpl.setStartPeriod(socialActivityCounter.getStartPeriod());
		socialActivityCounterImpl.setEndPeriod(socialActivityCounter.getEndPeriod());
		socialActivityCounterImpl.setActive(socialActivityCounter.isActive());

		return socialActivityCounterImpl;
	}

	/**
	 * Returns the social activity counter with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity counter
	 * @return the social activity counter
	 * @throws com.liferay.portal.NoSuchModelException if a social activity counter with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivityCounter findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social activity counter with the primary key or throws a {@link com.liferay.portlet.social.NoSuchActivityCounterException} if it could not be found.
	 *
	 * @param activityCounterId the primary key of the social activity counter
	 * @return the social activity counter
	 * @throws com.liferay.portlet.social.NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityCounter findByPrimaryKey(long activityCounterId)
		throws NoSuchActivityCounterException, SystemException {
		SocialActivityCounter socialActivityCounter = fetchByPrimaryKey(activityCounterId);

		if (socialActivityCounter == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + activityCounterId);
			}

			throw new NoSuchActivityCounterException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				activityCounterId);
		}

		return socialActivityCounter;
	}

	/**
	 * Returns the social activity counter with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity counter
	 * @return the social activity counter, or <code>null</code> if a social activity counter with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivityCounter fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social activity counter with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param activityCounterId the primary key of the social activity counter
	 * @return the social activity counter, or <code>null</code> if a social activity counter with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityCounter fetchByPrimaryKey(long activityCounterId)
		throws SystemException {
		SocialActivityCounter socialActivityCounter = (SocialActivityCounter)EntityCacheUtil.getResult(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
				SocialActivityCounterImpl.class, activityCounterId);

		if (socialActivityCounter == _nullSocialActivityCounter) {
			return null;
		}

		if (socialActivityCounter == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				socialActivityCounter = (SocialActivityCounter)session.get(SocialActivityCounterImpl.class,
						Long.valueOf(activityCounterId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (socialActivityCounter != null) {
					cacheResult(socialActivityCounter);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
						SocialActivityCounterImpl.class, activityCounterId,
						_nullSocialActivityCounter);
				}

				closeSession(session);
			}
		}

		return socialActivityCounter;
	}

	/**
	 * Returns all the social activity counters where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching social activity counters
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityCounter> findByC_C(long classNameId, long classPK)
		throws SystemException {
		return findByC_C(classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity counters where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @return the range of matching social activity counters
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityCounter> findByC_C(long classNameId,
		long classPK, int start, int end) throws SystemException {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity counters where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity counters
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityCounter> findByC_C(long classNameId,
		long classPK, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] { classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] {
					classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<SocialActivityCounter> list = (List<SocialActivityCounter>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialActivityCounter socialActivityCounter : list) {
				if ((classNameId != socialActivityCounter.getClassNameId()) ||
						(classPK != socialActivityCounter.getClassPK())) {
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

			query.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<SocialActivityCounter>)QueryUtil.list(q,
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
	 * Returns the first social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity counter
	 * @throws com.liferay.portlet.social.NoSuchActivityCounterException if a matching social activity counter could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityCounter findByC_C_First(long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws NoSuchActivityCounterException, SystemException {
		SocialActivityCounter socialActivityCounter = fetchByC_C_First(classNameId,
				classPK, orderByComparator);

		if (socialActivityCounter != null) {
			return socialActivityCounter;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityCounterException(msg.toString());
	}

	/**
	 * Returns the first social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityCounter fetchByC_C_First(long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws SystemException {
		List<SocialActivityCounter> list = findByC_C(classNameId, classPK, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity counter
	 * @throws com.liferay.portlet.social.NoSuchActivityCounterException if a matching social activity counter could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityCounter findByC_C_Last(long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchActivityCounterException, SystemException {
		SocialActivityCounter socialActivityCounter = fetchByC_C_Last(classNameId,
				classPK, orderByComparator);

		if (socialActivityCounter != null) {
			return socialActivityCounter;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityCounterException(msg.toString());
	}

	/**
	 * Returns the last social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityCounter fetchByC_C_Last(long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByC_C(classNameId, classPK);

		List<SocialActivityCounter> list = findByC_C(classNameId, classPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity counters before and after the current social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param activityCounterId the primary key of the current social activity counter
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity counter
	 * @throws com.liferay.portlet.social.NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityCounter[] findByC_C_PrevAndNext(
		long activityCounterId, long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchActivityCounterException, SystemException {
		SocialActivityCounter socialActivityCounter = findByPrimaryKey(activityCounterId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityCounter[] array = new SocialActivityCounterImpl[3];

			array[0] = getByC_C_PrevAndNext(session, socialActivityCounter,
					classNameId, classPK, orderByComparator, true);

			array[1] = socialActivityCounter;

			array[2] = getByC_C_PrevAndNext(session, socialActivityCounter,
					classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivityCounter getByC_C_PrevAndNext(Session session,
		SocialActivityCounter socialActivityCounter, long classNameId,
		long classPK, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivityCounter);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivityCounter> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param ownerType the owner type
	 * @return the matching social activity counters
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityCounter> findByG_C_C_O(long groupId,
		long classNameId, long classPK, int ownerType)
		throws SystemException {
		return findByG_C_C_O(groupId, classNameId, classPK, ownerType,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param ownerType the owner type
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @return the range of matching social activity counters
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityCounter> findByG_C_C_O(long groupId,
		long classNameId, long classPK, int ownerType, int start, int end)
		throws SystemException {
		return findByG_C_C_O(groupId, classNameId, classPK, ownerType, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param ownerType the owner type
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity counters
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityCounter> findByG_C_C_O(long groupId,
		long classNameId, long classPK, int ownerType, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_O;
			finderArgs = new Object[] { groupId, classNameId, classPK, ownerType };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C_O;
			finderArgs = new Object[] {
					groupId, classNameId, classPK, ownerType,
					
					start, end, orderByComparator
				};
		}

		List<SocialActivityCounter> list = (List<SocialActivityCounter>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialActivityCounter socialActivityCounter : list) {
				if ((groupId != socialActivityCounter.getGroupId()) ||
						(classNameId != socialActivityCounter.getClassNameId()) ||
						(classPK != socialActivityCounter.getClassPK()) ||
						(ownerType != socialActivityCounter.getOwnerType())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_O_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_O_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_O_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_C_C_O_OWNERTYPE_2);

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

				qPos.add(classPK);

				qPos.add(ownerType);

				list = (List<SocialActivityCounter>)QueryUtil.list(q,
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
	 * Returns the first social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param ownerType the owner type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity counter
	 * @throws com.liferay.portlet.social.NoSuchActivityCounterException if a matching social activity counter could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityCounter findByG_C_C_O_First(long groupId,
		long classNameId, long classPK, int ownerType,
		OrderByComparator orderByComparator)
		throws NoSuchActivityCounterException, SystemException {
		SocialActivityCounter socialActivityCounter = fetchByG_C_C_O_First(groupId,
				classNameId, classPK, ownerType, orderByComparator);

		if (socialActivityCounter != null) {
			return socialActivityCounter;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", ownerType=");
		msg.append(ownerType);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityCounterException(msg.toString());
	}

	/**
	 * Returns the first social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param ownerType the owner type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityCounter fetchByG_C_C_O_First(long groupId,
		long classNameId, long classPK, int ownerType,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialActivityCounter> list = findByG_C_C_O(groupId, classNameId,
				classPK, ownerType, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param ownerType the owner type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity counter
	 * @throws com.liferay.portlet.social.NoSuchActivityCounterException if a matching social activity counter could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityCounter findByG_C_C_O_Last(long groupId,
		long classNameId, long classPK, int ownerType,
		OrderByComparator orderByComparator)
		throws NoSuchActivityCounterException, SystemException {
		SocialActivityCounter socialActivityCounter = fetchByG_C_C_O_Last(groupId,
				classNameId, classPK, ownerType, orderByComparator);

		if (socialActivityCounter != null) {
			return socialActivityCounter;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", ownerType=");
		msg.append(ownerType);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityCounterException(msg.toString());
	}

	/**
	 * Returns the last social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param ownerType the owner type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityCounter fetchByG_C_C_O_Last(long groupId,
		long classNameId, long classPK, int ownerType,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_C_C_O(groupId, classNameId, classPK, ownerType);

		List<SocialActivityCounter> list = findByG_C_C_O(groupId, classNameId,
				classPK, ownerType, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity counters before and after the current social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * @param activityCounterId the primary key of the current social activity counter
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param ownerType the owner type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity counter
	 * @throws com.liferay.portlet.social.NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityCounter[] findByG_C_C_O_PrevAndNext(
		long activityCounterId, long groupId, long classNameId, long classPK,
		int ownerType, OrderByComparator orderByComparator)
		throws NoSuchActivityCounterException, SystemException {
		SocialActivityCounter socialActivityCounter = findByPrimaryKey(activityCounterId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityCounter[] array = new SocialActivityCounterImpl[3];

			array[0] = getByG_C_C_O_PrevAndNext(session, socialActivityCounter,
					groupId, classNameId, classPK, ownerType,
					orderByComparator, true);

			array[1] = socialActivityCounter;

			array[2] = getByG_C_C_O_PrevAndNext(session, socialActivityCounter,
					groupId, classNameId, classPK, ownerType,
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

	protected SocialActivityCounter getByG_C_C_O_PrevAndNext(Session session,
		SocialActivityCounter socialActivityCounter, long groupId,
		long classNameId, long classPK, int ownerType,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_O_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_O_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_O_CLASSPK_2);

		query.append(_FINDER_COLUMN_G_C_C_O_OWNERTYPE_2);

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

		qPos.add(classPK);

		qPos.add(ownerType);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivityCounter);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivityCounter> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; or throws a {@link com.liferay.portlet.social.NoSuchActivityCounterException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param name the name
	 * @param ownerType the owner type
	 * @param startPeriod the start period
	 * @return the matching social activity counter
	 * @throws com.liferay.portlet.social.NoSuchActivityCounterException if a matching social activity counter could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityCounter findByG_C_C_N_O_S(long groupId,
		long classNameId, long classPK, String name, int ownerType,
		int startPeriod) throws NoSuchActivityCounterException, SystemException {
		SocialActivityCounter socialActivityCounter = fetchByG_C_C_N_O_S(groupId,
				classNameId, classPK, name, ownerType, startPeriod);

		if (socialActivityCounter == null) {
			StringBundler msg = new StringBundler(14);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", name=");
			msg.append(name);

			msg.append(", ownerType=");
			msg.append(ownerType);

			msg.append(", startPeriod=");
			msg.append(startPeriod);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchActivityCounterException(msg.toString());
		}

		return socialActivityCounter;
	}

	/**
	 * Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param name the name
	 * @param ownerType the owner type
	 * @param startPeriod the start period
	 * @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityCounter fetchByG_C_C_N_O_S(long groupId,
		long classNameId, long classPK, String name, int ownerType,
		int startPeriod) throws SystemException {
		return fetchByG_C_C_N_O_S(groupId, classNameId, classPK, name,
			ownerType, startPeriod, true);
	}

	/**
	 * Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param name the name
	 * @param ownerType the owner type
	 * @param startPeriod the start period
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityCounter fetchByG_C_C_N_O_S(long groupId,
		long classNameId, long classPK, String name, int ownerType,
		int startPeriod, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, name, ownerType, startPeriod
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_S,
					finderArgs, this);
		}

		if (result instanceof SocialActivityCounter) {
			SocialActivityCounter socialActivityCounter = (SocialActivityCounter)result;

			if ((groupId != socialActivityCounter.getGroupId()) ||
					(classNameId != socialActivityCounter.getClassNameId()) ||
					(classPK != socialActivityCounter.getClassPK()) ||
					!Validator.equals(name, socialActivityCounter.getName()) ||
					(ownerType != socialActivityCounter.getOwnerType()) ||
					(startPeriod != socialActivityCounter.getStartPeriod())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_N_O_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_S_CLASSPK_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_C_C_N_O_S_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_C_C_N_O_S_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_C_C_N_O_S_NAME_2);
				}
			}

			query.append(_FINDER_COLUMN_G_C_C_N_O_S_OWNERTYPE_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_S_STARTPERIOD_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(ownerType);

				qPos.add(startPeriod);

				List<SocialActivityCounter> list = q.list();

				result = list;

				SocialActivityCounter socialActivityCounter = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_S,
						finderArgs, list);
				}
				else {
					socialActivityCounter = list.get(0);

					cacheResult(socialActivityCounter);

					if ((socialActivityCounter.getGroupId() != groupId) ||
							(socialActivityCounter.getClassNameId() != classNameId) ||
							(socialActivityCounter.getClassPK() != classPK) ||
							(socialActivityCounter.getName() == null) ||
							!socialActivityCounter.getName().equals(name) ||
							(socialActivityCounter.getOwnerType() != ownerType) ||
							(socialActivityCounter.getStartPeriod() != startPeriod)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_S,
							finderArgs, socialActivityCounter);
					}
				}

				return socialActivityCounter;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_S,
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
				return (SocialActivityCounter)result;
			}
		}
	}

	/**
	 * Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; or throws a {@link com.liferay.portlet.social.NoSuchActivityCounterException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param name the name
	 * @param ownerType the owner type
	 * @param endPeriod the end period
	 * @return the matching social activity counter
	 * @throws com.liferay.portlet.social.NoSuchActivityCounterException if a matching social activity counter could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityCounter findByG_C_C_N_O_E(long groupId,
		long classNameId, long classPK, String name, int ownerType,
		int endPeriod) throws NoSuchActivityCounterException, SystemException {
		SocialActivityCounter socialActivityCounter = fetchByG_C_C_N_O_E(groupId,
				classNameId, classPK, name, ownerType, endPeriod);

		if (socialActivityCounter == null) {
			StringBundler msg = new StringBundler(14);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", name=");
			msg.append(name);

			msg.append(", ownerType=");
			msg.append(ownerType);

			msg.append(", endPeriod=");
			msg.append(endPeriod);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchActivityCounterException(msg.toString());
		}

		return socialActivityCounter;
	}

	/**
	 * Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param name the name
	 * @param ownerType the owner type
	 * @param endPeriod the end period
	 * @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityCounter fetchByG_C_C_N_O_E(long groupId,
		long classNameId, long classPK, String name, int ownerType,
		int endPeriod) throws SystemException {
		return fetchByG_C_C_N_O_E(groupId, classNameId, classPK, name,
			ownerType, endPeriod, true);
	}

	/**
	 * Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param name the name
	 * @param ownerType the owner type
	 * @param endPeriod the end period
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityCounter fetchByG_C_C_N_O_E(long groupId,
		long classNameId, long classPK, String name, int ownerType,
		int endPeriod, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, name, ownerType, endPeriod
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_E,
					finderArgs, this);
		}

		if (result instanceof SocialActivityCounter) {
			SocialActivityCounter socialActivityCounter = (SocialActivityCounter)result;

			if ((groupId != socialActivityCounter.getGroupId()) ||
					(classNameId != socialActivityCounter.getClassNameId()) ||
					(classPK != socialActivityCounter.getClassPK()) ||
					!Validator.equals(name, socialActivityCounter.getName()) ||
					(ownerType != socialActivityCounter.getOwnerType()) ||
					(endPeriod != socialActivityCounter.getEndPeriod())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_N_O_E_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_E_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_E_CLASSPK_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_C_C_N_O_E_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_C_C_N_O_E_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_C_C_N_O_E_NAME_2);
				}
			}

			query.append(_FINDER_COLUMN_G_C_C_N_O_E_OWNERTYPE_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_E_ENDPERIOD_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(ownerType);

				qPos.add(endPeriod);

				List<SocialActivityCounter> list = q.list();

				result = list;

				SocialActivityCounter socialActivityCounter = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_E,
						finderArgs, list);
				}
				else {
					socialActivityCounter = list.get(0);

					cacheResult(socialActivityCounter);

					if ((socialActivityCounter.getGroupId() != groupId) ||
							(socialActivityCounter.getClassNameId() != classNameId) ||
							(socialActivityCounter.getClassPK() != classPK) ||
							(socialActivityCounter.getName() == null) ||
							!socialActivityCounter.getName().equals(name) ||
							(socialActivityCounter.getOwnerType() != ownerType) ||
							(socialActivityCounter.getEndPeriod() != endPeriod)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_E,
							finderArgs, socialActivityCounter);
					}
				}

				return socialActivityCounter;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_E,
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
				return (SocialActivityCounter)result;
			}
		}
	}

	/**
	 * Returns all the social activity counters.
	 *
	 * @return the social activity counters
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityCounter> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity counters.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @return the range of social activity counters
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityCounter> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity counters.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of social activity counters
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityCounter> findAll(int start, int end,
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

		List<SocialActivityCounter> list = (List<SocialActivityCounter>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SOCIALACTIVITYCOUNTER;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SocialActivityCounter>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialActivityCounter>)QueryUtil.list(q,
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
	 * Removes all the social activity counters where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_C(long classNameId, long classPK)
		throws SystemException {
		for (SocialActivityCounter socialActivityCounter : findByC_C(
				classNameId, classPK)) {
			remove(socialActivityCounter);
		}
	}

	/**
	 * Removes all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param ownerType the owner type
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_C_C_O(long groupId, long classNameId, long classPK,
		int ownerType) throws SystemException {
		for (SocialActivityCounter socialActivityCounter : findByG_C_C_O(
				groupId, classNameId, classPK, ownerType)) {
			remove(socialActivityCounter);
		}
	}

	/**
	 * Removes the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param name the name
	 * @param ownerType the owner type
	 * @param startPeriod the start period
	 * @return the social activity counter that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityCounter removeByG_C_C_N_O_S(long groupId,
		long classNameId, long classPK, String name, int ownerType,
		int startPeriod) throws NoSuchActivityCounterException, SystemException {
		SocialActivityCounter socialActivityCounter = findByG_C_C_N_O_S(groupId,
				classNameId, classPK, name, ownerType, startPeriod);

		return remove(socialActivityCounter);
	}

	/**
	 * Removes the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param name the name
	 * @param ownerType the owner type
	 * @param endPeriod the end period
	 * @return the social activity counter that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityCounter removeByG_C_C_N_O_E(long groupId,
		long classNameId, long classPK, String name, int ownerType,
		int endPeriod) throws NoSuchActivityCounterException, SystemException {
		SocialActivityCounter socialActivityCounter = findByG_C_C_N_O_E(groupId,
				classNameId, classPK, name, ownerType, endPeriod);

		return remove(socialActivityCounter);
	}

	/**
	 * Removes all the social activity counters from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SocialActivityCounter socialActivityCounter : findAll()) {
			remove(socialActivityCounter);
		}
	}

	/**
	 * Returns the number of social activity counters where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching social activity counters
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALACTIVITYCOUNTER_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param ownerType the owner type
	 * @return the number of matching social activity counters
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C_C_O(long groupId, long classNameId, long classPK,
		int ownerType) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, ownerType
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C_C_O,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_SOCIALACTIVITYCOUNTER_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_O_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_O_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_O_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_C_C_O_OWNERTYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(ownerType);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_C_O,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param name the name
	 * @param ownerType the owner type
	 * @param startPeriod the start period
	 * @return the number of matching social activity counters
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C_C_N_O_S(long groupId, long classNameId, long classPK,
		String name, int ownerType, int startPeriod) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, name, ownerType, startPeriod
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C_C_N_O_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_COUNT_SOCIALACTIVITYCOUNTER_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_N_O_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_S_CLASSPK_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_C_C_N_O_S_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_C_C_N_O_S_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_C_C_N_O_S_NAME_2);
				}
			}

			query.append(_FINDER_COLUMN_G_C_C_N_O_S_OWNERTYPE_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_S_STARTPERIOD_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(ownerType);

				qPos.add(startPeriod);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_C_N_O_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param name the name
	 * @param ownerType the owner type
	 * @param endPeriod the end period
	 * @return the number of matching social activity counters
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C_C_N_O_E(long groupId, long classNameId, long classPK,
		String name, int ownerType, int endPeriod) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, name, ownerType, endPeriod
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C_C_N_O_E,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_COUNT_SOCIALACTIVITYCOUNTER_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_N_O_E_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_E_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_E_CLASSPK_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_C_C_N_O_E_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_C_C_N_O_E_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_C_C_N_O_E_NAME_2);
				}
			}

			query.append(_FINDER_COLUMN_G_C_C_N_O_E_OWNERTYPE_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_E_ENDPERIOD_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(ownerType);

				qPos.add(endPeriod);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_C_N_O_E,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social activity counters.
	 *
	 * @return the number of social activity counters
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SOCIALACTIVITYCOUNTER);

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
	 * Initializes the social activity counter persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.social.model.SocialActivityCounter")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialActivityCounter>> listenersList = new ArrayList<ModelListener<SocialActivityCounter>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialActivityCounter>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(SocialActivityCounterImpl.class.getName());
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
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	private static final String _SQL_SELECT_SOCIALACTIVITYCOUNTER = "SELECT socialActivityCounter FROM SocialActivityCounter socialActivityCounter";
	private static final String _SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE = "SELECT socialActivityCounter FROM SocialActivityCounter socialActivityCounter WHERE ";
	private static final String _SQL_COUNT_SOCIALACTIVITYCOUNTER = "SELECT COUNT(socialActivityCounter) FROM SocialActivityCounter socialActivityCounter";
	private static final String _SQL_COUNT_SOCIALACTIVITYCOUNTER_WHERE = "SELECT COUNT(socialActivityCounter) FROM SocialActivityCounter socialActivityCounter WHERE ";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "socialActivityCounter.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "socialActivityCounter.classPK = ?";
	private static final String _FINDER_COLUMN_G_C_C_O_GROUPID_2 = "socialActivityCounter.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_O_CLASSNAMEID_2 = "socialActivityCounter.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_O_CLASSPK_2 = "socialActivityCounter.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_O_OWNERTYPE_2 = "socialActivityCounter.ownerType = ? AND socialActivityCounter.endPeriod = -1";
	private static final String _FINDER_COLUMN_G_C_C_N_O_S_GROUPID_2 = "socialActivityCounter.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_S_CLASSNAMEID_2 = "socialActivityCounter.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_S_CLASSPK_2 = "socialActivityCounter.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_S_NAME_1 = "socialActivityCounter.name IS NULL AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_S_NAME_2 = "socialActivityCounter.name = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_S_NAME_3 = "(socialActivityCounter.name IS NULL OR socialActivityCounter.name = ?) AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_S_OWNERTYPE_2 = "socialActivityCounter.ownerType = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_S_STARTPERIOD_2 = "socialActivityCounter.startPeriod = ?";
	private static final String _FINDER_COLUMN_G_C_C_N_O_E_GROUPID_2 = "socialActivityCounter.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_E_CLASSNAMEID_2 = "socialActivityCounter.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_E_CLASSPK_2 = "socialActivityCounter.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_E_NAME_1 = "socialActivityCounter.name IS NULL AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_E_NAME_2 = "socialActivityCounter.name = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_E_NAME_3 = "(socialActivityCounter.name IS NULL OR socialActivityCounter.name = ?) AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_E_OWNERTYPE_2 = "socialActivityCounter.ownerType = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_E_ENDPERIOD_2 = "socialActivityCounter.endPeriod = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialActivityCounter.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialActivityCounter exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SocialActivityCounter exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(SocialActivityCounterPersistenceImpl.class);
	private static SocialActivityCounter _nullSocialActivityCounter = new SocialActivityCounterImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SocialActivityCounter> toCacheModel() {
				return _nullSocialActivityCounterCacheModel;
			}
		};

	private static CacheModel<SocialActivityCounter> _nullSocialActivityCounterCacheModel =
		new CacheModel<SocialActivityCounter>() {
			public SocialActivityCounter toEntityModel() {
				return _nullSocialActivityCounter;
			}
		};
}