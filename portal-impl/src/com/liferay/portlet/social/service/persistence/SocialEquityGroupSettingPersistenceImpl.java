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
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.social.NoSuchEquityGroupSettingException;
import com.liferay.portlet.social.model.SocialEquityGroupSetting;
import com.liferay.portlet.social.model.impl.SocialEquityGroupSettingImpl;
import com.liferay.portlet.social.model.impl.SocialEquityGroupSettingModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the social equity group setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityGroupSettingPersistence
 * @see SocialEquityGroupSettingUtil
 * @generated
 */
public class SocialEquityGroupSettingPersistenceImpl extends BasePersistenceImpl<SocialEquityGroupSetting>
	implements SocialEquityGroupSettingPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SocialEquityGroupSettingUtil} to access the social equity group setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SocialEquityGroupSettingImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_T = new FinderPath(SocialEquityGroupSettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityGroupSettingModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityGroupSettingImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_T = new FinderPath(SocialEquityGroupSettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityGroupSettingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SocialEquityGroupSettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityGroupSettingModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityGroupSettingImpl.class, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialEquityGroupSettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityGroupSettingModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the social equity group setting in the entity cache if it is enabled.
	 *
	 * @param socialEquityGroupSetting the social equity group setting
	 */
	public void cacheResult(SocialEquityGroupSetting socialEquityGroupSetting) {
		EntityCacheUtil.putResult(SocialEquityGroupSettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityGroupSettingImpl.class,
			socialEquityGroupSetting.getPrimaryKey(), socialEquityGroupSetting);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_T,
			new Object[] {
				Long.valueOf(socialEquityGroupSetting.getGroupId()),
				Long.valueOf(socialEquityGroupSetting.getClassNameId()),
				Integer.valueOf(socialEquityGroupSetting.getType())
			}, socialEquityGroupSetting);

		socialEquityGroupSetting.resetOriginalValues();
	}

	/**
	 * Caches the social equity group settings in the entity cache if it is enabled.
	 *
	 * @param socialEquityGroupSettings the social equity group settings
	 */
	public void cacheResult(
		List<SocialEquityGroupSetting> socialEquityGroupSettings) {
		for (SocialEquityGroupSetting socialEquityGroupSetting : socialEquityGroupSettings) {
			if (EntityCacheUtil.getResult(
						SocialEquityGroupSettingModelImpl.ENTITY_CACHE_ENABLED,
						SocialEquityGroupSettingImpl.class,
						socialEquityGroupSetting.getPrimaryKey()) == null) {
				cacheResult(socialEquityGroupSetting);
			}
		}
	}

	/**
	 * Clears the cache for all social equity group settings.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SocialEquityGroupSettingImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SocialEquityGroupSettingImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the social equity group setting.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SocialEquityGroupSetting socialEquityGroupSetting) {
		EntityCacheUtil.removeResult(SocialEquityGroupSettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityGroupSettingImpl.class,
			socialEquityGroupSetting.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL, FINDER_ARGS_EMPTY);

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_T,
			new Object[] {
				Long.valueOf(socialEquityGroupSetting.getGroupId()),
				Long.valueOf(socialEquityGroupSetting.getClassNameId()),
				Integer.valueOf(socialEquityGroupSetting.getType())
			});
	}

	/**
	 * Creates a new social equity group setting with the primary key. Does not add the social equity group setting to the database.
	 *
	 * @param equityGroupSettingId the primary key for the new social equity group setting
	 * @return the new social equity group setting
	 */
	public SocialEquityGroupSetting create(long equityGroupSettingId) {
		SocialEquityGroupSetting socialEquityGroupSetting = new SocialEquityGroupSettingImpl();

		socialEquityGroupSetting.setNew(true);
		socialEquityGroupSetting.setPrimaryKey(equityGroupSettingId);

		return socialEquityGroupSetting;
	}

	/**
	 * Removes the social equity group setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the social equity group setting
	 * @return the social equity group setting that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a social equity group setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialEquityGroupSetting remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the social equity group setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param equityGroupSettingId the primary key of the social equity group setting
	 * @return the social equity group setting that was removed
	 * @throws com.liferay.portlet.social.NoSuchEquityGroupSettingException if a social equity group setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityGroupSetting remove(long equityGroupSettingId)
		throws NoSuchEquityGroupSettingException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialEquityGroupSetting socialEquityGroupSetting = (SocialEquityGroupSetting)session.get(SocialEquityGroupSettingImpl.class,
					Long.valueOf(equityGroupSettingId));

			if (socialEquityGroupSetting == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						equityGroupSettingId);
				}

				throw new NoSuchEquityGroupSettingException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					equityGroupSettingId);
			}

			return socialEquityGroupSettingPersistence.remove(socialEquityGroupSetting);
		}
		catch (NoSuchEquityGroupSettingException nsee) {
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
	 * Removes the social equity group setting from the database. Also notifies the appropriate model listeners.
	 *
	 * @param socialEquityGroupSetting the social equity group setting
	 * @return the social equity group setting that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialEquityGroupSetting remove(
		SocialEquityGroupSetting socialEquityGroupSetting)
		throws SystemException {
		return super.remove(socialEquityGroupSetting);
	}

	@Override
	protected SocialEquityGroupSetting removeImpl(
		SocialEquityGroupSetting socialEquityGroupSetting)
		throws SystemException {
		socialEquityGroupSetting = toUnwrappedModel(socialEquityGroupSetting);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, socialEquityGroupSetting);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		SocialEquityGroupSettingModelImpl socialEquityGroupSettingModelImpl = (SocialEquityGroupSettingModelImpl)socialEquityGroupSetting;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_T,
			new Object[] {
				Long.valueOf(socialEquityGroupSettingModelImpl.getGroupId()),
				Long.valueOf(socialEquityGroupSettingModelImpl.getClassNameId()),
				Integer.valueOf(socialEquityGroupSettingModelImpl.getType())
			});

		EntityCacheUtil.removeResult(SocialEquityGroupSettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityGroupSettingImpl.class,
			socialEquityGroupSetting.getPrimaryKey());

		return socialEquityGroupSetting;
	}

	@Override
	public SocialEquityGroupSetting updateImpl(
		com.liferay.portlet.social.model.SocialEquityGroupSetting socialEquityGroupSetting,
		boolean merge) throws SystemException {
		socialEquityGroupSetting = toUnwrappedModel(socialEquityGroupSetting);

		boolean isNew = socialEquityGroupSetting.isNew();

		SocialEquityGroupSettingModelImpl socialEquityGroupSettingModelImpl = (SocialEquityGroupSettingModelImpl)socialEquityGroupSetting;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, socialEquityGroupSetting, merge);

			socialEquityGroupSetting.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SocialEquityGroupSettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityGroupSettingImpl.class,
			socialEquityGroupSetting.getPrimaryKey(), socialEquityGroupSetting);

		if (!isNew &&
				((socialEquityGroupSetting.getGroupId() != socialEquityGroupSettingModelImpl.getOriginalGroupId()) ||
				(socialEquityGroupSetting.getClassNameId() != socialEquityGroupSettingModelImpl.getOriginalClassNameId()) ||
				(socialEquityGroupSetting.getType() != socialEquityGroupSettingModelImpl.getOriginalType()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_T,
				new Object[] {
					Long.valueOf(
						socialEquityGroupSettingModelImpl.getOriginalGroupId()),
					Long.valueOf(
						socialEquityGroupSettingModelImpl.getOriginalClassNameId()),
					Integer.valueOf(
						socialEquityGroupSettingModelImpl.getOriginalType())
				});
		}

		if (isNew ||
				((socialEquityGroupSetting.getGroupId() != socialEquityGroupSettingModelImpl.getOriginalGroupId()) ||
				(socialEquityGroupSetting.getClassNameId() != socialEquityGroupSettingModelImpl.getOriginalClassNameId()) ||
				(socialEquityGroupSetting.getType() != socialEquityGroupSettingModelImpl.getOriginalType()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_T,
				new Object[] {
					Long.valueOf(socialEquityGroupSetting.getGroupId()),
					Long.valueOf(socialEquityGroupSetting.getClassNameId()),
					Integer.valueOf(socialEquityGroupSetting.getType())
				}, socialEquityGroupSetting);
		}

		return socialEquityGroupSetting;
	}

	protected SocialEquityGroupSetting toUnwrappedModel(
		SocialEquityGroupSetting socialEquityGroupSetting) {
		if (socialEquityGroupSetting instanceof SocialEquityGroupSettingImpl) {
			return socialEquityGroupSetting;
		}

		SocialEquityGroupSettingImpl socialEquityGroupSettingImpl = new SocialEquityGroupSettingImpl();

		socialEquityGroupSettingImpl.setNew(socialEquityGroupSetting.isNew());
		socialEquityGroupSettingImpl.setPrimaryKey(socialEquityGroupSetting.getPrimaryKey());

		socialEquityGroupSettingImpl.setEquityGroupSettingId(socialEquityGroupSetting.getEquityGroupSettingId());
		socialEquityGroupSettingImpl.setGroupId(socialEquityGroupSetting.getGroupId());
		socialEquityGroupSettingImpl.setCompanyId(socialEquityGroupSetting.getCompanyId());
		socialEquityGroupSettingImpl.setClassNameId(socialEquityGroupSetting.getClassNameId());
		socialEquityGroupSettingImpl.setType(socialEquityGroupSetting.getType());
		socialEquityGroupSettingImpl.setEnabled(socialEquityGroupSetting.isEnabled());

		return socialEquityGroupSettingImpl;
	}

	/**
	 * Returns the social equity group setting with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the social equity group setting
	 * @return the social equity group setting
	 * @throws com.liferay.portal.NoSuchModelException if a social equity group setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialEquityGroupSetting findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social equity group setting with the primary key or throws a {@link com.liferay.portlet.social.NoSuchEquityGroupSettingException} if it could not be found.
	 *
	 * @param equityGroupSettingId the primary key of the social equity group setting
	 * @return the social equity group setting
	 * @throws com.liferay.portlet.social.NoSuchEquityGroupSettingException if a social equity group setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityGroupSetting findByPrimaryKey(long equityGroupSettingId)
		throws NoSuchEquityGroupSettingException, SystemException {
		SocialEquityGroupSetting socialEquityGroupSetting = fetchByPrimaryKey(equityGroupSettingId);

		if (socialEquityGroupSetting == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					equityGroupSettingId);
			}

			throw new NoSuchEquityGroupSettingException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				equityGroupSettingId);
		}

		return socialEquityGroupSetting;
	}

	/**
	 * Returns the social equity group setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social equity group setting
	 * @return the social equity group setting, or <code>null</code> if a social equity group setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialEquityGroupSetting fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social equity group setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param equityGroupSettingId the primary key of the social equity group setting
	 * @return the social equity group setting, or <code>null</code> if a social equity group setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityGroupSetting fetchByPrimaryKey(long equityGroupSettingId)
		throws SystemException {
		SocialEquityGroupSetting socialEquityGroupSetting = (SocialEquityGroupSetting)EntityCacheUtil.getResult(SocialEquityGroupSettingModelImpl.ENTITY_CACHE_ENABLED,
				SocialEquityGroupSettingImpl.class, equityGroupSettingId);

		if (socialEquityGroupSetting == _nullSocialEquityGroupSetting) {
			return null;
		}

		if (socialEquityGroupSetting == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				socialEquityGroupSetting = (SocialEquityGroupSetting)session.get(SocialEquityGroupSettingImpl.class,
						Long.valueOf(equityGroupSettingId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (socialEquityGroupSetting != null) {
					cacheResult(socialEquityGroupSetting);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(SocialEquityGroupSettingModelImpl.ENTITY_CACHE_ENABLED,
						SocialEquityGroupSettingImpl.class,
						equityGroupSettingId, _nullSocialEquityGroupSetting);
				}

				closeSession(session);
			}
		}

		return socialEquityGroupSetting;
	}

	/**
	 * Returns the social equity group setting where groupId = &#63; and classNameId = &#63; and type = &#63; or throws a {@link com.liferay.portlet.social.NoSuchEquityGroupSettingException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @return the matching social equity group setting
	 * @throws com.liferay.portlet.social.NoSuchEquityGroupSettingException if a matching social equity group setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityGroupSetting findByG_C_T(long groupId, long classNameId,
		int type) throws NoSuchEquityGroupSettingException, SystemException {
		SocialEquityGroupSetting socialEquityGroupSetting = fetchByG_C_T(groupId,
				classNameId, type);

		if (socialEquityGroupSetting == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEquityGroupSettingException(msg.toString());
		}

		return socialEquityGroupSetting;
	}

	/**
	 * Returns the social equity group setting where groupId = &#63; and classNameId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @return the matching social equity group setting, or <code>null</code> if a matching social equity group setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityGroupSetting fetchByG_C_T(long groupId,
		long classNameId, int type) throws SystemException {
		return fetchByG_C_T(groupId, classNameId, type, true);
	}

	/**
	 * Returns the social equity group setting where groupId = &#63; and classNameId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching social equity group setting, or <code>null</code> if a matching social equity group setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityGroupSetting fetchByG_C_T(long groupId,
		long classNameId, int type, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, classNameId, type };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_C_T,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SOCIALEQUITYGROUPSETTING_WHERE);

			query.append(_FINDER_COLUMN_G_C_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(type);

				List<SocialEquityGroupSetting> list = q.list();

				result = list;

				SocialEquityGroupSetting socialEquityGroupSetting = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_T,
						finderArgs, list);
				}
				else {
					socialEquityGroupSetting = list.get(0);

					cacheResult(socialEquityGroupSetting);

					if ((socialEquityGroupSetting.getGroupId() != groupId) ||
							(socialEquityGroupSetting.getClassNameId() != classNameId) ||
							(socialEquityGroupSetting.getType() != type)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_T,
							finderArgs, socialEquityGroupSetting);
					}
				}

				return socialEquityGroupSetting;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_T,
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
				return (SocialEquityGroupSetting)result;
			}
		}
	}

	/**
	 * Returns all the social equity group settings.
	 *
	 * @return the social equity group settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityGroupSetting> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social equity group settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social equity group settings
	 * @param end the upper bound of the range of social equity group settings (not inclusive)
	 * @return the range of social equity group settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityGroupSetting> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the social equity group settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social equity group settings
	 * @param end the upper bound of the range of social equity group settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of social equity group settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityGroupSetting> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		List<SocialEquityGroupSetting> list = (List<SocialEquityGroupSetting>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SOCIALEQUITYGROUPSETTING);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SOCIALEQUITYGROUPSETTING;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SocialEquityGroupSetting>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialEquityGroupSetting>)QueryUtil.list(q,
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
	 * Removes the social equity group setting where groupId = &#63; and classNameId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_C_T(long groupId, long classNameId, int type)
		throws NoSuchEquityGroupSettingException, SystemException {
		SocialEquityGroupSetting socialEquityGroupSetting = findByG_C_T(groupId,
				classNameId, type);

		socialEquityGroupSettingPersistence.remove(socialEquityGroupSetting);
	}

	/**
	 * Removes all the social equity group settings from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SocialEquityGroupSetting socialEquityGroupSetting : findAll()) {
			socialEquityGroupSettingPersistence.remove(socialEquityGroupSetting);
		}
	}

	/**
	 * Returns the number of social equity group settings where groupId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @return the number of matching social equity group settings
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C_T(long groupId, long classNameId, int type)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, classNameId, type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SOCIALEQUITYGROUPSETTING_WHERE);

			query.append(_FINDER_COLUMN_G_C_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social equity group settings.
	 *
	 * @return the number of social equity group settings
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SOCIALEQUITYGROUPSETTING);

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
	 * Initializes the social equity group setting persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.social.model.SocialEquityGroupSetting")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialEquityGroupSetting>> listenersList = new ArrayList<ModelListener<SocialEquityGroupSetting>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialEquityGroupSetting>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(SocialEquityGroupSettingImpl.class.getName());
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
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_SOCIALEQUITYGROUPSETTING = "SELECT socialEquityGroupSetting FROM SocialEquityGroupSetting socialEquityGroupSetting";
	private static final String _SQL_SELECT_SOCIALEQUITYGROUPSETTING_WHERE = "SELECT socialEquityGroupSetting FROM SocialEquityGroupSetting socialEquityGroupSetting WHERE ";
	private static final String _SQL_COUNT_SOCIALEQUITYGROUPSETTING = "SELECT COUNT(socialEquityGroupSetting) FROM SocialEquityGroupSetting socialEquityGroupSetting";
	private static final String _SQL_COUNT_SOCIALEQUITYGROUPSETTING_WHERE = "SELECT COUNT(socialEquityGroupSetting) FROM SocialEquityGroupSetting socialEquityGroupSetting WHERE ";
	private static final String _FINDER_COLUMN_G_C_T_GROUPID_2 = "socialEquityGroupSetting.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_T_CLASSNAMEID_2 = "socialEquityGroupSetting.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_T_TYPE_2 = "socialEquityGroupSetting.type = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialEquityGroupSetting.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialEquityGroupSetting exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SocialEquityGroupSetting exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(SocialEquityGroupSettingPersistenceImpl.class);
	private static SocialEquityGroupSetting _nullSocialEquityGroupSetting = new SocialEquityGroupSettingImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SocialEquityGroupSetting> toCacheModel() {
				return _nullSocialEquityGroupSettingCacheModel;
			}
		};

	private static CacheModel<SocialEquityGroupSetting> _nullSocialEquityGroupSettingCacheModel =
		new CacheModel<SocialEquityGroupSetting>() {
			public SocialEquityGroupSetting toEntityModel() {
				return _nullSocialEquityGroupSetting;
			}
		};
}