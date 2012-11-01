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
import com.liferay.portal.NoSuchOrgLaborException;
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
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.model.impl.OrgLaborImpl;
import com.liferay.portal.model.impl.OrgLaborModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the org labor service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OrgLaborPersistence
 * @see OrgLaborUtil
 * @generated
 */
public class OrgLaborPersistenceImpl extends BasePersistenceImpl<OrgLabor>
	implements OrgLaborPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link OrgLaborUtil} to access the org labor persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = OrgLaborImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ORGANIZATIONID =
		new FinderPath(OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
			OrgLaborModelImpl.FINDER_CACHE_ENABLED, OrgLaborImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByOrganizationId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ORGANIZATIONID =
		new FinderPath(OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
			OrgLaborModelImpl.FINDER_CACHE_ENABLED, OrgLaborImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByOrganizationId",
			new String[] { Long.class.getName() },
			OrgLaborModelImpl.ORGANIZATIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ORGANIZATIONID = new FinderPath(OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
			OrgLaborModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByOrganizationId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
			OrgLaborModelImpl.FINDER_CACHE_ENABLED, OrgLaborImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
			OrgLaborModelImpl.FINDER_CACHE_ENABLED, OrgLaborImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
			OrgLaborModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the org labor in the entity cache if it is enabled.
	 *
	 * @param orgLabor the org labor
	 */
	public void cacheResult(OrgLabor orgLabor) {
		EntityCacheUtil.putResult(OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
			OrgLaborImpl.class, orgLabor.getPrimaryKey(), orgLabor);

		orgLabor.resetOriginalValues();
	}

	/**
	 * Caches the org labors in the entity cache if it is enabled.
	 *
	 * @param orgLabors the org labors
	 */
	public void cacheResult(List<OrgLabor> orgLabors) {
		for (OrgLabor orgLabor : orgLabors) {
			if (EntityCacheUtil.getResult(
						OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
						OrgLaborImpl.class, orgLabor.getPrimaryKey()) == null) {
				cacheResult(orgLabor);
			}
			else {
				orgLabor.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all org labors.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(OrgLaborImpl.class.getName());
		}

		EntityCacheUtil.clearCache(OrgLaborImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the org labor.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(OrgLabor orgLabor) {
		EntityCacheUtil.removeResult(OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
			OrgLaborImpl.class, orgLabor.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<OrgLabor> orgLabors) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (OrgLabor orgLabor : orgLabors) {
			EntityCacheUtil.removeResult(OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
				OrgLaborImpl.class, orgLabor.getPrimaryKey());
		}
	}

	/**
	 * Creates a new org labor with the primary key. Does not add the org labor to the database.
	 *
	 * @param orgLaborId the primary key for the new org labor
	 * @return the new org labor
	 */
	public OrgLabor create(long orgLaborId) {
		OrgLabor orgLabor = new OrgLaborImpl();

		orgLabor.setNew(true);
		orgLabor.setPrimaryKey(orgLaborId);

		return orgLabor;
	}

	/**
	 * Removes the org labor with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param orgLaborId the primary key of the org labor
	 * @return the org labor that was removed
	 * @throws com.liferay.portal.NoSuchOrgLaborException if a org labor with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OrgLabor remove(long orgLaborId)
		throws NoSuchOrgLaborException, SystemException {
		return remove(Long.valueOf(orgLaborId));
	}

	/**
	 * Removes the org labor with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the org labor
	 * @return the org labor that was removed
	 * @throws com.liferay.portal.NoSuchOrgLaborException if a org labor with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgLabor remove(Serializable primaryKey)
		throws NoSuchOrgLaborException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrgLabor orgLabor = (OrgLabor)session.get(OrgLaborImpl.class,
					primaryKey);

			if (orgLabor == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOrgLaborException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(orgLabor);
		}
		catch (NoSuchOrgLaborException nsee) {
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
	protected OrgLabor removeImpl(OrgLabor orgLabor) throws SystemException {
		orgLabor = toUnwrappedModel(orgLabor);

		Session session = null;

		try {
			session = openSession();

			if (orgLabor.isCachedModel()) {
				orgLabor = (OrgLabor)session.get(OrgLaborImpl.class,
						orgLabor.getPrimaryKeyObj());
			}

			if (orgLabor != null) {
				session.delete(orgLabor);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (orgLabor != null) {
			clearCache(orgLabor);
		}

		return orgLabor;
	}

	@Override
	public OrgLabor updateImpl(com.liferay.portal.model.OrgLabor orgLabor)
		throws SystemException {
		orgLabor = toUnwrappedModel(orgLabor);

		boolean isNew = orgLabor.isNew();

		OrgLaborModelImpl orgLaborModelImpl = (OrgLaborModelImpl)orgLabor;

		Session session = null;

		try {
			session = openSession();

			if (orgLabor.isNew()) {
				session.save(orgLabor);

				orgLabor.setNew(false);
			}
			else {
				session.merge(orgLabor);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !OrgLaborModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((orgLaborModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ORGANIZATIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(orgLaborModelImpl.getOriginalOrganizationId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ORGANIZATIONID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ORGANIZATIONID,
					args);

				args = new Object[] {
						Long.valueOf(orgLaborModelImpl.getOrganizationId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ORGANIZATIONID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ORGANIZATIONID,
					args);
			}
		}

		EntityCacheUtil.putResult(OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
			OrgLaborImpl.class, orgLabor.getPrimaryKey(), orgLabor);

		return orgLabor;
	}

	protected OrgLabor toUnwrappedModel(OrgLabor orgLabor) {
		if (orgLabor instanceof OrgLaborImpl) {
			return orgLabor;
		}

		OrgLaborImpl orgLaborImpl = new OrgLaborImpl();

		orgLaborImpl.setNew(orgLabor.isNew());
		orgLaborImpl.setPrimaryKey(orgLabor.getPrimaryKey());

		orgLaborImpl.setOrgLaborId(orgLabor.getOrgLaborId());
		orgLaborImpl.setOrganizationId(orgLabor.getOrganizationId());
		orgLaborImpl.setTypeId(orgLabor.getTypeId());
		orgLaborImpl.setSunOpen(orgLabor.getSunOpen());
		orgLaborImpl.setSunClose(orgLabor.getSunClose());
		orgLaborImpl.setMonOpen(orgLabor.getMonOpen());
		orgLaborImpl.setMonClose(orgLabor.getMonClose());
		orgLaborImpl.setTueOpen(orgLabor.getTueOpen());
		orgLaborImpl.setTueClose(orgLabor.getTueClose());
		orgLaborImpl.setWedOpen(orgLabor.getWedOpen());
		orgLaborImpl.setWedClose(orgLabor.getWedClose());
		orgLaborImpl.setThuOpen(orgLabor.getThuOpen());
		orgLaborImpl.setThuClose(orgLabor.getThuClose());
		orgLaborImpl.setFriOpen(orgLabor.getFriOpen());
		orgLaborImpl.setFriClose(orgLabor.getFriClose());
		orgLaborImpl.setSatOpen(orgLabor.getSatOpen());
		orgLaborImpl.setSatClose(orgLabor.getSatClose());

		return orgLaborImpl;
	}

	/**
	 * Returns the org labor with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the org labor
	 * @return the org labor
	 * @throws com.liferay.portal.NoSuchModelException if a org labor with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgLabor findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the org labor with the primary key or throws a {@link com.liferay.portal.NoSuchOrgLaborException} if it could not be found.
	 *
	 * @param orgLaborId the primary key of the org labor
	 * @return the org labor
	 * @throws com.liferay.portal.NoSuchOrgLaborException if a org labor with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OrgLabor findByPrimaryKey(long orgLaborId)
		throws NoSuchOrgLaborException, SystemException {
		OrgLabor orgLabor = fetchByPrimaryKey(orgLaborId);

		if (orgLabor == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + orgLaborId);
			}

			throw new NoSuchOrgLaborException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				orgLaborId);
		}

		return orgLabor;
	}

	/**
	 * Returns the org labor with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the org labor
	 * @return the org labor, or <code>null</code> if a org labor with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OrgLabor fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the org labor with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param orgLaborId the primary key of the org labor
	 * @return the org labor, or <code>null</code> if a org labor with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OrgLabor fetchByPrimaryKey(long orgLaborId)
		throws SystemException {
		OrgLabor orgLabor = (OrgLabor)EntityCacheUtil.getResult(OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
				OrgLaborImpl.class, orgLaborId);

		if (orgLabor == _nullOrgLabor) {
			return null;
		}

		if (orgLabor == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				orgLabor = (OrgLabor)session.get(OrgLaborImpl.class,
						Long.valueOf(orgLaborId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (orgLabor != null) {
					cacheResult(orgLabor);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
						OrgLaborImpl.class, orgLaborId, _nullOrgLabor);
				}

				closeSession(session);
			}
		}

		return orgLabor;
	}

	/**
	 * Returns all the org labors where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @return the matching org labors
	 * @throws SystemException if a system exception occurred
	 */
	public List<OrgLabor> findByOrganizationId(long organizationId)
		throws SystemException {
		return findByOrganizationId(organizationId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the org labors where organizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param organizationId the organization ID
	 * @param start the lower bound of the range of org labors
	 * @param end the upper bound of the range of org labors (not inclusive)
	 * @return the range of matching org labors
	 * @throws SystemException if a system exception occurred
	 */
	public List<OrgLabor> findByOrganizationId(long organizationId, int start,
		int end) throws SystemException {
		return findByOrganizationId(organizationId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the org labors where organizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param organizationId the organization ID
	 * @param start the lower bound of the range of org labors
	 * @param end the upper bound of the range of org labors (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching org labors
	 * @throws SystemException if a system exception occurred
	 */
	public List<OrgLabor> findByOrganizationId(long organizationId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ORGANIZATIONID;
			finderArgs = new Object[] { organizationId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ORGANIZATIONID;
			finderArgs = new Object[] {
					organizationId,
					
					start, end, orderByComparator
				};
		}

		List<OrgLabor> list = (List<OrgLabor>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (OrgLabor orgLabor : list) {
				if ((organizationId != orgLabor.getOrganizationId())) {
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

			query.append(_SQL_SELECT_ORGLABOR_WHERE);

			query.append(_FINDER_COLUMN_ORGANIZATIONID_ORGANIZATIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(OrgLaborModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(organizationId);

				list = (List<OrgLabor>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first org labor in the ordered set where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching org labor
	 * @throws com.liferay.portal.NoSuchOrgLaborException if a matching org labor could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OrgLabor findByOrganizationId_First(long organizationId,
		OrderByComparator orderByComparator)
		throws NoSuchOrgLaborException, SystemException {
		OrgLabor orgLabor = fetchByOrganizationId_First(organizationId,
				orderByComparator);

		if (orgLabor != null) {
			return orgLabor;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("organizationId=");
		msg.append(organizationId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchOrgLaborException(msg.toString());
	}

	/**
	 * Returns the first org labor in the ordered set where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching org labor, or <code>null</code> if a matching org labor could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OrgLabor fetchByOrganizationId_First(long organizationId,
		OrderByComparator orderByComparator) throws SystemException {
		List<OrgLabor> list = findByOrganizationId(organizationId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last org labor in the ordered set where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching org labor
	 * @throws com.liferay.portal.NoSuchOrgLaborException if a matching org labor could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OrgLabor findByOrganizationId_Last(long organizationId,
		OrderByComparator orderByComparator)
		throws NoSuchOrgLaborException, SystemException {
		OrgLabor orgLabor = fetchByOrganizationId_Last(organizationId,
				orderByComparator);

		if (orgLabor != null) {
			return orgLabor;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("organizationId=");
		msg.append(organizationId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchOrgLaborException(msg.toString());
	}

	/**
	 * Returns the last org labor in the ordered set where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching org labor, or <code>null</code> if a matching org labor could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OrgLabor fetchByOrganizationId_Last(long organizationId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByOrganizationId(organizationId);

		List<OrgLabor> list = findByOrganizationId(organizationId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the org labors before and after the current org labor in the ordered set where organizationId = &#63;.
	 *
	 * @param orgLaborId the primary key of the current org labor
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next org labor
	 * @throws com.liferay.portal.NoSuchOrgLaborException if a org labor with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OrgLabor[] findByOrganizationId_PrevAndNext(long orgLaborId,
		long organizationId, OrderByComparator orderByComparator)
		throws NoSuchOrgLaborException, SystemException {
		OrgLabor orgLabor = findByPrimaryKey(orgLaborId);

		Session session = null;

		try {
			session = openSession();

			OrgLabor[] array = new OrgLaborImpl[3];

			array[0] = getByOrganizationId_PrevAndNext(session, orgLabor,
					organizationId, orderByComparator, true);

			array[1] = orgLabor;

			array[2] = getByOrganizationId_PrevAndNext(session, orgLabor,
					organizationId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected OrgLabor getByOrganizationId_PrevAndNext(Session session,
		OrgLabor orgLabor, long organizationId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ORGLABOR_WHERE);

		query.append(_FINDER_COLUMN_ORGANIZATIONID_ORGANIZATIONID_2);

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
			query.append(OrgLaborModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(organizationId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(orgLabor);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<OrgLabor> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the org labors.
	 *
	 * @return the org labors
	 * @throws SystemException if a system exception occurred
	 */
	public List<OrgLabor> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the org labors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of org labors
	 * @param end the upper bound of the range of org labors (not inclusive)
	 * @return the range of org labors
	 * @throws SystemException if a system exception occurred
	 */
	public List<OrgLabor> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the org labors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of org labors
	 * @param end the upper bound of the range of org labors (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of org labors
	 * @throws SystemException if a system exception occurred
	 */
	public List<OrgLabor> findAll(int start, int end,
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

		List<OrgLabor> list = (List<OrgLabor>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ORGLABOR);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ORGLABOR.concat(OrgLaborModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<OrgLabor>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<OrgLabor>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the org labors where organizationId = &#63; from the database.
	 *
	 * @param organizationId the organization ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByOrganizationId(long organizationId)
		throws SystemException {
		for (OrgLabor orgLabor : findByOrganizationId(organizationId)) {
			remove(orgLabor);
		}
	}

	/**
	 * Removes all the org labors from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (OrgLabor orgLabor : findAll()) {
			remove(orgLabor);
		}
	}

	/**
	 * Returns the number of org labors where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @return the number of matching org labors
	 * @throws SystemException if a system exception occurred
	 */
	public int countByOrganizationId(long organizationId)
		throws SystemException {
		Object[] finderArgs = new Object[] { organizationId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ORGANIZATIONID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ORGLABOR_WHERE);

			query.append(_FINDER_COLUMN_ORGANIZATIONID_ORGANIZATIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(organizationId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ORGANIZATIONID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of org labors.
	 *
	 * @return the number of org labors
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ORGLABOR);

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
	 * Initializes the org labor persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.OrgLabor")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<OrgLabor>> listenersList = new ArrayList<ModelListener<OrgLabor>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<OrgLabor>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(OrgLaborImpl.class.getName());
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
	private static final String _SQL_SELECT_ORGLABOR = "SELECT orgLabor FROM OrgLabor orgLabor";
	private static final String _SQL_SELECT_ORGLABOR_WHERE = "SELECT orgLabor FROM OrgLabor orgLabor WHERE ";
	private static final String _SQL_COUNT_ORGLABOR = "SELECT COUNT(orgLabor) FROM OrgLabor orgLabor";
	private static final String _SQL_COUNT_ORGLABOR_WHERE = "SELECT COUNT(orgLabor) FROM OrgLabor orgLabor WHERE ";
	private static final String _FINDER_COLUMN_ORGANIZATIONID_ORGANIZATIONID_2 = "orgLabor.organizationId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "orgLabor.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No OrgLabor exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No OrgLabor exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(OrgLaborPersistenceImpl.class);
	private static OrgLabor _nullOrgLabor = new OrgLaborImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<OrgLabor> toCacheModel() {
				return _nullOrgLaborCacheModel;
			}
		};

	private static CacheModel<OrgLabor> _nullOrgLaborCacheModel = new CacheModel<OrgLabor>() {
			public OrgLabor toEntityModel() {
				return _nullOrgLabor;
			}
		};
}