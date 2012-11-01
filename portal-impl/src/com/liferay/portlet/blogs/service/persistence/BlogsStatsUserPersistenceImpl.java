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

package com.liferay.portlet.blogs.service.persistence;

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
import com.liferay.portal.kernel.util.CalendarUtil;
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

import com.liferay.portlet.blogs.NoSuchStatsUserException;
import com.liferay.portlet.blogs.model.BlogsStatsUser;
import com.liferay.portlet.blogs.model.impl.BlogsStatsUserImpl;
import com.liferay.portlet.blogs.model.impl.BlogsStatsUserModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * The persistence implementation for the blogs stats user service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BlogsStatsUserPersistence
 * @see BlogsStatsUserUtil
 * @generated
 */
public class BlogsStatsUserPersistenceImpl extends BasePersistenceImpl<BlogsStatsUser>
	implements BlogsStatsUserPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link BlogsStatsUserUtil} to access the blogs stats user persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = BlogsStatsUserImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			BlogsStatsUserModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			BlogsStatsUserModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_U = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_U",
			new String[] { Long.class.getName(), Long.class.getName() },
			BlogsStatsUserModelImpl.GROUPID_COLUMN_BITMASK |
			BlogsStatsUserModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_NOTE = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_NotE",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_NOTE = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_NotE",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_NOTE = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_NotE",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_NOTE = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_NotE",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_L = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByU_L",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_L = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_L",
			new String[] { Long.class.getName(), Date.class.getName() },
			BlogsStatsUserModelImpl.USERID_COLUMN_BITMASK |
			BlogsStatsUserModelImpl.LASTPOSTDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_L = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_L",
			new String[] { Long.class.getName(), Date.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the blogs stats user in the entity cache if it is enabled.
	 *
	 * @param blogsStatsUser the blogs stats user
	 */
	public void cacheResult(BlogsStatsUser blogsStatsUser) {
		EntityCacheUtil.putResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserImpl.class, blogsStatsUser.getPrimaryKey(),
			blogsStatsUser);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
			new Object[] {
				Long.valueOf(blogsStatsUser.getGroupId()),
				Long.valueOf(blogsStatsUser.getUserId())
			}, blogsStatsUser);

		blogsStatsUser.resetOriginalValues();
	}

	/**
	 * Caches the blogs stats users in the entity cache if it is enabled.
	 *
	 * @param blogsStatsUsers the blogs stats users
	 */
	public void cacheResult(List<BlogsStatsUser> blogsStatsUsers) {
		for (BlogsStatsUser blogsStatsUser : blogsStatsUsers) {
			if (EntityCacheUtil.getResult(
						BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
						BlogsStatsUserImpl.class, blogsStatsUser.getPrimaryKey()) == null) {
				cacheResult(blogsStatsUser);
			}
			else {
				blogsStatsUser.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all blogs stats users.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(BlogsStatsUserImpl.class.getName());
		}

		EntityCacheUtil.clearCache(BlogsStatsUserImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the blogs stats user.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BlogsStatsUser blogsStatsUser) {
		EntityCacheUtil.removeResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserImpl.class, blogsStatsUser.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(blogsStatsUser);
	}

	@Override
	public void clearCache(List<BlogsStatsUser> blogsStatsUsers) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (BlogsStatsUser blogsStatsUser : blogsStatsUsers) {
			EntityCacheUtil.removeResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
				BlogsStatsUserImpl.class, blogsStatsUser.getPrimaryKey());

			clearUniqueFindersCache(blogsStatsUser);
		}
	}

	protected void clearUniqueFindersCache(BlogsStatsUser blogsStatsUser) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U,
			new Object[] {
				Long.valueOf(blogsStatsUser.getGroupId()),
				Long.valueOf(blogsStatsUser.getUserId())
			});
	}

	/**
	 * Creates a new blogs stats user with the primary key. Does not add the blogs stats user to the database.
	 *
	 * @param statsUserId the primary key for the new blogs stats user
	 * @return the new blogs stats user
	 */
	public BlogsStatsUser create(long statsUserId) {
		BlogsStatsUser blogsStatsUser = new BlogsStatsUserImpl();

		blogsStatsUser.setNew(true);
		blogsStatsUser.setPrimaryKey(statsUserId);

		return blogsStatsUser;
	}

	/**
	 * Removes the blogs stats user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param statsUserId the primary key of the blogs stats user
	 * @return the blogs stats user that was removed
	 * @throws com.liferay.portlet.blogs.NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser remove(long statsUserId)
		throws NoSuchStatsUserException, SystemException {
		return remove(Long.valueOf(statsUserId));
	}

	/**
	 * Removes the blogs stats user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the blogs stats user
	 * @return the blogs stats user that was removed
	 * @throws com.liferay.portlet.blogs.NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BlogsStatsUser remove(Serializable primaryKey)
		throws NoSuchStatsUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser blogsStatsUser = (BlogsStatsUser)session.get(BlogsStatsUserImpl.class,
					primaryKey);

			if (blogsStatsUser == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStatsUserException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(blogsStatsUser);
		}
		catch (NoSuchStatsUserException nsee) {
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
	protected BlogsStatsUser removeImpl(BlogsStatsUser blogsStatsUser)
		throws SystemException {
		blogsStatsUser = toUnwrappedModel(blogsStatsUser);

		Session session = null;

		try {
			session = openSession();

			if (blogsStatsUser.isCachedModel()) {
				blogsStatsUser = (BlogsStatsUser)session.get(BlogsStatsUserImpl.class,
						blogsStatsUser.getPrimaryKeyObj());
			}

			if (blogsStatsUser != null) {
				session.delete(blogsStatsUser);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (blogsStatsUser != null) {
			clearCache(blogsStatsUser);
		}

		return blogsStatsUser;
	}

	@Override
	public BlogsStatsUser updateImpl(
		com.liferay.portlet.blogs.model.BlogsStatsUser blogsStatsUser)
		throws SystemException {
		blogsStatsUser = toUnwrappedModel(blogsStatsUser);

		boolean isNew = blogsStatsUser.isNew();

		BlogsStatsUserModelImpl blogsStatsUserModelImpl = (BlogsStatsUserModelImpl)blogsStatsUser;

		Session session = null;

		try {
			session = openSession();

			if (blogsStatsUser.isNew()) {
				session.save(blogsStatsUser);

				blogsStatsUser.setNew(false);
			}
			else {
				session.merge(blogsStatsUser);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !BlogsStatsUserModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((blogsStatsUserModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(blogsStatsUserModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(blogsStatsUserModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((blogsStatsUserModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(blogsStatsUserModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] {
						Long.valueOf(blogsStatsUserModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((blogsStatsUserModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_L.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(blogsStatsUserModelImpl.getOriginalUserId()),
						
						blogsStatsUserModelImpl.getOriginalLastPostDate()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_L, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_L,
					args);

				args = new Object[] {
						Long.valueOf(blogsStatsUserModelImpl.getUserId()),
						
						blogsStatsUserModelImpl.getLastPostDate()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_L, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_L,
					args);
			}
		}

		EntityCacheUtil.putResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserImpl.class, blogsStatsUser.getPrimaryKey(),
			blogsStatsUser);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
				new Object[] {
					Long.valueOf(blogsStatsUser.getGroupId()),
					Long.valueOf(blogsStatsUser.getUserId())
				}, blogsStatsUser);
		}
		else {
			if ((blogsStatsUserModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(blogsStatsUserModelImpl.getOriginalGroupId()),
						Long.valueOf(blogsStatsUserModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
					new Object[] {
						Long.valueOf(blogsStatsUser.getGroupId()),
						Long.valueOf(blogsStatsUser.getUserId())
					}, blogsStatsUser);
			}
		}

		return blogsStatsUser;
	}

	protected BlogsStatsUser toUnwrappedModel(BlogsStatsUser blogsStatsUser) {
		if (blogsStatsUser instanceof BlogsStatsUserImpl) {
			return blogsStatsUser;
		}

		BlogsStatsUserImpl blogsStatsUserImpl = new BlogsStatsUserImpl();

		blogsStatsUserImpl.setNew(blogsStatsUser.isNew());
		blogsStatsUserImpl.setPrimaryKey(blogsStatsUser.getPrimaryKey());

		blogsStatsUserImpl.setStatsUserId(blogsStatsUser.getStatsUserId());
		blogsStatsUserImpl.setGroupId(blogsStatsUser.getGroupId());
		blogsStatsUserImpl.setCompanyId(blogsStatsUser.getCompanyId());
		blogsStatsUserImpl.setUserId(blogsStatsUser.getUserId());
		blogsStatsUserImpl.setEntryCount(blogsStatsUser.getEntryCount());
		blogsStatsUserImpl.setLastPostDate(blogsStatsUser.getLastPostDate());
		blogsStatsUserImpl.setRatingsTotalEntries(blogsStatsUser.getRatingsTotalEntries());
		blogsStatsUserImpl.setRatingsTotalScore(blogsStatsUser.getRatingsTotalScore());
		blogsStatsUserImpl.setRatingsAverageScore(blogsStatsUser.getRatingsAverageScore());

		return blogsStatsUserImpl;
	}

	/**
	 * Returns the blogs stats user with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the blogs stats user
	 * @return the blogs stats user
	 * @throws com.liferay.portal.NoSuchModelException if a blogs stats user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BlogsStatsUser findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the blogs stats user with the primary key or throws a {@link com.liferay.portlet.blogs.NoSuchStatsUserException} if it could not be found.
	 *
	 * @param statsUserId the primary key of the blogs stats user
	 * @return the blogs stats user
	 * @throws com.liferay.portlet.blogs.NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser findByPrimaryKey(long statsUserId)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = fetchByPrimaryKey(statsUserId);

		if (blogsStatsUser == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + statsUserId);
			}

			throw new NoSuchStatsUserException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				statsUserId);
		}

		return blogsStatsUser;
	}

	/**
	 * Returns the blogs stats user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the blogs stats user
	 * @return the blogs stats user, or <code>null</code> if a blogs stats user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BlogsStatsUser fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the blogs stats user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param statsUserId the primary key of the blogs stats user
	 * @return the blogs stats user, or <code>null</code> if a blogs stats user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser fetchByPrimaryKey(long statsUserId)
		throws SystemException {
		BlogsStatsUser blogsStatsUser = (BlogsStatsUser)EntityCacheUtil.getResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
				BlogsStatsUserImpl.class, statsUserId);

		if (blogsStatsUser == _nullBlogsStatsUser) {
			return null;
		}

		if (blogsStatsUser == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				blogsStatsUser = (BlogsStatsUser)session.get(BlogsStatsUserImpl.class,
						Long.valueOf(statsUserId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (blogsStatsUser != null) {
					cacheResult(blogsStatsUser);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
						BlogsStatsUserImpl.class, statsUserId,
						_nullBlogsStatsUser);
				}

				closeSession(session);
			}
		}

		return blogsStatsUser;
	}

	/**
	 * Returns all the blogs stats users where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsStatsUser> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs stats users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @return the range of matching blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsStatsUser> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsStatsUser> findByGroupId(long groupId, int start, int end,
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

		List<BlogsStatsUser> list = (List<BlogsStatsUser>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsStatsUser blogsStatsUser : list) {
				if ((groupId != blogsStatsUser.getGroupId())) {
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

			query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<BlogsStatsUser>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first blogs stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user
	 * @throws com.liferay.portlet.blogs.NoSuchStatsUserException if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = fetchByGroupId_First(groupId,
				orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the first blogs stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<BlogsStatsUser> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user
	 * @throws com.liferay.portlet.blogs.NoSuchStatsUserException if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the last blogs stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<BlogsStatsUser> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs stats users before and after the current blogs stats user in the ordered set where groupId = &#63;.
	 *
	 * @param statsUserId the primary key of the current blogs stats user
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs stats user
	 * @throws com.liferay.portlet.blogs.NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser[] findByGroupId_PrevAndNext(long statsUserId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, blogsStatsUser,
					groupId, orderByComparator, true);

			array[1] = blogsStatsUser;

			array[2] = getByGroupId_PrevAndNext(session, blogsStatsUser,
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

	protected BlogsStatsUser getByGroupId_PrevAndNext(Session session,
		BlogsStatsUser blogsStatsUser, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

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
			query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsStatsUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsStatsUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the blogs stats users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsStatsUser> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs stats users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @return the range of matching blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsStatsUser> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsStatsUser> findByUserId(long userId, int start, int end,
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

		List<BlogsStatsUser> list = (List<BlogsStatsUser>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsStatsUser blogsStatsUser : list) {
				if ((userId != blogsStatsUser.getUserId())) {
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

			query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<BlogsStatsUser>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first blogs stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user
	 * @throws com.liferay.portlet.blogs.NoSuchStatsUserException if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = fetchByUserId_First(userId,
				orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the first blogs stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<BlogsStatsUser> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user
	 * @throws com.liferay.portlet.blogs.NoSuchStatsUserException if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = fetchByUserId_Last(userId,
				orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the last blogs stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		List<BlogsStatsUser> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs stats users before and after the current blogs stats user in the ordered set where userId = &#63;.
	 *
	 * @param statsUserId the primary key of the current blogs stats user
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs stats user
	 * @throws com.liferay.portlet.blogs.NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser[] findByUserId_PrevAndNext(long statsUserId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = getByUserId_PrevAndNext(session, blogsStatsUser, userId,
					orderByComparator, true);

			array[1] = blogsStatsUser;

			array[2] = getByUserId_PrevAndNext(session, blogsStatsUser, userId,
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

	protected BlogsStatsUser getByUserId_PrevAndNext(Session session,
		BlogsStatsUser blogsStatsUser, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

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

		else {
			query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsStatsUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsStatsUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the blogs stats user where groupId = &#63; and userId = &#63; or throws a {@link com.liferay.portlet.blogs.NoSuchStatsUserException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching blogs stats user
	 * @throws com.liferay.portlet.blogs.NoSuchStatsUserException if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser findByG_U(long groupId, long userId)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = fetchByG_U(groupId, userId);

		if (blogsStatsUser == null) {
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

			throw new NoSuchStatsUserException(msg.toString());
		}

		return blogsStatsUser;
	}

	/**
	 * Returns the blogs stats user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser fetchByG_U(long groupId, long userId)
		throws SystemException {
		return fetchByG_U(groupId, userId, true);
	}

	/**
	 * Returns the blogs stats user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser fetchByG_U(long groupId, long userId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_U,
					finderArgs, this);
		}

		if (result instanceof BlogsStatsUser) {
			BlogsStatsUser blogsStatsUser = (BlogsStatsUser)result;

			if ((groupId != blogsStatsUser.getGroupId()) ||
					(userId != blogsStatsUser.getUserId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				List<BlogsStatsUser> list = q.list();

				result = list;

				BlogsStatsUser blogsStatsUser = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
						finderArgs, list);
				}
				else {
					blogsStatsUser = list.get(0);

					cacheResult(blogsStatsUser);

					if ((blogsStatsUser.getGroupId() != groupId) ||
							(blogsStatsUser.getUserId() != userId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
							finderArgs, blogsStatsUser);
					}
				}

				return blogsStatsUser;
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
				return (BlogsStatsUser)result;
			}
		}
	}

	/**
	 * Returns all the blogs stats users where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @return the matching blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsStatsUser> findByG_NotE(long groupId, int entryCount)
		throws SystemException {
		return findByG_NotE(groupId, entryCount, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs stats users where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @return the range of matching blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsStatsUser> findByG_NotE(long groupId, int entryCount,
		int start, int end) throws SystemException {
		return findByG_NotE(groupId, entryCount, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsStatsUser> findByG_NotE(long groupId, int entryCount,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_NOTE;
		finderArgs = new Object[] {
				groupId, entryCount,
				
				start, end, orderByComparator
			};

		List<BlogsStatsUser> list = (List<BlogsStatsUser>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsStatsUser blogsStatsUser : list) {
				if ((groupId != blogsStatsUser.getGroupId()) ||
						(entryCount != blogsStatsUser.getEntryCount())) {
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

			query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_G_NOTE_GROUPID_2);

			query.append(_FINDER_COLUMN_G_NOTE_ENTRYCOUNT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(entryCount);

				list = (List<BlogsStatsUser>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first blogs stats user in the ordered set where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user
	 * @throws com.liferay.portlet.blogs.NoSuchStatsUserException if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser findByG_NotE_First(long groupId, int entryCount,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = fetchByG_NotE_First(groupId,
				entryCount, orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", entryCount=");
		msg.append(entryCount);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the first blogs stats user in the ordered set where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser fetchByG_NotE_First(long groupId, int entryCount,
		OrderByComparator orderByComparator) throws SystemException {
		List<BlogsStatsUser> list = findByG_NotE(groupId, entryCount, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs stats user in the ordered set where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user
	 * @throws com.liferay.portlet.blogs.NoSuchStatsUserException if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser findByG_NotE_Last(long groupId, int entryCount,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = fetchByG_NotE_Last(groupId, entryCount,
				orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", entryCount=");
		msg.append(entryCount);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the last blogs stats user in the ordered set where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser fetchByG_NotE_Last(long groupId, int entryCount,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_NotE(groupId, entryCount);

		List<BlogsStatsUser> list = findByG_NotE(groupId, entryCount,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs stats users before and after the current blogs stats user in the ordered set where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param statsUserId the primary key of the current blogs stats user
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs stats user
	 * @throws com.liferay.portlet.blogs.NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser[] findByG_NotE_PrevAndNext(long statsUserId,
		long groupId, int entryCount, OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = getByG_NotE_PrevAndNext(session, blogsStatsUser,
					groupId, entryCount, orderByComparator, true);

			array[1] = blogsStatsUser;

			array[2] = getByG_NotE_PrevAndNext(session, blogsStatsUser,
					groupId, entryCount, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsStatsUser getByG_NotE_PrevAndNext(Session session,
		BlogsStatsUser blogsStatsUser, long groupId, int entryCount,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

		query.append(_FINDER_COLUMN_G_NOTE_GROUPID_2);

		query.append(_FINDER_COLUMN_G_NOTE_ENTRYCOUNT_2);

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
			query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(entryCount);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsStatsUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsStatsUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the blogs stats users where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @return the matching blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsStatsUser> findByC_NotE(long companyId, int entryCount)
		throws SystemException {
		return findByC_NotE(companyId, entryCount, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs stats users where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @return the range of matching blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsStatsUser> findByC_NotE(long companyId, int entryCount,
		int start, int end) throws SystemException {
		return findByC_NotE(companyId, entryCount, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsStatsUser> findByC_NotE(long companyId, int entryCount,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_NOTE;
		finderArgs = new Object[] {
				companyId, entryCount,
				
				start, end, orderByComparator
			};

		List<BlogsStatsUser> list = (List<BlogsStatsUser>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsStatsUser blogsStatsUser : list) {
				if ((companyId != blogsStatsUser.getCompanyId()) ||
						(entryCount != blogsStatsUser.getEntryCount())) {
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

			query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_C_NOTE_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_NOTE_ENTRYCOUNT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(entryCount);

				list = (List<BlogsStatsUser>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first blogs stats user in the ordered set where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user
	 * @throws com.liferay.portlet.blogs.NoSuchStatsUserException if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser findByC_NotE_First(long companyId, int entryCount,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = fetchByC_NotE_First(companyId,
				entryCount, orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", entryCount=");
		msg.append(entryCount);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the first blogs stats user in the ordered set where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser fetchByC_NotE_First(long companyId, int entryCount,
		OrderByComparator orderByComparator) throws SystemException {
		List<BlogsStatsUser> list = findByC_NotE(companyId, entryCount, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs stats user in the ordered set where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user
	 * @throws com.liferay.portlet.blogs.NoSuchStatsUserException if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser findByC_NotE_Last(long companyId, int entryCount,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = fetchByC_NotE_Last(companyId,
				entryCount, orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", entryCount=");
		msg.append(entryCount);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the last blogs stats user in the ordered set where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser fetchByC_NotE_Last(long companyId, int entryCount,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_NotE(companyId, entryCount);

		List<BlogsStatsUser> list = findByC_NotE(companyId, entryCount,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs stats users before and after the current blogs stats user in the ordered set where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param statsUserId the primary key of the current blogs stats user
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs stats user
	 * @throws com.liferay.portlet.blogs.NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser[] findByC_NotE_PrevAndNext(long statsUserId,
		long companyId, int entryCount, OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = getByC_NotE_PrevAndNext(session, blogsStatsUser,
					companyId, entryCount, orderByComparator, true);

			array[1] = blogsStatsUser;

			array[2] = getByC_NotE_PrevAndNext(session, blogsStatsUser,
					companyId, entryCount, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsStatsUser getByC_NotE_PrevAndNext(Session session,
		BlogsStatsUser blogsStatsUser, long companyId, int entryCount,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

		query.append(_FINDER_COLUMN_C_NOTE_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_NOTE_ENTRYCOUNT_2);

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
			query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(entryCount);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsStatsUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsStatsUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the blogs stats users where userId = &#63; and lastPostDate = &#63;.
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @return the matching blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsStatsUser> findByU_L(long userId, Date lastPostDate)
		throws SystemException {
		return findByU_L(userId, lastPostDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs stats users where userId = &#63; and lastPostDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @return the range of matching blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsStatsUser> findByU_L(long userId, Date lastPostDate,
		int start, int end) throws SystemException {
		return findByU_L(userId, lastPostDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where userId = &#63; and lastPostDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsStatsUser> findByU_L(long userId, Date lastPostDate,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_L;
			finderArgs = new Object[] { userId, lastPostDate };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_L;
			finderArgs = new Object[] {
					userId, lastPostDate,
					
					start, end, orderByComparator
				};
		}

		List<BlogsStatsUser> list = (List<BlogsStatsUser>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsStatsUser blogsStatsUser : list) {
				if ((userId != blogsStatsUser.getUserId()) ||
						!Validator.equals(lastPostDate,
							blogsStatsUser.getLastPostDate())) {
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

			query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_U_L_USERID_2);

			if (lastPostDate == null) {
				query.append(_FINDER_COLUMN_U_L_LASTPOSTDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_U_L_LASTPOSTDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (lastPostDate != null) {
					qPos.add(CalendarUtil.getTimestamp(lastPostDate));
				}

				list = (List<BlogsStatsUser>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first blogs stats user in the ordered set where userId = &#63; and lastPostDate = &#63;.
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user
	 * @throws com.liferay.portlet.blogs.NoSuchStatsUserException if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser findByU_L_First(long userId, Date lastPostDate,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = fetchByU_L_First(userId, lastPostDate,
				orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", lastPostDate=");
		msg.append(lastPostDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the first blogs stats user in the ordered set where userId = &#63; and lastPostDate = &#63;.
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser fetchByU_L_First(long userId, Date lastPostDate,
		OrderByComparator orderByComparator) throws SystemException {
		List<BlogsStatsUser> list = findByU_L(userId, lastPostDate, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs stats user in the ordered set where userId = &#63; and lastPostDate = &#63;.
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user
	 * @throws com.liferay.portlet.blogs.NoSuchStatsUserException if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser findByU_L_Last(long userId, Date lastPostDate,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = fetchByU_L_Last(userId, lastPostDate,
				orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", lastPostDate=");
		msg.append(lastPostDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the last blogs stats user in the ordered set where userId = &#63; and lastPostDate = &#63;.
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser fetchByU_L_Last(long userId, Date lastPostDate,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByU_L(userId, lastPostDate);

		List<BlogsStatsUser> list = findByU_L(userId, lastPostDate, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs stats users before and after the current blogs stats user in the ordered set where userId = &#63; and lastPostDate = &#63;.
	 *
	 * @param statsUserId the primary key of the current blogs stats user
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs stats user
	 * @throws com.liferay.portlet.blogs.NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser[] findByU_L_PrevAndNext(long statsUserId,
		long userId, Date lastPostDate, OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = getByU_L_PrevAndNext(session, blogsStatsUser, userId,
					lastPostDate, orderByComparator, true);

			array[1] = blogsStatsUser;

			array[2] = getByU_L_PrevAndNext(session, blogsStatsUser, userId,
					lastPostDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsStatsUser getByU_L_PrevAndNext(Session session,
		BlogsStatsUser blogsStatsUser, long userId, Date lastPostDate,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

		query.append(_FINDER_COLUMN_U_L_USERID_2);

		if (lastPostDate == null) {
			query.append(_FINDER_COLUMN_U_L_LASTPOSTDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_U_L_LASTPOSTDATE_2);
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
			query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (lastPostDate != null) {
			qPos.add(CalendarUtil.getTimestamp(lastPostDate));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsStatsUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsStatsUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the blogs stats users.
	 *
	 * @return the blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsStatsUser> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs stats users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @return the range of blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsStatsUser> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs stats users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<BlogsStatsUser> findAll(int start, int end,
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

		List<BlogsStatsUser> list = (List<BlogsStatsUser>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_BLOGSSTATSUSER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_BLOGSSTATSUSER.concat(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<BlogsStatsUser>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<BlogsStatsUser>)QueryUtil.list(q,
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
	 * Removes all the blogs stats users where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (BlogsStatsUser blogsStatsUser : findByGroupId(groupId)) {
			remove(blogsStatsUser);
		}
	}

	/**
	 * Removes all the blogs stats users where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (BlogsStatsUser blogsStatsUser : findByUserId(userId)) {
			remove(blogsStatsUser);
		}
	}

	/**
	 * Removes the blogs stats user where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the blogs stats user that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public BlogsStatsUser removeByG_U(long groupId, long userId)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = findByG_U(groupId, userId);

		return remove(blogsStatsUser);
	}

	/**
	 * Removes all the blogs stats users where groupId = &#63; and entryCount &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_NotE(long groupId, int entryCount)
		throws SystemException {
		for (BlogsStatsUser blogsStatsUser : findByG_NotE(groupId, entryCount)) {
			remove(blogsStatsUser);
		}
	}

	/**
	 * Removes all the blogs stats users where companyId = &#63; and entryCount &ne; &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_NotE(long companyId, int entryCount)
		throws SystemException {
		for (BlogsStatsUser blogsStatsUser : findByC_NotE(companyId, entryCount)) {
			remove(blogsStatsUser);
		}
	}

	/**
	 * Removes all the blogs stats users where userId = &#63; and lastPostDate = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU_L(long userId, Date lastPostDate)
		throws SystemException {
		for (BlogsStatsUser blogsStatsUser : findByU_L(userId, lastPostDate)) {
			remove(blogsStatsUser);
		}
	}

	/**
	 * Removes all the blogs stats users from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (BlogsStatsUser blogsStatsUser : findAll()) {
			remove(blogsStatsUser);
		}
	}

	/**
	 * Returns the number of blogs stats users where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

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
	 * Returns the number of blogs stats users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

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
	 * Returns the number of blogs stats users where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

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
	 * Returns the number of blogs stats users where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @return the number of matching blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_NotE(long groupId, int entryCount)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, entryCount };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_NOTE,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_G_NOTE_GROUPID_2);

			query.append(_FINDER_COLUMN_G_NOTE_ENTRYCOUNT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(entryCount);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_NOTE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of blogs stats users where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @return the number of matching blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_NotE(long companyId, int entryCount)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, entryCount };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_NOTE,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_C_NOTE_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_NOTE_ENTRYCOUNT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(entryCount);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_NOTE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of blogs stats users where userId = &#63; and lastPostDate = &#63;.
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @return the number of matching blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_L(long userId, Date lastPostDate)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId, lastPostDate };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_L,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_U_L_USERID_2);

			if (lastPostDate == null) {
				query.append(_FINDER_COLUMN_U_L_LASTPOSTDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_U_L_LASTPOSTDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (lastPostDate != null) {
					qPos.add(CalendarUtil.getTimestamp(lastPostDate));
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_L, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of blogs stats users.
	 *
	 * @return the number of blogs stats users
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_BLOGSSTATSUSER);

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
	 * Initializes the blogs stats user persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.blogs.model.BlogsStatsUser")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<BlogsStatsUser>> listenersList = new ArrayList<ModelListener<BlogsStatsUser>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<BlogsStatsUser>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(BlogsStatsUserImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = BlogsEntryPersistence.class)
	protected BlogsEntryPersistence blogsEntryPersistence;
	@BeanReference(type = BlogsStatsUserPersistence.class)
	protected BlogsStatsUserPersistence blogsStatsUserPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_BLOGSSTATSUSER = "SELECT blogsStatsUser FROM BlogsStatsUser blogsStatsUser";
	private static final String _SQL_SELECT_BLOGSSTATSUSER_WHERE = "SELECT blogsStatsUser FROM BlogsStatsUser blogsStatsUser WHERE ";
	private static final String _SQL_COUNT_BLOGSSTATSUSER = "SELECT COUNT(blogsStatsUser) FROM BlogsStatsUser blogsStatsUser";
	private static final String _SQL_COUNT_BLOGSSTATSUSER_WHERE = "SELECT COUNT(blogsStatsUser) FROM BlogsStatsUser blogsStatsUser WHERE ";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "blogsStatsUser.groupId = ?";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "blogsStatsUser.userId = ?";
	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "blogsStatsUser.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "blogsStatsUser.userId = ?";
	private static final String _FINDER_COLUMN_G_NOTE_GROUPID_2 = "blogsStatsUser.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_NOTE_ENTRYCOUNT_2 = "blogsStatsUser.entryCount != ?";
	private static final String _FINDER_COLUMN_C_NOTE_COMPANYID_2 = "blogsStatsUser.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_NOTE_ENTRYCOUNT_2 = "blogsStatsUser.entryCount != ?";
	private static final String _FINDER_COLUMN_U_L_USERID_2 = "blogsStatsUser.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_L_LASTPOSTDATE_1 = "blogsStatsUser.lastPostDate IS NULL";
	private static final String _FINDER_COLUMN_U_L_LASTPOSTDATE_2 = "blogsStatsUser.lastPostDate = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "blogsStatsUser.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No BlogsStatsUser exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No BlogsStatsUser exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(BlogsStatsUserPersistenceImpl.class);
	private static BlogsStatsUser _nullBlogsStatsUser = new BlogsStatsUserImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<BlogsStatsUser> toCacheModel() {
				return _nullBlogsStatsUserCacheModel;
			}
		};

	private static CacheModel<BlogsStatsUser> _nullBlogsStatsUserCacheModel = new CacheModel<BlogsStatsUser>() {
			public BlogsStatsUser toEntityModel() {
				return _nullBlogsStatsUser;
			}
		};
}