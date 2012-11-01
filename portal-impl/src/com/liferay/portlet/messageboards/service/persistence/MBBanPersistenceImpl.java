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
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.messageboards.NoSuchBanException;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.model.impl.MBBanImpl;
import com.liferay.portlet.messageboards.model.impl.MBBanModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the message boards ban service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBBanPersistence
 * @see MBBanUtil
 * @generated
 */
public class MBBanPersistenceImpl extends BasePersistenceImpl<MBBan>
	implements MBBanPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link MBBanUtil} to access the message boards ban persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = MBBanImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, MBBanImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, MBBanImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			MBBanModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, MBBanImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, MBBanImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			MBBanModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_BANUSERID =
		new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, MBBanImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByBanUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_BANUSERID =
		new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, MBBanImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByBanUserId",
			new String[] { Long.class.getName() },
			MBBanModelImpl.BANUSERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_BANUSERID = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByBanUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_B = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, MBBanImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_B",
			new String[] { Long.class.getName(), Long.class.getName() },
			MBBanModelImpl.GROUPID_COLUMN_BITMASK |
			MBBanModelImpl.BANUSERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_B = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_B",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, MBBanImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, MBBanImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the message boards ban in the entity cache if it is enabled.
	 *
	 * @param mbBan the message boards ban
	 */
	public void cacheResult(MBBan mbBan) {
		EntityCacheUtil.putResult(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanImpl.class, mbBan.getPrimaryKey(), mbBan);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_B,
			new Object[] {
				Long.valueOf(mbBan.getGroupId()),
				Long.valueOf(mbBan.getBanUserId())
			}, mbBan);

		mbBan.resetOriginalValues();
	}

	/**
	 * Caches the message boards bans in the entity cache if it is enabled.
	 *
	 * @param mbBans the message boards bans
	 */
	public void cacheResult(List<MBBan> mbBans) {
		for (MBBan mbBan : mbBans) {
			if (EntityCacheUtil.getResult(MBBanModelImpl.ENTITY_CACHE_ENABLED,
						MBBanImpl.class, mbBan.getPrimaryKey()) == null) {
				cacheResult(mbBan);
			}
			else {
				mbBan.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all message boards bans.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(MBBanImpl.class.getName());
		}

		EntityCacheUtil.clearCache(MBBanImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the message boards ban.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MBBan mbBan) {
		EntityCacheUtil.removeResult(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanImpl.class, mbBan.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(mbBan);
	}

	@Override
	public void clearCache(List<MBBan> mbBans) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MBBan mbBan : mbBans) {
			EntityCacheUtil.removeResult(MBBanModelImpl.ENTITY_CACHE_ENABLED,
				MBBanImpl.class, mbBan.getPrimaryKey());

			clearUniqueFindersCache(mbBan);
		}
	}

	protected void clearUniqueFindersCache(MBBan mbBan) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_B,
			new Object[] {
				Long.valueOf(mbBan.getGroupId()),
				Long.valueOf(mbBan.getBanUserId())
			});
	}

	/**
	 * Creates a new message boards ban with the primary key. Does not add the message boards ban to the database.
	 *
	 * @param banId the primary key for the new message boards ban
	 * @return the new message boards ban
	 */
	public MBBan create(long banId) {
		MBBan mbBan = new MBBanImpl();

		mbBan.setNew(true);
		mbBan.setPrimaryKey(banId);

		return mbBan;
	}

	/**
	 * Removes the message boards ban with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param banId the primary key of the message boards ban
	 * @return the message boards ban that was removed
	 * @throws com.liferay.portlet.messageboards.NoSuchBanException if a message boards ban with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan remove(long banId) throws NoSuchBanException, SystemException {
		return remove(Long.valueOf(banId));
	}

	/**
	 * Removes the message boards ban with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the message boards ban
	 * @return the message boards ban that was removed
	 * @throws com.liferay.portlet.messageboards.NoSuchBanException if a message boards ban with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBBan remove(Serializable primaryKey)
		throws NoSuchBanException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBBan mbBan = (MBBan)session.get(MBBanImpl.class, primaryKey);

			if (mbBan == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchBanException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(mbBan);
		}
		catch (NoSuchBanException nsee) {
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
	protected MBBan removeImpl(MBBan mbBan) throws SystemException {
		mbBan = toUnwrappedModel(mbBan);

		Session session = null;

		try {
			session = openSession();

			if (mbBan.isCachedModel()) {
				mbBan = (MBBan)session.get(MBBanImpl.class,
						mbBan.getPrimaryKeyObj());
			}

			if (mbBan != null) {
				session.delete(mbBan);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (mbBan != null) {
			clearCache(mbBan);
		}

		return mbBan;
	}

	@Override
	public MBBan updateImpl(com.liferay.portlet.messageboards.model.MBBan mbBan)
		throws SystemException {
		mbBan = toUnwrappedModel(mbBan);

		boolean isNew = mbBan.isNew();

		MBBanModelImpl mbBanModelImpl = (MBBanModelImpl)mbBan;

		Session session = null;

		try {
			session = openSession();

			if (mbBan.isNew()) {
				session.save(mbBan);

				mbBan.setNew(false);
			}
			else {
				session.merge(mbBan);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !MBBanModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((mbBanModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbBanModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { Long.valueOf(mbBanModelImpl.getGroupId()) };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((mbBanModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbBanModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] { Long.valueOf(mbBanModelImpl.getUserId()) };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((mbBanModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_BANUSERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbBanModelImpl.getOriginalBanUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_BANUSERID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_BANUSERID,
					args);

				args = new Object[] { Long.valueOf(mbBanModelImpl.getBanUserId()) };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_BANUSERID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_BANUSERID,
					args);
			}
		}

		EntityCacheUtil.putResult(MBBanModelImpl.ENTITY_CACHE_ENABLED,
			MBBanImpl.class, mbBan.getPrimaryKey(), mbBan);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_B,
				new Object[] {
					Long.valueOf(mbBan.getGroupId()),
					Long.valueOf(mbBan.getBanUserId())
				}, mbBan);
		}
		else {
			if ((mbBanModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_B.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbBanModelImpl.getOriginalGroupId()),
						Long.valueOf(mbBanModelImpl.getOriginalBanUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_B, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_B, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_B,
					new Object[] {
						Long.valueOf(mbBan.getGroupId()),
						Long.valueOf(mbBan.getBanUserId())
					}, mbBan);
			}
		}

		return mbBan;
	}

	protected MBBan toUnwrappedModel(MBBan mbBan) {
		if (mbBan instanceof MBBanImpl) {
			return mbBan;
		}

		MBBanImpl mbBanImpl = new MBBanImpl();

		mbBanImpl.setNew(mbBan.isNew());
		mbBanImpl.setPrimaryKey(mbBan.getPrimaryKey());

		mbBanImpl.setBanId(mbBan.getBanId());
		mbBanImpl.setGroupId(mbBan.getGroupId());
		mbBanImpl.setCompanyId(mbBan.getCompanyId());
		mbBanImpl.setUserId(mbBan.getUserId());
		mbBanImpl.setUserName(mbBan.getUserName());
		mbBanImpl.setCreateDate(mbBan.getCreateDate());
		mbBanImpl.setModifiedDate(mbBan.getModifiedDate());
		mbBanImpl.setBanUserId(mbBan.getBanUserId());

		return mbBanImpl;
	}

	/**
	 * Returns the message boards ban with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards ban
	 * @return the message boards ban
	 * @throws com.liferay.portal.NoSuchModelException if a message boards ban with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBBan findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the message boards ban with the primary key or throws a {@link com.liferay.portlet.messageboards.NoSuchBanException} if it could not be found.
	 *
	 * @param banId the primary key of the message boards ban
	 * @return the message boards ban
	 * @throws com.liferay.portlet.messageboards.NoSuchBanException if a message boards ban with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan findByPrimaryKey(long banId)
		throws NoSuchBanException, SystemException {
		MBBan mbBan = fetchByPrimaryKey(banId);

		if (mbBan == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + banId);
			}

			throw new NoSuchBanException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				banId);
		}

		return mbBan;
	}

	/**
	 * Returns the message boards ban with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards ban
	 * @return the message boards ban, or <code>null</code> if a message boards ban with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBBan fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the message boards ban with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param banId the primary key of the message boards ban
	 * @return the message boards ban, or <code>null</code> if a message boards ban with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan fetchByPrimaryKey(long banId) throws SystemException {
		MBBan mbBan = (MBBan)EntityCacheUtil.getResult(MBBanModelImpl.ENTITY_CACHE_ENABLED,
				MBBanImpl.class, banId);

		if (mbBan == _nullMBBan) {
			return null;
		}

		if (mbBan == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				mbBan = (MBBan)session.get(MBBanImpl.class, Long.valueOf(banId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (mbBan != null) {
					cacheResult(mbBan);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(MBBanModelImpl.ENTITY_CACHE_ENABLED,
						MBBanImpl.class, banId, _nullMBBan);
				}

				closeSession(session);
			}
		}

		return mbBan;
	}

	/**
	 * Returns all the message boards bans where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching message boards bans
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBBan> findByGroupId(long groupId) throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards bans where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards bans
	 * @param end the upper bound of the range of message boards bans (not inclusive)
	 * @return the range of matching message boards bans
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBBan> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards bans where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards bans
	 * @param end the upper bound of the range of message boards bans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards bans
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBBan> findByGroupId(long groupId, int start, int end,
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

		List<MBBan> list = (List<MBBan>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBBan mbBan : list) {
				if ((groupId != mbBan.getGroupId())) {
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

			query.append(_SQL_SELECT_MBBAN_WHERE);

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

				list = (List<MBBan>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first message boards ban in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards ban
	 * @throws com.liferay.portlet.messageboards.NoSuchBanException if a matching message boards ban could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchBanException, SystemException {
		MBBan mbBan = fetchByGroupId_First(groupId, orderByComparator);

		if (mbBan != null) {
			return mbBan;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchBanException(msg.toString());
	}

	/**
	 * Returns the first message boards ban in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBBan> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards ban in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards ban
	 * @throws com.liferay.portlet.messageboards.NoSuchBanException if a matching message boards ban could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchBanException, SystemException {
		MBBan mbBan = fetchByGroupId_Last(groupId, orderByComparator);

		if (mbBan != null) {
			return mbBan;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchBanException(msg.toString());
	}

	/**
	 * Returns the last message boards ban in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<MBBan> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards bans before and after the current message boards ban in the ordered set where groupId = &#63;.
	 *
	 * @param banId the primary key of the current message boards ban
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards ban
	 * @throws com.liferay.portlet.messageboards.NoSuchBanException if a message boards ban with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan[] findByGroupId_PrevAndNext(long banId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchBanException, SystemException {
		MBBan mbBan = findByPrimaryKey(banId);

		Session session = null;

		try {
			session = openSession();

			MBBan[] array = new MBBanImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, mbBan, groupId,
					orderByComparator, true);

			array[1] = mbBan;

			array[2] = getByGroupId_PrevAndNext(session, mbBan, groupId,
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

	protected MBBan getByGroupId_PrevAndNext(Session session, MBBan mbBan,
		long groupId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBBAN_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(mbBan);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBBan> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards bans where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching message boards bans
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBBan> findByUserId(long userId) throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards bans where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards bans
	 * @param end the upper bound of the range of message boards bans (not inclusive)
	 * @return the range of matching message boards bans
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBBan> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards bans where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards bans
	 * @param end the upper bound of the range of message boards bans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards bans
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBBan> findByUserId(long userId, int start, int end,
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

		List<MBBan> list = (List<MBBan>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBBan mbBan : list) {
				if ((userId != mbBan.getUserId())) {
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

			query.append(_SQL_SELECT_MBBAN_WHERE);

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

				list = (List<MBBan>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first message boards ban in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards ban
	 * @throws com.liferay.portlet.messageboards.NoSuchBanException if a matching message boards ban could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchBanException, SystemException {
		MBBan mbBan = fetchByUserId_First(userId, orderByComparator);

		if (mbBan != null) {
			return mbBan;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchBanException(msg.toString());
	}

	/**
	 * Returns the first message boards ban in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBBan> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards ban in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards ban
	 * @throws com.liferay.portlet.messageboards.NoSuchBanException if a matching message boards ban could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchBanException, SystemException {
		MBBan mbBan = fetchByUserId_Last(userId, orderByComparator);

		if (mbBan != null) {
			return mbBan;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchBanException(msg.toString());
	}

	/**
	 * Returns the last message boards ban in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		List<MBBan> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards bans before and after the current message boards ban in the ordered set where userId = &#63;.
	 *
	 * @param banId the primary key of the current message boards ban
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards ban
	 * @throws com.liferay.portlet.messageboards.NoSuchBanException if a message boards ban with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan[] findByUserId_PrevAndNext(long banId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchBanException, SystemException {
		MBBan mbBan = findByPrimaryKey(banId);

		Session session = null;

		try {
			session = openSession();

			MBBan[] array = new MBBanImpl[3];

			array[0] = getByUserId_PrevAndNext(session, mbBan, userId,
					orderByComparator, true);

			array[1] = mbBan;

			array[2] = getByUserId_PrevAndNext(session, mbBan, userId,
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

	protected MBBan getByUserId_PrevAndNext(Session session, MBBan mbBan,
		long userId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBBAN_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(mbBan);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBBan> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards bans where banUserId = &#63;.
	 *
	 * @param banUserId the ban user ID
	 * @return the matching message boards bans
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBBan> findByBanUserId(long banUserId)
		throws SystemException {
		return findByBanUserId(banUserId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards bans where banUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param banUserId the ban user ID
	 * @param start the lower bound of the range of message boards bans
	 * @param end the upper bound of the range of message boards bans (not inclusive)
	 * @return the range of matching message boards bans
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBBan> findByBanUserId(long banUserId, int start, int end)
		throws SystemException {
		return findByBanUserId(banUserId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards bans where banUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param banUserId the ban user ID
	 * @param start the lower bound of the range of message boards bans
	 * @param end the upper bound of the range of message boards bans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards bans
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBBan> findByBanUserId(long banUserId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_BANUSERID;
			finderArgs = new Object[] { banUserId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_BANUSERID;
			finderArgs = new Object[] { banUserId, start, end, orderByComparator };
		}

		List<MBBan> list = (List<MBBan>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBBan mbBan : list) {
				if ((banUserId != mbBan.getBanUserId())) {
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

			query.append(_SQL_SELECT_MBBAN_WHERE);

			query.append(_FINDER_COLUMN_BANUSERID_BANUSERID_2);

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

				qPos.add(banUserId);

				list = (List<MBBan>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first message boards ban in the ordered set where banUserId = &#63;.
	 *
	 * @param banUserId the ban user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards ban
	 * @throws com.liferay.portlet.messageboards.NoSuchBanException if a matching message boards ban could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan findByBanUserId_First(long banUserId,
		OrderByComparator orderByComparator)
		throws NoSuchBanException, SystemException {
		MBBan mbBan = fetchByBanUserId_First(banUserId, orderByComparator);

		if (mbBan != null) {
			return mbBan;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("banUserId=");
		msg.append(banUserId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchBanException(msg.toString());
	}

	/**
	 * Returns the first message boards ban in the ordered set where banUserId = &#63;.
	 *
	 * @param banUserId the ban user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan fetchByBanUserId_First(long banUserId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBBan> list = findByBanUserId(banUserId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards ban in the ordered set where banUserId = &#63;.
	 *
	 * @param banUserId the ban user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards ban
	 * @throws com.liferay.portlet.messageboards.NoSuchBanException if a matching message boards ban could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan findByBanUserId_Last(long banUserId,
		OrderByComparator orderByComparator)
		throws NoSuchBanException, SystemException {
		MBBan mbBan = fetchByBanUserId_Last(banUserId, orderByComparator);

		if (mbBan != null) {
			return mbBan;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("banUserId=");
		msg.append(banUserId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchBanException(msg.toString());
	}

	/**
	 * Returns the last message boards ban in the ordered set where banUserId = &#63;.
	 *
	 * @param banUserId the ban user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan fetchByBanUserId_Last(long banUserId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByBanUserId(banUserId);

		List<MBBan> list = findByBanUserId(banUserId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards bans before and after the current message boards ban in the ordered set where banUserId = &#63;.
	 *
	 * @param banId the primary key of the current message boards ban
	 * @param banUserId the ban user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards ban
	 * @throws com.liferay.portlet.messageboards.NoSuchBanException if a message boards ban with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan[] findByBanUserId_PrevAndNext(long banId, long banUserId,
		OrderByComparator orderByComparator)
		throws NoSuchBanException, SystemException {
		MBBan mbBan = findByPrimaryKey(banId);

		Session session = null;

		try {
			session = openSession();

			MBBan[] array = new MBBanImpl[3];

			array[0] = getByBanUserId_PrevAndNext(session, mbBan, banUserId,
					orderByComparator, true);

			array[1] = mbBan;

			array[2] = getByBanUserId_PrevAndNext(session, mbBan, banUserId,
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

	protected MBBan getByBanUserId_PrevAndNext(Session session, MBBan mbBan,
		long banUserId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBBAN_WHERE);

		query.append(_FINDER_COLUMN_BANUSERID_BANUSERID_2);

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

		qPos.add(banUserId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbBan);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBBan> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the message boards ban where groupId = &#63; and banUserId = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchBanException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param banUserId the ban user ID
	 * @return the matching message boards ban
	 * @throws com.liferay.portlet.messageboards.NoSuchBanException if a matching message boards ban could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan findByG_B(long groupId, long banUserId)
		throws NoSuchBanException, SystemException {
		MBBan mbBan = fetchByG_B(groupId, banUserId);

		if (mbBan == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", banUserId=");
			msg.append(banUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchBanException(msg.toString());
		}

		return mbBan;
	}

	/**
	 * Returns the message boards ban where groupId = &#63; and banUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param banUserId the ban user ID
	 * @return the matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan fetchByG_B(long groupId, long banUserId)
		throws SystemException {
		return fetchByG_B(groupId, banUserId, true);
	}

	/**
	 * Returns the message boards ban where groupId = &#63; and banUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param banUserId the ban user ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching message boards ban, or <code>null</code> if a matching message boards ban could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan fetchByG_B(long groupId, long banUserId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, banUserId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_B,
					finderArgs, this);
		}

		if (result instanceof MBBan) {
			MBBan mbBan = (MBBan)result;

			if ((groupId != mbBan.getGroupId()) ||
					(banUserId != mbBan.getBanUserId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_MBBAN_WHERE);

			query.append(_FINDER_COLUMN_G_B_GROUPID_2);

			query.append(_FINDER_COLUMN_G_B_BANUSERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(banUserId);

				List<MBBan> list = q.list();

				result = list;

				MBBan mbBan = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_B,
						finderArgs, list);
				}
				else {
					mbBan = list.get(0);

					cacheResult(mbBan);

					if ((mbBan.getGroupId() != groupId) ||
							(mbBan.getBanUserId() != banUserId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_B,
							finderArgs, mbBan);
					}
				}

				return mbBan;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_B,
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
				return (MBBan)result;
			}
		}
	}

	/**
	 * Returns all the message boards bans.
	 *
	 * @return the message boards bans
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBBan> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards bans.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards bans
	 * @param end the upper bound of the range of message boards bans (not inclusive)
	 * @return the range of message boards bans
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBBan> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards bans.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards bans
	 * @param end the upper bound of the range of message boards bans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of message boards bans
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBBan> findAll(int start, int end,
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

		List<MBBan> list = (List<MBBan>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_MBBAN);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MBBAN;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<MBBan>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<MBBan>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Removes all the message boards bans where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (MBBan mbBan : findByGroupId(groupId)) {
			remove(mbBan);
		}
	}

	/**
	 * Removes all the message boards bans where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (MBBan mbBan : findByUserId(userId)) {
			remove(mbBan);
		}
	}

	/**
	 * Removes all the message boards bans where banUserId = &#63; from the database.
	 *
	 * @param banUserId the ban user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByBanUserId(long banUserId) throws SystemException {
		for (MBBan mbBan : findByBanUserId(banUserId)) {
			remove(mbBan);
		}
	}

	/**
	 * Removes the message boards ban where groupId = &#63; and banUserId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param banUserId the ban user ID
	 * @return the message boards ban that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan removeByG_B(long groupId, long banUserId)
		throws NoSuchBanException, SystemException {
		MBBan mbBan = findByG_B(groupId, banUserId);

		return remove(mbBan);
	}

	/**
	 * Removes all the message boards bans from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (MBBan mbBan : findAll()) {
			remove(mbBan);
		}
	}

	/**
	 * Returns the number of message boards bans where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching message boards bans
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBBAN_WHERE);

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
	 * Returns the number of message boards bans where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching message boards bans
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBBAN_WHERE);

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
	 * Returns the number of message boards bans where banUserId = &#63;.
	 *
	 * @param banUserId the ban user ID
	 * @return the number of matching message boards bans
	 * @throws SystemException if a system exception occurred
	 */
	public int countByBanUserId(long banUserId) throws SystemException {
		Object[] finderArgs = new Object[] { banUserId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_BANUSERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBBAN_WHERE);

			query.append(_FINDER_COLUMN_BANUSERID_BANUSERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(banUserId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_BANUSERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message boards bans where groupId = &#63; and banUserId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param banUserId the ban user ID
	 * @return the number of matching message boards bans
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_B(long groupId, long banUserId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, banUserId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_B,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBBAN_WHERE);

			query.append(_FINDER_COLUMN_G_B_GROUPID_2);

			query.append(_FINDER_COLUMN_G_B_BANUSERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(banUserId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_B, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message boards bans.
	 *
	 * @return the number of message boards bans
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MBBAN);

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
	 * Initializes the message boards ban persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.messageboards.model.MBBan")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<MBBan>> listenersList = new ArrayList<ModelListener<MBBan>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<MBBan>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(MBBanImpl.class.getName());
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
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_MBBAN = "SELECT mbBan FROM MBBan mbBan";
	private static final String _SQL_SELECT_MBBAN_WHERE = "SELECT mbBan FROM MBBan mbBan WHERE ";
	private static final String _SQL_COUNT_MBBAN = "SELECT COUNT(mbBan) FROM MBBan mbBan";
	private static final String _SQL_COUNT_MBBAN_WHERE = "SELECT COUNT(mbBan) FROM MBBan mbBan WHERE ";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "mbBan.groupId = ?";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "mbBan.userId = ?";
	private static final String _FINDER_COLUMN_BANUSERID_BANUSERID_2 = "mbBan.banUserId = ?";
	private static final String _FINDER_COLUMN_G_B_GROUPID_2 = "mbBan.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_B_BANUSERID_2 = "mbBan.banUserId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "mbBan.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MBBan exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MBBan exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(MBBanPersistenceImpl.class);
	private static MBBan _nullMBBan = new MBBanImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<MBBan> toCacheModel() {
				return _nullMBBanCacheModel;
			}
		};

	private static CacheModel<MBBan> _nullMBBanCacheModel = new CacheModel<MBBan>() {
			public MBBan toEntityModel() {
				return _nullMBBan;
			}
		};
}