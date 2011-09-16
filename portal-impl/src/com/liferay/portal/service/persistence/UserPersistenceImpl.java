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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.model.impl.UserModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.announcements.service.persistence.AnnouncementsDeliveryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence;
import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.messageboards.service.persistence.MBBanPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBThreadFlagPersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingCartPersistence;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;
import com.liferay.portlet.social.service.persistence.SocialEquityLogPersistence;
import com.liferay.portlet.social.service.persistence.SocialEquityUserPersistence;
import com.liferay.portlet.social.service.persistence.SocialRequestPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the user service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserPersistence
 * @see UserUtil
 * @generated
 */
public class UserPersistenceImpl extends BasePersistenceImpl<User>
	implements UserPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link UserUtil} to access the user persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = UserImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_CONTACTID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByContactId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_CONTACTID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByContactId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_EMAILADDRESS = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST, "findByEmailAddress",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_EMAILADDRESS = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByEmailAddress",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_PORTRAITID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByPortraitId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_PORTRAITID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByPortraitId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_U = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_U = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByC_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_DU = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_DU",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_DU = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByC_DU",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_SN = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_SN",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_SN = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByC_SN",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_EA = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_EA",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_EA = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByC_EA",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_FID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_FID",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_FID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByC_FID",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_O = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_O",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_O = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByC_O",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_S = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST, "findByC_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_S = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByC_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the user in the entity cache if it is enabled.
	 *
	 * @param user the user
	 */
	public void cacheResult(User user) {
		EntityCacheUtil.putResult(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserImpl.class, user.getPrimaryKey(), user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONTACTID,
			new Object[] { Long.valueOf(user.getContactId()) }, user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PORTRAITID,
			new Object[] { Long.valueOf(user.getPortraitId()) }, user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U,
			new Object[] {
				Long.valueOf(user.getCompanyId()),
				Long.valueOf(user.getUserId())
			}, user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DU,
			new Object[] {
				Long.valueOf(user.getCompanyId()),
				Boolean.valueOf(user.getDefaultUser())
			}, user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_SN,
			new Object[] { Long.valueOf(user.getCompanyId()), user.getScreenName() },
			user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_EA,
			new Object[] {
				Long.valueOf(user.getCompanyId()),
				
			user.getEmailAddress()
			}, user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_FID,
			new Object[] {
				Long.valueOf(user.getCompanyId()),
				Long.valueOf(user.getFacebookId())
			}, user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_O,
			new Object[] { Long.valueOf(user.getCompanyId()), user.getOpenId() },
			user);

		user.resetOriginalValues();
	}

	/**
	 * Caches the users in the entity cache if it is enabled.
	 *
	 * @param users the users
	 */
	public void cacheResult(List<User> users) {
		for (User user : users) {
			if (EntityCacheUtil.getResult(UserModelImpl.ENTITY_CACHE_ENABLED,
						UserImpl.class, user.getPrimaryKey()) == null) {
				cacheResult(user);
			}
		}
	}

	/**
	 * Clears the cache for all users.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(UserImpl.class.getName());
		}

		EntityCacheUtil.clearCache(UserImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the user.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(User user) {
		EntityCacheUtil.removeResult(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserImpl.class, user.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL, FINDER_ARGS_EMPTY);

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CONTACTID,
			new Object[] { Long.valueOf(user.getContactId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PORTRAITID,
			new Object[] { Long.valueOf(user.getPortraitId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_U,
			new Object[] {
				Long.valueOf(user.getCompanyId()),
				Long.valueOf(user.getUserId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_DU,
			new Object[] {
				Long.valueOf(user.getCompanyId()),
				Boolean.valueOf(user.getDefaultUser())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_SN,
			new Object[] { Long.valueOf(user.getCompanyId()), user.getScreenName() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_EA,
			new Object[] {
				Long.valueOf(user.getCompanyId()),
				
			user.getEmailAddress()
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_FID,
			new Object[] {
				Long.valueOf(user.getCompanyId()),
				Long.valueOf(user.getFacebookId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_O,
			new Object[] { Long.valueOf(user.getCompanyId()), user.getOpenId() });
	}

	/**
	 * Creates a new user with the primary key. Does not add the user to the database.
	 *
	 * @param userId the primary key for the new user
	 * @return the new user
	 */
	public User create(long userId) {
		User user = new UserImpl();

		user.setNew(true);
		user.setPrimaryKey(userId);

		String uuid = PortalUUIDUtil.generate();

		user.setUuid(uuid);

		return user;
	}

	/**
	 * Removes the user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the user
	 * @return the user that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public User remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userId the primary key of the user
	 * @return the user that was removed
	 * @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User remove(long userId) throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			User user = (User)session.get(UserImpl.class, Long.valueOf(userId));

			if (user == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + userId);
				}

				throw new NoSuchUserException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					userId);
			}

			return userPersistence.remove(user);
		}
		catch (NoSuchUserException nsee) {
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
	 * Removes the user from the database. Also notifies the appropriate model listeners.
	 *
	 * @param user the user
	 * @return the user that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public User remove(User user) throws SystemException {
		return super.remove(user);
	}

	@Override
	protected User removeImpl(User user) throws SystemException {
		user = toUnwrappedModel(user);

		try {
			clearGroups.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}

		try {
			clearOrganizations.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}

		try {
			clearPermissions.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}

		try {
			clearRoles.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}

		try {
			clearTeams.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}

		try {
			clearUserGroups.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, user);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		UserModelImpl userModelImpl = (UserModelImpl)user;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CONTACTID,
			new Object[] { Long.valueOf(userModelImpl.getContactId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PORTRAITID,
			new Object[] { Long.valueOf(userModelImpl.getPortraitId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_U,
			new Object[] {
				Long.valueOf(userModelImpl.getCompanyId()),
				Long.valueOf(userModelImpl.getUserId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_DU,
			new Object[] {
				Long.valueOf(userModelImpl.getCompanyId()),
				Boolean.valueOf(userModelImpl.getDefaultUser())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_SN,
			new Object[] {
				Long.valueOf(userModelImpl.getCompanyId()),
				
			userModelImpl.getScreenName()
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_EA,
			new Object[] {
				Long.valueOf(userModelImpl.getCompanyId()),
				
			userModelImpl.getEmailAddress()
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_FID,
			new Object[] {
				Long.valueOf(userModelImpl.getCompanyId()),
				Long.valueOf(userModelImpl.getFacebookId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_O,
			new Object[] {
				Long.valueOf(userModelImpl.getCompanyId()),
				
			userModelImpl.getOpenId()
			});

		EntityCacheUtil.removeResult(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserImpl.class, user.getPrimaryKey());

		return user;
	}

	@Override
	public User updateImpl(com.liferay.portal.model.User user, boolean merge)
		throws SystemException {
		user = toUnwrappedModel(user);

		boolean isNew = user.isNew();

		UserModelImpl userModelImpl = (UserModelImpl)user;

		if (Validator.isNull(user.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			user.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, user, merge);

			user.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserImpl.class, user.getPrimaryKey(), user);

		if (!isNew &&
				(user.getContactId() != userModelImpl.getOriginalContactId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CONTACTID,
				new Object[] { Long.valueOf(
						userModelImpl.getOriginalContactId()) });
		}

		if (isNew ||
				(user.getContactId() != userModelImpl.getOriginalContactId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONTACTID,
				new Object[] { Long.valueOf(user.getContactId()) }, user);
		}

		if (!isNew &&
				(user.getPortraitId() != userModelImpl.getOriginalPortraitId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PORTRAITID,
				new Object[] { Long.valueOf(
						userModelImpl.getOriginalPortraitId()) });
		}

		if (isNew ||
				(user.getPortraitId() != userModelImpl.getOriginalPortraitId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PORTRAITID,
				new Object[] { Long.valueOf(user.getPortraitId()) }, user);
		}

		if (!isNew &&
				((user.getCompanyId() != userModelImpl.getOriginalCompanyId()) ||
				(user.getUserId() != userModelImpl.getOriginalUserId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_U,
				new Object[] {
					Long.valueOf(userModelImpl.getOriginalCompanyId()),
					Long.valueOf(userModelImpl.getOriginalUserId())
				});
		}

		if (isNew ||
				((user.getCompanyId() != userModelImpl.getOriginalCompanyId()) ||
				(user.getUserId() != userModelImpl.getOriginalUserId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U,
				new Object[] {
					Long.valueOf(user.getCompanyId()),
					Long.valueOf(user.getUserId())
				}, user);
		}

		if (!isNew &&
				((user.getCompanyId() != userModelImpl.getOriginalCompanyId()) ||
				(user.getDefaultUser() != userModelImpl.getOriginalDefaultUser()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_DU,
				new Object[] {
					Long.valueOf(userModelImpl.getOriginalCompanyId()),
					Boolean.valueOf(userModelImpl.getOriginalDefaultUser())
				});
		}

		if (isNew ||
				((user.getCompanyId() != userModelImpl.getOriginalCompanyId()) ||
				(user.getDefaultUser() != userModelImpl.getOriginalDefaultUser()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DU,
				new Object[] {
					Long.valueOf(user.getCompanyId()),
					Boolean.valueOf(user.getDefaultUser())
				}, user);
		}

		if (!isNew &&
				((user.getCompanyId() != userModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(user.getScreenName(),
					userModelImpl.getOriginalScreenName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_SN,
				new Object[] {
					Long.valueOf(userModelImpl.getOriginalCompanyId()),
					
				userModelImpl.getOriginalScreenName()
				});
		}

		if (isNew ||
				((user.getCompanyId() != userModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(user.getScreenName(),
					userModelImpl.getOriginalScreenName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_SN,
				new Object[] {
					Long.valueOf(user.getCompanyId()),
					
				user.getScreenName()
				}, user);
		}

		if (!isNew &&
				((user.getCompanyId() != userModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(user.getEmailAddress(),
					userModelImpl.getOriginalEmailAddress()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_EA,
				new Object[] {
					Long.valueOf(userModelImpl.getOriginalCompanyId()),
					
				userModelImpl.getOriginalEmailAddress()
				});
		}

		if (isNew ||
				((user.getCompanyId() != userModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(user.getEmailAddress(),
					userModelImpl.getOriginalEmailAddress()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_EA,
				new Object[] {
					Long.valueOf(user.getCompanyId()),
					
				user.getEmailAddress()
				}, user);
		}

		if (!isNew &&
				((user.getCompanyId() != userModelImpl.getOriginalCompanyId()) ||
				(user.getFacebookId() != userModelImpl.getOriginalFacebookId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_FID,
				new Object[] {
					Long.valueOf(userModelImpl.getOriginalCompanyId()),
					Long.valueOf(userModelImpl.getOriginalFacebookId())
				});
		}

		if (isNew ||
				((user.getCompanyId() != userModelImpl.getOriginalCompanyId()) ||
				(user.getFacebookId() != userModelImpl.getOriginalFacebookId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_FID,
				new Object[] {
					Long.valueOf(user.getCompanyId()),
					Long.valueOf(user.getFacebookId())
				}, user);
		}

		if (!isNew &&
				((user.getCompanyId() != userModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(user.getOpenId(),
					userModelImpl.getOriginalOpenId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_O,
				new Object[] {
					Long.valueOf(userModelImpl.getOriginalCompanyId()),
					
				userModelImpl.getOriginalOpenId()
				});
		}

		if (isNew ||
				((user.getCompanyId() != userModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(user.getOpenId(),
					userModelImpl.getOriginalOpenId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_O,
				new Object[] { Long.valueOf(user.getCompanyId()), user.getOpenId() },
				user);
		}

		return user;
	}

	protected User toUnwrappedModel(User user) {
		if (user instanceof UserImpl) {
			return user;
		}

		UserImpl userImpl = new UserImpl();

		userImpl.setNew(user.isNew());
		userImpl.setPrimaryKey(user.getPrimaryKey());

		userImpl.setUuid(user.getUuid());
		userImpl.setUserId(user.getUserId());
		userImpl.setCompanyId(user.getCompanyId());
		userImpl.setCreateDate(user.getCreateDate());
		userImpl.setModifiedDate(user.getModifiedDate());
		userImpl.setDefaultUser(user.isDefaultUser());
		userImpl.setContactId(user.getContactId());
		userImpl.setPassword(user.getPassword());
		userImpl.setPasswordEncrypted(user.isPasswordEncrypted());
		userImpl.setPasswordReset(user.isPasswordReset());
		userImpl.setPasswordModifiedDate(user.getPasswordModifiedDate());
		userImpl.setDigest(user.getDigest());
		userImpl.setReminderQueryQuestion(user.getReminderQueryQuestion());
		userImpl.setReminderQueryAnswer(user.getReminderQueryAnswer());
		userImpl.setGraceLoginCount(user.getGraceLoginCount());
		userImpl.setScreenName(user.getScreenName());
		userImpl.setEmailAddress(user.getEmailAddress());
		userImpl.setFacebookId(user.getFacebookId());
		userImpl.setOpenId(user.getOpenId());
		userImpl.setPortraitId(user.getPortraitId());
		userImpl.setLanguageId(user.getLanguageId());
		userImpl.setTimeZoneId(user.getTimeZoneId());
		userImpl.setGreeting(user.getGreeting());
		userImpl.setComments(user.getComments());
		userImpl.setFirstName(user.getFirstName());
		userImpl.setMiddleName(user.getMiddleName());
		userImpl.setLastName(user.getLastName());
		userImpl.setJobTitle(user.getJobTitle());
		userImpl.setLoginDate(user.getLoginDate());
		userImpl.setLoginIP(user.getLoginIP());
		userImpl.setLastLoginDate(user.getLastLoginDate());
		userImpl.setLastLoginIP(user.getLastLoginIP());
		userImpl.setLastFailedLoginDate(user.getLastFailedLoginDate());
		userImpl.setFailedLoginAttempts(user.getFailedLoginAttempts());
		userImpl.setLockout(user.isLockout());
		userImpl.setLockoutDate(user.getLockoutDate());
		userImpl.setAgreedToTermsOfUse(user.isAgreedToTermsOfUse());
		userImpl.setEmailAddressVerified(user.isEmailAddressVerified());
		userImpl.setStatus(user.getStatus());

		return userImpl;
	}

	/**
	 * Returns the user with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the user
	 * @return the user
	 * @throws com.liferay.portal.NoSuchModelException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public User findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the user with the primary key or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	 *
	 * @param userId the primary key of the user
	 * @return the user
	 * @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByPrimaryKey(long userId)
		throws NoSuchUserException, SystemException {
		User user = fetchByPrimaryKey(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + userId);
			}

			throw new NoSuchUserException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				userId);
		}

		return user;
	}

	/**
	 * Returns the user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user
	 * @return the user, or <code>null</code> if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public User fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userId the primary key of the user
	 * @return the user, or <code>null</code> if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByPrimaryKey(long userId) throws SystemException {
		User user = (User)EntityCacheUtil.getResult(UserModelImpl.ENTITY_CACHE_ENABLED,
				UserImpl.class, userId);

		if (user == _nullUser) {
			return null;
		}

		if (user == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				user = (User)session.get(UserImpl.class, Long.valueOf(userId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (user != null) {
					cacheResult(user);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(UserModelImpl.ENTITY_CACHE_ENABLED,
						UserImpl.class, userId, _nullUser);
				}

				closeSession(session);
			}
		}

		return user;
	}

	/**
	 * Returns all the users where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, start, end, orderByComparator };

		List<User> list = (List<User>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
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

			query.append(_SQL_SELECT_USER_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

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

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<User>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_UUID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		List<User> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last user in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByUuid_Last(String uuid, OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		int count = countByUuid(uuid);

		List<User> list = findByUuid(uuid, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the users before and after the current user in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the primary key of the current user
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User[] findByUuid_PrevAndNext(long userId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByUuid_PrevAndNext(session, user, uuid,
					orderByComparator, true);

			array[1] = user;

			array[2] = getByUuid_PrevAndNext(session, user, uuid,
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

	protected User getByUuid_PrevAndNext(Session session, User user,
		String uuid, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USER_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}
		}

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

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(user);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<User> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the users where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByCompanyId(long companyId) throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the users where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByCompanyId(long companyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId,
				
				start, end, orderByComparator
			};

		List<User> list = (List<User>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
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

			query.append(_SQL_SELECT_USER_WHERE);

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

				list = (List<User>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_COMPANYID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		List<User> list = findByCompanyId(companyId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		int count = countByCompanyId(companyId);

		List<User> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the users before and after the current user in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the primary key of the current user
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User[] findByCompanyId_PrevAndNext(long userId, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, user, companyId,
					orderByComparator, true);

			array[1] = user;

			array[2] = getByCompanyId_PrevAndNext(session, user, companyId,
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

	protected User getByCompanyId_PrevAndNext(Session session, User user,
		long companyId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USER_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(user);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<User> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the user where contactId = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	 *
	 * @param contactId the contact ID
	 * @return the matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByContactId(long contactId)
		throws NoSuchUserException, SystemException {
		User user = fetchByContactId(contactId);

		if (user == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("contactId=");
			msg.append(contactId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	/**
	 * Returns the user where contactId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param contactId the contact ID
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByContactId(long contactId) throws SystemException {
		return fetchByContactId(contactId, true);
	}

	/**
	 * Returns the user where contactId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param contactId the contact ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByContactId(long contactId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { contactId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_CONTACTID,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_CONTACTID_CONTACTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(contactId);

				List<User> list = q.list();

				result = list;

				User user = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONTACTID,
						finderArgs, list);
				}
				else {
					user = list.get(0);

					cacheResult(user);

					if ((user.getContactId() != contactId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONTACTID,
							finderArgs, user);
					}
				}

				return user;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CONTACTID,
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
				return (User)result;
			}
		}
	}

	/**
	 * Returns all the users where emailAddress = &#63;.
	 *
	 * @param emailAddress the email address
	 * @return the matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByEmailAddress(String emailAddress)
		throws SystemException {
		return findByEmailAddress(emailAddress, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where emailAddress = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByEmailAddress(String emailAddress, int start, int end)
		throws SystemException {
		return findByEmailAddress(emailAddress, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where emailAddress = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByEmailAddress(String emailAddress, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				emailAddress,
				
				start, end, orderByComparator
			};

		List<User> list = (List<User>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_EMAILADDRESS,
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

			query.append(_SQL_SELECT_USER_WHERE);

			if (emailAddress == null) {
				query.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_1);
			}
			else {
				if (emailAddress.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_3);
				}
				else {
					query.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_2);
				}
			}

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

				if (emailAddress != null) {
					qPos.add(emailAddress);
				}

				list = (List<User>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_EMAILADDRESS,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_EMAILADDRESS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user in the ordered set where emailAddress = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByEmailAddress_First(String emailAddress,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		List<User> list = findByEmailAddress(emailAddress, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("emailAddress=");
			msg.append(emailAddress);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last user in the ordered set where emailAddress = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByEmailAddress_Last(String emailAddress,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		int count = countByEmailAddress(emailAddress);

		List<User> list = findByEmailAddress(emailAddress, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("emailAddress=");
			msg.append(emailAddress);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the users before and after the current user in the ordered set where emailAddress = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the primary key of the current user
	 * @param emailAddress the email address
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User[] findByEmailAddress_PrevAndNext(long userId,
		String emailAddress, OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByEmailAddress_PrevAndNext(session, user,
					emailAddress, orderByComparator, true);

			array[1] = user;

			array[2] = getByEmailAddress_PrevAndNext(session, user,
					emailAddress, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected User getByEmailAddress_PrevAndNext(Session session, User user,
		String emailAddress, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USER_WHERE);

		if (emailAddress == null) {
			query.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_1);
		}
		else {
			if (emailAddress.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_3);
			}
			else {
				query.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_2);
			}
		}

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

		if (emailAddress != null) {
			qPos.add(emailAddress);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(user);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<User> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the user where portraitId = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	 *
	 * @param portraitId the portrait ID
	 * @return the matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByPortraitId(long portraitId)
		throws NoSuchUserException, SystemException {
		User user = fetchByPortraitId(portraitId);

		if (user == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("portraitId=");
			msg.append(portraitId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	/**
	 * Returns the user where portraitId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param portraitId the portrait ID
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByPortraitId(long portraitId) throws SystemException {
		return fetchByPortraitId(portraitId, true);
	}

	/**
	 * Returns the user where portraitId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param portraitId the portrait ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByPortraitId(long portraitId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { portraitId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_PORTRAITID,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_PORTRAITID_PORTRAITID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(portraitId);

				List<User> list = q.list();

				result = list;

				User user = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PORTRAITID,
						finderArgs, list);
				}
				else {
					user = list.get(0);

					cacheResult(user);

					if ((user.getPortraitId() != portraitId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PORTRAITID,
							finderArgs, user);
					}
				}

				return user;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PORTRAITID,
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
				return (User)result;
			}
		}
	}

	/**
	 * Returns the user where companyId = &#63; and userId = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_U(long companyId, long userId)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_U(companyId, userId);

		if (user == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	/**
	 * Returns the user where companyId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_U(long companyId, long userId)
		throws SystemException {
		return fetchByC_U(companyId, userId, true);
	}

	/**
	 * Returns the user where companyId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_U(long companyId, long userId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, userId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_U,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				List<User> list = q.list();

				result = list;

				User user = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U,
						finderArgs, list);
				}
				else {
					user = list.get(0);

					cacheResult(user);

					if ((user.getCompanyId() != companyId) ||
							(user.getUserId() != userId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U,
							finderArgs, user);
					}
				}

				return user;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_U,
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
				return (User)result;
			}
		}
	}

	/**
	 * Returns the user where companyId = &#63; and defaultUser = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @return the matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_DU(long companyId, boolean defaultUser)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_DU(companyId, defaultUser);

		if (user == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", defaultUser=");
			msg.append(defaultUser);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	/**
	 * Returns the user where companyId = &#63; and defaultUser = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_DU(long companyId, boolean defaultUser)
		throws SystemException {
		return fetchByC_DU(companyId, defaultUser, true);
	}

	/**
	 * Returns the user where companyId = &#63; and defaultUser = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_DU(long companyId, boolean defaultUser,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, defaultUser };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_DU,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_DU_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_DU_DEFAULTUSER_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(defaultUser);

				List<User> list = q.list();

				result = list;

				User user = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DU,
						finderArgs, list);
				}
				else {
					user = list.get(0);

					cacheResult(user);

					if ((user.getCompanyId() != companyId) ||
							(user.getDefaultUser() != defaultUser)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DU,
							finderArgs, user);
					}
				}

				return user;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_DU,
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
				return (User)result;
			}
		}
	}

	/**
	 * Returns the user where companyId = &#63; and screenName = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param screenName the screen name
	 * @return the matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_SN(long companyId, String screenName)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_SN(companyId, screenName);

		if (user == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", screenName=");
			msg.append(screenName);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	/**
	 * Returns the user where companyId = &#63; and screenName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param screenName the screen name
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_SN(long companyId, String screenName)
		throws SystemException {
		return fetchByC_SN(companyId, screenName, true);
	}

	/**
	 * Returns the user where companyId = &#63; and screenName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param screenName the screen name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_SN(long companyId, String screenName,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, screenName };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_SN,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_SN_COMPANYID_2);

			if (screenName == null) {
				query.append(_FINDER_COLUMN_C_SN_SCREENNAME_1);
			}
			else {
				if (screenName.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_SN_SCREENNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_SN_SCREENNAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (screenName != null) {
					qPos.add(screenName);
				}

				List<User> list = q.list();

				result = list;

				User user = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_SN,
						finderArgs, list);
				}
				else {
					user = list.get(0);

					cacheResult(user);

					if ((user.getCompanyId() != companyId) ||
							(user.getScreenName() == null) ||
							!user.getScreenName().equals(screenName)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_SN,
							finderArgs, user);
					}
				}

				return user;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_SN,
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
				return (User)result;
			}
		}
	}

	/**
	 * Returns the user where companyId = &#63; and emailAddress = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_EA(long companyId, String emailAddress)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_EA(companyId, emailAddress);

		if (user == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", emailAddress=");
			msg.append(emailAddress);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	/**
	 * Returns the user where companyId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_EA(long companyId, String emailAddress)
		throws SystemException {
		return fetchByC_EA(companyId, emailAddress, true);
	}

	/**
	 * Returns the user where companyId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_EA(long companyId, String emailAddress,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, emailAddress };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_EA,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_EA_COMPANYID_2);

			if (emailAddress == null) {
				query.append(_FINDER_COLUMN_C_EA_EMAILADDRESS_1);
			}
			else {
				if (emailAddress.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_EA_EMAILADDRESS_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_EA_EMAILADDRESS_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (emailAddress != null) {
					qPos.add(emailAddress);
				}

				List<User> list = q.list();

				result = list;

				User user = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_EA,
						finderArgs, list);
				}
				else {
					user = list.get(0);

					cacheResult(user);

					if ((user.getCompanyId() != companyId) ||
							(user.getEmailAddress() == null) ||
							!user.getEmailAddress().equals(emailAddress)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_EA,
							finderArgs, user);
					}
				}

				return user;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_EA,
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
				return (User)result;
			}
		}
	}

	/**
	 * Returns the user where companyId = &#63; and facebookId = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param facebookId the facebook ID
	 * @return the matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_FID(long companyId, long facebookId)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_FID(companyId, facebookId);

		if (user == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", facebookId=");
			msg.append(facebookId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	/**
	 * Returns the user where companyId = &#63; and facebookId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param facebookId the facebook ID
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_FID(long companyId, long facebookId)
		throws SystemException {
		return fetchByC_FID(companyId, facebookId, true);
	}

	/**
	 * Returns the user where companyId = &#63; and facebookId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param facebookId the facebook ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_FID(long companyId, long facebookId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, facebookId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_FID,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_FID_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_FID_FACEBOOKID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(facebookId);

				List<User> list = q.list();

				result = list;

				User user = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_FID,
						finderArgs, list);
				}
				else {
					user = list.get(0);

					cacheResult(user);

					if ((user.getCompanyId() != companyId) ||
							(user.getFacebookId() != facebookId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_FID,
							finderArgs, user);
					}
				}

				return user;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_FID,
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
				return (User)result;
			}
		}
	}

	/**
	 * Returns the user where companyId = &#63; and openId = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param openId the open ID
	 * @return the matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_O(long companyId, String openId)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_O(companyId, openId);

		if (user == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", openId=");
			msg.append(openId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	/**
	 * Returns the user where companyId = &#63; and openId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param openId the open ID
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_O(long companyId, String openId)
		throws SystemException {
		return fetchByC_O(companyId, openId, true);
	}

	/**
	 * Returns the user where companyId = &#63; and openId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param openId the open ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_O(long companyId, String openId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, openId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_O,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_O_COMPANYID_2);

			if (openId == null) {
				query.append(_FINDER_COLUMN_C_O_OPENID_1);
			}
			else {
				if (openId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_O_OPENID_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_O_OPENID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (openId != null) {
					qPos.add(openId);
				}

				List<User> list = q.list();

				result = list;

				User user = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_O,
						finderArgs, list);
				}
				else {
					user = list.get(0);

					cacheResult(user);

					if ((user.getCompanyId() != companyId) ||
							(user.getOpenId() == null) ||
							!user.getOpenId().equals(openId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_O,
							finderArgs, user);
					}
				}

				return user;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_O,
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
				return (User)result;
			}
		}
	}

	/**
	 * Returns all the users where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByC_S(long companyId, int status)
		throws SystemException {
		return findByC_S(companyId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByC_S(long companyId, int status, int start, int end)
		throws SystemException {
		return findByC_S(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByC_S(long companyId, int status, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, status,
				
				start, end, orderByComparator
			};

		List<User> list = (List<User>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_S,
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

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_S_STATUS_2);

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

				qPos.add(status);

				list = (List<User>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_C_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_S_First(long companyId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		List<User> list = findByC_S(companyId, status, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_S_Last(long companyId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		int count = countByC_S(companyId, status);

		List<User> list = findByC_S(companyId, status, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", status=");
			msg.append(status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the users before and after the current user in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the primary key of the current user
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User[] findByC_S_PrevAndNext(long userId, long companyId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByC_S_PrevAndNext(session, user, companyId, status,
					orderByComparator, true);

			array[1] = user;

			array[2] = getByC_S_PrevAndNext(session, user, companyId, status,
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

	protected User getByC_S_PrevAndNext(Session session, User user,
		long companyId, int status, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USER_WHERE);

		query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_S_STATUS_2);

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

		qPos.add(companyId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(user);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<User> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the users.
	 *
	 * @return the users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		List<User> list = (List<User>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_USER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_USER;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Removes all the users where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (User user : findByUuid(uuid)) {
			userPersistence.remove(user);
		}
	}

	/**
	 * Removes all the users where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (User user : findByCompanyId(companyId)) {
			userPersistence.remove(user);
		}
	}

	/**
	 * Removes the user where contactId = &#63; from the database.
	 *
	 * @param contactId the contact ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByContactId(long contactId)
		throws NoSuchUserException, SystemException {
		User user = findByContactId(contactId);

		userPersistence.remove(user);
	}

	/**
	 * Removes all the users where emailAddress = &#63; from the database.
	 *
	 * @param emailAddress the email address
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByEmailAddress(String emailAddress)
		throws SystemException {
		for (User user : findByEmailAddress(emailAddress)) {
			userPersistence.remove(user);
		}
	}

	/**
	 * Removes the user where portraitId = &#63; from the database.
	 *
	 * @param portraitId the portrait ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByPortraitId(long portraitId)
		throws NoSuchUserException, SystemException {
		User user = findByPortraitId(portraitId);

		userPersistence.remove(user);
	}

	/**
	 * Removes the user where companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_U(long companyId, long userId)
		throws NoSuchUserException, SystemException {
		User user = findByC_U(companyId, userId);

		userPersistence.remove(user);
	}

	/**
	 * Removes the user where companyId = &#63; and defaultUser = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_DU(long companyId, boolean defaultUser)
		throws NoSuchUserException, SystemException {
		User user = findByC_DU(companyId, defaultUser);

		userPersistence.remove(user);
	}

	/**
	 * Removes the user where companyId = &#63; and screenName = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param screenName the screen name
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_SN(long companyId, String screenName)
		throws NoSuchUserException, SystemException {
		User user = findByC_SN(companyId, screenName);

		userPersistence.remove(user);
	}

	/**
	 * Removes the user where companyId = &#63; and emailAddress = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_EA(long companyId, String emailAddress)
		throws NoSuchUserException, SystemException {
		User user = findByC_EA(companyId, emailAddress);

		userPersistence.remove(user);
	}

	/**
	 * Removes the user where companyId = &#63; and facebookId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param facebookId the facebook ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_FID(long companyId, long facebookId)
		throws NoSuchUserException, SystemException {
		User user = findByC_FID(companyId, facebookId);

		userPersistence.remove(user);
	}

	/**
	 * Removes the user where companyId = &#63; and openId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param openId the open ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_O(long companyId, String openId)
		throws NoSuchUserException, SystemException {
		User user = findByC_O(companyId, openId);

		userPersistence.remove(user);
	}

	/**
	 * Removes all the users where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_S(long companyId, int status)
		throws SystemException {
		for (User user : findByC_S(companyId, status)) {
			userPersistence.remove(user);
		}
	}

	/**
	 * Removes all the users from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (User user : findAll()) {
			userPersistence.remove(user);
		}
	}

	/**
	 * Returns the number of users where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USER_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of users where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USER_WHERE);

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
	 * Returns the number of users where contactId = &#63;.
	 *
	 * @param contactId the contact ID
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByContactId(long contactId) throws SystemException {
		Object[] finderArgs = new Object[] { contactId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CONTACTID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_CONTACTID_CONTACTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(contactId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CONTACTID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of users where emailAddress = &#63;.
	 *
	 * @param emailAddress the email address
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByEmailAddress(String emailAddress)
		throws SystemException {
		Object[] finderArgs = new Object[] { emailAddress };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_EMAILADDRESS,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USER_WHERE);

			if (emailAddress == null) {
				query.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_1);
			}
			else {
				if (emailAddress.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_3);
				}
				else {
					query.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (emailAddress != null) {
					qPos.add(emailAddress);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_EMAILADDRESS,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of users where portraitId = &#63;.
	 *
	 * @param portraitId the portrait ID
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByPortraitId(long portraitId) throws SystemException {
		Object[] finderArgs = new Object[] { portraitId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PORTRAITID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_PORTRAITID_PORTRAITID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(portraitId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PORTRAITID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of users where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_U(long companyId, long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_U,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_U, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of users where companyId = &#63; and defaultUser = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_DU(long companyId, boolean defaultUser)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, defaultUser };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_DU,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_DU_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_DU_DEFAULTUSER_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(defaultUser);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_DU,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of users where companyId = &#63; and screenName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param screenName the screen name
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_SN(long companyId, String screenName)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, screenName };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_SN,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_SN_COMPANYID_2);

			if (screenName == null) {
				query.append(_FINDER_COLUMN_C_SN_SCREENNAME_1);
			}
			else {
				if (screenName.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_SN_SCREENNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_SN_SCREENNAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (screenName != null) {
					qPos.add(screenName);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_SN,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of users where companyId = &#63; and emailAddress = &#63;.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_EA(long companyId, String emailAddress)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, emailAddress };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_EA,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_EA_COMPANYID_2);

			if (emailAddress == null) {
				query.append(_FINDER_COLUMN_C_EA_EMAILADDRESS_1);
			}
			else {
				if (emailAddress.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_EA_EMAILADDRESS_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_EA_EMAILADDRESS_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (emailAddress != null) {
					qPos.add(emailAddress);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_EA,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of users where companyId = &#63; and facebookId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param facebookId the facebook ID
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_FID(long companyId, long facebookId)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, facebookId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_FID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_FID_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_FID_FACEBOOKID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(facebookId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_FID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of users where companyId = &#63; and openId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param openId the open ID
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_O(long companyId, String openId)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, openId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_O,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_O_COMPANYID_2);

			if (openId == null) {
				query.append(_FINDER_COLUMN_C_O_OPENID_1);
			}
			else {
				if (openId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_O_OPENID_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_O_OPENID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (openId != null) {
					qPos.add(openId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_O, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of users where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_S(long companyId, int status) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of users.
	 *
	 * @return the number of users
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_USER);

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
	 * Returns all the groups associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the groups associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Group> getGroups(long pk)
		throws SystemException {
		return getGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the groups associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of groups associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Group> getGroups(long pk, int start,
		int end) throws SystemException {
		return getGroups(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_GROUPS = new FinderPath(com.liferay.portal.model.impl.GroupModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_GROUPS,
			com.liferay.portal.model.impl.GroupImpl.class,
			UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME, "getGroups",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	/**
	 * Returns an ordered range of all the groups associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of groups associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Group> getGroups(long pk, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portal.model.Group> list = (List<com.liferay.portal.model.Group>)FinderCacheUtil.getResult(FINDER_PATH_GET_GROUPS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETGROUPS.concat(ORDER_BY_CLAUSE)
										.concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETGROUPS.concat(com.liferay.portal.model.impl.GroupModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Group_",
					com.liferay.portal.model.impl.GroupImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.Group>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_GROUPS,
						finderArgs);
				}
				else {
					groupPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_GROUPS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_GROUPS_SIZE = new FinderPath(com.liferay.portal.model.impl.GroupModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_GROUPS, Long.class,
			UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME, "getGroupsSize",
			new String[] { Long.class.getName() });

	/**
	 * Returns the number of groups associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the number of groups associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public int getGroupsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_GROUPS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETGROUPSSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_GROUPS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_GROUP = new FinderPath(com.liferay.portal.model.impl.GroupModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_GROUPS, Boolean.class,
			UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME, "containsGroup",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the group is associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @param groupPK the primary key of the group
	 * @return <code>true</code> if the group is associated with the user; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsGroup(long pk, long groupPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, groupPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_GROUP,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsGroup.contains(pk, groupPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_GROUP,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the user has any groups associated with it.
	 *
	 * @param pk the primary key of the user to check for associations with groups
	 * @return <code>true</code> if the user has any groups associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsGroups(long pk) throws SystemException {
		if (getGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groupPK the primary key of the group
	 * @throws SystemException if a system exception occurred
	 */
	public void addGroup(long pk, long groupPK) throws SystemException {
		try {
			addGroup.add(pk, groupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param group the group
	 * @throws SystemException if a system exception occurred
	 */
	public void addGroup(long pk, com.liferay.portal.model.Group group)
		throws SystemException {
		try {
			addGroup.add(pk, group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groupPKs the primary keys of the groups
	 * @throws SystemException if a system exception occurred
	 */
	public void addGroups(long pk, long[] groupPKs) throws SystemException {
		try {
			for (long groupPK : groupPKs) {
				addGroup.add(pk, groupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groups the groups
	 * @throws SystemException if a system exception occurred
	 */
	public void addGroups(long pk, List<com.liferay.portal.model.Group> groups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Group group : groups) {
				addGroup.add(pk, group.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Clears all associations between the user and its groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user to clear the associated groups from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearGroups(long pk) throws SystemException {
		try {
			clearGroups.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groupPK the primary key of the group
	 * @throws SystemException if a system exception occurred
	 */
	public void removeGroup(long pk, long groupPK) throws SystemException {
		try {
			removeGroup.remove(pk, groupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param group the group
	 * @throws SystemException if a system exception occurred
	 */
	public void removeGroup(long pk, com.liferay.portal.model.Group group)
		throws SystemException {
		try {
			removeGroup.remove(pk, group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groupPKs the primary keys of the groups
	 * @throws SystemException if a system exception occurred
	 */
	public void removeGroups(long pk, long[] groupPKs)
		throws SystemException {
		try {
			for (long groupPK : groupPKs) {
				removeGroup.remove(pk, groupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groups the groups
	 * @throws SystemException if a system exception occurred
	 */
	public void removeGroups(long pk,
		List<com.liferay.portal.model.Group> groups) throws SystemException {
		try {
			for (com.liferay.portal.model.Group group : groups) {
				removeGroup.remove(pk, group.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Sets the groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groupPKs the primary keys of the groups to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public void setGroups(long pk, long[] groupPKs) throws SystemException {
		try {
			Set<Long> groupPKSet = SetUtil.fromArray(groupPKs);

			List<com.liferay.portal.model.Group> groups = getGroups(pk);

			for (com.liferay.portal.model.Group group : groups) {
				if (!groupPKSet.remove(group.getPrimaryKey())) {
					removeGroup.remove(pk, group.getPrimaryKey());
				}
			}

			for (Long groupPK : groupPKSet) {
				addGroup.add(pk, groupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Sets the groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groups the groups to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public void setGroups(long pk, List<com.liferay.portal.model.Group> groups)
		throws SystemException {
		try {
			long[] groupPKs = new long[groups.size()];

			for (int i = 0; i < groups.size(); i++) {
				com.liferay.portal.model.Group group = groups.get(i);

				groupPKs[i] = group.getPrimaryKey();
			}

			setGroups(pk, groupPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Returns all the organizations associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the organizations associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Organization> getOrganizations(long pk)
		throws SystemException {
		return getOrganizations(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the organizations associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of organizations associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end) throws SystemException {
		return getOrganizations(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_ORGANIZATIONS = new FinderPath(com.liferay.portal.model.impl.OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_ORGS,
			com.liferay.portal.model.impl.OrganizationImpl.class,
			UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME, "getOrganizations",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	/**
	 * Returns an ordered range of all the organizations associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of organizations associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portal.model.Organization> list = (List<com.liferay.portal.model.Organization>)FinderCacheUtil.getResult(FINDER_PATH_GET_ORGANIZATIONS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETORGANIZATIONS.concat(ORDER_BY_CLAUSE)
											   .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETORGANIZATIONS.concat(com.liferay.portal.model.impl.OrganizationModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Organization_",
					com.liferay.portal.model.impl.OrganizationImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.Organization>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_ORGANIZATIONS,
						finderArgs);
				}
				else {
					organizationPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_ORGANIZATIONS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_ORGANIZATIONS_SIZE = new FinderPath(com.liferay.portal.model.impl.OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_ORGS, Long.class,
			UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME,
			"getOrganizationsSize", new String[] { Long.class.getName() });

	/**
	 * Returns the number of organizations associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the number of organizations associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public int getOrganizationsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_ORGANIZATIONS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETORGANIZATIONSSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_ORGANIZATIONS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_ORGANIZATION = new FinderPath(com.liferay.portal.model.impl.OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_ORGS, Boolean.class,
			UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME,
			"containsOrganization",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the organization is associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPK the primary key of the organization
	 * @return <code>true</code> if the organization is associated with the user; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsOrganization(long pk, long organizationPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, organizationPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_ORGANIZATION,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsOrganization.contains(pk,
							organizationPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_ORGANIZATION,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the user has any organizations associated with it.
	 *
	 * @param pk the primary key of the user to check for associations with organizations
	 * @return <code>true</code> if the user has any organizations associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsOrganizations(long pk) throws SystemException {
		if (getOrganizationsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPK the primary key of the organization
	 * @throws SystemException if a system exception occurred
	 */
	public void addOrganization(long pk, long organizationPK)
		throws SystemException {
		try {
			addOrganization.add(pk, organizationPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organization the organization
	 * @throws SystemException if a system exception occurred
	 */
	public void addOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws SystemException {
		try {
			addOrganization.add(pk, organization.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPKs the primary keys of the organizations
	 * @throws SystemException if a system exception occurred
	 */
	public void addOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		try {
			for (long organizationPK : organizationPKs) {
				addOrganization.add(pk, organizationPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizations the organizations
	 * @throws SystemException if a system exception occurred
	 */
	public void addOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Organization organization : organizations) {
				addOrganization.add(pk, organization.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Clears all associations between the user and its organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user to clear the associated organizations from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearOrganizations(long pk) throws SystemException {
		try {
			clearOrganizations.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPK the primary key of the organization
	 * @throws SystemException if a system exception occurred
	 */
	public void removeOrganization(long pk, long organizationPK)
		throws SystemException {
		try {
			removeOrganization.remove(pk, organizationPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organization the organization
	 * @throws SystemException if a system exception occurred
	 */
	public void removeOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws SystemException {
		try {
			removeOrganization.remove(pk, organization.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPKs the primary keys of the organizations
	 * @throws SystemException if a system exception occurred
	 */
	public void removeOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		try {
			for (long organizationPK : organizationPKs) {
				removeOrganization.remove(pk, organizationPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizations the organizations
	 * @throws SystemException if a system exception occurred
	 */
	public void removeOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Organization organization : organizations) {
				removeOrganization.remove(pk, organization.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Sets the organizations associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPKs the primary keys of the organizations to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public void setOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		try {
			Set<Long> organizationPKSet = SetUtil.fromArray(organizationPKs);

			List<com.liferay.portal.model.Organization> organizations = getOrganizations(pk);

			for (com.liferay.portal.model.Organization organization : organizations) {
				if (!organizationPKSet.remove(organization.getPrimaryKey())) {
					removeOrganization.remove(pk, organization.getPrimaryKey());
				}
			}

			for (Long organizationPK : organizationPKSet) {
				addOrganization.add(pk, organizationPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Sets the organizations associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizations the organizations to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public void setOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		try {
			long[] organizationPKs = new long[organizations.size()];

			for (int i = 0; i < organizations.size(); i++) {
				com.liferay.portal.model.Organization organization = organizations.get(i);

				organizationPKs[i] = organization.getPrimaryKey();
			}

			setOrganizations(pk, organizationPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Returns all the permissions associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the permissions associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Permission> getPermissions(long pk)
		throws SystemException {
		return getPermissions(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the permissions associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of permissions associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Permission> getPermissions(long pk,
		int start, int end) throws SystemException {
		return getPermissions(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_PERMISSIONS = new FinderPath(com.liferay.portal.model.impl.PermissionModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_PERMISSIONS,
			com.liferay.portal.model.impl.PermissionImpl.class,
			UserModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME,
			"getPermissions",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	/**
	 * Returns an ordered range of all the permissions associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of permissions associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Permission> getPermissions(long pk,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portal.model.Permission> list = (List<com.liferay.portal.model.Permission>)FinderCacheUtil.getResult(FINDER_PATH_GET_PERMISSIONS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETPERMISSIONS.concat(ORDER_BY_CLAUSE)
											 .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETPERMISSIONS;
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Permission_",
					com.liferay.portal.model.impl.PermissionImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.Permission>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_PERMISSIONS,
						finderArgs);
				}
				else {
					permissionPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_PERMISSIONS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_PERMISSIONS_SIZE = new FinderPath(com.liferay.portal.model.impl.PermissionModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_PERMISSIONS, Long.class,
			UserModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME,
			"getPermissionsSize", new String[] { Long.class.getName() });

	/**
	 * Returns the number of permissions associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the number of permissions associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public int getPermissionsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_PERMISSIONS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETPERMISSIONSSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_PERMISSIONS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_PERMISSION = new FinderPath(com.liferay.portal.model.impl.PermissionModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_PERMISSIONS,
			Boolean.class, UserModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME,
			"containsPermission",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the permission is associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @param permissionPK the primary key of the permission
	 * @return <code>true</code> if the permission is associated with the user; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsPermission(long pk, long permissionPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, permissionPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_PERMISSION,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsPermission.contains(pk,
							permissionPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_PERMISSION,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the user has any permissions associated with it.
	 *
	 * @param pk the primary key of the user to check for associations with permissions
	 * @return <code>true</code> if the user has any permissions associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsPermissions(long pk) throws SystemException {
		if (getPermissionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the user and the permission. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param permissionPK the primary key of the permission
	 * @throws SystemException if a system exception occurred
	 */
	public void addPermission(long pk, long permissionPK)
		throws SystemException {
		try {
			addPermission.add(pk, permissionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the permission. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param permission the permission
	 * @throws SystemException if a system exception occurred
	 */
	public void addPermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws SystemException {
		try {
			addPermission.add(pk, permission.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the permissions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param permissionPKs the primary keys of the permissions
	 * @throws SystemException if a system exception occurred
	 */
	public void addPermissions(long pk, long[] permissionPKs)
		throws SystemException {
		try {
			for (long permissionPK : permissionPKs) {
				addPermission.add(pk, permissionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the permissions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param permissions the permissions
	 * @throws SystemException if a system exception occurred
	 */
	public void addPermissions(long pk,
		List<com.liferay.portal.model.Permission> permissions)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Permission permission : permissions) {
				addPermission.add(pk, permission.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Clears all associations between the user and its permissions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user to clear the associated permissions from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearPermissions(long pk) throws SystemException {
		try {
			clearPermissions.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the permission. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param permissionPK the primary key of the permission
	 * @throws SystemException if a system exception occurred
	 */
	public void removePermission(long pk, long permissionPK)
		throws SystemException {
		try {
			removePermission.remove(pk, permissionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the permission. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param permission the permission
	 * @throws SystemException if a system exception occurred
	 */
	public void removePermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws SystemException {
		try {
			removePermission.remove(pk, permission.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the permissions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param permissionPKs the primary keys of the permissions
	 * @throws SystemException if a system exception occurred
	 */
	public void removePermissions(long pk, long[] permissionPKs)
		throws SystemException {
		try {
			for (long permissionPK : permissionPKs) {
				removePermission.remove(pk, permissionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the permissions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param permissions the permissions
	 * @throws SystemException if a system exception occurred
	 */
	public void removePermissions(long pk,
		List<com.liferay.portal.model.Permission> permissions)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Permission permission : permissions) {
				removePermission.remove(pk, permission.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Sets the permissions associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param permissionPKs the primary keys of the permissions to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public void setPermissions(long pk, long[] permissionPKs)
		throws SystemException {
		try {
			Set<Long> permissionPKSet = SetUtil.fromArray(permissionPKs);

			List<com.liferay.portal.model.Permission> permissions = getPermissions(pk);

			for (com.liferay.portal.model.Permission permission : permissions) {
				if (!permissionPKSet.remove(permission.getPrimaryKey())) {
					removePermission.remove(pk, permission.getPrimaryKey());
				}
			}

			for (Long permissionPK : permissionPKSet) {
				addPermission.add(pk, permissionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Sets the permissions associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param permissions the permissions to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public void setPermissions(long pk,
		List<com.liferay.portal.model.Permission> permissions)
		throws SystemException {
		try {
			long[] permissionPKs = new long[permissions.size()];

			for (int i = 0; i < permissions.size(); i++) {
				com.liferay.portal.model.Permission permission = permissions.get(i);

				permissionPKs[i] = permission.getPrimaryKey();
			}

			setPermissions(pk, permissionPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_PERMISSIONS_NAME);
		}
	}

	/**
	 * Returns all the roles associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the roles associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Role> getRoles(long pk)
		throws SystemException {
		return getRoles(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the roles associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of roles associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end) throws SystemException {
		return getRoles(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_ROLES = new FinderPath(com.liferay.portal.model.impl.RoleModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_ROLES,
			com.liferay.portal.model.impl.RoleImpl.class,
			UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME, "getRoles",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	/**
	 * Returns an ordered range of all the roles associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of roles associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portal.model.Role> list = (List<com.liferay.portal.model.Role>)FinderCacheUtil.getResult(FINDER_PATH_GET_ROLES,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETROLES.concat(ORDER_BY_CLAUSE)
									   .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETROLES.concat(com.liferay.portal.model.impl.RoleModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Role_",
					com.liferay.portal.model.impl.RoleImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.Role>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_ROLES,
						finderArgs);
				}
				else {
					rolePersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_ROLES,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_ROLES_SIZE = new FinderPath(com.liferay.portal.model.impl.RoleModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_ROLES, Long.class,
			UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME, "getRolesSize",
			new String[] { Long.class.getName() });

	/**
	 * Returns the number of roles associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the number of roles associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public int getRolesSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_ROLES_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETROLESSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_ROLES_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_ROLE = new FinderPath(com.liferay.portal.model.impl.RoleModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_ROLES, Boolean.class,
			UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME, "containsRole",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the role is associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @param rolePK the primary key of the role
	 * @return <code>true</code> if the role is associated with the user; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsRole(long pk, long rolePK) throws SystemException {
		Object[] finderArgs = new Object[] { pk, rolePK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_ROLE,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsRole.contains(pk, rolePK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_ROLE,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the user has any roles associated with it.
	 *
	 * @param pk the primary key of the user to check for associations with roles
	 * @return <code>true</code> if the user has any roles associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsRoles(long pk) throws SystemException {
		if (getRolesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param rolePK the primary key of the role
	 * @throws SystemException if a system exception occurred
	 */
	public void addRole(long pk, long rolePK) throws SystemException {
		try {
			addRole.add(pk, rolePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Adds an association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param role the role
	 * @throws SystemException if a system exception occurred
	 */
	public void addRole(long pk, com.liferay.portal.model.Role role)
		throws SystemException {
		try {
			addRole.add(pk, role.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Adds an association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param rolePKs the primary keys of the roles
	 * @throws SystemException if a system exception occurred
	 */
	public void addRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			for (long rolePK : rolePKs) {
				addRole.add(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Adds an association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param roles the roles
	 * @throws SystemException if a system exception occurred
	 */
	public void addRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Role role : roles) {
				addRole.add(pk, role.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Clears all associations between the user and its roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user to clear the associated roles from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearRoles(long pk) throws SystemException {
		try {
			clearRoles.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Removes the association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param rolePK the primary key of the role
	 * @throws SystemException if a system exception occurred
	 */
	public void removeRole(long pk, long rolePK) throws SystemException {
		try {
			removeRole.remove(pk, rolePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Removes the association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param role the role
	 * @throws SystemException if a system exception occurred
	 */
	public void removeRole(long pk, com.liferay.portal.model.Role role)
		throws SystemException {
		try {
			removeRole.remove(pk, role.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Removes the association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param rolePKs the primary keys of the roles
	 * @throws SystemException if a system exception occurred
	 */
	public void removeRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			for (long rolePK : rolePKs) {
				removeRole.remove(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Removes the association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param roles the roles
	 * @throws SystemException if a system exception occurred
	 */
	public void removeRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Role role : roles) {
				removeRole.remove(pk, role.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Sets the roles associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param rolePKs the primary keys of the roles to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public void setRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			Set<Long> rolePKSet = SetUtil.fromArray(rolePKs);

			List<com.liferay.portal.model.Role> roles = getRoles(pk);

			for (com.liferay.portal.model.Role role : roles) {
				if (!rolePKSet.remove(role.getPrimaryKey())) {
					removeRole.remove(pk, role.getPrimaryKey());
				}
			}

			for (Long rolePK : rolePKSet) {
				addRole.add(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Sets the roles associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param roles the roles to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public void setRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			long[] rolePKs = new long[roles.size()];

			for (int i = 0; i < roles.size(); i++) {
				com.liferay.portal.model.Role role = roles.get(i);

				rolePKs[i] = role.getPrimaryKey();
			}

			setRoles(pk, rolePKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Returns all the teams associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the teams associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Team> getTeams(long pk)
		throws SystemException {
		return getTeams(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the teams associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of teams associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Team> getTeams(long pk, int start,
		int end) throws SystemException {
		return getTeams(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_TEAMS = new FinderPath(com.liferay.portal.model.impl.TeamModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_TEAMS,
			com.liferay.portal.model.impl.TeamImpl.class,
			UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME, "getTeams",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	/**
	 * Returns an ordered range of all the teams associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of teams associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Team> getTeams(long pk, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portal.model.Team> list = (List<com.liferay.portal.model.Team>)FinderCacheUtil.getResult(FINDER_PATH_GET_TEAMS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETTEAMS.concat(ORDER_BY_CLAUSE)
									   .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETTEAMS.concat(com.liferay.portal.model.impl.TeamModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Team", com.liferay.portal.model.impl.TeamImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.Team>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_TEAMS,
						finderArgs);
				}
				else {
					teamPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_TEAMS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_TEAMS_SIZE = new FinderPath(com.liferay.portal.model.impl.TeamModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_TEAMS, Long.class,
			UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME, "getTeamsSize",
			new String[] { Long.class.getName() });

	/**
	 * Returns the number of teams associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the number of teams associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public int getTeamsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_TEAMS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETTEAMSSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_TEAMS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_TEAM = new FinderPath(com.liferay.portal.model.impl.TeamModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_TEAMS, Boolean.class,
			UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME, "containsTeam",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the team is associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @param teamPK the primary key of the team
	 * @return <code>true</code> if the team is associated with the user; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsTeam(long pk, long teamPK) throws SystemException {
		Object[] finderArgs = new Object[] { pk, teamPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_TEAM,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsTeam.contains(pk, teamPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_TEAM,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the user has any teams associated with it.
	 *
	 * @param pk the primary key of the user to check for associations with teams
	 * @return <code>true</code> if the user has any teams associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsTeams(long pk) throws SystemException {
		if (getTeamsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teamPK the primary key of the team
	 * @throws SystemException if a system exception occurred
	 */
	public void addTeam(long pk, long teamPK) throws SystemException {
		try {
			addTeam.add(pk, teamPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param team the team
	 * @throws SystemException if a system exception occurred
	 */
	public void addTeam(long pk, com.liferay.portal.model.Team team)
		throws SystemException {
		try {
			addTeam.add(pk, team.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teamPKs the primary keys of the teams
	 * @throws SystemException if a system exception occurred
	 */
	public void addTeams(long pk, long[] teamPKs) throws SystemException {
		try {
			for (long teamPK : teamPKs) {
				addTeam.add(pk, teamPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teams the teams
	 * @throws SystemException if a system exception occurred
	 */
	public void addTeams(long pk, List<com.liferay.portal.model.Team> teams)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Team team : teams) {
				addTeam.add(pk, team.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Clears all associations between the user and its teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user to clear the associated teams from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearTeams(long pk) throws SystemException {
		try {
			clearTeams.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teamPK the primary key of the team
	 * @throws SystemException if a system exception occurred
	 */
	public void removeTeam(long pk, long teamPK) throws SystemException {
		try {
			removeTeam.remove(pk, teamPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param team the team
	 * @throws SystemException if a system exception occurred
	 */
	public void removeTeam(long pk, com.liferay.portal.model.Team team)
		throws SystemException {
		try {
			removeTeam.remove(pk, team.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teamPKs the primary keys of the teams
	 * @throws SystemException if a system exception occurred
	 */
	public void removeTeams(long pk, long[] teamPKs) throws SystemException {
		try {
			for (long teamPK : teamPKs) {
				removeTeam.remove(pk, teamPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teams the teams
	 * @throws SystemException if a system exception occurred
	 */
	public void removeTeams(long pk, List<com.liferay.portal.model.Team> teams)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Team team : teams) {
				removeTeam.remove(pk, team.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Sets the teams associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teamPKs the primary keys of the teams to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public void setTeams(long pk, long[] teamPKs) throws SystemException {
		try {
			Set<Long> teamPKSet = SetUtil.fromArray(teamPKs);

			List<com.liferay.portal.model.Team> teams = getTeams(pk);

			for (com.liferay.portal.model.Team team : teams) {
				if (!teamPKSet.remove(team.getPrimaryKey())) {
					removeTeam.remove(pk, team.getPrimaryKey());
				}
			}

			for (Long teamPK : teamPKSet) {
				addTeam.add(pk, teamPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Sets the teams associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teams the teams to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public void setTeams(long pk, List<com.liferay.portal.model.Team> teams)
		throws SystemException {
		try {
			long[] teamPKs = new long[teams.size()];

			for (int i = 0; i < teams.size(); i++) {
				com.liferay.portal.model.Team team = teams.get(i);

				teamPKs[i] = team.getPrimaryKey();
			}

			setTeams(pk, teamPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Returns all the user groups associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the user groups associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk)
		throws SystemException {
		return getUserGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the user groups associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of user groups associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk,
		int start, int end) throws SystemException {
		return getUserGroups(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_USERGROUPS = new FinderPath(com.liferay.portal.model.impl.UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_USERGROUPS,
			com.liferay.portal.model.impl.UserGroupImpl.class,
			UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME, "getUserGroups",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	/**
	 * Returns an ordered range of all the user groups associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user groups associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portal.model.UserGroup> list = (List<com.liferay.portal.model.UserGroup>)FinderCacheUtil.getResult(FINDER_PATH_GET_USERGROUPS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETUSERGROUPS.concat(ORDER_BY_CLAUSE)
											.concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETUSERGROUPS.concat(com.liferay.portal.model.impl.UserGroupModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("UserGroup",
					com.liferay.portal.model.impl.UserGroupImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.UserGroup>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_USERGROUPS,
						finderArgs);
				}
				else {
					userGroupPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_USERGROUPS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_USERGROUPS_SIZE = new FinderPath(com.liferay.portal.model.impl.UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_USERGROUPS, Long.class,
			UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME,
			"getUserGroupsSize", new String[] { Long.class.getName() });

	/**
	 * Returns the number of user groups associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the number of user groups associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public int getUserGroupsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_USERGROUPS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETUSERGROUPSSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_USERGROUPS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_USERGROUP = new FinderPath(com.liferay.portal.model.impl.UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_USERGROUPS, Boolean.class,
			UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME,
			"containsUserGroup",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the user group is associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPK the primary key of the user group
	 * @return <code>true</code> if the user group is associated with the user; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsUserGroup(long pk, long userGroupPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, userGroupPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_USERGROUP,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsUserGroup.contains(pk,
							userGroupPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_USERGROUP,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the user has any user groups associated with it.
	 *
	 * @param pk the primary key of the user to check for associations with user groups
	 * @return <code>true</code> if the user has any user groups associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsUserGroups(long pk) throws SystemException {
		if (getUserGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPK the primary key of the user group
	 * @throws SystemException if a system exception occurred
	 */
	public void addUserGroup(long pk, long userGroupPK)
		throws SystemException {
		try {
			addUserGroup.add(pk, userGroupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroup the user group
	 * @throws SystemException if a system exception occurred
	 */
	public void addUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) throws SystemException {
		try {
			addUserGroup.add(pk, userGroup.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPKs the primary keys of the user groups
	 * @throws SystemException if a system exception occurred
	 */
	public void addUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			for (long userGroupPK : userGroupPKs) {
				addUserGroup.add(pk, userGroupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroups the user groups
	 * @throws SystemException if a system exception occurred
	 */
	public void addUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				addUserGroup.add(pk, userGroup.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Clears all associations between the user and its user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user to clear the associated user groups from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearUserGroups(long pk) throws SystemException {
		try {
			clearUserGroups.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPK the primary key of the user group
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUserGroup(long pk, long userGroupPK)
		throws SystemException {
		try {
			removeUserGroup.remove(pk, userGroupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroup the user group
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) throws SystemException {
		try {
			removeUserGroup.remove(pk, userGroup.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPKs the primary keys of the user groups
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			for (long userGroupPK : userGroupPKs) {
				removeUserGroup.remove(pk, userGroupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroups the user groups
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				removeUserGroup.remove(pk, userGroup.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Sets the user groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPKs the primary keys of the user groups to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public void setUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			Set<Long> userGroupPKSet = SetUtil.fromArray(userGroupPKs);

			List<com.liferay.portal.model.UserGroup> userGroups = getUserGroups(pk);

			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				if (!userGroupPKSet.remove(userGroup.getPrimaryKey())) {
					removeUserGroup.remove(pk, userGroup.getPrimaryKey());
				}
			}

			for (Long userGroupPK : userGroupPKSet) {
				addUserGroup.add(pk, userGroupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Sets the user groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroups the user groups to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public void setUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			long[] userGroupPKs = new long[userGroups.size()];

			for (int i = 0; i < userGroups.size(); i++) {
				com.liferay.portal.model.UserGroup userGroup = userGroups.get(i);

				userGroupPKs[i] = userGroup.getPrimaryKey();
			}

			setUserGroups(pk, userGroupPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Initializes the user persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.User")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<User>> listenersList = new ArrayList<ModelListener<User>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<User>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsGroup = new ContainsGroup(this);

		addGroup = new AddGroup(this);
		clearGroups = new ClearGroups(this);
		removeGroup = new RemoveGroup(this);

		containsOrganization = new ContainsOrganization(this);

		addOrganization = new AddOrganization(this);
		clearOrganizations = new ClearOrganizations(this);
		removeOrganization = new RemoveOrganization(this);

		containsPermission = new ContainsPermission(this);

		addPermission = new AddPermission(this);
		clearPermissions = new ClearPermissions(this);
		removePermission = new RemovePermission(this);

		containsRole = new ContainsRole(this);

		addRole = new AddRole(this);
		clearRoles = new ClearRoles(this);
		removeRole = new RemoveRole(this);

		containsTeam = new ContainsTeam(this);

		addTeam = new AddTeam(this);
		clearTeams = new ClearTeams(this);
		removeTeam = new RemoveTeam(this);

		containsUserGroup = new ContainsUserGroup(this);

		addUserGroup = new AddUserGroup(this);
		clearUserGroups = new ClearUserGroups(this);
		removeUserGroup = new RemoveUserGroup(this);
	}

	public void destroy() {
		EntityCacheUtil.removeCache(UserImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
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
	@BeanReference(type = OrgGroupPermissionPersistence.class)
	protected OrgGroupPermissionPersistence orgGroupPermissionPersistence;
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
	@BeanReference(type = PermissionPersistence.class)
	protected PermissionPersistence permissionPersistence;
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
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceBlockPersistence.class)
	protected ResourceBlockPersistence resourceBlockPersistence;
	@BeanReference(type = ResourceBlockPermissionPersistence.class)
	protected ResourceBlockPermissionPersistence resourceBlockPermissionPersistence;
	@BeanReference(type = ResourceCodePersistence.class)
	protected ResourceCodePersistence resourceCodePersistence;
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
	@BeanReference(type = AnnouncementsDeliveryPersistence.class)
	protected AnnouncementsDeliveryPersistence announcementsDeliveryPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = BlogsStatsUserPersistence.class)
	protected BlogsStatsUserPersistence blogsStatsUserPersistence;
	@BeanReference(type = DLFileRankPersistence.class)
	protected DLFileRankPersistence dlFileRankPersistence;
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(type = MBBanPersistence.class)
	protected MBBanPersistence mbBanPersistence;
	@BeanReference(type = MBMessagePersistence.class)
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(type = MBStatsUserPersistence.class)
	protected MBStatsUserPersistence mbStatsUserPersistence;
	@BeanReference(type = MBThreadFlagPersistence.class)
	protected MBThreadFlagPersistence mbThreadFlagPersistence;
	@BeanReference(type = ShoppingCartPersistence.class)
	protected ShoppingCartPersistence shoppingCartPersistence;
	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	@BeanReference(type = SocialEquityLogPersistence.class)
	protected SocialEquityLogPersistence socialEquityLogPersistence;
	@BeanReference(type = SocialEquityUserPersistence.class)
	protected SocialEquityUserPersistence socialEquityUserPersistence;
	@BeanReference(type = SocialRequestPersistence.class)
	protected SocialRequestPersistence socialRequestPersistence;
	protected ContainsGroup containsGroup;
	protected AddGroup addGroup;
	protected ClearGroups clearGroups;
	protected RemoveGroup removeGroup;
	protected ContainsOrganization containsOrganization;
	protected AddOrganization addOrganization;
	protected ClearOrganizations clearOrganizations;
	protected RemoveOrganization removeOrganization;
	protected ContainsPermission containsPermission;
	protected AddPermission addPermission;
	protected ClearPermissions clearPermissions;
	protected RemovePermission removePermission;
	protected ContainsRole containsRole;
	protected AddRole addRole;
	protected ClearRoles clearRoles;
	protected RemoveRole removeRole;
	protected ContainsTeam containsTeam;
	protected AddTeam addTeam;
	protected ClearTeams clearTeams;
	protected RemoveTeam removeTeam;
	protected ContainsUserGroup containsUserGroup;
	protected AddUserGroup addUserGroup;
	protected ClearUserGroups clearUserGroups;
	protected RemoveUserGroup removeUserGroup;

	protected class ContainsGroup {
		protected ContainsGroup(UserPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSGROUP,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long userId, long groupId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userId), new Long(groupId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddGroup {
		protected AddGroup(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Groups (userId, groupId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long userId, long groupId) throws SystemException {
			if (!_persistenceImpl.containsGroup.contains(userId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeAddAssociation(userId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeAddAssociation(groupId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(groupId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterAddAssociation(userId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterAddAssociation(groupId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ClearGroups {
		protected ClearGroups(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Groups WHERE userId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long userId) throws SystemException {
			ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

			List<com.liferay.portal.model.Group> groups = null;

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				groups = getGroups(userId);

				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<User> listener : listeners) {
						listener.onBeforeRemoveAssociation(userId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onBeforeRemoveAssociation(group.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userId) });

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<User> listener : listeners) {
						listener.onAfterRemoveAssociation(userId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onAfterRemoveAssociation(group.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveGroup {
		protected RemoveGroup(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Groups WHERE userId = ? AND groupId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long userId, long groupId)
			throws SystemException {
			if (_persistenceImpl.containsGroup.contains(userId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeRemoveAssociation(userId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeRemoveAssociation(groupId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(groupId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterRemoveAssociation(userId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterRemoveAssociation(groupId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ContainsOrganization {
		protected ContainsOrganization(UserPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSORGANIZATION,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long userId, long organizationId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userId), new Long(organizationId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddOrganization {
		protected AddOrganization(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Orgs (userId, organizationId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long userId, long organizationId)
			throws SystemException {
			if (!_persistenceImpl.containsOrganization.contains(userId,
						organizationId)) {
				ModelListener<com.liferay.portal.model.Organization>[] organizationListeners =
					organizationPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeAddAssociation(userId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onBeforeAddAssociation(organizationId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(organizationId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterAddAssociation(userId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onAfterAddAssociation(organizationId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ClearOrganizations {
		protected ClearOrganizations(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Orgs WHERE userId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long userId) throws SystemException {
			ModelListener<com.liferay.portal.model.Organization>[] organizationListeners =
				organizationPersistence.getListeners();

			List<com.liferay.portal.model.Organization> organizations = null;

			if ((listeners.length > 0) || (organizationListeners.length > 0)) {
				organizations = getOrganizations(userId);

				for (com.liferay.portal.model.Organization organization : organizations) {
					for (ModelListener<User> listener : listeners) {
						listener.onBeforeRemoveAssociation(userId,
							com.liferay.portal.model.Organization.class.getName(),
							organization.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
						listener.onBeforeRemoveAssociation(organization.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userId) });

			if ((listeners.length > 0) || (organizationListeners.length > 0)) {
				for (com.liferay.portal.model.Organization organization : organizations) {
					for (ModelListener<User> listener : listeners) {
						listener.onAfterRemoveAssociation(userId,
							com.liferay.portal.model.Organization.class.getName(),
							organization.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
						listener.onAfterRemoveAssociation(organization.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveOrganization {
		protected RemoveOrganization(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Orgs WHERE userId = ? AND organizationId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long userId, long organizationId)
			throws SystemException {
			if (_persistenceImpl.containsOrganization.contains(userId,
						organizationId)) {
				ModelListener<com.liferay.portal.model.Organization>[] organizationListeners =
					organizationPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeRemoveAssociation(userId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onBeforeRemoveAssociation(organizationId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(organizationId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterRemoveAssociation(userId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onAfterRemoveAssociation(organizationId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ContainsPermission {
		protected ContainsPermission(UserPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSPERMISSION,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long userId, long permissionId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userId), new Long(permissionId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddPermission {
		protected AddPermission(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Permissions (userId, permissionId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long userId, long permissionId)
			throws SystemException {
			if (!_persistenceImpl.containsPermission.contains(userId,
						permissionId)) {
				ModelListener<com.liferay.portal.model.Permission>[] permissionListeners =
					permissionPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeAddAssociation(userId,
						com.liferay.portal.model.Permission.class.getName(),
						permissionId);
				}

				for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
					listener.onBeforeAddAssociation(permissionId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(permissionId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterAddAssociation(userId,
						com.liferay.portal.model.Permission.class.getName(),
						permissionId);
				}

				for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
					listener.onAfterAddAssociation(permissionId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ClearPermissions {
		protected ClearPermissions(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Permissions WHERE userId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long userId) throws SystemException {
			ModelListener<com.liferay.portal.model.Permission>[] permissionListeners =
				permissionPersistence.getListeners();

			List<com.liferay.portal.model.Permission> permissions = null;

			if ((listeners.length > 0) || (permissionListeners.length > 0)) {
				permissions = getPermissions(userId);

				for (com.liferay.portal.model.Permission permission : permissions) {
					for (ModelListener<User> listener : listeners) {
						listener.onBeforeRemoveAssociation(userId,
							com.liferay.portal.model.Permission.class.getName(),
							permission.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
						listener.onBeforeRemoveAssociation(permission.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userId) });

			if ((listeners.length > 0) || (permissionListeners.length > 0)) {
				for (com.liferay.portal.model.Permission permission : permissions) {
					for (ModelListener<User> listener : listeners) {
						listener.onAfterRemoveAssociation(userId,
							com.liferay.portal.model.Permission.class.getName(),
							permission.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
						listener.onAfterRemoveAssociation(permission.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemovePermission {
		protected RemovePermission(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Permissions WHERE userId = ? AND permissionId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long userId, long permissionId)
			throws SystemException {
			if (_persistenceImpl.containsPermission.contains(userId,
						permissionId)) {
				ModelListener<com.liferay.portal.model.Permission>[] permissionListeners =
					permissionPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeRemoveAssociation(userId,
						com.liferay.portal.model.Permission.class.getName(),
						permissionId);
				}

				for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
					listener.onBeforeRemoveAssociation(permissionId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(permissionId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterRemoveAssociation(userId,
						com.liferay.portal.model.Permission.class.getName(),
						permissionId);
				}

				for (ModelListener<com.liferay.portal.model.Permission> listener : permissionListeners) {
					listener.onAfterRemoveAssociation(permissionId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ContainsRole {
		protected ContainsRole(UserPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSROLE,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long userId, long roleId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userId), new Long(roleId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddRole {
		protected AddRole(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Roles (userId, roleId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long userId, long roleId) throws SystemException {
			if (!_persistenceImpl.containsRole.contains(userId, roleId)) {
				ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeAddAssociation(userId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onBeforeAddAssociation(roleId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(roleId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterAddAssociation(userId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onAfterAddAssociation(roleId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ClearRoles {
		protected ClearRoles(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Roles WHERE userId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long userId) throws SystemException {
			ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

			List<com.liferay.portal.model.Role> roles = null;

			if ((listeners.length > 0) || (roleListeners.length > 0)) {
				roles = getRoles(userId);

				for (com.liferay.portal.model.Role role : roles) {
					for (ModelListener<User> listener : listeners) {
						listener.onBeforeRemoveAssociation(userId,
							com.liferay.portal.model.Role.class.getName(),
							role.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
						listener.onBeforeRemoveAssociation(role.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userId) });

			if ((listeners.length > 0) || (roleListeners.length > 0)) {
				for (com.liferay.portal.model.Role role : roles) {
					for (ModelListener<User> listener : listeners) {
						listener.onAfterRemoveAssociation(userId,
							com.liferay.portal.model.Role.class.getName(),
							role.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
						listener.onAfterRemoveAssociation(role.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveRole {
		protected RemoveRole(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Roles WHERE userId = ? AND roleId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long userId, long roleId)
			throws SystemException {
			if (_persistenceImpl.containsRole.contains(userId, roleId)) {
				ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeRemoveAssociation(userId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onBeforeRemoveAssociation(roleId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(roleId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterRemoveAssociation(userId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onAfterRemoveAssociation(roleId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ContainsTeam {
		protected ContainsTeam(UserPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSTEAM,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long userId, long teamId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userId), new Long(teamId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddTeam {
		protected AddTeam(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Teams (userId, teamId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long userId, long teamId) throws SystemException {
			if (!_persistenceImpl.containsTeam.contains(userId, teamId)) {
				ModelListener<com.liferay.portal.model.Team>[] teamListeners = teamPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeAddAssociation(userId,
						com.liferay.portal.model.Team.class.getName(), teamId);
				}

				for (ModelListener<com.liferay.portal.model.Team> listener : teamListeners) {
					listener.onBeforeAddAssociation(teamId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(teamId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterAddAssociation(userId,
						com.liferay.portal.model.Team.class.getName(), teamId);
				}

				for (ModelListener<com.liferay.portal.model.Team> listener : teamListeners) {
					listener.onAfterAddAssociation(teamId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ClearTeams {
		protected ClearTeams(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Teams WHERE userId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long userId) throws SystemException {
			ModelListener<com.liferay.portal.model.Team>[] teamListeners = teamPersistence.getListeners();

			List<com.liferay.portal.model.Team> teams = null;

			if ((listeners.length > 0) || (teamListeners.length > 0)) {
				teams = getTeams(userId);

				for (com.liferay.portal.model.Team team : teams) {
					for (ModelListener<User> listener : listeners) {
						listener.onBeforeRemoveAssociation(userId,
							com.liferay.portal.model.Team.class.getName(),
							team.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Team> listener : teamListeners) {
						listener.onBeforeRemoveAssociation(team.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userId) });

			if ((listeners.length > 0) || (teamListeners.length > 0)) {
				for (com.liferay.portal.model.Team team : teams) {
					for (ModelListener<User> listener : listeners) {
						listener.onAfterRemoveAssociation(userId,
							com.liferay.portal.model.Team.class.getName(),
							team.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Team> listener : teamListeners) {
						listener.onAfterRemoveAssociation(team.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveTeam {
		protected RemoveTeam(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Teams WHERE userId = ? AND teamId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long userId, long teamId)
			throws SystemException {
			if (_persistenceImpl.containsTeam.contains(userId, teamId)) {
				ModelListener<com.liferay.portal.model.Team>[] teamListeners = teamPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeRemoveAssociation(userId,
						com.liferay.portal.model.Team.class.getName(), teamId);
				}

				for (ModelListener<com.liferay.portal.model.Team> listener : teamListeners) {
					listener.onBeforeRemoveAssociation(teamId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(teamId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterRemoveAssociation(userId,
						com.liferay.portal.model.Team.class.getName(), teamId);
				}

				for (ModelListener<com.liferay.portal.model.Team> listener : teamListeners) {
					listener.onAfterRemoveAssociation(teamId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ContainsUserGroup {
		protected ContainsUserGroup(UserPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSUSERGROUP,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long userId, long userGroupId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userId), new Long(userGroupId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddUserGroup {
		protected AddUserGroup(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_UserGroups (userId, userGroupId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long userId, long userGroupId)
			throws SystemException {
			if (!_persistenceImpl.containsUserGroup.contains(userId, userGroupId)) {
				ModelListener<com.liferay.portal.model.UserGroup>[] userGroupListeners =
					userGroupPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeAddAssociation(userId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onBeforeAddAssociation(userGroupId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(userGroupId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterAddAssociation(userId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onAfterAddAssociation(userGroupId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ClearUserGroups {
		protected ClearUserGroups(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_UserGroups WHERE userId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long userId) throws SystemException {
			ModelListener<com.liferay.portal.model.UserGroup>[] userGroupListeners =
				userGroupPersistence.getListeners();

			List<com.liferay.portal.model.UserGroup> userGroups = null;

			if ((listeners.length > 0) || (userGroupListeners.length > 0)) {
				userGroups = getUserGroups(userId);

				for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
					for (ModelListener<User> listener : listeners) {
						listener.onBeforeRemoveAssociation(userId,
							com.liferay.portal.model.UserGroup.class.getName(),
							userGroup.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
						listener.onBeforeRemoveAssociation(userGroup.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userId) });

			if ((listeners.length > 0) || (userGroupListeners.length > 0)) {
				for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
					for (ModelListener<User> listener : listeners) {
						listener.onAfterRemoveAssociation(userId,
							com.liferay.portal.model.UserGroup.class.getName(),
							userGroup.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
						listener.onAfterRemoveAssociation(userGroup.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveUserGroup {
		protected RemoveUserGroup(UserPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_UserGroups WHERE userId = ? AND userGroupId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long userId, long userGroupId)
			throws SystemException {
			if (_persistenceImpl.containsUserGroup.contains(userId, userGroupId)) {
				ModelListener<com.liferay.portal.model.UserGroup>[] userGroupListeners =
					userGroupPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeRemoveAssociation(userId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onBeforeRemoveAssociation(userGroupId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(userGroupId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterRemoveAssociation(userId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onAfterRemoveAssociation(userGroupId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private UserPersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_SELECT_USER = "SELECT user FROM User user";
	private static final String _SQL_SELECT_USER_WHERE = "SELECT user FROM User user WHERE ";
	private static final String _SQL_COUNT_USER = "SELECT COUNT(user) FROM User user";
	private static final String _SQL_COUNT_USER_WHERE = "SELECT COUNT(user) FROM User user WHERE ";
	private static final String _SQL_GETGROUPS = "SELECT {Group_.*} FROM Group_ INNER JOIN Users_Groups ON (Users_Groups.groupId = Group_.groupId) WHERE (Users_Groups.userId = ?)";
	private static final String _SQL_GETGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Groups WHERE userId = ?";
	private static final String _SQL_CONTAINSGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Groups WHERE userId = ? AND groupId = ?";
	private static final String _SQL_GETORGANIZATIONS = "SELECT {Organization_.*} FROM Organization_ INNER JOIN Users_Orgs ON (Users_Orgs.organizationId = Organization_.organizationId) WHERE (Users_Orgs.userId = ?)";
	private static final String _SQL_GETORGANIZATIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Orgs WHERE userId = ?";
	private static final String _SQL_CONTAINSORGANIZATION = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Orgs WHERE userId = ? AND organizationId = ?";
	private static final String _SQL_GETPERMISSIONS = "SELECT {Permission_.*} FROM Permission_ INNER JOIN Users_Permissions ON (Users_Permissions.permissionId = Permission_.permissionId) WHERE (Users_Permissions.userId = ?)";
	private static final String _SQL_GETPERMISSIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Permissions WHERE userId = ?";
	private static final String _SQL_CONTAINSPERMISSION = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Permissions WHERE userId = ? AND permissionId = ?";
	private static final String _SQL_GETROLES = "SELECT {Role_.*} FROM Role_ INNER JOIN Users_Roles ON (Users_Roles.roleId = Role_.roleId) WHERE (Users_Roles.userId = ?)";
	private static final String _SQL_GETROLESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Roles WHERE userId = ?";
	private static final String _SQL_CONTAINSROLE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Roles WHERE userId = ? AND roleId = ?";
	private static final String _SQL_GETTEAMS = "SELECT {Team.*} FROM Team INNER JOIN Users_Teams ON (Users_Teams.teamId = Team.teamId) WHERE (Users_Teams.userId = ?)";
	private static final String _SQL_GETTEAMSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Teams WHERE userId = ?";
	private static final String _SQL_CONTAINSTEAM = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Teams WHERE userId = ? AND teamId = ?";
	private static final String _SQL_GETUSERGROUPS = "SELECT {UserGroup.*} FROM UserGroup INNER JOIN Users_UserGroups ON (Users_UserGroups.userGroupId = UserGroup.userGroupId) WHERE (Users_UserGroups.userId = ?)";
	private static final String _SQL_GETUSERGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_UserGroups WHERE userId = ?";
	private static final String _SQL_CONTAINSUSERGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_UserGroups WHERE userId = ? AND userGroupId = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "user.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "user.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(user.uuid IS NULL OR user.uuid = ?)";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "user.companyId = ?";
	private static final String _FINDER_COLUMN_CONTACTID_CONTACTID_2 = "user.contactId = ?";
	private static final String _FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_1 = "user.emailAddress IS NULL";
	private static final String _FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_2 = "user.emailAddress = ?";
	private static final String _FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_3 = "(user.emailAddress IS NULL OR user.emailAddress = ?)";
	private static final String _FINDER_COLUMN_PORTRAITID_PORTRAITID_2 = "user.portraitId = ?";
	private static final String _FINDER_COLUMN_C_U_COMPANYID_2 = "user.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_USERID_2 = "user.userId = ?";
	private static final String _FINDER_COLUMN_C_DU_COMPANYID_2 = "user.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_DU_DEFAULTUSER_2 = "user.defaultUser = ?";
	private static final String _FINDER_COLUMN_C_SN_COMPANYID_2 = "user.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_SN_SCREENNAME_1 = "user.screenName IS NULL";
	private static final String _FINDER_COLUMN_C_SN_SCREENNAME_2 = "user.screenName = ?";
	private static final String _FINDER_COLUMN_C_SN_SCREENNAME_3 = "(user.screenName IS NULL OR user.screenName = ?)";
	private static final String _FINDER_COLUMN_C_EA_COMPANYID_2 = "user.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_EA_EMAILADDRESS_1 = "user.emailAddress IS NULL";
	private static final String _FINDER_COLUMN_C_EA_EMAILADDRESS_2 = "user.emailAddress = ?";
	private static final String _FINDER_COLUMN_C_EA_EMAILADDRESS_3 = "(user.emailAddress IS NULL OR user.emailAddress = ?)";
	private static final String _FINDER_COLUMN_C_FID_COMPANYID_2 = "user.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_FID_FACEBOOKID_2 = "user.facebookId = ?";
	private static final String _FINDER_COLUMN_C_O_COMPANYID_2 = "user.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_O_OPENID_1 = "user.openId IS NULL";
	private static final String _FINDER_COLUMN_C_O_OPENID_2 = "user.openId = ?";
	private static final String _FINDER_COLUMN_C_O_OPENID_3 = "(user.openId IS NULL OR user.openId = ?)";
	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 = "user.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_S_STATUS_2 = "user.status = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "user.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No User exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No User exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(UserPersistenceImpl.class);
	private static User _nullUser = new UserImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<User> toCacheModel() {
				return _nullUserCacheModel;
			}
		};

	private static CacheModel<User> _nullUserCacheModel = new CacheModel<User>() {
			public User toEntityModel() {
				return _nullUser;
			}
		};
}