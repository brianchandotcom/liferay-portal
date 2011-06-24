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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.messageboards.NoSuchMessageFlagException;
import com.liferay.portlet.messageboards.model.MBMessageFlag;
import com.liferay.portlet.messageboards.model.impl.MBMessageFlagImpl;
import com.liferay.portlet.messageboards.model.impl.MBMessageFlagModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the message boards message flag service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBMessageFlagPersistence
 * @see MBMessageFlagUtil
 * @generated
 */
public class MBMessageFlagPersistenceImpl extends BasePersistenceImpl<MBMessageFlag>
	implements MBMessageFlagPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link MBMessageFlagUtil} to access the message boards message flag persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = MBMessageFlagImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_THREADID = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByThreadId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_THREADID = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByThreadId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_MESSAGEID = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByMessageId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_MESSAGEID = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByMessageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_T_F = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByT_F",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_T_F = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByT_F",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_M_F = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByM_F",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_M_F = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByM_F",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_U_T_F = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByU_T_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_T_F = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU_T_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_U_M_F = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_M_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_M_F = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU_M_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the message boards message flag in the entity cache if it is enabled.
	 *
	 * @param mbMessageFlag the message boards message flag
	 */
	public void cacheResult(MBMessageFlag mbMessageFlag) {
		EntityCacheUtil.putResult(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagImpl.class, mbMessageFlag.getPrimaryKey(),
			mbMessageFlag);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_M_F,
			new Object[] {
				Long.valueOf(mbMessageFlag.getUserId()),
				Long.valueOf(mbMessageFlag.getMessageId()),
				Integer.valueOf(mbMessageFlag.getFlag())
			}, mbMessageFlag);

		mbMessageFlag.resetOriginalValues();
	}

	/**
	 * Caches the message boards message flags in the entity cache if it is enabled.
	 *
	 * @param mbMessageFlags the message boards message flags
	 */
	public void cacheResult(List<MBMessageFlag> mbMessageFlags) {
		for (MBMessageFlag mbMessageFlag : mbMessageFlags) {
			if (EntityCacheUtil.getResult(
						MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
						MBMessageFlagImpl.class, mbMessageFlag.getPrimaryKey(),
						this) == null) {
				cacheResult(mbMessageFlag);
			}
		}
	}

	/**
	 * Clears the cache for all message boards message flags.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(MBMessageFlagImpl.class.getName());
		}

		EntityCacheUtil.clearCache(MBMessageFlagImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the message boards message flag.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MBMessageFlag mbMessageFlag) {
		EntityCacheUtil.removeResult(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagImpl.class, mbMessageFlag.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_M_F,
			new Object[] {
				Long.valueOf(mbMessageFlag.getUserId()),
				Long.valueOf(mbMessageFlag.getMessageId()),
				Integer.valueOf(mbMessageFlag.getFlag())
			});
	}

	/**
	 * Creates a new message boards message flag with the primary key. Does not add the message boards message flag to the database.
	 *
	 * @param messageFlagId the primary key for the new message boards message flag
	 * @return the new message boards message flag
	 */
	public MBMessageFlag create(long messageFlagId) {
		MBMessageFlag mbMessageFlag = new MBMessageFlagImpl();

		mbMessageFlag.setNew(true);
		mbMessageFlag.setPrimaryKey(messageFlagId);

		return mbMessageFlag;
	}

	/**
	 * Removes the message boards message flag with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the message boards message flag
	 * @return the message boards message flag that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a message boards message flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBMessageFlag remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the message boards message flag with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param messageFlagId the primary key of the message boards message flag
	 * @return the message boards message flag that was removed
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a message boards message flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag remove(long messageFlagId)
		throws NoSuchMessageFlagException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBMessageFlag mbMessageFlag = (MBMessageFlag)session.get(MBMessageFlagImpl.class,
					Long.valueOf(messageFlagId));

			if (mbMessageFlag == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + messageFlagId);
				}

				throw new NoSuchMessageFlagException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					messageFlagId);
			}

			return mbMessageFlagPersistence.remove(mbMessageFlag);
		}
		catch (NoSuchMessageFlagException nsee) {
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
	 * Removes the message boards message flag from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mbMessageFlag the message boards message flag
	 * @return the message boards message flag that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBMessageFlag remove(MBMessageFlag mbMessageFlag)
		throws SystemException {
		return super.remove(mbMessageFlag);
	}

	@Override
	protected MBMessageFlag removeImpl(MBMessageFlag mbMessageFlag)
		throws SystemException {
		mbMessageFlag = toUnwrappedModel(mbMessageFlag);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, mbMessageFlag);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		MBMessageFlagModelImpl mbMessageFlagModelImpl = (MBMessageFlagModelImpl)mbMessageFlag;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_M_F,
			new Object[] {
				Long.valueOf(mbMessageFlagModelImpl.getUserId()),
				Long.valueOf(mbMessageFlagModelImpl.getMessageId()),
				Integer.valueOf(mbMessageFlagModelImpl.getFlag())
			});

		EntityCacheUtil.removeResult(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagImpl.class, mbMessageFlag.getPrimaryKey());

		return mbMessageFlag;
	}

	@Override
	public MBMessageFlag updateImpl(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag,
		boolean merge) throws SystemException {
		mbMessageFlag = toUnwrappedModel(mbMessageFlag);

		boolean isNew = mbMessageFlag.isNew();

		MBMessageFlagModelImpl mbMessageFlagModelImpl = (MBMessageFlagModelImpl)mbMessageFlag;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, mbMessageFlag, merge);

			mbMessageFlag.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
			MBMessageFlagImpl.class, mbMessageFlag.getPrimaryKey(),
			mbMessageFlag);

		if (!isNew &&
				((mbMessageFlag.getUserId() != mbMessageFlagModelImpl.getOriginalUserId()) ||
				(mbMessageFlag.getMessageId() != mbMessageFlagModelImpl.getOriginalMessageId()) ||
				(mbMessageFlag.getFlag() != mbMessageFlagModelImpl.getOriginalFlag()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_M_F,
				new Object[] {
					Long.valueOf(mbMessageFlagModelImpl.getOriginalUserId()),
					Long.valueOf(mbMessageFlagModelImpl.getOriginalMessageId()),
					Integer.valueOf(mbMessageFlagModelImpl.getOriginalFlag())
				});
		}

		if (isNew ||
				((mbMessageFlag.getUserId() != mbMessageFlagModelImpl.getOriginalUserId()) ||
				(mbMessageFlag.getMessageId() != mbMessageFlagModelImpl.getOriginalMessageId()) ||
				(mbMessageFlag.getFlag() != mbMessageFlagModelImpl.getOriginalFlag()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_M_F,
				new Object[] {
					Long.valueOf(mbMessageFlag.getUserId()),
					Long.valueOf(mbMessageFlag.getMessageId()),
					Integer.valueOf(mbMessageFlag.getFlag())
				}, mbMessageFlag);
		}

		return mbMessageFlag;
	}

	protected MBMessageFlag toUnwrappedModel(MBMessageFlag mbMessageFlag) {
		if (mbMessageFlag instanceof MBMessageFlagImpl) {
			return mbMessageFlag;
		}

		MBMessageFlagImpl mbMessageFlagImpl = new MBMessageFlagImpl();

		mbMessageFlagImpl.setNew(mbMessageFlag.isNew());
		mbMessageFlagImpl.setPrimaryKey(mbMessageFlag.getPrimaryKey());

		mbMessageFlagImpl.setMessageFlagId(mbMessageFlag.getMessageFlagId());
		mbMessageFlagImpl.setUserId(mbMessageFlag.getUserId());
		mbMessageFlagImpl.setModifiedDate(mbMessageFlag.getModifiedDate());
		mbMessageFlagImpl.setThreadId(mbMessageFlag.getThreadId());
		mbMessageFlagImpl.setMessageId(mbMessageFlag.getMessageId());
		mbMessageFlagImpl.setFlag(mbMessageFlag.getFlag());

		return mbMessageFlagImpl;
	}

	/**
	 * Returns the message boards message flag with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards message flag
	 * @return the message boards message flag
	 * @throws com.liferay.portal.NoSuchModelException if a message boards message flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBMessageFlag findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the message boards message flag with the primary key or throws a {@link com.liferay.portlet.messageboards.NoSuchMessageFlagException} if it could not be found.
	 *
	 * @param messageFlagId the primary key of the message boards message flag
	 * @return the message boards message flag
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a message boards message flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag findByPrimaryKey(long messageFlagId)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = fetchByPrimaryKey(messageFlagId);

		if (mbMessageFlag == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + messageFlagId);
			}

			throw new NoSuchMessageFlagException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				messageFlagId);
		}

		return mbMessageFlag;
	}

	/**
	 * Returns the message boards message flag with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards message flag
	 * @return the message boards message flag, or <code>null</code> if a message boards message flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MBMessageFlag fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the message boards message flag with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param messageFlagId the primary key of the message boards message flag
	 * @return the message boards message flag, or <code>null</code> if a message boards message flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag fetchByPrimaryKey(long messageFlagId)
		throws SystemException {
		MBMessageFlag mbMessageFlag = (MBMessageFlag)EntityCacheUtil.getResult(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
				MBMessageFlagImpl.class, messageFlagId, this);

		if (mbMessageFlag == _NULL_PLACE_HOLDER) {
			return null;
		}

		if (mbMessageFlag == null) {
			Session session = null;

			boolean hasError = false;

			try {
				session = openSession();

				mbMessageFlag = (MBMessageFlag)session.get(MBMessageFlagImpl.class,
						Long.valueOf(messageFlagId));
			}
			catch (Exception e) {
				hasError = true;

				throw processException(e);
			}
			finally {
				if ((!hasError) && (mbMessageFlag == null)) {
					EntityCacheUtil.putResult(MBMessageFlagModelImpl.ENTITY_CACHE_ENABLED,
						MBMessageFlagImpl.class, messageFlagId,
						_NULL_PLACE_HOLDER);
				}
				else {
					cacheResult(mbMessageFlag);
				}

				closeSession(session);
			}
		}

		return mbMessageFlag;
	}

	/**
	 * Returns all the message boards message flags where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessageFlag> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards message flags where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards message flags
	 * @param end the upper bound of the range of message boards message flags (not inclusive)
	 * @return the range of matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessageFlag> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards message flags where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards message flags
	 * @param end the upper bound of the range of message boards message flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessageFlag> findByUserId(long userId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				userId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessageFlag> list = (List<MBMessageFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
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

			query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

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

				list = (List<MBMessageFlag>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards message flag in the ordered set where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards message flag
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageFlagException, SystemException {
		List<MBMessageFlag> list = findByUserId(userId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message boards message flag in the ordered set where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards message flag
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageFlagException, SystemException {
		int count = countByUserId(userId);

		List<MBMessageFlag> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message boards message flags before and after the current message boards message flag in the ordered set where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageFlagId the primary key of the current message boards message flag
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards message flag
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a message boards message flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag[] findByUserId_PrevAndNext(long messageFlagId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = findByPrimaryKey(messageFlagId);

		Session session = null;

		try {
			session = openSession();

			MBMessageFlag[] array = new MBMessageFlagImpl[3];

			array[0] = getByUserId_PrevAndNext(session, mbMessageFlag, userId,
					orderByComparator, true);

			array[1] = mbMessageFlag;

			array[2] = getByUserId_PrevAndNext(session, mbMessageFlag, userId,
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

	protected MBMessageFlag getByUserId_PrevAndNext(Session session,
		MBMessageFlag mbMessageFlag, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

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
			Object[] values = orderByComparator.getOrderByValues(mbMessageFlag);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessageFlag> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards message flags where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessageFlag> findByThreadId(long threadId)
		throws SystemException {
		return findByThreadId(threadId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards message flags where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message boards message flags
	 * @param end the upper bound of the range of message boards message flags (not inclusive)
	 * @return the range of matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessageFlag> findByThreadId(long threadId, int start, int end)
		throws SystemException {
		return findByThreadId(threadId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards message flags where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param start the lower bound of the range of message boards message flags
	 * @param end the upper bound of the range of message boards message flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessageFlag> findByThreadId(long threadId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				threadId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessageFlag> list = (List<MBMessageFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_THREADID,
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

			query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

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

				list = (List<MBMessageFlag>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_THREADID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_THREADID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message boards message flag in the ordered set where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards message flag
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag findByThreadId_First(long threadId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageFlagException, SystemException {
		List<MBMessageFlag> list = findByThreadId(threadId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("threadId=");
			msg.append(threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message boards message flag in the ordered set where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards message flag
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag findByThreadId_Last(long threadId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageFlagException, SystemException {
		int count = countByThreadId(threadId);

		List<MBMessageFlag> list = findByThreadId(threadId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("threadId=");
			msg.append(threadId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message boards message flags before and after the current message boards message flag in the ordered set where threadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageFlagId the primary key of the current message boards message flag
	 * @param threadId the thread ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards message flag
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a message boards message flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag[] findByThreadId_PrevAndNext(long messageFlagId,
		long threadId, OrderByComparator orderByComparator)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = findByPrimaryKey(messageFlagId);

		Session session = null;

		try {
			session = openSession();

			MBMessageFlag[] array = new MBMessageFlagImpl[3];

			array[0] = getByThreadId_PrevAndNext(session, mbMessageFlag,
					threadId, orderByComparator, true);

			array[1] = mbMessageFlag;

			array[2] = getByThreadId_PrevAndNext(session, mbMessageFlag,
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

	protected MBMessageFlag getByThreadId_PrevAndNext(Session session,
		MBMessageFlag mbMessageFlag, long threadId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

		query.append(_FINDER_COLUMN_THREADID_THREADID_2);

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

		qPos.add(threadId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessageFlag);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessageFlag> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards message flags where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @return the matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessageFlag> findByMessageId(long messageId)
		throws SystemException {
		return findByMessageId(messageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards message flags where messageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the message ID
	 * @param start the lower bound of the range of message boards message flags
	 * @param end the upper bound of the range of message boards message flags (not inclusive)
	 * @return the range of matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessageFlag> findByMessageId(long messageId, int start,
		int end) throws SystemException {
		return findByMessageId(messageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards message flags where messageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the message ID
	 * @param start the lower bound of the range of message boards message flags
	 * @param end the upper bound of the range of message boards message flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessageFlag> findByMessageId(long messageId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				messageId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessageFlag> list = (List<MBMessageFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_MESSAGEID,
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

			query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

			query.append(_FINDER_COLUMN_MESSAGEID_MESSAGEID_2);

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

				qPos.add(messageId);

				list = (List<MBMessageFlag>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_MESSAGEID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_MESSAGEID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message boards message flag in the ordered set where messageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards message flag
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag findByMessageId_First(long messageId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageFlagException, SystemException {
		List<MBMessageFlag> list = findByMessageId(messageId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("messageId=");
			msg.append(messageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message boards message flag in the ordered set where messageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards message flag
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag findByMessageId_Last(long messageId,
		OrderByComparator orderByComparator)
		throws NoSuchMessageFlagException, SystemException {
		int count = countByMessageId(messageId);

		List<MBMessageFlag> list = findByMessageId(messageId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("messageId=");
			msg.append(messageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message boards message flags before and after the current message boards message flag in the ordered set where messageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageFlagId the primary key of the current message boards message flag
	 * @param messageId the message ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards message flag
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a message boards message flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag[] findByMessageId_PrevAndNext(long messageFlagId,
		long messageId, OrderByComparator orderByComparator)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = findByPrimaryKey(messageFlagId);

		Session session = null;

		try {
			session = openSession();

			MBMessageFlag[] array = new MBMessageFlagImpl[3];

			array[0] = getByMessageId_PrevAndNext(session, mbMessageFlag,
					messageId, orderByComparator, true);

			array[1] = mbMessageFlag;

			array[2] = getByMessageId_PrevAndNext(session, mbMessageFlag,
					messageId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessageFlag getByMessageId_PrevAndNext(Session session,
		MBMessageFlag mbMessageFlag, long messageId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

		query.append(_FINDER_COLUMN_MESSAGEID_MESSAGEID_2);

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

		qPos.add(messageId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessageFlag);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessageFlag> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards message flags where threadId = &#63; and flag = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param flag the flag
	 * @return the matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessageFlag> findByT_F(long threadId, int flag)
		throws SystemException {
		return findByT_F(threadId, flag, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards message flags where threadId = &#63; and flag = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param flag the flag
	 * @param start the lower bound of the range of message boards message flags
	 * @param end the upper bound of the range of message boards message flags (not inclusive)
	 * @return the range of matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessageFlag> findByT_F(long threadId, int flag, int start,
		int end) throws SystemException {
		return findByT_F(threadId, flag, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards message flags where threadId = &#63; and flag = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param flag the flag
	 * @param start the lower bound of the range of message boards message flags
	 * @param end the upper bound of the range of message boards message flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessageFlag> findByT_F(long threadId, int flag, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				threadId, flag,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessageFlag> list = (List<MBMessageFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_T_F,
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

			query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

			query.append(_FINDER_COLUMN_T_F_THREADID_2);

			query.append(_FINDER_COLUMN_T_F_FLAG_2);

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

				qPos.add(flag);

				list = (List<MBMessageFlag>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_T_F,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_T_F,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message boards message flag in the ordered set where threadId = &#63; and flag = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param flag the flag
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards message flag
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag findByT_F_First(long threadId, int flag,
		OrderByComparator orderByComparator)
		throws NoSuchMessageFlagException, SystemException {
		List<MBMessageFlag> list = findByT_F(threadId, flag, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("threadId=");
			msg.append(threadId);

			msg.append(", flag=");
			msg.append(flag);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message boards message flag in the ordered set where threadId = &#63; and flag = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param threadId the thread ID
	 * @param flag the flag
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards message flag
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag findByT_F_Last(long threadId, int flag,
		OrderByComparator orderByComparator)
		throws NoSuchMessageFlagException, SystemException {
		int count = countByT_F(threadId, flag);

		List<MBMessageFlag> list = findByT_F(threadId, flag, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("threadId=");
			msg.append(threadId);

			msg.append(", flag=");
			msg.append(flag);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message boards message flags before and after the current message boards message flag in the ordered set where threadId = &#63; and flag = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageFlagId the primary key of the current message boards message flag
	 * @param threadId the thread ID
	 * @param flag the flag
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards message flag
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a message boards message flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag[] findByT_F_PrevAndNext(long messageFlagId,
		long threadId, int flag, OrderByComparator orderByComparator)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = findByPrimaryKey(messageFlagId);

		Session session = null;

		try {
			session = openSession();

			MBMessageFlag[] array = new MBMessageFlagImpl[3];

			array[0] = getByT_F_PrevAndNext(session, mbMessageFlag, threadId,
					flag, orderByComparator, true);

			array[1] = mbMessageFlag;

			array[2] = getByT_F_PrevAndNext(session, mbMessageFlag, threadId,
					flag, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessageFlag getByT_F_PrevAndNext(Session session,
		MBMessageFlag mbMessageFlag, long threadId, int flag,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

		query.append(_FINDER_COLUMN_T_F_THREADID_2);

		query.append(_FINDER_COLUMN_T_F_FLAG_2);

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

		qPos.add(threadId);

		qPos.add(flag);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessageFlag);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessageFlag> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards message flags where messageId = &#63; and flag = &#63;.
	 *
	 * @param messageId the message ID
	 * @param flag the flag
	 * @return the matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessageFlag> findByM_F(long messageId, int flag)
		throws SystemException {
		return findByM_F(messageId, flag, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards message flags where messageId = &#63; and flag = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the message ID
	 * @param flag the flag
	 * @param start the lower bound of the range of message boards message flags
	 * @param end the upper bound of the range of message boards message flags (not inclusive)
	 * @return the range of matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessageFlag> findByM_F(long messageId, int flag, int start,
		int end) throws SystemException {
		return findByM_F(messageId, flag, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards message flags where messageId = &#63; and flag = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the message ID
	 * @param flag the flag
	 * @param start the lower bound of the range of message boards message flags
	 * @param end the upper bound of the range of message boards message flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessageFlag> findByM_F(long messageId, int flag, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				messageId, flag,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessageFlag> list = (List<MBMessageFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_M_F,
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

			query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

			query.append(_FINDER_COLUMN_M_F_MESSAGEID_2);

			query.append(_FINDER_COLUMN_M_F_FLAG_2);

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

				qPos.add(messageId);

				qPos.add(flag);

				list = (List<MBMessageFlag>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_M_F,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_M_F,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message boards message flag in the ordered set where messageId = &#63; and flag = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the message ID
	 * @param flag the flag
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards message flag
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag findByM_F_First(long messageId, int flag,
		OrderByComparator orderByComparator)
		throws NoSuchMessageFlagException, SystemException {
		List<MBMessageFlag> list = findByM_F(messageId, flag, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("messageId=");
			msg.append(messageId);

			msg.append(", flag=");
			msg.append(flag);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message boards message flag in the ordered set where messageId = &#63; and flag = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageId the message ID
	 * @param flag the flag
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards message flag
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag findByM_F_Last(long messageId, int flag,
		OrderByComparator orderByComparator)
		throws NoSuchMessageFlagException, SystemException {
		int count = countByM_F(messageId, flag);

		List<MBMessageFlag> list = findByM_F(messageId, flag, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("messageId=");
			msg.append(messageId);

			msg.append(", flag=");
			msg.append(flag);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message boards message flags before and after the current message boards message flag in the ordered set where messageId = &#63; and flag = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageFlagId the primary key of the current message boards message flag
	 * @param messageId the message ID
	 * @param flag the flag
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards message flag
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a message boards message flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag[] findByM_F_PrevAndNext(long messageFlagId,
		long messageId, int flag, OrderByComparator orderByComparator)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = findByPrimaryKey(messageFlagId);

		Session session = null;

		try {
			session = openSession();

			MBMessageFlag[] array = new MBMessageFlagImpl[3];

			array[0] = getByM_F_PrevAndNext(session, mbMessageFlag, messageId,
					flag, orderByComparator, true);

			array[1] = mbMessageFlag;

			array[2] = getByM_F_PrevAndNext(session, mbMessageFlag, messageId,
					flag, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessageFlag getByM_F_PrevAndNext(Session session,
		MBMessageFlag mbMessageFlag, long messageId, int flag,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

		query.append(_FINDER_COLUMN_M_F_MESSAGEID_2);

		query.append(_FINDER_COLUMN_M_F_FLAG_2);

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

		qPos.add(messageId);

		qPos.add(flag);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessageFlag);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessageFlag> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards message flags where userId = &#63; and threadId = &#63; and flag = &#63;.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @param flag the flag
	 * @return the matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessageFlag> findByU_T_F(long userId, long threadId, int flag)
		throws SystemException {
		return findByU_T_F(userId, threadId, flag, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards message flags where userId = &#63; and threadId = &#63; and flag = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @param flag the flag
	 * @param start the lower bound of the range of message boards message flags
	 * @param end the upper bound of the range of message boards message flags (not inclusive)
	 * @return the range of matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessageFlag> findByU_T_F(long userId, long threadId,
		int flag, int start, int end) throws SystemException {
		return findByU_T_F(userId, threadId, flag, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards message flags where userId = &#63; and threadId = &#63; and flag = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @param flag the flag
	 * @param start the lower bound of the range of message boards message flags
	 * @param end the upper bound of the range of message boards message flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessageFlag> findByU_T_F(long userId, long threadId,
		int flag, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				userId, threadId, flag,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessageFlag> list = (List<MBMessageFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_U_T_F,
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

			query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

			query.append(_FINDER_COLUMN_U_T_F_USERID_2);

			query.append(_FINDER_COLUMN_U_T_F_THREADID_2);

			query.append(_FINDER_COLUMN_U_T_F_FLAG_2);

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

				qPos.add(threadId);

				qPos.add(flag);

				list = (List<MBMessageFlag>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_U_T_F,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_U_T_F,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message boards message flag in the ordered set where userId = &#63; and threadId = &#63; and flag = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @param flag the flag
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards message flag
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag findByU_T_F_First(long userId, long threadId,
		int flag, OrderByComparator orderByComparator)
		throws NoSuchMessageFlagException, SystemException {
		List<MBMessageFlag> list = findByU_T_F(userId, threadId, flag, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", threadId=");
			msg.append(threadId);

			msg.append(", flag=");
			msg.append(flag);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last message boards message flag in the ordered set where userId = &#63; and threadId = &#63; and flag = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @param flag the flag
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards message flag
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag findByU_T_F_Last(long userId, long threadId, int flag,
		OrderByComparator orderByComparator)
		throws NoSuchMessageFlagException, SystemException {
		int count = countByU_T_F(userId, threadId, flag);

		List<MBMessageFlag> list = findByU_T_F(userId, threadId, flag,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", threadId=");
			msg.append(threadId);

			msg.append(", flag=");
			msg.append(flag);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchMessageFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the message boards message flags before and after the current message boards message flag in the ordered set where userId = &#63; and threadId = &#63; and flag = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param messageFlagId the primary key of the current message boards message flag
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @param flag the flag
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards message flag
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a message boards message flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag[] findByU_T_F_PrevAndNext(long messageFlagId,
		long userId, long threadId, int flag,
		OrderByComparator orderByComparator)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = findByPrimaryKey(messageFlagId);

		Session session = null;

		try {
			session = openSession();

			MBMessageFlag[] array = new MBMessageFlagImpl[3];

			array[0] = getByU_T_F_PrevAndNext(session, mbMessageFlag, userId,
					threadId, flag, orderByComparator, true);

			array[1] = mbMessageFlag;

			array[2] = getByU_T_F_PrevAndNext(session, mbMessageFlag, userId,
					threadId, flag, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBMessageFlag getByU_T_F_PrevAndNext(Session session,
		MBMessageFlag mbMessageFlag, long userId, long threadId, int flag,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

		query.append(_FINDER_COLUMN_U_T_F_USERID_2);

		query.append(_FINDER_COLUMN_U_T_F_THREADID_2);

		query.append(_FINDER_COLUMN_U_T_F_FLAG_2);

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

		qPos.add(threadId);

		qPos.add(flag);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(mbMessageFlag);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBMessageFlag> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the message boards message flag where userId = &#63; and messageId = &#63; and flag = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchMessageFlagException} if it could not be found.
	 *
	 * @param userId the user ID
	 * @param messageId the message ID
	 * @param flag the flag
	 * @return the matching message boards message flag
	 * @throws com.liferay.portlet.messageboards.NoSuchMessageFlagException if a matching message boards message flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag findByU_M_F(long userId, long messageId, int flag)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = fetchByU_M_F(userId, messageId, flag);

		if (mbMessageFlag == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", messageId=");
			msg.append(messageId);

			msg.append(", flag=");
			msg.append(flag);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchMessageFlagException(msg.toString());
		}

		return mbMessageFlag;
	}

	/**
	 * Returns the message boards message flag where userId = &#63; and messageId = &#63; and flag = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param messageId the message ID
	 * @param flag the flag
	 * @return the matching message boards message flag, or <code>null</code> if a matching message boards message flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag fetchByU_M_F(long userId, long messageId, int flag)
		throws SystemException {
		return fetchByU_M_F(userId, messageId, flag, true);
	}

	/**
	 * Returns the message boards message flag where userId = &#63; and messageId = &#63; and flag = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param messageId the message ID
	 * @param flag the flag
	 * @return the matching message boards message flag, or <code>null</code> if a matching message boards message flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBMessageFlag fetchByU_M_F(long userId, long messageId, int flag,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { userId, messageId, flag };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_M_F,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_MBMESSAGEFLAG_WHERE);

			query.append(_FINDER_COLUMN_U_M_F_USERID_2);

			query.append(_FINDER_COLUMN_U_M_F_MESSAGEID_2);

			query.append(_FINDER_COLUMN_U_M_F_FLAG_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(messageId);

				qPos.add(flag);

				List<MBMessageFlag> list = q.list();

				result = list;

				MBMessageFlag mbMessageFlag = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_M_F,
						finderArgs, list);
				}
				else {
					mbMessageFlag = list.get(0);

					cacheResult(mbMessageFlag);

					if ((mbMessageFlag.getUserId() != userId) ||
							(mbMessageFlag.getMessageId() != messageId) ||
							(mbMessageFlag.getFlag() != flag)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_M_F,
							finderArgs, mbMessageFlag);
					}
				}

				return mbMessageFlag;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_M_F,
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
				return (MBMessageFlag)result;
			}
		}
	}

	/**
	 * Returns all the message boards message flags.
	 *
	 * @return the message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessageFlag> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards message flags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards message flags
	 * @param end the upper bound of the range of message boards message flags (not inclusive)
	 * @return the range of message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessageFlag> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards message flags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards message flags
	 * @param end the upper bound of the range of message boards message flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBMessageFlag> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<MBMessageFlag> list = (List<MBMessageFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_MBMESSAGEFLAG);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MBMESSAGEFLAG;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<MBMessageFlag>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<MBMessageFlag>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the message boards message flags where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (MBMessageFlag mbMessageFlag : findByUserId(userId)) {
			mbMessageFlagPersistence.remove(mbMessageFlag);
		}
	}

	/**
	 * Removes all the message boards message flags where threadId = &#63; from the database.
	 *
	 * @param threadId the thread ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByThreadId(long threadId) throws SystemException {
		for (MBMessageFlag mbMessageFlag : findByThreadId(threadId)) {
			mbMessageFlagPersistence.remove(mbMessageFlag);
		}
	}

	/**
	 * Removes all the message boards message flags where messageId = &#63; from the database.
	 *
	 * @param messageId the message ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByMessageId(long messageId) throws SystemException {
		for (MBMessageFlag mbMessageFlag : findByMessageId(messageId)) {
			mbMessageFlagPersistence.remove(mbMessageFlag);
		}
	}

	/**
	 * Removes all the message boards message flags where threadId = &#63; and flag = &#63; from the database.
	 *
	 * @param threadId the thread ID
	 * @param flag the flag
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByT_F(long threadId, int flag) throws SystemException {
		for (MBMessageFlag mbMessageFlag : findByT_F(threadId, flag)) {
			mbMessageFlagPersistence.remove(mbMessageFlag);
		}
	}

	/**
	 * Removes all the message boards message flags where messageId = &#63; and flag = &#63; from the database.
	 *
	 * @param messageId the message ID
	 * @param flag the flag
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByM_F(long messageId, int flag) throws SystemException {
		for (MBMessageFlag mbMessageFlag : findByM_F(messageId, flag)) {
			mbMessageFlagPersistence.remove(mbMessageFlag);
		}
	}

	/**
	 * Removes all the message boards message flags where userId = &#63; and threadId = &#63; and flag = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @param flag the flag
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU_T_F(long userId, long threadId, int flag)
		throws SystemException {
		for (MBMessageFlag mbMessageFlag : findByU_T_F(userId, threadId, flag)) {
			mbMessageFlagPersistence.remove(mbMessageFlag);
		}
	}

	/**
	 * Removes the message boards message flag where userId = &#63; and messageId = &#63; and flag = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param messageId the message ID
	 * @param flag the flag
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU_M_F(long userId, long messageId, int flag)
		throws NoSuchMessageFlagException, SystemException {
		MBMessageFlag mbMessageFlag = findByU_M_F(userId, messageId, flag);

		mbMessageFlagPersistence.remove(mbMessageFlag);
	}

	/**
	 * Removes all the message boards message flags from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (MBMessageFlag mbMessageFlag : findAll()) {
			mbMessageFlagPersistence.remove(mbMessageFlag);
		}
	}

	/**
	 * Returns the number of message boards message flags where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBMESSAGEFLAG_WHERE);

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
	 * Returns the number of message boards message flags where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the number of matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public int countByThreadId(long threadId) throws SystemException {
		Object[] finderArgs = new Object[] { threadId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_THREADID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBMESSAGEFLAG_WHERE);

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
	 * Returns the number of message boards message flags where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @return the number of matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public int countByMessageId(long messageId) throws SystemException {
		Object[] finderArgs = new Object[] { messageId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_MESSAGEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBMESSAGEFLAG_WHERE);

			query.append(_FINDER_COLUMN_MESSAGEID_MESSAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(messageId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_MESSAGEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message boards message flags where threadId = &#63; and flag = &#63;.
	 *
	 * @param threadId the thread ID
	 * @param flag the flag
	 * @return the number of matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public int countByT_F(long threadId, int flag) throws SystemException {
		Object[] finderArgs = new Object[] { threadId, flag };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBMESSAGEFLAG_WHERE);

			query.append(_FINDER_COLUMN_T_F_THREADID_2);

			query.append(_FINDER_COLUMN_T_F_FLAG_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				qPos.add(flag);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_F, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message boards message flags where messageId = &#63; and flag = &#63;.
	 *
	 * @param messageId the message ID
	 * @param flag the flag
	 * @return the number of matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public int countByM_F(long messageId, int flag) throws SystemException {
		Object[] finderArgs = new Object[] { messageId, flag };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_M_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBMESSAGEFLAG_WHERE);

			query.append(_FINDER_COLUMN_M_F_MESSAGEID_2);

			query.append(_FINDER_COLUMN_M_F_FLAG_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(messageId);

				qPos.add(flag);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_M_F, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message boards message flags where userId = &#63; and threadId = &#63; and flag = &#63;.
	 *
	 * @param userId the user ID
	 * @param threadId the thread ID
	 * @param flag the flag
	 * @return the number of matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_T_F(long userId, long threadId, int flag)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId, threadId, flag };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_T_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_MBMESSAGEFLAG_WHERE);

			query.append(_FINDER_COLUMN_U_T_F_USERID_2);

			query.append(_FINDER_COLUMN_U_T_F_THREADID_2);

			query.append(_FINDER_COLUMN_U_T_F_FLAG_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(threadId);

				qPos.add(flag);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_T_F,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message boards message flags where userId = &#63; and messageId = &#63; and flag = &#63;.
	 *
	 * @param userId the user ID
	 * @param messageId the message ID
	 * @param flag the flag
	 * @return the number of matching message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_M_F(long userId, long messageId, int flag)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId, messageId, flag };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_M_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_MBMESSAGEFLAG_WHERE);

			query.append(_FINDER_COLUMN_U_M_F_USERID_2);

			query.append(_FINDER_COLUMN_U_M_F_MESSAGEID_2);

			query.append(_FINDER_COLUMN_U_M_F_FLAG_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(messageId);

				qPos.add(flag);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_M_F,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of message boards message flags.
	 *
	 * @return the number of message boards message flags
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MBMESSAGEFLAG);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the message boards message flag persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.messageboards.model.MBMessageFlag")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<MBMessageFlag>> listenersList = new ArrayList<ModelListener<MBMessageFlag>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<MBMessageFlag>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(MBMessageFlagImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
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
	@BeanReference(type = MBMessageFlagPersistence.class)
	protected MBMessageFlagPersistence mbMessageFlagPersistence;
	@BeanReference(type = MBStatsUserPersistence.class)
	protected MBStatsUserPersistence mbStatsUserPersistence;
	@BeanReference(type = MBThreadPersistence.class)
	protected MBThreadPersistence mbThreadPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_MBMESSAGEFLAG = "SELECT mbMessageFlag FROM MBMessageFlag mbMessageFlag";
	private static final String _SQL_SELECT_MBMESSAGEFLAG_WHERE = "SELECT mbMessageFlag FROM MBMessageFlag mbMessageFlag WHERE ";
	private static final String _SQL_COUNT_MBMESSAGEFLAG = "SELECT COUNT(mbMessageFlag) FROM MBMessageFlag mbMessageFlag";
	private static final String _SQL_COUNT_MBMESSAGEFLAG_WHERE = "SELECT COUNT(mbMessageFlag) FROM MBMessageFlag mbMessageFlag WHERE ";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "mbMessageFlag.userId = ?";
	private static final String _FINDER_COLUMN_THREADID_THREADID_2 = "mbMessageFlag.threadId = ?";
	private static final String _FINDER_COLUMN_MESSAGEID_MESSAGEID_2 = "mbMessageFlag.messageId = ?";
	private static final String _FINDER_COLUMN_T_F_THREADID_2 = "mbMessageFlag.threadId = ? AND ";
	private static final String _FINDER_COLUMN_T_F_FLAG_2 = "mbMessageFlag.flag = ?";
	private static final String _FINDER_COLUMN_M_F_MESSAGEID_2 = "mbMessageFlag.messageId = ? AND ";
	private static final String _FINDER_COLUMN_M_F_FLAG_2 = "mbMessageFlag.flag = ?";
	private static final String _FINDER_COLUMN_U_T_F_USERID_2 = "mbMessageFlag.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_T_F_THREADID_2 = "mbMessageFlag.threadId = ? AND ";
	private static final String _FINDER_COLUMN_U_T_F_FLAG_2 = "mbMessageFlag.flag = ?";
	private static final String _FINDER_COLUMN_U_M_F_USERID_2 = "mbMessageFlag.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_M_F_MESSAGEID_2 = "mbMessageFlag.messageId = ? AND ";
	private static final String _FINDER_COLUMN_U_M_F_FLAG_2 = "mbMessageFlag.flag = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "mbMessageFlag.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MBMessageFlag exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MBMessageFlag exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static final MBMessageFlag _NULL_PLACE_HOLDER = new MBMessageFlagImpl();
	private static Log _log = LogFactoryUtil.getLog(MBMessageFlagPersistenceImpl.class);
}