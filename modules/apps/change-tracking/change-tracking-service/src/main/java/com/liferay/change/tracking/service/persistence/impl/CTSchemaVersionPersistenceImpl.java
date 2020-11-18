/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.change.tracking.service.persistence.impl;

import com.liferay.change.tracking.exception.NoSuchSchemaVersionException;
import com.liferay.change.tracking.model.CTSchemaVersion;
import com.liferay.change.tracking.model.CTSchemaVersionTable;
import com.liferay.change.tracking.model.impl.CTSchemaVersionImpl;
import com.liferay.change.tracking.model.impl.CTSchemaVersionModelImpl;
import com.liferay.change.tracking.service.persistence.CTSchemaVersionPersistence;
import com.liferay.change.tracking.service.persistence.impl.constants.CTPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the ct schema version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {CTSchemaVersionPersistence.class, BasePersistence.class})
public class CTSchemaVersionPersistenceImpl
	extends BasePersistenceImpl<CTSchemaVersion>
	implements CTSchemaVersionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CTSchemaVersionUtil</code> to access the ct schema version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CTSchemaVersionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the ct schema versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ct schema versions
	 */
	@Override
	public List<CTSchemaVersion> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct schema versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct schema versions
	 * @param end the upper bound of the range of ct schema versions (not inclusive)
	 * @return the range of matching ct schema versions
	 */
	@Override
	public List<CTSchemaVersion> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct schema versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct schema versions
	 * @param end the upper bound of the range of ct schema versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct schema versions
	 */
	@Override
	public List<CTSchemaVersion> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTSchemaVersion> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct schema versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct schema versions
	 * @param end the upper bound of the range of ct schema versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct schema versions
	 */
	@Override
	public List<CTSchemaVersion> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTSchemaVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<CTSchemaVersion> list = null;

		if (useFinderCache) {
			list = (List<CTSchemaVersion>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CTSchemaVersion ctSchemaVersion : list) {
					if (companyId != ctSchemaVersion.getCompanyId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_CTSCHEMAVERSION_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CTSchemaVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<CTSchemaVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first ct schema version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct schema version
	 * @throws NoSuchSchemaVersionException if a matching ct schema version could not be found
	 */
	@Override
	public CTSchemaVersion findByCompanyId_First(
			long companyId,
			OrderByComparator<CTSchemaVersion> orderByComparator)
		throws NoSuchSchemaVersionException {

		CTSchemaVersion ctSchemaVersion = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (ctSchemaVersion != null) {
			return ctSchemaVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchSchemaVersionException(sb.toString());
	}

	/**
	 * Returns the first ct schema version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct schema version, or <code>null</code> if a matching ct schema version could not be found
	 */
	@Override
	public CTSchemaVersion fetchByCompanyId_First(
		long companyId, OrderByComparator<CTSchemaVersion> orderByComparator) {

		List<CTSchemaVersion> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct schema version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct schema version
	 * @throws NoSuchSchemaVersionException if a matching ct schema version could not be found
	 */
	@Override
	public CTSchemaVersion findByCompanyId_Last(
			long companyId,
			OrderByComparator<CTSchemaVersion> orderByComparator)
		throws NoSuchSchemaVersionException {

		CTSchemaVersion ctSchemaVersion = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (ctSchemaVersion != null) {
			return ctSchemaVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchSchemaVersionException(sb.toString());
	}

	/**
	 * Returns the last ct schema version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct schema version, or <code>null</code> if a matching ct schema version could not be found
	 */
	@Override
	public CTSchemaVersion fetchByCompanyId_Last(
		long companyId, OrderByComparator<CTSchemaVersion> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CTSchemaVersion> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct schema versions before and after the current ct schema version in the ordered set where companyId = &#63;.
	 *
	 * @param schemaVersionId the primary key of the current ct schema version
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct schema version
	 * @throws NoSuchSchemaVersionException if a ct schema version with the primary key could not be found
	 */
	@Override
	public CTSchemaVersion[] findByCompanyId_PrevAndNext(
			long schemaVersionId, long companyId,
			OrderByComparator<CTSchemaVersion> orderByComparator)
		throws NoSuchSchemaVersionException {

		CTSchemaVersion ctSchemaVersion = findByPrimaryKey(schemaVersionId);

		Session session = null;

		try {
			session = openSession();

			CTSchemaVersion[] array = new CTSchemaVersionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, ctSchemaVersion, companyId, orderByComparator, true);

			array[1] = ctSchemaVersion;

			array[2] = getByCompanyId_PrevAndNext(
				session, ctSchemaVersion, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTSchemaVersion getByCompanyId_PrevAndNext(
		Session session, CTSchemaVersion ctSchemaVersion, long companyId,
		OrderByComparator<CTSchemaVersion> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CTSCHEMAVERSION_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CTSchemaVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ctSchemaVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CTSchemaVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct schema versions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CTSchemaVersion ctSchemaVersion :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ctSchemaVersion);
		}
	}

	/**
	 * Returns the number of ct schema versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ct schema versions
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CTSCHEMAVERSION_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"ctSchemaVersion.companyId = ?";

	public CTSchemaVersionPersistenceImpl() {
		setModelClass(CTSchemaVersion.class);

		setModelImplClass(CTSchemaVersionImpl.class);
		setModelPKClass(long.class);

		setTable(CTSchemaVersionTable.INSTANCE);
	}

	/**
	 * Caches the ct schema version in the entity cache if it is enabled.
	 *
	 * @param ctSchemaVersion the ct schema version
	 */
	@Override
	public void cacheResult(CTSchemaVersion ctSchemaVersion) {
		entityCache.putResult(
			CTSchemaVersionImpl.class, ctSchemaVersion.getPrimaryKey(),
			ctSchemaVersion);
	}

	/**
	 * Caches the ct schema versions in the entity cache if it is enabled.
	 *
	 * @param ctSchemaVersions the ct schema versions
	 */
	@Override
	public void cacheResult(List<CTSchemaVersion> ctSchemaVersions) {
		for (CTSchemaVersion ctSchemaVersion : ctSchemaVersions) {
			if (entityCache.getResult(
					CTSchemaVersionImpl.class,
					ctSchemaVersion.getPrimaryKey()) == null) {

				cacheResult(ctSchemaVersion);
			}
		}
	}

	/**
	 * Clears the cache for all ct schema versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CTSchemaVersionImpl.class);

		finderCache.clearCache(CTSchemaVersionImpl.class);
	}

	/**
	 * Clears the cache for the ct schema version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CTSchemaVersion ctSchemaVersion) {
		entityCache.removeResult(CTSchemaVersionImpl.class, ctSchemaVersion);
	}

	@Override
	public void clearCache(List<CTSchemaVersion> ctSchemaVersions) {
		for (CTSchemaVersion ctSchemaVersion : ctSchemaVersions) {
			entityCache.removeResult(
				CTSchemaVersionImpl.class, ctSchemaVersion);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CTSchemaVersionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CTSchemaVersionImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new ct schema version with the primary key. Does not add the ct schema version to the database.
	 *
	 * @param schemaVersionId the primary key for the new ct schema version
	 * @return the new ct schema version
	 */
	@Override
	public CTSchemaVersion create(long schemaVersionId) {
		CTSchemaVersion ctSchemaVersion = new CTSchemaVersionImpl();

		ctSchemaVersion.setNew(true);
		ctSchemaVersion.setPrimaryKey(schemaVersionId);

		ctSchemaVersion.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ctSchemaVersion;
	}

	/**
	 * Removes the ct schema version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param schemaVersionId the primary key of the ct schema version
	 * @return the ct schema version that was removed
	 * @throws NoSuchSchemaVersionException if a ct schema version with the primary key could not be found
	 */
	@Override
	public CTSchemaVersion remove(long schemaVersionId)
		throws NoSuchSchemaVersionException {

		return remove((Serializable)schemaVersionId);
	}

	/**
	 * Removes the ct schema version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ct schema version
	 * @return the ct schema version that was removed
	 * @throws NoSuchSchemaVersionException if a ct schema version with the primary key could not be found
	 */
	@Override
	public CTSchemaVersion remove(Serializable primaryKey)
		throws NoSuchSchemaVersionException {

		Session session = null;

		try {
			session = openSession();

			CTSchemaVersion ctSchemaVersion = (CTSchemaVersion)session.get(
				CTSchemaVersionImpl.class, primaryKey);

			if (ctSchemaVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSchemaVersionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ctSchemaVersion);
		}
		catch (NoSuchSchemaVersionException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected CTSchemaVersion removeImpl(CTSchemaVersion ctSchemaVersion) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ctSchemaVersion)) {
				ctSchemaVersion = (CTSchemaVersion)session.get(
					CTSchemaVersionImpl.class,
					ctSchemaVersion.getPrimaryKeyObj());
			}

			if (ctSchemaVersion != null) {
				session.delete(ctSchemaVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ctSchemaVersion != null) {
			clearCache(ctSchemaVersion);
		}

		return ctSchemaVersion;
	}

	@Override
	public CTSchemaVersion updateImpl(CTSchemaVersion ctSchemaVersion) {
		boolean isNew = ctSchemaVersion.isNew();

		if (!(ctSchemaVersion instanceof CTSchemaVersionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ctSchemaVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ctSchemaVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ctSchemaVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTSchemaVersion implementation " +
					ctSchemaVersion.getClass());
		}

		CTSchemaVersionModelImpl ctSchemaVersionModelImpl =
			(CTSchemaVersionModelImpl)ctSchemaVersion;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(ctSchemaVersion);
			}
			else {
				ctSchemaVersion = (CTSchemaVersion)session.merge(
					ctSchemaVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CTSchemaVersionImpl.class, ctSchemaVersionModelImpl, false, true);

		if (isNew) {
			ctSchemaVersion.setNew(false);
		}

		ctSchemaVersion.resetOriginalValues();

		return ctSchemaVersion;
	}

	/**
	 * Returns the ct schema version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ct schema version
	 * @return the ct schema version
	 * @throws NoSuchSchemaVersionException if a ct schema version with the primary key could not be found
	 */
	@Override
	public CTSchemaVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSchemaVersionException {

		CTSchemaVersion ctSchemaVersion = fetchByPrimaryKey(primaryKey);

		if (ctSchemaVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSchemaVersionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ctSchemaVersion;
	}

	/**
	 * Returns the ct schema version with the primary key or throws a <code>NoSuchSchemaVersionException</code> if it could not be found.
	 *
	 * @param schemaVersionId the primary key of the ct schema version
	 * @return the ct schema version
	 * @throws NoSuchSchemaVersionException if a ct schema version with the primary key could not be found
	 */
	@Override
	public CTSchemaVersion findByPrimaryKey(long schemaVersionId)
		throws NoSuchSchemaVersionException {

		return findByPrimaryKey((Serializable)schemaVersionId);
	}

	/**
	 * Returns the ct schema version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param schemaVersionId the primary key of the ct schema version
	 * @return the ct schema version, or <code>null</code> if a ct schema version with the primary key could not be found
	 */
	@Override
	public CTSchemaVersion fetchByPrimaryKey(long schemaVersionId) {
		return fetchByPrimaryKey((Serializable)schemaVersionId);
	}

	/**
	 * Returns all the ct schema versions.
	 *
	 * @return the ct schema versions
	 */
	@Override
	public List<CTSchemaVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct schema versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct schema versions
	 * @param end the upper bound of the range of ct schema versions (not inclusive)
	 * @return the range of ct schema versions
	 */
	@Override
	public List<CTSchemaVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct schema versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct schema versions
	 * @param end the upper bound of the range of ct schema versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct schema versions
	 */
	@Override
	public List<CTSchemaVersion> findAll(
		int start, int end,
		OrderByComparator<CTSchemaVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct schema versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct schema versions
	 * @param end the upper bound of the range of ct schema versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct schema versions
	 */
	@Override
	public List<CTSchemaVersion> findAll(
		int start, int end,
		OrderByComparator<CTSchemaVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<CTSchemaVersion> list = null;

		if (useFinderCache) {
			list = (List<CTSchemaVersion>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CTSCHEMAVERSION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CTSCHEMAVERSION;

				sql = sql.concat(CTSchemaVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CTSchemaVersion>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the ct schema versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CTSchemaVersion ctSchemaVersion : findAll()) {
			remove(ctSchemaVersion);
		}
	}

	/**
	 * Returns the number of ct schema versions.
	 *
	 * @return the number of ct schema versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_CTSCHEMAVERSION);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "schemaVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CTSCHEMAVERSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CTSchemaVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ct schema version persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new CTSchemaVersionModelArgumentsResolver(),
			new HashMapDictionary<>());

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(CTSchemaVersionImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private BundleContext _bundleContext;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CTSCHEMAVERSION =
		"SELECT ctSchemaVersion FROM CTSchemaVersion ctSchemaVersion";

	private static final String _SQL_SELECT_CTSCHEMAVERSION_WHERE =
		"SELECT ctSchemaVersion FROM CTSchemaVersion ctSchemaVersion WHERE ";

	private static final String _SQL_COUNT_CTSCHEMAVERSION =
		"SELECT COUNT(ctSchemaVersion) FROM CTSchemaVersion ctSchemaVersion";

	private static final String _SQL_COUNT_CTSCHEMAVERSION_WHERE =
		"SELECT COUNT(ctSchemaVersion) FROM CTSchemaVersion ctSchemaVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ctSchemaVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CTSchemaVersion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CTSchemaVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CTSchemaVersionPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class CTSchemaVersionModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			CTSchemaVersionModelImpl ctSchemaVersionModelImpl =
				(CTSchemaVersionModelImpl)baseModel;

			long columnBitmask = ctSchemaVersionModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					ctSchemaVersionModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						ctSchemaVersionModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					ctSchemaVersionModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return CTSchemaVersionImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return CTSchemaVersionTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			CTSchemaVersionModelImpl ctSchemaVersionModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						ctSchemaVersionModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = ctSchemaVersionModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}