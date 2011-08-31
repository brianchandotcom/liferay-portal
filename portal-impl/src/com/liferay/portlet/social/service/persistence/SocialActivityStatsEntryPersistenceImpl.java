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
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.social.NoSuchActivityStatsEntryException;
import com.liferay.portlet.social.model.SocialActivityStatsEntry;
import com.liferay.portlet.social.model.impl.SocialActivityStatsEntryImpl;
import com.liferay.portlet.social.model.impl.SocialActivityStatsEntryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the social activity stats entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityStatsEntryPersistence
 * @see SocialActivityStatsEntryUtil
 * @generated
 */
public class SocialActivityStatsEntryPersistenceImpl extends BasePersistenceImpl<SocialActivityStatsEntry>
	implements SocialActivityStatsEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SocialActivityStatsEntryUtil} to access the social activity stats entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SocialActivityStatsEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_CN_CP = new FinderPath(SocialActivityStatsEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityStatsEntryModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityStatsEntryImpl.class, FINDER_CLASS_NAME_LIST,
			"findByCN_CP",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_CN_CP = new FinderPath(SocialActivityStatsEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityStatsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByCN_CP",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_CN_CP_CT = new FinderPath(SocialActivityStatsEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityStatsEntryModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityStatsEntryImpl.class, FINDER_CLASS_NAME_LIST,
			"findByG_CN_CP_CT",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_CN_CP_CT = new FinderPath(SocialActivityStatsEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityStatsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_CN_CP_CT",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_G_CN_CP_CT_SN_SPE = new FinderPath(SocialActivityStatsEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityStatsEntryModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityStatsEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_CN_CP_CT_SN_SPE",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_CN_CP_CT_SN_SPE = new FinderPath(SocialActivityStatsEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityStatsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_CN_CP_CT_SN_SPE",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_G_CN_CP_CT_SN_SPS = new FinderPath(SocialActivityStatsEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityStatsEntryModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityStatsEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_CN_CP_CT_SN_SPS",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_CN_CP_CT_SN_SPS = new FinderPath(SocialActivityStatsEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityStatsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_CN_CP_CT_SN_SPS",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SocialActivityStatsEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityStatsEntryModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityStatsEntryImpl.class, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialActivityStatsEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityStatsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the social activity stats entry in the entity cache if it is enabled.
	 *
	 * @param socialActivityStatsEntry the social activity stats entry
	 */
	public void cacheResult(SocialActivityStatsEntry socialActivityStatsEntry) {
		EntityCacheUtil.putResult(SocialActivityStatsEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityStatsEntryImpl.class,
			socialActivityStatsEntry.getPrimaryKey(), socialActivityStatsEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_CN_CP_CT_SN_SPE,
			new Object[] {
				Long.valueOf(socialActivityStatsEntry.getGroupId()),
				Long.valueOf(socialActivityStatsEntry.getClassNameId()),
				Long.valueOf(socialActivityStatsEntry.getClassPK()),
				Integer.valueOf(socialActivityStatsEntry.getClassType()),
				
			socialActivityStatsEntry.getStatName(),
				Integer.valueOf(socialActivityStatsEntry.getStatPeriodEnd())
			}, socialActivityStatsEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_CN_CP_CT_SN_SPS,
			new Object[] {
				Long.valueOf(socialActivityStatsEntry.getGroupId()),
				Long.valueOf(socialActivityStatsEntry.getClassNameId()),
				Long.valueOf(socialActivityStatsEntry.getClassPK()),
				Integer.valueOf(socialActivityStatsEntry.getClassType()),
				
			socialActivityStatsEntry.getStatName(),
				Integer.valueOf(socialActivityStatsEntry.getStatPeriodStart())
			}, socialActivityStatsEntry);

		socialActivityStatsEntry.resetOriginalValues();
	}

	/**
	 * Caches the social activity stats entries in the entity cache if it is enabled.
	 *
	 * @param socialActivityStatsEntries the social activity stats entries
	 */
	public void cacheResult(
		List<SocialActivityStatsEntry> socialActivityStatsEntries) {
		for (SocialActivityStatsEntry socialActivityStatsEntry : socialActivityStatsEntries) {
			if (EntityCacheUtil.getResult(
						SocialActivityStatsEntryModelImpl.ENTITY_CACHE_ENABLED,
						SocialActivityStatsEntryImpl.class,
						socialActivityStatsEntry.getPrimaryKey()) == null) {
				cacheResult(socialActivityStatsEntry);
			}
		}
	}

	/**
	 * Clears the cache for all social activity stats entries.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SocialActivityStatsEntryImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SocialActivityStatsEntryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the social activity stats entry.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SocialActivityStatsEntry socialActivityStatsEntry) {
		EntityCacheUtil.removeResult(SocialActivityStatsEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityStatsEntryImpl.class,
			socialActivityStatsEntry.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL, FINDER_ARGS_EMPTY);

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_CN_CP_CT_SN_SPE,
			new Object[] {
				Long.valueOf(socialActivityStatsEntry.getGroupId()),
				Long.valueOf(socialActivityStatsEntry.getClassNameId()),
				Long.valueOf(socialActivityStatsEntry.getClassPK()),
				Integer.valueOf(socialActivityStatsEntry.getClassType()),
				
			socialActivityStatsEntry.getStatName(),
				Integer.valueOf(socialActivityStatsEntry.getStatPeriodEnd())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_CN_CP_CT_SN_SPS,
			new Object[] {
				Long.valueOf(socialActivityStatsEntry.getGroupId()),
				Long.valueOf(socialActivityStatsEntry.getClassNameId()),
				Long.valueOf(socialActivityStatsEntry.getClassPK()),
				Integer.valueOf(socialActivityStatsEntry.getClassType()),
				
			socialActivityStatsEntry.getStatName(),
				Integer.valueOf(socialActivityStatsEntry.getStatPeriodStart())
			});
	}

	/**
	 * Creates a new social activity stats entry with the primary key. Does not add the social activity stats entry to the database.
	 *
	 * @param activityStatsEntryId the primary key for the new social activity stats entry
	 * @return the new social activity stats entry
	 */
	public SocialActivityStatsEntry create(long activityStatsEntryId) {
		SocialActivityStatsEntry socialActivityStatsEntry = new SocialActivityStatsEntryImpl();

		socialActivityStatsEntry.setNew(true);
		socialActivityStatsEntry.setPrimaryKey(activityStatsEntryId);

		return socialActivityStatsEntry;
	}

	/**
	 * Removes the social activity stats entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the social activity stats entry
	 * @return the social activity stats entry that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a social activity stats entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivityStatsEntry remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the social activity stats entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param activityStatsEntryId the primary key of the social activity stats entry
	 * @return the social activity stats entry that was removed
	 * @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a social activity stats entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityStatsEntry remove(long activityStatsEntryId)
		throws NoSuchActivityStatsEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialActivityStatsEntry socialActivityStatsEntry = (SocialActivityStatsEntry)session.get(SocialActivityStatsEntryImpl.class,
					Long.valueOf(activityStatsEntryId));

			if (socialActivityStatsEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						activityStatsEntryId);
				}

				throw new NoSuchActivityStatsEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					activityStatsEntryId);
			}

			return socialActivityStatsEntryPersistence.remove(socialActivityStatsEntry);
		}
		catch (NoSuchActivityStatsEntryException nsee) {
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
	 * Removes the social activity stats entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param socialActivityStatsEntry the social activity stats entry
	 * @return the social activity stats entry that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivityStatsEntry remove(
		SocialActivityStatsEntry socialActivityStatsEntry)
		throws SystemException {
		return super.remove(socialActivityStatsEntry);
	}

	@Override
	protected SocialActivityStatsEntry removeImpl(
		SocialActivityStatsEntry socialActivityStatsEntry)
		throws SystemException {
		socialActivityStatsEntry = toUnwrappedModel(socialActivityStatsEntry);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, socialActivityStatsEntry);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		SocialActivityStatsEntryModelImpl socialActivityStatsEntryModelImpl = (SocialActivityStatsEntryModelImpl)socialActivityStatsEntry;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_CN_CP_CT_SN_SPE,
			new Object[] {
				Long.valueOf(socialActivityStatsEntryModelImpl.getGroupId()),
				Long.valueOf(socialActivityStatsEntryModelImpl.getClassNameId()),
				Long.valueOf(socialActivityStatsEntryModelImpl.getClassPK()),
				Integer.valueOf(
					socialActivityStatsEntryModelImpl.getClassType()),
				
			socialActivityStatsEntryModelImpl.getStatName(),
				Integer.valueOf(
					socialActivityStatsEntryModelImpl.getStatPeriodEnd())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_CN_CP_CT_SN_SPS,
			new Object[] {
				Long.valueOf(socialActivityStatsEntryModelImpl.getGroupId()),
				Long.valueOf(socialActivityStatsEntryModelImpl.getClassNameId()),
				Long.valueOf(socialActivityStatsEntryModelImpl.getClassPK()),
				Integer.valueOf(
					socialActivityStatsEntryModelImpl.getClassType()),
				
			socialActivityStatsEntryModelImpl.getStatName(),
				Integer.valueOf(
					socialActivityStatsEntryModelImpl.getStatPeriodStart())
			});

		EntityCacheUtil.removeResult(SocialActivityStatsEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityStatsEntryImpl.class,
			socialActivityStatsEntry.getPrimaryKey());

		return socialActivityStatsEntry;
	}

	@Override
	public SocialActivityStatsEntry updateImpl(
		com.liferay.portlet.social.model.SocialActivityStatsEntry socialActivityStatsEntry,
		boolean merge) throws SystemException {
		socialActivityStatsEntry = toUnwrappedModel(socialActivityStatsEntry);

		boolean isNew = socialActivityStatsEntry.isNew();

		SocialActivityStatsEntryModelImpl socialActivityStatsEntryModelImpl = (SocialActivityStatsEntryModelImpl)socialActivityStatsEntry;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, socialActivityStatsEntry, merge);

			socialActivityStatsEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SocialActivityStatsEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityStatsEntryImpl.class,
			socialActivityStatsEntry.getPrimaryKey(), socialActivityStatsEntry);

		if (!isNew &&
				((socialActivityStatsEntry.getGroupId() != socialActivityStatsEntryModelImpl.getOriginalGroupId()) ||
				(socialActivityStatsEntry.getClassNameId() != socialActivityStatsEntryModelImpl.getOriginalClassNameId()) ||
				(socialActivityStatsEntry.getClassPK() != socialActivityStatsEntryModelImpl.getOriginalClassPK()) ||
				(socialActivityStatsEntry.getClassType() != socialActivityStatsEntryModelImpl.getOriginalClassType()) ||
				!Validator.equals(socialActivityStatsEntry.getStatName(),
					socialActivityStatsEntryModelImpl.getOriginalStatName()) ||
				(socialActivityStatsEntry.getStatPeriodEnd() != socialActivityStatsEntryModelImpl.getOriginalStatPeriodEnd()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_CN_CP_CT_SN_SPE,
				new Object[] {
					Long.valueOf(
						socialActivityStatsEntryModelImpl.getOriginalGroupId()),
					Long.valueOf(
						socialActivityStatsEntryModelImpl.getOriginalClassNameId()),
					Long.valueOf(
						socialActivityStatsEntryModelImpl.getOriginalClassPK()),
					Integer.valueOf(
						socialActivityStatsEntryModelImpl.getOriginalClassType()),
					
				socialActivityStatsEntryModelImpl.getOriginalStatName(),
					Integer.valueOf(
						socialActivityStatsEntryModelImpl.getOriginalStatPeriodEnd())
				});
		}

		if (isNew ||
				((socialActivityStatsEntry.getGroupId() != socialActivityStatsEntryModelImpl.getOriginalGroupId()) ||
				(socialActivityStatsEntry.getClassNameId() != socialActivityStatsEntryModelImpl.getOriginalClassNameId()) ||
				(socialActivityStatsEntry.getClassPK() != socialActivityStatsEntryModelImpl.getOriginalClassPK()) ||
				(socialActivityStatsEntry.getClassType() != socialActivityStatsEntryModelImpl.getOriginalClassType()) ||
				!Validator.equals(socialActivityStatsEntry.getStatName(),
					socialActivityStatsEntryModelImpl.getOriginalStatName()) ||
				(socialActivityStatsEntry.getStatPeriodEnd() != socialActivityStatsEntryModelImpl.getOriginalStatPeriodEnd()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_CN_CP_CT_SN_SPE,
				new Object[] {
					Long.valueOf(socialActivityStatsEntry.getGroupId()),
					Long.valueOf(socialActivityStatsEntry.getClassNameId()),
					Long.valueOf(socialActivityStatsEntry.getClassPK()),
					Integer.valueOf(socialActivityStatsEntry.getClassType()),
					
				socialActivityStatsEntry.getStatName(),
					Integer.valueOf(socialActivityStatsEntry.getStatPeriodEnd())
				}, socialActivityStatsEntry);
		}

		if (!isNew &&
				((socialActivityStatsEntry.getGroupId() != socialActivityStatsEntryModelImpl.getOriginalGroupId()) ||
				(socialActivityStatsEntry.getClassNameId() != socialActivityStatsEntryModelImpl.getOriginalClassNameId()) ||
				(socialActivityStatsEntry.getClassPK() != socialActivityStatsEntryModelImpl.getOriginalClassPK()) ||
				(socialActivityStatsEntry.getClassType() != socialActivityStatsEntryModelImpl.getOriginalClassType()) ||
				!Validator.equals(socialActivityStatsEntry.getStatName(),
					socialActivityStatsEntryModelImpl.getOriginalStatName()) ||
				(socialActivityStatsEntry.getStatPeriodStart() != socialActivityStatsEntryModelImpl.getOriginalStatPeriodStart()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_CN_CP_CT_SN_SPS,
				new Object[] {
					Long.valueOf(
						socialActivityStatsEntryModelImpl.getOriginalGroupId()),
					Long.valueOf(
						socialActivityStatsEntryModelImpl.getOriginalClassNameId()),
					Long.valueOf(
						socialActivityStatsEntryModelImpl.getOriginalClassPK()),
					Integer.valueOf(
						socialActivityStatsEntryModelImpl.getOriginalClassType()),
					
				socialActivityStatsEntryModelImpl.getOriginalStatName(),
					Integer.valueOf(
						socialActivityStatsEntryModelImpl.getOriginalStatPeriodStart())
				});
		}

		if (isNew ||
				((socialActivityStatsEntry.getGroupId() != socialActivityStatsEntryModelImpl.getOriginalGroupId()) ||
				(socialActivityStatsEntry.getClassNameId() != socialActivityStatsEntryModelImpl.getOriginalClassNameId()) ||
				(socialActivityStatsEntry.getClassPK() != socialActivityStatsEntryModelImpl.getOriginalClassPK()) ||
				(socialActivityStatsEntry.getClassType() != socialActivityStatsEntryModelImpl.getOriginalClassType()) ||
				!Validator.equals(socialActivityStatsEntry.getStatName(),
					socialActivityStatsEntryModelImpl.getOriginalStatName()) ||
				(socialActivityStatsEntry.getStatPeriodStart() != socialActivityStatsEntryModelImpl.getOriginalStatPeriodStart()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_CN_CP_CT_SN_SPS,
				new Object[] {
					Long.valueOf(socialActivityStatsEntry.getGroupId()),
					Long.valueOf(socialActivityStatsEntry.getClassNameId()),
					Long.valueOf(socialActivityStatsEntry.getClassPK()),
					Integer.valueOf(socialActivityStatsEntry.getClassType()),
					
				socialActivityStatsEntry.getStatName(),
					Integer.valueOf(
						socialActivityStatsEntry.getStatPeriodStart())
				}, socialActivityStatsEntry);
		}

		return socialActivityStatsEntry;
	}

	protected SocialActivityStatsEntry toUnwrappedModel(
		SocialActivityStatsEntry socialActivityStatsEntry) {
		if (socialActivityStatsEntry instanceof SocialActivityStatsEntryImpl) {
			return socialActivityStatsEntry;
		}

		SocialActivityStatsEntryImpl socialActivityStatsEntryImpl = new SocialActivityStatsEntryImpl();

		socialActivityStatsEntryImpl.setNew(socialActivityStatsEntry.isNew());
		socialActivityStatsEntryImpl.setPrimaryKey(socialActivityStatsEntry.getPrimaryKey());

		socialActivityStatsEntryImpl.setActivityStatsEntryId(socialActivityStatsEntry.getActivityStatsEntryId());
		socialActivityStatsEntryImpl.setGroupId(socialActivityStatsEntry.getGroupId());
		socialActivityStatsEntryImpl.setCompanyId(socialActivityStatsEntry.getCompanyId());
		socialActivityStatsEntryImpl.setClassNameId(socialActivityStatsEntry.getClassNameId());
		socialActivityStatsEntryImpl.setClassPK(socialActivityStatsEntry.getClassPK());
		socialActivityStatsEntryImpl.setClassType(socialActivityStatsEntry.getClassType());
		socialActivityStatsEntryImpl.setStatName(socialActivityStatsEntry.getStatName());
		socialActivityStatsEntryImpl.setCurrentValue(socialActivityStatsEntry.getCurrentValue());
		socialActivityStatsEntryImpl.setOverallValue(socialActivityStatsEntry.getOverallValue());
		socialActivityStatsEntryImpl.setGraceValue(socialActivityStatsEntry.getGraceValue());
		socialActivityStatsEntryImpl.setStatPeriodStart(socialActivityStatsEntry.getStatPeriodStart());
		socialActivityStatsEntryImpl.setStatPeriodEnd(socialActivityStatsEntry.getStatPeriodEnd());

		return socialActivityStatsEntryImpl;
	}

	/**
	 * Returns the social activity stats entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity stats entry
	 * @return the social activity stats entry
	 * @throws com.liferay.portal.NoSuchModelException if a social activity stats entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivityStatsEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social activity stats entry with the primary key or throws a {@link com.liferay.portlet.social.NoSuchActivityStatsEntryException} if it could not be found.
	 *
	 * @param activityStatsEntryId the primary key of the social activity stats entry
	 * @return the social activity stats entry
	 * @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a social activity stats entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityStatsEntry findByPrimaryKey(long activityStatsEntryId)
		throws NoSuchActivityStatsEntryException, SystemException {
		SocialActivityStatsEntry socialActivityStatsEntry = fetchByPrimaryKey(activityStatsEntryId);

		if (socialActivityStatsEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					activityStatsEntryId);
			}

			throw new NoSuchActivityStatsEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				activityStatsEntryId);
		}

		return socialActivityStatsEntry;
	}

	/**
	 * Returns the social activity stats entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity stats entry
	 * @return the social activity stats entry, or <code>null</code> if a social activity stats entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivityStatsEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social activity stats entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param activityStatsEntryId the primary key of the social activity stats entry
	 * @return the social activity stats entry, or <code>null</code> if a social activity stats entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityStatsEntry fetchByPrimaryKey(long activityStatsEntryId)
		throws SystemException {
		SocialActivityStatsEntry socialActivityStatsEntry = (SocialActivityStatsEntry)EntityCacheUtil.getResult(SocialActivityStatsEntryModelImpl.ENTITY_CACHE_ENABLED,
				SocialActivityStatsEntryImpl.class, activityStatsEntryId);

		if (socialActivityStatsEntry == _nullSocialActivityStatsEntry) {
			return null;
		}

		if (socialActivityStatsEntry == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				socialActivityStatsEntry = (SocialActivityStatsEntry)session.get(SocialActivityStatsEntryImpl.class,
						Long.valueOf(activityStatsEntryId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (socialActivityStatsEntry != null) {
					cacheResult(socialActivityStatsEntry);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(SocialActivityStatsEntryModelImpl.ENTITY_CACHE_ENABLED,
						SocialActivityStatsEntryImpl.class,
						activityStatsEntryId, _nullSocialActivityStatsEntry);
				}

				closeSession(session);
			}
		}

		return socialActivityStatsEntry;
	}

	/**
	 * Returns all the social activity stats entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching social activity stats entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityStatsEntry> findByCN_CP(long classNameId,
		long classPK) throws SystemException {
		return findByCN_CP(classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity stats entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of social activity stats entries
	 * @param end the upper bound of the range of social activity stats entries (not inclusive)
	 * @return the range of matching social activity stats entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityStatsEntry> findByCN_CP(long classNameId,
		long classPK, int start, int end) throws SystemException {
		return findByCN_CP(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity stats entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of social activity stats entries
	 * @param end the upper bound of the range of social activity stats entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity stats entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityStatsEntry> findByCN_CP(long classNameId,
		long classPK, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				classNameId, classPK,
				
				start, end, orderByComparator
			};

		List<SocialActivityStatsEntry> list = (List<SocialActivityStatsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_CN_CP,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SOCIALACTIVITYSTATSENTRY_WHERE);

			query.append(_FINDER_COLUMN_CN_CP_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_CN_CP_CLASSPK_2);

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

				list = (List<SocialActivityStatsEntry>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_CN_CP,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_CN_CP,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first social activity stats entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity stats entry
	 * @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a matching social activity stats entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityStatsEntry findByCN_CP_First(long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws NoSuchActivityStatsEntryException, SystemException {
		List<SocialActivityStatsEntry> list = findByCN_CP(classNameId, classPK,
				0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchActivityStatsEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last social activity stats entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity stats entry
	 * @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a matching social activity stats entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityStatsEntry findByCN_CP_Last(long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws NoSuchActivityStatsEntryException, SystemException {
		int count = countByCN_CP(classNameId, classPK);

		List<SocialActivityStatsEntry> list = findByCN_CP(classNameId, classPK,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchActivityStatsEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the social activity stats entries before and after the current social activity stats entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param activityStatsEntryId the primary key of the current social activity stats entry
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity stats entry
	 * @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a social activity stats entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityStatsEntry[] findByCN_CP_PrevAndNext(
		long activityStatsEntryId, long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchActivityStatsEntryException, SystemException {
		SocialActivityStatsEntry socialActivityStatsEntry = findByPrimaryKey(activityStatsEntryId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityStatsEntry[] array = new SocialActivityStatsEntryImpl[3];

			array[0] = getByCN_CP_PrevAndNext(session,
					socialActivityStatsEntry, classNameId, classPK,
					orderByComparator, true);

			array[1] = socialActivityStatsEntry;

			array[2] = getByCN_CP_PrevAndNext(session,
					socialActivityStatsEntry, classNameId, classPK,
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

	protected SocialActivityStatsEntry getByCN_CP_PrevAndNext(Session session,
		SocialActivityStatsEntry socialActivityStatsEntry, long classNameId,
		long classPK, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYSTATSENTRY_WHERE);

		query.append(_FINDER_COLUMN_CN_CP_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_CN_CP_CLASSPK_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(socialActivityStatsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivityStatsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social activity stats entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param classType the class type
	 * @return the matching social activity stats entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityStatsEntry> findByG_CN_CP_CT(long groupId,
		long classNameId, long classPK, int classType)
		throws SystemException {
		return findByG_CN_CP_CT(groupId, classNameId, classPK, classType,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity stats entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param classType the class type
	 * @param start the lower bound of the range of social activity stats entries
	 * @param end the upper bound of the range of social activity stats entries (not inclusive)
	 * @return the range of matching social activity stats entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityStatsEntry> findByG_CN_CP_CT(long groupId,
		long classNameId, long classPK, int classType, int start, int end)
		throws SystemException {
		return findByG_CN_CP_CT(groupId, classNameId, classPK, classType,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity stats entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param classType the class type
	 * @param start the lower bound of the range of social activity stats entries
	 * @param end the upper bound of the range of social activity stats entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity stats entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityStatsEntry> findByG_CN_CP_CT(long groupId,
		long classNameId, long classPK, int classType, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, classType,
				
				start, end, orderByComparator
			};

		List<SocialActivityStatsEntry> list = (List<SocialActivityStatsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_CN_CP_CT,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_SOCIALACTIVITYSTATSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_GROUPID_2);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_CLASSTYPE_2);

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

				qPos.add(classType);

				list = (List<SocialActivityStatsEntry>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_CN_CP_CT,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_CN_CP_CT,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first social activity stats entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param classType the class type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity stats entry
	 * @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a matching social activity stats entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityStatsEntry findByG_CN_CP_CT_First(long groupId,
		long classNameId, long classPK, int classType,
		OrderByComparator orderByComparator)
		throws NoSuchActivityStatsEntryException, SystemException {
		List<SocialActivityStatsEntry> list = findByG_CN_CP_CT(groupId,
				classNameId, classPK, classType, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", classType=");
			msg.append(classType);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchActivityStatsEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last social activity stats entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param classType the class type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity stats entry
	 * @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a matching social activity stats entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityStatsEntry findByG_CN_CP_CT_Last(long groupId,
		long classNameId, long classPK, int classType,
		OrderByComparator orderByComparator)
		throws NoSuchActivityStatsEntryException, SystemException {
		int count = countByG_CN_CP_CT(groupId, classNameId, classPK, classType);

		List<SocialActivityStatsEntry> list = findByG_CN_CP_CT(groupId,
				classNameId, classPK, classType, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", classType=");
			msg.append(classType);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchActivityStatsEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the social activity stats entries before and after the current social activity stats entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param activityStatsEntryId the primary key of the current social activity stats entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param classType the class type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity stats entry
	 * @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a social activity stats entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityStatsEntry[] findByG_CN_CP_CT_PrevAndNext(
		long activityStatsEntryId, long groupId, long classNameId,
		long classPK, int classType, OrderByComparator orderByComparator)
		throws NoSuchActivityStatsEntryException, SystemException {
		SocialActivityStatsEntry socialActivityStatsEntry = findByPrimaryKey(activityStatsEntryId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityStatsEntry[] array = new SocialActivityStatsEntryImpl[3];

			array[0] = getByG_CN_CP_CT_PrevAndNext(session,
					socialActivityStatsEntry, groupId, classNameId, classPK,
					classType, orderByComparator, true);

			array[1] = socialActivityStatsEntry;

			array[2] = getByG_CN_CP_CT_PrevAndNext(session,
					socialActivityStatsEntry, groupId, classNameId, classPK,
					classType, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivityStatsEntry getByG_CN_CP_CT_PrevAndNext(
		Session session, SocialActivityStatsEntry socialActivityStatsEntry,
		long groupId, long classNameId, long classPK, int classType,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYSTATSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_CN_CP_CT_GROUPID_2);

		query.append(_FINDER_COLUMN_G_CN_CP_CT_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_CN_CP_CT_CLASSPK_2);

		query.append(_FINDER_COLUMN_G_CN_CP_CT_CLASSTYPE_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(classType);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(socialActivityStatsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivityStatsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodEnd = &#63; or throws a {@link com.liferay.portlet.social.NoSuchActivityStatsEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param classType the class type
	 * @param statName the stat name
	 * @param statPeriodEnd the stat period end
	 * @return the matching social activity stats entry
	 * @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a matching social activity stats entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityStatsEntry findByG_CN_CP_CT_SN_SPE(long groupId,
		long classNameId, long classPK, int classType, String statName,
		int statPeriodEnd)
		throws NoSuchActivityStatsEntryException, SystemException {
		SocialActivityStatsEntry socialActivityStatsEntry = fetchByG_CN_CP_CT_SN_SPE(groupId,
				classNameId, classPK, classType, statName, statPeriodEnd);

		if (socialActivityStatsEntry == null) {
			StringBundler msg = new StringBundler(14);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", classType=");
			msg.append(classType);

			msg.append(", statName=");
			msg.append(statName);

			msg.append(", statPeriodEnd=");
			msg.append(statPeriodEnd);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchActivityStatsEntryException(msg.toString());
		}

		return socialActivityStatsEntry;
	}

	/**
	 * Returns the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodEnd = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param classType the class type
	 * @param statName the stat name
	 * @param statPeriodEnd the stat period end
	 * @return the matching social activity stats entry, or <code>null</code> if a matching social activity stats entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityStatsEntry fetchByG_CN_CP_CT_SN_SPE(long groupId,
		long classNameId, long classPK, int classType, String statName,
		int statPeriodEnd) throws SystemException {
		return fetchByG_CN_CP_CT_SN_SPE(groupId, classNameId, classPK,
			classType, statName, statPeriodEnd, true);
	}

	/**
	 * Returns the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodEnd = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param classType the class type
	 * @param statName the stat name
	 * @param statPeriodEnd the stat period end
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching social activity stats entry, or <code>null</code> if a matching social activity stats entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityStatsEntry fetchByG_CN_CP_CT_SN_SPE(long groupId,
		long classNameId, long classPK, int classType, String statName,
		int statPeriodEnd, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, classType, statName,
				statPeriodEnd
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_CN_CP_CT_SN_SPE,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_SELECT_SOCIALACTIVITYSTATSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPE_GROUPID_2);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPE_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPE_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPE_CLASSTYPE_2);

			if (statName == null) {
				query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPE_STATNAME_1);
			}
			else {
				if (statName.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPE_STATNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPE_STATNAME_2);
				}
			}

			query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPE_STATPERIODEND_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(classType);

				if (statName != null) {
					qPos.add(statName);
				}

				qPos.add(statPeriodEnd);

				List<SocialActivityStatsEntry> list = q.list();

				result = list;

				SocialActivityStatsEntry socialActivityStatsEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_CN_CP_CT_SN_SPE,
						finderArgs, list);
				}
				else {
					socialActivityStatsEntry = list.get(0);

					cacheResult(socialActivityStatsEntry);

					if ((socialActivityStatsEntry.getGroupId() != groupId) ||
							(socialActivityStatsEntry.getClassNameId() != classNameId) ||
							(socialActivityStatsEntry.getClassPK() != classPK) ||
							(socialActivityStatsEntry.getClassType() != classType) ||
							(socialActivityStatsEntry.getStatName() == null) ||
							!socialActivityStatsEntry.getStatName()
														 .equals(statName) ||
							(socialActivityStatsEntry.getStatPeriodEnd() != statPeriodEnd)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_CN_CP_CT_SN_SPE,
							finderArgs, socialActivityStatsEntry);
					}
				}

				return socialActivityStatsEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_CN_CP_CT_SN_SPE,
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
				return (SocialActivityStatsEntry)result;
			}
		}
	}

	/**
	 * Returns the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodStart = &#63; or throws a {@link com.liferay.portlet.social.NoSuchActivityStatsEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param classType the class type
	 * @param statName the stat name
	 * @param statPeriodStart the stat period start
	 * @return the matching social activity stats entry
	 * @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a matching social activity stats entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityStatsEntry findByG_CN_CP_CT_SN_SPS(long groupId,
		long classNameId, long classPK, int classType, String statName,
		int statPeriodStart)
		throws NoSuchActivityStatsEntryException, SystemException {
		SocialActivityStatsEntry socialActivityStatsEntry = fetchByG_CN_CP_CT_SN_SPS(groupId,
				classNameId, classPK, classType, statName, statPeriodStart);

		if (socialActivityStatsEntry == null) {
			StringBundler msg = new StringBundler(14);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", classType=");
			msg.append(classType);

			msg.append(", statName=");
			msg.append(statName);

			msg.append(", statPeriodStart=");
			msg.append(statPeriodStart);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchActivityStatsEntryException(msg.toString());
		}

		return socialActivityStatsEntry;
	}

	/**
	 * Returns the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodStart = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param classType the class type
	 * @param statName the stat name
	 * @param statPeriodStart the stat period start
	 * @return the matching social activity stats entry, or <code>null</code> if a matching social activity stats entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityStatsEntry fetchByG_CN_CP_CT_SN_SPS(long groupId,
		long classNameId, long classPK, int classType, String statName,
		int statPeriodStart) throws SystemException {
		return fetchByG_CN_CP_CT_SN_SPS(groupId, classNameId, classPK,
			classType, statName, statPeriodStart, true);
	}

	/**
	 * Returns the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodStart = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param classType the class type
	 * @param statName the stat name
	 * @param statPeriodStart the stat period start
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching social activity stats entry, or <code>null</code> if a matching social activity stats entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityStatsEntry fetchByG_CN_CP_CT_SN_SPS(long groupId,
		long classNameId, long classPK, int classType, String statName,
		int statPeriodStart, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, classType, statName,
				statPeriodStart
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_CN_CP_CT_SN_SPS,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_SELECT_SOCIALACTIVITYSTATSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPS_GROUPID_2);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPS_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPS_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPS_CLASSTYPE_2);

			if (statName == null) {
				query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPS_STATNAME_1);
			}
			else {
				if (statName.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPS_STATNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPS_STATNAME_2);
				}
			}

			query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPS_STATPERIODSTART_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(classType);

				if (statName != null) {
					qPos.add(statName);
				}

				qPos.add(statPeriodStart);

				List<SocialActivityStatsEntry> list = q.list();

				result = list;

				SocialActivityStatsEntry socialActivityStatsEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_CN_CP_CT_SN_SPS,
						finderArgs, list);
				}
				else {
					socialActivityStatsEntry = list.get(0);

					cacheResult(socialActivityStatsEntry);

					if ((socialActivityStatsEntry.getGroupId() != groupId) ||
							(socialActivityStatsEntry.getClassNameId() != classNameId) ||
							(socialActivityStatsEntry.getClassPK() != classPK) ||
							(socialActivityStatsEntry.getClassType() != classType) ||
							(socialActivityStatsEntry.getStatName() == null) ||
							!socialActivityStatsEntry.getStatName()
														 .equals(statName) ||
							(socialActivityStatsEntry.getStatPeriodStart() != statPeriodStart)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_CN_CP_CT_SN_SPS,
							finderArgs, socialActivityStatsEntry);
					}
				}

				return socialActivityStatsEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_CN_CP_CT_SN_SPS,
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
				return (SocialActivityStatsEntry)result;
			}
		}
	}

	/**
	 * Returns all the social activity stats entries.
	 *
	 * @return the social activity stats entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityStatsEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity stats entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity stats entries
	 * @param end the upper bound of the range of social activity stats entries (not inclusive)
	 * @return the range of social activity stats entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityStatsEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity stats entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity stats entries
	 * @param end the upper bound of the range of social activity stats entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of social activity stats entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityStatsEntry> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		List<SocialActivityStatsEntry> list = (List<SocialActivityStatsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SOCIALACTIVITYSTATSENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SOCIALACTIVITYSTATSENTRY;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SocialActivityStatsEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialActivityStatsEntry>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the social activity stats entries where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCN_CP(long classNameId, long classPK)
		throws SystemException {
		for (SocialActivityStatsEntry socialActivityStatsEntry : findByCN_CP(
				classNameId, classPK)) {
			socialActivityStatsEntryPersistence.remove(socialActivityStatsEntry);
		}
	}

	/**
	 * Removes all the social activity stats entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param classType the class type
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_CN_CP_CT(long groupId, long classNameId,
		long classPK, int classType) throws SystemException {
		for (SocialActivityStatsEntry socialActivityStatsEntry : findByG_CN_CP_CT(
				groupId, classNameId, classPK, classType)) {
			socialActivityStatsEntryPersistence.remove(socialActivityStatsEntry);
		}
	}

	/**
	 * Removes the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodEnd = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param classType the class type
	 * @param statName the stat name
	 * @param statPeriodEnd the stat period end
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_CN_CP_CT_SN_SPE(long groupId, long classNameId,
		long classPK, int classType, String statName, int statPeriodEnd)
		throws NoSuchActivityStatsEntryException, SystemException {
		SocialActivityStatsEntry socialActivityStatsEntry = findByG_CN_CP_CT_SN_SPE(groupId,
				classNameId, classPK, classType, statName, statPeriodEnd);

		socialActivityStatsEntryPersistence.remove(socialActivityStatsEntry);
	}

	/**
	 * Removes the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodStart = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param classType the class type
	 * @param statName the stat name
	 * @param statPeriodStart the stat period start
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_CN_CP_CT_SN_SPS(long groupId, long classNameId,
		long classPK, int classType, String statName, int statPeriodStart)
		throws NoSuchActivityStatsEntryException, SystemException {
		SocialActivityStatsEntry socialActivityStatsEntry = findByG_CN_CP_CT_SN_SPS(groupId,
				classNameId, classPK, classType, statName, statPeriodStart);

		socialActivityStatsEntryPersistence.remove(socialActivityStatsEntry);
	}

	/**
	 * Removes all the social activity stats entries from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SocialActivityStatsEntry socialActivityStatsEntry : findAll()) {
			socialActivityStatsEntryPersistence.remove(socialActivityStatsEntry);
		}
	}

	/**
	 * Returns the number of social activity stats entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching social activity stats entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCN_CP(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CN_CP,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALACTIVITYSTATSENTRY_WHERE);

			query.append(_FINDER_COLUMN_CN_CP_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_CN_CP_CLASSPK_2);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CN_CP,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social activity stats entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param classType the class type
	 * @return the number of matching social activity stats entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_CN_CP_CT(long groupId, long classNameId, long classPK,
		int classType) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, classType
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_CN_CP_CT,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_SOCIALACTIVITYSTATSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_GROUPID_2);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_CLASSTYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(classType);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_CN_CP_CT,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social activity stats entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodEnd = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param classType the class type
	 * @param statName the stat name
	 * @param statPeriodEnd the stat period end
	 * @return the number of matching social activity stats entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_CN_CP_CT_SN_SPE(long groupId, long classNameId,
		long classPK, int classType, String statName, int statPeriodEnd)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, classType, statName,
				statPeriodEnd
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_CN_CP_CT_SN_SPE,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_COUNT_SOCIALACTIVITYSTATSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPE_GROUPID_2);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPE_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPE_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPE_CLASSTYPE_2);

			if (statName == null) {
				query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPE_STATNAME_1);
			}
			else {
				if (statName.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPE_STATNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPE_STATNAME_2);
				}
			}

			query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPE_STATPERIODEND_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(classType);

				if (statName != null) {
					qPos.add(statName);
				}

				qPos.add(statPeriodEnd);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_CN_CP_CT_SN_SPE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social activity stats entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodStart = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param classType the class type
	 * @param statName the stat name
	 * @param statPeriodStart the stat period start
	 * @return the number of matching social activity stats entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_CN_CP_CT_SN_SPS(long groupId, long classNameId,
		long classPK, int classType, String statName, int statPeriodStart)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, classType, statName,
				statPeriodStart
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_CN_CP_CT_SN_SPS,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_COUNT_SOCIALACTIVITYSTATSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPS_GROUPID_2);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPS_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPS_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPS_CLASSTYPE_2);

			if (statName == null) {
				query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPS_STATNAME_1);
			}
			else {
				if (statName.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPS_STATNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPS_STATNAME_2);
				}
			}

			query.append(_FINDER_COLUMN_G_CN_CP_CT_SN_SPS_STATPERIODSTART_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(classType);

				if (statName != null) {
					qPos.add(statName);
				}

				qPos.add(statPeriodStart);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_CN_CP_CT_SN_SPS,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social activity stats entries.
	 *
	 * @return the number of social activity stats entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SOCIALACTIVITYSTATSENTRY);

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
	 * Initializes the social activity stats entry persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.social.model.SocialActivityStatsEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialActivityStatsEntry>> listenersList = new ArrayList<ModelListener<SocialActivityStatsEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialActivityStatsEntry>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(SocialActivityStatsEntryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	@BeanReference(type = SocialActivityAchievementPersistence.class)
	protected SocialActivityAchievementPersistence socialActivityAchievementPersistence;
	@BeanReference(type = SocialActivityLimitPersistence.class)
	protected SocialActivityLimitPersistence socialActivityLimitPersistence;
	@BeanReference(type = SocialActivitySettingPersistence.class)
	protected SocialActivitySettingPersistence socialActivitySettingPersistence;
	@BeanReference(type = SocialActivityStatsEntryPersistence.class)
	protected SocialActivityStatsEntryPersistence socialActivityStatsEntryPersistence;
	@BeanReference(type = SocialEquityAssetEntryPersistence.class)
	protected SocialEquityAssetEntryPersistence socialEquityAssetEntryPersistence;
	@BeanReference(type = SocialEquityGroupSettingPersistence.class)
	protected SocialEquityGroupSettingPersistence socialEquityGroupSettingPersistence;
	@BeanReference(type = SocialEquityHistoryPersistence.class)
	protected SocialEquityHistoryPersistence socialEquityHistoryPersistence;
	@BeanReference(type = SocialEquityLogPersistence.class)
	protected SocialEquityLogPersistence socialEquityLogPersistence;
	@BeanReference(type = SocialEquitySettingPersistence.class)
	protected SocialEquitySettingPersistence socialEquitySettingPersistence;
	@BeanReference(type = SocialEquityUserPersistence.class)
	protected SocialEquityUserPersistence socialEquityUserPersistence;
	@BeanReference(type = SocialRelationPersistence.class)
	protected SocialRelationPersistence socialRelationPersistence;
	@BeanReference(type = SocialRequestPersistence.class)
	protected SocialRequestPersistence socialRequestPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	private static final String _SQL_SELECT_SOCIALACTIVITYSTATSENTRY = "SELECT socialActivityStatsEntry FROM SocialActivityStatsEntry socialActivityStatsEntry";
	private static final String _SQL_SELECT_SOCIALACTIVITYSTATSENTRY_WHERE = "SELECT socialActivityStatsEntry FROM SocialActivityStatsEntry socialActivityStatsEntry WHERE ";
	private static final String _SQL_COUNT_SOCIALACTIVITYSTATSENTRY = "SELECT COUNT(socialActivityStatsEntry) FROM SocialActivityStatsEntry socialActivityStatsEntry";
	private static final String _SQL_COUNT_SOCIALACTIVITYSTATSENTRY_WHERE = "SELECT COUNT(socialActivityStatsEntry) FROM SocialActivityStatsEntry socialActivityStatsEntry WHERE ";
	private static final String _FINDER_COLUMN_CN_CP_CLASSNAMEID_2 = "socialActivityStatsEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_CN_CP_CLASSPK_2 = "socialActivityStatsEntry.classPK = ?";
	private static final String _FINDER_COLUMN_G_CN_CP_CT_GROUPID_2 = "socialActivityStatsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_CN_CP_CT_CLASSNAMEID_2 = "socialActivityStatsEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_CN_CP_CT_CLASSPK_2 = "socialActivityStatsEntry.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_CN_CP_CT_CLASSTYPE_2 = "socialActivityStatsEntry.classType = ? AND socialActivityStatsEntry.statPeriodEnd = -1";
	private static final String _FINDER_COLUMN_G_CN_CP_CT_SN_SPE_GROUPID_2 = "socialActivityStatsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_CN_CP_CT_SN_SPE_CLASSNAMEID_2 = "socialActivityStatsEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_CN_CP_CT_SN_SPE_CLASSPK_2 = "socialActivityStatsEntry.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_CN_CP_CT_SN_SPE_CLASSTYPE_2 = "socialActivityStatsEntry.classType = ? AND ";
	private static final String _FINDER_COLUMN_G_CN_CP_CT_SN_SPE_STATNAME_1 = "socialActivityStatsEntry.statName IS NULL AND ";
	private static final String _FINDER_COLUMN_G_CN_CP_CT_SN_SPE_STATNAME_2 = "socialActivityStatsEntry.statName = ? AND ";
	private static final String _FINDER_COLUMN_G_CN_CP_CT_SN_SPE_STATNAME_3 = "(socialActivityStatsEntry.statName IS NULL OR socialActivityStatsEntry.statName = ?) AND ";
	private static final String _FINDER_COLUMN_G_CN_CP_CT_SN_SPE_STATPERIODEND_2 =
		"socialActivityStatsEntry.statPeriodEnd = ?";
	private static final String _FINDER_COLUMN_G_CN_CP_CT_SN_SPS_GROUPID_2 = "socialActivityStatsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_CN_CP_CT_SN_SPS_CLASSNAMEID_2 = "socialActivityStatsEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_CN_CP_CT_SN_SPS_CLASSPK_2 = "socialActivityStatsEntry.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_CN_CP_CT_SN_SPS_CLASSTYPE_2 = "socialActivityStatsEntry.classType = ? AND ";
	private static final String _FINDER_COLUMN_G_CN_CP_CT_SN_SPS_STATNAME_1 = "socialActivityStatsEntry.statName IS NULL AND ";
	private static final String _FINDER_COLUMN_G_CN_CP_CT_SN_SPS_STATNAME_2 = "socialActivityStatsEntry.statName = ? AND ";
	private static final String _FINDER_COLUMN_G_CN_CP_CT_SN_SPS_STATNAME_3 = "(socialActivityStatsEntry.statName IS NULL OR socialActivityStatsEntry.statName = ?) AND ";
	private static final String _FINDER_COLUMN_G_CN_CP_CT_SN_SPS_STATPERIODSTART_2 =
		"socialActivityStatsEntry.statPeriodStart = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialActivityStatsEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialActivityStatsEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SocialActivityStatsEntry exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(SocialActivityStatsEntryPersistenceImpl.class);
	private static SocialActivityStatsEntry _nullSocialActivityStatsEntry = new SocialActivityStatsEntryImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SocialActivityStatsEntry> toCacheModel() {
				return _nullSocialActivityStatsEntryCacheModel;
			}
		};

	private static CacheModel<SocialActivityStatsEntry> _nullSocialActivityStatsEntryCacheModel =
		new CacheModel<SocialActivityStatsEntry>() {
			public SocialActivityStatsEntry toEntityModel() {
				return _nullSocialActivityStatsEntry;
			}
		};
}