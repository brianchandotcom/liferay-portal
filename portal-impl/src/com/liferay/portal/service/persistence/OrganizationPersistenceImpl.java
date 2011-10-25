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
import com.liferay.portal.NoSuchOrganizationException;
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
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.impl.OrganizationImpl;
import com.liferay.portal.model.impl.OrganizationModelImpl;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the organization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OrganizationPersistence
 * @see OrganizationUtil
 * @generated
 */
public class OrganizationPersistenceImpl extends BasePersistenceImpl<Organization>
	implements OrganizationPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link OrganizationUtil} to access the organization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = OrganizationImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, OrganizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, OrganizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			OrganizationModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LOCATIONS =
		new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, OrganizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLocations",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LOCATIONS =
		new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, OrganizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByLocations",
			new String[] { Long.class.getName() },
			OrganizationModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LOCATIONS = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLocations",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_P = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, OrganizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, OrganizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_P",
			new String[] { Long.class.getName(), Long.class.getName() },
			OrganizationModelImpl.COMPANYID_COLUMN_BITMASK |
			OrganizationModelImpl.PARENTORGANIZATIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_P = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_N = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, OrganizationImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] { Long.class.getName(), String.class.getName() },
			OrganizationModelImpl.COMPANYID_COLUMN_BITMASK |
			OrganizationModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, OrganizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, OrganizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the organization in the entity cache if it is enabled.
	 *
	 * @param organization the organization
	 */
	public void cacheResult(Organization organization) {
		EntityCacheUtil.putResult(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationImpl.class, organization.getPrimaryKey(), organization);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] {
				Long.valueOf(organization.getCompanyId()),
				
			organization.getName()
			}, organization);

		organization.resetOriginalValues();
	}

	/**
	 * Caches the organizations in the entity cache if it is enabled.
	 *
	 * @param organizations the organizations
	 */
	public void cacheResult(List<Organization> organizations) {
		for (Organization organization : organizations) {
			if (EntityCacheUtil.getResult(
						OrganizationModelImpl.ENTITY_CACHE_ENABLED,
						OrganizationImpl.class, organization.getPrimaryKey()) == null) {
				cacheResult(organization);
			}
			else {
				organization.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all organizations.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(OrganizationImpl.class.getName());
		}

		EntityCacheUtil.clearCache(OrganizationImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the organization.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Organization organization) {
		EntityCacheUtil.removeResult(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationImpl.class, organization.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] {
				Long.valueOf(organization.getCompanyId()),
				
			organization.getName()
			});
	}

	/**
	 * Creates a new organization with the primary key. Does not add the organization to the database.
	 *
	 * @param organizationId the primary key for the new organization
	 * @return the new organization
	 */
	public Organization create(long organizationId) {
		Organization organization = new OrganizationImpl();

		organization.setNew(true);
		organization.setPrimaryKey(organizationId);

		return organization;
	}

	/**
	 * Removes the organization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the organization
	 * @return the organization that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a organization with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Organization remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the organization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param organizationId the primary key of the organization
	 * @return the organization that was removed
	 * @throws com.liferay.portal.NoSuchOrganizationException if a organization with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Organization remove(long organizationId)
		throws NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Organization organization = (Organization)session.get(OrganizationImpl.class,
					Long.valueOf(organizationId));

			if (organization == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						organizationId);
				}

				throw new NoSuchOrganizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					organizationId);
			}

			return organizationPersistence.remove(organization);
		}
		catch (NoSuchOrganizationException nsee) {
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
	 * Removes the organization from the database. Also notifies the appropriate model listeners.
	 *
	 * @param organization the organization
	 * @return the organization that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Organization remove(Organization organization)
		throws SystemException {
		return super.remove(organization);
	}

	@Override
	protected Organization removeImpl(Organization organization)
		throws SystemException {
		organization = toUnwrappedModel(organization);

		try {
			clearGroups.clear(organization.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}

		try {
			clearUsers.clear(organization.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, organization);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		OrganizationModelImpl organizationModelImpl = (OrganizationModelImpl)organization;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] {
				Long.valueOf(organizationModelImpl.getCompanyId()),
				
			organizationModelImpl.getName()
			});

		EntityCacheUtil.removeResult(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationImpl.class, organization.getPrimaryKey());

		return organization;
	}

	@Override
	public Organization updateImpl(
		com.liferay.portal.model.Organization organization, boolean merge)
		throws SystemException {
		organization = toUnwrappedModel(organization);

		boolean isNew = organization.isNew();

		OrganizationModelImpl organizationModelImpl = (OrganizationModelImpl)organization;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, organization, merge);

			organization.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !OrganizationModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((organizationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(organizationModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(organizationModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((organizationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LOCATIONS.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(organizationModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LOCATIONS,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LOCATIONS,
					args);

				args = new Object[] {
						Long.valueOf(organizationModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LOCATIONS,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LOCATIONS,
					args);
			}

			if ((organizationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(organizationModelImpl.getOriginalCompanyId()),
						Long.valueOf(organizationModelImpl.getOriginalParentOrganizationId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P,
					args);

				args = new Object[] {
						Long.valueOf(organizationModelImpl.getCompanyId()),
						Long.valueOf(organizationModelImpl.getParentOrganizationId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P,
					args);
			}
		}

		EntityCacheUtil.putResult(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationImpl.class, organization.getPrimaryKey(), organization);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
				new Object[] {
					Long.valueOf(organization.getCompanyId()),
					
				organization.getName()
				}, organization);
		}
		else {
			if ((organizationModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(organizationModelImpl.getOriginalCompanyId()),
						
						organizationModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
					new Object[] {
						Long.valueOf(organization.getCompanyId()),
						
					organization.getName()
					}, organization);
			}
		}

		return organization;
	}

	protected Organization toUnwrappedModel(Organization organization) {
		if (organization instanceof OrganizationImpl) {
			return organization;
		}

		OrganizationImpl organizationImpl = new OrganizationImpl();

		organizationImpl.setNew(organization.isNew());
		organizationImpl.setPrimaryKey(organization.getPrimaryKey());

		organizationImpl.setOrganizationId(organization.getOrganizationId());
		organizationImpl.setCompanyId(organization.getCompanyId());
		organizationImpl.setParentOrganizationId(organization.getParentOrganizationId());
		organizationImpl.setTreePath(organization.getTreePath());
		organizationImpl.setName(organization.getName());
		organizationImpl.setType(organization.getType());
		organizationImpl.setRecursable(organization.isRecursable());
		organizationImpl.setRegionId(organization.getRegionId());
		organizationImpl.setCountryId(organization.getCountryId());
		organizationImpl.setStatusId(organization.getStatusId());
		organizationImpl.setComments(organization.getComments());

		return organizationImpl;
	}

	/**
	 * Returns the organization with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the organization
	 * @return the organization
	 * @throws com.liferay.portal.NoSuchModelException if a organization with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Organization findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the organization with the primary key or throws a {@link com.liferay.portal.NoSuchOrganizationException} if it could not be found.
	 *
	 * @param organizationId the primary key of the organization
	 * @return the organization
	 * @throws com.liferay.portal.NoSuchOrganizationException if a organization with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Organization findByPrimaryKey(long organizationId)
		throws NoSuchOrganizationException, SystemException {
		Organization organization = fetchByPrimaryKey(organizationId);

		if (organization == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + organizationId);
			}

			throw new NoSuchOrganizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				organizationId);
		}

		return organization;
	}

	/**
	 * Returns the organization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the organization
	 * @return the organization, or <code>null</code> if a organization with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Organization fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the organization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param organizationId the primary key of the organization
	 * @return the organization, or <code>null</code> if a organization with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Organization fetchByPrimaryKey(long organizationId)
		throws SystemException {
		Organization organization = (Organization)EntityCacheUtil.getResult(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
				OrganizationImpl.class, organizationId);

		if (organization == _nullOrganization) {
			return null;
		}

		if (organization == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				organization = (Organization)session.get(OrganizationImpl.class,
						Long.valueOf(organizationId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (organization != null) {
					cacheResult(organization);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(OrganizationModelImpl.ENTITY_CACHE_ENABLED,
						OrganizationImpl.class, organizationId,
						_nullOrganization);
				}

				closeSession(session);
			}
		}

		return organization;
	}

	/**
	 * Returns all the organizations where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching organizations
	 * @throws SystemException if a system exception occurred
	 */
	public List<Organization> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the organizations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations
	 * @throws SystemException if a system exception occurred
	 */
	public List<Organization> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations
	 * @throws SystemException if a system exception occurred
	 */
	public List<Organization> findByCompanyId(long companyId, int start,
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

		List<Organization> list = (List<Organization>)FinderCacheUtil.getResult(finderPath,
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

			query.append(_SQL_SELECT_ORGANIZATION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<Organization>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first organization in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching organization
	 * @throws com.liferay.portal.NoSuchOrganizationException if a matching organization could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Organization findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchOrganizationException, SystemException {
		List<Organization> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrganizationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last organization in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching organization
	 * @throws com.liferay.portal.NoSuchOrganizationException if a matching organization could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Organization findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchOrganizationException, SystemException {
		int count = countByCompanyId(companyId);

		List<Organization> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrganizationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the organizations before and after the current organization in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param organizationId the primary key of the current organization
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws com.liferay.portal.NoSuchOrganizationException if a organization with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Organization[] findByCompanyId_PrevAndNext(long organizationId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchOrganizationException, SystemException {
		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, organization,
					companyId, orderByComparator, true);

			array[1] = organization;

			array[2] = getByCompanyId_PrevAndNext(session, organization,
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

	protected Organization getByCompanyId_PrevAndNext(Session session,
		Organization organization, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ORGANIZATION_WHERE);

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
			query.append(OrganizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(organization);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Organization> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the organizations that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching organizations that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Organization> filterFindByCompanyId(long companyId)
		throws SystemException {
		return filterFindByCompanyId(companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Organization> filterFindByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Organization> filterFindByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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
			query.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Organization.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, OrganizationImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			return (List<Organization>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where companyId = &#63;.
	 *
	 * @param organizationId the primary key of the current organization
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws com.liferay.portal.NoSuchOrganizationException if a organization with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Organization[] filterFindByCompanyId_PrevAndNext(
		long organizationId, long companyId, OrderByComparator orderByComparator)
		throws NoSuchOrganizationException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByCompanyId_PrevAndNext(organizationId, companyId,
				orderByComparator);
		}

		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(session, organization,
					companyId, orderByComparator, true);

			array[1] = organization;

			array[2] = filterGetByCompanyId_PrevAndNext(session, organization,
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

	protected Organization filterGetByCompanyId_PrevAndNext(Session session,
		Organization organization, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Organization.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, OrganizationImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(organization);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Organization> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the organizations where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching organizations
	 * @throws SystemException if a system exception occurred
	 */
	public List<Organization> findByLocations(long companyId)
		throws SystemException {
		return findByLocations(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the organizations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations
	 * @throws SystemException if a system exception occurred
	 */
	public List<Organization> findByLocations(long companyId, int start, int end)
		throws SystemException {
		return findByLocations(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations
	 * @throws SystemException if a system exception occurred
	 */
	public List<Organization> findByLocations(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LOCATIONS;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LOCATIONS;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<Organization> list = (List<Organization>)FinderCacheUtil.getResult(finderPath,
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

			query.append(_SQL_SELECT_ORGANIZATION_WHERE);

			query.append(_FINDER_COLUMN_LOCATIONS_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<Organization>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first organization in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching organization
	 * @throws com.liferay.portal.NoSuchOrganizationException if a matching organization could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Organization findByLocations_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchOrganizationException, SystemException {
		List<Organization> list = findByLocations(companyId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrganizationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last organization in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching organization
	 * @throws com.liferay.portal.NoSuchOrganizationException if a matching organization could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Organization findByLocations_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchOrganizationException, SystemException {
		int count = countByLocations(companyId);

		List<Organization> list = findByLocations(companyId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrganizationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the organizations before and after the current organization in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param organizationId the primary key of the current organization
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws com.liferay.portal.NoSuchOrganizationException if a organization with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Organization[] findByLocations_PrevAndNext(long organizationId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchOrganizationException, SystemException {
		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = getByLocations_PrevAndNext(session, organization,
					companyId, orderByComparator, true);

			array[1] = organization;

			array[2] = getByLocations_PrevAndNext(session, organization,
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

	protected Organization getByLocations_PrevAndNext(Session session,
		Organization organization, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ORGANIZATION_WHERE);

		query.append(_FINDER_COLUMN_LOCATIONS_COMPANYID_2);

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
			query.append(OrganizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(organization);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Organization> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the organizations that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching organizations that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Organization> filterFindByLocations(long companyId)
		throws SystemException {
		return filterFindByLocations(companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Organization> filterFindByLocations(long companyId, int start,
		int end) throws SystemException {
		return filterFindByLocations(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Organization> filterFindByLocations(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByLocations(companyId, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_LOCATIONS_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Organization.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, OrganizationImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			return (List<Organization>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where companyId = &#63;.
	 *
	 * @param organizationId the primary key of the current organization
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws com.liferay.portal.NoSuchOrganizationException if a organization with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Organization[] filterFindByLocations_PrevAndNext(
		long organizationId, long companyId, OrderByComparator orderByComparator)
		throws NoSuchOrganizationException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByLocations_PrevAndNext(organizationId, companyId,
				orderByComparator);
		}

		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = filterGetByLocations_PrevAndNext(session, organization,
					companyId, orderByComparator, true);

			array[1] = organization;

			array[2] = filterGetByLocations_PrevAndNext(session, organization,
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

	protected Organization filterGetByLocations_PrevAndNext(Session session,
		Organization organization, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_LOCATIONS_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Organization.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, OrganizationImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(organization);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Organization> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the organizations where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @return the matching organizations
	 * @throws SystemException if a system exception occurred
	 */
	public List<Organization> findByC_P(long companyId,
		long parentOrganizationId) throws SystemException {
		return findByC_P(companyId, parentOrganizationId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations
	 * @throws SystemException if a system exception occurred
	 */
	public List<Organization> findByC_P(long companyId,
		long parentOrganizationId, int start, int end)
		throws SystemException {
		return findByC_P(companyId, parentOrganizationId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations
	 * @throws SystemException if a system exception occurred
	 */
	public List<Organization> findByC_P(long companyId,
		long parentOrganizationId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P;
			finderArgs = new Object[] { companyId, parentOrganizationId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_P;
			finderArgs = new Object[] {
					companyId, parentOrganizationId,
					
					start, end, orderByComparator
				};
		}

		List<Organization> list = (List<Organization>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ORGANIZATION_WHERE);

			query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_PARENTORGANIZATIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(parentOrganizationId);

				list = (List<Organization>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching organization
	 * @throws com.liferay.portal.NoSuchOrganizationException if a matching organization could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Organization findByC_P_First(long companyId,
		long parentOrganizationId, OrderByComparator orderByComparator)
		throws NoSuchOrganizationException, SystemException {
		List<Organization> list = findByC_P(companyId, parentOrganizationId, 0,
				1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", parentOrganizationId=");
			msg.append(parentOrganizationId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrganizationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching organization
	 * @throws com.liferay.portal.NoSuchOrganizationException if a matching organization could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Organization findByC_P_Last(long companyId,
		long parentOrganizationId, OrderByComparator orderByComparator)
		throws NoSuchOrganizationException, SystemException {
		int count = countByC_P(companyId, parentOrganizationId);

		List<Organization> list = findByC_P(companyId, parentOrganizationId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", parentOrganizationId=");
			msg.append(parentOrganizationId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrganizationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the organizations before and after the current organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param organizationId the primary key of the current organization
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws com.liferay.portal.NoSuchOrganizationException if a organization with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Organization[] findByC_P_PrevAndNext(long organizationId,
		long companyId, long parentOrganizationId,
		OrderByComparator orderByComparator)
		throws NoSuchOrganizationException, SystemException {
		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = getByC_P_PrevAndNext(session, organization, companyId,
					parentOrganizationId, orderByComparator, true);

			array[1] = organization;

			array[2] = getByC_P_PrevAndNext(session, organization, companyId,
					parentOrganizationId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Organization getByC_P_PrevAndNext(Session session,
		Organization organization, long companyId, long parentOrganizationId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ORGANIZATION_WHERE);

		query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_P_PARENTORGANIZATIONID_2);

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
			query.append(OrganizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(parentOrganizationId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(organization);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Organization> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @return the matching organizations that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Organization> filterFindByC_P(long companyId,
		long parentOrganizationId) throws SystemException {
		return filterFindByC_P(companyId, parentOrganizationId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of matching organizations that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Organization> filterFindByC_P(long companyId,
		long parentOrganizationId, int start, int end)
		throws SystemException {
		return filterFindByC_P(companyId, parentOrganizationId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations that the user has permissions to view where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching organizations that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<Organization> filterFindByC_P(long companyId,
		long parentOrganizationId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByC_P(companyId, parentOrganizationId, start, end,
				orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_P_PARENTORGANIZATIONID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Organization.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, OrganizationImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(parentOrganizationId);

			return (List<Organization>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param organizationId the primary key of the current organization
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next organization
	 * @throws com.liferay.portal.NoSuchOrganizationException if a organization with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Organization[] filterFindByC_P_PrevAndNext(long organizationId,
		long companyId, long parentOrganizationId,
		OrderByComparator orderByComparator)
		throws NoSuchOrganizationException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByC_P_PrevAndNext(organizationId, companyId,
				parentOrganizationId, orderByComparator);
		}

		Organization organization = findByPrimaryKey(organizationId);

		Session session = null;

		try {
			session = openSession();

			Organization[] array = new OrganizationImpl[3];

			array[0] = filterGetByC_P_PrevAndNext(session, organization,
					companyId, parentOrganizationId, orderByComparator, true);

			array[1] = organization;

			array[2] = filterGetByC_P_PrevAndNext(session, organization,
					companyId, parentOrganizationId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Organization filterGetByC_P_PrevAndNext(Session session,
		Organization organization, long companyId, long parentOrganizationId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ORGANIZATION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_P_PARENTORGANIZATIONID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(OrganizationModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(OrganizationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Organization.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, OrganizationImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, OrganizationImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(parentOrganizationId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(organization);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Organization> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the organization where companyId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchOrganizationException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching organization
	 * @throws com.liferay.portal.NoSuchOrganizationException if a matching organization could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Organization findByC_N(long companyId, String name)
		throws NoSuchOrganizationException, SystemException {
		Organization organization = fetchByC_N(companyId, name);

		if (organization == null) {
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

			throw new NoSuchOrganizationException(msg.toString());
		}

		return organization;
	}

	/**
	 * Returns the organization where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching organization, or <code>null</code> if a matching organization could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Organization fetchByC_N(long companyId, String name)
		throws SystemException {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the organization where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching organization, or <code>null</code> if a matching organization could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Organization fetchByC_N(long companyId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_N,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ORGANIZATION_WHERE);

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

			query.append(OrganizationModelImpl.ORDER_BY_JPQL);

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

				List<Organization> list = q.list();

				result = list;

				Organization organization = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
						finderArgs, list);
				}
				else {
					organization = list.get(0);

					cacheResult(organization);

					if ((organization.getCompanyId() != companyId) ||
							(organization.getName() == null) ||
							!organization.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
							finderArgs, organization);
					}
				}

				return organization;
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
				return (Organization)result;
			}
		}
	}

	/**
	 * Returns all the organizations.
	 *
	 * @return the organizations
	 * @throws SystemException if a system exception occurred
	 */
	public List<Organization> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the organizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of organizations
	 * @throws SystemException if a system exception occurred
	 */
	public List<Organization> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of organizations
	 * @throws SystemException if a system exception occurred
	 */
	public List<Organization> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Organization> list = (List<Organization>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ORGANIZATION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ORGANIZATION.concat(OrganizationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Organization>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Organization>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the organizations where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (Organization organization : findByCompanyId(companyId)) {
			organizationPersistence.remove(organization);
		}
	}

	/**
	 * Removes all the organizations where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByLocations(long companyId) throws SystemException {
		for (Organization organization : findByLocations(companyId)) {
			organizationPersistence.remove(organization);
		}
	}

	/**
	 * Removes all the organizations where companyId = &#63; and parentOrganizationId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_P(long companyId, long parentOrganizationId)
		throws SystemException {
		for (Organization organization : findByC_P(companyId,
				parentOrganizationId)) {
			organizationPersistence.remove(organization);
		}
	}

	/**
	 * Removes the organization where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_N(long companyId, String name)
		throws NoSuchOrganizationException, SystemException {
		Organization organization = findByC_N(companyId, name);

		organizationPersistence.remove(organization);
	}

	/**
	 * Removes all the organizations from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Organization organization : findAll()) {
			organizationPersistence.remove(organization);
		}
	}

	/**
	 * Returns the number of organizations where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching organizations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ORGANIZATION_WHERE);

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
	 * Returns the number of organizations that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching organizations that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByCompanyId(long companyId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByCompanyId(companyId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_ORGANIZATION_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Organization.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

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
	 * Returns the number of organizations where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching organizations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByLocations(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_LOCATIONS,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ORGANIZATION_WHERE);

			query.append(_FINDER_COLUMN_LOCATIONS_COMPANYID_2);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_LOCATIONS,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of organizations that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching organizations that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByLocations(long companyId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByLocations(companyId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_ORGANIZATION_WHERE);

		query.append(_FINDER_COLUMN_LOCATIONS_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Organization.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

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
	 * Returns the number of organizations where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @return the number of matching organizations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_P(long companyId, long parentOrganizationId)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, parentOrganizationId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ORGANIZATION_WHERE);

			query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_PARENTORGANIZATIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(parentOrganizationId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentOrganizationId the parent organization ID
	 * @return the number of matching organizations that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByC_P(long companyId, long parentOrganizationId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByC_P(companyId, parentOrganizationId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_ORGANIZATION_WHERE);

		query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_P_PARENTORGANIZATIONID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				Organization.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(parentOrganizationId);

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
	 * Returns the number of organizations where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching organizations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_N(long companyId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ORGANIZATION_WHERE);

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
	 * Returns the number of organizations.
	 *
	 * @return the number of organizations
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ORGANIZATION);

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
	 * Returns all the groups associated with the organization.
	 *
	 * @param pk the primary key of the organization
	 * @return the groups associated with the organization
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Group> getGroups(long pk)
		throws SystemException {
		return getGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the groups associated with the organization.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the organization
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of groups associated with the organization
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Group> getGroups(long pk, int start,
		int end) throws SystemException {
		return getGroups(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_GROUPS = new FinderPath(com.liferay.portal.model.impl.GroupModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED_GROUPS_ORGS,
			com.liferay.portal.model.impl.GroupImpl.class,
			OrganizationModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME, "getGroups",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	/**
	 * Returns an ordered range of all the groups associated with the organization.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the organization
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of groups associated with the organization
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
			OrganizationModelImpl.FINDER_CACHE_ENABLED_GROUPS_ORGS, Long.class,
			OrganizationModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME,
			"getGroupsSize", new String[] { Long.class.getName() });

	/**
	 * Returns the number of groups associated with the organization.
	 *
	 * @param pk the primary key of the organization
	 * @return the number of groups associated with the organization
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
			OrganizationModelImpl.FINDER_CACHE_ENABLED_GROUPS_ORGS,
			Boolean.class,
			OrganizationModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME,
			"containsGroup",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the group is associated with the organization.
	 *
	 * @param pk the primary key of the organization
	 * @param groupPK the primary key of the group
	 * @return <code>true</code> if the group is associated with the organization; <code>false</code> otherwise
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
	 * Returns <code>true</code> if the organization has any groups associated with it.
	 *
	 * @param pk the primary key of the organization to check for associations with groups
	 * @return <code>true</code> if the organization has any groups associated with it; <code>false</code> otherwise
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
	 * Adds an association between the organization and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Adds an association between the organization and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Adds an association between the organization and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Adds an association between the organization and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Clears all associations between the organization and its groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization to clear the associated groups from
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Removes the association between the organization and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Removes the association between the organization and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Removes the association between the organization and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Removes the association between the organization and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Sets the groups associated with the organization, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param groupPKs the primary keys of the groups to be associated with the organization
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Sets the groups associated with the organization, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param groups the groups to be associated with the organization
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Returns all the users associated with the organization.
	 *
	 * @param pk the primary key of the organization
	 * @return the users associated with the organization
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.User> getUsers(long pk)
		throws SystemException {
		return getUsers(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the users associated with the organization.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the organization
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @return the range of users associated with the organization
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.User> getUsers(long pk, int start,
		int end) throws SystemException {
		return getUsers(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_USERS = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			OrganizationModelImpl.FINDER_CACHE_ENABLED_USERS_ORGS,
			com.liferay.portal.model.impl.UserImpl.class,
			OrganizationModelImpl.MAPPING_TABLE_USERS_ORGS_NAME, "getUsers",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	/**
	 * Returns an ordered range of all the users associated with the organization.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the organization
	 * @param start the lower bound of the range of organizations
	 * @param end the upper bound of the range of organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of users associated with the organization
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
			OrganizationModelImpl.FINDER_CACHE_ENABLED_USERS_ORGS, Long.class,
			OrganizationModelImpl.MAPPING_TABLE_USERS_ORGS_NAME,
			"getUsersSize", new String[] { Long.class.getName() });

	/**
	 * Returns the number of users associated with the organization.
	 *
	 * @param pk the primary key of the organization
	 * @return the number of users associated with the organization
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
			OrganizationModelImpl.FINDER_CACHE_ENABLED_USERS_ORGS,
			Boolean.class, OrganizationModelImpl.MAPPING_TABLE_USERS_ORGS_NAME,
			"containsUser",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the user is associated with the organization.
	 *
	 * @param pk the primary key of the organization
	 * @param userPK the primary key of the user
	 * @return <code>true</code> if the user is associated with the organization; <code>false</code> otherwise
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
	 * Returns <code>true</code> if the organization has any users associated with it.
	 *
	 * @param pk the primary key of the organization to check for associations with users
	 * @return <code>true</code> if the organization has any users associated with it; <code>false</code> otherwise
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
	 * Adds an association between the organization and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Adds an association between the organization and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Adds an association between the organization and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Adds an association between the organization and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Clears all associations between the organization and its users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization to clear the associated users from
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Removes the association between the organization and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Removes the association between the organization and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Removes the association between the organization and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Removes the association between the organization and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Sets the users associated with the organization, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param userPKs the primary keys of the users to be associated with the organization
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Sets the users associated with the organization, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the organization
	 * @param users the users to be associated with the organization
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
			FinderCacheUtil.clearCache(OrganizationModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Initializes the organization persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Organization")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Organization>> listenersList = new ArrayList<ModelListener<Organization>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Organization>)InstanceFactory.newInstance(
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

		containsUser = new ContainsUser(this);

		addUser = new AddUser(this);
		clearUsers = new ClearUsers(this);
		removeUser = new RemoveUser(this);
	}

	public void destroy() {
		EntityCacheUtil.removeCache(OrganizationImpl.class.getName());
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
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	protected ContainsGroup containsGroup;
	protected AddGroup addGroup;
	protected ClearGroups clearGroups;
	protected RemoveGroup removeGroup;
	protected ContainsUser containsUser;
	protected AddUser addUser;
	protected ClearUsers clearUsers;
	protected RemoveUser removeUser;

	protected class ContainsGroup {
		protected ContainsGroup(OrganizationPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSGROUP,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long organizationId, long groupId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(organizationId), new Long(groupId)
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
		protected AddGroup(OrganizationPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Groups_Orgs (organizationId, groupId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long organizationId, long groupId)
			throws SystemException {
			if (!_persistenceImpl.containsGroup.contains(organizationId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<Organization> listener : listeners) {
					listener.onBeforeAddAssociation(organizationId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeAddAssociation(groupId,
						Organization.class.getName(), organizationId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(organizationId), new Long(groupId)
					});

				for (ModelListener<Organization> listener : listeners) {
					listener.onAfterAddAssociation(organizationId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterAddAssociation(groupId,
						Organization.class.getName(), organizationId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private OrganizationPersistenceImpl _persistenceImpl;
	}

	protected class ClearGroups {
		protected ClearGroups(OrganizationPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Orgs WHERE organizationId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long organizationId) throws SystemException {
			ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

			List<com.liferay.portal.model.Group> groups = null;

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				groups = getGroups(organizationId);

				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<Organization> listener : listeners) {
						listener.onBeforeRemoveAssociation(organizationId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onBeforeRemoveAssociation(group.getPrimaryKey(),
							Organization.class.getName(), organizationId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(organizationId) });

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<Organization> listener : listeners) {
						listener.onAfterRemoveAssociation(organizationId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onAfterRemoveAssociation(group.getPrimaryKey(),
							Organization.class.getName(), organizationId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveGroup {
		protected RemoveGroup(OrganizationPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Orgs WHERE organizationId = ? AND groupId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long organizationId, long groupId)
			throws SystemException {
			if (_persistenceImpl.containsGroup.contains(organizationId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<Organization> listener : listeners) {
					listener.onBeforeRemoveAssociation(organizationId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeRemoveAssociation(groupId,
						Organization.class.getName(), organizationId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(organizationId), new Long(groupId)
					});

				for (ModelListener<Organization> listener : listeners) {
					listener.onAfterRemoveAssociation(organizationId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterRemoveAssociation(groupId,
						Organization.class.getName(), organizationId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private OrganizationPersistenceImpl _persistenceImpl;
	}

	protected class ContainsUser {
		protected ContainsUser(OrganizationPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSUSER,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long organizationId, long userId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(organizationId), new Long(userId)
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
		protected AddUser(OrganizationPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Orgs (organizationId, userId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long organizationId, long userId)
			throws SystemException {
			if (!_persistenceImpl.containsUser.contains(organizationId, userId)) {
				ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

				for (ModelListener<Organization> listener : listeners) {
					listener.onBeforeAddAssociation(organizationId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onBeforeAddAssociation(userId,
						Organization.class.getName(), organizationId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(organizationId), new Long(userId)
					});

				for (ModelListener<Organization> listener : listeners) {
					listener.onAfterAddAssociation(organizationId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onAfterAddAssociation(userId,
						Organization.class.getName(), organizationId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private OrganizationPersistenceImpl _persistenceImpl;
	}

	protected class ClearUsers {
		protected ClearUsers(OrganizationPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Orgs WHERE organizationId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long organizationId) throws SystemException {
			ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

			List<com.liferay.portal.model.User> users = null;

			if ((listeners.length > 0) || (userListeners.length > 0)) {
				users = getUsers(organizationId);

				for (com.liferay.portal.model.User user : users) {
					for (ModelListener<Organization> listener : listeners) {
						listener.onBeforeRemoveAssociation(organizationId,
							com.liferay.portal.model.User.class.getName(),
							user.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
						listener.onBeforeRemoveAssociation(user.getPrimaryKey(),
							Organization.class.getName(), organizationId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(organizationId) });

			if ((listeners.length > 0) || (userListeners.length > 0)) {
				for (com.liferay.portal.model.User user : users) {
					for (ModelListener<Organization> listener : listeners) {
						listener.onAfterRemoveAssociation(organizationId,
							com.liferay.portal.model.User.class.getName(),
							user.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
						listener.onAfterRemoveAssociation(user.getPrimaryKey(),
							Organization.class.getName(), organizationId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveUser {
		protected RemoveUser(OrganizationPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Orgs WHERE organizationId = ? AND userId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long organizationId, long userId)
			throws SystemException {
			if (_persistenceImpl.containsUser.contains(organizationId, userId)) {
				ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

				for (ModelListener<Organization> listener : listeners) {
					listener.onBeforeRemoveAssociation(organizationId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onBeforeRemoveAssociation(userId,
						Organization.class.getName(), organizationId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(organizationId), new Long(userId)
					});

				for (ModelListener<Organization> listener : listeners) {
					listener.onAfterRemoveAssociation(organizationId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onAfterRemoveAssociation(userId,
						Organization.class.getName(), organizationId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private OrganizationPersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_SELECT_ORGANIZATION = "SELECT organization FROM Organization organization";
	private static final String _SQL_SELECT_ORGANIZATION_WHERE = "SELECT organization FROM Organization organization WHERE ";
	private static final String _SQL_COUNT_ORGANIZATION = "SELECT COUNT(organization) FROM Organization organization";
	private static final String _SQL_COUNT_ORGANIZATION_WHERE = "SELECT COUNT(organization) FROM Organization organization WHERE ";
	private static final String _SQL_GETGROUPS = "SELECT {Group_.*} FROM Group_ INNER JOIN Groups_Orgs ON (Groups_Orgs.groupId = Group_.groupId) WHERE (Groups_Orgs.organizationId = ?)";
	private static final String _SQL_GETGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Orgs WHERE organizationId = ?";
	private static final String _SQL_CONTAINSGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Orgs WHERE organizationId = ? AND groupId = ?";
	private static final String _SQL_GETUSERS = "SELECT {User_.*} FROM User_ INNER JOIN Users_Orgs ON (Users_Orgs.userId = User_.userId) WHERE (Users_Orgs.organizationId = ?)";
	private static final String _SQL_GETUSERSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Orgs WHERE organizationId = ?";
	private static final String _SQL_CONTAINSUSER = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Orgs WHERE organizationId = ? AND userId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "organization.companyId = ?";
	private static final String _FINDER_COLUMN_LOCATIONS_COMPANYID_2 = "organization.companyId = ? AND organization.parentOrganizationId != 0";
	private static final String _FINDER_COLUMN_C_P_COMPANYID_2 = "organization.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_PARENTORGANIZATIONID_2 = "organization.parentOrganizationId = ?";
	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 = "organization.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_NAME_1 = "organization.name IS NULL";
	private static final String _FINDER_COLUMN_C_N_NAME_2 = "organization.name = ?";
	private static final String _FINDER_COLUMN_C_N_NAME_3 = "(organization.name IS NULL OR organization.name = ?)";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "organization.organizationId";
	private static final String _FILTER_SQL_SELECT_ORGANIZATION_WHERE = "SELECT DISTINCT {organization.*} FROM Organization_ organization WHERE ";
	private static final String _FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {Organization_.*} FROM (SELECT DISTINCT organization.organizationId FROM Organization_ organization WHERE ";
	private static final String _FILTER_SQL_SELECT_ORGANIZATION_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN Organization_ ON TEMP_TABLE.organizationId = Organization_.organizationId";
	private static final String _FILTER_SQL_COUNT_ORGANIZATION_WHERE = "SELECT COUNT(DISTINCT organization.organizationId) AS COUNT_VALUE FROM Organization_ organization WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "organization";
	private static final String _FILTER_ENTITY_TABLE = "Organization_";
	private static final String _ORDER_BY_ENTITY_ALIAS = "organization.";
	private static final String _ORDER_BY_ENTITY_TABLE = "Organization_.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Organization exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Organization exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(OrganizationPersistenceImpl.class);
	private static Organization _nullOrganization = new OrganizationImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Organization> toCacheModel() {
				return _nullOrganizationCacheModel;
			}
		};

	private static CacheModel<Organization> _nullOrganizationCacheModel = new CacheModel<Organization>() {
			public Organization toEntityModel() {
				return _nullOrganization;
			}
		};
}