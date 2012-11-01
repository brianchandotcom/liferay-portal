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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchPluginSettingException;
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
import com.liferay.portal.model.PluginSetting;
import com.liferay.portal.model.impl.PluginSettingImpl;
import com.liferay.portal.model.impl.PluginSettingModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the plugin setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PluginSettingPersistence
 * @see PluginSettingUtil
 * @generated
 */
public class PluginSettingPersistenceImpl extends BasePersistenceImpl<PluginSetting>
	implements PluginSettingPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link PluginSettingUtil} to access the plugin setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = PluginSettingImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED,
			PluginSettingImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED,
			PluginSettingImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyId", new String[] { Long.class.getName() },
			PluginSettingModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_I_T = new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED,
			PluginSettingImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_I_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			PluginSettingModelImpl.COMPANYID_COLUMN_BITMASK |
			PluginSettingModelImpl.PLUGINID_COLUMN_BITMASK |
			PluginSettingModelImpl.PLUGINTYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_I_T = new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_I_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED,
			PluginSettingImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED,
			PluginSettingImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the plugin setting in the entity cache if it is enabled.
	 *
	 * @param pluginSetting the plugin setting
	 */
	public void cacheResult(PluginSetting pluginSetting) {
		EntityCacheUtil.putResult(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingImpl.class, pluginSetting.getPrimaryKey(),
			pluginSetting);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_I_T,
			new Object[] {
				Long.valueOf(pluginSetting.getCompanyId()),
				
			pluginSetting.getPluginId(),
				
			pluginSetting.getPluginType()
			}, pluginSetting);

		pluginSetting.resetOriginalValues();
	}

	/**
	 * Caches the plugin settings in the entity cache if it is enabled.
	 *
	 * @param pluginSettings the plugin settings
	 */
	public void cacheResult(List<PluginSetting> pluginSettings) {
		for (PluginSetting pluginSetting : pluginSettings) {
			if (EntityCacheUtil.getResult(
						PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
						PluginSettingImpl.class, pluginSetting.getPrimaryKey()) == null) {
				cacheResult(pluginSetting);
			}
			else {
				pluginSetting.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all plugin settings.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(PluginSettingImpl.class.getName());
		}

		EntityCacheUtil.clearCache(PluginSettingImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the plugin setting.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PluginSetting pluginSetting) {
		EntityCacheUtil.removeResult(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingImpl.class, pluginSetting.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(pluginSetting);
	}

	@Override
	public void clearCache(List<PluginSetting> pluginSettings) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (PluginSetting pluginSetting : pluginSettings) {
			EntityCacheUtil.removeResult(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
				PluginSettingImpl.class, pluginSetting.getPrimaryKey());

			clearUniqueFindersCache(pluginSetting);
		}
	}

	protected void clearUniqueFindersCache(PluginSetting pluginSetting) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_I_T,
			new Object[] {
				Long.valueOf(pluginSetting.getCompanyId()),
				
			pluginSetting.getPluginId(),
				
			pluginSetting.getPluginType()
			});
	}

	/**
	 * Creates a new plugin setting with the primary key. Does not add the plugin setting to the database.
	 *
	 * @param pluginSettingId the primary key for the new plugin setting
	 * @return the new plugin setting
	 */
	public PluginSetting create(long pluginSettingId) {
		PluginSetting pluginSetting = new PluginSettingImpl();

		pluginSetting.setNew(true);
		pluginSetting.setPrimaryKey(pluginSettingId);

		return pluginSetting;
	}

	/**
	 * Removes the plugin setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param pluginSettingId the primary key of the plugin setting
	 * @return the plugin setting that was removed
	 * @throws com.liferay.portal.NoSuchPluginSettingException if a plugin setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PluginSetting remove(long pluginSettingId)
		throws NoSuchPluginSettingException, SystemException {
		return remove(Long.valueOf(pluginSettingId));
	}

	/**
	 * Removes the plugin setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the plugin setting
	 * @return the plugin setting that was removed
	 * @throws com.liferay.portal.NoSuchPluginSettingException if a plugin setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PluginSetting remove(Serializable primaryKey)
		throws NoSuchPluginSettingException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PluginSetting pluginSetting = (PluginSetting)session.get(PluginSettingImpl.class,
					primaryKey);

			if (pluginSetting == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPluginSettingException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(pluginSetting);
		}
		catch (NoSuchPluginSettingException nsee) {
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
	protected PluginSetting removeImpl(PluginSetting pluginSetting)
		throws SystemException {
		pluginSetting = toUnwrappedModel(pluginSetting);

		Session session = null;

		try {
			session = openSession();

			if (pluginSetting.isCachedModel()) {
				pluginSetting = (PluginSetting)session.get(PluginSettingImpl.class,
						pluginSetting.getPrimaryKeyObj());
			}

			if (pluginSetting != null) {
				session.delete(pluginSetting);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (pluginSetting != null) {
			clearCache(pluginSetting);
		}

		return pluginSetting;
	}

	@Override
	public PluginSetting updateImpl(
		com.liferay.portal.model.PluginSetting pluginSetting)
		throws SystemException {
		pluginSetting = toUnwrappedModel(pluginSetting);

		boolean isNew = pluginSetting.isNew();

		PluginSettingModelImpl pluginSettingModelImpl = (PluginSettingModelImpl)pluginSetting;

		Session session = null;

		try {
			session = openSession();

			if (pluginSetting.isNew()) {
				session.save(pluginSetting);

				pluginSetting.setNew(false);
			}
			else {
				session.merge(pluginSetting);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !PluginSettingModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((pluginSettingModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(pluginSettingModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(pluginSettingModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		EntityCacheUtil.putResult(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
			PluginSettingImpl.class, pluginSetting.getPrimaryKey(),
			pluginSetting);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_I_T,
				new Object[] {
					Long.valueOf(pluginSetting.getCompanyId()),
					
				pluginSetting.getPluginId(),
					
				pluginSetting.getPluginType()
				}, pluginSetting);
		}
		else {
			if ((pluginSettingModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_I_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(pluginSettingModelImpl.getOriginalCompanyId()),
						
						pluginSettingModelImpl.getOriginalPluginId(),
						
						pluginSettingModelImpl.getOriginalPluginType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_I_T, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_I_T, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_I_T,
					new Object[] {
						Long.valueOf(pluginSetting.getCompanyId()),
						
					pluginSetting.getPluginId(),
						
					pluginSetting.getPluginType()
					}, pluginSetting);
			}
		}

		return pluginSetting;
	}

	protected PluginSetting toUnwrappedModel(PluginSetting pluginSetting) {
		if (pluginSetting instanceof PluginSettingImpl) {
			return pluginSetting;
		}

		PluginSettingImpl pluginSettingImpl = new PluginSettingImpl();

		pluginSettingImpl.setNew(pluginSetting.isNew());
		pluginSettingImpl.setPrimaryKey(pluginSetting.getPrimaryKey());

		pluginSettingImpl.setPluginSettingId(pluginSetting.getPluginSettingId());
		pluginSettingImpl.setCompanyId(pluginSetting.getCompanyId());
		pluginSettingImpl.setPluginId(pluginSetting.getPluginId());
		pluginSettingImpl.setPluginType(pluginSetting.getPluginType());
		pluginSettingImpl.setRoles(pluginSetting.getRoles());
		pluginSettingImpl.setActive(pluginSetting.isActive());

		return pluginSettingImpl;
	}

	/**
	 * Returns the plugin setting with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the plugin setting
	 * @return the plugin setting
	 * @throws com.liferay.portal.NoSuchModelException if a plugin setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PluginSetting findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the plugin setting with the primary key or throws a {@link com.liferay.portal.NoSuchPluginSettingException} if it could not be found.
	 *
	 * @param pluginSettingId the primary key of the plugin setting
	 * @return the plugin setting
	 * @throws com.liferay.portal.NoSuchPluginSettingException if a plugin setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PluginSetting findByPrimaryKey(long pluginSettingId)
		throws NoSuchPluginSettingException, SystemException {
		PluginSetting pluginSetting = fetchByPrimaryKey(pluginSettingId);

		if (pluginSetting == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + pluginSettingId);
			}

			throw new NoSuchPluginSettingException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				pluginSettingId);
		}

		return pluginSetting;
	}

	/**
	 * Returns the plugin setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the plugin setting
	 * @return the plugin setting, or <code>null</code> if a plugin setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PluginSetting fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the plugin setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param pluginSettingId the primary key of the plugin setting
	 * @return the plugin setting, or <code>null</code> if a plugin setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PluginSetting fetchByPrimaryKey(long pluginSettingId)
		throws SystemException {
		PluginSetting pluginSetting = (PluginSetting)EntityCacheUtil.getResult(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
				PluginSettingImpl.class, pluginSettingId);

		if (pluginSetting == _nullPluginSetting) {
			return null;
		}

		if (pluginSetting == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				pluginSetting = (PluginSetting)session.get(PluginSettingImpl.class,
						Long.valueOf(pluginSettingId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (pluginSetting != null) {
					cacheResult(pluginSetting);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(PluginSettingModelImpl.ENTITY_CACHE_ENABLED,
						PluginSettingImpl.class, pluginSettingId,
						_nullPluginSetting);
				}

				closeSession(session);
			}
		}

		return pluginSetting;
	}

	/**
	 * Returns all the plugin settings where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching plugin settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<PluginSetting> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the plugin settings where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of plugin settings
	 * @param end the upper bound of the range of plugin settings (not inclusive)
	 * @return the range of matching plugin settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<PluginSetting> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the plugin settings where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of plugin settings
	 * @param end the upper bound of the range of plugin settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching plugin settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<PluginSetting> findByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<PluginSetting> list = (List<PluginSetting>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PluginSetting pluginSetting : list) {
				if ((companyId != pluginSetting.getCompanyId())) {
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

			query.append(_SQL_SELECT_PLUGINSETTING_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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

				qPos.add(companyId);

				list = (List<PluginSetting>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first plugin setting in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plugin setting
	 * @throws com.liferay.portal.NoSuchPluginSettingException if a matching plugin setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PluginSetting findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchPluginSettingException, SystemException {
		PluginSetting pluginSetting = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (pluginSetting != null) {
			return pluginSetting;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPluginSettingException(msg.toString());
	}

	/**
	 * Returns the first plugin setting in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plugin setting, or <code>null</code> if a matching plugin setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PluginSetting fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<PluginSetting> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last plugin setting in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plugin setting
	 * @throws com.liferay.portal.NoSuchPluginSettingException if a matching plugin setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PluginSetting findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchPluginSettingException, SystemException {
		PluginSetting pluginSetting = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (pluginSetting != null) {
			return pluginSetting;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPluginSettingException(msg.toString());
	}

	/**
	 * Returns the last plugin setting in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plugin setting, or <code>null</code> if a matching plugin setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PluginSetting fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<PluginSetting> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the plugin settings before and after the current plugin setting in the ordered set where companyId = &#63;.
	 *
	 * @param pluginSettingId the primary key of the current plugin setting
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next plugin setting
	 * @throws com.liferay.portal.NoSuchPluginSettingException if a plugin setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PluginSetting[] findByCompanyId_PrevAndNext(long pluginSettingId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchPluginSettingException, SystemException {
		PluginSetting pluginSetting = findByPrimaryKey(pluginSettingId);

		Session session = null;

		try {
			session = openSession();

			PluginSetting[] array = new PluginSettingImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, pluginSetting,
					companyId, orderByComparator, true);

			array[1] = pluginSetting;

			array[2] = getByCompanyId_PrevAndNext(session, pluginSetting,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PluginSetting getByCompanyId_PrevAndNext(Session session,
		PluginSetting pluginSetting, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PLUGINSETTING_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(pluginSetting);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PluginSetting> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the plugin setting where companyId = &#63; and pluginId = &#63; and pluginType = &#63; or throws a {@link com.liferay.portal.NoSuchPluginSettingException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param pluginId the plugin ID
	 * @param pluginType the plugin type
	 * @return the matching plugin setting
	 * @throws com.liferay.portal.NoSuchPluginSettingException if a matching plugin setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PluginSetting findByC_I_T(long companyId, String pluginId,
		String pluginType) throws NoSuchPluginSettingException, SystemException {
		PluginSetting pluginSetting = fetchByC_I_T(companyId, pluginId,
				pluginType);

		if (pluginSetting == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", pluginId=");
			msg.append(pluginId);

			msg.append(", pluginType=");
			msg.append(pluginType);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPluginSettingException(msg.toString());
		}

		return pluginSetting;
	}

	/**
	 * Returns the plugin setting where companyId = &#63; and pluginId = &#63; and pluginType = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param pluginId the plugin ID
	 * @param pluginType the plugin type
	 * @return the matching plugin setting, or <code>null</code> if a matching plugin setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PluginSetting fetchByC_I_T(long companyId, String pluginId,
		String pluginType) throws SystemException {
		return fetchByC_I_T(companyId, pluginId, pluginType, true);
	}

	/**
	 * Returns the plugin setting where companyId = &#63; and pluginId = &#63; and pluginType = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param pluginId the plugin ID
	 * @param pluginType the plugin type
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching plugin setting, or <code>null</code> if a matching plugin setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PluginSetting fetchByC_I_T(long companyId, String pluginId,
		String pluginType, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, pluginId, pluginType };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_I_T,
					finderArgs, this);
		}

		if (result instanceof PluginSetting) {
			PluginSetting pluginSetting = (PluginSetting)result;

			if ((companyId != pluginSetting.getCompanyId()) ||
					!Validator.equals(pluginId, pluginSetting.getPluginId()) ||
					!Validator.equals(pluginType, pluginSetting.getPluginType())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_PLUGINSETTING_WHERE);

			query.append(_FINDER_COLUMN_C_I_T_COMPANYID_2);

			if (pluginId == null) {
				query.append(_FINDER_COLUMN_C_I_T_PLUGINID_1);
			}
			else {
				if (pluginId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_I_T_PLUGINID_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_I_T_PLUGINID_2);
				}
			}

			if (pluginType == null) {
				query.append(_FINDER_COLUMN_C_I_T_PLUGINTYPE_1);
			}
			else {
				if (pluginType.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_I_T_PLUGINTYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_I_T_PLUGINTYPE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (pluginId != null) {
					qPos.add(pluginId);
				}

				if (pluginType != null) {
					qPos.add(pluginType);
				}

				List<PluginSetting> list = q.list();

				result = list;

				PluginSetting pluginSetting = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_I_T,
						finderArgs, list);
				}
				else {
					pluginSetting = list.get(0);

					cacheResult(pluginSetting);

					if ((pluginSetting.getCompanyId() != companyId) ||
							(pluginSetting.getPluginId() == null) ||
							!pluginSetting.getPluginId().equals(pluginId) ||
							(pluginSetting.getPluginType() == null) ||
							!pluginSetting.getPluginType().equals(pluginType)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_I_T,
							finderArgs, pluginSetting);
					}
				}

				return pluginSetting;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_I_T,
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
				return (PluginSetting)result;
			}
		}
	}

	/**
	 * Returns all the plugin settings.
	 *
	 * @return the plugin settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<PluginSetting> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the plugin settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of plugin settings
	 * @param end the upper bound of the range of plugin settings (not inclusive)
	 * @return the range of plugin settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<PluginSetting> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the plugin settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of plugin settings
	 * @param end the upper bound of the range of plugin settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of plugin settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<PluginSetting> findAll(int start, int end,
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

		List<PluginSetting> list = (List<PluginSetting>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_PLUGINSETTING);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_PLUGINSETTING;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<PluginSetting>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<PluginSetting>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the plugin settings where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (PluginSetting pluginSetting : findByCompanyId(companyId)) {
			remove(pluginSetting);
		}
	}

	/**
	 * Removes the plugin setting where companyId = &#63; and pluginId = &#63; and pluginType = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param pluginId the plugin ID
	 * @param pluginType the plugin type
	 * @return the plugin setting that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public PluginSetting removeByC_I_T(long companyId, String pluginId,
		String pluginType) throws NoSuchPluginSettingException, SystemException {
		PluginSetting pluginSetting = findByC_I_T(companyId, pluginId,
				pluginType);

		return remove(pluginSetting);
	}

	/**
	 * Removes all the plugin settings from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (PluginSetting pluginSetting : findAll()) {
			remove(pluginSetting);
		}
	}

	/**
	 * Returns the number of plugin settings where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching plugin settings
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_PLUGINSETTING_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of plugin settings where companyId = &#63; and pluginId = &#63; and pluginType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param pluginId the plugin ID
	 * @param pluginType the plugin type
	 * @return the number of matching plugin settings
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_I_T(long companyId, String pluginId, String pluginType)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, pluginId, pluginType };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_I_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_PLUGINSETTING_WHERE);

			query.append(_FINDER_COLUMN_C_I_T_COMPANYID_2);

			if (pluginId == null) {
				query.append(_FINDER_COLUMN_C_I_T_PLUGINID_1);
			}
			else {
				if (pluginId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_I_T_PLUGINID_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_I_T_PLUGINID_2);
				}
			}

			if (pluginType == null) {
				query.append(_FINDER_COLUMN_C_I_T_PLUGINTYPE_1);
			}
			else {
				if (pluginType.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_I_T_PLUGINTYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_I_T_PLUGINTYPE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (pluginId != null) {
					qPos.add(pluginId);
				}

				if (pluginType != null) {
					qPos.add(pluginType);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_I_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of plugin settings.
	 *
	 * @return the number of plugin settings
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_PLUGINSETTING);

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
	 * Initializes the plugin setting persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.PluginSetting")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PluginSetting>> listenersList = new ArrayList<ModelListener<PluginSetting>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PluginSetting>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(PluginSettingImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = ClusterGroupPersistence.class)
	protected ClusterGroupPersistence clusterGroupPersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutBranchPersistence.class)
	protected LayoutBranchPersistence layoutBranchPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutRevisionPersistence.class)
	protected LayoutRevisionPersistence layoutRevisionPersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetBranchPersistence.class)
	protected LayoutSetBranchPersistence layoutSetBranchPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortalPreferencesPersistence.class)
	protected PortalPreferencesPersistence portalPreferencesPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = RepositoryPersistence.class)
	protected RepositoryPersistence repositoryPersistence;
	@BeanReference(type = RepositoryEntryPersistence.class)
	protected RepositoryEntryPersistence repositoryEntryPersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceBlockPersistence.class)
	protected ResourceBlockPersistence resourceBlockPersistence;
	@BeanReference(type = ResourceBlockPermissionPersistence.class)
	protected ResourceBlockPermissionPersistence resourceBlockPermissionPersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = ResourceTypePermissionPersistence.class)
	protected ResourceTypePermissionPersistence resourceTypePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = TicketPersistence.class)
	protected TicketPersistence ticketPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserNotificationEventPersistence.class)
	protected UserNotificationEventPersistence userNotificationEventPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = VirtualHostPersistence.class)
	protected VirtualHostPersistence virtualHostPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_PLUGINSETTING = "SELECT pluginSetting FROM PluginSetting pluginSetting";
	private static final String _SQL_SELECT_PLUGINSETTING_WHERE = "SELECT pluginSetting FROM PluginSetting pluginSetting WHERE ";
	private static final String _SQL_COUNT_PLUGINSETTING = "SELECT COUNT(pluginSetting) FROM PluginSetting pluginSetting";
	private static final String _SQL_COUNT_PLUGINSETTING_WHERE = "SELECT COUNT(pluginSetting) FROM PluginSetting pluginSetting WHERE ";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "pluginSetting.companyId = ?";
	private static final String _FINDER_COLUMN_C_I_T_COMPANYID_2 = "pluginSetting.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_I_T_PLUGINID_1 = "pluginSetting.pluginId IS NULL AND ";
	private static final String _FINDER_COLUMN_C_I_T_PLUGINID_2 = "pluginSetting.pluginId = ? AND ";
	private static final String _FINDER_COLUMN_C_I_T_PLUGINID_3 = "(pluginSetting.pluginId IS NULL OR pluginSetting.pluginId = ?) AND ";
	private static final String _FINDER_COLUMN_C_I_T_PLUGINTYPE_1 = "pluginSetting.pluginType IS NULL";
	private static final String _FINDER_COLUMN_C_I_T_PLUGINTYPE_2 = "pluginSetting.pluginType = ?";
	private static final String _FINDER_COLUMN_C_I_T_PLUGINTYPE_3 = "(pluginSetting.pluginType IS NULL OR pluginSetting.pluginType = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "pluginSetting.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PluginSetting exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PluginSetting exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(PluginSettingPersistenceImpl.class);
	private static PluginSetting _nullPluginSetting = new PluginSettingImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<PluginSetting> toCacheModel() {
				return _nullPluginSettingCacheModel;
			}
		};

	private static CacheModel<PluginSetting> _nullPluginSettingCacheModel = new CacheModel<PluginSetting>() {
			public PluginSetting toEntityModel() {
				return _nullPluginSetting;
			}
		};
}