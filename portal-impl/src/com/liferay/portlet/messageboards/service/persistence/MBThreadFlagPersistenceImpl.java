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

import com.liferay.portlet.messageboards.NoSuchThreadFlagException;
import com.liferay.portlet.messageboards.model.MBThreadFlag;
import com.liferay.portlet.messageboards.model.impl.MBThreadFlagImpl;
import com.liferay.portlet.messageboards.model.impl.MBThreadFlagModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the message boards thread flag service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadFlagPersistence
 * @see MBThreadFlagUtil
 * @generated
 */
public class MBThreadFlagPersistenceImpl extends BasePersistenceImpl<MBThreadFlag>
	implements MBThreadFlagPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link MBThreadFlagUtil} to access the message boards thread flag persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = MBThreadFlagImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, MBThreadFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, MBThreadFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			MBThreadFlagModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_THREADID = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, MBThreadFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByThreadId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_THREADID =
		new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, MBThreadFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByThreadId",
			new String[] { Long.class.getName() },
			MBThreadFlagModelImpl.THREADID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_THREADID = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByThreadId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_U_T = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, MBThreadFlagImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_T",
			new String[] { Long.class.getName(), Long.class.getName() },
			MBThreadFlagModelImpl.USERID_COLUMN_BITMASK |
			MBThreadFlagModelImpl.THREADID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_T = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_T",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, MBThreadFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, MBThreadFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the message boards thread flag in the entity cache if it is enabled.
	 *
	 * @param mbThreadFlag the message boards thread flag
	 */
	public void cacheResult(MBThreadFlag mbThreadFlag) {
		EntityCacheUtil.putResult(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagImpl.class, mbThreadFlag.getPrimaryKey(), mbThreadFlag);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
			new Object[] {
				Long.valueOf(mbThreadFlag.getUserId()),
				Long.valueOf(mbThreadFlag.getThreadId())
			}, mbThreadFlag);

		mbThreadFlag.resetOriginalValues();
	}

	/**
	 * Caches the message boards thread flags in the entity cache if it is enabled.
	 *
	 * @param mbThreadFlags the message boards thread flags
	 */
	public void cacheResult(List<MBThreadFlag> mbThreadFlags) {
		for (MBThreadFlag mbThreadFlag : mbThreadFlags) {
			if (EntityCacheUtil.getResult(
						MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
						MBThreadFlagImpl.class, mbThreadFlag.getPrimaryKey()) == null) {
				cacheResult(mbThreadFlag);
			}
			else {
				mbThreadFlag.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all message boards thread flags.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(MBThreadFlagImpl.class.getName());
		}

		EntityCacheUtil.clearCache(MBThreadFlagImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the message boards thread flag.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MBThreadFlag mbThreadFlag) {
		EntityCacheUtil.removeResult(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagImpl.class, mbThreadFlag.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(mbThreadFlag);
	}

	@Override
	public void clearCache(List<MBThreadFlag> mbThreadFlags) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MBThreadFlag mbThreadFlag : mbThreadFlags) {
			EntityCacheUtil.removeResult(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
				MBThreadFlagImpl.class, mbThreadFlag.getPrimaryKey());

			clearUniqueFindersCache(mbThreadFlag);
		}
	}

	protected void clearUniqueFindersCache(MBThreadFlag mbThreadFlag) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_T,
			new Object[] {
				Long.valueOf(mbThreadFlag.getUserId()),
				Long.valueOf(mbThreadFlag.getThreadId())
			});
	}

	/**
	 * Creates a new message boards thread flag with the primary key. Does not add the message boards thread flag to the database.
	 *
	 * @param threadFlagId the primary key for the new message boards thread flag
	 * @return the new message boards thread flag
	 */
	public MBThreadFlag create(long threadFlagId) {
		MBThreadFlag mbThreadFlag = new MBThreadFlagImpl();

		mbThreadFlag.setNew(true);
		mbThreadFlag.setPrimaryKey(threadFlagId);

		return mbThreadFlag;
	}

	/**
	 * Removes the message boards thread flag with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param threadFlagId the primary key of the message boards thread flag
	 * @return the message boards thread flag that was removed
	 * @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBThreadFlag remove(long threadFlagId)
		throws NoSuchThreadFlagException, SystemException {
		return remove(Long.valueOf(threadFlagId));
	}

	/**
	 * Removes the message boards thread flag with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the message boards thread flag
	 * @return the message boards thread flag that was removed
	 * @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBThreadFlag remove(Serializable primaryKey)
		throws NoSuchThreadFlagException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBThreadFlag mbThreadFlag = (MBThreadFlag)session.get(MBThreadFlagImpl.class,
					primaryKey);

			if (mbThreadFlag == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchThreadFlagException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(mbThreadFlag);
		}
		catch (NoSuchThreadFlagException nsee) {
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
	protected MBThreadFlag removeImpl(MBThreadFlag mbThreadFlag)
		throws SystemException {
		mbThreadFlag = toUnwrappedModel(mbThreadFlag);

		Session session = null;

		try {
			session = openSession();

			if (mbThreadFlag.isCachedModel()) {
				mbThreadFlag = (MBThreadFlag)session.get(MBThreadFlagImpl.class,
						mbThreadFlag.getPrimaryKeyObj());
			}

			if (mbThreadFlag != null) {
				session.delete(mbThreadFlag);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (mbThreadFlag != null) {
			clearCache(mbThreadFlag);
		}

		return mbThreadFlag;
	}

	@Override
	public MBThreadFlag updateImpl(
		com.liferay.portlet.messageboards.model.MBThreadFlag mbThreadFlag)
		throws SystemException {
		mbThreadFlag = toUnwrappedModel(mbThreadFlag);

		boolean isNew = mbThreadFlag.isNew();

		MBThreadFlagModelImpl mbThreadFlagModelImpl = (MBThreadFlagModelImpl)mbThreadFlag;

		Session session = null;

		try {
			session = openSession();

			if (mbThreadFlag.isNew()) {
				session.save(mbThreadFlag);

				mbThreadFlag.setNew(false);
			}
			else {
				session.merge(mbThreadFlag);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !MBThreadFlagModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((mbThreadFlagModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbThreadFlagModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] {
						Long.valueOf(mbThreadFlagModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((mbThreadFlagModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_THREADID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbThreadFlagModelImpl.getOriginalThreadId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_THREADID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_THREADID,
					args);

				args = new Object[] {
						Long.valueOf(mbThreadFlagModelImpl.getThreadId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_THREADID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_THREADID,
					args);
			}
		}

		EntityCacheUtil.putResult(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadFlagImpl.class, mbThreadFlag.getPrimaryKey(), mbThreadFlag);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
				new Object[] {
					Long.valueOf(mbThreadFlag.getUserId()),
					Long.valueOf(mbThreadFlag.getThreadId())
				}, mbThreadFlag);
		}
		else {
			if ((mbThreadFlagModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_U_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(mbThreadFlagModelImpl.getOriginalUserId()),
						Long.valueOf(mbThreadFlagModelImpl.getOriginalThreadId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_T, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_T, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
					new Object[] {
						Long.valueOf(mbThreadFlag.getUserId()),
						Long.valueOf(mbThreadFlag.getThreadId())
					}, mbThreadFlag);
			}
		}

		return mbThreadFlag;
	}

	protected MBThreadFlag toUnwrappedModel(MBThreadFlag mbThreadFlag) {
		if (mbThreadFlag instanceof MBThreadFlagImpl) {
			return mbThreadFlag;
		}

		MBThreadFlagImpl mbThreadFlagImpl = new MBThreadFlagImpl();

		mbThreadFlagImpl.setNew(mbThreadFlag.isNew());
		mbThreadFlagImpl.setPrimaryKey(mbThreadFlag.getPrimaryKey());

		mbThreadFlagImpl.setThreadFlagId(mbThreadFlag.getThreadFlagId());
		mbThreadFlagImpl.setUserId(mbThreadFlag.getUserId());
		mbThreadFlagImpl.setModifiedDate(mbThreadFlag.getModifiedDate());
		mbThreadFlagImpl.setThreadId(mbThreadFlag.getThreadId());

		return mbThreadFlagImpl;
	}

	/**
	 * Returns the message boards thread flag with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards thread flag
	 * @return the message boards thread flag
	 * @throws com.liferay.portal.NoSuchModelException if a message boards thread flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBThreadFlag findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the message boards thread flag with the primary key or throws a {@link com.liferay.portlet.messageboards.NoSuchThreadFlagException} if it could not be found.
	 *
	 * @param threadFlagId the primary key of the message boards thread flag
	 * @return the message boards thread flag
	 * @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBThreadFlag findByPrimaryKey(long threadFlagId)
		throws NoSuchThreadFlagException, SystemException {
		MBThreadFlag mbThreadFlag = fetchByPrimaryKey(threadFlagId);

		if (mbThreadFlag == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + threadFlagId);
			}

			throw new NoSuchThreadFlagException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				threadFlagId);
		}

		return mbThreadFlag;
	}

	/**
	 * Returns the message boards thread flag with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards thread flag
	 * @return the message boards thread flag, or <code>null</code> if a message boards thread flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBThreadFlag fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the message boards thread flag with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param threadFlagId the primary key of the message boards thread flag
	 * @return the message boards thread flag, or <code>null</code> if a message boards thread flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBThreadFlag fetchByPrimaryKey(long threadFlagId)
		throws SystemException {
		MBThreadFlag mbThreadFlag = (MBThreadFlag)EntityCacheUtil.getResult(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
				MBThreadFlagImpl.class, threadFlagId);

		if (mbThreadFlag == _nullMBThreadFlag) {
			return null;
		}

		if (mbThreadFlag == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				mbThreadFlag = (MBThreadFlag)session.get(MBThreadFlagImpl.class,
						Long.valueOf(threadFlagId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (mbThreadFlag != null) {
					cacheResult(mbThreadFlag);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(MBThreadFlagModelImpl.ENTITY_CACHE_ENABLED,
						MBThreadFlagImpl.class, threadFlagId, _nullMBThreadFlag);
				}

				closeSession(session);
			}
		}

		return mbThreadFlag;
	}

	/**
	 * Returns all the message boards thread flags where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching message boards thread flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBThreadFlag> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards thread flags where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @return the range of matching message boards thread flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBThreadFlag> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards thread flags where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards thread flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBThreadFlag> findByUserId(long userId, int start, int end,
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

		List<MBThreadFlag> list = (List<MBThreadFlag>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBThreadFlag mbThreadFlag : list) {
				if ((userId != mbThreadFlag.getUserId())) {
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

			query.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

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

				list = (List<MBThreadFlag>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards thread flag in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread flag
	 * @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a matching message boards thread flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBThreadFlag findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchThreadFlagException, SystemException {
		MBThreadFlag mbThreadFlag = fetchByUserId_First(userId,
				orderByComparator);

		if (mbThreadFlag != null) {
			return mbThreadFlag;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadFlagException(msg.toString());
	}

	/**
	 * Returns the first message boards thread flag in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBThreadFlag fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBThreadFlag> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread flag in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread flag
	 * @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a matching message boards thread flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBThreadFlag findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchThreadFlagException, SystemException {
		MBThreadFlag mbThreadFlag = fetchByUserId_Last(userId, orderByComparator);

		if (mbThreadFlag != null) {
			return mbThreadFlag;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadFlagException(msg.toString());
	}

	/**
	 * Returns the last message boards thread flag in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBThreadFlag fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		List<MBThreadFlag> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards thread flags before and after the current message boards thread flag in the ordered set where userId = &#63;.
	 *
	 * @param threadFlagId the primary key of the current message boards thread flag
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread flag
	 * @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBThreadFlag[] findByUserId_PrevAndNext(long threadFlagId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchThreadFlagException, SystemException {
		MBThreadFlag mbThreadFlag = findByPrimaryKey(threadFlagId);

		Session session = null;

		try {
			session = openSession();

			MBThreadFlag[] array = new MBThreadFlagImpl[3];

			array[0] = getByUserId_PrevAndNext(session, mbThreadFlag, userId,
					orderByComparator, true);

			array[1] = mbThreadFlag;

			array[2] = getByUserId_PrevAndNext(session, mbThreadFlag, userId,
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

	protected MBThreadFlag getByUserId_PrevAndNext(Session session,
		MBThreadFlag mbThreadFlag, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(mbThreadFlag);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThreadFlag> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards thread flags where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the matching message boards thread flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBThreadFlag> findByThreadId(long threadId)
		throws SystemException {
		return findByThreadId(threadId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards thread flags where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @return the range of matching message boards thread flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBThreadFlag> findByThreadId(long threadId, int start, int end)
		throws SystemException {
		return findByThreadId(threadId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards thread flags where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards thread flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBThreadFlag> findByThreadId(long threadId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_THREADID;
			finderArgs = new Object[] { threadId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_THREADID;
			finderArgs = new Object[] { threadId, start, end, orderByComparator };
		}

		List<MBThreadFlag> list = (List<MBThreadFlag>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (MBThreadFlag mbThreadFlag : list) {
				if ((threadId != mbThreadFlag.getThreadId())) {
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

			query.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

			query.append(_FINDER_COLUMN_THREADID_THREADID_2);

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

				qPos.add(threadId);

				list = (List<MBThreadFlag>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards thread flag in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread flag
	 * @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a matching message boards thread flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBThreadFlag findByThreadId_First(long threadId,
		OrderByComparator orderByComparator)
		throws NoSuchThreadFlagException, SystemException {
		MBThreadFlag mbThreadFlag = fetchByThreadId_First(threadId,
				orderByComparator);

		if (mbThreadFlag != null) {
			return mbThreadFlag;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("threadId=");
		msg.append(threadId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadFlagException(msg.toString());
	}

	/**
	 * Returns the first message boards thread flag in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBThreadFlag fetchByThreadId_First(long threadId,
		OrderByComparator orderByComparator) throws SystemException {
		List<MBThreadFlag> list = findByThreadId(threadId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread flag in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread flag
	 * @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a matching message boards thread flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBThreadFlag findByThreadId_Last(long threadId,
		OrderByComparator orderByComparator)
		throws NoSuchThreadFlagException, SystemException {
		MBThreadFlag mbThreadFlag = fetchByThreadId_Last(threadId,
				orderByComparator);

		if (mbThreadFlag != null) {
			return mbThreadFlag;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("threadId=");
		msg.append(threadId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadFlagException(msg.toString());
	}

	/**
	 * Returns the last message boards thread flag in the ordered set where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBThreadFlag fetchByThreadId_Last(long threadId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByThreadId(threadId);

		List<MBThreadFlag> list = findByThreadId(threadId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards thread flags before and after the current message boards thread flag in the ordered set where threadId = &#63;.
	 *
	 * @param threadFlagId the primary key of the current message boards thread flag
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread flag
	 * @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBThreadFlag[] findByThreadId_PrevAndNext(long threadFlagId,
		long threadId, OrderByComparator orderByComparator)
		throws NoSuchThreadFlagException, SystemException {
		MBThreadFlag mbThreadFlag = findByPrimaryKey(threadFlagId);

		Session session = null;

		try {
			session = openSession();

			MBThreadFlag[] array = new MBThreadFlagImpl[3];

			array[0] = getByThreadId_PrevAndNext(session, mbThreadFlag,
					threadId, orderByComparator, true);

			array[1] = mbThreadFlag;

			array[2] = getByThreadId_PrevAndNext(session, mbThreadFlag,
					threadId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThreadFlag getByThreadId_PrevAndNext(Session session,
		MBThreadFlag mbThreadFlag, long threadId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

		query.append(_FINDER_COLUMN_THREADID_THREADID_2);

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

		qPos.add(threadId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThreadFlag);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThreadFlag> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the message boards thread flag where userId = &#63; and threadId = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchThreadFlagException} if it could not be found.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @return the matching message boards thread flag
	 * @throws com.liferay.portlet.messageboards.NoSuchThreadFlagException if a matching message boards thread flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBThreadFlag findByU_T(long userId, long threadId)
		throws NoSuchThreadFlagException, SystemException {
		MBThreadFlag mbThreadFlag = fetchByU_T(userId, threadId);

		if (mbThreadFlag == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", threadId=");
			msg.append(threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchThreadFlagException(msg.toString());
		}

		return mbThreadFlag;
	}

	/**
	 * Returns the message boards thread flag where userId = &#63; and threadId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @return the matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBThreadFlag fetchByU_T(long userId, long threadId)
		throws SystemException {
		return fetchByU_T(userId, threadId, true);
	}

	/**
	 * Returns the message boards thread flag where userId = &#63; and threadId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBThreadFlag fetchByU_T(long userId, long threadId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { userId, threadId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_T,
					finderArgs, this);
		}

		if (result instanceof MBThreadFlag) {
			MBThreadFlag mbThreadFlag = (MBThreadFlag)result;

			if ((userId != mbThreadFlag.getUserId()) ||
					(threadId != mbThreadFlag.getThreadId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_MBTHREADFLAG_WHERE);

			query.append(_FINDER_COLUMN_U_T_USERID_2);

			query.append(_FINDER_COLUMN_U_T_THREADID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(threadId);

				List<MBThreadFlag> list = q.list();

				result = list;

				MBThreadFlag mbThreadFlag = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
						finderArgs, list);
				}
				else {
					mbThreadFlag = list.get(0);

					cacheResult(mbThreadFlag);

					if ((mbThreadFlag.getUserId() != userId) ||
							(mbThreadFlag.getThreadId() != threadId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
							finderArgs, mbThreadFlag);
					}
				}

				return mbThreadFlag;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_T,
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
				return (MBThreadFlag)result;
			}
		}
	}

	/**
	 * Returns all the message boards thread flags.
	 *
	 * @return the message boards thread flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBThreadFlag> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards thread flags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @return the range of message boards thread flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBThreadFlag> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards thread flags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards thread flags
	 * @param end the upper bound of the range of message boards thread flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of message boards thread flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBThreadFlag> findAll(int start, int end,
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

		List<MBThreadFlag> list = (List<MBThreadFlag>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_MBTHREADFLAG);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MBTHREADFLAG;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<MBThreadFlag>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<MBThreadFlag>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the message boards thread flags where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (MBThreadFlag mbThreadFlag : findByUserId(userId)) {
			remove(mbThreadFlag);
		}
	}

	/**
	 * Removes all the message boards thread flags where threadId = &#63; from the database.
	 *
	 * @param threadId the thread ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByThreadId(long threadId) throws SystemException {
		for (MBThreadFlag mbThreadFlag : findByThreadId(threadId)) {
			remove(mbThreadFlag);
		}
	}

	/**
	 * Removes the message boards thread flag where userId = &#63; and threadId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @return the message boards thread flag that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public MBThreadFlag removeByU_T(long userId, long threadId)
		throws NoSuchThreadFlagException, SystemException {
		MBThreadFlag mbThreadFlag = findByU_T(userId, threadId);

		return remove(mbThreadFlag);
	}

	/**
	 * Removes all the message boards thread flags from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (MBThreadFlag mbThreadFlag : findAll()) {
			remove(mbThreadFlag);
		}
	}

	/**
	 * Returns the number of message boards thread flags where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching message boards thread flags
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBTHREADFLAG_WHERE);

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
	 * Returns the number of message boards thread flags where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the number of matching message boards thread flags
	 * @throws SystemException if a system exception occurred
	 */
	public int countByThreadId(long threadId) throws SystemException {
		Object[] finderArgs = new Object[] { threadId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_THREADID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBTHREADFLAG_WHERE);

			query.append(_FINDER_COLUMN_THREADID_THREADID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_THREADID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message boards thread flags where userId = &#63; and threadId = &#63;.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @return the number of matching message boards thread flags
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_T(long userId, long threadId) throws SystemException {
		Object[] finderArgs = new Object[] { userId, threadId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBTHREADFLAG_WHERE);

			query.append(_FINDER_COLUMN_U_T_USERID_2);

			query.append(_FINDER_COLUMN_U_T_THREADID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(threadId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_T, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message boards thread flags.
	 *
	 * @return the number of message boards thread flags
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MBTHREADFLAG);

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
	 * Initializes the message boards thread flag persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.messageboards.model.MBThreadFlag")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<MBThreadFlag>> listenersList = new ArrayList<ModelListener<MBThreadFlag>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<MBThreadFlag>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(MBThreadFlagImpl.class.getName());
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
	private static final String _SQL_SELECT_MBTHREADFLAG = "SELECT mbThreadFlag FROM MBThreadFlag mbThreadFlag";
	private static final String _SQL_SELECT_MBTHREADFLAG_WHERE = "SELECT mbThreadFlag FROM MBThreadFlag mbThreadFlag WHERE ";
	private static final String _SQL_COUNT_MBTHREADFLAG = "SELECT COUNT(mbThreadFlag) FROM MBThreadFlag mbThreadFlag";
	private static final String _SQL_COUNT_MBTHREADFLAG_WHERE = "SELECT COUNT(mbThreadFlag) FROM MBThreadFlag mbThreadFlag WHERE ";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "mbThreadFlag.userId = ?";
	private static final String _FINDER_COLUMN_THREADID_THREADID_2 = "mbThreadFlag.threadId = ?";
	private static final String _FINDER_COLUMN_U_T_USERID_2 = "mbThreadFlag.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_T_THREADID_2 = "mbThreadFlag.threadId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "mbThreadFlag.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MBThreadFlag exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MBThreadFlag exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(MBThreadFlagPersistenceImpl.class);
	private static MBThreadFlag _nullMBThreadFlag = new MBThreadFlagImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<MBThreadFlag> toCacheModel() {
				return _nullMBThreadFlagCacheModel;
			}
		};

	private static CacheModel<MBThreadFlag> _nullMBThreadFlagCacheModel = new CacheModel<MBThreadFlag>() {
			public MBThreadFlag toEntityModel() {
				return _nullMBThreadFlag;
			}
		};
}