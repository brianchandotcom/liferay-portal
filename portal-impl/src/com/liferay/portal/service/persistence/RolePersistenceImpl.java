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
import com.liferay.portal.NoSuchRoleException;
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
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.model.impl.RoleModelImpl;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the role service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RolePersistence
 * @see RoleUtil
 * @generated
 */
public class RolePersistenceImpl extends BasePersistenceImpl<Role>
	implements RolePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link RoleUtil} to access the role persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = RoleImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, RoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, RoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			RoleModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_NAME = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, RoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByName",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NAME = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, RoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByName",
			new String[] { String.class.getName() },
			RoleModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_NAME = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByName",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SUBTYPE = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, RoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySubtype",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SUBTYPE =
		new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, RoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySubtype",
			new String[] { String.class.getName() },
			RoleModelImpl.SUBTYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SUBTYPE = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySubtype",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_N = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, RoleImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] { Long.class.getName(), String.class.getName() },
			RoleModelImpl.COMPANYID_COLUMN_BITMASK |
			RoleModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_T_S = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, RoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByT_S",
			new String[] {
				Integer.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_S = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, RoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByT_S",
			new String[] { Integer.class.getName(), String.class.getName() },
			RoleModelImpl.TYPE_COLUMN_BITMASK |
			RoleModelImpl.SUBTYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_T_S = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_S",
			new String[] { Integer.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C_C = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, RoleImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			RoleModelImpl.COMPANYID_COLUMN_BITMASK |
			RoleModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			RoleModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_C = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, RoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, RoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the role in the entity cache if it is enabled.
	 *
	 * @param role the role
	 */
	public void cacheResult(Role role) {
		EntityCacheUtil.putResult(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleImpl.class, role.getPrimaryKey(), role);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] { Long.valueOf(role.getCompanyId()), role.getName() },
			role);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
			new Object[] {
				Long.valueOf(role.getCompanyId()),
				Long.valueOf(role.getClassNameId()),
				Long.valueOf(role.getClassPK())
			}, role);

		role.resetOriginalValues();
	}

	/**
	 * Caches the roles in the entity cache if it is enabled.
	 *
	 * @param roles the roles
	 */
	public void cacheResult(List<Role> roles) {
		for (Role role : roles) {
			if (EntityCacheUtil.getResult(RoleModelImpl.ENTITY_CACHE_ENABLED,
						RoleImpl.class, role.getPrimaryKey()) == null) {
				cacheResult(role);
			}
			else {
				role.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all roles.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(RoleImpl.class.getName());
		}

		EntityCacheUtil.clearCache(RoleImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the role.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Role role) {
		EntityCacheUtil.removeResult(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleImpl.class, role.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(role);
	}

	@Override
	public void clearCache(List<Role> roles) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Role role : roles) {
			EntityCacheUtil.removeResult(RoleModelImpl.ENTITY_CACHE_ENABLED,
				RoleImpl.class, role.getPrimaryKey());

			clearUniqueFindersCache(role);
		}
	}

	protected void clearUniqueFindersCache(Role role) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] { Long.valueOf(role.getCompanyId()), role.getName() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_C,
			new Object[] {
				Long.valueOf(role.getCompanyId()),
				Long.valueOf(role.getClassNameId()),
				Long.valueOf(role.getClassPK())
			});
	}

	/**
	 * Creates a new role with the primary key. Does not add the role to the database.
	 *
	 * @param roleId the primary key for the new role
	 * @return the new role
	 */
	public Role create(long roleId) {
		Role role = new RoleImpl();

		role.setNew(true);
		role.setPrimaryKey(roleId);

		return role;
	}

	/**
	 * Removes the role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param roleId the primary key of the role
	 * @return the role that was removed
	 * @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role remove(long roleId) throws NoSuchRoleException, SystemException {
		return remove(Long.valueOf(roleId));
	}

	/**
	 * Removes the role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the role
	 * @return the role that was removed
	 * @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Role remove(Serializable primaryKey)
		throws NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Role role = (Role)session.get(RoleImpl.class, primaryKey);

			if (role == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRoleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(role);
		}
		catch (NoSuchRoleException nsee) {
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
	protected Role removeImpl(Role role) throws SystemException {
		role = toUnwrappedModel(role);

		try {
			clearGroups.clear(role.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}

		try {
			clearUsers.clear(role.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			if (role.isCachedModel()) {
				role = (Role)session.get(RoleImpl.class, role.getPrimaryKeyObj());
			}

			if (role != null) {
				session.delete(role);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (role != null) {
			clearCache(role);
		}

		return role;
	}

	@Override
	public Role updateImpl(com.liferay.portal.model.Role role)
		throws SystemException {
		role = toUnwrappedModel(role);

		boolean isNew = role.isNew();

		RoleModelImpl roleModelImpl = (RoleModelImpl)role;

		Session session = null;

		try {
			session = openSession();

			if (role.isNew()) {
				session.save(role);

				role.setNew(false);
			}
			else {
				session.merge(role);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !RoleModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((roleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(roleModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { Long.valueOf(roleModelImpl.getCompanyId()) };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((roleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NAME.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { roleModelImpl.getOriginalName() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_NAME, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NAME,
					args);

				args = new Object[] { roleModelImpl.getName() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_NAME, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NAME,
					args);
			}

			if ((roleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SUBTYPE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { roleModelImpl.getOriginalSubtype() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_SUBTYPE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SUBTYPE,
					args);

				args = new Object[] { roleModelImpl.getSubtype() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_SUBTYPE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SUBTYPE,
					args);
			}

			if ((roleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Integer.valueOf(roleModelImpl.getOriginalType()),
						
						roleModelImpl.getOriginalSubtype()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_S,
					args);

				args = new Object[] {
						Integer.valueOf(roleModelImpl.getType()),
						
						roleModelImpl.getSubtype()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_S,
					args);
			}
		}

		EntityCacheUtil.putResult(RoleModelImpl.ENTITY_CACHE_ENABLED,
			RoleImpl.class, role.getPrimaryKey(), role);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
				new Object[] { Long.valueOf(role.getCompanyId()), role.getName() },
				role);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
				new Object[] {
					Long.valueOf(role.getCompanyId()),
					Long.valueOf(role.getClassNameId()),
					Long.valueOf(role.getClassPK())
				}, role);
		}
		else {
			if ((roleModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(roleModelImpl.getOriginalCompanyId()),
						
						roleModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_N, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
					new Object[] {
						Long.valueOf(role.getCompanyId()),
						
					role.getName()
					}, role);
			}

			if ((roleModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(roleModelImpl.getOriginalCompanyId()),
						Long.valueOf(roleModelImpl.getOriginalClassNameId()),
						Long.valueOf(roleModelImpl.getOriginalClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_C, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_C, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
					new Object[] {
						Long.valueOf(role.getCompanyId()),
						Long.valueOf(role.getClassNameId()),
						Long.valueOf(role.getClassPK())
					}, role);
			}
		}

		return role;
	}

	protected Role toUnwrappedModel(Role role) {
		if (role instanceof RoleImpl) {
			return role;
		}

		RoleImpl roleImpl = new RoleImpl();

		roleImpl.setNew(role.isNew());
		roleImpl.setPrimaryKey(role.getPrimaryKey());

		roleImpl.setRoleId(role.getRoleId());
		roleImpl.setCompanyId(role.getCompanyId());
		roleImpl.setClassNameId(role.getClassNameId());
		roleImpl.setClassPK(role.getClassPK());
		roleImpl.setName(role.getName());
		roleImpl.setTitle(role.getTitle());
		roleImpl.setDescription(role.getDescription());
		roleImpl.setType(role.getType());
		roleImpl.setSubtype(role.getSubtype());

		return roleImpl;
	}

	/**
	 * Returns the role with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the role
	 * @return the role
	 * @throws com.liferay.portal.NoSuchModelException if a role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Role findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the role with the primary key or throws a {@link com.liferay.portal.NoSuchRoleException} if it could not be found.
	 *
	 * @param roleId the primary key of the role
	 * @return the role
	 * @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role findByPrimaryKey(long roleId)
		throws NoSuchRoleException, SystemException {
		Role role = fetchByPrimaryKey(roleId);

		if (role == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + roleId);
			}

			throw new NoSuchRoleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				roleId);
		}

		return role;
	}

	/**
	 * Returns the role with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the role
	 * @return the role, or <code>null</code> if a role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Role fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the role with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param roleId the primary key of the role
	 * @return the role, or <code>null</code> if a role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role fetchByPrimaryKey(long roleId) throws SystemException {
		Role role = (Role)EntityCacheUtil.getResult(RoleModelImpl.ENTITY_CACHE_ENABLED,
				RoleImpl.class, roleId);

		if (role == _nullRole) {
			return null;
		}

		if (role == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				role = (Role)session.get(RoleImpl.class, Long.valueOf(roleId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (role != null) {
					cacheResult(role);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(RoleModelImpl.ENTITY_CACHE_ENABLED,
						RoleImpl.class, roleId, _nullRole);
				}

				closeSession(session);
			}
		}

		return role;
	}

	/**
	 * Returns all the roles where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> findByCompanyId(long companyId) throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the roles where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @return the range of matching roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the roles where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> findByCompanyId(long companyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<Role> list = (List<Role>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Role role : list) {
				if ((companyId != role.getCompanyId())) {
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

			query.append(_SQL_SELECT_ROLE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(RoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<Role>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first role in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching role
	 * @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		Role role = fetchByCompanyId_First(companyId, orderByComparator);

		if (role != null) {
			return role;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRoleException(msg.toString());
	}

	/**
	 * Returns the first role in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching role, or <code>null</code> if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<Role> list = findByCompanyId(companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last role in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching role
	 * @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		Role role = fetchByCompanyId_Last(companyId, orderByComparator);

		if (role != null) {
			return role;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRoleException(msg.toString());
	}

	/**
	 * Returns the last role in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching role, or <code>null</code> if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<Role> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the roles before and after the current role in the ordered set where companyId = &#63;.
	 *
	 * @param roleId the primary key of the current role
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next role
	 * @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role[] findByCompanyId_PrevAndNext(long roleId, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		Role role = findByPrimaryKey(roleId);

		Session session = null;

		try {
			session = openSession();

			Role[] array = new RoleImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, role, companyId,
					orderByComparator, true);

			array[1] = role;

			array[2] = getByCompanyId_PrevAndNext(session, role, companyId,
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

	protected Role getByCompanyId_PrevAndNext(Session session, Role role,
		long companyId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ROLE_WHERE);

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

		else {
			query.append(RoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(role);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Role> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the roles that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching roles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> filterFindByCompanyId(long companyId)
		throws SystemException {
		return filterFindByCompanyId(companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the roles that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @return the range of matching roles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> filterFindByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the roles that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching roles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> filterFindByCompanyId(long companyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByCompanyId(companyId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ROLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ROLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ROLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(RoleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(RoleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Role.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, RoleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, RoleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			return (List<Role>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the roles before and after the current role in the ordered set of roles that the user has permission to view where companyId = &#63;.
	 *
	 * @param roleId the primary key of the current role
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next role
	 * @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role[] filterFindByCompanyId_PrevAndNext(long roleId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByCompanyId_PrevAndNext(roleId, companyId,
				orderByComparator);
		}

		Role role = findByPrimaryKey(roleId);

		Session session = null;

		try {
			session = openSession();

			Role[] array = new RoleImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(session, role,
					companyId, orderByComparator, true);

			array[1] = role;

			array[2] = filterGetByCompanyId_PrevAndNext(session, role,
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

	protected Role filterGetByCompanyId_PrevAndNext(Session session, Role role,
		long companyId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ROLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ROLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ROLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(RoleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(RoleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Role.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, RoleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, RoleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(role);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Role> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the roles where name = &#63;.
	 *
	 * @param name the name
	 * @return the matching roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> findByName(String name) throws SystemException {
		return findByName(name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the roles where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @return the range of matching roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> findByName(String name, int start, int end)
		throws SystemException {
		return findByName(name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the roles where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> findByName(String name, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NAME;
			finderArgs = new Object[] { name };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_NAME;
			finderArgs = new Object[] { name, start, end, orderByComparator };
		}

		List<Role> list = (List<Role>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Role role : list) {
				if (!Validator.equals(name, role.getName())) {
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

			query.append(_SQL_SELECT_ROLE_WHERE);

			if (name == null) {
				query.append(_FINDER_COLUMN_NAME_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_NAME_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_NAME_NAME_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(RoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
				}

				list = (List<Role>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first role in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching role
	 * @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role findByName_First(String name,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		Role role = fetchByName_First(name, orderByComparator);

		if (role != null) {
			return role;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRoleException(msg.toString());
	}

	/**
	 * Returns the first role in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching role, or <code>null</code> if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role fetchByName_First(String name,
		OrderByComparator orderByComparator) throws SystemException {
		List<Role> list = findByName(name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last role in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching role
	 * @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role findByName_Last(String name, OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		Role role = fetchByName_Last(name, orderByComparator);

		if (role != null) {
			return role;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRoleException(msg.toString());
	}

	/**
	 * Returns the last role in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching role, or <code>null</code> if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role fetchByName_Last(String name,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByName(name);

		List<Role> list = findByName(name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the roles before and after the current role in the ordered set where name = &#63;.
	 *
	 * @param roleId the primary key of the current role
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next role
	 * @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role[] findByName_PrevAndNext(long roleId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		Role role = findByPrimaryKey(roleId);

		Session session = null;

		try {
			session = openSession();

			Role[] array = new RoleImpl[3];

			array[0] = getByName_PrevAndNext(session, role, name,
					orderByComparator, true);

			array[1] = role;

			array[2] = getByName_PrevAndNext(session, role, name,
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

	protected Role getByName_PrevAndNext(Session session, Role role,
		String name, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ROLE_WHERE);

		if (name == null) {
			query.append(_FINDER_COLUMN_NAME_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_NAME_NAME_2);
			}
		}

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
			query.append(RoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (name != null) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(role);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Role> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the roles that the user has permission to view where name = &#63;.
	 *
	 * @param name the name
	 * @return the matching roles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> filterFindByName(String name) throws SystemException {
		return filterFindByName(name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the roles that the user has permission to view where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @return the range of matching roles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> filterFindByName(String name, int start, int end)
		throws SystemException {
		return filterFindByName(name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the roles that the user has permissions to view where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching roles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> filterFindByName(String name, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByName(name, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ROLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ROLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (name == null) {
			query.append(_FINDER_COLUMN_NAME_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_NAME_NAME_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ROLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(RoleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(RoleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Role.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, RoleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, RoleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			if (name != null) {
				qPos.add(name);
			}

			return (List<Role>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the roles before and after the current role in the ordered set of roles that the user has permission to view where name = &#63;.
	 *
	 * @param roleId the primary key of the current role
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next role
	 * @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role[] filterFindByName_PrevAndNext(long roleId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByName_PrevAndNext(roleId, name, orderByComparator);
		}

		Role role = findByPrimaryKey(roleId);

		Session session = null;

		try {
			session = openSession();

			Role[] array = new RoleImpl[3];

			array[0] = filterGetByName_PrevAndNext(session, role, name,
					orderByComparator, true);

			array[1] = role;

			array[2] = filterGetByName_PrevAndNext(session, role, name,
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

	protected Role filterGetByName_PrevAndNext(Session session, Role role,
		String name, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ROLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ROLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (name == null) {
			query.append(_FINDER_COLUMN_NAME_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_NAME_NAME_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ROLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(RoleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(RoleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Role.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, RoleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, RoleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		if (name != null) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(role);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Role> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the roles where subtype = &#63;.
	 *
	 * @param subtype the subtype
	 * @return the matching roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> findBySubtype(String subtype) throws SystemException {
		return findBySubtype(subtype, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the roles where subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param subtype the subtype
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @return the range of matching roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> findBySubtype(String subtype, int start, int end)
		throws SystemException {
		return findBySubtype(subtype, start, end, null);
	}

	/**
	 * Returns an ordered range of all the roles where subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param subtype the subtype
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> findBySubtype(String subtype, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SUBTYPE;
			finderArgs = new Object[] { subtype };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SUBTYPE;
			finderArgs = new Object[] { subtype, start, end, orderByComparator };
		}

		List<Role> list = (List<Role>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Role role : list) {
				if (!Validator.equals(subtype, role.getSubtype())) {
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

			query.append(_SQL_SELECT_ROLE_WHERE);

			if (subtype == null) {
				query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_1);
			}
			else {
				if (subtype.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(RoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (subtype != null) {
					qPos.add(subtype);
				}

				list = (List<Role>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first role in the ordered set where subtype = &#63;.
	 *
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching role
	 * @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role findBySubtype_First(String subtype,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		Role role = fetchBySubtype_First(subtype, orderByComparator);

		if (role != null) {
			return role;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("subtype=");
		msg.append(subtype);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRoleException(msg.toString());
	}

	/**
	 * Returns the first role in the ordered set where subtype = &#63;.
	 *
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching role, or <code>null</code> if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role fetchBySubtype_First(String subtype,
		OrderByComparator orderByComparator) throws SystemException {
		List<Role> list = findBySubtype(subtype, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last role in the ordered set where subtype = &#63;.
	 *
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching role
	 * @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role findBySubtype_Last(String subtype,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		Role role = fetchBySubtype_Last(subtype, orderByComparator);

		if (role != null) {
			return role;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("subtype=");
		msg.append(subtype);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRoleException(msg.toString());
	}

	/**
	 * Returns the last role in the ordered set where subtype = &#63;.
	 *
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching role, or <code>null</code> if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role fetchBySubtype_Last(String subtype,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countBySubtype(subtype);

		List<Role> list = findBySubtype(subtype, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the roles before and after the current role in the ordered set where subtype = &#63;.
	 *
	 * @param roleId the primary key of the current role
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next role
	 * @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role[] findBySubtype_PrevAndNext(long roleId, String subtype,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		Role role = findByPrimaryKey(roleId);

		Session session = null;

		try {
			session = openSession();

			Role[] array = new RoleImpl[3];

			array[0] = getBySubtype_PrevAndNext(session, role, subtype,
					orderByComparator, true);

			array[1] = role;

			array[2] = getBySubtype_PrevAndNext(session, role, subtype,
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

	protected Role getBySubtype_PrevAndNext(Session session, Role role,
		String subtype, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ROLE_WHERE);

		if (subtype == null) {
			query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_1);
		}
		else {
			if (subtype.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_3);
			}
			else {
				query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_2);
			}
		}

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
			query.append(RoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (subtype != null) {
			qPos.add(subtype);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(role);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Role> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the roles that the user has permission to view where subtype = &#63;.
	 *
	 * @param subtype the subtype
	 * @return the matching roles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> filterFindBySubtype(String subtype)
		throws SystemException {
		return filterFindBySubtype(subtype, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the roles that the user has permission to view where subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param subtype the subtype
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @return the range of matching roles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> filterFindBySubtype(String subtype, int start, int end)
		throws SystemException {
		return filterFindBySubtype(subtype, start, end, null);
	}

	/**
	 * Returns an ordered range of all the roles that the user has permissions to view where subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param subtype the subtype
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching roles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> filterFindBySubtype(String subtype, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findBySubtype(subtype, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ROLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ROLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (subtype == null) {
			query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_1);
		}
		else {
			if (subtype.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_3);
			}
			else {
				query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ROLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(RoleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(RoleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Role.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, RoleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, RoleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			if (subtype != null) {
				qPos.add(subtype);
			}

			return (List<Role>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the roles before and after the current role in the ordered set of roles that the user has permission to view where subtype = &#63;.
	 *
	 * @param roleId the primary key of the current role
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next role
	 * @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role[] filterFindBySubtype_PrevAndNext(long roleId, String subtype,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findBySubtype_PrevAndNext(roleId, subtype, orderByComparator);
		}

		Role role = findByPrimaryKey(roleId);

		Session session = null;

		try {
			session = openSession();

			Role[] array = new RoleImpl[3];

			array[0] = filterGetBySubtype_PrevAndNext(session, role, subtype,
					orderByComparator, true);

			array[1] = role;

			array[2] = filterGetBySubtype_PrevAndNext(session, role, subtype,
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

	protected Role filterGetBySubtype_PrevAndNext(Session session, Role role,
		String subtype, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ROLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ROLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (subtype == null) {
			query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_1);
		}
		else {
			if (subtype.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_3);
			}
			else {
				query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ROLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(RoleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(RoleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Role.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, RoleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, RoleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		if (subtype != null) {
			qPos.add(subtype);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(role);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Role> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the role where companyId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchRoleException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching role
	 * @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role findByC_N(long companyId, String name)
		throws NoSuchRoleException, SystemException {
		Role role = fetchByC_N(companyId, name);

		if (role == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchRoleException(msg.toString());
		}

		return role;
	}

	/**
	 * Returns the role where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching role, or <code>null</code> if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role fetchByC_N(long companyId, String name)
		throws SystemException {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the role where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching role, or <code>null</code> if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role fetchByC_N(long companyId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_N,
					finderArgs, this);
		}

		if (result instanceof Role) {
			Role role = (Role)result;

			if ((companyId != role.getCompanyId()) ||
					!Validator.equals(name, role.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ROLE_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_N_NAME_2);
				}
			}

			query.append(RoleModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				List<Role> list = q.list();

				result = list;

				Role role = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
						finderArgs, list);
				}
				else {
					role = list.get(0);

					cacheResult(role);

					if ((role.getCompanyId() != companyId) ||
							(role.getName() == null) ||
							!role.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
							finderArgs, role);
					}
				}

				return role;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
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
				return (Role)result;
			}
		}
	}

	/**
	 * Returns all the roles where type = &#63; and subtype = &#63;.
	 *
	 * @param type the type
	 * @param subtype the subtype
	 * @return the matching roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> findByT_S(int type, String subtype)
		throws SystemException {
		return findByT_S(type, subtype, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the roles where type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @return the range of matching roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> findByT_S(int type, String subtype, int start, int end)
		throws SystemException {
		return findByT_S(type, subtype, start, end, null);
	}

	/**
	 * Returns an ordered range of all the roles where type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> findByT_S(int type, String subtype, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_S;
			finderArgs = new Object[] { type, subtype };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_T_S;
			finderArgs = new Object[] {
					type, subtype,
					
					start, end, orderByComparator
				};
		}

		List<Role> list = (List<Role>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Role role : list) {
				if ((type != role.getType()) ||
						!Validator.equals(subtype, role.getSubtype())) {
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
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ROLE_WHERE);

			query.append(_FINDER_COLUMN_T_S_TYPE_2);

			if (subtype == null) {
				query.append(_FINDER_COLUMN_T_S_SUBTYPE_1);
			}
			else {
				if (subtype.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_T_S_SUBTYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_T_S_SUBTYPE_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(RoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				if (subtype != null) {
					qPos.add(subtype);
				}

				list = (List<Role>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first role in the ordered set where type = &#63; and subtype = &#63;.
	 *
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching role
	 * @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role findByT_S_First(int type, String subtype,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		Role role = fetchByT_S_First(type, subtype, orderByComparator);

		if (role != null) {
			return role;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append(", subtype=");
		msg.append(subtype);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRoleException(msg.toString());
	}

	/**
	 * Returns the first role in the ordered set where type = &#63; and subtype = &#63;.
	 *
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching role, or <code>null</code> if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role fetchByT_S_First(int type, String subtype,
		OrderByComparator orderByComparator) throws SystemException {
		List<Role> list = findByT_S(type, subtype, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last role in the ordered set where type = &#63; and subtype = &#63;.
	 *
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching role
	 * @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role findByT_S_Last(int type, String subtype,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		Role role = fetchByT_S_Last(type, subtype, orderByComparator);

		if (role != null) {
			return role;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append(", subtype=");
		msg.append(subtype);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRoleException(msg.toString());
	}

	/**
	 * Returns the last role in the ordered set where type = &#63; and subtype = &#63;.
	 *
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching role, or <code>null</code> if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role fetchByT_S_Last(int type, String subtype,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByT_S(type, subtype);

		List<Role> list = findByT_S(type, subtype, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the roles before and after the current role in the ordered set where type = &#63; and subtype = &#63;.
	 *
	 * @param roleId the primary key of the current role
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next role
	 * @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role[] findByT_S_PrevAndNext(long roleId, int type, String subtype,
		OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		Role role = findByPrimaryKey(roleId);

		Session session = null;

		try {
			session = openSession();

			Role[] array = new RoleImpl[3];

			array[0] = getByT_S_PrevAndNext(session, role, type, subtype,
					orderByComparator, true);

			array[1] = role;

			array[2] = getByT_S_PrevAndNext(session, role, type, subtype,
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

	protected Role getByT_S_PrevAndNext(Session session, Role role, int type,
		String subtype, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ROLE_WHERE);

		query.append(_FINDER_COLUMN_T_S_TYPE_2);

		if (subtype == null) {
			query.append(_FINDER_COLUMN_T_S_SUBTYPE_1);
		}
		else {
			if (subtype.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_T_S_SUBTYPE_3);
			}
			else {
				query.append(_FINDER_COLUMN_T_S_SUBTYPE_2);
			}
		}

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
			query.append(RoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(type);

		if (subtype != null) {
			qPos.add(subtype);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(role);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Role> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the roles that the user has permission to view where type = &#63; and subtype = &#63;.
	 *
	 * @param type the type
	 * @param subtype the subtype
	 * @return the matching roles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> filterFindByT_S(int type, String subtype)
		throws SystemException {
		return filterFindByT_S(type, subtype, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the roles that the user has permission to view where type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @return the range of matching roles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> filterFindByT_S(int type, String subtype, int start,
		int end) throws SystemException {
		return filterFindByT_S(type, subtype, start, end, null);
	}

	/**
	 * Returns an ordered range of all the roles that the user has permissions to view where type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching roles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> filterFindByT_S(int type, String subtype, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByT_S(type, subtype, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ROLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ROLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_T_S_TYPE_2);

		if (subtype == null) {
			query.append(_FINDER_COLUMN_T_S_SUBTYPE_1);
		}
		else {
			if (subtype.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_T_S_SUBTYPE_3);
			}
			else {
				query.append(_FINDER_COLUMN_T_S_SUBTYPE_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ROLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(RoleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(RoleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Role.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, RoleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, RoleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(type);

			if (subtype != null) {
				qPos.add(subtype);
			}

			return (List<Role>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the roles before and after the current role in the ordered set of roles that the user has permission to view where type = &#63; and subtype = &#63;.
	 *
	 * @param roleId the primary key of the current role
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next role
	 * @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role[] filterFindByT_S_PrevAndNext(long roleId, int type,
		String subtype, OrderByComparator orderByComparator)
		throws NoSuchRoleException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByT_S_PrevAndNext(roleId, type, subtype,
				orderByComparator);
		}

		Role role = findByPrimaryKey(roleId);

		Session session = null;

		try {
			session = openSession();

			Role[] array = new RoleImpl[3];

			array[0] = filterGetByT_S_PrevAndNext(session, role, type, subtype,
					orderByComparator, true);

			array[1] = role;

			array[2] = filterGetByT_S_PrevAndNext(session, role, type, subtype,
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

	protected Role filterGetByT_S_PrevAndNext(Session session, Role role,
		int type, String subtype, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ROLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ROLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_T_S_TYPE_2);

		if (subtype == null) {
			query.append(_FINDER_COLUMN_T_S_SUBTYPE_1);
		}
		else {
			if (subtype.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_T_S_SUBTYPE_3);
			}
			else {
				query.append(_FINDER_COLUMN_T_S_SUBTYPE_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ROLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(RoleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(RoleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Role.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, RoleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, RoleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(type);

		if (subtype != null) {
			qPos.add(subtype);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(role);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Role> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the role where companyId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portal.NoSuchRoleException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching role
	 * @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role findByC_C_C(long companyId, long classNameId, long classPK)
		throws NoSuchRoleException, SystemException {
		Role role = fetchByC_C_C(companyId, classNameId, classPK);

		if (role == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchRoleException(msg.toString());
		}

		return role;
	}

	/**
	 * Returns the role where companyId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching role, or <code>null</code> if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role fetchByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		return fetchByC_C_C(companyId, classNameId, classPK, true);
	}

	/**
	 * Returns the role where companyId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching role, or <code>null</code> if a matching role could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Role fetchByC_C_C(long companyId, long classNameId, long classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C_C,
					finderArgs, this);
		}

		if (result instanceof Role) {
			Role role = (Role)result;

			if ((companyId != role.getCompanyId()) ||
					(classNameId != role.getClassNameId()) ||
					(classPK != role.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_ROLE_WHERE);

			query.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			query.append(RoleModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<Role> list = q.list();

				result = list;

				Role role = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
						finderArgs, list);
				}
				else {
					role = list.get(0);

					cacheResult(role);

					if ((role.getCompanyId() != companyId) ||
							(role.getClassNameId() != classNameId) ||
							(role.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
							finderArgs, role);
					}
				}

				return role;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_C,
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
				return (Role)result;
			}
		}
	}

	/**
	 * Returns all the roles.
	 *
	 * @return the roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @return the range of roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of roles
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> findAll(int start, int end,
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

		List<Role> list = (List<Role>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ROLE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ROLE.concat(RoleModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Role>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Role>)QueryUtil.list(q, getDialect(), start,
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
	 * Removes all the roles where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (Role role : findByCompanyId(companyId)) {
			remove(role);
		}
	}

	/**
	 * Removes all the roles where name = &#63; from the database.
	 *
	 * @param name the name
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByName(String name) throws SystemException {
		for (Role role : findByName(name)) {
			remove(role);
		}
	}

	/**
	 * Removes all the roles where subtype = &#63; from the database.
	 *
	 * @param subtype the subtype
	 * @throws SystemException if a system exception occurred
	 */
	public void removeBySubtype(String subtype) throws SystemException {
		for (Role role : findBySubtype(subtype)) {
			remove(role);
		}
	}

	/**
	 * Removes the role where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the role that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Role removeByC_N(long companyId, String name)
		throws NoSuchRoleException, SystemException {
		Role role = findByC_N(companyId, name);

		return remove(role);
	}

	/**
	 * Removes all the roles where type = &#63; and subtype = &#63; from the database.
	 *
	 * @param type the type
	 * @param subtype the subtype
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByT_S(int type, String subtype) throws SystemException {
		for (Role role : findByT_S(type, subtype)) {
			remove(role);
		}
	}

	/**
	 * Removes the role where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the role that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Role removeByC_C_C(long companyId, long classNameId, long classPK)
		throws NoSuchRoleException, SystemException {
		Role role = findByC_C_C(companyId, classNameId, classPK);

		return remove(role);
	}

	/**
	 * Removes all the roles from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Role role : findAll()) {
			remove(role);
		}
	}

	/**
	 * Returns the number of roles where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching roles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ROLE_WHERE);

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
	 * Returns the number of roles that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching roles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByCompanyId(long companyId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByCompanyId(companyId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_ROLE_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Role.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of roles where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching roles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByName(String name) throws SystemException {
		Object[] finderArgs = new Object[] { name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_NAME,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ROLE_WHERE);

			if (name == null) {
				query.append(_FINDER_COLUMN_NAME_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_NAME_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_NAME_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (name != null) {
					qPos.add(name);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_NAME,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of roles that the user has permission to view where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching roles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByName(String name) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByName(name);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_ROLE_WHERE);

		if (name == null) {
			query.append(_FINDER_COLUMN_NAME_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_NAME_NAME_2);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Role.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (name != null) {
				qPos.add(name);
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of roles where subtype = &#63;.
	 *
	 * @param subtype the subtype
	 * @return the number of matching roles
	 * @throws SystemException if a system exception occurred
	 */
	public int countBySubtype(String subtype) throws SystemException {
		Object[] finderArgs = new Object[] { subtype };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_SUBTYPE,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ROLE_WHERE);

			if (subtype == null) {
				query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_1);
			}
			else {
				if (subtype.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (subtype != null) {
					qPos.add(subtype);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_SUBTYPE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of roles that the user has permission to view where subtype = &#63;.
	 *
	 * @param subtype the subtype
	 * @return the number of matching roles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountBySubtype(String subtype) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countBySubtype(subtype);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_ROLE_WHERE);

		if (subtype == null) {
			query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_1);
		}
		else {
			if (subtype.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_3);
			}
			else {
				query.append(_FINDER_COLUMN_SUBTYPE_SUBTYPE_2);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Role.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (subtype != null) {
				qPos.add(subtype);
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of roles where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching roles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_N(long companyId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ROLE_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of roles where type = &#63; and subtype = &#63;.
	 *
	 * @param type the type
	 * @param subtype the subtype
	 * @return the number of matching roles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByT_S(int type, String subtype) throws SystemException {
		Object[] finderArgs = new Object[] { type, subtype };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ROLE_WHERE);

			query.append(_FINDER_COLUMN_T_S_TYPE_2);

			if (subtype == null) {
				query.append(_FINDER_COLUMN_T_S_SUBTYPE_1);
			}
			else {
				if (subtype.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_T_S_SUBTYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_T_S_SUBTYPE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				if (subtype != null) {
					qPos.add(subtype);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of roles that the user has permission to view where type = &#63; and subtype = &#63;.
	 *
	 * @param type the type
	 * @param subtype the subtype
	 * @return the number of matching roles that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByT_S(int type, String subtype)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByT_S(type, subtype);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_ROLE_WHERE);

		query.append(_FINDER_COLUMN_T_S_TYPE_2);

		if (subtype == null) {
			query.append(_FINDER_COLUMN_T_S_SUBTYPE_1);
		}
		else {
			if (subtype.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_T_S_SUBTYPE_3);
			}
			else {
				query.append(_FINDER_COLUMN_T_S_SUBTYPE_2);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Role.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(type);

			if (subtype != null) {
				qPos.add(subtype);
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of roles where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching roles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_ROLE_WHERE);

			query.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of roles.
	 *
	 * @return the number of roles
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ROLE);

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
	 * Returns all the groups associated with the role.
	 *
	 * @param pk the primary key of the role
	 * @return the groups associated with the role
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Group> getGroups(long pk)
		throws SystemException {
		return getGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the groups associated with the role.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the role
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @return the range of groups associated with the role
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Group> getGroups(long pk, int start,
		int end) throws SystemException {
		return getGroups(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_GROUPS = new FinderPath(com.liferay.portal.model.impl.GroupModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED_GROUPS_ROLES,
			com.liferay.portal.model.impl.GroupImpl.class,
			RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME, "getGroups",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_GROUPS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the groups associated with the role.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the role
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of groups associated with the role
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
			RoleModelImpl.FINDER_CACHE_ENABLED_GROUPS_ROLES, Long.class,
			RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME, "getGroupsSize",
			new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_GROUPS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of groups associated with the role.
	 *
	 * @param pk the primary key of the role
	 * @return the number of groups associated with the role
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
			RoleModelImpl.FINDER_CACHE_ENABLED_GROUPS_ROLES, Boolean.class,
			RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME, "containsGroup",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the group is associated with the role.
	 *
	 * @param pk the primary key of the role
	 * @param groupPK the primary key of the group
	 * @return <code>true</code> if the group is associated with the role; <code>false</code> otherwise
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
	 * Returns <code>true</code> if the role has any groups associated with it.
	 *
	 * @param pk the primary key of the role to check for associations with groups
	 * @return <code>true</code> if the role has any groups associated with it; <code>false</code> otherwise
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
	 * Adds an association between the role and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role
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
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Adds an association between the role and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role
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
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Adds an association between the role and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role
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
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Adds an association between the role and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role
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
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Clears all associations between the role and its groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role to clear the associated groups from
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
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Removes the association between the role and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role
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
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Removes the association between the role and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role
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
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Removes the association between the role and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role
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
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Removes the association between the role and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role
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
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Sets the groups associated with the role, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role
	 * @param groupPKs the primary keys of the groups to be associated with the role
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
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Sets the groups associated with the role, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role
	 * @param groups the groups to be associated with the role
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
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Returns all the users associated with the role.
	 *
	 * @param pk the primary key of the role
	 * @return the users associated with the role
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.User> getUsers(long pk)
		throws SystemException {
		return getUsers(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the users associated with the role.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the role
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @return the range of users associated with the role
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.User> getUsers(long pk, int start,
		int end) throws SystemException {
		return getUsers(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_USERS = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED_USERS_ROLES,
			com.liferay.portal.model.impl.UserImpl.class,
			RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME, "getUsers",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_USERS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the users associated with the role.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the role
	 * @param start the lower bound of the range of roles
	 * @param end the upper bound of the range of roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of users associated with the role
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.User> getUsers(long pk, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portal.model.User> list = (List<com.liferay.portal.model.User>)FinderCacheUtil.getResult(FINDER_PATH_GET_USERS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETUSERS.concat(ORDER_BY_CLAUSE)
									   .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETUSERS;
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("User_",
					com.liferay.portal.model.impl.UserImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.User>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_USERS,
						finderArgs);
				}
				else {
					userPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_USERS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_USERS_SIZE = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED_USERS_ROLES, Long.class,
			RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME, "getUsersSize",
			new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_USERS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of users associated with the role.
	 *
	 * @param pk the primary key of the role
	 * @return the number of users associated with the role
	 * @throws SystemException if a system exception occurred
	 */
	public int getUsersSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_USERS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETUSERSSIZE);

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

				FinderCacheUtil.putResult(FINDER_PATH_GET_USERS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_USER = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			RoleModelImpl.FINDER_CACHE_ENABLED_USERS_ROLES, Boolean.class,
			RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME, "containsUser",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the user is associated with the role.
	 *
	 * @param pk the primary key of the role
	 * @param userPK the primary key of the user
	 * @return <code>true</code> if the user is associated with the role; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsUser(long pk, long userPK) throws SystemException {
		Object[] finderArgs = new Object[] { pk, userPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_USER,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsUser.contains(pk, userPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_USER,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the role has any users associated with it.
	 *
	 * @param pk the primary key of the role to check for associations with users
	 * @return <code>true</code> if the role has any users associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsUsers(long pk) throws SystemException {
		if (getUsersSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the role and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role
	 * @param userPK the primary key of the user
	 * @throws SystemException if a system exception occurred
	 */
	public void addUser(long pk, long userPK) throws SystemException {
		try {
			addUser.add(pk, userPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Adds an association between the role and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role
	 * @param user the user
	 * @throws SystemException if a system exception occurred
	 */
	public void addUser(long pk, com.liferay.portal.model.User user)
		throws SystemException {
		try {
			addUser.add(pk, user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Adds an association between the role and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role
	 * @param userPKs the primary keys of the users
	 * @throws SystemException if a system exception occurred
	 */
	public void addUsers(long pk, long[] userPKs) throws SystemException {
		try {
			for (long userPK : userPKs) {
				addUser.add(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Adds an association between the role and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role
	 * @param users the users
	 * @throws SystemException if a system exception occurred
	 */
	public void addUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			for (com.liferay.portal.model.User user : users) {
				addUser.add(pk, user.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Clears all associations between the role and its users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role to clear the associated users from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearUsers(long pk) throws SystemException {
		try {
			clearUsers.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Removes the association between the role and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role
	 * @param userPK the primary key of the user
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUser(long pk, long userPK) throws SystemException {
		try {
			removeUser.remove(pk, userPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Removes the association between the role and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role
	 * @param user the user
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUser(long pk, com.liferay.portal.model.User user)
		throws SystemException {
		try {
			removeUser.remove(pk, user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Removes the association between the role and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role
	 * @param userPKs the primary keys of the users
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUsers(long pk, long[] userPKs) throws SystemException {
		try {
			for (long userPK : userPKs) {
				removeUser.remove(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Removes the association between the role and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role
	 * @param users the users
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			for (com.liferay.portal.model.User user : users) {
				removeUser.remove(pk, user.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Sets the users associated with the role, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role
	 * @param userPKs the primary keys of the users to be associated with the role
	 * @throws SystemException if a system exception occurred
	 */
	public void setUsers(long pk, long[] userPKs) throws SystemException {
		try {
			Set<Long> userPKSet = SetUtil.fromArray(userPKs);

			List<com.liferay.portal.model.User> users = getUsers(pk);

			for (com.liferay.portal.model.User user : users) {
				if (!userPKSet.remove(user.getPrimaryKey())) {
					removeUser.remove(pk, user.getPrimaryKey());
				}
			}

			for (Long userPK : userPKSet) {
				addUser.add(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Sets the users associated with the role, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the role
	 * @param users the users to be associated with the role
	 * @throws SystemException if a system exception occurred
	 */
	public void setUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			long[] userPKs = new long[users.size()];

			for (int i = 0; i < users.size(); i++) {
				com.liferay.portal.model.User user = users.get(i);

				userPKs[i] = user.getPrimaryKey();
			}

			setUsers(pk, userPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(RoleModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Initializes the role persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Role")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Role>> listenersList = new ArrayList<ModelListener<Role>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Role>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsGroup = new ContainsGroup();

		addGroup = new AddGroup();
		clearGroups = new ClearGroups();
		removeGroup = new RemoveGroup();

		containsUser = new ContainsUser();

		addUser = new AddUser();
		clearUsers = new ClearUsers();
		removeUser = new RemoveUser();
	}

	public void destroy() {
		EntityCacheUtil.removeCache(RoleImpl.class.getName());
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
	protected ContainsGroup containsGroup;
	protected AddGroup addGroup;
	protected ClearGroups clearGroups;
	protected RemoveGroup removeGroup;
	protected ContainsUser containsUser;
	protected AddUser addUser;
	protected ClearUsers clearUsers;
	protected RemoveUser removeUser;

	protected class ContainsGroup {
		protected ContainsGroup() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSGROUP,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long roleId, long groupId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(roleId), new Long(groupId)
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
		protected AddGroup() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Groups_Roles (roleId, groupId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long roleId, long groupId) throws SystemException {
			if (!containsGroup.contains(roleId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<Role> listener : listeners) {
					listener.onBeforeAddAssociation(roleId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeAddAssociation(groupId,
						Role.class.getName(), roleId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(roleId), new Long(groupId)
					});

				for (ModelListener<Role> listener : listeners) {
					listener.onAfterAddAssociation(roleId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterAddAssociation(groupId,
						Role.class.getName(), roleId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearGroups {
		protected ClearGroups() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Roles WHERE roleId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long roleId) throws SystemException {
			ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

			List<com.liferay.portal.model.Group> groups = null;

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				groups = getGroups(roleId);

				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<Role> listener : listeners) {
						listener.onBeforeRemoveAssociation(roleId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onBeforeRemoveAssociation(group.getPrimaryKey(),
							Role.class.getName(), roleId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(roleId) });

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<Role> listener : listeners) {
						listener.onAfterRemoveAssociation(roleId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onAfterRemoveAssociation(group.getPrimaryKey(),
							Role.class.getName(), roleId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveGroup {
		protected RemoveGroup() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Roles WHERE roleId = ? AND groupId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long roleId, long groupId)
			throws SystemException {
			if (containsGroup.contains(roleId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<Role> listener : listeners) {
					listener.onBeforeRemoveAssociation(roleId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeRemoveAssociation(groupId,
						Role.class.getName(), roleId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(roleId), new Long(groupId)
					});

				for (ModelListener<Role> listener : listeners) {
					listener.onAfterRemoveAssociation(roleId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterRemoveAssociation(groupId,
						Role.class.getName(), roleId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ContainsUser {
		protected ContainsUser() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSUSER,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long roleId, long userId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(roleId), new Long(userId)
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

	protected class AddUser {
		protected AddUser() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Roles (roleId, userId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long roleId, long userId) throws SystemException {
			if (!containsUser.contains(roleId, userId)) {
				ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

				for (ModelListener<Role> listener : listeners) {
					listener.onBeforeAddAssociation(roleId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onBeforeAddAssociation(userId,
						Role.class.getName(), roleId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(roleId), new Long(userId)
					});

				for (ModelListener<Role> listener : listeners) {
					listener.onAfterAddAssociation(roleId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onAfterAddAssociation(userId,
						Role.class.getName(), roleId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearUsers {
		protected ClearUsers() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Roles WHERE roleId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long roleId) throws SystemException {
			ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

			List<com.liferay.portal.model.User> users = null;

			if ((listeners.length > 0) || (userListeners.length > 0)) {
				users = getUsers(roleId);

				for (com.liferay.portal.model.User user : users) {
					for (ModelListener<Role> listener : listeners) {
						listener.onBeforeRemoveAssociation(roleId,
							com.liferay.portal.model.User.class.getName(),
							user.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
						listener.onBeforeRemoveAssociation(user.getPrimaryKey(),
							Role.class.getName(), roleId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(roleId) });

			if ((listeners.length > 0) || (userListeners.length > 0)) {
				for (com.liferay.portal.model.User user : users) {
					for (ModelListener<Role> listener : listeners) {
						listener.onAfterRemoveAssociation(roleId,
							com.liferay.portal.model.User.class.getName(),
							user.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
						listener.onAfterRemoveAssociation(user.getPrimaryKey(),
							Role.class.getName(), roleId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveUser {
		protected RemoveUser() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Roles WHERE roleId = ? AND userId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long roleId, long userId)
			throws SystemException {
			if (containsUser.contains(roleId, userId)) {
				ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

				for (ModelListener<Role> listener : listeners) {
					listener.onBeforeRemoveAssociation(roleId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onBeforeRemoveAssociation(userId,
						Role.class.getName(), roleId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(roleId), new Long(userId)
					});

				for (ModelListener<Role> listener : listeners) {
					listener.onAfterRemoveAssociation(roleId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onAfterRemoveAssociation(userId,
						Role.class.getName(), roleId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	private static final String _SQL_SELECT_ROLE = "SELECT role FROM Role role";
	private static final String _SQL_SELECT_ROLE_WHERE = "SELECT role FROM Role role WHERE ";
	private static final String _SQL_COUNT_ROLE = "SELECT COUNT(role) FROM Role role";
	private static final String _SQL_COUNT_ROLE_WHERE = "SELECT COUNT(role) FROM Role role WHERE ";
	private static final String _SQL_GETGROUPS = "SELECT {Group_.*} FROM Group_ INNER JOIN Groups_Roles ON (Groups_Roles.groupId = Group_.groupId) WHERE (Groups_Roles.roleId = ?)";
	private static final String _SQL_GETGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Roles WHERE roleId = ?";
	private static final String _SQL_CONTAINSGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Roles WHERE roleId = ? AND groupId = ?";
	private static final String _SQL_GETUSERS = "SELECT {User_.*} FROM User_ INNER JOIN Users_Roles ON (Users_Roles.userId = User_.userId) WHERE (Users_Roles.roleId = ?)";
	private static final String _SQL_GETUSERSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Roles WHERE roleId = ?";
	private static final String _SQL_CONTAINSUSER = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Roles WHERE roleId = ? AND userId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "role.companyId = ?";
	private static final String _FINDER_COLUMN_NAME_NAME_1 = "role.name IS NULL";
	private static final String _FINDER_COLUMN_NAME_NAME_2 = "role.name = ?";
	private static final String _FINDER_COLUMN_NAME_NAME_3 = "(role.name IS NULL OR role.name = ?)";
	private static final String _FINDER_COLUMN_SUBTYPE_SUBTYPE_1 = "role.subtype IS NULL";
	private static final String _FINDER_COLUMN_SUBTYPE_SUBTYPE_2 = "role.subtype = ?";
	private static final String _FINDER_COLUMN_SUBTYPE_SUBTYPE_3 = "(role.subtype IS NULL OR role.subtype = ?)";
	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 = "role.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_NAME_1 = "role.name IS NULL";
	private static final String _FINDER_COLUMN_C_N_NAME_2 = "lower(role.name) = lower(CAST_TEXT(?))";
	private static final String _FINDER_COLUMN_C_N_NAME_3 = "(role.name IS NULL OR lower(role.name) = lower(CAST_TEXT(?)))";
	private static final String _FINDER_COLUMN_T_S_TYPE_2 = "role.type = ? AND ";
	private static final String _FINDER_COLUMN_T_S_SUBTYPE_1 = "role.subtype IS NULL";
	private static final String _FINDER_COLUMN_T_S_SUBTYPE_2 = "role.subtype = ?";
	private static final String _FINDER_COLUMN_T_S_SUBTYPE_3 = "(role.subtype IS NULL OR role.subtype = ?)";
	private static final String _FINDER_COLUMN_C_C_C_COMPANYID_2 = "role.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_CLASSNAMEID_2 = "role.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 = "role.classPK = ?";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "role.roleId";
	private static final String _FILTER_SQL_SELECT_ROLE_WHERE = "SELECT DISTINCT {role.*} FROM Role_ role WHERE ";
	private static final String _FILTER_SQL_SELECT_ROLE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {Role_.*} FROM (SELECT DISTINCT role.roleId FROM Role_ role WHERE ";
	private static final String _FILTER_SQL_SELECT_ROLE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN Role_ ON TEMP_TABLE.roleId = Role_.roleId";
	private static final String _FILTER_SQL_COUNT_ROLE_WHERE = "SELECT COUNT(DISTINCT role.roleId) AS COUNT_VALUE FROM Role_ role WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "role";
	private static final String _FILTER_ENTITY_TABLE = "Role_";
	private static final String _ORDER_BY_ENTITY_ALIAS = "role.";
	private static final String _ORDER_BY_ENTITY_TABLE = "Role_.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Role exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Role exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(RolePersistenceImpl.class);
	private static Role _nullRole = new RoleImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Role> toCacheModel() {
				return _nullRoleCacheModel;
			}
		};

	private static CacheModel<Role> _nullRoleCacheModel = new CacheModel<Role>() {
			public Role toEntityModel() {
				return _nullRole;
			}
		};
}