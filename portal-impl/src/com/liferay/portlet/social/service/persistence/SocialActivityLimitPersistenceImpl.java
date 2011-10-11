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
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_CN_CP = new FinderPath(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityLimitImpl.class, FINDER_CLASS_NAME_LIST,
			"findByCN_CP",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_CN_CP = new FinderPath(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByCN_CP",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_U_CN_CP_A_S = new FinderPath(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityLimitImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_U_CN_CP_A_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_CN_CP_A_S = new FinderPath(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_U_CN_CP_A_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityLimitImpl.class, FINDER_CLASS_NAME_LIST, "findAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the social activity limit in the entity cache if it is enabled.
	 *
	 * @param socialActivityLimit the social activity limit
	 */
	public void cacheResult(SocialActivityLimit socialActivityLimit) {
		EntityCacheUtil.putResult(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitImpl.class, socialActivityLimit.getPrimaryKey(),
			socialActivityLimit);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U_CN_CP_A_S,
			new Object[] {
				Long.valueOf(socialActivityLimit.getGroupId()),
				Long.valueOf(socialActivityLimit.getUserId()),
				Long.valueOf(socialActivityLimit.getClassNameId()),
				Long.valueOf(socialActivityLimit.getClassPK()),
				Integer.valueOf(socialActivityLimit.getActivityKey()),
				
			socialActivityLimit.getStatName()
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
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
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

		FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL, FINDER_ARGS_EMPTY);

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U_CN_CP_A_S,
			new Object[] {
				Long.valueOf(socialActivityLimit.getGroupId()),
				Long.valueOf(socialActivityLimit.getUserId()),
				Long.valueOf(socialActivityLimit.getClassNameId()),
				Long.valueOf(socialActivityLimit.getClassPK()),
				Integer.valueOf(socialActivityLimit.getActivityKey()),
				
			socialActivityLimit.getStatName()
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
	 * @param primaryKey the primary key of the social activity limit
	 * @return the social activity limit that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a social activity limit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivityLimit remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
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
		Session session = null;

		try {
			session = openSession();

			SocialActivityLimit socialActivityLimit = (SocialActivityLimit)session.get(SocialActivityLimitImpl.class,
					Long.valueOf(activityLimitId));

			if (socialActivityLimit == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						activityLimitId);
				}

				throw new NoSuchActivityLimitException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					activityLimitId);
			}

			return socialActivityLimitPersistence.remove(socialActivityLimit);
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

	/**
	 * Removes the social activity limit from the database. Also notifies the appropriate model listeners.
	 *
	 * @param socialActivityLimit the social activity limit
	 * @return the social activity limit that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivityLimit remove(SocialActivityLimit socialActivityLimit)
		throws SystemException {
		return super.remove(socialActivityLimit);
	}

	@Override
	protected SocialActivityLimit removeImpl(
		SocialActivityLimit socialActivityLimit) throws SystemException {
		socialActivityLimit = toUnwrappedModel(socialActivityLimit);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, socialActivityLimit);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		SocialActivityLimitModelImpl socialActivityLimitModelImpl = (SocialActivityLimitModelImpl)socialActivityLimit;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U_CN_CP_A_S,
			new Object[] {
				Long.valueOf(socialActivityLimitModelImpl.getGroupId()),
				Long.valueOf(socialActivityLimitModelImpl.getUserId()),
				Long.valueOf(socialActivityLimitModelImpl.getClassNameId()),
				Long.valueOf(socialActivityLimitModelImpl.getClassPK()),
				Integer.valueOf(socialActivityLimitModelImpl.getActivityKey()),
				
			socialActivityLimitModelImpl.getStatName()
			});

		EntityCacheUtil.removeResult(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitImpl.class, socialActivityLimit.getPrimaryKey());

		return socialActivityLimit;
	}

	@Override
	public SocialActivityLimit updateImpl(
		com.liferay.portlet.social.model.SocialActivityLimit socialActivityLimit,
		boolean merge) throws SystemException {
		socialActivityLimit = toUnwrappedModel(socialActivityLimit);

		boolean isNew = socialActivityLimit.isNew();

		SocialActivityLimitModelImpl socialActivityLimitModelImpl = (SocialActivityLimitModelImpl)socialActivityLimit;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, socialActivityLimit, merge);

			socialActivityLimit.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SocialActivityLimitModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityLimitImpl.class, socialActivityLimit.getPrimaryKey(),
			socialActivityLimit);

		if (!isNew &&
				((socialActivityLimit.getGroupId() != socialActivityLimitModelImpl.getOriginalGroupId()) ||
				(socialActivityLimit.getUserId() != socialActivityLimitModelImpl.getOriginalUserId()) ||
				(socialActivityLimit.getClassNameId() != socialActivityLimitModelImpl.getOriginalClassNameId()) ||
				(socialActivityLimit.getClassPK() != socialActivityLimitModelImpl.getOriginalClassPK()) ||
				(socialActivityLimit.getActivityKey() != socialActivityLimitModelImpl.getOriginalActivityKey()) ||
				!Validator.equals(socialActivityLimit.getStatName(),
					socialActivityLimitModelImpl.getOriginalStatName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U_CN_CP_A_S,
				new Object[] {
					Long.valueOf(
						socialActivityLimitModelImpl.getOriginalGroupId()),
					Long.valueOf(
						socialActivityLimitModelImpl.getOriginalUserId()),
					Long.valueOf(
						socialActivityLimitModelImpl.getOriginalClassNameId()),
					Long.valueOf(
						socialActivityLimitModelImpl.getOriginalClassPK()),
					Integer.valueOf(
						socialActivityLimitModelImpl.getOriginalActivityKey()),
					
				socialActivityLimitModelImpl.getOriginalStatName()
				});
		}

		if (isNew ||
				((socialActivityLimit.getGroupId() != socialActivityLimitModelImpl.getOriginalGroupId()) ||
				(socialActivityLimit.getUserId() != socialActivityLimitModelImpl.getOriginalUserId()) ||
				(socialActivityLimit.getClassNameId() != socialActivityLimitModelImpl.getOriginalClassNameId()) ||
				(socialActivityLimit.getClassPK() != socialActivityLimitModelImpl.getOriginalClassPK()) ||
				(socialActivityLimit.getActivityKey() != socialActivityLimitModelImpl.getOriginalActivityKey()) ||
				!Validator.equals(socialActivityLimit.getStatName(),
					socialActivityLimitModelImpl.getOriginalStatName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U_CN_CP_A_S,
				new Object[] {
					Long.valueOf(socialActivityLimit.getGroupId()),
					Long.valueOf(socialActivityLimit.getUserId()),
					Long.valueOf(socialActivityLimit.getClassNameId()),
					Long.valueOf(socialActivityLimit.getClassPK()),
					Integer.valueOf(socialActivityLimit.getActivityKey()),
					
				socialActivityLimit.getStatName()
				}, socialActivityLimit);
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
		socialActivityLimitImpl.setActivityKey(socialActivityLimit.getActivityKey());
		socialActivityLimitImpl.setStatName(socialActivityLimit.getStatName());
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
	 * Returns all the social activity limits where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching social activity limits
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivityLimit> findByCN_CP(long classNameId, long classPK)
		throws SystemException {
		return findByCN_CP(classNameId, classPK, QueryUtil.ALL_POS,
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
	public List<SocialActivityLimit> findByCN_CP(long classNameId,
		long classPK, int start, int end) throws SystemException {
		return findByCN_CP(classNameId, classPK, start, end, null);
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
	public List<SocialActivityLimit> findByCN_CP(long classNameId,
		long classPK, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				classNameId, classPK,
				
				start, end, orderByComparator
			};

		List<SocialActivityLimit> list = (List<SocialActivityLimit>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_CN_CP,
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

			query.append(_SQL_SELECT_SOCIALACTIVITYLIMIT_WHERE);

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

				list = (List<SocialActivityLimit>)QueryUtil.list(q,
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
	 * Returns the first social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity limit
	 * @throws com.liferay.portlet.social.NoSuchActivityLimitException if a matching social activity limit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit findByCN_CP_First(long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws NoSuchActivityLimitException, SystemException {
		List<SocialActivityLimit> list = findByCN_CP(classNameId, classPK, 0,
				1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchActivityLimitException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity limit
	 * @throws com.liferay.portlet.social.NoSuchActivityLimitException if a matching social activity limit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit findByCN_CP_Last(long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchActivityLimitException, SystemException {
		int count = countByCN_CP(classNameId, classPK);

		List<SocialActivityLimit> list = findByCN_CP(classNameId, classPK,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchActivityLimitException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the social activity limits before and after the current social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param activityLimitId the primary key of the current social activity limit
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity limit
	 * @throws com.liferay.portlet.social.NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit[] findByCN_CP_PrevAndNext(long activityLimitId,
		long classNameId, long classPK, OrderByComparator orderByComparator)
		throws NoSuchActivityLimitException, SystemException {
		SocialActivityLimit socialActivityLimit = findByPrimaryKey(activityLimitId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityLimit[] array = new SocialActivityLimitImpl[3];

			array[0] = getByCN_CP_PrevAndNext(session, socialActivityLimit,
					classNameId, classPK, orderByComparator, true);

			array[1] = socialActivityLimit;

			array[2] = getByCN_CP_PrevAndNext(session, socialActivityLimit,
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

	protected SocialActivityLimit getByCN_CP_PrevAndNext(Session session,
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
			Object[] values = orderByComparator.getOrderByValues(socialActivityLimit);

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
	 * Returns the social activity limit where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityKey = &#63; and statName = &#63; or throws a {@link com.liferay.portlet.social.NoSuchActivityLimitException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param activityKey the activity key
	 * @param statName the stat name
	 * @return the matching social activity limit
	 * @throws com.liferay.portlet.social.NoSuchActivityLimitException if a matching social activity limit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit findByG_U_CN_CP_A_S(long groupId, long userId,
		long classNameId, long classPK, int activityKey, String statName)
		throws NoSuchActivityLimitException, SystemException {
		SocialActivityLimit socialActivityLimit = fetchByG_U_CN_CP_A_S(groupId,
				userId, classNameId, classPK, activityKey, statName);

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

			msg.append(", activityKey=");
			msg.append(activityKey);

			msg.append(", statName=");
			msg.append(statName);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchActivityLimitException(msg.toString());
		}

		return socialActivityLimit;
	}

	/**
	 * Returns the social activity limit where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityKey = &#63; and statName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param activityKey the activity key
	 * @param statName the stat name
	 * @return the matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit fetchByG_U_CN_CP_A_S(long groupId, long userId,
		long classNameId, long classPK, int activityKey, String statName)
		throws SystemException {
		return fetchByG_U_CN_CP_A_S(groupId, userId, classNameId, classPK,
			activityKey, statName, true);
	}

	/**
	 * Returns the social activity limit where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityKey = &#63; and statName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param activityKey the activity key
	 * @param statName the stat name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivityLimit fetchByG_U_CN_CP_A_S(long groupId, long userId,
		long classNameId, long classPK, int activityKey, String statName,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, userId, classNameId, classPK, activityKey, statName
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_U_CN_CP_A_S,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_SELECT_SOCIALACTIVITYLIMIT_WHERE);

			query.append(_FINDER_COLUMN_G_U_CN_CP_A_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_CN_CP_A_S_USERID_2);

			query.append(_FINDER_COLUMN_G_U_CN_CP_A_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_U_CN_CP_A_S_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_U_CN_CP_A_S_ACTIVITYKEY_2);

			if (statName == null) {
				query.append(_FINDER_COLUMN_G_U_CN_CP_A_S_STATNAME_1);
			}
			else {
				if (statName.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_U_CN_CP_A_S_STATNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_U_CN_CP_A_S_STATNAME_2);
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

				qPos.add(activityKey);

				if (statName != null) {
					qPos.add(statName);
				}

				List<SocialActivityLimit> list = q.list();

				result = list;

				SocialActivityLimit socialActivityLimit = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U_CN_CP_A_S,
						finderArgs, list);
				}
				else {
					socialActivityLimit = list.get(0);

					cacheResult(socialActivityLimit);

					if ((socialActivityLimit.getGroupId() != groupId) ||
							(socialActivityLimit.getUserId() != userId) ||
							(socialActivityLimit.getClassNameId() != classNameId) ||
							(socialActivityLimit.getClassPK() != classPK) ||
							(socialActivityLimit.getActivityKey() != activityKey) ||
							(socialActivityLimit.getStatName() == null) ||
							!socialActivityLimit.getStatName().equals(statName)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U_CN_CP_A_S,
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
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U_CN_CP_A_S,
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
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		List<SocialActivityLimit> list = (List<SocialActivityLimit>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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
	 * Removes all the social activity limits where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCN_CP(long classNameId, long classPK)
		throws SystemException {
		for (SocialActivityLimit socialActivityLimit : findByCN_CP(
				classNameId, classPK)) {
			socialActivityLimitPersistence.remove(socialActivityLimit);
		}
	}

	/**
	 * Removes the social activity limit where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityKey = &#63; and statName = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param activityKey the activity key
	 * @param statName the stat name
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_U_CN_CP_A_S(long groupId, long userId,
		long classNameId, long classPK, int activityKey, String statName)
		throws NoSuchActivityLimitException, SystemException {
		SocialActivityLimit socialActivityLimit = findByG_U_CN_CP_A_S(groupId,
				userId, classNameId, classPK, activityKey, statName);

		socialActivityLimitPersistence.remove(socialActivityLimit);
	}

	/**
	 * Removes all the social activity limits from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SocialActivityLimit socialActivityLimit : findAll()) {
			socialActivityLimitPersistence.remove(socialActivityLimit);
		}
	}

	/**
	 * Returns the number of social activity limits where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching social activity limits
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCN_CP(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CN_CP,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALACTIVITYLIMIT_WHERE);

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
	 * Returns the number of social activity limits where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityKey = &#63; and statName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param activityKey the activity key
	 * @param statName the stat name
	 * @return the number of matching social activity limits
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U_CN_CP_A_S(long groupId, long userId,
		long classNameId, long classPK, int activityKey, String statName)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, userId, classNameId, classPK, activityKey, statName
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U_CN_CP_A_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_COUNT_SOCIALACTIVITYLIMIT_WHERE);

			query.append(_FINDER_COLUMN_G_U_CN_CP_A_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_CN_CP_A_S_USERID_2);

			query.append(_FINDER_COLUMN_G_U_CN_CP_A_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_U_CN_CP_A_S_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_U_CN_CP_A_S_ACTIVITYKEY_2);

			if (statName == null) {
				query.append(_FINDER_COLUMN_G_U_CN_CP_A_S_STATNAME_1);
			}
			else {
				if (statName.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_U_CN_CP_A_S_STATNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_U_CN_CP_A_S_STATNAME_2);
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

				qPos.add(activityKey);

				if (statName != null) {
					qPos.add(statName);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U_CN_CP_A_S,
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
	private static final String _SQL_SELECT_SOCIALACTIVITYLIMIT = "SELECT socialActivityLimit FROM SocialActivityLimit socialActivityLimit";
	private static final String _SQL_SELECT_SOCIALACTIVITYLIMIT_WHERE = "SELECT socialActivityLimit FROM SocialActivityLimit socialActivityLimit WHERE ";
	private static final String _SQL_COUNT_SOCIALACTIVITYLIMIT = "SELECT COUNT(socialActivityLimit) FROM SocialActivityLimit socialActivityLimit";
	private static final String _SQL_COUNT_SOCIALACTIVITYLIMIT_WHERE = "SELECT COUNT(socialActivityLimit) FROM SocialActivityLimit socialActivityLimit WHERE ";
	private static final String _FINDER_COLUMN_CN_CP_CLASSNAMEID_2 = "socialActivityLimit.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_CN_CP_CLASSPK_2 = "socialActivityLimit.classPK = ?";
	private static final String _FINDER_COLUMN_G_U_CN_CP_A_S_GROUPID_2 = "socialActivityLimit.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_CN_CP_A_S_USERID_2 = "socialActivityLimit.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_CN_CP_A_S_CLASSNAMEID_2 = "socialActivityLimit.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_CN_CP_A_S_CLASSPK_2 = "socialActivityLimit.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_U_CN_CP_A_S_ACTIVITYKEY_2 = "socialActivityLimit.activityKey = ? AND ";
	private static final String _FINDER_COLUMN_G_U_CN_CP_A_S_STATNAME_1 = "socialActivityLimit.statName IS NULL";
	private static final String _FINDER_COLUMN_G_U_CN_CP_A_S_STATNAME_2 = "socialActivityLimit.statName = ?";
	private static final String _FINDER_COLUMN_G_U_CN_CP_A_S_STATNAME_3 = "(socialActivityLimit.statName IS NULL OR socialActivityLimit.statName = ?)";
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