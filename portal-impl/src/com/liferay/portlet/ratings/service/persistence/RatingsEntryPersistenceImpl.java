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

package com.liferay.portlet.ratings.service.persistence;

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

import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence;
import com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence;
import com.liferay.portlet.ratings.NoSuchEntryException;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.impl.RatingsEntryImpl;
import com.liferay.portlet.ratings.model.impl.RatingsEntryModelImpl;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the ratings entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RatingsEntryPersistence
 * @see RatingsEntryUtil
 * @generated
 */
public class RatingsEntryPersistenceImpl extends BasePersistenceImpl<RatingsEntry>
	implements RatingsEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link RatingsEntryUtil} to access the ratings entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = RatingsEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryModelImpl.FINDER_CACHE_ENABLED, RatingsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryModelImpl.FINDER_CACHE_ENABLED, RatingsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			RatingsEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			RatingsEntryModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_U_C_C = new FinderPath(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryModelImpl.FINDER_CACHE_ENABLED, RatingsEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			RatingsEntryModelImpl.USERID_COLUMN_BITMASK |
			RatingsEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			RatingsEntryModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_C_C = new FinderPath(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_S = new FinderPath(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryModelImpl.FINDER_CACHE_ENABLED, RatingsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Double.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_S = new FinderPath(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryModelImpl.FINDER_CACHE_ENABLED, RatingsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Double.class.getName()
			},
			RatingsEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			RatingsEntryModelImpl.CLASSPK_COLUMN_BITMASK |
			RatingsEntryModelImpl.SCORE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_S = new FinderPath(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Double.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryModelImpl.FINDER_CACHE_ENABLED, RatingsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryModelImpl.FINDER_CACHE_ENABLED, RatingsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the ratings entry in the entity cache if it is enabled.
	 *
	 * @param ratingsEntry the ratings entry
	 */
	public void cacheResult(RatingsEntry ratingsEntry) {
		EntityCacheUtil.putResult(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryImpl.class, ratingsEntry.getPrimaryKey(), ratingsEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C,
			new Object[] {
				Long.valueOf(ratingsEntry.getUserId()),
				Long.valueOf(ratingsEntry.getClassNameId()),
				Long.valueOf(ratingsEntry.getClassPK())
			}, ratingsEntry);

		ratingsEntry.resetOriginalValues();
	}

	/**
	 * Caches the ratings entries in the entity cache if it is enabled.
	 *
	 * @param ratingsEntries the ratings entries
	 */
	public void cacheResult(List<RatingsEntry> ratingsEntries) {
		for (RatingsEntry ratingsEntry : ratingsEntries) {
			if (EntityCacheUtil.getResult(
						RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
						RatingsEntryImpl.class, ratingsEntry.getPrimaryKey()) == null) {
				cacheResult(ratingsEntry);
			}
			else {
				ratingsEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ratings entries.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(RatingsEntryImpl.class.getName());
		}

		EntityCacheUtil.clearCache(RatingsEntryImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ratings entry.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RatingsEntry ratingsEntry) {
		EntityCacheUtil.removeResult(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryImpl.class, ratingsEntry.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(ratingsEntry);
	}

	@Override
	public void clearCache(List<RatingsEntry> ratingsEntries) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (RatingsEntry ratingsEntry : ratingsEntries) {
			EntityCacheUtil.removeResult(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
				RatingsEntryImpl.class, ratingsEntry.getPrimaryKey());

			clearUniqueFindersCache(ratingsEntry);
		}
	}

	protected void clearUniqueFindersCache(RatingsEntry ratingsEntry) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_C_C,
			new Object[] {
				Long.valueOf(ratingsEntry.getUserId()),
				Long.valueOf(ratingsEntry.getClassNameId()),
				Long.valueOf(ratingsEntry.getClassPK())
			});
	}

	/**
	 * Creates a new ratings entry with the primary key. Does not add the ratings entry to the database.
	 *
	 * @param entryId the primary key for the new ratings entry
	 * @return the new ratings entry
	 */
	public RatingsEntry create(long entryId) {
		RatingsEntry ratingsEntry = new RatingsEntryImpl();

		ratingsEntry.setNew(true);
		ratingsEntry.setPrimaryKey(entryId);

		return ratingsEntry;
	}

	/**
	 * Removes the ratings entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the ratings entry
	 * @return the ratings entry that was removed
	 * @throws com.liferay.portlet.ratings.NoSuchEntryException if a ratings entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsEntry remove(long entryId)
		throws NoSuchEntryException, SystemException {
		return remove(Long.valueOf(entryId));
	}

	/**
	 * Removes the ratings entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ratings entry
	 * @return the ratings entry that was removed
	 * @throws com.liferay.portlet.ratings.NoSuchEntryException if a ratings entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public RatingsEntry remove(Serializable primaryKey)
		throws NoSuchEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RatingsEntry ratingsEntry = (RatingsEntry)session.get(RatingsEntryImpl.class,
					primaryKey);

			if (ratingsEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ratingsEntry);
		}
		catch (NoSuchEntryException nsee) {
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
	protected RatingsEntry removeImpl(RatingsEntry ratingsEntry)
		throws SystemException {
		ratingsEntry = toUnwrappedModel(ratingsEntry);

		Session session = null;

		try {
			session = openSession();

			if (ratingsEntry.isCachedModel()) {
				ratingsEntry = (RatingsEntry)session.get(RatingsEntryImpl.class,
						ratingsEntry.getPrimaryKeyObj());
			}

			if (ratingsEntry != null) {
				session.delete(ratingsEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ratingsEntry != null) {
			clearCache(ratingsEntry);
		}

		return ratingsEntry;
	}

	@Override
	public RatingsEntry updateImpl(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry)
		throws SystemException {
		ratingsEntry = toUnwrappedModel(ratingsEntry);

		boolean isNew = ratingsEntry.isNew();

		RatingsEntryModelImpl ratingsEntryModelImpl = (RatingsEntryModelImpl)ratingsEntry;

		Session session = null;

		try {
			session = openSession();

			if (ratingsEntry.isNew()) {
				session.save(ratingsEntry);

				ratingsEntry.setNew(false);
			}
			else {
				session.merge(ratingsEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !RatingsEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((ratingsEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ratingsEntryModelImpl.getOriginalClassNameId()),
						Long.valueOf(ratingsEntryModelImpl.getOriginalClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						Long.valueOf(ratingsEntryModelImpl.getClassNameId()),
						Long.valueOf(ratingsEntryModelImpl.getClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}

			if ((ratingsEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ratingsEntryModelImpl.getOriginalClassNameId()),
						Long.valueOf(ratingsEntryModelImpl.getOriginalClassPK()),
						Double.valueOf(ratingsEntryModelImpl.getOriginalScore())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_S,
					args);

				args = new Object[] {
						Long.valueOf(ratingsEntryModelImpl.getClassNameId()),
						Long.valueOf(ratingsEntryModelImpl.getClassPK()),
						Double.valueOf(ratingsEntryModelImpl.getScore())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_S,
					args);
			}
		}

		EntityCacheUtil.putResult(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
			RatingsEntryImpl.class, ratingsEntry.getPrimaryKey(), ratingsEntry);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C,
				new Object[] {
					Long.valueOf(ratingsEntry.getUserId()),
					Long.valueOf(ratingsEntry.getClassNameId()),
					Long.valueOf(ratingsEntry.getClassPK())
				}, ratingsEntry);
		}
		else {
			if ((ratingsEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_U_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ratingsEntryModelImpl.getOriginalUserId()),
						Long.valueOf(ratingsEntryModelImpl.getOriginalClassNameId()),
						Long.valueOf(ratingsEntryModelImpl.getOriginalClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_C_C, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_C_C, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C,
					new Object[] {
						Long.valueOf(ratingsEntry.getUserId()),
						Long.valueOf(ratingsEntry.getClassNameId()),
						Long.valueOf(ratingsEntry.getClassPK())
					}, ratingsEntry);
			}
		}

		return ratingsEntry;
	}

	protected RatingsEntry toUnwrappedModel(RatingsEntry ratingsEntry) {
		if (ratingsEntry instanceof RatingsEntryImpl) {
			return ratingsEntry;
		}

		RatingsEntryImpl ratingsEntryImpl = new RatingsEntryImpl();

		ratingsEntryImpl.setNew(ratingsEntry.isNew());
		ratingsEntryImpl.setPrimaryKey(ratingsEntry.getPrimaryKey());

		ratingsEntryImpl.setEntryId(ratingsEntry.getEntryId());
		ratingsEntryImpl.setCompanyId(ratingsEntry.getCompanyId());
		ratingsEntryImpl.setUserId(ratingsEntry.getUserId());
		ratingsEntryImpl.setUserName(ratingsEntry.getUserName());
		ratingsEntryImpl.setCreateDate(ratingsEntry.getCreateDate());
		ratingsEntryImpl.setModifiedDate(ratingsEntry.getModifiedDate());
		ratingsEntryImpl.setClassNameId(ratingsEntry.getClassNameId());
		ratingsEntryImpl.setClassPK(ratingsEntry.getClassPK());
		ratingsEntryImpl.setScore(ratingsEntry.getScore());

		return ratingsEntryImpl;
	}

	/**
	 * Returns the ratings entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the ratings entry
	 * @return the ratings entry
	 * @throws com.liferay.portal.NoSuchModelException if a ratings entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public RatingsEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the ratings entry with the primary key or throws a {@link com.liferay.portlet.ratings.NoSuchEntryException} if it could not be found.
	 *
	 * @param entryId the primary key of the ratings entry
	 * @return the ratings entry
	 * @throws com.liferay.portlet.ratings.NoSuchEntryException if a ratings entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException, SystemException {
		RatingsEntry ratingsEntry = fetchByPrimaryKey(entryId);

		if (ratingsEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + entryId);
			}

			throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				entryId);
		}

		return ratingsEntry;
	}

	/**
	 * Returns the ratings entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ratings entry
	 * @return the ratings entry, or <code>null</code> if a ratings entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public RatingsEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the ratings entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the ratings entry
	 * @return the ratings entry, or <code>null</code> if a ratings entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsEntry fetchByPrimaryKey(long entryId)
		throws SystemException {
		RatingsEntry ratingsEntry = (RatingsEntry)EntityCacheUtil.getResult(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
				RatingsEntryImpl.class, entryId);

		if (ratingsEntry == _nullRatingsEntry) {
			return null;
		}

		if (ratingsEntry == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				ratingsEntry = (RatingsEntry)session.get(RatingsEntryImpl.class,
						Long.valueOf(entryId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (ratingsEntry != null) {
					cacheResult(ratingsEntry);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(RatingsEntryModelImpl.ENTITY_CACHE_ENABLED,
						RatingsEntryImpl.class, entryId, _nullRatingsEntry);
				}

				closeSession(session);
			}
		}

		return ratingsEntry;
	}

	/**
	 * Returns all the ratings entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching ratings entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RatingsEntry> findByC_C(long classNameId, long classPK)
		throws SystemException {
		return findByC_C(classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ratings entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @return the range of matching ratings entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RatingsEntry> findByC_C(long classNameId, long classPK,
		int start, int end) throws SystemException {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ratings entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ratings entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RatingsEntry> findByC_C(long classNameId, long classPK,
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

		List<RatingsEntry> list = (List<RatingsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (RatingsEntry ratingsEntry : list) {
				if ((classNameId != ratingsEntry.getClassNameId()) ||
						(classPK != ratingsEntry.getClassPK())) {
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

			query.append(_SQL_SELECT_RATINGSENTRY_WHERE);

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

				list = (List<RatingsEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ratings entry
	 * @throws com.liferay.portlet.ratings.NoSuchEntryException if a matching ratings entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsEntry findByC_C_First(long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		RatingsEntry ratingsEntry = fetchByC_C_First(classNameId, classPK,
				orderByComparator);

		if (ratingsEntry != null) {
			return ratingsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsEntry fetchByC_C_First(long classNameId, long classPK,
		OrderByComparator orderByComparator) throws SystemException {
		List<RatingsEntry> list = findByC_C(classNameId, classPK, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ratings entry
	 * @throws com.liferay.portlet.ratings.NoSuchEntryException if a matching ratings entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsEntry findByC_C_Last(long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		RatingsEntry ratingsEntry = fetchByC_C_Last(classNameId, classPK,
				orderByComparator);

		if (ratingsEntry != null) {
			return ratingsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsEntry fetchByC_C_Last(long classNameId, long classPK,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_C(classNameId, classPK);

		List<RatingsEntry> list = findByC_C(classNameId, classPK, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ratings entries before and after the current ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param entryId the primary key of the current ratings entry
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ratings entry
	 * @throws com.liferay.portlet.ratings.NoSuchEntryException if a ratings entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsEntry[] findByC_C_PrevAndNext(long entryId, long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		RatingsEntry ratingsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			RatingsEntry[] array = new RatingsEntryImpl[3];

			array[0] = getByC_C_PrevAndNext(session, ratingsEntry, classNameId,
					classPK, orderByComparator, true);

			array[1] = ratingsEntry;

			array[2] = getByC_C_PrevAndNext(session, ratingsEntry, classNameId,
					classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected RatingsEntry getByC_C_PrevAndNext(Session session,
		RatingsEntry ratingsEntry, long classNameId, long classPK,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RATINGSENTRY_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(ratingsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<RatingsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the ratings entry where userId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.ratings.NoSuchEntryException} if it could not be found.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching ratings entry
	 * @throws com.liferay.portlet.ratings.NoSuchEntryException if a matching ratings entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsEntry findByU_C_C(long userId, long classNameId, long classPK)
		throws NoSuchEntryException, SystemException {
		RatingsEntry ratingsEntry = fetchByU_C_C(userId, classNameId, classPK);

		if (ratingsEntry == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return ratingsEntry;
	}

	/**
	 * Returns the ratings entry where userId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsEntry fetchByU_C_C(long userId, long classNameId, long classPK)
		throws SystemException {
		return fetchByU_C_C(userId, classNameId, classPK, true);
	}

	/**
	 * Returns the ratings entry where userId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsEntry fetchByU_C_C(long userId, long classNameId,
		long classPK, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { userId, classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_C_C,
					finderArgs, this);
		}

		if (result instanceof RatingsEntry) {
			RatingsEntry ratingsEntry = (RatingsEntry)result;

			if ((userId != ratingsEntry.getUserId()) ||
					(classNameId != ratingsEntry.getClassNameId()) ||
					(classPK != ratingsEntry.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_RATINGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_U_C_C_USERID_2);

			query.append(_FINDER_COLUMN_U_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_U_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<RatingsEntry> list = q.list();

				result = list;

				RatingsEntry ratingsEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C,
						finderArgs, list);
				}
				else {
					ratingsEntry = list.get(0);

					cacheResult(ratingsEntry);

					if ((ratingsEntry.getUserId() != userId) ||
							(ratingsEntry.getClassNameId() != classNameId) ||
							(ratingsEntry.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C,
							finderArgs, ratingsEntry);
					}
				}

				return ratingsEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_C_C,
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
				return (RatingsEntry)result;
			}
		}
	}

	/**
	 * Returns all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param score the score
	 * @return the matching ratings entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RatingsEntry> findByC_C_S(long classNameId, long classPK,
		double score) throws SystemException {
		return findByC_C_S(classNameId, classPK, score, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param score the score
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @return the range of matching ratings entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RatingsEntry> findByC_C_S(long classNameId, long classPK,
		double score, int start, int end) throws SystemException {
		return findByC_C_S(classNameId, classPK, score, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param score the score
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ratings entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RatingsEntry> findByC_C_S(long classNameId, long classPK,
		double score, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_S;
			finderArgs = new Object[] { classNameId, classPK, score };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_S;
			finderArgs = new Object[] {
					classNameId, classPK, score,
					
					start, end, orderByComparator
				};
		}

		List<RatingsEntry> list = (List<RatingsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (RatingsEntry ratingsEntry : list) {
				if ((classNameId != ratingsEntry.getClassNameId()) ||
						(classPK != ratingsEntry.getClassPK()) ||
						(score != ratingsEntry.getScore())) {
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
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_RATINGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_S_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_S_SCORE_2);

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

				qPos.add(score);

				list = (List<RatingsEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param score the score
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ratings entry
	 * @throws com.liferay.portlet.ratings.NoSuchEntryException if a matching ratings entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsEntry findByC_C_S_First(long classNameId, long classPK,
		double score, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		RatingsEntry ratingsEntry = fetchByC_C_S_First(classNameId, classPK,
				score, orderByComparator);

		if (ratingsEntry != null) {
			return ratingsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", score=");
		msg.append(score);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param score the score
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsEntry fetchByC_C_S_First(long classNameId, long classPK,
		double score, OrderByComparator orderByComparator)
		throws SystemException {
		List<RatingsEntry> list = findByC_C_S(classNameId, classPK, score, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param score the score
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ratings entry
	 * @throws com.liferay.portlet.ratings.NoSuchEntryException if a matching ratings entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsEntry findByC_C_S_Last(long classNameId, long classPK,
		double score, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		RatingsEntry ratingsEntry = fetchByC_C_S_Last(classNameId, classPK,
				score, orderByComparator);

		if (ratingsEntry != null) {
			return ratingsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", score=");
		msg.append(score);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param score the score
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsEntry fetchByC_C_S_Last(long classNameId, long classPK,
		double score, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByC_C_S(classNameId, classPK, score);

		List<RatingsEntry> list = findByC_C_S(classNameId, classPK, score,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ratings entries before and after the current ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	 *
	 * @param entryId the primary key of the current ratings entry
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param score the score
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ratings entry
	 * @throws com.liferay.portlet.ratings.NoSuchEntryException if a ratings entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsEntry[] findByC_C_S_PrevAndNext(long entryId,
		long classNameId, long classPK, double score,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		RatingsEntry ratingsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			RatingsEntry[] array = new RatingsEntryImpl[3];

			array[0] = getByC_C_S_PrevAndNext(session, ratingsEntry,
					classNameId, classPK, score, orderByComparator, true);

			array[1] = ratingsEntry;

			array[2] = getByC_C_S_PrevAndNext(session, ratingsEntry,
					classNameId, classPK, score, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected RatingsEntry getByC_C_S_PrevAndNext(Session session,
		RatingsEntry ratingsEntry, long classNameId, long classPK,
		double score, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RATINGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_C_S_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_S_CLASSPK_2);

		query.append(_FINDER_COLUMN_C_C_S_SCORE_2);

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

		qPos.add(score);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ratingsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<RatingsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the ratings entries.
	 *
	 * @return the ratings entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RatingsEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ratings entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @return the range of ratings entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RatingsEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ratings entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ratings entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<RatingsEntry> findAll(int start, int end,
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

		List<RatingsEntry> list = (List<RatingsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_RATINGSENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_RATINGSENTRY;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<RatingsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<RatingsEntry>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the ratings entries where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_C(long classNameId, long classPK)
		throws SystemException {
		for (RatingsEntry ratingsEntry : findByC_C(classNameId, classPK)) {
			remove(ratingsEntry);
		}
	}

	/**
	 * Removes the ratings entry where userId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the ratings entry that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public RatingsEntry removeByU_C_C(long userId, long classNameId,
		long classPK) throws NoSuchEntryException, SystemException {
		RatingsEntry ratingsEntry = findByU_C_C(userId, classNameId, classPK);

		return remove(ratingsEntry);
	}

	/**
	 * Removes all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param score the score
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_C_S(long classNameId, long classPK, double score)
		throws SystemException {
		for (RatingsEntry ratingsEntry : findByC_C_S(classNameId, classPK, score)) {
			remove(ratingsEntry);
		}
	}

	/**
	 * Removes all the ratings entries from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (RatingsEntry ratingsEntry : findAll()) {
			remove(ratingsEntry);
		}
	}

	/**
	 * Returns the number of ratings entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching ratings entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_RATINGSENTRY_WHERE);

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
	 * Returns the number of ratings entries where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching ratings entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_C_C(long userId, long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId, classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_C_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_RATINGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_U_C_C_USERID_2);

			query.append(_FINDER_COLUMN_U_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_U_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_C_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param score the score
	 * @return the number of matching ratings entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C_S(long classNameId, long classPK, double score)
		throws SystemException {
		Object[] finderArgs = new Object[] { classNameId, classPK, score };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_RATINGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_S_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_S_SCORE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(score);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of ratings entries.
	 *
	 * @return the number of ratings entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_RATINGSENTRY);

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
	 * Initializes the ratings entry persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.ratings.model.RatingsEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<RatingsEntry>> listenersList = new ArrayList<ModelListener<RatingsEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<RatingsEntry>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(RatingsEntryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = RatingsEntryPersistence.class)
	protected RatingsEntryPersistence ratingsEntryPersistence;
	@BeanReference(type = RatingsStatsPersistence.class)
	protected RatingsStatsPersistence ratingsStatsPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = BlogsEntryPersistence.class)
	protected BlogsEntryPersistence blogsEntryPersistence;
	@BeanReference(type = BlogsStatsUserPersistence.class)
	protected BlogsStatsUserPersistence blogsStatsUserPersistence;
	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	private static final String _SQL_SELECT_RATINGSENTRY = "SELECT ratingsEntry FROM RatingsEntry ratingsEntry";
	private static final String _SQL_SELECT_RATINGSENTRY_WHERE = "SELECT ratingsEntry FROM RatingsEntry ratingsEntry WHERE ";
	private static final String _SQL_COUNT_RATINGSENTRY = "SELECT COUNT(ratingsEntry) FROM RatingsEntry ratingsEntry";
	private static final String _SQL_COUNT_RATINGSENTRY_WHERE = "SELECT COUNT(ratingsEntry) FROM RatingsEntry ratingsEntry WHERE ";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "ratingsEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "ratingsEntry.classPK = ?";
	private static final String _FINDER_COLUMN_U_C_C_USERID_2 = "ratingsEntry.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_CLASSNAMEID_2 = "ratingsEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_CLASSPK_2 = "ratingsEntry.classPK = ?";
	private static final String _FINDER_COLUMN_C_C_S_CLASSNAMEID_2 = "ratingsEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_S_CLASSPK_2 = "ratingsEntry.classPK = ? AND ";
	private static final String _FINDER_COLUMN_C_C_S_SCORE_2 = "ratingsEntry.score = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ratingsEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No RatingsEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No RatingsEntry exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(RatingsEntryPersistenceImpl.class);
	private static RatingsEntry _nullRatingsEntry = new RatingsEntryImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<RatingsEntry> toCacheModel() {
				return _nullRatingsEntryCacheModel;
			}
		};

	private static CacheModel<RatingsEntry> _nullRatingsEntryCacheModel = new CacheModel<RatingsEntry>() {
			public RatingsEntry toEntityModel() {
				return _nullRatingsEntry;
			}
		};
}