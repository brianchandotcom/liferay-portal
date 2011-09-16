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

import com.liferay.portlet.social.NoSuchEquityUserException;
import com.liferay.portlet.social.model.SocialEquityUser;
import com.liferay.portlet.social.model.impl.SocialEquityUserImpl;
import com.liferay.portlet.social.model.impl.SocialEquityUserModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the social equity user service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityUserPersistence
 * @see SocialEquityUserUtil
 * @generated
 */
public class SocialEquityUserPersistenceImpl extends BasePersistenceImpl<SocialEquityUser>
	implements SocialEquityUserPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SocialEquityUserUtil} to access the social equity user persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SocialEquityUserImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityUserImpl.class, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPRANKED = new FinderPath(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityUserImpl.class, FINDER_CLASS_NAME_LIST,
			"findByGroupRanked",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPRANKED = new FinderPath(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByGroupRanked",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityUserImpl.class, FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_RANK = new FinderPath(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityUserImpl.class, FINDER_CLASS_NAME_LIST, "findByRank",
			new String[] {
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_RANK = new FinderPath(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByRank",
			new String[] { Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_U = new FinderPath(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityUserImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_R = new FinderPath(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityUserImpl.class, FINDER_CLASS_NAME_LIST, "findByG_R",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_R = new FinderPath(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_R",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityUserImpl.class, FINDER_CLASS_NAME_LIST, "findAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the social equity user in the entity cache if it is enabled.
	 *
	 * @param socialEquityUser the social equity user
	 */
	public void cacheResult(SocialEquityUser socialEquityUser) {
		EntityCacheUtil.putResult(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserImpl.class, socialEquityUser.getPrimaryKey(),
			socialEquityUser);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
			new Object[] {
				Long.valueOf(socialEquityUser.getGroupId()),
				Long.valueOf(socialEquityUser.getUserId())
			}, socialEquityUser);

		socialEquityUser.resetOriginalValues();
	}

	/**
	 * Caches the social equity users in the entity cache if it is enabled.
	 *
	 * @param socialEquityUsers the social equity users
	 */
	public void cacheResult(List<SocialEquityUser> socialEquityUsers) {
		for (SocialEquityUser socialEquityUser : socialEquityUsers) {
			if (EntityCacheUtil.getResult(
						SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
						SocialEquityUserImpl.class,
						socialEquityUser.getPrimaryKey()) == null) {
				cacheResult(socialEquityUser);
			}
		}
	}

	/**
	 * Clears the cache for all social equity users.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SocialEquityUserImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SocialEquityUserImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the social equity user.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SocialEquityUser socialEquityUser) {
		EntityCacheUtil.removeResult(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserImpl.class, socialEquityUser.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL, FINDER_ARGS_EMPTY);

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U,
			new Object[] {
				Long.valueOf(socialEquityUser.getGroupId()),
				Long.valueOf(socialEquityUser.getUserId())
			});
	}

	/**
	 * Creates a new social equity user with the primary key. Does not add the social equity user to the database.
	 *
	 * @param equityUserId the primary key for the new social equity user
	 * @return the new social equity user
	 */
	public SocialEquityUser create(long equityUserId) {
		SocialEquityUser socialEquityUser = new SocialEquityUserImpl();

		socialEquityUser.setNew(true);
		socialEquityUser.setPrimaryKey(equityUserId);

		return socialEquityUser;
	}

	/**
	 * Removes the social equity user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the social equity user
	 * @return the social equity user that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a social equity user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialEquityUser remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the social equity user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param equityUserId the primary key of the social equity user
	 * @return the social equity user that was removed
	 * @throws com.liferay.portlet.social.NoSuchEquityUserException if a social equity user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityUser remove(long equityUserId)
		throws NoSuchEquityUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialEquityUser socialEquityUser = (SocialEquityUser)session.get(SocialEquityUserImpl.class,
					Long.valueOf(equityUserId));

			if (socialEquityUser == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + equityUserId);
				}

				throw new NoSuchEquityUserException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					equityUserId);
			}

			return socialEquityUserPersistence.remove(socialEquityUser);
		}
		catch (NoSuchEquityUserException nsee) {
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
	 * Removes the social equity user from the database. Also notifies the appropriate model listeners.
	 *
	 * @param socialEquityUser the social equity user
	 * @return the social equity user that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialEquityUser remove(SocialEquityUser socialEquityUser)
		throws SystemException {
		return super.remove(socialEquityUser);
	}

	@Override
	protected SocialEquityUser removeImpl(SocialEquityUser socialEquityUser)
		throws SystemException {
		socialEquityUser = toUnwrappedModel(socialEquityUser);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, socialEquityUser);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		SocialEquityUserModelImpl socialEquityUserModelImpl = (SocialEquityUserModelImpl)socialEquityUser;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U,
			new Object[] {
				Long.valueOf(socialEquityUserModelImpl.getGroupId()),
				Long.valueOf(socialEquityUserModelImpl.getUserId())
			});

		EntityCacheUtil.removeResult(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserImpl.class, socialEquityUser.getPrimaryKey());

		return socialEquityUser;
	}

	@Override
	public SocialEquityUser updateImpl(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser,
		boolean merge) throws SystemException {
		socialEquityUser = toUnwrappedModel(socialEquityUser);

		boolean isNew = socialEquityUser.isNew();

		SocialEquityUserModelImpl socialEquityUserModelImpl = (SocialEquityUserModelImpl)socialEquityUser;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, socialEquityUser, merge);

			socialEquityUser.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityUserImpl.class, socialEquityUser.getPrimaryKey(),
			socialEquityUser);

		if (!isNew &&
				((socialEquityUser.getGroupId() != socialEquityUserModelImpl.getOriginalGroupId()) ||
				(socialEquityUser.getUserId() != socialEquityUserModelImpl.getOriginalUserId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U,
				new Object[] {
					Long.valueOf(socialEquityUserModelImpl.getOriginalGroupId()),
					Long.valueOf(socialEquityUserModelImpl.getOriginalUserId())
				});
		}

		if (isNew ||
				((socialEquityUser.getGroupId() != socialEquityUserModelImpl.getOriginalGroupId()) ||
				(socialEquityUser.getUserId() != socialEquityUserModelImpl.getOriginalUserId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
				new Object[] {
					Long.valueOf(socialEquityUser.getGroupId()),
					Long.valueOf(socialEquityUser.getUserId())
				}, socialEquityUser);
		}

		return socialEquityUser;
	}

	protected SocialEquityUser toUnwrappedModel(
		SocialEquityUser socialEquityUser) {
		if (socialEquityUser instanceof SocialEquityUserImpl) {
			return socialEquityUser;
		}

		SocialEquityUserImpl socialEquityUserImpl = new SocialEquityUserImpl();

		socialEquityUserImpl.setNew(socialEquityUser.isNew());
		socialEquityUserImpl.setPrimaryKey(socialEquityUser.getPrimaryKey());

		socialEquityUserImpl.setEquityUserId(socialEquityUser.getEquityUserId());
		socialEquityUserImpl.setGroupId(socialEquityUser.getGroupId());
		socialEquityUserImpl.setCompanyId(socialEquityUser.getCompanyId());
		socialEquityUserImpl.setUserId(socialEquityUser.getUserId());
		socialEquityUserImpl.setContributionK(socialEquityUser.getContributionK());
		socialEquityUserImpl.setContributionB(socialEquityUser.getContributionB());
		socialEquityUserImpl.setParticipationK(socialEquityUser.getParticipationK());
		socialEquityUserImpl.setParticipationB(socialEquityUser.getParticipationB());
		socialEquityUserImpl.setRank(socialEquityUser.getRank());

		return socialEquityUserImpl;
	}

	/**
	 * Returns the social equity user with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the social equity user
	 * @return the social equity user
	 * @throws com.liferay.portal.NoSuchModelException if a social equity user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialEquityUser findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social equity user with the primary key or throws a {@link com.liferay.portlet.social.NoSuchEquityUserException} if it could not be found.
	 *
	 * @param equityUserId the primary key of the social equity user
	 * @return the social equity user
	 * @throws com.liferay.portlet.social.NoSuchEquityUserException if a social equity user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityUser findByPrimaryKey(long equityUserId)
		throws NoSuchEquityUserException, SystemException {
		SocialEquityUser socialEquityUser = fetchByPrimaryKey(equityUserId);

		if (socialEquityUser == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + equityUserId);
			}

			throw new NoSuchEquityUserException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				equityUserId);
		}

		return socialEquityUser;
	}

	/**
	 * Returns the social equity user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social equity user
	 * @return the social equity user, or <code>null</code> if a social equity user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialEquityUser fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social equity user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param equityUserId the primary key of the social equity user
	 * @return the social equity user, or <code>null</code> if a social equity user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityUser fetchByPrimaryKey(long equityUserId)
		throws SystemException {
		SocialEquityUser socialEquityUser = (SocialEquityUser)EntityCacheUtil.getResult(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
				SocialEquityUserImpl.class, equityUserId);

		if (socialEquityUser == _nullSocialEquityUser) {
			return null;
		}

		if (socialEquityUser == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				socialEquityUser = (SocialEquityUser)session.get(SocialEquityUserImpl.class,
						Long.valueOf(equityUserId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (socialEquityUser != null) {
					cacheResult(socialEquityUser);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(SocialEquityUserModelImpl.ENTITY_CACHE_ENABLED,
						SocialEquityUserImpl.class, equityUserId,
						_nullSocialEquityUser);
				}

				closeSession(session);
			}
		}

		return socialEquityUser;
	}

	/**
	 * Returns all the social equity users where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityUser> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social equity users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social equity users
	 * @param end the upper bound of the range of social equity users (not inclusive)
	 * @return the range of matching social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityUser> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social equity users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social equity users
	 * @param end the upper bound of the range of social equity users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityUser> findByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				start, end, orderByComparator
			};

		List<SocialEquityUser> list = (List<SocialEquityUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
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

			query.append(_SQL_SELECT_SOCIALEQUITYUSER_WHERE);

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

				list = (List<SocialEquityUser>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_GROUPID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first social equity user in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social equity user
	 * @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityUser findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchEquityUserException, SystemException {
		List<SocialEquityUser> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last social equity user in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social equity user
	 * @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityUser findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchEquityUserException, SystemException {
		int count = countByGroupId(groupId);

		List<SocialEquityUser> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the social equity users before and after the current social equity user in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param equityUserId the primary key of the current social equity user
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social equity user
	 * @throws com.liferay.portlet.social.NoSuchEquityUserException if a social equity user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityUser[] findByGroupId_PrevAndNext(long equityUserId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchEquityUserException, SystemException {
		SocialEquityUser socialEquityUser = findByPrimaryKey(equityUserId);

		Session session = null;

		try {
			session = openSession();

			SocialEquityUser[] array = new SocialEquityUserImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, socialEquityUser,
					groupId, orderByComparator, true);

			array[1] = socialEquityUser;

			array[2] = getByGroupId_PrevAndNext(session, socialEquityUser,
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

	protected SocialEquityUser getByGroupId_PrevAndNext(Session session,
		SocialEquityUser socialEquityUser, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALEQUITYUSER_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(socialEquityUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialEquityUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social equity users where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityUser> findByGroupRanked(long groupId)
		throws SystemException {
		return findByGroupRanked(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the social equity users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social equity users
	 * @param end the upper bound of the range of social equity users (not inclusive)
	 * @return the range of matching social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityUser> findByGroupRanked(long groupId, int start,
		int end) throws SystemException {
		return findByGroupRanked(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social equity users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social equity users
	 * @param end the upper bound of the range of social equity users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityUser> findByGroupRanked(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				start, end, orderByComparator
			};

		List<SocialEquityUser> list = (List<SocialEquityUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPRANKED,
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

			query.append(_SQL_SELECT_SOCIALEQUITYUSER_WHERE);

			query.append(_FINDER_COLUMN_GROUPRANKED_GROUPID_2);

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

				list = (List<SocialEquityUser>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_GROUPRANKED,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPRANKED,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first social equity user in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social equity user
	 * @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityUser findByGroupRanked_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchEquityUserException, SystemException {
		List<SocialEquityUser> list = findByGroupRanked(groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last social equity user in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social equity user
	 * @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityUser findByGroupRanked_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchEquityUserException, SystemException {
		int count = countByGroupRanked(groupId);

		List<SocialEquityUser> list = findByGroupRanked(groupId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the social equity users before and after the current social equity user in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param equityUserId the primary key of the current social equity user
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social equity user
	 * @throws com.liferay.portlet.social.NoSuchEquityUserException if a social equity user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityUser[] findByGroupRanked_PrevAndNext(long equityUserId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchEquityUserException, SystemException {
		SocialEquityUser socialEquityUser = findByPrimaryKey(equityUserId);

		Session session = null;

		try {
			session = openSession();

			SocialEquityUser[] array = new SocialEquityUserImpl[3];

			array[0] = getByGroupRanked_PrevAndNext(session, socialEquityUser,
					groupId, orderByComparator, true);

			array[1] = socialEquityUser;

			array[2] = getByGroupRanked_PrevAndNext(session, socialEquityUser,
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

	protected SocialEquityUser getByGroupRanked_PrevAndNext(Session session,
		SocialEquityUser socialEquityUser, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALEQUITYUSER_WHERE);

		query.append(_FINDER_COLUMN_GROUPRANKED_GROUPID_2);

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

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(socialEquityUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialEquityUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social equity users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityUser> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social equity users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of social equity users
	 * @param end the upper bound of the range of social equity users (not inclusive)
	 * @return the range of matching social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityUser> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social equity users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of social equity users
	 * @param end the upper bound of the range of social equity users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityUser> findByUserId(long userId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { userId, start, end, orderByComparator };

		List<SocialEquityUser> list = (List<SocialEquityUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
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

			query.append(_SQL_SELECT_SOCIALEQUITYUSER_WHERE);

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

				list = (List<SocialEquityUser>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_USERID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first social equity user in the ordered set where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social equity user
	 * @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityUser findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchEquityUserException, SystemException {
		List<SocialEquityUser> list = findByUserId(userId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last social equity user in the ordered set where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social equity user
	 * @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityUser findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchEquityUserException, SystemException {
		int count = countByUserId(userId);

		List<SocialEquityUser> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the social equity users before and after the current social equity user in the ordered set where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param equityUserId the primary key of the current social equity user
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social equity user
	 * @throws com.liferay.portlet.social.NoSuchEquityUserException if a social equity user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityUser[] findByUserId_PrevAndNext(long equityUserId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchEquityUserException, SystemException {
		SocialEquityUser socialEquityUser = findByPrimaryKey(equityUserId);

		Session session = null;

		try {
			session = openSession();

			SocialEquityUser[] array = new SocialEquityUserImpl[3];

			array[0] = getByUserId_PrevAndNext(session, socialEquityUser,
					userId, orderByComparator, true);

			array[1] = socialEquityUser;

			array[2] = getByUserId_PrevAndNext(session, socialEquityUser,
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

	protected SocialEquityUser getByUserId_PrevAndNext(Session session,
		SocialEquityUser socialEquityUser, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALEQUITYUSER_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(socialEquityUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialEquityUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social equity users where rank = &#63;.
	 *
	 * @param rank the rank
	 * @return the matching social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityUser> findByRank(int rank)
		throws SystemException {
		return findByRank(rank, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social equity users where rank = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param rank the rank
	 * @param start the lower bound of the range of social equity users
	 * @param end the upper bound of the range of social equity users (not inclusive)
	 * @return the range of matching social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityUser> findByRank(int rank, int start, int end)
		throws SystemException {
		return findByRank(rank, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social equity users where rank = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param rank the rank
	 * @param start the lower bound of the range of social equity users
	 * @param end the upper bound of the range of social equity users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityUser> findByRank(int rank, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { rank, start, end, orderByComparator };

		List<SocialEquityUser> list = (List<SocialEquityUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_RANK,
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

			query.append(_SQL_SELECT_SOCIALEQUITYUSER_WHERE);

			query.append(_FINDER_COLUMN_RANK_RANK_2);

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

				qPos.add(rank);

				list = (List<SocialEquityUser>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_RANK,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_RANK,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first social equity user in the ordered set where rank = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param rank the rank
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social equity user
	 * @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityUser findByRank_First(int rank,
		OrderByComparator orderByComparator)
		throws NoSuchEquityUserException, SystemException {
		List<SocialEquityUser> list = findByRank(rank, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("rank=");
			msg.append(rank);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last social equity user in the ordered set where rank = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param rank the rank
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social equity user
	 * @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityUser findByRank_Last(int rank,
		OrderByComparator orderByComparator)
		throws NoSuchEquityUserException, SystemException {
		int count = countByRank(rank);

		List<SocialEquityUser> list = findByRank(rank, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("rank=");
			msg.append(rank);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the social equity users before and after the current social equity user in the ordered set where rank = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param equityUserId the primary key of the current social equity user
	 * @param rank the rank
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social equity user
	 * @throws com.liferay.portlet.social.NoSuchEquityUserException if a social equity user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityUser[] findByRank_PrevAndNext(long equityUserId,
		int rank, OrderByComparator orderByComparator)
		throws NoSuchEquityUserException, SystemException {
		SocialEquityUser socialEquityUser = findByPrimaryKey(equityUserId);

		Session session = null;

		try {
			session = openSession();

			SocialEquityUser[] array = new SocialEquityUserImpl[3];

			array[0] = getByRank_PrevAndNext(session, socialEquityUser, rank,
					orderByComparator, true);

			array[1] = socialEquityUser;

			array[2] = getByRank_PrevAndNext(session, socialEquityUser, rank,
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

	protected SocialEquityUser getByRank_PrevAndNext(Session session,
		SocialEquityUser socialEquityUser, int rank,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALEQUITYUSER_WHERE);

		query.append(_FINDER_COLUMN_RANK_RANK_2);

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

		qPos.add(rank);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(socialEquityUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialEquityUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the social equity user where groupId = &#63; and userId = &#63; or throws a {@link com.liferay.portlet.social.NoSuchEquityUserException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching social equity user
	 * @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityUser findByG_U(long groupId, long userId)
		throws NoSuchEquityUserException, SystemException {
		SocialEquityUser socialEquityUser = fetchByG_U(groupId, userId);

		if (socialEquityUser == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEquityUserException(msg.toString());
		}

		return socialEquityUser;
	}

	/**
	 * Returns the social equity user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching social equity user, or <code>null</code> if a matching social equity user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityUser fetchByG_U(long groupId, long userId)
		throws SystemException {
		return fetchByG_U(groupId, userId, true);
	}

	/**
	 * Returns the social equity user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching social equity user, or <code>null</code> if a matching social equity user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityUser fetchByG_U(long groupId, long userId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_U,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_SOCIALEQUITYUSER_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				List<SocialEquityUser> list = q.list();

				result = list;

				SocialEquityUser socialEquityUser = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
						finderArgs, list);
				}
				else {
					socialEquityUser = list.get(0);

					cacheResult(socialEquityUser);

					if ((socialEquityUser.getGroupId() != groupId) ||
							(socialEquityUser.getUserId() != userId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
							finderArgs, socialEquityUser);
					}
				}

				return socialEquityUser;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U,
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
				return (SocialEquityUser)result;
			}
		}
	}

	/**
	 * Returns all the social equity users where groupId = &#63; and rank = &#63;.
	 *
	 * @param groupId the group ID
	 * @param rank the rank
	 * @return the matching social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityUser> findByG_R(long groupId, int rank)
		throws SystemException {
		return findByG_R(groupId, rank, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the social equity users where groupId = &#63; and rank = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param rank the rank
	 * @param start the lower bound of the range of social equity users
	 * @param end the upper bound of the range of social equity users (not inclusive)
	 * @return the range of matching social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityUser> findByG_R(long groupId, int rank, int start,
		int end) throws SystemException {
		return findByG_R(groupId, rank, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social equity users where groupId = &#63; and rank = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param rank the rank
	 * @param start the lower bound of the range of social equity users
	 * @param end the upper bound of the range of social equity users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityUser> findByG_R(long groupId, int rank, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, rank,
				
				start, end, orderByComparator
			};

		List<SocialEquityUser> list = (List<SocialEquityUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_R,
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

			query.append(_SQL_SELECT_SOCIALEQUITYUSER_WHERE);

			query.append(_FINDER_COLUMN_G_R_GROUPID_2);

			query.append(_FINDER_COLUMN_G_R_RANK_2);

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

				qPos.add(rank);

				list = (List<SocialEquityUser>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_R,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_R,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first social equity user in the ordered set where groupId = &#63; and rank = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param rank the rank
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social equity user
	 * @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityUser findByG_R_First(long groupId, int rank,
		OrderByComparator orderByComparator)
		throws NoSuchEquityUserException, SystemException {
		List<SocialEquityUser> list = findByG_R(groupId, rank, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", rank=");
			msg.append(rank);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last social equity user in the ordered set where groupId = &#63; and rank = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param rank the rank
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social equity user
	 * @throws com.liferay.portlet.social.NoSuchEquityUserException if a matching social equity user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityUser findByG_R_Last(long groupId, int rank,
		OrderByComparator orderByComparator)
		throws NoSuchEquityUserException, SystemException {
		int count = countByG_R(groupId, rank);

		List<SocialEquityUser> list = findByG_R(groupId, rank, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", rank=");
			msg.append(rank);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquityUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the social equity users before and after the current social equity user in the ordered set where groupId = &#63; and rank = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param equityUserId the primary key of the current social equity user
	 * @param groupId the group ID
	 * @param rank the rank
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social equity user
	 * @throws com.liferay.portlet.social.NoSuchEquityUserException if a social equity user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityUser[] findByG_R_PrevAndNext(long equityUserId,
		long groupId, int rank, OrderByComparator orderByComparator)
		throws NoSuchEquityUserException, SystemException {
		SocialEquityUser socialEquityUser = findByPrimaryKey(equityUserId);

		Session session = null;

		try {
			session = openSession();

			SocialEquityUser[] array = new SocialEquityUserImpl[3];

			array[0] = getByG_R_PrevAndNext(session, socialEquityUser, groupId,
					rank, orderByComparator, true);

			array[1] = socialEquityUser;

			array[2] = getByG_R_PrevAndNext(session, socialEquityUser, groupId,
					rank, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialEquityUser getByG_R_PrevAndNext(Session session,
		SocialEquityUser socialEquityUser, long groupId, int rank,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALEQUITYUSER_WHERE);

		query.append(_FINDER_COLUMN_G_R_GROUPID_2);

		query.append(_FINDER_COLUMN_G_R_RANK_2);

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

		qPos.add(rank);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(socialEquityUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialEquityUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the social equity users.
	 *
	 * @return the social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityUser> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social equity users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social equity users
	 * @param end the upper bound of the range of social equity users (not inclusive)
	 * @return the range of social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityUser> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the social equity users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social equity users
	 * @param end the upper bound of the range of social equity users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityUser> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		List<SocialEquityUser> list = (List<SocialEquityUser>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SOCIALEQUITYUSER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SOCIALEQUITYUSER;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SocialEquityUser>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialEquityUser>)QueryUtil.list(q,
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
	 * Removes all the social equity users where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (SocialEquityUser socialEquityUser : findByGroupId(groupId)) {
			socialEquityUserPersistence.remove(socialEquityUser);
		}
	}

	/**
	 * Removes all the social equity users where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupRanked(long groupId) throws SystemException {
		for (SocialEquityUser socialEquityUser : findByGroupRanked(groupId)) {
			socialEquityUserPersistence.remove(socialEquityUser);
		}
	}

	/**
	 * Removes all the social equity users where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (SocialEquityUser socialEquityUser : findByUserId(userId)) {
			socialEquityUserPersistence.remove(socialEquityUser);
		}
	}

	/**
	 * Removes all the social equity users where rank = &#63; from the database.
	 *
	 * @param rank the rank
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByRank(int rank) throws SystemException {
		for (SocialEquityUser socialEquityUser : findByRank(rank)) {
			socialEquityUserPersistence.remove(socialEquityUser);
		}
	}

	/**
	 * Removes the social equity user where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_U(long groupId, long userId)
		throws NoSuchEquityUserException, SystemException {
		SocialEquityUser socialEquityUser = findByG_U(groupId, userId);

		socialEquityUserPersistence.remove(socialEquityUser);
	}

	/**
	 * Removes all the social equity users where groupId = &#63; and rank = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param rank the rank
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_R(long groupId, int rank) throws SystemException {
		for (SocialEquityUser socialEquityUser : findByG_R(groupId, rank)) {
			socialEquityUserPersistence.remove(socialEquityUser);
		}
	}

	/**
	 * Removes all the social equity users from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SocialEquityUser socialEquityUser : findAll()) {
			socialEquityUserPersistence.remove(socialEquityUser);
		}
	}

	/**
	 * Returns the number of social equity users where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALEQUITYUSER_WHERE);

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
	 * Returns the number of social equity users where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupRanked(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPRANKED,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALEQUITYUSER_WHERE);

			query.append(_FINDER_COLUMN_GROUPRANKED_GROUPID_2);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPRANKED,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social equity users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALEQUITYUSER_WHERE);

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
	 * Returns the number of social equity users where rank = &#63;.
	 *
	 * @param rank the rank
	 * @return the number of matching social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByRank(int rank) throws SystemException {
		Object[] finderArgs = new Object[] { rank };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_RANK,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALEQUITYUSER_WHERE);

			query.append(_FINDER_COLUMN_RANK_RANK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(rank);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_RANK,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social equity users where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALEQUITYUSER_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social equity users where groupId = &#63; and rank = &#63;.
	 *
	 * @param groupId the group ID
	 * @param rank the rank
	 * @return the number of matching social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_R(long groupId, int rank) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, rank };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_R,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALEQUITYUSER_WHERE);

			query.append(_FINDER_COLUMN_G_R_GROUPID_2);

			query.append(_FINDER_COLUMN_G_R_RANK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(rank);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_R, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social equity users.
	 *
	 * @return the number of social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SOCIALEQUITYUSER);

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
	 * Initializes the social equity user persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.social.model.SocialEquityUser")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialEquityUser>> listenersList = new ArrayList<ModelListener<SocialEquityUser>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialEquityUser>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(SocialEquityUserImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
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
	private static final String _SQL_SELECT_SOCIALEQUITYUSER = "SELECT socialEquityUser FROM SocialEquityUser socialEquityUser";
	private static final String _SQL_SELECT_SOCIALEQUITYUSER_WHERE = "SELECT socialEquityUser FROM SocialEquityUser socialEquityUser WHERE ";
	private static final String _SQL_COUNT_SOCIALEQUITYUSER = "SELECT COUNT(socialEquityUser) FROM SocialEquityUser socialEquityUser";
	private static final String _SQL_COUNT_SOCIALEQUITYUSER_WHERE = "SELECT COUNT(socialEquityUser) FROM SocialEquityUser socialEquityUser WHERE ";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "socialEquityUser.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPRANKED_GROUPID_2 = "socialEquityUser.groupId = ? AND socialEquityUser.rank > 0";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "socialEquityUser.userId = ?";
	private static final String _FINDER_COLUMN_RANK_RANK_2 = "socialEquityUser.rank = ?";
	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "socialEquityUser.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "socialEquityUser.userId = ?";
	private static final String _FINDER_COLUMN_G_R_GROUPID_2 = "socialEquityUser.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_R_RANK_2 = "socialEquityUser.rank = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialEquityUser.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialEquityUser exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SocialEquityUser exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(SocialEquityUserPersistenceImpl.class);
	private static SocialEquityUser _nullSocialEquityUser = new SocialEquityUserImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SocialEquityUser> toCacheModel() {
				return _nullSocialEquityUserCacheModel;
			}
		};

	private static CacheModel<SocialEquityUser> _nullSocialEquityUserCacheModel = new CacheModel<SocialEquityUser>() {
			public SocialEquityUser toEntityModel() {
				return _nullSocialEquityUser;
			}
		};
}