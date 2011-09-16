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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.social.NoSuchEquityHistoryException;
import com.liferay.portlet.social.model.SocialEquityHistory;
import com.liferay.portlet.social.model.impl.SocialEquityHistoryImpl;
import com.liferay.portlet.social.model.impl.SocialEquityHistoryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the social equity history service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityHistoryPersistence
 * @see SocialEquityHistoryUtil
 * @generated
 */
public class SocialEquityHistoryPersistenceImpl extends BasePersistenceImpl<SocialEquityHistory>
	implements SocialEquityHistoryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SocialEquityHistoryUtil} to access the social equity history persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SocialEquityHistoryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SocialEquityHistoryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityHistoryModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityHistoryImpl.class, FINDER_CLASS_NAME_LIST, "findAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialEquityHistoryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityHistoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the social equity history in the entity cache if it is enabled.
	 *
	 * @param socialEquityHistory the social equity history
	 */
	public void cacheResult(SocialEquityHistory socialEquityHistory) {
		EntityCacheUtil.putResult(SocialEquityHistoryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityHistoryImpl.class, socialEquityHistory.getPrimaryKey(),
			socialEquityHistory);

		socialEquityHistory.resetOriginalValues();
	}

	/**
	 * Caches the social equity histories in the entity cache if it is enabled.
	 *
	 * @param socialEquityHistories the social equity histories
	 */
	public void cacheResult(List<SocialEquityHistory> socialEquityHistories) {
		for (SocialEquityHistory socialEquityHistory : socialEquityHistories) {
			if (EntityCacheUtil.getResult(
						SocialEquityHistoryModelImpl.ENTITY_CACHE_ENABLED,
						SocialEquityHistoryImpl.class,
						socialEquityHistory.getPrimaryKey()) == null) {
				cacheResult(socialEquityHistory);
			}
		}
	}

	/**
	 * Clears the cache for all social equity histories.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SocialEquityHistoryImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SocialEquityHistoryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the social equity history.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SocialEquityHistory socialEquityHistory) {
		EntityCacheUtil.removeResult(SocialEquityHistoryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityHistoryImpl.class, socialEquityHistory.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL, FINDER_ARGS_EMPTY);
	}

	/**
	 * Creates a new social equity history with the primary key. Does not add the social equity history to the database.
	 *
	 * @param equityHistoryId the primary key for the new social equity history
	 * @return the new social equity history
	 */
	public SocialEquityHistory create(long equityHistoryId) {
		SocialEquityHistory socialEquityHistory = new SocialEquityHistoryImpl();

		socialEquityHistory.setNew(true);
		socialEquityHistory.setPrimaryKey(equityHistoryId);

		return socialEquityHistory;
	}

	/**
	 * Removes the social equity history with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the social equity history
	 * @return the social equity history that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a social equity history with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialEquityHistory remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the social equity history with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param equityHistoryId the primary key of the social equity history
	 * @return the social equity history that was removed
	 * @throws com.liferay.portlet.social.NoSuchEquityHistoryException if a social equity history with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityHistory remove(long equityHistoryId)
		throws NoSuchEquityHistoryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialEquityHistory socialEquityHistory = (SocialEquityHistory)session.get(SocialEquityHistoryImpl.class,
					Long.valueOf(equityHistoryId));

			if (socialEquityHistory == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						equityHistoryId);
				}

				throw new NoSuchEquityHistoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					equityHistoryId);
			}

			return socialEquityHistoryPersistence.remove(socialEquityHistory);
		}
		catch (NoSuchEquityHistoryException nsee) {
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
	 * Removes the social equity history from the database. Also notifies the appropriate model listeners.
	 *
	 * @param socialEquityHistory the social equity history
	 * @return the social equity history that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialEquityHistory remove(SocialEquityHistory socialEquityHistory)
		throws SystemException {
		return super.remove(socialEquityHistory);
	}

	@Override
	protected SocialEquityHistory removeImpl(
		SocialEquityHistory socialEquityHistory) throws SystemException {
		socialEquityHistory = toUnwrappedModel(socialEquityHistory);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, socialEquityHistory);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(SocialEquityHistoryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityHistoryImpl.class, socialEquityHistory.getPrimaryKey());

		return socialEquityHistory;
	}

	@Override
	public SocialEquityHistory updateImpl(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory,
		boolean merge) throws SystemException {
		socialEquityHistory = toUnwrappedModel(socialEquityHistory);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, socialEquityHistory, merge);

			socialEquityHistory.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SocialEquityHistoryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityHistoryImpl.class, socialEquityHistory.getPrimaryKey(),
			socialEquityHistory);

		return socialEquityHistory;
	}

	protected SocialEquityHistory toUnwrappedModel(
		SocialEquityHistory socialEquityHistory) {
		if (socialEquityHistory instanceof SocialEquityHistoryImpl) {
			return socialEquityHistory;
		}

		SocialEquityHistoryImpl socialEquityHistoryImpl = new SocialEquityHistoryImpl();

		socialEquityHistoryImpl.setNew(socialEquityHistory.isNew());
		socialEquityHistoryImpl.setPrimaryKey(socialEquityHistory.getPrimaryKey());

		socialEquityHistoryImpl.setEquityHistoryId(socialEquityHistory.getEquityHistoryId());
		socialEquityHistoryImpl.setGroupId(socialEquityHistory.getGroupId());
		socialEquityHistoryImpl.setCompanyId(socialEquityHistory.getCompanyId());
		socialEquityHistoryImpl.setUserId(socialEquityHistory.getUserId());
		socialEquityHistoryImpl.setCreateDate(socialEquityHistory.getCreateDate());
		socialEquityHistoryImpl.setPersonalEquity(socialEquityHistory.getPersonalEquity());

		return socialEquityHistoryImpl;
	}

	/**
	 * Returns the social equity history with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the social equity history
	 * @return the social equity history
	 * @throws com.liferay.portal.NoSuchModelException if a social equity history with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialEquityHistory findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social equity history with the primary key or throws a {@link com.liferay.portlet.social.NoSuchEquityHistoryException} if it could not be found.
	 *
	 * @param equityHistoryId the primary key of the social equity history
	 * @return the social equity history
	 * @throws com.liferay.portlet.social.NoSuchEquityHistoryException if a social equity history with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityHistory findByPrimaryKey(long equityHistoryId)
		throws NoSuchEquityHistoryException, SystemException {
		SocialEquityHistory socialEquityHistory = fetchByPrimaryKey(equityHistoryId);

		if (socialEquityHistory == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + equityHistoryId);
			}

			throw new NoSuchEquityHistoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				equityHistoryId);
		}

		return socialEquityHistory;
	}

	/**
	 * Returns the social equity history with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social equity history
	 * @return the social equity history, or <code>null</code> if a social equity history with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialEquityHistory fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social equity history with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param equityHistoryId the primary key of the social equity history
	 * @return the social equity history, or <code>null</code> if a social equity history with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityHistory fetchByPrimaryKey(long equityHistoryId)
		throws SystemException {
		SocialEquityHistory socialEquityHistory = (SocialEquityHistory)EntityCacheUtil.getResult(SocialEquityHistoryModelImpl.ENTITY_CACHE_ENABLED,
				SocialEquityHistoryImpl.class, equityHistoryId);

		if (socialEquityHistory == _nullSocialEquityHistory) {
			return null;
		}

		if (socialEquityHistory == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				socialEquityHistory = (SocialEquityHistory)session.get(SocialEquityHistoryImpl.class,
						Long.valueOf(equityHistoryId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (socialEquityHistory != null) {
					cacheResult(socialEquityHistory);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(SocialEquityHistoryModelImpl.ENTITY_CACHE_ENABLED,
						SocialEquityHistoryImpl.class, equityHistoryId,
						_nullSocialEquityHistory);
				}

				closeSession(session);
			}
		}

		return socialEquityHistory;
	}

	/**
	 * Returns all the social equity histories.
	 *
	 * @return the social equity histories
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityHistory> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social equity histories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social equity histories
	 * @param end the upper bound of the range of social equity histories (not inclusive)
	 * @return the range of social equity histories
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityHistory> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the social equity histories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social equity histories
	 * @param end the upper bound of the range of social equity histories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of social equity histories
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityHistory> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		List<SocialEquityHistory> list = (List<SocialEquityHistory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SOCIALEQUITYHISTORY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SOCIALEQUITYHISTORY;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SocialEquityHistory>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialEquityHistory>)QueryUtil.list(q,
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
	 * Removes all the social equity histories from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SocialEquityHistory socialEquityHistory : findAll()) {
			socialEquityHistoryPersistence.remove(socialEquityHistory);
		}
	}

	/**
	 * Returns the number of social equity histories.
	 *
	 * @return the number of social equity histories
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SOCIALEQUITYHISTORY);

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
	 * Initializes the social equity history persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.social.model.SocialEquityHistory")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialEquityHistory>> listenersList = new ArrayList<ModelListener<SocialEquityHistory>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialEquityHistory>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(SocialEquityHistoryImpl.class.getName());
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
	private static final String _SQL_SELECT_SOCIALEQUITYHISTORY = "SELECT socialEquityHistory FROM SocialEquityHistory socialEquityHistory";
	private static final String _SQL_COUNT_SOCIALEQUITYHISTORY = "SELECT COUNT(socialEquityHistory) FROM SocialEquityHistory socialEquityHistory";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialEquityHistory.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialEquityHistory exists with the primary key ";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(SocialEquityHistoryPersistenceImpl.class);
	private static SocialEquityHistory _nullSocialEquityHistory = new SocialEquityHistoryImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SocialEquityHistory> toCacheModel() {
				return _nullSocialEquityHistoryCacheModel;
			}
		};

	private static CacheModel<SocialEquityHistory> _nullSocialEquityHistoryCacheModel =
		new CacheModel<SocialEquityHistory>() {
			public SocialEquityHistory toEntityModel() {
				return _nullSocialEquityHistory;
			}
		};
}