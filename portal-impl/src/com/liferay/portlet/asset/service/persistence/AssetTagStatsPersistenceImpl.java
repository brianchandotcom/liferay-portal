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

package com.liferay.portlet.asset.service.persistence;

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

import com.liferay.portlet.asset.NoSuchTagStatsException;
import com.liferay.portlet.asset.model.AssetTagStats;
import com.liferay.portlet.asset.model.impl.AssetTagStatsImpl;
import com.liferay.portlet.asset.model.impl.AssetTagStatsModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the asset tag stats service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagStatsPersistence
 * @see AssetTagStatsUtil
 * @generated
 */
public class AssetTagStatsPersistenceImpl extends BasePersistenceImpl<AssetTagStats>
	implements AssetTagStatsPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AssetTagStatsUtil} to access the asset tag stats persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AssetTagStatsImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_TAGID = new FinderPath(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsModelImpl.FINDER_CACHE_ENABLED,
			AssetTagStatsImpl.class, FINDER_CLASS_NAME_LIST, "findByTagId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_TAGID = new FinderPath(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByTagId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_CLASSNAMEID = new FinderPath(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsModelImpl.FINDER_CACHE_ENABLED,
			AssetTagStatsImpl.class, FINDER_CLASS_NAME_LIST,
			"findByClassNameId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_CLASSNAMEID = new FinderPath(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByClassNameId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_T_C = new FinderPath(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsModelImpl.FINDER_CACHE_ENABLED,
			AssetTagStatsImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByT_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_T_C = new FinderPath(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByT_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsModelImpl.FINDER_CACHE_ENABLED,
			AssetTagStatsImpl.class, FINDER_CLASS_NAME_LIST, "findAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the asset tag stats in the entity cache if it is enabled.
	 *
	 * @param assetTagStats the asset tag stats
	 */
	public void cacheResult(AssetTagStats assetTagStats) {
		EntityCacheUtil.putResult(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsImpl.class, assetTagStats.getPrimaryKey(),
			assetTagStats);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C,
			new Object[] {
				Long.valueOf(assetTagStats.getTagId()),
				Long.valueOf(assetTagStats.getClassNameId())
			}, assetTagStats);

		assetTagStats.resetOriginalValues();
	}

	/**
	 * Caches the asset tag statses in the entity cache if it is enabled.
	 *
	 * @param assetTagStatses the asset tag statses
	 */
	public void cacheResult(List<AssetTagStats> assetTagStatses) {
		for (AssetTagStats assetTagStats : assetTagStatses) {
			if (EntityCacheUtil.getResult(
						AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
						AssetTagStatsImpl.class, assetTagStats.getPrimaryKey()) == null) {
				cacheResult(assetTagStats);
			}
		}
	}

	/**
	 * Clears the cache for all asset tag statses.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(AssetTagStatsImpl.class.getName());
		}

		EntityCacheUtil.clearCache(AssetTagStatsImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the asset tag stats.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetTagStats assetTagStats) {
		EntityCacheUtil.removeResult(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsImpl.class, assetTagStats.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL, FINDER_ARGS_EMPTY);

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_C,
			new Object[] {
				Long.valueOf(assetTagStats.getTagId()),
				Long.valueOf(assetTagStats.getClassNameId())
			});
	}

	/**
	 * Creates a new asset tag stats with the primary key. Does not add the asset tag stats to the database.
	 *
	 * @param tagStatsId the primary key for the new asset tag stats
	 * @return the new asset tag stats
	 */
	public AssetTagStats create(long tagStatsId) {
		AssetTagStats assetTagStats = new AssetTagStatsImpl();

		assetTagStats.setNew(true);
		assetTagStats.setPrimaryKey(tagStatsId);

		return assetTagStats;
	}

	/**
	 * Removes the asset tag stats with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset tag stats
	 * @return the asset tag stats that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a asset tag stats with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetTagStats remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the asset tag stats with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param tagStatsId the primary key of the asset tag stats
	 * @return the asset tag stats that was removed
	 * @throws com.liferay.portlet.asset.NoSuchTagStatsException if a asset tag stats with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagStats remove(long tagStatsId)
		throws NoSuchTagStatsException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AssetTagStats assetTagStats = (AssetTagStats)session.get(AssetTagStatsImpl.class,
					Long.valueOf(tagStatsId));

			if (assetTagStats == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + tagStatsId);
				}

				throw new NoSuchTagStatsException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					tagStatsId);
			}

			return assetTagStatsPersistence.remove(assetTagStats);
		}
		catch (NoSuchTagStatsException nsee) {
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
	 * Removes the asset tag stats from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetTagStats the asset tag stats
	 * @return the asset tag stats that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetTagStats remove(AssetTagStats assetTagStats)
		throws SystemException {
		return super.remove(assetTagStats);
	}

	@Override
	protected AssetTagStats removeImpl(AssetTagStats assetTagStats)
		throws SystemException {
		assetTagStats = toUnwrappedModel(assetTagStats);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, assetTagStats);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		AssetTagStatsModelImpl assetTagStatsModelImpl = (AssetTagStatsModelImpl)assetTagStats;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_C,
			new Object[] {
				Long.valueOf(assetTagStatsModelImpl.getTagId()),
				Long.valueOf(assetTagStatsModelImpl.getClassNameId())
			});

		EntityCacheUtil.removeResult(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsImpl.class, assetTagStats.getPrimaryKey());

		return assetTagStats;
	}

	@Override
	public AssetTagStats updateImpl(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats,
		boolean merge) throws SystemException {
		assetTagStats = toUnwrappedModel(assetTagStats);

		boolean isNew = assetTagStats.isNew();

		AssetTagStatsModelImpl assetTagStatsModelImpl = (AssetTagStatsModelImpl)assetTagStats;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, assetTagStats, merge);

			assetTagStats.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagStatsImpl.class, assetTagStats.getPrimaryKey(),
			assetTagStats);

		if (!isNew &&
				((assetTagStats.getTagId() != assetTagStatsModelImpl.getOriginalTagId()) ||
				(assetTagStats.getClassNameId() != assetTagStatsModelImpl.getOriginalClassNameId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_C,
				new Object[] {
					Long.valueOf(assetTagStatsModelImpl.getOriginalTagId()),
					Long.valueOf(
						assetTagStatsModelImpl.getOriginalClassNameId())
				});
		}

		if (isNew ||
				((assetTagStats.getTagId() != assetTagStatsModelImpl.getOriginalTagId()) ||
				(assetTagStats.getClassNameId() != assetTagStatsModelImpl.getOriginalClassNameId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C,
				new Object[] {
					Long.valueOf(assetTagStats.getTagId()),
					Long.valueOf(assetTagStats.getClassNameId())
				}, assetTagStats);
		}

		return assetTagStats;
	}

	protected AssetTagStats toUnwrappedModel(AssetTagStats assetTagStats) {
		if (assetTagStats instanceof AssetTagStatsImpl) {
			return assetTagStats;
		}

		AssetTagStatsImpl assetTagStatsImpl = new AssetTagStatsImpl();

		assetTagStatsImpl.setNew(assetTagStats.isNew());
		assetTagStatsImpl.setPrimaryKey(assetTagStats.getPrimaryKey());

		assetTagStatsImpl.setTagStatsId(assetTagStats.getTagStatsId());
		assetTagStatsImpl.setTagId(assetTagStats.getTagId());
		assetTagStatsImpl.setClassNameId(assetTagStats.getClassNameId());
		assetTagStatsImpl.setAssetCount(assetTagStats.getAssetCount());

		return assetTagStatsImpl;
	}

	/**
	 * Returns the asset tag stats with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset tag stats
	 * @return the asset tag stats
	 * @throws com.liferay.portal.NoSuchModelException if a asset tag stats with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetTagStats findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the asset tag stats with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchTagStatsException} if it could not be found.
	 *
	 * @param tagStatsId the primary key of the asset tag stats
	 * @return the asset tag stats
	 * @throws com.liferay.portlet.asset.NoSuchTagStatsException if a asset tag stats with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagStats findByPrimaryKey(long tagStatsId)
		throws NoSuchTagStatsException, SystemException {
		AssetTagStats assetTagStats = fetchByPrimaryKey(tagStatsId);

		if (assetTagStats == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + tagStatsId);
			}

			throw new NoSuchTagStatsException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				tagStatsId);
		}

		return assetTagStats;
	}

	/**
	 * Returns the asset tag stats with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset tag stats
	 * @return the asset tag stats, or <code>null</code> if a asset tag stats with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetTagStats fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the asset tag stats with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param tagStatsId the primary key of the asset tag stats
	 * @return the asset tag stats, or <code>null</code> if a asset tag stats with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagStats fetchByPrimaryKey(long tagStatsId)
		throws SystemException {
		AssetTagStats assetTagStats = (AssetTagStats)EntityCacheUtil.getResult(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
				AssetTagStatsImpl.class, tagStatsId);

		if (assetTagStats == _nullAssetTagStats) {
			return null;
		}

		if (assetTagStats == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				assetTagStats = (AssetTagStats)session.get(AssetTagStatsImpl.class,
						Long.valueOf(tagStatsId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (assetTagStats != null) {
					cacheResult(assetTagStats);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(AssetTagStatsModelImpl.ENTITY_CACHE_ENABLED,
						AssetTagStatsImpl.class, tagStatsId, _nullAssetTagStats);
				}

				closeSession(session);
			}
		}

		return assetTagStats;
	}

	/**
	 * Returns all the asset tag statses where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @return the matching asset tag statses
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagStats> findByTagId(long tagId)
		throws SystemException {
		return findByTagId(tagId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset tag statses where tagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param tagId the tag ID
	 * @param start the lower bound of the range of asset tag statses
	 * @param end the upper bound of the range of asset tag statses (not inclusive)
	 * @return the range of matching asset tag statses
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagStats> findByTagId(long tagId, int start, int end)
		throws SystemException {
		return findByTagId(tagId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tag statses where tagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param tagId the tag ID
	 * @param start the lower bound of the range of asset tag statses
	 * @param end the upper bound of the range of asset tag statses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tag statses
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagStats> findByTagId(long tagId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { tagId, start, end, orderByComparator };

		List<AssetTagStats> list = (List<AssetTagStats>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_TAGID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ASSETTAGSTATS_WHERE);

			query.append(_FINDER_COLUMN_TAGID_TAGID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetTagStatsModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tagId);

				list = (List<AssetTagStats>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_TAGID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_TAGID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset tag stats in the ordered set where tagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param tagId the tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag stats
	 * @throws com.liferay.portlet.asset.NoSuchTagStatsException if a matching asset tag stats could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagStats findByTagId_First(long tagId,
		OrderByComparator orderByComparator)
		throws NoSuchTagStatsException, SystemException {
		List<AssetTagStats> list = findByTagId(tagId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tagId=");
			msg.append(tagId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTagStatsException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last asset tag stats in the ordered set where tagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param tagId the tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag stats
	 * @throws com.liferay.portlet.asset.NoSuchTagStatsException if a matching asset tag stats could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagStats findByTagId_Last(long tagId,
		OrderByComparator orderByComparator)
		throws NoSuchTagStatsException, SystemException {
		int count = countByTagId(tagId);

		List<AssetTagStats> list = findByTagId(tagId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tagId=");
			msg.append(tagId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTagStatsException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the asset tag statses before and after the current asset tag stats in the ordered set where tagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param tagStatsId the primary key of the current asset tag stats
	 * @param tagId the tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset tag stats
	 * @throws com.liferay.portlet.asset.NoSuchTagStatsException if a asset tag stats with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagStats[] findByTagId_PrevAndNext(long tagStatsId, long tagId,
		OrderByComparator orderByComparator)
		throws NoSuchTagStatsException, SystemException {
		AssetTagStats assetTagStats = findByPrimaryKey(tagStatsId);

		Session session = null;

		try {
			session = openSession();

			AssetTagStats[] array = new AssetTagStatsImpl[3];

			array[0] = getByTagId_PrevAndNext(session, assetTagStats, tagId,
					orderByComparator, true);

			array[1] = assetTagStats;

			array[2] = getByTagId_PrevAndNext(session, assetTagStats, tagId,
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

	protected AssetTagStats getByTagId_PrevAndNext(Session session,
		AssetTagStats assetTagStats, long tagId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETTAGSTATS_WHERE);

		query.append(_FINDER_COLUMN_TAGID_TAGID_2);

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

		else {
			query.append(AssetTagStatsModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(tagId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(assetTagStats);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetTagStats> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset tag statses where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the matching asset tag statses
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagStats> findByClassNameId(long classNameId)
		throws SystemException {
		return findByClassNameId(classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset tag statses where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of asset tag statses
	 * @param end the upper bound of the range of asset tag statses (not inclusive)
	 * @return the range of matching asset tag statses
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagStats> findByClassNameId(long classNameId, int start,
		int end) throws SystemException {
		return findByClassNameId(classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tag statses where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of asset tag statses
	 * @param end the upper bound of the range of asset tag statses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tag statses
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagStats> findByClassNameId(long classNameId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				classNameId,
				
				start, end, orderByComparator
			};

		List<AssetTagStats> list = (List<AssetTagStats>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_CLASSNAMEID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ASSETTAGSTATS_WHERE);

			query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetTagStatsModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				list = (List<AssetTagStats>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_CLASSNAMEID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_CLASSNAMEID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset tag stats in the ordered set where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag stats
	 * @throws com.liferay.portlet.asset.NoSuchTagStatsException if a matching asset tag stats could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagStats findByClassNameId_First(long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchTagStatsException, SystemException {
		List<AssetTagStats> list = findByClassNameId(classNameId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTagStatsException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last asset tag stats in the ordered set where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag stats
	 * @throws com.liferay.portlet.asset.NoSuchTagStatsException if a matching asset tag stats could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagStats findByClassNameId_Last(long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchTagStatsException, SystemException {
		int count = countByClassNameId(classNameId);

		List<AssetTagStats> list = findByClassNameId(classNameId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTagStatsException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the asset tag statses before and after the current asset tag stats in the ordered set where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param tagStatsId the primary key of the current asset tag stats
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset tag stats
	 * @throws com.liferay.portlet.asset.NoSuchTagStatsException if a asset tag stats with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagStats[] findByClassNameId_PrevAndNext(long tagStatsId,
		long classNameId, OrderByComparator orderByComparator)
		throws NoSuchTagStatsException, SystemException {
		AssetTagStats assetTagStats = findByPrimaryKey(tagStatsId);

		Session session = null;

		try {
			session = openSession();

			AssetTagStats[] array = new AssetTagStatsImpl[3];

			array[0] = getByClassNameId_PrevAndNext(session, assetTagStats,
					classNameId, orderByComparator, true);

			array[1] = assetTagStats;

			array[2] = getByClassNameId_PrevAndNext(session, assetTagStats,
					classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetTagStats getByClassNameId_PrevAndNext(Session session,
		AssetTagStats assetTagStats, long classNameId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETTAGSTATS_WHERE);

		query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

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

		else {
			query.append(AssetTagStatsModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(assetTagStats);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetTagStats> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the asset tag stats where tagId = &#63; and classNameId = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchTagStatsException} if it could not be found.
	 *
	 * @param tagId the tag ID
	 * @param classNameId the class name ID
	 * @return the matching asset tag stats
	 * @throws com.liferay.portlet.asset.NoSuchTagStatsException if a matching asset tag stats could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagStats findByT_C(long tagId, long classNameId)
		throws NoSuchTagStatsException, SystemException {
		AssetTagStats assetTagStats = fetchByT_C(tagId, classNameId);

		if (assetTagStats == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tagId=");
			msg.append(tagId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTagStatsException(msg.toString());
		}

		return assetTagStats;
	}

	/**
	 * Returns the asset tag stats where tagId = &#63; and classNameId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param tagId the tag ID
	 * @param classNameId the class name ID
	 * @return the matching asset tag stats, or <code>null</code> if a matching asset tag stats could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagStats fetchByT_C(long tagId, long classNameId)
		throws SystemException {
		return fetchByT_C(tagId, classNameId, true);
	}

	/**
	 * Returns the asset tag stats where tagId = &#63; and classNameId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param tagId the tag ID
	 * @param classNameId the class name ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching asset tag stats, or <code>null</code> if a matching asset tag stats could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagStats fetchByT_C(long tagId, long classNameId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { tagId, classNameId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_T_C,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ASSETTAGSTATS_WHERE);

			query.append(_FINDER_COLUMN_T_C_TAGID_2);

			query.append(_FINDER_COLUMN_T_C_CLASSNAMEID_2);

			query.append(AssetTagStatsModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tagId);

				qPos.add(classNameId);

				List<AssetTagStats> list = q.list();

				result = list;

				AssetTagStats assetTagStats = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C,
						finderArgs, list);
				}
				else {
					assetTagStats = list.get(0);

					cacheResult(assetTagStats);

					if ((assetTagStats.getTagId() != tagId) ||
							(assetTagStats.getClassNameId() != classNameId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C,
							finderArgs, assetTagStats);
					}
				}

				return assetTagStats;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_C,
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
				return (AssetTagStats)result;
			}
		}
	}

	/**
	 * Returns all the asset tag statses.
	 *
	 * @return the asset tag statses
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagStats> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset tag statses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset tag statses
	 * @param end the upper bound of the range of asset tag statses (not inclusive)
	 * @return the range of asset tag statses
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagStats> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tag statses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset tag statses
	 * @param end the upper bound of the range of asset tag statses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset tag statses
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagStats> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		List<AssetTagStats> list = (List<AssetTagStats>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ASSETTAGSTATS);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETTAGSTATS.concat(AssetTagStatsModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<AssetTagStats>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AssetTagStats>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the asset tag statses where tagId = &#63; from the database.
	 *
	 * @param tagId the tag ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByTagId(long tagId) throws SystemException {
		for (AssetTagStats assetTagStats : findByTagId(tagId)) {
			assetTagStatsPersistence.remove(assetTagStats);
		}
	}

	/**
	 * Removes all the asset tag statses where classNameId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByClassNameId(long classNameId) throws SystemException {
		for (AssetTagStats assetTagStats : findByClassNameId(classNameId)) {
			assetTagStatsPersistence.remove(assetTagStats);
		}
	}

	/**
	 * Removes the asset tag stats where tagId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param tagId the tag ID
	 * @param classNameId the class name ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByT_C(long tagId, long classNameId)
		throws NoSuchTagStatsException, SystemException {
		AssetTagStats assetTagStats = findByT_C(tagId, classNameId);

		assetTagStatsPersistence.remove(assetTagStats);
	}

	/**
	 * Removes all the asset tag statses from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (AssetTagStats assetTagStats : findAll()) {
			assetTagStatsPersistence.remove(assetTagStats);
		}
	}

	/**
	 * Returns the number of asset tag statses where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @return the number of matching asset tag statses
	 * @throws SystemException if a system exception occurred
	 */
	public int countByTagId(long tagId) throws SystemException {
		Object[] finderArgs = new Object[] { tagId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_TAGID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETTAGSTATS_WHERE);

			query.append(_FINDER_COLUMN_TAGID_TAGID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tagId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_TAGID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset tag statses where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the number of matching asset tag statses
	 * @throws SystemException if a system exception occurred
	 */
	public int countByClassNameId(long classNameId) throws SystemException {
		Object[] finderArgs = new Object[] { classNameId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CLASSNAMEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETTAGSTATS_WHERE);

			query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CLASSNAMEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset tag statses where tagId = &#63; and classNameId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @param classNameId the class name ID
	 * @return the number of matching asset tag statses
	 * @throws SystemException if a system exception occurred
	 */
	public int countByT_C(long tagId, long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] { tagId, classNameId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETTAGSTATS_WHERE);

			query.append(_FINDER_COLUMN_T_C_TAGID_2);

			query.append(_FINDER_COLUMN_T_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tagId);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset tag statses.
	 *
	 * @return the number of asset tag statses
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETTAGSTATS);

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
	 * Initializes the asset tag stats persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.asset.model.AssetTagStats")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AssetTagStats>> listenersList = new ArrayList<ModelListener<AssetTagStats>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AssetTagStats>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(AssetTagStatsImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = AssetCategoryPersistence.class)
	protected AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(type = AssetCategoryPropertyPersistence.class)
	protected AssetCategoryPropertyPersistence assetCategoryPropertyPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = AssetLinkPersistence.class)
	protected AssetLinkPersistence assetLinkPersistence;
	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(type = AssetTagPropertyPersistence.class)
	protected AssetTagPropertyPersistence assetTagPropertyPersistence;
	@BeanReference(type = AssetTagStatsPersistence.class)
	protected AssetTagStatsPersistence assetTagStatsPersistence;
	@BeanReference(type = AssetVocabularyPersistence.class)
	protected AssetVocabularyPersistence assetVocabularyPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_ASSETTAGSTATS = "SELECT assetTagStats FROM AssetTagStats assetTagStats";
	private static final String _SQL_SELECT_ASSETTAGSTATS_WHERE = "SELECT assetTagStats FROM AssetTagStats assetTagStats WHERE ";
	private static final String _SQL_COUNT_ASSETTAGSTATS = "SELECT COUNT(assetTagStats) FROM AssetTagStats assetTagStats";
	private static final String _SQL_COUNT_ASSETTAGSTATS_WHERE = "SELECT COUNT(assetTagStats) FROM AssetTagStats assetTagStats WHERE ";
	private static final String _FINDER_COLUMN_TAGID_TAGID_2 = "assetTagStats.tagId = ?";
	private static final String _FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2 = "assetTagStats.classNameId = ?";
	private static final String _FINDER_COLUMN_T_C_TAGID_2 = "assetTagStats.tagId = ? AND ";
	private static final String _FINDER_COLUMN_T_C_CLASSNAMEID_2 = "assetTagStats.classNameId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetTagStats.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetTagStats exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetTagStats exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(AssetTagStatsPersistenceImpl.class);
	private static AssetTagStats _nullAssetTagStats = new AssetTagStatsImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<AssetTagStats> toCacheModel() {
				return _nullAssetTagStatsCacheModel;
			}
		};

	private static CacheModel<AssetTagStats> _nullAssetTagStatsCacheModel = new CacheModel<AssetTagStats>() {
			public AssetTagStats toEntityModel() {
				return _nullAssetTagStats;
			}
		};
}