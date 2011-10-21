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
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.social.NoSuchEquityLogException;
import com.liferay.portlet.social.model.SocialEquityLog;
import com.liferay.portlet.social.model.impl.SocialEquityLogImpl;
import com.liferay.portlet.social.model.impl.SocialEquityLogModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the social equity log service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityLogPersistence
 * @see SocialEquityLogUtil
 * @generated
 */
public class SocialEquityLogPersistenceImpl extends BasePersistenceImpl<SocialEquityLog>
	implements SocialEquityLogPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SocialEquityLogUtil} to access the social equity log persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SocialEquityLogImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityLogImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityLogImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			SocialEquityLogModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_AEI_T_A = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityLogImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByAEI_T_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AEI_T_A =
		new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityLogImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAEI_T_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName()
			},
			SocialEquityLogModelImpl.ASSETENTRYID_COLUMN_BITMASK |
			SocialEquityLogModelImpl.TYPE_COLUMN_BITMASK |
			SocialEquityLogModelImpl.ACTIVE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_AEI_T_A = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAEI_T_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_AEI_AID_A_E =
		new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityLogImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByAEI_AID_A_E",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AEI_AID_A_E =
		new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityLogImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAEI_AID_A_E",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(), String.class.getName()
			},
			SocialEquityLogModelImpl.ASSETENTRYID_COLUMN_BITMASK |
			SocialEquityLogModelImpl.ACTIONID_COLUMN_BITMASK |
			SocialEquityLogModelImpl.ACTIVE_COLUMN_BITMASK |
			SocialEquityLogModelImpl.EXTRADATA_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_AEI_AID_A_E = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAEI_AID_A_E",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(), String.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_AEI_AID_A_E =
		new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityLogImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByU_AEI_AID_A_E",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Boolean.class.getName(),
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_AEI_AID_A_E =
		new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityLogImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_AEI_AID_A_E",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			SocialEquityLogModelImpl.USERID_COLUMN_BITMASK |
			SocialEquityLogModelImpl.ASSETENTRYID_COLUMN_BITMASK |
			SocialEquityLogModelImpl.ACTIONID_COLUMN_BITMASK |
			SocialEquityLogModelImpl.ACTIVE_COLUMN_BITMASK |
			SocialEquityLogModelImpl.EXTRADATA_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_AEI_AID_A_E = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_AEI_AID_A_E",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_AID_AD_A_T_E =
		new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityLogImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByU_AID_AD_A_T_E",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_AID_AD_A_T_E =
		new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityLogImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_AID_AD_A_T_E",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), String.class.getName()
			},
			SocialEquityLogModelImpl.USERID_COLUMN_BITMASK |
			SocialEquityLogModelImpl.ACTIONID_COLUMN_BITMASK |
			SocialEquityLogModelImpl.ACTIONDATE_COLUMN_BITMASK |
			SocialEquityLogModelImpl.ACTIVE_COLUMN_BITMASK |
			SocialEquityLogModelImpl.TYPE_COLUMN_BITMASK |
			SocialEquityLogModelImpl.EXTRADATA_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_AID_AD_A_T_E = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_AID_AD_A_T_E",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), String.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_AEI_AID_AD_A_T_E =
		new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityLogImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByAEI_AID_AD_A_T_E",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AEI_AID_AD_A_T_E =
		new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityLogImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByAEI_AID_AD_A_T_E",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), String.class.getName()
			},
			SocialEquityLogModelImpl.ASSETENTRYID_COLUMN_BITMASK |
			SocialEquityLogModelImpl.ACTIONID_COLUMN_BITMASK |
			SocialEquityLogModelImpl.ACTIONDATE_COLUMN_BITMASK |
			SocialEquityLogModelImpl.ACTIVE_COLUMN_BITMASK |
			SocialEquityLogModelImpl.TYPE_COLUMN_BITMASK |
			SocialEquityLogModelImpl.EXTRADATA_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_AEI_AID_AD_A_T_E = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAEI_AID_AD_A_T_E",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_U_AEI_AID_AD_A_T_E = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityLogImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByU_AEI_AID_AD_A_T_E",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				String.class.getName()
			},
			SocialEquityLogModelImpl.USERID_COLUMN_BITMASK |
			SocialEquityLogModelImpl.ASSETENTRYID_COLUMN_BITMASK |
			SocialEquityLogModelImpl.ACTIONID_COLUMN_BITMASK |
			SocialEquityLogModelImpl.ACTIONDATE_COLUMN_BITMASK |
			SocialEquityLogModelImpl.ACTIVE_COLUMN_BITMASK |
			SocialEquityLogModelImpl.TYPE_COLUMN_BITMASK |
			SocialEquityLogModelImpl.EXTRADATA_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_AEI_AID_AD_A_T_E = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByU_AEI_AID_AD_A_T_E",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityLogImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityLogImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the social equity log in the entity cache if it is enabled.
	 *
	 * @param socialEquityLog the social equity log
	 */
	public void cacheResult(SocialEquityLog socialEquityLog) {
		EntityCacheUtil.putResult(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogImpl.class, socialEquityLog.getPrimaryKey(),
			socialEquityLog);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_AEI_AID_AD_A_T_E,
			new Object[] {
				Long.valueOf(socialEquityLog.getUserId()),
				Long.valueOf(socialEquityLog.getAssetEntryId()),
				
			socialEquityLog.getActionId(),
				Integer.valueOf(socialEquityLog.getActionDate()),
				Boolean.valueOf(socialEquityLog.getActive()),
				Integer.valueOf(socialEquityLog.getType()),
				
			socialEquityLog.getExtraData()
			}, socialEquityLog);

		socialEquityLog.resetOriginalValues();
	}

	/**
	 * Caches the social equity logs in the entity cache if it is enabled.
	 *
	 * @param socialEquityLogs the social equity logs
	 */
	public void cacheResult(List<SocialEquityLog> socialEquityLogs) {
		for (SocialEquityLog socialEquityLog : socialEquityLogs) {
			if (EntityCacheUtil.getResult(
						SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
						SocialEquityLogImpl.class,
						socialEquityLog.getPrimaryKey()) == null) {
				cacheResult(socialEquityLog);
			}
			else {
				socialEquityLog.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all social equity logs.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SocialEquityLogImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SocialEquityLogImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the social equity log.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SocialEquityLog socialEquityLog) {
		EntityCacheUtil.removeResult(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogImpl.class, socialEquityLog.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_AEI_AID_AD_A_T_E,
			new Object[] {
				Long.valueOf(socialEquityLog.getUserId()),
				Long.valueOf(socialEquityLog.getAssetEntryId()),
				
			socialEquityLog.getActionId(),
				Integer.valueOf(socialEquityLog.getActionDate()),
				Boolean.valueOf(socialEquityLog.getActive()),
				Integer.valueOf(socialEquityLog.getType()),
				
			socialEquityLog.getExtraData()
			});
	}

	/**
	 * Creates a new social equity log with the primary key. Does not add the social equity log to the database.
	 *
	 * @param equityLogId the primary key for the new social equity log
	 * @return the new social equity log
	 */
	public SocialEquityLog create(long equityLogId) {
		SocialEquityLog socialEquityLog = new SocialEquityLogImpl();

		socialEquityLog.setNew(true);
		socialEquityLog.setPrimaryKey(equityLogId);

		return socialEquityLog;
	}

	/**
	 * Removes the social equity log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the social equity log
	 * @return the social equity log that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a social equity log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialEquityLog remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the social equity log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param equityLogId the primary key of the social equity log
	 * @return the social equity log that was removed
	 * @throws com.liferay.portlet.social.NoSuchEquityLogException if a social equity log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog remove(long equityLogId)
		throws NoSuchEquityLogException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialEquityLog socialEquityLog = (SocialEquityLog)session.get(SocialEquityLogImpl.class,
					Long.valueOf(equityLogId));

			if (socialEquityLog == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + equityLogId);
				}

				throw new NoSuchEquityLogException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					equityLogId);
			}

			return socialEquityLogPersistence.remove(socialEquityLog);
		}
		catch (NoSuchEquityLogException nsee) {
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
	 * Removes the social equity log from the database. Also notifies the appropriate model listeners.
	 *
	 * @param socialEquityLog the social equity log
	 * @return the social equity log that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialEquityLog remove(SocialEquityLog socialEquityLog)
		throws SystemException {
		return super.remove(socialEquityLog);
	}

	@Override
	protected SocialEquityLog removeImpl(SocialEquityLog socialEquityLog)
		throws SystemException {
		socialEquityLog = toUnwrappedModel(socialEquityLog);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, socialEquityLog);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		SocialEquityLogModelImpl socialEquityLogModelImpl = (SocialEquityLogModelImpl)socialEquityLog;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_AEI_AID_AD_A_T_E,
			new Object[] {
				Long.valueOf(socialEquityLogModelImpl.getUserId()),
				Long.valueOf(socialEquityLogModelImpl.getAssetEntryId()),
				
			socialEquityLogModelImpl.getActionId(),
				Integer.valueOf(socialEquityLogModelImpl.getActionDate()),
				Boolean.valueOf(socialEquityLogModelImpl.getActive()),
				Integer.valueOf(socialEquityLogModelImpl.getType()),
				
			socialEquityLogModelImpl.getExtraData()
			});

		EntityCacheUtil.removeResult(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogImpl.class, socialEquityLog.getPrimaryKey());

		return socialEquityLog;
	}

	@Override
	public SocialEquityLog updateImpl(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog,
		boolean merge) throws SystemException {
		socialEquityLog = toUnwrappedModel(socialEquityLog);

		boolean isNew = socialEquityLog.isNew();

		SocialEquityLogModelImpl socialEquityLogModelImpl = (SocialEquityLogModelImpl)socialEquityLog;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, socialEquityLog, merge);

			socialEquityLog.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SocialEquityLogModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((socialEquityLogModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialEquityLogModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] {
						Long.valueOf(socialEquityLogModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((socialEquityLogModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AEI_T_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialEquityLogModelImpl.getOriginalAssetEntryId()),
						Integer.valueOf(socialEquityLogModelImpl.getOriginalType()),
						Boolean.valueOf(socialEquityLogModelImpl.getOriginalActive())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_AEI_T_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AEI_T_A,
					args);

				args = new Object[] {
						Long.valueOf(socialEquityLogModelImpl.getAssetEntryId()),
						Integer.valueOf(socialEquityLogModelImpl.getType()),
						Boolean.valueOf(socialEquityLogModelImpl.getActive())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_AEI_T_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AEI_T_A,
					args);
			}

			if ((socialEquityLogModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AEI_AID_A_E.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialEquityLogModelImpl.getOriginalAssetEntryId()),
						
						socialEquityLogModelImpl.getOriginalActionId(),
						Boolean.valueOf(socialEquityLogModelImpl.getOriginalActive()),
						
						socialEquityLogModelImpl.getOriginalExtraData()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_AEI_AID_A_E,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AEI_AID_A_E,
					args);

				args = new Object[] {
						Long.valueOf(socialEquityLogModelImpl.getAssetEntryId()),
						
						socialEquityLogModelImpl.getActionId(),
						Boolean.valueOf(socialEquityLogModelImpl.getActive()),
						
						socialEquityLogModelImpl.getExtraData()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_AEI_AID_A_E,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AEI_AID_A_E,
					args);
			}

			if ((socialEquityLogModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_AEI_AID_A_E.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialEquityLogModelImpl.getOriginalUserId()),
						Long.valueOf(socialEquityLogModelImpl.getOriginalAssetEntryId()),
						
						socialEquityLogModelImpl.getOriginalActionId(),
						Boolean.valueOf(socialEquityLogModelImpl.getOriginalActive()),
						
						socialEquityLogModelImpl.getOriginalExtraData()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_AEI_AID_A_E,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_AEI_AID_A_E,
					args);

				args = new Object[] {
						Long.valueOf(socialEquityLogModelImpl.getUserId()),
						Long.valueOf(socialEquityLogModelImpl.getAssetEntryId()),
						
						socialEquityLogModelImpl.getActionId(),
						Boolean.valueOf(socialEquityLogModelImpl.getActive()),
						
						socialEquityLogModelImpl.getExtraData()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_AEI_AID_A_E,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_AEI_AID_A_E,
					args);
			}

			if ((socialEquityLogModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_AID_AD_A_T_E.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialEquityLogModelImpl.getOriginalUserId()),
						
						socialEquityLogModelImpl.getOriginalActionId(),
						Integer.valueOf(socialEquityLogModelImpl.getOriginalActionDate()),
						Boolean.valueOf(socialEquityLogModelImpl.getOriginalActive()),
						Integer.valueOf(socialEquityLogModelImpl.getOriginalType()),
						
						socialEquityLogModelImpl.getOriginalExtraData()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_AID_AD_A_T_E,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_AID_AD_A_T_E,
					args);

				args = new Object[] {
						Long.valueOf(socialEquityLogModelImpl.getUserId()),
						
						socialEquityLogModelImpl.getActionId(),
						Integer.valueOf(socialEquityLogModelImpl.getActionDate()),
						Boolean.valueOf(socialEquityLogModelImpl.getActive()),
						Integer.valueOf(socialEquityLogModelImpl.getType()),
						
						socialEquityLogModelImpl.getExtraData()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_AID_AD_A_T_E,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_AID_AD_A_T_E,
					args);
			}

			if ((socialEquityLogModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AEI_AID_AD_A_T_E.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialEquityLogModelImpl.getOriginalAssetEntryId()),
						
						socialEquityLogModelImpl.getOriginalActionId(),
						Integer.valueOf(socialEquityLogModelImpl.getOriginalActionDate()),
						Boolean.valueOf(socialEquityLogModelImpl.getOriginalActive()),
						Integer.valueOf(socialEquityLogModelImpl.getOriginalType()),
						
						socialEquityLogModelImpl.getOriginalExtraData()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_AEI_AID_AD_A_T_E,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AEI_AID_AD_A_T_E,
					args);

				args = new Object[] {
						Long.valueOf(socialEquityLogModelImpl.getAssetEntryId()),
						
						socialEquityLogModelImpl.getActionId(),
						Integer.valueOf(socialEquityLogModelImpl.getActionDate()),
						Boolean.valueOf(socialEquityLogModelImpl.getActive()),
						Integer.valueOf(socialEquityLogModelImpl.getType()),
						
						socialEquityLogModelImpl.getExtraData()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_AEI_AID_AD_A_T_E,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AEI_AID_AD_A_T_E,
					args);
			}
		}

		EntityCacheUtil.putResult(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityLogImpl.class, socialEquityLog.getPrimaryKey(),
			socialEquityLog);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_AEI_AID_AD_A_T_E,
				new Object[] {
					Long.valueOf(socialEquityLog.getUserId()),
					Long.valueOf(socialEquityLog.getAssetEntryId()),
					
				socialEquityLog.getActionId(),
					Integer.valueOf(socialEquityLog.getActionDate()),
					Boolean.valueOf(socialEquityLog.getActive()),
					Integer.valueOf(socialEquityLog.getType()),
					
				socialEquityLog.getExtraData()
				}, socialEquityLog);
		}
		else {
			if ((socialEquityLogModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_U_AEI_AID_AD_A_T_E.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialEquityLogModelImpl.getOriginalUserId()),
						Long.valueOf(socialEquityLogModelImpl.getOriginalAssetEntryId()),
						
						socialEquityLogModelImpl.getOriginalActionId(),
						Integer.valueOf(socialEquityLogModelImpl.getOriginalActionDate()),
						Boolean.valueOf(socialEquityLogModelImpl.getOriginalActive()),
						Integer.valueOf(socialEquityLogModelImpl.getOriginalType()),
						
						socialEquityLogModelImpl.getOriginalExtraData()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_AEI_AID_AD_A_T_E,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_AEI_AID_AD_A_T_E,
					args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_AEI_AID_AD_A_T_E,
					new Object[] {
						Long.valueOf(socialEquityLog.getUserId()),
						Long.valueOf(socialEquityLog.getAssetEntryId()),
						
					socialEquityLog.getActionId(),
						Integer.valueOf(socialEquityLog.getActionDate()),
						Boolean.valueOf(socialEquityLog.getActive()),
						Integer.valueOf(socialEquityLog.getType()),
						
					socialEquityLog.getExtraData()
					}, socialEquityLog);
			}
		}

		return socialEquityLog;
	}

	protected SocialEquityLog toUnwrappedModel(SocialEquityLog socialEquityLog) {
		if (socialEquityLog instanceof SocialEquityLogImpl) {
			return socialEquityLog;
		}

		SocialEquityLogImpl socialEquityLogImpl = new SocialEquityLogImpl();

		socialEquityLogImpl.setNew(socialEquityLog.isNew());
		socialEquityLogImpl.setPrimaryKey(socialEquityLog.getPrimaryKey());

		socialEquityLogImpl.setEquityLogId(socialEquityLog.getEquityLogId());
		socialEquityLogImpl.setGroupId(socialEquityLog.getGroupId());
		socialEquityLogImpl.setCompanyId(socialEquityLog.getCompanyId());
		socialEquityLogImpl.setUserId(socialEquityLog.getUserId());
		socialEquityLogImpl.setAssetEntryId(socialEquityLog.getAssetEntryId());
		socialEquityLogImpl.setActionId(socialEquityLog.getActionId());
		socialEquityLogImpl.setActionDate(socialEquityLog.getActionDate());
		socialEquityLogImpl.setActive(socialEquityLog.isActive());
		socialEquityLogImpl.setExpiration(socialEquityLog.getExpiration());
		socialEquityLogImpl.setType(socialEquityLog.getType());
		socialEquityLogImpl.setValue(socialEquityLog.getValue());
		socialEquityLogImpl.setExtraData(socialEquityLog.getExtraData());

		return socialEquityLogImpl;
	}

	/**
	 * Returns the social equity log with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the social equity log
	 * @return the social equity log
	 * @throws com.liferay.portal.NoSuchModelException if a social equity log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialEquityLog findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social equity log with the primary key or throws a {@link com.liferay.portlet.social.NoSuchEquityLogException} if it could not be found.
	 *
	 * @param equityLogId the primary key of the social equity log
	 * @return the social equity log
	 * @throws com.liferay.portlet.social.NoSuchEquityLogException if a social equity log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog findByPrimaryKey(long equityLogId)
		throws NoSuchEquityLogException, SystemException {
		SocialEquityLog socialEquityLog = fetchByPrimaryKey(equityLogId);

		if (socialEquityLog == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + equityLogId);
			}

			throw new NoSuchEquityLogException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				equityLogId);
		}

		return socialEquityLog;
	}

	/**
	 * Returns the social equity log with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social equity log
	 * @return the social equity log, or <code>null</code> if a social equity log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialEquityLog fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social equity log with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param equityLogId the primary key of the social equity log
	 * @return the social equity log, or <code>null</code> if a social equity log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog fetchByPrimaryKey(long equityLogId)
		throws SystemException {
		SocialEquityLog socialEquityLog = (SocialEquityLog)EntityCacheUtil.getResult(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
				SocialEquityLogImpl.class, equityLogId);

		if (socialEquityLog == _nullSocialEquityLog) {
			return null;
		}

		if (socialEquityLog == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				socialEquityLog = (SocialEquityLog)session.get(SocialEquityLogImpl.class,
						Long.valueOf(equityLogId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (socialEquityLog != null) {
					cacheResult(socialEquityLog);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(SocialEquityLogModelImpl.ENTITY_CACHE_ENABLED,
						SocialEquityLogImpl.class, equityLogId,
						_nullSocialEquityLog);
				}

				closeSession(session);
			}
		}

		return socialEquityLog;
	}

	/**
	 * Returns all the social equity logs where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityLog> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social equity logs where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of social equity logs
	 * @param end the upper bound of the range of social equity logs (not inclusive)
	 * @return the range of matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityLog> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social equity logs where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of social equity logs
	 * @param end the upper bound of the range of social equity logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityLog> findByUserId(long userId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId, start, end, orderByComparator };
		}

		List<SocialEquityLog> list = (List<SocialEquityLog>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

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

				qPos.add(userId);

				list = (List<SocialEquityLog>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social equity log in the ordered set where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social equity log
	 * @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		List<SocialEquityLog> list = findByUserId(userId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last social equity log in the ordered set where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social equity log
	 * @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		int count = countByUserId(userId);

		List<SocialEquityLog> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the social equity logs before and after the current social equity log in the ordered set where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param equityLogId the primary key of the current social equity log
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social equity log
	 * @throws com.liferay.portlet.social.NoSuchEquityLogException if a social equity log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog[] findByUserId_PrevAndNext(long equityLogId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		SocialEquityLog socialEquityLog = findByPrimaryKey(equityLogId);

		Session session = null;

		try {
			session = openSession();

			SocialEquityLog[] array = new SocialEquityLogImpl[3];

			array[0] = getByUserId_PrevAndNext(session, socialEquityLog,
					userId, orderByComparator, true);

			array[1] = socialEquityLog;

			array[2] = getByUserId_PrevAndNext(session, socialEquityLog,
					userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialEquityLog getByUserId_PrevAndNext(Session session,
		SocialEquityLog socialEquityLog, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialEquityLog);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialEquityLog> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social equity logs where assetEntryId = &#63; and type = &#63; and active = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param active the active
	 * @return the matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityLog> findByAEI_T_A(long assetEntryId, int type,
		boolean active) throws SystemException {
		return findByAEI_T_A(assetEntryId, type, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social equity logs where assetEntryId = &#63; and type = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param active the active
	 * @param start the lower bound of the range of social equity logs
	 * @param end the upper bound of the range of social equity logs (not inclusive)
	 * @return the range of matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityLog> findByAEI_T_A(long assetEntryId, int type,
		boolean active, int start, int end) throws SystemException {
		return findByAEI_T_A(assetEntryId, type, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social equity logs where assetEntryId = &#63; and type = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param active the active
	 * @param start the lower bound of the range of social equity logs
	 * @param end the upper bound of the range of social equity logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityLog> findByAEI_T_A(long assetEntryId, int type,
		boolean active, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AEI_T_A;
			finderArgs = new Object[] { assetEntryId, type, active };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_AEI_T_A;
			finderArgs = new Object[] {
					assetEntryId, type, active,
					
					start, end, orderByComparator
				};
		}

		List<SocialEquityLog> list = (List<SocialEquityLog>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

			query.append(_FINDER_COLUMN_AEI_T_A_ASSETENTRYID_2);

			query.append(_FINDER_COLUMN_AEI_T_A_TYPE_2);

			query.append(_FINDER_COLUMN_AEI_T_A_ACTIVE_2);

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

				qPos.add(assetEntryId);

				qPos.add(type);

				qPos.add(active);

				list = (List<SocialEquityLog>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social equity log in the ordered set where assetEntryId = &#63; and type = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social equity log
	 * @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog findByAEI_T_A_First(long assetEntryId, int type,
		boolean active, OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		List<SocialEquityLog> list = findByAEI_T_A(assetEntryId, type, active,
				0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetEntryId=");
			msg.append(assetEntryId);

			msg.append(", type=");
			msg.append(type);

			msg.append(", active=");
			msg.append(active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last social equity log in the ordered set where assetEntryId = &#63; and type = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social equity log
	 * @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog findByAEI_T_A_Last(long assetEntryId, int type,
		boolean active, OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		int count = countByAEI_T_A(assetEntryId, type, active);

		List<SocialEquityLog> list = findByAEI_T_A(assetEntryId, type, active,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetEntryId=");
			msg.append(assetEntryId);

			msg.append(", type=");
			msg.append(type);

			msg.append(", active=");
			msg.append(active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the social equity logs before and after the current social equity log in the ordered set where assetEntryId = &#63; and type = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param equityLogId the primary key of the current social equity log
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social equity log
	 * @throws com.liferay.portlet.social.NoSuchEquityLogException if a social equity log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog[] findByAEI_T_A_PrevAndNext(long equityLogId,
		long assetEntryId, int type, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		SocialEquityLog socialEquityLog = findByPrimaryKey(equityLogId);

		Session session = null;

		try {
			session = openSession();

			SocialEquityLog[] array = new SocialEquityLogImpl[3];

			array[0] = getByAEI_T_A_PrevAndNext(session, socialEquityLog,
					assetEntryId, type, active, orderByComparator, true);

			array[1] = socialEquityLog;

			array[2] = getByAEI_T_A_PrevAndNext(session, socialEquityLog,
					assetEntryId, type, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialEquityLog getByAEI_T_A_PrevAndNext(Session session,
		SocialEquityLog socialEquityLog, long assetEntryId, int type,
		boolean active, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

		query.append(_FINDER_COLUMN_AEI_T_A_ASSETENTRYID_2);

		query.append(_FINDER_COLUMN_AEI_T_A_TYPE_2);

		query.append(_FINDER_COLUMN_AEI_T_A_ACTIVE_2);

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

		qPos.add(assetEntryId);

		qPos.add(type);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialEquityLog);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialEquityLog> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social equity logs where assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param active the active
	 * @param extraData the extra data
	 * @return the matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityLog> findByAEI_AID_A_E(long assetEntryId,
		String actionId, boolean active, String extraData)
		throws SystemException {
		return findByAEI_AID_A_E(assetEntryId, actionId, active, extraData,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social equity logs where assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param active the active
	 * @param extraData the extra data
	 * @param start the lower bound of the range of social equity logs
	 * @param end the upper bound of the range of social equity logs (not inclusive)
	 * @return the range of matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityLog> findByAEI_AID_A_E(long assetEntryId,
		String actionId, boolean active, String extraData, int start, int end)
		throws SystemException {
		return findByAEI_AID_A_E(assetEntryId, actionId, active, extraData,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the social equity logs where assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param active the active
	 * @param extraData the extra data
	 * @param start the lower bound of the range of social equity logs
	 * @param end the upper bound of the range of social equity logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityLog> findByAEI_AID_A_E(long assetEntryId,
		String actionId, boolean active, String extraData, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AEI_AID_A_E;
			finderArgs = new Object[] { assetEntryId, actionId, active, extraData };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_AEI_AID_A_E;
			finderArgs = new Object[] {
					assetEntryId, actionId, active, extraData,
					
					start, end, orderByComparator
				};
		}

		List<SocialEquityLog> list = (List<SocialEquityLog>)FinderCacheUtil.getResult(finderPath,
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

			query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

			query.append(_FINDER_COLUMN_AEI_AID_A_E_ASSETENTRYID_2);

			if (actionId == null) {
				query.append(_FINDER_COLUMN_AEI_AID_A_E_ACTIONID_1);
			}
			else {
				if (actionId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_AEI_AID_A_E_ACTIONID_3);
				}
				else {
					query.append(_FINDER_COLUMN_AEI_AID_A_E_ACTIONID_2);
				}
			}

			query.append(_FINDER_COLUMN_AEI_AID_A_E_ACTIVE_2);

			if (extraData == null) {
				query.append(_FINDER_COLUMN_AEI_AID_A_E_EXTRADATA_1);
			}
			else {
				if (extraData.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_AEI_AID_A_E_EXTRADATA_3);
				}
				else {
					query.append(_FINDER_COLUMN_AEI_AID_A_E_EXTRADATA_2);
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

				qPos.add(assetEntryId);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(active);

				if (extraData != null) {
					qPos.add(extraData);
				}

				list = (List<SocialEquityLog>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social equity log in the ordered set where assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param active the active
	 * @param extraData the extra data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social equity log
	 * @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog findByAEI_AID_A_E_First(long assetEntryId,
		String actionId, boolean active, String extraData,
		OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		List<SocialEquityLog> list = findByAEI_AID_A_E(assetEntryId, actionId,
				active, extraData, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetEntryId=");
			msg.append(assetEntryId);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(", active=");
			msg.append(active);

			msg.append(", extraData=");
			msg.append(extraData);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last social equity log in the ordered set where assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param active the active
	 * @param extraData the extra data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social equity log
	 * @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog findByAEI_AID_A_E_Last(long assetEntryId,
		String actionId, boolean active, String extraData,
		OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		int count = countByAEI_AID_A_E(assetEntryId, actionId, active, extraData);

		List<SocialEquityLog> list = findByAEI_AID_A_E(assetEntryId, actionId,
				active, extraData, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetEntryId=");
			msg.append(assetEntryId);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(", active=");
			msg.append(active);

			msg.append(", extraData=");
			msg.append(extraData);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the social equity logs before and after the current social equity log in the ordered set where assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param equityLogId the primary key of the current social equity log
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param active the active
	 * @param extraData the extra data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social equity log
	 * @throws com.liferay.portlet.social.NoSuchEquityLogException if a social equity log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog[] findByAEI_AID_A_E_PrevAndNext(long equityLogId,
		long assetEntryId, String actionId, boolean active, String extraData,
		OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		SocialEquityLog socialEquityLog = findByPrimaryKey(equityLogId);

		Session session = null;

		try {
			session = openSession();

			SocialEquityLog[] array = new SocialEquityLogImpl[3];

			array[0] = getByAEI_AID_A_E_PrevAndNext(session, socialEquityLog,
					assetEntryId, actionId, active, extraData,
					orderByComparator, true);

			array[1] = socialEquityLog;

			array[2] = getByAEI_AID_A_E_PrevAndNext(session, socialEquityLog,
					assetEntryId, actionId, active, extraData,
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

	protected SocialEquityLog getByAEI_AID_A_E_PrevAndNext(Session session,
		SocialEquityLog socialEquityLog, long assetEntryId, String actionId,
		boolean active, String extraData, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

		query.append(_FINDER_COLUMN_AEI_AID_A_E_ASSETENTRYID_2);

		if (actionId == null) {
			query.append(_FINDER_COLUMN_AEI_AID_A_E_ACTIONID_1);
		}
		else {
			if (actionId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_AEI_AID_A_E_ACTIONID_3);
			}
			else {
				query.append(_FINDER_COLUMN_AEI_AID_A_E_ACTIONID_2);
			}
		}

		query.append(_FINDER_COLUMN_AEI_AID_A_E_ACTIVE_2);

		if (extraData == null) {
			query.append(_FINDER_COLUMN_AEI_AID_A_E_EXTRADATA_1);
		}
		else {
			if (extraData.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_AEI_AID_A_E_EXTRADATA_3);
			}
			else {
				query.append(_FINDER_COLUMN_AEI_AID_A_E_EXTRADATA_2);
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

		qPos.add(assetEntryId);

		if (actionId != null) {
			qPos.add(actionId);
		}

		qPos.add(active);

		if (extraData != null) {
			qPos.add(extraData);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialEquityLog);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialEquityLog> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social equity logs where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	 *
	 * @param userId the user ID
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param active the active
	 * @param extraData the extra data
	 * @return the matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityLog> findByU_AEI_AID_A_E(long userId,
		long assetEntryId, String actionId, boolean active, String extraData)
		throws SystemException {
		return findByU_AEI_AID_A_E(userId, assetEntryId, actionId, active,
			extraData, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social equity logs where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param active the active
	 * @param extraData the extra data
	 * @param start the lower bound of the range of social equity logs
	 * @param end the upper bound of the range of social equity logs (not inclusive)
	 * @return the range of matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityLog> findByU_AEI_AID_A_E(long userId,
		long assetEntryId, String actionId, boolean active, String extraData,
		int start, int end) throws SystemException {
		return findByU_AEI_AID_A_E(userId, assetEntryId, actionId, active,
			extraData, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social equity logs where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param active the active
	 * @param extraData the extra data
	 * @param start the lower bound of the range of social equity logs
	 * @param end the upper bound of the range of social equity logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityLog> findByU_AEI_AID_A_E(long userId,
		long assetEntryId, String actionId, boolean active, String extraData,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_AEI_AID_A_E;
			finderArgs = new Object[] {
					userId, assetEntryId, actionId, active, extraData
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_AEI_AID_A_E;
			finderArgs = new Object[] {
					userId, assetEntryId, actionId, active, extraData,
					
					start, end, orderByComparator
				};
		}

		List<SocialEquityLog> list = (List<SocialEquityLog>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(7 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

			query.append(_FINDER_COLUMN_U_AEI_AID_A_E_USERID_2);

			query.append(_FINDER_COLUMN_U_AEI_AID_A_E_ASSETENTRYID_2);

			if (actionId == null) {
				query.append(_FINDER_COLUMN_U_AEI_AID_A_E_ACTIONID_1);
			}
			else {
				if (actionId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_U_AEI_AID_A_E_ACTIONID_3);
				}
				else {
					query.append(_FINDER_COLUMN_U_AEI_AID_A_E_ACTIONID_2);
				}
			}

			query.append(_FINDER_COLUMN_U_AEI_AID_A_E_ACTIVE_2);

			if (extraData == null) {
				query.append(_FINDER_COLUMN_U_AEI_AID_A_E_EXTRADATA_1);
			}
			else {
				if (extraData.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_U_AEI_AID_A_E_EXTRADATA_3);
				}
				else {
					query.append(_FINDER_COLUMN_U_AEI_AID_A_E_EXTRADATA_2);
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

				qPos.add(userId);

				qPos.add(assetEntryId);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(active);

				if (extraData != null) {
					qPos.add(extraData);
				}

				list = (List<SocialEquityLog>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social equity log in the ordered set where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param active the active
	 * @param extraData the extra data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social equity log
	 * @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog findByU_AEI_AID_A_E_First(long userId,
		long assetEntryId, String actionId, boolean active, String extraData,
		OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		List<SocialEquityLog> list = findByU_AEI_AID_A_E(userId, assetEntryId,
				actionId, active, extraData, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", assetEntryId=");
			msg.append(assetEntryId);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(", active=");
			msg.append(active);

			msg.append(", extraData=");
			msg.append(extraData);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last social equity log in the ordered set where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param active the active
	 * @param extraData the extra data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social equity log
	 * @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog findByU_AEI_AID_A_E_Last(long userId,
		long assetEntryId, String actionId, boolean active, String extraData,
		OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		int count = countByU_AEI_AID_A_E(userId, assetEntryId, actionId,
				active, extraData);

		List<SocialEquityLog> list = findByU_AEI_AID_A_E(userId, assetEntryId,
				actionId, active, extraData, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", assetEntryId=");
			msg.append(assetEntryId);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(", active=");
			msg.append(active);

			msg.append(", extraData=");
			msg.append(extraData);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the social equity logs before and after the current social equity log in the ordered set where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param equityLogId the primary key of the current social equity log
	 * @param userId the user ID
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param active the active
	 * @param extraData the extra data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social equity log
	 * @throws com.liferay.portlet.social.NoSuchEquityLogException if a social equity log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog[] findByU_AEI_AID_A_E_PrevAndNext(long equityLogId,
		long userId, long assetEntryId, String actionId, boolean active,
		String extraData, OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		SocialEquityLog socialEquityLog = findByPrimaryKey(equityLogId);

		Session session = null;

		try {
			session = openSession();

			SocialEquityLog[] array = new SocialEquityLogImpl[3];

			array[0] = getByU_AEI_AID_A_E_PrevAndNext(session, socialEquityLog,
					userId, assetEntryId, actionId, active, extraData,
					orderByComparator, true);

			array[1] = socialEquityLog;

			array[2] = getByU_AEI_AID_A_E_PrevAndNext(session, socialEquityLog,
					userId, assetEntryId, actionId, active, extraData,
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

	protected SocialEquityLog getByU_AEI_AID_A_E_PrevAndNext(Session session,
		SocialEquityLog socialEquityLog, long userId, long assetEntryId,
		String actionId, boolean active, String extraData,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

		query.append(_FINDER_COLUMN_U_AEI_AID_A_E_USERID_2);

		query.append(_FINDER_COLUMN_U_AEI_AID_A_E_ASSETENTRYID_2);

		if (actionId == null) {
			query.append(_FINDER_COLUMN_U_AEI_AID_A_E_ACTIONID_1);
		}
		else {
			if (actionId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_U_AEI_AID_A_E_ACTIONID_3);
			}
			else {
				query.append(_FINDER_COLUMN_U_AEI_AID_A_E_ACTIONID_2);
			}
		}

		query.append(_FINDER_COLUMN_U_AEI_AID_A_E_ACTIVE_2);

		if (extraData == null) {
			query.append(_FINDER_COLUMN_U_AEI_AID_A_E_EXTRADATA_1);
		}
		else {
			if (extraData.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_U_AEI_AID_A_E_EXTRADATA_3);
			}
			else {
				query.append(_FINDER_COLUMN_U_AEI_AID_A_E_EXTRADATA_2);
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

		qPos.add(userId);

		qPos.add(assetEntryId);

		if (actionId != null) {
			qPos.add(actionId);
		}

		qPos.add(active);

		if (extraData != null) {
			qPos.add(extraData);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialEquityLog);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialEquityLog> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social equity logs where userId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @param actionDate the action date
	 * @param active the active
	 * @param type the type
	 * @param extraData the extra data
	 * @return the matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityLog> findByU_AID_AD_A_T_E(long userId,
		String actionId, int actionDate, boolean active, int type,
		String extraData) throws SystemException {
		return findByU_AID_AD_A_T_E(userId, actionId, actionDate, active, type,
			extraData, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social equity logs where userId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @param actionDate the action date
	 * @param active the active
	 * @param type the type
	 * @param extraData the extra data
	 * @param start the lower bound of the range of social equity logs
	 * @param end the upper bound of the range of social equity logs (not inclusive)
	 * @return the range of matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityLog> findByU_AID_AD_A_T_E(long userId,
		String actionId, int actionDate, boolean active, int type,
		String extraData, int start, int end) throws SystemException {
		return findByU_AID_AD_A_T_E(userId, actionId, actionDate, active, type,
			extraData, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social equity logs where userId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @param actionDate the action date
	 * @param active the active
	 * @param type the type
	 * @param extraData the extra data
	 * @param start the lower bound of the range of social equity logs
	 * @param end the upper bound of the range of social equity logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityLog> findByU_AID_AD_A_T_E(long userId,
		String actionId, int actionDate, boolean active, int type,
		String extraData, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_AID_AD_A_T_E;
			finderArgs = new Object[] {
					userId, actionId, actionDate, active, type, extraData
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_AID_AD_A_T_E;
			finderArgs = new Object[] {
					userId, actionId, actionDate, active, type, extraData,
					
					start, end, orderByComparator
				};
		}

		List<SocialEquityLog> list = (List<SocialEquityLog>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(8 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(7);
			}

			query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

			query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_USERID_2);

			if (actionId == null) {
				query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_ACTIONID_1);
			}
			else {
				if (actionId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_ACTIONID_3);
				}
				else {
					query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_ACTIONID_2);
				}
			}

			query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_ACTIONDATE_2);

			query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_ACTIVE_2);

			query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_TYPE_2);

			if (extraData == null) {
				query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_EXTRADATA_1);
			}
			else {
				if (extraData.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_EXTRADATA_3);
				}
				else {
					query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_EXTRADATA_2);
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

				qPos.add(userId);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(actionDate);

				qPos.add(active);

				qPos.add(type);

				if (extraData != null) {
					qPos.add(extraData);
				}

				list = (List<SocialEquityLog>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social equity log in the ordered set where userId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @param actionDate the action date
	 * @param active the active
	 * @param type the type
	 * @param extraData the extra data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social equity log
	 * @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog findByU_AID_AD_A_T_E_First(long userId,
		String actionId, int actionDate, boolean active, int type,
		String extraData, OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		List<SocialEquityLog> list = findByU_AID_AD_A_T_E(userId, actionId,
				actionDate, active, type, extraData, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(14);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(", actionDate=");
			msg.append(actionDate);

			msg.append(", active=");
			msg.append(active);

			msg.append(", type=");
			msg.append(type);

			msg.append(", extraData=");
			msg.append(extraData);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last social equity log in the ordered set where userId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @param actionDate the action date
	 * @param active the active
	 * @param type the type
	 * @param extraData the extra data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social equity log
	 * @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog findByU_AID_AD_A_T_E_Last(long userId,
		String actionId, int actionDate, boolean active, int type,
		String extraData, OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		int count = countByU_AID_AD_A_T_E(userId, actionId, actionDate, active,
				type, extraData);

		List<SocialEquityLog> list = findByU_AID_AD_A_T_E(userId, actionId,
				actionDate, active, type, extraData, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(14);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(", actionDate=");
			msg.append(actionDate);

			msg.append(", active=");
			msg.append(active);

			msg.append(", type=");
			msg.append(type);

			msg.append(", extraData=");
			msg.append(extraData);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the social equity logs before and after the current social equity log in the ordered set where userId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param equityLogId the primary key of the current social equity log
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @param actionDate the action date
	 * @param active the active
	 * @param type the type
	 * @param extraData the extra data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social equity log
	 * @throws com.liferay.portlet.social.NoSuchEquityLogException if a social equity log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog[] findByU_AID_AD_A_T_E_PrevAndNext(
		long equityLogId, long userId, String actionId, int actionDate,
		boolean active, int type, String extraData,
		OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		SocialEquityLog socialEquityLog = findByPrimaryKey(equityLogId);

		Session session = null;

		try {
			session = openSession();

			SocialEquityLog[] array = new SocialEquityLogImpl[3];

			array[0] = getByU_AID_AD_A_T_E_PrevAndNext(session,
					socialEquityLog, userId, actionId, actionDate, active,
					type, extraData, orderByComparator, true);

			array[1] = socialEquityLog;

			array[2] = getByU_AID_AD_A_T_E_PrevAndNext(session,
					socialEquityLog, userId, actionId, actionDate, active,
					type, extraData, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialEquityLog getByU_AID_AD_A_T_E_PrevAndNext(Session session,
		SocialEquityLog socialEquityLog, long userId, String actionId,
		int actionDate, boolean active, int type, String extraData,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

		query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_USERID_2);

		if (actionId == null) {
			query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_ACTIONID_1);
		}
		else {
			if (actionId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_ACTIONID_3);
			}
			else {
				query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_ACTIONID_2);
			}
		}

		query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_ACTIONDATE_2);

		query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_ACTIVE_2);

		query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_TYPE_2);

		if (extraData == null) {
			query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_EXTRADATA_1);
		}
		else {
			if (extraData.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_EXTRADATA_3);
			}
			else {
				query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_EXTRADATA_2);
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

		qPos.add(userId);

		if (actionId != null) {
			qPos.add(actionId);
		}

		qPos.add(actionDate);

		qPos.add(active);

		qPos.add(type);

		if (extraData != null) {
			qPos.add(extraData);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialEquityLog);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialEquityLog> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social equity logs where assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param actionDate the action date
	 * @param active the active
	 * @param type the type
	 * @param extraData the extra data
	 * @return the matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityLog> findByAEI_AID_AD_A_T_E(long assetEntryId,
		String actionId, int actionDate, boolean active, int type,
		String extraData) throws SystemException {
		return findByAEI_AID_AD_A_T_E(assetEntryId, actionId, actionDate,
			active, type, extraData, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social equity logs where assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param actionDate the action date
	 * @param active the active
	 * @param type the type
	 * @param extraData the extra data
	 * @param start the lower bound of the range of social equity logs
	 * @param end the upper bound of the range of social equity logs (not inclusive)
	 * @return the range of matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityLog> findByAEI_AID_AD_A_T_E(long assetEntryId,
		String actionId, int actionDate, boolean active, int type,
		String extraData, int start, int end) throws SystemException {
		return findByAEI_AID_AD_A_T_E(assetEntryId, actionId, actionDate,
			active, type, extraData, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social equity logs where assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param actionDate the action date
	 * @param active the active
	 * @param type the type
	 * @param extraData the extra data
	 * @param start the lower bound of the range of social equity logs
	 * @param end the upper bound of the range of social equity logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityLog> findByAEI_AID_AD_A_T_E(long assetEntryId,
		String actionId, int actionDate, boolean active, int type,
		String extraData, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_AEI_AID_AD_A_T_E;
			finderArgs = new Object[] {
					assetEntryId, actionId, actionDate, active, type, extraData
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_AEI_AID_AD_A_T_E;
			finderArgs = new Object[] {
					assetEntryId, actionId, actionDate, active, type, extraData,
					
					start, end, orderByComparator
				};
		}

		List<SocialEquityLog> list = (List<SocialEquityLog>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(8 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(7);
			}

			query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

			query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_ASSETENTRYID_2);

			if (actionId == null) {
				query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_ACTIONID_1);
			}
			else {
				if (actionId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_ACTIONID_3);
				}
				else {
					query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_ACTIONID_2);
				}
			}

			query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_ACTIONDATE_2);

			query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_ACTIVE_2);

			query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_TYPE_2);

			if (extraData == null) {
				query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_EXTRADATA_1);
			}
			else {
				if (extraData.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_EXTRADATA_3);
				}
				else {
					query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_EXTRADATA_2);
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

				qPos.add(assetEntryId);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(actionDate);

				qPos.add(active);

				qPos.add(type);

				if (extraData != null) {
					qPos.add(extraData);
				}

				list = (List<SocialEquityLog>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first social equity log in the ordered set where assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param actionDate the action date
	 * @param active the active
	 * @param type the type
	 * @param extraData the extra data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social equity log
	 * @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog findByAEI_AID_AD_A_T_E_First(long assetEntryId,
		String actionId, int actionDate, boolean active, int type,
		String extraData, OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		List<SocialEquityLog> list = findByAEI_AID_AD_A_T_E(assetEntryId,
				actionId, actionDate, active, type, extraData, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(14);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetEntryId=");
			msg.append(assetEntryId);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(", actionDate=");
			msg.append(actionDate);

			msg.append(", active=");
			msg.append(active);

			msg.append(", type=");
			msg.append(type);

			msg.append(", extraData=");
			msg.append(extraData);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last social equity log in the ordered set where assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param actionDate the action date
	 * @param active the active
	 * @param type the type
	 * @param extraData the extra data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social equity log
	 * @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog findByAEI_AID_AD_A_T_E_Last(long assetEntryId,
		String actionId, int actionDate, boolean active, int type,
		String extraData, OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		int count = countByAEI_AID_AD_A_T_E(assetEntryId, actionId, actionDate,
				active, type, extraData);

		List<SocialEquityLog> list = findByAEI_AID_AD_A_T_E(assetEntryId,
				actionId, actionDate, active, type, extraData, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(14);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetEntryId=");
			msg.append(assetEntryId);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(", actionDate=");
			msg.append(actionDate);

			msg.append(", active=");
			msg.append(active);

			msg.append(", type=");
			msg.append(type);

			msg.append(", extraData=");
			msg.append(extraData);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityLogException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the social equity logs before and after the current social equity log in the ordered set where assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param equityLogId the primary key of the current social equity log
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param actionDate the action date
	 * @param active the active
	 * @param type the type
	 * @param extraData the extra data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social equity log
	 * @throws com.liferay.portlet.social.NoSuchEquityLogException if a social equity log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog[] findByAEI_AID_AD_A_T_E_PrevAndNext(
		long equityLogId, long assetEntryId, String actionId, int actionDate,
		boolean active, int type, String extraData,
		OrderByComparator orderByComparator)
		throws NoSuchEquityLogException, SystemException {
		SocialEquityLog socialEquityLog = findByPrimaryKey(equityLogId);

		Session session = null;

		try {
			session = openSession();

			SocialEquityLog[] array = new SocialEquityLogImpl[3];

			array[0] = getByAEI_AID_AD_A_T_E_PrevAndNext(session,
					socialEquityLog, assetEntryId, actionId, actionDate,
					active, type, extraData, orderByComparator, true);

			array[1] = socialEquityLog;

			array[2] = getByAEI_AID_AD_A_T_E_PrevAndNext(session,
					socialEquityLog, assetEntryId, actionId, actionDate,
					active, type, extraData, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialEquityLog getByAEI_AID_AD_A_T_E_PrevAndNext(
		Session session, SocialEquityLog socialEquityLog, long assetEntryId,
		String actionId, int actionDate, boolean active, int type,
		String extraData, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

		query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_ASSETENTRYID_2);

		if (actionId == null) {
			query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_ACTIONID_1);
		}
		else {
			if (actionId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_ACTIONID_3);
			}
			else {
				query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_ACTIONID_2);
			}
		}

		query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_ACTIONDATE_2);

		query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_ACTIVE_2);

		query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_TYPE_2);

		if (extraData == null) {
			query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_EXTRADATA_1);
		}
		else {
			if (extraData.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_EXTRADATA_3);
			}
			else {
				query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_EXTRADATA_2);
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

		qPos.add(assetEntryId);

		if (actionId != null) {
			qPos.add(actionId);
		}

		qPos.add(actionDate);

		qPos.add(active);

		qPos.add(type);

		if (extraData != null) {
			qPos.add(extraData);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialEquityLog);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialEquityLog> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the social equity log where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63; or throws a {@link com.liferay.portlet.social.NoSuchEquityLogException} if it could not be found.
	 *
	 * @param userId the user ID
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param actionDate the action date
	 * @param active the active
	 * @param type the type
	 * @param extraData the extra data
	 * @return the matching social equity log
	 * @throws com.liferay.portlet.social.NoSuchEquityLogException if a matching social equity log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog findByU_AEI_AID_AD_A_T_E(long userId,
		long assetEntryId, String actionId, int actionDate, boolean active,
		int type, String extraData)
		throws NoSuchEquityLogException, SystemException {
		SocialEquityLog socialEquityLog = fetchByU_AEI_AID_AD_A_T_E(userId,
				assetEntryId, actionId, actionDate, active, type, extraData);

		if (socialEquityLog == null) {
			StringBundler msg = new StringBundler(16);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", assetEntryId=");
			msg.append(assetEntryId);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(", actionDate=");
			msg.append(actionDate);

			msg.append(", active=");
			msg.append(active);

			msg.append(", type=");
			msg.append(type);

			msg.append(", extraData=");
			msg.append(extraData);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEquityLogException(msg.toString());
		}

		return socialEquityLog;
	}

	/**
	 * Returns the social equity log where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param actionDate the action date
	 * @param active the active
	 * @param type the type
	 * @param extraData the extra data
	 * @return the matching social equity log, or <code>null</code> if a matching social equity log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog fetchByU_AEI_AID_AD_A_T_E(long userId,
		long assetEntryId, String actionId, int actionDate, boolean active,
		int type, String extraData) throws SystemException {
		return fetchByU_AEI_AID_AD_A_T_E(userId, assetEntryId, actionId,
			actionDate, active, type, extraData, true);
	}

	/**
	 * Returns the social equity log where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param actionDate the action date
	 * @param active the active
	 * @param type the type
	 * @param extraData the extra data
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching social equity log, or <code>null</code> if a matching social equity log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityLog fetchByU_AEI_AID_AD_A_T_E(long userId,
		long assetEntryId, String actionId, int actionDate, boolean active,
		int type, String extraData, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				userId, assetEntryId, actionId, actionDate, active, type,
				extraData
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_AEI_AID_AD_A_T_E,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(8);

			query.append(_SQL_SELECT_SOCIALEQUITYLOG_WHERE);

			query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_USERID_2);

			query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_ASSETENTRYID_2);

			if (actionId == null) {
				query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_ACTIONID_1);
			}
			else {
				if (actionId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_ACTIONID_3);
				}
				else {
					query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_ACTIONID_2);
				}
			}

			query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_ACTIONDATE_2);

			query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_ACTIVE_2);

			query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_TYPE_2);

			if (extraData == null) {
				query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_EXTRADATA_1);
			}
			else {
				if (extraData.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_EXTRADATA_3);
				}
				else {
					query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_EXTRADATA_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(assetEntryId);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(actionDate);

				qPos.add(active);

				qPos.add(type);

				if (extraData != null) {
					qPos.add(extraData);
				}

				List<SocialEquityLog> list = q.list();

				result = list;

				SocialEquityLog socialEquityLog = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_AEI_AID_AD_A_T_E,
						finderArgs, list);
				}
				else {
					socialEquityLog = list.get(0);

					cacheResult(socialEquityLog);

					if ((socialEquityLog.getUserId() != userId) ||
							(socialEquityLog.getAssetEntryId() != assetEntryId) ||
							(socialEquityLog.getActionId() == null) ||
							!socialEquityLog.getActionId().equals(actionId) ||
							(socialEquityLog.getActionDate() != actionDate) ||
							(socialEquityLog.getActive() != active) ||
							(socialEquityLog.getType() != type) ||
							(socialEquityLog.getExtraData() == null) ||
							!socialEquityLog.getExtraData().equals(extraData)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_AEI_AID_AD_A_T_E,
							finderArgs, socialEquityLog);
					}
				}

				return socialEquityLog;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_AEI_AID_AD_A_T_E,
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
				return (SocialEquityLog)result;
			}
		}
	}

	/**
	 * Returns all the social equity logs.
	 *
	 * @return the social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityLog> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social equity logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social equity logs
	 * @param end the upper bound of the range of social equity logs (not inclusive)
	 * @return the range of social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityLog> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the social equity logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social equity logs
	 * @param end the upper bound of the range of social equity logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityLog> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<SocialEquityLog> list = (List<SocialEquityLog>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SOCIALEQUITYLOG);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SOCIALEQUITYLOG;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SocialEquityLog>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialEquityLog>)QueryUtil.list(q,
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
	 * Removes all the social equity logs where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (SocialEquityLog socialEquityLog : findByUserId(userId)) {
			socialEquityLogPersistence.remove(socialEquityLog);
		}
	}

	/**
	 * Removes all the social equity logs where assetEntryId = &#63; and type = &#63; and active = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param active the active
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByAEI_T_A(long assetEntryId, int type, boolean active)
		throws SystemException {
		for (SocialEquityLog socialEquityLog : findByAEI_T_A(assetEntryId,
				type, active)) {
			socialEquityLogPersistence.remove(socialEquityLog);
		}
	}

	/**
	 * Removes all the social equity logs where assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param active the active
	 * @param extraData the extra data
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByAEI_AID_A_E(long assetEntryId, String actionId,
		boolean active, String extraData) throws SystemException {
		for (SocialEquityLog socialEquityLog : findByAEI_AID_A_E(assetEntryId,
				actionId, active, extraData)) {
			socialEquityLogPersistence.remove(socialEquityLog);
		}
	}

	/**
	 * Removes all the social equity logs where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param active the active
	 * @param extraData the extra data
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU_AEI_AID_A_E(long userId, long assetEntryId,
		String actionId, boolean active, String extraData)
		throws SystemException {
		for (SocialEquityLog socialEquityLog : findByU_AEI_AID_A_E(userId,
				assetEntryId, actionId, active, extraData)) {
			socialEquityLogPersistence.remove(socialEquityLog);
		}
	}

	/**
	 * Removes all the social equity logs where userId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @param actionDate the action date
	 * @param active the active
	 * @param type the type
	 * @param extraData the extra data
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU_AID_AD_A_T_E(long userId, String actionId,
		int actionDate, boolean active, int type, String extraData)
		throws SystemException {
		for (SocialEquityLog socialEquityLog : findByU_AID_AD_A_T_E(userId,
				actionId, actionDate, active, type, extraData)) {
			socialEquityLogPersistence.remove(socialEquityLog);
		}
	}

	/**
	 * Removes all the social equity logs where assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param actionDate the action date
	 * @param active the active
	 * @param type the type
	 * @param extraData the extra data
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByAEI_AID_AD_A_T_E(long assetEntryId, String actionId,
		int actionDate, boolean active, int type, String extraData)
		throws SystemException {
		for (SocialEquityLog socialEquityLog : findByAEI_AID_AD_A_T_E(
				assetEntryId, actionId, actionDate, active, type, extraData)) {
			socialEquityLogPersistence.remove(socialEquityLog);
		}
	}

	/**
	 * Removes the social equity log where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param actionDate the action date
	 * @param active the active
	 * @param type the type
	 * @param extraData the extra data
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU_AEI_AID_AD_A_T_E(long userId, long assetEntryId,
		String actionId, int actionDate, boolean active, int type,
		String extraData) throws NoSuchEquityLogException, SystemException {
		SocialEquityLog socialEquityLog = findByU_AEI_AID_AD_A_T_E(userId,
				assetEntryId, actionId, actionDate, active, type, extraData);

		socialEquityLogPersistence.remove(socialEquityLog);
	}

	/**
	 * Removes all the social equity logs from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SocialEquityLog socialEquityLog : findAll()) {
			socialEquityLogPersistence.remove(socialEquityLog);
		}
	}

	/**
	 * Returns the number of social equity logs where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALEQUITYLOG_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social equity logs where assetEntryId = &#63; and type = &#63; and active = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param active the active
	 * @return the number of matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByAEI_T_A(long assetEntryId, int type, boolean active)
		throws SystemException {
		Object[] finderArgs = new Object[] { assetEntryId, type, active };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_AEI_T_A,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SOCIALEQUITYLOG_WHERE);

			query.append(_FINDER_COLUMN_AEI_T_A_ASSETENTRYID_2);

			query.append(_FINDER_COLUMN_AEI_T_A_TYPE_2);

			query.append(_FINDER_COLUMN_AEI_T_A_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				qPos.add(type);

				qPos.add(active);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_AEI_T_A,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social equity logs where assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param active the active
	 * @param extraData the extra data
	 * @return the number of matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByAEI_AID_A_E(long assetEntryId, String actionId,
		boolean active, String extraData) throws SystemException {
		Object[] finderArgs = new Object[] {
				assetEntryId, actionId, active, extraData
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_AEI_AID_A_E,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_SOCIALEQUITYLOG_WHERE);

			query.append(_FINDER_COLUMN_AEI_AID_A_E_ASSETENTRYID_2);

			if (actionId == null) {
				query.append(_FINDER_COLUMN_AEI_AID_A_E_ACTIONID_1);
			}
			else {
				if (actionId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_AEI_AID_A_E_ACTIONID_3);
				}
				else {
					query.append(_FINDER_COLUMN_AEI_AID_A_E_ACTIONID_2);
				}
			}

			query.append(_FINDER_COLUMN_AEI_AID_A_E_ACTIVE_2);

			if (extraData == null) {
				query.append(_FINDER_COLUMN_AEI_AID_A_E_EXTRADATA_1);
			}
			else {
				if (extraData.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_AEI_AID_A_E_EXTRADATA_3);
				}
				else {
					query.append(_FINDER_COLUMN_AEI_AID_A_E_EXTRADATA_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(active);

				if (extraData != null) {
					qPos.add(extraData);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_AEI_AID_A_E,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social equity logs where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and active = &#63; and extraData = &#63;.
	 *
	 * @param userId the user ID
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param active the active
	 * @param extraData the extra data
	 * @return the number of matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_AEI_AID_A_E(long userId, long assetEntryId,
		String actionId, boolean active, String extraData)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				userId, assetEntryId, actionId, active, extraData
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_AEI_AID_A_E,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_SOCIALEQUITYLOG_WHERE);

			query.append(_FINDER_COLUMN_U_AEI_AID_A_E_USERID_2);

			query.append(_FINDER_COLUMN_U_AEI_AID_A_E_ASSETENTRYID_2);

			if (actionId == null) {
				query.append(_FINDER_COLUMN_U_AEI_AID_A_E_ACTIONID_1);
			}
			else {
				if (actionId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_U_AEI_AID_A_E_ACTIONID_3);
				}
				else {
					query.append(_FINDER_COLUMN_U_AEI_AID_A_E_ACTIONID_2);
				}
			}

			query.append(_FINDER_COLUMN_U_AEI_AID_A_E_ACTIVE_2);

			if (extraData == null) {
				query.append(_FINDER_COLUMN_U_AEI_AID_A_E_EXTRADATA_1);
			}
			else {
				if (extraData.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_U_AEI_AID_A_E_EXTRADATA_3);
				}
				else {
					query.append(_FINDER_COLUMN_U_AEI_AID_A_E_EXTRADATA_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(assetEntryId);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(active);

				if (extraData != null) {
					qPos.add(extraData);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_AEI_AID_A_E,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social equity logs where userId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	 *
	 * @param userId the user ID
	 * @param actionId the action ID
	 * @param actionDate the action date
	 * @param active the active
	 * @param type the type
	 * @param extraData the extra data
	 * @return the number of matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_AID_AD_A_T_E(long userId, String actionId,
		int actionDate, boolean active, int type, String extraData)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				userId, actionId, actionDate, active, type, extraData
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_AID_AD_A_T_E,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_COUNT_SOCIALEQUITYLOG_WHERE);

			query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_USERID_2);

			if (actionId == null) {
				query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_ACTIONID_1);
			}
			else {
				if (actionId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_ACTIONID_3);
				}
				else {
					query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_ACTIONID_2);
				}
			}

			query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_ACTIONDATE_2);

			query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_ACTIVE_2);

			query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_TYPE_2);

			if (extraData == null) {
				query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_EXTRADATA_1);
			}
			else {
				if (extraData.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_EXTRADATA_3);
				}
				else {
					query.append(_FINDER_COLUMN_U_AID_AD_A_T_E_EXTRADATA_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(actionDate);

				qPos.add(active);

				qPos.add(type);

				if (extraData != null) {
					qPos.add(extraData);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_AID_AD_A_T_E,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social equity logs where assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param actionDate the action date
	 * @param active the active
	 * @param type the type
	 * @param extraData the extra data
	 * @return the number of matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByAEI_AID_AD_A_T_E(long assetEntryId, String actionId,
		int actionDate, boolean active, int type, String extraData)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				assetEntryId, actionId, actionDate, active, type, extraData
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_AEI_AID_AD_A_T_E,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_COUNT_SOCIALEQUITYLOG_WHERE);

			query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_ASSETENTRYID_2);

			if (actionId == null) {
				query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_ACTIONID_1);
			}
			else {
				if (actionId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_ACTIONID_3);
				}
				else {
					query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_ACTIONID_2);
				}
			}

			query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_ACTIONDATE_2);

			query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_ACTIVE_2);

			query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_TYPE_2);

			if (extraData == null) {
				query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_EXTRADATA_1);
			}
			else {
				if (extraData.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_EXTRADATA_3);
				}
				else {
					query.append(_FINDER_COLUMN_AEI_AID_AD_A_T_E_EXTRADATA_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(actionDate);

				qPos.add(active);

				qPos.add(type);

				if (extraData != null) {
					qPos.add(extraData);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_AEI_AID_AD_A_T_E,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social equity logs where userId = &#63; and assetEntryId = &#63; and actionId = &#63; and actionDate = &#63; and active = &#63; and type = &#63; and extraData = &#63;.
	 *
	 * @param userId the user ID
	 * @param assetEntryId the asset entry ID
	 * @param actionId the action ID
	 * @param actionDate the action date
	 * @param active the active
	 * @param type the type
	 * @param extraData the extra data
	 * @return the number of matching social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_AEI_AID_AD_A_T_E(long userId, long assetEntryId,
		String actionId, int actionDate, boolean active, int type,
		String extraData) throws SystemException {
		Object[] finderArgs = new Object[] {
				userId, assetEntryId, actionId, actionDate, active, type,
				extraData
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_AEI_AID_AD_A_T_E,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(8);

			query.append(_SQL_COUNT_SOCIALEQUITYLOG_WHERE);

			query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_USERID_2);

			query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_ASSETENTRYID_2);

			if (actionId == null) {
				query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_ACTIONID_1);
			}
			else {
				if (actionId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_ACTIONID_3);
				}
				else {
					query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_ACTIONID_2);
				}
			}

			query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_ACTIONDATE_2);

			query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_ACTIVE_2);

			query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_TYPE_2);

			if (extraData == null) {
				query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_EXTRADATA_1);
			}
			else {
				if (extraData.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_EXTRADATA_3);
				}
				else {
					query.append(_FINDER_COLUMN_U_AEI_AID_AD_A_T_E_EXTRADATA_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(assetEntryId);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(actionDate);

				qPos.add(active);

				qPos.add(type);

				if (extraData != null) {
					qPos.add(extraData);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_AEI_AID_AD_A_T_E,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social equity logs.
	 *
	 * @return the number of social equity logs
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SOCIALEQUITYLOG);

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
	 * Initializes the social equity log persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.social.model.SocialEquityLog")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialEquityLog>> listenersList = new ArrayList<ModelListener<SocialEquityLog>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialEquityLog>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(SocialEquityLogImpl.class.getName());
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
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	private static final String _SQL_SELECT_SOCIALEQUITYLOG = "SELECT socialEquityLog FROM SocialEquityLog socialEquityLog";
	private static final String _SQL_SELECT_SOCIALEQUITYLOG_WHERE = "SELECT socialEquityLog FROM SocialEquityLog socialEquityLog WHERE ";
	private static final String _SQL_COUNT_SOCIALEQUITYLOG = "SELECT COUNT(socialEquityLog) FROM SocialEquityLog socialEquityLog";
	private static final String _SQL_COUNT_SOCIALEQUITYLOG_WHERE = "SELECT COUNT(socialEquityLog) FROM SocialEquityLog socialEquityLog WHERE ";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "socialEquityLog.userId = ?";
	private static final String _FINDER_COLUMN_AEI_T_A_ASSETENTRYID_2 = "socialEquityLog.assetEntryId = ? AND ";
	private static final String _FINDER_COLUMN_AEI_T_A_TYPE_2 = "socialEquityLog.type = ? AND ";
	private static final String _FINDER_COLUMN_AEI_T_A_ACTIVE_2 = "socialEquityLog.active = ?";
	private static final String _FINDER_COLUMN_AEI_AID_A_E_ASSETENTRYID_2 = "socialEquityLog.assetEntryId = ? AND ";
	private static final String _FINDER_COLUMN_AEI_AID_A_E_ACTIONID_1 = "socialEquityLog.actionId IS NULL AND ";
	private static final String _FINDER_COLUMN_AEI_AID_A_E_ACTIONID_2 = "socialEquityLog.actionId = ? AND ";
	private static final String _FINDER_COLUMN_AEI_AID_A_E_ACTIONID_3 = "(socialEquityLog.actionId IS NULL OR socialEquityLog.actionId = ?) AND ";
	private static final String _FINDER_COLUMN_AEI_AID_A_E_ACTIVE_2 = "socialEquityLog.active = ? AND ";
	private static final String _FINDER_COLUMN_AEI_AID_A_E_EXTRADATA_1 = "socialEquityLog.extraData IS NULL";
	private static final String _FINDER_COLUMN_AEI_AID_A_E_EXTRADATA_2 = "socialEquityLog.extraData = ?";
	private static final String _FINDER_COLUMN_AEI_AID_A_E_EXTRADATA_3 = "(socialEquityLog.extraData IS NULL OR socialEquityLog.extraData = ?)";
	private static final String _FINDER_COLUMN_U_AEI_AID_A_E_USERID_2 = "socialEquityLog.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_AEI_AID_A_E_ASSETENTRYID_2 = "socialEquityLog.assetEntryId = ? AND ";
	private static final String _FINDER_COLUMN_U_AEI_AID_A_E_ACTIONID_1 = "socialEquityLog.actionId IS NULL AND ";
	private static final String _FINDER_COLUMN_U_AEI_AID_A_E_ACTIONID_2 = "socialEquityLog.actionId = ? AND ";
	private static final String _FINDER_COLUMN_U_AEI_AID_A_E_ACTIONID_3 = "(socialEquityLog.actionId IS NULL OR socialEquityLog.actionId = ?) AND ";
	private static final String _FINDER_COLUMN_U_AEI_AID_A_E_ACTIVE_2 = "socialEquityLog.active = ? AND ";
	private static final String _FINDER_COLUMN_U_AEI_AID_A_E_EXTRADATA_1 = "socialEquityLog.extraData IS NULL";
	private static final String _FINDER_COLUMN_U_AEI_AID_A_E_EXTRADATA_2 = "socialEquityLog.extraData = ?";
	private static final String _FINDER_COLUMN_U_AEI_AID_A_E_EXTRADATA_3 = "(socialEquityLog.extraData IS NULL OR socialEquityLog.extraData = ?)";
	private static final String _FINDER_COLUMN_U_AID_AD_A_T_E_USERID_2 = "socialEquityLog.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_AID_AD_A_T_E_ACTIONID_1 = "socialEquityLog.actionId IS NULL AND ";
	private static final String _FINDER_COLUMN_U_AID_AD_A_T_E_ACTIONID_2 = "socialEquityLog.actionId = ? AND ";
	private static final String _FINDER_COLUMN_U_AID_AD_A_T_E_ACTIONID_3 = "(socialEquityLog.actionId IS NULL OR socialEquityLog.actionId = ?) AND ";
	private static final String _FINDER_COLUMN_U_AID_AD_A_T_E_ACTIONDATE_2 = "socialEquityLog.actionDate = ? AND ";
	private static final String _FINDER_COLUMN_U_AID_AD_A_T_E_ACTIVE_2 = "socialEquityLog.active = ? AND ";
	private static final String _FINDER_COLUMN_U_AID_AD_A_T_E_TYPE_2 = "socialEquityLog.type = ? AND ";
	private static final String _FINDER_COLUMN_U_AID_AD_A_T_E_EXTRADATA_1 = "socialEquityLog.extraData IS NULL";
	private static final String _FINDER_COLUMN_U_AID_AD_A_T_E_EXTRADATA_2 = "socialEquityLog.extraData = ?";
	private static final String _FINDER_COLUMN_U_AID_AD_A_T_E_EXTRADATA_3 = "(socialEquityLog.extraData IS NULL OR socialEquityLog.extraData = ?)";
	private static final String _FINDER_COLUMN_AEI_AID_AD_A_T_E_ASSETENTRYID_2 = "socialEquityLog.assetEntryId = ? AND ";
	private static final String _FINDER_COLUMN_AEI_AID_AD_A_T_E_ACTIONID_1 = "socialEquityLog.actionId IS NULL AND ";
	private static final String _FINDER_COLUMN_AEI_AID_AD_A_T_E_ACTIONID_2 = "socialEquityLog.actionId = ? AND ";
	private static final String _FINDER_COLUMN_AEI_AID_AD_A_T_E_ACTIONID_3 = "(socialEquityLog.actionId IS NULL OR socialEquityLog.actionId = ?) AND ";
	private static final String _FINDER_COLUMN_AEI_AID_AD_A_T_E_ACTIONDATE_2 = "socialEquityLog.actionDate = ? AND ";
	private static final String _FINDER_COLUMN_AEI_AID_AD_A_T_E_ACTIVE_2 = "socialEquityLog.active = ? AND ";
	private static final String _FINDER_COLUMN_AEI_AID_AD_A_T_E_TYPE_2 = "socialEquityLog.type = ? AND ";
	private static final String _FINDER_COLUMN_AEI_AID_AD_A_T_E_EXTRADATA_1 = "socialEquityLog.extraData IS NULL";
	private static final String _FINDER_COLUMN_AEI_AID_AD_A_T_E_EXTRADATA_2 = "socialEquityLog.extraData = ?";
	private static final String _FINDER_COLUMN_AEI_AID_AD_A_T_E_EXTRADATA_3 = "(socialEquityLog.extraData IS NULL OR socialEquityLog.extraData = ?)";
	private static final String _FINDER_COLUMN_U_AEI_AID_AD_A_T_E_USERID_2 = "socialEquityLog.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_AEI_AID_AD_A_T_E_ASSETENTRYID_2 =
		"socialEquityLog.assetEntryId = ? AND ";
	private static final String _FINDER_COLUMN_U_AEI_AID_AD_A_T_E_ACTIONID_1 = "socialEquityLog.actionId IS NULL AND ";
	private static final String _FINDER_COLUMN_U_AEI_AID_AD_A_T_E_ACTIONID_2 = "socialEquityLog.actionId = ? AND ";
	private static final String _FINDER_COLUMN_U_AEI_AID_AD_A_T_E_ACTIONID_3 = "(socialEquityLog.actionId IS NULL OR socialEquityLog.actionId = ?) AND ";
	private static final String _FINDER_COLUMN_U_AEI_AID_AD_A_T_E_ACTIONDATE_2 = "socialEquityLog.actionDate = ? AND ";
	private static final String _FINDER_COLUMN_U_AEI_AID_AD_A_T_E_ACTIVE_2 = "socialEquityLog.active = ? AND ";
	private static final String _FINDER_COLUMN_U_AEI_AID_AD_A_T_E_TYPE_2 = "socialEquityLog.type = ? AND ";
	private static final String _FINDER_COLUMN_U_AEI_AID_AD_A_T_E_EXTRADATA_1 = "socialEquityLog.extraData IS NULL";
	private static final String _FINDER_COLUMN_U_AEI_AID_AD_A_T_E_EXTRADATA_2 = "socialEquityLog.extraData = ?";
	private static final String _FINDER_COLUMN_U_AEI_AID_AD_A_T_E_EXTRADATA_3 = "(socialEquityLog.extraData IS NULL OR socialEquityLog.extraData = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialEquityLog.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialEquityLog exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SocialEquityLog exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(SocialEquityLogPersistenceImpl.class);
	private static SocialEquityLog _nullSocialEquityLog = new SocialEquityLogImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SocialEquityLog> toCacheModel() {
				return _nullSocialEquityLogCacheModel;
			}
		};

	private static CacheModel<SocialEquityLog> _nullSocialEquityLogCacheModel = new CacheModel<SocialEquityLog>() {
			public SocialEquityLog toEntityModel() {
				return _nullSocialEquityLog;
			}
		};
}