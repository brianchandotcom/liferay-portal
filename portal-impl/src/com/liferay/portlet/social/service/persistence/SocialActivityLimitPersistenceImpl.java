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

import com.liferay.portlet.social.NoSuchActivityLimitException;
import com.liferay.portlet.social.model.SocialActivityLimit;
import com.liferay.portlet.social.model.impl.SocialActivityLimitImpl;
import com.liferay.portlet.social.model.impl.SocialActivityLimitModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the social activity limit service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityLimitPersistence
 * @see SocialActivityLimitUtil
 * @generated
 */
public class SocialActivityLimitPersistenceImpl extends BasePersistenceImpl<SocialActivityLimit>
	implements SocialActivityLimitPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SocialActivityLimitUtil} to access the social activity limit persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SocialActivityLimitImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityLimitImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityLimitImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			SocialActivityLimitModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityLimitImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityLimitImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			SocialActivityLimitModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivityLimitModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_U_C_C_A_A = new FinderPath(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityLimitImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_U_C_C_A_A",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				String.class.getName()
			},
			SocialActivityLimitModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivityLimitModelImpl.USERID_COLUMN_BITMASK |
			SocialActivityLimitModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivityLimitModelImpl.CLASSPK_COLUMN_BITMASK |
			SocialActivityLimitModelImpl.ACTIVITYTYPE_COLUMN_BITMASK |
			SocialActivityLimitModelImpl.ACTIVITYCOUNTERNAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_C_C_A_A = new FinderPath(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_C_C_A_A",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityLimitImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityLimitImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the social activity limit in the entity cache if it is enabled.
	 *
	 * @param socialActivityLimit the social activity limit
	 */
	public void cacheResult(SocialActivityLimit socialActivityLimit) {
		EntityCacheUtil.putResult(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitImpl.class, socialActivityLimit.getPrimaryKey(),
			socialActivityLimit);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U_C_C_A_A,
			new Object[] {
				Long.valueOf(socialActivityLimit.getGroupId()),
				Long.valueOf(socialActivityLimit.getUserId()),
				Long.valueOf(socialActivityLimit.getClassNameId()),
				Long.valueOf(socialActivityLimit.getClassPK()),
				Integer.valueOf(socialActivityLimit.getActivityType()),
				
			socialActivityLimit.getActivityCounterName()
			}, socialActivityLimit);

		socialActivityLimit.resetOriginalValues();
	}

	/**
	 * Caches the social activity limits in the entity cache if it is enabled.
	 *
	 * @param socialActivityLimits the social activity limits
	 */
	public void cacheResult(List<SocialActivityLimit> socialActivityLimits) {
		for (SocialActivityLimit socialActivityLimit : socialActivityLimits) {
			if (EntityCacheUtil.getResult(
						SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
						SocialActivityLimitImpl.class,
						socialActivityLimit.getPrimaryKey()) == null) {
				cacheResult(socialActivityLimit);
			}
			else {
				socialActivityLimit.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all social activity limits.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SocialActivityLimitImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SocialActivityLimitImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the social activity limit.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SocialActivityLimit socialActivityLimit) {
		EntityCacheUtil.removeResult(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitImpl.class, socialActivityLimit.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(socialActivityLimit);
	}

	@Override
	public void clearCache(List<SocialActivityLimit> socialActivityLimits) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SocialActivityLimit socialActivityLimit : socialActivityLimits) {
			EntityCacheUtil.removeResult(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
				SocialActivityLimitImpl.class,
				socialActivityLimit.getPrimaryKey());

			clearUniqueFindersCache(socialActivityLimit);
		}
	}

	protected void clearUniqueFindersCache(
		SocialActivityLimit socialActivityLimit) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U_C_C_A_A,
			new Object[] {
				Long.valueOf(socialActivityLimit.getGroupId()),
				Long.valueOf(socialActivityLimit.getUserId()),
				Long.valueOf(socialActivityLimit.getClassNameId()),
				Long.valueOf(socialActivityLimit.getClassPK()),
				Integer.valueOf(socialActivityLimit.getActivityType()),
				
			socialActivityLimit.getActivityCounterName()
			});
	}

	/**
	 * Creates a new social activity limit with the primary key. Does not add the social activity limit to the database.
	 *
	 * @param activityLimitId the primary key for the new social activity limit
	 * @return the new social activity limit
	 */
	public SocialActivityLimit create(long activityLimitId) {
		SocialActivityLimit socialActivityLimit = new SocialActivityLimitImpl();

		socialActivityLimit.setNew(true);
		socialActivityLimit.setPrimaryKey(activityLimitId);

		return socialActivityLimit;
	}

	/**
	 * Removes the social activity limit with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param activityLimitId the primary key of the social activity limit
	 * @return the social activity limit that was removed
	 * @throws com.liferay.portlet.social.NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit remove(long activityLimitId)
		throws NoSuchActivityLimitException, SystemException {
		return remove(Long.valueOf(activityLimitId));
	}

	/**
	 * Removes the social activity limit with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the social activity limit
	 * @return the social activity limit that was removed
	 * @throws com.liferay.portlet.social.NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivityLimit remove(Serializable primaryKey)
		throws NoSuchActivityLimitException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialActivityLimit socialActivityLimit = (SocialActivityLimit)session.get(SocialActivityLimitImpl.class,
					primaryKey);

			if (socialActivityLimit == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchActivityLimitException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(socialActivityLimit);
		}
		catch (NoSuchActivityLimitException nsee) {
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
	protected SocialActivityLimit removeImpl(
		SocialActivityLimit socialActivityLimit) throws SystemException {
		socialActivityLimit = toUnwrappedModel(socialActivityLimit);

		Session session = null;

		try {
			session = openSession();

			if (socialActivityLimit.isCachedModel()) {
				socialActivityLimit = (SocialActivityLimit)session.get(SocialActivityLimitImpl.class,
						socialActivityLimit.getPrimaryKeyObj());
			}

			if (socialActivityLimit != null) {
				session.delete(socialActivityLimit);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (socialActivityLimit != null) {
			clearCache(socialActivityLimit);
		}

		return socialActivityLimit;
	}

	@Override
	public SocialActivityLimit updateImpl(
		com.liferay.portlet.social.model.SocialActivityLimit socialActivityLimit)
		throws SystemException {
		socialActivityLimit = toUnwrappedModel(socialActivityLimit);

		boolean isNew = socialActivityLimit.isNew();

		SocialActivityLimitModelImpl socialActivityLimitModelImpl = (SocialActivityLimitModelImpl)socialActivityLimit;

		Session session = null;

		try {
			session = openSession();

			if (socialActivityLimit.isNew()) {
				session.save(socialActivityLimit);

				socialActivityLimit.setNew(false);
			}
			else {
				session.merge(socialActivityLimit);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SocialActivityLimitModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((socialActivityLimitModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialActivityLimitModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] {
						Long.valueOf(socialActivityLimitModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((socialActivityLimitModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialActivityLimitModelImpl.getOriginalClassNameId()),
						Long.valueOf(socialActivityLimitModelImpl.getOriginalClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						Long.valueOf(socialActivityLimitModelImpl.getClassNameId()),
						Long.valueOf(socialActivityLimitModelImpl.getClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}
		}

		EntityCacheUtil.putResult(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitImpl.class, socialActivityLimit.getPrimaryKey(),
			socialActivityLimit);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U_C_C_A_A,
				new Object[] {
					Long.valueOf(socialActivityLimit.getGroupId()),
					Long.valueOf(socialActivityLimit.getUserId()),
					Long.valueOf(socialActivityLimit.getClassNameId()),
					Long.valueOf(socialActivityLimit.getClassPK()),
					Integer.valueOf(socialActivityLimit.getActivityType()),
					
				socialActivityLimit.getActivityCounterName()
				}, socialActivityLimit);
		}
		else {
			if ((socialActivityLimitModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_U_C_C_A_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(socialActivityLimitModelImpl.getOriginalGroupId()),
						Long.valueOf(socialActivityLimitModelImpl.getOriginalUserId()),
						Long.valueOf(socialActivityLimitModelImpl.getOriginalClassNameId()),
						Long.valueOf(socialActivityLimitModelImpl.getOriginalClassPK()),
						Integer.valueOf(socialActivityLimitModelImpl.getOriginalActivityType()),
						
						socialActivityLimitModelImpl.getOriginalActivityCounterName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U_C_C_A_A,
					args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U_C_C_A_A,
					args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U_C_C_A_A,
					new Object[] {
						Long.valueOf(socialActivityLimit.getGroupId()),
						Long.valueOf(socialActivityLimit.getUserId()),
						Long.valueOf(socialActivityLimit.getClassNameId()),
						Long.valueOf(socialActivityLimit.getClassPK()),
						Integer.valueOf(socialActivityLimit.getActivityType()),
						
					socialActivityLimit.getActivityCounterName()
					}, socialActivityLimit);
			}
		}

		return socialActivityLimit;
	}

	protected SocialActivityLimit toUnwrappedModel(
		SocialActivityLimit socialActivityLimit) {
		if (socialActivityLimit instanceof SocialActivityLimitImpl) {
			return socialActivityLimit;
		}

		SocialActivityLimitImpl socialActivityLimitImpl = new SocialActivityLimitImpl();

		socialActivityLimitImpl.setNew(socialActivityLimit.isNew());
		socialActivityLimitImpl.setPrimaryKey(socialActivityLimit.getPrimaryKey());

		socialActivityLimitImpl.setActivityLimitId(socialActivityLimit.getActivityLimitId());
		socialActivityLimitImpl.setGroupId(socialActivityLimit.getGroupId());
		socialActivityLimitImpl.setCompanyId(socialActivityLimit.getCompanyId());
		socialActivityLimitImpl.setUserId(socialActivityLimit.getUserId());
		socialActivityLimitImpl.setClassNameId(socialActivityLimit.getClassNameId());
		socialActivityLimitImpl.setClassPK(socialActivityLimit.getClassPK());
		socialActivityLimitImpl.setActivityType(socialActivityLimit.getActivityType());
		socialActivityLimitImpl.setActivityCounterName(socialActivityLimit.getActivityCounterName());
		socialActivityLimitImpl.setValue(socialActivityLimit.getValue());

		return socialActivityLimitImpl;
	}

	/**
	 * Returns the social activity limit with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity limit
	 * @return the social activity limit
	 * @throws com.liferay.portal.NoSuchModelException if a social activity limit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivityLimit findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social activity limit with the primary key or throws a {@link com.liferay.portlet.social.NoSuchActivityLimitException} if it could not be found.
	 *
	 * @param activityLimitId the primary key of the social activity limit
	 * @return the social activity limit
	 * @throws com.liferay.portlet.social.NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit findByPrimaryKey(long activityLimitId)
		throws NoSuchActivityLimitException, SystemException {
		SocialActivityLimit socialActivityLimit = fetchByPrimaryKey(activityLimitId);

		if (socialActivityLimit == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + activityLimitId);
			}

			throw new NoSuchActivityLimitException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				activityLimitId);
		}

		return socialActivityLimit;
	}

	/**
	 * Returns the social activity limit with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity limit
	 * @return the social activity limit, or <code>null</code> if a social activity limit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivityLimit fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social activity limit with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param activityLimitId the primary key of the social activity limit
	 * @return the social activity limit, or <code>null</code> if a social activity limit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit fetchByPrimaryKey(long activityLimitId)
		throws SystemException {
		SocialActivityLimit socialActivityLimit = (SocialActivityLimit)EntityCacheUtil.getResult(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
				SocialActivityLimitImpl.class, activityLimitId);

		if (socialActivityLimit == _nullSocialActivityLimit) {
			return null;
		}

		if (socialActivityLimit == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				socialActivityLimit = (SocialActivityLimit)session.get(SocialActivityLimitImpl.class,
						Long.valueOf(activityLimitId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (socialActivityLimit != null) {
					cacheResult(socialActivityLimit);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
						SocialActivityLimitImpl.class, activityLimitId,
						_nullSocialActivityLimit);
				}

				closeSession(session);
			}
		}

		return socialActivityLimit;
	}

	/**
	 * Returns all the social activity limits where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching social activity limits
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityLimit> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity limits where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of social activity limits
	 * @param end the upper bound of the range of social activity limits (not inclusive)
	 * @return the range of matching social activity limits
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityLimit> findByUserId(long userId, int start,
		int end) throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity limits where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of social activity limits
	 * @param end the upper bound of the range of social activity limits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity limits
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityLimit> findByUserId(long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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

		List<SocialActivityLimit> list = (List<SocialActivityLimit>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialActivityLimit socialActivityLimit : list) {
				if ((userId != socialActivityLimit.getUserId())) {
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

			query.append(_SQL_SELECT_SOCIALACTIVITYLIMIT_WHERE);

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

				list = (List<SocialActivityLimit>)QueryUtil.list(q,
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
	 * Returns the first social activity limit in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity limit
	 * @throws com.liferay.portlet.social.NoSuchActivityLimitException if a matching social activity limit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchActivityLimitException, SystemException {
		SocialActivityLimit socialActivityLimit = fetchByUserId_First(userId,
				orderByComparator);

		if (socialActivityLimit != null) {
			return socialActivityLimit;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityLimitException(msg.toString());
	}

	/**
	 * Returns the first social activity limit in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialActivityLimit> list = findByUserId(userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity limit in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity limit
	 * @throws com.liferay.portlet.social.NoSuchActivityLimitException if a matching social activity limit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchActivityLimitException, SystemException {
		SocialActivityLimit socialActivityLimit = fetchByUserId_Last(userId,
				orderByComparator);

		if (socialActivityLimit != null) {
			return socialActivityLimit;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityLimitException(msg.toString());
	}

	/**
	 * Returns the last social activity limit in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		List<SocialActivityLimit> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity limits before and after the current social activity limit in the ordered set where userId = &#63;.
	 *
	 * @param activityLimitId the primary key of the current social activity limit
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity limit
	 * @throws com.liferay.portlet.social.NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit[] findByUserId_PrevAndNext(
		long activityLimitId, long userId, OrderByComparator orderByComparator)
		throws NoSuchActivityLimitException, SystemException {
		SocialActivityLimit socialActivityLimit = findByPrimaryKey(activityLimitId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityLimit[] array = new SocialActivityLimitImpl[3];

			array[0] = getByUserId_PrevAndNext(session, socialActivityLimit,
					userId, orderByComparator, true);

			array[1] = socialActivityLimit;

			array[2] = getByUserId_PrevAndNext(session, socialActivityLimit,
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

	protected SocialActivityLimit getByUserId_PrevAndNext(Session session,
		SocialActivityLimit socialActivityLimit, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYLIMIT_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivityLimit);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivityLimit> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social activity limits where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching social activity limits
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityLimit> findByC_C(long classNameId, long classPK)
		throws SystemException {
		return findByC_C(classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity limits where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of social activity limits
	 * @param end the upper bound of the range of social activity limits (not inclusive)
	 * @return the range of matching social activity limits
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityLimit> findByC_C(long classNameId, long classPK,
		int start, int end) throws SystemException {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity limits where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of social activity limits
	 * @param end the upper bound of the range of social activity limits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity limits
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityLimit> findByC_C(long classNameId, long classPK,
		int start, int end, OrderByComparator orderByComparator)
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

		List<SocialActivityLimit> list = (List<SocialActivityLimit>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SocialActivityLimit socialActivityLimit : list) {
				if ((classNameId != socialActivityLimit.getClassNameId()) ||
						(classPK != socialActivityLimit.getClassPK())) {
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

			query.append(_SQL_SELECT_SOCIALACTIVITYLIMIT_WHERE);

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

				list = (List<SocialActivityLimit>)QueryUtil.list(q,
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
	 * Returns the first social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity limit
	 * @throws com.liferay.portlet.social.NoSuchActivityLimitException if a matching social activity limit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit findByC_C_First(long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchActivityLimitException, SystemException {
		SocialActivityLimit socialActivityLimit = fetchByC_C_First(classNameId,
				classPK, orderByComparator);

		if (socialActivityLimit != null) {
			return socialActivityLimit;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityLimitException(msg.toString());
	}

	/**
	 * Returns the first social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit fetchByC_C_First(long classNameId, long classPK,
		OrderByComparator orderByComparator) throws SystemException {
		List<SocialActivityLimit> list = findByC_C(classNameId, classPK, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity limit
	 * @throws com.liferay.portlet.social.NoSuchActivityLimitException if a matching social activity limit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit findByC_C_Last(long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchActivityLimitException, SystemException {
		SocialActivityLimit socialActivityLimit = fetchByC_C_Last(classNameId,
				classPK, orderByComparator);

		if (socialActivityLimit != null) {
			return socialActivityLimit;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityLimitException(msg.toString());
	}

	/**
	 * Returns the last social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit fetchByC_C_Last(long classNameId, long classPK,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_C(classNameId, classPK);

		List<SocialActivityLimit> list = findByC_C(classNameId, classPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity limits before and after the current social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param activityLimitId the primary key of the current social activity limit
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity limit
	 * @throws com.liferay.portlet.social.NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit[] findByC_C_PrevAndNext(long activityLimitId,
		long classNameId, long classPK, OrderByComparator orderByComparator)
		throws NoSuchActivityLimitException, SystemException {
		SocialActivityLimit socialActivityLimit = findByPrimaryKey(activityLimitId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityLimit[] array = new SocialActivityLimitImpl[3];

			array[0] = getByC_C_PrevAndNext(session, socialActivityLimit,
					classNameId, classPK, orderByComparator, true);

			array[1] = socialActivityLimit;

			array[2] = getByC_C_PrevAndNext(session, socialActivityLimit,
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

	protected SocialActivityLimit getByC_C_PrevAndNext(Session session,
		SocialActivityLimit socialActivityLimit, long classNameId,
		long classPK, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYLIMIT_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivityLimit);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivityLimit> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the social activity limit where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityType = &#63; and activityCounterName = &#63; or throws a {@link com.liferay.portlet.social.NoSuchActivityLimitException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param activityType the activity type
	 * @param activityCounterName the activity counter name
	 * @return the matching social activity limit
	 * @throws com.liferay.portlet.social.NoSuchActivityLimitException if a matching social activity limit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit findByG_U_C_C_A_A(long groupId, long userId,
		long classNameId, long classPK, int activityType,
		String activityCounterName)
		throws NoSuchActivityLimitException, SystemException {
		SocialActivityLimit socialActivityLimit = fetchByG_U_C_C_A_A(groupId,
				userId, classNameId, classPK, activityType, activityCounterName);

		if (socialActivityLimit == null) {
			StringBundler msg = new StringBundler(14);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", activityType=");
			msg.append(activityType);

			msg.append(", activityCounterName=");
			msg.append(activityCounterName);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchActivityLimitException(msg.toString());
		}

		return socialActivityLimit;
	}

	/**
	 * Returns the social activity limit where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityType = &#63; and activityCounterName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param activityType the activity type
	 * @param activityCounterName the activity counter name
	 * @return the matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit fetchByG_U_C_C_A_A(long groupId, long userId,
		long classNameId, long classPK, int activityType,
		String activityCounterName) throws SystemException {
		return fetchByG_U_C_C_A_A(groupId, userId, classNameId, classPK,
			activityType, activityCounterName, true);
	}

	/**
	 * Returns the social activity limit where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityType = &#63; and activityCounterName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param activityType the activity type
	 * @param activityCounterName the activity counter name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit fetchByG_U_C_C_A_A(long groupId, long userId,
		long classNameId, long classPK, int activityType,
		String activityCounterName, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, userId, classNameId, classPK, activityType,
				activityCounterName
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_U_C_C_A_A,
					finderArgs, this);
		}

		if (result instanceof SocialActivityLimit) {
			SocialActivityLimit socialActivityLimit = (SocialActivityLimit)result;

			if ((groupId != socialActivityLimit.getGroupId()) ||
					(userId != socialActivityLimit.getUserId()) ||
					(classNameId != socialActivityLimit.getClassNameId()) ||
					(classPK != socialActivityLimit.getClassPK()) ||
					(activityType != socialActivityLimit.getActivityType()) ||
					!Validator.equals(activityCounterName,
						socialActivityLimit.getActivityCounterName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_SELECT_SOCIALACTIVITYLIMIT_WHERE);

			query.append(_FINDER_COLUMN_G_U_C_C_A_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_A_A_USERID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_A_A_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_A_A_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_U_C_C_A_A_ACTIVITYTYPE_2);

			if (activityCounterName == null) {
				query.append(_FINDER_COLUMN_G_U_C_C_A_A_ACTIVITYCOUNTERNAME_1);
			}
			else {
				if (activityCounterName.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_U_C_C_A_A_ACTIVITYCOUNTERNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_U_C_C_A_A_ACTIVITYCOUNTERNAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(activityType);

				if (activityCounterName != null) {
					qPos.add(activityCounterName);
				}

				List<SocialActivityLimit> list = q.list();

				result = list;

				SocialActivityLimit socialActivityLimit = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U_C_C_A_A,
						finderArgs, list);
				}
				else {
					socialActivityLimit = list.get(0);

					cacheResult(socialActivityLimit);

					if ((socialActivityLimit.getGroupId() != groupId) ||
							(socialActivityLimit.getUserId() != userId) ||
							(socialActivityLimit.getClassNameId() != classNameId) ||
							(socialActivityLimit.getClassPK() != classPK) ||
							(socialActivityLimit.getActivityType() != activityType) ||
							(socialActivityLimit.getActivityCounterName() == null) ||
							!socialActivityLimit.getActivityCounterName()
													.equals(activityCounterName)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U_C_C_A_A,
							finderArgs, socialActivityLimit);
					}
				}

				return socialActivityLimit;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U_C_C_A_A,
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
				return (SocialActivityLimit)result;
			}
		}
	}

	/**
	 * Returns all the social activity limits.
	 *
	 * @return the social activity limits
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityLimit> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity limits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity limits
	 * @param end the upper bound of the range of social activity limits (not inclusive)
	 * @return the range of social activity limits
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityLimit> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity limits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity limits
	 * @param end the upper bound of the range of social activity limits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of social activity limits
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityLimit> findAll(int start, int end,
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

		List<SocialActivityLimit> list = (List<SocialActivityLimit>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SOCIALACTIVITYLIMIT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SOCIALACTIVITYLIMIT;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SocialActivityLimit>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialActivityLimit>)QueryUtil.list(q,
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
	 * Removes all the social activity limits where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (SocialActivityLimit socialActivityLimit : findByUserId(userId)) {
			remove(socialActivityLimit);
		}
	}

	/**
	 * Removes all the social activity limits where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_C(long classNameId, long classPK)
		throws SystemException {
		for (SocialActivityLimit socialActivityLimit : findByC_C(classNameId,
				classPK)) {
			remove(socialActivityLimit);
		}
	}

	/**
	 * Removes the social activity limit where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityType = &#63; and activityCounterName = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param activityType the activity type
	 * @param activityCounterName the activity counter name
	 * @return the social activity limit that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit removeByG_U_C_C_A_A(long groupId, long userId,
		long classNameId, long classPK, int activityType,
		String activityCounterName)
		throws NoSuchActivityLimitException, SystemException {
		SocialActivityLimit socialActivityLimit = findByG_U_C_C_A_A(groupId,
				userId, classNameId, classPK, activityType, activityCounterName);

		return remove(socialActivityLimit);
	}

	/**
	 * Removes all the social activity limits from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SocialActivityLimit socialActivityLimit : findAll()) {
			remove(socialActivityLimit);
		}
	}

	/**
	 * Returns the number of social activity limits where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching social activity limits
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALACTIVITYLIMIT_WHERE);

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
	 * Returns the number of social activity limits where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching social activity limits
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALACTIVITYLIMIT_WHERE);

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
	 * Returns the number of social activity limits where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityType = &#63; and activityCounterName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param activityType the activity type
	 * @param activityCounterName the activity counter name
	 * @return the number of matching social activity limits
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U_C_C_A_A(long groupId, long userId, long classNameId,
		long classPK, int activityType, String activityCounterName)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, userId, classNameId, classPK, activityType,
				activityCounterName
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U_C_C_A_A,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_COUNT_SOCIALACTIVITYLIMIT_WHERE);

			query.append(_FINDER_COLUMN_G_U_C_C_A_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_A_A_USERID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_A_A_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_U_C_C_A_A_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_U_C_C_A_A_ACTIVITYTYPE_2);

			if (activityCounterName == null) {
				query.append(_FINDER_COLUMN_G_U_C_C_A_A_ACTIVITYCOUNTERNAME_1);
			}
			else {
				if (activityCounterName.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_U_C_C_A_A_ACTIVITYCOUNTERNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_U_C_C_A_A_ACTIVITYCOUNTERNAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(activityType);

				if (activityCounterName != null) {
					qPos.add(activityCounterName);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U_C_C_A_A,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social activity limits.
	 *
	 * @return the number of social activity limits
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SOCIALACTIVITYLIMIT);

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
	 * Initializes the social activity limit persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.social.model.SocialActivityLimit")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialActivityLimit>> listenersList = new ArrayList<ModelListener<SocialActivityLimit>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialActivityLimit>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(SocialActivityLimitImpl.class.getName());
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
	private static final String _SQL_SELECT_SOCIALACTIVITYLIMIT = "SELECT socialActivityLimit FROM SocialActivityLimit socialActivityLimit";
	private static final String _SQL_SELECT_SOCIALACTIVITYLIMIT_WHERE = "SELECT socialActivityLimit FROM SocialActivityLimit socialActivityLimit WHERE ";
	private static final String _SQL_COUNT_SOCIALACTIVITYLIMIT = "SELECT COUNT(socialActivityLimit) FROM SocialActivityLimit socialActivityLimit";
	private static final String _SQL_COUNT_SOCIALACTIVITYLIMIT_WHERE = "SELECT COUNT(socialActivityLimit) FROM SocialActivityLimit socialActivityLimit WHERE ";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "socialActivityLimit.userId = ?";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "socialActivityLimit.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "socialActivityLimit.classPK = ?";
	private static final String _FINDER_COLUMN_G_U_C_C_A_A_GROUPID_2 = "socialActivityLimit.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_C_C_A_A_USERID_2 = "socialActivityLimit.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_C_C_A_A_CLASSNAMEID_2 = "socialActivityLimit.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_C_C_A_A_CLASSPK_2 = "socialActivityLimit.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_U_C_C_A_A_ACTIVITYTYPE_2 = "socialActivityLimit.activityType = ? AND ";
	private static final String _FINDER_COLUMN_G_U_C_C_A_A_ACTIVITYCOUNTERNAME_1 =
		"socialActivityLimit.activityCounterName IS NULL";
	private static final String _FINDER_COLUMN_G_U_C_C_A_A_ACTIVITYCOUNTERNAME_2 =
		"socialActivityLimit.activityCounterName = ?";
	private static final String _FINDER_COLUMN_G_U_C_C_A_A_ACTIVITYCOUNTERNAME_3 =
		"(socialActivityLimit.activityCounterName IS NULL OR socialActivityLimit.activityCounterName = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialActivityLimit.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialActivityLimit exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SocialActivityLimit exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(SocialActivityLimitPersistenceImpl.class);
	private static SocialActivityLimit _nullSocialActivityLimit = new SocialActivityLimitImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SocialActivityLimit> toCacheModel() {
				return _nullSocialActivityLimitCacheModel;
			}
		};

	private static CacheModel<SocialActivityLimit> _nullSocialActivityLimitCacheModel =
		new CacheModel<SocialActivityLimit>() {
			public SocialActivityLimit toEntityModel() {
				return _nullSocialActivityLimit;
			}
		};
}