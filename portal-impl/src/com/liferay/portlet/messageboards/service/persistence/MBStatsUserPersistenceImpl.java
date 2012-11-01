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

package com.liferay.portlet.messageboards.service.persistence;

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
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.messageboards.NoSuchStatsUserException;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.model.impl.MBStatsUserImpl;
import com.liferay.portlet.messageboards.model.impl.MBStatsUserModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the message boards stats user service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBStatsUserPersistence
 * @see MBStatsUserUtil
 * @generated
 */
public class MBStatsUserPersistenceImpl extends BasePersistenceImpl<MBStatsUser>
	implements MBStatsUserPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link MBStatsUserUtil} to access the message boards stats user persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = MBStatsUserImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, MBStatsUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, MBStatsUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			MBStatsUserModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, MBStatsUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, MBStatsUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			MBStatsUserModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_U = new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, MBStatsUserImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_U",
			new String[] { Long.class.getName(), Long.class.getName() },
			MBStatsUserModelImpl.GROUPID_COLUMN_BITMASK |
			MBStatsUserModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_NOTU_NOTM =
		new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, MBStatsUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_NotU_NotM",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_NOTU_NOTM =
		new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_NotU_NotM",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, MBStatsUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, MBStatsUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the message boards stats user in the entity cache if it is enabled.
	 *
	 * @param mbStatsUser the message boards stats user
	 */
	public void cacheResult(MBStatsUser mbStatsUser) {
		EntityCacheUtil.putResult(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserImpl.class, mbStatsUser.getPrimaryKey(), mbStatsUser);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
			new Object[] {
				Long.valueOf(mbStatsUser.getGroupId()),
				Long.valueOf(mbStatsUser.getUserId())
			}, mbStatsUser);

		mbStatsUser.resetOriginalValues();
	}

	/**
	 * Caches the message boards stats users in the entity cache if it is enabled.
	 *
	 * @param mbStatsUsers the message boards stats users
	 */
	public void cacheResult(List<MBStatsUser> mbStatsUsers) {
		for (MBStatsUser mbStatsUser : mbStatsUsers) {
			if (EntityCacheUtil.getResult(
						MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
						MBStatsUserImpl.class, mbStatsUser.getPrimaryKey()) == null) {
				cacheResult(mbStatsUser);
			}
			else {
				mbStatsUser.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all message boards stats users.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(MBStatsUserImpl.class.getName());
		}

		EntityCacheUtil.clearCache(MBStatsUserImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the message boards stats user.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MBStatsUser mbStatsUser) {
		EntityCacheUtil.removeResult(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserImpl.class, mbStatsUser.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(mbStatsUser);
	}

	@Override
	public void clearCache(List<MBStatsUser> mbStatsUsers) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MBStatsUser mbStatsUser : mbStatsUsers) {
			EntityCacheUtil.removeResult(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
				MBStatsUserImpl.class, mbStatsUser.getPrimaryKey());

			clearUniqueFindersCache(mbStatsUser);
		}
	}

	protected void clearUniqueFindersCache(MBStatsUser mbStatsUser) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U,
			new Object[] {
				Long.valueOf(mbStatsUser.getGroupId()),
				Long.valueOf(mbStatsUser.getUserId())
			});
	}

	/**
	 * Creates a new message boards stats user with the primary key. Does not add the message boards stats user to the database.
	 *
	 * @param statsUserId the primary key for the new message boards stats user
	 * @return the new message boards stats user
	 */
	public MBStatsUser create(long statsUserId) {
		MBStatsUser mbStatsUser = new MBStatsUserImpl();

		mbStatsUser.setNew(true);
		mbStatsUser.setPrimaryKey(statsUserId);

		return mbStatsUser;
	}

	/**
	 * Removes the message boards stats user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param statsUserId the primary key of the message boards stats user
	 * @return the message boards stats user that was removed
	 * @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser remove(long statsUserId)
		throws NoSuchStatsUserException, SystemException {
		return remove(Long.valueOf(statsUserId));
	}

	/**
	 * Removes the message boards stats user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the message boards stats user
	 * @return the message boards stats user that was removed
	 * @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBStatsUser remove(Serializable primaryKey)
		throws NoSuchStatsUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBStatsUser mbStatsUser = (MBStatsUser)session.get(MBStatsUserImpl.class,
					primaryKey);

			if (mbStatsUser == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStatsUserException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(mbStatsUser);
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
	protected MBStatsUser removeImpl(MBStatsUser mbStatsUser)
		throws SystemException {
		mbStatsUser = toUnwrappedModel(mbStatsUser);

		Session session = null;

		try {
			session = openSession();

			if (mbStatsUser.isCachedModel()) {
				mbStatsUser = (MBStatsUser)session.get(MBStatsUserImpl.class,
						mbStatsUser.getPrimaryKeyObj());
			}

			if (mbStatsUser != null) {
				session.delete(mbStatsUser);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (mbStatsUser != null) {
			clearCache(mbStatsUser);
		}

		return mbStatsUser;
	}

	@Override
	public MBStatsUser updateImpl(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser)
		throws SystemException {
		mbStatsUser = toUnwrappedModel(mbStatsUser);

		boolean isNew = mbStatsUser.isNew();

		MBStatsUserModelImpl mbStatsUserModelImpl = (MBStatsUserModelImpl)mbStatsUser;

		Session session = null;

		try {
			session = openSession();

			if (mbStatsUser.isNew()) {
				session.save(mbStatsUser);

				mbStatsUser.setNew(false);
			}
			else {
				session.merge(mbStatsUser);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !MBStatsUserModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((mbStatsUserModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbStatsUserModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(mbStatsUserModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((mbStatsUserModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbStatsUserModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] {
						Long.valueOf(mbStatsUserModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}
		}

		EntityCacheUtil.putResult(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			MBStatsUserImpl.class, mbStatsUser.getPrimaryKey(), mbStatsUser);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
				new Object[] {
					Long.valueOf(mbStatsUser.getGroupId()),
					Long.valueOf(mbStatsUser.getUserId())
				}, mbStatsUser);
		}
		else {
			if ((mbStatsUserModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbStatsUserModelImpl.getOriginalGroupId()),
						Long.valueOf(mbStatsUserModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
					new Object[] {
						Long.valueOf(mbStatsUser.getGroupId()),
						Long.valueOf(mbStatsUser.getUserId())
					}, mbStatsUser);
			}
		}

		return mbStatsUser;
	}

	protected MBStatsUser toUnwrappedModel(MBStatsUser mbStatsUser) {
		if (mbStatsUser instanceof MBStatsUserImpl) {
			return mbStatsUser;
		}

		MBStatsUserImpl mbStatsUserImpl = new MBStatsUserImpl();

		mbStatsUserImpl.setNew(mbStatsUser.isNew());
		mbStatsUserImpl.setPrimaryKey(mbStatsUser.getPrimaryKey());

		mbStatsUserImpl.setStatsUserId(mbStatsUser.getStatsUserId());
		mbStatsUserImpl.setGroupId(mbStatsUser.getGroupId());
		mbStatsUserImpl.setUserId(mbStatsUser.getUserId());
		mbStatsUserImpl.setMessageCount(mbStatsUser.getMessageCount());
		mbStatsUserImpl.setLastPostDate(mbStatsUser.getLastPostDate());

		return mbStatsUserImpl;
	}

	/**
	 * Returns the message boards stats user with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards stats user
	 * @return the message boards stats user
	 * @throws com.liferay.portal.NoSuchModelException if a message boards stats user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBStatsUser findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the message boards stats user with the primary key or throws a {@link com.liferay.portlet.messageboards.NoSuchStatsUserException} if it could not be found.
	 *
	 * @param statsUserId the primary key of the message boards stats user
	 * @return the message boards stats user
	 * @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser findByPrimaryKey(long statsUserId)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = fetchByPrimaryKey(statsUserId);

		if (mbStatsUser == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + statsUserId);
			}

			throw new NoSuchStatsUserException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				statsUserId);
		}

		return mbStatsUser;
	}

	/**
	 * Returns the message boards stats user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards stats user
	 * @return the message boards stats user, or <code>null</code> if a message boards stats user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBStatsUser fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the message boards stats user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param statsUserId the primary key of the message boards stats user
	 * @return the message boards stats user, or <code>null</code> if a message boards stats user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser fetchByPrimaryKey(long statsUserId)
		throws SystemException {
		MBStatsUser mbStatsUser = (MBStatsUser)EntityCacheUtil.getResult(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
				MBStatsUserImpl.class, statsUserId);

		if (mbStatsUser == _nullMBStatsUser) {
			return null;
		}

		if (mbStatsUser == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				mbStatsUser = (MBStatsUser)session.get(MBStatsUserImpl.class,
						Long.valueOf(statsUserId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (mbStatsUser != null) {
					cacheResult(mbStatsUser);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(MBStatsUserModelImpl.ENTITY_CACHE_ENABLED,
						MBStatsUserImpl.class, statsUserId, _nullMBStatsUser);
				}

				closeSession(session);
			}
		}

		return mbStatsUser;
	}

	/**
	 * Returns all the message boards stats users where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching message boards stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBStatsUser> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards stats users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @return the range of matching message boards stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBStatsUser> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards stats users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBStatsUser> findByGroupId(long groupId, int start, int end,
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

		List<MBStatsUser> list = (List<MBStatsUser>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBStatsUser mbStatsUser : list) {
				if ((groupId != mbStatsUser.getGroupId())) {
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

			query.append(_SQL_SELECT_MBSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBStatsUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<MBStatsUser>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user
	 * @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a matching message boards stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = fetchByGroupId_First(groupId,
				orderByComparator);

		if (mbStatsUser != null) {
			return mbStatsUser;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the first message boards stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBStatsUser> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user
	 * @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a matching message boards stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = fetchByGroupId_Last(groupId, orderByComparator);

		if (mbStatsUser != null) {
			return mbStatsUser;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the last message boards stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<MBStatsUser> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards stats users before and after the current message boards stats user in the ordered set where groupId = &#63;.
	 *
	 * @param statsUserId the primary key of the current message boards stats user
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards stats user
	 * @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser[] findByGroupId_PrevAndNext(long statsUserId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			MBStatsUser[] array = new MBStatsUserImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, mbStatsUser, groupId,
					orderByComparator, true);

			array[1] = mbStatsUser;

			array[2] = getByGroupId_PrevAndNext(session, mbStatsUser, groupId,
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

	protected MBStatsUser getByGroupId_PrevAndNext(Session session,
		MBStatsUser mbStatsUser, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBSTATSUSER_WHERE);

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
			query.append(MBStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbStatsUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBStatsUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards stats users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching message boards stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBStatsUser> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards stats users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @return the range of matching message boards stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBStatsUser> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards stats users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBStatsUser> findByUserId(long userId, int start, int end,
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

		List<MBStatsUser> list = (List<MBStatsUser>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBStatsUser mbStatsUser : list) {
				if ((userId != mbStatsUser.getUserId())) {
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

			query.append(_SQL_SELECT_MBSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBStatsUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<MBStatsUser>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user
	 * @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a matching message boards stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = fetchByUserId_First(userId, orderByComparator);

		if (mbStatsUser != null) {
			return mbStatsUser;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the first message boards stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBStatsUser> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user
	 * @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a matching message boards stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = fetchByUserId_Last(userId, orderByComparator);

		if (mbStatsUser != null) {
			return mbStatsUser;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the last message boards stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		List<MBStatsUser> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards stats users before and after the current message boards stats user in the ordered set where userId = &#63;.
	 *
	 * @param statsUserId the primary key of the current message boards stats user
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards stats user
	 * @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser[] findByUserId_PrevAndNext(long statsUserId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			MBStatsUser[] array = new MBStatsUserImpl[3];

			array[0] = getByUserId_PrevAndNext(session, mbStatsUser, userId,
					orderByComparator, true);

			array[1] = mbStatsUser;

			array[2] = getByUserId_PrevAndNext(session, mbStatsUser, userId,
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

	protected MBStatsUser getByUserId_PrevAndNext(Session session,
		MBStatsUser mbStatsUser, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBSTATSUSER_WHERE);

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
			query.append(MBStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbStatsUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBStatsUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the message boards stats user where groupId = &#63; and userId = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchStatsUserException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching message boards stats user
	 * @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a matching message boards stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser findByG_U(long groupId, long userId)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = fetchByG_U(groupId, userId);

		if (mbStatsUser == null) {
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

		return mbStatsUser;
	}

	/**
	 * Returns the message boards stats user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser fetchByG_U(long groupId, long userId)
		throws SystemException {
		return fetchByG_U(groupId, userId, true);
	}

	/**
	 * Returns the message boards stats user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser fetchByG_U(long groupId, long userId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_U,
					finderArgs, this);
		}

		if (result instanceof MBStatsUser) {
			MBStatsUser mbStatsUser = (MBStatsUser)result;

			if ((groupId != mbStatsUser.getGroupId()) ||
					(userId != mbStatsUser.getUserId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_MBSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			query.append(MBStatsUserModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				List<MBStatsUser> list = q.list();

				result = list;

				MBStatsUser mbStatsUser = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
						finderArgs, list);
				}
				else {
					mbStatsUser = list.get(0);

					cacheResult(mbStatsUser);

					if ((mbStatsUser.getGroupId() != groupId) ||
							(mbStatsUser.getUserId() != userId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
							finderArgs, mbStatsUser);
					}
				}

				return mbStatsUser;
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
				return (MBStatsUser)result;
			}
		}
	}

	/**
	 * Returns all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @return the matching message boards stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBStatsUser> findByG_NotU_NotM(long groupId, long userId,
		int messageCount) throws SystemException {
		return findByG_NotU_NotM(groupId, userId, messageCount,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @return the range of matching message boards stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBStatsUser> findByG_NotU_NotM(long groupId, long userId,
		int messageCount, int start, int end) throws SystemException {
		return findByG_NotU_NotM(groupId, userId, messageCount, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBStatsUser> findByG_NotU_NotM(long groupId, long userId,
		int messageCount, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_NOTU_NOTM;
		finderArgs = new Object[] {
				groupId, userId, messageCount,
				
				start, end, orderByComparator
			};

		List<MBStatsUser> list = (List<MBStatsUser>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBStatsUser mbStatsUser : list) {
				if ((groupId != mbStatsUser.getGroupId()) ||
						(userId != mbStatsUser.getUserId()) ||
						(messageCount != mbStatsUser.getMessageCount())) {
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
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_MBSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_G_NOTU_NOTM_GROUPID_2);

			query.append(_FINDER_COLUMN_G_NOTU_NOTM_USERID_2);

			query.append(_FINDER_COLUMN_G_NOTU_NOTM_MESSAGECOUNT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(MBStatsUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(messageCount);

				list = (List<MBStatsUser>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user
	 * @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a matching message boards stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser findByG_NotU_NotM_First(long groupId, long userId,
		int messageCount, OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = fetchByG_NotU_NotM_First(groupId, userId,
				messageCount, orderByComparator);

		if (mbStatsUser != null) {
			return mbStatsUser;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", messageCount=");
		msg.append(messageCount);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the first message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser fetchByG_NotU_NotM_First(long groupId, long userId,
		int messageCount, OrderByComparator orderByComparator)
		throws SystemException {
		List<MBStatsUser> list = findByG_NotU_NotM(groupId, userId,
				messageCount, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user
	 * @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a matching message boards stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser findByG_NotU_NotM_Last(long groupId, long userId,
		int messageCount, OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = fetchByG_NotU_NotM_Last(groupId, userId,
				messageCount, orderByComparator);

		if (mbStatsUser != null) {
			return mbStatsUser;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", messageCount=");
		msg.append(messageCount);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the last message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser fetchByG_NotU_NotM_Last(long groupId, long userId,
		int messageCount, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_NotU_NotM(groupId, userId, messageCount);

		List<MBStatsUser> list = findByG_NotU_NotM(groupId, userId,
				messageCount, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards stats users before and after the current message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param statsUserId the primary key of the current message boards stats user
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards stats user
	 * @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser[] findByG_NotU_NotM_PrevAndNext(long statsUserId,
		long groupId, long userId, int messageCount,
		OrderByComparator orderByComparator)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			MBStatsUser[] array = new MBStatsUserImpl[3];

			array[0] = getByG_NotU_NotM_PrevAndNext(session, mbStatsUser,
					groupId, userId, messageCount, orderByComparator, true);

			array[1] = mbStatsUser;

			array[2] = getByG_NotU_NotM_PrevAndNext(session, mbStatsUser,
					groupId, userId, messageCount, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBStatsUser getByG_NotU_NotM_PrevAndNext(Session session,
		MBStatsUser mbStatsUser, long groupId, long userId, int messageCount,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBSTATSUSER_WHERE);

		query.append(_FINDER_COLUMN_G_NOTU_NOTM_GROUPID_2);

		query.append(_FINDER_COLUMN_G_NOTU_NOTM_USERID_2);

		query.append(_FINDER_COLUMN_G_NOTU_NOTM_MESSAGECOUNT_2);

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
			query.append(MBStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(messageCount);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbStatsUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBStatsUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards stats users.
	 *
	 * @return the message boards stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBStatsUser> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards stats users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @return the range of message boards stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBStatsUser> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards stats users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of message boards stats users
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBStatsUser> findAll(int start, int end,
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

		List<MBStatsUser> list = (List<MBStatsUser>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_MBSTATSUSER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MBSTATSUSER.concat(MBStatsUserModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<MBStatsUser>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<MBStatsUser>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the message boards stats users where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (MBStatsUser mbStatsUser : findByGroupId(groupId)) {
			remove(mbStatsUser);
		}
	}

	/**
	 * Removes all the message boards stats users where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (MBStatsUser mbStatsUser : findByUserId(userId)) {
			remove(mbStatsUser);
		}
	}

	/**
	 * Removes the message boards stats user where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the message boards stats user that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public MBStatsUser removeByG_U(long groupId, long userId)
		throws NoSuchStatsUserException, SystemException {
		MBStatsUser mbStatsUser = findByG_U(groupId, userId);

		return remove(mbStatsUser);
	}

	/**
	 * Removes all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_NotU_NotM(long groupId, long userId, int messageCount)
		throws SystemException {
		for (MBStatsUser mbStatsUser : findByG_NotU_NotM(groupId, userId,
				messageCount)) {
			remove(mbStatsUser);
		}
	}

	/**
	 * Removes all the message boards stats users from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (MBStatsUser mbStatsUser : findAll()) {
			remove(mbStatsUser);
		}
	}

	/**
	 * Returns the number of message boards stats users where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching message boards stats users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBSTATSUSER_WHERE);

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
	 * Returns the number of message boards stats users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching message boards stats users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBSTATSUSER_WHERE);

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
	 * Returns the number of message boards stats users where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching message boards stats users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBSTATSUSER_WHERE);

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
	 * Returns the number of message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @return the number of matching message boards stats users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_NotU_NotM(long groupId, long userId, int messageCount)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId, messageCount };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_NOTU_NOTM,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_MBSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_G_NOTU_NOTM_GROUPID_2);

			query.append(_FINDER_COLUMN_G_NOTU_NOTM_USERID_2);

			query.append(_FINDER_COLUMN_G_NOTU_NOTM_MESSAGECOUNT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(messageCount);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_NOTU_NOTM,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message boards stats users.
	 *
	 * @return the number of message boards stats users
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MBSTATSUSER);

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
	 * Initializes the message boards stats user persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.messageboards.model.MBStatsUser")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<MBStatsUser>> listenersList = new ArrayList<ModelListener<MBStatsUser>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<MBStatsUser>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(MBStatsUserImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = MBBanPersistence.class)
	protected MBBanPersistence mbBanPersistence;
	@BeanReference(type = MBCategoryPersistence.class)
	protected MBCategoryPersistence mbCategoryPersistence;
	@BeanReference(type = MBDiscussionPersistence.class)
	protected MBDiscussionPersistence mbDiscussionPersistence;
	@BeanReference(type = MBMailingListPersistence.class)
	protected MBMailingListPersistence mbMailingListPersistence;
	@BeanReference(type = MBMessagePersistence.class)
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(type = MBStatsUserPersistence.class)
	protected MBStatsUserPersistence mbStatsUserPersistence;
	@BeanReference(type = MBThreadPersistence.class)
	protected MBThreadPersistence mbThreadPersistence;
	@BeanReference(type = MBThreadFlagPersistence.class)
	protected MBThreadFlagPersistence mbThreadFlagPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_MBSTATSUSER = "SELECT mbStatsUser FROM MBStatsUser mbStatsUser";
	private static final String _SQL_SELECT_MBSTATSUSER_WHERE = "SELECT mbStatsUser FROM MBStatsUser mbStatsUser WHERE ";
	private static final String _SQL_COUNT_MBSTATSUSER = "SELECT COUNT(mbStatsUser) FROM MBStatsUser mbStatsUser";
	private static final String _SQL_COUNT_MBSTATSUSER_WHERE = "SELECT COUNT(mbStatsUser) FROM MBStatsUser mbStatsUser WHERE ";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "mbStatsUser.groupId = ?";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "mbStatsUser.userId = ?";
	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "mbStatsUser.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "mbStatsUser.userId = ?";
	private static final String _FINDER_COLUMN_G_NOTU_NOTM_GROUPID_2 = "mbStatsUser.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_NOTU_NOTM_USERID_2 = "mbStatsUser.userId != ? AND ";
	private static final String _FINDER_COLUMN_G_NOTU_NOTM_MESSAGECOUNT_2 = "mbStatsUser.messageCount != ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "mbStatsUser.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MBStatsUser exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MBStatsUser exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(MBStatsUserPersistenceImpl.class);
	private static MBStatsUser _nullMBStatsUser = new MBStatsUserImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<MBStatsUser> toCacheModel() {
				return _nullMBStatsUserCacheModel;
			}
		};

	private static CacheModel<MBStatsUser> _nullMBStatsUserCacheModel = new CacheModel<MBStatsUser>() {
			public MBStatsUser toEntityModel() {
				return _nullMBStatsUser;
			}
		};
}